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

public class ShellModel {
	
	private List<MethodDeclaration> functions;
	private List<FieldDeclaration> variables;
	private List<MethodDeclaration> statements;
	
	public ShellModel() {
		functions = new ArrayList<MethodDeclaration>();
		variables = new ArrayList<FieldDeclaration>();
		statements = new ArrayList<MethodDeclaration>();
	}
	
	public void addFunction(MethodDeclaration funtion) {
		functions.add(funtion);
	}

	public List<MethodDeclaration> getFunctions() {
		return functions;
	}
	
	public void addVariable(FieldDeclaration variable) {
		variables.add(variable);
	}

	public List<FieldDeclaration> getVariables() {
		return variables;
	}

	public void addStatement(MethodDeclaration statement) {
		statements.add(statement);
	}

	public List<MethodDeclaration> getStatements() {
		return statements;
	}
}
