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
 * Created by mharmath on 7/13/12
 */
package org.kuali.student.enrollment.class2.population.keyvalue;

import org.kuali.rice.core.api.resourceloader.GlobalResourceLoader;
import org.kuali.rice.core.api.util.ConcreteKeyValue;
import org.kuali.rice.core.api.util.KeyValue;
import org.kuali.rice.krad.uif.control.UifKeyValuesFinderBase;
import org.kuali.rice.krad.uif.view.ViewModel;
import org.kuali.rice.krad.util.GlobalVariables;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.LocaleInfo;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.common.util.constants.PopulationServiceConstants;
import org.kuali.student.r2.core.population.service.PopulationService;

import javax.xml.namespace.QName;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

/**
 * This class //TODO ...
 *
 * @author Kuali Student Team
 */
public class PopulationTypeKeyValues extends UifKeyValuesFinderBase implements Serializable {

    private PopulationService populationService;

    @Override
    public List<KeyValue> getKeyValues(ViewModel model) {

        List<KeyValue> keyValues = new ArrayList();
        List<String> populationTypeKeyList = new ArrayList();
        // TODO: Change hardcoded values to DB call after DB is fixed
        List<String> populationTypeList = new ArrayList(Arrays.asList("Any Type", "Core", "Union", "Intersection", "Exclusion"));

//        try {
//            for (String populationType : populationTypeList ) {
//                if (populationType.equalsIgnoreCase("Any Type")) {
//                    populationTypeKeyList = getPopulationService().getPopulationKeysByType("NONE", getContextInfo());
//                    for (String keyVal : populationTypeKeyList){
//                        keyValues.add(new ConcreteKeyValue(populationType, keyVal));
//                    }
//                } else {
//                    populationTypeKeyList = getPopulationService().getPopulationKeysByType(populationType, getContextInfo());
//                    for (String keyVal : populationTypeKeyList){
//                        keyValues.add(new ConcreteKeyValue(populationType, keyVal));
//                    }
//                }
//            }
//        } catch (InvalidParameterException e) {
//            throw new RuntimeException(e);
//        } catch (MissingParameterException e) {
//            throw new RuntimeException(e);
//        } catch (OperationFailedException e) {
//            throw new RuntimeException(e);
//        } catch (PermissionDeniedException e) {
//            throw new RuntimeException(e);
//        }
        return keyValues;
    }

    protected PopulationService getPopulationService() {
        if(populationService == null) {
            populationService = (PopulationService) GlobalResourceLoader.getService(new QName(PopulationServiceConstants.NAMESPACE, "PopulationService"));
        }
        return this.populationService;
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
}
