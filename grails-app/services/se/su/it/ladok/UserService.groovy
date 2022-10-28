package se.su.it.ladok

import grails.gorm.transactions.Transactional

@Transactional
class UserService {
    @Transactional
    void updateUserAffiliation(String affiliation, User user) {
        if(affiliation?.trim() && user) {
            Affiliation.withTransaction {
                Affiliation.findOrCreateByAffiliation(affiliation.trim()).save(failOnError: true)
            }
            UserAffiliation.findOrCreateByAffiliationAndUser(Affiliation.findByAffiliation(affiliation.trim()), user).save(failOnError: true)
        }
    }

    @Transactional
    void updateUserEntitlement(String entitlement, User user) {
        if(entitlement?.trim() && user) {
            Entitlement.withTransaction {
                Entitlement.findOrCreateByEntitlement(entitlement.trim()).save(failOnError: true)
            }
            UserEntitlement.findOrCreateByEntitlementAndUser(Entitlement.findByEntitlement(entitlement.trim()), user).save(failOnError: true)
        }
    }

    @Transactional
    void updateUser(String eppn, String givenName, String sn, String displayName, String emailAddress, String norEduPersonNIN, String socialSecurityNumber, String affiliations, String entitlements) {
        if(eppn?.trim()) {
            User.withTransaction {
                User user = User.findOrCreateByEppn(eppn.trim())
                user.givenName = givenName?.trim()
                user.sn = sn?.trim()
                user.displayName = displayName?.trim()
                user.emailAddress = emailAddress?.trim()
                user.norEduPersonNin = norEduPersonNIN?.replaceAll('-','')?.trim()
                user.socialSecurityNumber = socialSecurityNumber?.replaceAll('-','')?.trim()
                user.save(failOnError: true)
            }
            User user = User.findByEppn(eppn.trim())
            if(user) {
                UserAffiliation.withTransaction {
                    UserAffiliation.findAllByUser(user)*.delete(flush: true)
                }
                UserEntitlement.withTransaction {
                    UserEntitlement.findAllByUser(user)*.delete(flush: true)
                }
                affiliations?.split(";").each {String affiliation ->
                    UserAffiliation.withTransaction {
                        updateUserAffiliation(affiliation, user)
                    }
                }
                entitlements?.split(";").each {String entitlement ->
                    UserEntitlement.withTransaction {
                        updateUserEntitlement(entitlement, user)
                    }
                }
            }
        }
    }
}
