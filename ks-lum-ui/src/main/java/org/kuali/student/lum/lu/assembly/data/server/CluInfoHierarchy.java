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

package org.kuali.student.lum.lu.assembly.data.server;

import java.util.ArrayList;
import java.util.List;

import org.kuali.student.lum.lu.dto.CluInfo;

public class CluInfoHierarchy {
	public enum ModificationState {
		CREATED,
		UPDATED,
		DELETED
	}
	private CluInfo cluInfo;
	private String parentRelationType;
	private String parentRelationState;
	private List<CluInfoHierarchy> children;
	private ModificationState modificationState;
	
	public CluInfo getCluInfo() {
		return cluInfo;
	}
	public void setCluInfo(CluInfo cluInfo) {
		this.cluInfo = cluInfo;
	}
	public String getParentRelationType() {
		return parentRelationType;
	}
	public void setParentRelationType(String parentRelationType) {
		this.parentRelationType = parentRelationType;
	}
	public List<CluInfoHierarchy> getChildren() {
		if (children == null) {
			children = new ArrayList<CluInfoHierarchy>();
		}
		return children;
	}
	public void setChildren(List<CluInfoHierarchy> children) {
		this.children = children;
	}
	public ModificationState getModificationState() {
		return modificationState;
	}
	public void setModificationState(ModificationState modificationState) {
		this.modificationState = modificationState;
	}
	public String getParentRelationState() {
		return parentRelationState;
	}
	public void setParentRelationState(String parentRelationState) {
		this.parentRelationState = parentRelationState;
	}
	
}
