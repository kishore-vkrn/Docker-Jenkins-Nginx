FROM ubuntu:14.04
MAINTAINER Kishore

EXPOSE 8080

RUN apt-get install -y software-properties-common
RUN apt-get update

######################################################## BUILD TOOLS #########################################################
# GIT
#####
RUN apt-get install -y git

RUN apt-get install -y git

# JAVA
############
# Auto-accept the Oracle JDK license

RUN apt-get install -q -y libicu52 libfontconfig libfontconfig1 libjpeg8 libpng12-0 zip

RUN apt-get update && \
    apt-get install --no-install-recommends -y openjdk-7-jre-headless && \
    apt-get clean && \
    rm -rf /var/lib/apt/lists/*

ADD http://redrockdigimark.com/apachemirror/maven/maven-3/3.3.9/binaries/apache-maven-3.3.9-bin.zip /tmp/apache-maven-3.3.9-bin.zip
RUN unzip /tmp/apache-maven-3.3.9-bin.zip 


############################################################ JENKINS  ############################################################
ENV JENKINS_VERSION 1.647
##########################
ADD http://mirrors.jenkins-ci.org/war/$JENKINS_VERSION/jenkins.war /opt/jenkins.war
RUN chmod 644 /opt/jenkins.war

# Plugins
##########
ENV JENKINS_HOME /jenkins
ENV JENKINS_PLUGINS_LOCAL $JENKINS_HOME/plugins
ENV JENKINS_PLUGINS_REMOTE https://updates.jenkins-ci.org/download/plugins

ADD $JENKINS_PLUGINS_REMOTE/build-pipeline-plugin/1.4.9/build-pipeline-plugin.hpi   $JENKINS_PLUGINS_LOCAL/build-pipeline-plugin.hpi
ADD $JENKINS_PLUGINS_REMOTE/git/2.4.2/git.hpi                                       $JENKINS_PLUGINS_LOCAL/git.hpi
ADD $JENKINS_PLUGINS_REMOTE/git-client/1.19.3/git-client.hpi                        $JENKINS_PLUGINS_LOCAL/git-client.hpi
ADD $JENKINS_PLUGINS_REMOTE/parameterized-trigger/2.30/parameterized-trigger.hpi    $JENKINS_PLUGINS_LOCAL/parameterized-trigger.hpi
ADD $JENKINS_PLUGINS_REMOTE/scm-api/1.0/scm-api.hpi                                 $JENKINS_PLUGINS_LOCAL/scm-api.hpi
ADD $JENKINS_PLUGINS_REMOTE/copyartifact/1.37/copyartifact.hpi                      $JENKINS_PLUGINS_LOCAL/copyartifact.hpi
ADD $JENKINS_PLUGINS_REMOTE/promoted-builds/2.24.1/promoted-builds.hpi              $JENKINS_PLUGINS_LOCAL/promoted-builds.hpi
ADD $JENKINS_PLUGINS_REMOTE/docker-plugin/0.16.0/docker-plugin.hpi                  $JENKINS_PLUGINS_LOCAL/docker-plugin.hpi
ADD $JENKINS_PLUGINS_REMOTE/rundeck/3.5.1/rundeck.hpi                               $JENKINS_PLUGINS_LOCAL/rundeck.hpi
ADD $JENKINS_PLUGINS_REMOTE/sonargraph-integration/1.0.2/sonargraph-integration.hpi $JENKINS_PLUGINS_LOCAL/sonargraph-integration.hpi
ADD $JENKINS_PLUGINS_REMOTE/sonar/2.3/sonar.hpi                                     $JENKINS_PLUGINS_LOCAL/sonar.hpi

ENTRYPOINT ["java", "-jar", "/opt/jenkins.war"]
EXPOSE 8080
