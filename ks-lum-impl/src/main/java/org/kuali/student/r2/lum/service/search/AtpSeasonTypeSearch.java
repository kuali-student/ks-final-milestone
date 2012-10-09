package org.kuali.student.r2.lum.service.search;

import org.kuali.student.r1.common.search.dto.SearchRequest;
import org.kuali.student.r1.common.search.dto.SearchResult;
import org.kuali.student.r2.common.class1.type.dto.TypeInfo;
import org.kuali.student.r2.common.exceptions.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: SW
 * Date: 2012/09/14
 * Time: 3:11 PM
 * To change this template use File | Settings | File Templates.
 */
public class AtpSeasonTypeSearch extends AbstractTypeSearch{

    private String ATP_SEARCH_SEASONTYPES = "atp.search.atpSeasonTypes";

    private String ATP_RESULTCOLUMN_ID = "atp.resultColumn.atpSeasonType";
    private String ATP_RESULTCOLUMN_NAME = "atp.resultColumn.atpSeasonTypeName";
    private String ATP_RESULTCOLUM_DESC = "atp.resultColumn.atpSeasonTypeDesc";

    @Override
    public String getSearchTypeKey() {
        return ATP_SEARCH_SEASONTYPES;
    }

    @Override
    public SearchResult search(SearchRequest searchRequest) throws MissingParameterException, InvalidParameterException, DoesNotExistException, OperationFailedException, PermissionDeniedException {
        List<String> typeKeys = new ArrayList();
        typeKeys.add("kuali.atp.season.Any");
        typeKeys.add("kuali.atp.season.Fall");
        typeKeys.add("kuali.atp.season.Spring");
        typeKeys.add("kuali.atp.season.Summer");
        List<TypeInfo> typeInfos = this.getTypeService().getTypesByKeys(typeKeys, this.getContextInfo());
        return createSearchResultFromTypeInfo(typeInfos, ATP_RESULTCOLUMN_ID, ATP_RESULTCOLUMN_NAME, ATP_RESULTCOLUM_DESC);
    }
}
