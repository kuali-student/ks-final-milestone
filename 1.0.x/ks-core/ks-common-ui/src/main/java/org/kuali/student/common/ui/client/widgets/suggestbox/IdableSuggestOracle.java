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

package org.kuali.student.common.ui.client.widgets.suggestbox;

import java.util.HashMap;
import java.util.Map;

import org.kuali.student.core.search.dto.SearchResult;

import com.google.gwt.user.client.ui.SuggestOracle;

public abstract class IdableSuggestOracle extends SuggestOracle{
    public static class IdableSuggestion implements Suggestion{
        private String id;
        private String displayString;
        private String replacementString;
        private Map<String, String> attrMap = new HashMap<String, String>();
        //private List<String> attrKeys = new ArrayList<String>();
        
        public IdableSuggestion(){}

        public IdableSuggestion(String id, String displayString, String replacementString) {
            this.displayString = displayString;
            this.id = id;
            this.replacementString = replacementString;
        }

        public String getId() {
            return id;
        }

        @Override
        public String getDisplayString() {
            return displayString;
        }

        @Override
        public String getReplacementString() {
            return replacementString;
        }
        
        public void setId(String id) {
            this.id = id;
        }

        public void setDisplayString(String displayString) {
            this.displayString = displayString;
        }

        public void setReplacementString(String replacementString) {
            this.replacementString = replacementString;
        }

        protected void addAttr(String key, String value){
            attrMap.put(key, value);
        }
        
/*        protected void addAttrKey(String key){
            attrKeys.add(key);
        }*/
        
        public Map<String, String> getAttrMap(){
            return attrMap;
        }
        
/*        public List<String> getAttrKeys(){
            return attrKeys;
        }*/
        
        
    }
    //public abstract IdableSuggestion getSuggestionById(String id);
    public abstract IdableSuggestion getSuggestionByText(String text);
    public abstract void getSuggestionByIdSearch(String id, org.kuali.student.common.ui.client.mvc.Callback<IdableSuggestion> callback);
    public abstract void addSearchCompletedCallback(org.kuali.student.common.ui.client.mvc.Callback<IdableSuggestion> callback);
}
