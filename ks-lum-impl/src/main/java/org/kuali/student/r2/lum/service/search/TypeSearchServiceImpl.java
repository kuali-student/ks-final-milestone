package org.kuali.student.r2.lum.service.search;

import org.kuali.student.r1.common.search.dto.*;
import org.kuali.student.r1.common.search.service.SearchService;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.exceptions.*;
import org.kuali.student.r2.core.class1.type.dto.TypeInfo;
import org.kuali.student.r2.core.class1.type.service.TypeService;

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

    private TypeService typeService;

    private String LRC_SEARCH_RESULTCOMPONENT = "lrc.search.resultComponentType";

    private String LRC_QUERYPARAM_ID = "lrc.queryParam.resultComponent.id";

    private String LRC_RESULTCOLUMN_ID = "lrc.resultColumn.resultComponent.id";
    private String LRC_RESULTCOLUMN_NAME = "lrc.resultColumn.resultComponent.name";

    public TypeService getTypeService() {
        return typeService;
    }

    public void setTypeService(TypeService typeService) {
        this.typeService = typeService;
    }

    @Override
    public List<SearchTypeInfo> getSearchTypes() throws OperationFailedException {
        List<SearchTypeInfo> typeInfos = new ArrayList<SearchTypeInfo>();
        SearchTypeInfo searchTypeInfo = new SearchTypeInfo();
        searchTypeInfo.setKey(LRC_SEARCH_RESULTCOMPONENT);
        typeInfos.add(searchTypeInfo);
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
        SearchResult searchResult;
        if (searchRequest.getSearchKey().equals(LRC_SEARCH_RESULTCOMPONENT)){
            String typeKey = this.getParamValueForKey(searchRequest, LRC_QUERYPARAM_ID);
            if (typeKey!=null){
                try {
                    TypeInfo typeInfo = typeService.getType(typeKey, new ContextInfo());
                    return createSearchResultFromTypeInfo(typeInfo, LRC_RESULTCOLUMN_ID, LRC_RESULTCOLUMN_NAME);
                } catch (DoesNotExistException e) {
                    e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                } catch (InvalidParameterException e) {
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

    private String getParamValueForKey(SearchRequest searchRequest, String key){
        for(SearchParam param : searchRequest.getParams()){
            if (param.getKey().equals(key)){
                return (String) param.getValue();
            }
        }
        return null;
    }

    private SearchResult createSearchResultFromTypeInfo(TypeInfo typeInfo, String idKey, String nameKey){
        if (typeInfo==null){
            return null;
        }

        SearchResult searchResult = new SearchResult();
        SearchResultRow row = new SearchResultRow();
        row.addCell(idKey, typeInfo.getKey());
        row.addCell(nameKey, typeInfo.getName());
        searchResult.getRows().add(row);

        return searchResult;
    }
}
