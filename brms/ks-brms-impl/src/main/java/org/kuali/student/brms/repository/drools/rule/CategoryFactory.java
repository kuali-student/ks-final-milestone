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

package org.kuali.student.brms.repository.drools.rule;

import org.kuali.student.brms.repository.rule.Category;

public class CategoryFactory {
    /** Category factory instance */
    private final static CategoryFactory factory = new CategoryFactory();

    /**
     * Private constructor
     */
    private CategoryFactory() { }

    /**
     * Gets an instance of this class.
     * 
     * @return A factory
     */
    public static CategoryFactory getInstance() {
        return factory;
    }

    /**
     * Creates a new category.
     * 
     * @param name Category name
     * @param description Category description
     * @return A new category
     */
    public Category createDroolsCategory(final String name, final String path) {
        return new DroolsCategoryImpl(name, path);
    }
}
