 docker run -it -p 8000:8080 -p 50000:50000 -v /home/ubuntu/data:/var/jenkins_home jenkins_master
 
 --------
 ADD & COPY commands
ADD for auto download and untar
COPY just copies it
-------------------------------
Docker tree
docker run --rm -v /var/run/docker.sock:/var/run/docker.sock nate/dockviz images -t
-------------------------------

Compiose: Differenrt versions
export COMPOSE_API_VERSION=1.18


set https_proxy=https://393565:Worknow2@10.155.103.179:6050

git config --global http.proxy http.proxy https://393565:Worknow2@10.155.103.179:6050


----
Docker Compose: https://github.com/docker/compose/releases
 
 export COMPOSE_API_VERSION=1.18
 docker run -it --link db_test1:db table_crete_with_port

overrride imnage Entryponit
docker run -ti --entrypoint=/bin/bash myimage -s

override container entrypoing
docker exec -ti cf10f001d0c9 bash

 docker exec -ti <container_id> <cmd> 
 -------
 
 docker daemon &
 ---
 
ubuntu
apt-get install software-properties-common

devops

----------
 su - postgres
 psql -d postgres -U postgres
  \dt
  DROP TABLE
  
  readlink -f $(which java)
  
 
