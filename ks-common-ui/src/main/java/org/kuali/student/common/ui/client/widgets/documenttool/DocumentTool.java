package org.kuali.student.common.ui.client.widgets.documenttool;

import java.util.ArrayList;
import java.util.List;

import org.kuali.student.common.ui.client.configurable.mvc.HasReferenceId;
import org.kuali.student.common.ui.client.configurable.mvc.ToolView;
import org.kuali.student.common.ui.client.images.KSImages;
import org.kuali.student.common.ui.client.mvc.Callback;
import org.kuali.student.common.ui.client.service.DocumentRpcService;
import org.kuali.student.common.ui.client.service.DocumentRpcServiceAsync;
import org.kuali.student.common.ui.client.service.UploadStatusRpcService;
import org.kuali.student.common.ui.client.service.UploadStatusRpcServiceAsync;
import org.kuali.student.common.ui.client.dto.UploadStatus;
import org.kuali.student.common.ui.client.dto.UploadStatus.UploadTransferStatus;
import org.kuali.student.common.ui.client.widgets.KSButton;
import org.kuali.student.common.ui.client.widgets.KSLabel;
import org.kuali.student.common.ui.client.widgets.KSLightBox;
import org.kuali.student.common.ui.client.widgets.KSStyles;
import org.kuali.student.common.ui.client.widgets.KSTextArea;
import org.kuali.student.common.ui.client.widgets.buttongroups.ButtonGroup;
import org.kuali.student.common.ui.client.widgets.buttongroups.ConfirmCancelGroup;
import org.kuali.student.common.ui.client.widgets.buttongroups.OkGroup;
import org.kuali.student.common.ui.client.widgets.buttongroups.ButtonEnumerations.ConfirmCancelEnum;
import org.kuali.student.common.ui.client.widgets.buttongroups.ButtonEnumerations.OkEnum;
import org.kuali.student.common.ui.client.widgets.layout.HorizontalBlockFlowPanel;
import org.kuali.student.common.ui.client.widgets.layout.VerticalFlowPanel;
import org.kuali.student.common.ui.client.widgets.KSTextBox;
import org.kuali.student.core.comment.dto.CommentInfo;
import org.kuali.student.core.document.dto.DocumentInfo;
import org.kuali.student.core.dto.RichTextInfo;
import org.kuali.student.core.dto.StatusInfo;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.i18n.client.NumberFormat;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FileUpload;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.FormPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Hyperlink;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.client.ui.FormPanel.SubmitCompleteEvent;
import com.google.gwt.user.client.ui.FormPanel.SubmitCompleteHandler;
import com.google.gwt.widgetideas.client.ProgressBar;
import com.google.gwt.widgetideas.client.ProgressBar.TextFormatter;

public class DocumentTool extends ToolView implements HasReferenceId{

	private String referenceId;
	private String referenceTypeKey;
	private String referenceType;
	private String referenceState;

	private final int POLL_INTERVAL = 50; //FIXME put this back to 2000, low for testing ONLY
	
	private DocumentRpcServiceAsync documentServiceAsync = GWT.create(DocumentRpcService.class);
	private HorizontalBlockFlowPanel browsePanel = new HorizontalBlockFlowPanel();
	private VerticalFlowPanel layout = new VerticalFlowPanel();
    private VerticalFlowPanel documentList = new VerticalFlowPanel();
    private VerticalFlowPanel uploadList = new VerticalFlowPanel();
    private KSButton addMore = new KSButton("Add Another");
    private SimplePanel createPanel = new SimplePanel();
    private boolean showingEditButtons = true;
    private List<Document> documents = new ArrayList<Document>();
    private KSLabel loadingDocuments = new KSLabel("Loading Documents");
    private FormPanel form = new FormPanel();
    
    private KSLightBox progressWindow = new KSLightBox();
    private VerticalFlowPanel progressPanel = new VerticalFlowPanel();
    private KSLabel progressLabel = new KSLabel();
    private ProgressBar progressBar = new ProgressBar();
    private OkGroup progressButtons = new OkGroup(new Callback<OkEnum>(){
		@Override
		public void exec(OkEnum result) {
			  if(result == OkEnum.Ok){
				  progressWindow.hide();
			  }
		}
    });  
    private UploadStatusRpcServiceAsync uploadStatusRpc = GWT.create(UploadStatusRpcService.class);
    
    private List<DocumentInfo> uploadedDocs = new ArrayList<DocumentInfo>();
    private List<String> uploadedDocIds = new ArrayList<String>();
	
	private OkGroup buttonPanel = new OkGroup(new Callback<OkEnum>(){

		@Override
		public void exec(OkEnum result) {
			  if(result == OkEnum.Ok){
				  progressButtons.getButton(OkEnum.Ok).setEnabled(false);
				  progressLabel.setText("Starting upload...");
				  //progressBar.setMin
				  progressBar.setProgress(0);
				  progressBar.setMaxProgress(0);
				  progressWindow.show();
				  uploadStatusRpc.getUploadId(new AsyncCallback<String>(){

					@Override
					public void onFailure(Throwable caught) {
						progressLabel.setText("Could not contact server.");
						progressButtons.getButton(OkEnum.Ok).setEnabled(true);
					}

					@Override
					public void onSuccess(final String result) {
						form.setAction(GWT.getModuleBaseURL()+"rpcservices/DocumentUpload?uploadId=" + result);
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

									@Override
									public void onSuccess(UploadStatus result) {
										if(!maxSet && result.getTotalSize() != null && result.getTotalSize() != 0){
											progressBar.setMaxProgress(((double)result.getTotalSize())/1024);
											maxSet = true;
										}
										
										if(result.getProgress() != null){
											progressBar.setProgress(((double)result.getProgress())/1024);
										}
										
										if(result.getStatus() == UploadTransferStatus.UPLOAD_FINISHED){
											progressLabel.setText("Processing...");
										}
										else if(result.getStatus() == UploadTransferStatus.COMMIT_FINISHED){
											progressLabel.setText("File upload successful!");
											progressButtons.getButton(OkEnum.Ok).setEnabled(true);
											resetUploadFormPanel();
											
											//FIXME This is not the way it will work, this temporary to show doc links
											//FIXME the refresh will query relations for this tool's referenceId and get the docs from there
											//These ids probably will be used to create the doc relations here instead
											uploadedDocIds.addAll(result.getCreatedDocIds());
											refreshDocuments();
											
											cancel();
										}
										else if(result.getStatus() == UploadTransferStatus.ERROR){
											progressLabel.setText("Error uploading!");
											progressButtons.getButton(OkEnum.Ok).setEnabled(true);
											refreshDocuments();
											cancel();
										}
										
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
	
	//TODO: future constructor, need to define a service interface for document relations to be passed in so we can relate
	//documents to a lu, etc, etc and get all document ids for an id 
/*	public DocumentTool(Enum<?> viewEnum, String viewName, String categoryNameKey, DocumentRelationService relationService) {
		super(viewEnum, viewName);
	}*/

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
		progressBar.setWidth("300px");
		progressBar.setTextFormatter(new TextFormatter(){

			@Override
			protected String getText(ProgressBar bar, double curProgress) {
				String result;
				NumberFormat nf = NumberFormat.getFormat("#.##");
				if(curProgress == bar.getMaxProgress()){
					result = "Complete (" + nf.format(curProgress) + "kb)";
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
		progressPanel.setWidth("400px");
		progressWindow.setWidget(progressPanel);
		//refreshDocuments();
		
		return layout;
	}
	
	private void resetUploadFormPanel() {
		uploadList.clear();
		uploadList.add(new DocumentForm());
		uploadList.add(addMore);
	}
	
	//FIXME: remove after done testing
	public Widget test(){
		return createWidget();
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
        refreshDocuments();
    }
	
	private void refreshDocuments(){
		documentList.clear();
        documentList.add(loadingDocuments);     
        if(!uploadedDocIds.isEmpty()){
        
	        //FIXME: call to get doc relations here and then use that to get document meta information which will not
	        //include the actual data in the future, INSTEAD of using this local id list
	        try {
	        	
				documentServiceAsync.getDocumentsByIdList(uploadedDocIds, new AsyncCallback<List<DocumentInfo>>(){
	
					@Override
					public void onFailure(Throwable caught) {
						caught.printStackTrace();
						documentList.remove(loadingDocuments);
					}
	
					@Override
					public void onSuccess(List<DocumentInfo> result) {
						for(DocumentInfo info: result){
							documentList.add(new Document(info));
						}
						documentList.remove(loadingDocuments);
					}
	
				});
			} catch (Exception e) {
				e.printStackTrace();
			}
        
        }
        else{
        	documentList.remove(loadingDocuments);
        }
        
	}
	
/*    private void showDocumentsEditActions(boolean show){
    	if(show){
    		//FIXME do a check to see if the current person can edit for each comment in the list then show the actions, else
    		//dont show them
    		if(!showingEditButtons){
	    		buttonPanel.setVisible(show);
	    		createPanel.setWidget(createDocumentEditor(buttonPanel, browse));
	    		for(Document d: documents){
	        		d.showEditActions(show);
	        	}
	    		showingEditButtons = true;
    		}
    	}
    	else{
    		if(showingEditButtons){
	    		buttonPanel.setVisible(show);
	    		createPanel.remove(tableLayout);
	    		for(Document d: documents){
	        		d.showEditActions(show);
	        	}
	    		showingEditButtons = false;
    		}
    	}
    	
    }*/
	
	private class Document extends Composite{
        
        private SimplePanel content = new SimplePanel();
        private VerticalFlowPanel editLayout = new VerticalFlowPanel();
        private VerticalFlowPanel viewLayout = new VerticalFlowPanel();
        private HorizontalBlockFlowPanel header = new HorizontalBlockFlowPanel();
        private VerticalFlowPanel headerTextContainer = new VerticalFlowPanel();
        //private HorizontalBlockFlowPanel footer = new HorizontalBlockFlowPanel();
        private HorizontalBlockFlowPanel editActions = new HorizontalBlockFlowPanel();
/*        private KSButton replaceFile = new KSButton("Replace File", new ClickHandler(){

			@Override
			public void onClick(ClickEvent event) {
				// TODO Auto-generated method stub
				
			}
		});
		private KSTextArea editor = new KSTextArea();
		*/
        
        Image delete = KSImages.INSTANCE.deleteComment().createImage();
        
        private Hyperlink name = new Hyperlink();
        private HTML documentText = new HTML();
        private KSLabel fileType = new KSLabel();
        
        
        
        private DocumentInfo info;
        
        public Document(DocumentInfo info){
            this.info = info;

            
            delete.addClickHandler(new ClickHandler(){
                @Override
                public void onClick(ClickEvent event) {
                	 try {
             			documentServiceAsync.deleteDocument(Document.this.info.getId(), new AsyncCallback<StatusInfo>(){

             				@Override
             				public void onFailure(Throwable caught) {
             					//FIXME dont know what to do here
             					
             				}

							@Override
							public void onSuccess(StatusInfo result) {
								//FIXME dont know what to do here
								
							}

             			});
             		} catch (Exception e) {
             			e.printStackTrace();
             		}
                	
                	refreshDocuments();
                }
            });
            
            setupDefaultStyles();
            headerTextContainer.add(name);
            headerTextContainer.add(fileType);
            //editActions.add(edit);
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
        	//edit.addStyleName(KSStyles.KS_COMMENT_IMAGE_BUTTON);
        	delete.addStyleName(KSStyles.KS_COMMENT_IMAGE_BUTTON);
        	editActions.addStyleName(KSStyles.KS_COMMENT_IMAGE_BUTTON_PANEL);
        	name.addStyleName(KSStyles.KS_COMMENT_NAME);
        	documentText.addStyleName(KSStyles.KS_COMMENT_TEXT);
        	fileType.addStyleName(KSStyles.KS_COMMENT_DATE_CREATED);
        	//editor.setStyleName(KSStyles.KS_COMMENT_INLINE_EDIT_EDITOR);
        }
        
        private void setupViewLayout(){
            
            viewLayout.clear();
            viewLayout.add(header);
            viewLayout.add(documentText); 
            
            if(info.getFileName() != null){
            	name.setText(info.getFileName());
            	name.addClickHandler(new ClickHandler(){

					@Override
					public void onClick(ClickEvent event) {
						Window.open(GWT.getModuleBaseURL()+"rpcservices/DocumentUpload?docId=" + info.getId(), info.getName(), "resizable=yes,scrollbars=yes,menubar=no,location=yes,status=yes");
						
					}
				});
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
}
