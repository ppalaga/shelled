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

import java.util.ArrayList;
import java.util.List;

import org.eclipse.dltk.ast.declarations.MethodDeclaration;
import org.eclipse.dltk.ast.declarations.ModuleDeclaration;

public class ShellModuleDeclaration extends ModuleDeclaration {

	private List<FunctionInfo> functionsInfo;

	public ShellModuleDeclaration(int sourceLength) {
		super(sourceLength);
		functionsInfo = new ArrayList<FunctionInfo>();
	}

	public void setFunctions(List<MethodDeclaration> functions) {
		getFunctionList().addAll(functions);
		for (MethodDeclaration method : functions) {
			FunctionInfo mInfo = new FunctionInfo();
			mInfo.name = method.getName();
			mInfo.nameSourceStart = method.getNameStart();
			mInfo.nameSourceEnd = method.getNameEnd();
			mInfo.declarationStart = method.sourceStart();
			mInfo.declarationEnd = method.sourceEnd();
			functionsInfo.add(mInfo);
		}
	}

	public List<FunctionInfo> getFunctionsInfo() {
		return functionsInfo;
	}
}
