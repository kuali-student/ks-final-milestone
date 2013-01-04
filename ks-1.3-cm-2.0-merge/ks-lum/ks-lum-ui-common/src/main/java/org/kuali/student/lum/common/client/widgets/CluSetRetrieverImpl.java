package org.kuali.student.lum.common.client.widgets;

import org.kuali.student.common.ui.client.mvc.Callback;
import org.kuali.student.common.ui.client.service.DataSaveResult;
import org.kuali.student.r1.common.assembly.data.Data;
import org.kuali.student.r1.common.assembly.data.Metadata;
import org.kuali.student.r2.common.util.ContextUtils;


import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;


public class CluSetRetrieverImpl implements CluSetRetriever {

    private CluSetManagementRpcServiceAsync cluSetManagementRpcServiceAsync = GWT.create(CluSetManagementRpcService.class);

    @Override
    public void getCluSetInformation(final String cluSetId, final Callback<CluSetInformation> retrieveDoneCallback) {
        cluSetManagementRpcServiceAsync.getCluSetInformation(cluSetId, new AsyncCallback<CluSetInformation>() {
            @Override
            public void onFailure(Throwable caught) {
                Window.alert("Failed to retrieve CluSet information for " + cluSetId);
                retrieveDoneCallback.exec(null);
            }
            @Override
            public void onSuccess(CluSetInformation result) {
                retrieveDoneCallback.exec(result);
            }
        });
    }

    @Override
    public void getMetadata(final String id, final Callback<Metadata> callback) {
        cluSetManagementRpcServiceAsync.getMetadata(id, null, new AsyncCallback<Metadata>() {

            @Override
            public void onFailure(Throwable caught) {
                Window.alert("Failed to get metadata with id " + id);
                callback.exec(null);
            }

            @Override
            public void onSuccess(Metadata result) {
                callback.exec(result);
            }
        });
    }

    @Override
    public void getData(final String cluSetId, final Callback<Data> callback) {
        cluSetManagementRpcServiceAsync.getData(cluSetId, new AsyncCallback<Data>() {
            @Override
            public void onFailure(Throwable caught) {
                Window.alert("Failed to retreive data for clu Set with id " + cluSetId);
                callback.exec(null);
            }
            @Override
            public void onSuccess(Data result) {
                callback.exec(result);
            }
        });
    }

    @Override
    public void saveData(final Data data, final Callback<DataSaveResult> callback) {
        cluSetManagementRpcServiceAsync.saveData(data, new AsyncCallback<DataSaveResult>() {
            @Override
            public void onFailure(Throwable caught) {
                Window.alert("Failed to save clu set data");
                callback.exec(null);
            }
            @Override
            public void onSuccess(DataSaveResult result) {
                callback.exec(result);
            }
        });
    }
    
}
