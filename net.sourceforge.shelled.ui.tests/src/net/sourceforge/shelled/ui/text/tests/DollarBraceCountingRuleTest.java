/*******************************************************************************
 * Copyright (c) 2009 Mat Booth and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package net.sourceforge.shelled.ui.text.tests;

import net.sourceforge.shelled.ui.text.DollarBraceCountingRule;
import net.sourceforge.shelled.ui.text.IShellPartitions;

import org.eclipse.jface.text.rules.Token;
import org.junit.Assert;
import org.junit.Test;

/**
 * Tests the dollar-prefixed brace-counting single-line rule. Basically we're
 * testing to make sure syntax highlighting is correct for ${} and $() blocks.
 */
public class DollarBraceCountingRuleTest {

	// Mock objects
	private MockScanner fScanner;

	// Objects under test
	private static DollarBraceCountingRule fRule = new DollarBraceCountingRule(
			'{', '}', new Token(IShellPartitions.PARAM_CONTENT_TYPE), '\\');

	/**
	 * Highlight a simple pair of dollar-prefixed braces.
	 */
	@Test
	public void testSimpleMatch() {
		fScanner = new MockScanner("${basic}test}");
		Assert.assertFalse(fRule.evaluate(fScanner).isUndefined());
		Assert.assertEquals("${basic}", fScanner.getBuffer().substring(0,
				fScanner.getOffset()));
	}

	/**
	 * Highlight a pair of braces enclosing escaped braces.
	 */
	@Test
	public void testEscapedBraceMatch() {
		fScanner = new MockScanner("${basic\\}\\}}test}");
		Assert.assertFalse(fRule.evaluate(fScanner).isUndefined());
		Assert.assertEquals("${basic\\}\\}}", fScanner.getBuffer().substring(0,
				fScanner.getOffset()));
	}

	/**
	 * Highlight the correct amount of nested braces.
	 */
	@Test
	public void testNestedBraceMatch() {
		fScanner = new MockScanner("${a{b{cd}e}f}g}");
		Assert.assertFalse(fRule.evaluate(fScanner).isUndefined());
		Assert.assertEquals("${a{b{cd}e}f}", fScanner.getBuffer().substring(0,
				fScanner.getOffset()));
	}

	/**
	 * Highlight everything up to the end of the line, if that occurs before the
	 * final closing brace.
	 */
	@Test
	public void testEndOfLineMatch() {
		fScanner = new MockScanner("${a{b}c\nd}");
		Assert.assertFalse(fRule.evaluate(fScanner).isUndefined());
		Assert.assertEquals("${a{b}c\n", fScanner.getBuffer().substring(0,
				fScanner.getOffset()));
	}

	/**
	 * Do not highlight if EOF interrupts the pattern.
	 */
	@Test
	public void testEndOfFileMatch() {
		fScanner = new MockScanner("${end of file is here:");
		Assert.assertTrue(fRule.evaluate(fScanner).isUndefined());
		Assert.assertEquals("", fScanner.getBuffer().substring(0,
				fScanner.getOffset()));
	}
}
