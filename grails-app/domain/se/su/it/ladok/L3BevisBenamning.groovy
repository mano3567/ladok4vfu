package se.su.it.ladok

class L3BevisBenamning {
    String anteckning
    String benamningEn
    String benamningSv
    Date dateCreated
    Edu edu
    Date giltighetsPeriodSlutDatum
    String kod
    Date lastUpdated
    double omfattning = 0.0
    String uid

    static constraints = {
        anteckning(nullable: true, blank: true)
        benamningEn(nullable: false, blank: false)
        benamningSv(nullable: false, blank: false)
        dateCreated(nullable: true)
        edu(nullable: false)
        giltighetsPeriodSlutDatum(nullable: true)
        kod(nullable: false, blank: false, unique: 'edu')
        lastUpdated(nullable: true)
        uid(nullable: false, blank: false, unique: true)
    }
}
