/**
 * Copyright 2012 The Kuali Foundation Licensed under the
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
 * Created by Charles on 11/15/12
 */
package org.kuali.student.enrollment.class2.courseoffering.refdata;

import org.apache.log4j.Logger;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

/**
 * This class //TODO ...
 *
 * @author Kuali Student Team
 */
public class BasicCSVParser {
    private static final Logger LOGGER = Logger.getLogger(BasicCSVParser.class);

    /**
     * Assumes splitting each row based on
     * @param filename
     * @return
     * @throws FileNotFoundException
     */
    public static SpreadsheetData parseSimpleCSV(String filename) throws FileNotFoundException, InvalidParameterException {
        if (filename == null) {
            return null;
        } else if (!filename.toLowerCase().endsWith(".csv")) {
            LOGGER.warn(filename +  " does not end in .csv");
            return null;
        }
        File file = new File(filename);
        Scanner scanner = new Scanner(file);
        SpreadsheetData data = new SpreadsheetData();
        boolean hasProcessedHeader = false;
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            if (line.trim().isEmpty()) {
                continue; // Shouldn't happen, but doesn't hurt
            }
            String[] row = line.split(",");
            List<String> rowList = new ArrayList<String>(Arrays.asList(row));
            for (int i = 0; i < rowList.size(); i++) {
                rowList.set(i, rowList.get(i).trim());
            }
            if (!hasProcessedHeader) {
                data.addHeaders(rowList);
                hasProcessedHeader = true;
            } else {
                data.addRow(rowList);
            }
        }
        return data;
    }
}
