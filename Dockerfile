FROM openjdk:15
ADD target/musiclibrary-0.0.1-SNAPSHOT.jar music-dk-sqlite.jar
ENTRYPOINT ["java", "-jar", "music-dk-sqlite.jar"]