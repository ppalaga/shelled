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

import java.util.ArrayList;
import java.util.List;

import org.eclipse.dltk.ast.ASTVisitor;
import org.eclipse.dltk.ast.declarations.MethodDeclaration;
import org.eclipse.dltk.ast.declarations.ModuleDeclaration;
import org.eclipse.dltk.ast.expressions.Expression;
import org.eclipse.dltk.ast.expressions.MethodCallExpression;
import org.eclipse.dltk.ast.references.VariableReference;
import org.eclipse.dltk.codeassist.ScriptSelectionEngine;
import org.eclipse.dltk.compiler.env.IModuleSource;
import org.eclipse.dltk.core.DLTKCore;
import org.eclipse.dltk.core.IModelElement;
import org.eclipse.dltk.core.IModelElementVisitor;
import org.eclipse.dltk.core.ISourceModule;
import org.eclipse.dltk.core.ModelException;
import org.eclipse.dltk.core.SourceParserUtil;

public class ShelledSelectionEngine extends ScriptSelectionEngine {
	private ISourceModule sourceModule;

	@Override
	public IModelElement[] select(IModuleSource module, final int offset, int i) {
		sourceModule = (ISourceModule) module.getModelElement();
		ModuleDeclaration moduleDeclaration = SourceParserUtil
				.getModuleDeclaration(sourceModule, null);
		final List<IModelElement> results = new ArrayList<IModelElement>();
		try {
			moduleDeclaration.traverse(new ASTVisitor() {
				@Override
				public boolean visit(Expression s) throws Exception {
					if ((s.sourceStart() <= offset)
							&& (offset <= s.sourceEnd())) {
						if (s instanceof MethodCallExpression) {
							findDeclaration(
									((MethodCallExpression) s).getName(),
									results);
						}
						if (s instanceof VariableReference) {
							findDeclaration(((VariableReference) s).getName(),
									results);
						}
					}
					return super.visit(s);
				}

				@Override
				public boolean visit(MethodDeclaration s) throws Exception {
					if ((s.getNameStart() <= offset)
							&& (offset <= s.getNameEnd())) {
						findDeclaration(s.getName(), results);
					}
					return super.visit(s);
				}

			});
		} catch (Exception e) {
			if (DLTKCore.DEBUG) {
				e.printStackTrace();
			}
		}
		return results.toArray(new IModelElement[results.size()]);
	}

	private void findDeclaration(final String name,
			final List<IModelElement> results) {
		try {
			this.sourceModule.accept(new IModelElementVisitor() {
				@Override
				public boolean visit(IModelElement element) {
					if (element.getElementName().equals(name)) {
						results.add(element);
					}
					return true;
				}
			});
		} catch (ModelException e) {
			if (DLTKCore.DEBUG) {
				e.printStackTrace();
			}
		}
	}

}
