# Run in robocode/libs
mvn install:install-file -Dfile=robocode.jar -DartifactId=robocode  -DgroupId=net.sourceforge.robocode -Dversion=1.9.2.4 -Dpackaging=jar -DgeneratePom=tr
mvn install:install-file -Dfile=robocode.battle-1.9.2.4.jar -DartifactId=robocode.battle -DgroupId=net.sourceforge.robocode -Dversion=1.9.2.4 -Dpackaging=jar -DgeneratePom=true
mvn install:install-file -Dfile=robocode.core-1.9.2.4.jar   -DartifactId=robocode.core   -DgroupId=net.sourceforge.robocode -Dversion=1.9.2.4 -Dpackaging=jar -DgeneratePom=true
mvn install:install-file -Dfile=robocode.host-1.9.2.4.jar   -DartifactId=robocode.host   -DgroupId=net.sourceforge.robocode -Dversion=1.9.2.4 -Dpackaging=jar -DgeneratePom=true
mvn install:install-file -Dfile=robocode.repository-1.9.2.4.jar -DartifactId=robocode.repository   -DgroupId=net.sourceforge.robocode -Dversion=1.9.2.4 -Dpackaging=jar -DgeneratePom=true
mvn install:install-file -Dfile=picocontainer-2.14.2.jar -DgroupId=net.sourceforge.robocode -DartifactId=picocontainer -Dversion=2.14.2 -Dpackaging=jar -DgeneratePom=true
