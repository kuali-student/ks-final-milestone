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
 */
package org.kuali.student.enrollment.class2.coursewaitlist.model;

import org.kuali.student.r2.common.entity.BaseAttributeEntity;
import org.kuali.student.r2.common.infc.Attribute;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
 * User: jonrcook
 * Date: 9/3/13
 * Time: 10:48 AM
 */
public class CourseWaitListAttributeEntity extends BaseAttributeEntity<CourseWaitListEntity> {

    @ManyToOne
    @JoinColumn(name = "OWNER_ID")
    private CourseWaitListEntity owner;

    public CourseWaitListAttributeEntity() {
        super();
    }

    public CourseWaitListAttributeEntity(Attribute att, CourseWaitListEntity owner) {
        super(att, owner);
    }

    public void setOwner(CourseWaitListEntity owner) {
        this.owner = owner;
    }

    public CourseWaitListEntity getOwner() {
        return owner;
    }
}
