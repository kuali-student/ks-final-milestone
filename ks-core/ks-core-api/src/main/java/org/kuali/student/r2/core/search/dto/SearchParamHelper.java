package org.kuali.student.r2.core.search.dto;

import java.util.ArrayList;
import java.util.List;


public class SearchParamHelper {

    /**
     * 
     * This method is only used to convert r2 SearchParamInfos to r1 SearchParams. Should be deleted once assembler 
     * is fixed.
     * 
     * @param infos
     * @return
     */
    @Deprecated
    public static List<org.kuali.student.r1.common.search.dto.SearchParam> toSearchParamInfos(List<SearchParamInfo> infos) {
        List<org.kuali.student.r1.common.search.dto.SearchParam> searchParams = new ArrayList<org.kuali.student.r1.common.search.dto.SearchParam>();
        for (SearchParamInfo paramInfo : infos){
            searchParams.add(toSearchParam(paramInfo));
        }
        return searchParams;
    }
    
    /**
     * 
     * This method is only used to convert r2 SearchParamInfo to r1 SearchParam. Should be deleted once assembler 
     * is fixed.
     * 
     * @param paramInfo
     * @return
     */
    @Deprecated
    private static org.kuali.student.r1.common.search.dto.SearchParam toSearchParam(SearchParamInfo paramInfo){
        org.kuali.student.r1.common.search.dto.SearchParam param = new org.kuali.student.r1.common.search.dto.SearchParam();
        param.setKey(paramInfo.getKey());
        try {
            param.setValue(paramInfo.getValues().get(0));
        } catch (ClassCastException e) {
            param.setValue(paramInfo.toString());
        }
        return param;
    }
}
