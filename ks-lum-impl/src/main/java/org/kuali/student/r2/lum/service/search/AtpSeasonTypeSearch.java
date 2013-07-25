package org.kuali.student.r2.lum.service.search;

import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.exceptions.*;
import org.kuali.student.r2.core.search.dto.SearchRequestInfo;
import org.kuali.student.r2.core.search.dto.SearchResultInfo;
import org.kuali.student.r2.core.class1.type.dto.TypeInfo;

import javax.jws.WebParam;
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
    public SearchResultInfo search(SearchRequestInfo searchRequestInfo, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws MissingParameterException, OperationFailedException, PermissionDeniedException, InvalidParameterException, DoesNotExistException {
        List<String> typeKeys = new ArrayList();
        typeKeys.add("kuali.atp.season.Any");
        typeKeys.add("kuali.atp.season.Fall");
        typeKeys.add("kuali.atp.season.Spring");
        typeKeys.add("kuali.atp.season.Summer");
        List<TypeInfo> typeInfos = null;
        typeInfos = this.getTypeService().getTypesByKeys(typeKeys, contextInfo);
        return createSearchResultFromTypeInfo(typeInfos, ATP_RESULTCOLUMN_ID, ATP_RESULTCOLUMN_NAME, ATP_RESULTCOLUM_DESC);
    }
}
