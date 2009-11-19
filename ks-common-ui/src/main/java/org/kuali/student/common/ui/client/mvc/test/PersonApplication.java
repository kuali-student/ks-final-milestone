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
import org.kuali.student.common.ui.client.mvc.View;
import org.kuali.student.common.ui.client.mvc.ViewComposite;
import org.kuali.student.common.ui.client.mvc.events.LogoutEvent;
import org.kuali.student.common.ui.client.mvc.events.LogoutHandler;
import org.kuali.student.common.ui.client.widgets.KSButton;
import org.kuali.student.core.dto.Idable;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.VerticalPanel;

// TODO fix handler registration bug (handlers registered in onLoad, and not deregistered afterwards
public class PersonApplication extends Controller {
    public enum PersonViews {
        ADD_PERSON, SHOW_PEOPLE, HISTORY, PERSON_ADDRESS
    }

    private final HistoryModel hist = new HistoryModel();

    private final AddPersonView addPersonView = new AddPersonView(this);
    private final ShowPeopleView peopleView = new ShowPeopleView(this);
    private final HistoryView history = new HistoryView(this, hist);
    private final PersonAddressView personAddress = new PersonAddressView(this, hist);

    private final HorizontalPanel panel = new HorizontalPanel();
    private final VerticalPanel menu = new VerticalPanel();
    private final SimplePanel viewPanel = new SimplePanel();
    
    private Model<Person> people;

    private final KSButton showAddView = new KSButton("Add Person", new ClickHandler() {
        public void onClick(ClickEvent event) {
            showView(PersonViews.ADD_PERSON);
        }
    });
    private final KSButton showPeopleView = new KSButton("Show People", new ClickHandler() {
        public void onClick(ClickEvent event) {
            // example showing that a view change can be canceled
            if (!showView(PersonViews.SHOW_PEOPLE)) {
                Window.alert("User canceled view change");
            }
        }
    });
    private final KSButton showHistory = new KSButton("Show History", new ClickHandler() {
        public void onClick(ClickEvent event) {
            // example showing that a view change can be canceled
            if (!showView(PersonViews.HISTORY)) {
                Window.alert("User canceled view change");
            }
        }
    });
    private final KSButton logout = new KSButton("Logout", new ClickHandler() {
        public void onClick(ClickEvent event) {
            fireApplicationEvent(new LogoutEvent());
        }
    });

    public PersonApplication() {
        super.initWidget(panel);
        panel.add(menu);
        panel.add(viewPanel);
        menu.add(showAddView);
        menu.add(showPeopleView);
        menu.add(showHistory);
        menu.add(logout);
        showDefaultView();
    }

    @Override
    protected void onLoad() {
        // add event handler to show example of controller listening for unchecked events
        addApplicationEventHandler(LogoutEvent.TYPE, new LogoutHandler() {
            public void onLogout(LogoutEvent event) {
                Window.alert("PersonApplication caught logout event");
            }
        });
    }

    // controller operations
    @Override
    public void renderView(View view) {
        // in this case we know that all of our widgets are composites
        // but we could do view specific rendering, e.g. show a lightbox, etc
        viewPanel.setWidget((ViewComposite) view);
    }

    @Override
    protected void hideView(View view) {
        viewPanel.clear();
    }

    @Override
    @SuppressWarnings("unchecked")
    public void requestModel(Class modelType, ModelRequestCallback callback) {
        if (modelType.equals(Person.class)) {
            if (people == null) {
                people = new Model<Person>();
            }
            callback.onModelReady(people);
        } else {
            super.requestModel(modelType, callback);
        }
    }

    @Override
    public void showDefaultView() {
        showView(PersonViews.SHOW_PEOPLE);
    }

    @Override
    protected <V extends Enum<?>> View getView(V viewType) {
        if (viewType instanceof PersonViews) {
            switch ((PersonViews) viewType) {
                case ADD_PERSON:
                    return addPersonView;
                case HISTORY:
                    return history;
                case SHOW_PEOPLE:
                    return peopleView;
                case PERSON_ADDRESS:
                    personAddress.setPerson(peopleView.getSelectedPerson());
                    return personAddress;
                default:
                    // do nothing
            }
        }
        return null;
    }

    @Override
    public Class<PersonViews> getViewsEnum() {
        return PersonViews.class;
    }

}
