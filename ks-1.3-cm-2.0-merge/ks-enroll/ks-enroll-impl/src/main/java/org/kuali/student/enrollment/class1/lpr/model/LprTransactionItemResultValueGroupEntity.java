/**
 * Copyright 2012 The Kuali Foundation
 *
 * Licensed under the the Educational Community License, Version 1.0
 * (the "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 * http://www.opensource.org/licenses/ecl1.php
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.kuali.student.enrollment.class1.lpr.model;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 * @author ocleirig
 * 
 */
@Entity
@Table(name = "KSEN_LPR_TRANS_ITEM_RVG", uniqueConstraints = { @UniqueConstraint(columnNames = {
		"LPR_TRANS_ITEM_ID", "RESULT_VAL_GRP_ID" }) })
public class LprTransactionItemResultValueGroupEntity extends
		AbstractResultValueGroupEntity {

	@ManyToOne
	@JoinColumn(name = "LPR_TRANS_ITEM_ID", nullable = false)
	private LprTransactionItemEntity lprTransactionItem;

	/**
	 * 
	 */
	public LprTransactionItemResultValueGroupEntity() {
		super();
	}

	public LprTransactionItemResultValueGroupEntity(String value) {
		super(value);
	}

	public LprTransactionItemEntity getLprTransactionItem() {
		return lprTransactionItem;
	}

	public void setLprTransactionItem(
			LprTransactionItemEntity lprTransactionItem) {
		this.lprTransactionItem = lprTransactionItem;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("LprTransactionItemResultValueGroupEntity [id=");
		builder.append(getId());
		builder.append(", resultValueGroupId=");
		builder.append(getResultValueGroupId());
		builder.append("]");
		return builder.toString();
	}

	
}
