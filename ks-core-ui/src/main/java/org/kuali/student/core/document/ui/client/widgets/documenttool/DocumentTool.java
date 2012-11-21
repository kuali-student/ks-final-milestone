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

package org.kuali.student.core.document.ui.client.widgets.documenttool;

import java.util.List;
import java.util.Map;

import org.kuali.student.common.ui.client.application.Application;
import org.kuali.student.common.ui.client.application.KSAsyncCallback;
import org.kuali.student.common.ui.client.configurable.mvc.DelayedToolView;
import org.kuali.student.common.ui.client.configurable.mvc.FieldDescriptor;
import org.kuali.student.common.ui.client.configurable.mvc.HasReferenceId;
import org.kuali.student.common.ui.client.configurable.mvc.SectionTitle;
import org.kuali.student.common.ui.client.configurable.mvc.binding.ModelWidgetBinding;
import org.kuali.student.common.ui.client.configurable.mvc.multiplicity.MultiplicityConfiguration;
import org.kuali.student.common.ui.client.configurable.mvc.multiplicity.MultiplicityFieldConfiguration;
import org.kuali.student.common.ui.client.configurable.mvc.multiplicity.MultiplicityFieldWidgetInitializer;
import org.kuali.student.common.ui.client.configurable.mvc.multiplicity.SwapCompositeCondition;
import org.kuali.student.common.ui.client.configurable.mvc.multiplicity.SwapCompositeConditionFieldConfig;
import org.kuali.student.common.ui.client.configurable.mvc.sections.InfoMessage;
import org.kuali.student.common.ui.client.configurable.mvc.sections.MultiplicitySection;
import org.kuali.student.common.ui.client.configurable.mvc.views.SectionView;
import org.kuali.student.common.ui.client.configurable.mvc.views.VerticalSectionView;
import org.kuali.student.common.ui.client.dto.FileStatus;
import org.kuali.student.common.ui.client.dto.FileStatus.FileTransferStatus;
import org.kuali.student.common.ui.client.dto.UploadStatus;
import org.kuali.student.common.ui.client.dto.UploadStatus.UploadTransferStatus;
import org.kuali.student.common.ui.client.mvc.Callback;
import org.kuali.student.common.ui.client.mvc.DataModel;
import org.kuali.student.common.ui.client.mvc.DataModelDefinition;
import org.kuali.student.common.ui.client.theme.Theme;
import org.kuali.student.common.ui.client.widgets.KSButtonAbstract.ButtonStyle;
import org.kuali.student.common.ui.client.widgets.KSLabel;
import org.kuali.student.common.ui.client.widgets.KSLightBox;
import org.kuali.student.common.ui.client.widgets.KSTextArea;
import org.kuali.student.common.ui.client.widgets.buttongroups.ButtonEnumerations.OkEnum;
import org.kuali.student.common.ui.client.widgets.buttongroups.OkGroup;
import org.kuali.student.common.ui.client.widgets.field.layout.element.MessageKeyInfo;
import org.kuali.student.common.ui.client.widgets.layout.VerticalFlowPanel;
import org.kuali.student.core.document.ui.client.service.DocumentRpcService;
import org.kuali.student.core.document.ui.client.service.DocumentRpcServiceAsync;
import org.kuali.student.core.document.ui.client.service.UploadStatusRpcService;
import org.kuali.student.core.document.ui.client.service.UploadStatusRpcServiceAsync;
import org.kuali.student.r1.common.assembly.data.ConstraintMetadata;
import org.kuali.student.r1.common.assembly.data.Metadata;
import org.kuali.student.r1.common.assembly.data.QueryPath;

import com.google.gwt.core.client.GWT;
import com.google.gwt.i18n.client.NumberFormat;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FileUpload;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.FormPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.widgetideas.client.ProgressBar;
import com.google.gwt.widgetideas.client.ProgressBar.TextFormatter;
import org.kuali.student.r2.core.class1.type.dto.TypeInfo;
import org.kuali.student.r2.core.document.dto.RefDocRelationInfo;

/*
 * Messages hard-coded throughout since this can't access KSMG-MESSAGE
 * This is not actually true. Added getMessage method for more flexible documentTool Instructions.
 * However all the other original hardcoded messages still exist here.
 */
public class DocumentTool extends DelayedToolView implements HasReferenceId{
    private static final String MSG_GROUP = "common";
    private String referenceId;
    private String referenceTypeKey;
    private String referenceType;
    private String referenceState;
    private String refObjectTypeKey;
    private final String refDocRelationTypeKey = "kuali.org.DocRelation.allObjectTypes";
    
    private static final int POLL_INTERVAL = 2000;

    protected DocumentRpcServiceAsync documentServiceAsync = GWT.create(DocumentRpcService.class);
    private VerticalFlowPanel layout = new VerticalFlowPanel();
    private VerticalFlowPanel documentList = new VerticalFlowPanel();
    private VerticalFlowPanel uploadList = new VerticalFlowPanel();
//    private KSButton addMore = new KSButton("Add Another");
    private KSLabel loadingDocuments = new KSLabel("Loading Documents...");
    private FormPanel form = new FormPanel();
    private Callback<String> deleteCallback = new Callback<String>(){

        @Override
        public void exec(String result) {
            refreshDocuments();
        }

    };

    private KSLightBox progressWindow = new KSLightBox();
    private VerticalFlowPanel progressPanel = new VerticalFlowPanel();
    private KSLabel progressLabel = new KSLabel();
    private ProgressBar progressBar = new ProgressBar();
    private FlexTable fileProgressTable = new FlexTable();
    private InfoMessage saveWarning = new InfoMessage("The document must be saved before Document files can be uploaded.", true);
    private Composite showAllLink;

    private DataModelDefinition modelDefinition;

    private OkGroup progressButtons = new OkGroup(new Callback<OkEnum>(){
        @Override
        public void exec(OkEnum result) {
              if(result == OkEnum.Ok){
                  progressWindow.hide();
              }
        }
    });
    private UploadStatusRpcServiceAsync uploadStatusRpc = GWT.create(UploadStatusRpcService.class);

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
                  uploadStatusRpc.getUploadId(new KSAsyncCallback<String>(){

                    @Override
                    public void handleFailure(Throwable caught) {
                        progressLabel.setText("Could not contact server.");
                        progressButtons.getButton(OkEnum.Ok).setEnabled(true);
                    }

                    @Override
                    public void onSuccess(final String result) {
                        form.setAction(GWT.getModuleBaseURL()+"rpcservices/DocumentUpload?uploadId=" + result + "&referenceId=" + referenceId +"&refObjectTypeKey=" + refObjectTypeKey + "&refDocRelationTypeKey=" + refDocRelationTypeKey);
                        form.submit();

                        progressLabel.setText("Uploading...");
                        Timer progressTimer = new Timer(){
                            private boolean maxSet = false;
                            @Override
                            public void run() {
                                uploadStatusRpc.getUploadStatus(result, new KSAsyncCallback<UploadStatus>(){

                                    @Override
                                    public void handleFailure(Throwable caught) {
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

    public DocumentTool(String refObjectTypeKey, Enum<?> viewEnum, String viewName) {
        super(viewEnum, viewName);
        this.refObjectTypeKey = refObjectTypeKey;
    }

    protected void isAuthorizedUploadDocuments() {
        documentServiceAsync.isAuthorizedUploadDocuments(referenceId, referenceTypeKey, new KSAsyncCallback<Boolean>() {

            @Override
            public void handleFailure(Throwable caught) {
                GWT.log("Error checking permission for adding comments: ", caught);
                throw new RuntimeException("Error checking Permissions: ", caught);
            }

            @Override
            public void onSuccess(Boolean result) {
                GWT.log("User is " + ((result) ? "" : "not ") + "authorized to add comment.", null);
                if(referenceId != null && !(referenceId.isEmpty())){
                    //buttonPanel.getButton(OkEnum.Ok).setEnabled(true);
                    processUi(result);
                }
                else{
                    saveWarning.setVisible(true);
                    buttonPanel.setVisible(false);
                    documentList.setVisible(false);
                    //buttonPanel.getButton(OkEnum.Ok).setEnabled(false);
                }
            }

        });
    }

    protected void processUi(Boolean result){
       saveWarning.setVisible(false);
       buttonPanel.setVisible(result);
       documentList.setVisible(true);
       refreshDocuments();
    }

    private Metadata getMetaData(String fieldKey) {
        return modelDefinition.getMetadata(QueryPath.concat(fieldKey));
    }

    protected String getLabel(String labelKey) {
        // Could have used gwt-message.xml to define labels but this ui is not using the
        // UI label framework.
        return labelKey;
    }

    protected MultiplicityConfiguration setupMultiplicityConfig(  MultiplicityConfiguration.MultiplicityType multiplicityType
                                                              , MultiplicityConfiguration.StyleType styleType
                                                              , String path, String addItemlabelMessageKey
                                                              , String itemLabelMessageKey
                                                              , Map<SwapCompositeCondition, List<SwapCompositeConditionFieldConfig>> swappableFieldsDefinition
                                                              , List<String> deletionParentKeys){
        QueryPath parentPath = QueryPath.concat(path);
        MultiplicityConfiguration config = GWT.create(MultiplicityConfiguration.class);
        config.init(multiplicityType, styleType, getMetaData(parentPath.toString()));
        FieldDescriptor parentFd = buildMuliplicityParentFieldDescriptor(path, null, null);
        
        MultiplicityFieldConfiguration fc = buildMultiplicityFD("fieldKey", "", parentPath.toString());
        MultiplicityFieldWidgetInitializer fieldWidgetInitializer = new MultiplicityFieldWidgetInitializer() {
            
            @Override
            public ModelWidgetBinding getModelWidgetBindingInstance() {
                
                return new ModelWidgetBinding<DocumentForm>() {
                    
                    public void setModelValue(DocumentForm widget, DataModel model, String path) {

                    }
                    public void setWidgetValue(DocumentForm widget, DataModel model, String path){
                        

                    }
                };
            }
            
            @Override
            public Widget getNewWidget() {
                
                return new DocumentForm();
            }
        };
        
        ConstraintMetadata min1 = new ConstraintMetadata();
        Metadata metadata = new Metadata();
        
        config.setAddItemLabel(getLabel(addItemlabelMessageKey));
        config.setItemLabel(getLabel(itemLabelMessageKey));
        config.setUpdateable(true);
        config.setParent(parentFd);
        
        fc.setFieldWidgetInitializer(fieldWidgetInitializer);

        // make the initial number of item equal to 1
        min1.setMinOccurs(1);
        metadata.getConstraints().add(min1);
        
        config.setMetaData(metadata);
        config.addFieldConfiguration(fc);
        config.nextLine();
        
        return config;
    }

    private FieldDescriptor buildMuliplicityParentFieldDescriptor(String fieldKey, String messageKey, String parentPath) {
        QueryPath path = QueryPath.concat(parentPath, fieldKey);
        Metadata meta = modelDefinition.getMetadata(path);
        FieldDescriptor fd = new FieldDescriptor(path.toString(), generateMessageInfo(messageKey), meta);
        return fd;
    }

    private MultiplicityFieldConfiguration buildMultiplicityFD(
            String fieldKey, String labelKey, String parentPath) {

        QueryPath fieldPath = QueryPath.concat(parentPath, QueryPath.getWildCard(), fieldKey);
        Metadata meta = modelDefinition.getMetadata(fieldPath);

        MultiplicityFieldConfiguration fd = new MultiplicityFieldConfiguration(
                fieldPath.toString(), generateMessageInfo(labelKey), meta, null);


        return fd;

    }

    protected MessageKeyInfo generateMessageInfo(String labelKey) {
        return new MessageKeyInfo("groupName", "type", "state", labelKey);
    }
    
    public String getMessage(String courseMessageKey) {
        String msg = Application.getApplicationContext().getMessage(MSG_GROUP, courseMessageKey);
        if (msg == null) {
            msg = courseMessageKey;
        }
        return msg;
    }

    protected Widget createUploadForm() {
        final VerticalSectionView verticalSectionView= new VerticalSectionView(this.getViewEnum(), this.getName(), this.getController().getDefaultModelId());
        MultiplicitySection ms= new MultiplicitySection(setupMultiplicityConfig(  MultiplicityConfiguration.MultiplicityType.GROUP
                                                                                , MultiplicityConfiguration.StyleType.TOP_LEVEL_GROUP
                                                                                , "path", "Include More Files", "File", null, null));
        
        verticalSectionView.addSection(ms);

        try {
            
            documentServiceAsync.getDocumentTypes(new KSAsyncCallback<List<TypeInfo>>(){
                
                @Override
                public void onSuccess(List<TypeInfo> supportedDocumentTypeInfoResults){
                    String acceptableDocumentTypesString;
                    String maxFileSizeString= "10000000";   //from ks-document-dictionary-context.xml, inaccessible here (in a different service-loaded module?)
                    int maxFileSizeInt= Integer.parseInt(maxFileSizeString)/1048576;
                    
                    if(supportedDocumentTypeInfoResults.isEmpty()){
                        
                        acceptableDocumentTypesString= "Sorry, no documents supported for upload now!";       
                        
                    }else{
                        
                        acceptableDocumentTypesString= "." + supportedDocumentTypeInfoResults.get(0).getName();
                        for(int i= 1; i < supportedDocumentTypeInfoResults.size(); i++){
                            
                            acceptableDocumentTypesString+= ", ." + supportedDocumentTypeInfoResults.get(i).getName();
                        }                 
                    }
                    
                    if(verticalSectionView instanceof SectionView){
                        
                        // Get University Specific Instructions, if available.
                        String documentToolInstructions = getMessage("documentToolInstructions");
                        
                        // Set default if not.
                        if (documentToolInstructions.equals("documentToolInstructions")){
                            documentToolInstructions = "Multiple supporting documents associated with this course can be uploaded.<br>";
                        }
                        ((SectionView) verticalSectionView).setInstructions( documentToolInstructions   
                                                                            + "&nbsp;&nbsp;&nbsp;<b>Acceptable file types:</b>&nbsp;&nbsp;" + "<i>" + acceptableDocumentTypesString + "</i><br>"
                                                                            + "&nbsp;&nbsp;&nbsp;<b>Max file size:</b>&nbsp;&nbsp;" + "<i>~" + maxFileSizeInt + "MB </i>");
                    }
               }
            }); 
            
        } catch (Exception e) {

            throw new RuntimeException(e);
        }
        
        verticalSectionView.getLayout().setWidth("700");

        return verticalSectionView;
    }

    @Override
    protected Widget createWidget() {
        //This section title code does not seem consistent with other sections (i.e: of CourseProposal)
        //  section title is now instead displayed based on verticalSectionView in createUploadForm()
        /*SectionTitle viewTitle = SectionTitle.generateH2Title(getTitle());
          
        viewTitle.addStyleName("ks-layout-header");
        layout.add(viewTitle);*/
        
        layout.add(saveWarning);
        saveWarning.setVisible(false);
        buttonPanel.setButtonText(OkEnum.Ok, "Upload");
        buttonPanel.getButton(OkEnum.Ok).setStyleName(ButtonStyle.SECONDARY.getStyle());

        uploadList.add(createUploadForm());
        form.setWidget(uploadList);
        form.setMethod(FormPanel.METHOD_POST);
        form.setEncoding(FormPanel.ENCODING_MULTIPART);

        buttonPanel.setContent(form);
        if (showAllLink != null) {
            layout.add(showAllLink);
        }
        layout.add(buttonPanel);
        layout.add(documentList);
        documentList.setVisible(false);
        buttonPanel.setVisible(false);

        SectionTitle sectionTitle = SectionTitle.generateH2Title("Upload Status");
        progressWindow.setNonCaptionHeader(sectionTitle);
        progressPanel.add(progressLabel);
        progressPanel.add(progressBar);
        progressPanel.add(fileProgressTable);
        progressBar.setWidth("400px");
        progressBar.setTextFormatter(new TextFormatter() {

            @Override
            protected String getText(ProgressBar bar, double curProgress) {
                String result;
                NumberFormat nf = NumberFormat.getFormat("#.##");
                if (curProgress == bar.getMaxProgress()) {
                    result = "Total Uploaded: " + nf.format(curProgress) + "kb";
                } else if (curProgress == 0 || bar.getMaxProgress() == 0) {
                    result = "";
                } else {
                    String curProgressString;
                    String maxProgressString;

                    if (curProgress < 1024) {
                        curProgressString = nf.format(curProgress) + "kb";
                    } else {
                        curProgressString = nf.format(curProgress / 1024) + "mb";
                    }

                    if (bar.getMaxProgress() < 1024) {
                        maxProgressString = nf.format(bar.getMaxProgress()) + "kb";
                    } else {
                        maxProgressString = nf.format((bar.getMaxProgress()) / 1024) + "mb";
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
        progressWindow.setSize(520, 270);

        return layout;
    }

    private void resetUploadFormPanel() {
        uploadList.clear();
        uploadList.add(createUploadForm());
    }

    private static class DocumentForm extends Composite{
        private KSLabel file = new KSLabel("* File Location");
        private KSLabel description = new KSLabel("Description");
        private FileUpload upload = new FileUpload();
        private KSTextArea documentDescription = new KSTextArea();
        private FlexTable tableLayout = new FlexTable();

        /*
         * A "File Name n" widget box for choosing a file to upload, and adding a description to it.
         */
        public DocumentForm(){
            tableLayout.setWidget(0, 0, file);
            tableLayout.setWidget(0, 1, upload);
            upload.setName("uploadFile");
            tableLayout.setWidget(1, 0, description);
            tableLayout.setWidget(1, 1, documentDescription);
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
        if (showAllLink == null){
            isAuthorizedUploadDocuments();
        }
    }

    private void refreshDocuments(){
        documentList.clear();
        if(referenceId != null && !(referenceId.isEmpty())){
            documentList.add(loadingDocuments);
            try {
                documentServiceAsync.getRefDocIdsForRef(refObjectTypeKey, referenceId, new KSAsyncCallback<List<RefDocRelationInfo>>(){

                    @Override
                    public void handleFailure(Throwable caught) {
                        GWT.log("getRefDocIdsForRef failed", caught);
                        documentList.remove(loadingDocuments);

                    }

                    @Override
                    public void onSuccess(List<RefDocRelationInfo> result) {
                        documentList.clear();
                        if(result != null && !(result.isEmpty())){
                            documentList.add(new DocumentList(refObjectTypeKey, result, deleteCallback));
                        }
                        documentList.remove(loadingDocuments);
                    }
                });
            } catch (Exception e) {
                GWT.log("getRefDocIdsForRef failed", e);
            }
        }
    }

    @Override
    public Image getImage() {
        return Theme.INSTANCE.getCommonImages().getDocumentIcon();
    }

    public DataModelDefinition getModelDefinition() {
        return modelDefinition;
    }

    public void setModelDefinition(DataModelDefinition modelDefinition) {
        this.modelDefinition = modelDefinition;
    }

    public void setShowAllLink(Composite showAllLink) {
        this.showAllLink = showAllLink;
    }

    public void showShowAllLink(boolean show) {
        this.showAllLink.setVisible(show);
        if (show) {
            saveWarning.setVisible(false);
            buttonPanel.setVisible(false);
            documentList.setVisible(false);
        } else {
            isAuthorizedUploadDocuments();
        }
    }

    @Override
    public void showExport(boolean show) {
    // TODO Auto-generated method stub

    }

}
