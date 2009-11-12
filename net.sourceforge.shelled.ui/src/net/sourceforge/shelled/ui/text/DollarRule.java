/*******************************************************************************
 * Copyright (c) 2009 Red Hat Inc. and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Alexander Kurtakov - initial API and implementation
 *******************************************************************************/
package net.sourceforge.shelled.ui.text;

import org.eclipse.jface.text.rules.ICharacterScanner;
import org.eclipse.jface.text.rules.IRule;
import org.eclipse.jface.text.rules.IToken;
import org.eclipse.jface.text.rules.IWordDetector;
import org.eclipse.jface.text.rules.Token;

public class DollarRule implements IRule {
	private final boolean bracesRequired;
	private final StringBuffer buffer = new StringBuffer();
	private final char closeBrace;
	private final IToken defaultToken;
	private final IWordDetector detector;
	private final char openBrace;
	private final IToken successToken;

	public DollarRule(IWordDetector detector, IToken defaultToken,
			IToken token, boolean bracesRequired, char openBrace,
			char closeBrace) {
		this.detector = detector;
		this.successToken = token;
		this.defaultToken = defaultToken;
		this.openBrace = openBrace;
		this.closeBrace = closeBrace;
		this.bracesRequired = bracesRequired;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.jface.text.rules.IRule#evaluate(org.eclipse.jface.text.rules
	 * .ICharacterScanner)
	 */
	public IToken evaluate(ICharacterScanner scanner) {
		int c = scanner.read();
		if (detector.isWordStart((char) c)) {
			buffer.setLength(0);
			int counter = 0;
			do {
				buffer.append((char) c);
				c = scanner.read();
				++counter;
			} while ((c != ICharacterScanner.EOF)
					&& (detector.isWordPart((char) c) || ((c == openBrace) && (counter == 1))));
			if (c == closeBrace)
				buffer.append((char) c);
			else
				scanner.unread();
			if (buffer.length() > 1) {
				char startChar = buffer.charAt(1);
				char endChar = buffer.charAt(buffer.length() - 1);
				if (((startChar == openBrace) && (endChar == closeBrace))
						|| (!bracesRequired && (Character
								.isJavaIdentifierPart(startChar) && Character
								.isJavaIdentifierPart(endChar))))
					return successToken;
			}
			if (defaultToken.isUndefined())
				unreadBuffer(scanner);
			return defaultToken;
		}
		scanner.unread();
		return Token.UNDEFINED;
	}

	/**
	 * Returns the characters in the buffer to the scanner.
	 * 
	 * @param scanner
	 *            the scanner to be used
	 */
	protected void unreadBuffer(ICharacterScanner scanner) {
		for (int i = buffer.length() - 1; i >= 0; i--)
			scanner.unread();
	}
}
