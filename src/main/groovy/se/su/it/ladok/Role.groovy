package se.su.it.ladok

import groovy.transform.CompileStatic

@CompileStatic
enum Role {
    PUBLIC('public', -1),
    SYSADMIN('SYSADMIN', 999)

    private String itsName
    private int itsValue

    Role(String theName, int theValue) {
        itsName = theName?.trim()
        itsValue = (theValue<0) ? -1 : ((theValue>999) ? 999 : theValue)
    }

    static Role findByName(String someName) {
        return Role.values().find{it.itsName==someName}
    }

    static Role findByValue(int someValue) {
        return Role.values().find{it.itsValue==someValue}
    }

    String getName() {
        return itsName
    }

    int getValue() {
        return itsValue
    }
}