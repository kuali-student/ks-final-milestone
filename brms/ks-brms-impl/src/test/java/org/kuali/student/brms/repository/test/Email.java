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

package org.kuali.student.brms.repository.test;

public class Email
{
    private String emailAddress;
    private long average = -1;
    
	public Email() {}
	public Email(String email) { this.emailAddress = email; }
    public String getEmailAddress() { return emailAddress; }
    public void setEmailAddress(String value) { this.emailAddress = value; }
    public long calculateAverage() 
    { 
    	this.average = Math.round( Math.random() * 100 );
    	return this.average;
    }

    public long getAverage()
    {
    	return this.average;
    }

    public void setAverage( long avg )
    {
    	this.average = avg;
    }
}
