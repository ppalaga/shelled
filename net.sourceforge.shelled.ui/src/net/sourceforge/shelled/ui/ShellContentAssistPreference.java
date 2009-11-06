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

import org.eclipse.dltk.ui.text.ScriptTextTools;
import org.eclipse.dltk.ui.text.completion.ContentAssistPreference;

public class ShellContentAssistPreference extends ContentAssistPreference {
	private static ShellContentAssistPreference instance;

	public static ContentAssistPreference getDefault() {
		if (instance == null) {
			instance = new ShellContentAssistPreference();
		}
		return instance;
	}

	@Override
	protected ScriptTextTools getTextTools() {
		return Activator.getDefault().getTextTools();
	}

}
