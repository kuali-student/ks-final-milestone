package org.kuali.student.common.ui.client.widgets.documenttool;

import java.util.ArrayList;
import java.util.List;

import org.kuali.student.common.ui.client.application.KSAsyncCallback;
import org.kuali.student.common.ui.client.configurable.mvc.SectionTitle;
import org.kuali.student.common.ui.client.mvc.Callback;
import org.kuali.student.common.ui.client.service.DocumentRpcService;
import org.kuali.student.common.ui.client.service.DocumentRpcServiceAsync;
import org.kuali.student.common.ui.client.widgets.field.layout.element.AbbrButton;
import org.kuali.student.common.ui.client.widgets.field.layout.element.AbbrButton.AbbrButtonType;
import org.kuali.student.core.document.dto.RefDocRelationInfo;
import org.kuali.student.core.dto.StatusInfo;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTML;

public class DocumentList extends Composite{
	private DocumentRpcServiceAsync documentServiceAsync = GWT.create(DocumentRpcService.class);
    private FlexTable tableLayout = new FlexTable();
    private List<RefDocRelationInfo> docInfos;
    private Callback<String> deleteCallback;
    private boolean canDelete = false;
    private boolean showDesc = true;
    private boolean showTitle = true;
    private String refObjectType;
    
    public DocumentList(String refObjectType, boolean showTitle, boolean showDesc) {
    	this.showTitle = showTitle;
    	this.showDesc = showDesc;
    	this.refObjectType = refObjectType;
        this.initWidget(tableLayout);
    }
    
    public DocumentList(String refObjectType, List<RefDocRelationInfo> docInfos, boolean showTitle, boolean showDesc) {
    	this.showTitle = showTitle;
    	this.showDesc = showDesc;
    	this.refObjectType = refObjectType;
        setDocInfos(docInfos);
        this.initWidget(tableLayout);
    }
    
    
    public DocumentList(String refObjectType, List<RefDocRelationInfo> docInfos,
			Callback<String> deleteCallback) {
    	canDelete = true;
    	this.refObjectType = refObjectType;
    	this.deleteCallback = deleteCallback;
        setDocInfos(docInfos);
        this.initWidget(tableLayout);
	}

	public void getAndSetDocInfos(String id){
		documentServiceAsync.getRefDocIdsForRef(refObjectType, id, new KSAsyncCallback<List<RefDocRelationInfo>>(){

			@Override
			public void handleFailure(Throwable caught) {
				GWT.log("getRefDocIdsForRef failed", caught);
				setDocInfos(null);
			}

			@Override
			public void onSuccess(List<RefDocRelationInfo> result) {
				setDocInfos(result);
			}
		});
    }
    
    public void setDocInfos(List<RefDocRelationInfo> docInfos) {
        this.docInfos = docInfos;
        redraw();
    }
    
    public void add(RefDocRelationInfo docInfo) {
        docInfos = (docInfos == null)? new ArrayList<RefDocRelationInfo>() :
            docInfos;
        docInfos.add(docInfo);
        redraw();
    }
    
    private void redraw() {
        tableLayout.clear();
        if (docInfos != null) {
            int rowIndex = 0;
            
            for (final RefDocRelationInfo docInfo : docInfos) {
                HTML name = new HTML("No file exists");
                HTML documentText = new HTML();
                
                int columnIndex = 0;
                
                // display header
                if (rowIndex == 0 && showTitle) {
                    StringBuilder titleTextSb = new StringBuilder();
                    titleTextSb.append("Attached Documents (").append(docInfos.size()).append(")");
                    SectionTitle uploadedFileSectionHeader = SectionTitle.generateH3Title(titleTextSb.toString());
                    uploadedFileSectionHeader.getElement().getStyle().setProperty("borderBottom", "1px solid #D8D8D8");
                    tableLayout.setWidget(rowIndex, columnIndex, uploadedFileSectionHeader);
                    tableLayout.getFlexCellFormatter().setColSpan(rowIndex, columnIndex, 3);
                    rowIndex++;
                }
                
                tableLayout.setWidget(rowIndex, columnIndex, name);
                name.setHTML("<a href=\"" + GWT.getModuleBaseURL()+"rpcservices/DocumentUpload?docId=" + docInfo.getDocumentId() + "\" target=\"_blank\"><b>" + docInfo.getTitle() + "</b></a>");
                name.getElement().getStyle().setPaddingRight(20d, Style.Unit.PX);
                tableLayout.setWidget(rowIndex, columnIndex, name);
                columnIndex++;
                if(showDesc){
	                documentText.setHTML(docInfo.getDesc().getPlain());
	                documentText.getElement().getStyle().setPaddingRight(20d, Style.Unit.PX);
	                tableLayout.setWidget(rowIndex, columnIndex, documentText);
	                columnIndex++;
                }
                if(canDelete){
	                AbbrButton delete = new AbbrButton(AbbrButtonType.DELETE);
	                
	                tableLayout.setWidget(rowIndex, columnIndex, delete);
	                delete.addClickHandler(new ClickHandler(){
	                    @Override
	                    public void onClick(ClickEvent event) {
	                         try {
	                             //TODO Reviewed in M6, future fix: this will fail if the document does not exist BUT the relation does, needs a check for existance
	                             //before delete
	                        	 documentServiceAsync.deleteRefDocRelationAndOrphanedDoc(docInfo.getId(), docInfo.getDocumentId(), new KSAsyncCallback<StatusInfo>(){
	                                @Override
	                                public void onSuccess(StatusInfo result) {
	                                	deleteCallback.exec(result.toString());
	                                }
	                            });
	                        } catch (Exception e) {
	                            GWT.log("deleteRefDocRelationAndOrphanedDoc failed", e);
	                        }
	
	
	                    }
	                });
                }
                rowIndex++;
            }
        }
    }
}
