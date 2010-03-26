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
		rules.add(new EndOfLineRule("#!", new Token(
				IShellPartitions.HASHBANG_CONTENT_TYPE)));
		/*
		 * There are 2 comment rules in order to correctly support # in
		 * variables.
		 */
		// This rule recognizes only comments starting from the beginning of
		// line.
		EndOfLineRule commentRule = new EndOfLineRule("#", new Token(
				IShellPartitions.COMMENT_CONTENT_TYPE));
		commentRule.setColumnConstraint(0);
		rules.add(commentRule);
		// This rule recognizes only comments which has space in front of it
		commentRule = new EndOfLineRule(" #", new Token(
				IShellPartitions.COMMENT_CONTENT_TYPE));
		rules.add(commentRule);
		commentRule = new EndOfLineRule("\t#", new Token(
				IShellPartitions.COMMENT_CONTENT_TYPE));
		rules.add(commentRule);

		rules.add(new DollarBraceCountingRule('(', ')', new Token(
				IShellPartitions.EVAL_CONTENT_TYPE), '\\'));
		rules.add(new DollarBraceCountingRule('{', '}', new Token(
				IShellPartitions.PARAM_CONTENT_TYPE), '\\'));
		rules.add(new SingleLineRule("`", "`", new Token(
				IShellPartitions.EVAL_CONTENT_TYPE), '\\', false, true));
		rules
				.add(new SingleLineRule("\"", "\"", new Token(
						IShellPartitions.DOUBLE_QUOTE_CONTENT_TYPE), '\\',
						false, true));
		rules
				.add(new SingleLineRule("'", "'", new Token(
						IShellPartitions.SINGLE_QUOTE_CONTENT_TYPE), '\\',
						false, true));

		IPredicateRule[] result = new IPredicateRule[rules.size()];
		rules.toArray(result);
		setPredicateRules(result);
	}

}
