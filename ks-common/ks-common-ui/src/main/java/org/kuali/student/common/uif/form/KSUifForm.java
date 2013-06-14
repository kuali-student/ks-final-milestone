/**
 * Copyright 2013 The Kuali Foundation Licensed under the
 * Educational Community License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License. You may
 * obtain a copy of the License at
 *
 * http://www.osedu.org/licenses/ECL-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an "AS IS"
 * BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing
 * permissions and limitations under the License.
 *
 * Created by David Yin on 1/16/13
 */
package org.kuali.student.common.uif.form;

import org.apache.commons.lang.StringUtils;
import org.kuali.rice.krad.web.form.UifFormBase;
import org.kuali.student.r2.common.dto.MetaInfo;
import org.kuali.student.r2.common.util.date.DateFormatters;
import org.kuali.student.r2.common.util.date.KSDateTimeFormatter;

/**
 * KS form class that extends UifFormBase. It contains properties that are shared
 * among all the KS forms.
 *
 * @author Kuali Student Team
 */
public class KSUifForm extends UifFormBase {

    MetaInfo meta = new MetaInfo();
    KSDateTimeFormatter defaultFormatter = DateFormatters.COURSE_OFFERING_VIEW_HELPER_DATE_TIME_FORMATTER;

    public KSUifForm() {
        super();
    }

    public MetaInfo getMeta() {
        return meta;
    }

    public void setMeta(MetaInfo meta) {
        this.meta = meta;
    }

    public KSDateTimeFormatter getDefaultFormatter() {
        return defaultFormatter;
    }

    public void setDefaultFormatter(KSDateTimeFormatter defaultFormatter) {
        this.defaultFormatter = defaultFormatter;
    }

    public String getVersionInd(){
        if (StringUtils.isEmpty(meta.getVersionInd())){
            return "";
        } else {
            return  meta.getVersionInd();
        }

    }

    public String getCreateTime(){
        if(meta.getCreateTime() == null){
            return "";
        } else {
            return getDefaultFormatter().format(meta.getCreateTime());
        }
    }

    public String getUpdateTime(){
        if(meta.getUpdateTime() == null){
            return "";
        } else {
            return getDefaultFormatter().format(meta.getUpdateTime());
        }
    }

    public String getCreateId(){
        if(StringUtils.isEmpty(meta.getCreateId() )){
            return "";
        } else {
            return meta.getCreateId();
        }

    }
    public String getUpdateId(){
        if(StringUtils.isEmpty(meta.getUpdateId() )){
            return "";
        } else {
            return meta.getUpdateId();
        }

    }

}
