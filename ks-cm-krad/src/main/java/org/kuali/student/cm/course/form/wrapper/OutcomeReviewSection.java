/**
 * Copyright 2014 The Kuali Foundation Licensed under the
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
 * Created by venkat on 3/25/14
 */
package org.kuali.student.cm.course.form.wrapper;
/**
 *
 * @author Kuali Student Team
 */
public class OutcomeReviewSection {

    protected String outComeType;
    protected String outComeValue;

    public OutcomeReviewSection(){
    }

    public OutcomeReviewSection(String outComeType, String outComeValue){
        this.outComeType = outComeType;
        this.outComeValue = outComeValue;
    }

    public String getOutComeValue() {
        return outComeValue;
    }

    public String getOutComeType() {
        return outComeType;
    }

}
