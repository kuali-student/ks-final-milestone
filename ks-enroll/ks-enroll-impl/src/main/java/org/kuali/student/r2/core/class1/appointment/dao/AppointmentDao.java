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
 */
package org.kuali.student.r2.core.class1.appointment.dao;

import org.kuali.student.enrollment.dao.GenericEntityDao;
import org.kuali.student.r2.core.class1.appointment.model.AppointmentEntity;

import java.util.List;

/**
 * JPQL Queries which primarily accesses Appointment Entity
 *
 * @author Kuali Student Team
 */
public class AppointmentDao extends GenericEntityDao<AppointmentEntity> {
    /**
     * Retrieve all appointments in a given slot
     * @param apptSlotId The slot ID to retrieve by
     * @return A list of appointment entities with the slot ID
     */
    public List<AppointmentEntity> getAppointmentsBySlotId(String apptSlotId) {
        return em.createQuery("FROM AppointmentEntity a WHERE a.slotEntity.id = :apptSlotId")
                .setParameter("apptSlotId", apptSlotId).getResultList();
    }

    /**
     * Relatively fast way to count number of appointments with appointment window id
     * @param apptWinId The appointment window ID
     * @return A count of how many appointments are in a window
     */
    public Long countAppointmentsByWindowId(String apptWinId) {
        String query = "SELECT COUNT(*) FROM AppointmentSlotEntity slot, AppointmentEntity appt " +
                "WHERE slot.apptWinEntity.id = :apptWinId AND appt.slotEntity.id = slot.id";
        return (Long) em.createQuery(query).setParameter("apptWinId", apptWinId).getSingleResult();
    }
}
