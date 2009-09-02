package org.kuali.student.common.ui.client.widgets.documenttool;

import java.util.ArrayList;
import java.util.List;

import org.kuali.student.common.ui.client.configurable.mvc.HasReferenceId;
import org.kuali.student.common.ui.client.configurable.mvc.ToolView;
import org.kuali.student.common.ui.client.images.KSImages;
import org.kuali.student.common.ui.client.mvc.Callback;
import org.kuali.student.common.ui.client.widgets.KSButton;
import org.kuali.student.common.ui.client.widgets.KSLabel;
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

import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FileUpload;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Hyperlink;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;

public class DocumentTool extends ToolView implements HasReferenceId{

	private String referenceId;
	private String referenceTypeKey;
	private String referenceType;
	private String referenceState;
	
	private KSLabel file = new KSLabel("File");
	private KSLabel type = new KSLabel("Type");
	private KSLabel description = new KSLabel("Description");
	private KSTextBox fileName = new KSTextBox();
	private KSTextArea documentDescription = new KSTextArea();
	private HorizontalBlockFlowPanel browsePanel = new HorizontalBlockFlowPanel();
	private VerticalFlowPanel layout = new VerticalFlowPanel();
    private VerticalFlowPanel documentList = new VerticalFlowPanel();
    private SimplePanel createPanel = new SimplePanel();
    private boolean showingEditButtons = true;
    private List<Document> documents = new ArrayList<Document>();
    private KSLabel loadingDocuments = new KSLabel("Loading Documents");
    private FileUpload upload = new FileUpload();
    
    private FlexTable tableLayout = new FlexTable();
	
	private KSButton browse = new KSButton("Browse", new ClickHandler(){

		@Override
		public void onClick(ClickEvent event) {
			//upload.getElement().dispatchEvent(new NativeEvent());
			
		}
	});
	
	private OkGroup buttonPanel = new OkGroup(new Callback<OkEnum>(){

		@Override
		public void exec(OkEnum result) {
			  if(result == OkEnum.Ok){
				  //Mock Data
				  DocumentInfo doc = new DocumentInfo();
				  doc.setDesc(new RichTextInfo());
				  doc.getDesc().setPlain(documentDescription.getText());
				  doc.setName("File.doc");
				  
				  //call doc service here, maybe have callback to add the relation too?
				  
				  Document document = new Document(doc); 
				  documents.add(document);
				  documentDescription.setText("");
				  documentList.add(document);
			  }
		}
	});
	
	public DocumentTool(Enum<?> viewEnum, String viewName) {
		super(viewEnum, viewName);
	}

	@Override
	protected Widget createWidget() {
		buttonPanel.setButtonText(OkEnum.Ok, "Add");
		
		createPanel.setWidget(createDocumentEditor(buttonPanel, browse));
		layout.add(createPanel);
		layout.add(documentList);

		layout.add(new FileUpload());
		//new FileUpload().
		
		return layout;
	}
	
	public Widget test(){
		return createWidget();
	}
	
	private FlexTable createDocumentEditor(ButtonGroup theButtons, KSButton fileEditButton){
		fileName.setEnabled(false);
		browsePanel.add(fileName);
		browsePanel.add(fileEditButton);
		theButtons.setContent(documentDescription);
		tableLayout.setWidget(0, 0, file);
		tableLayout.setWidget(0, 1, browsePanel);
		tableLayout.setWidget(1, 0, type);
		tableLayout.setWidget(1, 1, new KSLabel("Type Widget goes here"));
		tableLayout.setWidget(2, 0, description);
		tableLayout.setWidget(2, 1, theButtons);
		
		/*if(no type widget remove the row maybe?)
		tableLayout.removeRow(1);*/
		
		return tableLayout;
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
        
        //TODO: call to get documents and create new document list here instead of this for loop
        for(Document d: documents){
        	documentList.add(d);
        }
        
        documentList.remove(loadingDocuments);
        
	}
	
    private void showDocumentsEditActions(boolean show){
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
    	
    }
	
	private class Document extends Composite{
        
        private SimplePanel content = new SimplePanel();
        private VerticalFlowPanel editLayout = new VerticalFlowPanel();
        private VerticalFlowPanel viewLayout = new VerticalFlowPanel();
        private HorizontalBlockFlowPanel header = new HorizontalBlockFlowPanel();
        private VerticalFlowPanel headerTextContainer = new VerticalFlowPanel();
        //private HorizontalBlockFlowPanel footer = new HorizontalBlockFlowPanel();
        private HorizontalBlockFlowPanel editActions = new HorizontalBlockFlowPanel();
        private KSButton replaceFile = new KSButton("Replace File", new ClickHandler(){

			@Override
			public void onClick(ClickEvent event) {
				// TODO Auto-generated method stub
				
			}
		});
        
        Image edit = KSImages.INSTANCE.editComment().createImage();
        Image delete = KSImages.INSTANCE.deleteComment().createImage();
        
        private Hyperlink name = new Hyperlink();
        private HTML documentText = new HTML();
        private KSLabel fileType = new KSLabel();
        
        private KSTextArea editor = new KSTextArea();
        
        private DocumentInfo info;
        
        public Document(DocumentInfo info){
            this.info = info;
            edit.addClickHandler(new ClickHandler(){
                @Override
                public void onClick(ClickEvent event) {
                	editLayout.clear();
                	showDocumentsEditActions(false);
                    content.addStyleName(KSStyles.KS_COMMENT_CONTAINER_IN_USE);
                    documentDescription.setText(Document.this.info.getDesc().getPlain());
                    
                    ConfirmCancelGroup buttonPanel = new ConfirmCancelGroup(new Callback<ConfirmCancelEnum>(){
                        @Override
                        public void exec(ConfirmCancelEnum result) {
                            switch(result){
                                case CONFIRM:
                                	
                                    //Document.this.info.getDesc().setFormatted(DocumentTool.this.documentDescription.getText());
                                    Document.this.info.getDesc().setPlain(DocumentTool.this.documentDescription.getText());
                                    
                                	//TODO commit the changes to the service
                                    
                                    //Clean up
                                    content.removeStyleName(KSStyles.KS_COMMENT_CONTAINER_IN_USE);
                                    documentDescription.setText("");
                                    editLayout.remove(tableLayout);
                                    break;
                                case CANCEL:
                                	
                                	//Clean up
                                	content.removeStyleName(KSStyles.KS_COMMENT_CONTAINER_IN_USE);
                                	documentDescription.setText("");
                                	editLayout.remove(tableLayout);
                                    setupViewLayout();
                                    break;
                            }
                            
                        }
                    });
                    
                    buttonPanel.addStyleName(KSStyles.KS_COMMENT_INLINE_EDIT);
                    
                    //editLayout.add(header);
                    editLayout.add(createDocumentEditor(buttonPanel, replaceFile));
                    content.setWidget(editLayout);
                }
            });
            
            delete.addClickHandler(new ClickHandler(){
                @Override
                public void onClick(ClickEvent event) {
                	//TODO call the delete document here
                	
                	
                	refreshDocuments();
                }
            });
            
            setupDefaultStyles();
            headerTextContainer.add(name);
            headerTextContainer.add(fileType);
            editActions.add(edit);
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
        	edit.addStyleName(KSStyles.KS_COMMENT_IMAGE_BUTTON);
        	delete.addStyleName(KSStyles.KS_COMMENT_IMAGE_BUTTON);
        	editActions.addStyleName(KSStyles.KS_COMMENT_IMAGE_BUTTON_PANEL);
        	name.addStyleName(KSStyles.KS_COMMENT_NAME);
        	documentText.addStyleName(KSStyles.KS_COMMENT_TEXT);
        	fileType.addStyleName(KSStyles.KS_COMMENT_DATE_CREATED);
        	editor.setStyleName(KSStyles.KS_COMMENT_INLINE_EDIT_EDITOR);
        }
        
        private void setupViewLayout(){
            
            viewLayout.clear();
            viewLayout.add(header);
            viewLayout.add(documentText); 
            
            if(info.getFileName() != null){
            	name.setText(info.getName());
            }
            
            if(info.getDesc() != null){
            	documentText.setHTML("<b>" + "Description: " + "</b>" + info.getDesc().getPlain());
            }
            
            content.setWidget(viewLayout);
            showDocumentsEditActions(true);
            
        }
        
        public void showEditActions(boolean show){
        	editActions.setVisible(show);
        }
        
    }
}
