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
package net.sourceforge.shelled.ui.text;

import org.eclipse.jface.text.IDocument;

public interface IShellPartitions {
	public final static String SHELL_PARTITIONING = "__shell_partitioning";

	// Content types supplied by the shell script partitioner
	public static final String COMMENT_CONTENT_TYPE = "__comment";
	public static final String DOUBLE_QUOTE_CONTENT_TYPE = "__double_quote";
	public static final String PARAM_CONTENT_TYPE = "__parameter";
	public static final String EVAL_CONTENT_TYPE = "__eval";
	public static final String HASHBANG_CONTENT_TYPE = "__hashbang";
	public static final String FUNCTION_CONTENT_TYPE = "__function";
	public static final String SINGLE_QUOTE_CONTENT_TYPE = "__single_quote";

	public static final String[] CONTENT_TYPES = new String[] {
			IDocument.DEFAULT_CONTENT_TYPE, HASHBANG_CONTENT_TYPE,
			COMMENT_CONTENT_TYPE, SINGLE_QUOTE_CONTENT_TYPE,
			DOUBLE_QUOTE_CONTENT_TYPE, PARAM_CONTENT_TYPE, EVAL_CONTENT_TYPE,
			FUNCTION_CONTENT_TYPE };
}