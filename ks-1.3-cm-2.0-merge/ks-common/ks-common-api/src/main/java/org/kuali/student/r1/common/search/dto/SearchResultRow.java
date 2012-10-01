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

package org.kuali.student.r1.common.search.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
@Deprecated
@XmlAccessorType(XmlAccessType.FIELD)
public class SearchResultRow implements Serializable {
	private static final long serialVersionUID = 1L;
	private List<SearchResultCell> cells;

	public void addCell(String key, String value){
		getCells().add(new SearchResultCell(key, value));
	}
	
	public List<SearchResultCell> getCells() {
		if (cells == null) {
			cells = new ArrayList<SearchResultCell>(0);
		}
		return cells;
	}

	public void setCells(List<SearchResultCell> cells) {
		this.cells = cells;
	}

	@Override
	public String toString() {
		return "SearchResultRow[cells=" + cells + "]";
	}
}
