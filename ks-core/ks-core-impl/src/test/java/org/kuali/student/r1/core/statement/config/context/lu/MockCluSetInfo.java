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

package org.kuali.student.r1.core.statement.config.context.lu;

import java.util.ArrayList;
import java.util.List;

/**
 * {@link MockCluSetInfo} wrapper class.
 */
public class MockCluSetInfo {

	private String id;
	private List<MockCluInfo> cluList;
    private List<String> cluIds = new ArrayList<String>();

	public MockCluSetInfo() {
	}
	
	public MockCluSetInfo(String cluSetId, List<MockCluInfo> cluList) {
		this.id = cluSetId;
		this.cluList = cluList;
		for(MockCluInfo clu : cluList) {
			cluIds.add(clu.getId());
		}
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public List<MockCluInfo> getCluList() {
		return this.cluList;
	}

	public void setCluList(List<MockCluInfo> cluList) {
		this.cluList = cluList;
	}
	
    public List<String> getCluIds() {
        if (cluIds == null) {
            cluIds = new ArrayList<String>();
        }
        return cluIds;
    }

	public void setCluIds(List<String> cluIds) {
		this.cluIds = cluIds;
	}

    public String getCluAsShortName(int index) {
		return this.cluList.get(index).getShortName();
	}
	
	public String getCluAsCode(int index) {
		return this.cluList.get(index).getCode();
	}
	
	public String getCluSetAsShortName() {
		StringBuilder sb = new StringBuilder();
		for(MockCluInfo clu : this.cluList) {
			sb.append(clu.getShortName());
			sb.append(", ");
		}
		return getString(sb);
	}

	public String getCluSetAsLongName() {
		StringBuilder sb = new StringBuilder();
		for(MockCluInfo clu : this.cluList) {
			sb.append(clu.getShortName());
			sb.append(", ");
		}
		return getString(sb);
	}

	public String getCluSetAsCode() {
		StringBuilder sb = new StringBuilder();
		for(MockCluInfo clu : this.cluList) {
			sb.append(clu.getCode());
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
		return "id=" + this.id;
	}
}
