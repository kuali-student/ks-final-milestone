package org.kuali.student.lum.lu.naturallanguage;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.velocity.exception.VelocityException;
import org.kuali.student.common.util.VelocityTemplateEngine;
import org.kuali.student.core.exceptions.DoesNotExistException;
import org.kuali.student.core.exceptions.OperationFailedException;
import org.kuali.student.lum.lu.dao.LuDao;
import org.kuali.student.lum.lu.entity.CluSet;
import org.kuali.student.lum.lu.entity.ReqComponent;
import org.kuali.student.lum.lu.entity.ReqComponentField;
import org.kuali.student.lum.lu.entity.ReqComponentType;
import org.kuali.student.lum.lu.entity.ReqComponentTypeNLTemplate;

public class ReqComponentTranslator extends AbstractTranslator<ReqComponent> {
    private LuDao luDao;

    public void setLuDao(LuDao luDao) {
        this.luDao = luDao;
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

        Map<String, Object> velocityContextMap = new HashMap<String, Object>();
        buildVelocityContextMap(velocityContextMap, reqComponent);

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
    private void buildVelocityContextMap(Map<String, Object> velocityContextMap, ReqComponent reqComponent) throws DoesNotExistException {
        String reqComponentType = reqComponent.getRequiredComponentType().getId();
        ReqCompTypes.CustomReqComponentType type = ReqCompTypes.CustomReqComponentType.valueOfKey(reqComponentType);
        
        switch (type) {
            case COURSE_LIST_ALL:
                createContextMap(reqComponent, velocityContextMap);
                break;
            case COURSE_LIST_NOF:
                createContextMap(reqComponent, velocityContextMap);
                break;
            case COURSE_LIST_1OF2:
                createContextMap(reqComponent, velocityContextMap);
                break;
            case GRADE_CONDITION_COURSE_LIST:
                break;
            case GRADE_CHECK:
                createGPAContextMap(reqComponent, velocityContextMap);
                break;
            default:
                break;
        }
    }

    /**
     * Gets the CLU set.
     * 
     * @param cluSetId
     *            CLU set id
     * @return CLU set
     * @throws DoesNotExistException
     */
    private CustomCluSet getCluSet(String cluSetId) throws DoesNotExistException {
        CluSet cluSet = this.luDao.fetch(CluSet.class, cluSetId);
        return new CustomCluSet(cluSet);
    }

    /**
     * Creates the Velocity context map (template data) for the requirement component.
     * 
     * @param reqComponent
     *            Requirement component
     * @param velocityContextMap
     *            Context map
     * @throws DoesNotExistException
     */
    private void createContextMap(ReqComponent reqComponent, Map<String, Object> velocityContextMap) throws DoesNotExistException {
        List<ReqComponentField> fields = reqComponent.getReqCompField();
        Map<String, String> map = new HashMap<String, String>();
        for (ReqComponentField field : fields) {
            String key = field.getKey();
            String value = field.getValue();
            map.put(key, value);
        }

        velocityContextMap.put(ReqCompTypes.VelocityToken.EXPECTED_VALUE_KEY.getKey(), map.get(ReqCompTypes.ReqCompFiledDefinitions.REQUIRED_COUNT_KEY.getKey()));
        velocityContextMap.put(ReqCompTypes.VelocityToken.OPERATOR_KEY.getKey(), map.get(ReqCompTypes.ReqCompFiledDefinitions.OPERATOR_KEY.getKey()));

        String cluSetId = map.get(ReqCompTypes.ReqCompFiledDefinitions.COUNT_CLUSET.getKey());

        CustomCluSet cluSet = getCluSet(cluSetId);
        velocityContextMap.put(ReqCompTypes.VelocityToken.CLU_SET_KEY.getKey(), cluSet);
    }

    /**
     * This method creates the velocity  
     * 
     * @param reqComponent
     * @param velocityContextMap
     * @throws DoesNotExistException
     */
    private void createGPAContextMap(ReqComponent reqComponent, Map<String, Object> velocityContextMap) throws DoesNotExistException {
        List<ReqComponentField> fields = reqComponent.getReqCompField();
        Map<String, String> map = new HashMap<String, String>();
        for (ReqComponentField field : fields) {
            String key = field.getKey();
            String value = field.getValue();
            map.put(key, value);
        }
        
        velocityContextMap.put(ReqCompTypes.VelocityToken.GPA.getKey(), map.get(ReqCompTypes.ReqCompFiledDefinitions.GPA_KEY.getKey()));
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
        for (ReqComponentTypeNLTemplate temp : templateList) {
            if (nlUsageTypeKey.equals(temp.getNlUsageTypeKey())) {
                return temp;
            }
        }
        throw new DoesNotExistException("Natural language usage type key '" + nlUsageTypeKey + "' not found");
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
            throw new OperationFailedException("Generating template failed: " + e.getMessage(), e);
        }
        return naturalLanguage.trim();
    }

}
