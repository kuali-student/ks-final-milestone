package org.kuali.student.r2.lum.service.search;

import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.exceptions.*;
import org.kuali.student.r2.core.search.dto.SearchRequestInfo;
import org.kuali.student.r2.core.search.dto.SearchResultInfo;
import org.kuali.student.r2.core.class1.type.dto.TypeInfo;
import org.kuali.student.r2.lum.util.constants.LrcServiceConstants;

import javax.jws.WebParam;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: SW
 * Date: 2012/09/14
 * Time: 3:10 PM
 * To change this template use File | Settings | File Templates.
 */
public class ResultComponentTypeSearch extends AbstractTypeSearch {

    private String LRC_SEARCH_RESULTCOMPONENT = "lrc.search.resultComponentType";

    private String LRC_QUERYPARAM_ID = "lrc.queryParam.resultComponent.id";
    private String LRC_QUERYPARAM_ID_RESTRICTIONLIST = "lrc.queryParam.resultComponent.idRestrictionList";

    private String LRC_RESULTCOLUMN_ID = "lrc.resultColumn.resultComponent.id";
    private String LRC_RESULTCOLUMN_NAME = "lrc.resultColumn.resultComponent.name";

    @Override
    public String getSearchTypeKey() {
        return LRC_SEARCH_RESULTCOMPONENT;
    }

    @Override
    public SearchResultInfo search(SearchRequestInfo searchRequestInfo, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws MissingParameterException, InvalidParameterException, DoesNotExistException, OperationFailedException, PermissionDeniedException {
        String typeKey = this.getParamValueForKey(searchRequestInfo, LRC_QUERYPARAM_ID);
        if (typeKey!=null){
            TypeInfo typeInfo = this.getTypeService().getType(typeKey, contextInfo);
            return createSearchResultFromTypeInfo(typeInfo, LRC_RESULTCOLUMN_ID, LRC_RESULTCOLUMN_NAME, null);
        } else {
            List<TypeInfo> typeInfos = this.getTypeService().getTypesByRefObjectUri(LrcServiceConstants.REF_OBJECT_URI_RESULT_VALUES_GROUP, contextInfo);
            List<String> idList = this.getParamListForKey(searchRequestInfo, LRC_QUERYPARAM_ID_RESTRICTIONLIST);
            if ((idList != null) && (idList.size() > 0)){
                List<TypeInfo> restrictedTypes = new ArrayList<TypeInfo>();
                for (TypeInfo type : typeInfos){
                    for (String id : idList){
                        if (id.equals(type.getKey())){
                            restrictedTypes.add(type);
                            continue;
                        }
                    }
                }
                typeInfos = restrictedTypes;
            }
            return createSearchResultFromTypeInfo(typeInfos, LRC_RESULTCOLUMN_ID, LRC_RESULTCOLUMN_NAME, null);
        }
    }
}
