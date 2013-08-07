The ks-lum-rice upgrades folder contains workflow (KREW) and kim permissions (KRIM) table inserts & updates required by
KSCM.  The sql files provided are meant to be run against an existing rice db which will be used by Kuali Student.
Note if using the ks-rice-db impex to impex the rice tables, these sql files should not be run, as data is already
contained in the KS impex modules.

R1.2.1-prior
  	This folder contains old sql files used for R1.1-R1.2.1 releases and are not reliable.  THESE SQL FILES SHOULD
  	NEVER BE USED.  It is recommended that institutions start with R1.2.2 release for their implementations and
  	use sql provided for R1.2.2 release and on.
    
R1.2.2
	This folder contains all initial sql needed for workflow and kim permissions to run Kuali student.  The files
	should be run in the order they exist in this file.   
