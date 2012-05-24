/*
 * Copyright 2011 The Kuali Foundation Licensed under the Educational Community
 * License, Version 2.0 (the "License"); you may not use this file except in
 * compliance with the License. You may obtain a copy of the License at
 * http://www.osedu.org/licenses/ECL-2.0 Unless required by applicable law or
 * agreed to in writing, software distributed under the License is distributed
 * on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
 * express or implied. See the License for the specific language governing
 * permissions and limitations under the License.
 */
package org.kuali.student.enrollment.class1.lpr.service.impl.mock;

import java.util.List;

import org.kuali.rice.core.api.criteria.QueryByCriteria;
import org.kuali.student.enrollment.lpr.dto.LprTransactionInfo;
import org.kuali.student.enrollment.lpr.dto.LprInfo;
import org.kuali.student.enrollment.lpr.dto.LprTransactionItemInfo;
import org.kuali.student.enrollment.lpr.service.LprService;
import org.kuali.student.enrollment.lui.service.LuiService;
import org.kuali.student.r2.common.dto.BulkStatusInfo;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.StatusInfo;
import org.kuali.student.r2.common.dto.ValidationResultInfo;
import org.kuali.student.r2.common.exceptions.AlreadyExistsException;
import org.kuali.student.r2.common.exceptions.DataValidationErrorException;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.common.exceptions.ReadOnlyException;
import org.kuali.student.r2.common.exceptions.VersionMismatchException;
import org.kuali.student.r2.common.infc.HoldsLprService;
import org.kuali.student.r2.common.infc.HoldsLuiService;

/**
 * @author nwright
 */
public class LprServiceMockImpl implements LprService, HoldsLuiService {

    private LuiService luiService;

    @Override
    public LuiService getLuiService() {
        return luiService;
    }

    @Override
    public void setLuiService(LuiService luiService) {
        this.luiService = luiService;
    }

    @Override
    public String createLpr(String personId, String luiId, String lprTypeKey, LprInfo lprInfo, ContextInfo contextInfo) throws DataValidationErrorException,
            DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException, ReadOnlyException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public LprTransactionInfo createLprTransaction(String lprTransactionType, LprTransactionInfo lprTransactionInfo, ContextInfo contextInfo) throws DataValidationErrorException,
            DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public LprTransactionInfo createLprTransactionFromExisting(String lprTransactionId, ContextInfo contextInfo) throws DataValidationErrorException,
            DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<String> createLprsForLui(String luiId, String lprTypeKey, List<LprInfo> lprInfos, ContextInfo contextInfo) throws DataValidationErrorException,
            DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException, ReadOnlyException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<BulkStatusInfo> createLprsForPerson(String personId, String lprTypeKey, List<LprInfo> lprInfos, ContextInfo contextInfo) throws DataValidationErrorException,
            DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException, ReadOnlyException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public StatusInfo deleteLpr(String lprId, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public StatusInfo deleteLprTransaction(String lprTransactionId, ContextInfo contextInfo) throws DoesNotExistException,
            InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public LprInfo getLpr(String lprId, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public LprTransactionInfo getLprTransaction(String lprTransactionId, ContextInfo contextInfo) throws DoesNotExistException,
            InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<LprTransactionItemInfo> getLprTransactionItemsByLui(String luiId, ContextInfo contextInfo) throws InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<LprTransactionItemInfo> getLprTransactionItemsByPersonAndLui(String personId, String luiId, ContextInfo contextInfo) throws DoesNotExistException,
            InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<LprTransactionInfo> getLprTransactionsByIds(List<String> lprTransactionIds, ContextInfo contextInfo) throws DoesNotExistException,
            InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<LprTransactionItemInfo> getLprTransactionsWithItemsByResultingLpr(String lprId, ContextInfo contextInfo) throws InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<LprInfo> getLprsByIds(List<String> lprIds, ContextInfo contextInfo) throws DoesNotExistException,
            InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<LprInfo> getLprsByLui(String luiId, ContextInfo contextInfo) throws DoesNotExistException,
            InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<LprInfo> getLprsByLuiAndType(String luiId, String lprTypeKey, ContextInfo contextInfo) throws DoesNotExistException,
            InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<LprInfo> getLprsByPerson(String personId, ContextInfo contextInfo) throws DoesNotExistException,
            InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<LprInfo> getLprsByPersonAndLui(String personId, String luiId, ContextInfo contextInfo) throws DoesNotExistException,
            InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<LprInfo> getLprsByPersonAndLuiType(String personId, String luiTypeKey, ContextInfo contextInfo) throws DoesNotExistException,
            InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<LprInfo> getLprsByPersonAndTypeForAtp(String personId, String atpId, String lprTypeKey, ContextInfo contextInfo) throws DoesNotExistException,
            InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<LprInfo> getLprsByPersonForAtp(String personId, String atpId, ContextInfo contextInfo) throws DoesNotExistException,
            InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<LprInfo> getLprsByPersonForAtpAndLuiType(String personId, String atpId, String luiTypeKey, ContextInfo contextInfo) throws DoesNotExistException,
            InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<String> getLuiIdsByPersonAndTypeAndState(String personId, String lprTypeKey, String relationState, ContextInfo contextInfo) throws DoesNotExistException,
            InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<String> getPersonIdsByLuiAndTypeAndState(String luiId, String lprTypeKey, String relationState, ContextInfo contextInfo) throws DoesNotExistException,
            InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<LprTransactionInfo> getUnsubmittedLprTransactionsByRequestingPersonAndAtp(String requestingPersonId, String atpId, ContextInfo contextInfo) throws InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public LprTransactionInfo processLprTransaction(String lprTransactionId, ContextInfo contextInfo) throws AlreadyExistsException,
            DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<String> searchForLprIds(QueryByCriteria criteria, ContextInfo contextInfo) throws InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<String> searchForLprTransactionIds(QueryByCriteria criteria, ContextInfo contextInfo) throws InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<LprTransactionInfo> searchForLprTransactions(QueryByCriteria criteria, ContextInfo contextInfo) throws InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<LprInfo> searchForLprs(QueryByCriteria criteria, ContextInfo contextInfo) throws InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public LprInfo updateLpr(String lprId, LprInfo lprInfo, ContextInfo contextInfo) throws DoesNotExistException,
            DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException, ReadOnlyException, VersionMismatchException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public LprTransactionInfo updateLprTransaction(String lprTransactionId, LprTransactionInfo lprTransactionInfo, ContextInfo contextInfo) throws DataValidationErrorException,
            DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException, VersionMismatchException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<ValidationResultInfo> validateLpr(String validationType, String luiId, String personId, String lprTypeKey, LprInfo lprInfo, ContextInfo contextInfo) throws DoesNotExistException,
            InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<ValidationResultInfo> verifyLprTransaction(String lprTransactionId, ContextInfo contextInfo) throws DoesNotExistException,
            InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new UnsupportedOperationException("Not supported yet.");
    }


}
