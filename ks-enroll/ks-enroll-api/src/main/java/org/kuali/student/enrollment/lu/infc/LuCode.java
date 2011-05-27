/*
 * Copyright 2011 The Kuali Foundation
 *
 * Licensed under the Educational Community License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may	obtain a copy of the License at
 *
 * 	http://www.osedu.org/licenses/ECL-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.kuali.student.enrollment.lu.infc;

import org.kuali.student.r2.common.infc.IdEntity;

/**
 * Detailed information about a single LUI.
 */
public interface LuCode extends IdEntity {

	/**
	 * Name: Lu Code Code identifier/name for the LU.
	 */
	public String getLuCode();

	/**
	 * Name: Lu Id Unique identifier for the Learning Unit (CLU) of which this
	 * is an instance.
	 */
	public String getId();

	/**
	 * Name: Lu Type The Lu code's value.
	 */
	public String getType();

	/**
	 * Name: Lu value The Lu code's value.
	 */
	public String getValue();

}
