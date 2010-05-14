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
package org.kuali.student.rules.repository.rule;

/**
 * This class contains a single rule or rule set compilation errors. 
 * 
 * @author Kuali Student Team (len.kuali@googlegroups.com)
 *
 */
public class CompilerResult implements java.io.Serializable {
    
    /** Class serial version uid */
    private static final long serialVersionUID = 1L;

    /** Item UUID */
    private String itemUuid;
    /** Item name */
    private String itemName;
    /** Item format E.g. Drools drl */
    private String itemFormat;
    /** Item compilation error message */
    private String itemMessage;

    /**
     * Constructs a new compiler result.
     * 
     * @param uuid Item UUID
     * @param name Item name
     * @param format Item format
     * @param message Compilation error message
     */
    public CompilerResult(String uuid, String name, String format, String message) {
        this.itemUuid = uuid;
        this.itemName = name;
        this.itemFormat = format;
        this.itemMessage = message;
    }

    /**
     * Gets the item UUID.
     * 
     * @return Item UUID
     */
    public String getItemUuid() {
        return this.itemUuid;
    }

    /**
     * Gets the item name.
     * 
     * @return Item name
     */
    public String getItemName() {
        return this.itemName;
    }

    /**
     * Gets the item format. E.g. Drools DRL (drl)
     * 
     * @return Item format (e.g. drl)
     */
    public String getItemFormat() {
        return this.itemFormat;
    }

    /**
     * Gets the compilation error message.
     * 
     * @return Compilation error message
     */
    public String getMessage() {
        return this.itemMessage;
    }

    /**
     * @see java.lang.Object#toString()
     * @return Returns the UUID, name, format and error message
     */
    public String toString() {
        return 
            "\nUUID: " + this.itemUuid + 
            "\nName: " + this.itemName + 
            "\nFormat: " + this.itemFormat + 
            "\nMessage: " + this.itemMessage;
    }
}
