/**
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
 *
 * Created by Charles on 10/31/12
 */
package org.kuali.student.enrollment.class2.courseoffering.dao;

import org.kuali.student.enrollment.class2.courseoffering.model.SeatPoolDefinitionEntity;
import org.kuali.student.r2.common.dao.EntityDao;

/**
 * Creating an API allows for DAOs to be mocked (for unit tests that don't hit a database)
 *
 * @author Kuali Student Team
 */
public interface SeatPoolDefinitionDaoApi extends EntityDao<String, SeatPoolDefinitionEntity> {
}
