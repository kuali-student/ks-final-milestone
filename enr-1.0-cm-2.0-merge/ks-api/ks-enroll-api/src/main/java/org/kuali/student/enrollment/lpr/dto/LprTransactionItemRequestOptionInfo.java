/*
 * Copyright 2007 The Kuali Foundation Licensed under the Educational Community
 * License, Version 1.0 (the "License"); you may not use this file except in
 * compliance with the License. You may obtain a copy of the License at
 * http://www.opensource.org/licenses/ecl1.php Unless required by applicable law
 * or agreed to in writing, software distributed under the License is
 * distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 */
package org.kuali.student.enrollment.lpr.dto;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import org.kuali.student.enrollment.lpr.infc.LprTransactionItemRequestOption;
import org.w3c.dom.Element;

/**
 * @author Kuali Student Team (sambitpatnaik)
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RequestOptionInfo", propOrder = {"id", "optionKey", "optionValue", "_futureElements"})
public class LprTransactionItemRequestOptionInfo implements LprTransactionItemRequestOption {

    @XmlElement
    private String id;
    @XmlElement
    private String optionKey;
    @XmlElement
    private String optionValue;
    @XmlAnyElement
    private List<Element> _futureElements;

    public LprTransactionItemRequestOptionInfo() {
    }

    public LprTransactionItemRequestOptionInfo(LprTransactionItemRequestOption reqOp) {
        if (null == reqOp) {
            return;
        }
        this.optionValue = reqOp.getOptionValue();
        this.optionKey = reqOp.getOptionKey();
    }

    public void setOptionValue(String optionValue) {
        this.optionValue = optionValue;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public String getOptionKey() {
        return optionKey;
    }

    @Override
    public String getOptionValue() {
        return optionValue;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setOptionKey(String optionKey) {
        this.optionKey = optionKey;
    }

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("LprTransactionItemRequestOptionInfo [id=");
		builder.append(id);
		builder.append(", optionKey=");
		builder.append(optionKey);
		builder.append(", optionValue=");
		builder.append(optionValue);
		builder.append("]");
		return builder.toString();
	}
    
    
}
