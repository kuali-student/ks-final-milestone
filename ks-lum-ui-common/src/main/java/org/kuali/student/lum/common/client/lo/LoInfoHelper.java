/*
 * Copyright 2010 The Kuali Foundation
 *
 * Licensed under the Educational Community License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may	obtain a copy of the License at
 *
 * 	http://www.osedu.org/licenses/ECL-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.kuali.student.lum.common.client.lo;


import org.kuali.student.r1.common.assembly.data.Data;
import org.kuali.student.r1.common.assembly.helper.PropertyEnum;


public class LoInfoHelper {
    private Data data;

    public enum Properties implements PropertyEnum {
        NAME("name"),
        DESCR("descr"),
        ID("id"),
        SEQUENCE("sequence"),
        META("meta");

        private final String key;

        private Properties(final String key) {
            this.key = key;
        }

        @Override
        public String getKey() {
            return this.key;
        }
    }

    public LoInfoHelper() {
        data = new Data();
    }

    public LoInfoHelper(Data data) {
        this.data = data;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public void setName(String name) {
        data.set(LoInfoHelper.Properties.NAME.getKey(), name);
    }

    public String getName() {
        return (String) data.get(Properties.NAME.getKey());
    }

    public void setDescr(Data descData) {
        HelperUtil.setDataField(LoInfoHelper.Properties.DESCR, data, descData);
    }

    public Data getDescr() {
        return HelperUtil.getDataField(LoInfoHelper.Properties.DESCR, data);
    }

    public void setId(String id) {
        data.set(LoInfoHelper.Properties.ID.getKey(), id);
    }

    public String getId() {
        return (String) data.get(LoInfoHelper.Properties.ID.getKey());
    }

    public void setSequence(String sequence) {
        data.set(LoInfoHelper.Properties.SEQUENCE.getKey(), sequence);
    }

    public String getSequence() {
        return (String) data.get(LoInfoHelper.Properties.SEQUENCE.getKey());
    }

    public void setMeta(Data metaData) {
        HelperUtil.setDataField(LoInfoHelper.Properties.META, data, metaData);
    }

    public Data getMeta() {
        return HelperUtil.getDataField(LoInfoHelper.Properties.META, data);
    }

}