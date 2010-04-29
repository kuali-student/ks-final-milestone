/*
 * Copyright 2009 The Kuali Foundation Licensed under the
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
package org.kuali.student.common.ui.client.widgets.documenttool;

import java.util.List;

import org.kuali.student.common.ui.client.configurable.mvc.DelayedToolView;
import org.kuali.student.common.ui.client.configurable.mvc.HasReferenceId;
import org.kuali.student.common.ui.client.configurable.mvc.ToolView;
import org.kuali.student.common.ui.client.dto.FileStatus;
import org.kuali.student.common.ui.client.dto.UploadStatus;
import org.kuali.student.common.ui.client.dto.FileStatus.FileTransferStatus;
import org.kuali.student.common.ui.client.dto.UploadStatus.UploadTransferStatus;
import org.kuali.student.common.ui.client.mvc.Callback;
import org.kuali.student.common.ui.client.mvc.history.HistoryStackFrame;
import org.kuali.student.common.ui.client.service.DocumentRelationMockRpcService;
import org.kuali.student.common.ui.client.service.DocumentRelationMockRpcServiceAsync;
import org.kuali.student.common.ui.client.service.DocumentRpcService;
import org.kuali.student.common.ui.client.service.DocumentRpcServiceAsync;
import org.kuali.student.common.ui.client.service.UploadStatusRpcService;
import org.kuali.student.common.ui.client.service.UploadStatusRpcServiceAsync;
import org.kuali.student.common.ui.client.theme.Theme;
import org.kuali.student.common.ui.client.widgets.KSButton;
import org.kuali.student.common.ui.client.widgets.KSImage;
import org.kuali.student.common.ui.client.widgets.KSLabel;
import org.kuali.student.common.ui.client.widgets.KSLightBox;
import org.kuali.student.common.ui.client.widgets.KSStyles;
import org.kuali.student.common.ui.client.widgets.KSTextArea;
import org.kuali.student.common.ui.client.widgets.buttongroups.OkGroup;
import org.kuali.student.common.ui.client.widgets.buttongroups.ButtonEnumerations.OkEnum;
import org.kuali.student.common.ui.client.widgets.layout.HorizontalBlockFlowPanel;
import org.kuali.student.common.ui.client.widgets.layout.VerticalFlowPanel;
import org.kuali.student.core.dto.RefDocRelationInfoMock;
import org.kuali.student.core.dto.StatusInfo;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.i18n.client.NumberFormat;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FileUpload;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.FormPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.widgetideas.client.ProgressBar;
import com.google.gwt.widgetideas.client.ProgressBar.TextFormatter;

public class DocumentTool extends DelayedToolView implements HasReferenceId{

	private String referenceId;
	private String referenceTypeKey;
	private String referenceType;
	private String referenceState;

	private final int POLL_INTERVAL = 2000;
	
	private DocumentRpcServiceAsync documentServiceAsync = GWT.create(DocumentRpcService.class);
	private VerticalFlowPanel layout = new VerticalFlowPanel();
    private VerticalFlowPanel documentList = new VerticalFlowPanel();
    private VerticalFlowPanel uploadList = new VerticalFlowPanel();
    private KSButton addMore = new KSButton("Add Another");
    private KSLabel loadingDocuments = new KSLabel("Loading Documents...");
    private FormPanel form = new FormPanel();
    
    private KSLightBox progressWindow = new KSLightBox();
    private VerticalFlowPanel progressPanel = new VerticalFlowPanel();
    private KSLabel progressLabel = new KSLabel();
    private ProgressBar progressBar = new ProgressBar();
    private FlexTable fileProgressTable = new FlexTable();
    
    private OkGroup progressButtons = new OkGroup(new Callback<OkEnum>(){
		@Override
		public void exec(OkEnum result) {
			  if(result == OkEnum.Ok){
				  progressWindow.hide();
			  }
		}
    });  
    private UploadStatusRpcServiceAsync uploadStatusRpc = GWT.create(UploadStatusRpcService.class);
    private DocumentRelationMockRpcServiceAsync documentRelationRpc = GWT.create(DocumentRelationMockRpcService.class);
	
	private OkGroup buttonPanel = new OkGroup(new Callback<OkEnum>(){

		@Override
		public void exec(OkEnum result) {
			  if(result == OkEnum.Ok){
				  progressButtons.getButton(OkEnum.Ok).setEnabled(false);
				  progressLabel.setText("Starting upload...");
				  //progressBar.setMin
				  progressBar.setProgress(0);
				  progressBar.setMaxProgress(0);
				  fileProgressTable.clear();
				  progressWindow.show();
				  uploadStatusRpc.getUploadId(new AsyncCallback<String>(){

					@Override
					public void onFailure(Throwable caught) {
						progressLabel.setText("Could not contact server.");
						progressButtons.getButton(OkEnum.Ok).setEnabled(true);
					}

					@Override
					public void onSuccess(final String result) {
						form.setAction(GWT.getModuleBaseURL()+"rpcservices/DocumentUpload?uploadId=" + result + "&referenceId=" + referenceId);
						form.submit();
						
						progressLabel.setText("Uploading...");
						Timer progressTimer = new Timer(){
							private boolean maxSet = false;
							@Override
							public void run() {
								uploadStatusRpc.getUploadStatus(result, new AsyncCallback<UploadStatus>(){

									@Override
									public void onFailure(Throwable caught) {
										progressLabel.setText("Unable to query upload status.");
										progressButtons.getButton(OkEnum.Ok).setEnabled(true);
										refreshDocuments();
										cancel();
									}
									
									private int currentFile = 0;
									
									@Override
									public void onSuccess(UploadStatus result) {
										if(!maxSet && result.getTotalSize() != null && result.getTotalSize() != 0){
											progressBar.setMaxProgress(((double)result.getTotalSize())/1024);
											maxSet = true;
										}
										
										//Individual file status
										fileProgressTable.clear();
										currentFile = 0;
										for(FileStatus fs: result.getFileStatusList()){
											addFileProgress(fs);
											currentFile++;
										}
										
										if(result.getProgress() != null){
											progressBar.setProgress(((double)result.getProgress())/1024);
										}
										
										if(result.getStatus() == UploadTransferStatus.UPLOAD_FINISHED){
											progressLabel.setText("Processing...");
											progressBar.setProgress(progressBar.getMaxProgress());
										}
										else if(result.getStatus() == UploadTransferStatus.COMMIT_FINISHED){
											progressLabel.setText("File upload successful!");
											progressBar.setProgress(progressBar.getMaxProgress());
											progressButtons.getButton(OkEnum.Ok).setEnabled(true);
											resetUploadFormPanel();
											
											cancel();
											refreshDocuments();
											
										}
										else if(result.getStatus() == UploadTransferStatus.ERROR){
											progressLabel.setText("Error uploading!");
											progressButtons.getButton(OkEnum.Ok).setEnabled(true);
											refreshDocuments();
											cancel();
										}
										
									}
									
									NumberFormat nf = NumberFormat.getFormat("#.##");
									
									private void addFileProgress(FileStatus fs) {
										
										HTML fileNameLabel;
										//Name
										if(fs.getStatus() == FileTransferStatus.COMMIT_FINISHED){
											fileNameLabel = new HTML("<a href=\"" + GWT.getModuleBaseURL()+"rpcservices/DocumentUpload?docId=" + fs.getDocId() + "\"><b>" + fs.getFileName() + "</b></a>");
										}
										else{
											fileNameLabel = new HTML("<b>" + fs.getFileName() + "</b>");
										}
										
										fileProgressTable.setWidget(currentFile, 0, fileNameLabel);
										
										//Progress
										Long curProgress = (fs.getProgress())/1024;
										String curProgressString = "";
										if(curProgress < 1024){
											curProgressString = nf.format(curProgress) + "kb";
										}
										else{
											curProgressString = nf.format(curProgress/1024) + "mb";
										}
										fileProgressTable.setWidget(currentFile, 1,  new KSLabel(curProgressString));
										
										//Status
										String statusString = "";
										switch(fs.getStatus()){
											case ERROR:
												statusString = "Error!";
												break;
											case COMMIT_FINISHED:
												statusString = "Finished";
												break;
											case FILE_ERROR:
												//Not used
												break;
											case IN_PROGRESS:
												statusString = "Uploading...";
												break;
											case UPLOAD_FINISHED:
												statusString = "Processing...";
												break;
										}
										fileProgressTable.setWidget(currentFile, 2, new KSLabel(statusString));
										
									}


								});
							}
						};
						progressTimer.scheduleRepeating(POLL_INTERVAL);
					}
				  });
			  }
		}
	});
	
	public DocumentTool(Enum<?> viewEnum, String viewName) {
		super(viewEnum, viewName);
	}

	@Override
	protected Widget createWidget() {
		buttonPanel.setButtonText(OkEnum.Ok, "Upload");
		
		uploadList.add(new DocumentForm());		
		uploadList.add(addMore);
		addMore.addClickHandler(new ClickHandler(){

			@Override
			public void onClick(ClickEvent event) {
				uploadList.insert(new DocumentForm(), uploadList.getWidgetIndex(addMore));
				
			}
		});
		form.setWidget(uploadList);		
		form.setMethod(FormPanel.METHOD_POST);
		form.setEncoding(FormPanel.ENCODING_MULTIPART);
		
		buttonPanel.setContent(form);
		layout.add(documentList);
		layout.add(buttonPanel);
		
		
		progressPanel.add(progressLabel);
		progressPanel.add(progressBar);
		progressPanel.add(fileProgressTable);
		progressBar.setWidth("400px");
		progressBar.setTextFormatter(new TextFormatter(){

			@Override
			protected String getText(ProgressBar bar, double curProgress) {
				String result;
				NumberFormat nf = NumberFormat.getFormat("#.##");
				if(curProgress == bar.getMaxProgress()){
					result = "Total Uploaded: " + nf.format(curProgress) + "kb";
				}
				else if(curProgress == 0 || bar.getMaxProgress() == 0){
					result = "";
				}
				else{
					String curProgressString;
					String maxProgressString;
					
					if(curProgress < 1024){
						curProgressString = nf.format(curProgress) + "kb";
					}
					else{
						curProgressString = nf.format(curProgress/1024) + "mb";
					}
					
					if(bar.getMaxProgress() < 1024){
						maxProgressString = nf.format(bar.getMaxProgress()) + "kb";
					}
					else{
						maxProgressString = nf.format((bar.getMaxProgress())/1024) + "mb";
					}
					result = curProgressString + " out of " + maxProgressString;
				}
				return result;
			}
		});
		progressBar.setHeight("30px");
		progressPanel.add(progressButtons);
		progressPanel.setWidth("500px");
		progressWindow.setWidget(progressPanel);

		
		return layout;
	}
	
	private void resetUploadFormPanel() {
		uploadList.clear();
		uploadList.add(new DocumentForm());
		uploadList.add(addMore);
	}
	
	private class DocumentForm extends Composite{
		private KSLabel file = new KSLabel("File");
		private KSLabel type = new KSLabel("Type");
		private KSLabel description = new KSLabel("Description");
		private FileUpload upload = new FileUpload();
		private KSTextArea documentDescription = new KSTextArea();
		private FlexTable tableLayout = new FlexTable();
		
		public DocumentForm(){
			tableLayout.setWidget(0, 0, file);
			tableLayout.setWidget(0, 1, upload);
			upload.setName("uploadFile");
			tableLayout.setWidget(1, 0, type);
			tableLayout.setWidget(1, 1, new KSLabel("Type Widget goes here"));
			tableLayout.setWidget(2, 0, description);
			tableLayout.setWidget(2, 1, documentDescription);
			documentDescription.setName("documentDescription");
			this.initWidget(tableLayout);
		}
	}

	public String getReferenceId() {
		return referenceId;
	}

	public void setReferenceId(String referenceId) {
		this.referenceId = referenceId;
	}

	public String getReferenceTypeKey() {
		return referenceTypeKey;
	}

	public void setReferenceTypeKey(String referenceTypeKey) {
		this.referenceTypeKey = referenceTypeKey;
	}

	public String getReferenceType() {
		return referenceType;
	}

	public void setReferenceType(String referenceType) {
		this.referenceType = referenceType;
	}

	public String getReferenceState() {
		return referenceState;
	}

	public void setReferenceState(String referenceState) {
		this.referenceState = referenceState;
	}
	
    @Override
    public void setVisible(boolean visible) {
        super.setVisible(visible);
		if(referenceId != null && !(referenceId.isEmpty())){
			buttonPanel.getButton(OkEnum.Ok).setEnabled(true);
		}
		else{
			buttonPanel.getButton(OkEnum.Ok).setEnabled(false);
		}
        refreshDocuments();
    }
	
	private void refreshDocuments(){
		documentList.clear();
        if(referenceId != null && !(referenceId.isEmpty())){
        	documentList.add(loadingDocuments); 
	        try {
	        	//FIXME: change to real doc relation stuff later
	        	documentRelationRpc.getRefDocIdsForRef(referenceId, new AsyncCallback<List<RefDocRelationInfoMock>>(){
	
					@Override
					public void onFailure(Throwable caught) {
						caught.printStackTrace();
						documentList.remove(loadingDocuments);
						
					}
	
					@Override
					public void onSuccess(List<RefDocRelationInfoMock> result) {
						if(result != null && !(result.isEmpty())){	
							for(RefDocRelationInfoMock info: result){
								documentList.add(new Document(info));
							}
						}
						documentList.remove(loadingDocuments);
					}
				});
			} catch (Exception e) {
				e.printStackTrace();
			}
        }
	}
	
	private class Document extends Composite{
        
        private SimplePanel content = new SimplePanel();
        private VerticalFlowPanel viewLayout = new VerticalFlowPanel();
        private HorizontalBlockFlowPanel header = new HorizontalBlockFlowPanel();
        private VerticalFlowPanel headerTextContainer = new VerticalFlowPanel();
        private HorizontalBlockFlowPanel editActions = new HorizontalBlockFlowPanel();
        
        KSImage delete = Theme.INSTANCE.getCommonImages().getDeleteCommentIcon();
        
        private HTML name = new HTML("No file exists");
        private HTML documentText = new HTML();
        private KSLabel fileType = new KSLabel();
        
        private RefDocRelationInfoMock info;
        
        public Document(RefDocRelationInfoMock info){
            this.info = info;

            
            delete.addClickHandler(new ClickHandler(){
                @Override
                public void onClick(ClickEvent event) {
                	 try {
                		 //TODO this will fail if the document does not exist BUT the relation does, needs a check for existance
                		 //before delete
             			documentRelationRpc.deleteRefDocRelation(Document.this.info.getId(), new AsyncCallback<StatusInfo>(){

							@Override
							public void onFailure(Throwable caught) {
								//FIXME dont know what to do here
								refreshDocuments();
							}

							@Override
							public void onSuccess(StatusInfo result) {
		             			try {
									documentServiceAsync.deleteDocument(Document.this.info.getDocumentId(), new AsyncCallback<StatusInfo>(){

										@Override
										public void onFailure(Throwable caught) {
											//FIXME dont know what to do here
											refreshDocuments();
										}

										@Override
										public void onSuccess(StatusInfo result) {
											refreshDocuments();
										}

									});
								} catch (Exception e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
								
							}
						});
             		} catch (Exception e) {
             			e.printStackTrace();
             		}
                	
                	
                }
            });
            
            setupDefaultStyles();
            headerTextContainer.add(name);
            headerTextContainer.add(fileType);
            editActions.add(delete);
            header.add(headerTextContainer);
            header.add(editActions);
            setupViewLayout();

            this.initWidget(content);
        }
        
        private void setupDefaultStyles(){
        	content.addStyleName(KSStyles.KS_COMMENT_CONTAINER);
        	header.addStyleName(KSStyles.KS_COMMENT_HEADER);
        	headerTextContainer.addStyleName(KSStyles.KS_COMMENT_HEADER_LEFT);
        	delete.addStyleName(KSStyles.KS_COMMENT_IMAGE_BUTTON);
        	editActions.addStyleName(KSStyles.KS_COMMENT_IMAGE_BUTTON_PANEL);
        	documentText.addStyleName(KSStyles.KS_COMMENT_TEXT);
        	fileType.addStyleName(KSStyles.KS_COMMENT_DATE_CREATED);
        }
        
        private void setupViewLayout(){
            
            viewLayout.clear();
            viewLayout.add(header);
            viewLayout.add(documentText); 
            
            if(info.getTitle() != null){
            	//name.setText(info.getTitle());
            	//TODO maybe not open up a new window for certain file types not expected to be viewed in browser
            	name.setHTML("<a href=\"" + GWT.getModuleBaseURL()+"rpcservices/DocumentUpload?docId=" + info.getDocumentId() + "\" target=\"_blank\"><b>" + info.getTitle() + "</b></a>");
/*            	name.addClickHandler(new ClickHandler(){

					@Override
					public void onClick(ClickEvent event) {
						
						
						Window.open(GWT.getModuleBaseURL()+"rpcservices/DocumentUpload?docId=" + info.getDocumentId(), info.getTitle(), "resizable=yes,scrollbars=yes,menubar=no,location=yes,status=yes");
						
					}
				});*/
            }
            
            if(info.getDesc() != null){
            	documentText.setHTML("<b>" + "Description: " + "</b>" + info.getDesc().getPlain());
            }
            
            content.setWidget(viewLayout);
            
        }
        
        public void showEditActions(boolean show){
        	editActions.setVisible(show);
        }
        
    }
	
	
    @Override
    public void collectHistory(HistoryStackFrame frame) {
        // do nothing
    }

    @Override
    public void onHistoryEvent(HistoryStackFrame frame) {
        // do nothing
    }

	@Override
	public KSImage getImage() {
		return Theme.INSTANCE.getCommonImages().getDocumentIcon();
	}
}
