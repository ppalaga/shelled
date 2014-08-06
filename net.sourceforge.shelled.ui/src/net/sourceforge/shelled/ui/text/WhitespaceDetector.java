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

import org.eclipse.jface.text.rules.IWhitespaceDetector;

/**
 * A stateless {@link IWhitespaceDetector} therefore the {@link #INSTANCE}
 * singleton can be used rather than creating a new instance every time.
 *
 * @author <a href="mailto:ppalaga@redhat.com">Peter Palaga</a>
 */
public class WhitespaceDetector implements IWhitespaceDetector {
	public static final WhitespaceDetector INSTANCE = new WhitespaceDetector();

	/**
	 * Use {@link #INSTANCE}.
	 */
	private WhitespaceDetector() {
		super();
	}

	/**
	 * @see IWhitespaceDetector#isWhitespace
	 */
	@Override
	public boolean isWhitespace(char c) {
		return Character.isWhitespace(c);
	}
}
