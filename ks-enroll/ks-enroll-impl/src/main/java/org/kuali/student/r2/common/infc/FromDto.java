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
package org.kuali.student.r2.common.infc;

import java.util.List;

import org.kuali.student.r2.common.entity.BaseEntity;

/**
 * @author ocleirig
 *
 */
public interface FromDto<E extends BaseEntity, INFO> {
	
	/**
	 * Defines a mechanism to merge the source info data into an entity.  Possilbily resulting in orphaned entities that
	 * @param info the source data
	 * @return list of orphans that were created by the dto to entity conversion.
	 * 
	 */
	public List<E>fromDto(INFO info);
	
	

}
