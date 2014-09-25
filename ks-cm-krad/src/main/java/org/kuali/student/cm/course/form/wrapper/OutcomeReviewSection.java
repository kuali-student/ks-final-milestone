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

import org.kuali.student.cm.uif.wrapper.DTOWrapperBase;

/**
 *
 * @author Kuali Student Team
 */
public class OutcomeReviewSection extends DTOWrapperBase implements CourseCompareCollectionElement {

    protected String outComeType;
    protected String outComeValue;
    private boolean hightlightRow;
    private boolean fakeObjectForCompare;

    public OutcomeReviewSection(){
        super();
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

    /**
     * @see org.kuali.student.cm.course.form.wrapper.CourseCompareCollectionElement#isHightlightRow()
     * @return
     */
    @Override
    public boolean isHightlightRow() {
        return hightlightRow;
    }

    /**
     * @see CourseCompareCollectionElement#setFakeObjectForCompare(boolean)
     * @param hightlightRow
     */
    @Override
    public void setHightlightRow(boolean hightlightRow) {
        this.hightlightRow = hightlightRow;
    }

    /**
     * @see #setFakeObjectForCompare(boolean)
     * @return
     */
    @Override
    public boolean isFakeObjectForCompare() {
        return fakeObjectForCompare;
    }

    /**
     * This flag is being used ONLY for compare view. In compare view, all the collections (LO, formats/activites
     * and Outcomes) should be of same size for display purpose. We create fake collection elements for this
     * purpose.
     *
     * @param fakeObjectForCompare
     */
    @Override
    public void setFakeObjectForCompare(boolean fakeObjectForCompare) {
        this.fakeObjectForCompare = fakeObjectForCompare;
        this.hightlightRow = true;
    }
}
