/**
 * Copyright 2010 The Kuali Foundation Licensed under the
 * Educational Community License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License. You may
 * obtain a copy of the License at
 *
 * http://www.osedu.org/licenses/ECL-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an "AS IS"
 * BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing
 * permissions and limitations under the License.
 */

/**
 * 
 */
package org.kuali.student.lum.lu.assembly.data.client.refactorme.orch.removeinm4;


import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.TreeSet;

import org.kuali.student.common.ui.client.configurable.mvc.binding.ModelWidgetBindingSupport;
import org.kuali.student.common.ui.client.mvc.DataModel;
import org.kuali.student.core.assembly.data.AssemblyException;
import org.kuali.student.core.assembly.data.Data;
import org.kuali.student.core.assembly.data.QueryPath;
import org.kuali.student.core.assembly.data.Data.Property;
import org.kuali.student.core.dto.RichTextInfo;
import org.kuali.student.lum.common.client.lo.LOBuilder;
import org.kuali.student.lum.common.client.lo.LOPicker;
import org.kuali.student.lum.common.client.lo.OutlineNode;
import org.kuali.student.lum.common.client.lo.RichTextInfoHelper;
import org.kuali.student.lum.lo.dto.LoCategoryInfo;
import org.kuali.student.lum.lu.assembly.data.client.refactorme.base.LoCategoryInfoHelper;
import org.kuali.student.lum.lu.assembly.data.client.refactorme.orch.CreditCourseCourseSpecificLOsHelper;
import org.kuali.student.lum.lu.assembly.data.client.refactorme.orch.SingleUseLoChildSingleUseLosHelper;
import org.kuali.student.lum.lu.assembly.data.client.refactorme.orch.SingleUseLoHelper;

import com.google.gwt.core.client.GWT;

/**
 * @author Jim Tomlinson
 * 
 * Convert the DOL's "/course/courseSpecificLos" elements to/from a List of OutlineNode<LOPicker>'s
 * 
 * Remove after https://test.kuali.org/jira/browse/KSPLAN-198 is resolved
 *
 */
public class LOBuilderBinding extends ModelWidgetBindingSupport<LOBuilder> {

	public static LOBuilderBinding INSTANCE = new LOBuilderBinding();

	private LOBuilderBinding() {}
	
	// TODO - drive DOL down farther, probably by creating an OutlineNodeBinding or,
	// more likely, an LOPickerBinding
	
	// TODO - if not driving DOL down, factor this, because it's far twistier than it should be,
	// http://c2.com/cgi/wiki/Wiki?TimeToMakeItShort
    @Override
    public void setModelValue(LOBuilder builder, DataModel model, String path) {        
    	Data losData = new Data();
    	
    	CreditCourseCourseSpecificLOsHelper currTopLevelLoRelationshipHelper = null;
    	
    	// map of relationship helpers for levels 1-n (level 0 handled by currTopLevelLoRelationshipHelper)
    	Map<Integer, SingleUseLoChildSingleUseLosHelper> childHelperMap = new TreeMap<Integer, SingleUseLoChildSingleUseLosHelper>();
    	Integer currSequenceNo = 0;
    	Integer currLevel = 0;
    	
    	for (OutlineNode<LOPicker> node : builder.getValue()) {
    		int newLevel = node.getIndentLevel();
    		
    		assert (newLevel >= 0 && currLevel >= 0);
    		
    		// create a SingleUserLoHelper for the LoInfo represented by the node
    		SingleUseLoHelper helper;
			try {
				helper = createLoHelper(node);
			} catch (AssemblyException e) {
				// TODO - what to do in this situation?
				GWT.log("Assembly Exception was caught",e);
				return;
			}
		    		
    		if (newLevel > currLevel) {
				SingleUseLoChildSingleUseLosHelper newChildHelper =  SingleUseLoChildSingleUseLosHelper.wrap(new Data());
				setChildLevelRelationshipValues(newChildHelper, helper, currSequenceNo);
				childHelperMap.put(newLevel, newChildHelper);
    			// don't have to do anything else; will all be handled on unwind (to higher level)
				// or on exit of for loop
    		} else if (newLevel < currLevel) {
    			if (0 == newLevel) {
    				// new top-level LO; unwind up and save the last top-level tree
    				for (int levelIdx = currLevel; levelIdx > 1; levelIdx--) {
    					if (null == childHelperMap.get(levelIdx - 1).getChildLo().getChildSingleUseLos()) {
	    					childHelperMap.get(levelIdx - 1).getChildLo().setChildSingleUseLos(new Data());
    					}
    					childHelperMap.get(levelIdx - 1).getChildLo().getChildSingleUseLos().add(childHelperMap.get(levelIdx).getData());
    					// childHelperMap.get(levelIdx - 1).getChildLo().setChildSingleUseLos(childHelperMap.get(levelIdx).getData());
    				}
					if (null == currTopLevelLoRelationshipHelper.getIncludedSingleUseLo().getChildSingleUseLos()) {
    					currTopLevelLoRelationshipHelper.getIncludedSingleUseLo().setChildSingleUseLos(new Data());
					}
    				currTopLevelLoRelationshipHelper.getIncludedSingleUseLo().getChildSingleUseLos().add(childHelperMap.get(1).getData());
    				// currTopLevelLoRelationshipHelper.getIncludedSingleUseLo().setChildSingleUseLos(childHelperMap.get(1).getData());
		    		losData.add(currTopLevelLoRelationshipHelper.getData());
		    		
		    		// and create a new one
    				currTopLevelLoRelationshipHelper = CreditCourseCourseSpecificLOsHelper.wrap(new Data());
    				setTopLevelRelationshipValue(currTopLevelLoRelationshipHelper, helper, currSequenceNo);
		    		currLevel = newLevel;
    			} else {
    				// new lower-level LO; unwind up and save the last tree rooted at newLevel
    				for (int levelIdx = currLevel; levelIdx > newLevel; levelIdx--) {
    					// childHelperMap.get(levelIdx - 1).getChildLo().setChildSingleUseLos(childHelperMap.get(levelIdx).getData());
    					if (null == childHelperMap.get(levelIdx - 1).getChildLo().getChildSingleUseLos()) {
	    					childHelperMap.get(levelIdx - 1).getChildLo().setChildSingleUseLos(new Data());
    					}
    					childHelperMap.get(levelIdx - 1).getChildLo().getChildSingleUseLos().add(childHelperMap.get(levelIdx).getData());
    				}
    				if (1 == newLevel) {
    					// one below the top; save to top child
    					// currTopLevelLoRelationshipHelper.getIncludedSingleUseLo().setChildSingleUseLos(childHelperMap.get(1).getData());
						if (null == currTopLevelLoRelationshipHelper.getIncludedSingleUseLo().getChildSingleUseLos()) {
	    					currTopLevelLoRelationshipHelper.getIncludedSingleUseLo().setChildSingleUseLos(new Data());
						}
    					currTopLevelLoRelationshipHelper.getIncludedSingleUseLo().getChildSingleUseLos().add(childHelperMap.get(1).getData());
    				} else { // below that; save to child helper above
    					if (null == childHelperMap.get(newLevel - 1).getChildLo().getChildSingleUseLos()) {
	    					childHelperMap.get(newLevel - 1).getChildLo().setChildSingleUseLos(new Data());
    					}
    					childHelperMap.get(newLevel - 1).getChildLo().getChildSingleUseLos().add(childHelperMap.get(newLevel).getData());
    				}
    				// and create a new one
    				SingleUseLoChildSingleUseLosHelper newChildHelper =  SingleUseLoChildSingleUseLosHelper.wrap(new Data());
    				setChildLevelRelationshipValues(newChildHelper, helper, currSequenceNo);
    				childHelperMap.put(newLevel, newChildHelper);
    			}
	    	} else { // same level
	    		if (0 == currLevel) {
    				// new top-level LO; save previous tree
    				if (null != currTopLevelLoRelationshipHelper) {
			    		losData.add(currTopLevelLoRelationshipHelper.getData());
    				}
		    		// and create a new one
    				currTopLevelLoRelationshipHelper = CreditCourseCourseSpecificLOsHelper.wrap(new Data());
    				setTopLevelRelationshipValue(currTopLevelLoRelationshipHelper, helper, currSequenceNo);
	    		} else { // new lower-level LO; save previous tree
	    			if (1 == currLevel) {
    					// one below the top; save to top child
    					// currTopLevelLoRelationshipHelper.getIncludedSingleUseLo().setChildSingleUseLos(childHelperMap.get(1).getData());
						if (null == currTopLevelLoRelationshipHelper.getIncludedSingleUseLo().getChildSingleUseLos()) {
	    					currTopLevelLoRelationshipHelper.getIncludedSingleUseLo().setChildSingleUseLos(new Data());
						}
    					currTopLevelLoRelationshipHelper.getIncludedSingleUseLo().getChildSingleUseLos().add(childHelperMap.get(1).getData());
	    			} else {
    					// childHelperMap.get(currLevel - 1).getChildLo().setChildSingleUseLos(childHelperMap.get(currLevel).getData());
    					if (null == childHelperMap.get(currLevel - 1).getChildLo().getChildSingleUseLos()) {
	    					childHelperMap.get(currLevel - 1).getChildLo().setChildSingleUseLos(new Data());
    					}
    					childHelperMap.get(currLevel - 1).getChildLo().getChildSingleUseLos().add(childHelperMap.get(currLevel).getData());
	    			}
	    			// and create a new one
    				SingleUseLoChildSingleUseLosHelper newChildHelper =  SingleUseLoChildSingleUseLosHelper.wrap(new Data());
    				setChildLevelRelationshipValues(newChildHelper, helper, currSequenceNo);
    				childHelperMap.put(currLevel, newChildHelper);
	    		}
	    	}
    		currLevel = newLevel; // possible no-op
			currSequenceNo++;
    	}
		// unwind up and save the last top-level tree
    	if (currLevel > 0) {
			for (int levelIdx = currLevel; levelIdx > 1; levelIdx--) {
				if (null == childHelperMap.get(levelIdx - 1).getChildLo().getChildSingleUseLos()) {
					childHelperMap.get(levelIdx - 1).getChildLo().setChildSingleUseLos(new Data());
				}
				childHelperMap.get(levelIdx - 1).getChildLo().getChildSingleUseLos().add(childHelperMap.get(levelIdx).getData());
			}
			if (null == currTopLevelLoRelationshipHelper.getIncludedSingleUseLo().getChildSingleUseLos()) {
				currTopLevelLoRelationshipHelper.getIncludedSingleUseLo().setChildSingleUseLos(new Data());
			}
			currTopLevelLoRelationshipHelper.getIncludedSingleUseLo().getChildSingleUseLos().add(childHelperMap.get(1).getData());
    	}
		
    	// now add the last top-level helper's data in
    	if (null != currTopLevelLoRelationshipHelper) {
			losData.add(currTopLevelLoRelationshipHelper.getData());
    	}
		// and update the model
    	model.set(QueryPath.parse(path), losData);
    	
    	/* debug code
        DataType dataType = model.getType(QueryPath.parse(path));
        System.out.println(dataType.toString());
        Metadata metaData = model.getMetadata(QueryPath.parse(path));
        System.out.println(metaData.toString());
        */
    }

	private SingleUseLoHelper createLoHelper(OutlineNode<LOPicker> node) throws AssemblyException {
		SingleUseLoHelper helper;
    	
		/*
		if (null != node.getOpaque() && node.getOpaque() instanceof SingleUseLoHelper) {
			// existing LO, represented by a Helper we stashed previously via setOpaque() in setWidgetValue()
			helper = (SingleUseLoHelper) node.getOpaque();
		} else {
    		helper = SingleUseLoHelper.wrap(new Data());
    		// Clu's "draft", but associated lo's and their categories are "active"
			// TODO - here, or down in persistence code?
    		helper.setState("active");
			// TODO - here, or down in persistence code?
    		helper.setEffectiveDate(new Date());
    		// TODO - these shouldn't be hardcoded
    		helper.setType("kuali.lo.type.singleUse");
    		helper.setLoRepository("kuali.loRepository.key.singleUse");
		}
		*/
		helper = SingleUseLoHelper.wrap(new Data());
		helper.setId((String) node.getOpaque());
		// all the user can change currently re: an LO is its description and
		// what LoCategory's are associated with it
    		
		// modify the description
		RichTextInfoHelper rtHelper = RichTextInfoHelper.wrap(new Data());
		String loDesc = node.getUserObject().getLOText();
		rtHelper.setFormatted(loDesc);
		rtHelper.setPlain(loDesc);
		// if (null != helper.getDescription() && ! new RichTextInfoComparator ().equalStrVals(helper.getDescription(), rtHelper)) {
			// TODO - how do we setUpdated() on the client?
			// AssemblerUtils.setUpdated(helper.getData(), true);
		// }
		helper.setDescription(rtHelper);
		if (null == helper.getName() || helper.getName().length() == 0) {
			helper.setName("SINGLE USE LO");
		}
		
		Data categoriesData = new Data();
		// collect the LoCategory info from the LOCategoryBuilder
		for (LoCategoryInfo cat : node.getUserObject().getLoCategories()) {
			LoCategoryInfoHelper catHelper = LoCategoryInfoHelper.wrap(new Data());
			catHelper.setId(cat.getId());
			catHelper.setName(cat.getName());
			RichTextInfoHelper catRTHelper = RichTextInfoHelper.wrap(new Data());
			RichTextInfo catRT = cat.getDesc();
			if (null != catRT) {
				catRTHelper.setFormatted(cat.getDesc().getFormatted());
				catRTHelper.setPlain(cat.getDesc().getPlain());
			}
			catHelper.setDesc(catRTHelper);
			catHelper.setLoRepository(cat.getLoRepository());
			catHelper.setEffectiveDate(cat.getEffectiveDate());
			catHelper.setExpirationDate(cat.getExpirationDate());
			/* TODO - doesn't work on the client; what to do?
			AttributesAssembler attAssembler = new AttributesAssembler();
			catHelper.setAttributes(attAssembler.assemble(cat.getAttributes()));
			*/
			catHelper.setState(cat.getState());
			catHelper.setType(cat.getType());
			
			// TODO - do we need  MetaInfo communicated to the client, and hence a MetaInfoAssembler?
			/*
			MetaInfo mInfo = loCategory.getMetaInfo();
			MetaInfoHelper metaHelper = MetaInfoHelper.wrap(new Data());
			metaHelper.setCreateId(mInfo.getCreateId());
			...
			*/
			// TODO - LoCategoryInfoAssembler, w/ an assemble method so we can just do 
			// categoriesData.add(LoCategoryInfoAssembler.assemble(cat)) instead
			// of all the above
			categoriesData.add(catHelper.getData());
		}
		// replace helper's loCategory Data w/ what's in the LOBuilder
		helper.setCategories(categoriesData);
		return helper;
	}

	private void setTopLevelRelationshipValue(CreditCourseCourseSpecificLOsHelper currentTopLevelLoRelationshipHelper,
												SingleUseLoHelper helper,
												Integer sequenceNo) {
    			
		// TODO - these 3 need to be (possibly) set on basis of existing CluLoRelation,
		// when such a beast is available from the new LuService (rc4). Or, to
		// defaults as defined in Metadata
    	currentTopLevelLoRelationshipHelper.setId(null);
    	currentTopLevelLoRelationshipHelper.setEffectiveDate(helper.getEffectiveDate());
    	currentTopLevelLoRelationshipHelper.setState(helper.getState());
    	
    	currentTopLevelLoRelationshipHelper.setSequence(sequenceNo.toString());
    	currentTopLevelLoRelationshipHelper.setIncludedSingleUseLo(helper);
	}

	private void setChildLevelRelationshipValues(
			SingleUseLoChildSingleUseLosHelper currChildLoRelationshipHelper,
			SingleUseLoHelper helper,
			Integer sequenceNo) {
		currChildLoRelationshipHelper.setChildLo(helper);
		currChildLoRelationshipHelper.setEffectiveDate(new Date());
		currChildLoRelationshipHelper.setId(null);
		currChildLoRelationshipHelper.setSequence(sequenceNo.toString());
		currChildLoRelationshipHelper.setState(helper.getState());
		currChildLoRelationshipHelper.setType(helper.getType());
	}

	// change the List of OutlineNode's into 'courseSpecificLOs' elements, and place in the DataModel
	// TODO - Need to drill DOL down into LOPicker
    @Override
    public void setWidgetValue(LOBuilder builder, DataModel model, String path) {
    	List<OutlineNode<LOPicker>> loOutlineNodes = new ArrayList<OutlineNode<LOPicker>>();
    	
    	// change the 'courseSpecificLOs' elements into a List of OutlineNode's
        QueryPath qPath = QueryPath.parse(path);
        
        Data data = null;
        if(model!=null){
        	data = model.get(qPath);
        }
        	
        if (data != null){
            Iterator<Property> itr = data.realPropertyIterator();
            SortedHelperSet cccsLoHelpers = new SortedHelperSet();
            
            // get top-level LO's in the right order
            while (itr.hasNext()){
                Property p = (Property) itr.next();
                Data cccsData = p.getValue();
                cccsLoHelpers.add(CreditCourseCourseSpecificLOsHelper.wrap(cccsData));
            }
            for (CreditCourseCourseSpecificLOsHelper cccsLoHelper : cccsLoHelpers) {
            	
                SingleUseLoHelper helper = cccsLoHelper.getIncludedSingleUseLo();
                
                LOPicker picker = new LOPicker(LOBuilder.getMessageGroup(), LOBuilder.getType(), LOBuilder.getState(), LOBuilder.getRepoKey());
                picker.setLOText(helper.getDescription().getPlain());
                
                List<LoCategoryInfo> categories = getCategoryList(helper);
                picker.setLOCategories(categories);
                
                OutlineNode<LOPicker> node = new OutlineNode<LOPicker>();
                
                node.setUserObject(picker);
                node.setOpaque(helper.getId());
                node.setIndentLevel(0);
                
                loOutlineNodes.add(node);
                
                addChildLos(helper, loOutlineNodes, 1);
            }
	    	builder.setValue(loOutlineNodes);
        }
    }

	private void addChildLos(SingleUseLoHelper loHelper,
							 List<OutlineNode<LOPicker>> loOutlineNodes,
							 int currLevel) {
		
        SingleUseLoChildSingleUseLosHelper relationHelper = SingleUseLoChildSingleUseLosHelper.wrap(loHelper.getChildSingleUseLos());
        if (null != relationHelper) {
            SortedChildHelperSet childLoHelpers = new SortedChildHelperSet();
            Iterator<Property> itr = relationHelper.getData().realPropertyIterator();
            
            while (itr.hasNext()){
                Property p = (Property) itr.next();
                Data childHelperData = p.getValue();
                childLoHelpers.add(SingleUseLoChildSingleUseLosHelper.wrap(childHelperData));
            }
            for (SingleUseLoChildSingleUseLosHelper suLoChildHelper : childLoHelpers) {
            	
                SingleUseLoHelper helper = suLoChildHelper.getChildLo();
                
                LOPicker picker = new LOPicker(LOBuilder.getMessageGroup(), LOBuilder.getType(), LOBuilder.getState(), LOBuilder.getRepoKey());
                picker.setLOText(helper.getDescription().getPlain());
                
                List<LoCategoryInfo> categories = getCategoryList(helper);
                picker.setLOCategories(categories);
                
                OutlineNode<LOPicker> node = new OutlineNode<LOPicker>();
                
                node.setUserObject(picker);
                node.setOpaque(helper.getId());
                node.setIndentLevel(currLevel);
                
                loOutlineNodes.add(node);
                
                // recurse
                addChildLos(helper, loOutlineNodes, currLevel + 1);
            } 
        }
	}

	private List<LoCategoryInfo> getCategoryList(SingleUseLoHelper helper) {
		List<LoCategoryInfo> categoryInfos = new ArrayList<LoCategoryInfo>();
		
		Data categoryData = helper.getCategories();
		
		if (null != categoryData) {
			Iterator<Property> itr = categoryData.realPropertyIterator();
				
			while (itr.hasNext()) {
				Property catProp = itr.next();
				Data catData = catProp.getValue();
				
				LoCategoryInfoHelper catHelper = LoCategoryInfoHelper.wrap(catData);
				
				LoCategoryInfo catInfo = new LoCategoryInfo();
				
				// TODO - LoCategoryInfoHelper.toLoCategoryInfo()?
				catInfo.setId(catHelper.getId());
				RichTextInfoHelper rtHelper = catHelper.getDesc();
				if (null != rtHelper) {
					RichTextInfo descInfo = new RichTextInfo();
					descInfo.setFormatted(catHelper.getDesc().getFormatted());
					descInfo.setPlain(catHelper.getDesc().getPlain());
					catInfo.setDesc(descInfo);
				}
				catInfo.setEffectiveDate(catHelper.getEffectiveDate());
				catInfo.setExpirationDate(catHelper.getExpirationDate());
				catInfo.setLoRepository(catHelper.getLoRepository());
				// TODO - this should't be necessary when DOL pushed down into LOPicker
				// and its LOCategoryBuilder
				// catInfo.setAttributes(catHelper.getAttributes());
				catInfo.setName(catHelper.getName());
				catInfo.setState(catHelper.getState());
				catInfo.setType(catHelper.getType());
				// TODO - LoCategoryInfoAssembler, w/ a disassemble method so we can just do 
				// categoriesData.add(LoCategoryInfoAssembler.disassemble(catData)) instead
				// of all the above
				categoryInfos.add(catInfo);
			}
		}
		return categoryInfos;
	}
	
	class SortedHelperSet extends TreeSet<CreditCourseCourseSpecificLOsHelper> {

		private static final long serialVersionUID = 1L;
		
		public SortedHelperSet() {
			super(new Comparator<CreditCourseCourseSpecificLOsHelper>() {
					@Override
					public int compare(CreditCourseCourseSpecificLOsHelper cccs1,
									   CreditCourseCourseSpecificLOsHelper cccs2) {
						return Integer.valueOf(cccs1.getSequence()).compareTo(Integer.valueOf(cccs2.getSequence()));
					}
			});
		}
	}
	
	class SortedChildHelperSet extends TreeSet<SingleUseLoChildSingleUseLosHelper> {

		private static final long serialVersionUID = 1L;
		
		public SortedChildHelperSet() {
			super(new Comparator<SingleUseLoChildSingleUseLosHelper>() {
					@Override
					public int compare(SingleUseLoChildSingleUseLosHelper sulcsulh1,
									   SingleUseLoChildSingleUseLosHelper sulcsulh2) {
						return Integer.valueOf(sulcsulh1.getSequence()).compareTo(Integer.valueOf(sulcsulh2.getSequence()));
					}
			});
		}
	}
}
