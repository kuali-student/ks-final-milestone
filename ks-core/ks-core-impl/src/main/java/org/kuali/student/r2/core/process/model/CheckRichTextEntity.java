package org.kuali.student.r2.core.process.model;

import org.kuali.student.r2.common.dto.RichTextInfo;
import org.kuali.student.r2.common.entity.RichTextEntity;
import org.kuali.student.r2.common.infc.RichText;

import javax.persistence.Entity;
import javax.persistence.Table;


@Entity
@Table(name = "KSEN_CHECK_RICH_TEXT")
public class CheckRichTextEntity extends RichTextEntity {

    public CheckRichTextEntity() {}

    public CheckRichTextEntity(String plain, String formatted){
        this.setFormatted(formatted);
        this.setPlain(plain);
    }

    public CheckRichTextEntity(RichText rt) {
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