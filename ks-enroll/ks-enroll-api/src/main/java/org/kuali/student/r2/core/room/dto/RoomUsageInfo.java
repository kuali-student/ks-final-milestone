package org.kuali.student.r2.core.room.dto;

import org.kuali.student.r2.core.room.infc.RoomUsage;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @Version 2.0
 * @Author Sri komandur@uw.edu
 */
public class RoomUsageInfo implements RoomUsage, Serializable {

    private String id;
    private Integer preferredCapacity;
    private Integer hardCapacity;
    private Integer examCapacity;
    private List<String> usageTypeKeys;
    private List<String> layoutTypeKeys;

    public RoomUsageInfo() {

    }

    public RoomUsageInfo(RoomUsage roomUsage) {
        if (null != roomUsage) {
            this.preferredCapacity = roomUsage.getPreferredCapacity();
            this.hardCapacity = roomUsage.getHardCapacity();
            this.examCapacity = roomUsage.getExamCapacity();
            if (null != roomUsage.getUsageTypeKeys()) {
                this.usageTypeKeys = new ArrayList<String>(roomUsage.getUsageTypeKeys());
            }
            if (null != roomUsage.getLayoutTypeKeys()) {
                this.layoutTypeKeys = new ArrayList<String>(roomUsage.getLayoutTypeKeys());
            }
        }
    }

    @Override
    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }
    
    @Override
    public Integer getPreferredCapacity() {
        return this.preferredCapacity;
    }

    public void setPreferredCapacity(Integer preferredCapacity) {
        this.preferredCapacity = preferredCapacity;
    }

    @Override
    public Integer getHardCapacity() {
        return this.hardCapacity;
    }

    public void setHardCapacity(Integer hardCapacity) {
        this.hardCapacity = hardCapacity;
    }

    @Override
    public Integer getExamCapacity() {
        return this.examCapacity;
    }

    public void setExamCapacity(Integer examCapacity) {
        this.examCapacity = examCapacity;
    }

    @Override
    public List<String> getUsageTypeKeys() {
        return this.usageTypeKeys;
    }

    public void setUsageTypeKeys(List<String> usageTypeKeys) {
        this.usageTypeKeys = usageTypeKeys;
    }

    @Override
    public List<String> getLayoutTypeKeys() {
        return this.layoutTypeKeys;
    }

    public void setLayoutTypeKeys(List<String> layoutTypeKeys) {
        this.layoutTypeKeys = layoutTypeKeys;
    }

}
