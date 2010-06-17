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

package org.kuali.student.core.search.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
@XmlAccessorType(XmlAccessType.FIELD)
public class JoinCriteriaInfo implements Serializable {
	private static final long serialVersionUID = 1L;
	public enum JoinType {AND,OR,NOT};
	
	private JoinType joinType;
	private List<JoinCriteriaInfo> joinCriteria;
	private List<JoinComparisonInfo> comparisons;
	public JoinType getJoinType() {
		return joinType;
	}
	public void setJoinType(JoinType joinType) {
		this.joinType = joinType;
	}
	public List<JoinComparisonInfo> getComparisons() {
		if(comparisons==null){
			comparisons = new ArrayList<JoinComparisonInfo>();
		}
		return comparisons;
	}
	public void setComparisons(List<JoinComparisonInfo> comparisons) {
		this.comparisons = comparisons;
	}
	public List<JoinCriteriaInfo> getJoinCriteria() {
		if(joinCriteria==null){
			joinCriteria = new ArrayList<JoinCriteriaInfo>();
		}
		return joinCriteria;
	}
	public void setJoinCriteria(List<JoinCriteriaInfo> joinCriteria) {
		this.joinCriteria = joinCriteria;
	}
	
}
