/*******************************************************************************
 * Copyright (c) 2009 Red Hat Inc. and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package net.sourceforge.shelled.ui.text;

import org.eclipse.jface.text.rules.IWordDetector;

/**
 * Detemines whether given any given character forms part of a shell variable.
 */
public class DollarDetector implements IWordDetector {

	@Override
	public boolean isWordPart(char c) {
		return Character.isJavaIdentifierPart(c) || (c == '[') || (c == ']');
	}

	@Override
	public boolean isWordStart(char c) {
		return c == '$';
	}

}
