/**
 * Copyright 2005-2013 The Kuali Foundation Licensed under the
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
 */
package org.kuali.student.cm.course.service.impl;

import org.kuali.student.r2.common.constants.CommonServiceConstants;


/**
 * 
 * TODO KSCM 950
 * 
 * Temp constants class to hold hardcoded strings
 * 
 * @author OpenCollab/rSmart KRAD CM Conversion Alliance!
 */
public class LookupableConstants {
    
    /*
     * Constants needed to search for personSearch
     */
    public static final String NAMESPACE_PERSONSEACH = CommonServiceConstants.REF_OBJECT_URI_GLOBAL_PREFIX + "personsearch";
    public static final String PERSONSEACH_SERVICE_NAME_LOCAL_PART = "PersonSearchService";
    
    /*
     * Constants needed to search for subjectCode
     */
    public static final String NAMESPACE_SUBJECTCODE = CommonServiceConstants.REF_OBJECT_URI_GLOBAL_PREFIX + "subjectCode";
    
    /*
     * Search constants.
     */
    public static final String SEARCHBY_SEARCH = "searchBy";
    public static final String COURSETITLE_SEARCH = "courseTitle";
    public static final String COURSECODE_SEARCH = "courseCode";
    public static final String DESCR_PLAIN_SEARCH ="descr.plain";
    public static final String GENERIC_LU_SEARCH = "lu.search.generic";
    public static final String MOSTCURRENT_UNION_SEARCH = "lu.search.mostCurrent.union";
    public static final String CURRENT_QUICK_SEARCH = "lu.search.current.quick";
    public static final String SUBJECTCODE_GENERIC_SEARCH =  "subjectCode.search.subjectCodeGeneric";
    public static final String LOCATEGORY_SEARCH = "lo.search.loCategories";
    public static final String LO_BY_CATEGORY_CLU_CROSS_SEARCH = "lo.search.loByCategoryCluCrossSearch";
    
    /*
     * Parameters for the search
     */
    public static final String OPTIONAL_LONGNAME_PARAM = "lu.queryParam.luOptionalLongName";
    public static final String OPTIONAL_CODE_PARAM = "lu.queryParam.luOptionalCode";
    public static final String OPTIONAL_DESCR_PARAM = "lu.queryParam.luOptionalDescr";
    public static final String OPTIONAL_TYPE_PARAM = "lu.queryParam.luOptionalType";
    public static final String OPTIONAL_STATE_PARAM = "lu.queryParam.luOptionalState";
    public static final String SUBJECTCODE_CODE_PARAM =  "subjectCode.queryParam.code";
    public static final String OPTIONAL_LO_CATEGORY_NAME_PARAM = "lo.queryParam.loOptionalCategoryName";
    public static final String LO_DESC_PLAIN_PARAM = "lo.queryParam.loDescPlain";
    public static final String OPTIONAL_LU_OPTIONAL_ADMIN_ORG_IDS_PARAM = "lu.queryParam.luOptionalAdminOrgIds";
    public static final String OPTIONAL_LU_OPTIONAL_ADMIN_ORG_TYPES_PARAM = "lu.queryParam.luOptionalAdminOrgTypes";
    
    /*
     * Types for lu
     */
    public static final String CREDITCOURSE_LU ="kuali.lu.type.CreditCourse";
    
    
    /*
     * Results constants
     */
    public static final String ID_RESULT = "lu.resultColumn.cluId";
    public static final String LONGNAME_OPTIONAL_RESULT = "lu.resultColumn.luOptionalLongName";
    public static final String OPTIONALCODE_RESULT = "lu.resultColumn.luOptionalCode";
    public static final String DESCR_RESULT = "lu.resultColumn.luOptionalDescr";
    public static final String SUBJECTCODE_CODE_RESULT = "subjectCode.resultColumn.code";
    public static final String SUBJECTCODE_ID_RESULT = "subjectCode.resultColumn.id";
    public static final String LO_CATEGORY_NAME_RESULT = "lo.resultColumn.categoryName";
    public static final String LO_CATEGORY_ID_RESULT = "lo.resultColumn.categoryId";
    public static final String LO_CATEGORY_TYPE_RESULT = "lo.resultColumn.categoryType";
    public static final String LO_CATEGORY_TYPE_NAME_RESULT = "lo.resultColumn.categoryTypeName";
    public static final String LO_CATEGORY_STATE_RESULT = "lo.resultColumn.categoryState";
    public static final String LO_CATEGORY_NAME_AND_TYPE_RESULT = "lo.resultColumn.categoryNameAndType";
    public static final String LU_CLU_OFFICIAL_IDENTIFIER_CLU_CODE_RESULT = "lu.resultColumn.cluOfficialIdentifier.cluCode";
    public static final String LO_CLU_CODE_RESULT = "lo.resultColumn.loCluCode";
    public static final String LO_CLU_TYPE_RESULT = "lo.resultColumn.loCluType";
    public static final String LO_CLU_STATE_RESULT = "lo.resultColumn.loCluState";
    public static final String LO_DESC_PLAIN_RESULT = "lo.resultColumn.loDescPlain";

}
