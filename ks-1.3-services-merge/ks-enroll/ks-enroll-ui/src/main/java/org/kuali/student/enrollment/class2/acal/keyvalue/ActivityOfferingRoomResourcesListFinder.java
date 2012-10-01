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
 * Created by bobhurt on 6/13/12
 */
package org.kuali.student.enrollment.class2.acal.keyvalue;

import org.kuali.rice.core.api.util.ConcreteKeyValue;
import org.kuali.rice.core.api.util.KeyValue;
import org.kuali.rice.krad.uif.control.UifKeyValuesFinderBase;
import org.kuali.rice.krad.uif.view.ViewModel;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * This class returns a list of room resources, given a building and room
 *
 * @author Kuali Student Team
 */
public class ActivityOfferingRoomResourcesListFinder extends UifKeyValuesFinderBase implements Serializable
{

    @Override
    public List<KeyValue> getKeyValues(ViewModel model) {
        List<KeyValue> keyValues = new ArrayList<KeyValue>();

        //TODO - get values from...?
        keyValues.add(new ConcreteKeyValue("Camcorder",null));
        keyValues.add(new ConcreteKeyValue("DVD Player",null));
        keyValues.add(new ConcreteKeyValue("Overhead Projector",null));
        keyValues.add(new ConcreteKeyValue("Phonograph Player",null));
        keyValues.add(new ConcreteKeyValue("Walkie-Talkie",null));
        keyValues.add(new ConcreteKeyValue("AM Radio",null));
        keyValues.add(new ConcreteKeyValue("8-Track Cassette Player",null));

        return keyValues;
    }

}