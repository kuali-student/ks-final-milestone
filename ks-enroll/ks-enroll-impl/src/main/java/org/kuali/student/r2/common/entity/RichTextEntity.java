package org.kuali.student.r2.common.entity;

import javax.persistence.Column;
import javax.persistence.Inheritance;
import javax.persistence.MappedSuperclass;
import javax.persistence.InheritanceType;

import org.kuali.student.common.entity.KSEntityConstants;

@MappedSuperclass
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class RichTextEntity {
    @Column(name = "PLAIN",length=KSEntityConstants.LONG_TEXT_LENGTH)
    private String plain;
    
    @Column(name = "FORMATTED",length=KSEntityConstants.LONG_TEXT_LENGTH)
    private String formatted;

    public String getPlain() {
        return plain;
    }

    public void setPlain(String plain) {
        this.plain = plain;
    }

    public String getFormatted() {
        return formatted;
    }

    public void setFormatted(String formatted) {
        this.formatted = formatted;
    }
}
