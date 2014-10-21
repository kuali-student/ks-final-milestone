# Kuali Student Build 917

As of October 2014 this is the final milestone for CM 3.0 and ENR 1.0.

# Purpose

For those interested in forking the final milestone master this repository can be cloned directly.

If additional history is needed it can be retrieved from the [archived-from-svn](https://github.com/kuali-student/archived-from-svn) repository later.

If forking for the purpose of continued development we recommend forking the [ks-development](https://github.com/kuali-student/ks-development) and [ks-development-impex](https://github.com/kuali-student/ks-development-impex) repositories instead as placing the .mpx files within the same repository as the code will result in a very large repository.

The subversion to git conversion process removed all of the .mpx file content and this repository has had the manual impex processes rerun to make startup simpler.

# Origin

In subversion this build was located in : https://svn.kuali.org/repos/student/enrollment/aggregate/tags/builds/student-2.1/2.1.1-FR2-M1/build-917

In Git it is the [enrollment_aggregate_tags_builds_2.1_2.1.1-FR2-M1_build-917](https://github.com/kuali-student/archived-from-svn/tree/enrollment_aggregate_tags_builds_student-2.1_2.1.1-FR2-M1_build-917) branch.

# Loading Impex Data

First assemble the ks-impex-{bundled,app,rice}-db artifacts:


``` mvn clean install -Pimpex-only ```

Then run the [load impex data process](https://wiki.kuali.org/display/STUDENTDOC/3.2+Load+Impex+Data+Via+Maven).


# More Information

Please see [kuali-student.github.io](https://kuali-student.github.io) for the latest information on the Kuali Student Git Repositories.


