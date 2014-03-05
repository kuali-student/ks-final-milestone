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
package org.kuali.student.enrollment.class2.acal.service.impl;

import org.apache.log4j.Logger;
import org.kuali.rice.core.api.resourceloader.GlobalResourceLoader;
import org.kuali.rice.krad.lookup.LookupableImpl;
import org.kuali.rice.krad.web.form.LookupForm;
import org.kuali.student.common.util.CalendarSearchViewHelperUtil;
import org.kuali.student.common.util.security.ContextUtils;
import org.kuali.student.r2.core.acal.dto.TermInfo;
import org.kuali.student.r2.core.atp.service.AtpService;
import org.kuali.student.r2.core.class1.type.service.TypeService;
import org.kuali.student.r2.core.constants.AtpServiceConstants;
import org.kuali.student.r2.core.constants.TypeServiceConstants;

import javax.xml.namespace.QName;
import java.util.List;
import java.util.Map;


public class AcademicTermLookupableImpl  extends LookupableImpl {
    private static final long serialVersionUID = 1L;

    private transient AtpService atpService;
    private transient TypeService typeService;

    private final static Logger LOG = Logger.getLogger(AcademicTermLookupableImpl.class);

    @Override
    protected List<?> getSearchResults(LookupForm lookupForm, Map<String, String> fieldValues, boolean unbounded) {

        List<TermInfo> rList;
        String name = fieldValues.get("code");
        String year = fieldValues.get("startDate");

        try{
            rList = CalendarSearchViewHelperUtil.searchForTerms(name, year, ContextUtils.createDefaultContextInfo(), getAtpService(), getTypeService());
        }   catch (Exception ex){
            LOG.error(ex);
            throw new RuntimeException("Error in AcademicTermLookupableImpl searching for term. name[" + name +"] year["+year +"]", ex);
        }

        return rList;
    }

    protected AtpService getAtpService() {
         if(atpService == null) {
             atpService = (AtpService) GlobalResourceLoader.getService(new QName(AtpServiceConstants.NAMESPACE, AtpServiceConstants.SERVICE_NAME_LOCAL_PART));
        }
        return this.atpService;
    }

    protected TypeService getTypeService(){
        if (typeService == null){
            typeService = GlobalResourceLoader.getService(new QName(TypeServiceConstants.NAMESPACE, TypeServiceConstants.SERVICE_NAME_LOCAL_PART));
        }
        return typeService;
    }
}

