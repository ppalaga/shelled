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
package net.sourceforge.shelled.core;

import java.util.regex.Pattern;

import org.eclipse.dltk.core.ScriptContentDescriber;

public class ShelledContentDescriber extends ScriptContentDescriber {

	protected static Pattern[] header_patterns = { Pattern.compile("^#!.*sh.*",
			Pattern.MULTILINE) };

	@Override
	protected Pattern[] getHeaderPatterns() {
		return header_patterns;
	}
}
