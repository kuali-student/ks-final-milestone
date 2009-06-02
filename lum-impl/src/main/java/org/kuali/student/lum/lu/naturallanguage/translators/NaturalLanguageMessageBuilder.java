package org.kuali.student.lum.lu.naturallanguage.translators;

import java.util.Locale;
import java.util.Map;

import org.kuali.student.brms.internal.common.runtime.MessageContainer;
import org.kuali.student.brms.ruleexecution.runtime.SimpleExecutor;
import org.kuali.student.brms.ruleexecution.runtime.report.MessageBuilder;
import org.kuali.student.brms.ruleexecution.runtime.report.ast.BooleanOperators;
import org.kuali.student.brms.ruleexecution.runtime.report.ast.MessageBuilderImpl;

/**
 * This class builds a message from a boolean expression in a specific language.
 */
public class NaturalLanguageMessageBuilder {
	private SimpleExecutor executor;
    private MessageBuilder messageBuilder;
    private String language;
    private BooleanOperators booleanOperators;
    private Map<String, BooleanOperators> booleanOperatorsLanguageMap;

    /**
     * Constructs a new natural language message builder in the default
     * language locale with default 'and' and 'or' boolean operators.
     * 
     * @param executor Rule engine executor
     */
	public NaturalLanguageMessageBuilder(final SimpleExecutor executor) {
		this.language = Locale.getDefault().getLanguage();
		this.executor = executor;
		this.booleanOperators = new BooleanOperators("and", "or");
		createMessageBuilder();
	}

	/**
     * Constructs a new natural language message builder in a specific language.
	 * 
	 * @param executor Rule engine executor
	 * @param language Translation language
	 * @param booleanOperators Boolean operators
	 */
	public NaturalLanguageMessageBuilder(final SimpleExecutor executor, final String language, final BooleanOperators booleanOperators) {
		this.language = language;
		this.executor = executor;
		this.booleanOperators = booleanOperators;
		createMessageBuilder();
	}

	/**
     * Constructs a new natural language message builder in a specific language
     * with a boolean operator language map.
	 * 
	 * @param executor Rule engine executor
	 * @param language Translation language
	 * @param booleanOperatorsLanguageMap boolean operator language map
	 */
	public NaturalLanguageMessageBuilder(final SimpleExecutor executor, final String language, final Map<String, BooleanOperators> booleanOperatorsLanguageMap) {
		this.executor = executor;
		this.language = language;
		this.booleanOperatorsLanguageMap = booleanOperatorsLanguageMap;
		createMessageBuilder();
	}

	/**
	 * Gets the translation language.
	 * 
	 * @return Boolean operator translation language
	 */
	public String getLanguage() {
		return language;
	}

	/**
	 * Sets the translation language.
	 * 
	 * @param language Boolean operator translation language
	 */
	public void setLanguage(final String language) {
		this.language = language;
		createMessageBuilder();
	}

	/**
	 * Builds the message from the <code>booleanExpression</code>.
	 * 
	 * @param booleanExpression Boolean expression
	 * @param messageContainer List of messages
	 * @return Composed message from the boolean expression
	 */
	public String buildMessage(final String booleanExpression, final MessageContainer messageContainer) {
		return this.messageBuilder.buildMessage(booleanExpression, messageContainer);
	}

	/**
	 * Creates the message builder.
	 */
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
