package se.su.it.ladok

import grails.core.GrailsApplication
import grails.gorm.transactions.NotTransactional
import grails.gorm.transactions.Transactional

@Transactional
class ConfigurationService {
    GrailsApplication grailsApplication

    @Transactional(readOnly = true)
    boolean getConfigBoolean(String someKey) {
        ConfigValue configValue = ConfigValue.findByName(someKey?.trim())
        return configValue?.booleanValue
    }

    @Transactional(readOnly = true)
    double getConfigDouble(String someKey) {
        ConfigValue configValue = ConfigValue.findByName(someKey?.trim())
        return configValue?.doubleValue ?: 0.0
    }

    @Transactional(readOnly = true)
    int getConfigInteger(String someKey) {
        ConfigValue configValue = ConfigValue.findByName(someKey?.trim())
        return configValue?.intValue ?: 0
    }

    @Transactional(readOnly = true)
    String getConfigString(String someKey) {
        ConfigValue configValue = ConfigValue.findByName(someKey?.trim())
        return configValue?.stringValue
    }

    @Transactional(readOnly = true)
    String getLadokFeed3Url() {
        ConfigValue configValue = ConfigValue.findByName('ladok3.feedURL')
        return configValue?.stringValue
    }

    @Transactional(readOnly = true)
    String getLadok3Url() {
        ConfigValue configValue = ConfigValue.findByName('ladok3.URL')
        return configValue?.stringValue
    }

    @NotTransactional
    String getPassWordForCertByEdu(Edu edu) {
        String path = null
        if(edu) {
            path = grailsApplication.config.getProperty("ladok3Settings.${edu.toString().toLowerCase()}.certPassWord") as String
        }
        return path
    }

    @NotTransactional
    String getPathForCertByEdu(Edu edu) {
        String path = null
        if(edu) {
            path = grailsApplication.config.getProperty("ladok3Settings.${edu.toString().toLowerCase()}.certPath") as String
        }
        return path
    }

    @Transactional(readOnly = true)
    boolean isLadokEnabledForEdu(Edu edu) {
        boolean isEnabled = false
        if(edu) {
            ConfigValue configValue = ConfigValue.findByName("ladok3.enabled.for.${edu.toString()}")
            if(configValue) {
                isEnabled = configValue.booleanValue
            }
        }
        return isEnabled
    }

    @Transactional
    void setConfigBoolean(String someKey, boolean someValue) {
        if(someKey?.trim()) {
            ConfigValue configValue = ConfigValue.findOrCreateByName(someKey.trim())
            configValue.booleanValue = someValue
            configValue.save(failOnError: true)
        }
    }

    @Transactional
    void setConfigDouble(String someKey, double someValue) {
        if(someKey?.trim()) {
            ConfigValue configValue = ConfigValue.findOrCreateByName(someKey.trim())
            configValue.doubleValue = someValue
            configValue.save(failOnError: true)
        }
    }

    @Transactional
    void setConfigInteger(String someKey, int someValue) {
        if(someKey?.trim()) {
            ConfigValue configValue = ConfigValue.findOrCreateByName(someKey.trim())
            configValue.intValue = someValue
            configValue.save(failOnError: true)
        }
    }

    @Transactional
    void setConfigString(String someKey, String someValue) {
        if(someKey?.trim() && someValue?.trim()) {
            ConfigValue configValue = ConfigValue.findOrCreateByName(someKey.trim())
            configValue.stringValue = someValue.trim()
            configValue.save(failOnError: true)
        }
    }

    @Transactional
    void setLadokFeed3Url(String someValue) {
        ConfigValue configValue = ConfigValue.findOrCreateByName("ladok3.feedURL")
        configValue.stringValue = someValue?.trim()
        configValue.save(failOnError: true)
    }

    @Transactional
    void setLadok3Url(String someValue) {
        ConfigValue configValue = ConfigValue.findOrCreateByName("ladok3.URL")
        configValue.stringValue = someValue?.trim()
        configValue.save(failOnError: true)
    }

    @Transactional
    void setLadokEnabledForEdu(Edu edu, boolean newValue) {
        if(edu) {
            ConfigValue configValue = ConfigValue.findOrCreateByName("ladok3.enabled.for.${edu.toString()}")
            configValue.booleanValue = newValue
            configValue.save(failOnError: true)
        }
    }
}
