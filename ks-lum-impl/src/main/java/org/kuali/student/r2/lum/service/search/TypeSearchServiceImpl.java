package org.kuali.student.r2.lum.service.search;

import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.exceptions.*;
import org.kuali.student.r2.core.search.dto.SearchRequestInfo;
import org.kuali.student.r2.core.search.dto.SearchResultInfo;
import org.kuali.student.r2.core.search.service.SearchService;
import org.kuali.student.r2.core.class1.type.dto.TypeInfo;

import javax.jws.WebParam;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: SW
 * Date: 2012/09/12
 * Time: 11:22 AM
 * To change this template use File | Settings | File Templates.
 */
public class TypeSearchServiceImpl implements SearchService {

    private List<TypeSearch> typeSearches;

    public List<TypeSearch> getTypeSearches() {
        return typeSearches;
    }

    public void setTypeSearches(List<TypeSearch> typeSearches) {
        this.typeSearches = typeSearches;
    }

    @Override
    public List<TypeInfo> getSearchTypes( ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException {
        List<TypeInfo> typeInfos = new ArrayList<TypeInfo>();
        for(TypeSearch typeSearch : typeSearches){
            TypeInfo typeInfo = new TypeInfo();
            typeInfo.setKey(typeSearch.getSearchTypeKey());
            typeInfos.add(typeInfo);
        }
        return typeInfos;
    }

    @Override
    public TypeInfo getSearchType(String searchTypeKey,  ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        for(TypeSearch typeSearch : typeSearches){
            if(typeSearch.getSearchTypeKey().equals(searchTypeKey)){
                TypeInfo typeInfo = new TypeInfo();
                typeInfo.setKey(typeSearch.getSearchTypeKey());
                return typeInfo;
            }
        }
        return null;
    }

    @Override
    public List<TypeInfo> getSearchTypesByResult(String searchResultTypeKey,  ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public List<TypeInfo> getSearchTypesByCriteria(String searchCriteriaTypeKey,  ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public List<TypeInfo> getSearchResultTypes( ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public List<TypeInfo> getSearchCriteriaTypes( ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public SearchResultInfo search(SearchRequestInfo searchRequestInfo,  ContextInfo contextInfo) throws MissingParameterException, OperationFailedException, PermissionDeniedException {
        for (TypeSearch typeSearch : typeSearches){
            if (searchRequestInfo.getSearchKey().equals(typeSearch.getSearchTypeKey())){
                try {
                    return typeSearch.search(searchRequestInfo, contextInfo);
                } catch (Exception e) {
                    throw new OperationFailedException("Search failed for " + typeSearch.getSearchTypeKey(), e);
                }
            }
        }
        return null;
    }





}
