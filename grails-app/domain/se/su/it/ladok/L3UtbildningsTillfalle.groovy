package se.su.it.ladok

class L3UtbildningsTillfalle {
    Date dateCreated
    Edu edu
    int finansieringsFormId = 0
    boolean installt = false
    Date lastUpdated
    double omfattningsVarde = 0.0
    String organisationsUid
    Date slutDatum
    double specificeradOmfattning = 0.0 // SpecificeradOmfattning
    Date startDatum
    int startPeriodId = 0
    int status = 0
    int studieLokaliseringId = 0
    int studieTaktId = 0
    String tillfallesKod
    String uid
    int undervisningsTidId = 0
    String utbildningsInstansUid
    int utbildningsTypId = 0

    static constraints = {
        dateCreated(nullable: true)
        edu(nullable: false)
        lastUpdated(nullable: true)
        organisationsUid(nullable: false, blank: false)
        slutDatum(nullable: false)
        startDatum(nullable: false)
        tillfallesKod(nullable: false, blank: false, unique: ['edu', 'startPeriodId'])
        uid(nullable: false, blank: false, unique: true)
        utbildningsInstansUid(nullable: false, blank: false)
    }

    List<L3utbildningsTillfallePeriod> getTillfallesPerioder() {
        return L3utbildningsTillfallePeriod.findAllByL3UtbildningsTillfalle(this, [sort: 'forstaUndervisningsDatum'])
    }
}
