package org.kuali.student.lum.lu.ui.krms.util;

import org.kuali.student.lum.lu.ui.krms.dto.CluInformation;
import org.kuali.student.r2.common.dto.DtoConstants;
import org.kuali.student.r2.core.search.dto.SearchParamInfo;
import org.kuali.student.r2.core.search.dto.SearchResultCellInfo;
import org.kuali.student.r2.core.search.dto.SearchResultInfo;
import org.kuali.student.r2.core.search.dto.SearchResultRowInfo;
import org.kuali.student.r2.lum.util.constants.CluServiceConstants;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: SW Genis
 * Date: 2013/11/08
 * Time: 9:49 AM
 * To change this template use File | Settings | File Templates.
 */
public class CluSearchUtil {

    public static SearchParamInfo createSearchParam(String key, String value){
        SearchParamInfo searchParam = new SearchParamInfo();
        searchParam.setKey(key);
        searchParam.getValues().add(value);
        return searchParam;
    }

    public static SearchParamInfo createSearchParam(String key, List<String> values){
        SearchParamInfo searchParam = new SearchParamInfo();
        searchParam.setKey(key);
        for(String value : values){
            searchParam.getValues().add(value);
        }
        return searchParam;
    }

    public static SearchParamInfo getApprovedStateSearchParam() {
        return createSearchParam("lu.queryParam.luOptionalState", getApprovedStatesForClus());
    }

    public static List<String> getApprovedStatesForClus() {
        List<String> states = new ArrayList<String>();
        states.add(DtoConstants.STATE_APPROVED);
        states.add(DtoConstants.STATE_ACTIVE);
        states.add(DtoConstants.STATE_RETIRED);
        states.add(DtoConstants.STATE_SUSPENDED);
        return states;
    }

    public static SearchParamInfo getTypeSearchParamForCourse() {
        return createSearchParam("lu.queryParam.luOptionalType", getCluTypesForCourse());
    }

    public static List<String> getCluTypesForCourse(){
        List<String> types = new ArrayList<String>();
        types.add(CluServiceConstants.CREDIT_COURSE_LU_TYPE_KEY);
        return types;
    }

    public static SearchParamInfo getTypeSearchParamForProgram() {
        return createSearchParam("lu.queryParam.luOptionalType", getCluTypesForProgram());
    }

    public static List<String> getCluTypesForProgram(){
        List<String> types = new ArrayList<String>();
        types.add("kuali.lu.type.credential.Baccalaureate");
        types.add("kuali.lu.type.credential.Masters");
        types.add("kuali.lu.type.credential.Professional");
        types.add("kuali.lu.type.credential.Doctoral");
        types.add("kuali.lu.type.credential.UndergraduateCertificate");
        types.add("kuali.lu.type.credential.GraduateCertificate");
        types.add("kuali.lu.type.credential.ContinuingEd");
        types.add("kuali.lu.type.MajorDiscipline");
        types.add("kuali.lu.type.Variation");
        types.add("kuali.lu.type.MinorDiscipline");
        types.add("kuali.lu.type.CoreProgram");
        types.add("kuali.lu.type.Honors");
        types.add("kuali.lu.type.LivingLearning");
        return types;
    }

    public static List<CluInformation> resolveCluSearchResultSet(SearchResultInfo searchResult) {
        List<CluInformation> clus = new ArrayList<CluInformation>();
        List<SearchResultRowInfo> rows = searchResult.getRows();
        for (SearchResultRowInfo row : rows) {
            List<SearchResultCellInfo> cells = row.getCells();
            CluInformation cluInformation = new CluInformation();
            for (SearchResultCellInfo cell : cells) {
                if (cell.getKey().equals("lu.resultColumn.cluId")) {
                    cluInformation.setCluId(cell.getValue());
                } else if (cell.getKey().equals("lu.resultColumn.luOptionalCode")) {
                    cluInformation.setCode(cell.getValue());
                } else if (cell.getKey().equals("lu.resultColumn.luOptionalLongName")) {
                    cluInformation.setTitle(cell.getValue());
                } else if (cell.getKey().equals("lu.resultColumn.luOptionalDescr")) {
                    cluInformation.setDescription(cell.getValue());
                } else if (cell.getKey().equals("lu.resultColumn.luOptionalState")) {
                    cluInformation.setState(cell.getValue());
                } else if (cell.getKey().equals("lu.resultColumn.luOptionalVersionIndId")) {
                    cluInformation.setVerIndependentId(cell.getValue());
                } else if (cell.getKey().equals("lu.resultColumn.luOptionalShortName")) {
                    cluInformation.setShortName(cell.getValue());
                }
            }
            clus.add(cluInformation);
        }
        return clus;
    }
}
