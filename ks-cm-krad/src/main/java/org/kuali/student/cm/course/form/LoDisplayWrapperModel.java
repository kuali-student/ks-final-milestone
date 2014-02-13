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

/**
 * A model that is used for displaying the {@link LoDisplayInfoWrapper} instances. This
 * model determines whether the item in a list can be indented, moved left ('outdented'),
 * moved up or moved down and proceeds to make the change if possible.
 * 
 * @author OpenCollab/rSmart KRAD CM Conversion Alliance!
 */
public class LoDisplayWrapperModel implements java.io.Serializable {

    private List<LoDisplayInfoWrapper> loWrappers;

    private LoDisplayInfoWrapper currentLoWrapper;

    public void clearLoWrappers() {
        loWrappers = new ArrayList<LoDisplayInfoWrapper>(0);
    }

    public List<LoDisplayInfoWrapper> getLoWrappers() {
        if (loWrappers == null) {
            loWrappers = new ArrayList<LoDisplayInfoWrapper>(0);
        }
        return loWrappers;
    }

    public void addLoWrapper(LoDisplayInfoWrapper loWrapper) {
        loWrappers.add(loWrapper);
    }

    public void setCurrentLoWrapper(LoDisplayInfoWrapper loWrapper) {
        currentLoWrapper = loWrapper;
    }

    public void moveUpCurrent() {
        if (!this.isMoveUpable()) {
            return;
        }
        List<LoDisplayInfoWrapper> siblingList = getSiblingList();
        int indexInSibling = siblingList.indexOf(currentLoWrapper);
        LoDisplayInfoWrapper nextLoDisplayInfoWrapperInSibling = siblingList.get(indexInSibling - 1);

        List<LoDisplayInfoWrapper> childList = getChildList(currentLoWrapper);
        childList.add(0, currentLoWrapper);// add parent
        for (int i = 0; i < childList.size(); i++) {
            LoDisplayInfoWrapper childLoDisplayInfoWrapper = childList.get(i);
            loWrappers.remove(childLoDisplayInfoWrapper);
        }

        int moveToIndex = loWrappers.indexOf(nextLoDisplayInfoWrapperInSibling);

        for (int i = 0; i < childList.size(); i++) {
            LoDisplayInfoWrapper childLoDisplayInfoWrapper = childList.get(i);
            loWrappers.add(moveToIndex + i, childLoDisplayInfoWrapper);
        }
    }

    public void moveDownCurrent() {
        if (!this.isMoveDownable()) {
            return;
        }
        int index = loWrappers.indexOf(currentLoWrapper);
        if (index == -1 || index == loWrappers.size() - 1) {
            return;
        }
        List<LoDisplayInfoWrapper> siblingList = getSiblingList();
        int indexInSibling = siblingList.indexOf(currentLoWrapper);
        LoDisplayInfoWrapper nextLoDisplayInfoWrapperInSibling = siblingList.get(indexInSibling + 1);

        List<LoDisplayInfoWrapper> childList = getChildList(currentLoWrapper);
        childList.add(0, currentLoWrapper);// add parent
        for (int i = 0; i < childList.size(); i++) {
            LoDisplayInfoWrapper childLoDisplayInfoWrapper = childList.get(i);
            loWrappers.remove(childLoDisplayInfoWrapper);
        }

        List<LoDisplayInfoWrapper> nextLoDisplayInfoWrapperChildList = getChildList(nextLoDisplayInfoWrapperInSibling);
        int moveToIndex = -1;
        if (nextLoDisplayInfoWrapperChildList.size() != 0) {
            moveToIndex = loWrappers.indexOf(nextLoDisplayInfoWrapperChildList.get(nextLoDisplayInfoWrapperChildList
                    .size() - 1));
        } else {
            moveToIndex = loWrappers.indexOf(nextLoDisplayInfoWrapperInSibling);
        }

        for (int i = 0; i < childList.size(); i++) {
            LoDisplayInfoWrapper childLoDisplayInfoWrapper = childList.get(i);
            loWrappers.add(moveToIndex + 1 + i, childLoDisplayInfoWrapper);
        }

    }

    public void indentCurrent() {
        if (this.isIndentable()) {
            currentLoWrapper.indent();
        }
    }

    public void outdentCurrent() {
        if (this.isOutdentable()) {
            List<LoDisplayInfoWrapper> childList = getChildList(currentLoWrapper);
            childList.add(0, currentLoWrapper);// add parent
            for (LoDisplayInfoWrapper childLoDisplayInfoWrapper : childList) {
                childLoDisplayInfoWrapper.outdent();
            }
        }
    }

    private List<LoDisplayInfoWrapper> getChildList(LoDisplayInfoWrapper loWrapper) {
        int index = loWrappers.indexOf(loWrapper);
        List<LoDisplayInfoWrapper> childList = new ArrayList<LoDisplayInfoWrapper>();
        for (int i = index + 1; i < loWrappers.size(); i++) {
            if (loWrappers.get(i).getIndentLevel() > loWrapper.getIndentLevel()) {
                childList.add(loWrappers.get(i));
            } else {
                break;
            }
        }
        return childList;
    }

    private List<LoDisplayInfoWrapper> getSiblingList() {
        List<LoDisplayInfoWrapper> siblingList = new ArrayList<LoDisplayInfoWrapper>();
        int index = loWrappers.indexOf(currentLoWrapper);
        // if first level
        if (currentLoWrapper.getIndentLevel() == 0) {
            for (int i = 0; i < loWrappers.size(); i++) {
                if (loWrappers.get(i).getIndentLevel() == 0) {
                    siblingList.add(loWrappers.get(i));
                }
            }
            return siblingList;
        }
        // not first level
        // get parent first and then get all Siblings
        LoDisplayInfoWrapper parentLoDisplayInfoWrapper = null;
        for (int i = index - 1; i >= 0; i--) {
            if (loWrappers.get(i).getIndentLevel() - currentLoWrapper.getIndentLevel() == -1) {
                parentLoDisplayInfoWrapper = loWrappers.get(i);
                break;
            }
        }
        int parentIndex = loWrappers.indexOf(parentLoDisplayInfoWrapper);
        for (int i = parentIndex + 1; i < loWrappers.size(); i++) {
            if (loWrappers.get(i).getIndentLevel() - parentLoDisplayInfoWrapper.getIndentLevel() == 1) {
                siblingList.add(loWrappers.get(i));
            } else if (loWrappers.get(i).getIndentLevel() == parentLoDisplayInfoWrapper.getIndentLevel()) {
                break;
            }
        }
        return siblingList;
    }

    private boolean isIndentable() {
        int index = loWrappers.indexOf(currentLoWrapper);
        if (index == 0) {
            return false;
        }
        // if current node is the only child

        List<LoDisplayInfoWrapper> siblingList = getSiblingList();
        if (siblingList.size() == 1) {
            return false;
        }
        //first kid
        int indexInSiblings = siblingList.indexOf(currentLoWrapper);
        if (indexInSiblings == 0) {
            return false;
        }

        return true;
    }

    private boolean isOutdentable() {
        if (currentLoWrapper.getIndentLevel() == 0) {// first level
            return false;
        }
        return true;
    }

    private boolean isMoveUpable() {
        List<LoDisplayInfoWrapper> list = getSiblingList();
        if (list.size() == 1) { // only child
            return false;
        }
        int index = list.indexOf(currentLoWrapper);
        if (index == 0) {// first child
            return false;
        }
        return true;
    }

    private boolean isMoveDownable() {
        List<LoDisplayInfoWrapper> list = getSiblingList();
        if (list.size() == 0) { // only child
            return false;
        }
        int index = list.indexOf(currentLoWrapper);
        if (index == list.size() - 1) {// last child
            return false;
        }
        return true;
    }

}