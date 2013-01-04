package org.kuali.student.lum.common.client.widgets;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.kuali.student.r1.common.assembly.data.Data;
import org.kuali.student.r1.common.search.dto.SearchParam;
import org.kuali.student.r1.common.search.dto.SearchRequest;
import org.kuali.student.r2.core.search.dto.SearchParamInfo;
import org.kuali.student.r2.lum.clu.dto.MembershipQueryInfo;

public class CluSetRangeModelUtil {
    
    public static CluSetRangeModelUtil INSTANCE = new CluSetRangeModelUtil();
    
    private CluSetRangeModelUtil() {
    }
    
    public Data toData(SearchRequest searchRequest, String searchRequestId) {
        if (searchRequest == null) {
            return null;
        }
        List<SearchParam> searchParams = searchRequest.getParams();
        CluSetRangeHelper cluSetRangeHelper = CluSetRangeHelper.wrap(new Data());
        cluSetRangeHelper.setId(searchRequestId);
        cluSetRangeHelper.setSearchTypeKey(searchRequest.getSearchKey());
        if (searchParams != null) {
            for (SearchParam searchParam : searchParams) {
                QueryParamHelper queryParamHelper = QueryParamHelper.wrap(new Data());
                queryParamHelper.setValue(null);
                queryParamHelper.setListValue(null);
                queryParamHelper.setKey(searchParam.getKey());

                
                if (searchParam.getValue() != null) {
                	if (searchParam.getValue().getClass().equals(String.class)) {                		
                		queryParamHelper.setValue((String)searchParam.getValue());
                		if (cluSetRangeHelper.getQueryParams() == null) {
                            cluSetRangeHelper.setQueryParams(new Data());
                        }
                    	cluSetRangeHelper.getQueryParams().add(queryParamHelper.getData());
                	}
                }                
                
                
                    

            }
        }
        
        return cluSetRangeHelper.getData();
    }
    
    public Data toData(MembershipQueryInfo membershipQueryInfo) {
        if (membershipQueryInfo == null) {
            return null;
        }
        List<SearchParamInfo> searchParams = membershipQueryInfo.getQueryParamValues();
        CluSetRangeHelper cluSetRangeHelper = CluSetRangeHelper.wrap(new Data());
        cluSetRangeHelper.setId(membershipQueryInfo.getId());
        cluSetRangeHelper.setSearchTypeKey(membershipQueryInfo.getSearchTypeKey());
        if (searchParams != null) {
            for (SearchParamInfo searchParam : searchParams) {
                QueryParamHelper queryParamHelper = QueryParamHelper.wrap(new Data());
                queryParamHelper.setValue(searchParam.getValues().get(0));
                queryParamHelper.setListValue(null);
                queryParamHelper.setKey(searchParam.getKey());
                if (cluSetRangeHelper.getQueryParams() == null) {
                    cluSetRangeHelper.setQueryParams(new Data());
                }
                cluSetRangeHelper.getQueryParams().add(queryParamHelper.getData());
            }
        }
        
        return cluSetRangeHelper.getData();
    }
    
//    public SearchRequest toSearchRequest(Data data) {
//        SearchRequest searchRequest = null;
//        if (data != null) {
//            CluSetRangeHelper cluSetRangeHelper = CluSetRangeHelper.wrap(data);
//            Data queryParamsData = cluSetRangeHelper.getQueryParams();
//            searchRequest = new SearchRequest();
//            searchRequest.setSearchKey(cluSetRangeHelper.getSearchTypeKey());
//            for (Data.Property p : queryParamsData) {
//                QueryParamHelper queryParamHelper = QueryParamHelper.wrap((Data)p.getValue());
//                SearchParam searchParam = new SearchParam();
//                searchParam.setKey(queryParamHelper.getKey());
//                searchParam.setValue(queryParamHelper.getValue());
//                if (searchRequest.getParams() == null) {
//                    searchRequest.setParams(new ArrayList<SearchParam>());
//                }
//                searchRequest.getParams().add(searchParam);
//            }
//        }
//        return searchRequest;
//    }
    
    public MembershipQueryInfo toMembershipQueryInfo(Data data) {
        MembershipQueryInfo membershipQueryInfo = null;
        if (data != null) {
            CluSetRangeHelper cluSetRangeHelper = CluSetRangeHelper.wrap(data);
            Data queryParamsData = cluSetRangeHelper.getQueryParams();
            membershipQueryInfo = new MembershipQueryInfo();
            membershipQueryInfo.setId(cluSetRangeHelper.getId());
            membershipQueryInfo.setSearchTypeKey(cluSetRangeHelper.getSearchTypeKey());
            // make sure the membershipQueryInfo has some contents in it.
            // Return null if otherwise.
            if (membershipQueryInfo.getSearchTypeKey() == null ||
                    membershipQueryInfo.getSearchTypeKey().trim().isEmpty()) {
                return null;
            }
            for (Data.Property p : queryParamsData) {
                QueryParamHelper queryParamHelper = QueryParamHelper.wrap((Data)p.getValue());
                SearchParamInfo searchParam = new SearchParamInfo();
                searchParam.setKey(queryParamHelper.getKey());
                searchParam.setValues(Arrays.asList(queryParamHelper.getValue()));
                if (membershipQueryInfo.getQueryParamValues() == null) {
                    membershipQueryInfo.setQueryParamValues(new ArrayList<SearchParamInfo>());
                }
                membershipQueryInfo.getQueryParamValues().add(searchParam);
            }
        }
        return membershipQueryInfo;
    }

}
