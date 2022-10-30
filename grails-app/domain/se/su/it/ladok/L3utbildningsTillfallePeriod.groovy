package se.su.it.ladok

class L3utbildningsTillfallePeriod {
    Date dateCreated
    Date forstaUndervisningsDatum
    L3UtbildningsTillfalle l3UtbildningsTillfalle
    Date lastUpdated
    double omfattning = 0.0
    Date sistaUndervisningsDatum


    static constraints = {
        dateCreated(nullable: true)
        forstaUndervisningsDatum(nullable: false)
        l3UtbildningsTillfalle(nullable: false)
        lastUpdated(nullable: true)
        sistaUndervisningsDatum(nullable: false)
    }
}
