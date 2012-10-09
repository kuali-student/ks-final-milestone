package org.kuali.student.r2.lum.service.search;

import org.kuali.student.r1.common.search.dto.*;
import org.kuali.student.r1.common.search.service.SearchService;
import org.kuali.student.r2.common.exceptions.*;

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
public class TypeSearchServiceImpl implements SearchService{

    private List<TypeSearch> typeSearches;

    public List<TypeSearch> getTypeSearches() {
        return typeSearches;
    }

    public void setTypeSearches(List<TypeSearch> typeSearches) {
        this.typeSearches = typeSearches;
    }

    @Override
    public List<SearchTypeInfo> getSearchTypes() throws OperationFailedException {
        List<SearchTypeInfo> typeInfos = new ArrayList<SearchTypeInfo>();
        for(TypeSearch typeSearch : typeSearches){
            SearchTypeInfo searchTypeInfo = new SearchTypeInfo();
            searchTypeInfo.setKey(typeSearch.getSearchTypeKey());
            typeInfos.add(searchTypeInfo);
        }
        return typeInfos;
    }

    @Override
    public SearchTypeInfo getSearchType(@WebParam(name = "searchTypeKey") String searchTypeKey) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public List<SearchTypeInfo> getSearchTypesByResult(@WebParam(name = "searchResultTypeKey") String searchResultTypeKey) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public List<SearchTypeInfo> getSearchTypesByCriteria(@WebParam(name = "searchCriteriaTypeKey") String searchCriteriaTypeKey) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public List<SearchResultTypeInfo> getSearchResultTypes() throws OperationFailedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public SearchResultTypeInfo getSearchResultType(@WebParam(name = "searchResultTypeKey") String searchResultTypeKey) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public List<SearchCriteriaTypeInfo> getSearchCriteriaTypes() throws OperationFailedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public SearchCriteriaTypeInfo getSearchCriteriaType(@WebParam(name = "searchCriteriaTypeKey") String searchCriteriaTypeKey) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public SearchResult search(SearchRequest searchRequest) throws MissingParameterException {
        for (TypeSearch typeSearch : typeSearches){
            if (searchRequest.getSearchKey().equals(typeSearch.getSearchTypeKey())){
                try {
                    return typeSearch.search(searchRequest);
                } catch (InvalidParameterException e) {
                    e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                } catch (DoesNotExistException e) {
                    e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                } catch (OperationFailedException e) {
                    e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                } catch (PermissionDeniedException e) {
                    e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                }
            }
        }
        return null;
    }





}
