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
package net.sourceforge.shelled.ui.interpreter;

import net.sourceforge.shelled.core.ShelledNature;

import org.eclipse.dltk.core.PreferencesLookupDelegate;
import org.eclipse.dltk.debug.ui.launchConfigurations.MainLaunchConfigurationTab;

public class ShellMainLaunchConfigurationTab extends MainLaunchConfigurationTab {

	public ShellMainLaunchConfigurationTab(String mode) {
		super(mode);
	}

	@Override
	protected boolean breakOnFirstLinePrefEnabled(
			PreferencesLookupDelegate delegate) {
		return false;
	}

	@Override
	protected boolean dbpgLoggingPrefEnabled(PreferencesLookupDelegate delegate) {
		return false;
	}

	@Override
	public String getNatureID() {
		return ShelledNature.SHELLED_NATURE;
	}

}
