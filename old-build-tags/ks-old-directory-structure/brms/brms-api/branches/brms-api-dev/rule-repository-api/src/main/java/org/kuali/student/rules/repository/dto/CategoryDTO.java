/*
 * Copyright 2007 The Kuali Foundation
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
package org.kuali.student.rules.repository.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement
public class CategoryDTO implements java.io.Serializable {

    /** Class serial version uid */
    private static final long serialVersionUID = 1L;
    
    /** Category name */
	@XmlAttribute
    private String name;
    /** Category path */
	@XmlElement
    private String path;

    public CategoryDTO() {}
    
    /**
     * Constructs a new category.
     * 
     * @param name Category name
     * @param path Category path
     */
    public CategoryDTO(String name, String path) {
        this.name = name;
        this.path = path;
    }

    /**
     * Gets the category name.
     * 
     * @see org.kuali.student.rules.repository.rule.Category#getName()
     */
    public String getName() {
        return this.name;
    }

    /**
     * Gets the category path.
     * 
     * @see org.kuali.student.rules.repository.rule.Category#getPath()
     */
    public String getPath() {
        return this.path;
    }
}
