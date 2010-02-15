package org.kuali.student.lum.lu.ui.course.server.gwt;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Deque;
import java.util.List;
import java.util.TreeSet;

import org.apache.log4j.Logger;
import org.kuali.student.common.ui.client.mvc.dto.ModelDTO;
import org.kuali.student.common.ui.client.mvc.dto.ModelDTOValue;
import org.kuali.student.common.ui.client.mvc.dto.ModelDTOValue.ListType;
import org.kuali.student.common.ui.client.mvc.dto.ModelDTOValue.ModelDTOType;
import org.kuali.student.common.ui.server.mvc.dto.BeanMapper;
import org.kuali.student.common.ui.server.mvc.dto.BeanMappingException;
import org.kuali.student.common.ui.server.mvc.dto.DefaultBeanMapper;
import org.kuali.student.common.ui.server.mvc.dto.MapContext;
import org.kuali.student.common.ui.server.mvc.dto.PropertyMapping;
import org.kuali.student.core.dto.RichTextInfo;
import org.kuali.student.core.exceptions.AlreadyExistsException;
import org.kuali.student.core.exceptions.CircularRelationshipException;
import org.kuali.student.core.exceptions.DataValidationErrorException;
import org.kuali.student.core.exceptions.DependentObjectsExistException;
import org.kuali.student.core.exceptions.DoesNotExistException;
import org.kuali.student.core.exceptions.InvalidParameterException;
import org.kuali.student.core.exceptions.MissingParameterException;
import org.kuali.student.core.exceptions.OperationFailedException;
import org.kuali.student.core.exceptions.PermissionDeniedException;
import org.kuali.student.core.exceptions.VersionMismatchException;
import org.kuali.student.lum.lo.dto.LoInfo;
import org.kuali.student.lum.lo.dto.LoLoRelationInfo;
import org.kuali.student.lum.lo.service.LearningObjectiveService;
import org.kuali.student.lum.lu.assembly.data.client.LoModelDTO;
import org.kuali.student.lum.lu.service.LuService;

/**
 * Total HACK. Remove early in M4 implementation; replace with an
 * LoAssembler.
 *
 * @author Jim Tomlinson
 *
 */
public class LoInfoPersistenceBean {

	final Logger logger = Logger.getLogger(LoInfoPersistenceBean.class);
	
	private LearningObjectiveService loService;
	private LuService luService;
	private LoModelDTOComparator loModelDTOComparator = new LoModelDTOComparator();
	
	public void setLoService(LearningObjectiveService loService) {
		this.loService = loService;
	}
	  
	public void setLuService(LuService luService) {
		this.luService = luService;
	}

    /* TODO - this should all go away when
     * a) we figure out how to do multiple LO's in the dictionary, so the configurable UI can handle
     *    this for us by using a key of "desc/plain", or
     * b) the new orchestration framework makes all this moot
     */
    private static MapContext loMapContext;
    static {
	    loMapContext = new MapContext();
	    BeanMapper loBeanMapper = loMapContext.getBeanMapper(LoInfo.class.getName(), new DefaultBeanMapper());
	    loBeanMapper.addPropertyMapping("desc", new LoDescMapper());
	    loMapContext.addBeanMapper(LoInfo.class.getName(), loBeanMapper);
    }

	// Handle ModelDTO<->LoInfo for LO's "desc" RichText
	public static class LoDescMapper implements PropertyMapping {

		/* (non-Javadoc)
		 * @see org.kuali.student.common.ui.server.mvc.dto.PropertyMapping#fromModelDTOValue(org.kuali.student.common.ui.client.mvc.dto.ModelDTOValue, org.kuali.student.common.ui.server.mvc.dto.MapContext)
		 */
		@Override
		public Object fromModelDTOValue(ModelDTOValue value, MapContext context)
				throws BeanMappingException {
			assert value instanceof ModelDTOValue.StringType;
			RichTextInfo desc = new RichTextInfo();
			String descStr = ((ModelDTOValue.StringType) value).get();
			desc.setPlain(descStr);
			desc.setFormatted(descStr);
			return desc;
		}

		/* (non-Javadoc)
		 * @see org.kuali.student.common.ui.server.mvc.dto.PropertyMapping#toModelDTOValue(java.lang.Object, org.kuali.student.common.ui.server.mvc.dto.MapContext)
		 */
		@Override
		public ModelDTOValue toModelDTOValue(Object source, MapContext context)
				throws BeanMappingException {
			assert source instanceof RichTextInfo;
			ModelDTOValue.StringType returnVal = new ModelDTOValue.StringType();
			returnVal.set(((RichTextInfo) source).getPlain());
			return returnVal;
		}
	}
	/* End of:
	 * TODO - this should all go away when ...
	 */
    
	/*
	 * So, this is especially ugly. Would spend time cleaning up / refactoring, actually replacing
	 * but it all goes away next week, right? Too tired to think of the right way to do this.
	 */
	public void updateLos(String courseId, LoModelDTO loModelDTO) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, VersionMismatchException, BeanMappingException, DependentObjectsExistException, CircularRelationshipException {
		List<String> currTopLevelLoIds = luService.getLoIdsByClu(courseId);
		List<String> currChildLoIds = getAllChildLoIds(currTopLevelLoIds);
		TreeSet<String> topLevelLoSet = new TreeSet<String>(currTopLevelLoIds);
		TreeSet<String> currChildLoIdSet = new TreeSet<String>(currChildLoIds);
		ListType listTypeFromClient = (ListType) loModelDTO.get(LoModelDTO.LO_KEY);
		if (null != listTypeFromClient) {
			List<ModelDTOValue> loDTOValueList = listTypeFromClient.get();
			List<ModelDTO> loDTOs = new ArrayList<ModelDTO>();
			for (ModelDTOValue val : loDTOValueList) {
				loDTOs.add(((ModelDTOValue.ModelDTOType) val).get());
			}
			// make sure they're in the order the user specified
			Collections.sort(loDTOs, loModelDTOComparator);
			
			Deque<LoInfo> parentStack = new ArrayDeque<LoInfo>();
			LoInfo lastLoInfo = null;
			int lastLevel = 0;
			for (ModelDTO dto : loDTOs) {
				int level = ((ModelDTOValue.IntegerType) dto.get("level")).get(); // auto-unbox
				if (level < lastLevel) {
					assert( (level == lastLevel - 1) && (level >= 0) && ( ! parentStack.isEmpty() ) );
					lastLevel--;
					parentStack.pop();
				} else if (level > lastLevel) {
					assert(level == lastLevel + 1);
					lastLevel++;
					parentStack.push(lastLoInfo);
				}
				
				LoInfo currLoInfo;
				
				ModelDTOValue.ModelDTOType existingLoDtoVal = (ModelDTOType) dto.get("lo");
				if (null != existingLoDtoVal) { // get the one we previously sent to client
					currLoInfo = (LoInfo) loMapContext.fromModelDTO(existingLoDtoVal.get());
				} else { // new one
					currLoInfo = new LoInfo();
					currLoInfo.setState("draft");
				}
				// set the desc
				if (updateLoDesc(currLoInfo, dto)) {
					// and the "sequence" dynamic attribute (should probably be in the DOL equivalent of the
					// AppStateDB in M4
					currLoInfo.getAttributes().put("sequence", ((ModelDTOValue.IntegerType) dto.get("sequence")).getString());
					// keep track of who's missing by removing id's of those that aren't
					if (parentStack.isEmpty() && null != currLoInfo.getId()) { // we're at the top level
						topLevelLoSet.remove(currLoInfo.getId());
					} else {
						currChildLoIds.remove(currLoInfo.getId());
					}
					if (null != currLoInfo.getId()) {
						currLoInfo = loService.updateLo(currLoInfo.getId(), currLoInfo);
					} else {
						currLoInfo = loService.createLo("kuali.loRepository.key.singleUse", "kuali.lo.type.singleUse", currLoInfo);
					}
					if (parentStack.isEmpty()) {
						try {
							luService.addOutcomeLoToClu(currLoInfo.getId(), courseId);
						} catch (Exception aee) {
							System.err.println(aee.getMessage());
						} // no worries
					} else { // this should be "include"ed in parent LO
						LoLoRelationInfo llrInfo = new LoLoRelationInfo();
						llrInfo.setState("draft"); // TODO M4 - correct?
						try {
							loService.createLoLoRelation(parentStack.peek().getId(), currLoInfo.getId(), "kuali.lo.relation.type.includes", llrInfo);
							// TODO - record the LoLoRelations we create for an LO id; remove others, including the LO's below them
							// probably in a Tree, so we can delete LO's bottom-up?
						} catch (AlreadyExistsException aee) {} // no worries
					}
					lastLoInfo = currLoInfo;
				}
			}
		}
		// remove any top-level LO's from Clu that user removed
		for (String loId : topLevelLoSet) {
			luService.removeOutcomeLoFromClu(loId, courseId);
			if ( ! currChildLoIdSet.contains(loId) ) { // didn't get demoted
				loService.deleteLo(loId);
			}
		}
		// and formerly 'include'ed LO's
		// TODO - orphan exceptions will happen; keep on radar in LoAssembler design work
		currTopLevelLoIds = luService.getLoIdsByClu(courseId);
		for (String loId : currChildLoIdSet) {
			if ( ! currTopLevelLoIds.contains(loId) ) { // didn't get promoted
				loService.deleteLo(loId);
			}
		}
	}
	
	private List<String> getAllChildLoIds(List<String> parentLoIds) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
		List<String> accumulatedLoIds = new ArrayList<String>();
		
		for (String loId : parentLoIds) {
			accumulatedLoIds.add(loId);
			accumulatedLoIds.addAll(getChildLoIds(loId));
		}
		return accumulatedLoIds;
	}

	private Collection<? extends String> getChildLoIds(String loId) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
		List<LoInfo> includedLos =  loService.getRelatedLosByLoId(loId, "kuali.lo.relation.type.includes");
		List<String> loIds = new ArrayList<String>();
		for (LoInfo loInfo : includedLos) {
			loIds.add(loInfo.getId());
			loIds.addAll(getChildLoIds(loInfo.getId()));
		}
		return loIds;
	}

	private boolean updateLoDesc(LoInfo loInfo, ModelDTO dto) {
		String userDefinedDesc = ((ModelDTOValue.StringType) dto.get("value")).get();
		if (null != userDefinedDesc && userDefinedDesc.length() > 0) {
			RichTextInfo newDesc = new RichTextInfo();
			newDesc.setPlain(userDefinedDesc);
			newDesc.setFormatted(userDefinedDesc);
			loInfo.setDesc(newDesc);
			return true;
		}
		return false;
	}

	public ModelDTOValue getLos(String courseId) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, BeanMappingException {
        ModelDTOValue.ListType loList = new ModelDTOValue.ListType();
    	List<ModelDTOValue> loModelDTOValueList = new ArrayList<ModelDTOValue>();;
    	// put the list in now; client expects empty list, rather than null list
    	// client's right: Effective Java, item 43: 'Return empty arrays or collections, not nulls'
    	// just have to go one deeper in this case
    	loList.set(loModelDTOValueList);

		try {
	        logger.debug("Retrieving learning objectives for clu with id: " + courseId);
		        
	        // get id's of top-level LOs
			List<String> loIds = luService.getLoIdsByClu(courseId);
	        
			if (null != loIds && ( ! loIds.isEmpty() )) {
				// get the LO's themselves
				List<LoInfo> loInfos = loService.getLoByIdList(loIds);
			
				// recursively add them to the List of ModelDTOValue's the client expects
				// in the ModelDTOValue.ListType (loList)
				addLos(loInfos, loModelDTOValueList, 0);
				
			}
	    	
		} catch (Exception e) {
	        logger.error("Error getting learning objective. ", e);
		} 
    	
		return loList;
	}
	
	
	/**
	 * Recursively add the tree of "include"d LO's to a List<ModelDTOValue>
	 * with the right key/value's in each ModelDTOValue's ModelDTO
	 * 
	 * @param los
	 * @param modelDTOValueList
	 * @param level
	 * @throws BeanMappingException
	 * @throws DoesNotExistException
	 * @throws InvalidParameterException
	 * @throws MissingParameterException
	 * @throws OperationFailedException
	 */
	private void addLos(List<LoInfo> los, List<ModelDTOValue> modelDTOValueList, int level) throws BeanMappingException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        for (LoInfo loInfo : los) {
        	
        	ModelDTO loInfoModelDTO = loMapContext.fromBean(loInfo);
        	
        	ModelDTOValue.ModelDTOType loInfoModelDTOValue = new ModelDTOValue.ModelDTOType();
        	
        	loInfoModelDTOValue.set(loInfoModelDTO);
        	
        	ModelDTO listModelDTO = new ModelDTO();
        	listModelDTO.put("value", loInfo.getDesc().getPlain());
        	Object sequence = loInfo.getAttributes().get("sequence");
        	if (null != sequence) {
        		ModelDTOValue.IntegerType seqVal = new ModelDTOValue.IntegerType();
        		seqVal.set(new Integer(Integer.parseInt((String) sequence)));
        		listModelDTO.put("sequence", seqVal);
        	}
        	ModelDTOValue.IntegerType levelVal = new ModelDTOValue.IntegerType();
        	levelVal.set(new Integer(level));
        	listModelDTO.put("level", levelVal);
        	listModelDTO.put("lo", loInfoModelDTOValue);
        	
        	ModelDTOValue.ModelDTOType listModelDTOValue = new ModelDTOValue.ModelDTOType();
        	listModelDTOValue.set(listModelDTO);
			modelDTOValueList.add(listModelDTOValue);
			
			List<LoInfo> includedLos = loService.getRelatedLosByLoId(loInfo.getId(), "kuali.lo.relation.type.includes");
			// recurse down thru "include"d LOs
			if (null != includedLos && ! includedLos.isEmpty()) {
				addLos(includedLos, modelDTOValueList, level+1);
			}
        }
	}


	public class LoModelDTOComparator implements Comparator<ModelDTO> {
		@Override
		public int compare(ModelDTO dto1, ModelDTO dto2) {
			Object obj1 = dto1.get("sequence");
			Object obj2 = dto2.get("sequence");
			
			// those with no sequence number go at bottom of list
			if (null == obj1) {
				return (null == obj2 ? 0 : 1);
			} else if (null != obj2) {
				ModelDTOValue.IntegerType val1 = (ModelDTOValue.IntegerType) obj1;
				ModelDTOValue.IntegerType val2 = (ModelDTOValue.IntegerType) obj2;
				return (val1.get().compareTo(val2.get()));
			} else { // dto2 had no sequence #
				return -1;
			}
		}
	}
}
