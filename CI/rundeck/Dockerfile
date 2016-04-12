FROM java:latest
MAINTAINER Kishore

ADD http://dl.bintray.com/rundeck/rundeck-maven/rundeck-launcher-2.6.4.jar /opt/

ENTRYPOINT ["java", "-jar", "/opt/rundeck-launcher-2.6.4.jar"]

EXPOSE 4440 4443
