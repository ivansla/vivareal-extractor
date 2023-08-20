FROM ibmjava:11
MAINTAINER sk.refactorit
LABEL authors="ivansla"

COPY target/vivareal-extractor-1.0-SNAPSHOT.jar vivareal-extractor-1.0-SNAPSHOT.jar
ENTRYPOINT ["java", "-Dspring.profiles.active=local", "-jar","/vivareal-extractor-1.0-SNAPSHOT.jar"]
