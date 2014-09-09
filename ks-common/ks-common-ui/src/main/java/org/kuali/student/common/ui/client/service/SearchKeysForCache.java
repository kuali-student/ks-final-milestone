package org.kuali.student.common.ui.client.service;

import org.kuali.student.r2.core.constants.AtpSearchServiceConstants;

import java.util.Arrays;
import java.util.List;

/**
 * @author Igor
 */
@Deprecated
public class SearchKeysForCache {

    private static final List<String> KEYS = Arrays.asList(AtpSearchServiceConstants.ATP_RESULTCOLUMN_SEASONTYPE_ID,
            AtpSearchServiceConstants.ATP_SEARCH_DURATIONTYPES,
            "lrc.search.resultComponent", "lrc.search.resultComponentType", AtpSearchServiceConstants.ATP_SEARCH_ADVANCED);

    public static boolean contains(String key){
        return KEYS.contains(key);
    }
}
