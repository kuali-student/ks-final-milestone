package org.kuali.student.r2.common.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.jws.WebService;
import javax.persistence.NoResultException;

import org.kuali.student.r2.common.dao.StateDao;
import org.kuali.student.r2.common.dao.StateProcessDao;
import org.kuali.student.r2.common.dao.StateProcessRelationDao;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.StateInfo;
import org.kuali.student.r2.common.dto.StateProcessInfo;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.model.StateEntity;
import org.kuali.student.r2.common.model.StateProcessEntity;
import org.kuali.student.r2.common.service.StateService;
import org.springframework.transaction.annotation.Transactional;

@WebService(name = "StateService", serviceName = "StateService", portName = "StateService", targetNamespace = "http://student.kuali.org/wsdl/state")
@Transactional(readOnly=true,noRollbackFor={DoesNotExistException.class},rollbackFor={Throwable.class})
public class StateServiceImpl implements StateService{

	private StateDao stateDao;
	private StateProcessDao spDao;
	private StateProcessRelationDao sprDao;
	
	public StateDao getStateDao() {
		return stateDao;
	}

	public void setStateDao(StateDao stateDao) {
		this.stateDao = stateDao;
	}

	public StateProcessDao getSpDao() {
		return spDao;
	}

	public void setSpDao(StateProcessDao spDao) {
		this.spDao = spDao;
	}

	public StateProcessRelationDao getSprDao() {
		return sprDao;
	}

	public void setSprDao(StateProcessRelationDao sprDao) {
		this.sprDao = sprDao;
	}

	@Override
	public StateProcessInfo getProcessByKey(String processKey, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
	    StateProcessEntity sp = spDao.getProcessByKey(processKey);
	    if (null == sp) {
		    throw new DoesNotExistException("This process does not exist: processKey=" + processKey);
		}
		return sp.toDto();	    
	}

	@Override
	public List<String> getProcessByObjectType(String objectTypeKey, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
	    return null;
	}	
	
	@Override
	public StateInfo getState(String processKey, String stateKey,
			ContextInfo context) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException {
		StateEntity state = null;
		try{	
			state = stateDao.getState(processKey, stateKey);
		}catch(NoResultException ex){
			throw new DoesNotExistException("This state does not exist: processKey=" + processKey + ", stateKey=" + stateKey);
		}
		if (null == state) {
		    throw new DoesNotExistException("This state does not exist: processKey=" + processKey + ", stateKey=" + stateKey);
		}
		return state.toDto();
	}

	@Override
	public List<StateInfo> getStatesByProcess(String processKey,
			ContextInfo context) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException {
		List<StateInfo> stateInfos = null;
		List<StateEntity> states;
		
		try{
			states = stateDao.getStatesByProcess(processKey);
		}catch(NoResultException ex){
			throw new DoesNotExistException("No state exists for this process: " + processKey);
		}
		
		if(null != states && !states.isEmpty()){
			stateInfos = new ArrayList<StateInfo>();
			for(StateEntity se : states){
				stateInfos.add(se.toDto());
			}
		}			
		else
			throw new DoesNotExistException("No state exists for this process: " + processKey);
		
		return stateInfos;			
	}

	@Override
	public List<StateInfo> getInitialValidStates(String processKey,
			ContextInfo context) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException {
		List<StateInfo> stateInfos = null;
		List<StateEntity> states;
		
		try{
			states = sprDao.getInitialValidStates(processKey);
		}catch(NoResultException ex){
			throw new DoesNotExistException("No valid initial state exists for this process: " + processKey);
		}
		
		if(null != states && !states.isEmpty()){
			stateInfos = new ArrayList<StateInfo>();
			for(StateEntity se : states){
				stateInfos.add(se.toDto());
			}
		}			
		else
			throw new DoesNotExistException("No valid initial state exists for this process: " + processKey);
		
		return stateInfos;			
	}

	@Override
	public StateInfo getNextHappyState(String processKey,
			String currentStateKey, ContextInfo context)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException {
		StateEntity state = null;
		
		try{
			state = sprDao.getNextHappyState(processKey, currentStateKey);
		}catch(NoResultException ex){
			throw new DoesNotExistException("No next state: processKey=" + processKey + ", stateKey=" + currentStateKey);
		}

		if (null == state) {
		    throw new DoesNotExistException("No next state: processKey=" + processKey + ", stateKey=" + currentStateKey);
		}
		return state.toDto();
	}

}
