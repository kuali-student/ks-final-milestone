/*
 * Copyright 2010 The Kuali Foundation 
 *
 * Licensed under the Educational Community License, Version 2.0 (the
 * "License"); you may not use this file except in compliance with the
 * License. You may obtain a copy of the License at
 *
 * http://www.osedu.org/licenses/ECL-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
 * implied. See the License for the specific language governing
 * permissions and limitations under the License.
 */
package org.kuali.student.enrollment.courseoffering.infc;

import java.util.List;

import org.kuali.student.r2.common.infc.IdEntity;

/**
 * A Format Offering specifies the allowable Activity Offering Types
 * that may be offered.
 *
 * @author tom
 */
public interface FormatOffering
        extends IdEntity {

    /**
     * The Course Offering Id to which this Format Offering belongs.
     *
     * @name Course Offering Id
     * @required
     * @readOnly
     */
    public String getCourseOfferingId();

    /**
     * Canonical Format this Format Offering corresponds
     * to. Currently, this is optional and Format Offerings may not
     * directly map to any canonical Format.
     *
     * @name Format Id
     */
    public String getFormatId();

    /**
     * Academic term the format is being offered in. 
     * 
     * Same as course offering term or a nested term of course
     * offering.
     * 
     * @name Term Id
     * @required
     * @readonly
     * @impl map to Lui.getAtpId
     */
    public String getTermId();

    /**
     * The short name for this Format Offering, such as LC or LC/LB.
     *
     * @name Short Name
     */
    public String getShortName();

    /**
     * Gets a list of Activity Offering Types within this Format
     * Offering.
     *
     * @name Activity Offering Types
     */
    public List<String> getActivityOfferingTypeKeys();

    /**
     * Key indicating the level at which grade rosters should be generated -
     * activity, format or course. TODO: define these types. TODO: add a service
     * method to get the list of types that can be put in this field.
     *
     * @name Grade Roster Level Key
     * @impl this should be a constrained the a list types generated from the
     *       roster types from the generic type system.
     */
    public String getGradeRosterLevelTypeKey();


    /**
     * Indicates what activity type does the final exam exist in
     *
     * @name  Final Exam Level Type Key
     */
    public String getFinalExamLevelTypeKey();
}
