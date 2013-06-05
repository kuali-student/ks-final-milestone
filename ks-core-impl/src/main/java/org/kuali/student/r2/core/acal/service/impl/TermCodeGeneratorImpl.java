/**
 * Copyright 2012 The Kuali Foundation Licensed under the
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
 *
 */
package org.kuali.student.r2.core.acal.service.impl;

import org.apache.commons.httpclient.util.DateUtil;
import org.kuali.rice.core.api.resourceloader.GlobalResourceLoader;
import org.kuali.student.r2.common.assembler.AssemblyException;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.exceptions.DataValidationErrorException;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.common.exceptions.VersionMismatchException;
import org.kuali.student.r2.common.util.ContextUtils;
import org.kuali.student.r2.core.acal.dto.TermInfo;
import org.kuali.student.r2.core.acal.service.TermCodeGenerator;
import org.kuali.student.r2.core.class1.type.dto.TypeInfo;
import org.kuali.student.r2.core.class1.type.service.TypeService;
import org.kuali.student.r2.core.constants.AtpServiceConstants;
import org.kuali.student.r2.core.constants.TypeServiceConstants;

import javax.xml.namespace.QName;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This class //TODO ...
 *
 * @author Kuali Student Team
 */
public class TermCodeGeneratorImpl implements TermCodeGenerator {
    private static final String YEAR_ONLY_FORMAT_STRING = "yyyy";

    private static Map<String, String> termTypeCodeMap;
    private TypeService typeService;

    static {
        Map<String, String> map = new HashMap<String, String>(5);

        map.put(AtpServiceConstants.ATP_WINTER_TYPE_KEY, "12");
        map.put(AtpServiceConstants.ATP_SPRING_TYPE_KEY, "01");
        map.put(AtpServiceConstants.ATP_SUMMER1_TYPE_KEY, "05");
        map.put(AtpServiceConstants.ATP_SUMMER1_TYPE_KEY, "07");
        map.put(AtpServiceConstants.ATP_FALL_TYPE_KEY, "08");

        termTypeCodeMap = Collections.unmodifiableMap(map);
    }

    @Override
    public String generateTermCode(TermInfo term) {
        //Don't generate if the term was set already
        if (term.getCode() != null){
            return term.getCode();
        }

        if(term.getTypeKey() == null || term.getTypeKey().equals("")) {
            return null;
        }

        String typeCode = "";

        try {
            TypeInfo type = getTypeService().getType(term.getTypeKey(), createContextInfo());
            typeCode = type.getAttributeValue(TypeServiceConstants.ATP_TERM_TYPE_CODE_ATTR);
        } catch (Exception e) {
            throw new RuntimeException("Term Code Generation : " + e.getMessage());
        }


        // if the term is not of a type that is handled by the defined formula, return null, since the value for the atp code is undefined at that point
        if(typeCode == null || typeCode.equals("")) {
            return null;
        }

        StringBuilder result = new StringBuilder(DateUtil.formatDate(term.getStartDate(), YEAR_ONLY_FORMAT_STRING));

        result.append(typeCode);

        return result.toString();
    }

    public TypeService getTypeService() {
        if(typeService == null) {
            typeService = (TypeService) GlobalResourceLoader.getService(new QName(TypeServiceConstants.NAMESPACE, TypeServiceConstants.SERVICE_NAME_LOCAL_PART));
        }
        return this.typeService;
    }

    public ContextInfo createContextInfo(){
        return ContextUtils.createDefaultContextInfo();
    }
}
