package org.kuali.student.lum.common.client.widgets;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.kuali.student.r1.common.assembly.data.Data;
import org.kuali.student.r2.core.search.dto.SearchParamInfo;
import org.kuali.student.r2.core.search.dto.SearchRequestInfo;
import org.kuali.student.r2.lum.clu.dto.MembershipQueryInfo;

public class CluSetRangeModelUtil {
    
    public static CluSetRangeModelUtil INSTANCE = new CluSetRangeModelUtil();
    
    private CluSetRangeModelUtil() {
    }
    
    public Data toData(SearchRequestInfo searchRequest, String searchRequestId) {
        if (searchRequest == null) {
            return null;
        }
        List<SearchParamInfo> searchParams = searchRequest.getParams();
        CluSetRangeHelper cluSetRangeHelper = CluSetRangeHelper.wrap(new Data());
        cluSetRangeHelper.setId(searchRequestId);
        cluSetRangeHelper.setSearchTypeKey(searchRequest.getSearchKey());
        if (searchParams != null) {
            for (SearchParamInfo searchParam : searchParams) {
                QueryParamHelper queryParamHelper = QueryParamHelper.wrap(new Data());
                queryParamHelper.setValue(null);
                queryParamHelper.setListValue(null);
                queryParamHelper.setKey(searchParam.getKey());

                
                if (searchParam.getValues().get(0) != null) {
                	if (searchParam.getValues().get(0).getClass().equals(String.class)) {
                		queryParamHelper.setValue((String)searchParam.getValues().get(0));
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
        List<org.kuali.student.r2.core.search.dto.SearchParamInfo> searchParams = membershipQueryInfo.getQueryParamValues();
        CluSetRangeHelper cluSetRangeHelper = CluSetRangeHelper.wrap(new Data());
        cluSetRangeHelper.setId(membershipQueryInfo.getId());
        cluSetRangeHelper.setSearchTypeKey(membershipQueryInfo.getSearchTypeKey());
        if (searchParams != null) {
            for (org.kuali.student.r2.core.search.dto.SearchParamInfo searchParam : searchParams) {
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
    
//    public SearchRequestInfo toSearchRequest(Data data) {
//        SearchRequestInfo searchRequest = null;
//        if (data != null) {
//            CluSetRangeHelper cluSetRangeHelper = CluSetRangeHelper.wrap(data);
//            Data queryParamsData = cluSetRangeHelper.getQueryParams();
//            searchRequest = new SearchRequestInfo();
//            searchRequest.setSearchKey(cluSetRangeHelper.getSearchTypeKey());
//            for (Data.Property p : queryParamsData) {
//                QueryParamHelper queryParamHelper = QueryParamHelper.wrap((Data)p.getValue());
//                SearchParamInfo searchParam = new SearchParamInfo();
//                searchParam.setKey(queryParamHelper.getKey());
//                searchParam.setValue(queryParamHelper.getValue());
//                if (searchRequest.getParams() == null) {
//                    searchRequest.setParams(new ArrayList<SearchParamInfo>());
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
                org.kuali.student.r2.core.search.dto.SearchParamInfo searchParam = new org.kuali.student.r2.core.search.dto.SearchParamInfo();
                searchParam.setKey(queryParamHelper.getKey());
                searchParam.setValues(Arrays.asList(queryParamHelper.getValue()));
                if (membershipQueryInfo.getQueryParamValues() == null) {
                    membershipQueryInfo.setQueryParamValues(new ArrayList<org.kuali.student.r2.core.search.dto.SearchParamInfo>());
                }
                membershipQueryInfo.getQueryParamValues().add(searchParam);
            }
        }
        return membershipQueryInfo;
    }

}
