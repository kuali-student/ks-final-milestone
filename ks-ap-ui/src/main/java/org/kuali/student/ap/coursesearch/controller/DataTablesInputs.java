package org.kuali.student.ap.coursesearch.controller;

import javax.servlet.http.HttpServletRequest;
import java.util.regex.Pattern;

/**
 * Input command processor for supporting DataTables server-side processing.
 *
 *
 * @see <a
 *      href="http://datatables.net/usage/server-side">http://datatables.net/usage/server-side</a>
 */
class DataTablesInputs {
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

    DataTablesInputs(HttpServletRequest request) {
        String s;
        iDisplayStart = (s = request.getParameter("iDisplayStart")) == null ? 0
                : Integer.parseInt(s);
        iDisplayLength = (s = request.getParameter("iDisplayLength")) == null ? 0
                : Integer.parseInt(s);
        iColumns = (s = request.getParameter("iColumns")) == null ? 0
                : Integer.parseInt(s);
        bRegex = (s = request.getParameter("bRegex")) == null ? false
                : new Boolean(s);
        bSmart = (s = request.getParameter("bSmart")) == null ? false
                : new Boolean(s);
        patSearch = (sSearch = escape(request.getParameter("sSearch"), "+")) == null
                || !bRegex ? null : Pattern.compile(sSearch);
        bSearchable_ = new boolean[iColumns];
        sSearch_ = new String[iColumns];
        patSearch_ = new Pattern[iColumns];
        bRegex_ = new boolean[iColumns];
        bSmart_ = new boolean[iColumns];
        bSortable_ = new boolean[iColumns];
        for (int i = 0; i < iColumns; i++) {
            bSearchable_[i] = (s = request.getParameter("bSearchable_" + i)) == null ? false
                    : new Boolean(s);
            bRegex_[i] = (s = request.getParameter("bRegex_" + i)) == null ? false
                    : new Boolean(s);
            bSmart_[i] = (s = request.getParameter("bSmart_" + i)) == null ? false
                    : new Boolean(s);
            patSearch_[i] = (sSearch_[i] = escape(request.getParameter("sSearch_"
                    + i), "+")) == null
                    || !bRegex_[i] ? null : Pattern.compile(sSearch_[i]);
            bSortable_[i] = (s = request.getParameter("bSortable_" + i)) == null ? false
                    : new Boolean(s);
        }
        iSortingCols = (s = request.getParameter("iSortingCols")) == null ? 0
                : Integer.parseInt(s);
        iSortCol_ = new int[iSortingCols];
        sSortDir_ = new String[iSortingCols];
        for (int i = 0; i < iSortingCols; i++) {
            iSortCol_[i] = (s = request.getParameter("iSortCol_" + i)) == null ? 0
                    : Integer.parseInt(s);
            sSortDir_[i] = request.getParameter("sSortDir_" + i);
        }
        mDataProp_ = new String[iColumns];
        for (int i = 0; i < iColumns; i++)
            mDataProp_[i] = request.getParameter("mDataProp_" + i);
        sEcho = (s = request.getParameter("sEcho")) == null ? 0 : Integer
                .parseInt(s);
    }

    /**
     * Look through the input string for occurrences of escapeVal and escape them
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

    int getiDisplayStart() {
        return iDisplayStart;
    }

    int getiDisplayLength() {
        return iDisplayLength;
    }

    int getiColumns() {
        return iColumns;
    }

    int getiSortingCols() {
        return iSortingCols;
    }

    int getsEcho() {
        return sEcho;
    }

    String getsSearch() {
        return sSearch;
    }

    Pattern getPatSearch() {
        return patSearch;
    }

    boolean isbRegex() {
        return bRegex;
    }

    boolean isbSmart() {
        return bSmart;
    }

    boolean[] getbSearchable_() {
        return bSearchable_;
    }

    boolean[] getbRegex_() {
        return bRegex_;
    }

    boolean[] getbSortable_() {
        return bSortable_;
    }

    boolean[] getbSmart_() {
        return bSmart_;
    }

    String[] getsSearch_() {
        return sSearch_;
    }

    String[] getsSortDir_() {
        return sSortDir_;
    }

    String[] getmDataProp_() {
        return mDataProp_;
    }

    Pattern[] getPatSearch_() {
        return patSearch_;
    }

    int[] getiSortCol_() {
        return iSortCol_;
    }
}
