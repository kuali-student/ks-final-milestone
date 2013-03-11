package org.kuali.rice.krms.util;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: SW
 * Date: 2013/02/12
 * Time: 12:15 PM
 * To change this template use File | Settings | File Templates.
 */
public class AlphaIterator implements Serializable, Iterator {

    private List<Inner> list = new ArrayList<Inner>();

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

        return returnValue;
    }

    @Override
    public void remove() {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    private class Inner {

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
