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
package net.sourceforge.shelled.ui;

import net.sourceforge.shelled.core.ShellScriptLanguageToolkit;

import org.eclipse.dltk.core.IDLTKLanguageToolkit;
import org.eclipse.dltk.ui.AbstractDLTKUILanguageToolkit;
import org.eclipse.jface.preference.IPreferenceStore;

public class ShelledUILanguageToolkit extends AbstractDLTKUILanguageToolkit {
	@Override
	public IDLTKLanguageToolkit getCoreToolkit() {
		return ShellScriptLanguageToolkit.getDefault();
	}

	@Override
	public IPreferenceStore getPreferenceStore() {
		return Activator.getDefault().getPreferenceStore();
	}

}
