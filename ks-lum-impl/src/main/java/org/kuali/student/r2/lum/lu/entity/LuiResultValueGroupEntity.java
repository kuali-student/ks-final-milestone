package org.kuali.student.r2.lum.lu.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created with IntelliJ IDEA.
 * User: swedev
 * Date: 11/18/12
 * Time: 9:51 PM
 * To change this template use File | Settings | File Templates.
 */

@Entity
@Table(name = "KSEN_LUI_RESULT_VAL_GRP")
public class LuiResultValueGroupEntity {

    @Id
    @Column(name = "LUI_ID")
    private String luiId;
    @Column(name = "RESULT_VAL_GRP_ID")
    private String rvgId;

    public String getLuiId() {
        return luiId;
    }

    public void setLuiId(String luiId) {
        this.luiId = luiId;
    }

    public String getRvgId() {
        return rvgId;
    }

    public void setRvgId(String rvgId) {
        this.rvgId = rvgId;
    }
}
