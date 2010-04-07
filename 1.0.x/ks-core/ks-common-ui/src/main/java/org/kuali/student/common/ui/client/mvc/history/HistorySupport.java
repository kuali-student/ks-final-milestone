package org.kuali.student.common.ui.client.mvc.history;

public interface HistorySupport {
    public void collectHistory(HistoryStackFrame frame);
    public void onHistoryEvent(HistoryStackFrame frame);
}
