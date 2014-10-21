package org.kuali.student.common.ui.client.util;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import org.kuali.student.common.ui.client.application.Application;
import org.kuali.student.common.ui.client.configurable.mvc.FieldDescriptor;
import org.kuali.student.common.ui.client.mvc.HasDataValue;
import org.kuali.student.common.ui.client.widgets.KSTextBox;
import org.kuali.student.r1.common.assembly.data.Data;
import org.kuali.student.r1.common.assembly.data.Data.Property;
import org.kuali.student.r1.common.assembly.data.Data.Value;
import org.kuali.student.r1.common.assembly.data.Data.StringValue;
import org.kuali.student.r1.common.assembly.data.LookupMetadata;
import org.kuali.student.r1.common.assembly.data.LookupParamMetadata;
import org.kuali.student.r1.common.assembly.data.Metadata.WriteAccess;
import org.kuali.student.r2.core.search.dto.SearchParamInfo;
import org.kuali.student.r2.core.search.dto.SearchRequestInfo;
import org.kuali.student.r2.core.search.dto.SortDirection;

import com.google.gwt.core.client.GWT;

/**
 *  
 *  This is a library of utility methods that can be used when working with the search rpc service. 
 *
 */
public class SearchUtils {

	/**
	 * This class wraps the search request, with additional information needed to execute/process
	 * a search, such as handling constraints or whether the search should be deferred.
	 * 
	 */
	public static class SearchRequestWrapper{
		SearchRequestInfo searchRequest;
		HashSet<String> crossConstraints = new HashSet<String>();
		boolean deferSearch = false;
		
		public SearchRequestInfo getSearchRequest() {
			return searchRequest;
		}
		
		public void setSearchRequest(SearchRequestInfo searchRequest) {
			this.searchRequest = searchRequest;
		}
		
		public HashSet<String> getCrossConstraints() {
			return crossConstraints;
		}
		
		public void setCrossConstraints(HashSet<String> constraints) {
			this.crossConstraints = constraints;
		}

		public boolean isDeferSearch() {
			return deferSearch;
		}

		public void setDeferSearch(boolean deferSearch) {
			this.deferSearch = deferSearch;
		}				
	}
	
	/**
	 * Use this to build a SearchRequestInfo given a LookupMetadata definition. The search
	 * request can then be passed into the SearchRpcService to retreive a list of search
	 * results.
	 * 
	 * @param lookup
	 * @return
	 */
	public static SearchRequestInfo initializeSearchRequest(LookupMetadata lookup) {
		//Initialize the search using the SearchRequestWrapper, but return only the
		//SearchRequestInfo, since the consumer doesn't care about additional search data
		SearchRequestWrapper searchRequestWrapper = new SearchRequestWrapper();
		initializeSearchRequest(lookup, searchRequestWrapper);
		return searchRequestWrapper.getSearchRequest();
	}

	/**
	 * Use this to build a SearchRequestInfo, update search constraints and deferred search options
	 * contained within the SearchRequestWrapper. The wrapper is mostly to accommodate handling 
	 * of search options required for constraining values that appear in the KSPicker.
	 * 
	 * Generally this method should not be called directly if only the SearchRequestInfo is required.
	 * @see SearchUtils#initializeSearchRequest(LookupMetadata)
	 * 
	 * @param lookup
	 * @return
	 */
	public static void initializeSearchRequest(LookupMetadata lookup, SearchRequestWrapper searchRequestWrapper) {

		HashSet<String> crossConstraints = searchRequestWrapper.getCrossConstraints();
		
		SearchRequestInfo sr = new SearchRequestInfo();
        List<SearchParamInfo> params = new ArrayList<SearchParamInfo>();

        sr.setSearchKey(lookup.getSearchTypeId());

        if (lookup.getResultSortKey() != null){
        	sr.setSortColumn(lookup.getResultSortKey());
        }
        if(SortDirection.DESC.equals(lookup.getSortDirection())){
        	sr.setSortDirection(SortDirection.DESC);
        }

        //initialize search parameters that are hidden from the UI because they are set to default context specific values
        for(final LookupParamMetadata metaParam: lookup.getParams()){
            if(metaParam.getWriteAccess() == WriteAccess.NEVER){
                if ((metaParam.getDefaultValueString() == null || metaParam.getDefaultValueString().isEmpty())&&
                    (metaParam.getDefaultValueList() == null || metaParam.getDefaultValueList().isEmpty())&&
                    (metaParam.getFieldPath() == null || metaParam.getFieldPath().isEmpty())) {
                    //FIXME throw an exception?
                    GWT.log("Key = " + metaParam.getKey() + " has write access NEVER but has no default value!", null);
                    continue;
                }
                final SearchParamInfo param = new SearchParamInfo();
                param.setKey(metaParam.getKey());
                if(metaParam.getFieldPath()!=null){
                	FieldDescriptor fd = null;
                	String finalPath = resolvePath(metaParam.getFieldPath());
            		crossConstraints.add(finalPath);
            		fd = Application.getApplicationContext().getPathToFieldMapping(null, finalPath);
                	if(fd!=null){
                		Value value = null;
                		if(fd.getFieldElement().getFieldWidget() instanceof KSTextBox){
                			value = new StringValue(((KSTextBox)fd.getFieldElement().getFieldWidget()).getValue());
                		}
                		if(fd.getFieldElement().getFieldWidget() instanceof HasDataValue){
                			value = ((HasDataValue)fd.getFieldElement().getFieldWidget()).getValue();
                		}
            			if(value!=null&&value.get()!=null){
            				if(value.get() instanceof Data){
            					ArrayList<String> listValue = new ArrayList<String>();
            					for(Iterator<Property> iter =((Data)value.get()).realPropertyIterator();iter.hasNext();){
            						listValue.add(iter.next().getValue().toString());
            					}
            					if(listValue.isEmpty()){
            						listValue.add("");
            					}
            					param.setValues(listValue);
            				}else{
            					param.getValues().add(value.get().toString());
            				}
            			}else{
            				param.getValues().add((String)null);
            			}                				
                	}
                	searchRequestWrapper.setDeferSearch(true);
                }else if(metaParam.getDefaultValueList()==null){
                    param.getValues().add(metaParam.getDefaultValueString());
                }else{
                    param.setValues(metaParam.getDefaultValueList());
                }
                params.add(param);
            }
            else if(metaParam.getWriteAccess() == WriteAccess.WHEN_NULL){
                if((metaParam.getDefaultValueString() != null && !metaParam.getDefaultValueString().isEmpty())||
                   (metaParam.getDefaultValueList() != null && !metaParam.getDefaultValueList().isEmpty())||
                   (metaParam.getFieldPath() != null && !metaParam.getFieldPath().isEmpty())){
                    final SearchParamInfo param = new SearchParamInfo();
                    param.setKey(metaParam.getKey());
                    if(metaParam.getFieldPath()!=null){
                    	FieldDescriptor fd = null;
                    	String finalPath;
                    	if(metaParam.getFieldPath().startsWith("/")){
                    		finalPath=metaParam.getFieldPath().substring(1);
                    	}else{
                    		finalPath=Application.getApplicationContext().getParentPath()+metaParam.getFieldPath();
                    	}
                		crossConstraints.add(finalPath);
                		fd = Application.getApplicationContext().getPathToFieldMapping(null, finalPath);
                    	if(fd!=null){
                    		if(fd.getFieldElement().getFieldWidget() instanceof HasDataValue){
                    			Value value = ((HasDataValue)fd.getFieldElement().getFieldWidget()).getValue();
                    			param.getValues().add(value==null?null:value.get()==null?null:value.get().toString());
                    		}
                    	}
                    	searchRequestWrapper.setDeferSearch(true);
                    }else if(metaParam.getDefaultValueList()==null){
                        param.getValues().add(metaParam.getDefaultValueString());
                    }else{
                        param.setValues(metaParam.getDefaultValueList());
                    }
                    params.add(param);
                }
            }
        }
        sr.setParams(params);
        searchRequestWrapper.setSearchRequest(sr);
    }

	public static String resolvePath(String path) {
		String finalPath;
		if(path.startsWith("/")){
    		finalPath=path.substring(1);
    	}else{
    		finalPath=Application.getApplicationContext().getParentPath()+path;
    	}
		return finalPath;
	}

}
