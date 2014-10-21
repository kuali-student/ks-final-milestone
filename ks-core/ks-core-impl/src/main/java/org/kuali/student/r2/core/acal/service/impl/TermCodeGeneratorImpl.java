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

import org.apache.commons.lang.StringUtils;
import org.kuali.rice.core.api.resourceloader.GlobalResourceLoader;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.common.util.security.ContextUtils;
import org.kuali.student.r2.common.util.date.DateFormatters;
import org.kuali.student.r2.core.acal.dto.TermInfo;
import org.kuali.student.r2.core.acal.service.TermCodeGenerator;
import org.kuali.student.r2.core.class1.type.dto.TypeInfo;
import org.kuali.student.r2.core.class1.type.service.TypeService;
import org.kuali.student.r2.core.constants.TypeServiceConstants;

import javax.xml.namespace.QName;

/**
 * This implementation class generates the term code based off the term's type attributes.  The type attribute we're
 * checking can be found in
 * org.kuali.student.r2.core.constants.TypeServiceConstants.ATP_TERM_TYPE_CODE_ATTR
 *
 * @author Kuali Student Team
 */
public class TermCodeGeneratorImpl implements TermCodeGenerator {
    private static final String YEAR_ONLY_FORMAT_STRING = "yyyy";

    private TypeService typeService;

    /**
     * In this implementation we are generating the termCode from the term's startDate and the term's type attribute.
     * From the startDate we are pulling just the year in the yyyy format. The type attribute will contain the
     * Exact string that we need to populate to append to the year.
     *
     * So, in this example the year could be 2012 and if the type is spring and at UMD the attribute would contain '01'.
     * This would result in a code: '201201' for spring 2012.
     * @param term
     * @return
     */
    public String generateTermCode(TermInfo term) {
        //Don't generate if the term was set already
        if (term.getCode() != null){
            return term.getCode();
        }

        if(term.getTypeKey() == null || StringUtils.isBlank(term.getTypeKey())) {
            return null;
        }

        String typeCode;

        try {
            TypeInfo type = getTypeService().getType(term.getTypeKey(), createContextInfo());
            typeCode = type.getAttributeValue(TypeServiceConstants.ATP_TERM_TYPE_CODE_ATTR);
        } catch (Exception e) {
            throw new RuntimeException("Term Code Generation error.", e);
        }

        // if the term is not of a type that is handled by the defined formula, return null, since the value for the atp code is undefined at that point
        if(typeCode == null || typeCode.equals("")) {
//            throw new RuntimeException("Error: missing term code attribute. Please configure a type attribute of '" + TypeServiceConstants.ATP_TERM_TYPE_CODE_ATTR +
//                    "' in KSEN_TYPE_ATTTR table. For term type " + term.getTypeKey());
            return null;
        }

        StringBuilder result = new StringBuilder(DateFormatters.DEFULT_YEAR_FORMATTER.format(term.getStartDate()));

        result.append(typeCode);

        return result.toString();
    }

    public ContextInfo createContextInfo(){
        return ContextUtils.createDefaultContextInfo();
    }

    public TypeService getTypeService() {
        if(typeService == null) {
            typeService = GlobalResourceLoader.getService(new QName(TypeServiceConstants.NAMESPACE, TypeServiceConstants.SERVICE_NAME_LOCAL_PART));
        }
        return this.typeService;
    }
}
