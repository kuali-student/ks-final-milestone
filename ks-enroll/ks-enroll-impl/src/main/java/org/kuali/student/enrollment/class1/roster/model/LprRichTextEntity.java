package org.kuali.student.enrollment.class1.roster.model;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.kuali.student.r2.common.dto.RichTextInfo;
import org.kuali.student.r2.common.entity.RichTextEntity;
import org.kuali.student.r2.common.infc.RichText;

@Entity
@Table(name = "KSEN_RICH_TEXT_T")
public class LprRichTextEntity extends RichTextEntity {

    public LprRichTextEntity() {}

    public LprRichTextEntity(String plain, String formatted) {
        this.setFormatted(formatted);
        this.setPlain(plain);
    }

    public LprRichTextEntity(RichText rt) {
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
