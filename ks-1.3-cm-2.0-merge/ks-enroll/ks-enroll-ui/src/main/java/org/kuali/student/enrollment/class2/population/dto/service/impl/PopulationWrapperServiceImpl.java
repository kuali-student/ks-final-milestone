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
 * Created by vgadiyak on 7/16/12
 */
package org.kuali.student.enrollment.class2.population.dto.service.impl;

import org.kuali.rice.core.api.resourceloader.GlobalResourceLoader;
import org.kuali.rice.krad.util.GlobalVariables;
import org.kuali.student.enrollment.class2.population.dto.PopulationWrapper;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.LocaleInfo;
import org.kuali.student.r2.core.population.service.PopulationService;

import javax.xml.namespace.QName;
import java.util.Locale;

/**
 * This class //TODO ...
 *
 * @author Kuali Student Team
 */
public class PopulationWrapperServiceImpl {

    private ContextInfo contextInfo;
    private transient PopulationService populationService;

    // getting the PopulationInfo and Rule and pushing them into wrapper
    public PopulationWrapper getPopulationWrapper(String id) {

        PopulationWrapper wrapper = new PopulationWrapper();

        try {
            wrapper.setPopulationInfo(getPopulationService().getPopulation(id, getContextInfo()));
            wrapper.setPopulationRuleInfo(getPopulationService().getPopulationRuleForPopulation(id, getContextInfo()));
        } catch (Exception ex){
            throw new RuntimeException(ex);
        }

        return wrapper;
    }

    // saving the PopulationInfo and Rule: if no id - create new one, otherwise update
    public PopulationWrapper savePopulationWrapper(PopulationWrapper wrapper) {
        String id = wrapper.getPopulationInfo().getId();

        try {
            if (id == null || id.equals("")) {
                wrapper.setPopulationInfo(getPopulationService().createPopulation(wrapper.getPopulationInfo(), getContextInfo()));
                wrapper.setPopulationRuleInfo(getPopulationService().createPopulationRule(wrapper.getPopulationRuleInfo(), getContextInfo()));
            } else {
                getPopulationService().updatePopulation(id, wrapper.getPopulationInfo(), getContextInfo());
                getPopulationService().updatePopulationRule(wrapper.getPopulationRuleInfo().getId(), wrapper.getPopulationRuleInfo(), getContextInfo());
            }
        } catch (Exception ex){
            throw new RuntimeException(ex);
        }

        return wrapper;
    }

    public ContextInfo getContextInfo() {
        if (null == contextInfo) {
            contextInfo = new ContextInfo();
            contextInfo.setAuthenticatedPrincipalId(GlobalVariables.getUserSession().getPrincipalId());
            contextInfo.setPrincipalId(GlobalVariables.getUserSession().getPrincipalId());
            LocaleInfo localeInfo = new LocaleInfo();
            localeInfo.setLocaleLanguage(Locale.getDefault().getLanguage());
            localeInfo.setLocaleRegion(Locale.getDefault().getCountry());
            contextInfo.setLocale(localeInfo);
        }
        return contextInfo;
    }

    protected PopulationService getPopulationService() {
        if(populationService == null) {
            populationService = (PopulationService) GlobalResourceLoader.getService(new QName("http://student.kuali.org/wsdl/population", "PopulationService"));
        }
        return this.populationService;
    }

}
