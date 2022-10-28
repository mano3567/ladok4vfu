package se.su.it.ladok

class Entitlement {
    Date dateCreated
    String entitlement
    Date lastUpdated

    static constraints = {
        entitlement(nullable: false, blank: false, unique: true)
        dateCreated(nullable: true)
        lastUpdated(nullable: true)
    }
}
