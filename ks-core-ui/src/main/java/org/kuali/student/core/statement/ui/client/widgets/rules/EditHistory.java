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

package org.kuali.student.core.statement.ui.client.widgets.rules;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class EditHistory implements Serializable {

    private static final long serialVersionUID = 1L;
    private List<StatementVO> histories = new ArrayList<StatementVO>();
    private static final int MAX_NUM_HISTORIES = 10;
    private int currHistoryIndex = -1;

    public EditHistory(StatementVO origStatementVO) {
        save(origStatementVO);
    }

    public void save(StatementVO historicStmtVO) {
        List<StatementVO> newHistories = new ArrayList<StatementVO>();
        
        // replace out of date histories with the new historicStmtVO
        if (currHistoryIndex >= 0 && currHistoryIndex < histories.size()) {
            for (int i = 0; i < currHistoryIndex + 1; i++) {
                newHistories.add(histories.get(i));
            }
            histories = newHistories;
        }
        
        histories.add(RulesUtil.clone(historicStmtVO));
        currHistoryIndex++;

        // delete old element
        if (histories.size() > MAX_NUM_HISTORIES) {
            histories.remove(0);
            currHistoryIndex = histories.size() - 1;
        }
    }

    public StatementVO getLastHistoricStmtVO() {
        return RulesUtil.clone(histories.get(currHistoryIndex));
    }
    
    public StatementVO getSafeHistoricStmtVO(int index) {
        index = (index < 0 ? 0 : (index < histories.size() ? index : histories.size()));
        return RulesUtil.clone(histories.get(index));
    }
    
    public StatementVO undo() {
        currHistoryIndex--;
        currHistoryIndex = (currHistoryIndex < 0 ? -1 : currHistoryIndex);
        StatementVO lastHistoricStmtVO = getSafeHistoricStmtVO(currHistoryIndex);
        return lastHistoricStmtVO;
    }
    
    public StatementVO redo() {
        currHistoryIndex++;
        currHistoryIndex = (currHistoryIndex < histories.size() ? currHistoryIndex : histories.size());
        StatementVO futureHistoricStmtVO = getSafeHistoricStmtVO(currHistoryIndex);
        return futureHistoricStmtVO;
    }

    public boolean isUndoable() {
        return currHistoryIndex > 0;
    }
    
    public boolean isRedoable() {
        return currHistoryIndex < histories.size() - 1;
    }   
}
