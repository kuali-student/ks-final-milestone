====
    Copyright 2010 The Kuali Foundation Licensed under the
    Educational Community License, Version 2.0 (the "License"); you may
    not use this file except in compliance with the License. You may
    obtain a copy of the License at

    http://www.osedu.org/licenses/ECL-2.0

    Unless required by applicable law or agreed to in writing,
    software distributed under the License is distributed on an "AS IS"
    BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
    or implied. See the License for the specific language governing
    permissions and limitations under the License.
====

HOW TO COMPILE & RUN GWT HOSTED MODE
=====================================

Setting up Maven Run Configuration in Eclipse
---------------------------------------------
	It is recommended that you set up eclipse run configurations to run mvn commands from within eclipse.
	For example, to setup run config for maven command: "mvn -P build-codehaus-gwt compile", do the following:
	
	1. Right click on pom and chose [Run As]->[Maven build...]
	2. Under Goals enter "compile"
	3. Under Profiles enter "build-codehaus-gwt"
	4. Click run

Run/Debug hosted mode using launch script
-----------------------------------------
	1. Run "mvn -P build-codehaus-gwt compile" from cmd line (or use equivalent eclipse maven run configuration)
	2. In Eclipse right click OrgGWT.launch and select [Run As]->[OrgGWT] or [Debug As]->[OrgGWT]
	
Run hosted mode using gwt maven plugin
--------------------------------------
	NOTE: Running gwt hosted mode via the plugin does work yet due do dependencies on Kuali Rice 
	
	Run either of the following from command line or maven run configuration in eclipse
	
	"mvn -P build-codehaus-gwt compile gwt:gwt" OR "mvn -P build-codehaus-gwt test"
	 

Notes regarding Hosted Mode browser
-----------------------------------
- Changes to client side java files are reflected by simply hitting the "Refresh" button in hosted mode browser
- Changes to server side code requires, you to restart the hosted mode browser
- Debugger only works when hosted mode is started with launch file 