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
        [:]
    }

    def testEducationImport() {
        L3Utbildning.getImplementedEducationTypeCodes().each {String kod ->
            Edu.values().each { Edu edu ->
                log.info "Trying to import ${kod} for ${edu}"
                ladokService.testUpdateAFewUtbildningarPerTypeAndEdu(kod, edu)
            }
        }
        return redirect(action: 'index')
    }
}
