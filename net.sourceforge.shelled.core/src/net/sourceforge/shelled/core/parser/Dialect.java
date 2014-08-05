/*******************************************************************************
 * Copyright (c) 2012 Red Hat Inc. and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Alexander Kurtakov - initial API and implementation
 *******************************************************************************/
package net.sourceforge.shelled.core.parser;

/**
 * Shell Command Language dialect identifier.
 *
 * @author <a href="mailto:ppalaga@redhat.com">Peter Palaga</a>
 */
public enum Dialect {
	/** Any dialect */
	ANY(0xFFFFFFFF),

	/** Strict POSIX compatible Shell Command Language */
	POSIX(1),

	/** Bourne Shell language */
	BOURNE_SHELL(1 << 1),

	/** Bash Language */
	BASH(1 << 2);

	/** Typically an n-left-shifted one */
	final int id;

	/**
	 * @param id
	 */
	private Dialect(int id) {
		this.id = id;
	}
}
