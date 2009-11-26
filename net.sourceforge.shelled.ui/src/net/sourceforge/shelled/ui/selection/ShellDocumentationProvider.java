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
package net.sourceforge.shelled.ui.selection;

import java.io.Reader;
import java.io.StringReader;

import org.eclipse.dltk.core.IMember;
import org.eclipse.dltk.ui.documentation.IScriptDocumentationProvider;
import org.eclipse.linuxtools.man.parser.ManPage;

public class ShellDocumentationProvider implements IScriptDocumentationProvider {

	@Override
	public Reader getInfo(String content) {
		return new StringReader(new ManPage(content).getStrippedHtmlPage()
				.toString());
	}

	@Override
	public Reader getInfo(IMember element, boolean lookIntoParents,
			boolean lookIntoExternal) {
		// TODO show comments for functions if there is any
		return null;
	}

}
