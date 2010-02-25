/*
 * Copyright 2010 The Kuali Foundation
 *
 * Licensed under the Educational Community License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may	obtain a copy of the License at
 *
 * 	http://www.osedu.org/licenses/ECL-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.kuali.student.service.lu.dev.api;


import java.util.List;


public interface LuService
{
	
	/**
	* Retrieves the list of delivery method types
	* 
	* @return list of delivery method type information
	*/
	public List<DeliveryMethodTypeInfo> getDeliveryMethodTypes()
		throws OperationFailedException
	;
	
	/**
	* Retrieves information about a delivery method type
	* 
	* @param deliveryMethodTypeKey Key of the Delivery Method Type
	* @return information about a Delivery Method Type
	*/
	public DeliveryMethodTypeInfo getDeliveryMethodType(String deliveryMethodTypeKey)
		throws DoesNotExistException
		      ,InvalidParameterException
		      ,MissingParameterException
		      ,OperationFailedException
	;
	
	/**
	* Retrieves the list of instructional format types
	* 
	* @return list of instructional format type information
	*/
	public List<InstructionalFormatTypeInfo> getInstructionalFormatTypes()
		throws OperationFailedException
	;
	
	/**
	* Retrieves information about a Instructional Format Type
	* 
	* @param instructionalFormatTypeKey Key of the Instructional Format Type
	* @return information about a Instructional Format Type
	*/
	public InstructionalFormatTypeInfo getInstructionalFormatType(String instructionalFormatTypeKey)
		throws DoesNotExistException
		      ,InvalidParameterException
		      ,MissingParameterException
		      ,OperationFailedException
	;
	
	/**
	* Retrieves the list of LU types
	* 
	* @return list of LU type information
	*/
	public List<LuTypeInfo> getLuTypes()
		throws OperationFailedException
	;
	
	/**
	* Retrieves information about a LU Type
	* 
	* @param luTypeKey Key of the LU Type
	* @return information about a LU Type
	*/
	public LuTypeInfo getLuType(String luTypeKey)
		throws DoesNotExistException
		      ,InvalidParameterException
		      ,MissingParameterException
		      ,OperationFailedException
	;
	
	/**
	* Retrieves the list of learning unit code types
	* 
	* @return list of lu code type information
	*/
	public List<LuCodeTypeInfo> getLuCodeTypes()
		throws OperationFailedException
	;
	
	/**
	* Retrieves information about a learning unit code type
	* 
	* @param luCodeTypeKey Key of the learning unit code type
	* @return information about a learning unit code type
	*/
	public LuCodeTypeInfo getLuCodeType(String luCodeTypeKey)
		throws DoesNotExistException
		      ,InvalidParameterException
		      ,MissingParameterException
		      ,OperationFailedException
	;
	
	/**
	* Retrieves the complete list of LU to LU relation types
	* 
	* @return list of LU to LU relation type information
	*/
	public List<LuLuRelationTypeInfo> getLuLuRelationTypes()
		throws OperationFailedException
	;
	
	/**
	* Retrieves the LU to LU relation type
	* 
	* @param luLuRelationTypeKey Key of the LU to LU Relation Type
	* @return LU to LU relation type information
	*/
	public LuLuRelationTypeInfo getLuLuRelationType(String luLuRelationTypeKey)
		throws OperationFailedException
		      ,MissingParameterException
		      ,DoesNotExistException
	;
	
	/**
	* Retrieves the list of allowed relation types between the two specified LU Types
	* 
	* @param luTypeKey Key of the first LU Type
	* @param relatedLuTypeKey Key of the second LU Type
	* @return list of LU to LU relation types
	*/
	public List<String> getAllowedLuLuRelationTypesForLuType(String luTypeKey, String relatedLuTypeKey)
		throws DoesNotExistException
		      ,InvalidParameterException
		      ,MissingParameterException
		      ,OperationFailedException
	;
	
	/**
	* Retrieves the list of Learning Unit publication types
	* 
	* @return list of Learning Unit publication type information
	*/
	public List<LuPublicationTypeInfo> getLuPublicationTypes()
		throws OperationFailedException
	;
	
	/**
	* Retrieves information about a publication type
	* 
	* @param luPublicationTypeKey Key of the Learning Unit Publication Type
	* @return information about a Learning Unit Publication Type
	*/
	public LuPublicationTypeInfo getLuPublicationType(String luPublicationTypeKey)
		throws DoesNotExistException
		      ,InvalidParameterException
		      ,MissingParameterException
		      ,OperationFailedException
	;
	
	/**
	* Retrieves the list of allowed publication types for the specified LU Type
	* 
	* @param luTypeKey Key of the LU Type
	* @return list of LU publication types
	*/
	public List<String> getLuPublicationTypesForLuType(String luTypeKey)
		throws DoesNotExistException
		      ,InvalidParameterException
		      ,MissingParameterException
		      ,OperationFailedException
	;
	
	/**
	* Retrieves a list of types for clu result objects.
	* 
	* @return list of clu result type information
	*/
	public List<CluResultTypeInfo> getCluResultTypes()
		throws OperationFailedException
	;
	
	/**
	* Retrieves information about a clu result object type
	* 
	* @param cluResultTypeKey Key of the Canonical Learning Unit Result Type
	* @return information about a Learning Unit Publication Type
	*/
	public CluResultTypeInfo getCluResultType(String cluResultTypeKey)
		throws DoesNotExistException
		      ,InvalidParameterException
		      ,MissingParameterException
		      ,OperationFailedException
	;
	
	/**
	* Retrieves the list of clu result types which are allowed to be used in 
	* conjunction with a particular lu type.
	* 
	* @param luTypeKey luTypeKey
	* @return list of clu result types
	*/
	public List<String> getCluResultTypesForLuType(String luTypeKey)
		throws DoesNotExistException
		      ,InvalidParameterException
		      ,MissingParameterException
		      ,OperationFailedException
	;
	
	/**
	* Retrieves a list of result usage types
	* 
	* @return list of result usage type information
	*/
	public List<ResultUsageTypeInfo> getResultUsageTypes()
		throws OperationFailedException
	;
	
	/**
	* Retrieves information about a Result Usage Type
	* 
	* @param resultUsageTypeKey Key of the Result Usage Type
	* @return information about a Result Usage Type
	*/
	public ResultUsageTypeInfo getResultUsageType(String resultUsageTypeKey)
		throws DoesNotExistException
		      ,InvalidParameterException
		      ,MissingParameterException
		      ,OperationFailedException
	;
	
	/**
	* Retrieves the list of result usage types which are allowed to be used in 
	* conjunction with an lu type.
	* 
	* @param luTypeKey luTypeKey
	* @return list of result usage types
	*/
	public List<String> getAllowedResultUsageTypesForLuType(String luTypeKey)
		throws DoesNotExistException
		      ,InvalidParameterException
		      ,MissingParameterException
		      ,OperationFailedException
	;
	
	/**
	* Retrieves the list of result component types which are allowed to be used in 
	* conjunction with result usage type.
	* 
	* @param resultUsageTypeKey resultUsageTypeKey
	* @return list of result component types
	*/
	public List<String> getAllowedResultComponentTypesForResultUsageType(String resultUsageTypeKey)
		throws DoesNotExistException
		      ,InvalidParameterException
		      ,MissingParameterException
		      ,OperationFailedException
	;
	
	/**
	* Retrieves the complete list of CLU to LO relation types
	* 
	* @return list of CLU to LO relation type information
	*/
	public List<CluLoRelationTypeInfo> getCluLoRelationTypes()
		throws OperationFailedException
	;
	
	/**
	* Retrieves information for a specified CLU to LO relation type
	* 
	* @param cluLoRelationTypeKey Key of the CLU to LO Relation Type
	* @return CLU to LO relation type information
	*/
	public CluLoRelationTypeInfo getCluLoRelationType(String cluLoRelationTypeKey)
		throws DoesNotExistException
		      ,InvalidParameterException
		      ,MissingParameterException
		      ,OperationFailedException
	;
	
	/**
	* Retrieves the list of CLU LO relation types which are allowed to be used in 
	* conjunction with an lu type.
	* 
	* @param luTypeKey luTypeKey
	* @return list of clu lo relation types
	*/
	public List<String> getAllowedCluLoRelationTypesForLuType(String luTypeKey)
		throws DoesNotExistException
		      ,InvalidParameterException
		      ,MissingParameterException
		      ,OperationFailedException
	;
	
	/**
	* Retrieves the list of CLU set types known by the service
	* 
	* @return list of CLU set type information
	*/
	public List<CluSetTypeInfo> getCluSetTypes()
		throws OperationFailedException
	;
	
	/**
	* Retrieves information about a specified CLU set type
	* 
	* @param cluSetTypeKey Key of the CLU set type
	* @return information about a CLU set type
	*/
	public CluSetTypeInfo getCluSetType(String cluSetTypeKey)
		throws DoesNotExistException
		      ,InvalidParameterException
		      ,MissingParameterException
		      ,OperationFailedException
	;
	
	/**
	* Retrieves core information about a CLU
	* 
	* @param cluId identifier of the CLU
	* @return information about a CLU
	*/
	public CluInfo getClu(String cluId)
		throws DoesNotExistException
		      ,InvalidParameterException
		      ,MissingParameterException
		      ,OperationFailedException
	;
	
	/**
	* Retrieves information about CLUs from a list of ids
	* 
	* @param cluIdList List of CLU identifiers
	* @return information a list of CLUs
	*/
	public List<CluInfo> getClusByIdList(List<String> cluIdList)
		throws DoesNotExistException
		      ,InvalidParameterException
		      ,MissingParameterException
		      ,OperationFailedException
	;
	
	/**
	* Retrieves the list of CLUs for the specified LU Type and state
	* 
	* @param luTypeKey Type of the CLUs to retrieve
	* @param luState State of the CLUs to retrieve.
	* @return list of CLU information
	*/
	public List<CluInfo> getClusByLuType(String luTypeKey, String luState)
		throws DoesNotExistException
		      ,InvalidParameterException
		      ,MissingParameterException
		      ,OperationFailedException
	;
	
	/**
	* Retrieves the list of CLU ids for the specified LU Type and state
	* 
	* @param luTypeKey Type of the CLUs whose identifiers should be retrieved
	* @param luState State of the CLUs whose identifiers should be retrieved
	* @return list of CLU identifiers
	*/
	public List<String> getCluIdsByLuType(String luTypeKey, String luState)
		throws DoesNotExistException
		      ,InvalidParameterException
		      ,MissingParameterException
		      ,OperationFailedException
	;
	
	/**
	* Retrieves the list of allowed relation types between the two specified CLUs
	* 
	* @param cluId identifier of the first CLU
	* @param relatedCluId identifier of the second CLU
	* @return list of LU to LU relation types
	*/
	public List<String> getAllowedLuLuRelationTypesByCluId(String cluId, String relatedCluId)
		throws DoesNotExistException
		      ,InvalidParameterException
		      ,MissingParameterException
		      ,OperationFailedException
	;
	
	/**
	* from the other direction)
	* 
	* @param relatedCluId identifier of the child or To CLU
	* @param luLuRelationType the LU to LU relation type
	* @return list of CLU information
	*/
	public List<CluInfo> getClusByRelation(String relatedCluId, String luLuRelationType)
		throws DoesNotExistException
		      ,InvalidParameterException
		      ,MissingParameterException
		      ,OperationFailedException
	;
	
	/**
	* from the other direction)
	* 
	* @param relatedCluId identifier of the child or To CLU
	* @param luLuRelationType the LU to LU relation type
	* @return list of CLU identifiers
	*/
	public List<String> getCluIdsByRelation(String relatedCluId, String luLuRelationType)
		throws DoesNotExistException
		      ,InvalidParameterException
		      ,MissingParameterException
		      ,OperationFailedException
	;
	
	/**
	* from the other direction)
	* 
	* @param cluId identifier of the parent or From CLU
	* @param luLuRelationType the LU to LU relation type
	* @return list of CLU information
	*/
	public List<CluInfo> getRelatedClusByCluId(String cluId, String luLuRelationType)
		throws DoesNotExistException
		      ,InvalidParameterException
		      ,MissingParameterException
		      ,OperationFailedException
	;
	
	/**
	* from the other direction)
	* 
	* @param cluId identifier of the parent or From CLU
	* @param luLuRelationType the LU to LU relation type
	* @return list of CLU identifiers
	*/
	public List<String> getRelatedCluIdsByCluId(String cluId, String luLuRelationType)
		throws DoesNotExistException
		      ,InvalidParameterException
		      ,MissingParameterException
		      ,OperationFailedException
	;
	
	/**
	* Retrieves the relationship information between CLUs for a particular Relation 
	* instance
	* 
	* @param cluCluRelationId identifier of the CLU to CLU relation
	* @return information on the relation between two CLUs
	*/
	public CluCluRelationInfo getCluCluRelation(String cluCluRelationId)
		throws DoesNotExistException
		      ,InvalidParameterException
		      ,MissingParameterException
		      ,OperationFailedException
	;
	
	/**
	* Retrieves the list of relationship information for the specified CLU
	* 
	* @param cluId identifier of the parent or From CLU
	* @return list of CLU to CLU relation information
	*/
	public List<CluCluRelationInfo> getCluCluRelationsByClu(String cluId)
		throws DoesNotExistException
		      ,InvalidParameterException
		      ,MissingParameterException
		      ,OperationFailedException
	;
	
	/**
	* Retrieves a list of publication objects for a particular clu
	* 
	* @param cluId clu identifier
	* @return list of publication objects used by the specified clu
	*/
	public List<CluPublicationInfo> getCluPublicationsByCluId(String cluId)
		throws DoesNotExistException
		      ,InvalidParameterException
		      ,MissingParameterException
		      ,OperationFailedException
	;
	
	/**
	* Retrieves a list of publication objects of a particular Type
	* 
	* @param luPublicationTypeKey luPublicationType identifier
	* @return list of CLU Publication objects using the specified type
	*/
	public List<CluPublicationInfo> getCluPublicationsByType(String luPublicationTypeKey)
		throws DoesNotExistException
		      ,InvalidParameterException
		      ,MissingParameterException
		      ,OperationFailedException
	;
	
	/**
	* Retrieves an LU publication object by its identifier
	* 
	* @param cluPublicationId CLU publication identifier
	* @return CLU Publication information
	*/
	public CluPublicationInfo getCluPublication(String cluPublicationId)
		throws DoesNotExistException
		      ,InvalidParameterException
		      ,MissingParameterException
		      ,OperationFailedException
	;
	
	/**
	* Retrieves information about a Clu Result
	* 
	* @param cluResultId identifier of the Clu Result
	* @return information about a Clu Result
	*/
	public CluResultInfo getCluResult(String cluResultId)
		throws DoesNotExistException
		      ,InvalidParameterException
		      ,MissingParameterException
		      ,OperationFailedException
	;
	
	/**
	* Retrieves the list of cluResults associated with a particular clu
	* 
	* @param cluId clu identifier
	* @return result information for a clu
	*/
	public List<CluResultInfo> getCluResultsByClu(String cluId)
		throws DoesNotExistException
		      ,InvalidParameterException
		      ,MissingParameterException
		      ,OperationFailedException
	;
	
	/**
	* Retrieves the list of clu ids with the results of the specified usage type. This 
	* would for example allow requests for all clus which have a final grade.
	* 
	* @param resultUsageTypeKey identifier of the result usage type
	* @return list of clu ids
	*/
	public List<String> getCluIdsByResultUsageType(String resultUsageTypeKey)
		throws DoesNotExistException
		      ,InvalidParameterException
		      ,MissingParameterException
		      ,OperationFailedException
	;
	
	/**
	* Retrieves the list of clu ids which use a particular result component
	* 
	* @param resultComponentId identifier of the result component
	* @return list of clu ids
	*/
	public List<String> getCluIdsByResultComponent(String resultComponentId)
		throws DoesNotExistException
		      ,InvalidParameterException
		      ,MissingParameterException
		      ,OperationFailedException
	;
	
	/**
	* Retrieve information on a CLU LO Relation.
	* 
	* @param cluLoRelationId Identifier of the CLU LO Relation
	* @return The retrieved CLU LO Relation information
	*/
	public CluLoRelationInfo getCluLoRelation(String cluLoRelationId)
		throws DoesNotExistException
		      ,InvalidParameterException
		      ,MissingParameterException
		      ,OperationFailedException
		      ,PermissionDeniedException
	;
	
	/**
	* Retrieves the list of canonical learning unit to learning objective 
	* relationships for a given CLU.
	* 
	* @param cluId Identifier for the CLU
	* @return List of canonical learning unit to learning objective relationships
	*/
	public List<CluLoRelationInfo> getCluLoRelationsByClu(String cluId)
		throws DoesNotExistException
		      ,InvalidParameterException
		      ,MissingParameterException
		      ,OperationFailedException
	;
	
	/**
	* Retrieves the list of CLU identifiers associated with a given learning objective 
	* identifier.
	* 
	* @param loId Identifier for the learning objective
	* @return List of CLU LO Relations
	*/
	public List<CluLoRelationInfo> getCluLoRelationsByLo(String loId)
		throws DoesNotExistException
		      ,InvalidParameterException
		      ,MissingParameterException
		      ,OperationFailedException
	;
	
	/**
	* Retrieves the list of Resource requirements for the specified CLU
	* 
	* @param cluId Unique identifier for the CLU
	* @return List of resource requirements
	*/
	public List<String> getResourceRequirementsForCluId(String cluId)
		throws DoesNotExistException
		      ,InvalidParameterException
		      ,MissingParameterException
		      ,OperationFailedException
	;
	
	/**
	* Retrieve information on a CLU set. This information should be about the set 
	* itself, and in the case of a dynamic CLU set, should include the criteria used 
	* to generate the set.
	* 
	* @param cluSetId Identifier of the CLU set
	* @return The retrieved CLU set information
	*/
	public CluSetInfo getCluSetInfo(String cluSetId)
		throws DoesNotExistException
		      ,InvalidParameterException
		      ,MissingParameterException
		      ,OperationFailedException
		      ,PermissionDeniedException
	;
	
	/**
	* Retrieve information on CLU sets from a list of cluSet Ids.
	* 
	* @param cluSetIdList List of identifiers of CLU sets
	* @return The retrieved list of CLU set information
	*/
	public List<CluSetInfo> getCluSetInfoByIdList(List<String> cluSetIdList)
		throws DoesNotExistException
		      ,InvalidParameterException
		      ,MissingParameterException
		      ,OperationFailedException
		      ,PermissionDeniedException
	;
	
	/**
	* Retrieve the list of CLU Set Ids within a CLU Set
	* 
	* @param cluSetId Identifier of the CLU set
	* @return The retrieved list of CLU Set Ids within the specified CLU set
	*/
	public List<String> getCluSetIdsFromCluSet(String cluSetId)
		throws DoesNotExistException
		      ,InvalidParameterException
		      ,MissingParameterException
		      ,OperationFailedException
		      ,PermissionDeniedException
	;
	
	/**
	* Retrieves the list of CLUs in a CLU set. This only retrieves the direct members.
	* 
	* @param cluSetId Identifier of the CLU set
	* @return The retrieved list of information on the CLUs within the CLU set 
	* (flattened and de-duped)
	*/
	public List<CluInfo> getClusFromCluSet(String cluSetId)
		throws DoesNotExistException
		      ,InvalidParameterException
		      ,MissingParameterException
		      ,OperationFailedException
		      ,PermissionDeniedException
	;
	
	/**
	* Retrieves the list of CLU Identifiers within a CLU Set. This only retrieves the 
	* direct members.
	* 
	* @param cluSetId Identifier of the CLU set
	* @return The retrieved list of CLU Ids within the specified CLU set (flattened 
	* and de-duped)
	*/
	public List<String> getCluIdsFromCluSet(String cluSetId)
		throws DoesNotExistException
		      ,InvalidParameterException
		      ,MissingParameterException
		      ,OperationFailedException
		      ,PermissionDeniedException
	;
	
	/**
	* Retrieves the full list of CLUs in this CLU set or any cluset that is included 
	* within that.
	* 
	* @param cluSetId Identifier of the CLU set
	* @return The retrieved list of information on the CLUs
	*/
	public List<CluInfo> getAllClusInCluSet(String cluSetId)
		throws DoesNotExistException
		      ,InvalidParameterException
		      ,MissingParameterException
		      ,OperationFailedException
		      ,PermissionDeniedException
	;
	
	/**
	* Retrieves the list of CLU Identifiers within a CLU Set or any cluset that is 
	* included within that.
	* 
	* @param cluSetId Identifier of the CLU set
	* @return The retrieved list of CLU Ids within the specified CLU set
	*/
	public List<String> getAllCluIdsInCluSet(String cluSetId)
		throws DoesNotExistException
		      ,InvalidParameterException
		      ,MissingParameterException
		      ,OperationFailedException
		      ,PermissionDeniedException
	;
	
	/**
	* Checks if a CLU is a member of a CLU set or any contained CLU set
	* 
	* @param cluId Identifier of the CLU to check
	* @param cluSetId Identifier of the CLU set
	* @return True if the CLU is a member of the CLU Set
	*/
	public Boolean isCluInCluSet(String cluId, String cluSetId)
		throws DoesNotExistException
		      ,InvalidParameterException
		      ,MissingParameterException
		      ,OperationFailedException
		      ,PermissionDeniedException
	;
	
	/**
	* Retrieves information about a LUI
	* 
	* @param luiId identifier of the LUI
	* @return information about a LUI
	*/
	public LuiInfo getLui(String luiId)
		throws DoesNotExistException
		      ,InvalidParameterException
		      ,MissingParameterException
		      ,OperationFailedException
	;
	
	/**
	* Retrieves information about LUIs from a list of Ids
	* 
	* @param luiIdList List of LUI identifiers
	* @return information about a list of LUIs
	*/
	public List<LuiInfo> getLuisByIdList(List<String> luiIdList)
		throws DoesNotExistException
		      ,InvalidParameterException
		      ,MissingParameterException
		      ,OperationFailedException
	;
	
	/**
	* Retrieves the list of LUIs for the specified CLU and period
	* 
	* @param cluId identifier of the CLU
	* @param atpKey identifier for the academic time period
	* @return list of LUI information
	*/
	public List<LuiInfo> getLuisInAtpByCluId(String cluId, String atpKey)
		throws DoesNotExistException
		      ,InvalidParameterException
		      ,MissingParameterException
		      ,OperationFailedException
	;
	
	/**
	* Retrieves the list of LUI ids for the specified CLU
	* 
	* @param cluId identifier of the CLU
	* @return list of LUI identifiers
	*/
	public List<String> getLuiIdsByCluId(String cluId)
		throws DoesNotExistException
		      ,InvalidParameterException
		      ,MissingParameterException
		      ,OperationFailedException
	;
	
	/**
	* Retrieves the list of LUI ids for the specified CLU and Time period
	* 
	* @param cluId identifier of the CLU
	* @param atpKey identifier for the academic time period
	* @return list of LUI identifiers
	*/
	public List<String> getLuiIdsInAtpByCluId(String cluId, String atpKey)
		throws DoesNotExistException
		      ,InvalidParameterException
		      ,MissingParameterException
		      ,OperationFailedException
	;
	
	/**
	* Retrieves the list of allowed relation types between the two specified LUIs
	* 
	* @param luiId identifier of the first LUI
	* @param relatedLuiId identifier of the second LUI
	* @return list of LU to LU relation types
	*/
	public List<String> getAllowedLuLuRelationTypesByLuiId(String luiId, String relatedLuiId)
		throws DoesNotExistException
		      ,InvalidParameterException
		      ,MissingParameterException
		      ,OperationFailedException
	;
	
	/**
	* from the other direction)
	* 
	* @param relatedLuiId identifier of the LUI
	* @param luLuRelationType the LU to LU relation type
	* @return list of LUI information
	*/
	public List<LuiInfo> getLuisByRelation(String relatedLuiId, String luLuRelationType)
		throws DoesNotExistException
		      ,InvalidParameterException
		      ,MissingParameterException
		      ,OperationFailedException
	;
	
	/**
	* from the other direction)
	* 
	* @param relatedLuiId identifier of the LUI
	* @param luLuRelationType the LU to LU relation type
	* @return list of LUI identifiers
	*/
	public List<String> getLuiIdsByRelation(String relatedLuiId, String luLuRelationType)
		throws DoesNotExistException
		      ,InvalidParameterException
		      ,MissingParameterException
		      ,OperationFailedException
	;
	
	/**
	* from the other direction)
	* 
	* @param luiId identifier of the LUI
	* @param luLuRelationType the LU to LU relation type
	* @return list of LUI information
	*/
	public List<LuiInfo> getRelatedLuisByLuiId(String luiId, String luLuRelationType)
		throws DoesNotExistException
		      ,InvalidParameterException
		      ,MissingParameterException
		      ,OperationFailedException
	;
	
	/**
	* from the other direction)
	* 
	* @param luiId identifier of the LUI
	* @param luLuRelationType the LU to LU relation type
	* @return list of LUI identifiers
	*/
	public List<String> getRelatedLuiIdsByLuiId(String luiId, String luLuRelationType)
		throws DoesNotExistException
		      ,InvalidParameterException
		      ,MissingParameterException
		      ,OperationFailedException
	;
	
	/**
	* Retrieves the relationship information between LUIs given a specific relation 
	* instance
	* 
	* @param luiLuiRelationId identifier of LUI to LUI relation
	* @return information on the relation between two LUIs
	*/
	public LuiLuiRelationInfo getLuiLuiRelation(String luiLuiRelationId)
		throws DoesNotExistException
		      ,InvalidParameterException
		      ,MissingParameterException
		      ,OperationFailedException
	;
	
	/**
	* Retrieves the list of relationship information for the specified LUI
	* 
	* @param luiId identifier of the LUI
	* @return list of LUI to LUI relation information
	*/
	public List<LuiLuiRelationInfo> getLuiLuiRelationsByLui(String luiId)
		throws DoesNotExistException
		      ,InvalidParameterException
		      ,MissingParameterException
		      ,OperationFailedException
	;
	
	/**
	* Validates a CLU. Depending on the value of validationType, this validation could 
	* be limited to tests on just the current object and its directly contained sub-
	* objects or expanded to perform all tests related to this object. If an 
	* identifier is present for the CLU (and/or one of its contained sub-objects) and 
	* a record is found for that identifier, the validation checks if the CLU can be 
	* shifted to the new values. If an identifier is not present or a record cannot be 
	* found for the identifier, it is assumed that the record does not exist and as 
	* such, the checks performed will be much shallower, typically mimicking those 
	* performed by setting the validationType to the current object.
	* 
	* @param validationType identifier of the extent of validation
	* @param cluInfo CLU information to be tested.
	* @return results from performing the validation
	*/
	public List<ValidationResultInfo> validateClu(String validationType, CluInfo cluInfo)
		throws DoesNotExistException
		      ,InvalidParameterException
		      ,MissingParameterException
		      ,OperationFailedException
	;
	
	/**
	* Creates a new CLU
	* 
	* @param luTypeKey identifier of the LU Type for the CLU being created
	* @param cluInfo information about the CLU being created
	* @return the created CLU information
	*/
	public CluInfo createClu(String luTypeKey, CluInfo cluInfo)
		throws AlreadyExistsException
		      ,DataValidationErrorException
		      ,DoesNotExistException
		      ,InvalidParameterException
		      ,MissingParameterException
		      ,OperationFailedException
		      ,PermissionDeniedException
	;
	
	/**
	* Updates an existing CLU
	* 
	* @param cluId identifier for the CLU to be updated
	* @param cluInfo updated information about the CLU
	* @return the updated CLU information
	*/
	public CluInfo updateClu(String cluId, CluInfo cluInfo)
		throws DataValidationErrorException
		      ,DoesNotExistException
		      ,InvalidParameterException
		      ,MissingParameterException
		      ,OperationFailedException
		      ,PermissionDeniedException
		      ,VersionMismatchException
	;
	
	/**
	* Deletes an existing CLU
	* 
	* @param cluId identifier for the CLU to be deleted
	* @return status of the operation
	*/
	public StatusInfo deleteClu(String cluId)
		throws DoesNotExistException
		      ,InvalidParameterException
		      ,MissingParameterException
		      ,DependentObjectsExistException
		      ,OperationFailedException
		      ,PermissionDeniedException
	;
	
	/**
	* Updates the state of the specified CLU
	* 
	* @param cluId identifier for the CLU to be updated
	* @param luState new state for the CLU. Value is expected to be constrained to 
	* those in the luState enumeration.
	* @return the updated CLU information
	*/
	public CluInfo updateCluState(String cluId, String luState)
		throws DataValidationErrorException
		      ,DoesNotExistException
		      ,InvalidParameterException
		      ,MissingParameterException
		      ,OperationFailedException
		      ,PermissionDeniedException
	;
	
	/**
	* Validates a cluCluRelation. Depending on the value of validationType, this 
	* validation could be limited to tests on just the current object and its directly 
	* contained sub-objects or expanded to perform all tests related to this object. 
	* If an identifier is present for the cluCluRelation (and/or one of its contained 
	* sub-objects) and a record is found for that identifier, the validation checks if 
	* the relationship can be shifted to the new values. If an identifier is not 
	* present or a record cannot be found for the identifier, it is assumed that the 
	* record does not exist and as such, the checks performed will be much shallower, 
	* typically mimicking those performed by setting the validationType to the current 
	* object.
	* 
	* @param validationType identifier of the extent of validation
	* @param cluCluRelationInfo cluCluRelation information to be tested.
	* @return results from performing the validation
	*/
	public List<ValidationResultInfo> validateCluCluRelation(String validationType, CluCluRelationInfo cluCluRelationInfo)
		throws DoesNotExistException
		      ,InvalidParameterException
		      ,MissingParameterException
		      ,OperationFailedException
	;
	
	/**
	* Create a directional relationship between two CLUs
	* 
	* @param cluId identifier of the first CLU in the relationship - The From or 
	* Parent of the relation
	* @param relatedCluId identifier of the second CLU in the relationship to be 
	* related to - the To or Child of the Relation
	* @param luLuRelationType the LU to LU relationship type of the relationship
	* @param cluCluRelationInfo information about the relationship between the two 
	* CLUs
	* @return the created CLU to CLU relation information
	*/
	public CluCluRelationInfo createCluCluRelation(String cluId, String relatedCluId, String luLuRelationType, CluCluRelationInfo cluCluRelationInfo)
		throws AlreadyExistsException
		      ,CircularRelationshipException
		      ,DataValidationErrorException
		      ,DoesNotExistException
		      ,InvalidParameterException
		      ,MissingParameterException
		      ,OperationFailedException
		      ,PermissionDeniedException
	;
	
	/**
	* Updates a relationship between two CLUs
	* 
	* @param cluCluRelationId identifier of the CLU to CLU relation to be updated
	* @param cluCluRelationInfo changed information about the CLU to CLU relationship
	* @return the updated CLU to CLU relation information
	*/
	public CluCluRelationInfo updateCluCluRelation(String cluCluRelationId, CluCluRelationInfo cluCluRelationInfo)
		throws DataValidationErrorException
		      ,DoesNotExistException
		      ,InvalidParameterException
		      ,MissingParameterException
		      ,OperationFailedException
		      ,PermissionDeniedException
		      ,VersionMismatchException
	;
	
	/**
	* Deletes a relationship between two CLUs
	* 
	* @param cluCluRelationId identifier of CLU to CLU relationship to delete
	* @return status of the operation (success or failure)
	*/
	public StatusInfo deleteCluCluRelation(String cluCluRelationId)
		throws DoesNotExistException
		      ,InvalidParameterException
		      ,MissingParameterException
		      ,OperationFailedException
		      ,PermissionDeniedException
	;
	
	/**
	* Validates information about publication for a clu. Depending on the value of 
	* validationType, this validation could be limited to tests on just the current 
	* object and its directly contained sub-objects or expanded to perform all tests 
	* related to this object. If an identifier is present for the clu publication 
	* object (and/or one of its contained sub-objects) and a record is found for that 
	* identifier, the validation checks if the clu publication object can be shifted 
	* to the new values. If an identifier is not present or a record cannot be found 
	* for the identifier, it is assumed that the record does not exist and as such, 
	* the checks performed will be much shallower, typically mimicking those performed 
	* by setting the validationType to the current object.
	* 
	* @param validationType identifier of the extent of validation
	* @param cluPublicationInfo CLU publication information to be tested.
	* @return results from performing the validation
	*/
	public List<ValidationResultInfo> validateCluPublication(String validationType, CluPublicationInfo cluPublicationInfo)
		throws DoesNotExistException
		      ,InvalidParameterException
		      ,MissingParameterException
		      ,OperationFailedException
	;
	
	/**
	* Create a clu publication object, which contains information about publication 
	* for a clu.
	* 
	* @param cluId identifier of a clu
	* @param luPublicationType type of lu publication
	* @param cluPublicationInfo information about publication for a clu
	* @return information about the created clu publication object
	*/
	public CluPublicationInfo createCluPublication(String cluId, String luPublicationType, CluPublicationInfo cluPublicationInfo)
		throws AlreadyExistsException
		      ,DataValidationErrorException
		      ,InvalidParameterException
		      ,MissingParameterException
		      ,OperationFailedException
		      ,PermissionDeniedException
	;
	
	/**
	* Updates an existing clu publication object
	* 
	* @param cluPublicationId identifier for the clu publication object to be updated
	* @param cluPublicationInfo updated information about the clu publication object
	* @return the updated clu publication information
	*/
	public CluPublicationInfo updateCluPublication(String cluPublicationId, CluPublicationInfo cluPublicationInfo)
		throws DataValidationErrorException
		      ,DoesNotExistException
		      ,InvalidParameterException
		      ,MissingParameterException
		      ,OperationFailedException
		      ,PermissionDeniedException
		      ,VersionMismatchException
	;
	
	/**
	* Deletes an existing clu publication object
	* 
	* @param cluPublicationId identifier for the clu publication object to be deleted
	* @return status of the operation
	*/
	public StatusInfo deleteCluPublication(String cluPublicationId)
		throws DoesNotExistException
		      ,InvalidParameterException
		      ,MissingParameterException
		      ,DependentObjectsExistException
		      ,OperationFailedException
		      ,PermissionDeniedException
	;
	
	/**
	* Validates information about results for a clu. Depending on the value of 
	* validationType, this validation could be limited to tests on just the current 
	* object and its directly contained sub-objects or expanded to perform all tests 
	* related to this object. If an identifier is present for the clu result object 
	* (and/or one of its contained sub-objects) and a record is found for that 
	* identifier, the validation checks if the clu result object can be shifted to the 
	* new values. If an identifier is not present or a record cannot be found for the 
	* identifier, it is assumed that the record does not exist and as such, the checks 
	* performed will be much shallower, typically mimicking those performed by setting 
	* the validationType to the current object.
	* 
	* @param validationType identifier of the extent of validation
	* @param cluResultInfo CLU result information to be tested.
	* @return results from performing the validation
	*/
	public List<ValidationResultInfo> validateCluResult(String validationType, CluResultInfo cluResultInfo)
		throws DoesNotExistException
		      ,InvalidParameterException
		      ,MissingParameterException
		      ,OperationFailedException
	;
	
	/**
	* Create a clu result object, which contains information about potential results 
	* for a clu.
	* 
	* @param cluId identifier of a clu
	* @param cluResultType type of clu result
	* @param cluResultInfo information about potential results for a clu
	* @return information about the created clu result
	*/
	public CluResultInfo createCluResult(String cluId, String cluResultType, CluResultInfo cluResultInfo)
		throws AlreadyExistsException
		      ,DataValidationErrorException
		      ,InvalidParameterException
		      ,MissingParameterException
		      ,OperationFailedException
		      ,PermissionDeniedException
		      ,DoesNotExistException
	;
	
	/**
	* Updates an existing clu result
	* 
	* @param cluResultId identifier for the clu result to be updated
	* @param cluResultInfo updated information about the clu result
	* @return the updated clu result information
	*/
	public CluResultInfo updateCluResult(String cluResultId, CluResultInfo cluResultInfo)
		throws DataValidationErrorException
		      ,DoesNotExistException
		      ,InvalidParameterException
		      ,MissingParameterException
		      ,OperationFailedException
		      ,PermissionDeniedException
		      ,VersionMismatchException
	;
	
	/**
	* Deletes an existing clu result
	* 
	* @param cluResultId identifier for the clu result to be deleted
	* @return status of the operation
	*/
	public StatusInfo deleteCluResult(String cluResultId)
		throws DoesNotExistException
		      ,InvalidParameterException
		      ,MissingParameterException
		      ,DependentObjectsExistException
		      ,OperationFailedException
		      ,PermissionDeniedException
	;
	
	/**
	* Validates a cluLoRelation. Depending on the value of validationType, this 
	* validation could be limited to tests on just the current object and its directly 
	* contained sub-objects or expanded to perform all tests related to this object. 
	* If an identifier is present for the cluLoRelation (and/or one of its contained 
	* sub-objects) and a record is found for that identifier, the validation checks if 
	* the relationship can be shifted to the new values. If an identifier is not 
	* present or a record cannot be found for the identifier, it is assumed that the 
	* record does not exist and as such, the checks performed will be much shallower, 
	* typically mimicking those performed by setting the validationType to the current 
	* object.
	* 
	* @param validationType identifier of the extent of validation
	* @param cluLoRelationInfo cluLoRelation information to be tested.
	* @return results from performing the validation
	*/
	public List<ValidationResultInfo> validateCluLoRelation(String validationType, CluLoRelationInfo cluLoRelationInfo)
		throws DoesNotExistException
		      ,InvalidParameterException
		      ,MissingParameterException
		      ,OperationFailedException
	;
	
	/**
	* Creates a relationship between a learning objective and a CLU.
	* 
	* @param cluId CLU identifier
	* @param loId learning objective identifier
	* @param cluLoRelationType type of clu learning objective relationship
	* @param cluLoRelationInfo clu learning objective relationship information
	* @return the newly created clu learning objective relationship
	*/
	public CluLoRelationInfo createCluLoRelation(String cluId, String loId, String cluLoRelationType, CluLoRelationInfo cluLoRelationInfo)
		throws AlreadyExistsException
		      ,DoesNotExistException
		      ,InvalidParameterException
		      ,MissingParameterException
		      ,OperationFailedException
		      ,PermissionDeniedException
	;
	
	/**
	* Updates a relationship between a clu and learning objective
	* 
	* @param cluLoRelationId identifier of the clu learning objective relationship to 
	* be updated
	* @param cluLoRelationInfo information about the clu learning objective 
	* relationship to be updated
	* @return the updated clu learning objective relationship information
	*/
	public CluLoRelationInfo updateCluLoRelation(String cluLoRelationId, CluLoRelationInfo cluLoRelationInfo)
		throws DataValidationErrorException
		      ,DoesNotExistException
		      ,InvalidParameterException
		      ,MissingParameterException
		      ,OperationFailedException
		      ,PermissionDeniedException
		      ,VersionMismatchException
	;
	
	/**
	* Removes a relationship between a learning objective and a Clu.
	* 
	* @param cluLoRelationId CLU learning objective Relationship identifier
	* @return Status
	*/
	public StatusInfo deleteCluLoRelation(String cluLoRelationId)
		throws DoesNotExistException
		      ,InvalidParameterException
		      ,MissingParameterException
		      ,OperationFailedException
		      ,PermissionDeniedException
	;
	
	/**
	* Add a Resource requirement to a CLU
	* 
	* @param resourceTypeKey identifier of the resource requirement type to be added 
	* to the CLU
	* @param cluId identifier of the CLU
	* @return status of the operation (success or failure)
	*/
	public StatusInfo addCluResourceRequirement(String resourceTypeKey, String cluId)
		throws AlreadyExistsException
		      ,DoesNotExistException
		      ,InvalidParameterException
		      ,MissingParameterException
		      ,OperationFailedException
		      ,PermissionDeniedException
	;
	
	/**
	* Remove a Resource requirement from a CLU
	* 
	* @param resourceTypeKey identifier of the resource type to be removed from the 
	* CLU
	* @param cluId identifier of the CLU
	* @return status of the operation (success or failure)
	*/
	public StatusInfo removeCluResourceRequirement(String resourceTypeKey, String cluId)
		throws DoesNotExistException
		      ,InvalidParameterException
		      ,MissingParameterException
		      ,OperationFailedException
		      ,PermissionDeniedException
	;
	
	/**
	* Validates information about a clu set. Depending on the value of validationType, 
	* this validation could be limited to tests on just the current object and its 
	* directly contained sub-objects or expanded to perform all tests related to this 
	* object. If an identifier is present for the clu set (and/or one of its contained 
	* sub-objects) and a record is found for that identifier, the validation checks if 
	* the clu set can be shifted to the new values. If an identifier is not present or 
	* a record cannot be found for the identifier, it is assumed that the record does 
	* not exist and as such, the checks performed will be much shallower, typically 
	* mimicking those performed by setting the validationType to the current object.
	* 
	* @param validationType identifier of the extent of validation
	* @param cluSetInfo CLU set information to be tested.
	* @return results from performing the validation
	*/
	public List<ValidationResultInfo> validateCluSet(String validationType, CluSetInfo cluSetInfo)
		throws DoesNotExistException
		      ,InvalidParameterException
		      ,MissingParameterException
		      ,OperationFailedException
	;
	
	/**
	* Creates a CLU set.
	* 
	* @param cluSetType type of the CLU set to be created
	* @param cluSetInfo information required to create a CLU set
	* @return the created CLU set information
	*/
	public CluSetInfo createCluSet(String cluSetType, CluSetInfo cluSetInfo)
		throws AlreadyExistsException
		      ,DataValidationErrorException
		      ,InvalidParameterException
		      ,MissingParameterException
		      ,OperationFailedException
		      ,PermissionDeniedException
		      ,DoesNotExistException
	;
	
	/**
	* Update the information for a CLU set
	* 
	* @param cluSetId identifier of the CLU set to be updated
	* @param cluSetInfo updated information about the CLU set
	* @return the updated CLU set information
	*/
	public CluSetInfo updateCluSet(String cluSetId, CluSetInfo cluSetInfo)
		throws DataValidationErrorException
		      ,DoesNotExistException
		      ,InvalidParameterException
		      ,MissingParameterException
		      ,OperationFailedException
		      ,PermissionDeniedException
		      ,VersionMismatchException
		      ,UnsupportedActionException
		      ,CircularRelationshipException
	;
	
	/**
	* Delete a CLU set
	* 
	* @param cluSetId identifier of the CLU set to be deleted
	* @return status of the operation (success or failure)
	*/
	public StatusInfo deleteCluSet(String cluSetId)
		throws DoesNotExistException
		      ,InvalidParameterException
		      ,MissingParameterException
		      ,OperationFailedException
		      ,PermissionDeniedException
	;
	
	/**
	* Adds one CLU set to another
	* 
	* @param cluSetId identifier of the host CLU set
	* @param addedCluSetId identifier of the CLU set to be added
	* @return status of the operation (success or failure)
	*/
	public StatusInfo addCluSetToCluSet(String cluSetId, String addedCluSetId)
		throws CircularRelationshipException
		      ,DoesNotExistException
		      ,InvalidParameterException
		      ,MissingParameterException
		      ,OperationFailedException
		      ,PermissionDeniedException
		      ,UnsupportedActionException
	;
	
	/**
	* Removes one CLU set from another
	* 
	* @param cluSetId identifier of the host CLU set
	* @param removedCluSetId identifier of the CLU set to be removed
	* @return status of the operation (success or failure)
	*/
	public StatusInfo removeCluSetFromCluSet(String cluSetId, String removedCluSetId)
		throws DoesNotExistException
		      ,InvalidParameterException
		      ,MissingParameterException
		      ,OperationFailedException
		      ,PermissionDeniedException
		      ,UnsupportedActionException
	;
	
	/**
	* Add a CLU to a CLU set
	* 
	* @param cluId identifier of CLU to add to the CLU set
	* @param cluSetId identifier of the CLU set
	* @return status of the operation (success or failure)
	*/
	public StatusInfo addCluToCluSet(String cluId, String cluSetId)
		throws DoesNotExistException
		      ,InvalidParameterException
		      ,MissingParameterException
		      ,OperationFailedException
		      ,PermissionDeniedException
		      ,UnsupportedActionException
	;
	
	/**
	* Remove a CLU from a CLU set
	* 
	* @param cluId identifier of CLU to remove from the CLU set
	* @param cluSetId identifier of the CLU set
	* @return status of the operation (success or failure)
	*/
	public StatusInfo removeCluFromCluSet(String cluId, String cluSetId)
		throws DoesNotExistException
		      ,InvalidParameterException
		      ,MissingParameterException
		      ,OperationFailedException
		      ,PermissionDeniedException
		      ,UnsupportedActionException
	;
	
	/**
	* Validates a LUI. Depending on the value of validationType, this validation could 
	* be limited to tests on just the current object and its directly contained sub-
	* objects or expanded to perform all tests related to this object. If an 
	* identifier is present for the LUI (and/or one of its contained sub-objects) and 
	* a record is found for that identifier, the validation checks if the LUI can be 
	* shifted to the new values. If an identifier is not present or a record cannot be 
	* found for the identifier, it is assumed that the record does not exist and as 
	* such, the checks performed will be much shallower, typically mimicking those 
	* performed by setting the validationType to the current object.
	* 
	* @param validationType identifier of the extent of validation
	* @param luiInfo LUI information to be tested.
	* @return results from performing the validation
	*/
	public List<ValidationResultInfo> validateLui(String validationType, LuiInfo luiInfo)
		throws DoesNotExistException
		      ,InvalidParameterException
		      ,MissingParameterException
		      ,OperationFailedException
	;
	
	/**
	* Creates a new LUI
	* 
	* @param cluId identifier of the CLU for the LUI being created
	* @param atpKey identifier of the academic time period for the LUI being created
	* @param luiInfo information about the LUI being created
	* @return the created LUI information
	*/
	public LuiInfo createLui(String cluId, String atpKey, LuiInfo luiInfo)
		throws AlreadyExistsException
		      ,DataValidationErrorException
		      ,DoesNotExistException
		      ,InvalidParameterException
		      ,MissingParameterException
		      ,OperationFailedException
		      ,PermissionDeniedException
	;
	
	/**
	* Updates an existing LUI
	* 
	* @param luiId identifier for the LUI to be updated
	* @param luiInfo updated information about the LUI
	* @return the updated LUI information
	*/
	public LuiInfo updateLui(String luiId, LuiInfo luiInfo)
		throws DataValidationErrorException
		      ,DoesNotExistException
		      ,InvalidParameterException
		      ,MissingParameterException
		      ,OperationFailedException
		      ,PermissionDeniedException
		      ,VersionMismatchException
	;
	
	/**
	* Deletes a LUI record
	* 
	* @param luiId identifier for the LUI to be deleted
	* @return status of the operation
	*/
	public StatusInfo deleteLui(String luiId)
		throws DependentObjectsExistException
		      ,DoesNotExistException
		      ,InvalidParameterException
		      ,MissingParameterException
		      ,OperationFailedException
		      ,PermissionDeniedException
	;
	
	/**
	* Updates the state of the LUI
	* 
	* @param luiId identifier for the LUI to be updated
	* @param luState New state for LUI. Value is expected to be constrained to those 
	* in the luState enumeration.
	* @return the updated LUI information
	*/
	public LuiInfo updateLuiState(String luiId, String luState)
		throws DataValidationErrorException
		      ,DoesNotExistException
		      ,InvalidParameterException
		      ,MissingParameterException
		      ,OperationFailedException
		      ,PermissionDeniedException
	;
	
	/**
	* Validates a relationship between LUIs. Depending on the value of validationType, 
	* this validation could be limited to tests on just the current object and its 
	* directly contained sub-objects or expanded to perform all tests related to this 
	* object. If an identifier is present for the relationship (and/or one of its 
	* contained sub-objects) and a record is found for that identifier, the validation 
	* checks if the relationship can be shifted to the new values. If an identifier is 
	* not present or a record cannot be found for the identifier, it is assumed that 
	* the record does not exist and as such, the checks performed will be much 
	* shallower, typically mimicking those performed by setting the validationType to 
	* the current object.
	* 
	* @param validationType identifier of the extent of validation
	* @param luiLuiRelationInfo LUI to LUI relationship information to be tested.
	* @return results from performing the validation
	*/
	public List<ValidationResultInfo> validateLuiLuiRelation(String validationType, LuiLuiRelationInfo luiLuiRelationInfo)
		throws DoesNotExistException
		      ,InvalidParameterException
		      ,MissingParameterException
		      ,OperationFailedException
	;
	
	/**
	* Create a relationship between two LUIs
	* 
	* @param luiId identifier of the first LUI in the relationship
	* @param relatedLuiId identifier of the second LUI in the relationship to be 
	* related to
	* @param luLuRelationType the LU to LU relationship type of the relationship
	* @param luiLuiRelationInfo information about the relationship between the two 
	* LUIs
	* @return the created LUI to LUI relation information
	*/
	public LuiLuiRelationInfo createLuiLuiRelation(String luiId, String relatedLuiId, String luLuRelationType, LuiLuiRelationInfo luiLuiRelationInfo)
		throws AlreadyExistsException
		      ,CircularRelationshipException
		      ,DataValidationErrorException
		      ,DoesNotExistException
		      ,InvalidParameterException
		      ,MissingParameterException
		      ,OperationFailedException
		      ,PermissionDeniedException
	;
	
	/**
	* Updates a relationship between two LUIs
	* 
	* @param luiLuiRelationId identifier of the LUI to LUI relation to update
	* @param luiLuiRelationInfo changed information about the relationship between the 
	* two LUIs
	* @return the update LUI to LUI relation information
	*/
	public LuiLuiRelationInfo updateLuiLuiRelation(String luiLuiRelationId, LuiLuiRelationInfo luiLuiRelationInfo)
		throws DataValidationErrorException
		      ,DoesNotExistException
		      ,InvalidParameterException
		      ,MissingParameterException
		      ,OperationFailedException
		      ,PermissionDeniedException
		      ,VersionMismatchException
	;
	
	/**
	* Deletes a relationship between two LUIs
	* 
	* @param luiLuiRelationId identifier of the LUI to LUI relation to delete
	* @return status of the operation (success or failure)
	*/
	public StatusInfo deleteLuiLuiRelation(String luiLuiRelationId)
		throws DoesNotExistException
		      ,InvalidParameterException
		      ,MissingParameterException
		      ,OperationFailedException
		      ,PermissionDeniedException
	;
}

