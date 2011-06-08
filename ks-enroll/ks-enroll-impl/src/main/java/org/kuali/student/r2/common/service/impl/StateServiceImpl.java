package org.kuali.student.r2.common.service.impl;

import java.util.List;

import javax.jws.WebService;

import org.kuali.student.r2.common.dao.StateDao;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.StateInfo;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.service.StateService;
import org.springframework.transaction.annotation.Transactional;

@WebService(name = "StateService", serviceName = "StateService", portName = "StateService", targetNamespace = "http://student.kuali.org/wsdl/state")
@Transactional(readOnly=true,noRollbackFor={DoesNotExistException.class},rollbackFor={Throwable.class})
public class StateServiceImpl implements StateService{

	private StateDao stateDao;
	
	public StateDao getStateDao() {
		return stateDao;
	}

	public void setStateDao(StateDao stateDao) {
		this.stateDao = stateDao;
	}

	@Override
	public List<String> getProcessKeys(String typeKey, ContextInfo context)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public StateInfo getState(String processKey, String stateKey,
			ContextInfo context) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<StateInfo> getStatesByProcess(String processKey,
			ContextInfo context) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<StateInfo> getInitialValidStates(String processKey,
			ContextInfo context) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public StateInfo getNextHappyState(String processKey,
			String currentStateKey, ContextInfo context)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException {
		// TODO Auto-generated method stub
		return null;
	}

}
