package org.kuali.student.common.ui.client.mvc.test;

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

public class AddressManager extends Controller {
    public enum AddressViews {
        ADDRESS_LIST, ADD_ADDRESS
    }

    private final SimplePanel viewPanel = new SimplePanel();
    private final View showAddressesView = new ShowAddressesView(this);
    private final View addAddressView = new AddAddressView(this);
    private final Model<Address> addresses;

    public AddressManager(Model<Address> addresses) {
        super();
        this.addresses = addresses;
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
        if (modelType.equals(Address.class)) {
            callback.onModelReady(addresses);
        } else {
            super.requestModel(modelType, callback);
        }
    }

    @Override
    public void showDefaultView() {
        showView(AddressViews.ADDRESS_LIST);
    }

    @Override
    protected <V extends Enum<?>> View getView(V viewType) {
        switch ((AddressViews) viewType) {
            case ADD_ADDRESS:
                return addAddressView;
            case ADDRESS_LIST:
                return showAddressesView;
            default:
                return null;
        }
    }

    @Override
    public Class<AddressViews> getViewsEnum() {
        return AddressViews.class;
    }

}
