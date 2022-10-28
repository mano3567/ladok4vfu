package se.su.it.ladok

class UserAffiliation {
    Affiliation affiliation
    Date dateCreated
    Date lastUpdated
    User user

    static constraints = {
        affiliation(nullable: false)
        dateCreated(nullable: true)
        lastUpdated(nullable: true)
        user(nullable: false, unique: 'affiliation')
    }
}
