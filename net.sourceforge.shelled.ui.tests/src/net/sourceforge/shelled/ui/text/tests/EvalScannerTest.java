/*******************************************************************************
 * Copyright (c) 2010 Mat Booth and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package net.sourceforge.shelled.ui.text.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import net.sourceforge.shelled.ui.Activator;
import net.sourceforge.shelled.ui.IShellColorConstants;
import net.sourceforge.shelled.ui.text.EvalScanner;

import org.eclipse.jface.text.TextAttribute;
import org.eclipse.jface.text.rules.IToken;
import org.eclipse.jface.text.rules.RuleBasedScanner;
import org.junit.Test;

/**
 * Test the scanner for command substitution partitions.
 */
public class EvalScannerTest extends AbstractScannerTester {

	@Override
	protected RuleBasedScanner getScanner() {
		return new EvalScanner(cm, Activator.getDefault().getPreferenceStore());
	}

	@Override
	protected String getText() {
		return "$vars in command ${substitutions}";
	}

	/**
	 * Tests that we correctly highlight $dollar style parameter expansions
	 * within command substitutions.
	 */
	@Test
	public void testDollar() {
		IToken token = getNextToken();
		assertTrue(token instanceof IToken);
		assertEquals(5, scanner.getTokenLength());
		assertEquals(0, scanner.getTokenOffset());
		TextAttribute ta = (TextAttribute) token.getData();
		assertEquals(ta.getForeground(), cm.getColor(IShellColorConstants.SHELL_VARIABLE));
	}

	/**
	 * Tests that we correctly highlight ${dollar-brace} style parameter
	 * expansions within command substitutions.
	 */
	@Test
	public void testDollarBrace() {
		IToken token = getNthToken(14);
		assertTrue(token instanceof IToken);
		assertEquals(16, scanner.getTokenLength());
		assertEquals(17, scanner.getTokenOffset());
		TextAttribute ta = (TextAttribute) token.getData();
		assertEquals(ta.getForeground(), cm.getColor(IShellColorConstants.SHELL_VARIABLE));
	}

	/**
	 * Everything else should have the default highlighting for this partition
	 * type.
	 */
	@Test
	public void testDefault() {
		IToken token = getNthToken(3);
		assertTrue(token instanceof IToken);
		assertEquals(1, scanner.getTokenLength());
		assertEquals(6, scanner.getTokenOffset());
		TextAttribute ta = (TextAttribute) token.getData();
		assertEquals(ta.getForeground(), cm.getColor(IShellColorConstants.SHELL_EVAL));
	}
}
