package org.kuali.student.common.ui.client.configurableui.demo;

import org.kuali.student.core.dto.Idable;

public class Department implements Idable{
    private String name;
    private String id;
    @Override
    public String getId() {

        return id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
        
    }

}
