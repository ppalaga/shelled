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

import org.eclipse.dltk.core.AbstractLanguageToolkit;
import org.eclipse.dltk.core.IDLTKLanguageToolkit;

public class ShellScriptLanguageToolkit extends AbstractLanguageToolkit {
	private static ShellScriptLanguageToolkit toolkit;

	public static IDLTKLanguageToolkit getDefault() {
		if (toolkit == null) {
			toolkit = new ShellScriptLanguageToolkit();
		}
		return toolkit;
	}

	public String getLanguageName() {
		return "Shell Script";
	}

	public String getNatureId() {
		return ShelledNature.SHELLED_NATURE;
	}

	public String getLanguageContentType() {
		return "net.sourceforge.shelled.content-type";
	}

}
