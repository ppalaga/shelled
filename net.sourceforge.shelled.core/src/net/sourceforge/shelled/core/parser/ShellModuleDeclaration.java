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

import org.eclipse.dltk.ast.declarations.FieldDeclaration;
import org.eclipse.dltk.ast.declarations.MethodDeclaration;
import org.eclipse.dltk.ast.declarations.ModuleDeclaration;
import org.eclipse.dltk.compiler.IElementRequestor.FieldInfo;

public class ShellModuleDeclaration extends ModuleDeclaration {

	private List<FunctionInfo> functionsInfo;
	private List<FieldInfo> variablesInfo;

	public ShellModuleDeclaration(int sourceLength) {
		super(sourceLength);
		functionsInfo = new ArrayList<FunctionInfo>();
		variablesInfo = new ArrayList<FieldInfo>();
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

	public void setVariables(List<FieldDeclaration> variables) {
		getVariablesList().addAll(variables);
		for (FieldDeclaration method : variables) {
			FieldInfo vInfo = new FieldInfo();
			vInfo.name = method.getName();
			vInfo.nameSourceStart = method.getNameStart();
			vInfo.nameSourceEnd = method.getNameEnd();
			vInfo.declarationStart = method.sourceStart();
			variablesInfo.add(vInfo);
		}
	}

	public List<FunctionInfo> getFunctionsInfo() {
		return functionsInfo;
	}

	public List<FieldInfo> getFieldsInfo() {
		return variablesInfo;
	}
}
