call mvn -N -Drelease.arguments="-N -Drelease.distribution.repository=svn:https://test.kuali.org/svn/student/deploymentlab/repository" release:prepare
call mvn -N -Drelease.arguments="-N -Drelease.distribution.repository=svn:https://test.kuali.org/svn/student/deploymentlab/repository" release:perform
