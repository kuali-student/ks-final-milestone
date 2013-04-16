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
package org.kuali.student.enrollment.class2.population.service;

import org.kuali.rice.krad.web.form.MaintenanceForm;
import org.kuali.student.r2.core.population.dto.PopulationInfo;

import java.util.List;

/**
 * This class //TODO ...
 *
 * @author Kuali Student Team
 */
public interface PopulationManagementViewHelperService {

    public List<PopulationInfo> findPopulationsByNameAndType (String name, String typeKey, MaintenanceForm form);

}
