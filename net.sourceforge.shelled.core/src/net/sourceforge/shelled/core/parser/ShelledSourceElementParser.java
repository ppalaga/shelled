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
package net.sourceforge.shelled.core.parser;

import net.sourceforge.shelled.core.ShelledNature;

import org.eclipse.dltk.compiler.ISourceElementRequestor;
import org.eclipse.dltk.compiler.ISourceElementRequestor.FieldInfo;
import org.eclipse.dltk.compiler.ISourceElementRequestor.TypeInfo;
import org.eclipse.dltk.core.AbstractSourceElementParser;
import org.eclipse.dltk.core.ISourceModuleInfoCache.ISourceModuleInfo;

public class ShelledSourceElementParser extends AbstractSourceElementParser {

	@Override
	protected String getNatureId() {
		return ShelledNature.SHELLED_NATURE;
	}

	@Override
	public void parseSourceModule(
			org.eclipse.dltk.compiler.env.ISourceModule module,
			ISourceModuleInfo astCache) {
		final ShellModuleDeclaration moduleDeclaration = (ShellModuleDeclaration)parse(module, astCache);

		ISourceElementRequestor requestor = getRequestor();

		requestor.enterModule();
		TypeInfo tInfo = new TypeInfo();
		tInfo.name=module.getModelElement().getElementName();
		requestor.enterType(tInfo);
		for (FunctionInfo method : moduleDeclaration.getFunctionsInfo()){
			requestor.enterMethod(method);
			requestor.exitMethod(method.declarationEnd);
		}
		for (FieldInfo variable : moduleDeclaration.getFieldsInfo()){
			requestor.enterField(variable);
			requestor.exitMethod(variable.nameSourceEnd);
		}
		requestor.exitType(module.getSourceContents().length());
		requestor.exitModule(module.getSourceContents().length());
	}

}
