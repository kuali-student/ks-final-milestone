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
 * Created by vgadiyak on 7/19/12
 */
package org.kuali.student.enrollment.class2.population.service.impl;

import org.apache.commons.lang.StringUtils;
import org.kuali.rice.core.api.criteria.PredicateFactory;
import org.kuali.rice.core.api.criteria.QueryByCriteria;
import org.kuali.rice.core.api.resourceloader.GlobalResourceLoader;
import org.kuali.rice.krad.uif.service.impl.ViewHelperServiceImpl;
import org.kuali.rice.krad.util.GlobalVariables;
import org.kuali.rice.krad.web.form.MaintenanceForm;
import org.kuali.student.enrollment.acal.dto.TermInfo;
import org.kuali.student.enrollment.class2.courseoffering.util.CourseOfferingConstants;
import org.kuali.student.enrollment.class2.population.dto.PopulationWrapper;
import org.kuali.student.enrollment.class2.population.service.PopulationManagementViewHelperService;
import org.kuali.student.enrollment.courseoffering.dto.CourseOfferingInfo;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.LocaleInfo;
import org.kuali.student.r2.core.population.dto.PopulationInfo;
import org.kuali.student.r2.core.population.service.PopulationService;

import javax.xml.namespace.QName;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * This class //TODO ...
 *
 * @author Kuali Student Team
 */
public class PopulationManagementViewHelperServiceImpl extends ViewHelperServiceImpl implements PopulationManagementViewHelperService {

    private PopulationService populationService;

    public List<PopulationInfo> findPopulationsByNameAndType (String name, String typeKey, MaintenanceForm form) {
        List<PopulationInfo> populations = new ArrayList<PopulationInfo>();

        try {
            //get courseOfferingId based on courseOfferingCode and termId
            if (StringUtils.isNotBlank(name) && StringUtils.isNotBlank(typeKey)) {
                QueryByCriteria.Builder qbcBuilder = QueryByCriteria.Builder.create();
                qbcBuilder.setPredicates(PredicateFactory.and(
                    PredicateFactory.like("name", name),
                    PredicateFactory.equal("typeKey", typeKey)));
                QueryByCriteria criteria = qbcBuilder.build();

                //Do search. In ideal case, returns one element, which is the desired CO.
                populations = populationService.searchForPopulations(criteria, new ContextInfo());
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return populations;
    }

    public ContextInfo getContextInfo() {
        ContextInfo contextInfo = new ContextInfo();
        contextInfo.setAuthenticatedPrincipalId(GlobalVariables.getUserSession().getPrincipalId());
        contextInfo.setPrincipalId(GlobalVariables.getUserSession().getPrincipalId());
        LocaleInfo localeInfo = new LocaleInfo();
        localeInfo.setLocaleLanguage(Locale.getDefault().getLanguage());
        localeInfo.setLocaleRegion(Locale.getDefault().getCountry());
        contextInfo.setLocale(localeInfo);
        return contextInfo;
    }

    protected PopulationService getPopulationService() {
        if(populationService == null) {
            populationService = (PopulationService) GlobalResourceLoader.getService(new QName("http://student.kuali.org/wsdl/population", "PopulationService"));
        }
        return this.populationService;
    }

}
