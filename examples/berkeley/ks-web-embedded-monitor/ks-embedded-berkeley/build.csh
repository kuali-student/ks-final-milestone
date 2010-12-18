#/bin/csh -f
set ts = `date +%F`
# source ~/bin/mvn-opts.csh
setenv MAVEN_OPTS "-Djava.awt.headless=true -server -Xss512m -Xms1024m -Xmx1024m -XX:PermSize=128m -XX:MaxPermSize=128m"
setenv JAVA_OPTS "-Djava.awt.headless=true -server -Xss512m -Xms1024m -Xmx1024m -XX:PermSize=128m -XX:MaxPermSize=128m"
unset noclobber

echo " "
echo -n "building berkeley embedded ks "
date
echo " "

   mvn -e -Pkuali-developer-properties clean install >& build.out.$ts

echo " "
echo -n 'build berkeley embedded ks complete...'  
date

