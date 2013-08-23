/**
 * Copyright 2012 The Kuali Foundation
 *
 * Licensed under the the Educational Community License, Version 1.0
 * (the "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 * http://www.opensource.org/licenses/ecl1.php
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.kuali.student.enrollment.class1.lpr.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.kuali.student.enrollment.lpr.dto.LprTransactionItemRequestOptionInfo;
import org.kuali.student.enrollment.lpr.infc.LprTransactionItemRequestOption;
import org.kuali.student.r2.common.entity.BaseEntity;

@Entity
@Table(name="KSEN_LPR_TRANS_ITEM_RQST_OPT")
public class LprTransactionItemRequestOptionEntity extends BaseEntity {

    @Column(name = "OPTION_KEY")
    private String optionKey;

    @Column(name = "OPTION_VALUE")
    private String optionValue;

    @ManyToOne 
    @JoinColumn (name="LPR_TRANS_ITEM_ID")
    private LprTransactionItemEntity lprTransactionItem;

    public LprTransactionItemRequestOptionEntity() {
    }
    
    
    public LprTransactionItemRequestOptionEntity(
			LprTransactionItemRequestOption info) {    	
    	fromDto (info);
    }
    
    

	public void fromDto(LprTransactionItemRequestOption info) {
		
		this.setOptionKey(info.getOptionKey());
		this.setOptionValue(info.getOptionValue());
		
	}



	public String getOptionKey() {
        return optionKey;
    }

    public void setOptionKey(String optionKey) {
        this.optionKey = optionKey;
    }

    public String getOptionValue() {
        return optionValue;
    }

    public void setOptionValue(String optionValue) {
        this.optionValue = optionValue;
    }

	public LprTransactionItemEntity getLprTransactionItem() {
		return lprTransactionItem;
	}

	public void setLprTransactionItem(LprTransactionItemEntity lprTransactionItem) {
		this.lprTransactionItem = lprTransactionItem;
	}



	public LprTransactionItemRequestOptionInfo toDto() {
		
		LprTransactionItemRequestOptionInfo info = new LprTransactionItemRequestOptionInfo();
		
		info.setId(getId());
		info.setOptionKey(getOptionKey());
		info.setOptionValue(getOptionValue());
		
		return info;
	}



	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("LprTransactionItemRequestOptionEntity [id=");
		builder.append(getId());
		builder.append(", optionKey=");
		builder.append(optionKey);
		builder.append(", optionValue=");
		builder.append(optionValue);
		builder.append("]");
		return builder.toString();
	}

    
}
