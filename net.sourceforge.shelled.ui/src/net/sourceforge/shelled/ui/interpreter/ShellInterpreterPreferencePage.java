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

import org.eclipse.dltk.internal.debug.ui.interpreters.InterpretersBlock;
import org.eclipse.dltk.internal.debug.ui.interpreters.ScriptInterpreterPreferencePage;

public class ShellInterpreterPreferencePage extends
		ScriptInterpreterPreferencePage {

	public static final String PAGE_ID = "net.sourceforge.shelled.ui.preferences.interpreters";

	@Override
	public InterpretersBlock createInterpretersBlock() {
		return new ShellScriptInterpretersBlock();
	}
}
