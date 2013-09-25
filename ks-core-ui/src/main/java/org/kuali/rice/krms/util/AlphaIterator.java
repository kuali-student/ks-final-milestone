/**
 * Copyright 2005-2013 The Kuali Foundation
 *
 * Licensed under the Educational Community License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.opensource.org/licenses/ecl2.php
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.kuali.rice.krms.util;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author Kuali Student Team
 */
public class AlphaIterator implements Serializable, Iterator {

    private List<Inner> list = new ArrayList<Inner>();
    private String prefix;

    public AlphaIterator(String prefix){
        super();
        this.prefix = prefix;
    }

    @Override
    public boolean hasNext() {
        return true;
    }

    @Override
    public Object next() {

        //Add an initial value to the list
        if (list.isEmpty()) {
            list.add(new Inner());
        } else {

            //Increment the value
            for (int i = list.size() - 1; i >= 0; i--) {
                Inner in = list.get(i);
                if (in.increment()>0) {
                    break;
                } else {
                    in.reset();
                    if (i == 0) {
                        list.add(new Inner());
                    }
                }
            }
        }

        //Create the return string
        String returnValue = "";
        for (Inner in : list) {
            returnValue = returnValue + (char)in.current();
        }

        return prefix + returnValue;
    }

    @Override
    public void remove() {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    private class Inner implements Serializable{

        private final static int START = 'A';
        private final static int END = 'Z';

        private int value;

        public Inner() {
            value = START;
        }

        public void reset() {
            value = START;
        }

        public int current() {
            return value;
        }

        public int increment() {
            if (value == END) {
                return -1;
            } else {
                return value++;
            }
        }

    }
}
