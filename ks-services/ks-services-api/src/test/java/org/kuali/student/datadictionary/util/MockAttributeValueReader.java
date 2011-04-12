/*
 * Copyright 2011 The Kuali Foundation
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
package org.kuali.student.datadictionary.util;


import java.util.List;
import org.kuali.rice.kns.datadictionary.exception.AttributeValidationException;
import org.kuali.rice.kns.datadictionary.validation.AttributeValueReader;
import org.kuali.rice.kns.datadictionary.validation.capability.Constrainable;

public class MockAttributeValueReader implements AttributeValueReader
{

    @Override
    public String getAttributeName() {
        return "test";
    }

    @Override
    public List<String> getCleanSearchableValues(String attributeName) throws AttributeValidationException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Constrainable getDefinition(String attributeName) {
       return null;
    }

    @Override
    public List<Constrainable> getDefinitions() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Constrainable getEntry() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public String getEntryName() {
        return "test";
    }

    @Override
    public String getLabel(String attributeName) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Object getObject() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public String getPath() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Class<?> getType(String attributeName) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public <X> X getValue() throws AttributeValidationException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public <X> X getValue(String attributeName) throws AttributeValidationException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void setAttributeName(String attributeName) {
        throw new UnsupportedOperationException("Not supported yet.");
    }


}
