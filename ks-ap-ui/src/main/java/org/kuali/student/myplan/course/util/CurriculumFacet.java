package org.kuali.student.myplan.course.util;

import javax.xml.namespace.QName;

import org.apache.log4j.Logger;
import org.kuali.student.myplan.course.dataobject.CourseSearchItem;
import org.kuali.student.myplan.course.dataobject.FacetItem;
import org.kuali.student.myplan.plan.util.OrgHelper;

import java.util.*;

/**
 * Logic for building list of FacetItems and coding CourseSearchItems.
 */
public class CurriculumFacet extends AbstractFacet {

    private final Logger logger = Logger.getLogger(CurriculumFacet.class);

    private HashMap<String, Map<String, String>> hashMap;

    public HashMap<String, Map<String, String>> getHashMap() {
        if (this.hashMap == null) {
            this.hashMap = new HashMap<String, Map<String, String>>();
        }
        return this.hashMap;
    }

    public void setHashMap(HashMap<String, Map<String, String>> hashMap) {
        this.hashMap = hashMap;
    }

    public CurriculumFacet() {
        super();
    }

    private HashSet<String> curriculumFacetSet = new HashSet<String>();

    @Override
    public List<FacetItem> getFacetItems() {
        String[] list = curriculumFacetSet.toArray(new String[0]);
        Arrays.sort(list);


        for (String display : list) {
            FacetItem item = new FacetItem();

            String key = FACET_KEY_DELIMITER + display + FACET_KEY_DELIMITER;
            item.setKey(key);
            item.setDisplayName(display);
            String title = this.getTitle(display);
            if (title != null || title != "") {
                item.setTitle(title);
            }
            facetItems.add(item);
        }

        return facetItems;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void process(CourseSearchItem course) {
        // TODO: Use the Org Service to lookup curriculum name based on key.
        // For now just use the serviceArea code as the displayName.
        String subject = course.getSubject();

        if (subject == null || subject.equals("")) {
            subject = unknownFacetKey;
        }

        curriculumFacetSet.add(subject);

        String key = FACET_KEY_DELIMITER + subject + FACET_KEY_DELIMITER;

        //  Code the item with the facet key.
        Set<String> keys = new HashSet<String>();
        keys.add(key);
        course.setCurriculumFacetKeys(keys);
    }

    /**
     * To get the title for the respective display name
     *
     * @param display
     * @return
     */
    protected String getTitle(String display) {
        String titleValue = null;
        Map<String, String> subjects = null;
        try {
            if (!this.getHashMap().containsKey(CourseSearchConstants.SUBJECT_AREA)) {
                subjects = OrgHelper.getTrimmedSubjectAreas();
                getHashMap().put(CourseSearchConstants.SUBJECT_AREA, subjects);

            } else {
                subjects = this.hashMap.get(CourseSearchConstants.SUBJECT_AREA);
            }
            for (Map.Entry<String, String> entry : subjects.entrySet()) {
                if (entry.getKey().trim().equalsIgnoreCase(display)) {
                    titleValue = entry.getValue().trim();
                    break;
                }
            }

        } catch (Exception e) {
            logger.error("Could not load title value");
        }

        return titleValue;
    }


}
