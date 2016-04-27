import jenkins.model.Jenkins
import hudson.security.*
import jenkins.model.*

def adminun = "admin"
def adminpwd = "admin"
def normalun = "user"
def normalpwd = "user"
println "getting in"
def instance = Jenkins.getInstance()
def hudsonRealm = new HudsonPrivateSecurityRealm(false)
    hudsonRealm.createAccount(adminun, adminpwd)
    def strategy = new GlobalMatrixAuthorizationStrategy()
    strategy.add(Jenkins.ADMINISTER, adminun)
    instance.setAuthorizationStrategy(strategy)
    println "getting in1"
    hudsonRealm.createAccount(normalun, normalpwd)
	println "getting in2"
println "getting in3"
instance.setSecurityRealm(hudsonRealm)
instance.save()
