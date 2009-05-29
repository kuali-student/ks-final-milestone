package org.kuali.student.lum.lu.naturallanguage.translators;

import java.util.Locale;
import java.util.Map;

import org.kuali.student.brms.internal.common.runtime.MessageContainer;
import org.kuali.student.brms.ruleexecution.runtime.SimpleExecutor;
import org.kuali.student.brms.ruleexecution.runtime.report.MessageBuilder;
import org.kuali.student.brms.ruleexecution.runtime.report.ast.BooleanOperators;
import org.kuali.student.brms.ruleexecution.runtime.report.ast.MessageBuilderImpl;

public class NaturalLanguageMessageBuilder {
	private SimpleExecutor executor;
    private MessageBuilder messageBuilder;
    private String language;
    private BooleanOperators booleanOperators;
    private Map<String, BooleanOperators> booleanOperatorsLanguageMap;

	public NaturalLanguageMessageBuilder(SimpleExecutor executor) {
		this.language = Locale.getDefault().getLanguage();
		this.executor = executor;
		this.booleanOperators = new BooleanOperators("and", "or");
		createMessageBuilder();
	}

	public NaturalLanguageMessageBuilder(SimpleExecutor executor, String language, BooleanOperators booleanOperators) {
		this.language = language;
		this.executor = executor;
		this.booleanOperators = booleanOperators;
		createMessageBuilder();
	}

	public NaturalLanguageMessageBuilder(SimpleExecutor executor, String language, Map<String, BooleanOperators> booleanOperatorsLanguageMap) {
		this.executor = executor;
		this.language = language;
		this.booleanOperatorsLanguageMap = booleanOperatorsLanguageMap;
		createMessageBuilder();
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
		createMessageBuilder();
	}

	public String buildMessage(String booleanExpression, MessageContainer messageContainer) {
		return this.messageBuilder.buildMessage(booleanExpression, messageContainer);
	}

	private void createMessageBuilder() {
		if(this.booleanOperators != null) {
			this.messageBuilder = new MessageBuilderImpl(this.executor, this.language, this.booleanOperators);
		} else {
			if(this.booleanOperatorsLanguageMap == null) {
				throw new RuntimeException("Boolean operators language map not found. Language key: " + this.language);
			}
			BooleanOperators booleanOperators = this.booleanOperatorsLanguageMap.get(this.language);
			if(booleanOperators == null) {
				throw new RuntimeException("Boolean operators not found for language key: " + this.language);
			}
			this.messageBuilder = new MessageBuilderImpl(this.executor, this.language, booleanOperators);
		}
	}
}
