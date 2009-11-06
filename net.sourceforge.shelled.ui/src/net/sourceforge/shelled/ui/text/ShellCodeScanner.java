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

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import net.sourceforge.shelled.ui.IShellColorConstants;

import org.eclipse.dltk.ui.text.AbstractScriptScanner;
import org.eclipse.dltk.ui.text.IColorManager;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.text.rules.EndOfLineRule;
import org.eclipse.jface.text.rules.IRule;
import org.eclipse.jface.text.rules.IToken;
import org.eclipse.jface.text.rules.IWordDetector;
import org.eclipse.jface.text.rules.SingleLineRule;
import org.eclipse.jface.text.rules.WhitespaceRule;
import org.eclipse.jface.text.rules.WordRule;

public class ShellCodeScanner extends AbstractScriptScanner {
	public class ShellWordDetector implements IWordDetector {
		public boolean isWordPart(char character) {
			return Character.isJavaIdentifierPart(character);
		}

		public boolean isWordStart(char character) {
			return Character.isJavaIdentifierStart(character);
		}
	}

	public static String[] KEYWORDS = { "do", "done", "if", "fi", "then",
			"else", "elif", "case", "esac", "while", "for", "in", "select",
			"time", "until", "function", "[", "[[", "]", "]]" };

	private static List<String> fgCommands = getCommands();

	private static String fgTokenProperties[] = new String[] {
			IShellColorConstants.SHELL_COMMENT,
			IShellColorConstants.SHELL_DEFAULT,
			IShellColorConstants.SHELL_KEYWORD,
			IShellColorConstants.SHELL_HASHBANG,
			IShellColorConstants.SHELL_EVAL,
			IShellColorConstants.SHELL_SINGLE_QUOTE,
			IShellColorConstants.SHELL_DOUBLE_QUOTE,
			IShellColorConstants.SHELL_VARIABLE,
			IShellColorConstants.SHELL_COMMAND };

	public ShellCodeScanner(IColorManager manager, IPreferenceStore store) {
		super(manager, store);
		this.initialize();
	}

	public static List<String> getCommands() {
		String path = System.getenv("PATH");
		List<String> commands = new ArrayList<String>();
		String[] pathEntries = path.split(":");
		for (String pathEntry : pathEntries) {
			File dir = new File(pathEntry);
			if (dir.exists() && dir.isDirectory()) {
				File[] files = dir.listFiles();
				for (File file : files) {
					if (file.canExecute()) {
						commands.add(file.getName());
					}
				}
			}
		}
		return commands;
	}

	@Override
	protected List<IRule> createRules() {
		List<IRule> rules = new ArrayList<IRule>();
		IToken keyword = this.getToken(IShellColorConstants.SHELL_KEYWORD);
		IToken commandToken = this.getToken(IShellColorConstants.SHELL_COMMAND);
		IToken other = this.getToken(IShellColorConstants.SHELL_DEFAULT);
		IToken hashbang = this.getToken(IShellColorConstants.SHELL_HASHBANG);
		IToken comment = this.getToken(IShellColorConstants.SHELL_COMMENT);
		IToken eval = this.getToken(IShellColorConstants.SHELL_EVAL);
		IToken doubleQuote = this
				.getToken(IShellColorConstants.SHELL_DOUBLE_QUOTE);
		IToken singleQuote = this
				.getToken(IShellColorConstants.SHELL_SINGLE_QUOTE);
		IToken variable = this.getToken(IShellColorConstants.SHELL_VARIABLE);
		rules.add(new EndOfLineRule("#!", hashbang));
		rules.add(new EndOfLineRule("#", comment));
		rules.add(new SingleLineRule("$(", ")", eval, '\\', false, true));
		rules.add(new SingleLineRule("`", "`", eval, '\\', false, true));
		rules
				.add(new SingleLineRule("\"", "\"", doubleQuote, '\\', false,
						true));
		rules.add(new SingleLineRule("'", "'", singleQuote, '\\', false, true));
		WordRule wordRule = new WordRule(new ShellWordDetector(), other);
		for (String element : KEYWORDS) {
			wordRule.addWord(element, keyword);
		}
		for (String command : fgCommands) {
			wordRule.addWord(command, commandToken);
		}
		rules.add(wordRule);
		IWordDetector wordDetector = new IWordDetector() {
			public boolean isWordPart(char c) {
				return Character.isJavaIdentifierPart(c);
			}

			public boolean isWordStart(char c) {
				return Character.isJavaIdentifierStart(c);
			}
		};

		IWordDetector dollarDetector = new IWordDetector() {
			public boolean isWordPart(char c) {
				return Character.isJavaIdentifierPart(c);
			}

			public boolean isWordStart(char c) {
				return c == '$';
			}
		};
		rules.add(new WhitespaceRule(new WhitespaceDetector()));
		rules.add(new AssignmentRule(wordDetector, variable, variable));
		rules.add(new DollarRule(dollarDetector, variable, variable, false,
				'{', '}'));

		this.setDefaultReturnToken(other);
		return rules;
	}

	@Override
	protected String[] getTokenProperties() {
		return fgTokenProperties;
	}

}
