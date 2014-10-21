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
package org.kuali.student.lum.lu.ui.course.keyvalues;

import java.util.ArrayList;
import java.util.List;

import org.kuali.rice.core.api.util.ConcreteKeyValue;
import org.kuali.rice.core.api.util.KeyValue;
import org.kuali.rice.krad.uif.control.UifKeyValuesFinderBase;
import org.kuali.rice.krad.uif.view.ViewModel;

/**
 * 
 * This is the helper class for CourseView
 * 
 * @author OpenCollab/rSmart KRAD CM Conversion Alliance!
 */

public class LoSearchByValuesFinder extends UifKeyValuesFinderBase {

    private static final long serialVersionUID = 2760967200064651984L;
    
    public enum SearchByKeys {
       KEYWORD("Keyword"), CATEGORY("Category"), CODE("Code"), TITLE("Title");
        
        private String display;
        
        SearchByKeys(String display) {
            this.display = display;
        }
        
        public String getDisplay() {
            return display;
        }
    }

    @Override
    public List<KeyValue> getKeyValues(ViewModel model) {
        final List<KeyValue> keyValues = new ArrayList<KeyValue>();
        keyValues.add(new ConcreteKeyValue(SearchByKeys.KEYWORD.toString(), SearchByKeys.KEYWORD.getDisplay()));
        keyValues.add(new ConcreteKeyValue(SearchByKeys.CATEGORY.toString(), SearchByKeys.CATEGORY.getDisplay()));
        keyValues.add(new ConcreteKeyValue(SearchByKeys.CODE.toString(), SearchByKeys.CODE.getDisplay()));
        keyValues.add(new ConcreteKeyValue(SearchByKeys.TITLE.toString(), SearchByKeys.TITLE.getDisplay()));
        return keyValues;
    }

}
