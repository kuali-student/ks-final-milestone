package org.kuali.student.lum.lu.naturallanguage.translators;

import java.util.List;
import java.util.Map;

import org.apache.velocity.exception.VelocityException;
import org.kuali.student.brms.repository.drools.DroolsJackrabbitRepository;
import org.kuali.student.common.util.VelocityTemplateEngine;
import org.kuali.student.core.exceptions.DoesNotExistException;
import org.kuali.student.core.exceptions.OperationFailedException;
import org.kuali.student.lum.lu.dao.LuDao;
import org.kuali.student.lum.lu.entity.ReqComponent;
import org.kuali.student.lum.lu.entity.ReqComponentType;
import org.kuali.student.lum.lu.entity.ReqComponentTypeNLTemplate;
import org.kuali.student.lum.lu.naturallanguage.ContextRegistry;
import org.kuali.student.lum.lu.naturallanguage.contexts.Context;
import org.kuali.student.lum.lu.naturallanguage.util.ReqComponentTypes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ReqComponentTranslator {
    /** SLF4J logging framework */
    final static Logger logger = LoggerFactory.getLogger(DroolsJackrabbitRepository.class);
    private LuDao luDao;
    private ContextRegistry contextRegistry;

    public void setLuDao(LuDao luDao) {
        this.luDao = luDao;
    }

    public void setContextRegistry(ContextRegistry contextRegistry) {
    	this.contextRegistry = contextRegistry;
    }

    /**
     * Translates a requirement component by <code>reqComponentId</code> for a specific <code>nlUsageTypeKey</code> into
     * natural language.
     * 
     * @param id
     *            Id of object type to translate
     * @param nlUsageTypeKey
     *            Natural language usuage type key (context)
     * @return Natural language translation
     * @throws DoesNotExistException
     *             Id or natural language usuage type key does not exist
     * @throws OperationFailedException
     *             Translation fails
     */
    public String translate(String reqComponentId, String nlUsageTypeKey) throws DoesNotExistException, OperationFailedException {
        ReqComponent reqComponent = this.luDao.fetch(ReqComponent.class, reqComponentId);
        return translate(reqComponent, nlUsageTypeKey);
    }

    /**
     * Translates an <code>reqComponent</code> for a specific <code>nlUsageTypeKey</code> into natural language.
     * 
     * @param reqComponent
     *            Requirement component to translate
     * @param nlUsageTypeKey
     *            Natural language usuage type key (context)
     * @return Natural language translation
     * @throws DoesNotExistException
     *             Natural language usuage type key does not exist
     * @throws OperationFailedException
     *             Translation fails
     */
    public String translate(ReqComponent reqComponent, String nlUsageTypeKey) throws DoesNotExistException, OperationFailedException {
        ReqComponentType reqComponentType = reqComponent.getRequiredComponentType();

        Map<String, Object> velocityContextMap = buildContextMap(reqComponent);

        ReqComponentTypeNLTemplate template = getTemplate(reqComponentType, nlUsageTypeKey);

        return translate(velocityContextMap, template.getTemplate());
    }

    /**
     * Builds Velocity context map.
     * 
     * @param velocityContextMap
     *            Velocity context map
     * @param reqComponent
     *            Requirement component
     * @throws DoesNotExistException
     */
    private Map<String, Object> buildContextMap(ReqComponent reqComponent) throws DoesNotExistException {
        String reqComponentType = reqComponent.getRequiredComponentType().getId();
        ReqComponentTypes.ReqCompTypes type = ReqComponentTypes.ReqCompTypes.valueOfKey(reqComponentType);
        
    	Context context = this.contextRegistry.get(type.getKey());
    	Map<String, Object> velocityContextMap = context.createContextMap(reqComponent);
    	
        return velocityContextMap;
    }

    /**
     * Gets the requirement component type template for the <code>nlUsageTypeKey</code>.
     * 
     * @param reqComponentType
     *            Requirement component type
     * @param nlUsageTypeKey
     *            Natural language usuage type key (context)
     * @return Requirement component type template
     * @throws DoesNotExistException
     */
    private ReqComponentTypeNLTemplate getTemplate(ReqComponentType reqComponentType, String nlUsageTypeKey) throws DoesNotExistException {
        List<ReqComponentTypeNLTemplate> templateList = reqComponentType.getNlUsageTemplates();
        for (ReqComponentTypeNLTemplate template : templateList) {
            if (nlUsageTypeKey.equals(template.getNlUsageTypeKey())) {
                return template;
            }
        }
        throw new DoesNotExistException("Natural language usage type key '" + nlUsageTypeKey + "' for requirement component type template not found");
    }

    /**
     * Translates the template with the context map (data) into natural language.
     * 
     * @param contextMap
     *            Velocity context map (template data)
     * @param template
     *            Velocity template
     * @return Natural language translation
     * @throws OperationFailedException
     */
    private String translate(Map<String, Object> contextMap, String template) throws OperationFailedException {
        VelocityTemplateEngine templateEngine = new VelocityTemplateEngine();
        String naturalLanguage;
        try {
            naturalLanguage = templateEngine.evaluate(contextMap, template);
        } catch (VelocityException e) {
			logger.error("template: "+template);
			logger.error("contextMap: "+contextMap);
			logger.error(e.getMessage(), e);
            throw new OperationFailedException("Generating template failed: " + e.getMessage(), e);
        }
        return naturalLanguage.trim();
    }
}
