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
        
    public SearchResultListItems(){
        
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
    
    public SearchResultListItems(List<ResultColumnInfo> resultColumns, List<SearchResultRow> results){
        setResultColumns(resultColumns);
        setResults(results);
    }       
    
    public void setResults(List<SearchResultRow> results){            
        resultDataMap.clear();
        if (results != null){
            for (SearchResultRow r: results){
                //FIXME: This assumes search results have id as first column
                resultDataMap.put(r.getCells().get(0).getValue(), r);
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
        // FIXME: What value should this really return for multi-column list, id or the first non-id column?
        return resultDataMap.get(id).getCells().get(1).getValue();
    }
    
}
