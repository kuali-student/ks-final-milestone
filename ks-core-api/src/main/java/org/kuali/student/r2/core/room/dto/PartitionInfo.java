/**
 * Copyright 2013 The Kuali Foundation Licensed under the
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
 *
 * Created by mahtabme on 9/20/13
 */
package org.kuali.student.r2.core.room.dto;

import org.kuali.student.r2.common.dto.IdEntityInfo;
import org.kuali.student.r2.core.room.infc.Partition;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlType;
import java.io.Serializable;
import java.util.List;

/**
 * This class represents a Partition (a loose association
 * of buildings, rooms, campuses). Formerly (and also) known
 * as a "Region".
 *
 * @author Kuali Student Team
 * @author Mezba Mahtab
 * @Version 2.0
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PartitionInfo", propOrder = {"id", "typeKey", "stateKey",
        "name", "descr", "meta", "attributes", "_futureElements" })
public class PartitionInfo extends IdEntityInfo implements Partition, Serializable {

    ///////////////////////////
    // CLASS CONSTANTS
    ///////////////////////////

    private static final long serialVersionUID = 1L;

    @XmlAnyElement
    private List<Object> _futureElements;

    ///////////////////////////
    // CONSTRUCTORS
    ///////////////////////////

    public PartitionInfo() {}

    public PartitionInfo(Partition partition) {
        super(partition);
    }
}
