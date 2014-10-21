package org.kuali.student.r2.lum.service.search;

import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.core.constants.AtpSearchServiceConstants;
import org.kuali.student.r2.core.search.dto.SearchRequestInfo;
import org.kuali.student.r2.core.search.dto.SearchResultInfo;
import org.kuali.student.r2.core.class1.type.dto.TypeInfo;
import org.kuali.student.r2.core.constants.AtpServiceConstants;

import javax.jws.WebParam;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: SW
 * Date: 2012/09/14
 * Time: 3:10 PM
 * To change this template use File | Settings | File Templates.
 */
public class AtpDurationTypeSearch extends AbstractTypeSearch {

    @Override
    public String getSearchTypeKey() {
        return AtpSearchServiceConstants.ATP_SEARCH_DURATIONTYPES;
    }

    @Override
    public SearchResultInfo search(SearchRequestInfo searchRequestInfo, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws MissingParameterException, OperationFailedException, PermissionDeniedException {
        String typeKey = this.getParamValueForKey(searchRequestInfo, AtpSearchServiceConstants.ATP_QUERYPARAM_DURATIONTYPE_ID);
        try {
            if (typeKey != null) {
                TypeInfo typeInfo = this.getTypeService().getType(typeKey, contextInfo);
                return createSearchResultFromTypeInfo(typeInfo, AtpSearchServiceConstants.ATP_RESULTCOLUMN_DURATIONTYPE_ID,
                        AtpSearchServiceConstants.ATP_RESULTCOLUMN_DURATIONTYPE_NAME, AtpSearchServiceConstants.ATP_RESULTCOLUM_DURATIONTYPE_DESC);
            } else {
                List<TypeInfo> typeInfos = null;

                typeInfos = this.getTypeService().getTypesByRefObjectUri(AtpServiceConstants.REF_OBJECT_URI_TIME_AMOUNT_INFO, contextInfo);

                return createSearchResultFromTypeInfo(typeInfos, AtpSearchServiceConstants.ATP_RESULTCOLUMN_DURATIONTYPE_ID,
                        AtpSearchServiceConstants.ATP_RESULTCOLUMN_DURATIONTYPE_NAME, AtpSearchServiceConstants.ATP_RESULTCOLUM_DURATIONTYPE_DESC);
            }
        } catch (Exception e) {
            throw new OperationFailedException("Atp Duration Type Search Failed.", e);
        }
    }
}
