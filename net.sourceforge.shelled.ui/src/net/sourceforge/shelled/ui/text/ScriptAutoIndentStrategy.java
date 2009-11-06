/*******************************************************************************
 * Copyright (c) 2009 Doug Satchwell and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Doug Satchwell - initial API and implementation
 *******************************************************************************/
package net.sourceforge.shelled.ui.text;

import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.DefaultIndentLineAutoEditStrategy;
import org.eclipse.jface.text.DocumentCommand;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.rules.IRule;
import org.eclipse.jface.text.rules.IToken;

/**
 * An indent strategy capable of indenting on any set of words, depending on the
 * rules that are set.
 * 
 */
public class ScriptAutoIndentStrategy extends DefaultIndentLineAutoEditStrategy {
	private DocumentAndCommandScanner scanner = new DocumentAndCommandScanner();

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.jface.text.DefaultAutoIndentStrategy#customizeDocumentCommand
	 * (org.eclipse.jface.text.IDocument,
	 * org.eclipse.jface.text.DocumentCommand)
	 */
	@Override
	public void customizeDocumentCommand(IDocument d, DocumentCommand c) {
		if (c.length == 0 && c.text != null && endsWithDelimiter(d, c.text))
			smartIndentAfterNewLine(d, c);
		else if (c.text.length() > 0) {
			try {
				int docLength = d.getLength();
				int p = c.offset == docLength ? c.offset - 1 : c.offset;
				int line = d.getLineOfOffset(p);
				int lineOffset = d.getLineOffset(line);

				scanner.setRange(d, c, lineOffset, c.offset - lineOffset);
				while (true) {
					IToken token = scanner.nextToken();
					if (token.isEOF())
						break;

					if (token.isOther()) {
						IndentType type = (IndentType) token.getData();
						if (type != null && type == IndentType.DECREMENT
								|| type == IndentType.INFLEXION) {
							smartInsertAfterBracket(d, c);
							break;
						}
					}
				}
			} catch (BadLocationException e) {
				// e.printStackTrace();
			}
		}
	}

	/**
	 * Returns whether or not the text ends with one of the given search
	 * strings.
	 */
	private boolean endsWithDelimiter(IDocument d, String txt) {
		String[] delimiters = d.getLegalLineDelimiters();
		for (int i = 0; i < delimiters.length; i++) {
			if (txt.endsWith(delimiters[i]))
				return true;
		}
		return false;
	}

	/**
	 * Returns the line number of the next bracket after end.
	 * 
	 * @param document
	 *            - the document being parsed
	 * @param line
	 *            - the line to start searching back from
	 * @param end
	 *            - the end position to search back from
	 * @param closingBracketIncrease
	 *            - the number of brackets to skip
	 * @return the line number of the next matching bracket after end
	 */
	protected int findMatchingOpenBracket(IDocument document, int line,
			int end, int closingBracketIncrease) throws BadLocationException {
		int start = document.getLineOffset(line);
		int brackcount = getBracketCount(document, start, end, false)
				- closingBracketIncrease;

		// sum up the brackets counts of each line (closing brackets count
		// negative,
		// opening positive) until we find a line the brings the count to zero
		while (brackcount < 0) {
			line--;
			if (line < 0) {
				return -1;
			}
			start = document.getLineOffset(line);
			end = start + document.getLineLength(line) - 1;
			brackcount += getBracketCount(document, start, end, false);
		}
		return line;
	}

	/**
	 * Returns the bracket value of a section of text. Closing brackets have a
	 * value of -1 and open brackets have a value of 1.
	 * 
	 * @param document
	 *            - the document being parsed
	 * @param start
	 *            - the start position for the search
	 * @param end
	 *            - the end position for the search
	 * @param ignoreCloseBrackets
	 *            - whether or not to ignore closing brackets in the count
	 * @return the line number of the next matching bracket after end
	 */
	private int getBracketCount(IDocument document, int start, int end,
			boolean ignoreCloseBrackets) {
		int bracketcount = 0;
		scanner.setRange(document, start, end - start);

		while (true) {
			IToken token = scanner.nextToken();
			if (token.isEOF())
				break;

			if (token.isOther()) {
				IndentType type = (IndentType) token.getData();
				if (type == IndentType.INCREMENT
						|| (ignoreCloseBrackets && type == IndentType.INFLEXION))
					++bracketcount;
				else if (type == IndentType.DECREMENT)
					--bracketcount;
			}
		}
		return bracketcount;
	}

	/**
	 * Returns the String at line with the leading whitespace removed.
	 * 
	 * @param document
	 *            - the document being parsed
	 * @param line
	 *            - the line being searched
	 * @return the String at line with the leading whitespace removed.
	 */
	protected String getIndentOfLine(IDocument document, int line)
			throws BadLocationException {
		if (line > -1) {
			int start = document.getLineOffset(line);
			int end = start + document.getLineLength(line) - 1;
			int whiteend = findEndOfWhiteSpace(document, start, end);
			return document.get(start, whiteend - start);
		} else {
			return "";
		}
	}

	/**
	 * Set the rules that this will use to identify indentations.
	 * 
	 * @param rules
	 */
	public void setRules(IRule[] rules) {
		scanner.setRules(rules);
	}

	/**
	 * Set the indent of a new line based on the command provided in the
	 * supplied document.
	 * 
	 * @param document
	 *            - the document being parsed
	 * @param command
	 *            - the command being performed
	 */
	protected void smartIndentAfterNewLine(IDocument document,
			DocumentCommand command) {
		int docLength = document.getLength();
		if (command.offset == -1 || docLength == 0)
			return;

		try {
			int p = command.offset == docLength ? command.offset - 1
					: command.offset;
			int line = document.getLineOfOffset(p);

			StringBuffer buf = new StringBuffer(command.text);
			if (command.offset < docLength
					&& document.getChar(command.offset) == '}') {
				int indLine = findMatchingOpenBracket(document, line,
						command.offset, 0);
				if (indLine == -1)
					indLine = line;
				buf.append(getIndentOfLine(document, indLine));
			} else {
				int start = document.getLineOffset(line);
				int whiteend = findEndOfWhiteSpace(document, start,
						command.offset);
				buf.append(document.get(start, whiteend - start));
				if (getBracketCount(document, start, command.offset, true) > 0) {
					// Indent as many tabs as specified in preferences
					buf.append("\t");
					// buf.append(PreferenceUtil.getIndent());
				}
			}
			command.text = buf.toString();

		} catch (BadLocationException x) {
			x.printStackTrace(System.err);
		}
	}

	/**
	 * Set the indent of a bracket based on the command provided in the supplied
	 * document.
	 * 
	 * @param document
	 *            - the document being parsed
	 * @param command
	 *            - the command being performed
	 */
	protected void smartInsertAfterBracket(IDocument document,
			DocumentCommand command) {
		if (command.offset == -1 || document.getLength() == 0)
			return;

		try {
			int p = command.offset == document.getLength() ? command.offset - 1
					: command.offset;
			int line = document.getLineOfOffset(p);
			int start = document.getLineOffset(line);
			int whiteend = findEndOfWhiteSpace(document, start, command.offset);

			// shift only when line does not contain any text up to the closing
			// bracket
			for (int i = whiteend; i < command.offset; i++)
				if (Character.isWhitespace(document.getChar(i)))
					return;

			// evaluate the line with the opening bracket that matches out
			// closing bracket
			int indLine = findMatchingOpenBracket(document, line,
					command.offset, 1);
			// if (indLine != -1 && indLine != line)
			// {
			// take the indent of the found line
			StringBuffer replaceText = new StringBuffer(getIndentOfLine(
					document, indLine));
			// add the rest of the current line including the just added close
			// bracket
			replaceText.append(document
					.get(whiteend, command.offset - whiteend));
			replaceText.append(command.text);
			// modify document command
			command.length = command.offset - start;
			command.offset = start;
			command.text = replaceText.toString();
			// }
		} catch (BadLocationException x) {
			x.printStackTrace(System.err);
		}
	}
}
