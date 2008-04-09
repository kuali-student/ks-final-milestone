package org.kuali.student.brms.repository;

public class BuilderResult implements java.io.Serializable 
{
    public String assetUuid;
    public String assetName;
    public String assetFormat;
    public String assetMessage;

    public BuilderResult( String uuid, String assetName, String assetFormat, String message ) 
    {
		this.assetUuid = uuid;
		this.assetName = assetName;
		this.assetFormat = assetFormat;
		this.assetMessage = message;
	}

	public String getUuid() { return this.assetUuid; }

	public String getAssetName() { return this.assetName; }

	public String getAssetFormat() { return this.assetFormat; }

	public String getMessage() { return this.assetMessage; }

	public String toString() 
    {
        return "\nAsset: " + this.assetName + 
        	   "\nFormat: " + this.assetFormat +
		       "\nUUID: " + this.assetUuid +
               "\nMessage: " + this.assetMessage; 
    }
}
