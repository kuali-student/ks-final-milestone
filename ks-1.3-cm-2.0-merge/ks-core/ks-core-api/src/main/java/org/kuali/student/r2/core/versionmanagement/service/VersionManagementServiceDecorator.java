/*
 * Copyright 2011 The Kuali Foundation Licensed under the
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

package org.kuali.student.r2.core.versionmanagement.service;

import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.exceptions.*;
import org.kuali.student.r2.core.versionmanagement.dto.VersionDisplayInfo;

import java.util.Date;
import java.util.List;

/**
 * @Version 2.0
 * @Author Sri komandur@uw.edu
 */
public class VersionManagementServiceDecorator implements VersionManagementService {

    private VersionManagementService nextDecorator;

    public VersionManagementService getNextDecorator() {
        return nextDecorator;
    }

    public void setNextDecorator(VersionManagementService nextDecorator) {
        this.nextDecorator = nextDecorator;
    }

    @Override
    public List<VersionDisplayInfo> getVersions(String refObjectUri, String refObjectId, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return nextDecorator.getVersions(refObjectUri, refObjectId, contextInfo);
    }

    @Override
    public VersionDisplayInfo getFirstVersion(String refObjectUri, String refObjectId, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return nextDecorator.getFirstVersion(refObjectUri, refObjectId, contextInfo);
    }

    @Override
    public VersionDisplayInfo getLatestVersion(String refObjectUri, String refObjectId, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return nextDecorator.getLatestVersion(refObjectUri, refObjectId, contextInfo);
    }

    @Override
    public VersionDisplayInfo getCurrentVersion(String refObjectUri, String refObjectId, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return nextDecorator.getCurrentVersion(refObjectUri, refObjectId, contextInfo);
    }

    @Override
    public VersionDisplayInfo getVersionBySequenceNumber(String refObjectUri, String refObjectId, Long sequence, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return nextDecorator.getVersionBySequenceNumber(refObjectUri, refObjectId, sequence, contextInfo);
    }

    @Override
    public VersionDisplayInfo getCurrentVersionOnDate(String refObjectUri, String refObjectId, Date date, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return nextDecorator.getCurrentVersionOnDate(refObjectUri, refObjectId, date, contextInfo);
    }

    @Override
    public List<VersionDisplayInfo> getVersionsInDateRange(String refObjectUri, String refObjectId, Date from, Date to, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return nextDecorator.getVersionsInDateRange(refObjectUri, refObjectId, from, to, contextInfo);
    }
}
