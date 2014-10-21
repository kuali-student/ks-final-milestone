This module provides a mechanism for adding data and structure to the database.  Expanding all the folders you'll find a folder named upgrades.  This folder will contain your Phased release upgrade scripts.  Each phase should have a folder and the folders should be sequenced.  Within each folder place sql which will progressivly add structure and data to the database.

This process is documented here: https://wiki.kuali.org/display/STUDENTDOC/4.7+Database+Management

For loading your database please follow the steps here: https://wiki.kuali.org/display/STUDENTDOC/3.2+Load+Impex+Data+Via+Maven