package org.kuali.student.lum.lu.ui.course.client.widgets;

import java.util.List;

import org.kuali.student.common.ui.client.mvc.dto.ModelDTO;
import org.kuali.student.common.ui.client.widgets.KSButton;
import org.kuali.student.common.ui.client.widgets.KSCheckBox;
import org.kuali.student.common.ui.client.widgets.KSDropDown;
import org.kuali.student.common.ui.client.widgets.KSLabel;
import org.kuali.student.common.ui.client.widgets.KSLightBox;
import org.kuali.student.common.ui.client.widgets.KSListBox;
import org.kuali.student.common.ui.client.widgets.KSTextBox;
import org.kuali.student.core.dto.StatusInfo;
import org.kuali.student.lum.lo.dto.LoCategoryInfo;
import org.kuali.student.lum.lo.dto.LoCategoryTypeInfo;
import org.kuali.student.lum.lu.ui.course.client.service.LoRpcService;
import org.kuali.student.lum.lu.ui.course.client.service.LoRpcServiceAsync;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Hyperlink;
import com.google.gwt.user.client.ui.VerticalPanel;

public class CategoryManagement extends Composite{
    private KSButton addButton = new KSButton("Create");
    private KSButton deleteButton = new KSButton("Delete");
    private KSButton updateButton = new KSButton("Update");

    KSCheckBox selectAllCheckBox = new KSCheckBox("Select All");
    KSCheckBox accreditationCheckBox = new KSCheckBox("Accreditation");
    KSCheckBox skillCheckBox = new KSCheckBox("Skill");
    KSCheckBox subjectCheckBox = new KSCheckBox("Subject");
    KSTextBox wordsInCategoryTextBox = new KSTextBox();
    KSButton filterButton = new KSButton("Filter");
    
    CategoryTable categoryTable = new CategoryTable();

    VerticalPanel mainPanel = new VerticalPanel();
    KSLabel messageLabel = new KSLabel();
    LoRpcServiceAsync loRpcServiceAsync= GWT.create(LoRpcService.class);
    public CategoryManagement(){
      super.initWidget(mainPanel);
      
      VerticalPanel filterPanel = new VerticalPanel();
      filterPanel.add(new KSLabel("Filter"));
      filterPanel.add(new KSLabel("Select all"));
      
      Hyperlink selectAllLink = new Hyperlink();
      selectAllLink.setText("Select all");
      filterPanel.add(selectAllLink);
      
      Hyperlink clearLink = new Hyperlink();
      selectAllLink.setText("Clear");
      filterPanel.add(clearLink);
      
      filterPanel.add(accreditationCheckBox);
      filterPanel.add(skillCheckBox);
      filterPanel.add(subjectCheckBox);
      filterPanel.add(new KSLabel("By sords in category"));
      filterPanel.add(wordsInCategoryTextBox);
      filterPanel.add(filterButton);
      
      HorizontalPanel buttonPanel = new HorizontalPanel();
      buttonPanel.add(addButton);
      //buttonPanel.add(filterButton);
      buttonPanel.add(deleteButton);
      buttonPanel.add(updateButton);

      mainPanel.add(buttonPanel);
      mainPanel.add(filterPanel);
      mainPanel.add(categoryTable);
      mainPanel.add(messageLabel);
      filterButton.addClickHandler(new ClickHandler(){
          @Override
          public void onClick(ClickEvent event) {
          
          }
          
      });
      addButton.addClickHandler(new ClickHandler(){
        @Override
        public void onClick(ClickEvent event) {
            CreateCategoryDialog dialog = new CreateCategoryDialog();
            
            dialog.show();
            List<LoCategoryInfo> categories = dialog.getCategory();
            for(LoCategoryInfo cate: categories){
                //create new category how to get lo repo key
                loRpcServiceAsync.createLoCategory(null, cate.getType(), cate,new AsyncCallback<LoCategoryInfo>() {
                    @Override
                    public void onFailure(Throwable caught) {
                        Window.alert("create LoCategory failed " + caught.getMessage());
                    }

                    @Override
                    public void onSuccess(LoCategoryInfo result) {
                        Window.alert("create LoCategory successfully");
                    }
                } );
            }
        }
      });
      deleteButton.addClickHandler(new ClickHandler(){
        @Override
        public void onClick(ClickEvent event) {
            //get select category from table
            LoCategoryInfo cate = null;
            DeleteConfirmationDialog dialog = new DeleteConfirmationDialog();
            
          //  dialog.setCategory(cate);
            dialog.show();
            if(dialog.isOKButtonClicked()){
                loRpcServiceAsync.deleteLoCategory( cate.getId(),new AsyncCallback<StatusInfo>() {
                    @Override
                    public void onFailure(Throwable caught) {
                        Window.alert("delete LoCategory failed " + caught.getMessage());
                    }

                    @Override
                    public void onSuccess(StatusInfo result) {
                        Window.alert("delete LoCategory successfully");
                    }
                } );
            }
        }
      });
      updateButton.addClickHandler(new ClickHandler(){
          @Override
          public void onClick(ClickEvent event) {
              //get selected from table
              LoCategoryInfo cate = null;
              UpdateCategoryDialog dialog = new UpdateCategoryDialog();
          //    dialog.setCategory(null);
              dialog.show();
              cate = dialog.getCategory();
                  loRpcServiceAsync.updateLoCategory( cate.getId(), cate,new AsyncCallback<LoCategoryInfo>() {
                      @Override
                      public void onFailure(Throwable caught) {
                          Window.alert("update LoCategory failed " + caught.getMessage());
                      }

                      @Override
                      public void onSuccess(LoCategoryInfo result) {
                          Window.alert("update LoCategory successfully");
                      }
                  } );

          }
        });
      loRpcServiceAsync.getLoCategories("kuali.loRepository.key.singleUse", new AsyncCallback<List<LoCategoryInfo>>() {
          @Override
          public void onFailure(Throwable caught) {
              Window.alert("getLoCategory failed " + caught.getMessage());
          }

          @Override
          public void onSuccess(List<LoCategoryInfo> result) {
              System.out.println("\n\n\n\n"+result.size());   
              categoryTable.setData();
          }
      });
    }
}
        
       
class DeleteConfirmationDialog extends KSLightBox{
    KSLabel categoryNameLabel = new KSLabel();
    KSLabel categoryTypeLabel = new KSLabel();
    private boolean okButtonClicked = false;
    public DeleteConfirmationDialog(){
        VerticalPanel mainPanel = new VerticalPanel();
        FlexTable layoutTable = new FlexTable();
        mainPanel.add(new KSLabel("You are about to delete the following:"));
        mainPanel.add(layoutTable);
        layoutTable.setWidget(0,0, new KSLabel("Category"));
        layoutTable.setWidget(0,1, categoryNameLabel);
        layoutTable.setWidget(1,0, new KSLabel("Type"));
        layoutTable.setWidget(1,1, categoryTypeLabel);
        
        KSButton deleteButton = new KSButton("Delete");
        Hyperlink cancelButton = new Hyperlink();
        cancelButton.setText("Cancel");
        HorizontalPanel buttonPanel = new HorizontalPanel();
        buttonPanel.add(deleteButton);
        
        deleteButton.addClickHandler(new ClickHandler(){
            @Override
            public void onClick(ClickEvent event) {
                okButtonClicked = true;
                DeleteConfirmationDialog.this.hide();
            }
        });
        
        buttonPanel.add(cancelButton);
        cancelButton.addClickHandler(new ClickHandler(){
            @Override
            public void onClick(ClickEvent event) {
                okButtonClicked = false;
                DeleteConfirmationDialog.this.hide();
            }
            
        });
        mainPanel.add(buttonPanel);
        super.setWidget(mainPanel);
    }
    public boolean isOKButtonClicked(){
        return this.okButtonClicked;
    }
    public void setCategory(LoCategoryInfo categoryInfo){
        categoryNameLabel.setText(categoryInfo.getName());
        categoryNameLabel.setText(categoryInfo.getName());
    
    }
    
}
class UpdateCategoryDialog extends KSLightBox{
    FlexTable layoutTable = new FlexTable();
    KSTextBox nameTextBox = new KSTextBox();
    KSDropDown typeListBox = new KSDropDown();
    KSButton okButton = new KSButton("OK");
    KSButton cancelButton = new KSButton("Cancel");
    public UpdateCategoryDialog(){
        layoutTable.setWidget(0,0, new KSLabel("Category"));
        layoutTable.setWidget(0,1, new KSLabel("Type"));
        layoutTable.setWidget(1,0, nameTextBox);
        layoutTable.setWidget(1,1,typeListBox);
        
        HorizontalPanel buttonPanel = new HorizontalPanel();
        buttonPanel.add(okButton);
        buttonPanel.add(cancelButton);
        
        VerticalPanel mainPanel = new VerticalPanel();
        mainPanel.add(new KSLabel("Update Category"));
        mainPanel.add(layoutTable);
        mainPanel.add(buttonPanel);
        
        super.setWidget(mainPanel);
        cancelButton.addClickHandler(new ClickHandler(){

            @Override
            public void onClick(ClickEvent event) {
                UpdateCategoryDialog.this.hide();
            }
            
        });
        super.setWidget(mainPanel);
    }
    
    public void setCategory(LoCategoryTypeInfo categoryInfo){
        categoryInfo.getDesc();
        categoryInfo.getName();
    
    }
    public LoCategoryInfo getCategory(){
        
        return null;
    }
    
}
class CreateCategoryDialog extends KSLightBox{
    FlexTable layoutTable = new FlexTable();
    
    KSButton okButton = new KSButton("OK");
    KSButton cancelButton = new KSButton("Cancel");
    
    KSTextBox nameTextBox = new KSTextBox();
    KSDropDown typeListBox = new KSDropDown();
    public CreateCategoryDialog(){
 
        layoutTable.setWidget(0,0, new KSLabel("Category"));
        layoutTable.setWidget(0,1, new KSLabel("Type"));
        layoutTable.setWidget(1,0, nameTextBox);
        layoutTable.setWidget(1,1, typeListBox);
        
        HorizontalPanel buttonPanel = new HorizontalPanel();
        buttonPanel.add(okButton);
        buttonPanel.add(cancelButton);
        
        VerticalPanel mainPanel = new VerticalPanel();
        mainPanel.add(new KSLabel("Create New Category"));
        mainPanel.add(layoutTable);
        mainPanel.add(buttonPanel);
        mainPanel.setPixelSize(300,300);
        super.setWidget(mainPanel);
        
        cancelButton.addClickHandler(new ClickHandler(){

            @Override
            public void onClick(ClickEvent event) {
                CreateCategoryDialog.this.hide();
            }
            
        });
    }
    
    public List<LoCategoryInfo> getCategory(){
        
        return null;
    }
}



class CategoryTable extends FlexTable{
    List<LoCategoryInfo> categoryList =null;
    public ModelDTO getSelectedData(){
        return null;
    }
    public void setData(){}
}