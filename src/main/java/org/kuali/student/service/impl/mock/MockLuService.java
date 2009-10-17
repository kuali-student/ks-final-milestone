/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.kuali.student.service.impl.mock;

import org.kuali.student.comp.infc.KSContext;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.kuali.student.core.dictionary.dto.ObjectStructure;
import org.kuali.student.core.dto.StatusInfo;
import org.kuali.student.core.enumerable.dto.EnumeratedValue;
import org.kuali.student.core.exceptions.AlreadyExistsException;
import org.kuali.student.core.exceptions.CircularReferenceException;
import org.kuali.student.core.exceptions.DataValidationErrorException;
import org.kuali.student.core.exceptions.DependentObjectsExistException;
import org.kuali.student.core.exceptions.DoesNotExistException;
import org.kuali.student.core.exceptions.InvalidParameterException;
import org.kuali.student.core.exceptions.MissingParameterException;
import org.kuali.student.core.exceptions.OperationFailedException;
import org.kuali.student.core.exceptions.PermissionDeniedException;
import org.kuali.student.core.exceptions.UnsupportedActionException;
import org.kuali.student.core.exceptions.VersionMismatchException;
import org.kuali.student.core.search.dto.QueryParamValue;
import org.kuali.student.core.search.dto.Result;
import org.kuali.student.core.search.dto.SearchCriteriaTypeInfo;
import org.kuali.student.core.search.dto.SearchResultTypeInfo;
import org.kuali.student.core.search.dto.SearchTypeInfo;
import org.kuali.student.core.validation.dto.ValidationResultContainer;
import org.kuali.student.lum.lu.dto.CluCluRelationCriteriaInfo;
import org.kuali.student.lum.lu.dto.CluCluRelationInfo;
import org.kuali.student.lum.lu.dto.CluCriteriaInfo;
import org.kuali.student.lum.lu.dto.CluInfo;
import org.kuali.student.lum.lu.dto.CluSetInfo;
import org.kuali.student.lum.lu.dto.LrTypeInfo;
import org.kuali.student.lum.lu.dto.LuDocRelationInfo;
import org.kuali.student.lum.lu.dto.LuDocRelationTypeInfo;
import org.kuali.student.lum.lu.dto.LuLuRelationTypeInfo;
import org.kuali.student.lum.lu.dto.LuStatementInfo;
import org.kuali.student.lum.lu.dto.LuStatementTypeInfo;
import org.kuali.student.lum.lu.dto.LuTypeInfo;
import org.kuali.student.lum.lu.dto.LuiCriteriaInfo;
import org.kuali.student.lum.lu.dto.LuiInfo;
import org.kuali.student.lum.lu.dto.LuiLuiRelationCriteriaInfo;
import org.kuali.student.lum.lu.dto.LuiLuiRelationInfo;
import org.kuali.student.lum.lu.dto.ReqComponentInfo;
import org.kuali.student.lum.lu.dto.ReqComponentTypeInfo;
import org.kuali.student.lum.lu.service.LuService;

/**
 *
 * @author nwright
 */
public class MockLuService implements LuService
{

 private KSContext context;

 public MockLuService (KSContext context)
 {
  this.context = context;
 }

 private static int maxId = 0;
 private static Map<String, CluInfo> cluInfos = new Hashtable ();
 private static Map<String, LuTypeInfo> luTypes;

 static
 {
  luTypes = new Hashtable ();
  LuTypeInfo info = null;

  info = new LuTypeInfo ();
  info.setId ("kuali.lu.type.credit.course");
  info.setDesc ("Credit Course");
  info.setName ("Credit Course");
  info.setEffectiveDate (new Date ());
  luTypes.put (info.getId (), info);

  info = new LuTypeInfo ();
  info.setId ("kuali.lu.type.course.format.shell");
  info.setDesc ("Course Format Shell");
  info.setName ("Course Format");
  info.setEffectiveDate (new Date ());
  luTypes.put (info.getId (), info);

  info = new LuTypeInfo ();
  info.setId ("kuali.lu.type.non.credit.course");
  info.setDesc ("Non-Credit Course");
  info.setName ("Non-Credit Course");
  info.setEffectiveDate (new Date ());
  luTypes.put (info.getId (), info);


  info = new LuTypeInfo ();
  info.setId ("kuali.lu.type.lab");
  info.setDesc ("Laboratory");
  info.setName ("Lab");
  info.setEffectiveDate (new Date ());
  luTypes.put (info.getId (), info);

  info = new LuTypeInfo ();
  info.setId ("kuali.lu.type.lecture");
  info.setDesc ("Lecture");
  info.setName ("Lecture");
  info.setEffectiveDate (new Date ());
  luTypes.put (info.getId (), info);
 }

 private static Set<String> luStates = new HashSet ();

 static
 {
  luStates.add ("template");
  luStates.add ("explore");
  luStates.add ("draft");
  luStates.add ("submitted");
  luStates.add ("approve");
  luStates.add ("not approved");
  luStates.add ("withdrawn");
  luStates.add ("retired");
  luStates.add ("inactive");
 }

 @Override
 public StatusInfo addCluResourceRequirement (String resourceTypeKey,
                                              String cluId)
  throws AlreadyExistsException,
         DoesNotExistException,
         InvalidParameterException,
         MissingParameterException,
         OperationFailedException,
         PermissionDeniedException
 {
  throw new UnsupportedOperationException ("Not supported yet.");
 }

 @Override
 public StatusInfo addCluSetToCluSet (String cluSetId, String addedCluSetId)
  throws CircularReferenceException,
         DoesNotExistException,
         InvalidParameterException,
         MissingParameterException,
         OperationFailedException,
         PermissionDeniedException,
         UnsupportedActionException
 {
  throw new UnsupportedOperationException ("Not supported yet.");
 }

 @Override
 public StatusInfo addCluToCluSet (String cluId, String cluSetId)
  throws DoesNotExistException,
         InvalidParameterException,
         MissingParameterException,
         OperationFailedException,
         PermissionDeniedException,
         UnsupportedActionException
 {
  throw new UnsupportedOperationException ("Not supported yet.");
 }

 @Override
 public StatusInfo addLrScaleToClu (String lrScaleTypeKey, String lrTypeKey,
                                    String cluId)
  throws AlreadyExistsException,
         DoesNotExistException,
         InvalidParameterException,
         MissingParameterException,
         OperationFailedException,
         PermissionDeniedException,
         UnsupportedActionException
 {
  throw new UnsupportedOperationException ("Not supported yet.");
 }

 @Override
 public StatusInfo addLrScaleToLui (String lrScaleTypeKey, String lrTypeKey,
                                    String luiId)
  throws AlreadyExistsException,
         DoesNotExistException,
         InvalidParameterException,
         MissingParameterException,
         OperationFailedException,
         PermissionDeniedException,
         UnsupportedActionException
 {
  throw new UnsupportedOperationException ("Not supported yet.");
 }

 @Override
 public StatusInfo addLuStatementToClu (String cluId, String luStatementId)
  throws AlreadyExistsException,
         DoesNotExistException,
         InvalidParameterException,
         MissingParameterException,
         OperationFailedException,
         PermissionDeniedException
 {
  throw new UnsupportedOperationException ("Not supported yet.");
 }

 @Override
 public StatusInfo addOutcomeLoToClu (String loId, String cluId)
  throws AlreadyExistsException,
         DoesNotExistException,
         InvalidParameterException,
         MissingParameterException,
         OperationFailedException,
         PermissionDeniedException
 {
  throw new UnsupportedOperationException ("Not supported yet.");
 }

 @Override
 public StatusInfo addOutcomeLoToLui (String loId, String luiId)
  throws AlreadyExistsException,
         DoesNotExistException,
         InvalidParameterException,
         MissingParameterException,
         OperationFailedException,
         PermissionDeniedException,
         UnsupportedActionException
 {
  throw new UnsupportedOperationException ("Not supported yet.");
 }

 @Override
 public CluInfo createClu (String luTypeKey, CluInfo cluInfo)
  throws AlreadyExistsException,
         DataValidationErrorException,
         DoesNotExistException,
         InvalidParameterException,
         MissingParameterException,
         OperationFailedException,
         PermissionDeniedException
 {

  if (cluInfo.getId () != null)
  {
   throw new InvalidParameterException ("lu Id cannot already be assigned");
  }
  if (cluInfo.getType () == null)
  {
   throw new MissingParameterException ("type is required");
  }
  if ( ! cluInfo.getType ().equals (luTypeKey))
  {
   throw new InvalidParameterException ("lu type on info must be the same as parameter");
  }
  if ( ! luTypes.containsKey (cluInfo.getType ()))
  {
   throw new InvalidParameterException ("type");
  }
  if (cluInfo.getState () == null)
  {
   throw new MissingParameterException ("state is required");
  }
  if ( ! luStates.contains (cluInfo.getState ()))
  {
   throw new InvalidParameterException ("state");
  }

  CluInfo info = (CluInfo) new DeepCopier (cluInfo).copy ();
  maxId ++;
  info.setId (maxId + "");
  info.setMetaInfo (new MetaInfoUtility (context).getForCreate ());
  cluInfos.put (info.getId (), info);
  return (CluInfo) new DeepCopier (info).copy ();
 }

 @Override
 public CluCluRelationInfo createCluCluRelation (String cluId,
                                                 String relatedCluId,
                                                 String luLuRelationTypeKey,
                                                 CluCluRelationInfo cluCluRelationInfo)
  throws AlreadyExistsException,
         CircularReferenceException,
         DataValidationErrorException,
         DoesNotExistException,
         InvalidParameterException,
         MissingParameterException,
         OperationFailedException,
         PermissionDeniedException
 {
  throw new UnsupportedOperationException ("Not supported yet.");
 }

 @Override
 public CluSetInfo createEnumeratedCluSet (String cluSetName,
                                           CluSetInfo cluSetInfo)
  throws AlreadyExistsException,
         DataValidationErrorException,
         InvalidParameterException,
         MissingParameterException,
         OperationFailedException,
         PermissionDeniedException,
         DoesNotExistException
 {
  throw new UnsupportedOperationException ("Not supported yet.");
 }

 @Override
 public LuDocRelationInfo createLuDocRelationForClu (String luDocRelationType,
                                                     String documentId,
                                                     String cluId,
                                                     LuDocRelationInfo luDocRelationInfo)
  throws AlreadyExistsException,
         DataValidationErrorException,
         DoesNotExistException,
         InvalidParameterException,
         MissingParameterException,
         OperationFailedException,
         PermissionDeniedException
 {
  throw new UnsupportedOperationException ("Not supported yet.");
 }

 @Override
 public LuStatementInfo createLuStatement (String luStatementType,
                                           LuStatementInfo luStatementInfo)
  throws AlreadyExistsException,
         DataValidationErrorException,
         DoesNotExistException,
         InvalidParameterException,
         MissingParameterException,
         OperationFailedException,
         PermissionDeniedException
 {
  throw new UnsupportedOperationException ("Not supported yet.");
 }

 @Override
 public LuiInfo createLui (String cluId, String atpKey, LuiInfo luiInfo)
  throws AlreadyExistsException,
         DataValidationErrorException,
         DoesNotExistException,
         InvalidParameterException,
         MissingParameterException,
         OperationFailedException,
         PermissionDeniedException
 {
  throw new UnsupportedOperationException ("Not supported yet.");
 }

 @Override
 public LuiLuiRelationInfo createLuiLuiRelation (String luiId,
                                                 String relatedLuiId,
                                                 String luLuRelationTypeKey,
                                                 LuiLuiRelationInfo luiLuiRelationInfo)
  throws AlreadyExistsException,
         CircularReferenceException,
         DataValidationErrorException,
         DoesNotExistException,
         InvalidParameterException,
         MissingParameterException,
         OperationFailedException,
         PermissionDeniedException
 {
  throw new UnsupportedOperationException ("Not supported yet.");
 }

 @Override
 public ReqComponentInfo createReqComponent (String reqComponentType,
                                             ReqComponentInfo reqComponentInfo)
  throws AlreadyExistsException,
         DataValidationErrorException,
         DoesNotExistException,
         InvalidParameterException,
         MissingParameterException,
         OperationFailedException,
         PermissionDeniedException
 {
  throw new UnsupportedOperationException ("Not supported yet.");
 }

 @Override
 public StatusInfo deleteClu (String cluId)
  throws DoesNotExistException,
         InvalidParameterException,
         MissingParameterException,
         DependentObjectsExistException,
         OperationFailedException,
         PermissionDeniedException
 {
  CluInfo info = cluInfos.get (cluId);
  if (info == null)
  {
   throw new DoesNotExistException ();
  }
  cluInfos.remove (cluId);
  StatusInfo status = new StatusInfo ();
  status.setSuccess (Boolean.TRUE);
  return status;
 }

 @Override
 public StatusInfo deleteCluCluRelation (String cluCluRelationId)
  throws DoesNotExistException,
         InvalidParameterException,
         MissingParameterException,
         OperationFailedException,
         PermissionDeniedException
 {
  throw new UnsupportedOperationException ("Not supported yet.");
 }

 @Override
 public StatusInfo deleteCluSet (String cluSetId)
  throws DoesNotExistException,
         InvalidParameterException,
         MissingParameterException,
         OperationFailedException,
         PermissionDeniedException
 {
  throw new UnsupportedOperationException ("Not supported yet.");
 }

 @Override
 public StatusInfo deleteLuDocRelation (String luDocRelationId)
  throws DoesNotExistException,
         InvalidParameterException,
         MissingParameterException,
         OperationFailedException,
         PermissionDeniedException
 {
  throw new UnsupportedOperationException ("Not supported yet.");
 }

 @Override
 public StatusInfo deleteLuStatement (String luStatementId)
  throws DoesNotExistException,
         InvalidParameterException,
         MissingParameterException,
         OperationFailedException,
         PermissionDeniedException
 {
  throw new UnsupportedOperationException ("Not supported yet.");
 }

 @Override
 public StatusInfo deleteLui (String luiId)
  throws DependentObjectsExistException,
         DoesNotExistException,
         InvalidParameterException,
         MissingParameterException,
         OperationFailedException,
         PermissionDeniedException
 {
  throw new UnsupportedOperationException ("Not supported yet.");
 }

 @Override
 public StatusInfo deleteLuiLuiRelation (String luiLuiRelationId)
  throws DoesNotExistException,
         InvalidParameterException,
         MissingParameterException,
         OperationFailedException,
         PermissionDeniedException
 {
  throw new UnsupportedOperationException ("Not supported yet.");
 }

 @Override
 public StatusInfo deleteReqComponent (String reqComponentId)
  throws DoesNotExistException,
         InvalidParameterException,
         MissingParameterException,
         OperationFailedException,
         PermissionDeniedException
 {
  throw new UnsupportedOperationException ("Not supported yet.");
 }

 @Override
 public List<String> getAllCluIdsInCluSet (String cluSetId)
  throws DoesNotExistException,
         InvalidParameterException,
         MissingParameterException,
         OperationFailedException,
         PermissionDeniedException
 {
  throw new UnsupportedOperationException ("Not supported yet.");
 }

 @Override
 public List<CluInfo> getAllClusInCluSet (String cluSetId)
  throws DoesNotExistException,
         InvalidParameterException,
         MissingParameterException,
         OperationFailedException,
         PermissionDeniedException
 {
  throw new UnsupportedOperationException ("Not supported yet.");
 }

 @Override
 public List<String> getAllowedLrScaleTypesForLuType (String luTypeKey,
                                                      String lrTypeKey)
  throws DoesNotExistException,
         InvalidParameterException,
         MissingParameterException,
         OperationFailedException
 {
  throw new UnsupportedOperationException ("Not supported yet.");
 }

 @Override
 public List<String> getAllowedLrTypesForLuType (String luTypeKey)
  throws DoesNotExistException,
         InvalidParameterException,
         MissingParameterException,
         OperationFailedException
 {
  throw new UnsupportedOperationException ("Not supported yet.");
 }

 @Override
 public List<String> getAllowedLuLuRelationTypeInfosByCluId (String cluId,
                                                             String relatedCluId)
  throws DoesNotExistException,
         InvalidParameterException,
         MissingParameterException,
         OperationFailedException
 {
  throw new UnsupportedOperationException ("Not supported yet.");
 }

 @Override
 public List<String> getAllowedLuLuRelationTypeInfosByLuiId (String luiId,
                                                             String relatedLuiId)
  throws DoesNotExistException,
         InvalidParameterException,
         MissingParameterException,
         OperationFailedException
 {
  throw new UnsupportedOperationException ("Not supported yet.");
 }

 @Override
 public List<String> getAllowedLuLuRelationTypeInfosForLuType (String luTypeKey,
                                                               String relatedLuTypeKey)
  throws DoesNotExistException,
         InvalidParameterException,
         MissingParameterException,
         OperationFailedException
 {
  throw new UnsupportedOperationException ("Not supported yet.");
 }

 @Override
 public CluInfo getClu (String cluId)
  throws DoesNotExistException,
         InvalidParameterException,
         MissingParameterException,
         OperationFailedException
 {
  CluInfo info = cluInfos.get (cluId);
  if (info == null)
  {
   throw new DoesNotExistException ();
  }
  return (CluInfo) new DeepCopier (info).copy ();
 }

 @Override
 public CluCluRelationInfo getCluCluRelation (String cluCluRelationId)
  throws DoesNotExistException,
         InvalidParameterException,
         MissingParameterException,
         OperationFailedException
 {
  throw new UnsupportedOperationException ("Not supported yet.");
 }

 @Override
 public List<CluCluRelationInfo> getCluCluRelationsByClu (String cluId)
  throws DoesNotExistException,
         InvalidParameterException,
         MissingParameterException,
         OperationFailedException
 {
  throw new UnsupportedOperationException ("Not supported yet.");
 }

 @Override
 public List<String> getCluIdsByLoId (String loId)
  throws DoesNotExistException,
         InvalidParameterException,
         MissingParameterException,
         OperationFailedException
 {
  throw new UnsupportedOperationException ("Not supported yet.");
 }

 @Override
 public List<String> getCluIdsByLuType (String luTypeKey, String luState)
  throws DoesNotExistException,
         InvalidParameterException,
         MissingParameterException,
         OperationFailedException
 {
  throw new UnsupportedOperationException ("Not supported yet.");
 }

 @Override
 public List<String> getCluIdsByRelation (String relatedCluId,
                                          String luLuRelationTypeKey)
  throws DoesNotExistException,
         InvalidParameterException,
         MissingParameterException,
         OperationFailedException
 {
  throw new UnsupportedOperationException ("Not supported yet.");
 }

 @Override
 public List<String> getCluIdsFromCluSet (String cluSetId)
  throws DoesNotExistException,
         InvalidParameterException,
         MissingParameterException,
         OperationFailedException,
         PermissionDeniedException
 {
  throw new UnsupportedOperationException ("Not supported yet.");
 }

 @Override
 public List<String> getCluSetIdsFromCluSet (String cluSetId)
  throws DoesNotExistException,
         InvalidParameterException,
         MissingParameterException,
         OperationFailedException,
         PermissionDeniedException
 {
  throw new UnsupportedOperationException ("Not supported yet.");
 }

 @Override
 public CluSetInfo getCluSetInfo (String cluSetId)
  throws DoesNotExistException,
         InvalidParameterException,
         MissingParameterException,
         OperationFailedException,
         PermissionDeniedException
 {
  throw new UnsupportedOperationException ("Not supported yet.");
 }

 @Override
 public List<CluSetInfo> getCluSetInfoByIdList (List<String> cluSetIdList)
  throws DoesNotExistException,
         InvalidParameterException,
         MissingParameterException,
         OperationFailedException,
         PermissionDeniedException
 {
  throw new UnsupportedOperationException ("Not supported yet.");
 }

 @Override
 public List<CluInfo> getClusByIdList (List<String> cluIdList)
  throws DoesNotExistException,
         InvalidParameterException,
         MissingParameterException,
         OperationFailedException
 {
  throw new UnsupportedOperationException ("Not supported yet.");
 }

 @Override
 public List<CluInfo> getClusByLuType (String luTypeKey, String luState)
  throws DoesNotExistException,
         InvalidParameterException,
         MissingParameterException,
         OperationFailedException
 {
  throw new UnsupportedOperationException ("Not supported yet.");
 }

 @Override
 public List<CluInfo> getClusByRelation (String relatedCluId,
                                         String luLuRelationTypeKey)
  throws DoesNotExistException,
         InvalidParameterException,
         MissingParameterException,
         OperationFailedException
 {
  throw new UnsupportedOperationException ("Not supported yet.");
 }

 @Override
 public List<CluInfo> getClusFromCluSet (String cluSetId)
  throws DoesNotExistException,
         InvalidParameterException,
         MissingParameterException,
         OperationFailedException,
         PermissionDeniedException
 {
  throw new UnsupportedOperationException ("Not supported yet.");
 }

 @Override
 public List<String> getClusUsingComponent (String reqComponentId)
  throws DoesNotExistException,
         InvalidParameterException,
         MissingParameterException,
         OperationFailedException
 {
  throw new UnsupportedOperationException ("Not supported yet.");
 }

 @Override
 public List<String> getLoIdsByClu (String cluId)
  throws DoesNotExistException,
         InvalidParameterException,
         MissingParameterException,
         OperationFailedException
 {
  throw new UnsupportedOperationException ("Not supported yet.");
 }

 @Override
 public List<String> getLoIdsByLui (String luiId)
  throws DoesNotExistException,
         InvalidParameterException,
         MissingParameterException,
         OperationFailedException
 {
  throw new UnsupportedOperationException ("Not supported yet.");
 }

 @Override
 public List<String> getLrScaleTypesForClu (String cluId, String lrTypeKey)
  throws DoesNotExistException,
         InvalidParameterException,
         MissingParameterException,
         OperationFailedException
 {
  throw new UnsupportedOperationException ("Not supported yet.");
 }

 @Override
 public List<String> getLrScaleTypesForLui (String luiId, String lrTypeKey)
  throws DoesNotExistException,
         InvalidParameterException,
         MissingParameterException,
         OperationFailedException
 {
  throw new UnsupportedOperationException ("Not supported yet.");
 }

 @Override
 public LrTypeInfo getLrType (String lrTypeKey)
  throws DoesNotExistException,
         InvalidParameterException,
         MissingParameterException,
         OperationFailedException
 {
  throw new UnsupportedOperationException ("Not supported yet.");
 }

 @Override
 public List<LrTypeInfo> getLrTypes ()
  throws OperationFailedException
 {
  throw new UnsupportedOperationException ("Not supported yet.");
 }

 @Override
 public List<String> getLrTypesForClu (String cluId)
  throws DoesNotExistException,
         InvalidParameterException,
         MissingParameterException,
         OperationFailedException
 {
  throw new UnsupportedOperationException ("Not supported yet.");
 }

 @Override
 public List<String> getLrTypesForLui (String luiId)
  throws DoesNotExistException,
         InvalidParameterException,
         MissingParameterException,
         OperationFailedException
 {
  throw new UnsupportedOperationException ("Not supported yet.");
 }

 @Override
 public LuDocRelationInfo getLuDocRelation (String luDocRelationId)
  throws DoesNotExistException,
         InvalidParameterException,
         MissingParameterException,
         OperationFailedException
 {
  throw new UnsupportedOperationException ("Not supported yet.");
 }

 @Override
 public LuDocRelationTypeInfo getLuDocRelationType (String luDocRelationTypeKey)
  throws DoesNotExistException,
         InvalidParameterException,
         MissingParameterException,
         OperationFailedException
 {
  throw new UnsupportedOperationException ("Not supported yet.");
 }

 @Override
 public List<LuDocRelationTypeInfo> getLuDocRelationTypes ()
  throws OperationFailedException
 {
  throw new UnsupportedOperationException ("Not supported yet.");
 }

 @Override
 public List<LuDocRelationInfo> getLuDocRelationsByClu (String cluId)
  throws DoesNotExistException,
         InvalidParameterException,
         MissingParameterException,
         OperationFailedException
 {
  throw new UnsupportedOperationException ("Not supported yet.");
 }

 @Override
 public List<LuDocRelationInfo> getLuDocRelationsByDocument (String documentId)
  throws DoesNotExistException,
         InvalidParameterException,
         MissingParameterException,
         OperationFailedException
 {
  throw new UnsupportedOperationException ("Not supported yet.");
 }

 @Override
 public List<LuDocRelationInfo> getLuDocRelationsByIdList (
  List<String> luDocRelationIdList)
  throws DoesNotExistException,
         InvalidParameterException,
         MissingParameterException,
         OperationFailedException
 {
  throw new UnsupportedOperationException ("Not supported yet.");
 }

 @Override
 public List<LuDocRelationInfo> getLuDocRelationsByType (
  String luDocRelationTypeKey)
  throws DoesNotExistException,
         InvalidParameterException,
         MissingParameterException,
         OperationFailedException
 {
  throw new UnsupportedOperationException ("Not supported yet.");
 }

 @Override
 public LuLuRelationTypeInfo getLuLuRelationTypeInfo (String luLuRelationTypeKey)
  throws OperationFailedException,
         MissingParameterException,
         DoesNotExistException
 {
  throw new UnsupportedOperationException ("Not supported yet.");
 }

 @Override
 public List<LuLuRelationTypeInfo> getLuLuRelationTypeInfos ()
  throws OperationFailedException
 {
  throw new UnsupportedOperationException ("Not supported yet.");
 }

 @Override
 public LuStatementInfo getLuStatement (String luStatementId)
  throws DoesNotExistException,
         InvalidParameterException,
         MissingParameterException,
         OperationFailedException
 {
  throw new UnsupportedOperationException ("Not supported yet.");
 }

 @Override
 public LuStatementTypeInfo getLuStatementType (String luStatementTypeKey)
  throws DoesNotExistException,
         InvalidParameterException,
         MissingParameterException,
         OperationFailedException
 {
  throw new UnsupportedOperationException ("Not supported yet.");
 }

 @Override
 public List<LuStatementTypeInfo> getLuStatementTypes ()
  throws OperationFailedException
 {
  throw new UnsupportedOperationException ("Not supported yet.");
 }

 @Override
 public List<LuStatementTypeInfo> getLuStatementTypesForLuStatementType (
  String luStatementTypeKey)
  throws DoesNotExistException,
         InvalidParameterException,
         MissingParameterException,
         OperationFailedException
 {
  throw new UnsupportedOperationException ("Not supported yet.");
 }

 @Override
 public List<LuStatementInfo> getLuStatements (List<String> luStatementIdList)
  throws DoesNotExistException,
         InvalidParameterException,
         MissingParameterException,
         OperationFailedException
 {
  throw new UnsupportedOperationException ("Not supported yet.");
 }

 @Override
 public List<LuStatementInfo> getLuStatementsByType (String luStatementTypeKey)
  throws DoesNotExistException,
         InvalidParameterException,
         MissingParameterException,
         OperationFailedException
 {
  throw new UnsupportedOperationException ("Not supported yet.");
 }

 @Override
 public List<LuStatementInfo> getLuStatementsForClu (String cluId)
  throws DoesNotExistException,
         InvalidParameterException,
         MissingParameterException,
         OperationFailedException
 {
  throw new UnsupportedOperationException ("Not supported yet.");
 }

 @Override
 public LuTypeInfo getLuType (String luTypeKey)
  throws DoesNotExistException,
         InvalidParameterException,
         MissingParameterException,
         OperationFailedException
 {
  LuTypeInfo info = luTypes.get (luTypeKey);
  if (info == null)
  {
   throw new DoesNotExistException ();
  }
  return (LuTypeInfo) new DeepCopier (info).copy ();
 }

 @Override
 public List<LuTypeInfo> getLuTypes ()
  throws OperationFailedException
 {
  return new ArrayList (luTypes.values ());
 }

 @Override
 public LuiInfo getLui (String luiId)
  throws DoesNotExistException,
         InvalidParameterException,
         MissingParameterException,
         OperationFailedException
 {
  throw new UnsupportedOperationException ("Not supported yet.");
 }

 @Override
 public List<String> getLuiIdsByCluId (String cluId)
  throws DoesNotExistException,
         InvalidParameterException,
         MissingParameterException,
         OperationFailedException
 {
  throw new UnsupportedOperationException ("Not supported yet.");
 }

 @Override
 public List<String> getLuiIdsByLoId (String loId)
  throws DoesNotExistException,
         InvalidParameterException,
         MissingParameterException,
         OperationFailedException
 {
  throw new UnsupportedOperationException ("Not supported yet.");
 }

 @Override
 public List<String> getLuiIdsByRelation (String relatedLuiId,
                                          String luLuRelationTypeKey)
  throws DoesNotExistException,
         InvalidParameterException,
         MissingParameterException,
         OperationFailedException
 {
  throw new UnsupportedOperationException ("Not supported yet.");
 }

 @Override
 public List<String> getLuiIdsInAtpByCluId (String cluId, String atpKey)
  throws DoesNotExistException,
         InvalidParameterException,
         MissingParameterException,
         OperationFailedException
 {
  throw new UnsupportedOperationException ("Not supported yet.");
 }

 @Override
 public LuiLuiRelationInfo getLuiLuiRelation (String luiLuiRelationId)
  throws DoesNotExistException,
         InvalidParameterException,
         MissingParameterException,
         OperationFailedException
 {
  throw new UnsupportedOperationException ("Not supported yet.");
 }

 @Override
 public List<LuiLuiRelationInfo> getLuiLuiRelations (String luiId)
  throws DoesNotExistException,
         InvalidParameterException,
         MissingParameterException,
         OperationFailedException
 {
  throw new UnsupportedOperationException ("Not supported yet.");
 }

 @Override
 public List<LuiInfo> getLuisByIdList (List<String> luiIdList)
  throws DoesNotExistException,
         InvalidParameterException,
         MissingParameterException,
         OperationFailedException
 {
  throw new UnsupportedOperationException ("Not supported yet.");
 }

 @Override
 public List<LuiInfo> getLuisByRelation (String relatedLuiId,
                                         String luLuRelationTypeKey)
  throws DoesNotExistException,
         InvalidParameterException,
         MissingParameterException,
         OperationFailedException
 {
  throw new UnsupportedOperationException ("Not supported yet.");
 }

 @Override
 public List<LuiInfo> getLuisInAtpByCluId (String cluId, String atpKey)
  throws DoesNotExistException,
         InvalidParameterException,
         MissingParameterException,
         OperationFailedException
 {
  throw new UnsupportedOperationException ("Not supported yet.");
 }

 @Override
 public List<String> getRelatedCluIdsByCluId (String cluId,
                                              String luLuRelationTypeKey)
  throws DoesNotExistException,
         InvalidParameterException,
         MissingParameterException,
         OperationFailedException
 {
  throw new UnsupportedOperationException ("Not supported yet.");
 }

 @Override
 public List<CluInfo> getRelatedClusByCluId (String cluId,
                                             String luLuRelationTypeKey)
  throws DoesNotExistException,
         InvalidParameterException,
         MissingParameterException,
         OperationFailedException
 {
  throw new UnsupportedOperationException ("Not supported yet.");
 }

 @Override
 public List<String> getRelatedLuiIdsByLuiId (String luiId,
                                              String luLuRelationTypeKey)
  throws DoesNotExistException,
         InvalidParameterException,
         MissingParameterException,
         OperationFailedException
 {
  throw new UnsupportedOperationException ("Not supported yet.");
 }

 @Override
 public List<LuiInfo> getRelatedLuisByLuiId (String luiId,
                                             String luLuRelationTypeKey)
  throws DoesNotExistException,
         InvalidParameterException,
         MissingParameterException,
         OperationFailedException
 {
  throw new UnsupportedOperationException ("Not supported yet.");
 }

 @Override
 public ReqComponentInfo getReqComponent (String reqComponentId)
  throws DoesNotExistException,
         InvalidParameterException,
         MissingParameterException,
         OperationFailedException
 {
  throw new UnsupportedOperationException ("Not supported yet.");
 }

 @Override
 public ReqComponentTypeInfo getReqComponentType (String reqComponentTypeKey)
  throws DoesNotExistException,
         InvalidParameterException,
         MissingParameterException,
         OperationFailedException
 {
  throw new UnsupportedOperationException ("Not supported yet.");
 }

 @Override
 public List<ReqComponentTypeInfo> getReqComponentTypes ()
  throws OperationFailedException
 {
  throw new UnsupportedOperationException ("Not supported yet.");
 }

 @Override
 public List<ReqComponentTypeInfo> getReqComponentTypesForLuStatementType (
  String luStatementTypeKey)
  throws DoesNotExistException,
         InvalidParameterException,
         MissingParameterException,
         OperationFailedException
 {
  throw new UnsupportedOperationException ("Not supported yet.");
 }

 @Override
 public List<ReqComponentInfo> getReqComponents (List<String> reqComponentIdList)
  throws DoesNotExistException,
         InvalidParameterException,
         MissingParameterException,
         OperationFailedException
 {
  throw new UnsupportedOperationException ("Not supported yet.");
 }

 @Override
 public List<ReqComponentInfo> getReqComponentsByType (
  String reqComponentTypeKey)
  throws DoesNotExistException,
         InvalidParameterException,
         MissingParameterException,
         OperationFailedException
 {
  throw new UnsupportedOperationException ("Not supported yet.");
 }

 @Override
 public List<ReqComponentInfo> getReqComponentsUsingClu (String cluId)
  throws DoesNotExistException,
         InvalidParameterException,
         MissingParameterException,
         OperationFailedException
 {
  throw new UnsupportedOperationException ("Not supported yet.");
 }

 @Override
 public List<String> getResourceRequirementsForCluId (String cluId)
  throws DoesNotExistException,
         InvalidParameterException,
         MissingParameterException,
         OperationFailedException
 {
  throw new UnsupportedOperationException ("Not supported yet.");
 }

 @Override
 public List<LuStatementInfo> getStatementsUsingComponent (String reqComponentId)
  throws DoesNotExistException,
         InvalidParameterException,
         MissingParameterException,
         OperationFailedException
 {
  throw new UnsupportedOperationException ("Not supported yet.");
 }

 @Override
 public Boolean isCluInCluSet (String cluId, String cluSetId)
  throws DoesNotExistException,
         InvalidParameterException,
         MissingParameterException,
         OperationFailedException,
         PermissionDeniedException
 {
  throw new UnsupportedOperationException ("Not supported yet.");
 }

 @Override
 public StatusInfo removeCluFromCluSet (String cluId, String cluSetId)
  throws DoesNotExistException,
         InvalidParameterException,
         MissingParameterException,
         OperationFailedException,
         PermissionDeniedException,
         UnsupportedActionException
 {
  throw new UnsupportedOperationException ("Not supported yet.");
 }

 @Override
 public StatusInfo removeCluResourceRequirement (String resourceTypeKey,
                                                 String cluId)
  throws DoesNotExistException,
         InvalidParameterException,
         MissingParameterException,
         OperationFailedException,
         PermissionDeniedException
 {
  throw new UnsupportedOperationException ("Not supported yet.");
 }

 @Override
 public StatusInfo removeCluSetFromCluSet (String cluSetId,
                                           String removedCluSetId)
  throws DoesNotExistException,
         InvalidParameterException,
         MissingParameterException,
         OperationFailedException,
         PermissionDeniedException,
         UnsupportedActionException
 {
  throw new UnsupportedOperationException ("Not supported yet.");
 }

 @Override
 public StatusInfo removeLrScaleFromClu (String lrScaleTypeKey, String lrTypeKey,
                                         String cluId)
  throws DependentObjectsExistException,
         DoesNotExistException,
         InvalidParameterException,
         MissingParameterException,
         OperationFailedException,
         PermissionDeniedException
 {
  throw new UnsupportedOperationException ("Not supported yet.");
 }

 @Override
 public StatusInfo removeLrScaleFromLui (String lrScaleTypeKey, String lrTypeKey,
                                         String luiId)
  throws DoesNotExistException,
         InvalidParameterException,
         MissingParameterException,
         OperationFailedException,
         PermissionDeniedException
 {
  throw new UnsupportedOperationException ("Not supported yet.");
 }

 @Override
 public StatusInfo removeLuStatementFromClu (String cluId, String luStatementId)
  throws DoesNotExistException,
         InvalidParameterException,
         MissingParameterException,
         OperationFailedException,
         PermissionDeniedException
 {
  throw new UnsupportedOperationException ("Not supported yet.");
 }

 @Override
 public StatusInfo removeOutcomeLoFromClu (String loId, String cluId)
  throws DependentObjectsExistException,
         DoesNotExistException,
         InvalidParameterException,
         MissingParameterException,
         OperationFailedException,
         PermissionDeniedException
 {
  throw new UnsupportedOperationException ("Not supported yet.");
 }

 @Override
 public StatusInfo removeOutcomeLoFromLui (String loId, String luiId)
  throws DoesNotExistException,
         InvalidParameterException,
         MissingParameterException,
         OperationFailedException,
         PermissionDeniedException
 {
  throw new UnsupportedOperationException ("Not supported yet.");
 }

 @Override
 public CluInfo updateClu (String cluId, CluInfo cluInfo)
  throws DataValidationErrorException,
         DoesNotExistException,
         InvalidParameterException,
         MissingParameterException,
         OperationFailedException,
         PermissionDeniedException,
         VersionMismatchException
 {
  if ( ! cluInfos.containsKey (cluId))
  {
   throw new DoesNotExistException ();
  }
  CluInfo info = (CluInfo) new DeepCopier (cluInfo).copy ();
  new MetaInfoUtility (context).update (info.getMetaInfo ());
  cluInfos.put (cluId, info);
  return (CluInfo) new DeepCopier (info).copy ();
 }

 @Override
 public CluCluRelationInfo updateCluCluRelation (String cluCluRelationId,
                                                 CluCluRelationInfo cluCluRelationInfo)
  throws DataValidationErrorException,
         DoesNotExistException,
         InvalidParameterException,
         MissingParameterException,
         OperationFailedException,
         PermissionDeniedException,
         VersionMismatchException
 {
  throw new UnsupportedOperationException ("Not supported yet.");
 }

 @Override
 public CluSetInfo updateCluSet (String cluSetId, CluSetInfo cluSetInfo)
  throws DataValidationErrorException,
         DoesNotExistException,
         InvalidParameterException,
         MissingParameterException,
         OperationFailedException,
         PermissionDeniedException,
         VersionMismatchException,
         CircularReferenceException,
         UnsupportedActionException
 {
  throw new UnsupportedOperationException ("Not supported yet.");
 }

 @Override
 public CluInfo updateCluState (String cluId, String luState)
  throws DataValidationErrorException,
         DoesNotExistException,
         InvalidParameterException,
         MissingParameterException,
         OperationFailedException,
         PermissionDeniedException
 {
  throw new UnsupportedOperationException ("Not supported yet.");
 }

 @Override
 public LuDocRelationInfo updateLuDocRelation (String luDocRelationId,
                                               LuDocRelationInfo luDocRelationInfo)
  throws DataValidationErrorException,
         DoesNotExistException,
         InvalidParameterException,
         MissingParameterException,
         OperationFailedException,
         PermissionDeniedException,
         VersionMismatchException
 {
  throw new UnsupportedOperationException ("Not supported yet.");
 }

 @Override
 public LuStatementInfo updateLuStatement (String luStatementId,
                                           LuStatementInfo luStatementInfo)
  throws CircularReferenceException,
         DataValidationErrorException,
         DoesNotExistException,
         InvalidParameterException,
         MissingParameterException,
         OperationFailedException,
         PermissionDeniedException,
         VersionMismatchException
 {
  throw new UnsupportedOperationException ("Not supported yet.");
 }

 @Override
 public LuiInfo updateLui (String luiId, LuiInfo luiInfo)
  throws DataValidationErrorException,
         DoesNotExistException,
         InvalidParameterException,
         MissingParameterException,
         OperationFailedException,
         PermissionDeniedException,
         VersionMismatchException
 {
  throw new UnsupportedOperationException ("Not supported yet.");
 }

 @Override
 public LuiLuiRelationInfo updateLuiLuiRelation (String luiLuiRelationId,
                                                 LuiLuiRelationInfo luiLuiRelationInfo)
  throws DataValidationErrorException,
         DoesNotExistException,
         InvalidParameterException,
         MissingParameterException,
         OperationFailedException,
         PermissionDeniedException,
         VersionMismatchException
 {
  throw new UnsupportedOperationException ("Not supported yet.");
 }

 @Override
 public LuiInfo updateLuiState (String luiId, String luState)
  throws DataValidationErrorException,
         DoesNotExistException,
         InvalidParameterException,
         MissingParameterException,
         OperationFailedException,
         PermissionDeniedException
 {
  throw new UnsupportedOperationException ("Not supported yet.");
 }

 @Override
 public ReqComponentInfo updateReqComponent (String reqComponentId,
                                             ReqComponentInfo reqComponentInfo)
  throws DataValidationErrorException,
         DoesNotExistException,
         InvalidParameterException,
         MissingParameterException,
         OperationFailedException,
         PermissionDeniedException,
         VersionMismatchException
 {
  throw new UnsupportedOperationException ("Not supported yet.");
 }

 @Override
 public List<ValidationResultContainer> validateClu (String validationType,
                                                     CluInfo cluInfo)
  throws DoesNotExistException,
         InvalidParameterException,
         MissingParameterException,
         OperationFailedException
 {
  throw new UnsupportedOperationException ("Not supported yet.");
 }

 @Override
 public List<ValidationResultContainer> validateCluCluRelation (
  String validationType,
                                                                CluCluRelationInfo cluCluRelationInfo)
  throws DoesNotExistException,
         InvalidParameterException,
         MissingParameterException,
         OperationFailedException
 {
  throw new UnsupportedOperationException ("Not supported yet.");
 }

 @Override
 public List<ValidationResultContainer> validateLuDocRelation (
  String validationType,
                                                               LuDocRelationInfo luDocRelationInfo)
  throws DoesNotExistException,
         InvalidParameterException,
         MissingParameterException,
         OperationFailedException
 {
  throw new UnsupportedOperationException ("Not supported yet.");
 }

 @Override
 public List<ValidationResultContainer> validateLuStatement (
  String validationType,
                                                             LuStatementInfo luStatementInfo)
  throws DoesNotExistException,
         InvalidParameterException,
         MissingParameterException,
         OperationFailedException
 {
  throw new UnsupportedOperationException ("Not supported yet.");
 }

 @Override
 public List<ValidationResultContainer> validateReqComponent (
  String validationType,
                                                              ReqComponentInfo reqComponentInfo)
  throws DoesNotExistException,
         InvalidParameterException,
         MissingParameterException,
         OperationFailedException
 {
  throw new UnsupportedOperationException ("Not supported yet.");
 }

 @Override
 public ObjectStructure getObjectStructure (String objectTypeKey)
 {
  throw new UnsupportedOperationException ("Not supported yet.");
 }

 @Override
 public List<String> getObjectTypes ()
 {
  throw new UnsupportedOperationException ("Not supported yet.");
 }

 @Override
 public boolean validateObject (String objectTypeKey, String stateKey,
                                String info)
 {
  throw new UnsupportedOperationException ("Not supported yet.");
 }

 @Override
 public boolean validateStructureData (String objectTypeKey, String stateKey,
                                       String info)
 {
  throw new UnsupportedOperationException ("Not supported yet.");
 }

 @Override
 public List<EnumeratedValue> getEnumeration (String enumerationKey,
                                              String enumContextKey,
                                              String contextValue,
                                              Date contextDate)
 {
  throw new UnsupportedOperationException ("Not supported yet.");
 }

 @Override
 public SearchCriteriaTypeInfo getSearchCriteriaType (
  String searchCriteriaTypeKey)
  throws DoesNotExistException,
         InvalidParameterException,
         MissingParameterException,
         OperationFailedException
 {
  throw new UnsupportedOperationException ("Not supported yet.");
 }

 @Override
 public List<SearchCriteriaTypeInfo> getSearchCriteriaTypes ()
  throws OperationFailedException
 {
  throw new UnsupportedOperationException ("Not supported yet.");
 }

 @Override
 public SearchResultTypeInfo getSearchResultType (String searchResultTypeKey)
  throws DoesNotExistException,
         InvalidParameterException,
         MissingParameterException,
         OperationFailedException
 {
  throw new UnsupportedOperationException ("Not supported yet.");
 }

 @Override
 public List<SearchResultTypeInfo> getSearchResultTypes ()
  throws OperationFailedException
 {
  throw new UnsupportedOperationException ("Not supported yet.");
 }

 @Override
 public SearchTypeInfo getSearchType (String searchTypeKey)
  throws DoesNotExistException,
         InvalidParameterException,
         MissingParameterException,
         OperationFailedException
 {
  throw new UnsupportedOperationException ("Not supported yet.");
 }

 @Override
 public List<SearchTypeInfo> getSearchTypes ()
  throws OperationFailedException
 {
  throw new UnsupportedOperationException ("Not supported yet.");
 }

 @Override
 public List<SearchTypeInfo> getSearchTypesByCriteria (
  String searchCriteriaTypeKey)
  throws DoesNotExistException,
         InvalidParameterException,
         MissingParameterException,
         OperationFailedException
 {
  throw new UnsupportedOperationException ("Not supported yet.");
 }

 @Override
 public List<SearchTypeInfo> getSearchTypesByResult (String searchResultTypeKey)
  throws DoesNotExistException,
         InvalidParameterException,
         MissingParameterException,
         OperationFailedException
 {
  throw new UnsupportedOperationException ("Not supported yet.");
 }

 @Override
 public List<Result> searchForResults (String searchTypeKey,
                                       List<QueryParamValue> queryParamValues)
  throws DoesNotExistException,
         InvalidParameterException,
         MissingParameterException,
         OperationFailedException,
         PermissionDeniedException
 {
  throw new UnsupportedOperationException ("Not supported yet.");
 }

 public CluSetInfo createDynamicCluSet (String cluSetName, CluSetInfo cluSetInfo,
                                        CluCriteriaInfo cluCriteria)
  throws AlreadyExistsException,
         DataValidationErrorException,
         InvalidParameterException,
         MissingParameterException,
         OperationFailedException,
         PermissionDeniedException
 {
  throw new UnsupportedOperationException ("Not supported yet.");
 }

 public List<String> searchForCluCluRelations (CluCluRelationCriteriaInfo cluCluRelationCriteria)
  throws InvalidParameterException,
         MissingParameterException,
         OperationFailedException
 {
  throw new UnsupportedOperationException ("Not supported yet.");
 }

 public List<String> searchForClus (CluCriteriaInfo cluCriteria)
  throws InvalidParameterException,
         MissingParameterException,
         OperationFailedException
 {
  throw new UnsupportedOperationException ("Not supported yet.");
 }

 public List<String> searchForLuiLuiRelations (LuiLuiRelationCriteriaInfo luiLuiRelationCriteria)
  throws InvalidParameterException,
         MissingParameterException,
         OperationFailedException
 {
  throw new UnsupportedOperationException ("Not supported yet.");
 }

 public List<String> searchForLuis (LuiCriteriaInfo luiCriteria)
  throws InvalidParameterException,
         MissingParameterException,
         OperationFailedException
 {
  throw new UnsupportedOperationException ("Not supported yet.");
 }


}
