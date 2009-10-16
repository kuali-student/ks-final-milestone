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

import java.util.HashMap;
import java.util.Map;

import org.kuali.student.common.ui.client.mvc.Controller;
import org.kuali.student.common.ui.client.mvc.Model;
import org.kuali.student.common.ui.client.mvc.ModelChangeEvent;
import org.kuali.student.common.ui.client.mvc.ModelChangeHandler;
import org.kuali.student.common.ui.client.mvc.ModelRequestCallback;
import org.kuali.student.common.ui.client.mvc.ViewComposite;
import org.kuali.student.common.ui.client.mvc.test.AddressManager.AddressViews;
import org.kuali.student.common.ui.client.widgets.KSButton;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.FlexTable;

public class ShowAddressesView extends ViewComposite {
    private final FlexTable table = new FlexTable();
    private final Map<Address, Integer> rowMap = new HashMap<Address, Integer>();
    private final KSButton addAddress = new KSButton("Add Address", new ClickHandler() {
        public void onClick(ClickEvent event) {
            getController().showView(AddressViews.ADD_ADDRESS);
        }
    });
    private Model<Address> addresses = null;

    public ShowAddressesView(Controller controller) {
        super(controller, AddressManager.AddressViews.ADDRESS_LIST.toString());
        super.initWidget(table);
    }

    @Override
    protected void onLoad() {
        if (addresses == null) {
            getController().requestModel(Address.class, new ModelRequestCallback<Address>() {
                public void onModelReady(Model<Address> model) {
                    addresses = model;
                    setup();
                }

                public void onRequestFail(Throwable cause) {
                    throw new RuntimeException("Unable to connect to model", cause);
                }

            });
        }
    }

    protected void setup() {
        addresses.addModelChangeHandler(new ModelChangeHandler<Address>() {
            public void onModelChange(ModelChangeEvent<Address> event) {
                redraw();
            }
        });

        redraw();
    }

    protected void redraw() {
        while (table.getRowCount() > 0) {
            table.removeRow(0);
        }
        table.setText(0, 0, "ID");
        table.setText(0, 1, "Street");
        table.setText(0, 2, "City");
        table.setText(0, 3, "State");
        table.setText(0, 4, "ZIP");
        table.setWidget(0, 5, addAddress);
        rowMap.clear();
        for (Address p : addresses.getValues()) {
            addAddress(p);
        }
    }

    private void addAddress(final Address a) {
        int index = table.getRowCount();
        table.setText(index, 0, a.getId());
        table.setText(index, 1, a.getStreet());
        table.setText(index, 2, a.getCity());
        table.setText(index, 3, a.getState());
        table.setText(index, 4, a.getZip());

        table.setWidget(index, 5, new KSButton("Delete", new ClickHandler() {
            public void onClick(ClickEvent event) {
                getController().requestModel(Address.class, new ModelRequestCallback<Address>() {
                    public void onModelReady(Model<Address> model) {
                        model.remove(a);
                    }

                    public void onRequestFail(Throwable cause) {
                        throw new RuntimeException(cause);
                    }
                });
            }
        }));
    }

}
