package se.su.it.ladok

class BootStrap {
    ConfigurationService configurationService
    LadokService ladokService

    def init = { servletContext ->
        Edu.values().each {Edu edu ->
            if(ConfigValue.countByName("ladok3.enabled.for.${edu.toString()}")<1) {
                configurationService.setLadokEnabledForEdu(edu, false)
            }
        }
        if(ConfigValue.countByName("ladok3.URL")<1) {
            configurationService.setLadok3Url("https://api.ladok.se")
        }
        if(ConfigValue.countByName("ladok3.feedURL")<1) {
            configurationService.setLadokFeed3Url("https://api.ladok.se/handelser/feed/")
        }
//        ladokService.updateLadok3BevisBenamning(Edu.SU)
//        ladokService.updateLadok3Finansieringsform(Edu.SU)
//        ladokService.updateLadok3Organizations(Edu.SU)
//        ladokService.updateLadok3Periods(Edu.SU)
//        ladokService.updateLadok3StudieLokalisering(Edu.SU)
//        ladokService.updateLadok3StudieTakt(Edu.SU)
//        ladokService.updateLadok3UndervisningsForm(Edu.SU)
//        ladokService.updateLadok3UndervisningsTid(Edu.SU)
    }

    def destroy = {
    }
}
