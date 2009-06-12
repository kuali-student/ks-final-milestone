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
package org.kuali.student.ui.kitchensink.client.kscommons.dto;


import java.util.ArrayList;
import java.util.List;

import com.google.gwt.gen2.table.client.ColumnDefinition;

/**
 * This is a description of what this class does - Gary Struthers don't forget to fill this in. 
 * 
 * @author Kuali Student Team (gstruthers@berkeley.edu)
 *
 */
public class PersonColumnDefinitions {
    List<ColumnDefinition<Person,?>> columnDefs;
    public PersonColumnDefinitions() {
        columnDefs = new ArrayList<ColumnDefinition<Person,?>>();
    }

    public PersonColumnDefinitions(String firstNameHeader,String lastNameHeader) {
        columnDefs = new ArrayList<ColumnDefinition<Person,?>>();
        columnDefs.add(new FirstNameColumnDefinition(firstNameHeader));
        columnDefs.add(new LastNameColumnDefinition(lastNameHeader));

    }
    
    public FirstNameColumnDefinition getFirstNameColumnDefinition(String colHeader) {
        return new FirstNameColumnDefinition(colHeader);
    }
    public LastNameColumnDefinition getLastNameColumnDefinition(String colHeader) {
        return new LastNameColumnDefinition(colHeader);
    }
}
