/**
 * Copyright 2005-2012 The Kuali Foundation
 *
 * Licensed under the Educational Community License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.opensource.org/licenses/ecl2.php
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.kuali.student.enrollment.class1.krms.keyvalues;


import org.kuali.rice.core.api.util.ConcreteKeyValue;
import org.kuali.rice.core.api.util.KeyValue;
import org.kuali.rice.krad.keyvalues.KeyValuesBase;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author Kuali Rice Team (rice.collab@kuali.org)
 *
 */
public class CourseCodeValuesFinder extends KeyValuesBase {

    /*
     * @see org.kuali.keyvalues.KeyValuesFinder#getKeyValues()
     */
    @Override
    public List<KeyValue> getKeyValues() {
        List<KeyValue> labels = new ArrayList<KeyValue>();
        labels.add(new ConcreteKeyValue("", ""));
        labels.add(new ConcreteKeyValue("MATH110", "MATH110"));
        labels.add(new ConcreteKeyValue("MATH120", "MATH120"));
        labels.add(new ConcreteKeyValue("GEOG130", "GEOG130"));
        labels.add(new ConcreteKeyValue("GEOG130", "GEOG130"));
        labels.add(new ConcreteKeyValue("BIO140", "BIO140"));
        labels.add(new ConcreteKeyValue("BIO150", "BIO150"));
        return labels;
    }    
}
