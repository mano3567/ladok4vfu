package se.su.it.ladok

import grails.util.Environment


class LoginInterceptor {
    int order = 30

    UserService userService

    LoginInterceptor() {
        matchAll().excludes(controller: 'public', action: 'index')
    }

    boolean before() {
        if(!session.getAttribute('eppn')) {
            String affiliation = request.getAttribute("affiliation")?.trim() as String
            String displayName = request.getAttribute('displayName')?.trim() as String
            String entitlement = request.getAttribute('entitlement')?.trim() as String
            String emailAddress = request.getAttribute('mail')?.trim() as String
            String eppn = request.getAttribute('eppn')?.trim() as String
            String givenName = request.getAttribute('givenName')?.trim() as String
            String norEduPersonNIN = request.getAttribute('norEduPersonNIN')?.trim() as String
            String socialSecurityNumber = request.getAttribute('socialSecurityNumber')?.trim() as String
            String sn = request.getAttribute('sn')?.trim() as String

            if(eppn) {
                userService.updateUser(eppn, givenName, sn, displayName, emailAddress, norEduPersonNIN, socialSecurityNumber, affiliation, entitlement)
                session.setAttribute('eppn', eppn)
            } else if (Environment.current == Environment.DEVELOPMENT) {
                userService.updateUser('donald.duck@toontown.org', 'Kalle', 'Anka', 'Kalle Anka', 'kalle.anka@ankeborg.se', '19123456-7890', '123456-7890', 'employee@ab.se;student@ab.se', 'lalala;hohoho')
                session.setAttribute('eppn', 'donald.duck@toontown.org')
            }
        }
        if(!session.getAttribute('eppn')) {
            redirect(controller: 'public', action: 'index')
            return false
        }
        return true
    }

    boolean after() {
        return true
    }

    void afterView() {
        // no-op
    }
}
