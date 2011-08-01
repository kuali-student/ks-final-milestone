package org.kuali.student.enrollment.class2.grading.keyvalue;

/*
 * Copyright 2007 The Kuali Foundation
 *
 * Licensed under the Educational Community License, Version 1.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.opensource.org/licenses/ecl1.php
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import org.kuali.rice.core.util.ConcreteKeyValue;
import org.kuali.rice.kns.lookup.keyvalues.KeyValuesBase;

import java.util.ArrayList;
import java.util.List;

public class CourseOfferingKeyValues extends KeyValuesBase {

    public List getKeyValues() {
        List keyValues = new ArrayList();



        keyValues.add(new ConcreteKeyValue("", ""));
        keyValues.add(new ConcreteKeyValue("PHYS121", "PHYS 121"));
        keyValues.add(new ConcreteKeyValue("PHYS122", "PHYS 122"));

        return keyValues;
    }

   /* private void getCourseOffernings(){
        CourseOfferingInfo courseOffering = new CourseOfferingInfo();
        courseOffering.setCourseId("1");
        courseOffering.setCourseNumberSuffix("T");
        courseOffering.setCourseOfferingCode("MAT");
        courseOffering.setCourseTitle("Maths");
        courseOffering.setIsHonorsOffering(false);
        RichTextInfo desc = new RichTextInfo();
        desc.setPlain("Sample CourseOfferning");
        courseOffering.setDescr(desc);
        courseOffering.setHasFinalExam(true);

        GradingServiceMockImpl c= new GradingServiceMockImpl();
    }
*/
}