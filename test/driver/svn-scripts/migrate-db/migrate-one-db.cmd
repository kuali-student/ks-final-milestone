set BASE_URL=%1
set DB=%2

svn move -m "Renaming %DB% to %DB%-impex" %BASE_URL%/ks-cfg-dbs/%DB% %BASE_URL%/ks-cfg-dbs/%DB%-impex 
svn move -m "Moving %DB%-impex to %DB%/src/main/impex/data" --parents %BASE_URL%/ks-cfg-dbs/%DB%-impex %BASE_URL%/ks-cfg-dbs/%DB%/src/main/impex/datasets/reference
svn move -m "Moving %DB% schema.xml out of the data directory" %BASE_URL%/ks-cfg-dbs/%DB%/src/main/impex/datasets/reference/schema.xml %BASE_URL%/ks-cfg-dbs/%DB%/src/main/impex/schema.xml 
svn delete -m "Removing unnecessary %DB% .project file" %BASE_URL%/ks-cfg-dbs/%DB%/src/main/impex/datasets/reference/.project 
svn delete -m "Removing unnecessary %DB% data.dtd file" %BASE_URL%/ks-cfg-dbs/%DB%/src/main/impex/datasets/reference/data.dtd 
svn delete -m "Removing unnecessary %DB% database.dtd file" %BASE_URL%/ks-cfg-dbs/%DB%/src/main/impex/datasets/reference/database.dtd 
