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
import org.kuali.student.common.ui.client.mvc.ModelChangeEvent;
import org.kuali.student.common.ui.client.mvc.ModelChangeHandler;
import org.kuali.student.common.ui.client.mvc.ModelRequestCallback;
import org.kuali.student.common.ui.client.mvc.ViewComposite;

public class PersonAddressView extends ViewComposite {
    private final Model<Address> model = new Model<Address>();
    private final AddressManager manager = new AddressManager(model);;
    private Person person = null;

    public PersonAddressView(Controller controller) {
        this(controller, null);
    }

    public PersonAddressView(Controller controller, HistoryModel hist) {
        super(controller, PersonApplication.PersonViews.PERSON_ADDRESS.toString());
        super.initWidget(manager);
        model.addModelChangeHandler(new ModelChangeHandler<Address>() {
            public void onModelChange(ModelChangeEvent<Address> event) {
                if (person != null) {
                    switch (event.getAction()) {
                        case ADD:
                        case UPDATE:
                            person.getAddresses().put(event.getValue().getId(), event.getValue());
                            break;
                        case REMOVE:
                            person.getAddresses().remove(event.getValue().getId());
                            break;
                    }
                    firePersonUpdate();
                }
            }
        });
        if(hist != null) {
            hist.addController("subview", manager);
        }
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = null;
        for (Address a : model.getValues()) {
            model.remove(a);
        }
        for (Address a : person.getAddresses().values()) {
            model.add(a);
        }
        this.person = person;
    }

    private void firePersonUpdate() {
        getController().requestModel(Person.class, new ModelRequestCallback<Person>() {
            @Override
            public void onModelReady(Model<Person> model) {
                model.update(person);
            }

            @Override
            public void onRequestFail(Throwable cause) {
                throw new RuntimeException("Could not flag update on person model", cause);
            }

        });
    }

}
