package se.su.it.ladok

class User {
    Date dateCreated
    String displayName
    String emailAddress
    String eppn
    String givenName
    Date lastUpdated
    String norEduPersonNin
    String sn
    String socialSecurityNumber

    static constraints = {
        dateCreated(nullable: true)
        displayName(nullable: false, blank: false)
        emailAddress(nullable: false, blank: false)
        eppn(nullable: false, blank: false, unique: true)
        givenName(nullable: false, blank: false)
        lastUpdated(nullable: true)
        norEduPersonNin(nullable: true)
        sn(nullable: false, blank: false)
        socialSecurityNumber(nullable: true)
    }
}
