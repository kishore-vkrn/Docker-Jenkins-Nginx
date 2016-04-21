# Dockerize Jenkins

![](https://raw.githubusercontent.com/sinohore/temp/master/Capture.PNG)

Dcokerfile for Jenkins image with 

#### Pre installed tools
* JAVA
* GIT
* MAVEN
* TINI

#### Customized plugins 
* Build-pipeline-plugin
* GIT plugin
* Gradle
* Nexus
* Docker Plugin
* Rundeck
* Nexus
* Sonar

#### Customized Jenkins configurations 

* Default username devops / password devops 
* Default configurations of Slave nodes in docker plugin _ [make sure to change the host name]_

#### Directions to Use
**Prerequisite**: Make sure that that Docker, Docker-compose has been installed and the docker API has been enabled to listen on 4243. Then type

#### `docker-compose up`

jenkins_master - contains the dockerfile for Jenkins Master. The JAVA_OPTS & JENKINS_OPTS variables can be defined so as to start the container with required configurations 

jenkins_nginx - load balancer for the jenkins master and on-demand jenkins slave 
