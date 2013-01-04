/**
 * Copyright 2010 The Kuali Foundation Licensed under the
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

package org.kuali.student.r2.lum.clu.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import org.kuali.student.r2.common.dto.HasAttributesAndMetaInfo;
import org.kuali.student.r2.common.dto.RichTextInfo;
import org.kuali.student.r2.lum.clu.infc.CluFee;
import org.kuali.student.r2.lum.clu.infc.CluFeeRecord;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CluFeeInfo", propOrder = {"id", "descr", "cluFeeRecords", "attributes", "meta" , "_futureElements" }) 
public class CluFeeInfo extends HasAttributesAndMetaInfo implements CluFee, Serializable {

    private static final long serialVersionUID = 1L;

    @XmlAttribute
    private String id;

    @XmlElement
    private RichTextInfo descr;

    @XmlElement
    private List<CluFeeRecordInfo> cluFeeRecords;

    @XmlAnyElement
    private List<Object> _futureElements;  

    public CluFeeInfo() {

    }

    public CluFeeInfo(CluFee cluFee) {
        super(cluFee);
        if (null != cluFee) {
            this.id = cluFee.getId();
            this.descr = cluFee.getDescr();
            this.cluFeeRecords = new ArrayList<CluFeeRecordInfo>();
            for (CluFeeRecord cluFeeRecord : cluFee.getCluFeeRecords()) {
                this.cluFeeRecords.add(new CluFeeRecordInfo(cluFeeRecord));
            }
        }
    }

    @Override
    public List<CluFeeRecordInfo> getCluFeeRecords() {
        if (cluFeeRecords == null) {
            cluFeeRecords = new ArrayList<CluFeeRecordInfo>(0);
        }
        return cluFeeRecords;
    }

    public void setCluFeeRecords(List<CluFeeRecordInfo> cluFeeRecords) {
        this.cluFeeRecords = cluFeeRecords;
    }

    @Override
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public RichTextInfo getDescr() {
        return descr;
    }

    public void setDescr(RichTextInfo descr) {
        this.descr = descr;
    }

}
