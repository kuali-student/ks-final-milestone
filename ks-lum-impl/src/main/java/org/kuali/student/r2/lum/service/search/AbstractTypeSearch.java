package org.kuali.student.r2.lum.service.search;

import org.kuali.student.r1.common.search.dto.SearchParam;
import org.kuali.student.r1.common.search.dto.SearchRequest;
import org.kuali.student.r1.common.search.dto.SearchResult;
import org.kuali.student.r1.common.search.dto.SearchResultRow;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.exceptions.*;
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

    protected ContextInfo getContextInfo(){
        return new ContextInfo();
    }

    protected String getParamValueForKey(SearchRequest searchRequest, String key){
        for(SearchParam param : searchRequest.getParams()){
            if (param.getKey().equals(key)){
                return (String) param.getValue();
            }
        }
        return null;
    }

    protected SearchResult createSearchResultFromTypeInfo(TypeInfo typeInfo, String idKey, String nameKey, String descKey){

        List<TypeInfo> typeInfos = new ArrayList<TypeInfo>();
        typeInfos.add(typeInfo);

        return this.createSearchResultFromTypeInfo(typeInfos, idKey, nameKey, descKey);
    }

    protected SearchResult createSearchResultFromTypeInfo(List<TypeInfo> typeInfos, String idKey, String nameKey, String descKey){

        SearchResult searchResult = new SearchResult();
        for (TypeInfo typeInfo : typeInfos){
            searchResult.getRows().add(this.createSearchResultRowFromTypeInfo(typeInfo, idKey, nameKey, descKey));
        }

        return searchResult;
    }

    protected SearchResultRow createSearchResultRowFromTypeInfo(TypeInfo typeInfo, String idKey, String nameKey, String descKey){
        if (typeInfo==null){
            return null;
        }

        SearchResultRow row = new SearchResultRow();
        row.addCell(idKey, typeInfo.getKey());
        row.addCell(nameKey, typeInfo.getName());
        if (descKey != null){
            row.addCell(descKey, typeInfo.getDescr().getPlain());
        }

        return row;
    }
}
