/**
 * Copyright 2010 The Kuali Foundation Licensed under the
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

package org.kuali.student.krms.naturallanguage.translators;

import org.kuali.rice.krms.impl.repository.PropositionBo;
import org.kuali.student.krms.naturallanguage.Context;
import org.kuali.student.krms.naturallanguage.ContextRegistry;
import org.kuali.student.r1.core.statement.dto.ReqComponentInfo;
import org.kuali.student.r1.core.statement.entity.ReqComponentType;
import org.kuali.student.r1.core.statement.entity.ReqComponentTypeNLTemplate;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * This class translates requirement components into a specific
 * natural language. This class is not thread safe.
 */
public class PropositionTranslator {
    /** SLF4J logging framework */
	private final static Logger logger = LoggerFactory.getLogger(PropositionTranslator.class);

    private String language;
    private ContextRegistry<Context<PropositionBo>> contextRegistry;
    private TemplateTranslator templateTranslator = new TemplateTranslator();

    /**
	 * Constructs a new natural language translator in the
	 * default language locale.
     */
    public PropositionTranslator() {
		this.language = Locale.getDefault().getLanguage();
    }

	/**
	 * Sets requirement component translation language.
	 *
	 * @param language Language translation
	 */
    public void setLanguage(final String language) {
		this.language = language;
	}

    /**
     * Sets the template context registry.
     *
     * @param contextRegistry Template context registry
     */
    public void setContextRegistry(final ContextRegistry<Context<PropositionBo>> contextRegistry) {
    	this.contextRegistry = contextRegistry;
    }

    /**
     * Translates an <code>reqComponent</code> for a specific
     * <code>nlUsageTypeKey</code> into natural language.
     * This method is not thread safe.
     *
     * @param proposition
     *            Proposition to translate
     * @param nlUsageTypeKey
     *            Natural language usuage type key (context)
     * @return Natural language translation
     * @throws org.kuali.student.r2.common.exceptions.DoesNotExistException
     *             Natural language usuage type key does not exist
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     *             Translation failed
     */
    public String translate(final PropositionBo proposition, final String nlUsageTypeKey) throws DoesNotExistException, OperationFailedException {
    	return translate(proposition, nlUsageTypeKey, this.language);
    }

    /**
     * Translates an <code>reqComponent</code> for a specific
     * <code>nlUsageTypeKey</code> into natural language.
     * This method is not thread safe.
     *
     * @param proposition
     *            Proposition to translate
     * @param nlUsageTypeKey
     *            Natural language usuage type key (context)
     * @param language
     *            Translation language
     * @return
     * @throws org.kuali.student.r2.common.exceptions.DoesNotExistException
     *             Natural language usuage type key does not exist
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     *             Translation failed
     */
    public String translate(final PropositionBo proposition, final String nlUsageTypeKey, final String language) throws DoesNotExistException, OperationFailedException {
    	if(proposition == null) {
    		throw new DoesNotExistException("Proposition cannot be null");
    	}
        //KSENROLL-5044
//    	String propositionTypeId = proposition.getTypeId();
//    	ReqComponentInfo reqComponentInfo = StatementAssembler.toReqComponentInfo(reqComponent);
//    	Map<String, Object> contextMap = buildContextMap(reqComponentInfo);
//        ReqComponentTypeNLTemplate template = getTemplate(reqComponentType, nlUsageTypeKey, language);
//
//        try {
//			String nl = this.templateTranslator.translate(contextMap, template.getTemplate());
//
//			if(logger.isInfoEnabled()) {
//    			logger.info("nl="+nl);
//    		}
//
//			return nl;
//		} catch (OperationFailedException e) {
//			String msg = "Generating template for requirement component failed: "+reqComponent;
//			logger.error(msg, e);
//			throw new OperationFailedException(msg);
//		}
        return null; //KSENROLL-5044
    }

    /**
     * Builds a requirement component type context map.
     *
     * @param reqComponent Requirement component
     * @throws org.kuali.student.r2.common.exceptions.DoesNotExistException Requirement component context not found in registry
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException Creating context map failed
     */
    private Map<String, Object> buildContextMap(ReqComponentInfo reqComponent) throws DoesNotExistException, OperationFailedException {
        List<Context<PropositionBo>> contextList = this.contextRegistry.get(reqComponent.getType());
        if(contextList == null || contextList.isEmpty()) {
        	throw new DoesNotExistException("Requirement component context not found in registry for requirement component type id: " + reqComponent.getType());
        }
        Map<String, Object> contextMap = new HashMap<String, Object>();
        //KSENROLL-5044
//        for(Context<PropositionBo> context : contextList) {
//    		Map<String, Object> cm = context.createContextMap(reqComponent, new org.kuali.student.r2.common.dto.ContextInfo());  // KSCM contextInfo is not passed through here
//    		contextMap.putAll(cm);
//    	}

        if(logger.isInfoEnabled()) {
			logger.info("contextMap="+contextMap);
		}

        return contextMap;
    }

    /**
     * Gets the requirement component type template for the <code>nlUsageTypeKey</code>.
     *
     * @param reqComponentType
     *            Requirement component type
     * @param nlUsageTypeKey
     *            Natural language usuage type key (context)
     * @return Requirement component type template
     * @throws org.kuali.student.r2.common.exceptions.DoesNotExistException Template does not exist
     */
    private ReqComponentTypeNLTemplate getTemplate(ReqComponentType reqComponentType, String nlUsageTypeKey, String language) throws DoesNotExistException {
    	List<ReqComponentTypeNLTemplate> templateList = reqComponentType.getNlUsageTemplates();
        for (ReqComponentTypeNLTemplate template : templateList) {
            if (nlUsageTypeKey.equals(template.getNlUsageTypeKey()) && language.equals(template.getLanguage())) {

            	if(logger.isInfoEnabled()) {
        			logger.info("template="+template);
        		}

                return template;
            }
        }
        throw new DoesNotExistException("Natural language usage type key '" + nlUsageTypeKey + "'" +
        		" and language code '" + language + "' for requirement component type " + reqComponentType.getId() + " template not found");
    }
}
