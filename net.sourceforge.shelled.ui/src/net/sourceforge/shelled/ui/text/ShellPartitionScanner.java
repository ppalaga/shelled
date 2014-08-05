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

import java.util.ArrayList;
import java.util.List;

import net.sourceforge.shelled.core.parser.LexicalConstants;

import org.eclipse.jface.text.rules.EndOfLineRule;
import org.eclipse.jface.text.rules.IPredicateRule;
import org.eclipse.jface.text.rules.IRule;
import org.eclipse.jface.text.rules.RuleBasedPartitionScanner;
import org.eclipse.jface.text.rules.SingleLineRule;
import org.eclipse.jface.text.rules.Token;

public class ShellPartitionScanner extends RuleBasedPartitionScanner {

	public ShellPartitionScanner() {
		super();

		List<IRule> rules = new ArrayList<IRule>();
		// Add rule for single line comments.
		rules.add(new EndOfLineRule(LexicalConstants.HASH_BANG, new Token(
				IShellPartitions.HASHBANG_CONTENT_TYPE)));
		/*
		 * There are 2 comment rules in order to correctly support # in
		 * variables.
		 */
		// This rule recognizes only comments starting from the beginning of
		// line.
		EndOfLineRule commentRule = new EndOfLineRule(
				LexicalConstants.HASH_STRING, new Token(
						IShellPartitions.COMMENT_CONTENT_TYPE));
		commentRule.setColumnConstraint(0);
		rules.add(commentRule);
		// This rule recognizes only comments which has space in front of it
		commentRule = new EndOfLineRule("" + LexicalConstants.SPACE
				+ LexicalConstants.HASH, new Token(
						IShellPartitions.COMMENT_CONTENT_TYPE));
		rules.add(commentRule);
		commentRule = new EndOfLineRule("" + LexicalConstants.TAB
				+ LexicalConstants.HASH, new Token(
						IShellPartitions.COMMENT_CONTENT_TYPE));
		rules.add(commentRule);

		rules.add(new DollarBraceCountingRule(LexicalConstants.LPAREN,
				LexicalConstants.RPAREN, new Token(
						IShellPartitions.EVAL_CONTENT_TYPE),
				LexicalConstants.BACKSLASH));
		rules.add(new DollarBraceCountingRule(LexicalConstants.LBRACE,
				LexicalConstants.RBRACE, new Token(
						IShellPartitions.PARAM_CONTENT_TYPE),
				LexicalConstants.BACKSLASH));
		rules.add(new SingleLineRule(LexicalConstants.GRAVE_STRING,
				LexicalConstants.GRAVE_STRING, new Token(
						IShellPartitions.EVAL_CONTENT_TYPE),
				LexicalConstants.BACKSLASH, false, true));
		rules.add(new SingleLineRule(LexicalConstants.QUOT_STRING,
				LexicalConstants.QUOT_STRING, new Token(
						IShellPartitions.DOUBLE_QUOTE_CONTENT_TYPE),
				LexicalConstants.BACKSLASH, false, true));
		rules.add(new SingleLineRule(LexicalConstants.APOS_STRING,
				LexicalConstants.APOS_STRING, new Token(
						IShellPartitions.SINGLE_QUOTE_CONTENT_TYPE),
				LexicalConstants.BACKSLASH, false, true));

		IPredicateRule[] result = new IPredicateRule[rules.size()];
		rules.toArray(result);
		setPredicateRules(result);
	}

}
