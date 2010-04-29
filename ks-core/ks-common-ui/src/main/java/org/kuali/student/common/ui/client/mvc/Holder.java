/*
 * Copyright 2009 The Kuali Foundation Licensed under the
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
package org.kuali.student.common.ui.client.mvc;

/**
 * 
 * Used to hold a reference to an object in cases where the declared reference needs to be final.
 * For example, when using anonymous classes for asynchronous callbacks, a Holder can be used for
 * a mutable value within the callback.
 * 
 * @author Kuali Student Team
 *
 */
public class Holder<T extends Object> {
    private T value = null;
    public Holder() {
    	super();
    }
    public Holder(T value) {
    	this.value = value;
    }
    public T get() {
        return value;
    }
    public void set(T value) {
        this.value = value;
    }
}
