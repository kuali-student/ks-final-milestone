package org.kuali.student.r2.common.messenger;

import java.io.Serializable;

/**
 * Created by SW Genis on 2014/02/28.
 */
public class UserMessage implements Serializable {

    private String key;
    private String parameters;
    private String theme;
    private boolean hasMore;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getParameters() {
        return parameters;
    }

    public void setParameters(String parameters) {
        this.parameters = parameters;
    }

    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }

    public boolean hasMore() {
        return hasMore;
    }

    public void setHasMore(boolean hasMore) {
        this.hasMore = hasMore;
    }
}
