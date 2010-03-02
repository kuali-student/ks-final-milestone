package org.kuali.student.common.ui.client.mvc;

public interface ModelProvider<T extends Model> {
    public void requestModel(ModelRequestCallback<T> callback);
}
