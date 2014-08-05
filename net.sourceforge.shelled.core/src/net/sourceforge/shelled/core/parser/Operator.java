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

import java.util.Locale;

/**
 * Shell Command Language operators. See the chapter <a href=
 * "http://pubs.opengroup.org/onlinepubs/9699919799/utilities/V3_chap02.html#tag_18_10_02"
 * >Shell Grammar</a> of POSIX.1-2008.
 *
 * @author <a href="mailto:ppalaga@redhat.com">Peter Palaga</a>
 */
public enum Operator {
	AND_IF("&&"), OR_IF("||"), DSEMI(";;"), DLESS("<<"), DGREAT(">>"), LESSAND(
			"<&"), GREATAND(">&"), LESSGREAT("<>"), DLESSDASH("<<-"), CLOBBER(
			">|");

	private final String token;

	private Operator(String token) {
		this.token = token;
	}

	private Operator() {
		this.token = name().toLowerCase(Locale.ENGLISH);
	}

	@Override
	public String toString() {
		return token;
	}

}
