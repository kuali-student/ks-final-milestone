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

package org.kuali.student.common.ui.client.mvc.history;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.google.gwt.http.client.URL;

public class HistoryStackFrame implements Serializable {
    private static final long serialVersionUID = 1L;
    LinkedHashMap<String, HistoryToken> tokens = new LinkedHashMap<String, HistoryToken>();
    public LinkedHashMap<String, HistoryToken> getTokens() {
        return tokens;
    }
    
    
    public static String toDebugString(HistoryStackFrame frame) {
        StringBuilder sb = new StringBuilder("{");
        
        for (HistoryToken token : frame.getTokens().values()) {
            sb.append(token.getKey());
            sb.append(" : ");
            sb.append("{");
            for (Map.Entry<String, String> param : token.getParameters().entrySet()) {
                sb.append(param.getKey());
                sb.append("=\"");
                sb.append(param.getValue());
                sb.append("\"\t");
            }
            sb.append("},\t");
        }
        
        sb.append("}");
        return sb.toString();
    }
    
    public static String toSerializedForm(HistoryStackFrame frame) {
        StringBuilder sb = new StringBuilder();
        
        int tokenIdx = 0;
        for (Iterator<HistoryToken> tokenItr = frame.getTokens().values().iterator(); tokenItr.hasNext();) {
            HistoryToken token = tokenItr.next();
            sb.append("t");
            sb.append(tokenIdx);
            sb.append("=");
            sb.append(URL.encode(token.getKey()));
            
            int paramIdx = 0;
            for (Iterator<Entry<String, String>> paramItr = token.getParameters().entrySet().iterator(); paramItr.hasNext();) {
                Entry<String, String> e = paramItr.next();
                sb.append("&");
                sb.append("t");
                sb.append(tokenIdx);
                sb.append("p");
                sb.append(paramIdx);
                sb.append("k");
                sb.append("=");
                sb.append(URL.encode(e.getKey()));
                
                sb.append("&");
                sb.append("t");
                sb.append(tokenIdx);
                sb.append("p");
                sb.append(paramIdx);
                sb.append("v");
                sb.append("=");
                sb.append(URL.encode(e.getValue()));
                
                paramIdx++;
            }

            if (tokenItr.hasNext()) {
                sb.append("&");
            }
            tokenIdx++;
        }
        
        return sb.toString();
    }
    
    public static HistoryStackFrame fromSerializedForm(String input) {
        input = (input == null) ? "" : input.trim();
        if (input.isEmpty()) {
            return null;
        }
        
        HistoryStackFrame frame = new HistoryStackFrame();
        
        Map<String, String> pairs = toKeyValuePairs(input);
        int idx = 0;
        while (true) {
            HistoryToken token = extractToken(idx, pairs);
            if (token == null) {
                break;
            } else {
                frame.getTokens().put(token.getKey(), token);
                idx++;
            }
        }
        
        return frame;
    }
    
    private static Map<String, String> toKeyValuePairs(String input) {
        Map<String, String> pairs = new HashMap<String, String>();
        String[] arr = input.split("&");
        for (String s : arr) {
            String[] tmp = s.split("="); 
            pairs.put(tmp[0], tmp[1]);
        }
        return pairs;
    }
    
    private static HistoryToken extractToken(int idx, Map<String, String> pairs) {
        HistoryToken token = null;
        
        if (pairs.containsKey("t"+idx)) {
            token = new HistoryToken(URL.decode(pairs.get("t"+idx)));
            int paramIdx = 0;
            while (extractParameter(idx, paramIdx, pairs, token)) {
                paramIdx++;
            }
        }
        
        return token;
    }
    
    private static boolean extractParameter(int tokenIdx, int paramIdx, Map<String, String> pairs, HistoryToken token) {
        String paramKey = "t" + tokenIdx + "p" + paramIdx + "k";
        String valKey = "t" + tokenIdx + "p" + paramIdx + "v";
        boolean result = pairs.containsKey(paramKey);
        
        if (result) {
            token.getParameters().put(URL.decode(pairs.get(paramKey)), URL.decode(pairs.get(valKey)));
        }
        
        return result;
    }
    
}
