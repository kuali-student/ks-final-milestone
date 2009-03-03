package org.kuali.student.rules.ruleexecution.runtime.report.ast;

import org.kuali.student.rules.internal.common.runtime.ast.BooleanMessageContainer;
import org.kuali.student.rules.ruleexecution.runtime.SimpleExecutor;

public class BooleanMessageBuilder extends AbstractMessageBuilder {

	public BooleanMessageBuilder(SimpleExecutor executor) {
		super(executor);
	}

    /**
     * Builds message from a boolean expression. 
     * Example: (M1 AND M2) OR M3 - (M1*M2)+M3
     * 
     * @param booleanRule Boolean expression
     * @param messageContainer Contains a list of messages
     * @return A message
     */
    public String buildMessage(String booleanRule, BooleanMessageContainer messageContainer) {
        return super.buildMessage(booleanRule, messageContainer.getMessageMap());
    }
}
