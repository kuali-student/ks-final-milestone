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

    private boolean addRequisiteAction;
    private boolean deleteRequisiteAction;
    private boolean compareAction;
    private boolean addRuleAction;
    private boolean editRuleAction;
    private boolean groupRuleAction;
    private boolean moveUpRuleAction;
    private boolean moveDownRuleAction;
    private boolean moveOutRuleAction;
    private boolean moveInRuleAction;
    private boolean copyRuleAction;
    private boolean cutRuleAction;
    private boolean pasteRuleAction;
    private boolean deleteRuleAction;
    private boolean editRuleLogicTab;
    private boolean updateRuleAction;

    public PermissionWrapper() {
        this.addRequisiteAction = true;
        this.deleteRequisiteAction = true;
        this.compareAction = true;
        this.addRuleAction = true;
        this.editRuleAction = true;
        this.groupRuleAction = true;
        this.moveUpRuleAction = true;
        this.moveDownRuleAction = true;
        this.moveOutRuleAction = true;
        this.moveInRuleAction = true;
        this.copyRuleAction = true;
        this.cutRuleAction = true;
        this.pasteRuleAction = true;
        this.deleteRuleAction = true;
        this.editRuleLogicTab = true;
        this.updateRuleAction = true;
    }

    public boolean isAddRequisiteAction() {
        return addRequisiteAction;
    }

    public void setAddRequisiteAction(boolean addRequisiteAction) {
        this.addRequisiteAction = addRequisiteAction;
    }

    public boolean isDeleteRequisiteAction() {
        return deleteRequisiteAction;
    }

    public void setDeleteRequisiteAction(boolean deleteRequisiteAction) {
        this.deleteRequisiteAction = deleteRequisiteAction;
    }

    public boolean isCompareAction() {
        return compareAction;
    }

    public void setCompareAction(boolean compareAction) {
        this.compareAction = compareAction;
    }

    public boolean isAddRuleAction() {
        return addRuleAction;
    }

    public void setAddRuleAction(boolean addRuleAction) {
        this.addRuleAction = addRuleAction;
    }

    public boolean isEditRuleAction() {
        return editRuleAction;
    }

    public void setEditRuleAction(boolean editRuleAction) {
        this.editRuleAction = editRuleAction;
    }

    public boolean isGroupRuleAction() {
        return groupRuleAction;
    }

    public void setGroupRuleAction(boolean groupRuleAction) {
        this.groupRuleAction = groupRuleAction;
    }

    public boolean isMoveUpRuleAction() {
        return moveUpRuleAction;
    }

    public void setMoveUpRuleAction(boolean moveUpRuleAction) {
        this.moveUpRuleAction = moveUpRuleAction;
    }

    public boolean isMoveDownRuleAction() {
        return moveDownRuleAction;
    }

    public void setMoveDownRuleAction(boolean moveDownRuleAction) {
        this.moveDownRuleAction = moveDownRuleAction;
    }

    public boolean isMoveOutRuleAction() {
        return moveOutRuleAction;
    }

    public void setMoveOutRuleAction(boolean moveOutRuleAction) {
        this.moveOutRuleAction = moveOutRuleAction;
    }

    public boolean isMoveInRuleAction() {
        return moveInRuleAction;
    }

    public void setMoveInRuleAction(boolean moveInRuleAction) {
        this.moveInRuleAction = moveInRuleAction;
    }

    public boolean isCopyRuleAction() {
        return copyRuleAction;
    }

    public void setCopyRuleAction(boolean copyRuleAction) {
        this.copyRuleAction = copyRuleAction;
    }

    public boolean isCutRuleAction() {
        return cutRuleAction;
    }

    public void setCutRuleAction(boolean cutRuleAction) {
        this.cutRuleAction = cutRuleAction;
    }

    public boolean isPasteRuleAction() {
        return pasteRuleAction;
    }

    public void setPasteRuleAction(boolean pasteRuleAction) {
        this.pasteRuleAction = pasteRuleAction;
    }

    public boolean isDeleteRuleAction() {
        return deleteRuleAction;
    }

    public void setDeleteRuleAction(boolean deleteRuleAction) {
        this.deleteRuleAction = deleteRuleAction;
    }

    public boolean isEditRuleLogicTab() {
        return editRuleLogicTab;
    }

    public void setEditRuleLogicTab(boolean editRuleLogicTab) {
        this.editRuleLogicTab = editRuleLogicTab;
    }

    public boolean isUpdateRuleAction() {
        return updateRuleAction;
    }

    public void setUpdateRuleAction(boolean updateRuleAction) {
        this.updateRuleAction = updateRuleAction;
    }
}
