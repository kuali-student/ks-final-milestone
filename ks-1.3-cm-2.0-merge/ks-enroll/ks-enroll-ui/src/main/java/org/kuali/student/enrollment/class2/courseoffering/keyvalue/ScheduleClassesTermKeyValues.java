package org.kuali.student.enrollment.class2.courseoffering.keyvalue;

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



import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import org.kuali.rice.core.api.util.ConcreteKeyValue;
import org.kuali.rice.core.api.util.KeyValue;
import org.kuali.rice.krad.keyvalues.KeyValuesBase;

/**
 * @deprecated This class is leftover from Core Slice. Delete when no longer needed or un deprecate if needed.
 */
@Deprecated
public class ScheduleClassesTermKeyValues extends KeyValuesBase implements Serializable {

    private static final long serialVersionUID = 1L;
   
    @Override
    public List<KeyValue> getKeyValues() {
        List<KeyValue> keyValues = new ArrayList<KeyValue>();

        keyValues.add(new ConcreteKeyValue("kuali.atp.type.Fall", "Fall" ));
        keyValues.add(new ConcreteKeyValue("kuali.atp.type.Winter", "Winter"));
        keyValues.add(new ConcreteKeyValue("kuali.atp.type.Spring", "Spring"));
        keyValues.add(new ConcreteKeyValue("kuali.atp.type.Summer", "Summer"));
        return keyValues;
    }

}
