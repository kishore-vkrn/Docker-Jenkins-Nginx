FROM ubuntu:14.04
MAINTAINER Kishore
EXPOSE 8080
EXPOSE 50000
RUN apt-get update && apt-get install -y wget git curl zip && rm -rf /var/lib/apt/lists/*
VOLUME /var/jenkins_home

################# BUILD TOOLS #################
# GIT
#######
RUN apt-get install -y git

# JAVA
#######
RUN \
  apt-get update && \
  apt-get install -y openjdk-7-jdk && \
  rm -rf /var/lib/apt/lists/*

# Define working directory.
WORKDIR /data

# Define commonly used JAVA_HOME variable
ENV JAVA_HOME /usr/lib/jvm/java-7-openjdk-amd64

# Define default command.
CMD ["bash"]


# MAVEN
#######
ENV MAVEN_VERSION 3.3.3
RUN mkdir -p /usr/share/maven \
  && curl -fsSL http://apache.osuosl.org/maven/maven-3/$MAVEN_VERSION/binaries/apache-maven-$MAVEN_VERSION-bin.tar.gz \
    | tar -xzC /usr/share/maven --strip-components=1 \
  && ln -s /usr/share/maven/bin/mvn /usr/bin/mvn

ENV MAVEN_HOME /usr/share/maven
CMD ["mvn"]

################# JENKINS  #################
ENV JENKINS_VERSION 1.647
##########################
ADD http://mirrors.jenkins-ci.org/war/$JENKINS_VERSION/jenkins.war /opt/jenkins.war
RUN chmod 644 /opt/jenkins.war

# JENKINS Environment Variables
##########################
ENV JENKINS_HOME /var/jenkins_home
ENV JENKINS_SLAVE_AGENT_PORT 50000
ENV JAVA_OPTS="-Xmx8192m"
ENV JENKINS_OPTS="--handlerCountStartup=100 --handlerCountMax=300 --logfile=/var/log/jenkins/jenkins.log  --webroot=/var/cache/jenkins/war"
#ENV TINI_SHA 066ad710107dc7ee05d3aa6e4974f01dc98f3888
# Use tini as subreaper in Docker container to adopt zombie processes
#RUN curl -fsSL https://github.com/krallin/tini/releases/download/v0.5.0/tini-static -o /bin/tini && chmod +x /bin/tini && echo "$TINI_SHA /bin/tini" | sha1sum -c -

# Plugins
##########
ENV JENKINS_PLUGINS_LOCAL $JENKINS_HOME/plugins
ENV JENKINS_PLUGINS_REMOTE https://updates.jenkins-ci.org/download/plugins
#build-pipeline-plugin dependency
ADD $JENKINS_PLUGINS_REMOTE/build-pipeline-plugin/1.4.9/build-pipeline-plugin.hpi   $JENKINS_PLUGINS_LOCAL/build-pipeline-plugin.hpi
ADD $JENKINS_PLUGINS_REMOTE/jquery/1.7.2-1/jquery.hpi                               $JENKINS_PLUGINS_LOCAL/jquery.hpi
#GIT Plugin dependency
ADD $JENKINS_PLUGINS_REMOTE/git/2.4.2/git.hpi                                       $JENKINS_PLUGINS_LOCAL/git.hpi
ADD $JENKINS_PLUGINS_REMOTE/git-client/1.19.3/git-client.hpi                        $JENKINS_PLUGINS_LOCAL/git-client.hpi
ADD $JENKINS_PLUGINS_REMOTE/scm-api/1.0/scm-api.hpi                                 $JENKINS_PLUGINS_LOCAL/scm-api.hpi
ADD $JENKINS_PLUGINS_REMOTE/parameterized-trigger/2.30/parameterized-trigger.hpi    $JENKINS_PLUGINS_LOCAL/parameterized-trigger.hpi
ADD $JENKINS_PLUGINS_REMOTE/promoted-builds/2.24.1/promoted-builds.hpi              $JENKINS_PLUGINS_LOCAL/promoted-builds.hpi
#
ADD $JENKINS_PLUGINS_REMOTE/gradle/1.24/gradle.hpi                                                                      $JENKINS_PLUGINS_LOCAL/gradle.hpi
#
ADD $JENKINS_PLUGINS_REMOTE/nexus-artifact-uploader/2.1.2/nexus-artifact-uploader.hpi $JENKINS_PLUGINS_LOCAL/nexus-artifact-uploader.hpi
#Docker Plugin dependency
ADD $JENKINS_PLUGINS_REMOTE/docker-plugin/0.16.0/docker-plugin.hpi                  $JENKINS_PLUGINS_LOCAL/docker-plugin.hpi
ADD $JENKINS_PLUGINS_REMOTE/token-macro/1.7/token-macro.hpi                                             $JENKINS_PLUGINS_LOCAL/token-macro.hpi
ADD $JENKINS_PLUGINS_REMOTE/ssh-slaves/1.6/ssh-slaves.hpi                                               $JENKINS_PLUGINS_LOCAL/ssh-slaves.hpi
ADD $JENKINS_PLUGINS_REMOTE/durable-task/1.3/durable-task.hpi                                           $JENKINS_PLUGINS_LOCAL/durable-task.hpi
#Rundeck plugin
ADD $JENKINS_PLUGINS_REMOTE/rundeck/3.5.1/rundeck.hpi                               $JENKINS_PLUGINS_LOCAL/rundeck.hpi
#SONAR plugin
ADD $JENKINS_PLUGINS_REMOTE/sonar/2.3/sonar.hpi                                     $JENKINS_PLUGINS_LOCAL/sonar.hpi

ENTRYPOINT ["java", "-jar", "/opt/jenkins.war"]

