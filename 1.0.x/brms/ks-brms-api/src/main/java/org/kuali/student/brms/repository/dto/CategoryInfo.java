/**
 * Copyright 2010 The Kuali Foundation Licensed under the
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
 */

package org.kuali.student.brms.repository.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement
public class CategoryInfo implements java.io.Serializable {

    /** Class serial version uid */
    private static final long serialVersionUID = 1L;
    
    /** Category name */
	@XmlAttribute
    private String name;
    /** Category path */
	@XmlElement
    private String path;

    public CategoryInfo() {}
    
    /**
     * Constructs a new category.
     * 
     * @param name Category name
     * @param path Category path
     */
    public CategoryInfo(String name, String path) {
        this.name = name;
        this.path = path;
    }

    /**
     * Gets the category name.
     * 
     * @see org.kuali.student.brms.repository.rule.Category#getName()
     */
    public String getName() {
        return this.name;
    }

    /**
     * Gets the category path.
     * 
     * @see org.kuali.student.brms.repository.rule.Category#getPath()
     */
    public String getPath() {
        return this.path;
    }
}
