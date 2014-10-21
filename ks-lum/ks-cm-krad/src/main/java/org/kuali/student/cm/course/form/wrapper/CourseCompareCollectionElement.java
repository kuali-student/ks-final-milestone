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
 * Created by venkat on 9/24/14
 */
package org.kuali.student.cm.course.form.wrapper;

/**
 * Used by all the collections involved compare view. This interface contract enforces those collection
 * elements to set the hightlight row and if it's a fake object just used for compare view.
 *
 * @author Kuali Student Team
 */
public interface CourseCompareCollectionElement {

    @SuppressWarnings("unused")
    /**
     * Used only at KRAD markup to hightlight row for compare
     */
    public boolean isHightlightRow();

    /**
     * Marker for collection rows to be highlighted in compare view.
     *
     * @param hightlightRow
     */
    public void setHightlightRow(boolean hightlightRow);

    public boolean isFakeObjectForCompare();

    /**
     * Flag to indicate an empty collection element used at compare view. For compare view, collections should
     * be of same size. To make that happen, we create fake objects just for display purpose.
     * @param fakeObjectForCompare
     */
    public void setFakeObjectForCompare(boolean fakeObjectForCompare);

}
