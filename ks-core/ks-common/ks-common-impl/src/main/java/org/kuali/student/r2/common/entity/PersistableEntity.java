/*
 * Copyright 2012 The Kuali Foundation Licensed under the
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
package org.kuali.student.r2.common.entity;

import java.io.Serializable;

/**
 * Represents the base persistable entity.
 * 
 * This provides a way for the GenericEntityDao to get the Id from the loaded type data.
 * 
 * Is was created to act as a bridge between classes extending the r1 and r1 BaseEntity classes.
 * 
 * @author Kuali Student Team
 *
 */
public interface PersistableEntity<K extends Serializable> {

	/**
	 * 
	 * @return the persistence Id for the object.
	 * 
	 */
	public K getId();
}
