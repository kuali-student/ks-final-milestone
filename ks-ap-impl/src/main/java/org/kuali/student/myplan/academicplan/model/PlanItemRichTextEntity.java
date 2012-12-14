package org.kuali.student.myplan.academicplan.model;

import org.kuali.student.r2.common.dto.RichTextInfo;
import org.kuali.student.r2.common.entity.RichTextEntity;
import org.kuali.student.r2.common.infc.RichText;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Plan item comment.
 */
@Entity
@Table(name = "KSPL_LRNG_PI_RICH_TEXT")
public class PlanItemRichTextEntity extends RichTextEntity {

    public PlanItemRichTextEntity() {}

    public PlanItemRichTextEntity(String plain, String formatted){
        this.setFormatted(formatted);
        this.setPlain(plain);
    }

    public PlanItemRichTextEntity(RichText rt) {
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
