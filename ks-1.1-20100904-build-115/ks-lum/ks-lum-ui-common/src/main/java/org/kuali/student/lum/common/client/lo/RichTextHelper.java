package org.kuali.student.lum.common.client.lo;

import org.kuali.student.core.assembly.data.Data;
import org.kuali.student.core.assembly.helper.PropertyEnum;

public class RichTextHelper {
    private Data data;

    public enum Properties implements PropertyEnum {
        PLAIN("plain"),
        FORMATTED("formatted");
        private final String key;

        private Properties(final String key) {
            this.key = key;
        }

        @Override
        public String getKey() {
            return this.key;
        }
    }

    public RichTextHelper() {
        data = new Data();
    }

    public RichTextHelper(Data data) {
        this.data = data;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public void setPlain(String plain) {
        data.set(Properties.PLAIN.getKey(), plain);
    }

    public String getPlain() {
        return (String) data.get(Properties.PLAIN.getKey());
    }

    public void setFormatted(String formatted) {
        data.set(Properties.FORMATTED.getKey(), formatted);
    }

    public String getFormatted() {
        return (String) data.get(Properties.FORMATTED.getKey());
    }
}