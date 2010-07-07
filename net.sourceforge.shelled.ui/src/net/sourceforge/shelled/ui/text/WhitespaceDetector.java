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

public class WhitespaceDetector implements IWhitespaceDetector {

	/**
	 * @see IWhitespaceDetector#isWhitespace
	 */
	@Override
	public boolean isWhitespace(char c) {
		return Character.isWhitespace(c);
	}
}
