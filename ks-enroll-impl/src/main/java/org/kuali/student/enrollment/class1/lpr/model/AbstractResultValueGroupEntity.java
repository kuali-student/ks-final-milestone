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
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;

import org.kuali.student.common.util.UUIDHelper;

/**
 * 
 * A base class for tiny entities that refer the the ResultValueGroup class from lrc.
 * 
 * Foreign keys are not used because it crosses the service boundaries.
 * 
 * @author ocleirig
 *
 */
@MappedSuperclass
public abstract class AbstractResultValueGroupEntity {

	@Id
	@Column(name = "ID")
	private String id;

	@Column (name="RESULT_VAL_GRP_ID", nullable=false)
	private String resultValueGroupId;
	
	@PrePersist
	public void prePersist() {
		this.id = UUIDHelper.genStringUUID();
	}
	
	public AbstractResultValueGroupEntity() {
	}
	
	public AbstractResultValueGroupEntity(String resultValueGroupId) {
		this();
		
		setResultValueGroupId(resultValueGroupId);
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getResultValueGroupId() {
		return resultValueGroupId;
	}

	public void setResultValueGroupId(String resultValueGroupId) {
		this.resultValueGroupId = resultValueGroupId;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("AbstractResultValueGroupEntity [id=");
		builder.append(id);
		builder.append(", resultValueGroupId=");
		builder.append(resultValueGroupId);
		builder.append("]");
		return builder.toString();
	}
	
	

}
