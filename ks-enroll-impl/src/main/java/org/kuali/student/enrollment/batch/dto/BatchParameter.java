package org.kuali.student.enrollment.batch.dto;

/**
 * Created by SW Genis on 2014/03/25.
 */
public class BatchParameter {

    private String key;
    private String value;

    public BatchParameter(String key, String value){
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

}
