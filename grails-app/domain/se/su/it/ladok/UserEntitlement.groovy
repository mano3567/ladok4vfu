package se.su.it.ladok

class UserEntitlement {
    Entitlement entitlement
    Date dateCreated
    Date lastUpdated
    User user

    static constraints = {
        entitlement(nullable: false)
        dateCreated(nullable: true)
        lastUpdated(nullable: true)
        user(nullable: false, unique: 'entitlement')
    }
}
