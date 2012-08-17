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
 * Created by Charles on 3/8/12
 */
package org.kuali.student.r2.core.class1.appointment.dao;

import org.kuali.student.r2.common.dao.GenericEntityDao;
import org.kuali.student.r2.core.class1.appointment.model.AppointmentSlotEntity;

import java.util.List;

/**
 * JPQL queries for Appointment Slots.
 *
 * @author Kuali Student Team
 */
public class AppointmentSlotDao extends GenericEntityDao<AppointmentSlotEntity>  {
    /**
     * Get list of slots given an appointment window ID in chronological order earliest to latest
     * @param apptWindowId The id of an appointment window (which slots will be fetched)
     * @return List of AppointmentSlotEntity objects in chronological order (earliests first).
     */
    public List<AppointmentSlotEntity> getSlotsByWindowIdSorted(String apptWindowId) {
        return em.createQuery("FROM AppointmentSlotEntity a WHERE a.apptWinEntity.id = :apptWindowId ORDER BY a.startDate ASC")
                .setParameter("apptWindowId", apptWindowId).getResultList();
    }
}
