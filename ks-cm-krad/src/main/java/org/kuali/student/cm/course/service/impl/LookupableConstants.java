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
    
    /*
     * Parameters for the search
     */
    public static final String OPTIONAL_LONGNAME_PARAM = "lu.queryParam.luOptionalLongName";
    public static final String OPTIONAL_CODE_PARAM = "lu.queryParam.luOptionalCode";
    public static final String OPTIONAL_DESCR_PARAM = "lu.queryParam.luOptionalDescr";
    public static final String OPTIONAL_TYPE_PARAM = "lu.queryParam.luOptionalType";
    public static final String OPTIONAL_STATE_PARAM = "lu.queryParam.luOptionalState";
    public static final String SUBJECTCODE_CODE_PARAM =  "subjectCode.queryParam.code";
    
    /*
     * Types for lu
     */
    public static final String CREDITCOURSE_lU ="kuali.lu.type.CreditCourse";
    
    
    /*
     * Results constants
     */
    public static final String ID_RESULT = "lu.resultColumn.cluId";
    public static final String LONGNAME_OPTIONAL_RESULT = "lu.resultColumn.luOptionalLongName";
    public static final String OPTIONALCODE_RESULT = "lu.resultColumn.luOptionalCode";
    public static final String DESCR_RESULT = "lu.resultColumn.luOptionalDescr";
    public static final String SUBJECTCODE_CODE_RESULT = "subjectCode.resultColumn.code";
    public static final String SUBJECTCODE_ID_RESULT = "subjectCode.resultColumn.id";

}
