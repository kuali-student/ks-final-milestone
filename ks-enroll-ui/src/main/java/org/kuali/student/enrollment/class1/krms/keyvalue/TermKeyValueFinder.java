/**
 * Copyright 2005-2013 The Kuali Foundation
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
package org.kuali.student.enrollment.class1.krms.keyvalue;

import org.kuali.rice.core.api.util.ConcreteKeyValue;
import org.kuali.rice.core.api.util.KeyValue;
import org.kuali.rice.krad.keyvalues.KeyValuesBase;
import org.kuali.student.r2.core.acal.infc.Term;

import java.util.ArrayList;
import java.util.List;

/**
 * This class created values for the term droplist in krms ui.
 *
 * @author Kuali Student Team
 */
public class TermKeyValueFinder extends KeyValuesBase {

    @Override
    public List<KeyValue> getKeyValues() {
        List<KeyValue> labels = new ArrayList<KeyValue>();
        labels.add(new ConcreteKeyValue("", "Select Term Type"));
        labels.add(new ConcreteKeyValue("1", "Fall Term"));
        labels.add(new ConcreteKeyValue("2", "Subterm: Half Fall1"));
        labels.add(new ConcreteKeyValue("3", "Subterm: Half Fall2"));
        labels.add(new ConcreteKeyValue("4", "Spring Term"));
        labels.add(new ConcreteKeyValue("5", "Summer Term"));
        labels.add(new ConcreteKeyValue("6", "Summer 1"));
        labels.add(new ConcreteKeyValue("7", "Summer 2"));
        labels.add(new ConcreteKeyValue("8", "Continuing Education Term 1"));
        labels.add(new ConcreteKeyValue("9", "Continuing Education Term 2"));
        labels.add(new ConcreteKeyValue("10", "Continuing Education Term 3"));
        labels.add(new ConcreteKeyValue("11", "Continuing Education Term 4"));

        return labels;
    }
}
