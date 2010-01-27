package org.kuali.student.core.organization.assembly.data.client.org;



import org.kuali.student.common.assembly.client.Data;
import org.kuali.student.core.organization.assembly.data.client.PropertyEnum;

public class OrgSearchHelper {
    private static final long serialVersionUID = 1L;

    public enum Properties implements PropertyEnum{
        ORG_ID("searchOrgs");

        private final String key;

        private Properties(final String key) {
            this.key = key;
        }

        @Override
        public String getKey() {
            return this.key;
        }
    }

    private Data data;
    
    private OrgSearchHelper(Data data){
        this.data=data;
    }

    public static OrgSearchHelper wrap (Data data)
    {
        if (data == null)
        {
             return null;
        }
        return new OrgSearchHelper(data);
    }
    
    public Data getData(){
        return data;
    }
    
    public void setOrgId(String id){
        data.set(Properties.ORG_ID.getKey(), id);
    }
    
    public String getOrgId() {
        return data.get(Properties.ORG_ID.getKey());
    }
    
   
    

}
