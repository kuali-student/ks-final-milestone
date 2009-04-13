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

	public String translate(String reqComponentId, String nlUsageTypeKey) throws DoesNotExistException, OperationFailedException {
		ReqComponent reqComponent = this.luDao.fetch(ReqComponent.class, reqComponentId);
		return translate(reqComponent,nlUsageTypeKey);
	}
	
	public String translate(ReqComponent reqComponent, String nlUsageTypeKey) throws DoesNotExistException, OperationFailedException {
		ReqComponentType reqComponentType = reqComponent.getRequiredComponentType();

		Map<String, Object> velocityContextMap = new HashMap<String, Object>();
        buildVelocityContextMap(velocityContextMap, reqComponent);

        ReqComponentTypeNLTemplate template = getTemplate(reqComponentType, nlUsageTypeKey); 

        return translate(velocityContextMap, template.getTemplate());
	}

	private void buildVelocityContextMap(Map<String, Object> velocityContextMap, ReqComponent reqComponent) throws DoesNotExistException {
		String reqComponentType = reqComponent.getRequiredComponentType().getId();
		ReqCompTypes.CustomReqComponentType type = ReqCompTypes.CustomReqComponentType.valueOfKey(reqComponentType);

		switch(type) {
			case COURSE_LIST_ALL:
			    doCount(reqComponent, velocityContextMap);
				break;
            case COURSE_LIST_NOF:
                doCount(reqComponent, velocityContextMap);
                break;				
			case GRADE_CONDITION_COURSE_LIST:
				break;
			case GRADE_CHECK:
				break;
			default:
				break;
		}
	}
	
	private CustomCluSet getCluSet(String countType, String countTarget) throws DoesNotExistException {
		CluSet cluSet = this.luDao.fetch(CluSet.class, countTarget);
		return new CustomCluSet(cluSet);
	}

	private void doCount(ReqComponent reqComponent, Map<String, Object> velocityContextMap) throws DoesNotExistException {
		List<ReqComponentField> fields = reqComponent.getReqCompField();
		Map<String, String> map = new HashMap<String, String>();
		for(ReqComponentField field : fields) {
			String key = field.getKey();
			String value = field.getValue();
			map.put(key, value);
		}

		velocityContextMap.put(
				ReqCompTypes.VelocityToken.EXPECTED_VALUE_KEY.getKey(), 
				map.get(ReqCompTypes.CountDefinitionType.EXPECTED_VALUE_KEY.getKey()));
		velocityContextMap.put(
				ReqCompTypes.VelocityToken.OPERATOR_KEY.getKey(),
				map.get(ReqCompTypes.CountDefinitionType.OPERATOR_KEY.getKey()));
		
		String countType =  map.get(ReqCompTypes.CountDefinitionType.COUNT_TYPE_KEY.getKey());
		String countTarget = map.get(ReqCompTypes.CountDefinitionType.COUNT_TARTGET_KEY.getKey());

		CustomCluSet cluSet = getCluSet(countType, countTarget);
		velocityContextMap.put(ReqCompTypes.VelocityToken.CLU_SET_KEY.getKey(), cluSet);
	}
	
	private ReqComponentTypeNLTemplate getTemplate(ReqComponentType reqComponentType, String nlUsageTypeKey) {
		List<ReqComponentTypeNLTemplate> templateList = reqComponentType.getNlUsageTemplates();
		for(ReqComponentTypeNLTemplate temp : templateList) {
			if(nlUsageTypeKey.equals(temp.getNlUsageTypeKey())) {
				return temp;
			}
		}
		return null;
	}

	private String translate(Map<String, Object> contextMap, String template) throws OperationFailedException {
        VelocityTemplateEngine templateEngine = new VelocityTemplateEngine();
        String naturalLanguage;
		try {
			naturalLanguage = templateEngine.evaluate(contextMap, template);
		} catch (VelocityException e) {
			throw new OperationFailedException("Generating template failed: "+e.getMessage(), e);
		}
        return naturalLanguage.trim();
	}

}
