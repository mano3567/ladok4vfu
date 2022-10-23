package se.su.it.ladok

class L3Period {
    String benamningEn
    String benamningSv
    Date dateCreated
    Edu edu
    Date fromDatum
    String kod
    int ladokId = -1
    int larosateId = -1
    Date lastUpdated
    int periodTypId = -1
    Date tomDatum

    static constraints = {
        benamningEn(nullable: false, blank: false)
        benamningSv(nullable: false, blank: false)
        dateCreated(nullable: true)
        edu(nullable: false)
        fromDatum(nullable: false)
        kod(nullable: false, blank: false, unique: 'edu')
        lastUpdated(nullable: true)
        tomDatum(nullable: false)
    }
}
