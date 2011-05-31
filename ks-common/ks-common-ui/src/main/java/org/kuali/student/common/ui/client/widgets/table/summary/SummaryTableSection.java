package org.kuali.student.common.ui.client.widgets.table.summary;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.kuali.student.common.assembly.data.Data;
import org.kuali.student.common.assembly.data.MetadataInterrogator;
import org.kuali.student.common.assembly.data.QueryPath;
import org.kuali.student.common.assembly.data.Data.Property;
import org.kuali.student.common.ui.client.application.Application;
import org.kuali.student.common.ui.client.configurable.mvc.FieldDescriptor;
import org.kuali.student.common.ui.client.configurable.mvc.FieldDescriptorReadOnly;
import org.kuali.student.common.ui.client.configurable.mvc.SectionTitle;
import org.kuali.student.common.ui.client.configurable.mvc.binding.ModelWidgetBinding;
import org.kuali.student.common.ui.client.configurable.mvc.multiplicity.MultiplicityConfiguration;
import org.kuali.student.common.ui.client.configurable.mvc.multiplicity.MultiplicityFieldConfiguration;
import org.kuali.student.common.ui.client.configurable.mvc.sections.Section;
import org.kuali.student.common.ui.client.configurable.mvc.sections.VerticalSection;
import org.kuali.student.common.ui.client.mvc.Controller;
import org.kuali.student.common.ui.client.mvc.DataModel;
import org.kuali.student.common.ui.client.mvc.ModelRequestCallback;
import org.kuali.student.common.validation.dto.ValidationResultInfo;
import org.kuali.student.common.validation.dto.ValidationResultInfo.ErrorLevel;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.Widget;

public class SummaryTableSection extends VerticalSection {
    SummaryTable summaryTable = new SummaryTable();
    SummaryTableModel summaryTableModel = new SummaryTableModel();
    Controller controller;
    DataModel comparisonModel = null;
    List<ShowRowConditionCallback> showRowCallbacks = new ArrayList<ShowRowConditionCallback>();

    public SummaryTableSection(Controller controller) {
        super();
        this.controller = controller;
        this.addWidget(summaryTable);
        summaryTable.setModel(summaryTableModel);
    }

    public SummaryTableSection(Controller controller, SectionTitle title) {
        super(title);
        this.controller = controller;
        this.addWidget(summaryTable);
        summaryTable.setModel(summaryTableModel);
    }

    public void setContentColumnHeader1(String title) {
        summaryTableModel.setContentColumnHeader1(title);
    }

    public void setContentColumnHeader2(String title) {
        summaryTableModel.setContentColumnHeader2(title);
    }

    public String getContentColumnHeader1() {
        return summaryTableModel.getContentColumnHeader1();
    }

    public String getContentColumnHeader2() {
        return summaryTableModel.getContentColumnHeader2();
    }

    public void setEditable(boolean bool) {
        summaryTableModel.setEditable(bool);
        summaryTable.doLayout();
        summaryTable.markDiffs("rowDiffHighlight");
    }
    
    public void addShowRowCallback(ShowRowConditionCallback callback){
    	this.showRowCallbacks.add(callback);
    }
    
    private void processShowConditions(SummaryTableFieldRow row, DataModel model, DataModel comparisonModel){
    	for(int i =0; i < showRowCallbacks.size(); i++){
    		showRowCallbacks.get(i).processShowConditions(row, model, comparisonModel);
    	}
    }

    public void addSummaryTableFieldBlock(SummaryTableFieldBlock section) {
        summaryTableModel.addSection(section);
    }
    
    @Override
    public ErrorLevel processValidationResults(
    		List<ValidationResultInfo> results) {
    	
    	ErrorLevel status = ErrorLevel.OK;
    	for(int i = 0; i < results.size(); i++){
    		if(summaryTable.containsKey(results.get(i).getElement())){
    			System.out.println(results.get(i).getElement() + " *** " + results.get(i).getErrorLevel() + " *** " + results.get(i).getMessage());
    			if(results.get(i).getLevel().getLevel() > status.getLevel()){
    				status = results.get(i).getLevel();
    			}
    			if(this.isValidationEnabled){
        			summaryTable.highlightRow(results.get(i).getElement(), "rowHighlight");
        		}
    		}
    	}
    	
    	List<ValidationResultInfo> warnings = Application.getApplicationContext().getValidationWarnings();
    	ValidationResultInfo tempVr = new ValidationResultInfo();
    	tempVr.setElement("");
    	for(int i = 0; i < warnings.size(); i++){
    		//Reformat the validation element path based on how it can be referenced in sumaryTable rowMap
    		String element = warnings.get(i).getElement();    		
    		if (element.startsWith("/")){    			
    			//Remove leading '/' since paths aren't stored this way in rowMap
    			element = element.substring(1);
    		} else if (element.matches(".*/[0-9]+")){
    			//Validation warnings returns path to individual items of simple multiplicity, 
    			//stripping of the item index to highlight the entire field. 
    			element = element.substring(0, element.lastIndexOf("/")); 
    		}
    		
    		if(summaryTable.containsKey(element)){
    			if(warnings.get(i).getLevel().getLevel() > status.getLevel()){
    				status = warnings.get(i).getLevel();
    			}
    			
       			summaryTable.highlightRow(element, "warning");
    		}
    	}
    	
    	return status;
    }
    
    @Override
    public ErrorLevel processValidationResults(List<ValidationResultInfo> results, boolean clearErrors) {
    	if(clearErrors){
    		this.removeValidationHighlighting();
    	}
    	return this.processValidationResults(results);
    }
    
    public void removeValidationHighlighting(){
    	summaryTable.clearHighlightedRows("rowHighlight");
    }
    
    private int buildMultiplicityRows(DataModel model, DataModel compModel, SummaryTableMultiplicityFieldRow parentRow, 
    		List<SummaryTableRow> rowList, int styleLevel, Integer parentNum){
    	StaticDeletemeLineLogger.log("buildMultiplicityRows-0");
    	MultiplicityConfiguration config = parentRow.getConfig();
    	StaticDeletemeLineLogger.log("buildMultiplicityRows-1");
    	int index = rowList.indexOf(parentRow) + 1;
    	StaticDeletemeLineLogger.log("buildMultiplicityRows-2");
    	int fieldRowsCreated = 0;
    	StaticDeletemeLineLogger.log("buildMultiplicityRows-3");
    	int number = 0;
    	StaticDeletemeLineLogger.log("buildMultiplicityRows-4");
    	String path = parentRow.getConfig().getParentFd().getFieldKey();
    	StaticDeletemeLineLogger.log("buildMultiplicityRows-5");
    	if(parentNum != null){
    		StaticDeletemeLineLogger.log("buildMultiplicityRows-6");
    		path = path.replace("*", "" + parentNum);
    		StaticDeletemeLineLogger.log("buildMultiplicityRows-7");
    	}
    	StaticDeletemeLineLogger.log("buildMultiplicityRows-8");
    	Data data = null;
    	StaticDeletemeLineLogger.log("buildMultiplicityRows-9");
    	if(model != null && model.isValidPath(path)){
    		StaticDeletemeLineLogger.log("buildMultiplicityRows-10");
    		data = model.get(QueryPath.parse(path));
    		StaticDeletemeLineLogger.log("buildMultiplicityRows-11");
    	}
    	StaticDeletemeLineLogger.log("buildMultiplicityRows-12");
		Data compData = null;
		StaticDeletemeLineLogger.log("buildMultiplicityRows-13");
		if(compModel != null && compModel.isValidPath(path)){
			StaticDeletemeLineLogger.log("buildMultiplicityRows-14");
			compData = compModel.get(QueryPath.parse(path));
			StaticDeletemeLineLogger.log("buildMultiplicityRows-15");
		}
		StaticDeletemeLineLogger.log("buildMultiplicityRows-16");
    	if((data != null && data.size() > 0) || (compData != null && compData.size() > 0)){
    		StaticDeletemeLineLogger.log("buildMultiplicityRows-17");
    		Iterator<Property> itr = null;
    		StaticDeletemeLineLogger.log("buildMultiplicityRows-18");
    		if(data != null && compData != null){
    			StaticDeletemeLineLogger.log("buildMultiplicityRows-19");
    			if(data.size() >= compData.size()){
    				StaticDeletemeLineLogger.log("buildMultiplicityRows-20");
    				itr = data.iterator();
    				StaticDeletemeLineLogger.log("buildMultiplicityRows-21");
    			} else{
    				StaticDeletemeLineLogger.log("buildMultiplicityRows-22");
    				itr = compData.iterator();
    				StaticDeletemeLineLogger.log("buildMultiplicityRows-23");
    			}
    			StaticDeletemeLineLogger.log("buildMultiplicityRows-24");
    		} else if(data != null){
    			StaticDeletemeLineLogger.log("buildMultiplicityRows-25");
    			itr = data.iterator();
    			StaticDeletemeLineLogger.log("buildMultiplicityRows-26");
    		} else{
    			StaticDeletemeLineLogger.log("buildMultiplicityRows-27");
    			itr = compData.iterator();
    			StaticDeletemeLineLogger.log("buildMultiplicityRows-28");
    		}
    		StaticDeletemeLineLogger.log("buildMultiplicityRows-29");
    		SummaryTableMultiplicityFieldRow currentMultiplicityRow = parentRow;
    		StaticDeletemeLineLogger.log("buildMultiplicityRows-30");
            while (itr.hasNext()) {
            	StaticDeletemeLineLogger.log("buildMultiplicityRows-31");
                Property p = (Property) itr.next();
                StaticDeletemeLineLogger.log("buildMultiplicityRows-32");
                if (p.getKey() instanceof Integer){
                	StaticDeletemeLineLogger.log("buildMultiplicityRows-33");
                	number = (Integer)p.getKey();
                	StaticDeletemeLineLogger.log("buildMultiplicityRows-34");
                }
                StaticDeletemeLineLogger.log("buildMultiplicityRows-35");
        		if(config.getItemLabel() != null && !config.getItemLabel().isEmpty()){
        			StaticDeletemeLineLogger.log("buildMultiplicityRows-36");
        			currentMultiplicityRow.setTitle(config.getItemLabel() + " "+ (number + 1));
        			StaticDeletemeLineLogger.log("buildMultiplicityRows-37");
        			currentMultiplicityRow.setKey(path);
        			StaticDeletemeLineLogger.log("buildMultiplicityRows-38");
            		if(MetadataInterrogator.isRequired(config.getMetaData()) || 
                			MetadataInterrogator.isRequiredForNextState(config.getMetaData())){
            			StaticDeletemeLineLogger.log("buildMultiplicityRows-39");
            			currentMultiplicityRow.setRequired(true);
            			StaticDeletemeLineLogger.log("buildMultiplicityRows-40");
            		}
            		StaticDeletemeLineLogger.log("buildMultiplicityRows-41");
            		currentMultiplicityRow.addTitleCellStyleName("summary-table-multiplicity-level-" + styleLevel);
            		StaticDeletemeLineLogger.log("buildMultiplicityRows-42");
        		}
        		else{
        			StaticDeletemeLineLogger.log("buildMultiplicityRows-43");
        			currentMultiplicityRow.setShown(false);
        			StaticDeletemeLineLogger.log("buildMultiplicityRows-44");
        		}
	    		//set has-data/requires data style here
        		StaticDeletemeLineLogger.log("buildMultiplicityRows-45");
	    		Map<Integer, List<MultiplicityFieldConfiguration>> fieldsCopy = config.getFields();
	    		StaticDeletemeLineLogger.log("buildMultiplicityRows-46");
				for(int i = 0; i < config.getFields().size(); i++){
					StaticDeletemeLineLogger.log("buildMultiplicityRows-47");
					for(int j = 0; j < config.getFields().get(i).size(); j++){
						//TODO handle custom widgets (multiplicity field widget initializer)
						StaticDeletemeLineLogger.log("buildMultiplicityRows-48");
						MultiplicityFieldConfiguration field = fieldsCopy.get(i).get(j);
						StaticDeletemeLineLogger.log("buildMultiplicityRows-49");
	    				String fieldKey = translatePath(field.getFieldPath(), path, number);
	    				StaticDeletemeLineLogger.log("buildMultiplicityRows-50");
	    				FieldDescriptorReadOnly fd1 = new FieldDescriptorReadOnly(fieldKey, field.getMessageKeyInfo(), field.getMetadata());
	    				StaticDeletemeLineLogger.log("buildMultiplicityRows-51");
	    				fd1.setOptional(field.isOptional());
	    				StaticDeletemeLineLogger.log("buildMultiplicityRows-52");
	    				if(field.getModelWidgetBinding() != null){
	    					StaticDeletemeLineLogger.log("buildMultiplicityRows-53");
	    					fd1.setWidgetBinding(field.getModelWidgetBinding());
	    					StaticDeletemeLineLogger.log("buildMultiplicityRows-54");
	    				}
	    				StaticDeletemeLineLogger.log("buildMultiplicityRows-55");
	    				FieldDescriptorReadOnly fd2 = new FieldDescriptorReadOnly(fieldKey, field.getMessageKeyInfo(), field.getMetadata());
	    				StaticDeletemeLineLogger.log("buildMultiplicityRows-56");
	    				fd2.setOptional(field.isOptional());
	    				StaticDeletemeLineLogger.log("buildMultiplicityRows-57");
	    				if(field.getModelWidgetBinding() != null){
	    					StaticDeletemeLineLogger.log("buildMultiplicityRows-58");
	    					fd2.setWidgetBinding(field.getModelWidgetBinding());
	    					StaticDeletemeLineLogger.log("buildMultiplicityRows-59");
	    				}
	    				StaticDeletemeLineLogger.log("buildMultiplicityRows-60");
	    				SummaryTableFieldRow row = new SummaryTableFieldRow(fd1, fd2);
	    				StaticDeletemeLineLogger.log("buildMultiplicityRows-61");
	    				row.setTemporaryRowFlag(true);
	    				StaticDeletemeLineLogger.log("buildMultiplicityRows-62");
	    				rowList.add(index, row);
	    				StaticDeletemeLineLogger.log("buildMultiplicityRows-63");
	    				index++;
	    				StaticDeletemeLineLogger.log("buildMultiplicityRows-64");
	    				fieldRowsCreated++;
	    				StaticDeletemeLineLogger.log("buildMultiplicityRows-65");
	    			}
				}
				StaticDeletemeLineLogger.log("buildMultiplicityRows-66");
	    		if(config.getNestedConfig() != null){
	    			StaticDeletemeLineLogger.log("buildMultiplicityRows-67");
	    			MultiplicityConfiguration nestedConfig = config.getNestedConfig();
	    			StaticDeletemeLineLogger.log("buildMultiplicityRows-68");
	    			nestedConfig.getParentFd().getFieldKey().replace(config.getParentFd().getFieldKey(), path);
	    			StaticDeletemeLineLogger.log("buildMultiplicityRows-69");
	    			SummaryTableMultiplicityFieldRow mRow = new SummaryTableMultiplicityFieldRow(nestedConfig);
	    			mRow.setTemporaryRowFlag(true);
	    			StaticDeletemeLineLogger.log("buildMultiplicityRows-70");
	    			rowList.add(index, mRow);
	    			StaticDeletemeLineLogger.log("buildMultiplicityRows-71");
	    			index++;
	    			StaticDeletemeLineLogger.log("buildMultiplicityRows-72");
	    			fieldRowsCreated++;
	    			StaticDeletemeLineLogger.log("buildMultiplicityRows-73");
	    			int result = buildMultiplicityRows(model, compModel, mRow, rowList, styleLevel + 1, number);
	    			StaticDeletemeLineLogger.log("buildMultiplicityRows-74");
	    			index = index + result;
	    			StaticDeletemeLineLogger.log("buildMultiplicityRows-75");
	    		}
	    		StaticDeletemeLineLogger.log("buildMultiplicityRows-76");
	    		if(itr.hasNext()){
	    			StaticDeletemeLineLogger.log("buildMultiplicityRows-77");
	    			SummaryTableMultiplicityFieldRow mRow = new SummaryTableMultiplicityFieldRow(config);
	    			StaticDeletemeLineLogger.log("buildMultiplicityRows-78");
	    			mRow.setTemporaryRowFlag(true);
	    			StaticDeletemeLineLogger.log("buildMultiplicityRows-79");
	    			rowList.add(index, mRow);
	    			StaticDeletemeLineLogger.log("buildMultiplicityRows-80");
	    			index++;
	    			StaticDeletemeLineLogger.log("buildMultiplicityRows-81");
	    			fieldRowsCreated++;
	    			StaticDeletemeLineLogger.log("buildMultiplicityRows-82");
	    			currentMultiplicityRow = mRow;
	    			StaticDeletemeLineLogger.log("buildMultiplicityRows-83");
	    		}
            }
    	}
    	else{
    		StaticDeletemeLineLogger.log("buildMultiplicityRows-84");
        	if(MetadataInterrogator.isRequired(config.getMetaData()) || 
        			MetadataInterrogator.isRequiredForNextState(config.getMetaData())){
        		StaticDeletemeLineLogger.log("buildMultiplicityRows-85");
        		if(config.getItemLabel() != null && !config.getItemLabel().isEmpty()){
        			StaticDeletemeLineLogger.log("buildMultiplicityRows-86");
        			parentRow.setTitle(config.getItemLabel() + " "+ (number + 1));
        			StaticDeletemeLineLogger.log("buildMultiplicityRows-87");
        			parentRow.setKey(path);
        			StaticDeletemeLineLogger.log("buildMultiplicityRows-88");
            		parentRow.setRequired(true);
            		StaticDeletemeLineLogger.log("buildMultiplicityRows-89");
            		parentRow.addTitleCellStyleName("summary-table-multiplicity-level-" + styleLevel);
            		StaticDeletemeLineLogger.log("buildMultiplicityRows-90");
        		}
        		else{
        			StaticDeletemeLineLogger.log("buildMultiplicityRows-91");
        			parentRow.setShown(false);
        			StaticDeletemeLineLogger.log("buildMultiplicityRows-92");
        		}
        		//set has-data/requires data style here
        		StaticDeletemeLineLogger.log("buildMultiplicityRows-93");
        		Map<Integer, List<MultiplicityFieldConfiguration>> fields = config.getFields();
        		StaticDeletemeLineLogger.log("buildMultiplicityRows-94");
				for(int i = 0; i < fields.size(); i++){
	        		StaticDeletemeLineLogger.log("buildMultiplicityRows-95");
					for(int j = 0; j < fields.get(i).size(); j++){
		        		StaticDeletemeLineLogger.log("buildMultiplicityRows-96");
						//TODO handle custom widgets (multiplicity field widget initializer)
						MultiplicityFieldConfiguration field = fields.get(i).get(j);
		        		StaticDeletemeLineLogger.log("buildMultiplicityRows-97");
	    				String fieldKey = translatePath(field.getFieldPath(), path, number);
	            		StaticDeletemeLineLogger.log("buildMultiplicityRows-98");
	    				FieldDescriptorReadOnly fd1 = new FieldDescriptorReadOnly(fieldKey, field.getMessageKeyInfo(), field.getMetadata());
	            		StaticDeletemeLineLogger.log("buildMultiplicityRows-99");
	    				fd1.setOptional(field.isOptional());
	            		StaticDeletemeLineLogger.log("buildMultiplicityRows-100");
	    				if(field.getModelWidgetBinding() != null){
	    	        		StaticDeletemeLineLogger.log("buildMultiplicityRows-101");
	    					fd1.setWidgetBinding(field.getModelWidgetBinding());
	    	        		StaticDeletemeLineLogger.log("buildMultiplicityRows-102");
	    				}
	            		StaticDeletemeLineLogger.log("buildMultiplicityRows-103");
	    				FieldDescriptorReadOnly fd2 = new FieldDescriptorReadOnly(fieldKey, field.getMessageKeyInfo(), field.getMetadata());
	            		StaticDeletemeLineLogger.log("buildMultiplicityRows-104");
	    				fd2.setOptional(field.isOptional());
	            		StaticDeletemeLineLogger.log("buildMultiplicityRows-105");
	    				if(field.getModelWidgetBinding() != null){
	    	        		StaticDeletemeLineLogger.log("buildMultiplicityRows-106");
	    					fd2.setWidgetBinding(field.getModelWidgetBinding());
	    	        		StaticDeletemeLineLogger.log("buildMultiplicityRows-107");
	    				}
	            		StaticDeletemeLineLogger.log("buildMultiplicityRows-108");
	    				SummaryTableFieldRow row = new SummaryTableFieldRow(fd1, fd2);
	            		StaticDeletemeLineLogger.log("buildMultiplicityRows-109");
	    				row.setTemporaryRowFlag(true);
	            		StaticDeletemeLineLogger.log("buildMultiplicityRows-110");
	    				rowList.add(index, row);
	            		StaticDeletemeLineLogger.log("buildMultiplicityRows-111");
	    				index++;
	            		StaticDeletemeLineLogger.log("buildMultiplicityRows-112");
	    				fieldRowsCreated++;
	            		StaticDeletemeLineLogger.log("buildMultiplicityRows-113");
	    			}
				}
				
    			
        		StaticDeletemeLineLogger.log("buildMultiplicityRows-114");
	    		if(config.getNestedConfig() != null){
	        		StaticDeletemeLineLogger.log("buildMultiplicityRows-115");
	    			MultiplicityConfiguration nestedConfig = config.getNestedConfig();
	        		StaticDeletemeLineLogger.log("buildMultiplicityRows-116");
	    			nestedConfig.getParentFd().getFieldKey().replace(config.getParentFd().getFieldKey(), path);
	        		StaticDeletemeLineLogger.log("buildMultiplicityRows-117");
	    			SummaryTableMultiplicityFieldRow mRow = new SummaryTableMultiplicityFieldRow(nestedConfig);
	        		StaticDeletemeLineLogger.log("buildMultiplicityRows-118");
	    			mRow.setTemporaryRowFlag(true);
	        		StaticDeletemeLineLogger.log("buildMultiplicityRows-119");
	    			rowList.add(index, mRow);
	        		StaticDeletemeLineLogger.log("buildMultiplicityRows-120");
	    			index++;
	        		StaticDeletemeLineLogger.log("buildMultiplicityRows-121");
	    			fieldRowsCreated++;
	        		StaticDeletemeLineLogger.log("buildMultiplicityRows-122");
	    			buildMultiplicityRows(null, null, mRow, rowList, styleLevel + 1, number);
	        		StaticDeletemeLineLogger.log("buildMultiplicityRows-123");
	    		}
        	}
        	else{
        		//Alternate label possibly here
        		StaticDeletemeLineLogger.log("buildMultiplicityRows-124");
        		parentRow.setTitle(config.getItemLabel());
        		StaticDeletemeLineLogger.log("buildMultiplicityRows-125");
        		parentRow.setRequired(false);
        		StaticDeletemeLineLogger.log("buildMultiplicityRows-126");
        		parentRow.setKey(config.getParentFd().getFieldKey());
        		StaticDeletemeLineLogger.log("buildMultiplicityRows-127");
        		//set unrequired style here
        	}
    	}
    	return fieldRowsCreated;
    }
    
    public String translatePath(String path, String parentPath, int num) {
        String fieldPath;
        if (parentPath != null) {
            QueryPath parent = QueryPath.concat(parentPath);
            int i = parent.size();

            QueryPath subPath = QueryPath.concat(path);
            String itemPath =  subPath.subPath(i, subPath.size()).toString();

            QueryPath qp = QueryPath.concat(parentPath, itemPath);
            fieldPath = qp.toString();
        }
        else {
            fieldPath = path;
        }

        fieldPath = fieldPath.replace("*", "" + num);
        return fieldPath;
    }
    
    @Override
    public void updateWidgetData(final DataModel model) {
    	
        controller.requestModel("ComparisonModel", new ModelRequestCallback<DataModel>() {
            @Override
            public void onModelReady(DataModel otherModel) {
                comparisonModel = otherModel;
                updateTableData(model);
            }

            @Override
            public void onRequestFail(Throwable cause) {
            	comparisonModel = null;
            	updateTableData(model);
                //GWT.log("ComparisonModel cannot be found. " + cause.getLocalizedMessage(), null);
            }
        });
    }
    
    private void resetSummaryTableRows(SummaryTableFieldBlock fieldBlock){
    	List<SummaryTableRow> rowList = fieldBlock.getSectionRowList();
    	List<SummaryTableRow> removeList = new ArrayList<SummaryTableRow>();
        for (int j = 0; j < rowList.size(); j++) {
        	 SummaryTableFieldRow fieldRow = (SummaryTableFieldRow) rowList.get(j);
        	 if(fieldRow.isTemporaryRow()){
        		 removeList.add(fieldRow);
        	 }
        	 if(!fieldRow.isShown()){
        		 fieldRow.setShown(true);
        	 }
        }
        rowList.removeAll(removeList);
    }
    
    private void buildSummaryTableMultiplicity(DataModel model, DataModel compModel, SummaryTableFieldBlock fieldBlock){
    	List<SummaryTableMultiplicityFieldRow> mRows = fieldBlock.getMultiplicityList();
    	for(int i = 0; i < mRows.size(); i++){
    		SummaryTableMultiplicityFieldRow mRow = mRows.get(i);
    		
    		buildMultiplicityRows(model, compModel, mRow, fieldBlock.getSectionRowList(), 1, null);
    	}
    }
    
    @SuppressWarnings("unchecked")
	private void updateTableData(DataModel model){
    	List<SummaryTableBlock> sectionList = summaryTableModel.getSectionList();
        for (int i = 0; i < sectionList.size(); i++) {
            SummaryTableFieldBlock fieldBlock = (SummaryTableFieldBlock) sectionList.get(i);
            resetSummaryTableRows(fieldBlock);
            if(!fieldBlock.getMultiplicityList().isEmpty()){
            	buildSummaryTableMultiplicity(model, comparisonModel, fieldBlock);
            }
            List<SummaryTableRow> rowList = fieldBlock.getSectionRowList();
            
            for (int j = 0; j < rowList.size(); j++) {
                SummaryTableFieldRow fieldRow = (SummaryTableFieldRow) rowList.get(j);
                FieldDescriptor field = fieldRow.getFieldDescriptor1();
                final FieldDescriptor field2 = fieldRow.getFieldDescriptor2();
                boolean optional = false;
                boolean firstValueEmpty = true;
                boolean secondValueEmpty = true;
                // for the first column
                if(field != null){
                	
                	if(field instanceof FieldDescriptorReadOnly){
                		optional = ((FieldDescriptorReadOnly)field).isOptional();
                	}
                	String fieldPath = QueryPath.getPathSeparator() + field.getFieldKey();
                	if(model.isValidPath(fieldPath)){
                	
	                	Object value = model.get(QueryPath.parse(fieldPath));
	                	if(value != null){
                    		if(value instanceof String && ((String)value).isEmpty()){
		                		firstValueEmpty = true;
		                	}
                    		else if(value instanceof Data && ((Data) value).size() == 0){
                    			firstValueEmpty = true;
                    		}
                    		else{
                    			firstValueEmpty = false;
                    		}
	                	}
	                	
		                
	                	ModelWidgetBinding binding = field.getModelWidgetBinding();
	                
		                if (binding != null) {
		                    Widget w = field.getFieldWidget();
		                    binding.setWidgetValue(w, model, fieldPath);
		                } else {
		                    GWT.log(field.getFieldKey() + " has no widget binding.", null);
		                }
	                }
                	
                }

                // the second column
                if (comparisonModel == null) {
                	if(fieldRow.getContentCellCount() == 2){
                		fieldRow.setContentCellCount(1);
                	}
                }else{
                	summaryTableModel.setContentColumnHeader1(model.getModelName());
                	summaryTableModel.setContentColumnHeader2(comparisonModel.getModelName());
                	if(fieldRow.getContentCellCount() == 1){
                		fieldRow.setContentCellCount(2);
                	}
                	if(field2 != null){
	                    
	                    String fieldPath2 = QueryPath.getPathSeparator() + field2.getFieldKey();
	                    if(comparisonModel.isValidPath(fieldPath2)){
	                    	
	                    	Object value = model.get(QueryPath.parse(fieldPath2));
	                    	if(value != null){
	                    		if(value instanceof String && ((String)value).isEmpty()){
			                		secondValueEmpty = true;
			                	}
	                    		else if(value instanceof Data && ((Data) value).size() == 0){
	                    			secondValueEmpty = true;
	                    		}
	                    		else{
	                    			secondValueEmpty = false;
	                    		}
		                	}
	                    	
	                    	ModelWidgetBinding binding2 = field2.getModelWidgetBinding();
	                    	
		                    if (binding2 != null) {
		                        Widget w = field2.getFieldWidget();
		                        binding2.setWidgetValue(w, comparisonModel, fieldPath2);
		                    } else {
		                        GWT.log(field2.getFieldKey() + " has no widget binding for the ComparisonModel.", null);
		                    }
	                    }
                	}
                }
                
                if(firstValueEmpty && secondValueEmpty && optional){
                	fieldRow.setShown(false);
                }
                processShowConditions(fieldRow, model, comparisonModel);
            }
            
        }

        summaryTable.doLayout();
        summaryTable.markDiffs("rowDiffHighlight");
    }

    @Override
    public String addField(FieldDescriptor fieldDescriptor) {
        GWT.log("addField(FieldDescriptor fieldDescriptor) method not supported");
        throw new UnsupportedOperationException("SummaryTableSection.addField(FieldDescriptor fieldDescriptor) method not supported");
    }

    @Override
    public String addSection(Section section) {
        GWT.log("addSection(Section section) method not supported");
        throw new UnsupportedOperationException("SummaryTableSection.addSection(Section section) method not supported");
    }

    @Override
    public String addSection(String key, Section section) {
        GWT.log("addSection(String key, Section section) method not supported");
        throw new UnsupportedOperationException("SummaryTableSection.addSection(String key, Section section) method not supported");
    }

    public SummaryTable getSummaryTable() {
        return summaryTable;
    }

}
