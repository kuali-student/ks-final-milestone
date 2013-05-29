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

package org.kuali.student.krms.naturallanguage.config.context;

import org.kuali.rice.core.api.resourceloader.GlobalResourceLoader;
import org.kuali.student.r2.core.constants.TypeServiceConstants;
import org.kuali.student.r2.core.krms.naturallanguage.TermParameterTypes;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.util.ContextUtils;
import org.kuali.student.r2.core.atp.service.AtpService;
import org.kuali.student.r2.core.class1.type.dto.TypeInfo;
import org.kuali.student.r2.core.class1.type.service.TypeService;

import javax.xml.namespace.QName;
import java.util.Map;
import org.kuali.rice.core.api.exception.RiceIllegalStateException;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;


/**
 * This class creates the template context for an academic time period.
 */
public class AtpContextImpl extends BasicContextImpl {


    private AtpService atpService;
    private TypeService typeService;
	
	public final static String DURATION_TYPE_TOKEN = "durationType";
	public final static String DURATION_TOKEN = "duration";

	public void setAtpService(AtpService atpService) {
		this.atpService = atpService;
	}


//    public TypeService getTypeService() {
//        return typeService;
//    }
    public TypeService getTypeService() {
        if(typeService == null){
            typeService = (TypeService) GlobalResourceLoader.getService(new QName(TypeServiceConstants.NAMESPACE, TypeServiceConstants.SERVICE_NAME_LOCAL_PART));
        }
        return typeService;
    }

    public void setTypeService(TypeService typeService) {
        this.typeService = typeService;
    }

	private TypeInfo getAtpDurationType(String atpDurationTypeKey)  {
		if (atpDurationTypeKey == null) {
			return null;
		}
		try {
			TypeInfo atpDurationType = this.getTypeService().getType(atpDurationTypeKey, ContextUtils.getContextInfo());
			return atpDurationType;
		} catch (Exception e) {
                   throw new RiceIllegalStateException (e);
		}
	}
	
    /**
     * Creates the context map (template data) for the requirement component.
     *
     * @param parameters
     * @param contextInfo
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException Creating context map fails
     */
    @Override
    public Map<String, Object> createContextMap(Map<String, Object> parameters) {
        Map<String, Object> contextMap = super.createContextMap(parameters);

        String durationTypeKey = (String) parameters.get(TermParameterTypes.DURATION_TYPE_KEY.getId());
        TypeInfo atpDurationType = getAtpDurationType(durationTypeKey);
        if( atpDurationType != null){
            contextMap.put(DURATION_TYPE_TOKEN, atpDurationType);
        }
        String duration = (String) parameters.get(TermParameterTypes.DURATION_KEY.getId());
        if( duration != null){
            contextMap.put(DURATION_TOKEN, duration);
        }

        return contextMap;
    }
}
