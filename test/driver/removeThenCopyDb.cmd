svn delete -m "Removing" %1/%3
svn copy -m "Copying from dev branch" --username jcaddel --password gw570229 %2/%3/tags/%3-1.0.0-m5 %1/%3