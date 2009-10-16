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
import org.kuali.student.common.ui.client.mvc.test.PersonApplication.PersonViews;
import org.kuali.student.common.ui.client.widgets.KSButton;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.FlexTable;

public class ShowPeopleView extends ViewComposite {
    private final FlexTable table = new FlexTable();
    private final Map<Person, Integer> rowMap = new HashMap<Person, Integer>();
    private Model<Person> people = null;
    private Person selectedPerson = null;

    public ShowPeopleView(Controller controller) {
        super(controller, PersonApplication.PersonViews.SHOW_PEOPLE.toString());
        super.initWidget(table);

        controller.requestModel(Person.class, new ModelRequestCallback<Person>() {
            public void onModelReady(Model<Person> model) {
                people = model;
                setup();
            }

            public void onRequestFail(Throwable cause) {
                throw new RuntimeException("Unable to connect to model", cause);
            }

        });
    }

    protected void setup() {
        people.addModelChangeHandler(new ModelChangeHandler<Person>() {
            public void onModelChange(ModelChangeEvent<Person> event) {
                // here we could take a specific action based on add/update/remove
                // for now, just redraw
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
        table.setText(0, 1, "First Name");
        table.setText(0, 2, "Last Name");

        rowMap.clear();
        for (Person p : people.getValues()) {
            addPerson(p);
        }
    }

    private void addPerson(final Person p) {
        int index = table.getRowCount();
        table.setText(index, 0, p.getId());
        table.setText(index, 1, p.getFirstName());
        table.setText(index, 2, p.getLastName());
        table.setWidget(index, 3, new KSButton("Delete", new ClickHandler() {
            public void onClick(ClickEvent event) {
                getController().requestModel(Person.class, new ModelRequestCallback<Person>() {
                    public void onModelReady(Model<Person> model) {
                        model.remove(p);
                    }

                    public void onRequestFail(Throwable cause) {
                        throw new RuntimeException(cause);
                    }
                });
            }
        }));

        table.setWidget(index, 4, new KSButton("Addresses", new ClickHandler() {
            public void onClick(ClickEvent event) {
                setSelectedPerson(p);
                getController().showView(PersonViews.PERSON_ADDRESS);
            }
        }));
    }

    public void setSelectedPerson(Person selectedPerson) {
        this.selectedPerson = selectedPerson;
    }

    public Person getSelectedPerson() {
        return selectedPerson;
    }

}
