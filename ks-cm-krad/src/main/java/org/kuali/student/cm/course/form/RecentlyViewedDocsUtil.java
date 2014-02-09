package org.kuali.student.cm.course.form;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.kuali.rice.krad.util.GlobalVariables;
import org.kuali.student.lum.common.client.helpers.RecentDocInfo;

public class RecentlyViewedDocsUtil {
    
    private static final String RECENT_DOCS_SESSION_KEY = "recentDocs";

    @SuppressWarnings("unchecked")
    public static List<RecentDocInfo> getRecentDocs() {
        if ((List<RecentDocInfo>)GlobalVariables.getUserSession().retrieveObject(RECENT_DOCS_SESSION_KEY) == null) {
            GlobalVariables.getUserSession().addObject(RECENT_DOCS_SESSION_KEY, new ArrayList<RecentDocInfo>());
        }
        return (List<RecentDocInfo>)GlobalVariables.getUserSession().retrieveObject(RECENT_DOCS_SESSION_KEY);
    }

    public static void addRecentDoc(String name, String location) {
        if (StringUtils.isNotBlank(name) && StringUtils.isNotBlank(location)) {
            boolean docExists = false;
            for (RecentDocInfo doc : getRecentDocs()) {
                if (location.equals(doc.getLocation())) {
                    docExists = true;
                    break;
                }
            }
            if (!docExists) {
                getRecentDocs().add(new RecentDocInfo(name, location));
            }
        }
    }

}
