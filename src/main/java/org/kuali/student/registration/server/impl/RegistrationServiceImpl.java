/**
 * 
 */
package org.kuali.student.registration.server.impl;

import java.util.List;
import java.util.Vector;

import org.kuali.student.poc.wsdl.learningunit.lu.LuService;
import org.kuali.student.poc.wsdl.learningunit.luipersonrelation.LuiPersonRelationService;
import org.kuali.student.poc.xsd.learningunit.lu.dto.Status;
import org.kuali.student.poc.xsd.learningunit.luipersonrelation.dto.LuiPersonRelationDisplay;
import org.kuali.student.poc.xsd.learningunit.luipersonrelation.dto.LuiPersonRelationTypeInfo;
import org.kuali.student.poc.xsd.learningunit.luipersonrelation.dto.RelationStateInfo;
import org.kuali.student.registration.client.model.GwtLuiPersonRelationDisplay;
import org.kuali.student.registration.client.model.GwtLuiPersonRelationInfo;
import org.kuali.student.registration.client.model.GwtLuiPersonRelationTypeInfo;
import org.kuali.student.registration.client.model.GwtRelationStateInfo;
import org.kuali.student.registration.client.service.RegistrationService;
import org.kuali.student.registration.server.convert.RegistrationConverter;
import org.kuali.student.ui.personidentity.client.model.lu.GwtCluInfo;
import org.kuali.student.ui.personidentity.client.model.lu.GwtLuiInfo;
import org.kuali.student.ui.personidentity.server.convert.GwtException;
import org.kuali.student.ui.personidentity.server.convert.lu.LearningUnitAppConverter;

import com.google.gwt.user.client.rpc.SerializableException;

/**
 * @author Garey
 *
 */
public class RegistrationServiceImpl implements RegistrationService {
	
	public static final LuiPersonRelationTypeInfo REL_STUDENT = 
		new LuiPersonRelationTypeInfo("kuali.student");
	public static final LuiPersonRelationTypeInfo REL_INSTRUCTOR = 
		new LuiPersonRelationTypeInfo("kuali.instructor");
	
	public static final RelationStateInfo STATE_BASKET = 
		new RelationStateInfo("basket");
	public static final RelationStateInfo STATE_PENDING = 
		new RelationStateInfo("pending");
	public static final RelationStateInfo STATE_COMPLETE = 
		new RelationStateInfo("complete");
	
	
	// NOTE: Not sure if this should be here
	private LuService luService;	
					
	private LuiPersonRelationService lprService;
	

	/* (non-Javadoc)
	 * @see org.kuali.student.poc.wsdl.learningunit.luipersonrelation.LuiPersonRelationService#createLuiPersonRelation(java.lang.String, java.lang.String, org.kuali.student.poc.xsd.learningunit.luipersonrelation.dto.RelationStateInfo, org.kuali.student.poc.xsd.learningunit.luipersonrelation.dto.LuiPersonRelationTypeInfo, org.kuali.student.poc.xsd.learningunit.luipersonrelation.dto.LuiPersonRelationCreateInfo)
	 */
	
	public String createLuiPersonRelation(String personId, String luiId,
			GwtRelationStateInfo relationStateInfo,
			GwtLuiPersonRelationTypeInfo luiPersonRelationTypeInfo,
			GwtLuiPersonRelationInfo luiPersonRelationCreateInfo)
			throws SerializableException {

		String ret = null;
		try{
		
			ret = lprService.createLuiPersonRelation(personId, luiId, 
				RegistrationConverter.convert(relationStateInfo), 
				RegistrationConverter.convert(luiPersonRelationTypeInfo), 
				RegistrationConverter.convertToLuiPersonRelationCreateInfo(luiPersonRelationCreateInfo));
		}catch(Exception ex){
			throw GwtException.toGWT(ex);
		}
		return ret;
	}

	public List<String> createLuiPersonRelation(String personId, List<String> luiIds,
			GwtRelationStateInfo relationStateInfo,
			GwtLuiPersonRelationTypeInfo luiPersonRelationTypeInfo,
			GwtLuiPersonRelationInfo luiPersonRelationCreateInfo)
			throws SerializableException {

		List<String> ret = null;
		try{
			ret = new Vector<String>();
			for(String luiId: luiIds){
				ret.add(lprService.createLuiPersonRelation(personId, luiId, 
						RegistrationConverter.convert(relationStateInfo), 
						RegistrationConverter.convert(luiPersonRelationTypeInfo), 
						RegistrationConverter.convertToLuiPersonRelationCreateInfo(luiPersonRelationCreateInfo)));
			}
			
		}catch(Exception ex){
			throw GwtException.toGWT(ex);
		}
		return ret;
	}

	
	/* (non-Javadoc)
	 * @see org.kuali.student.poc.wsdl.learningunit.luipersonrelation.LuiPersonRelationService#deleteLuiPersonRelation(java.lang.String)
	 */
	
	public boolean deleteLuiPersonRelation(String luiPersonRelationId)
		throws SerializableException {

		boolean bRet = false;
		try{
			bRet = lprService.deleteLuiPersonRelation(luiPersonRelationId).isSuccess();
		}catch(Exception ex){
			throw GwtException.toGWT(ex);
		}
		
		return bRet; 
	}

	/* (non-Javadoc)
	 * @see org.kuali.student.poc.wsdl.learningunit.luipersonrelation.LuiPersonRelationService#fetchLUIPersonRelation(java.lang.String)
	 */
	
	public GwtLuiPersonRelationInfo fetchLUIPersonRelation(
			String luiPersonRelationId) throws SerializableException {
		GwtLuiPersonRelationInfo ret = null;
		
		try{
			ret = RegistrationConverter.convert(lprService.fetchLUIPersonRelation(luiPersonRelationId));
		}
		catch(Exception ex){
			throw GwtException.toGWT(ex);
		}
		
		return ret;
	}

	/* (non-Javadoc)
	 * @see org.kuali.student.poc.wsdl.learningunit.luipersonrelation.LuiPersonRelationService#findAllValidLuiIdsForPerson(java.lang.String, org.kuali.student.poc.xsd.learningunit.luipersonrelation.dto.LuiPersonRelationTypeInfo, org.kuali.student.poc.xsd.learningunit.luipersonrelation.dto.RelationStateInfo, java.lang.String)
	 */
	
	/*
	public List<String> findAllValidLuiIdsForPerson(String personId,
			LuiPersonRelationTypeInfo luiPersonRelationTypeInfo,
			RelationStateInfo relationStateInfo, String atpId)
			throws DoesNotExistException, DisabledIdentifierException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {
		// TODO Auto-generated method stub
		return null;
	}
*/
	/* (non-Javadoc)
	 * @see org.kuali.student.poc.wsdl.learningunit.luipersonrelation.LuiPersonRelationService#findAllValidLuisForPerson(java.lang.String, org.kuali.student.poc.xsd.learningunit.luipersonrelation.dto.LuiPersonRelationTypeInfo, org.kuali.student.poc.xsd.learningunit.luipersonrelation.dto.RelationStateInfo, java.lang.String)
	 */
	
	/*public List<LuiDisplay> findAllValidLuisForPerson(String personId,
			LuiPersonRelationTypeInfo luiPersonRelationTypeInfo,
			RelationStateInfo relationStateInfo, String atpId)
			throws DoesNotExistException, DisabledIdentifierException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {
		// TODO Auto-generated method stub
		return null;
	}*/

	/* (non-Javadoc)
	 * @see org.kuali.student.poc.wsdl.learningunit.luipersonrelation.LuiPersonRelationService#findAllValidPeopleForLui(java.lang.String, org.kuali.student.poc.xsd.learningunit.luipersonrelation.dto.LuiPersonRelationTypeInfo, org.kuali.student.poc.xsd.learningunit.luipersonrelation.dto.RelationStateInfo)
	 */
	
	/*public List<PersonDisplay> findAllValidPeopleForLui(String luiId,
			LuiPersonRelationTypeInfo luiPersonRelationTypeInfo,
			RelationStateInfo relationStateInfo) throws DoesNotExistException,
			DisabledIdentifierException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
		// TODO Auto-generated method stub
		return null;
	}*/

	/* (non-Javadoc)
	 * @see org.kuali.student.poc.wsdl.learningunit.luipersonrelation.LuiPersonRelationService#findAllValidPersonIdsForLui(java.lang.String, org.kuali.student.poc.xsd.learningunit.luipersonrelation.dto.LuiPersonRelationTypeInfo, org.kuali.student.poc.xsd.learningunit.luipersonrelation.dto.RelationStateInfo)
	 */
	
	/*public List<String> findAllValidPersonIdsForLui(String luiId,
			LuiPersonRelationTypeInfo luiPersonRelationTypeInfo,
			RelationStateInfo relationStateInfo) throws DoesNotExistException,
			DisabledIdentifierException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
		// TODO Auto-generated method stub
		return null;
	}*/

	/* (non-Javadoc)
	 * @see org.kuali.student.poc.wsdl.learningunit.luipersonrelation.LuiPersonRelationService#findAllowedRelationStates(org.kuali.student.poc.xsd.learningunit.luipersonrelation.dto.LuiPersonRelationTypeInfo)
	 */
	
	public List<GwtRelationStateInfo> findAllowedRelationStates(
			LuiPersonRelationTypeInfo luiPersonRelationTypeInfo)
			throws SerializableException {
		List<GwtRelationStateInfo> lRet = null;
		try{
			List<RelationStateInfo> tmp = lprService.findAllowedRelationStates(luiPersonRelationTypeInfo);
			if(tmp != null){
				lRet = new Vector<GwtRelationStateInfo>();
				for(RelationStateInfo rsi: tmp){
					lRet.add(RegistrationConverter.convert(rsi));
				}
			}
							
		}catch (Exception ex) {
			throw GwtException.toGWT(ex);
		}
		
		return lRet;

	}

	/* (non-Javadoc)
	 * @see org.kuali.student.poc.wsdl.learningunit.luipersonrelation.LuiPersonRelationService#findLuiIdsRelatedToPerson(java.lang.String, org.kuali.student.poc.xsd.learningunit.luipersonrelation.dto.LuiPersonRelationTypeInfo, org.kuali.student.poc.xsd.learningunit.luipersonrelation.dto.RelationStateInfo)
	 */
	
	public List<String> findLuiIdsRelatedToPerson(String personId,
			GwtLuiPersonRelationTypeInfo luiPersonRelationTypeInfo,
			GwtRelationStateInfo relationStateInfo) throws SerializableException {

		List<String> lRet = null;
		
		try{
			lRet = lprService.findLuiIdsRelatedToPerson(personId,
					RegistrationConverter.convert(luiPersonRelationTypeInfo), 
					RegistrationConverter.convert(relationStateInfo));
			
		}catch (Exception ex) {
			throw GwtException.toGWT(ex);
		}
		
		return lRet;
	}
	
	public List<GwtLuiInfo> findLuiInfosRelatedToPerson(String personId,
			GwtLuiPersonRelationTypeInfo luiPersonRelationTypeInfo,
			GwtRelationStateInfo relationStateInfo) throws SerializableException {

		List<GwtLuiInfo> lRet = null;
		
		try{
			List<String> lTmp = lprService.findLuiIdsRelatedToPerson(personId,
					RegistrationConverter.convert(luiPersonRelationTypeInfo), 
					RegistrationConverter.convert(relationStateInfo));
			if(lTmp != null){
				lRet = new Vector<GwtLuiInfo>();
				for(String luiId: lTmp){
					GwtLuiInfo in = LearningUnitAppConverter.convert(luService.fetchLui(luiId));
					GwtCluInfo cInfo = LearningUnitAppConverter.convert(luService.fetchClu(in.getCluDisplay().getCluId()));
					in.setCluInfo(cInfo);
					lRet.add(in);
				}
			}
			
		}catch (Exception ex) {
			throw GwtException.toGWT(ex);
		}
		
		return lRet;
	}
	

	/* (non-Javadoc)
	 * @see org.kuali.student.poc.wsdl.learningunit.luipersonrelation.LuiPersonRelationService#findLuiPersonRelationIds(java.lang.String, java.lang.String)
	 */
	
	/*public List<String> findLuiPersonRelationIds(String personId, String luiId)
			throws DoesNotExistException, DisabledIdentifierException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {
		// TODO Auto-generated method stub
		return null;
	}*/

	/* (non-Javadoc)
	 * @see org.kuali.student.poc.wsdl.learningunit.luipersonrelation.LuiPersonRelationService#findLuiPersonRelationIdsForLui(java.lang.String)
	 */
	
	/*public List<String> findLuiPersonRelationIdsForLui(String luiId)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
		// TODO Auto-generated method stub
		return null;
	}*/

	/* (non-Javadoc)
	 * @see org.kuali.student.poc.wsdl.learningunit.luipersonrelation.LuiPersonRelationService#findLuiPersonRelationIdsForPerson(java.lang.String)
	 */
	
	public List<String> findLuiPersonRelations(String personId, String luiId)
	throws SerializableException {
		List<String> lRet = null;
		try{			
			lRet = lprService.findLuiPersonRelationIds(personId, luiId);
		}catch (Exception ex) {
			throw GwtException.toGWT(ex);
		}
		return lRet;
	}
	
	public List<String> findLuiPersonRelationIdsForPerson(String personId)
			throws SerializableException {
		
		List<String> lRet = null;
		try{			
			lRet = lprService.findLuiPersonRelationIdsForPerson(personId);
		}catch (Exception ex) {
			throw GwtException.toGWT(ex);
		}
		
		return lRet;
	}

	/* (non-Javadoc)
	 * This method was not implemented on the service side, but it is 
	 * required for the ui to work.
	 */	
	public List<LuiPersonRelationTypeInfo> findLuiPersonRelationTypes()
			throws SerializableException {
								
		List<LuiPersonRelationTypeInfo> lRet = null;
		try{
			lRet = new Vector<LuiPersonRelationTypeInfo>();
			lRet.add(REL_STUDENT);
			lRet.add(REL_INSTRUCTOR);
			// Because we can't add these types, We have to
			// Fake them.
			//lRet = lprService.findLuiPersonRelationTypes();
		}catch (Exception ex) {
			throw GwtException.toGWT(ex);
		}
		
		return lRet;
	}
	
	public List<GwtLuiPersonRelationDisplay> findLuiPersonRelationsForPerson(String personId)
		throws SerializableException {
		List<GwtLuiPersonRelationDisplay> lRet = null;
		
		try{
			List<LuiPersonRelationDisplay> lTmp = null;
			lTmp = lprService.findLuiPersonRelationsForPerson(personId);
			lRet = new Vector<GwtLuiPersonRelationDisplay>();
			if(lTmp != null){
				for(LuiPersonRelationDisplay disp: lTmp){
					lRet.add(RegistrationConverter.convert(disp));
				}
			}
		}catch (Exception ex) {
			throw GwtException.toGWT(ex);
		}
		
		return lRet;
		
	}

	/* (non-Javadoc)
	 * @see org.kuali.student.poc.wsdl.learningunit.luipersonrelation.LuiPersonRelationService#findLuiPersonRelationTypesForLuiPersonRelation(java.lang.String, java.lang.String, org.kuali.student.poc.xsd.learningunit.luipersonrelation.dto.RelationStateInfo)
	 */
	
	/*public List<LuiPersonRelationTypeInfo> findLuiPersonRelationTypesForLuiPersonRelation(
			String personId, String luiId, RelationStateInfo relationStateInfo)
			throws DoesNotExistException, DisabledIdentifierException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {
		// TODO Auto-generated method stub
		return null;
	}*/

	/* (non-Javadoc)
	 * @see org.kuali.student.poc.wsdl.learningunit.luipersonrelation.LuiPersonRelationService#findLuiPersonRelations(java.lang.String, java.lang.String)
	 */
	
	/*public List<LuiPersonRelationDisplay> findLuiPersonRelations(
			String personId, String luiId) throws DoesNotExistException,
			DisabledIdentifierException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
		// TODO Auto-generated method stub
		return null;
	}*/

	/* (non-Javadoc)
	 * @see org.kuali.student.poc.wsdl.learningunit.luipersonrelation.LuiPersonRelationService#findLuiPersonRelationsForLui(java.lang.String)
	 */
	
	/*public List<LuiPersonRelationDisplay> findLuiPersonRelationsForLui(
			String luiId) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {
		// TODO Auto-generated method stub
		return null;
	}*/

	/* (non-Javadoc)
	 * @see org.kuali.student.poc.wsdl.learningunit.luipersonrelation.LuiPersonRelationService#findLuiPersonRelationsForPerson(java.lang.String)
	 */
	
	/*public List<LuiPersonRelationDisplay> findLuiPersonRelationsForPerson(
			String personId) throws DoesNotExistException,
			DisabledIdentifierException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
		// TODO Auto-generated method stub
		return null;
	}*/

	/* (non-Javadoc)
	 * @see org.kuali.student.poc.wsdl.learningunit.luipersonrelation.LuiPersonRelationService#findOrderedRelationStatesForLuiPersonRelation(java.lang.String)
	 */
	
	/*public List<LuiPersonRelationDisplay> findOrderedRelationStatesForLuiPersonRelation(
			String luiPersonRelationId) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {
		// TODO Auto-generated method stub
		return null;
	}*/

	/* (non-Javadoc)
	 * @see org.kuali.student.poc.wsdl.learningunit.luipersonrelation.LuiPersonRelationService#findPersonIdsRelatedToLui(java.lang.String, org.kuali.student.poc.xsd.learningunit.luipersonrelation.dto.LuiPersonRelationTypeInfo, org.kuali.student.poc.xsd.learningunit.luipersonrelation.dto.RelationStateInfo)
	 */
	
	/*public List<String> findPersonIdsRelatedToLui(String luiId,
			LuiPersonRelationTypeInfo luiPersonRelationTypeInfo,
			RelationStateInfo relationStateInfo) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {
		// TODO Auto-generated method stub
		return null;
	}*/

	/* (non-Javadoc)
	 * @see org.kuali.student.poc.wsdl.learningunit.luipersonrelation.LuiPersonRelationService#findRelationStates()
	 */
	
	/*public List<RelationStateInfo> findRelationStates()
			throws OperationFailedException {
		// TODO Auto-generated method stub
		return null;
	}
*/
	/* (non-Javadoc)
	 * @see org.kuali.student.poc.wsdl.learningunit.luipersonrelation.LuiPersonRelationService#findValidRelationStatesForLuiPersonRelation(java.lang.String, java.lang.String, org.kuali.student.poc.xsd.learningunit.luipersonrelation.dto.LuiPersonRelationTypeInfo)
	 */
	
	/*public List<RelationStateInfo> findValidRelationStatesForLuiPersonRelation(
			String personId, String luiId,
			LuiPersonRelationTypeInfo luiPersonRelationTypeInfo)
			throws DoesNotExistException, DisabledIdentifierException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {
		// TODO Auto-generated method stub
		return null;
	}
*/
	/* (non-Javadoc)
	 * @see org.kuali.student.poc.wsdl.learningunit.luipersonrelation.LuiPersonRelationService#isRelated(java.lang.String, java.lang.String, org.kuali.student.poc.xsd.learningunit.luipersonrelation.dto.LuiPersonRelationTypeInfo, org.kuali.student.poc.xsd.learningunit.luipersonrelation.dto.RelationStateInfo)
	 */
	
	/*public boolean isRelated(String personId, String luiId,
			LuiPersonRelationTypeInfo luiPersonRelationTypeInfo,
			RelationStateInfo relationStateInfo) throws DoesNotExistException,
			DisabledIdentifierException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
		// TODO Auto-generated method stub
		return false;
	}*/

	/* (non-Javadoc)
	 * @see org.kuali.student.poc.wsdl.learningunit.luipersonrelation.LuiPersonRelationService#isValidLuiPersonRelation(java.lang.String, java.lang.String, org.kuali.student.poc.xsd.learningunit.luipersonrelation.dto.LuiPersonRelationTypeInfo, org.kuali.student.poc.xsd.learningunit.luipersonrelation.dto.RelationStateInfo)
	 */
	
	/*public boolean isValidLuiPersonRelation(String personId, String luiId,
			LuiPersonRelationTypeInfo luiPersonRelationTypeInfo,
			RelationStateInfo relationStateInfo) throws DoesNotExistException,
			DisabledIdentifierException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
		// TODO Auto-generated method stub
		return false;
	}*/

	/* (non-Javadoc)
	 * @see org.kuali.student.poc.wsdl.learningunit.luipersonrelation.LuiPersonRelationService#searchForLuiPersonRelationIds(org.kuali.student.poc.xsd.learningunit.luipersonrelation.dto.LuiPersonRelationCriteria)
	 */
	
	/*public List<String> searchForLuiPersonRelationIds(
			LuiPersonRelationCriteria luiPersonRelationCriteria)
			throws InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {
		// TODO Auto-generated method stub
		return null;
	}*/

	/* (non-Javadoc)
	 * @see org.kuali.student.poc.wsdl.learningunit.luipersonrelation.LuiPersonRelationService#searchForLuiPersonRelations(org.kuali.student.poc.xsd.learningunit.luipersonrelation.dto.LuiPersonRelationCriteria)
	 */
	
	/*public List<LuiPersonRelationDisplay> searchForLuiPersonRelations(
			LuiPersonRelationCriteria luiPersonRelationCriteria)
			throws InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {
		// TODO Auto-generated method stub
		return null;
	}*/

	/* (non-Javadoc)
	 * @see org.kuali.student.poc.wsdl.learningunit.luipersonrelation.LuiPersonRelationService#updateLuiPersonRelation(java.lang.String, org.kuali.student.poc.xsd.learningunit.luipersonrelation.dto.LuiPersonRelationUpdateInfo)
	 */
	
	public boolean updateLuiPersonRelation(String luiPersonRelationId,
			GwtLuiPersonRelationInfo luiPersonRelationUpdateInfo)
	throws SerializableException {
		boolean ret = false;
		
		try{
			Status s = lprService.updateLuiPersonRelation(
					luiPersonRelationId, 
						RegistrationConverter.convertToLuiPersonRelationUpdateInfo(
								luiPersonRelationUpdateInfo));
			ret = s.isSuccess();
		}catch (Exception ex) {
			throw GwtException.toGWT(ex);
		}
		
		return ret;
	}

	/**
	 * @return the luService
	 */
	public  LuService getLuService() {
		return luService;
	}

	/**
	 * @param luService the luService to set
	 */
	public void setLuService(LuService luService) {
		this.luService = luService;
	}

	/**
	 * @return the lprService
	 */
	public LuiPersonRelationService getLprService() {
		return lprService;
	}

	/**
	 * @param lprService the lprService to set
	 */
	public void setLprService(LuiPersonRelationService lprService) {
		this.lprService = lprService;
	}

	/* (non-Javadoc)
	 * @see org.kuali.student.poc.wsdl.learningunit.luipersonrelation.LuiPersonRelationService#updateRelationState(java.lang.String, org.kuali.student.poc.xsd.learningunit.luipersonrelation.dto.RelationStateInfo)
	 */
	
	/*public Status updateRelationState(String luiPersonRelationId,
			RelationStateInfo relationStateInfo) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {
		// TODO Auto-generated method stub
		return null;
	}*/

	/* (non-Javadoc)
	 * @see org.kuali.student.poc.wsdl.learningunit.luipersonrelation.LuiPersonRelationService#validateLuiPersonRelation(java.lang.String, java.lang.String, org.kuali.student.poc.xsd.learningunit.luipersonrelation.dto.LuiPersonRelationTypeInfo, org.kuali.student.poc.xsd.learningunit.luipersonrelation.dto.RelationStateInfo)
	 */
	
	/*public ValidationResult validateLuiPersonRelation(String personId,
			String luiId, LuiPersonRelationTypeInfo luiPersonRelationTypeInfo,
			RelationStateInfo relationStateInfo) throws DoesNotExistException,
			DisabledIdentifierException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
		// TODO Auto-generated method stub
		return null;
	}*/

}
