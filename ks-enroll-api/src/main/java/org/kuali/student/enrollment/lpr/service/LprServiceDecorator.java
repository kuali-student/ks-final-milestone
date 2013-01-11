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
package org.kuali.student.enrollment.lpr.service;

import javax.jws.WebParam;

import org.kuali.rice.core.api.criteria.QueryByCriteria;
import org.kuali.student.enrollment.lpr.dto.LprInfo;
import org.kuali.student.enrollment.lpr.dto.LprTransactionInfo;
import org.kuali.student.enrollment.lpr.dto.LprTransactionItemInfo;
import org.kuali.student.r2.common.dto.BulkStatusInfo;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.StatusInfo;
import org.kuali.student.r2.common.dto.ValidationResultInfo;
import org.kuali.student.r2.common.exceptions.*;

import javax.jws.WebParam;
import java.util.List;

/**
 * The base decorator for the {@link LprService}- Other sub
 * classes of this decorator only have to override the methods to which we want
 * to add additional functionality
 * 
 * @author nwright
 */
public class LprServiceDecorator implements LprService {

    private LprService nextDecorator;

    public LprService getNextDecorator() throws OperationFailedException {
        if (null == nextDecorator) {
            throw new OperationFailedException("Misconfigured application: nextDecorator is null");
        }
        return nextDecorator;
    }

    public void setNextDecorator(LprService nextDecorator) {
        this.nextDecorator = nextDecorator;
    }

    @Override
    public List<ValidationResultInfo> verifyLprTransaction(String lprTransactionId, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().verifyLprTransaction(lprTransactionId, contextInfo);
    }

    @Override
    public List<ValidationResultInfo> validateLpr(String validationType, String luiId, String personId, String lprTypeKey, LprInfo lprInfo, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().validateLpr(validationType, luiId, personId, lprTypeKey, lprInfo, contextInfo);
    }

    @Override
    public LprTransactionInfo updateLprTransaction(String lprTransactionId, LprTransactionInfo lprTransactionInfo, ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, VersionMismatchException {
        return getNextDecorator().updateLprTransaction(lprTransactionId, lprTransactionInfo, contextInfo);
    }

    @Override
    public LprInfo updateLpr(String lprId, LprInfo lprInfo, ContextInfo contextInfo) throws DoesNotExistException, DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException, VersionMismatchException {
        return getNextDecorator().updateLpr(lprId, lprInfo, contextInfo);
    }

    @Override
    public List<LprInfo> searchForLprs(QueryByCriteria criteria, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().searchForLprs(criteria, contextInfo);
    }

    @Override
    public List<LprTransactionInfo> searchForLprTransactions(QueryByCriteria criteria, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().searchForLprTransactions(criteria, contextInfo);
    }

    @Override
    public List<String> searchForLprTransactionIds(QueryByCriteria criteria, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().searchForLprTransactionIds(criteria, contextInfo);
    }

    @Override
    public List<String> searchForLprIds(QueryByCriteria criteria, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().searchForLprIds(criteria, contextInfo);
    }

    @Override
    public LprTransactionInfo processLprTransaction(String lprTransactionId, ContextInfo contextInfo) throws AlreadyExistsException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().processLprTransaction(lprTransactionId, contextInfo);
    }

    @Override
    public List<LprTransactionInfo> getUnsubmittedLprTransactionsByRequestingPersonAndAtp(String requestingPersonId, String atpId, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getUnsubmittedLprTransactionsByRequestingPersonAndAtp(requestingPersonId, atpId, contextInfo);
    }

    @Override
    public List<String> getPersonIdsByLuiAndTypeAndState(String luiId, String lprTypeKey, String relationState, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getPersonIdsByLuiAndTypeAndState(luiId, lprTypeKey, relationState, contextInfo);
    }

    @Override
    public List<String> getLuiIdsByPersonAndTypeAndState(String personId, String lprTypeKey, String relationState, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getLuiIdsByPersonAndTypeAndState(personId, lprTypeKey, relationState, contextInfo);
    }

    @Override
    public List<LprInfo> getLprsByPersonForAtpAndLuiType(String personId, String atpId, String luiTypeKey, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getLprsByPersonForAtpAndLuiType(personId, atpId, luiTypeKey, contextInfo);
    }

    @Override
    public List<LprInfo> getLprsByPersonForAtp(String personId, String atpId, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getLprsByPersonForAtp(personId, atpId, contextInfo);
    }

    @Override
    public List<LprInfo> getLprsByPersonAndTypeForAtp(String personId, String atpId, String lprTypeKey, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getLprsByPersonAndTypeForAtp(personId, atpId, lprTypeKey, contextInfo);
    }

    @Override
    public List<LprInfo> getLprsByPersonAndLuiType(String personId, String luiTypeKey, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getLprsByPersonAndLuiType(personId, luiTypeKey, contextInfo);
    }

    @Override
    public List<LprInfo> getLprsByPersonAndLui(String personId, String luiId, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getLprsByPersonAndLui(personId, luiId, contextInfo);
    }

    @Override
    public List<LprInfo> getLprsByPerson(String personId, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getLprsByPerson(personId, contextInfo);
    }

    @Override
    public List<LprInfo> getLprsByLuiAndType(String luiId, String lprTypeKey, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getLprsByLuiAndType(luiId, lprTypeKey, contextInfo);
    }

    @Override
    public List<LprInfo> getLprsByLui(String luiId, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getLprsByLui(luiId, contextInfo);
    }
    
    

    @Override
	public List<LprInfo> getLprsByLuis(
			@WebParam(name = "luiIds") List<String> luiIds,
			@WebParam(name = "contextInfo") ContextInfo contextInfo)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
		return getNextDecorator().getLprsByLuis(luiIds, contextInfo);
	}

	@Override
    public List<LprInfo> getLprsByIds(List<String> lprIds, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getLprsByIds(lprIds, contextInfo);
    }

    @Override
    public List<LprTransactionItemInfo> getLprTransactionItemsByResultingLpr(String lprId, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getLprTransactionItemsByResultingLpr(lprId, contextInfo);
    }

    @Override
    public List<LprTransactionItemInfo> getLprTransactionItemsByPersonAndLui(String personId, String luiId, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getLprTransactionItemsByPersonAndLui(personId, luiId, contextInfo);
    }

    @Override
    public List<LprTransactionInfo> getLprTransactionsByIds(List<String> lprTransactionIds, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getLprTransactionsByIds(lprTransactionIds, contextInfo);
    }

    @Override
    public List<LprTransactionItemInfo> getLprTransactionItemsByLui(String luiId, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getLprTransactionItemsByLui(luiId, contextInfo);
    }

    @Override
    public LprTransactionInfo getLprTransaction(String lprTransactionId, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getLprTransaction(lprTransactionId, contextInfo);
    }

    @Override
    public LprInfo getLpr(String lprId, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getLpr(lprId, contextInfo);
    }

    @Override
    public StatusInfo deleteLprTransaction(String lprTransactionId, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().deleteLprTransaction(lprTransactionId, contextInfo);
    }

    @Override
    public StatusInfo deleteLpr(String lprId, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().deleteLpr(lprId, contextInfo);
    }

    @Override
    public List<BulkStatusInfo> createLprsForPerson(String personId, String lprTypeKey, List<LprInfo> lprInfos, ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException {
        return getNextDecorator().createLprsForPerson(personId, lprTypeKey, lprInfos, contextInfo);
    }

    @Override
    public List<BulkStatusInfo> createLprsForLui(String luiId, String lprTypeKey, List<LprInfo> lprInfos, ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException {
        return getNextDecorator().createLprsForLui(luiId, lprTypeKey, lprInfos, contextInfo);
    }

    @Override
    public LprTransactionInfo createLprTransactionFromExisting(String lprTransactionId, ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().createLprTransactionFromExisting(lprTransactionId, contextInfo);
    }

    @Override
    public LprTransactionInfo createLprTransaction(String lprTransactionType, LprTransactionInfo lprTransactionInfo, ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().createLprTransaction(lprTransactionType, lprTransactionInfo, contextInfo);
    }

    @Override
    public LprInfo createLpr(String personId, String luiId, String lprTypeKey, LprInfo lprInfo, ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException {
        return getNextDecorator().createLpr(personId, luiId, lprTypeKey, lprInfo, contextInfo);
    }

    @Override
    public StatusInfo changeLprState(String lprId, String nextStateKey, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().changeLprState(lprId, nextStateKey, contextInfo);
    }

    @Override
    public StatusInfo changeLprTransactionState(@WebParam(name = "lprTransactionId") String lprTransactionId, @WebParam(name = "nextStateKey") String nextStateKey, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().changeLprTransactionState(lprTransactionId, nextStateKey, contextInfo);
    }
}
