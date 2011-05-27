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
    	int ix=0;
    	StaticDeletemeLineLogger.log("buildMultiplicityRows-"+ix++);
    	MultiplicityConfiguration config = parentRow.getConfig();
    	StaticDeletemeLineLogger.log("buildMultiplicityRows-"+ix++);
    	int index = rowList.indexOf(parentRow) + 1;
    	StaticDeletemeLineLogger.log("buildMultiplicityRows-"+ix++);
    	int fieldRowsCreated = 0;
    	StaticDeletemeLineLogger.log("buildMultiplicityRows-"+ix++);
    	int number = 0;
    	StaticDeletemeLineLogger.log("buildMultiplicityRows-"+ix++);
    	String path = parentRow.getConfig().getParentFd().getFieldKey();
    	StaticDeletemeLineLogger.log("buildMultiplicityRows-"+ix++);
    	if(parentNum != null){
    		StaticDeletemeLineLogger.log("buildMultiplicityRows-"+ix++);
    		path = path.replace("*", "" + parentNum);
    		StaticDeletemeLineLogger.log("buildMultiplicityRows-"+ix++);
    	}
    	StaticDeletemeLineLogger.log("buildMultiplicityRows-"+ix++);
    	Data data = null;
    	StaticDeletemeLineLogger.log("buildMultiplicityRows-"+ix++);
    	if(model != null && model.isValidPath(path)){
    		StaticDeletemeLineLogger.log("buildMultiplicityRows-"+ix++);
    		data = model.get(QueryPath.parse(path));
    		StaticDeletemeLineLogger.log("buildMultiplicityRows-"+ix++);
    	}
    	StaticDeletemeLineLogger.log("buildMultiplicityRows-"+ix++);
		Data compData = null;
		StaticDeletemeLineLogger.log("buildMultiplicityRows-"+ix++);
		if(compModel != null && compModel.isValidPath(path)){
			StaticDeletemeLineLogger.log("buildMultiplicityRows-"+ix++);
			compData = compModel.get(QueryPath.parse(path));
			StaticDeletemeLineLogger.log("buildMultiplicityRows-"+ix++);
		}
		StaticDeletemeLineLogger.log("buildMultiplicityRows-"+ix++);
    	if((data != null && data.size() > 0) || (compData != null && compData.size() > 0)){
    		StaticDeletemeLineLogger.log("buildMultiplicityRows-"+ix++);
    		Iterator<Property> itr = null;
    		StaticDeletemeLineLogger.log("buildMultiplicityRows-"+ix++);
    		if(data != null && compData != null){
    			StaticDeletemeLineLogger.log("buildMultiplicityRows-"+ix++);
    			if(data.size() >= compData.size()){
    				StaticDeletemeLineLogger.log("buildMultiplicityRows-"+ix++);
    				itr = data.iterator();
    				StaticDeletemeLineLogger.log("buildMultiplicityRows-"+ix++);
    			} else{
    				StaticDeletemeLineLogger.log("buildMultiplicityRows-"+ix++);
    				itr = compData.iterator();
    				StaticDeletemeLineLogger.log("buildMultiplicityRows-"+ix++);
    			}
    			StaticDeletemeLineLogger.log("buildMultiplicityRows-"+ix++);
    		} else if(data != null){
    			StaticDeletemeLineLogger.log("buildMultiplicityRows-"+ix++);
    			itr = data.iterator();
    			StaticDeletemeLineLogger.log("buildMultiplicityRows-"+ix++);
    		} else{
    			StaticDeletemeLineLogger.log("buildMultiplicityRows-"+ix++);
    			itr = compData.iterator();
    			StaticDeletemeLineLogger.log("buildMultiplicityRows-"+ix++);
    		}
    		StaticDeletemeLineLogger.log("buildMultiplicityRows-"+ix++);
    		SummaryTableMultiplicityFieldRow currentMultiplicityRow = parentRow;
    		StaticDeletemeLineLogger.log("buildMultiplicityRows-"+ix++);
            while (itr.hasNext()) {
            	StaticDeletemeLineLogger.log("buildMultiplicityRows-"+ix++);
                Property p = (Property) itr.next();
                StaticDeletemeLineLogger.log("buildMultiplicityRows-"+ix++);
                if (p.getKey() instanceof Integer){
                	StaticDeletemeLineLogger.log("buildMultiplicityRows-"+ix++);
                	number = (Integer)p.getKey();
                	StaticDeletemeLineLogger.log("buildMultiplicityRows-"+ix++);
                }
                StaticDeletemeLineLogger.log("buildMultiplicityRows-"+ix++);
        		if(config.getItemLabel() != null && !config.getItemLabel().isEmpty()){
        			StaticDeletemeLineLogger.log("buildMultiplicityRows-"+ix++);
        			currentMultiplicityRow.setTitle(config.getItemLabel() + " "+ (number + 1));
        			StaticDeletemeLineLogger.log("buildMultiplicityRows-"+ix++);
        			currentMultiplicityRow.setKey(path);
        			StaticDeletemeLineLogger.log("buildMultiplicityRows-"+ix++);
            		if(MetadataInterrogator.isRequired(config.getMetaData()) || 
                			MetadataInterrogator.isRequiredForNextState(config.getMetaData())){
            			StaticDeletemeLineLogger.log("buildMultiplicityRows-"+ix++);
            			currentMultiplicityRow.setRequired(true);
            			StaticDeletemeLineLogger.log("buildMultiplicityRows-"+ix++);
            		}
            		StaticDeletemeLineLogger.log("buildMultiplicityRows-"+ix++);
            		currentMultiplicityRow.addTitleCellStyleName("summary-table-multiplicity-level-" + styleLevel);
            		StaticDeletemeLineLogger.log("buildMultiplicityRows-"+ix++);
        		}
        		else{
        			StaticDeletemeLineLogger.log("buildMultiplicityRows-"+ix++);
        			currentMultiplicityRow.setShown(false);
        			StaticDeletemeLineLogger.log("buildMultiplicityRows-"+ix++);
        		}
	    		//set has-data/requires data style here
        		StaticDeletemeLineLogger.log("buildMultiplicityRows-"+ix++);
	    		Map<Integer, List<MultiplicityFieldConfiguration>> fieldsCopy = config.getFields();
	    		StaticDeletemeLineLogger.log("buildMultiplicityRows-"+ix++);
				for(int i = 0; i < config.getFields().size(); i++){
					StaticDeletemeLineLogger.log("buildMultiplicityRows-"+ix++);
					for(int j = 0; j < config.getFields().get(i).size(); j++){
						//TODO handle custom widgets (multiplicity field widget initializer)
						StaticDeletemeLineLogger.log("buildMultiplicityRows-"+ix++);
						MultiplicityFieldConfiguration field = fieldsCopy.get(i).get(j);
						StaticDeletemeLineLogger.log("buildMultiplicityRows-"+ix++);
	    				String fieldKey = translatePath(field.getFieldPath(), path, number);
	    				StaticDeletemeLineLogger.log("buildMultiplicityRows-"+ix++);
	    				FieldDescriptorReadOnly fd1 = new FieldDescriptorReadOnly(fieldKey, field.getMessageKeyInfo(), field.getMetadata());
	    				StaticDeletemeLineLogger.log("buildMultiplicityRows-"+ix++);
	    				fd1.setOptional(field.isOptional());
	    				StaticDeletemeLineLogger.log("buildMultiplicityRows-"+ix++);
	    				if(field.getModelWidgetBinding() != null){
	    					StaticDeletemeLineLogger.log("buildMultiplicityRows-"+ix++);
	    					fd1.setWidgetBinding(field.getModelWidgetBinding());
	    					StaticDeletemeLineLogger.log("buildMultiplicityRows-"+ix++);
	    				}
	    				StaticDeletemeLineLogger.log("buildMultiplicityRows-"+ix++);
	    				FieldDescriptorReadOnly fd2 = new FieldDescriptorReadOnly(fieldKey, field.getMessageKeyInfo(), field.getMetadata());
	    				StaticDeletemeLineLogger.log("buildMultiplicityRows-"+ix++);
	    				fd2.setOptional(field.isOptional());
	    				StaticDeletemeLineLogger.log("buildMultiplicityRows-"+ix++);
	    				if(field.getModelWidgetBinding() != null){
	    					StaticDeletemeLineLogger.log("buildMultiplicityRows-"+ix++);
	    					fd2.setWidgetBinding(field.getModelWidgetBinding());
	    					StaticDeletemeLineLogger.log("buildMultiplicityRows-"+ix++);
	    				}
	    				StaticDeletemeLineLogger.log("buildMultiplicityRows-"+ix++);
	    				SummaryTableFieldRow row = new SummaryTableFieldRow(fd1, fd2);
	    				StaticDeletemeLineLogger.log("buildMultiplicityRows-"+ix++);
	    				row.setTemporaryRowFlag(true);
	    				StaticDeletemeLineLogger.log("buildMultiplicityRows-"+ix++);
	    				rowList.add(index, row);
	    				StaticDeletemeLineLogger.log("buildMultiplicityRows-"+ix++);
	    				index++;
	    				StaticDeletemeLineLogger.log("buildMultiplicityRows-"+ix++);
	    				fieldRowsCreated++;
	    				StaticDeletemeLineLogger.log("buildMultiplicityRows-"+ix++);
	    			}
				}
				StaticDeletemeLineLogger.log("buildMultiplicityRows-"+ix++);
	    		if(config.getNestedConfig() != null){
	    			StaticDeletemeLineLogger.log("buildMultiplicityRows-"+ix++);
	    			MultiplicityConfiguration nestedConfig = config.getNestedConfig();
	    			StaticDeletemeLineLogger.log("buildMultiplicityRows-"+ix++);
	    			nestedConfig.getParentFd().getFieldKey().replace(config.getParentFd().getFieldKey(), path);
	    			StaticDeletemeLineLogger.log("buildMultiplicityRows-"+ix++);
	    			SummaryTableMultiplicityFieldRow mRow = new SummaryTableMultiplicityFieldRow(nestedConfig);
	    			mRow.setTemporaryRowFlag(true);
	    			StaticDeletemeLineLogger.log("buildMultiplicityRows-"+ix++);
	    			rowList.add(index, mRow);
	    			StaticDeletemeLineLogger.log("buildMultiplicityRows-"+ix++);
	    			index++;
	    			StaticDeletemeLineLogger.log("buildMultiplicityRows-"+ix++);
	    			fieldRowsCreated++;
	    			StaticDeletemeLineLogger.log("buildMultiplicityRows-"+ix++);
	    			int result = buildMultiplicityRows(model, compModel, mRow, rowList, styleLevel + 1, number);
	    			StaticDeletemeLineLogger.log("buildMultiplicityRows-"+ix++);
	    			index = index + result;
	    			StaticDeletemeLineLogger.log("buildMultiplicityRows-"+ix++);
	    		}
	    		StaticDeletemeLineLogger.log("buildMultiplicityRows-"+ix++);
	    		if(itr.hasNext()){
	    			StaticDeletemeLineLogger.log("buildMultiplicityRows-"+ix++);
	    			SummaryTableMultiplicityFieldRow mRow = new SummaryTableMultiplicityFieldRow(config);
	    			StaticDeletemeLineLogger.log("buildMultiplicityRows-"+ix++);
	    			mRow.setTemporaryRowFlag(true);
	    			StaticDeletemeLineLogger.log("buildMultiplicityRows-"+ix++);
	    			rowList.add(index, mRow);
	    			StaticDeletemeLineLogger.log("buildMultiplicityRows-"+ix++);
	    			index++;
	    			StaticDeletemeLineLogger.log("buildMultiplicityRows-"+ix++);
	    			fieldRowsCreated++;
	    			StaticDeletemeLineLogger.log("buildMultiplicityRows-"+ix++);
	    			currentMultiplicityRow = mRow;
	    			StaticDeletemeLineLogger.log("buildMultiplicityRows-"+ix++);
	    		}
            }
    	}
    	else{
    		StaticDeletemeLineLogger.log("buildMultiplicityRows-"+ix++);
        	if(MetadataInterrogator.isRequired(config.getMetaData()) || 
        			MetadataInterrogator.isRequiredForNextState(config.getMetaData())){
        		StaticDeletemeLineLogger.log("buildMultiplicityRows-"+ix++);
        		if(config.getItemLabel() != null && !config.getItemLabel().isEmpty()){
        			StaticDeletemeLineLogger.log("buildMultiplicityRows-"+ix++);
        			parentRow.setTitle(config.getItemLabel() + " "+ (number + 1));
        			StaticDeletemeLineLogger.log("buildMultiplicityRows-"+ix++);
        			parentRow.setKey(path);
        			StaticDeletemeLineLogger.log("buildMultiplicityRows-"+ix++);
            		parentRow.setRequired(true);
            		StaticDeletemeLineLogger.log("buildMultiplicityRows-"+ix++);
            		parentRow.addTitleCellStyleName("summary-table-multiplicity-level-" + styleLevel);
            		StaticDeletemeLineLogger.log("buildMultiplicityRows-"+ix++);
        		}
        		else{
        			StaticDeletemeLineLogger.log("buildMultiplicityRows-"+ix++);
        			parentRow.setShown(false);
        			StaticDeletemeLineLogger.log("buildMultiplicityRows-"+ix++);
        		}
        		//set has-data/requires data style here
        		StaticDeletemeLineLogger.log("buildMultiplicityRows-"+ix++);
        		Map<Integer, List<MultiplicityFieldConfiguration>> fields = config.getFields();
        		StaticDeletemeLineLogger.log("buildMultiplicityRows-"+ix++);
				for(int i = 0; i < fields.size(); i++){
	        		StaticDeletemeLineLogger.log("buildMultiplicityRows-"+ix++);
					for(int j = 0; j < fields.get(i).size(); j++){
		        		StaticDeletemeLineLogger.log("buildMultiplicityRows-"+ix++);
						//TODO handle custom widgets (multiplicity field widget initializer)
						MultiplicityFieldConfiguration field = fields.get(i).get(j);
		        		StaticDeletemeLineLogger.log("buildMultiplicityRows-"+ix++);
	    				String fieldKey = translatePath(field.getFieldPath(), path, number);
	            		StaticDeletemeLineLogger.log("buildMultiplicityRows-"+ix++);
	    				FieldDescriptorReadOnly fd1 = new FieldDescriptorReadOnly(fieldKey, field.getMessageKeyInfo(), field.getMetadata());
	            		StaticDeletemeLineLogger.log("buildMultiplicityRows-"+ix++);
	    				fd1.setOptional(field.isOptional());
	            		StaticDeletemeLineLogger.log("buildMultiplicityRows-"+ix++);
	    				if(field.getModelWidgetBinding() != null){
	    	        		StaticDeletemeLineLogger.log("buildMultiplicityRows-"+ix++);
	    					fd1.setWidgetBinding(field.getModelWidgetBinding());
	    	        		StaticDeletemeLineLogger.log("buildMultiplicityRows-"+ix++);
	    				}
	            		StaticDeletemeLineLogger.log("buildMultiplicityRows-"+ix++);
	    				FieldDescriptorReadOnly fd2 = new FieldDescriptorReadOnly(fieldKey, field.getMessageKeyInfo(), field.getMetadata());
	            		StaticDeletemeLineLogger.log("buildMultiplicityRows-"+ix++);
	    				fd2.setOptional(field.isOptional());
	            		StaticDeletemeLineLogger.log("buildMultiplicityRows-"+ix++);
	    				if(field.getModelWidgetBinding() != null){
	    	        		StaticDeletemeLineLogger.log("buildMultiplicityRows-"+ix++);
	    					fd2.setWidgetBinding(field.getModelWidgetBinding());
	    	        		StaticDeletemeLineLogger.log("buildMultiplicityRows-"+ix++);
	    				}
	            		StaticDeletemeLineLogger.log("buildMultiplicityRows-"+ix++);
	    				SummaryTableFieldRow row = new SummaryTableFieldRow(fd1, fd2);
	            		StaticDeletemeLineLogger.log("buildMultiplicityRows-"+ix++);
	    				row.setTemporaryRowFlag(true);
	            		StaticDeletemeLineLogger.log("buildMultiplicityRows-"+ix++);
	    				rowList.add(index, row);
	            		StaticDeletemeLineLogger.log("buildMultiplicityRows-"+ix++);
	    				index++;
	            		StaticDeletemeLineLogger.log("buildMultiplicityRows-"+ix++);
	    				fieldRowsCreated++;
	            		StaticDeletemeLineLogger.log("buildMultiplicityRows-"+ix++);
	    			}
				}
				
    			
        		StaticDeletemeLineLogger.log("buildMultiplicityRows-"+ix++);
	    		if(config.getNestedConfig() != null){
	        		StaticDeletemeLineLogger.log("buildMultiplicityRows-"+ix++);
	    			MultiplicityConfiguration nestedConfig = config.getNestedConfig();
	        		StaticDeletemeLineLogger.log("buildMultiplicityRows-"+ix++);
	    			nestedConfig.getParentFd().getFieldKey().replace(config.getParentFd().getFieldKey(), path);
	        		StaticDeletemeLineLogger.log("buildMultiplicityRows-"+ix++);
	    			SummaryTableMultiplicityFieldRow mRow = new SummaryTableMultiplicityFieldRow(nestedConfig);
	        		StaticDeletemeLineLogger.log("buildMultiplicityRows-"+ix++);
	    			mRow.setTemporaryRowFlag(true);
	        		StaticDeletemeLineLogger.log("buildMultiplicityRows-"+ix++);
	    			rowList.add(index, mRow);
	        		StaticDeletemeLineLogger.log("buildMultiplicityRows-"+ix++);
	    			index++;
	        		StaticDeletemeLineLogger.log("buildMultiplicityRows-"+ix++);
	    			fieldRowsCreated++;
	        		StaticDeletemeLineLogger.log("buildMultiplicityRows-"+ix++);
	    			buildMultiplicityRows(null, null, mRow, rowList, styleLevel + 1, number);
	        		StaticDeletemeLineLogger.log("buildMultiplicityRows-"+ix++);
	    		}
        	}
        	else{
        		//Alternate label possibly here
        		StaticDeletemeLineLogger.log("buildMultiplicityRows-"+ix++);
        		parentRow.setTitle(config.getItemLabel());
        		StaticDeletemeLineLogger.log("buildMultiplicityRows-"+ix++);
        		parentRow.setRequired(false);
        		StaticDeletemeLineLogger.log("buildMultiplicityRows-"+ix++);
        		parentRow.setKey(config.getParentFd().getFieldKey());
        		StaticDeletemeLineLogger.log("buildMultiplicityRows-"+ix++);
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
