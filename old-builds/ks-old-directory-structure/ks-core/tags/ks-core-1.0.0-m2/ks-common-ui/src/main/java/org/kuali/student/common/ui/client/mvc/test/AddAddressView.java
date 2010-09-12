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
package org.kuali.student.common.ui.client.mvc.test;

import org.kuali.student.common.ui.client.mvc.Controller;
import org.kuali.student.common.ui.client.mvc.Model;
import org.kuali.student.common.ui.client.mvc.ModelRequestCallback;
import org.kuali.student.common.ui.client.mvc.ViewComposite;
import org.kuali.student.common.ui.client.widgets.KSButton;
import org.kuali.student.common.ui.client.widgets.KSTextBox;

import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.FlexTable;

public class AddAddressView extends ViewComposite {
    private final FlexTable table = new FlexTable();
    private final KSTextBox id = new KSTextBox();
    private final KSTextBox street = new KSTextBox();
    private final KSTextBox city = new KSTextBox();
    private final KSTextBox state = new KSTextBox();
    private final KSTextBox zip = new KSTextBox();

    private final KSButton save = new KSButton("Save", new ClickHandler() {
        public void onClick(ClickEvent event) {
            final Address a = new Address();
            a.setId(id.getText());
            a.setStreet(street.getText());
            a.setCity(city.getText());
            a.setState(state.getText());
            a.setZip(zip.getText());

            getController().requestModel(Address.class, new ModelRequestCallback<Address>() {
                public void onModelReady(Model<Address> model) {
                    model.add(a);
                    reset();
                    getController().showDefaultView();
                }

                public void onRequestFail(Throwable cause) {
                    Window.alert("Unable to save address object");
                }
            });
        }
    });

    private final KSButton cancel = new KSButton("Cancel", new ClickHandler() {
        public void onClick(ClickEvent event) {
            reset();
        }
    });

    private final ChangeHandler changeHandler = new ChangeHandler() {
        public void onChange(ChangeEvent event) {
            dirty = true;
        }
    };

    private boolean dirty = false;

    public AddAddressView(Controller controller) {
        super(controller, AddressManager.AddressViews.ADD_ADDRESS.toString());
        super.initWidget(table);

        table.setText(0, 0, "Address ID");
        table.setWidget(0, 1, id);
        table.setText(1, 0, "Street");
        table.setWidget(1, 1, street);
        table.setText(2, 0, "City");
        table.setWidget(2, 1, city);
        table.setText(3, 0, "State");
        table.setWidget(3, 1, state);
        table.setText(4, 0, "ZIP");
        table.setWidget(4, 1, zip);

        table.setWidget(5, 0, save);
        table.setWidget(5, 1, cancel);

        id.addChangeHandler(changeHandler);
        street.addChangeHandler(changeHandler);
        city.addChangeHandler(changeHandler);
        state.addChangeHandler(changeHandler);
        zip.addChangeHandler(changeHandler);

    }

    @Override
    public boolean beforeHide() {
        boolean result = super.beforeHide();
        if (result && dirty) {
            result = Window.confirm("Are you sure you want discard your changes?");
            if (result) {
                reset();
            }
        }
        return result;
    }

    protected void reset() {
        id.setText("");
        street.setText("");
        city.setText("");
        state.setText("");
        zip.setText("");
        dirty = false;
    }

}
