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
 */
package org.kuali.student.r2.core.class1.search;

import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.StringUtils;
import org.kuali.student.r2.common.class1.search.SearchServiceAbstractHardwiredImpl;
import org.kuali.student.r2.common.dao.GenericEntityDao;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.util.RichTextHelper;
import org.kuali.student.r2.common.util.constants.LuiServiceConstants;
import org.kuali.student.r2.common.util.date.DateFormatters;
import org.kuali.student.r2.core.class1.type.dto.TypeInfo;
import org.kuali.student.r2.core.search.dto.SearchRequestInfo;
import org.kuali.student.r2.core.search.dto.SearchResultCellInfo;
import org.kuali.student.r2.core.search.dto.SearchResultInfo;
import org.kuali.student.r2.core.search.dto.SearchResultRowInfo;
import org.kuali.student.r2.core.search.util.SearchRequestHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: swedev
 * Date: 11/18/12
 * Time: 4:49 PM
 * To change this template use File | Settings | File Templates.
 */
public class CourseOfferingManagementSearchImpl extends SearchServiceAbstractHardwiredImpl {

    private GenericEntityDao genericEntityDao;

    public static final class SearchParameters {
        public static final String COURSE_CODE = "courseCode";
        public static final String SUBJECT_AREA = "subjectArea";
        public static final String ATP_ID = "atpId";
        public static final String CROSS_LIST_SEARCH_ENABLED = "crossListSearchEnabled";
    }

    public static final class SearchResultColumns {
        public static final String CODE = "courseOfferingCode";
        public static final String DESC = "courseOfferingDesc";
        public static final String STATE = "courseOfferingState";
        public static final String CREDIT_OPTION = "courseOfferingCreditOption";
        public static final String GRADING_OPTION = "courseOfferingGradingOption";
        public static final String CO_ID = "courseOfferingId";
        public static final String SUBJECT_AREA = "subjectArea";
        public static final String IS_CROSS_LISTED = "isCrossListedCode";
        public static final String CROSS_LISTED_COURSES = "crossListedCodes";
        
    }
    public static final TypeInfo CO_MANAGEMENT_SEARCH;

    public static final String CO_MANAGEMENT_SEARCH_KEY = "kuali.search.type.lui.courseOfferingManagementDisplay";

    static {
        TypeInfo info = new TypeInfo();
        info.setKey(CO_MANAGEMENT_SEARCH_KEY);
        info.setName("Manage Course Offering Search");
        info.setDescr(new RichTextHelper().fromPlain("Return search results from manage course offering screen quickly"));

        try {
            info.setEffectiveDate(DateFormatters.MONTH_DAY_YEAR_DATE_FORMATTER.parse("01/01/2012"));
        } catch ( IllegalArgumentException ex) {
            throw new RuntimeException("bad code");
        }
        CO_MANAGEMENT_SEARCH = info;
    }

    @Override
    public TypeInfo getSearchType() {
        return CO_MANAGEMENT_SEARCH;
    }

    /**
     * This method returns all the Course Offerings in a specific term and subject area and/or course code.
     * If <code>crossListSearchEnabled</code> flag is enabled, it returns all the related cross listed
     * courses (alternate identifiers) as well.
     *
     * <p>
     * For example, If ENGL100 has 2 cross listed courses HIST200,ART300. And, ENGL555 is a crosslisting
     * course of WMST100 in a term 20122. For a search like <i>atpId=20122 and courseCode=ENGL and crossListSearchEnabled=true</i>,
     * this method returns ENGL100 (with crosslisted courses HIST200 and ART300) and ENGL555 (with WMST100 as owner course).
     * </p>
     *
     * @param searchRequestInfo which has all the criteria needed for the search
     * @param contextInfo context Info
     * @return
     * @throws MissingParameterException if atp id and/or course code is blank
     * @throws OperationFailedException
     */
    @Override
    public SearchResultInfo search(SearchRequestInfo searchRequestInfo, ContextInfo contextInfo)
            throws MissingParameterException, OperationFailedException {

        if (!StringUtils.equals(searchRequestInfo.getSearchKey(), CO_MANAGEMENT_SEARCH.getKey())) {
            throw new OperationFailedException("Unsupported search type: " + searchRequestInfo.getSearchKey());
        }

        SearchRequestHelper requestHelper = new SearchRequestHelper(searchRequestInfo);

        String courseCode = requestHelper.getParamAsString(SearchParameters.COURSE_CODE);
        String subjectArea = requestHelper.getParamAsString(SearchParameters.SUBJECT_AREA);
        String atpId = requestHelper.getParamAsString(SearchParameters.ATP_ID);
        boolean enableCrossListSearch = BooleanUtils.toBoolean(requestHelper.getParamAsString(SearchParameters.CROSS_LIST_SEARCH_ENABLED));

        SearchResultInfo resultInfo = new SearchResultInfo();
        resultInfo.setStartAt(0);

        if (StringUtils.isBlank(atpId)){
            throw new MissingParameterException("Term code is required to search course offerings");
        }

        if (StringUtils.isBlank(courseCode) && StringUtils.isBlank(subjectArea)){
            throw new MissingParameterException("Either Course code or subject area must be set to search course offerings");
        }

        String query = "SELECT" +
                        "    ident.code," +
                        "    ident.longName," +
                        "    lui.luiState," +
                        "    lui_rvg1," +
                        "    lui_rvg2,        " +
                        "    lui.id,        " +
                        "    ident.division,        " +
                        "    ident.type  " +
                        "FROM" +
                        "    LuiIdentifierEntity ident," +
                        "    LuiEntity lui," +
                        "    IN(lui.resultValuesGroupKeys) lui_rvg1," +
                        "    ResultValuesGroupEntity lrc_rvg1,    " +
                        "    IN(lui.resultValuesGroupKeys) lui_rvg2," +
                        "    ResultValuesGroupEntity lrc_rvg2  " +
                        "WHERE" +
                        "    lui.id = ident.lui.id" +
                        "    AND lui.atpId = '" + atpId + "' " +
                        "    AND lrc_rvg1.id = lui_rvg1" +
                        "    AND lrc_rvg1.resultScaleId LIKE 'kuali.result.scale.credit.%'    " +
                        "    AND lrc_rvg2.id = lui_rvg2" +
                        "    AND lrc_rvg2.resultScaleId LIKE 'kuali.result.scale.grade.%'" +
                        //Exclude these two types that can cause duplicates.
                        // audit and passfail are moved into different fields, after that there can be only one grading option
                        // of Satisfactory, Letter, or Percentage
                        "    AND lrc_rvg2 NOT IN ('kuali.resultComponent.grade.audit','kuali.resultComponent.grade.passFail') ";

        if (StringUtils.isNotBlank(subjectArea)){
            query = query + " AND ident.division='" + subjectArea + "'  ";
        }

        if (StringUtils.isNotBlank(courseCode)){
            query = query + "  AND ident.code like '" + courseCode + "%' ";
        }

        /**
         * Search for the course offerings first for a term and subjectarea/coursecode
         */
        List<Object[]> results = genericEntityDao.getEm().createQuery(query).getResultList();

        resultInfo.setTotalResults(results.size());
        resultInfo.setStartAt(0);

        List<String> luiCode = new ArrayList<String>();
        Map<String,List<SearchResultRowInfo>> luiIds2ResultRow = new HashMap<String, List<SearchResultRowInfo>>();

        for (Object[] result : results) {
            SearchResultRowInfo row = new SearchResultRowInfo();
            resultInfo.getRows().add(row);
            int i=0;
            String coCode = (String)result[i++];
            row.addCell(SearchResultColumns.CODE,coCode);
            row.addCell(SearchResultColumns.DESC,(String)result[i++]);
            row.addCell(SearchResultColumns.STATE,(String)result[i++]);
            row.addCell(SearchResultColumns.CREDIT_OPTION,(String)result[i++]);
            row.addCell(SearchResultColumns.GRADING_OPTION,(String)result[i++]);
            String courseOfferingId = (String)result[i++];
            row.addCell(SearchResultColumns.CO_ID,courseOfferingId);
            row.addCell(SearchResultColumns.SUBJECT_AREA,(String)result[i++]);

            if (enableCrossListSearch){
                /**
                 * For cross list search, hold all the luis in a map for faster retrieval later when we search for the
                 * official/crosslisted courses. As there may be same lui exists for multiple courses, group together
                 * all those in a list.
                 */
                List<SearchResultRowInfo> matchingRowList = luiIds2ResultRow.get(courseOfferingId);
                if (matchingRowList == null){
                    matchingRowList = new ArrayList<SearchResultRowInfo>();
                    luiIds2ResultRow.put(courseOfferingId,matchingRowList);
                }
                matchingRowList.add(row);
                luiCode.add(coCode);
                String luiIdentifierType = (String)result[i++];
                boolean isCrossListed = false;
                if (StringUtils.equals(luiIdentifierType,LuiServiceConstants.LUI_IDENTIFIER_CROSSLISTED_TYPE_KEY)){
                    isCrossListed = true;
                }
                row.addCell(SearchResultColumns.IS_CROSS_LISTED,"" + isCrossListed);
            }

        }

        /**
         * Search for the crosslisted/official course codes for the already fetched luis
         */
        if (!luiIds2ResultRow.isEmpty()){
            //Get all the other related lui identifiers for the already fetched luis (excluding the already fetched lui codes)
            List<String> luiIds = new ArrayList<String>(luiIds2ResultRow.keySet());
            query = "SELECT ident.lui.id,ident.code,ident.type FROM LuiIdentifierEntity ident where ident.lui.id IN (" + commaString(luiIds) + ") " +
                                    " AND ident.code NOT IN (" + commaString(luiCode) + ") ORDER BY ident.code asc , ident.type desc";

            results = genericEntityDao.getEm().createQuery(query, Object[].class).getResultList();

            for (Object[] result : results) {
                int i=0;
                String luiId = (String)result[i++];
                String luiAlternateCode = (String)result[i++];
                String luiIdentifierType = (String)result[i++];

                List<SearchResultRowInfo> matchingRows = luiIds2ResultRow.get(luiId);
                SearchResultCellInfo cell;

                for (SearchResultRowInfo row : matchingRows){
                    //This cell size check is to make sure the column already exists. If not, create one.
                    if (row.getCells().size() == 8){
                        row.addCell(SearchResultColumns.CROSS_LISTED_COURSES,getAlternateCodeUI(luiAlternateCode,luiIdentifierType));
                    } else {
                        cell = row.getCells().get(8);

                        String cellData = cell.getValue();
                        if (StringUtils.isNotBlank(cellData)){
                            cellData = cellData + "," + getAlternateCodeUI(luiAlternateCode,luiIdentifierType);
                        } else {
                            cellData = getAlternateCodeUI(luiAlternateCode,luiIdentifierType);
                        }
                        cell.setValue(cellData);
                    }
                }
            }
        }

        luiIds2ResultRow.clear();

        resultInfo.setStartAt(0);
        return resultInfo;
    }

    /**
     * Append <code>"Owner"</code> if a lui identifier is of official type. This is used for display purpose.
     *
     * @param luiAlternateCode
     * @param luiIdentifierType
     * @return
     */
    protected String getAlternateCodeUI(String luiAlternateCode,String luiIdentifierType){
        if (StringUtils.equals(luiIdentifierType,LuiServiceConstants.LUI_IDENTIFIER_OFFICIAL_TYPE_KEY)){
            return luiAlternateCode + " (Owner)";
        } else {
            return luiAlternateCode;
        }
    }

    private static String commaString(List<String> items){
        StringBuilder sb = new StringBuilder();
        String delim = "";
        for (String str : items) {
            sb.append(delim).append("'" + str + "'");
            delim = ",";
        }
        return sb.toString();
    }

    public void setGenericEntityDao(GenericEntityDao genericEntityDao) {
        this.genericEntityDao = genericEntityDao;
    }


}
