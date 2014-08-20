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

import net.sourceforge.shelled.core.parser.LexicalConstants;

import org.eclipse.jface.text.rules.ICharacterScanner;
import org.eclipse.jface.text.rules.IRule;
import org.eclipse.jface.text.rules.IToken;
import org.eclipse.jface.text.rules.IWordDetector;
import org.eclipse.jface.text.rules.Token;

public class AssignmentRule implements IRule {
	private final StringBuffer buffer = new StringBuffer();
	private final IToken defaultToken;
	private final IWordDetector detector;
	private final IToken successToken;

	public AssignmentRule(IWordDetector detector, IToken defaultToken,
			IToken token) {
		this.detector = detector;
		this.successToken = token;
		this.defaultToken = defaultToken;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.jface.text.rules.IRule#evaluate(org.eclipse.jface.text.rules
	 * .ICharacterScanner)
	 */
	@Override
	public IToken evaluate(ICharacterScanner scanner) {
		char c = (char) scanner.read();
		if (detector.isWordStart((c))) {
			buffer.setLength(0);
			do {
				buffer.append((c));
				c = (char) scanner.read();
			} while ((c != ICharacterScanner.EOF) && detector.isWordPart(c));
			scanner.unread();
			if (c == LexicalConstants.EQ)
				return successToken;
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
