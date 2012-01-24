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

package org.kuali.student.core.enumerationmanagement.model;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.kuali.student.common.dto.RichTextInfo;
import org.kuali.student.common.entity.RichTextEntity;
import org.kuali.student.common.infc.RichText;

@Entity
@Table(name = "KSEM_RICH_TEXT_T")
public class EnumerationRichTextEntity extends RichTextEntity {

    public EnumerationRichTextEntity(){}
    
    public EnumerationRichTextEntity(String plain, String formatted){
        this.setFormatted(formatted);
        this.setPlain(plain);
    }
    
    public EnumerationRichTextEntity(RichText rt) {
        if (null != rt) {
            this.setFormatted(rt.getFormatted());
            this.setPlain(rt.getPlain());
        }
    }
    
    public RichTextInfo toDto() {
        RichTextInfo rti = new RichTextInfo();
        rti.setPlain(getPlain());
        rti.setFormatted(getFormatted());
        return rti;
    }
}
