package org.kuali.student.lum.common.client.widgets;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
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


                if (searchParam.getValues() != null) {

                    if (searchParam.getValues().size() == 1) {
                        queryParamHelper.setValue(searchParam.getValues().get(0));
                    } else {
                        for (String param : searchParam.getValues()) {
                            queryParamHelper.getListValue().add(param);
                        }
                    }
                    if (cluSetRangeHelper.getQueryParams() == null) {
                        cluSetRangeHelper.setQueryParams(new Data());
                    }
                    cluSetRangeHelper.getQueryParams().add(queryParamHelper.getData());
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
            for (SearchParamInfo searchParam : searchParams) {
                QueryParamHelper queryParamHelper = QueryParamHelper.wrap(new Data());
                if(searchParam.getValues() != null) {
                    if( searchParam.getValues().size() == 1){
                        queryParamHelper.setValue(searchParam.getValues().get(0));
                    }
                    else {
                        for(String param : searchParam.getValues()){
                            queryParamHelper.getListValue().add(param);
                        }
                    }
                }
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
                if (queryParamHelper.getValue() != null) {
                    searchParam.setValues(Arrays.asList(queryParamHelper.getValue()));
                }
                else
                {
                    for (Iterator<Data.Property> propIter=(queryParamHelper.getListValue()).iterator();propIter.hasNext();) {
                        Data.Property prop = propIter.next();
                        searchParam.getValues().add((String) prop.getValue());
                    }
                }
                if (membershipQueryInfo.getQueryParamValues() == null) {
                    membershipQueryInfo.setQueryParamValues(new ArrayList<org.kuali.student.r2.core.search.dto.SearchParamInfo>());
                }
                membershipQueryInfo.getQueryParamValues().add(searchParam);
            }
        }
        return membershipQueryInfo;
    }

}
