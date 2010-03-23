svn delete -m "Removing" %1
svn copy -m "Copying from dev branch" --username jcaddel --password gw570229 %2 %1
