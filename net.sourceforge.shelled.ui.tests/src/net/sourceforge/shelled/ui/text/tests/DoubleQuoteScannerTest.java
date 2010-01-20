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
import net.sourceforge.shelled.ui.text.DoubleQuoteScanner;

import org.eclipse.jface.text.TextAttribute;
import org.eclipse.jface.text.rules.IToken;
import org.eclipse.jface.text.rules.RuleBasedScanner;
import org.junit.Test;

/**
 * Test the scanner for double quote partitions.
 */
public class DoubleQuoteScannerTest extends AbstractScannerTester {

	@Override
	protected RuleBasedScanner getScanner() {
		return new DoubleQuoteScanner(cm, Activator.getDefault().getPreferenceStore());
	}

	@Override
	protected String getText() {
		return "x${var}x$var`eval`x$(eval)";
	}

	/**
	 * Tests that we correctly highlight $dollar style parameter expansions
	 * within interpolated strings.
	 */
	@Test
	public void testDollar() {
		IToken token = getNthToken(4);
		assertTrue(token instanceof IToken);
		assertEquals(4, scanner.getTokenLength());
		assertEquals(8, scanner.getTokenOffset());
		TextAttribute ta = (TextAttribute) token.getData();
		assertEquals(ta.getForeground(), cm.getColor(IShellColorConstants.SHELL_VARIABLE));
	}

	/**
	 * Tests that we correctly highlight ${dollar-brace} style parameter
	 * expansions within interpolated strings.
	 */
	@Test
	public void testDollarBrace() {
		IToken token = getNthToken(2);
		assertTrue(token instanceof IToken);
		assertEquals(6, scanner.getTokenLength());
		assertEquals(1, scanner.getTokenOffset());
		TextAttribute ta = (TextAttribute) token.getData();
		assertEquals(ta.getForeground(), cm.getColor(IShellColorConstants.SHELL_VARIABLE));
	}

	/**
	 * Tests that we correctly highlight `back-tick` style command substitutions
	 * within interpolated strings.
	 */
	@Test
	public void testEval() {
		IToken token = getNthToken(5);
		assertTrue(token instanceof IToken);
		assertEquals(6, scanner.getTokenLength());
		assertEquals(12, scanner.getTokenOffset());
		TextAttribute ta = (TextAttribute) token.getData();
		assertEquals(ta.getForeground(), cm.getColor(IShellColorConstants.SHELL_EVAL));
	}

	/**
	 * Tests that we correctly highlight $(dollar-brace) style command
	 * substitutions within interpolated strings.
	 */
	@Test
	public void testDollarEval() {
		IToken token = getNthToken(7);
		assertTrue(token instanceof IToken);
		assertEquals(7, scanner.getTokenLength());
		assertEquals(19, scanner.getTokenOffset());
		TextAttribute ta = (TextAttribute) token.getData();
		assertEquals(ta.getForeground(), cm.getColor(IShellColorConstants.SHELL_EVAL));
	}

	/**
	 * Everything else should have the default highlighting for this partition
	 * type.
	 */
	@Test
	public void testDefault() {
		IToken token = getNthToken(1);
		assertTrue(token instanceof IToken);
		assertEquals(1, scanner.getTokenLength());
		assertEquals(0, scanner.getTokenOffset());
		TextAttribute ta = (TextAttribute) token.getData();
		assertEquals(ta.getForeground(), cm
				.getColor(IShellColorConstants.SHELL_DOUBLE_QUOTE));

		token = getNthToken(2);
		assertTrue(token instanceof IToken);
		assertEquals(1, scanner.getTokenLength());
		assertEquals(7, scanner.getTokenOffset());
		ta = (TextAttribute) token.getData();
		assertEquals(ta.getForeground(), cm
				.getColor(IShellColorConstants.SHELL_DOUBLE_QUOTE));

		token = getNthToken(3);
		assertTrue(token instanceof IToken);
		assertEquals(1, scanner.getTokenLength());
		assertEquals(18, scanner.getTokenOffset());
		ta = (TextAttribute) token.getData();
		assertEquals(ta.getForeground(), cm
				.getColor(IShellColorConstants.SHELL_DOUBLE_QUOTE));
	}
}
