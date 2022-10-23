package se.su.it.ladok

class L3UndervisningsForm {
    String benamningEn
    String benamningSv
    String beskrivningSv
    String beskrivningEn
    Date dateCreated
    Edu edu
    String kod
    int ladokId = -1
    Date lastUpdated
    Date slutDatum
    Date startDatum

    static constraints = {
        benamningEn(nullable: true)
        benamningSv(nullable: true)
        beskrivningEn(nullable: true)
        beskrivningSv(nullable: true)
        dateCreated(nullable: true)
        edu(nullable: false, unique: 'ladokId')
        kod(nullable: false, blank: false)
        lastUpdated(nullable: true)
        slutDatum(nullable: true)
        startDatum(nullable: true)
    }
}
