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
 * @author Kuali Student Team
 */

package org.kuali.student.cm.course.form.wrapper;

import org.apache.commons.lang.StringUtils;
import org.kuali.student.cm.uif.util.RenderHelper;

/**
 * Wrapper for Activities for Review Proposal page. All the getters are being referenced from the krad
 * markup xml at CourseViewReviewProposal.xml
 *
 */
public class ActivityInfoWrapper {

    private String activityType;
    private Integer anticipatedClassSize;
    private String durationCount;
    private String contactHours;
    private RenderHelper renderHelper;

    /**
     * Not being used from any other java classes. It's here for Spring/krad to
     * create new objects for the collection we defined for course maintenance view
     */
    @SuppressWarnings("unused")
    public ActivityInfoWrapper(){

    }

    public ActivityInfoWrapper(Integer anticipatedClassSize, String activityType, String durationCount, String contactHours) {
        this.durationCount = durationCount;
        this.anticipatedClassSize = anticipatedClassSize;
        this.activityType = activityType;
        this.contactHours = contactHours;
    }

    /**
     * Reference only at KRAD markup xml
     * @return
     */
    @SuppressWarnings("unused")
    public String getActivityType() {
        if (activityType == null)
            return "";
        // to get Discussion                  from  kuali.lu.type.activity.Discussion
        //        ExperientialLearningOROther from  kuali.lu.type.activity.ExperientialLearningOROther
        //        Lab                         from  kuali.lu.type.activity.Lab
        return activityType.substring(activityType.lastIndexOf(".") + 1);
    }

    /**
     * Reference only at KRAD markup xml
     * @return
     */
    @SuppressWarnings("unused")
    public String getContactHours() {
        return StringUtils.defaultIfEmpty(contactHours,"");
    }

    /**
     * Reference only at KRAD markup xml
     * @return
     */
    @SuppressWarnings("unused")
    public Integer getAnticipatedClassSize() {
        if (anticipatedClassSize == null){
            return Integer.valueOf(0);
        }
        return anticipatedClassSize;
    }

    /**
     * Reference only at KRAD markup xml
     * @return
     */
    @SuppressWarnings("unused")
    public String getDurationCount() {
        return StringUtils.defaultIfEmpty(durationCount,"");
    }

    public RenderHelper getRenderHelper() {
        return renderHelper;
    }

    public void setRenderHelper(RenderHelper renderHelper) {
        this.renderHelper = renderHelper;
    }

}
