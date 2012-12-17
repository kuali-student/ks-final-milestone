/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.kuali.student.r2.common.class1.search;

import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.exceptions.*;
import org.kuali.student.r2.core.search.dto.SearchRequestInfo;
import org.kuali.student.r2.core.search.dto.SearchResultInfo;
import org.kuali.student.r2.core.search.service.SearchService;
import org.kuali.student.r2.core.class1.type.dto.TypeInfo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author nwright
 */
public class SearchServiceDispatcherImpl
        implements SearchService {

    private List<SearchService> searchServices;
    private Map<String, SearchService> searchType2ServiceMap = new HashMap<String, SearchService>();

    public SearchServiceDispatcherImpl() {
        super();
    }

    public SearchServiceDispatcherImpl(SearchService... services){
        super();
        this.searchServices = new ArrayList<SearchService>();
        if(services!=null){
            for(SearchService service:services){
                this.searchServices.add(service);
            }
        }
    }

    public List<SearchService> getSearchServices() {
        if (this.searchServices == null) {
            this.searchServices = new ArrayList<SearchService>();
        }
        return searchServices;
    }

    public void setSearchServices(List<SearchService> searchServices) {
        this.searchServices = searchServices;
    }

    @Override
    public List<TypeInfo> getSearchCriteriaTypes(ContextInfo contextInfo)
            throws InvalidParameterException,
            MissingParameterException,
            OperationFailedException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<TypeInfo> getSearchResultTypes(ContextInfo contextInfo)
            throws InvalidParameterException,
            MissingParameterException,
            OperationFailedException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public TypeInfo getSearchType(String searchTypeKey, ContextInfo contextInfo)
            throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException {
        SearchService searchService = this.searchType2ServiceMap.get(searchTypeKey);
        if (searchService != null) {
            return searchService.getSearchType(searchTypeKey, contextInfo);
        }
        for (SearchService ss : searchServices) {
            try {
                TypeInfo type = ss.getSearchType(searchTypeKey, contextInfo);
                if (type != null){
                    this.searchType2ServiceMap.put(searchTypeKey, ss);
                    return type;
                }
            } catch (DoesNotExistException ex) {
                // continue
            }
        }
        throw new DoesNotExistException(searchTypeKey);
    }

    @Override
    public List<TypeInfo> getSearchTypes(ContextInfo contextInfo)
            throws InvalidParameterException,
            MissingParameterException,
            OperationFailedException {
        List<TypeInfo> list = new ArrayList<TypeInfo>();
        for (SearchService ss : this.searchServices) {
            list.addAll(ss.getSearchTypes(contextInfo));
        }
        return list;
    }

    @Override
    public List<TypeInfo> getSearchTypesByCriteria(String searchCriteriaTypeKey, ContextInfo contextInfo)
            throws
            DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<TypeInfo> getSearchTypesByResult(String searchResultTypeKey, ContextInfo contextInfo)
            throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public SearchResultInfo search(SearchRequestInfo searchRequestInfo, ContextInfo contextInfo)
            throws MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        try {
            // check type of search is supported
            TypeInfo type = this.getSearchType(searchRequestInfo.getSearchKey(), contextInfo);
        } catch (DoesNotExistException ex) {
            throw new OperationFailedException("unknown/unsupported search type " + searchRequestInfo.getSearchKey());
        } catch (InvalidParameterException ex) {
            throw new OperationFailedException("unknown/unsupported search type " + searchRequestInfo.getSearchKey());
        }
        SearchService searchService = this.searchType2ServiceMap.get(searchRequestInfo.getSearchKey());
        if (searchService == null) {
            throw new OperationFailedException(searchRequestInfo.getSearchKey() + "was not in map as expected");
        }
        return searchService.search(searchRequestInfo, contextInfo);

    }
}
