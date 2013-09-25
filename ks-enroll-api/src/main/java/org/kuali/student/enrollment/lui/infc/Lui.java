/*
 * Copyright 2011 The Kuali Foundation
 *
 * Licensed under the Educational Community License, Version 2.0 (the
 * "License"); you may not use this file except in compliance with the
 * License.  You may obtain a copy of the License at
 *
 * 	http://www.osedu.org/licenses/ECL-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
 * implied.  See the License for the specific language governing
 * permissions and limitations under the License.
 */

package org.kuali.student.enrollment.lui.infc;

import org.kuali.student.r2.common.infc.HasEffectiveDates;
import org.kuali.student.r2.common.infc.IdEntity;
import org.kuali.student.r2.lum.clu.infc.LuCode;

import java.util.List;

/**
 * Detailed information about a single LUI.
 */
public interface Lui 
    extends IdEntity, HasEffectiveDates {

    /**
     * The LUI identifier.
     *
     * @name: Lui Identifier
     */
    public LuiIdentifier getOfficialIdentifier();

    /**
     * The alternate LUI identifiers.
     *
     * @name: Lui Alternate Identifiers
     */
    public List<? extends LuiIdentifier> getAlternateIdentifiers();

    /**
     * The unique identifier for the Canonical Learning Unit
     * (CLU) of which this is an instance.
     *
     * @name Clu Id
     * @readonly
     * @required
     */
    public String getCluId();

    /**
     * Any Clu Clu Relation identifiers on which this instance is
     * based. While the LUI is in instance of a CLU, the LUI may be an
     * instance of a set of CLUs related to the principal CLU, such as
     * a Course Format.
     *
     * @name Clu Clu Relation Ids
     */
    public List<String> getCluCluRelationIds();

    /**
     * The unique identifier for the Academic Time Period (ATP)
     * for which this instance is offered.
     *
     * @name Atp Id
     * @readonly
     * @required
     */
    public String getAtpId();

    /**
     * Places where this LUI is offered. This is a temporary field
     * that parallels the campus enumeration in CLU. It may evolve
     * into a list of Campus Ids when we work that out.
     *
     * @name Campus Location Keys
     */
    public List<String> getCampusLocations();

    /**
     * The Schedule Ids
     *
     * @name Schedule Ids
     */
    public List<String> getScheduleIds();

    /**
     * List of LU code info structures. These are structures so that
     * many different types of codes can be associated with the
     * clu. This allows them to be put into categories.
     *
     * @name codes
     */
    public List<? extends LuCode> getLuiCodes();
 
    /**
     * The total maximum number of "seats" or enrollment slots that
     * can be filled for the lui.
     *
     * @name Maximum Enrollment
     */    
    public Integer getMaximumEnrollment();
    
    /**
     * Total minimum number of seats that must be filled for the lui.
     *
     * @name Minimum Enrollment
     */
    public Integer getMinimumEnrollment();

    /**
     * The reference URL for this LUI.
     *
     * @name referenceURL
     */
    public String getReferenceURL();

    /**
     * Identifiers for Organization(s) that is responsible for the delivery - and all
     * associated logistics - of the Lui.
     *
     * @name Units Deployment Org Id
     */
    public List<String> getUnitsDeployment();

    /**
     * Identifiers for Organization(s) that is responsible for the academic content of
     * the Lui as approved in its canonical form.
     *
     * @name Units Content Owner Org Id
     */
    public List<String> getUnitsContentOwner();

    /**
     * The options/scales that indicate the allowable grades that can
     * be awarded or credits applied.  
     * 
     * If the value is set here then the Clu must have a
     * grading option or credit options set on the canonical activity.
     * 
     * ResultValuesGroup will contain grade values valid for this
     * course offering.
     * 
     * @name: Result Values Group Ids
     */
    public List<String> getResultValuesGroupKeys();

    /**
     *  These are the related lui types which may be related to this lui, e.g., activty
     *  offering types to format offering
     *
     * @name Related Lui Types
     */
    public List<String> getRelatedLuiTypes();
}