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
package org.kuali.student.lum.ui.requirements.client.controller;

import org.kuali.student.common.ui.client.mvc.Controller;
import org.kuali.student.common.ui.client.mvc.Model;
import org.kuali.student.common.ui.client.mvc.ModelRequestCallback;
import org.kuali.student.common.ui.client.mvc.View;
import org.kuali.student.common.ui.client.mvc.ViewComposite;
import org.kuali.student.common.ui.client.mvc.events.LogoutEvent;
import org.kuali.student.common.ui.client.mvc.events.LogoutHandler;
import org.kuali.student.core.dto.Idable;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.SimplePanel;

public class CoreqManager extends Controller {
//    public enum AddressViews {
//        ADDRESS_LIST, ADD_ADDRESS
//    }

    private final SimplePanel viewPanel = new SimplePanel();
//    private final View showAddressesView = new ShowAddressesView(this);
//    private final View addAddressView = new AddAddressView(this);
//    private final Model<Address> addresses;

    public CoreqManager() {
        super();
        super.initWidget(viewPanel);
    }

    @Override
    protected void onLoad() {
        showDefaultView();
        // add event handler to show example of a nested controller listening for unchecked events
        addApplicationEventHandler(LogoutEvent.TYPE, new LogoutHandler() {
            public void onLogout(LogoutEvent event) {
                Window.alert("AddressManager caught logout event");
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
//        if (modelType.equals(Address.class)) {
//            callback.onModelReady(addresses);
//        } else {
//            super.requestModel(modelType, callback);
//        }
    }

    @Override
    public void showDefaultView() {
//        showView(AddressViews.ADDRESS_LIST);
    }

    @Override
    protected <V extends Enum<?>> View getView(V viewType) {
//        switch ((AddressViews) viewType) {
//            case ADD_ADDRESS:
//                return addAddressView;
//            case ADDRESS_LIST:
//                return showAddressesView;
//            default:
//                return null;
//        }
        return null;
    }

    public Class<? extends Enum<?>> getViewsEnum() {
        // TODO ddean - THIS METHOD NEEDS JAVADOCS
        return null;
    }

}
