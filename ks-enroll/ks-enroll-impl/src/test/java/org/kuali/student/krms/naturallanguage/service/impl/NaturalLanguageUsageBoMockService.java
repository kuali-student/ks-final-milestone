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
 * Created by NWUuser on 2/26/13
 */
package org.kuali.student.krms.naturallanguage.service.impl;

import org.kuali.rice.krms.api.repository.language.NaturalLanguageUsage;
import org.kuali.rice.krms.api.repository.language.NaturalLanguageUsageContract;
import org.kuali.rice.krms.impl.repository.NaturalLanguageUsageBo;
import org.kuali.rice.krms.impl.repository.NaturalLanguageUsageBoService;
import org.kuali.student.krms.naturallanguage.KRMSDataGenerator;

import java.util.List;

/**
 * This class //TODO ...
 *
 * @author Kuali Student Team
 */
public class NaturalLanguageUsageBoMockService implements NaturalLanguageUsageBoService {
    public NaturalLanguageUsageContract naturalLanguageUsageContract = KRMSDataGenerator.createNaturalLanguageUsage("Kuali Rule Edit","kuali.krms.edit","KS-SYS","10000",true,0L);

    @Override
    public NaturalLanguageUsage createNaturalLanguageUsage(NaturalLanguageUsage naturalLanguageUsage) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public NaturalLanguageUsage getNaturalLanguageUsage(String naturalLanguageUsageId) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public NaturalLanguageUsage getNaturalLanguageUsageByName(String namespace, String name) {
        return NaturalLanguageUsage.Builder.create(naturalLanguageUsageContract).build();
    }

    @Override
    public void updateNaturalLanguageUsage(NaturalLanguageUsage naturalLanguageUsage) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void deleteNaturalLanguageUsage(String naturalLanguageUsageId) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public List<NaturalLanguageUsage> findNaturalLanguageUsagesByName(String name) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public List<NaturalLanguageUsage> findNaturalLanguageUsagesByDescription(String description) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public List<NaturalLanguageUsage> findNaturalLanguageUsagesByNamespace(String namespace) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public NaturalLanguageUsage to(NaturalLanguageUsageBo naturalLanguageUsageBo) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public NaturalLanguageUsageBo from(NaturalLanguageUsage naturalLanguageUsage) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
