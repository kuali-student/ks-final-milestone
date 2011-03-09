/*
* Copyright 2011 The Kuali Foundation
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
package org.kuali.student.common.infc;

import java.io.Serializable;
import java.util.Date;

public class MetaBean
        implements MetaInfc, Serializable {

    private static final long serialVersionUID = 1L;
    private String versionInd;

    /**
     * Set ????
     * <p/>
     * Type: String
     * <p/>
     * An indicator of the version of the thing being described with this meta
     * information. This is set by the service implementation and will be used to
     * determine conflicts in updates.
     */
    @Override
    public void setVersionInd(String versionInd) {
        this.versionInd = versionInd;
    }

    /**
     * Get ????
     * <p/>
     * Type: String
     * <p/>
     * An indicator of the version of the thing being described with this meta
     * information. This is set by the service implementation and will be used to
     * determine conflicts in updates.
     */
    @Override
    public String getVersionInd() {
        return this.versionInd;
    }

    private Date createTime;

    /**
     * Set ????
     * <p/>
     * Type: Date
     * <p/>
     * The date and time the thing being described with this meta information was last
     * updated
     */
    @Override
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * Get ????
     * <p/>
     * Type: Date
     * <p/>
     * The date and time the thing being described with this meta information was last
     * updated
     */
    @Override
    public Date getCreateTime() {
        return this.createTime;
    }

    private String createId;

    /**
     * Set ????
     * <p/>
     * Type: String
     * <p/>
     * The principal who created the thing being described with this meta information
     */
    @Override
    public void setCreateId(String createId) {
        this.createId = createId;
    }

    /**
     * Get ????
     * <p/>
     * Type: String
     * <p/>
     * The principal who created the thing being described with this meta information
     */
    @Override
    public String getCreateId() {
        return this.createId;
    }

    private Date updateTime;

    /**
     * Set ????
     * <p/>
     * Type: Date
     * <p/>
     * The date and time the thing being described with this meta information was last
     * updated
     */
    @Override
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * Get ????
     * <p/>
     * Type: Date
     * <p/>
     * The date and time the thing being described with this meta information was last
     * updated
     */
    @Override
    public Date getUpdateTime() {
        return this.updateTime;
    }

    private String updateId;

    /**
     * Set ????
     * <p/>
     * Type: String
     * <p/>
     * The principal who last updated the thing being described with this meta
     * information
     */
    @Override
    public void setUpdateId(String updateId) {
        this.updateId = updateId;
    }

    /**
     * Get ????
     * <p/>
     * Type: String
     * <p/>
     * The principal who last updated the thing being described with this meta
     * information
     */
    @Override
    public String getUpdateId() {
        return this.updateId;
    }
}

