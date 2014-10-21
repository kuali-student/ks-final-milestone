/*
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
 */

package org.kuali.student.ap.coursesearch.controller;

import javax.servlet.http.HttpServletRequest;
import java.util.regex.Pattern;

/**
 * Input command processor for supporting DataTables server-side processing.
 *
 * @see <a href="http://datatables.net/usage/server-side">http://datatables.net/usage/server-side</a>
 */
public class DataTablesInputs {
    private final int iDisplayStart, iDisplayLength, iColumns,
            iSortingCols, sEcho;
    private final String sSearch;
    private final Pattern patSearch;
    private final boolean bRegex;
    private final boolean bSmart;
    private final boolean[] bSearchable_, bRegex_, bSortable_, bSmart_;
    private final String[] sSearch_, sSortDir_, mDataProp_;
    private final Pattern[] patSearch_;
    private final int[] iSortCol_;

    /**
     * Constructor which fills in the information needed for the datatable plugin used by the page for showing the
     * search results.
     *
     * @param request - Request object for the page storing information sent by the page.
     */
    public DataTablesInputs(HttpServletRequest request) {
        String s;

        // Fill in basic settings
        iDisplayStart = (s = request.getParameter("iDisplayStart")) == null ? 0
                : Integer.parseInt(s);
        iDisplayLength = (s = request.getParameter("iDisplayLength")) == null ? 0
                : Integer.parseInt(s);
        iColumns = (s = request.getParameter("iColumns")) == null ? 0
                : Integer.parseInt(s);
        bRegex = (s = request.getParameter("bRegex")) == null ? false
                : Boolean.valueOf(s);
        bSmart = (s = request.getParameter("bSmart")) == null ? false
                : Boolean.valueOf(s);
        patSearch = (sSearch = escape(request.getParameter("sSearch"), "+")) == null
                || !bRegex ? null : Pattern.compile(sSearch);
        sEcho = (s = request.getParameter("sEcho")) == null ? 0 : Integer
                .parseInt(s);

        // Fill in column settings
        bSearchable_ = new boolean[iColumns];
        sSearch_ = new String[iColumns];
        patSearch_ = new Pattern[iColumns];
        bRegex_ = new boolean[iColumns];
        bSmart_ = new boolean[iColumns];
        bSortable_ = new boolean[iColumns];
        mDataProp_ = new String[iColumns];
        for (int i = 0; i < iColumns; i++) {
            bSearchable_[i] = (s = request.getParameter("bSearchable_" + i)) == null ? false
                    : Boolean.valueOf(s);
            bRegex_[i] = (s = request.getParameter("bRegex_" + i)) == null ? false
                    : Boolean.valueOf(s);
            bSmart_[i] = (s = request.getParameter("bSmart_" + i)) == null ? false
                    : Boolean.valueOf(s);
            patSearch_[i] = (sSearch_[i] = escape(request.getParameter("sSearch_"
                    + i), "+")) == null
                    || !bRegex_[i] ? null : Pattern.compile(sSearch_[i]);
            bSortable_[i] = (s = request.getParameter("bSortable_" + i)) == null ? false
                    : Boolean.valueOf(s);
            mDataProp_[i] = request.getParameter("mDataProp_" + i);
        }

        // Fill in sorting settings
        iSortingCols = (s = request.getParameter("iSortingCols")) == null ? 0
                : Integer.parseInt(s);
        iSortCol_ = new int[iSortingCols];
        sSortDir_ = new String[iSortingCols];
        for (int i = 0; i < iSortingCols; i++) {
            iSortCol_[i] = (s = request.getParameter("iSortCol_" + i)) == null ? 0
                    : Integer.parseInt(s);
            sSortDir_[i] = request.getParameter("sSortDir_" + i);
        }

    }

    /**
     * Look through the input string for occurrences of escapeVal and escape them
     *
     * @param input Input string to search
     * @param escapeVal Value to look for in the input
     * @return The string, with the escapeVal character escaped
     */
    private String escape(String input, String escapeVal) {
        String retVal = input;
        if (input != null && escapeVal != null && input.contains(escapeVal))
            retVal = input.replace(escapeVal, "\\".concat(escapeVal));
        return retVal;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(super.toString());
        sb.append("\n\tiDisplayStart = ");
        sb.append(iDisplayStart);
        sb.append("\n\tiDisplayLength = ");
        sb.append(iDisplayLength);
        sb.append("\n\tiColumns = ");
        sb.append(iColumns);
        sb.append("\n\tsSearch = ");
        sb.append(sSearch);
        sb.append("\n\tbRegex = ");
        sb.append(bRegex);
        sb.append("\n\tbSmart = ");
        sb.append(bSmart);
        sb.append("\n\tpatSearch = ");
        sb.append(patSearch);
        for (int i = 0; i < iColumns; i++) {
            sb.append("\n\tbSearchable_").append(i).append(" = ");
            sb.append(bSearchable_[i]);
            sb.append("\n\tsSearch_").append(i).append(" = ");
            sb.append(sSearch_[i]);
            sb.append("\n\tbRegex_").append(i).append(" = ");
            sb.append(bRegex_[i]);
            sb.append("\n\tbSmart_").append(i).append(" = ");
            sb.append(bSmart_[i]);
            sb.append("\n\tpatSearch_").append(i).append(" = ");
            sb.append(patSearch_[i]);
            sb.append("\n\tbSortable_").append(i).append(" = ");
            sb.append(bSortable_[i]);
        }
        sb.append("\n\tiSortingCols = ");
        sb.append(iSortingCols);
        for (int i = 0; i < iSortingCols; i++) {
            sb.append("\n\tiSortCol_").append(i).append(" = ");
            sb.append(iSortCol_[i]);
            sb.append("\n\tsSortDir_").append(i).append(" = ");
            sb.append(sSortDir_[i]);
        }
        for (int i = 0; i < iColumns; i++) {
            sb.append("\n\tmDataProp_").append(i).append(" = ");
            sb.append(mDataProp_[i]);
        }
        sb.append("\n\tsEcho = ");
        sb.append(sEcho);
        return sb.toString();
    }

    public int getiDisplayStart() {
        return iDisplayStart;
    }

    public int getiDisplayLength() {
        return iDisplayLength;
    }

    public int getiColumns() {
        return iColumns;
    }

    public int getiSortingCols() {
        return iSortingCols;
    }

    public int getsEcho() {
        return sEcho;
    }

    public String getsSearch() {
        return sSearch;
    }

    public Pattern getPatSearch() {
        return patSearch;
    }

    public boolean isbRegex() {
        return bRegex;
    }

    public boolean isbSmart() {
        return bSmart;
    }

    public boolean[] getbSearchable_() {
        return bSearchable_;
    }

    public boolean[] getbRegex_() {
        return bRegex_;
    }

    public boolean[] getbSortable_() {
        return bSortable_;
    }

    public boolean[] getbSmart_() {
        return bSmart_;
    }

    public String[] getsSearch_() {
        return sSearch_;
    }

    public String[] getsSortDir_() {
        return sSortDir_;
    }

    public String[] getmDataProp_() {
        return mDataProp_;
    }

    public Pattern[] getPatSearch_() {
        return patSearch_;
    }

    public int[] getiSortCol_() {
        return iSortCol_;
    }
}
