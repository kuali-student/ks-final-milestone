package org.kuali.student.enrollment.class2.courseoffering.model;

import org.kuali.student.r2.common.entity.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 * Created with IntelliJ IDEA.
 * User: swedev
 * Date: 12/7/12
 * Time: 1:10 PM
 * To change this template use File | Settings | File Templates.
 */

@Entity
@Table(name = "KSEN_CODE_GENERATOR_LOCKS", uniqueConstraints={
        @UniqueConstraint(columnNames={"CODE", "UNIQUE_KEY", "NAMESPACE"})}  )
public class CodeGeneratorLocksEntity extends BaseEntity {

    @Column(name = "CODE", nullable = false)
    String code;

    @Column(name = "UNIQUE_KEY", nullable = false)
    String uniqueKey;

    @Column(name = "NAMESPACE", nullable = false)
    String namespace;

    public CodeGeneratorLocksEntity() {
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getNamespace() {
        return namespace;
    }

    public void setNamespace(String namespace) {
        this.namespace = namespace;
    }

    public String getUniqueKey() {
        return uniqueKey;
    }

    public void setUniqueKey(String uniqueKey) {
        this.uniqueKey = uniqueKey;
    }
}
