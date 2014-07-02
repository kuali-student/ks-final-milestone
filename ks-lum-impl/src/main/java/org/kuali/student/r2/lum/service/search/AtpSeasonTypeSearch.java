package org.kuali.student.r2.lum.service.search;

import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.exceptions.*;
import org.kuali.student.r2.core.search.dto.SearchRequestInfo;
import org.kuali.student.r2.core.search.dto.SearchResultInfo;
import org.kuali.student.r2.core.class1.type.dto.TypeInfo;
import org.kuali.student.r2.core.constants.AtpServiceConstants;

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

    @Override
    public String getSearchTypeKey() {
        return AtpServiceConstants.ATP_SEARCH_SEASONTYPES;
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
        return createSearchResultFromTypeInfo(typeInfos, AtpServiceConstants.ATP_RESULTCOLUMN_ID,
                                                         AtpServiceConstants.ATP_RESULTCOLUMN_NAME,
                                                         AtpServiceConstants.ATP_RESULTCOLUM_DESC);
    }
}
