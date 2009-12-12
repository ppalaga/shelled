/*******************************************************************************
 * Copyright (c) 2009 Mat Booth and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package net.sourceforge.shelled.ui.text.tests;

import org.eclipse.jface.text.rules.ICharacterScanner;

/**
 * Simple concrete character scanner that we can use to test IPredicateRule and
 * IRule implementations. As far as the rule being tested is concerned, this
 * scanner is feeding it chunks of a real document.
 */
public class MockScanner implements ICharacterScanner {

	private int fOffset = 0;
	private String fBuffer;

	public MockScanner(String buffer) {
		fBuffer = buffer;
	}

	public int getOffset() {
		return fOffset;
	}

	public String getBuffer() {
		return fBuffer;
	}

	@Override
	public int getColumn() {
		return 0;
	}

	@Override
	public char[][] getLegalLineDelimiters() {
		return new char[][] { { '\r' }, { '\n' }, { '\r', '\n' }, };
	}

	@Override
	public int read() {

		if (fOffset < fBuffer.length()) {
			return fBuffer.charAt(fOffset++);
		}
		return EOF;
	}

	@Override
	public void unread() {
		--fOffset;
	}
}
