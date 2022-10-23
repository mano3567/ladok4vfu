package se.su.it.ladok

class L3UtbildningsTyp {
    boolean avserTillfalle = false
    String benamningEn
    String benamningSv
    String beskrivningEn
    String beskrivningSv
    Date dateCreated
    Edu edu
    int forvaldOmfattning = 0
    String grundTyp
    boolean harOmfattning = true
    boolean individuell = false
    boolean kanUtannonseras = false
    String kod
    int ladokId = 0
    Date lastUpdated
    int nivaInomStudieordningId = 0
    boolean sjalvstandig = true
    Date slutDatum
    int sorteringsOrdning = 0
    Date startDatum
    int studieOrdningId = 0
    String tillatnaUtbildningstyperIStruktur
    String tillfalleInomUtbildningstyper
    boolean versionsHanteras = true

    static constraints = {
        benamningEn(nullable: false, blank: false)
        benamningSv(nullable: false, blank: false)
        beskrivningEn(nullable: true)
        beskrivningSv(nullable: true)
        dateCreated(nullable: true)
        edu(nullable: false, unique: 'ladokId')
        grundTyp(nullable: false, blank: false)
        kod(nullable: false, blank: false, unique: 'edu')
        lastUpdated(nullable: true)
        slutDatum(nullable: true)
        startDatum(nullable: true)
        tillatnaUtbildningstyperIStruktur(nullable: true)
        tillfalleInomUtbildningstyper(nullable: true)
    }
}
