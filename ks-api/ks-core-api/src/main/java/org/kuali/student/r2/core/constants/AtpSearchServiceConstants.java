/**
 * Copyright 2014 The Kuali Foundation
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

package org.kuali.student.r2.core.constants;

/**
 * ATP Search Service constants
 *
 * @author Kuali Student Team (ks.collab@kuali.org)
 */
public class AtpSearchServiceConstants {
    // season types
    public static final String ATP_SEARCH_SEASONTYPES = "atp.search.atpSeasonTypes";
    public static final String ATP_QUERYPARAM_SEASON_TYPE = "atp.queryParam.atpSeasonType";
    public static final String ATP_RESULTCOLUMN_SEASONTYPE_ID = "atp.resultColumn.atpSeasonType";
    public static final String ATP_RESULTCOLUMN_SEASONTYPE_NAME = "atp.resultColumn.atpSeasonTypeName";
    public static final String ATP_RESULTCOLUM_SEASONTYPE_DESC = "atp.resultColumn.atpSeasonTypeDesc";

    //milesstone types
    public static final String ATP_SEARCH_MILESTONE_IDS_BY_ATP_ID = "milestone.search.milestoneIdsByAtpId";
    public static final String ATP_QUERYPARAM_MILESTONE_TYPES = "milestone.queryParam.milestoneTypes";
    public static final String ATP_QUERYPARAM_MILESTONE_ATP_ID = "milestone.queryParam.atpId";

    //related atps
    public static final String ATP_SEARCH_RELATED_ATP_IDS_BY_ATP_ID = "atp.search.relatedAtpIdsByAtpId";
    public static final String ATP_QUERYPARAM_PARENT_ATP_ID = "atp.queryParam.parentAtpId";
    public static final String ATP_QUERYPARAM_RELATION_TYPE = "atp.queryParam.relationType";
    public static final String ATP_QUERYPARAM_RELATED_ATP_TYPES = "atp.queryParam.relatedAtpTypes";
    public static final String ATP_RESULTCOLUMN_RELATED_ATP_ID = "atp.resultColumn.relatedAtpId";

    //duration types
    public static String ATP_SEARCH_DURATIONTYPES = "atp.search.atpDurationTypes";
    public static String ATP_QUERYPARAM_DURATIONTYPE_ID = "atp.queryParam.atpDurationType";
    public static String ATP_RESULTCOLUMN_DURATIONTYPE_ID = "atp.resultColumn.atpDurType";
    public static String ATP_RESULTCOLUMN_DURATIONTYPE_NAME = "atp.resultColumn.atpDurTypeName";
    public static String ATP_RESULTCOLUM_DURATIONTYPE_DESC = "atp.resultColumn.atpDurTypeDesc";

    //advanced search
    public static final String ATP_SEARCH_ADVANCED = "atp.search.advancedAtpSearch";
    public static final String ATP_ADVANCED_QUERYPARAM_ATP_ID = "atp.advancedAtpSearchParam.atpId";
    public static final String ATP_ADVANCED_QUERYPARAM_ATP_TYPE = "atp.advancedAtpSearchParam.atpType";
    public static final String ATP_ADVANCED_QUERYPARAM_ATP_YEAR = "atp.advancedAtpSearchParam.atpYear";
    public static final String ATP_ADVANCED_QUERYPARAM_ATP_SHORT_NAME = "atp.advancedAtpSearchParam.atpShortName";
    public static final String ATP_ADVANCED_QUERYPARAM_ATP_END_DATE_CONSTRAINT_EXCLUSIVE = "atp.advancedAtpSearchParam.atpEndDateAtpConstraintIdExclusive";
    public static final String ATP_ADVANCED_QUERYPARAM_ATP_START_DATE_AFTER_END_DATE_CONSTRAINT_EXCLUSIVE = "atp.advancedAtpSearchParam.atpStartDateAfterEndDateAtpConstraintIdExclusive";
    public static final String ATP_ADVANCED_QUERYPARAM_ATP_END_DATE_BEFORE_START_DATE_CONSTRAINT_EXCLUSIVE = "atp.advancedAtpSearchParam.atpEndDateBeforeStartDateAtpConstraintIdExclusive";
    public static final String ATP_ADVANCED_ATP_SEARCH_PARAM_OPTIONAL_ATP_IDS = "atp.advancedAtpSearchParam.optionalAtpIds";

    public static final String ATP_RESULTCOLUMN_ATP_ID = "atp.resultColumn.atpId";
    public static final String ATP_RESULTCOLUMN_ATP_SHORT_NAME = "atp.resultColumn.atpShortName";
    public static final String ATP_RESULTCOLUMN_ATP_DESCR_PLAIN = "atp.resultColumn.atpDescrPlain";
    public static final String ATP_RESULTCOLUMN_ATP_START_DATE = "atp.resultColumn.atpStartDate";
    public static final String ATP_RESULTCOLUMN_ATP_END_DATE = "atp.resultColumn.atpEndDate";
    public static final String ATP_RESULTCOLUMN_ATP_STATE = "atp.resultColumn.atpState";
}
