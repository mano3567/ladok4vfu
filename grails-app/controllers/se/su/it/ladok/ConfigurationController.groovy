package se.su.it.ladok

class ConfigurationController {
    final static String defaultAction = "index"
    ConfigurationService configurationService

    def index() {
        [:]
    }
}
