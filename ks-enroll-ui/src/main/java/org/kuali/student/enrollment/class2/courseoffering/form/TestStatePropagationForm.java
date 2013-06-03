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
 * Created by Charles on 5/6/13
 */
package org.kuali.student.enrollment.class2.courseoffering.form;

import org.kuali.rice.krad.web.form.UifFormBase;
import org.kuali.student.enrollment.class2.courseoffering.dto.StatePropagationWrapper;

import java.util.ArrayList;
import java.util.List;

/**
 * This class //TODO ...
 *
 * @author Kuali Student Team
 */
public class TestStatePropagationForm extends UifFormBase {

    private List<StatePropagationWrapper> aoStatePropagationWrapperList;
    private List<StatePropagationWrapper> foStatePropagationWrapperList;
    private List<StatePropagationWrapper> coStatePropagationWrapperList;

     public TestStatePropagationForm() {

        aoStatePropagationWrapperList = new ArrayList<StatePropagationWrapper>();
        foStatePropagationWrapperList = new ArrayList<StatePropagationWrapper>();
        coStatePropagationWrapperList = new ArrayList<StatePropagationWrapper>();
    }


    public List<StatePropagationWrapper> getFoStatePropagationWrapperList() {
        return foStatePropagationWrapperList;
    }

    public void setFoStatePropagationWrapperList(List<StatePropagationWrapper> foStatePropagationWrapperList) {
        this.foStatePropagationWrapperList = foStatePropagationWrapperList;
    }

    public List<StatePropagationWrapper> getAoStatePropagationWrapperList() {
        return aoStatePropagationWrapperList;
    }

    public void setAoStatePropagationWrapperList(List<StatePropagationWrapper> aoStatePropagationWrapperList) {
        this.aoStatePropagationWrapperList = aoStatePropagationWrapperList;
    }

    public List<StatePropagationWrapper> getCoStatePropagationWrapperList() {
        return coStatePropagationWrapperList;
    }

    public void setCoStatePropagationWrapperList(List<StatePropagationWrapper> coStatePropagationWrapperList) {
        this.coStatePropagationWrapperList = coStatePropagationWrapperList;
    }



    public void addAoStatePropagationWrapper(StatePropagationWrapper wrapper){
        aoStatePropagationWrapperList.add(wrapper);
    }

    public void addFoStatePropagationWrapper(StatePropagationWrapper wrapper){
        foStatePropagationWrapperList.add(wrapper);
    }

    public void addCoStatePropagationWrapper(StatePropagationWrapper wrapper){
        coStatePropagationWrapperList.add(wrapper);
    }
}
