/**
 * Copyright 2010 The Kuali Foundation Licensed under the
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

package org.kuali.student.common.assembly.data;

import java.io.Serializable;
import java.util.List;

/**
 * @author nwright
 */
public class LookupQosMetadata implements Serializable {

    /**
  *
  */
    private static final long serialVersionUID = 1L;
    private Integer startAt;
    private Integer maxResults;

    public Integer getStartAt() {
        return startAt;
    }

    public void setStartAt(Integer startAt) {
        this.startAt = startAt;
    }

    public Integer getMaxResults() {
        return maxResults;
    }

    public void setMaxResults(Integer maxResults) {
        this.maxResults = maxResults;
    }

    public static class SortMetadata {

        public SortMetadata() {}

        private String resultColumnKey;

        public String getResultColumnKey() {
            return resultColumnKey;
        }

        public void setResultColumnKey(String resultColumnKey) {
            this.resultColumnKey = resultColumnKey;
        }

        private boolean descending;

        public boolean isDescending() {
            return descending;
        }

        public void setDescending(boolean descending) {
            this.descending = descending;
        }

    }

    protected List<SortMetadata> sorts;

    /**
     * Get the value of sorts
     * 
     * @return the value of sorts
     */
    public List<SortMetadata> getSorts() {
        return sorts;
    }

    /**
     * Set the value of sorts
     * 
     * @param sorts
     *            new value of sorts
     */
    public void setSorts(List<SortMetadata> sorts) {
        this.sorts = sorts;
    }

}
