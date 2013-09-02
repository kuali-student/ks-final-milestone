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
 * Created by bobhurt on 10/31/12
 */
package org.kuali.student.common.kitchensink;

import org.kuali.rice.core.api.resourceloader.GlobalResourceLoader;
import org.kuali.rice.krad.maintenance.MaintainableImpl;
import org.kuali.rice.krad.maintenance.MaintenanceDocument;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.util.ContextUtils;
import org.kuali.student.r2.core.constants.PopulationServiceConstants;
import org.kuali.student.r2.core.population.dto.PopulationInfo;
import org.kuali.student.r2.core.population.service.PopulationService;

import javax.xml.namespace.QName;
import java.util.Map;

/**
 * This class does PopulationInfo maintenance
 *
 * @author Kuali Student Team
 */
public class KitchenSinkPopulationInfoMaintainableImpl extends MaintainableImpl {

    private transient PopulationService populationService;
    private transient ContextInfo contextInfo;

    @Override
    public void saveDataObject() {
        try {
            PopulationInfo populationInfo = (PopulationInfo)getDataObject();
            // code to update PopulationInfo
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public Object retrieveObjectForEditOrCopy(MaintenanceDocument document, Map<String, String> dataObjectKeys) {
        try {
            String populationId =  dataObjectKeys.get("id");
            PopulationInfo populationInfo = getPopulationService().getPopulation(populationId, getContextInfo());
            return populationInfo;
        }
        catch (Exception e) {
            throw new RuntimeException("Could not retrieve PopulationInfo for edit", e);
        }
    }
/*
    @Override
    public void prepareForSave() {
        if (getMaintenanceAction().equalsIgnoreCase(KRADConstants.MAINTENANCE_NEW_ACTION)) {
            PopulationInfo populationInfo = (PopulationInfo)getDataObject();
            populationInfo.setStateKey(PopulationServiceConstants.POPULATION_ACTIVE_STATE_KEY);
            populationInfo.setTypeKey(PopulationServiceConstants.POPULATION_STUDENT_TYPE_KEY);
        }
        super.prepareForSave();
    }

    @Override
    public void processAfterEdit(MaintenanceDocument document, Map<String, String[]> requestParameters) {
        // document description is required for save
        document.getDocumentHeader().setDocumentDescription("Edit Population Info");

    }
*/

    private ContextInfo getContextInfo() {
        if (null == contextInfo) {
            contextInfo = ContextUtils.getContextInfo();
        }
        return contextInfo;
    }

    private PopulationService getPopulationService() {
        if (populationService == null) {
            populationService = (PopulationService) GlobalResourceLoader.getService(new QName(PopulationServiceConstants.NAMESPACE, "PopulationService"));
        }
        return populationService;
    }

}
