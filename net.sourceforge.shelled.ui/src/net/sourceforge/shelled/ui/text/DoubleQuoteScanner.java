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

import java.util.ArrayList;
import java.util.List;

import net.sourceforge.shelled.ui.IShellColorConstants;

import org.eclipse.dltk.ui.text.AbstractScriptScanner;
import org.eclipse.dltk.ui.text.IColorManager;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.text.rules.IRule;
import org.eclipse.jface.text.rules.IToken;
import org.eclipse.jface.text.rules.SingleLineRule;
import org.eclipse.jface.text.rules.WhitespaceRule;

public class DoubleQuoteScanner extends AbstractScriptScanner {

	private static String fgTokenProperties[] = new String[] {
			IShellColorConstants.SHELL_DEFAULT,
			IShellColorConstants.SHELL_EVAL,
			IShellColorConstants.SHELL_DOUBLE_QUOTE,
			IShellColorConstants.SHELL_VARIABLE };

	public DoubleQuoteScanner(IColorManager manager, IPreferenceStore store) {
		super(manager, store);
		initialize();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.something.eclipse.script.text.ScriptScanner#createRules()
	 */
	@Override
	protected List<IRule> createRules() {
		List<IRule> rules = new ArrayList<IRule>();

		// Add all keywords as rules
		IToken defaultToken = this
				.getToken(IShellColorConstants.SHELL_DOUBLE_QUOTE);
		IToken evalToken = this.getToken(IShellColorConstants.SHELL_EVAL);
		IToken varToken = this.getToken(IShellColorConstants.SHELL_VARIABLE);

		// Add generic whitespace rule. This is here for efficiency reasons,
		// there is a LOT of whitespace
		// and when a token is detected the other rules are not evaluated.
		rules.add(new WhitespaceRule(new WhitespaceDetector()));
		rules.add(new DollarBraceCountingRule('(', ')', evalToken, '\\'));
		rules.add(new DollarBraceCountingRule('{', '}', varToken, '\\'));
		rules.add(new DollarRule(new DollarDetector(), defaultToken, varToken,
				false, '{', '}'));
		rules.add(new SingleLineRule("`", "`", evalToken, '\\', false));
		setDefaultReturnToken(defaultToken);
		return rules;
	}

	@Override
	protected String[] getTokenProperties() {
		return fgTokenProperties;
	}

}
