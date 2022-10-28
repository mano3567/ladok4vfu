package se.su.it.ladok

class Affiliation {
    String affiliation
    Date dateCreated
    Date lastUpdated

    static constraints = {
        affiliation(nullable: false, blank: false, unique: true)
        dateCreated(nullable: true)
        lastUpdated(nullable: true)
    }
}
