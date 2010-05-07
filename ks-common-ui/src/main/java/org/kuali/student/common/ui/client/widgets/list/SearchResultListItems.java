/**
 * Copyright 2010 The Kuali Foundation Licensed under the
 * Educational Community License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License. You may
 * obtain a copy of the License at
 *
 * http://www.osedu.org/licenses/ECL-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an "AS IS"
 * BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing
 * permissions and limitations under the License.
 */

package org.kuali.student.common.ui.client.widgets.list;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.kuali.student.core.assembly.data.LookupMetadata;
import org.kuali.student.core.search.dto.ResultColumnInfo;
import org.kuali.student.core.search.dto.SearchResultRow;

/**
 * This is a ListItems adapter for search results returned by the search service.
 * Searches must return id as the first column to be compatible with the ListItems interface. 
 * 
 * @author Kuali Student Team
 *
 */
public class SearchResultListItems implements ListItems{

    private ArrayList<String> attrKeys;
    private List<SearchResultRow> resultDataMap = new ArrayList<SearchResultRow>();
    private int attrOffset = 0;
    //default values for attr indexes
    //these are necessary for use in search dispatcher assumed 2 columns min
    //we may want to switch these all to 0 to allow for 1 column result lists
    private int sortAttrNdx = 1;        //sort key
    private int itemTextAttrNdx = 1;
    private int keyAttrNdx = 0;         //unique key
        
    public int getSortAttrNdx() {
		return sortAttrNdx;
	}

	public void setSortAttrNdx(int sortAttrNdx) {
		this.sortAttrNdx = sortAttrNdx;
	}

	public void setSortAttrNdxFromAttrKey(List<SearchResultRow> results, String sortAttrKey) {
		this.sortAttrNdx = getAttrKeyNdx(results, sortAttrKey);
	}

	public int getKeyAttrNdx() {
		return keyAttrNdx;
	}

	public void setKeyAttrNdx(int keyAttrNdx) {
		this.keyAttrNdx = keyAttrNdx;
	}
	
	public void setKeyAttrNdxFromAttrKey(List<SearchResultRow> results, String keyAttrKey) {
		this.keyAttrNdx = getAttrKeyNdx(results, keyAttrKey);
	}

	public int getItemTextAttrNdx() {
		return itemTextAttrNdx;
	}

	public void setItemTextAttrNdx(int itemTextAttrNdx) {
		this.itemTextAttrNdx = itemTextAttrNdx;
	}

	public void setItemTextAttrNdxFromAttrKey(List<SearchResultRow> results, String itemTextAttrKey) {
		this.itemTextAttrNdx = getAttrKeyNdx(results, itemTextAttrKey);
	}
	
	public SearchResultListItems(){ 
    }
    
    private void setAttrNdxs(List<SearchResultRow> results, LookupMetadata lookupMetadata) {
    	
    	setItemTextAttrNdxFromAttrKey(results, lookupMetadata.getResultDisplayKey());
    	setKeyAttrNdxFromAttrKey(results, lookupMetadata.getResultReturnKey());
    	setSortAttrNdxFromAttrKey(results, lookupMetadata.getResultSortKey());        
    }
    
    public SearchResultListItems(List<ResultColumnInfo> resultColumns, List<SearchResultRow> results, LookupMetadata lookupMetadata){
    	
    	setAttrNdxs(results, lookupMetadata);
    	setResultColumns(resultColumns);
        setResults(results);
    }   
    
    public SearchResultListItems(List<SearchResultRow> results, LookupMetadata lookupMetadata){
    
    	setAttrNdxs(results, lookupMetadata);
        setResults(results);
    }
    
    public SearchResultListItems(List<SearchResultRow> results){
        
    	setResults(results);
    }
    	
    public void setResultColumns(List<ResultColumnInfo> resultColumns){
        attrKeys = new ArrayList<String>();
        
        for (ResultColumnInfo r:resultColumns){
            attrKeys.add(r.getName());
        }
        
        //Exclude identifier column from list items (don't want this displayed)
        attrKeys.remove(0);
        attrOffset = 1;
    }
           
    public void setResults(List<SearchResultRow> results) {          
        resultDataMap.clear();

        if (results != null){            
            resultDataMap = new ArrayList<SearchResultRow>(results);           
            
            //Default keys for column attributes
            if (results.size() > 0){
                SearchResultRow r = results.get(0);
                if (attrKeys == null){
                    attrKeys = new ArrayList<String>();
                    for (int i=0; i < r.getCells().size(); i ++){
                        attrKeys.add("attr" + String.valueOf(i));
                    }
                }
            }
        }
    }
    
    private int getAttrKeyNdx(List<SearchResultRow> results, String keyAttrKey) {

    	if (results.size() > 0){
	        for (int i=0; i < results.get(0).getCells().size(); i++){
	        	if (results.get(0).getCells().get(i).getKey().equals(keyAttrKey)) {
	        		return i;
	        	}
	        }
		}
		
		return 0;
	}
    
    @Override
    public List<String> getAttrKeys() {                       
        return attrKeys;
    }

    @Override
    public String getItemAttribute(String id, String attrKey) {
        SearchResultRow r = getListItem(id);
        
        int attrIndex = attrKeys.indexOf(attrKey);
        if (attrIndex >= 0 && r != null){
            return r.getCells().get(attrIndex + attrOffset).getValue(); 
        }

        return null;
    }

    /**
     * @see org.kuali.student.common.ui.client.widgets.list.ListItems#getItemCount()
     */
    @Override
    public int getItemCount() {
        return resultDataMap.size();
    }

    /**
     * @see org.kuali.student.common.ui.client.widgets.list.ListItems#getItemIds()
     */
    @Override
    public List<String> getItemIds() {
        List<String> ids = new ArrayList<String>();

        for (SearchResultRow s:resultDataMap){
            ids.add(s.getCells().get(keyAttrNdx).getValue());
        }
        
        return ids;
    }

    /**
     * @see org.kuali.student.common.ui.client.widgets.list.ListItems#getItemText(java.lang.String)
     */
    @Override
    public String getItemText(String id) {
        return getListItem(id).getCells().get(itemTextAttrNdx).getValue();
    }
    
    private SearchResultRow getListItem(String id) {
        for (SearchResultRow s : resultDataMap) {
            if (s.getCells().get(keyAttrNdx).getValue() == id) {
                return s;
            }
        }
        return null;
    }
    
}
