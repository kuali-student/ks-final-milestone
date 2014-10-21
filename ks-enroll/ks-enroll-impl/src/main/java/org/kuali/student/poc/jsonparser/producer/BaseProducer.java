/**
 * Copyright 2014 The Kuali Foundation Licensed under the
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
 * Created by Charles on 8/1/2014
 */
package org.kuali.student.poc.jsonparser.producer;

/**
 * An interface to represent something that produces characters for parsing
 *
 * @author Kuali Student Team
 */
public interface BaseProducer {
    // Any more characters left?
    boolean hasNext();
    // Look at the next character without consuming
    char peek();
    // Does str match current position?  Only valid across a single line
    boolean peekMatch(String str);
    // Consume the next character
    char consume() throws IndexOutOfBoundsException;
    // Consume num chars where num > 0
    String consume(int num) throws IndexOutOfBoundsException;
    // Current row
    int getRow();
    // Current column
    int getColumn();
}
