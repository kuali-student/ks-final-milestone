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

import org.kuali.rice.krms.api.repository.language.NaturalLanguageTemplate;
import org.kuali.rice.krms.api.repository.language.NaturalLanguageTemplateContract;
import org.kuali.rice.krms.impl.repository.NaturalLanguageTemplateBo;
import org.kuali.rice.krms.impl.repository.NaturalLanguageTemplateBoService;
import org.kuali.student.krms.naturallanguage.KRMSDataGenerator;

import java.util.List;
import java.util.Map;

/**
 * This class //TODO ...
 *
 * @author Kuali Student Team
 */
public class NaturalLanguageTemplateBoMockService implements NaturalLanguageTemplateBoService {

    @Override
    public NaturalLanguageTemplate createNaturalLanguageTemplate(NaturalLanguageTemplate naturalLanguageTemplate) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public NaturalLanguageTemplate getNaturalLanguageTemplate(String naturalLanguageTemplateId) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void updateNaturalLanguageTemplate(NaturalLanguageTemplate naturalLanguageTemplate) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void deleteNaturalLanguageTemplate(String naturalLanguageTemplateId) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public List<NaturalLanguageTemplate> findNaturalLanguageTemplatesByAttributes(Map attributes) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public List<NaturalLanguageTemplate> findNaturalLanguageTemplatesByLanguageCode(String languageCode) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public NaturalLanguageTemplate findNaturalLanguageTemplateByLanguageCodeTypeIdAndNluId(String languageCode, String typeId, String naturalLanguageUsageId) {
        NaturalLanguageTemplateContract naturalLanguageTemplateContract;

        if( typeId.equals("kuali.krms.proposition.type.success.course.courseset.completed.all")){
            naturalLanguageTemplateContract = KRMSDataGenerator.createNaturalLanguageTemplate(languageCode,naturalLanguageUsageId,typeId,"Student must have completed all of MATH 152, MATH 221",null,"10002",true,0L);
            return NaturalLanguageTemplate.Builder.create(naturalLanguageTemplateContract).build();
        }else if( typeId.equals("kuali.krms.proposition.type.course.courseset.completed.nof")){
            naturalLanguageTemplateContract = KRMSDataGenerator.createNaturalLanguageTemplate(languageCode,naturalLanguageUsageId,typeId,"Student must have completed 1 of MATH 152, MATH 221",null,"10002",true,0L);
            return NaturalLanguageTemplate.Builder.create(naturalLanguageTemplateContract).build();
        }else if( typeId.equals("kuali.krms.proposition.type.course.courseset.completed.none")){
            naturalLanguageTemplateContract = KRMSDataGenerator.createNaturalLanguageTemplate(languageCode,naturalLanguageUsageId,typeId,"Student must have completed none of MATH 152, MATH 221",null,"10002",true,0L);
            return NaturalLanguageTemplate.Builder.create(naturalLanguageTemplateContract).build();
        }else{
            naturalLanguageTemplateContract = KRMSDataGenerator.createNaturalLanguageTemplate(languageCode,naturalLanguageUsageId,typeId,"Student needs a minimum GPA of 3.0",null,"10002",true,0L);
            return NaturalLanguageTemplate.Builder.create(naturalLanguageTemplateContract).build();
        }
    }

    @Override
    public List<NaturalLanguageTemplate> findNaturalLanguageTemplatesByNaturalLanguageUsage(String naturalLanguageUsageId) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public List<NaturalLanguageTemplate> findNaturalLanguageTemplatesByType(String typeId) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public List<NaturalLanguageTemplate> findNaturalLanguageTemplatesByTemplate(String template) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public NaturalLanguageTemplate to(NaturalLanguageTemplateBo naturalLanguageTemplateBo) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public NaturalLanguageTemplateBo from(NaturalLanguageTemplate naturalLanguageTemplate) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }
       
}
