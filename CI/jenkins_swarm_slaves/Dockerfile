FROM ubuntu:14.04
MAINTAINER Kishore

# Expose Ports for web and slave agents
EXPOSE 8080
EXPOSE 50000

# Update & Install common packages
RUN apt-get update && apt-get install -y wget git curl zip net-tools software-properties-common

# Install a basic SSH server
RUN apt-get install -y openssh-server
RUN sed -i 's|session    required     pam_loginuid.so|session    optional     pam_loginuid.so|g' /etc/pam.d/sshd
RUN mkdir -p /var/run/sshd

######################################################## BUILD TOOLS #########################################################
# GIT
#####
RUN apt-get install -y git

# JAVA
############
# Auto-accept the Oracle JDK license
RUN \
  echo oracle-java8-installer shared/accepted-oracle-license-v1-1 select true | debconf-set-selections && \
  add-apt-repository -y ppa:webupd8team/java && \
  apt-get update && \
  apt-get install -y oracle-java8-installer && \
  rm -rf /var/lib/apt/lists/* && \
  rm -rf /var/cache/oracle-jdk8-installer

# Define commonly used JAVA_HOME variable
ENV JAVA_HOME /usr/lib/jvm/java-8-oracle

# MAVEN
#######
ENV MAVEN_VERSION 3.3.3
RUN mkdir -p /usr/share/maven \
  && curl -fsSL http://apache.osuosl.org/maven/maven-3/$MAVEN_VERSION/binaries/apache-maven-$MAVEN_VERSION-bin.tar.gz \
    | tar -xzC /usr/share/maven --strip-components=1 \
  && ln -s /usr/share/maven/bin/mvn /usr/bin/mvn

ENV MAVEN_HOME /usr/share/maven

# `/usr/share/jenkins/ref/` contains all reference configuration we want 
# to set on a fresh new installation. Use it to bundle additional plugins 
# or config file with your custom jenkins Docker image
RUN mkdir -p /usr/share/jenkins/ref/init.groovy.d

#COPY user.groovy /usr/share/jenkins/ref/init.groovy.d/

# Install Tini
ENV TINI_SHA 066ad710107dc7ee05d3aa6e4974f01dc98f3888

# Use tini as subreaper in Docker container to adopt zombie processes
COPY tini-static  /bin/tini 
RUN  chmod +x /bin/tini  && echo "$TINI_SHA /bin/tini" 

########################################################### JENKINS  ###########################################################
ENV JENKINS_VERSION 1.647
##########################
RUN curl -fsSL http://repo.jenkins-ci.org/public/org/jenkins-ci/main/jenkins-war/${JENKINS_VERSION}/jenkins-war-${JENKINS_VERSION}.war -o /opt/jenkins.war
RUN chmod 644 /opt/jenkins.war

# Plugins
##########
ENV JENKINS_HOME /var/jenkins
ENV JENKINS_PLUGINS_LOCAL $JENKINS_HOME/plugins
#ENV JENKINS_UC https://updates.jenkins.io
ENV JENKINS_UC http://jenkins-updates.cloudbees.com
ENV COPY_REFERENCE_FILE_LOG $JENKINS_HOME/copy_reference_file.log
ENV JAVA_OPTS="-Xmx8192m"
ENV JENKINS_OPTS="--handlerCountStartup=100 --handlerCountMax=300  --webroot=/var/cache/jenkins/war"

# SWARM SETUP
ENV JENKINS_SWARM_VERSION 2.0
ENV HOME /home/jenkins-slave

# Initializations
RUN mkdir -p /usr/share/jenkins/ref/
RUN mkdir -p $JENKINS_HOME
RUN mkdir -p $JENKINS_PLUGINS_LOCAL
RUN mkdir -p ~/.ssh/
RUN touch $JENKINS_HOME/copy_reference_file.log
COPY jenkins.sh /usr/local/bin/jenkins.sh
RUN chmod +x /usr/local/bin/jenkins.sh
COPY user.groovy $JENKINS_HOME/init.groovy

COPY id_rsa ~/.ssh/id_rsa
RUN eval "$(ssh-agent -s)"
#Plugins Add
COPY plugins.sh /usr/local/bin/plugins.sh
COPY plugins.txt /usr/share/jenkins/plugins.txt

# Adding config.xml and user "devops" with password "devops"
#ADD https://raw.githubusercontent.com/sinohore/Docker/master/Jenkins_Nginx/config.xml $JENKINS_HOME/config.xml
#ADD https://raw.githubusercontent.com/sinohore/Docker/master/Jenkins_Nginx/credentials.xml $JENKINS_HOME/credentials.xml
#RUN mkdir -p $JENKINS_HOME/users/devops
#ADD https://raw.githubusercontent.com/sinohore/Docker/master/Jenkins_Nginx/users/devops/config.xml $JENKINS_HOME/users/devops/config.xml

ENV COPY_REFERENCE_FILE_LOG $JENKINS_HOME/copy_reference_file.log

#ADD $JENKINS_PLUGINS_REMOTE/copyartifact/1.37/copyartifact.hpi                      $JENKINS_PLUGINS_LOCAL/copyartifact.hpi
#ADD $JENKINS_PLUGINS_REMOTE/promoted-builds/2.24.1/promoted-builds.hpi              $JENKINS_PLUGINS_LOCAL/promoted-builds.hpi
# Build-pipeline-plugin
#ADD $JENKINS_PLUGINS_REMOTE/build-pipeline-plugin/1.4.9/build-pipeline-plugin.hpi   $JENKINS_PLUGINS_LOCAL/build-pipeline-plugin.hpi
#ADD $JENKINS_PLUGINS_REMOTE/jquery/1.11.2-0/jquery.hpi                              $JENKINS_PLUGINS_LOCAL/jquery.hpi
# GIT
#ADD $JENKINS_PLUGINS_REMOTE/git/2.4.2/git.hpi                                       $JENKINS_PLUGINS_LOCAL/git.hpi
#ADD $JENKINS_PLUGINS_REMOTE/git-client/1.19.3/git-client.hpi                        $JENKINS_PLUGINS_LOCAL/git-client.hpi
#ADD $JENKINS_PLUGINS_REMOTE/parameterized-trigger/2.30/parameterized-trigger.hpi    $JENKINS_PLUGINS_LOCAL/parameterized-trigger.hpi
#ADD $JENKINS_PLUGINS_REMOTE/scm-api/1.0/scm-api.hpi                                 $JENKINS_PLUGINS_LOCAL/scm-api.hpi
# Gradle
#ADD $JENKINS_PLUGINS_REMOTE/gradle/1.24/gradle.hpi                                  $JENKINS_PLUGINS_LOCAL/gradle.hpi
# Nexus
#ADD $JENKINS_PLUGINS_REMOTE/nexus-artifact-uploader/2.1.2/nexus-artifact-uploader.hpi $JENKINS_PLUGINS_LOCAL/nexus-artifact-uploader.hpi
#ADD $JENKINS_PLUGINS_REMOTE/nexus-task-runner/0.9.2/nexus-task-runner.hpi			$JENKINS_PLUGINS_LOCAL/nexus-task-runner.hpi
# Docker Plugin dependency
#ADD $JENKINS_PLUGINS_REMOTE/docker-plugin/0.16.0/docker-plugin.hpi                  $JENKINS_PLUGINS_LOCAL/docker-plugin.hpi
#ADD $JENKINS_PLUGINS_REMOTE/token-macro/1.12.1/token-macro.hpi                      $JENKINS_PLUGINS_LOCAL/token-macro.hpi
#ADD $JENKINS_PLUGINS_REMOTE/ssh-slaves/1.9/ssh-slaves.hpi                           $JENKINS_PLUGINS_LOCAL/ssh-slaves.hpi
#ADD $JENKINS_PLUGINS_REMOTE/durable-task/1.9/durable-task.hpi                       $JENKINS_PLUGINS_LOCAL/durable-task.hpi
# Rundeck plugin
#ADD $JENKINS_PLUGINS_REMOTE/rundeck/3.5.1/rundeck.hpi                               $JENKINS_PLUGINS_LOCAL/rundeck.hpi
# SONAR plugin
#ADD $JENKINS_PLUGINS_REMOTE/sonar/2.3/sonar.hpi                                     $JENKINS_PLUGINS_LOCAL/sonar.hpi

# Tini as the entry point to manage zombie processes
#ENTRYPOINT ["/bin/tini", "--", "/usr/local/bin/jenkins.sh"]

RUN /usr/local/bin/plugins.sh /usr/share/jenkins/plugins.txt

RUN useradd -c "Jenkins Slave user" -d $HOME -m jenkins-slave
RUN curl --create-dirs -sSLo /usr/share/jenkins/swarm-client-$JENKINS_SWARM_VERSION-jar-with-dependencies.jar http://maven.jenkins-ci.org/content/repositories/releases/org/jenkins-ci/plugins/swarm-client/$JENKINS_SWARM_VERSION/swarm-client-$JENKINS_SWARM_VERSION-jar-with-dependencies.jar \
  && chmod 755 /usr/share/jenkins

COPY jenkins-slave.sh /usr/local/bin/jenkins-slave.sh

USER jenkins-slave
VOLUME /home/jenkins-slave

ENTRYPOINT ["/usr/local/bin/jenkins-slave.sh"]
