cd ..
call mvn -o -Dmaven.test.skip=true -Pkuali-developer-properties -Dks.java2ws.phase=none install
cd ks-scripts
