/*
 * Copyright 2007 The Kuali Foundation
 *
 * Licensed under the Educational Community License, Version 1.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.opensource.org/licenses/ecl1.php
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.kuali.student.brms.repository.rule;

public class BuilderResult implements java.io.Serializable {
    public String assetUuid;
    public String assetName;
    public String assetFormat;
    public String assetMessage;

    public BuilderResult(String uuid, String assetName, String assetFormat, String message) {
        this.assetUuid = uuid;
        this.assetName = assetName;
        this.assetFormat = assetFormat;
        this.assetMessage = message;
    }

    public String getUuid() {
        return this.assetUuid;
    }

    public String getAssetName() {
        return this.assetName;
    }

    public String getAssetFormat() {
        return this.assetFormat;
    }

    public String getMessage() {
        return this.assetMessage;
    }

    public String toString() {
        return "\nAsset: " + this.assetName + "\nFormat: " + this.assetFormat + "\nUUID: " + this.assetUuid + "\nMessage: " + this.assetMessage;
    }
}
