/*******************************************************************************
 * Copyright (c) 2011 Mat Booth and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Mat Booth
 *******************************************************************************/
package net.sourceforge.shelled.ui.text;

import net.sourceforge.shelled.core.parser.LexicalConstants;

import org.eclipse.jface.text.rules.IWordDetector;

/**
 * Determines whether given any given character forms part of a shell
 * assignment.
 */
public class AssignmentDetector implements IWordDetector {

	@Override
	public boolean isWordPart(char c) {
		return Character.isJavaIdentifierPart(c)
				|| (c == LexicalConstants.LSQUARE)
				|| (c == LexicalConstants.RSQUARE);
	}

	@Override
	public boolean isWordStart(char c) {
		return Character.isJavaIdentifierStart(c);
	}

};
