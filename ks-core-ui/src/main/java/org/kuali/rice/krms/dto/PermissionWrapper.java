/**
 * Copyright 2005-2013 The Kuali Foundation
 *
 * Licensed under the Educational Community License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.opensource.org/licenses/ecl2.php
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.kuali.rice.krms.dto;

import java.io.Serializable;

/**
 *
 * @author Kuali Student Team
 */
public class PermissionWrapper implements Serializable {

    private boolean addEditGroupRule;
    private boolean moveRule;
    private boolean copyCutRule;
    private boolean editLogic;
    private boolean deleteRule;

    public PermissionWrapper() {
        this.addEditGroupRule = true;
        this.moveRule = true;
        this.copyCutRule = true;
        this.editLogic = true;
        this.deleteRule = true;
    }

    public boolean isAddEditGroupRule() {
        return addEditGroupRule;
    }

    public void setAddEditGroupRule(boolean addEditGroupRule) {
        this.addEditGroupRule = addEditGroupRule;
    }

    public boolean isMoveRule() {
        return moveRule;
    }

    public void setMoveRule(boolean moveRule) {
        this.moveRule = moveRule;
    }

    public boolean isCopyCutRule() {
        return copyCutRule;
    }

    public void setCopyCutRule(boolean copyCutRule) {
        this.copyCutRule = copyCutRule;
    }

    public boolean isEditLogic() {
        return editLogic;
    }

    public void setEditLogic(boolean editLogic) {
        this.editLogic = editLogic;
    }

    public boolean isDeleteRule() {
        return deleteRule;
    }

    public void setDeleteRule(boolean deleteRule) {
        this.deleteRule = deleteRule;
    }
}
