package se.su.it.ladok

class ConfigValue {
    boolean booleanValue = false
    Date dateCreated
    double doubleValue = 0.0
    int intValue = 0
    Date lastUpdated
    String name
    String stringValue ="-"

    static constraints = {
        dateCreated(nullable: true)
        lastUpdated(nullable: true)
        name(nullable: false, blank: false, unique: true)
        stringValue(nullable: false, blank: false)
    }
}
