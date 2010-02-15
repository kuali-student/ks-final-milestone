/*
 * Copyright 2009 The Kuali Foundation Licensed under the
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
package org.kuali.student.lum.nlt.naturallanguage.util;

import java.util.List;

import org.kuali.student.lum.lu.dto.CluInfo;

/**
 * {@link CluSetInfo} wrapper class.
 */
public class CustomCluSetInfo {

	private String cluSetId;
	private List<CluInfo> cluList;
	
	public CustomCluSetInfo(String cluSetId, List<CluInfo> cluList) {
		this.cluSetId = cluSetId;
		this.cluList = cluList;
	}

	public String getCluSetId() {
		return cluSetId;
	}

	public List<CluInfo> getCluList() {
		return this.cluList;
	}
	
	public String getCluAsShortName(int index) {
		return this.cluList.get(index).getOfficialIdentifier().getShortName();
	}
	
	public String getCluAsCode(int index) {
		return this.cluList.get(index).getOfficialIdentifier().getCode();
	}
	
	public String getCluSetAsShortName() {
		StringBuilder sb = new StringBuilder();
		for(CluInfo clu : this.cluList) {
			sb.append(clu.getOfficialIdentifier().getShortName());
			sb.append(", ");
		}
		return getString(sb);
	}

	public String getCluSetAsLongName() {
		StringBuilder sb = new StringBuilder();
		for(CluInfo clu : this.cluList) {
			sb.append(clu.getOfficialIdentifier().getShortName());
			sb.append(", ");
		}
		return getString(sb);
	}

	public String getCluSetAsCode() {
		StringBuilder sb = new StringBuilder();
		for(CluInfo clu : this.cluList) {
			sb.append(clu.getOfficialIdentifier().getCode());
			sb.append(", ");
		}
		return getString(sb);
	}
	
	private String getString(StringBuilder sb) {
		return (sb.length() == 0 ? "No Clus available in CluSet" : sb.toString().substring(0, sb.toString().length() - 2));
	}
	
	public String toString() {
		if(this.cluList == null) {
			return "Null CluSet";
		}
		return "id=" + this.cluSetId;
	}
}
