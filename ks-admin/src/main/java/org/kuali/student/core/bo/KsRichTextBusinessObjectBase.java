package org.kuali.student.core.bo;

import javax.persistence.Column;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class KsRichTextBusinessObjectBase extends KsBusinessObjectBase {
    
    private static final long serialVersionUID = -8080006030631944540L;

    @Column(name = "PLAIN")
    private String plain;

    @Column(name = "FORMATTED")
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
