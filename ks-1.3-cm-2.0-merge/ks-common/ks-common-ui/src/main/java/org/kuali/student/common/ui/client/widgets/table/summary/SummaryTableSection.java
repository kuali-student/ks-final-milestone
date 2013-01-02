 package org.kuali.student.common.ui.client.widgets.table.summary;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

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
import org.kuali.student.r1.common.assembly.data.Data;
import org.kuali.student.r1.common.assembly.data.MetadataInterrogator;
import org.kuali.student.r1.common.assembly.data.QueryPath;
import org.kuali.student.r1.common.assembly.data.Data.Property;
import org.kuali.student.r2.common.dto.ValidationResultInfo;
import org.kuali.student.r2.common.infc.ValidationResult.ErrorLevel;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.Widget;

public class SummaryTableSection extends VerticalSection {
    SummaryTable summaryTable = new SummaryTable();
    SummaryTableModel summaryTableModel = new SummaryTableModel();
    Controller controller;
    DataModel comparisonModel = null;
    List<ShowRowConditionCallback> showRowCallbacks = new ArrayList<ShowRowConditionCallback>();
    boolean isMissingFields= false;	//KSLAB-1985
	boolean hasWarnings= false;	//KSLAB-1985
    
    public boolean getIsMissingFields() {
		return isMissingFields;
	}

    public boolean getHasWarnings(){
    	return hasWarnings;
    }

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
    public ErrorLevel processValidationResults(List<ValidationResultInfo> results) {    	
    	//initialize condition parameters
    	ErrorLevel status= ErrorLevel.OK;
    	
    	isMissingFields= false;    // Set-ness affects CourseSummaryConfigurer.resolveMissingFieldsWarnings()
    	hasWarnings= false;    // Set-ness affects CourseSummaryConfigurer.resolveProposalSubmissionWarnings()
    	
    	// Process results
    	for(int i = 0; i < results.size(); i++){
    		ValidationResultInfo resultI= results.get(i);
    		
    		if(summaryTable.containsKey(results.get(i).getElement())){
    			
    			System.out.println(resultI.getElement() + " *** " + resultI.getErrorLevel() + " *** " + resultI.getMessage());
    			
    			if(resultI.getLevel().getLevel() > status.getLevel()){    				
    				status= resultI.getLevel();
    				
    				if(resultI.getMessage().equals("Required")){	//KSLAB-1985
    				    
    					isMissingFields= true;
    				}
    			}
    			
    			if(this.isValidationEnabled){
    			    
        			summaryTable.highlightRow(resultI.getElement(), "rowHighlight");
        		}
    		}
    	}
    	
    	List<ValidationResultInfo> warnings= Application.getApplicationContext().getValidationWarnings();
    	ValidationResultInfo tempVr= new ValidationResultInfo();
    	
    	tempVr.setElement("");
    	
    	// Process ApplicationContext warnings
    	for(int i = 0; i < warnings.size(); i++){    		
    		//Reformat the validation element path based on how it can be referenced in sumaryTable rowMap
    		String element= warnings.get(i).getElement();    
    		
    		if (element.startsWith("/")){    		    			
    		    
    			//Remove leading '/' since paths aren't stored this way in rowMap
    			element= element.substring(1);
    			
    		} else if (element.matches(".*/[0-9]+")){ 
    		    
    			//Validation warnings returns path to individual items of simple multiplicity, 
    			//stripping of the item index to highlight the entire field. 
    			element= element.substring(0, element.lastIndexOf("/")); 
    		}
    		
    		if(summaryTable.containsKey(element)){
    			
        		hasWarnings= true;
    			
        		if(warnings.get(i).getLevel().getLevel() > status.getLevel()){
    				
        		    status= warnings.get(i).getLevel();
    			}
        		    			
       			summaryTable.highlightRow(element, "warning");    //Highlights related warning fields in Dark Yellow
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
    	MultiplicityConfiguration config = parentRow.getConfig();
    	int index = rowList.indexOf(parentRow) + 1;
    	int fieldRowsCreated = 0;
    	int number = 0;
    	String path = parentRow.getConfig().getParentFd().getFieldKey();
    	if(parentNum != null){
    		path = path.replace("*", "" + parentNum);
    	}
    	Data data = null;
    	if(model != null && model.isValidPath(path)){
    		data = model.get(QueryPath.parse(path));
    	}
		Data compData = null;
		if(compModel != null && compModel.isValidPath(path)){
			compData = compModel.get(QueryPath.parse(path));
		}
    	if((data != null && data.size() > 0) || (compData != null && compData.size() > 0)){
    		Iterator<Property> itr = null;
    		if(data != null && compData != null){
    			if(data.size() >= compData.size()){
    				itr = data.iterator();
    			} else{
    				itr = compData.iterator();
    			}
    		} else if(data != null){
    			itr = data.iterator();
    		} else{
    			itr = compData.iterator();
    		}
    		SummaryTableMultiplicityFieldRow currentMultiplicityRow = parentRow;
            while (itr.hasNext()) {
                Property p = (Property) itr.next();
                if (p.getKey() instanceof Integer){
                	number = (Integer)p.getKey();
                }
        		if(config.getItemLabel() != null && !config.getItemLabel().isEmpty()){
        			currentMultiplicityRow.setTitle(config.getItemLabel() + " "+ (number + 1));
        			currentMultiplicityRow.setKey(path);
            		if(MetadataInterrogator.isRequired(config.getMetaData()) || 
                			MetadataInterrogator.isRequiredForNextState(config.getMetaData())){
            			currentMultiplicityRow.setRequired(true);
            		}
            		currentMultiplicityRow.addTitleCellStyleName("summary-table-multiplicity-level-" + styleLevel);
        		}
        		else{
        			currentMultiplicityRow.setShown(false);
        		}
	    		//set has-data/requires data style here
	    		Map<Integer, List<MultiplicityFieldConfiguration>> fieldsCopy = config.getFields();
				for(int i = 0; i < config.getFields().size(); i++){
					for(int j = 0; j < config.getFields().get(i).size(); j++){
						//TODO handle custom widgets (multiplicity field widget initializer)
						MultiplicityFieldConfiguration field = fieldsCopy.get(i).get(j);
	    				String fieldKey = translatePath(field.getFieldPath(), path, number);
	    				FieldDescriptorReadOnly fd1 = new FieldDescriptorReadOnly(fieldKey, field.getMessageKeyInfo(), field.getMetadata());
	    				fd1.setOptional(field.isOptional());
	    				if(field.getModelWidgetBinding() != null){
	    					fd1.setWidgetBinding(field.getModelWidgetBinding());
	    				}
	    				FieldDescriptorReadOnly fd2 = new FieldDescriptorReadOnly(fieldKey, field.getMessageKeyInfo(), field.getMetadata());
	    				fd2.setOptional(field.isOptional());
	    				if(field.getModelWidgetBinding() != null){
	    					fd2.setWidgetBinding(field.getModelWidgetBinding());
	    				}
	    				SummaryTableFieldRow row = new SummaryTableFieldRow(fd1, fd2);
	    				row.setTemporaryRowFlag(true);
	    				rowList.add(index, row);
	    				index++;
	    				fieldRowsCreated++;
	    			}
				}
	    		if(config.getNestedConfig() != null){
	    			MultiplicityConfiguration nestedConfig = config.getNestedConfig();
	    			nestedConfig.getParentFd().getFieldKey().replace(config.getParentFd().getFieldKey(), path);
	    			SummaryTableMultiplicityFieldRow mRow = new SummaryTableMultiplicityFieldRow(nestedConfig);
	    			mRow.setTemporaryRowFlag(true);
	    			rowList.add(index, mRow);
	    			index++;
	    			fieldRowsCreated++;
	    			int result = buildMultiplicityRows(model, compModel, mRow, rowList, styleLevel + 1, number);
	    			index = index + result;
	    		}
	    		if(itr.hasNext()){
	    			SummaryTableMultiplicityFieldRow mRow = new SummaryTableMultiplicityFieldRow(config);
	    			mRow.setTemporaryRowFlag(true);
	    			rowList.add(index, mRow);
	    			index++;
	    			fieldRowsCreated++;
	    			currentMultiplicityRow = mRow;
	    		}
            }
    	}
    	else{
        	if(MetadataInterrogator.isRequired(config.getMetaData()) || 
        			MetadataInterrogator.isRequiredForNextState(config.getMetaData())){
        		if(config.getItemLabel() != null && !config.getItemLabel().isEmpty()){
        			parentRow.setTitle(config.getItemLabel() + " "+ (number + 1));
        			parentRow.setKey(path);
            		parentRow.setRequired(true);
            		parentRow.addTitleCellStyleName("summary-table-multiplicity-level-" + styleLevel);
        		}
        		else{
        			parentRow.setShown(false);
        		}
        		//set has-data/requires data style here
        		Map<Integer, List<MultiplicityFieldConfiguration>> fields = config.getFields();
				for(int i = 0; i < fields.size(); i++){
					for(int j = 0; j < fields.get(i).size(); j++){
						//TODO handle custom widgets (multiplicity field widget initializer)
						MultiplicityFieldConfiguration field = fields.get(i).get(j);
	    				String fieldKey = translatePath(field.getFieldPath(), path, number);
	    				FieldDescriptorReadOnly fd1 = new FieldDescriptorReadOnly(fieldKey, field.getMessageKeyInfo(), field.getMetadata());
	    				fd1.setOptional(field.isOptional());
	    				if(field.getModelWidgetBinding() != null){
	    					fd1.setWidgetBinding(field.getModelWidgetBinding());
	    				}
	    				FieldDescriptorReadOnly fd2 = new FieldDescriptorReadOnly(fieldKey, field.getMessageKeyInfo(), field.getMetadata());
	    				fd2.setOptional(field.isOptional());
	    				if(field.getModelWidgetBinding() != null){
	    					fd2.setWidgetBinding(field.getModelWidgetBinding());
	    				}
	    				SummaryTableFieldRow row = new SummaryTableFieldRow(fd1, fd2);
	    				row.setTemporaryRowFlag(true);
	    				rowList.add(index, row);
	    				index++;
	    				fieldRowsCreated++;
	    			}
				}
	    		if(config.getNestedConfig() != null){
	    			MultiplicityConfiguration nestedConfig = config.getNestedConfig();
	    			nestedConfig.getParentFd().getFieldKey().replace(config.getParentFd().getFieldKey(), path);
	    			SummaryTableMultiplicityFieldRow mRow = new SummaryTableMultiplicityFieldRow(nestedConfig);
	    			mRow.setTemporaryRowFlag(true);
	    			rowList.add(index, mRow);
	    			index++;
	    			fieldRowsCreated++;
	    			buildMultiplicityRows(null, null, mRow, rowList, styleLevel + 1, number);
	    		}
        	}
        	else{
        		//Alternate label possibly here
        		parentRow.setTitle(config.getItemLabel());
        		parentRow.setRequired(false);
        		parentRow.setKey(config.getParentFd().getFieldKey());
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
                	}
		                
	                ModelWidgetBinding binding = field.getModelWidgetBinding();
	                
		            if (binding != null) {
		                Widget w = field.getFieldWidget();
		                binding.setWidgetValue(w, model, fieldPath);
		            } else {
		                GWT.log(field.getFieldKey() + " has no widget binding.", null);
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
