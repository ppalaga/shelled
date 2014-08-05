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
 * Shell Command Language reserved words. Note that all dialects are covered in
 * this enum. To loop through e.g. POSIX-only reserved words, you can filter
 * using {@link #isValidIn(Dialect)}.
 * <p>
 * References:
 * <ul>
 * <li><a href=
 * "http://pubs.opengroup.org/onlinepubs/9699919799/utilities/V3_chap02.html#tag_18_04"
 * >Reserved Words</a> of POSIX.1-2008
 * <li>Bash's <a
 * href="http://www.gnu.org/software/bash/manual/html_node/Bash-Builtins.html"
 * >Builtin Commands</a>
 * </ul>
 *
 * @author <a href="mailto:ppalaga@redhat.com">Peter Palaga</a>
 */
public enum ReservedWord {
	/** POSIX {@code !} */
	BANG("" + LexicalConstants.BANG),
	/** POSIX <code>{</code> */
	LBRACE(LexicalConstants.LBRACE_STRING),
	/** POSIX <code>}</code> */
	RBRACE(LexicalConstants.LBRACE_STRING),
	/** POSIX {@code case} */
	CASE,
	/** POSIX {@code case} */
	DO,
	/** POSIX {@code do} */
	DONE,
	/** POSIX {@code done} */
	ELIF,
	/** POSIX {@code elif} */
	ELSE,
	/** POSIX {@code else} */
	ESAC,
	/** POSIX {@code esac} */
	FI,
	/** POSIX {@code fi} */
	FOR,
	/** POSIX {@code for} */
	IF,
	/** POSIX {@code if} */
	IN,
	/** POSIX {@code in} */
	THEN,
	/** POSIX {@code until} */
	UNTIL,
	/** POSIX {@code while} */
	WHILE,

	/** Bash and Bourne Shell builtin {@code .} */
	COLON("" + LexicalConstants.COLON, Dialect.BASH.id
			| Dialect.BOURNE_SHELL.id),

	/** Bash and Bourne Shell builtin {@code :} */
	PERIOD("" + LexicalConstants.PERIOD, Dialect.BASH.id
			| Dialect.BOURNE_SHELL.id),

	/** Bash and Bourne Shell builtin {@code break} */
	BREAK(Dialect.BASH.id | Dialect.BOURNE_SHELL.id),

	/** Bash and Bourne Shell builtin {@code continue} */
	CD(Dialect.BASH.id | Dialect.BOURNE_SHELL.id),

	/** Bash and Bourne Shell builtin {@code continue} */
	CONTINUE(Dialect.BASH.id | Dialect.BOURNE_SHELL.id),

	/** Bash and Bourne Shell builtin {@code eval} */
	EVAL(Dialect.BASH.id | Dialect.BOURNE_SHELL.id),

	/** Bash and Bourne Shell builtin {@code exec} */
	EXEC(Dialect.BASH.id | Dialect.BOURNE_SHELL.id),

	/** Bash and Bourne Shell builtin {@code exit} */
	EXIT(Dialect.BASH.id | Dialect.BOURNE_SHELL.id),

	/** Bash and Bourne Shell builtin {@code export} */
	EXPORT(Dialect.BASH.id | Dialect.BOURNE_SHELL.id),

	/** Bash and Bourne Shell builtin {@code getopts} */
	GETOPTS(Dialect.BASH.id | Dialect.BOURNE_SHELL.id),

	/** Bash and Bourne Shell builtin {@code hash} */
	HASH(Dialect.BASH.id | Dialect.BOURNE_SHELL.id),

	/** Bash and Bourne Shell builtin {@code pwd} */
	PWD(Dialect.BASH.id | Dialect.BOURNE_SHELL.id),

	/** Bash and Bourne Shell builtin {@code readonly} */
	READONLY(Dialect.BASH.id | Dialect.BOURNE_SHELL.id),

	/** Bash and Bourne Shell builtin {@code return} */
	RETURN(Dialect.BASH.id | Dialect.BOURNE_SHELL.id),

	/** Bash and Bourne Shell builtin {@code shift} */
	SHIFT(Dialect.BASH.id | Dialect.BOURNE_SHELL.id),

	/** Bash and Bourne Shell builtin {@code test} */
	TEST(Dialect.BASH.id | Dialect.BOURNE_SHELL.id),

	/** Bash and Bourne Shell builtin {@code [} */
	LSQUARE("" + LexicalConstants.LSQUARE, Dialect.BASH.id
			| Dialect.BOURNE_SHELL.id),
	/** Bash and Bourne Shell builtin {@code [} */
	RSQUARE("" + LexicalConstants.RSQUARE, Dialect.BASH.id
			| Dialect.BOURNE_SHELL.id),

	/** Bash and Bourne Shell builtin {@code times} */
	TIMES(Dialect.BASH.id | Dialect.BOURNE_SHELL.id),

	/** Bash and Bourne Shell builtin {@code trap} */
	TRAP(Dialect.BASH.id | Dialect.BOURNE_SHELL.id),

	/** Bash and Bourne Shell builtin {@code umask} */
	UMASK(Dialect.BASH.id | Dialect.BOURNE_SHELL.id),

	/** Bash and Bourne Shell builtin {@code unset} */
	UNSET(Dialect.BASH.id | Dialect.BOURNE_SHELL.id),

	/** Bash builtin {@code alias} */
	ALIAS(Dialect.BASH.id),
	/** Bash builtin {@code bind} */
	BIND(Dialect.BASH.id),
	/** Bash builtin {@code builtin} */
	BUILTIN(Dialect.BASH.id),
	/** Bash builtin {@code caller} */
	CALLER(Dialect.BASH.id),
	/** Bash builtin {@code command} */
	COMMAND(Dialect.BASH.id),
	/** Bash builtin {@code declare} */
	DECLARE(Dialect.BASH.id),
	/** Bash builtin {@code echo} */
	ECHO(Dialect.BASH.id),
	/** Bash builtin {@code enable} */
	ENABLE(Dialect.BASH.id),
	/** Bash builtin {@code help} */
	HELP(Dialect.BASH.id),
	/** Bash builtin {@code let} */
	LET(Dialect.BASH.id),
	/** Bash builtin {@code local} */
	LOCAL(Dialect.BASH.id),
	/** Bash builtin {@code logout} */
	LOGOUT(Dialect.BASH.id),
	/** Bash builtin {@code mapfile} */
	MAPFILE(Dialect.BASH.id),
	/** Bash builtin {@code printf} */
	PRINTF(Dialect.BASH.id),
	/** Bash builtin {@code read} */
	READ(Dialect.BASH.id),
	/** Bash builtin {@code readarray} */
	READARRAY(Dialect.BASH.id),
	/** Bash builtin {@code source} */
	SOURCE(Dialect.BASH.id),
	/** Bash reserved word {@code time} */
	TIME(Dialect.BASH.id),
	/** Bash builtin {@code type} */
	TYPE(Dialect.BASH.id),
	/** Bash builtin {@code typeset} */
	TYPESET(Dialect.BASH.id),
	/** Bash builtin {@code ulimit} */
	ULIMIT(Dialect.BASH.id),
	/** Bash builtin {@code unalias} */
	UNALIAS,

	/** Bash builtin {@code set} */
	SET(Dialect.BASH.id),

	/** Bash builtin {@code shopt} */
	SHOPT(Dialect.BASH.id),

	/** Bash only {@code [[} */
	DLSQUARE("" + LexicalConstants.LSQUARE + LexicalConstants.LSQUARE,
			Dialect.BASH.id),

	/** Bash only {@code ]]} */
	DRSQUARE("" + LexicalConstants.RSQUARE + LexicalConstants.RSQUARE,
			Dialect.BASH.id),

	/** Bash only {@code function} */
	FUNCTION(Dialect.BASH.id),

	/** Bash only {@code select} */
	SELECT(Dialect.BASH.id), ;

	private final String token;
	private final int validInDialects;

	private ReservedWord(String token, int validInDialects) {
		this.token = token;
		this.validInDialects = validInDialects;
	}

	private ReservedWord(String token) {
		this(token, Dialect.ANY.id);
	}

	private ReservedWord(int validInDialects) {
		this.token = name().toLowerCase(Locale.ENGLISH);
		this.validInDialects = validInDialects;
	}

	private ReservedWord() {
		this.token = name().toLowerCase(Locale.ENGLISH);
		this.validInDialects = Dialect.ANY.id;
	}

	@Override
	public String toString() {
		return token;
	}

	public String token() {
		return token;
	}

	public boolean isValidIn(Dialect dialect) {
		return (this.validInDialects & dialect.id) == dialect.id;
	}
}
