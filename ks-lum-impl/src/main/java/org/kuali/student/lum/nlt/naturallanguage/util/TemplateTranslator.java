/*
 * Copyright 2009 The Kuali Foundation Licensed under the
 * Educational Community License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License. You may
 * obtain a copy of the License at
 * 
 * http://www.osedu.org/licenses/ECL-2.0
 * 
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an "AS IS"
 * BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing
 * permissions and limitations under the License.
 */
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
