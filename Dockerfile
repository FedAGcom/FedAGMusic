FROM openjdk:11
VOLUME /tmp
ADD target/fedagmusic-0.0.1-SNAPSHOT.jar fedagmusic.jar
#EXPOSE 8080
ENTRYPOINT ["java","-jar","/fedagmusic.jar"]