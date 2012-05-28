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

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.kuali.student.common.util.UUIDHelper;

/**
 * @author ocleirig
 * 
 */
@Entity
@Table(name = "KSEN_LPR_RESULT_VAL_GRP",uniqueConstraints = {
	    @UniqueConstraint(columnNames = {"LPR_ID", "RESULT_VAL_GRP_ID"})})
public class LprResultValueGroupEntity extends AbstractResultValueGroupEntity {

	@ManyToOne
	@JoinColumn(name = "LPR_ID", nullable=false)
	private LprEntity lpr;
	

	public LprResultValueGroupEntity() {
		super();
	}



	public LprEntity getLpr() {
		return lpr;
	}



	public void setLpr(LprEntity lpr) {
		this.lpr = lpr;
	}
	
	

	

}
