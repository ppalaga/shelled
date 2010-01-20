/*******************************************************************************
 * Copyright (c) 2010 Mat Booth and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package net.sourceforge.shelled.ui.text.tests;

import org.eclipse.dltk.internal.ui.text.DLTKColorManager;
import org.eclipse.jface.text.Document;
import org.eclipse.jface.text.rules.IToken;
import org.eclipse.jface.text.rules.RuleBasedScanner;
import org.junit.Before;

/**
 * Common base class for rules-based scanner tests.
 */
public abstract class AbstractScannerTester {

	protected DLTKColorManager cm = new DLTKColorManager();
	protected RuleBasedScanner scanner;

	/**
	 * Reset the scanner before every test.
	 */
	@Before
	public void setup() {
		scanner = getScanner();
		scanner.setRange(new Document(getText()), 0, getText().length());
	}

	/**
	 * To be implemented by the test.
	 * 
	 * @return an instance of the scanner under test
	 */
	protected abstract RuleBasedScanner getScanner();

	/**
	 * To be implemented by the test.
	 * 
	 * @return the text that will be used to test the scanner
	 */
	protected abstract String getText();

	/**
	 * Gets the nth token from the scanner's document.
	 * 
	 * @param n
	 *            1 for the very next token, 2 for the token after that, etc
	 * @return the nth token
	 */
	protected IToken getNthToken(int n) {
		for (int i = 0; i < n - 1; i++) {
			scanner.nextToken();
		}
		return scanner.nextToken();
	}

	/**
	 * Gets the very next token from the scanner's document.
	 * 
	 * @return the next token
	 */
	protected IToken getNextToken() {
		return getNthToken(1);
	}
}
