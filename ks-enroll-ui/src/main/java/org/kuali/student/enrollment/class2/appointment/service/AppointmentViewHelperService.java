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
 * Created by Daniel on 3/28/12
 */
package org.kuali.student.enrollment.class2.appointment.service;

import org.kuali.rice.krad.uif.service.ViewHelperService;
import org.kuali.student.enrollment.class2.appointment.dto.AppointmentWindowWrapper;
import org.kuali.student.enrollment.class2.appointment.form.RegistrationWindowsManagementForm;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.exceptions.*;

/**
 * This class //TODO ...
 *
 * @author Kuali Student Team
 */
public interface AppointmentViewHelperService extends ViewHelperService {

    public void searchForTerm(String name, String year, RegistrationWindowsManagementForm form)throws Exception;
    public void loadTermAndPeriods(String termId, RegistrationWindowsManagementForm form) throws Exception;
    public void loadPeriods(String termId, RegistrationWindowsManagementForm form) throws Exception;
    public boolean saveWindows(RegistrationWindowsManagementForm form) throws InvalidParameterException, DataValidationErrorException, MissingParameterException,
                    DoesNotExistException, ReadOnlyException, PermissionDeniedException, OperationFailedException, VersionMismatchException;
    public boolean saveApptWindow(AppointmentWindowWrapper appointmentWindowWrapper) throws InvalidParameterException, DataValidationErrorException, MissingParameterException,
                    DoesNotExistException, ReadOnlyException, PermissionDeniedException, OperationFailedException, VersionMismatchException;
    public boolean validateApptWidnow(AppointmentWindowWrapper appointmentWindowWrapper);
    public boolean validateApptWidnow(AppointmentWindowWrapper appointmentWindowWrapper, boolean validateForUniqueness);


}
