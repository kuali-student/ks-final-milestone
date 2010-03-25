/*
 * Copyright 2009 The Kuali Foundation Licensed under the
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

import org.kuali.student.core.search.dto.ResultColumnInfo;
import org.kuali.student.core.search.dto.SearchResultCell;
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
    private HashMap<String, SearchResultRow> resultDataMap = new HashMap<String, SearchResultRow>();
    private int attrOffset = 0;
    //FIXME: need to add sortAttrNdx as well?
    //FIXME: currently have default values for attr indexes - these should go away
    private int itemTextAttrNdx = 1;
    private int keyAttrNdx = 0;
        
    public int getKeyAttrNdx() {
		return keyAttrNdx;
	}

	public void setKeyAttrNdx(int keyAttrNdx) {
		this.keyAttrNdx = keyAttrNdx;
	}

	public int getItemTextAttrNdx() {
		return itemTextAttrNdx;
	}
	
	public void setKeyAttrNdxFromAttrKey(List<SearchResultRow> results, String keyAttrKey) {
		this.keyAttrNdx = getAttrKeyNdx(results, keyAttrKey);
	}

	public void setItemTextAttrNdx(int itemTextAttrNdx) {
		this.itemTextAttrNdx = itemTextAttrNdx;
	}

	public void setItemTextAttrNdxFromAttrKey(List<SearchResultRow> results, String itemTextAttrKey) {
		this.itemTextAttrNdx = getAttrKeyNdx(results, itemTextAttrKey);
	}
	
	public SearchResultListItems(){ 
    }
    
    public SearchResultListItems(List<ResultColumnInfo> resultColumns, List<SearchResultRow> results){
 	
        setResultColumns(resultColumns);
        setResults(results);
    }   
    
    public SearchResultListItems(List<SearchResultRow> results){
        setResults(results);
    }
    
    public SearchResultListItems(List<ResultColumnInfo> resultColumns, List<SearchResultRow> results, String keyAttrKey, String itemTextAttrKey){
    	
    	setItemTextAttrNdxFromAttrKey(results, itemTextAttrKey);
    	setKeyAttrNdxFromAttrKey(results, keyAttrKey);
        setResultColumns(resultColumns);
        setResults(results);
    }   
    
    public SearchResultListItems(List<SearchResultRow> results, String keyAttrKey, String itemTextAttrKey){
    	setItemTextAttrNdxFromAttrKey(results, itemTextAttrKey);
    	setKeyAttrNdxFromAttrKey(results, keyAttrKey);
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
           
    public void setResults(List<SearchResultRow> results, int keyNdx){            
        resultDataMap.clear();
        if (results != null){
            for (SearchResultRow r: results){
                resultDataMap.put(r.getCells().get(keyNdx).getValue(), r);
            }
            
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
	    int notFound = -1;
		
		if (results.size() > 0){
	        for (int i=0; i < results.get(0).getCells().size(); i++){
	        	if (results.get(0).getCells().get(i).getKey().equals(keyAttrKey)) {
	        		return i;
	        	}
	        }
		}
		
		return notFound;
	}
    
    public void setResults(List<SearchResultRow> results) {
    	//FIXME: default key index is 0.  Should probably force a keyAttrKey.
    	setResults(results, 0);
    }
    
    @Override
    public List<String> getAttrKeys() {                       
        return attrKeys;
    }

    @Override
    public String getItemAttribute(String id, String attrKey) {
        SearchResultRow r = resultDataMap.get(id);
        
        int attrIndex = attrKeys.indexOf(attrKey);
        if (attrIndex >= 0 ){
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
        List<String> keys = new ArrayList<String>();

        for (String s:resultDataMap.keySet()){
            keys.add(s);
        }
        
        return keys;
    }

    /**
     * @see org.kuali.student.common.ui.client.widgets.list.ListItems#getItemText(java.lang.String)
     */
    @Override
    public String getItemText(String id) {
        return resultDataMap.get(id).getCells().get(itemTextAttrNdx).getValue();
    }
    
}
