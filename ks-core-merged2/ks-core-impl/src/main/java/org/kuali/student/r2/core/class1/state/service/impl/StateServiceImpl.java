package org.kuali.student.r2.core.class1.state.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.jws.WebService;
import javax.persistence.NoResultException;

import org.kuali.rice.core.api.criteria.QueryByCriteria;
import org.kuali.student.r2.core.class1.state.dao.LifecycleDao;
import org.kuali.student.r2.core.class1.state.dao.StateDao;
import org.kuali.student.r2.core.class1.state.dao.StateLifecycleRelationDao;
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

	

//	@Override
//	public List<StateInfo> getInitialValidStates(String lifecycle, ContextInfo context) 
//			throws DoesNotExistException, InvalidParameterException, MissingParameterException,
//			OperationFailedException {
//		List<StateInfo> stateInfos = null;
//		List<StateEntity> states;
//		
//		try{
//			states = sprDao.getInitialValidStates(lifecycle);
//		}catch(NoResultException ex){
//			throw new DoesNotExistException("No valid initial state exists for this lifecycle: " + lifecycle);
//		}
//		
//		if(null != states && !states.isEmpty()){
//			stateInfos = new ArrayList<StateInfo>();
//			for(StateEntity se : states){
//				stateInfos.add(se.toDto());
//			}
//		}			
//		else
//			throw new DoesNotExistException("No valid initial state exists for this lifecycle: " + lifecycle);
//		
//		return stateInfos;			
//	}
//
//	@Override
//	public StateInfo getNextHappyState(String processKey,
//			String currentStateKey, ContextInfo context)
//			throws DoesNotExistException, InvalidParameterException,
//			MissingParameterException, OperationFailedException {
//		StateEntity state = null;
//		
//		try{
//			state = sprDao.getNextHappyState(processKey, currentStateKey);
//		}catch(NoResultException ex){
//			throw new DoesNotExistException("No next state: processKey=" + processKey + ", stateKey=" + currentStateKey);
//		}
//
//		if (null == state) {
//		    throw new DoesNotExistException("No next state: processKey=" + processKey + ", stateKey=" + currentStateKey);
//		}
//		return state.toDto();
//	}
        
    @Override
    public List<LifecycleInfo> getLifecyclesByKeys(List<String> lifecycleKeys, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        // TODO sambit - THIS METHOD NEEDS JAVADOCS
        return null;
    }
    
    
//    @Override
//    public List<String> getLifecycleKeysByType(String lifecycleTypeKey, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException,
//            PermissionDeniedException {
//        // TODO sambit - THIS METHOD NEEDS JAVADOCS
//        return null;
//    }
    
    @Override
    public List<String> getLifecyclesByRefObjectUri(String refObjectUri, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        // TODO sambit - THIS METHOD NEEDS JAVADOCS
        return null;
    }
    @Override
    public List<String> searchForLifecycleKeys(QueryByCriteria criteria, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        // TODO sambit - THIS METHOD NEEDS JAVADOCS
        return null;
    }
    @Override
    public List<LifecycleInfo> searchForLifecycles(QueryByCriteria criteria, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        // TODO sambit - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public List<ValidationResultInfo> validateLifecycle(String validationTypeKey, LifecycleInfo lifecycleInfo, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
               // TODO sambit - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public LifecycleInfo createLifecycle(String lifecycleKey, LifecycleInfo lifecycleInfo, ContextInfo contextInfo) throws AlreadyExistsException, DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException {
            // TODO sambit - THIS METHOD NEEDS JAVADOCS
        return null;
    }
    @Override
    public LifecycleInfo updateLifecycle(String lifecycleKey, LifecycleInfo lifecycleInfo, ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException,
            InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException, VersionMismatchException {
        // TODO sambit - THIS METHOD NEEDS JAVADOCS
        return null;
    }
    @Override
    public StatusInfo deleteLifecycle(String lifecycleKey, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        // TODO sambit - THIS METHOD NEEDS JAVADOCS
        return null;
    }
    @Override
    public List<StateInfo> getStatesByKeys(List<String> stateKeys, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        // TODO sambit - THIS METHOD NEEDS JAVADOCS
        return null;
    }
    @Override
    public List<StateInfo> getStatesByLifecycle(String lifecycleKey, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        List<StateEntity> states = null;
        try{
            states = stateDao.getStatesByProcess(lifecycleKey);
        }catch(NoResultException ex){
            throw new DoesNotExistException("No states found for lifecycle. lifecycleKey=" + lifecycleKey);
        }
        if (null == states || 0 == states.size() ) {
            throw new DoesNotExistException("No states found for lifecycle. lifecycleKey=" + lifecycleKey);
        }

        List<StateInfo> stateInfos = new ArrayList<StateInfo>();
        for (StateEntity state : states) {
            stateInfos.add(state.toDto());
        }
        return stateInfos;
    }
    @Override
    public List<String> searchForStateKeys(QueryByCriteria criteria, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        // TODO sambit - THIS METHOD NEEDS JAVADOCS
        return null;
    }
    @Override
    public List<StateInfo> searchForStates(QueryByCriteria criteria, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        // TODO sambit - THIS METHOD NEEDS JAVADOCS
        return null;
    }
    @Override
    public List<ValidationResultInfo> validateState(String validationTypeKey, String lifecycleKey, StateInfo stateInfo, ContextInfo contextInfo) throws DoesNotExistException,
            InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        // TODO sambit - THIS METHOD NEEDS JAVADOCS
        return null;
    }
    @Override
    public StateInfo createState(String lifecycleKey, String stateKey, StateInfo stateInfo, ContextInfo contextInfo) throws AlreadyExistsException, DataValidationErrorException,
            DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException {
        // TODO sambit - THIS METHOD NEEDS JAVADOCS
        return null;
    }
    @Override
    public StateInfo updateState(String stateKey, StateInfo stateInfo, ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException, VersionMismatchException {
        // TODO sambit - THIS METHOD NEEDS JAVADOCS
        return null;
    }
    @Override
    public StatusInfo deleteState(String stateKey, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        // TODO sambit - THIS METHOD NEEDS JAVADOCS
        return null;
    }

}
