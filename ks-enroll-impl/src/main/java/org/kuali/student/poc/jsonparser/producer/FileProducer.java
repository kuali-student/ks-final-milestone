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

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * This class converts a JSON file into a producer so it can be tokenized/parsed
 *
 * @author Kuali Student Team
 */
public class FileProducer implements BaseProducer {
    private Scanner reader;
    private String thisLine, nextLine;
    private int row, column; // Peek at this
    private boolean done;

    public FileProducer(String filePath) {
        try {
            final String dir = System.getProperty("user.dir");
            System.err.println("current dir = " + dir);
            reader = new Scanner(new FileInputStream(filePath));
            done = false;
            if (reader.hasNextLine()) {
                thisLine = reader.nextLine();
                if (reader.hasNextLine()) {
                    nextLine = reader.nextLine();
                }
                row = 0;
                column = 0;
            } else {
                done = true;
            }
        } catch (FileNotFoundException e) {
            reader = null;
            done = true;
        }
    }

    @Override
    public boolean hasNext() {
        return !done;
    }

    @Override
    public char peek() {
        if (done) {
            throw new IndexOutOfBoundsException();
        }

        if (column >= thisLine.length()) {
            // Return a newline
            return '\n';
        }
        return thisLine.charAt(column);
    }

    @Override
    public boolean peekMatch(String str) {
        return thisLine.indexOf(str, column) == column;
    }

    @Override
    public char consume() {
        if (done) {
            throw new IndexOutOfBoundsException();
        }
        char result = peek();
        if (nextLine == null && column == thisLine.length() - 1) {
            // Last character of the file
            done = true;
        } else if (column >= thisLine.length()) {
            // next line must not be null
            thisLine = nextLine;
            row++;
            column = 0;
            if (reader.hasNextLine()) {
                nextLine = reader.nextLine();
            } else {
                nextLine = null;
            }
        } else {
            // Simple case--move the column
            column++;
        }
        return result;
    }

    @Override
    public String consume(int num) throws IndexOutOfBoundsException {
        if (num < 1) {
            throw new IndexOutOfBoundsException(num + " is too small a parameter");
        }
        StringBuilder buf = new StringBuilder();
        for (int i = 0; i < num; i++) {
            buf.append(consume());
        }
        String result = buf.toString();
        return result;
    }

    @Override
    public int getRow() {
        if (done) {
            throw new IndexOutOfBoundsException();
        }
        return row;
    }

    @Override
    public int getColumn() {
        if (done) {
            throw new IndexOutOfBoundsException();
        }
        return column;
    }
}
