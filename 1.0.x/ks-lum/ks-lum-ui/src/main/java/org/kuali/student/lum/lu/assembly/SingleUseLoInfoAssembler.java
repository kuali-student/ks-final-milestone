package org.kuali.student.lum.lu.assembly;

import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.kuali.student.core.assembly.Assembler;
import org.kuali.student.core.assembly.data.AssemblyException;
import org.kuali.student.core.assembly.data.Data;
import org.kuali.student.core.assembly.data.Metadata;
import org.kuali.student.core.assembly.data.SaveResult;
import org.kuali.student.core.assembly.data.Data.Property;
import org.kuali.student.core.exceptions.DoesNotExistException;
import org.kuali.student.core.exceptions.InvalidParameterException;
import org.kuali.student.core.exceptions.MissingParameterException;
import org.kuali.student.core.exceptions.OperationFailedException;
import org.kuali.student.core.search.dto.SearchRequest;
import org.kuali.student.core.search.dto.SearchResult;
import org.kuali.student.core.validation.dto.ValidationResultInfo;
import org.kuali.student.core.validation.dto.ValidationResultInfo.ErrorLevel;
import org.kuali.student.lum.lo.dto.LoCategoryInfo;
import org.kuali.student.lum.lo.dto.LoInfo;
import org.kuali.student.lum.lo.dto.LoLoRelationInfo;
import org.kuali.student.lum.lo.service.LearningObjectiveService;
import org.kuali.student.lum.lu.assembly.data.client.refactorme.base.LoCategoryInfoHelper;
import org.kuali.student.lum.lu.assembly.data.client.refactorme.base.RichTextInfoHelper;
import org.kuali.student.lum.lu.assembly.data.client.refactorme.orch.CreditCourseCourseSpecificLOsHelper;
import org.kuali.student.lum.lu.assembly.data.client.refactorme.orch.SingleUseLoChildSingleUseLosHelper;
import org.kuali.student.lum.lu.assembly.data.client.refactorme.orch.SingleUseLoHelper;
import org.kuali.student.lum.lu.dto.CluLoRelationInfo;
import org.kuali.student.lum.lu.service.LuService;

// TODO - need a CourseSpecificLosAssembler that implements Assembler<Data, List<LoInfo>>
// that calls this class' methods, rather than using this directly
public class SingleUseLoInfoAssembler implements Assembler<Data, LoInfo> {

	private LearningObjectiveService loService;

	private LuService luService;
	
	public static final String INCLUDES_RELATION_TYPE = "kuali.lo.relation.type.includes";

	public void setLoService(LearningObjectiveService loService) {
		this.loService = loService;
	}

	public void setLuService(LuService luService) {
		this.luService = luService;
	}

	@Override
	public Data assemble(LoInfo input) throws AssemblyException {
		throw new UnsupportedOperationException("assembly from target type not supported");
	}


	@Override
	public LoInfo disassemble(Data input) throws AssemblyException {
		throw new UnsupportedOperationException("disassembly to target type not supported");
	}

	@Override
	public Data get(String cluId) throws AssemblyException {
		Data losData = new Data();
		RichTextInfoAssembler rtAssembler = new RichTextInfoAssembler();
		
		try {
		    List<CluLoRelationInfo> cluRelations = luService.getCluLoRelationsByClu(cluId); 
		    
		    if (cluRelations != null){
    			for (CluLoRelationInfo cluLoRltnInfo : cluRelations) {
    				
    				CreditCourseCourseSpecificLOsHelper cccsLoHelper = CreditCourseCourseSpecificLOsHelper.wrap(new Data());
    				SingleUseLoHelper topLevelLoHelper = SingleUseLoHelper.wrap(new Data());
    				LoInfo lo = loService.getLo(cluLoRltnInfo.getLoId());
    				if (lo != null) {
    					
    					topLevelLoHelper.setId(lo.getId());
    					topLevelLoHelper.setDescription(RichTextInfoHelper.wrap(rtAssembler.assemble(lo.getDesc())));
    					topLevelLoHelper.setLoRepository(lo.getLoRepositoryKey());
    					topLevelLoHelper.setEffectiveDate(lo.getEffectiveDate());
    					topLevelLoHelper.setState(lo.getState());
    					topLevelLoHelper.setType(lo.getType());
    					List<LoCategoryInfo> loCategories = loService.getLoCategoriesForLo(lo.getId());
    					
    					Data categoriesData = new Data();
    					if (null != loCategories) {
    						for (LoCategoryInfo loCategory : loCategories) {
    							LoCategoryInfoHelper catHelper = LoCategoryInfoHelper.wrap(new Data());
    							catHelper.setId(loCategory.getId());
    							catHelper.setName(loCategory.getName());
    							// there must be a more efficient way to do this; creating two RichTextInfoHelpers in this call
    							catHelper.setDesc(RichTextInfoHelper.wrap(rtAssembler.assemble(loCategory.getDesc())));
    							catHelper.setLoRepository(loCategory.getLoRepository());
    							catHelper.setEffectiveDate(loCategory.getEffectiveDate());
    							catHelper.setExpirationDate(loCategory.getExpirationDate());
    							AttributesAssembler attAssembler = new AttributesAssembler();
    							catHelper.setAttributes(attAssembler.assemble(loCategory.getAttributes()));
    							catHelper.setState(loCategory.getState());
    							catHelper.setType(loCategory.getType());
    							
    							// TODO - do we need  MetaInfo communicated to the client, and hence a MetaInfoAssembler?
    							/*
    							MetaInfo mInfo = loCategory.getMetaInfo();
    							MetaInfoHelper metaHelper = MetaInfoHelper.wrap(new Data());
    							metaHelper.setCreateId(mInfo.getCreateId());
    							...
    							*/
    							
    							categoriesData.add(catHelper.getData());
    						}
    					}
    					topLevelLoHelper.setCategories(categoriesData);
    					cccsLoHelper.setSequence(lo.getAttributes().get("sequence"));
    					/* Until there's an actual CluLoRelation retrieved from LUService, this stuff
    					 * is probably moot. And, the hard-coded values should perhaps come from
    					 * something like a CluInfoHierarchyAssembler.RelationshipHierarchy, that's
    					 * initialized with the right values from metadata
    					 */
    					cccsLoHelper.setEffectiveDate(lo.getEffectiveDate());
    					cccsLoHelper.setId(null);
    					cccsLoHelper.setState("draft");
    					cccsLoHelper.setType("kuali.lu.lo.relation.type.includes");
    					
    					// retrieve child hierarchy
    					// TODO - factor out common code here and in getChildLos()
    					topLevelLoHelper.setChildSingleUseLos(getChildLos(lo.getId()));
    				}
    				cccsLoHelper.setIncludedSingleUseLo(topLevelLoHelper);
    				losData.add(cccsLoHelper.getData());
    			}
		    }
		} catch (Exception e) {
			throw new AssemblyException(e);
		}

		return losData;
	}
	
	private Data getChildLos(String parentLoId) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, AssemblyException {
		Data childLosData = new Data();
		RichTextInfoAssembler childRTAssembler = new RichTextInfoAssembler();
		
		// seem to be getting a null List back from service; check now until it's resolved
		// TODO - find out why LoService is returning a null List, and fix it
		// getLoLoRelationsByLoId(parentLoId);
		// TODO - obviously, the loLoRelationType should come from Metadata
		List<LoInfo> relatedLos = loService.getRelatedLosByLoId(parentLoId, "kuali.lo.relation.type.includes");
		if (null != relatedLos) {
			for (LoInfo relatedLo : relatedLos) {
				SingleUseLoChildSingleUseLosHelper loChildLoRelationshipHelper = SingleUseLoChildSingleUseLosHelper.wrap(new Data());
				SingleUseLoHelper loHelper = SingleUseLoHelper.wrap(new Data());
				
				loHelper.setId(relatedLo.getId());
				loHelper.setDescription(RichTextInfoHelper.wrap(childRTAssembler.assemble(relatedLo.getDesc())));
				loHelper.setLoRepository(relatedLo.getLoRepositoryKey());
				loHelper.setEffectiveDate(relatedLo.getEffectiveDate());
				loHelper.setState(relatedLo.getState());
				loHelper.setType(relatedLo.getType());
				
				Data categoriesData = new Data();
				List<LoCategoryInfo> loChildCategories = loService.getLoCategoriesForLo(relatedLo.getId());
				if (null != loChildCategories) {
					for (LoCategoryInfo loCategory : loChildCategories) {
						LoCategoryInfoHelper catHelper = LoCategoryInfoHelper.wrap(new Data());
						catHelper.setId(loCategory.getId());
						catHelper.setName(loCategory.getName());
						// there must be a more efficient way to do this; creating two RichTextInfoHelpers in this call
						catHelper.setDesc(RichTextInfoHelper.wrap(childRTAssembler.assemble(loCategory.getDesc())));
						catHelper.setLoRepository(loCategory.getLoRepository());
						catHelper.setEffectiveDate(loCategory.getEffectiveDate());
						catHelper.setExpirationDate(loCategory.getExpirationDate());
						AttributesAssembler attAssembler = new AttributesAssembler();
						catHelper.setAttributes(attAssembler.assemble(loCategory.getAttributes()));
						catHelper.setState(loCategory.getState());
						catHelper.setType(loCategory.getType());
						
						// TODO - do we need  MetaInfo communicated to the client, and hence a MetaInfoAssembler?
						/*
						MetaInfo mInfo = loCategory.getMetaInfo();
						MetaInfoHelper metaHelper = MetaInfoHelper.wrap(new Data());
						metaHelper.setCreateId(mInfo.getCreateId());
						...
						*/
						
						categoriesData.add(catHelper.getData());
					}
				}
				loHelper.setCategories(categoriesData);
				
				loChildLoRelationshipHelper.setSequence(relatedLo.getAttributes().get("sequence"));
				/* TODO - until there's an actual CluLoRelation retrieved from LUService, this stuff
				 * is probably moot. And, the hard-coded values should perhaps come from
				 * something like a CluInfoHierarchyAssembler.RelationshipHierarchy, that's
				 * initialized with the right values from metadata
				 */
				loChildLoRelationshipHelper.setEffectiveDate(relatedLo.getEffectiveDate());
				loChildLoRelationshipHelper.setId(null);
				loChildLoRelationshipHelper.setState("draft");
				loChildLoRelationshipHelper.setType("kuali.lu.lo.relation.type.includes");
				
				// recurse
				loHelper.setChildSingleUseLos(getChildLos(loHelper.getId()));
				
				loChildLoRelationshipHelper.setChildLo(loHelper);
				childLosData.add(loChildLoRelationshipHelper.getData());
			}
		}
		return childLosData;
	}

	@Override
	public Metadata getMetadata(String idType, String id, String type, String state) throws AssemblyException {
		throw new UnsupportedOperationException("Assembler is not type/state specific");
	}

	// TODO - revisit making this an implementation of Assembler.save() after Heather's
	// CIHAssembler refactor
	// @Override
	public SaveResult<Data> save(String cluId, Data losData) throws AssemblyException {
		SaveResult<Data> result = new SaveResult<Data>();
		
		if (null != losData) { // There are LO's
			try {
				List<ValidationResultInfo> val = validate(losData);
				result.setValidationResults(val);
				
				if (isValid(val)) {
					saveLos(cluId, losData);
					result.setValue(removeOrphans(losData));
				} else {
					result.setValue(losData);
				}
			} catch (Exception e) {
				throw new AssemblyException("Unable to save Learning Objectives", e);
			}
		}
		return result;
	}
	
	private void saveLos(String cluId, Data input) throws AssemblyException {
		CreditCourseCourseSpecificLOsHelper cccsLoHelper;
		RichTextInfoAssembler rtAssembler = new RichTextInfoAssembler();
		
        Iterator<Property> iter = input.iterator();
        Data loData;
        
        List<CluLoRelationInfo> cluLoRelations = null;
        Map<String, CluLoRelationInfo> loIdToCluLoReltnMap = new HashMap<String, CluLoRelationInfo>();
		try {
			cluLoRelations = luService.getCluLoRelationsByClu(cluId);
		} catch (DoesNotExistException e1) {
			// Pretty bogus to throw instead of returning an empty list,
			// but swallow it and move on
		} catch (Exception e) {
			throw new AssemblyException(e);
		}
        if (null != cluLoRelations) {
        	for (CluLoRelationInfo clrInfo : cluLoRelations) {
        		loIdToCluLoReltnMap.put(clrInfo.getLoId(), clrInfo);
        	}
        }
        while (iter.hasNext()) {
	    	LoInfo loToSave;
        	loData = iter.next().getValue();
	    	cccsLoHelper = CreditCourseCourseSpecificLOsHelper.wrap(loData);
	    	SingleUseLoHelper loHelper = cccsLoHelper.getIncludedSingleUseLo();
	    	
	    	String loId = loHelper.getId();
	    	if (null != loId && loId.length() > 0) {
	    		try {
					loToSave = loService.getLo(loId);
				} catch (Exception e) {
					throw new AssemblyException(e);
				} 
	    	} else {
		    	loToSave = new LoInfo();
	    		loToSave.setState("active");
	    		loToSave.setEffectiveDate(new Date());
	    		loToSave.setName(loHelper.getName());
	    		
	    		// TODO - these shouldn't be hardcoded
	    		loToSave.setType("kuali.lo.type.singleUse");
	    		loToSave.setLoRepositoryKey("kuali.loRepository.key.singleUse");
	    	}
	    	// TODO - seems pretty convoluted to get a RichTextInfo
	    	loToSave.setDesc(rtAssembler.disassemble(loHelper.getDescription().getData()));
	    	loToSave.getAttributes().put("sequence", cccsLoHelper.getSequence());
	    	
	    	LoInfo resultLoInfo = null;
	    	// Currently unable to get DataValidationErrorException's back, and then do a service validateLo()
	    	// call and get the list of validation results back.
	    	// FIXME - fix LO service's validate...() methods so they can be called remotely; Jim & Kamal
	    	// are a bit stumped on what the problem is currently
    		try {
		    	if (null == loId || loId.length() == 0) {
					resultLoInfo = loService.createLo(loToSave.getLoRepositoryKey(), loToSave.getType(), loToSave);
		    	} else { // might have to update due to sequence # change
	    			 // FIXME - when persisting sequence # to app state db or elsewhere, updating should be unnecessary
					resultLoInfo = loService.updateLo(loToSave.getId(), loToSave);
				} 
		    	String resultLoId = resultLoInfo.getId(); // make things a tad more readable
		    	saveCategoryAssociations(resultLoId, loHelper);
		    	if ( null == loIdToCluLoReltnMap.get(resultLoId) ) {
	
			    	CluLoRelationInfo clRltnInfo = new CluLoRelationInfo();
			    	clRltnInfo.setCluId(cluId);
			    	clRltnInfo.setEffectiveDate(new Date());
			    	clRltnInfo.setLoId(resultLoId);
			    	clRltnInfo.setState("active");
			    	clRltnInfo.setType("cluLuType.default");
		    		// TODO - "cluLuType.default" is only type so far; fix when there's more than one
					luService.createCluLoRelation(cluId, resultLoId, "cluLuType.default", clRltnInfo);
		    	} else { // keep track of those no longer associated w/ the Clu, by removing those that are
		    		loIdToCluLoReltnMap.remove(resultLoId);
		    	}
	    	} catch (Exception e) {
				throw new AssemblyException(e);
			} 
	    	saveChildLos(resultLoInfo.getId(), loHelper);
        }
        // remove CluLoRelations that no longer exist
    	for (CluLoRelationInfo clrInfo : loIdToCluLoReltnMap.values()) {
    		try {
				luService.deleteCluLoRelation(clrInfo.getId());
			} catch (Exception e) {
				e.printStackTrace();
				throw new AssemblyException(e);
			}
    	}
	}
	
	private void saveCategoryAssociations(String loId , SingleUseLoHelper loHelper) throws Exception {
		
		Data categoryData = loHelper.getCategories();
		Set<String> alreadyAssociatedCategoryIds = new TreeSet<String>();
		List<LoCategoryInfo> cats = loService.getLoCategoriesForLo(loId);
		// seem to be getting a null List back from service; check now until it's resolved
		// TODO - find out why LoService is returning a null List, and fix it
		if (null != cats) {
			for (LoCategoryInfo catInfo : cats) {
				alreadyAssociatedCategoryIds.add(catInfo.getId());
			}
		}
		
		if (null != categoryData) {
			Iterator<Property> itr = loHelper.getCategories().iterator();
			while (itr.hasNext()) {
				Property catProp = itr.next();
				Data catData = catProp.getValue();
				LoCategoryInfoHelper catHelper = LoCategoryInfoHelper.wrap(catData);
				String catId = catHelper.getId();
				// new association?
				if ( ! alreadyAssociatedCategoryIds.contains(catId) ) {
					loService.addLoCategoryToLo(catId, loId);
				} else { // already; remove from set so we can remove leftovers when done
					alreadyAssociatedCategoryIds.remove(catId);
				}
			}
		} // if it wasn't in the data from client, it was removed
		for (String deletedCatId : alreadyAssociatedCategoryIds) {
			loService.removeLoCategoryFromLo(deletedCatId, loId);
		}
	}

	// Recursively save child LO's, and LoLoRelations
	private void saveChildLos(String parentLoId, SingleUseLoHelper parentLoHelper) throws AssemblyException {
		SingleUseLoChildSingleUseLosHelper childLosHelper;
		RichTextInfoAssembler rtAssembler = new RichTextInfoAssembler();
		
		
		Data childLoData = parentLoHelper.getChildSingleUseLos();
		if (null != childLoData) {
	        Iterator<Property> iter = parentLoHelper.getChildSingleUseLos().iterator();
	        Data loData;
	        while (iter.hasNext()) {
		    	LoInfo loToSave;
	        	loData = iter.next().getValue();
		    	childLosHelper = SingleUseLoChildSingleUseLosHelper.wrap(loData);
		    	SingleUseLoHelper loHelper = childLosHelper.getChildLo();
		    	
		    	String loId = loHelper.getId();
		    	if (null != loId && loId.length() > 0) {
		    		try {
						loToSave = loService.getLo(loId);
					} catch (Exception e) {
						throw new AssemblyException(e);
					} 
		    	} else {
			    	loToSave = new LoInfo();
		    		loToSave.setState("active");
		    		loToSave.setEffectiveDate(new Date());
		    		loToSave.setName(loHelper.getName());
	    		
		    		// TODO - these shouldn't be hardcoded
		    		loToSave.setType("kuali.lo.type.singleUse");
		    		loToSave.setLoRepositoryKey("kuali.loRepository.key.singleUse");
		    	}
		    	// TODO - seems pretty convoluted to get a RichTextInfo
		    	loToSave.setDesc(rtAssembler.disassemble(loHelper.getDescription().getData()));
		    	loToSave.getAttributes().put("sequence", childLosHelper.getSequence());
		    	
		    	LoInfo currLo;
		    	// Currently unable to get DataValidationErrorException's back, and then do a service validateLo()
		    	// call and get the list of validation results back.
		    	// FIXME - fix LO service's validate...() methods so they can be called remotely; Jim & Kamal
		    	// are a bit stumped on what the problem is currently
		    	try {
			    	if (null == loId || loId.length() == 0) {
						currLo = loService.createLo(loToSave.getLoRepositoryKey(), loToSave.getType(), loToSave);
			    	} else {
						currLo = loService.updateLo(loToSave.getId(), loToSave);
			    	}
			    	saveCategoryAssociations(currLo.getId(), loHelper);
			    	saveLoLoRelation(parentLoId, currLo.getId());
		    	} catch (Exception e) {
						throw new AssemblyException(e);
				} 
		    
		    	// recurse
		    	saveChildLos(currLo.getId(), loHelper);
	        }
		}
	}


	private void saveLoLoRelation(String parentLoId, String relatedLoId) throws Exception {
		LoLoRelationInfo relationInfo = new LoLoRelationInfo();
		relationInfo.setEffectiveDate(new Date());
		relationInfo.setLoId(parentLoId);
		relationInfo.setRelatedLoId(relatedLoId);
		// TODO - obviously, the loLoRelationType should come from Metadata
		loService.createLoLoRelation(parentLoId, relatedLoId, "kuali.lo.relation.type.includes", relationInfo);
	}

	// TODO - implement
	private Data removeOrphans(Data input) {
		return null;
	}

	private boolean isValid(List<ValidationResultInfo> val) {
		boolean result = true;
		if (val != null) {
			for (ValidationResultInfo v : val) {
				if (v.getLevel() == ErrorLevel.ERROR) {
					result = false;
					break;
				}
			}
		}
		return result;
	}

	@Override
	public SearchResult search(SearchRequest searchRequest) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ValidationResultInfo> validate(Data input)
			throws AssemblyException {
		// TODO Auto-generated method stub
		return null;
	}

	// TODO - remove after removing cluId parameter from save(String, Data) above,
	// after Heather's CIHAssembler refactor
	@Override
	public SaveResult<Data> save(Data input) throws AssemblyException {
		throw new UnsupportedOperationException("SingleUseLoInfoAssembler#save(Data) waiting for CluInfoHierarchyAssembler refactor");
	}

	@Override
	public Metadata getDefaultMetadata() throws AssemblyException {
		// TODO Auto-generated method stub
		return null;
	}
}
