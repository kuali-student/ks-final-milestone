package org.kuali.student.core.atp.model;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.kuali.student.common.dto.RichTextInfo;
import org.kuali.student.common.entity.RichTextEntity;
import org.kuali.student.common.infc.RichText;
 
@Entity
@Table(name = "KSEN_RICH_TEXT_T")
public class AtpRichTextEntity extends RichTextEntity {

    public AtpRichTextEntity(){}
    
    public AtpRichTextEntity(String plain, String formatted){
        this.setFormatted(formatted);
        this.setPlain(plain);
    }
    
    public AtpRichTextEntity(RichText rt) {
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
