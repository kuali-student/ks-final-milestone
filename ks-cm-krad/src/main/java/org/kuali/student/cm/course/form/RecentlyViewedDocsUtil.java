package org.kuali.student.cm.course.form;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.kuali.student.lum.common.client.helpers.RecentDocInfo;

public class RecentlyViewedDocsUtil {

    private static final List<RecentDocInfo> recentDocs = new ArrayList<RecentDocInfo>();

    public static List<RecentDocInfo> getRecentDocs() {
        return recentDocs;
    }

    public static void addRecentDoc(String name, String location) {
        if (StringUtils.isNotBlank(name) && StringUtils.isNotBlank(location)) {
            boolean docExists = false;
            for (RecentDocInfo doc : recentDocs) {
                if (location.equals(doc.getLocation())) {
                    docExists = true;
                    break;
                }
            }
            if (!docExists) {
                recentDocs.add(new RecentDocInfo(name, location));
            }
        }
    }

}
