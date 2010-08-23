set SRC_URL=%1
set DST_URL=%2
set DB=%3

svn copy -m "Migrate impex" %SRC_URL%/%DB% %DST_URL%/%DB%/src/main
svn delete -m "Deleting" %DST_URL%/%DB%/src/main/impex
svn move -m "Renaming" %DST_URL%/%DB%/src/main/%DB% %DST_URL%/%DB%/src/main/impex
