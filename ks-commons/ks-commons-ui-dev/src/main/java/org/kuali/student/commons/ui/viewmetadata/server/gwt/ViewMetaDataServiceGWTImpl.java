package org.kuali.student.commons.ui.viewmetadata.server.gwt;

import java.util.List;
import java.util.Map;

import org.kuali.student.commons.ui.viewmetadata.client.ViewMetaData;
import org.kuali.student.commons.ui.viewmetadata.client.ViewMetaDataService;
import org.kuali.student.commons.ui.viewmetadata.server.impl.ViewMetaDataServiceImpl;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

/**
 * GWT remote service servlet provides a wrapper for the ViewMetaData service
 */

public class ViewMetaDataServiceGWTImpl extends RemoteServiceServlet implements ViewMetaDataService {

    private static final long serialVersionUID = -7229885467942096136L;

    private final ViewMetaDataService impl = new ViewMetaDataServiceImpl();

    /**
     * @see org.kuali.student.commons.ui.viewmetadata.client.ViewMetaDataService#getViewMetaData(java.lang.String,
     *      java.lang.String)
     */
    @Override
    public Map<String, ViewMetaData> getViewMetaData(String locale, String viewName) {
        return impl.getViewMetaData(locale, viewName);
    }

    /**
     * @see org.kuali.student.commons.ui.viewmetadata.client.ViewMetaDataService#getViewMetaDataMap(java.lang.String,
     *      java.util.List)
     */
    @Override
    public Map<String, ViewMetaData> getViewMetaDataMap(String locale, List<String> viewNames) {
        return impl.getViewMetaDataMap(locale, viewNames);
    }

}
