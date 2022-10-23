package se.su.it.ladok

class User {
    Date dateCreated
    String displayName
    String eppn
    String givenName
    Date lastUpdated
    String sn

    static constraints = {
        dateCreated(nullable: true)
        displayName(nullable: false, blank: false)
        eppn(nullable: false, blank: false, unique: true)
        givenName(nullable: false, blank: false)
        lastUpdated(nullable: true)
        sn(nullable: false, blank: false)
    }
}
