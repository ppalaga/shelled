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

import org.eclipse.dltk.internal.launching.StandardInterpreterRunner;
import org.eclipse.dltk.launching.AbstractInterpreterInstall;
import org.eclipse.dltk.launching.IInterpreterInstallType;
import org.eclipse.dltk.launching.IInterpreterRunner;

public class ShellScriptInstall extends AbstractInterpreterInstall {
	public ShellScriptInstall(IInterpreterInstallType type, String id) {
		super(type, id);
	}

	@Override
	public String getNatureId() {
		return ShelledNature.SHELLED_NATURE;
	}

	@Override
	public IInterpreterRunner getInterpreterRunner(String mode) {
		return new StandardInterpreterRunner(this);
	}

}
