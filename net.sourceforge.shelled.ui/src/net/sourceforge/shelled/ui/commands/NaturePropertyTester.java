/*******************************************************************************
 * Copyright (c) 2010 Mat Booth and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package net.sourceforge.shelled.ui.commands;

import net.sourceforge.shelled.core.ShelledNature;

import org.eclipse.core.expressions.PropertyTester;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IAdaptable;

/**
 * A property tester that returns true if the project containing any given
 * resource is a ShellEd-natured project.
 */
public class NaturePropertyTester extends PropertyTester {

	// Properties made available for test by this tester
	private static final String HAS_SHELLED_NATURE = "hasShellEdNature"; //$NON-NLS-1$

	@Override
	public boolean test(Object receiver, String property, Object[] args,
			Object expectedValue) {
		IResource resource = (IResource) ((IAdaptable) receiver)
				.getAdapter(IResource.class);
		if (property.equals(HAS_SHELLED_NATURE)) {
			try {
				IProject proj = resource.getProject();
				return proj.isAccessible()
						&& proj.hasNature(ShelledNature.SHELLED_NATURE);
			} catch (CoreException e) {
				return false;
			}
		}
		return false;
	}
}
