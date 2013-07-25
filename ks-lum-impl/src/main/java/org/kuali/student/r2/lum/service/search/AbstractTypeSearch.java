package org.kuali.student.r2.lum.service.search;

import org.kuali.student.r2.core.search.dto.SearchParamInfo;
import org.kuali.student.r2.core.search.dto.SearchRequestInfo;
import org.kuali.student.r2.core.search.dto.SearchResultInfo;
import org.kuali.student.r2.core.search.dto.SearchResultRowInfo;
import org.kuali.student.r2.core.class1.type.dto.TypeInfo;
import org.kuali.student.r2.core.class1.type.service.TypeService;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: SW
 * Date: 2012/09/14
 * Time: 3:09 PM
 * To change this template use File | Settings | File Templates.
 */
public abstract class AbstractTypeSearch implements TypeSearch{

    private TypeService typeService;

    public TypeService getTypeService() {
        return typeService;
    }

    public void setTypeService(TypeService typeService) {
        this.typeService = typeService;
    }

    protected String getParamValueForKey(SearchRequestInfo searchRequest, String key){
        for(SearchParamInfo param : searchRequest.getParams()){
            if (param.getKey().equals(key)){
                return (String) param.getValues().get(0);
            }
        }
        return null;
    }

    protected List<String> getParamListForKey(SearchRequestInfo searchRequest, String key){
        for(SearchParamInfo param : searchRequest.getParams()){
            if (param.getKey().equals(key)){
                return param.getValues();
            }
        }
        return null;
    }

    protected SearchResultInfo createSearchResultFromTypeInfo(TypeInfo typeInfo, String idKey, String nameKey, String descKey){

        List<TypeInfo> typeInfos = new ArrayList<TypeInfo>();
        typeInfos.add(typeInfo);

        return this.createSearchResultFromTypeInfo(typeInfos, idKey, nameKey, descKey);
    }

    protected SearchResultInfo createSearchResultFromTypeInfo(List<TypeInfo> typeInfos, String idKey, String nameKey, String descKey){

        SearchResultInfo searchResult = new SearchResultInfo();
        for (TypeInfo typeInfo : typeInfos){
            searchResult.getRows().add(this.createSearchResultRowFromTypeInfo(typeInfo, idKey, nameKey, descKey));
        }

        return searchResult;
    }

    protected SearchResultRowInfo createSearchResultRowFromTypeInfo(TypeInfo typeInfo, String idKey, String nameKey, String descKey){
        if (typeInfo==null){
            return null;
        }

        SearchResultRowInfo row = new SearchResultRowInfo();
        row.addCell(idKey, typeInfo.getKey());
        row.addCell(nameKey, typeInfo.getName());
        if (descKey != null){
            row.addCell(descKey, typeInfo.getDescr().getPlain());
        }

        return row;
    }
}
