package org.kuali.student.r2.core.class1.state.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.jws.WebService;
import javax.persistence.NoResultException;

import org.kuali.student.r2.core.class1.state.dao.StateDao;
import org.kuali.student.r2.core.class1.state.dao.LifecycleDao;
import org.kuali.student.r2.core.class1.state.dao.StateLifecycleRelationDao;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.core.class1.state.model.StateEntity;
import org.kuali.student.r2.core.class1.state.model.LifecycleEntity;
import org.kuali.student.r2.core.state.dto.LifecycleInfo;
import org.kuali.student.r2.core.state.dto.StateInfo;
import org.kuali.student.r2.core.state.service.StateService;
import org.springframework.transaction.annotation.Transactional;

@WebService(name = "StateService", serviceName = "StateService", portName = "StateService", targetNamespace = "http://student.kuali.org/wsdl/state")
@Transactional(readOnly=true,noRollbackFor={DoesNotExistException.class},rollbackFor={Throwable.class})
public class StateServiceImpl implements StateService{

	private StateDao stateDao;
	private LifecycleDao spDao;
	private StateLifecycleRelationDao sprDao;
	
	public StateDao getStateDao() {
		return stateDao;
	}
	public void setStateDao(StateDao stateDao) {
		this.stateDao = stateDao;
	}

	public LifecycleDao getSpDao() {
		return spDao;
	}
	public void setSpDao(LifecycleDao spDao) {
		this.spDao = spDao;
	}

	public StateLifecycleRelationDao getSprDao() {
		return sprDao;
	}
	public void setSprDao(StateLifecycleRelationDao sprDao) {
		this.sprDao = sprDao;
	}
	

	@Override
	public LifecycleInfo getLifecycle(String lifecycleKey, ContextInfo context) 
			throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
	    LifecycleEntity sp = spDao.getProcessByKey(lifecycleKey);
	    if (null == sp) {
		    throw new DoesNotExistException("This process does not exist: processKey=" + lifecycleKey);
		}
		return sp.toDto();	    
	}

        
	@Override
	public List<String> getLifecyclesByObjectType(String objectTypeKey, ContextInfo context) 
			throws InvalidParameterException, MissingParameterException, OperationFailedException {
	    return new ArrayList<String>();
	}	
	
	@Override
	public StateInfo getState(String stateKey, ContextInfo context) 
			throws DoesNotExistException, InvalidParameterException, MissingParameterException,
			OperationFailedException {
		StateEntity state = null;
		try{	
			state = stateDao.getState(stateKey);
		}catch(NoResultException ex){
			throw new DoesNotExistException("This state does not exist: stateKey=" + stateKey);
		}
		if (null == state) {
		    throw new DoesNotExistException("This state does not exist: stateKey=" + stateKey);
		}
		return state.toDto();
	}

	@Override
	public List<StateInfo> getStatesForLifecycle(String processKey, ContextInfo context) 
			throws DoesNotExistException, InvalidParameterException, MissingParameterException,
			OperationFailedException {
		List<StateInfo> stateInfos = null;
		List<StateEntity> states;
		
		try{
			states = stateDao.getStatesByProcess(processKey);
		}catch(NoResultException ex){
			throw new DoesNotExistException("No state exists for this lifecycle: " + processKey);
		}
		
		if(null != states && !states.isEmpty()){
			stateInfos = new ArrayList<StateInfo>();
			for(StateEntity se : states){
				stateInfos.add(se.toDto());
			}
		}			
		else
			throw new DoesNotExistException("No state exists for this lifecycle: " + processKey);
		
		return stateInfos;			
	}

	@Override
	public List<StateInfo> getInitialValidStates(String lifecycle, ContextInfo context) 
			throws DoesNotExistException, InvalidParameterException, MissingParameterException,
			OperationFailedException {
		List<StateInfo> stateInfos = null;
		List<StateEntity> states;
		
		try{
			states = sprDao.getInitialValidStates(lifecycle);
		}catch(NoResultException ex){
			throw new DoesNotExistException("No valid initial state exists for this lifecycle: " + lifecycle);
		}
		
		if(null != states && !states.isEmpty()){
			stateInfos = new ArrayList<StateInfo>();
			for(StateEntity se : states){
				stateInfos.add(se.toDto());
			}
		}			
		else
			throw new DoesNotExistException("No valid initial state exists for this lifecycle: " + lifecycle);
		
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
