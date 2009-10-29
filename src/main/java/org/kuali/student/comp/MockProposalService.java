/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.kuali.student.comp;


import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.kuali.student.core.dictionary.dto.ObjectStructure;
import org.kuali.student.core.dto.ReferenceTypeInfo;
import org.kuali.student.core.dto.StatusInfo;
import org.kuali.student.core.exceptions.AlreadyExistsException;
import org.kuali.student.core.exceptions.DataValidationErrorException;
import org.kuali.student.core.exceptions.DependentObjectsExistException;
import org.kuali.student.core.exceptions.DoesNotExistException;
import org.kuali.student.core.exceptions.InvalidParameterException;
import org.kuali.student.core.exceptions.MissingParameterException;
import org.kuali.student.core.exceptions.OperationFailedException;
import org.kuali.student.core.exceptions.PermissionDeniedException;
import org.kuali.student.core.exceptions.VersionMismatchException;
import org.kuali.student.core.proposal.dto.ProposalDocRelationInfo;
import org.kuali.student.core.proposal.dto.ProposalDocRelationTypeInfo;
import org.kuali.student.core.proposal.dto.ProposalInfo;
import org.kuali.student.core.proposal.dto.ProposalTypeInfo;
import org.kuali.student.core.proposal.service.ProposalService;
import org.kuali.student.core.search.dto.QueryParamValue;
import org.kuali.student.core.search.dto.Result;
import org.kuali.student.core.search.dto.SearchCriteriaTypeInfo;
import org.kuali.student.core.search.dto.SearchResultTypeInfo;
import org.kuali.student.core.search.dto.SearchTypeInfo;
import org.kuali.student.core.validation.dto.ValidationResultContainer;

/**
 *
 * @author nwright
 */
public class MockProposalService implements ProposalService
{


 private static int maxId = 0;
 private static Map<String, ProposalInfo> proposals = new Hashtable ();
 private static Map<String, ProposalTypeInfo> proposalTypes;
 private static Map<String, String> proposalTypeReferenceType;
 private static final String REFERENCE_TYPE_CLU_ID = "clu.id";
 private static final String REFERENCE_TYPE_ORG_ID = "org.id";

 static
 {
  proposalTypes = new Hashtable ();
  proposalTypeReferenceType = new Hashtable ();
  ProposalTypeInfo info = null;

  info = new ProposalTypeInfo ();
  info.setId ("kuali.new.credit.course.proposal");
  info.setDesc ("Proposal for a new credit course");
  info.setName ("New Credit Course");
  info.setEffectiveDate (new Date ());
  proposalTypes.put (info.getId (), info);
  proposalTypeReferenceType.put (info.getId (), REFERENCE_TYPE_CLU_ID);

  info = new ProposalTypeInfo ();
  info.setId ("kuali.modify.credit.course.proposal");
  info.setDesc ("Proposal to modify a credit course");
  info.setName ("Modify Credit Course");
  info.setEffectiveDate (new Date ());
  proposalTypes.put (info.getId (), info);
  proposalTypeReferenceType.put (info.getId (), REFERENCE_TYPE_CLU_ID);

  info = new ProposalTypeInfo ();
  info.setId ("kuali.retire.credit.course.proposal");
  info.setDesc ("Proposal to retire a credit course");
  info.setName ("Retire a Credit Course");
  info.setEffectiveDate (new Date ());
  proposalTypes.put (info.getId (), info);
  proposalTypeReferenceType.put (info.getId (), REFERENCE_TYPE_CLU_ID);


  info = new ProposalTypeInfo ();
  info.setId ("kuali.non.credit.course.proposal");
  info.setDesc ("Proposal to create or modify a non-credit course");
  info.setName ("Non-Credit Course Proposal");
  info.setEffectiveDate (new Date ());
  proposalTypes.put (info.getId (), info);
  proposalTypeReferenceType.put (info.getId (), REFERENCE_TYPE_CLU_ID);


  info = new ProposalTypeInfo ();
  info.setId ("kuali.new.department.proposal");
  info.setDesc ("Proposal to create a new department");
  info.setName ("New Department Proposal");
  info.setEffectiveDate (new Date ());
  proposalTypes.put (info.getId (), info);
  proposalTypeReferenceType.put (info.getId (), REFERENCE_TYPE_ORG_ID);
 }

 private static Set<String> proposalStates = new HashSet ();

 static
 {
  proposalStates.add ("draft.private");
  proposalStates.add ("draft.public");
  proposalStates.add ("submitted");
  proposalStates.add ("approve");
  proposalStates.add ("not approved");
  proposalStates.add ("withdrawn");
  proposalStates.add ("retired");
  proposalStates.add ("inactive");
 }

 private static Set<String> referenceTypes = new HashSet ();

 static
 {
  referenceTypes.add (REFERENCE_TYPE_CLU_ID);
  referenceTypes.add (REFERENCE_TYPE_ORG_ID);

 }

 @Override
 public ProposalInfo createProposal (String proposalTypeKey,
                                     ProposalInfo proposalInfo)
  throws AlreadyExistsException,
         DataValidationErrorException,
         DoesNotExistException,
         InvalidParameterException,
         MissingParameterException,
         OperationFailedException,
         PermissionDeniedException
 {
  if (proposalInfo.getId () != null)
  {
   throw new InvalidParameterException ("proposal Id cannot already be assigned");
  }
  if (proposalInfo.getType () == null)
  {
   throw new MissingParameterException ("type is required");
  }
  if ( ! proposalInfo.getType ().equals (proposalTypeKey))
  {
   throw new InvalidParameterException ("proposal type on info must be the same as parameter");
  }
  if ( ! proposalTypes.containsKey (proposalInfo.getType ()))
  {
   throw new InvalidParameterException ("type");
  }
  if (proposalInfo.getState () == null)
  {
   throw new MissingParameterException ("state is required");
  }
  if ( ! proposalStates.contains (proposalInfo.getState ()))
  {
   throw new InvalidParameterException ("state");
  }

  ProposalInfo info = (ProposalInfo) new DeepCopier (proposalInfo).copy ();
  maxId ++;
  info.setId (maxId + "");
  info.setMetaInfo (new MetaInfoUtility ().getForCreate ());
  proposals.put (info.getId (), info);
  return (ProposalInfo) new DeepCopier (info).copy ();
 }

 @Override
 public ProposalDocRelationInfo createProposalDocRelation (
  String proposalDocRelationType,
  String documentId,
  String proposalId,
  ProposalDocRelationInfo proposalDocRelationInfo)
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
 public StatusInfo deleteProposal (String proposalId)
  throws DoesNotExistException,
         InvalidParameterException,
         MissingParameterException,
         DependentObjectsExistException,
         OperationFailedException,
         PermissionDeniedException
 {
  ProposalInfo info = proposals.get (proposalId);
  if (info == null)
  {
   throw new DoesNotExistException ();
  }
  proposals.remove (proposalId);
  StatusInfo status = new StatusInfo ();
  status.setSuccess (Boolean.TRUE);
  return status;
 }

 @Override
 public StatusInfo deleteProposalDocRelation (String proposalDocRelationId)
  throws DoesNotExistException,
         InvalidParameterException,
         MissingParameterException,
         OperationFailedException,
         PermissionDeniedException
 {
  throw new UnsupportedOperationException ("Not supported yet.");
 }

 @Override
 public List<String> getAllowedProposalDocRelationTypesForProposalType (
  String proposalTypeKey)
  throws DoesNotExistException,
         InvalidParameterException,
         MissingParameterException,
         OperationFailedException
 {
  throw new UnsupportedOperationException ("Not supported yet.");
 }

 @Override
 public ProposalInfo getProposal (String proposalId)
  throws DoesNotExistException,
         InvalidParameterException,
         MissingParameterException,
         OperationFailedException
 {
  ProposalInfo info = proposals.get (proposalId);
  if (info == null)
  {
   throw new DoesNotExistException ();
  }
  return (ProposalInfo) new DeepCopier (info).copy ();
 }

 @Override
 public ProposalDocRelationInfo getProposalDocRelation (
  String proposalDocRelationId)
  throws DoesNotExistException,
         InvalidParameterException,
         MissingParameterException,
         OperationFailedException
 {
  throw new UnsupportedOperationException ("Not supported yet.");
 }

 @Override
 public ProposalDocRelationTypeInfo getProposalDocRelationType (
  String proposalDocRelationTypeKey)
  throws DoesNotExistException,
         InvalidParameterException,
         MissingParameterException,
         OperationFailedException
 {
  throw new UnsupportedOperationException ("Not supported yet.");
 }

 @Override
 public List<ProposalDocRelationTypeInfo> getProposalDocRelationTypes ()
  throws OperationFailedException
 {
  throw new UnsupportedOperationException ("Not supported yet.");
 }

 @Override
 public List<ProposalDocRelationInfo> getProposalDocRelationsByDocument (
  String documentId)
  throws DoesNotExistException,
         InvalidParameterException,
         MissingParameterException,
         OperationFailedException
 {
  throw new UnsupportedOperationException ("Not supported yet.");
 }

 @Override
 public List<ProposalDocRelationInfo> getProposalDocRelationsByIdList (
  List<String> proposalDocRelationIdList)
  throws DoesNotExistException,
         InvalidParameterException,
         MissingParameterException,
         OperationFailedException
 {
  throw new UnsupportedOperationException ("Not supported yet.");
 }

 @Override
 public List<ProposalDocRelationInfo> getProposalDocRelationsByProposal (
  String proposalId)
  throws DoesNotExistException,
         InvalidParameterException,
         MissingParameterException,
         OperationFailedException
 {
  throw new UnsupportedOperationException ("Not supported yet.");
 }

 @Override
 public List<ProposalDocRelationInfo> getProposalDocRelationsByType (
  String proposalDocRelationTypeKey)
  throws DoesNotExistException,
         InvalidParameterException,
         MissingParameterException,
         OperationFailedException
 {
  throw new UnsupportedOperationException ("Not supported yet.");
 }

 @Override
 public ProposalTypeInfo getProposalType (String proposalTypeKey)
  throws DoesNotExistException,
         InvalidParameterException,
         MissingParameterException,
         OperationFailedException
 {
  ProposalTypeInfo info = proposalTypes.get (proposalTypeKey);
  if (info == null)
  {
   throw new DoesNotExistException ();
  }
  return (ProposalTypeInfo) new DeepCopier (info).copy ();
 }

 @Override
 public List<ProposalTypeInfo> getProposalTypes ()
  throws OperationFailedException
 {
  return new ArrayList (proposalTypes.values ());
 }

 @Override
 public List<ProposalTypeInfo> getProposalTypesForReferenceType (
  String referenceTypeKey)
  throws DoesNotExistException,
         InvalidParameterException,
         MissingParameterException,
         OperationFailedException
 {
  if ( ! referenceTypes.contains (referenceTypeKey))
  {
   throw new InvalidParameterException ();
  }
  List<ProposalTypeInfo> list = new ArrayList ();
  for (ProposalTypeInfo info : proposalTypes.values ())
  {
   if (proposalTypeReferenceType.get (info.getId ()).equals (referenceTypeKey))
   {
    list.add (info);
   }
  }
  return list;
 }

 @Override
 public List<ProposalInfo> getProposalsByIdList (List<String> proposalIdList)
  throws DoesNotExistException,
         InvalidParameterException,
         MissingParameterException,
         OperationFailedException
 {
  List<ProposalInfo> list = new ArrayList ();
  for (ProposalInfo info : proposals.values ())
  {
   if (proposalIdList.contains (info.getId ()))
   {
    list.add (info);
   }
  }
  if (list.size () != proposalIdList.size ())
  {
   throw new DoesNotExistException ();
  }
  return list;
 }

 @Override
 public List<ProposalInfo> getProposalsByProposalType (String proposalTypeKey)
  throws DoesNotExistException,
         InvalidParameterException,
         MissingParameterException,
         OperationFailedException
 {
  if ( ! proposalTypes.keySet ().contains (proposalTypeKey))
  {
   throw new DoesNotExistException ();
  }
  List<ProposalInfo> list = new ArrayList ();
  for (ProposalInfo info : proposals.values ())
  {
   if (info.getType ().equals (proposalTypeKey))
   {
    list.add (info);
   }
  }
  return list;
 }

 @Override
 public List<ProposalInfo> getProposalsByReference (String referenceTypeKey,
                                                    String referenceId)
  throws DoesNotExistException,
         InvalidParameterException,
         MissingParameterException,
         OperationFailedException
 {
  if ( ! referenceTypes.contains (referenceTypeKey))
  {
   throw new InvalidParameterException ();
  }
  List<ProposalInfo> list = new ArrayList ();
  for (ProposalInfo info : proposals.values ())
  {
   if (info.getProposalReferenceType ().equals (referenceTypeKey))
   {
    if (info.getProposalReference ().contains (referenceId))
    {
     list.add (info);
    }
   }
  }
  return list;
 }

 @Override
 public List<ProposalInfo> getProposalsByState (String proposalState,
                                                String proposalTypeKey)
  throws DoesNotExistException,
         InvalidParameterException,
         MissingParameterException,
         OperationFailedException
 {
  if ( ! proposalStates.contains (proposalState))
  {
   throw new InvalidParameterException ();
  }
  List<ProposalInfo> list = new ArrayList ();
  for (ProposalInfo info : proposals.values ())
  {
   if (info.getState ().equals (proposalState))
   {
    list.add (info);
   }
  }
  return list;
 }

 @Override
 public List<ReferenceTypeInfo> getReferenceTypes ()
  throws OperationFailedException
 {
  return new ArrayList (referenceTypes);
 }

 @Override
 public ProposalInfo updateProposal (String proposalId,
                                     ProposalInfo proposalInfo)
  throws DataValidationErrorException,
         DoesNotExistException,
         InvalidParameterException,
         MissingParameterException,
         OperationFailedException,
         PermissionDeniedException,
         VersionMismatchException
 {
  if ( ! proposals.containsKey (proposalId))
  {
   throw new DoesNotExistException ();
  }
  ProposalInfo info = (ProposalInfo) new DeepCopier (proposalInfo).copy ();
  new MetaInfoUtility ().update (info.getMetaInfo ());
  proposals.put (proposalId, info);
  return (ProposalInfo) new DeepCopier (info).copy ();
 }

 @Override
 public ProposalDocRelationInfo updateProposalDocRelation (
  String proposalDocRelationId,
  ProposalDocRelationInfo proposalDocRelationInfo)
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
 public List<ValidationResultContainer> validateProposal (String validationType,
                                                          ProposalInfo proposalInfo)
  throws DoesNotExistException,
         InvalidParameterException,
         MissingParameterException,
         OperationFailedException
 {
  throw new UnsupportedOperationException ("Not supported yet.");
 }

 @Override
 public List<ValidationResultContainer> validateProposalDocRelation (
  String validationType,
  ProposalDocRelationInfo proposalDocRelationInfo)
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

}
