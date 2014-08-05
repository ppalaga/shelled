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

import net.sourceforge.shelled.ui.Activator;
import net.sourceforge.shelled.ui.text.IShellPartitions;

import org.eclipse.dltk.ui.text.folding.IFoldingContent;
import org.eclipse.dltk.ui.text.folding.PartitioningFoldingBlockProvider;

public class ShellCodeFoldingBlockProvider extends
PartitioningFoldingBlockProvider {

	public ShellCodeFoldingBlockProvider() {
		super(Activator.getDefault().getTextTools());
	}

	@Override
	public void computeFoldableBlocks(IFoldingContent content) {
		computeBlocksForPartitionType(content,
				IShellPartitions.FUNCTION_CONTENT_TYPE,
				FoldingBlockKind.FUNCTION, false);

	}

}
