package org.kuali.student.enrollment.lpr.model.usinginfc;

import javax.persistence.*;
import java.io.Serializable;
import org.kuali.student.common.infc.AttributeInfc;

/**
 * @author Igor
 */
@Entity
public class AttributeEntity implements AttributeInfc, Serializable {
    private static final long serialVersionUID = -8609058228324425537L;

    @Id
    @GeneratedValue
    private Long id;

    @Enumerated(EnumType.STRING)
    private String path;

    private String key;

    private String value;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPath() {
        return path;
    }

    public void setPath (String path) {
        this.path = path;
    }

    @Override
    public String getKey() {
        return key;
    }

    @Override
    public void setKey(String key) {
        this.key = key;
    }

    @Override
    public String getValue() {
        return value;
    }

    @Override
    public void setValue(String value) {
        this.value = value;
    }
}
