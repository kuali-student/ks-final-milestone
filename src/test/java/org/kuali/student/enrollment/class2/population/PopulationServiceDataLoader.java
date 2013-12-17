/**
 * Copyright 2013 The Kuali Foundation Licensed under the
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
 * Created by mahtabme on 12/17/13
 */
package org.kuali.student.enrollment.class2.population;

import org.kuali.student.common.test.AbstractMockServicesAwareDataLoader;
import org.kuali.student.r2.common.dto.RichTextInfo;
import org.kuali.student.r2.common.util.RichTextHelper;
import org.kuali.student.r2.core.constants.PopulationServiceConstants;
import org.kuali.student.r2.core.population.dto.PopulationInfo;
import org.kuali.student.r2.core.population.service.PopulationService;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * This class loads data into a Population Service
 *
 * @author Mezba Mahtab
 */
public class PopulationServiceDataLoader extends AbstractMockServicesAwareDataLoader {

    @Resource
    private PopulationService populationService;

    @Override
    protected void initializeData() throws Exception {
        createPopulations();
    }

    protected void createPopulations () throws Exception {
        populationService.createPopulation
                (createPopulationInfo
                        ("Freshmen",
                                "A list of first year students",
                                PopulationServiceConstants.POPULATION_STUDENT_TYPE_KEY,
                                PopulationServiceConstants.POPULATION_ACTIVE_STATE_KEY,
                                new ArrayList<String>(),
                                Boolean.FALSE,
                                Boolean.TRUE), context);
        populationService.createPopulation
                (createPopulationInfo
                        ("Students",
                                "A list of students",
                                PopulationServiceConstants.POPULATION_STUDENT_TYPE_KEY,
                                PopulationServiceConstants.POPULATION_ACTIVE_STATE_KEY,
                                new ArrayList<String>(),
                                Boolean.FALSE,
                                Boolean.TRUE), context);
        populationService.createPopulation
                (createPopulationInfo
                        ("Instructors",
                                "A list of instructors",
                                PopulationServiceConstants.POPULATION_TYPE_KEY,
                                PopulationServiceConstants.POPULATION_ACTIVE_STATE_KEY,
                                new ArrayList<String>(),
                                Boolean.FALSE,
                                Boolean.TRUE), context);
        populationService.createPopulation
                (createPopulationInfo
                        ("Humans",
                                "A list of humans in the POC",
                                PopulationServiceConstants.POPULATION_TYPE_KEY,
                                PopulationServiceConstants.POPULATION_ACTIVE_STATE_KEY,
                                new ArrayList<String>(),
                                Boolean.FALSE,
                                Boolean.TRUE), context);

    }

    protected PopulationInfo createPopulationInfo
            (String name,
             String descriptionPlain,
             String typeKey,
             String stateKey,
             List<String> sortOrderTypeKeys,
             Boolean variesByTime,
             Boolean supportsGetMembers) {
        PopulationInfo populationInfo = new PopulationInfo();
        populationInfo.setName(name);
        populationInfo.setDescr(new RichTextHelper().fromPlain(descriptionPlain));
        populationInfo.setTypeKey(typeKey);
        populationInfo.setStateKey(stateKey);
        populationInfo.setSortOrderTypeKeys(sortOrderTypeKeys);
        populationInfo.setVariesByTime(variesByTime);
        populationInfo.setSupportsGetMembers(supportsGetMembers);
        return populationInfo;
    }
}
