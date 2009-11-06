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

import net.sourceforge.shelled.core.ShelledNature;

import org.eclipse.dltk.launching.AbstractScriptLaunchConfigurationDelegate;

public class LaunchConfigurationDelegate extends
		AbstractScriptLaunchConfigurationDelegate {

	@Override
	public String getLanguageId() {
		return ShelledNature.SHELLED_NATURE;
	}

}
