package org.kuali.student.enrollment.class1.hold.model;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.kuali.student.r2.common.dto.RichTextInfo;
import org.kuali.student.r2.common.entity.RichTextEntity;
import org.kuali.student.r2.common.infc.RichText;


@Entity
@Table(name = "KSEN_HOLD_RICH_TEXT")
public class HoldRichTextEntity extends RichTextEntity {

    public HoldRichTextEntity() {
        
    }
    
    public HoldRichTextEntity(String plain, String formatted){
        this.setFormatted(formatted);
        this.setPlain(plain);
    }
    
    public HoldRichTextEntity(RichText rt) {
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
