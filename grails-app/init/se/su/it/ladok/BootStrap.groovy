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
        Edu.values().each {Edu edu ->
//            ladokService.updateLadok3BevisBenamning(edu)
//            ladokService.updateLadok3Finansieringsform(edu)
//            ladokService.updateLadok3Organizations(edu)
//            ladokService.updateLadok3Periods(edu)
//            ladokService.updateLadok3StudieLokalisering(edu)
//            ladokService.updateLadok3StudieTakt(edu)
//            ladokService.updateLadok3UndervisningsForm(edu)
//            ladokService.updateLadok3UndervisningsTid(edu)
            ladokService.updateLadok3UtbildningsTyp(edu)
        }
    }

    def destroy = {
    }
}
