package org.kuali.student.lum.lu.naturallanguage.translators;

import java.util.List;
import java.util.Locale;
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This class translates requirement components into a specific 
 * natural language.
 */
public class ReqComponentTranslator {
    /** SLF4J logging framework */
    final static Logger logger = LoggerFactory.getLogger(DroolsJackrabbitRepository.class);
	private String language;
    private LuDao luDao;
    private ContextRegistry<Context<ReqComponent>> contextRegistry;
    private VelocityTemplateEngine templateEngine = new VelocityTemplateEngine();

    /**
	 * Constructs a new natural language translator in the 
	 * default language locale.
     */
    public ReqComponentTranslator() {
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
     * Sets the learning unit data access object.
     * 
     * @param luDao LU DAO
     */
    public void setLuDao(final LuDao luDao) {
        this.luDao = luDao;
    }

    /**
     * Sets the template context registry.
     * 
     * @param contextRegistry Template context registry
     */
    public void setContextRegistry(final ContextRegistry<Context<ReqComponent>> contextRegistry) {
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
    public String translate(final String reqComponentId, final String nlUsageTypeKey) throws DoesNotExistException, OperationFailedException {
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
    public String translate(final ReqComponent reqComponent, final String nlUsageTypeKey) throws DoesNotExistException, OperationFailedException {
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
        String reqComponentTypeKey = reqComponent.getRequiredComponentType().getId();

        if(!this.contextRegistry.containsKey(reqComponentTypeKey)) {
        	throw new DoesNotExistException("Context does not exist in registry for requirement component type key: " + reqComponentTypeKey);
        }
        
        Context<ReqComponent> context = this.contextRegistry.get(reqComponentTypeKey);
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
            if (nlUsageTypeKey.equals(template.getNlUsageTypeKey()) && this.language.equals(template.getLanguage())) {
                return template;
            }
        }
        throw new DoesNotExistException("Natural language usage type key '" + nlUsageTypeKey + "'" +
        		" and language code '" + this.language + "' for requirement component type template not found");
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
        String naturalLanguage;
        try {
            naturalLanguage = this.templateEngine.evaluate(contextMap, template);
        } catch (VelocityException e) {
			logger.error("template: "+template);
			logger.error("contextMap: "+contextMap);
			logger.error(e.getMessage(), e);
            throw new OperationFailedException("Generating template failed: " + e.getMessage(), e);
        }
        return naturalLanguage.trim();
    }
}
