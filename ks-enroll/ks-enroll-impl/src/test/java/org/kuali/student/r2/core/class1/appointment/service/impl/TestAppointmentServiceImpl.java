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
 * Created by Charles on 2/28/12
 */
package org.kuali.student.r2.core.class1.appointment.service.impl;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.exceptions.*;
import org.kuali.student.r2.core.appointment.dto.AppointmentWindowInfo;

/**
 * This class //TODO ...
 *
 * @author Kuali Student Team
 */
public class TestAppointmentServiceImpl {
    private AppointmentServiceImpl apptServiceImpl;
    private ContextInfo contextInfo;
    private AppointmentWindowInfo apptWindowInfo;
    @Before
    public void before() {
        apptServiceImpl = new AppointmentServiceImpl();
        contextInfo = new ContextInfo();
        apptWindowInfo = new AppointmentWindowInfo();
    }
    
    @Test
    public void testExists() {
        // Setup
        String id = "123";
        apptWindowInfo.setId(id);
        //
        try {
            apptServiceImpl.createAppointmentWindow(id, apptWindowInfo, contextInfo);
            // Now try to get it back
            AppointmentWindowInfo info = apptServiceImpl.getAppointmentWindow(id, contextInfo);
            assertNotNull(info);
            assertEquals(id, info.getId());

        } catch (DataValidationErrorException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (DoesNotExistException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (InvalidParameterException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (MissingParameterException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (OperationFailedException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (PermissionDeniedException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (ReadOnlyException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

    }
}
