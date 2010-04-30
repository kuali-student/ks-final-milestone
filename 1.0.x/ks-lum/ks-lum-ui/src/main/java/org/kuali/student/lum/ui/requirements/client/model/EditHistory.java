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

package org.kuali.student.lum.ui.requirements.client.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class EditHistory implements Serializable {

    private static final long serialVersionUID = 1L;
    private List<StatementVO> histories = new ArrayList<StatementVO>();
    private static final int MAX_NUM_HISTORIES = 5;
    private int currHistoryIndex = -1;
    
    public void save(StatementVO history) {
        StatementVO cloned = null;
        List<StatementVO> newHistories = new ArrayList<StatementVO>();
        // replace out of date histories with the new history
        if (currHistoryIndex >= 0 &&
                currHistoryIndex < histories.size()) {
            for (int i = 0; i < currHistoryIndex + 1; i++) {
                newHistories.add(histories.get(i));
            }
            histories = newHistories;
        }
        currHistoryIndex++;
        cloned = ObjectClonerUtil.clone(history);
        histories.add(cloned);
        
        // delete old element
        if (histories.size() > MAX_NUM_HISTORIES) {
            histories.remove(0);
            currHistoryIndex = histories.size() - 1;
        }
    }
    
    public StatementVO getCurrentState() {
        StatementVO history = null;
        history = ObjectClonerUtil.clone(histories.get(currHistoryIndex));
        return history;
    }
    
    public StatementVO undo() {
        StatementVO history = null;
        if (isUndoable()) {
            currHistoryIndex--;
        }
        history = ObjectClonerUtil.clone(histories.get(currHistoryIndex));
        return history;
    }
    
    public StatementVO redo() {
        StatementVO history = null;
        if (isRedoable()) {
            currHistoryIndex++;
        }
        history = ObjectClonerUtil.clone(histories.get(currHistoryIndex));
        return history;
    }
    
    public boolean isUndoable() {
        return currHistoryIndex > 0;
    }
    
    public boolean isRedoable() {
        return currHistoryIndex < histories.size() - 1;
    }
    
    
}
