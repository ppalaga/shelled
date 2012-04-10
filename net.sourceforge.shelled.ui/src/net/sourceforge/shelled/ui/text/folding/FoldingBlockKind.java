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
package net.sourceforge.shelled.ui.text.folding;

import org.eclipse.dltk.ui.text.folding.IFoldingBlockKind;

public enum FoldingBlockKind implements IFoldingBlockKind {
	FUNCTION, COMMENT;

	@Override
	public boolean isComment() {
		return this == COMMENT;
	}
}