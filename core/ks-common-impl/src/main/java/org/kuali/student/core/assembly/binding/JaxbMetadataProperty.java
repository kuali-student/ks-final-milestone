/*
 * Copyright 2007 The Kuali Foundation Licensed under the Educational Community License, Version 1.0 (the "License"); you may
 * not use this file except in compliance with the License. You may obtain a copy of the License at
 * http://www.opensource.org/licenses/ecl1.php Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
 * express or implied. See the License for the specific language governing permissions and limitations under the License.
 */
package org.kuali.student.core.assembly.binding;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.kuali.student.core.assembly.data.Metadata;

/**
 * Metadata property for jaxb map adapter
 * 
 * @author Kuali Student Team
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "property", propOrder = {"name", "metadata"})
public class JaxbMetadataProperty {
    
    @XmlAttribute
    String name;
    @XmlElement
    Metadata metadata;

    public JaxbMetadataProperty() {
        super();
    }

    /**
     * @param name
     * @param metadata
     */
    public JaxbMetadataProperty(String name, Metadata metadata) {
        super();
        this.name = name;
        this.metadata = metadata;
    }
}
