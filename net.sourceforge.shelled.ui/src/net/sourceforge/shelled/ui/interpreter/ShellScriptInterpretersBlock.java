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

import org.eclipse.dltk.core.environment.IEnvironment;
import org.eclipse.dltk.internal.debug.ui.interpreters.AddScriptInterpreterDialog;
import org.eclipse.dltk.internal.debug.ui.interpreters.InterpretersBlock;
import org.eclipse.dltk.launching.IInterpreterInstall;
import org.eclipse.dltk.launching.ScriptRuntime;

public class ShellScriptInterpretersBlock extends InterpretersBlock {
	@Override
	protected AddScriptInterpreterDialog createInterpreterDialog(
			IEnvironment environment, IInterpreterInstall standin) {
		AddShellScriptInterpreterDialog dialog = new AddShellScriptInterpreterDialog(
				this, getShell(),
				ScriptRuntime.getInterpreterInstallTypes(getCurrentNature()),
				standin);
		return dialog;
	}

	@Override
	protected String getCurrentNature() {
		return ShelledNature.SHELLED_NATURE;
	}

}
