package org.kuali.student.ap.coursesearch.dataobject;

/**
 *  Facet results record.
 */
public class FacetItem implements Comparable<FacetItem> {

    /* The key which will be used for filtering in the main grid of search results. */
    private String key;

    /* The name which will be displayed */
    private String displayName;
    
    private String title;

    public FacetItem() {}

    /**
     * Convenience constructor.
     */
    public FacetItem(String key, String displayName) {
        setKey(key);
        setDisplayName(displayName);
        setTitle(title);
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    @Override
    public int compareTo(FacetItem other) {
        if (other == null) {
            return 1;
        }
        return this.getDisplayName().compareTo(other.getDisplayName());
    }
}