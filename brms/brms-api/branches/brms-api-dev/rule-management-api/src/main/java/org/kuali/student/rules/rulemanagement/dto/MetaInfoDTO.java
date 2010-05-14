/*
 *
 * Licensed under the Educational Community License, Version 1.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.opensource.org/licenses/ecl1.php
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.kuali.student.rules.rulemanagement.dto;

import java.io.Serializable;
import java.util.Date;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

/**
 * Contains meta information about any entity 
 * 
 * @author Kuali Student Team (kamal.kuali@gmail.com)
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class MetaInfoDTO implements Serializable{

    @XmlElement
    private Date createTime;

    @XmlElement
    private String createID;
    
    @XmlElement
    private String createComment;
    
    @XmlElement
    private Date updateTime;
    
    @XmlElement
    private String updateID;
    
    @XmlElement
    private String updateComment;

    /**
     * @return the createTime
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * @param createTime the createTime to set
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * @return the createID
     */
    public String getCreateID() {
        return createID;
    }

    /**
     * @param createID the createID to set
     */
    public void setCreateID(String createID) {
        this.createID = createID;
    }

    /**
     * @return the createComment
     */
    public String getCreateComment() {
        return createComment;
    }

    /**
     * @param createComment the createComment to set
     */
    public void setCreateComment(String createComment) {
        this.createComment = createComment;
    }

    /**
     * @return the updateTime
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * @param updateTime the updateTime to set
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * @return the updateID
     */
    public String getUpdateID() {
        return updateID;
    }

    /**
     * @param updateID the updateID to set
     */
    public void setUpdateID(String updateID) {
        this.updateID = updateID;
    }

    /**
     * @return the updateComment
     */
    public String getUpdateComment() {
        return updateComment;
    }

    /**
     * @param updateComment the updateComment to set
     */
    public void setUpdateComment(String updateComment) {
        this.updateComment = updateComment;
    }    
}
