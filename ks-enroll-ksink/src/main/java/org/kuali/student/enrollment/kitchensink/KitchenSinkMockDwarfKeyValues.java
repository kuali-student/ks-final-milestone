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
 * Created by bobhurt on 9/6/12
 */
package org.kuali.student.enrollment.kitchensink;

import org.kuali.rice.core.api.util.ConcreteKeyValue;
import org.kuali.rice.core.api.util.KeyValue;
import org.kuali.rice.krad.uif.control.UifKeyValuesFinderBase;
import org.kuali.rice.krad.uif.view.ViewModel;

import java.util.ArrayList;
import java.util.List;

/**
 * This class provides a mock implementation for a checkbox control's "optionsFinder" property.
 *
 * @author Kuali Student Team
 */
public class KitchenSinkMockDwarfKeyValues extends UifKeyValuesFinderBase {

    public KitchenSinkMockDwarfKeyValues() {
        // prevent addBlankOption from ever being set to true:
        this.setAddBlankOption(false);
    }
    /**
     * This is a fake implementation of a key value finder, normally this would make a request to
     * a database to obtain the necessary values.  Used only for testing.
     *
     * @see org.kuali.rice.krad.uif.control.UifKeyValuesFinderBase
     */
    @Override
    public List<KeyValue> getKeyValues(ViewModel model) {
        // NOTE: if the model isn't needed, the class can just extend KeyValuesBase
        KitchenSinkForm form = (KitchenSinkForm)model;

        List<KeyValue> keyValues = new ArrayList<KeyValue>();
        keyValues.add(new ConcreteKeyValue("1", "A dwarf named Bashful"));
        keyValues.add(new ConcreteKeyValue("2", "A dwarf named Doc"));
        keyValues.add(new ConcreteKeyValue("3", "A dwarf named Dopey"));
        keyValues.add(new ConcreteKeyValue("4", "A dwarf named Grumpy"));
        keyValues.add(new ConcreteKeyValue("5", "A dwarf named Happy"));
        keyValues.add(new ConcreteKeyValue("6", "A dwarf named Sleepy"));
        keyValues.add(new ConcreteKeyValue("7", "A dwarf named Sneezy"));
        return keyValues;
    }

}
