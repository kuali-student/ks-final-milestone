/**
 * Copyright 2013 The Kuali Foundation Licensed under the
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
 *
 */
package org.kuali.student.enrollment.class2.scheduleofclasses.sort;

import java.util.Comparator;

/**
 * Interface which extends from {@link Comparator} and provides an option for reverse sorting.
 *
 * @author Kuali Student Team
 */
public interface ComparatorElement<T> extends Comparator<T>{

    public void setReverseSearch(boolean reverseSearch);

    public boolean isReverseSearch();
}
