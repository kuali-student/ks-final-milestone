package org.kuali.student.lum.nlt.naturallanguage.util;

import java.util.Map;

import org.apache.velocity.exception.VelocityException;
import org.kuali.student.common.util.VelocityTemplateEngine;
import org.kuali.student.core.exceptions.OperationFailedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TemplateTranslator {
    /** SLF4J logging framework */
    final static Logger logger = LoggerFactory.getLogger(TemplateTranslator.class);

    private VelocityTemplateEngine templateEngine = new VelocityTemplateEngine();

    /**
     * Translates the template and context map (data) into natural language.
     * 
     * @param contextMap Context map (template data)
     * @param template Velocity template
     * @return Natural language translation
     * @throws OperationFailedException If translation fails
     */
	public String translate(Map<String, Object> contextMap, String template) throws OperationFailedException {
        String naturalLanguage = null;
        try {
            naturalLanguage = this.templateEngine.evaluate(contextMap, template);
        } catch (VelocityException e) {
			logger.error("template: "+template);
			logger.error("contextMap: "+contextMap);
			logger.error(e.getMessage(), e);
            throw new OperationFailedException("Generating template failed: " + e.getMessage(), e);
        }
        return naturalLanguage;
    }

}
