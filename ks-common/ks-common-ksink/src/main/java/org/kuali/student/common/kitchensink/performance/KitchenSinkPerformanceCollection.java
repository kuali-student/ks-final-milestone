package org.kuali.student.common.kitchensink.performance;

/**
 * Created with IntelliJ IDEA.
 * User: swedev
 * Date: 11/21/13
 * Time: 2:22 PM
 * To change this template use File | Settings | File Templates.
 */
public class KitchenSinkPerformanceCollection {

    String column1;
    String column2;
    String column3;
    String column4;
    String column5;

    public KitchenSinkPerformanceCollection(int rowCount) {
        column1 = "row "  + rowCount +  " column-1";
        column2 = "row "  + rowCount +  " column-2";
        column3 = "row "  + rowCount +  " column-3";
        column4 = "row "  + rowCount +  " column-4";
        column5 = "row "  + rowCount +  " column-5";
    }

    public String getColumn1() {
        return column1;
    }

    public void setColumn1(String column1) {
        this.column1 = column1;
    }

    public String getColumn2() {
        return column2;
    }

    public void setColumn2(String column2) {
        this.column2 = column2;
    }

    public String getColumn3() {
        return column3;
    }

    public void setColumn3(String column3) {
        this.column3 = column3;
    }

    public String getColumn4() {
        return column4;
    }

    public void setColumn4(String column4) {
        this.column4 = column4;
    }

    public String getColumn5() {
        return column5;
    }

    public void setColumn5(String column5) {
        this.column5 = column5;
    }
}
