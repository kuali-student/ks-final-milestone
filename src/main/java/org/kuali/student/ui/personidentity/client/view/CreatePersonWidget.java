/**
 * 
 */
package org.kuali.student.ui.personidentity.client.view;

import java.util.List;

import org.kuali.student.commons.ui.mvc.client.ApplicationContext;
import org.kuali.student.commons.ui.propertychangesupport.PropertyChangeEvent;
import org.kuali.student.commons.ui.propertychangesupport.PropertyChangeListener;
import org.kuali.student.commons.ui.propertychangesupport.PropertyChangeListenerProxy;
import org.kuali.student.commons.ui.validators.client.ValidationResult;
import org.kuali.student.poc.xsd.personidentity.person.dto.PersonTypeDisplay;
import org.kuali.student.ui.personidentity.client.ModelState;
import org.kuali.student.ui.personidentity.client.controller.PersonIdentityController;
import org.kuali.student.ui.personidentity.client.model.validators.PersonCreateValidator;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

/**
 * @author Garey
 */
public class CreatePersonWidget extends Composite {

    AdminEditPanel aPanel = null;
    PersonCreateInfoWidget pWidget = null;
    PersonCreateValidator pValidator = new PersonCreateValidator();
    ClickListener clickSave = new ClickListener() {
        public void onClick(Widget sender) {

            PersonIdentityController.validate(pWidget.getPersonObj(), new AsyncCallback() {
                public void onFailure(Throwable caught) {

                }

                public void onSuccess(Object result) {
                    ValidationResult vr = (ValidationResult) result;
                    if (vr.isError()) {
                        String message = "";
                        for (String msg : vr.getMessages()) {
                            message += msg + " \n";
                        }

                        Info.displayInfo(ApplicationContext.getViews().get(AdminEditPanel.VIEW_NAME).getMessages().get("error"), message);
                    } else {

                        PersonIdentityController.createPerson(pWidget.getPersonObj(), pWidget.getPersonTypeKeys());
                        pWidget.setEditable(false);
                        // Info.displayInfo("Info", "Person created");
                        // Info.displayInfo(I18N.i18nConstant.info(), I18N.i18nConstant.personCreated());
                        Info.displayInfo(ApplicationContext.getViews().get(AdminEditPanel.VIEW_NAME).getMessages().get("info"), ApplicationContext.getViews().get(AdminEditPanel.VIEW_NAME).getMessages().get("personCreated"));
                    }

                }
            });
            /*
             * ValidationResult vr = pValidator.validate(pWidget.getPersonObj()); if(vr.isError() ){ String message = "";
             * for(String msg : vr.getMessages()){ message += msg + " \n"; }
             * Info.displayInfo(ApplicationContext.getViews().get(AdminEditPanel.VIEW_NAME).getMessages().get("error"),message);
             * }else{ PersonIdentityController.createPerson(pWidget.getPersonObj(), pWidget.getPersonTypeKeys());
             * pWidget.setEditable(false); //Info.displayInfo("Info", "Person created");
             * //Info.displayInfo(I18N.i18nConstant.info(), I18N.i18nConstant.personCreated());
             * Info.displayInfo(ApplicationContext.getViews().get(AdminEditPanel.VIEW_NAME).getMessages().get("info"),ApplicationContext.getViews().get(AdminEditPanel.VIEW_NAME).getMessages().get("personCreated")); }
             */
        }
    };

    ClickListener clickCancel = new ClickListener() {
        public void onClick(Widget sender) {
            // controller.setToSaved();
            pWidget.clear();
        }
    };

    private PropertyChangeListener listener = new PropertyChangeListenerProxy("personTypeKeys", new PropertyChangeListener() {
        public void propertyChange(PropertyChangeEvent propertyChangeEvent) {
            pWidget.setPersonTypeDisplayList(ModelState.getInstance().getPersonTypeKeys());
        }
    });

    private PropertyChangeListener CREATE_TAB_LISTENER = new PropertyChangeListenerProxy(ActionEvents.CLICK_CREATE_PERSON_TAB.getPropertyName(), new PropertyChangeListener() {
        public void propertyChange(PropertyChangeEvent propertyChangeEvent) {
            PersonIdentityController.getService().findCreatablePersonTypes(new PersonDisplayTypes());
        }
    });

    private class PersonDisplayTypes implements AsyncCallback {
        public void onFailure(Throwable caught) {
            Window.alert(caught.getMessage());
        }

        public void onSuccess(Object result) {
            List<PersonTypeDisplay> pTypes = (List<PersonTypeDisplay>) result;
            ModelState.getInstance().setPersonTypeKeys(pTypes);
            // pWidget.setPersonTypeDisplayList(pTypes);
        }
    }

    /**
     * 
     */
    public CreatePersonWidget() {

        aPanel = new AdminEditPanel();
        pWidget = new PersonCreateInfoWidget();
        ModelState.getInstance().addPropertyChangeListener(listener);
        ModelState.getInstance().addPropertyChangeListener(CREATE_TAB_LISTENER);

        pWidget.setEditable(true);

        aPanel.innerPanel.add(pWidget);

        aPanel.cBar.bEdit.setVisible(false);
        aPanel.cBar.bDelete.setVisible(false);
        aPanel.cBar.bCancel.setText(ApplicationContext.getViews().get(AdminEditPanel.VIEW_NAME).getMessages().get("reset"));
        aPanel.cBar.bCancel.addClickListener(clickCancel);
        aPanel.bSave.addClickListener(clickSave);

        initWidget(aPanel);
    }

    public void setEditable(boolean b) {
        pWidget.setEditable(b);
    }

    public void clear() {
        pWidget.clear();
    }

}
