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
package net.sourceforge.shelled.ui.text.tests;

import net.sourceforge.shelled.ui.text.DollarDetector;
import net.sourceforge.shelled.ui.text.DollarRule;
import net.sourceforge.shelled.ui.text.IShellPartitions;

import org.eclipse.jface.text.rules.Token;
import org.junit.Assert;
import org.junit.Test;

/**
 * Tests the dollar rule that detects $var style parameter expansions.
 */
public class DollarRuleTest {

	// Mock objects
	private MockScanner fScanner;

	// Objects under test
	private static DollarRule fRule = new DollarRule(new DollarDetector(),
			Token.UNDEFINED, new Token(IShellPartitions.PARAM_CONTENT_TYPE));

	/**
	 * Don't match ${} syntax because that is taken care of in another rule.
	 */
	@Test
	public void testDollarBraceMatch() {
		fScanner = new MockScanner("${braces}");
		Assert.assertTrue(fRule.evaluate(fScanner).isUndefined());
		Assert.assertEquals("", fScanner.getBuffer().substring(0,
				fScanner.getOffset()));
	}

	/**
	 * Match simple alpha-numeric vars that may contain underscores. Don't match
	 * [ or ] because array index references must be inside braces.
	 */
	@Test
	public void testDollarMatch() {
		fScanner = new MockScanner("$alpha_01[5] numeric vars");
		Assert.assertFalse(fRule.evaluate(fScanner).isUndefined());
		Assert.assertEquals("$alpha_01", fScanner.getBuffer().substring(0,
				fScanner.getOffset()));
	}

	/**
	 * If the variable starts with a digit, then it's a reference to a
	 * positional parameter and only that one digit may be highlighted
	 * (characters after the digit are ignored and parameters 10+ must be
	 * referenced using the brace syntax.) Special parameters are detected
	 * similarly.
	 */
	@Test
	public void testPositionalSpecialMatch() {
		// List of valid special or positional parameters
		char specials[] = new char[] { '*', '@', '#', '?', '-', '$', '!', '_',
				'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', };
		for (int i = '!'; i <= '~'; i++) {
			String s = "$" + Character.toString((char) i);
			fScanner = new MockScanner(s + "some more text");

			// Make sure that special/positional parameters are matched
			// correctly
			boolean isSpecial = false;
			for (int j = 0; j < specials.length; j++) {
				if ((char) i == specials[j]) {
					Assert.assertFalse("Testing Special " + s, fRule.evaluate(
							fScanner).isUndefined());
					Assert.assertEquals(s, fScanner.getBuffer().substring(0,
							fScanner.getOffset()));
					isSpecial = true;
					break;
				}
			}

			if (!isSpecial) {
				if (Character.isLetter((char) i)) {
					// Make sure that valid characters that are not specials are
					// matched normally
					Assert.assertFalse("Testing Not Special " + s, fRule
							.evaluate(fScanner).isUndefined());
					Assert.assertEquals(s + "some", fScanner.getBuffer()
							.substring(0, fScanner.getOffset()));
				} else {
					// Make sure that invalid characters are not matched at all
					Assert.assertTrue("Testing Not Special " + s, fRule
							.evaluate(fScanner).isUndefined());
					Assert.assertEquals("", fScanner.getBuffer().substring(0,
							fScanner.getOffset()));
				}
			}
		}
	}
}
