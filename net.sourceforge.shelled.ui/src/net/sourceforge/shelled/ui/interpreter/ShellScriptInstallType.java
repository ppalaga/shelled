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

import java.io.IOException;

import net.sourceforge.shelled.core.ShelledNature;
import net.sourceforge.shelled.ui.Activator;

import org.eclipse.core.runtime.ILog;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.dltk.core.environment.IDeployment;
import org.eclipse.dltk.core.environment.IFileHandle;
import org.eclipse.dltk.internal.launching.AbstractInterpreterInstallType;
import org.eclipse.dltk.launching.EnvironmentVariable;
import org.eclipse.dltk.launching.IInterpreterInstall;
import org.eclipse.dltk.launching.LibraryLocation;

public class ShellScriptInstallType extends AbstractInterpreterInstallType {
	private static final String[] INTERPRETER_NAMES = { "sh", "bash" };

	@Override
	protected IPath createPathFile(IDeployment deployment) throws IOException {
		return null;
	}

	@Override
	public synchronized LibraryLocation[] getDefaultLibraryLocations(
			IFileHandle installLocation, EnvironmentVariable[] variables,
			IProgressMonitor monitor) {
		return new LibraryLocation[0];
	}

	@Override
	protected IInterpreterInstall doCreateInterpreterInstall(String id) {
		return new ShellScriptInstall(this, id);
	}

	@Override
	protected ILog getLog() {
		return Activator.getDefault().getLog();
	}

	public String getName() {
		return "Shell interperter";
	}

	public String getNatureId() {
		return ShelledNature.SHELLED_NATURE;
	}

	@Override
	protected String getPluginId() {
		return Activator.PLUGIN_ID;
	}

	@Override
	protected String[] getPossibleInterpreterNames() {
		return INTERPRETER_NAMES;
	}

}
