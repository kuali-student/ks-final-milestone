package org.kuali.student.common.ui.client.service;

import java.util.Arrays;
import java.util.List;

/**
 * @author Igor
 */
@Deprecated
public class SearchKeysForCache {

    private static final List<String> KEYS = Arrays.asList( "atp.search.atpSeasonTypes", "atp.search.atpDurationTypes",
            "lrc.search.resultComponent", "lrc.search.resultComponentType", "atp.search.advancedAtpSearch");

    public static boolean contains(String key){
        return KEYS.contains(key);
    }
}
