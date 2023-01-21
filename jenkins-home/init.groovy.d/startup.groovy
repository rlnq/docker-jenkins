import jenkins.model.Jenkins
import hudson.model.*
import java.util.logging.Logger

Logger.global.info("[Running] startup script")

configureSecurity()

Jenkins.instance.save()

buildJob('an-example-of-github-project')

Logger.global.info("[Done] startup script")

private void configureSecurity() {
    Jenkins.getInstance().disableSecurity()
}

#user
def instance = Jenkins.getInstance()

def hudsonRealm = new HudsonPrivateSecurityRealm(false)
hudsonRealm.createAccount("MyUSERNAME","MyPASSWORD")
instance.setSecurityRealm(hudsonRealm)
def strategy = new GlobalMatrixAuthorizationStrategy()
strategy.add(Jenkins.ADMINISTER, "myUSERNAME")
instance.setAuthorizationStrategy(strategy)
instance.save()
#end user

private def buildJob(String jobName) {
    Logger.global.info("Building job '$jobName")
    def job = Jenkins.instance.getJob(jobName)
    Jenkins.instance.queue.schedule(job, 0, new CauseAction(new Cause() {
        @Override
        String getShortDescription() {
            'Jenkins startup script'
        }
    }))
}
