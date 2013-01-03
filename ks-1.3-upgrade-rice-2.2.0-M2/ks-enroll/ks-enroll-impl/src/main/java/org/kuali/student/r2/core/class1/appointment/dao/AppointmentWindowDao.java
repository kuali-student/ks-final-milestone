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
 * Created by Charles on 3/1/12
 */
package org.kuali.student.r2.core.class1.appointment.dao;

import org.kuali.student.enrollment.dao.GenericEntityDao;
import org.kuali.student.r2.core.class1.appointment.model.AppointmentWindowEntity;

import java.util.List;

/**
 * JPQL queries for AppointmentWindowEntity
 *
 * @author Kuali Student Team
 */
public class AppointmentWindowDao extends GenericEntityDao<AppointmentWindowEntity> {
    /**
     * Fetch all appointment window given a period milestone ID
     * @param periodMilestoneId The period milestone ID
     * @return A list of AppointmentWindowEntity objects with a period milestone
     */
    public List<AppointmentWindowEntity> getAppointmentWindowsByMilestoneId(String periodMilestoneId) {
        return em.createQuery("FROM AppointmentWindowEntity a WHERE a.periodMilestoneId = :periodMilestoneId")
                .setParameter("periodMilestoneId", periodMilestoneId).getResultList();
    }
}
