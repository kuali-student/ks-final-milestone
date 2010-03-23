package org.kuali.student.common.ui.client.mvc;

import org.kuali.student.core.assembly.data.Data;

public interface HasDataValue {
	public Data.Value getValue();
	public void setValue(Data.Value value);
	public void addValueChangeCallback(Callback<Data.Value> callback);
	
}
