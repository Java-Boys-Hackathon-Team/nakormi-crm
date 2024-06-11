FROM eclipse-temurin:17-jdk

COPY build/libs/nakormi-0.0.1-SNAPSHOT.jar nakormi.jar

ENTRYPOINT ["java","-jar","/nakormi.jar"]