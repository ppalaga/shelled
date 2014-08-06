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
package net.sourceforge.shelled.core.parser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.HashSet;
import java.util.Set;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.dltk.ast.declarations.Declaration;
import org.eclipse.dltk.ast.declarations.FieldDeclaration;
import org.eclipse.dltk.ast.declarations.MethodDeclaration;
import org.eclipse.dltk.ast.declarations.ModuleDeclaration;
import org.eclipse.dltk.ast.expressions.MethodCallExpression;
import org.eclipse.dltk.ast.parser.AbstractSourceParser;
import org.eclipse.dltk.ast.parser.IModuleDeclaration;
import org.eclipse.dltk.ast.references.VariableReference;
import org.eclipse.dltk.compiler.env.IModuleSource;
import org.eclipse.dltk.compiler.problem.IProblemReporter;

public class ShellScriptSourceParser extends AbstractSourceParser {

	private ShellModel parse(StringReader reader,
			ShellModuleDeclaration moduleDeclaration) {
		ShellModel model = new ShellModel();

		BufferedReader bReader = new BufferedReader(reader);
		String line;
		int lineStart = 0;
		int commentLength = 0;
		Set<String> functionNames = new HashSet<String>();
		Set<String> varNames = new HashSet<String>();
		MethodDeclaration mDeclaration = null;
		Stack<Declaration> tmp = new Stack<Declaration>();
		boolean isPrevLnContinued = false;

		try {
			while ((line = bReader.readLine()) != null) {
				String trimmedLine = line.trim();
				if (trimmedLine.length() == 0
						|| line.trim().charAt(0) == LexicalConstants.HASH) {
					lineStart += line.length() + 1;
					continue;
				}
				int hashOffset = line.indexOf(LexicalConstants.HASH);
				if (hashOffset >= 0) {
					commentLength = line.length() - hashOffset;
					line = line.substring(0, hashOffset);
				}
				if (line.contains(LexicalConstants.LPAREN_RPAREN)) {
					final int lBracket = Math.max(
							line.indexOf(LexicalConstants.LBRACE), 0);
					final String fToken = ReservedWord.FUNCTION.token();
					final int fOffset = line.indexOf(fToken);
					final int fPlusEight = fOffset >= 0 ? fOffset
							+ fToken.length() : 0;
					mDeclaration = new MethodDeclaration(line.substring(
							fPlusEight, line.indexOf(LexicalConstants.LPAREN))
							.trim(), lineStart, lineStart + line.length() - 1,
							lBracket + lineStart, lBracket + lineStart);
					functionNames.add(line.substring(fPlusEight,
							line.indexOf(LexicalConstants.LPAREN)).trim());
					tmp.push(mDeclaration);
					model.addFunction(mDeclaration);
				} else if (line.contains("function ")) {
					final String fToken = ReservedWord.FUNCTION.token();
					final int fPlusEight = line.indexOf(fToken)
							+ fToken.length();
					int lBracket = line.indexOf(LexicalConstants.LBRACE);
					lBracket = lBracket == -1 ? line.length() : lBracket - 1;
					if (fPlusEight >= line.length())
						continue;
					if (fPlusEight > lBracket)
						continue;
					mDeclaration = new MethodDeclaration(line.substring(
							fPlusEight, lBracket).trim(), lineStart, lineStart
							+ line.length() - 1, lBracket + lineStart, lBracket
							+ lineStart);
					functionNames.add(line.substring(fPlusEight, lBracket)
							.trim());
					tmp.push(mDeclaration);
					model.addFunction(mDeclaration);
				} else if (line.trim().equals(LexicalConstants.RBRACE_STRING)) {
					if (mDeclaration != null) {
						if (!tmp.isEmpty()) {
							mDeclaration = (MethodDeclaration) tmp.pop();
							mDeclaration.setEnd(lineStart + line.length());
						}
					}
				}
				Pattern assignmentPattern = Pattern.compile("(^|\\W)\\w*=");
				Matcher matcher = assignmentPattern.matcher(line);
				if (matcher.find()) {
					String varName = line.substring(matcher.start(),
							matcher.end() - 1);
					if (isValidName(varName)) {
						FieldDeclaration variable = new FieldDeclaration(
								varName, lineStart + matcher.start(), lineStart
										+ matcher.end(), lineStart
										+ matcher.start(), lineStart
										+ matcher.end());
						varNames.add(varName);
						model.addVariable(variable);
					}
				}

				// start of if statement
				if (line.contains("if ") && !line.contains("elif ")) {
					mDeclaration = new MethodDeclaration(line.substring(0,
							line.length()).trim(), lineStart
							+ line.indexOf(ReservedWord.IF.token()), lineStart
							+ line.length() - 1, lineStart, lineStart
							+ line.length());
					model.addStatement(mDeclaration);
					tmp.push(mDeclaration);

					// end of if statement
				} else if (line.trim().equals(ReservedWord.FI.token())) {
					if (!tmp.isEmpty()) {
						mDeclaration = (MethodDeclaration) tmp.pop();
						mDeclaration.setEnd(lineStart
								+ line.indexOf(ReservedWord.FI.token()));
					}

					// start of while statement
				} else if (line.contains("while ")) {
					mDeclaration = new MethodDeclaration(line.substring(0,
							line.length()).trim(), lineStart
							+ line.indexOf(ReservedWord.WHILE.token()),
							lineStart + line.length() - 1, lineStart, lineStart
									+ line.length());
					model.addStatement(mDeclaration);
					tmp.push(mDeclaration);

					// start of until statement
				} else if (line.contains("until ")) {
					mDeclaration = new MethodDeclaration(line.substring(0,
							line.length()).trim(), lineStart
							+ line.indexOf(ReservedWord.UNTIL.token()),
							lineStart + line.length() - 1, lineStart, lineStart
									+ line.length());
					model.addStatement(mDeclaration);
					tmp.push(mDeclaration);

					// done statement encountered
				} else if (line.contains("done ")
						|| line.trim().equals(ReservedWord.DONE.token())) {
					if (!tmp.isEmpty()) {
						mDeclaration = (MethodDeclaration) tmp.pop();
						mDeclaration.setEnd(lineStart
								+ line.indexOf(ReservedWord.DONE.token()));
					}

					// start of for statement
				} else if (line.contains("for ")) {
					mDeclaration = new MethodDeclaration(line.substring(0,
							line.length()).trim(), lineStart
							+ line.indexOf(ReservedWord.FOR.token()), lineStart
							+ line.length() - 1, lineStart, lineStart
							+ line.length());
					model.addStatement(mDeclaration);
					tmp.push(mDeclaration);

					// start of case statement
				} else if (line.contains("case ")) {
					mDeclaration = new MethodDeclaration(line.substring(0,
							line.length()).trim(), lineStart
							+ line.indexOf(ReservedWord.CASE.token()),
							lineStart + line.length() - 1, lineStart, lineStart
									+ line.length());
					model.addStatement(mDeclaration);
					tmp.push(mDeclaration);

					// end of case statement
				} else if (line.trim().equals(ReservedWord.ESAC.token())) {
					if (!tmp.isEmpty()) {
						mDeclaration = (MethodDeclaration) tmp.pop();
						mDeclaration.setEnd(lineStart
								+ line.indexOf(ReservedWord.ESAC.token()));
					}
				}

				// multi-line commands and literals
				if (line.charAt(line.length() - 1) == LexicalConstants.BACKSLASH
						&& !isPrevLnContinued) {
					isPrevLnContinued = true;
					mDeclaration = new MethodDeclaration(line.substring(0,
							line.length()).trim(), lineStart
							+ line.indexOf("\\"),
							lineStart + line.length() - 1, lineStart, lineStart
									+ line.length());
					tmp.push(mDeclaration);
					model.addStatement(mDeclaration);
				} else if (line.charAt(line.length() - 1) == LexicalConstants.BACKSLASH
						&& isPrevLnContinued) {
					if (!tmp.isEmpty()) {
						mDeclaration = (MethodDeclaration) tmp.pop();
						mDeclaration.setEnd(lineStart
								+ line.indexOf(LexicalConstants.BACKSLASH));
						tmp.push(mDeclaration);
					}
				} else if (isPrevLnContinued) {
					isPrevLnContinued = false;
					if (!tmp.isEmpty()) {
						mDeclaration = (MethodDeclaration) tmp.pop();
						mDeclaration.setEnd(lineStart + line.length() - 2);
					}
				}

				for (String funcName : functionNames) {
					if (line.contains(funcName)) {
						moduleDeclaration
								.addStatement(new MethodCallExpression(
										lineStart + line.indexOf(funcName),
										lineStart + line.indexOf(funcName)
												+ funcName.length(), null,
										funcName, null));
					}
				}
				for (String varName : varNames) {
					Pattern varRefPattern = Pattern.compile("(^|\\W)\\$\\b"
							+ varName + "\\b");
					Matcher varRefMatcher = varRefPattern.matcher(line);
					while (varRefMatcher.find()) {
						moduleDeclaration.addStatement(new VariableReference(
								lineStart + varRefMatcher.start(), lineStart
										+ varRefMatcher.end(), varName));
					}
				}

				lineStart += line.length() + commentLength + 1;
				commentLength = 0;
			}
			bReader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return model;
	}

	private boolean isValidName(String varName) {
		if (varName.matches("\\w+")) {
			return true;
		}
		return false;
	}

	private void processNode(ShellModel parse,
			ModuleDeclaration moduleDeclaration) {
		for (MethodDeclaration functionNode : parse.getFunctions()) {
			moduleDeclaration.addStatement(functionNode);
		}
		for (FieldDeclaration variableNode : parse.getVariables()) {
			moduleDeclaration.addStatement(variableNode);
		}
		for (MethodDeclaration statement : parse.getStatements()) {
			moduleDeclaration.addStatement(statement);
		}
	}

	@Override
	public IModuleDeclaration parse(IModuleSource source, IProblemReporter arg1) {
		ShellModuleDeclaration moduleDeclaration = new ShellModuleDeclaration(
				source.getSourceContents().length());

		ShellModel shellModel = parse(
				new StringReader(source.getSourceContents()), moduleDeclaration);
		moduleDeclaration.setFunctions(shellModel.getFunctions());
		moduleDeclaration.setVariables(shellModel.getVariables());
		processNode(shellModel, moduleDeclaration);
		return moduleDeclaration;
	}

}
