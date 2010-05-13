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

package org.kuali.student.lum.lu.entity;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.kuali.student.core.entity.Attribute;

@Entity
@Table(name = "KSLU_CLU_ADMIN_ORG_ATTR")
public class CluAdminOrgAttribute extends Attribute<CluAdminOrg> {

	@ManyToOne
	@JoinColumn(name = "OWNER")
	private CluAdminOrg owner;

	@Override
	public CluAdminOrg getOwner() {
		return owner;
	}

	@Override
	public void setOwner(CluAdminOrg owner) {
		this.owner = owner;
	}
	
	

}
