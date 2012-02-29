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

package org.kuali.student.lum.common.client.lo;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.event.dom.client.ChangeHandler;

public 
class OutlineNodeModel<T> {
	private ArrayList<OutlineNode<T>> outlineNodeList = new ArrayList<OutlineNode<T>>();

	private final ArrayList<ChangeHandler> changeHandlerList = new ArrayList<ChangeHandler>();

	private OutlineNode<T> currentNode;

	public void clearNodes(){
		outlineNodeList = new ArrayList<OutlineNode<T>>();
	}
	public void setCurrentNode(OutlineNode<T> aNode) {
		currentNode = aNode;
	}

	public void addChangeHandler(ChangeHandler ch) {
		changeHandlerList.add(ch);
	}

	private void fireChangeEvents() {
		for (ChangeHandler ch : changeHandlerList) {
			ch.onChange(null);
		}
	}

	public void moveUpCurrent() {
		if (this.isMoveUpable() == false) {
			return;
		}
		List<OutlineNode<T>> siblingList = getSiblingList();
		int indexInSibling = siblingList.indexOf(currentNode);
		OutlineNode<T> nextNodeInSibling = siblingList.get(indexInSibling - 1);

		List<OutlineNode<T>> childList = getChildList(currentNode);
		childList.add(0, currentNode);// add parent
		for (int i = 0; i < childList.size(); i++) {
			OutlineNode<T> aNode = childList.get(i);
			outlineNodeList.remove(aNode);
		}

		int moveToIndex = outlineNodeList.indexOf(nextNodeInSibling);

		for (int i = 0; i < childList.size(); i++) {
			OutlineNode<T> aNode = childList.get(i);
			outlineNodeList.add(moveToIndex + i, aNode);
		}
		fireChangeEvents();
	}
	public void moveDownCurrent() {
		if (this.isMoveDownable() == false) {
			return;
		}
		int index = outlineNodeList.indexOf(currentNode);
		if (index == -1 || index == outlineNodeList.size() - 1) {
			return;
		}
		List<OutlineNode<T>> siblingList = getSiblingList();
		int indexInSibling = siblingList.indexOf(currentNode);
		OutlineNode<T> nextNodeInSibling = siblingList.get(indexInSibling + 1);

		List<OutlineNode<T>> childList = getChildList(currentNode);
		childList.add(0, currentNode);// add parent
		for (int i = 0; i < childList.size(); i++) {
			OutlineNode<T> aNode = childList.get(i);
			outlineNodeList.remove(aNode);
		}

		List<OutlineNode<T>> nextNodeChildList = getChildList(nextNodeInSibling);
		int moveToIndex = -1;
		if(nextNodeChildList.size() != 0){
			moveToIndex = outlineNodeList.indexOf(nextNodeChildList.get(nextNodeChildList.size()-1));
		}else{
			moveToIndex =outlineNodeList.indexOf(nextNodeInSibling);
		}

		for (int i = 0; i < childList.size(); i++) {
			OutlineNode<T> aNode = childList.get(i);
			outlineNodeList.add(moveToIndex + 1 + i, aNode);
		}

		fireChangeEvents();
	}

	public void indentCurrent() {
		if (this.isIndentable()) {
			currentNode.indent();
			fireChangeEvents();
		}
	}

	public void outdentCurrent() {
		if (this.isOutdentable()) {
			List<OutlineNode<T>> childList = getChildList(currentNode);
			childList.add(0, currentNode);// add parent
			for(OutlineNode<T> aNode:childList){
				aNode.outdent();
			}
			fireChangeEvents();
		}
	}

	public void deleteCurrent() {
//		if (this.isDeletable()) {
			List<OutlineNode<T>> childList = getChildList(currentNode);
			childList.add(0, currentNode);// add parent
			for (int i = 0; i < childList.size(); i++) {
				OutlineNode<T> aNode = childList.get(i);
				outlineNodeList.remove(aNode);
			}
			fireChangeEvents();
//		}
	}

	public void addPeer() {
		int index = outlineNodeList.indexOf(currentNode);
		OutlineNode<T> aNode = new OutlineNode<T>();
		// aNode.setUserObject(new TextBox());
		aNode.setIndentLevel(currentNode.getIndentLevel());
		outlineNodeList.add(index + 1, aNode);
		fireChangeEvents();
	}

	public void addChild() {
		int index = outlineNodeList.indexOf(currentNode);
		OutlineNode<T> aNode = new OutlineNode<T>();
		// aNode.setUserObject(new TextBox());
		aNode.setIndentLevel(currentNode.getIndentLevel() + 1);
		outlineNodeList.add(index + 1, aNode);
		fireChangeEvents();
	}

	public void addOutlineNode(OutlineNode<T> aNode) {
		outlineNodeList.add(aNode);
	}

	@SuppressWarnings("unchecked")
	public OutlineNode<T>[] toOutlineNodes() {
		return outlineNodeList.toArray(new OutlineNode[outlineNodeList.size()]);
	}

	public List<OutlineNode<T>> getOutlineNodes() {
		return outlineNodeList;
	}

	public List<OutlineNode<T>> getChildList(OutlineNode<T> aNode) {
		int index = outlineNodeList.indexOf(aNode);
		List<OutlineNode<T>> childList = new ArrayList<OutlineNode<T>>();
		for (int i = index + 1; i < outlineNodeList.size(); i++) {
			if (outlineNodeList.get(i).getIndentLevel() > aNode.getIndentLevel()) {
				childList.add(outlineNodeList.get(i));
			} else {
				break;
			}
		}
		return childList;
	}

	public List<OutlineNode<T>> getSiblingList() {
		List<OutlineNode<T>> siblingList = new ArrayList<OutlineNode<T>>();
		int index = outlineNodeList.indexOf(currentNode);
		// if first level
		if (currentNode.getIndentLevel() == 0) {
			for (int i = 0; i < outlineNodeList.size(); i++) {
				if (outlineNodeList.get(i).getIndentLevel() == 0) {
					siblingList.add(outlineNodeList.get(i));
				}
			}
			return siblingList;
		}
		// not first level
		// get parent first and then get all Siblings
		OutlineNode<T> parentNode = null;
		for (int i = index - 1; i >= 0; i--) {
			if (outlineNodeList.get(i).getIndentLevel() - currentNode.getIndentLevel() == -1) {
				parentNode = outlineNodeList.get(i);
				break;
			}
		}
		int parentIndex = outlineNodeList.indexOf(parentNode);
		for (int i = parentIndex + 1; i < outlineNodeList.size(); i++) {
			if (outlineNodeList.get(i).getIndentLevel() - parentNode.getIndentLevel() == 1) {
				siblingList.add(outlineNodeList.get(i));
			} else if (outlineNodeList.get(i).getIndentLevel() == parentNode.getIndentLevel()) {
				break;
			}
		}
		return siblingList;
	}

	public boolean isIndentable() {
		int index = outlineNodeList.indexOf(currentNode);
		if (index == 0) {
			return false;
		}
		// if current node is the only child

		List<OutlineNode<T>> siblingList = getSiblingList();
		if (siblingList.size() == 1) {
			return false;
		}
		//first kid
		int indexInSiblings = siblingList.indexOf(currentNode);
		if(indexInSiblings == 0){
			return false;
		}

		return true;
	}

	public boolean isOutdentable() {
		if (currentNode.getIndentLevel() == 0) {// first level
			return false;
		}
		return true;
	}

	public boolean isMoveUpable() {
		List<OutlineNode<T>> list = getSiblingList();
		if (list.size() == 1) { // only child
			return false;
		}
		int index = list.indexOf(currentNode);
		if (index == 0) {// first child
			return false;
		}
		return true;
	}

	public boolean isMoveDownable() {
		List<OutlineNode<T>> list = getSiblingList();
		if (list.size() == 0) { // only child
			return false;
		}
		int index = list.indexOf(currentNode);
		if (index == list.size() - 1) {// last child
			return false;
		}
		return true;
	}

	public boolean isDeletable() {
//		if(outlineNodeList.size() == 1){
//			return false;
//		}
		return true;
	}
}