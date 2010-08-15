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

package org.kuali.student.lum.lu.ui.course.client.widgets;

import org.kuali.student.core.assembly.data.Data;

public class OutlineNode<T> {
    int indentLevel = 0;

    T userObject;

    OutlineNodeModel<T> outlineModel;
    
    // TODO M4 - some way to subclass OutlineNode? 
    private Object opaque;

	private Data metaInfo;

    public void setModel(OutlineNodeModel<T> model) {
      outlineModel = model;
    }

    public void setCurrentNode() {
      outlineModel.setCurrentNode(this);
    }

    public T getUserObject() {
      return userObject;
    }

    public void setUserObject(T u) {
      userObject = u;
    }

    public void indent() {
      indentLevel++;
    }

    public void outdent() {
      indentLevel--;
    }

    public void setIndentLevel(int level) {
      indentLevel = level;
    }

    public int getIndentLevel() {
      return indentLevel;
    }

	public void setOpaque(Object opaque) {
		this.opaque = opaque;
	}

	public Object getOpaque() {
		return opaque;
	}

	public void setMetaInfo(Data metaInfo) {
		this.metaInfo=metaInfo;
	}
	public Data getMetaInfo() {
		return metaInfo;
	}
  }