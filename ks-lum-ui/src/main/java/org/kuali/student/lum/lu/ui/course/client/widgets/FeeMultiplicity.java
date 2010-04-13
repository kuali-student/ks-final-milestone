/**
 * 
 */
package org.kuali.student.lum.lu.ui.course.client.widgets;

import java.util.Iterator;

import org.kuali.student.common.ui.client.application.Application;
import org.kuali.student.common.ui.client.configurable.mvc.FieldDescriptor;
import org.kuali.student.common.ui.client.configurable.mvc.binding.ModelWidgetBinding;
import org.kuali.student.common.ui.client.configurable.mvc.binding.ModelWidgetBindingSupport;
import org.kuali.student.common.ui.client.configurable.mvc.binding.MultiplicityItemBinding;
import org.kuali.student.common.ui.client.configurable.mvc.binding.SectionBinding;
import org.kuali.student.common.ui.client.configurable.mvc.multiplicity.MultiplicityItem;
import org.kuali.student.common.ui.client.configurable.mvc.multiplicity.UpdatableMultiplicityComposite;
import org.kuali.student.common.ui.client.configurable.mvc.sections.GroupSection;
import org.kuali.student.common.ui.client.configurable.mvc.sections.Section;
import org.kuali.student.common.ui.client.mvc.DataModel;
import org.kuali.student.common.ui.client.mvc.DataModelDefinition;
import org.kuali.student.common.ui.client.widgets.KSDropDown;
import org.kuali.student.common.ui.client.widgets.list.KSSelectItemWidgetAbstract;
import org.kuali.student.common.ui.client.widgets.list.SelectionChangeEvent;
import org.kuali.student.common.ui.client.widgets.list.SelectionChangeHandler;
import org.kuali.student.common.ui.client.widgets.list.impl.SimpleListItems;
import org.kuali.student.core.assembly.data.Data;
import org.kuali.student.core.assembly.data.Metadata;
import org.kuali.student.core.assembly.data.QueryPath;
import org.kuali.student.core.assembly.data.Data.Property;
import org.kuali.student.core.organization.assembly.data.client.RuntimeDataConstants;
import org.kuali.student.lum.lu.assembly.data.client.refactorme.orch.FeeInfoConstants;
import org.kuali.student.lum.lu.assembly.data.client.refactorme.orch.FeeInfoHelper;
import org.kuali.student.lum.lu.ui.course.client.configuration.LUConstants;

import com.google.gwt.user.client.ui.Widget;

/**
 * @author Jim Tomlinson
 *
 */
public class FeeMultiplicity extends UpdatableMultiplicityComposite {
    
    private final String parentPath;
	private String groupName;
	private String type;
	private String state;
    private DataModelDefinition modelDefinition;
		
    public FeeMultiplicity(String parentPath, String groupName, String type, String state){
        super(StyleType.TOP_LEVEL);
        this.parentPath = parentPath;
        this.groupName = groupName;
        this.type = type;
        this.state = state;
	    setAddItemLabel(getLabel(LUConstants.ADD_A_FEE));
	    setItemLabel(getLabel(LUConstants.FEE));
    }
       
    @Override
    public Widget createItem() {
    	// avoid collisions w/ existing fees in the model
    	return new TransmogrifyingFeeRecordItem(parentPath, itemCount);
    }

    public Widget createItem(int itemCount) {
    	return new TransmogrifyingFeeRecordItem(parentPath, itemCount);
    }
        
    @Override
	public MultiplicityItem addItem() {
    	return addItem(FeeInfoConstants.FIXED_RATE_FEE, 1000 - itemCount);
    }
        
	public MultiplicityItem addItem(String rateType, int modelIdx) {
		itemCount++;
		visualItemCount++;
	    //itemCount = itemsPanel.getWidgetCount();
	    MultiplicityItem item = getItemDecorator(style);
	    Widget itemWidget = createItem(itemCount);
	    ((TransmogrifyingFeeRecordItem) itemWidget).setRateType(rateType, modelIdx);
    	
	    if (item != null){
		    item.setItemKey(new Integer(itemCount - 1));
		    item.setItemWidget(itemWidget);
		    item.setRemoveCallback(removeCallback);
	    } else if (itemWidget instanceof MultiplicityItem){
	    	item = (MultiplicityItem)itemWidget;
	    	item.setItemKey(new Integer(itemCount -1));
	    }
	    items.add(item);
	    item.redraw();
	    itemsPanel.add(item);
	    
	    return item;
	}
	
	public int getItemCount() {
		return itemCount;
	}
		
    protected String getLabel(String labelKey) {
        return Application.getApplicationContext().getUILabel(groupName, type, state, labelKey);
    }
		
    public void setModelDefinition(DataModelDefinition modelDefinition){
    	this.modelDefinition = modelDefinition;
    }

    private FieldDescriptor addField(Section section, String fieldKey, String fieldLabel, Widget widget, String parentPath) {
        QueryPath path = QueryPath.concat(parentPath, fieldKey);
    	Metadata meta = modelDefinition.getMetadata(path);

    	FieldDescriptor fd = new FieldDescriptor(path.toString(), fieldLabel, meta);
    	if (widget != null) {
    		fd.setFieldWidget(widget);
    	}
    	section.addField(fd);
    	return fd;
    }

    public class TransmogrifyingFeeRecordItem extends GroupSection {

		private String parentPath;
		private FeeTypeList feeTypeList = new FeeTypeList();
		private RateTypeList rateTypeList = new RateTypeList();
		private GroupSection dropDownSection;
		private GroupSection fieldSection;
        private String fixedPath = "0/" + FeeInfoConstants.FIXED_RATE_FEE + "/";
        private String variablePath = "0/" + FeeInfoConstants.VARIABLE_RATE_FEE + "/";
        private String multiplePath = "0/" + FeeInfoConstants.MULTIPLE_RATE_FEE + "/";
        private String perCreditPath = "0/" + FeeInfoConstants.PER_CREDIT_FEE + "/";
        private String currentModelPath;
        private SelectionChangeHandler dropDownHandler;
            
		public TransmogrifyingFeeRecordItem(String parentPath, int itemCount) {
			
            final int itemNumber = itemCount;
            this.parentPath = parentPath;

            // swap in/out sections (and their query paths) based on user input
            dropDownHandler = new SelectionChangeHandler() {
                @Override
                public void onSelectionChange(SelectionChangeEvent event) {
                    String selectedItem = ((KSSelectItemWidgetAbstract) event.getSource()).getSelectedItem();
                    // 1000 - the number of this mutiplicity item to avoid any conflicts with existing 
                    // model indices
                    setRateType(selectedItem, 1000 - itemNumber);
		            // feeTypeList.selectItem("kuali.enum.type.feeTypes.labFee");
                }
            };
		}

		public void setRateType(String rateType, int modelIdx) {
			clearSection();
            buildRateSpecificDropDownSection(rateType, modelIdx);
            addSection(dropDownSection);
            buildRateSpecificFieldSection(rateType, modelIdx);
            addSection(fieldSection);
            setCurrentModelPath(rateType, modelIdx);
		}
		
		private void setCurrentModelPath(String rateType, int modelIdx) {
    		if (FeeInfoConstants.FIXED_RATE_FEE.equals(rateType)) {
	            currentModelPath = fixedPath + modelIdx;
    		} else if (FeeInfoConstants.MULTIPLE_RATE_FEE.equals(rateType)) {
	            currentModelPath = multiplePath + modelIdx;
    		} else if (FeeInfoConstants.PER_CREDIT_FEE.equals(rateType)) {
	            currentModelPath = perCreditPath + modelIdx;
    		} else if (FeeInfoConstants.VARIABLE_RATE_FEE.equals(rateType)) {
	            currentModelPath = variablePath + modelIdx;
    		}
		}
		
		public String getCurrentModelPath() {
			return currentModelPath;
		}

		private void clearSection() {
			if (null != dropDownSection) {
				removeSection(dropDownSection);
			}
			if (null != fieldSection) {
				removeSection(fieldSection);
			}
		}
		
    	private void buildRateSpecificDropDownSection(String rateType, int modelIdx) {
    		dropDownSection = new GroupSection();

    		if (FeeInfoConstants.FIXED_RATE_FEE.equals(rateType)) {
	            FeeMultiplicity.this.addField(dropDownSection, fixedPath + modelIdx + "/feeType", "Fee Type", feeTypeList, parentPath);
	            FeeMultiplicity.this.addField(dropDownSection, fixedPath + modelIdx + "/rateType", "Rate Type", rateTypeList, parentPath);
    		} else if (FeeInfoConstants.MULTIPLE_RATE_FEE.equals(rateType)) {
	            FeeMultiplicity.this.addField(dropDownSection, multiplePath + modelIdx + "/feeType", "Fee Type", feeTypeList, parentPath);
	            FeeMultiplicity.this.addField(dropDownSection, multiplePath + modelIdx + "/rateType", "Rate Type", rateTypeList, parentPath);
    		} else if (FeeInfoConstants.PER_CREDIT_FEE.equals(rateType)) {
	            FeeMultiplicity.this.addField(dropDownSection, perCreditPath + modelIdx + "/feeType", "Fee Type", feeTypeList, parentPath);
	            FeeMultiplicity.this.addField(dropDownSection, perCreditPath + modelIdx + "/rateType", "Rate Type", rateTypeList, parentPath);
    		} else if (FeeInfoConstants.VARIABLE_RATE_FEE.equals(rateType)) {
	            FeeMultiplicity.this.addField(dropDownSection, variablePath + modelIdx + "/feeType", "Fee Type", feeTypeList, parentPath);
	            FeeMultiplicity.this.addField(dropDownSection, variablePath + modelIdx + "/rateType", "Rate Type", rateTypeList, parentPath);
    		}
            rateTypeList.selectItem(rateType);
            rateTypeList.addSelectionChangeHandler(dropDownHandler);
    	}
    	
    	private void buildRateSpecificFieldSection(String rateType, int modelIdx) {
    		fieldSection = new GroupSection();
    		if (FeeInfoConstants.FIXED_RATE_FEE.equals(rateType)) {
	            FeeMultiplicity.this.addField(fieldSection, fixedPath + modelIdx + "/amount", "Amount", null, parentPath );
    		} else if (FeeInfoConstants.MULTIPLE_RATE_FEE.equals(rateType)) {
	            FeeMultiplicity.this.addField(fieldSection, multiplePath + modelIdx + "/amount", "Amount", new MultipleFeeList(parentPath + "/" + multiplePath), parentPath );
    		} else if (FeeInfoConstants.PER_CREDIT_FEE.equals(rateType)) {
	            FeeMultiplicity.this.addField(fieldSection, perCreditPath + modelIdx + "/amount", "Amount (PerCredit)", null, parentPath );
    		} else if (FeeInfoConstants.VARIABLE_RATE_FEE.equals(rateType)) {
	            FeeMultiplicity.this.addField(fieldSection, variablePath + modelIdx + "/minAmount", "Amount", null, parentPath );
	            FeeMultiplicity.this.addField(fieldSection, variablePath + modelIdx + "/maxAmount", "To", null, parentPath );
    		}
    	}
	}
		
	static public class FeeMultiplicityBinding extends ModelWidgetBindingSupport<FeeMultiplicity> {

		@Override
		public void setModelValue(FeeMultiplicity widget, DataModel model, String path) {
	        for (MultiplicityItem item : widget.getItems()) {
				QueryPath qPath;
				if (item.getItemWidget() instanceof TransmogrifyingFeeRecordItem) {
					// set the runtimeData so delete works
					String itemModelPath = ((TransmogrifyingFeeRecordItem) item.getItemWidget()).getCurrentModelPath();
					
					if (item.isCreated()) {
						qPath = QueryPath.parse(path + QueryPath.getPathSeparator() + itemModelPath + QueryPath.getPathSeparator() + RUNTIME_ROOT + QueryPath.getPathSeparator() + RuntimeDataConstants.CREATED);
					} else if (item.isDeleted()) {
						qPath = QueryPath.parse(path + QueryPath.getPathSeparator() + itemModelPath + QueryPath.getPathSeparator() + RUNTIME_ROOT + QueryPath.getPathSeparator() + RuntimeDataConstants.DELETED);
					} else {
						qPath = QueryPath.parse(path + QueryPath.getPathSeparator() + itemModelPath + QueryPath.getPathSeparator() + RUNTIME_ROOT + QueryPath.getPathSeparator() + RuntimeDataConstants.UPDATED);
					}
					Boolean oldValue = model.get(qPath);
					Boolean newValue = true;
					if (!nullsafeEquals(oldValue, newValue)) {
						model.set(qPath, newValue);
						setDirtyFlag(model, qPath);
					}
				}
				MultiplicityItemBinding.INSTANCE.setModelValue(item, model, path);
	        }
		}

		/*
		 * Yeah, duped this due to architectural issues. Technical debt, or
		 * fodder for DOL simplification?
		 */
		@Override
		public void setWidgetValue(FeeMultiplicity widget, DataModel model, String path) {
	        widget.clear();

	        QueryPath qPath = QueryPath.parse(path);
	        Data data = null;
	        if(model!=null){
	        	data = model.get(qPath);
	        }

	        if (data != null) {
	            Iterator<Property> itr = data.realPropertyIterator();
	            if (itr.hasNext()) {
	            	FeeInfoHelper feeHelper = FeeInfoHelper.wrap((Data) itr.next().getValue());
	            	if (null != feeHelper.getFixedRateFee()) {
	            		addRateTypeSpecificItems(feeHelper.getFixedRateFee().realPropertyIterator(), FeeInfoConstants.FIXED_RATE_FEE, widget, model, path);
        			}
	            	if (null != feeHelper.getVariableRateFee()) {
	            		addRateTypeSpecificItems(feeHelper.getVariableRateFee().realPropertyIterator(), FeeInfoConstants.VARIABLE_RATE_FEE, widget, model, path);
	            	}
	            	if (null != feeHelper.getPerCreditFee()) {
	            		addRateTypeSpecificItems(feeHelper.getPerCreditFee().realPropertyIterator(), FeeInfoConstants.PER_CREDIT_FEE, widget, model, path);
	            	}
	            	if (null != feeHelper.getMultipleRateFee()) {
	            		addRateTypeSpecificItems(feeHelper.getMultipleRateFee().realPropertyIterator(), FeeInfoConstants.MULTIPLE_RATE_FEE, widget, model, path);
	            	}
            	}
            }
        }

		private void addRateTypeSpecificItems(Iterator<Property> iterator, String rateType, FeeMultiplicity widget, DataModel model, String path) {
			while (iterator.hasNext()) {
                Property p = (Property) iterator.next();

                if (p.getKey() instanceof Integer) {
                	// use the index the model has for this item (as it's model index, not item index)
                    MultiplicityItem item = widget.addItem(rateType, ((Integer) p.getKey()).intValue());
                    
                    // FIXME: Is assumption correct that data passed to setValue() has already been persisted
                    item.setCreated(false);
                    MultiplicityItemBinding.INSTANCE.setWidgetValue(item, model, path);
                }
			}
		}
	}
		
    public class MultipleFeeList extends UpdatableMultiplicityComposite {
        {
            setAddItemLabel(getLabel(LUConstants.ADD_ANOTHER_FEE));
            setItemLabel(getLabel(LUConstants.FEE));
        }
        private final String parentPath;
        public MultipleFeeList(String parentPath){
            super(StyleType.TOP_LEVEL);
            this.parentPath = parentPath;
        }
        @Override
        public Widget createItem() {
            String path = QueryPath.concat(parentPath, String.valueOf(itemCount-1)).toString();
            GroupSection ns = new GroupSection();
            addField(ns, "another Fee", getLabel(LUConstants.FEE), null, path );
            
            return ns;
        }
    }
    // TODO - from enums
    public class RateTypeList extends KSDropDown {
        public RateTypeList() {
            SimpleListItems types = new SimpleListItems();
            types.addItem("variableRateFee", getLabel(LUConstants.VARIABLE_RATE));
            types.addItem("fixedRateFee", getLabel(LUConstants.FIXED_RATE));
            types.addItem("multipleRateFee", getLabel(LUConstants.MULTIPLE_RATE));
            types.addItem("perCreditFee", getLabel(LUConstants.PER_CREDIT_RATE));
            super.setListItems(types);
        }
    }
    // TODO - from enums
    public class FeeTypeList extends KSDropDown {
        public FeeTypeList(){
            SimpleListItems types = new SimpleListItems();
        types.addItem("kuali.enum.type.feeTypes.labFee", getLabel(LUConstants.LAB_FEE));
        types.addItem("kuali.enum.type.feeTypes.materialFee",  getLabel(LUConstants.MATERIAL_FEE));
        types.addItem("kuali.enum.type.feeTypes.studioFee", getLabel(LUConstants.STUDIO_FEE));
        types.addItem("kuali.enum.type.feeTypes.fieldTripFee", getLabel(LUConstants.FIELD_TRIP_FEE));
        types.addItem("kuali.enum.type.feeTypes.fieldStudyFee", getLabel(LUConstants.FIELD_STUDY_FEE));
        types.addItem("kuali.enum.type.feeTypes.administrativeFee", getLabel(LUConstants.ADMINISTRATIVE_FEE));
        types.addItem("kuali.enum.type.feeTypes.coopFee", getLabel(LUConstants.COOP_FEE));
        types.addItem("kuali.enum.type.feeTypes.greensFee",  getLabel(LUConstants.GREENS_FEE));
            super.setListItems(types);
        }
    }    		
}
    
    
	