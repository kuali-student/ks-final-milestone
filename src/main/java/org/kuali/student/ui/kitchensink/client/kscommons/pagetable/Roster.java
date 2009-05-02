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
package org.kuali.student.ui.kitchensink.client.kscommons.pagetable;
 
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
public class Roster extends MutableTableModel<Staffer> {
    private final String header ="Institution,Name,FTE %,Comment,Primary Project Role,Primary Team,Sub Team Name";
    private List<Staffer> staff = new ArrayList<Staffer>(); 

    private String[] splitHeader;
    private int fromIndex = 0;
    /**
     * A boolean indicating that we should throw an error.
     */
    private boolean errorMode = false;
    /**
     * A boolean indicating that we should return 0 rows in the response.
     */
    private boolean zeroMode = false;
    
    public Roster() {
        splitHeader = header.split(",");
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
            final Callback<Staffer> callback) {
        List<Staffer> staffers = null;
        if (errorMode) {
            // Return an error
            callback.onFailure(new Exception("An error has occured."));
        } else if (zeroMode) {
            // Return an empty result
            staffers = new ArrayList<Staffer>(0);
            callback.onRowsReady(request, new SerializableResponse<Staffer>(staffers));
        } else {
            // Generate data locally
            int numRows = request.getNumRows();
            //fromIndex = request.getStartRow(); This disables column sort, bug?
            if((fromIndex + numRows) >= staff.size()) {
                fromIndex = 0;
            } 
            int toIndex = fromIndex + numRows;
            
            //staffers = staff.subList(fromIndex, toIndex);Compile error only during mvn install 
            staffers = new ArrayList<Staffer>();
            for(int i = fromIndex; i < toIndex; i++) {
                Staffer e = staff.get(i);
                staffers.add(e);
            }
            fromIndex = toIndex;
            SerializableResponse<Staffer> response = new SerializableResponse<Staffer>(
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
    protected boolean onSetRowValue(int row, Staffer rowValue) {
      return true;
    }
    protected void initLocal() {
        staff.add(new Staffer("Berkeley,James Dudek,100%,,Business Analyst,Use Case,Use Case,"));
        staff.add(new Staffer("Berkeley,Ruth Schleifer,50%,,Business Analyst,Use Case,Use Case,"));
        staff.add(new Staffer("Berkeley,Russell Low,100%,start October 50%, 100% Nov,SME,Use Case,Use Case,"));
        staff.add(new Staffer("Berkeley,Cathy Dew,70%,,Business Analyst,Services,Services,"));
        staff.add(new Staffer("Berkeley,Tim Heidinger,100%,,Team Lead,Functional Council,Functional Council,"));
        staff.add(new Staffer("Berkeley,Sara Quigley,100%,,Data Analyst,Services,Services,"));
        staff.add(new Staffer("Berkeley,JR Schulden,20%,,Steering Committee,Steering Committee,Steering Committee,"));
        staff.add(new Staffer("Berkeley,Yu-Tin Kuo,100%,,Developer,Services,Services,"));
        staff.add(new Staffer("Berkeley,Randy Ballew,100%,,Technical Architect,Development,QA Deployment,"));
        staff.add(new Staffer("Berkeley,Thomas O'Brian,100%,,Developer,Application Design,Application Design,"));
        staff.add(new Staffer("Berkeley,Gary Struthers,100%,,Developer,Development,T1: LU - UI,"));
        staff.add(new Staffer("Berkeley,George Atala,100%,,Technical Architect,Development,QA Deployment,"));
        staff.add(new Staffer("Berkeley,Rachel Hollowgrass,100%,,UX Expert,User Experience ,User Experience,"));
        staff.add(new Staffer("Berkeley,Kristina Batiste,100%,,User Experience,User Experience ,User Experience,"));
        staff.add(new Staffer("Berkeley,Joan Shao,50%,until Dec,Communications Coord,Comms/Doc,Comms/Doc,"));
        staff.add(new Staffer("Berkeley,Johanna Metzgar,100%,staying with R1,SME,Use Case,Use Case,"));
        staff.add(new Staffer("Cambridge,Ian Boston,10%,Not in budget document,Technical Architect,External Review,External Review,"));
        staff.add(new Staffer("Delta,Matt Coombs,75%,,Team Lead,Use Case,Use Case,"));
        staff.add(new Staffer("Delta,Charles Jennings,25%,,Lead SME,Functional Council,Functional Council,"));
        staff.add(new Staffer("Delta,Chris Kirschenman,75%,on RICE now --50% KS in Feb , 75% by May,Developer,Development,T2: LU -  Services,"));
        staff.add(new Staffer("Delta,Chris MacDannald,100%,,Developer,Application Design,Application Design,"));
        staff.add(new Staffer("Delta,Brian Smith,50%,,Developer,Development,T1: LU - UI,"));
        staff.add(new Staffer("Delta,David Elyea,75%,on RICE now --50% KS in Feb , 75% by May,Developer,Development,T2: LU -  Services,"));
        staff.add(new Staffer("Delta,Catherine Mooney,10%,FC Only ,Lead SME,Inst. Signoff,Use Case,"));
        staff.add(new Staffer("Delta,Karen Sea,80%,starting in Dec,Lead SME,Use Case,Use Case,"));
        staff.add(new Staffer("Delta,Melissa Green,25%,r1 institution review, r2,SME,Use Case,Use Case,"));
        staff.add(new Staffer("FSU,Tim Martin,100%,,Lead SME,Use Case,Use Case,"));
        staff.add(new Staffer("FSU,Rick Burnette,100%,,Team Lead,Services,Services,"));
        staff.add(new Staffer("FSU,Andy Bucior,100%,,Services Architect,Services,Services,"));
        staff.add(new Staffer("FSU,Kamal Muthuswamy,100%,,Developer,Development,T3: Course/Pgm Rules,"));
        staff.add(new Staffer("FSU,Wil Johnson,100%,,Lead Developer,Development,T1: LU - UI,"));
        staff.add(new Staffer("FSU,Derek Dean,100%,,Developer,Development,T2: LU -  Services,"));
        staff.add(new Staffer("FSU,Ricky Ratliff,0%,On-leave,Developer,Development,T1: LU - UI,"));
        staff.add(new Staffer("MIT,Seth Winerman,75%,,Business Analyst,Application Design,Application  Design,"));
        staff.add(new Staffer("MIT,Norm Wright,50%,,Business Analyst,Application Design,Application Design,"));
        staff.add(new Staffer("MIT,Scott Thorne,100%,,Services Architect,Services,Services,"));
        staff.add(new Staffer("MIT,JoAnne Stevenson,10%,,Steering Committee,Steering  Committee,Steering Committee,"));
        staff.add(new Staffer("MIT,Dean Briggs,10%,,Steering Committee,Steering Committee,Steering Committee,"));
        staff.add(new Staffer("MIT,Brian Canavan,75%,,Lead SME,Use Case,Use Case,"));
        staff.add(new Staffer("MIT,Rajiv Kaushik,100%,January start,Project Manager,Program Level,Program Level,"));
        staff.add(new Staffer("MIT,Paul Luke,0%,??,Technical Writer,Comms/Doc,Comms/Doc,"));
        staff.add(new Staffer("UBC,Gord Uyeda,100%,,Team Lead,Application Design,Application Design,"));
        staff.add(new Staffer("UBC,Cath Fairlie,100%,,Prgram Director,Program Level,Program Level,"));
        staff.add(new Staffer("UBC,Brittney Brokenshire,100%,,Business Analyst,Application  Design ,Application Design,"));
        staff.add(new Staffer("UBC,Doris Yen,100%,,Busness Analyst,Use Case,Use Case,"));
        staff.add(new Staffer("UBC,Heather Johnson,100%,,Developer,Development,T1: LU - UI,"));
        staff.add(new Staffer("UBC,Cindy Nahm,?,transitioning to Impl. Team,Lead SME, , ,"));
        staff.add(new Staffer("UBC,Audrey Lindsay ,80%,,Functional Council,Functional Council,Functional Council,"));
        staff.add(new Staffer("UBC,Leo Fernig,100%,,Lead Architect,Development,Development,"));
        staff.add(new Staffer("UBC,Len Carlsen,100%,,Developer,Development,T3: Course/Pgm Rules,"));
        staff.add(new Staffer("UBC,Zdenek Zraly,100%,,Developer,Development,T3: Course/Pgm Rules,"));
        staff.add(new Staffer("UBC,Joe Yin,100%,,Developer,Development,T1: LU - UI,"));
        staff.add(new Staffer("UBC,George Lindholm,100%,,Developer,Development,T2: LU -  Services,"));
        staff.add(new Staffer("UBC,Sherman Tse,100%,,Developer,Development,T3: Course/Pgm Rules,"));
        staff.add(new Staffer("UBC,Jing Cui,100%,,Data Analyst,Services,Services,"));
        staff.add(new Staffer("UBC,Chris Eaton,50%,staying with R1,SME,Inst. Signoff,Use Case,"));
        staff.add(new Staffer("UBC,Ginette Vallee,0%,maternity leave,SME,Use Case,Use Case,"));
        staff.add(new Staffer("UBC,Erina James,100%,,Business Analyst,Use Case ,Use Case,"));
        staff.add(new Staffer("UBC,Nancy Low,100%,,SME,Use Case ,Use Case,"));
        staff.add(new Staffer("UBC,Vivian Luk,100%,start Feb 23,User Experience,User Experience ,User Experience,"));
        staff.add(new Staffer("UMCP,Dan Symonds,75%,,Lead SME,Use Case,Use Case,"));
        staff.add(new Staffer("UMCP,Dan Symonds,25%,Other Kuali activities, , , ,"));
        staff.add(new Staffer("UMCP,Michael Passarella George,40%,,Lead SME,Use Case,Use Case,"));
        staff.add(new Staffer("UMCP,Teddy Wu,50%,,Business Analyst,Application Design,Application Design,"));
        staff.add(new Staffer("UMCP,William Spann,50%,,Functional Council,Functional Council,Functional Council,"));
        staff.add(new Staffer("UMCP,Scott Gibson,100%,,Lead Developer,Development,T2: LU -  Services,"));
        staff.add(new Staffer("UMCP,Will Gomes,100%,,Developer,Development,T1: LU - UI,"));
        staff.add(new Staffer("UMCP,Daniel Epstein,100%,,Developer,Development,T2: LU -  Services,"));
        staff.add(new Staffer("UMCP,Richard Diaz,100%,,Business Analyst,Services,Services,"));
        staff.add(new Staffer("UMCP,Larry Symms,100%,,Lead Developer,Development,T3: Course/Pgm Rules,"));
        staff.add(new Staffer("UMCP,Stuart Sim,40%,,QA,Services,Services,"));
        staff.add(new Staffer("UMCP,Stuart Sim,40%,,QA,Development,QA Deployment,"));
        staff.add(new Staffer("UMCP,Scott Shepherd,100%,,Communications,Comms/Doc,Comms/Doc,"));
        staff.add(new Staffer("UMCP,Glenn Kirksey,100%,,Lead SME,Use Case,Use Case,"));
        staff.add(new Staffer("UMCP,Michelle Appel,40%,staying with R1,Lead SME,Use Case,Use Case,"));
        staff.add(new Staffer("UMCP,Paul Roche,50%,Start October,SME,Use Case,Use Case,"));
        staff.add(new Staffer("UMCP,Dan Beaty,40%,starting Jan,SME,Use Case,Use Case,"));
        staff.add(new Staffer("UMCP,Hilary Sazama,50%,replacement for Merdith,SME,Use Case,Use Case,"));
        staff.add(new Staffer("UMCP,Sarah Bauder,40%,Confirmed by Bill, may increase later,Lead SME,Functional Council,Functional Council,"));
        staff.add(new Staffer("UMCP,TBD???,,??,Testing Coordinator,R1 Testing,R1 Testing,CMU,Brian Bennett,100%,,Document Coordinator,Comms/Doc,Comms/Doc,"));
        staff.add(new Staffer("CMU,Darlene LaBarbara,20%,,Lead SME,Functional Council,Functional Council,"));
        staff.add(new Staffer("CMU,Tom Vrana,25%,,Steering Committee,Steering Committee,Steering Committee,"));
        staff.add(new Staffer("UW,Bill Shirey,10%,,Steering Committee,Steering Committee,Steering Committee,"));
        staff.add(new Staffer("UW,Bill Yock,10%,,Steering Committee,Steering Committee,Steering Committee,"));
        staff.add(new Staffer("UW,Bob Jansson,100%,by Jan,Business Analyst,Application Design ,Application Design,"));
        staff.add(new Staffer("UW,Hugh Parker ,100%,100% by Jan,Business Analyst,Application Design ,Application Design,"));
        staff.add(new Staffer("UW,Debbie Wiegand,80%,by Jan,SME,Use Case ,Use Case,"));
        staff.add(new Staffer("UW,Carol Bershad,100%,50% in Jan, ramp to 100% by Apr,BA,Use Case,Use Case,"));
        staff.add(new Staffer("UW,John Drew,50%,50% by Jan,SME,Use Case,Use Case,"));
        staff.add(new Staffer("UW,Jim Tomlinson,100%,starting Jan, 50% now,Developer,Development,T1: LU - UI,"));
        staff.add(new Staffer("UW,William Washington,50%,Feb/Mar,User Experience,User Experience ,User Experience,"));
        staff.add(new Staffer("UW,Kevin Pittman,50%,Feb/Mar,User Experience,User Experience ,User Experience,"));
        staff.add(new Staffer("UW,TBD,100%,January??,QA Manager,Program Level,Program Level,"));
        staff.add(new Staffer("USC,Steve Barnhart,50%,50%,Lead SME,Use Case,Use Case,"));
        staff.add(new Staffer("USC,Ken Servis,10%,,Functional Council,Functional Council,Functional Council,"));
        staff.add(new Staffer("USC,KC Peterson,10%,Not in budget document,Steering Committee,Steering Committee,Steering Committee,"));
        staff.add(new Staffer("USC,Asbed Bedrosian,10%,Not in budget document,Steering Committee,Steering Committee,Steering Committee,"));
        staff.add(new Staffer("USC,Matt Bemis,10%,start date unknown,BA,Application Design ,Application Design,"));
        staff.add(new Staffer("USC,Betty Cowin,10%,start date unknown,BA,Application Design,Application Design,"));
        staff.add(new Staffer("USC,Cathy Bonds,10%,start date unknown,BA,Application Design,Application Design")); 

    }

    /**
     * @return the staff
     */
    public List<Staffer> getStaff() {
        return staff;
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
}

