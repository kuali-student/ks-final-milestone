package org.kuali.student.r2.core.search.dto;

import java.util.ArrayList;
import java.util.List;

import org.kuali.student.r1.common.search.dto.SearchParam;


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
    public static List<SearchParam> toSearchParams(List<SearchParamInfo> infos) {
        List<SearchParam> searchParams = new ArrayList<SearchParam>();
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
    private static SearchParam toSearchParam(SearchParamInfo paramInfo){
        SearchParam param = new SearchParam();
        param.setKey(paramInfo.getKey());
        try {
            param.setValue(paramInfo.getValues().get(0));
        } catch (ClassCastException e) {
            param.setValue(paramInfo.toString());
        }
        return param;
    }
    
    /**
     * 
     * This method is used to convert r1 SearchParams to r2 SearchParamInfos. Since Dozer
     * can't successfully convert SearchParam/SearchParamInfos, this method might need to
     * be called to do that section manually.
     * 
     * @param searchParams
     * @return
     */
    public static List<SearchParamInfo> toSearchParamInfos(List<SearchParam> searchParams) {
        List<SearchParamInfo> searchParamInfos = new ArrayList<SearchParamInfo>();
        for (SearchParam paramInfo : searchParams) {
            searchParamInfos.add(toSearchParamInfo(paramInfo));
        }
        return searchParamInfos;
    }

    /**
     * 
     * This method is used to convert r1 SearchParam to r2 SearchParamInfo. Since Dozer
     * can't successfully convert SearchParam/SearhParamInfo, this method might need to be
     * called to handle that section manually.
     * 
     * @param param
     * @return
     */
    private static SearchParamInfo toSearchParamInfo(SearchParam param) {
        SearchParamInfo paramInfo = new SearchParamInfo();
        paramInfo.setKey(param.getKey());
        if (param.getValue() instanceof List) {
            paramInfo.setValues((List<String>) param.getValue());
        } else {
            List<String> listValues = new ArrayList<String>();
            listValues.add((String) param.getValue());
            paramInfo.setValues(listValues);
        }
        return paramInfo;
    }
    
}
