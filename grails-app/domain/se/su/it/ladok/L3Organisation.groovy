package se.su.it.ladok

class L3Organisation {
    String benamningEn
    String benamningSv
    Date dateCreated
    Edu edu
    Date giltighetsPeriodSlutDatum
    Date lastUpdated
    int organisationsEnhetsTyp = -1
    String organisationsKod
    String uid

    static constraints = {
        benamningEn(nullable: true)
        benamningSv(nullable: false, blank: false)
        dateCreated(nullable: true)
        edu(nullable: false)
        giltighetsPeriodSlutDatum(nullable: true)
        lastUpdated(nullable: true)
        organisationsKod(nullable: false, blank: false, unique: 'edu')
        uid(nullable: false, blank: false, unique: true)
    }
}
