import jenkins.model.*
import hudson.security.*
import java.util.logging.Logger

Logger.global.info("[Running] startup script")

def instance = Jenkins.getInstance()

def hudsonRealm = new HudsonPrivateSecurityRealm(false)
hudsonRealm.createAccount("jenkins-user","jenkins1234")
instance.setSecurityRealm(hudsonRealm)

def strategy = new hudson.security.FullControlOnceLoggedInAuthorizationStrategy()
strategy.setAllowAnonymousRead(false)
instance.setAuthorizationStrategy(strategy)

instance.save()
