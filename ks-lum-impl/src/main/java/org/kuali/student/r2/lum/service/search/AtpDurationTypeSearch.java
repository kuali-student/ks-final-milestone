package org.kuali.student.r2.lum.service.search;

import org.kuali.student.r1.common.search.dto.SearchRequest;
import org.kuali.student.r1.common.search.dto.SearchResult;
import org.kuali.student.r2.core.class1.type.dto.TypeInfo;

/**
 * Created with IntelliJ IDEA.
 * User: SW
 * Date: 2012/09/14
 * Time: 3:10 PM
 * To change this template use File | Settings | File Templates.
 */
public class AtpDurationTypeSearch extends AbstractTypeSearch {

    private String ATP_SEARCH_DURATIONTYPES = "atp.search.atpDurationTypes";

    private String ATP_QUERYPARAM_DURATIONTYPE = "atp.queryParam.atpDurationType";

    private String ATP_RESULTCOLUMN_ID = "atp.resultColumn.atpDurType";
    private String ATP_RESULTCOLUMN_NAME = "atp.resultColumn.atpDurTypeName";
    private String ATP_RESULTCOLUM_DESC = "atp.resultColumn.atpDurTypeDesc";

    @Override
    public String getSearchTypeKey() {
        return ATP_SEARCH_DURATIONTYPES;
    }

    @Override
    public SearchResult search(SearchRequest searchRequest) {
        String typeKey = this.getParamValueForKey(searchRequest, ATP_QUERYPARAM_DURATIONTYPE);
        if (typeKey!=null){
            TypeInfo typeInfo = this.getTypeForKey(typeKey);
            return createSearchResultFromTypeInfo(typeInfo, ATP_RESULTCOLUMN_ID, ATP_RESULTCOLUMN_NAME, ATP_RESULTCOLUM_DESC);
        }
        return null;
    }
}
