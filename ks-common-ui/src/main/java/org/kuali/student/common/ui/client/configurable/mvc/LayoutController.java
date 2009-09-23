package org.kuali.student.common.ui.client.configurable.mvc;

import java.util.List;

import org.kuali.student.common.ui.client.application.Application;
import org.kuali.student.common.ui.client.event.ValidateResultEvent;
import org.kuali.student.common.ui.client.event.ValidateResultHandler;
import org.kuali.student.common.ui.client.mvc.Controller;
import org.kuali.student.common.ui.client.mvc.dto.ModelDTO;
import org.kuali.student.common.ui.client.mvc.dto.ModelDTOValue.ModelDTOType;
import org.kuali.student.common.ui.client.validator.ModelDTOConstraintSetupFactory;
import org.kuali.student.common.validator.Validator;
import org.kuali.student.core.dictionary.dto.ObjectStructure;
import org.kuali.student.core.validation.dto.ValidationResultContainer;
import org.kuali.student.core.validation.dto.ValidationResultInfo;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Widget;

public abstract class LayoutController extends Controller  {
    private ModelDTO modelDTO;
    private String objectKey;
    private LayoutController parentLayoutController= null; 
    
    public LayoutController(){
        
    }
    
    public void setModelDTO(ModelDTO dto, String objectKey){
        modelDTO = dto;
        this.objectKey = objectKey;
        super.addApplicationEventHandler(ValidateResultEvent.TYPE, new ValidateResultHandler() {
            @Override
            public void onValidateResult(ValidateResultEvent event) {
               List<ValidationResultContainer> list = event.getValidationResult();
               if(list == null || list.size() == 0 ){
                   return;
               }
               String ele = "";
               for(ValidationResultContainer vc: list){
                   ele += vc.getDataType()+" "+vc.getElement()+"\n";    
                   List<ValidationResultInfo> vrList = vc.getValidationResults();
                   for(ValidationResultInfo vr: vrList){
                       ele += vr.getMessage()+"\n";
                   }
               }
               Window.alert("Error:"+ele);
            }
        });
    }
    public ModelDTO getModel(){
        return modelDTO;
    }
    public void validate(){
        ModelDTOConstraintSetupFactory bc = new ModelDTOConstraintSetupFactory();
        final Validator val = new Validator(bc, true);
        final ValidateResultEvent e = new ValidateResultEvent();
        //ObjectStructure objStructure = Application.getApplicationContext().getDictionaryData(objectKey);
//        if(objStructure == null){
  //         Window.alert("Cannot load dictionary(object structure)");
    //    }
      //  List<ValidationResultContainer> results = val.validateTypeStateObject(getModel(), objStructure);
        //e.setValidationResult(results);// filled by calling the real validate code
//        fireApplicationEvent(e);
        //
        ModelDTO model = getModel();
        for(String key: model.keySet()){
         ModelDTO currentModel = ((ModelDTOType) model.get(key)).get();
         //CluDictionaryClassNameHelper
         currentModel.getClassName();
         //Use CluDictionaryClassNameHelper to get objectkey HERE
         ObjectStructure objStructure = Application.getApplicationContext().getDictionaryData(objectKey);
            if(objStructure == null){
               Window.alert("Cannot load dictionary(object structure)");
            }
            List<ValidationResultContainer> results = val.validateTypeStateObject(getModel(), objStructure);
            e.setValidationResult(results);// filled by calling the real validate code
            fireApplicationEvent(e);
        }
        
    }
    public static LayoutController findParentLayout(Widget w){
        LayoutController result = null;
        while (true) {
            w = w.getParent();
            if (w == null) {
                break;
            } else if (w instanceof Controller) {
                result = (LayoutController) w;
                break;
            } 
        }
        return result;
    }
    public void setParentLayout(LayoutController controller) {
        parentLayoutController = controller;
    }
    public LayoutController getParentLayout() {
        if (parentLayoutController == null) {
            parentLayoutController = LayoutController.findParentLayout(this);
        }
        return parentLayoutController;
    }    
}
