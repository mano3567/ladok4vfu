package se.su.it.ladok

class LadokController {
    final static String defaultAction = "index"

    ConfigurationService configurationService
    LadokService ladokService

    def checkCertStatus() {
        Map<Edu, Map<String, String>> status = [:]
        Edu.values().each { Edu edu ->
            String expirationDate = ladokService.getExpirationDateForLadokCert(edu)
            Map<String, String> statusByEdu = [
                    'enabled': configurationService.isLadokEnabledForEdu(edu),
                    'expirationDate': expirationDate,
                    'path': configurationService.getPathForCertByEdu(edu)
            ]
            status.put(edu, statusByEdu)
        }
        [status: status]
    }

    def index() {
        Edu edu = params.edu?.trim() ? Edu.findByName(params.edu.trim() as String) : null
        List<Edu> edus = Edu.values().sort {it.fullName}
        if(params.startL3BasicsImport && edu) {
            UpdateL3BasicsJob.triggerNow([edu: edu.name])
        }
        if(params.startL3EducationsImport && edu) {
            UpdateEducationsJob.triggerNow([edu: edu.name])
        }
        if(params.testEvent) {
            L3Utbildning education = L3KursPaketering.findByEdu(Edu.SU, [max: 1])
            ladokService.testUpdateEducationEvent(education.uid, education.edu)
        }
        [edu: edu, edus: edus]
    }
}
