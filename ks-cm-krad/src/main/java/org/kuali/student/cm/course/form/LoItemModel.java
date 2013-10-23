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

package org.kuali.student.cm.course.form;

import java.util.ArrayList;
import java.util.List;

public class LoItemModel {

    private List<LoItem> loItems;

    private LoItem currentLoItem;

    public void clearLoItems() {
        loItems = new ArrayList<LoItem>();
    }

    public List<LoItem> getLoItems() {
        if (loItems == null) {
            loItems = new ArrayList<LoItem>();
        }
        return loItems;
    }

    public void addLoItem(LoItem loItem) {
        loItems.add(loItem);
    }

    public void setCurrentLoItem(LoItem loItem) {
        currentLoItem = loItem;
    }

    public void moveUpCurrent() {
        if (this.isMoveUpable() == false) {
            return;
        }
        List<LoItem> siblingList = getSiblingList();
        int indexInSibling = siblingList.indexOf(currentLoItem);
        LoItem nextLoItemInSibling = siblingList.get(indexInSibling - 1);

        List<LoItem> childList = getChildList(currentLoItem);
        childList.add(0, currentLoItem);// add parent
        for (int i = 0; i < childList.size(); i++) {
            LoItem childLoItem = childList.get(i);
            loItems.remove(childLoItem);
        }

        int moveToIndex = loItems.indexOf(nextLoItemInSibling);

        for (int i = 0; i < childList.size(); i++) {
            LoItem childLoItem = childList.get(i);
            loItems.add(moveToIndex + i, childLoItem);
        }
    }

    public void moveDownCurrent() {
        if (this.isMoveDownable() == false) {
            return;
        }
        int index = loItems.indexOf(currentLoItem);
        if (index == -1 || index == loItems.size() - 1) {
            return;
        }
        List<LoItem> siblingList = getSiblingList();
        int indexInSibling = siblingList.indexOf(currentLoItem);
        LoItem nextLoItemInSibling = siblingList.get(indexInSibling + 1);

        List<LoItem> childList = getChildList(currentLoItem);
        childList.add(0, currentLoItem);// add parent
        for (int i = 0; i < childList.size(); i++) {
            LoItem childLoItem = childList.get(i);
            loItems.remove(childLoItem);
        }

        List<LoItem> nextLoItemChildList = getChildList(nextLoItemInSibling);
        int moveToIndex = -1;
        if (nextLoItemChildList.size() != 0) {
            moveToIndex = loItems.indexOf(nextLoItemChildList.get(nextLoItemChildList.size() - 1));
        } else {
            moveToIndex = loItems.indexOf(nextLoItemInSibling);
        }

        for (int i = 0; i < childList.size(); i++) {
            LoItem childLoItem = childList.get(i);
            loItems.add(moveToIndex + 1 + i, childLoItem);
        }

    }

    public void indentCurrent() {
        if (this.isIndentable()) {
            currentLoItem.indent();
        }
    }

    public void outdentCurrent() {
        if (this.isOutdentable()) {
            List<LoItem> childList = getChildList(currentLoItem);
            childList.add(0, currentLoItem);// add parent
            for (LoItem childLoItem : childList) {
                childLoItem.outdent();
            }
        }
    }

    private List<LoItem> getChildList(LoItem loItem) {
        int index = loItems.indexOf(loItem);
        List<LoItem> childList = new ArrayList<LoItem>();
        for (int i = index + 1; i < loItems.size(); i++) {
            if (loItems.get(i).getIndentLevel() > loItem.getIndentLevel()) {
                childList.add(loItems.get(i));
            } else {
                break;
            }
        }
        return childList;
    }

    private List<LoItem> getSiblingList() {
        List<LoItem> siblingList = new ArrayList<LoItem>();
        int index = loItems.indexOf(currentLoItem);
        // if first level
        if (currentLoItem.getIndentLevel() == 0) {
            for (int i = 0; i < loItems.size(); i++) {
                if (loItems.get(i).getIndentLevel() == 0) {
                    siblingList.add(loItems.get(i));
                }
            }
            return siblingList;
        }
        // not first level
        // get parent first and then get all Siblings
        LoItem parentLoItem = null;
        for (int i = index - 1; i >= 0; i--) {
            if (loItems.get(i).getIndentLevel() - currentLoItem.getIndentLevel() == -1) {
                parentLoItem = loItems.get(i);
                break;
            }
        }
        int parentIndex = loItems.indexOf(parentLoItem);
        for (int i = parentIndex + 1; i < loItems.size(); i++) {
            if (loItems.get(i).getIndentLevel() - parentLoItem.getIndentLevel() == 1) {
                siblingList.add(loItems.get(i));
            } else if (loItems.get(i).getIndentLevel() == parentLoItem.getIndentLevel()) {
                break;
            }
        }
        return siblingList;
    }

    private boolean isIndentable() {
        int index = loItems.indexOf(currentLoItem);
        if (index == 0) {
            return false;
        }
        // if current node is the only child

        List<LoItem> siblingList = getSiblingList();
        if (siblingList.size() == 1) {
            return false;
        }
        //first kid
        int indexInSiblings = siblingList.indexOf(currentLoItem);
        if (indexInSiblings == 0) {
            return false;
        }

        return true;
    }

    private boolean isOutdentable() {
        if (currentLoItem.getIndentLevel() == 0) {// first level
            return false;
        }
        return true;
    }

    private boolean isMoveUpable() {
        List<LoItem> list = getSiblingList();
        if (list.size() == 1) { // only child
            return false;
        }
        int index = list.indexOf(currentLoItem);
        if (index == 0) {// first child
            return false;
        }
        return true;
    }

    private boolean isMoveDownable() {
        List<LoItem> list = getSiblingList();
        if (list.size() == 0) { // only child
            return false;
        }
        int index = list.indexOf(currentLoItem);
        if (index == list.size() - 1) {// last child
            return false;
        }
        return true;
    }

}