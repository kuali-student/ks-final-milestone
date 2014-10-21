package org.kuali.student.lum.common.client.widgets;

import org.kuali.student.common.ui.client.mvc.Callback;
import org.kuali.student.common.ui.client.service.DataSaveResult;
import org.kuali.student.r1.common.assembly.data.Data;
import org.kuali.student.r1.common.assembly.data.Metadata;

public interface CluSetRetriever {
    
    public void getCluSetInformation(String cluSetId, Callback<CluSetInformation> retrieveDoneCallback);
    public void getMetadata(String id, Callback<Metadata> callback);
    public void getData(String cluSetId, Callback<Data> callback);
    public void saveData(Data data, Callback<DataSaveResult> callback);

}
