package org.kuali.student.r2.core.queue.dto;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

import org.kuali.student.r2.common.dto.IdEntityInfo;
import org.kuali.student.r2.core.queue.infc.QueueEntry;
import org.kuali.student.r2.core.queue.infc.Queue;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "QueueInfo", propOrder = {"id", "typeKey", "stateKey", "name", "descr",
        "queueEntries", "refObjectId", "refObjectTypeKey", "meta", "attributes", "_futureElements"})
public class QueueInfo extends IdEntityInfo implements Queue, Serializable {

    private static final long serialVersionUID = 1L;

    private List<QueueEntry> queueEntries;

    private String refObjectId;

    private String refObjectTypeKey;

    public String getRefObjectTypeKey() {
        return refObjectTypeKey;
    }

    public void setRefObjectTypeKey(String refObjectTypeKey) {
        this.refObjectTypeKey = refObjectTypeKey;
    }

    @Override
    public List<QueueEntry> getQueueEntries() {
        return queueEntries;
    }

    @Override
    public String getRefObjectId() {
        return refObjectId;
    }

    @Override
    public String getProcessingAlgorithm() {
        return null;
    }

    public void setQueueEntries(List<QueueEntry> collectionEntries) {
        this.queueEntries = collectionEntries;
    }

    public void setRefObjectId(String refObjectId) {
        this.refObjectId = refObjectId;
    }

}
