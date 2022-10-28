package se.su.it.ladok

class L3UtbildningsTillfalle {
    String benamningEn
    String benamningSv
    Date dateCreated
    Edu edu
    int finansieringsFormId = 0
    boolean installt = false
    Date lastUpdated
    double omfattningsVarde = 0.0
    String organisationUid
    Date slutDatum
    double specificeradOmfattning = 0.0 // SpecificeradOmfattning
    Date startDatum
    int startPeriodId = 0
    int status = 0
    int studieLokaliseringId = 0
    int studieTaktId = 0
    String tillfallesKod
    String uid
    String utbildningsInstansUid
    int utbildningsTypId = 0

    static constraints = {
        benamningEn(nullable: true)
        benamningSv(nullable: false, blank: false)
        dateCreated(nullable: true)
        edu(nullable: false)
        lastUpdated(nullable: true)
        organisationUid(nullable: false, blank: false)
        slutDatum(nullable: false)
        startDatum(nullable: false)
        tillfallesKod(nullable: false, blank: false, unique: ['edu', 'startPeriodId'])
        uid(nullable: false, blank: false, unique: true)
        utbildningsInstansUid(nullable: false, blank: false)
    }
}
