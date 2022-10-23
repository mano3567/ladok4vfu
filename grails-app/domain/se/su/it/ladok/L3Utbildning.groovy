package se.su.it.ladok

class L3Utbildning {
    boolean avvecklad = false
    String benamning
    String benamningEn
    String benamningSv
    Date dateCreated
    Edu edu
    Date lastUpdated
    boolean senasteVersion = false
    String uid
    String utbildningsKod
    int utbildningsTypId = 0
    String utbildningsUid
    int versionsNummer = 0

    static constraints = {
        benamning(nullable: false, blank: false)
        benamningEn(nullable: true)
        benamningSv(nullable: false, blank: false)
        dateCreated(nullable: true)
        edu(nullable: false)
        lastUpdated(nullable: true)
        uid(nullable: false, blank: false)
        utbildningsKod(nullable: false, blank: false)
        utbildningsUid(nullable: false, blank: false, unique: 'edu')
    }

    static List<String> getImplementedEducationTypeCodes() {
        List<String> educationTypes = []
        L3Kurs.UTBILDNINGTYPER.each {String code ->
            educationTypes << code
        }
        L3KursPaketering.UTBILDNINGTYPER.each {String code ->
            educationTypes << code
        }
        L3Program.UTBILDNINGTYPER.each {String code ->
            educationTypes << code
        }
        L3ProgramInriktning.UTBILDNINGTYPER.each {String code ->
            educationTypes << code
        }
        return educationTypes.unique().sort()
    }

    L3UtbildningsTyp getUtbildningsTyp() {
        return L3UtbildningsTyp.findByEduAndLadokId(edu, utbildningsTypId)
    }
}
