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
package org.kuali.student.common.ui.client.widgets.suggestbox;

import java.util.ArrayList;
import java.util.List;

import org.kuali.student.common.ui.client.widgets.list.ListItems;

public class KSListItemsSuggestOracle extends IdableSuggestOracle{
    
    private ListItems listItems = null;
    private static final String WHITESPACE_STRING = " ";
    //private static final char PERIOD_CHAR = '.';
    private static final char WHITESPACE_CHAR = ' ';
    private static final String NORMALIZE_TO_SINGLE_WHITE_SPACE = "\\s+";
    
    
    @Override
    public void requestSuggestions(Request request, Callback callback) {
        final List<IdableSuggestion> suggestions = getSuggestions(request.getQuery(), request.getLimit());
        Response response = new Response(suggestions);
        callback.onSuggestionsReady(request, response);
        
    }
    
    private String[] splitQuery(String query){
        query = query.toLowerCase();
        query = query.replaceAll("[.]+", "");
        query = query.replace(',', WHITESPACE_CHAR);
        query = query.replaceAll(NORMALIZE_TO_SINGLE_WHITE_SPACE, WHITESPACE_STRING);
        query = query.trim();
        String[] split = query.split(WHITESPACE_STRING);
        return split;
    }
    
    String htmlString = "";
    
    private List<IdableSuggestion> getSuggestions(String query, int limit){
        query = query.trim();
        List<IdableSuggestion> suggestions = new ArrayList<IdableSuggestion>();
        if(!query.equals("")){
            List<String> ids = listItems.getItemIds();
            List<String> attributes = listItems.getAttrKeys();
            
            String[] splitQueryArr = splitQuery(query);
            
            boolean limitReached = false;
            for(String id: ids){
                for(String attr: attributes){
                    String attrString = listItems.getItemAttribute(id, attr);
                    
                    htmlString = "";
                    if(isValidSuggestion(splitQueryArr, id, attr, attrString)){
                        IdableSuggestion suggestion = new IdableSuggestion(id, htmlString, listItems.getItemText(id));
                        addAttributesToSuggestion(suggestion);
                        suggestions.add(suggestion);
                        if(suggestions.size() == limit){
                            limitReached = true;
                            break;
                        }
                    }
    
                }
                if(limitReached){
                    break;
                }
            }
        }
        
        return suggestions;
    }

    private void addAttributesToSuggestion(IdableSuggestion suggestion) {
        for(String attrKey: listItems.getAttrKeys()){
            //suggestion.addAttrKey(attrKey);
            suggestion.addAttr(attrKey, listItems.getItemAttribute(suggestion.getId(), attrKey));
        }
    }

    private boolean isValidSuggestion(String[] splitQueryArr, String id, String attrKey, String attrString) {
        for(String q: splitQueryArr){
            q.trim();
            if(attrString.regionMatches(true, 0, q, 0, q.length())){
                String itemText = listItems.getItemText(id);
                if(itemText.toLowerCase().contains(attrString.toLowerCase().trim())){
                    int index = itemText.toLowerCase().indexOf(attrString.toLowerCase().trim());
                    htmlString = itemText.substring(0,index) + "<b>" + itemText.substring(index, q.length()) + "</b>" + itemText.substring(index + q.length(), itemText.length());
                }
                else{
                    htmlString = listItems.getItemText(id) + " (<b>" + attrString.substring(0, q.length()) + "</b>" +
                        attrString.substring(q.length(), attrString.length()) + ")";
                }
                return true;
            }
        }
        return false;
    }
    

    @Override
    public boolean isDisplayStringHTML() {
        return true;
    }

    public ListItems getListItems() {
        return listItems;
    }

    public void setListItems(ListItems listItems) {
        this.listItems = listItems;
    }

    public List<String> getAttrKeys() {
        return listItems.getAttrKeys();
    }

    //@Override
/*    public IdableSuggestion getSuggestionByIdSearch(String id) {
        IdableSuggestion suggestion = null;
        if(listItems.getItemIds().contains(id)){
            suggestion = new IdableSuggestion(id, listItems.getItemText(id), listItems.getItemText(id));
            addAttributesToSuggestion(suggestion);
        }
        return suggestion;
    }*/

    @Override
    public IdableSuggestion getSuggestionByText(String text) {
        IdableSuggestion suggestion = null;
        for(String id: listItems.getItemIds()){
            text = text.trim();
            if(text.equalsIgnoreCase(listItems.getItemText(id).trim())){
                suggestion = new IdableSuggestion(id, listItems.getItemText(id), listItems.getItemText(id));
                addAttributesToSuggestion(suggestion);
                break;
            }
                
        }
        return suggestion;
    }

    @Override
    public void getSuggestionByIdSearch(String id, org.kuali.student.common.ui.client.mvc.Callback<IdableSuggestion> callback) {
        IdableSuggestion suggestion = null;
        if(listItems.getItemIds().contains(id)){
            suggestion = new IdableSuggestion(id, listItems.getItemText(id), listItems.getItemText(id));
            addAttributesToSuggestion(suggestion);
        }
        callback.exec(suggestion);
        
    }
}
