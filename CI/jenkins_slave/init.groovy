import jenkins.*
import hudson.*
import com.cloudbees.plugins.credentials.*
import com.cloudbees.plugins.credentials.common.*
import com.cloudbees.plugins.credentials.domains.*
import com.cloudbees.jenkins.plugins.sshcredentials.impl.*
import hudson.plugins.sshslaves.*;
import hudson.model.*
import jenkins.model.*
import hudson.security.*


// SET SLAVE PORT AND NUMBER OF EXECUTORS AT MASTER NODE( im setting it to 5, you may declare it after you know you have enough RAM)
// Jenkins eats a lot of RAM :D 
def instance = Jenkins.getInstance()
instance.setNumExecutors(2)
instance.setSlaveAgentPort([50000])
// Create a global credential to work with git, this is needed to access the git related jobs.
// which means all the jobs, since all our jobs are somewhat working with git :)
// This assumes there is a ssh private key in /root/.ssh/ which i actually added in dockerfile , no need to worry
global_domain = Domain.global()
credentials_store =
Jenkins.instance.getExtensionList(
'com.cloudbees.plugins.credentials.SystemCredentialsProvider'
)[0].getStore()
credentials = new BasicSSHUserPrivateKey(
CredentialsScope.GLOBAL,
null,
"root",
new BasicSSHUserPrivateKey.UsersPrivateKeySource(),
"",
"")
credentials_store.addCredentials(global_domain, credentials)
