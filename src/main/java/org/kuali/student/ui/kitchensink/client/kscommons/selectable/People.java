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
package org.kuali.student.ui.kitchensink.client.kscommons.selectable;
 
import java.util.ArrayList;
import java.util.List;

import com.google.gwt.gen2.table.client.MutableTableModel;
import com.google.gwt.gen2.table.client.TableModel.Callback;
import com.google.gwt.gen2.table.client.TableModelHelper.Request;
import com.google.gwt.gen2.table.client.TableModelHelper.SerializableResponse;
import com.google.gwt.user.client.Random;

/**
 * This is a description of what this class does - Gary Struthers don't forget to fill this in. 
 * 
 * @author Kuali Student Team (gstruthers@berkeley.edu)
 *
 */
public class People extends MutableTableModel<Person> {
    private List<Person> persons = new ArrayList<Person>(); 
    private int fromIndex = 0;
    /**
     * A boolean indicating that we should throw an error.
     */
    private boolean errorMode = false;
    /**
     * A boolean indicating that we should return 0 rows in the response.
     */
    private boolean zeroMode = false;
    
    public People() {
        try {
            initLocal();
        }catch(Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * Override that can optionally throw an error.
     */
    @Override
    public void requestRows(final Request request,
            final Callback<Person> callback) {
        List<Person> staffers = null;
        if (errorMode) {
            // Return an error
            callback.onFailure(new Exception("An error has occured."));
        } else if (zeroMode) {
            // Return an empty result
            staffers = new ArrayList<Person>(0);
            callback.onRowsReady(request, new SerializableResponse<Person>(staffers));
        } else {
            // Generate data locally
            int numRows = request.getNumRows();
            //fromIndex = request.getStartRow(); This disables column sort, bug?
            if((fromIndex + numRows) >= persons.size()) {
                fromIndex = 0;
            } 
            int toIndex = fromIndex + numRows;
            
            staffers = new ArrayList<Person>();
            for(int i = fromIndex; i < toIndex; i++) {
                Person e = persons.get(i);
                staffers.add(e);
            }
            fromIndex = toIndex;
            SerializableResponse<Person> response = new SerializableResponse<Person>(
                    staffers);
            callback.onRowsReady(request, response);
        }
    }

    @Override
    protected boolean onRowInserted(int beforeRow) {
      return true;
    }

    @Override
    protected boolean onRowRemoved(int row) {
      return true;
    }

    @Override
    protected boolean onSetRowValue(int row, Person rowValue) {
      return true;
    }
    protected void initLocal() {
        persons.add(new Person("James","Dudeck"));
        persons.add(new Person("Ruth","Schleifer"));
        persons.add(new Person("Russell","Low"));
        persons.add(new Person("Cathy","Dew"));
        persons.add(new Person("Tim","Heidinger"));
        persons.add(new Person("Sara","Quigley"));
        persons.add(new Person("JR","Schulden"));
        persons.add(new Person("Yu-Tin","Kuo"));
        persons.add(new Person("Randy","Ballew"));
        persons.add(new Person("Thomas","O'Brian"));
        persons.add(new Person("Gary","Struthers"));
        persons.add(new Person("George","Atala"));
        persons.add(new Person("Rachel","Hollowgrass"));
        persons.add(new Person("Kristina","Batiste"));
        persons.add(new Person("Joan","Shao"));
        persons.add(new Person("Johanna","Metzgar"));
        persons.add(new Person("Ian","Boston"));
        persons.add(new Person("Matt","Coombs"));
        persons.add(new Person("Charles","Jennings"));
        persons.add(new Person("Chris","Kirschenman"));
        persons.add(new Person("Chris","MacDannald"));
        persons.add(new Person("Brian","Smith"));
        persons.add(new Person("David","Elyea"));
        persons.add(new Person("Catherine","Mooney"));
        persons.add(new Person("Karen","Sea,"));
        persons.add(new Person("Melissa","Green"));
        persons.add(new Person("Tim","Martin"));
        persons.add(new Person("Rick","Burnette"));
        persons.add(new Person("Andy","Bucior"));
        persons.add(new Person("Kamal","Muthuswamy"));
        persons.add(new Person("Wil","Johnson"));
        persons.add(new Person("Derek","Dean"));
        persons.add(new Person("Ricky","Ratliff"));
        persons.add(new Person("Seth","Winerman"));
        persons.add(new Person("Norm","Wright"));
        persons.add(new Person("Scott","Thorne"));
        persons.add(new Person("JoAnne","Stevenson"));
        persons.add(new Person("Dean","Briggs"));
        persons.add(new Person("Brian","Canavan"));
        persons.add(new Person("Rajiv","Kaushik,"));
        persons.add(new Person("Paul","Luke"));
        persons.add(new Person("Gord","Uyeda"));
        persons.add(new Person("Cath","Fairlie"));
        persons.add(new Person("Brittney","Brokenshire"));
        persons.add(new Person("Doris","Yen"));
        persons.add(new Person("Heather","Johnson"));
        persons.add(new Person("Cindy","Nahm"));
        persons.add(new Person("Audrey","Lindsay"));
        persons.add(new Person("Leo","Fernig"));
        persons.add(new Person("Len","Carlsen"));
        persons.add(new Person("Zdenek","Zraly"));
        persons.add(new Person("Joe","Yin"));
        persons.add(new Person("George","Lindholm"));
        persons.add(new Person("Sherman","Tse"));
        persons.add(new Person("Jing","Cui"));
        persons.add(new Person("Chris","Eaton"));
        persons.add(new Person("Ginette","Vallee"));
        persons.add(new Person("Erina","James"));
        persons.add(new Person("Nancy","Low"));
        persons.add(new Person("Vivian","Luk"));
        persons.add(new Person("Dan","Symonds"));
        persons.add(new Person("Michael","Passarella George"));
        persons.add(new Person("Teddy","Wu"));
        persons.add(new Person("William","Spann"));
        persons.add(new Person("Scott","Gibson"));
        persons.add(new Person("Will","Gomes"));
        persons.add(new Person("Daniel","Epstein"));
        persons.add(new Person("Richard","Diaz"));
        persons.add(new Person("Larry","Symms"));
        persons.add(new Person("Stuart","Sim"));
        persons.add(new Person("Stuart","Sim"));
        persons.add(new Person("Scott","Shepherd"));
        persons.add(new Person("Glenn","Kirksey"));
        persons.add(new Person("Michelle","Appel"));
        persons.add(new Person("Paul","Roche"));
        persons.add(new Person("Dan","Beaty"));
        persons.add(new Person("Hilary","Sazama"));
        persons.add(new Person("Sarah","Bauder"));
        persons.add(new Person("UMCP","TBD"));
        persons.add(new Person("Darlene","LaBarbara"));
        persons.add(new Person("Tom","Vrana"));
        persons.add(new Person("Bill","Shirey"));
        persons.add(new Person("Bill","Yock"));
        persons.add(new Person("Bob","Jansson"));
        persons.add(new Person("Hugh","Parker"));
        persons.add(new Person("Debbie","Wiegand"));
        persons.add(new Person("Carol","Bershad"));
        persons.add(new Person("John","Drew"));
        persons.add(new Person("Jim","Tomlinson"));
        persons.add(new Person("William","Washington"));
        persons.add(new Person("Kevin","Pittman"));
        persons.add(new Person("UW","TBD"));
        persons.add(new Person("Steve","Barnhart"));
        persons.add(new Person("Ken","Servis"));
        persons.add(new Person("KC","Peterson"));
        persons.add(new Person("Asbed","Bedrosian"));
        persons.add(new Person("Matt","Bemis"));
        persons.add(new Person("Betty","Cowin"));
        persons.add(new Person("Cathy","Bonds")); 

    }

    /**
     * @return the persons
     */
    public List<Person> getStaff() {
        return persons;
    }
    /**
     * @return the errorMode
     */
    public boolean isErrorMode() {
        return errorMode;
    }
    /**
     * @return the zeroMode
     */
    public boolean isZeroMode() {
        return zeroMode;
    }
    /**
     * This overridden method returns the static size the table
     * 
     * @see com.google.gwt.gen2.table.client.TableModel#getRowCount()
     */
    @Override
    public int getRowCount() {
        // TODO return dynamic size when loading with RPC
        return persons.size();
    }
}

