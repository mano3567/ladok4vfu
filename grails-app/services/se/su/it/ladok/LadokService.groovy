package se.su.it.ladok

import grails.gorm.transactions.NotTransactional
import grails.gorm.transactions.Transactional

import java.security.KeyStore
import java.security.cert.X509Certificate
import java.text.SimpleDateFormat

@Transactional
class LadokService {
    ConfigurationService configurationService
    HttpClientService httpClientService

    @NotTransactional
    String getExpirationDateForLadokCert(Edu edu) {
        String expirationDate = ""
        String passwd = configurationService.getPassWordForCertByEdu(edu)
        String path = configurationService.getPathForCertByEdu(edu)
        if(passwd && path) {
            KeyStore keystore = KeyStore.getInstance(KeyStore.getDefaultType())
            try {
                keystore.load(new FileInputStream(path), passwd.toCharArray())
                Enumeration aliases = keystore.aliases()
                while(aliases.hasMoreElements()) {
                    String alias = aliases.nextElement()
                    X509Certificate certificate = (X509Certificate) keystore.getCertificate(alias)
                    Date certExpiryDate = certificate.getNotAfter()
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss")
                    expirationDate = simpleDateFormat.format(certExpiryDate)
                }
            } catch(Throwable exception) {
                log.info "Problem in getExpirationDateForLadokCert(${edu.toString()}: ${exception.getMessage()}"
            }
        }
        return expirationDate
    }

    @Transactional
    void updateLadok3BevisBenamning(Edu edu) {
        if(edu) {
            int count = 0
            try {
                Map response = httpClientService.getLadok3MapFromJsonResponseByUrlAndType(edu, "/examen/bevisbenamning", "application/vnd.ladok-examen+json", [:])
                log.info("Processing (${response?.Bevisbenamning?response.Bevisbenamning.size():0}) updateLadok3BevisBenamning for edu: ${edu}")
                response.Bevisbenamning.each { Map bevisBenamning ->
                    String kod = bevisBenamning.get('Kod', null)?.trim() as String
                    if(kod) {
                        L3BevisBenamning l3BevisBenamning = L3BevisBenamning.findOrCreateByEduAndKod(edu, kod)
                        l3BevisBenamning.anteckning = bevisBenamning.get('Anteckning', null)?.trim() as String
                        if(l3BevisBenamning.anteckning?.length()>511) {
                            l3BevisBenamning.anteckning = l3BevisBenamning.anteckning.substring(0, 511)
                        }
                        Map benamning = bevisBenamning.get('Benamning', [en: '', sv: ''])
                        l3BevisBenamning.benamningEn = benamning.get('en')?.trim() as String
                        l3BevisBenamning.benamningSv = benamning.get('sv')?.trim() as String
                        l3BevisBenamning.omfattning = bevisBenamning.get('Omfattning', 0.0) as double
                        l3BevisBenamning.uid = bevisBenamning.get('Uid', null)?.trim() as String
                        l3BevisBenamning.giltighetsPeriodSlutDatum = null
                        try {
                            Map giltighetsPeriod = l3bevis.get('Giltighetsperiod', [Slutdatum: ''])
                            String dateString = giltighetsPeriod.Slutdatum?.trim() as String
                            if(dateString) {
                                SimpleDateFormat simpleDateFormat = new SimpleDateFormat('yyyy-MM-dd')
                                l3BevisBenamning.giltighetsPeriodSlutDatum = simpleDateFormat.parse(dateString)
                            }
                        } catch(Throwable exception) {
                            l3BevisBenamning.giltighetsPeriodSlutDatum = null
                        }
                        l3BevisBenamning.save(failOnError: true)

                        count++
                        if((count % 100) == 0) {
                            log.info("Number of bevisbenamningar this far (${edu}): ${count}")
                        }
                    }
                }
            } catch(Throwable exception) {
                log.warn "Some problem calling /kataloginformation/grunddata/period: ${exception.getMessage()}"
            }
        }
    }

    void updateLadok3Finansieringsform(Edu edu) {
        int count = 0
        Map response = httpClientService.getLadok3MapFromJsonResponseByUrlAndType(edu, "/utbildningsinformation/grunddata/finansieringsform", "application/vnd.ladok-utbildningsinformation+json")
        log.info("Processing (${response?.Finansieringsform?response.Finansieringsform.size():0}) updateLadok3Finansieringsform for edu: ${edu}")
        if (response && response.Finansieringsform) {
            response.Finansieringsform.each { Map finansieringsForm ->
                if(!edu || (!finansieringsForm?.ID || finansieringsForm.ID.isEmpty())) {
                    throw new Exception("Missing <edu> or <finansieringsform.ID")
                }
                if(finansieringsForm) {
                    int ladokId = finansieringsForm.ID as int
                    L3FinansieringsForm l3FinansieringsForm = L3FinansieringsForm.findOrCreateByEduAndLadokId(edu, ladokId)
                    l3FinansieringsForm.edu = edu
                    l3FinansieringsForm.ladokId = ladokId

                    l3FinansieringsForm.benamningEn = finansieringsForm.Benamning?.en?.trim() as String
                    l3FinansieringsForm.benamningSv = finansieringsForm.Benamning?.sv?.trim() as String
                    l3FinansieringsForm.beskrivningEn = finansieringsForm.Beskrivning?.en?.trim() as String
                    l3FinansieringsForm.beskrivningSv = finansieringsForm.Beskrivning?.sv?.trim() as String

                    l3FinansieringsForm.kod = finansieringsForm.Kod?.trim() as String

                    String someDate = finansieringsForm.Giltighetsperiod?.Slutdatum?.trim() as String
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat('yyyy-MM-dd')
                    l3FinansieringsForm.slutDatum = someDate ? simpleDateFormat.parse(someDate) : null

                    someDate = finansieringsForm.Giltighetsperiod?.Startdatum?.trim() as String
                    l3FinansieringsForm.startDatum = someDate ? simpleDateFormat.parse(someDate) : null

                    l3FinansieringsForm.save(failOnError: true)

                    count++
                    if((count % 100) == 0) {
                        log.info("Number of finansieringsformer this far (${edu}): ${count}")
                    }
                }
            }
        }
    }

    @Transactional
    void updateLadok3Organizations(Edu edu) {
        if(edu) {
            int count = 0
            Map query = [page            : 1,
                         limit           : 1,
                         onlyCount       : true]
            try {
                Map response = httpClientService.getLadok3MapFromJsonResponseByUrlAndType(edu, "/kataloginformation/organisation/filtrera", "application/vnd.ladok-kataloginformation+json", query)
                if (response && response.TotaltAntalPoster && response.TotaltAntalPoster > 0) {
                    int rowCount = response.TotaltAntalPoster as int
                    log.info("Processing (${rowCount}) updateLadok3Organizations for edu: ${edu}")
                    for (int i = 400; i < rowCount + 401; i += 400) {
                        query = [page: i / 400, limit: 400]
                        response = httpClientService.getLadok3MapFromJsonResponseByUrlAndType(edu, "/kataloginformation/organisation/filtrera", "application/vnd.ladok-kataloginformation+json", query)
                        response.Resultat.each { Map l3org ->
                            String organisationsKod = l3org.get('Organisationskod')?.trim() as String
                            if(organisationsKod) {
                                L3Organisation l3Organisation = L3Organisation.findOrCreateByEduAndOrganisationsKod(edu, organisationsKod)
                                Map benamning = l3org.get('Benamning', [sv: '', en: ''])
                                l3Organisation.benamningEn = benamning.en?.trim() as String
                                l3Organisation.benamningSv = benamning.sv?.trim() as String
                                l3Organisation.giltighetsPeriodSlutDatum = null
                                Map giltighetsperiod = l3org.get('Giltighetsperiod', [Slutdatum: ''])
                                String dateString = giltighetsperiod.Slutdatum?.trim() as String
                                if(dateString) {
                                    try {
                                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat('yyyy-MM-dd')
                                        l3Organisation.giltighetsPeriodSlutDatum = simpleDateFormat.parse(dateString)
                                    } catch (Throwable exception) {
                                        l3Organisation.giltighetsPeriodSlutDatum = null
                                    }
                                }
                                l3Organisation.organisationsEnhetsTyp = l3org.get('Organisationsenhetstyp', -1) as int
                                l3Organisation.uid = l3org.get('Uid', null)?.trim() as String
                                l3Organisation.save(failOnError: true)

                                count++
                                if((count % 100) == 0) {
                                    log.info("Number of organizations this far (${edu}): ${count}")
                                }
                            }
                        }
                    }
                }
            } catch(Throwable exception) {
                log.warn "Some problem calling /kataloginformation/organisation/filtrera: ${exception.getMessage()}"
            }
        }
    }

    @Transactional
    void updateLadok3Periods(Edu edu) {
        if(edu) {
            int count = 0
            try {
                Map response = httpClientService.getLadok3MapFromJsonResponseByUrlAndType(edu, "/kataloginformation/grunddata/period", "application/vnd.ladok-kataloginformation+json", [:])
                log.info("Processing (${response?.Period?response.Period.size():0}) updateLadok3Periods for edu: ${edu}")
                response.Period.each { Map l3per ->
                    String kod = l3per.get('Kod', null)?.trim() as String
                    if(kod) {
                        L3Period period = L3Period.findOrCreateByEduAndKod(edu, kod)
                        Map benamning = l3per.get('Benamning', [en: '', sv: ''])
                        period.benamningEn = benamning.get('en')?.trim() as String
                        period.benamningSv = benamning.get('sv')?.trim() as String
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat('yyyy-MM-dd')
                        String dateString = l3per.get('FromDatum', null)?.trim() as String
                        if(dateString) {
                            period.fromDatum = simpleDateFormat.parse(dateString)
                        } else {
                            period.fromDatum = null
                        }
                        period.ladokId = l3per.get('ID', -1) as int
                        period.larosateId = l3per.get('LarosateID', -1) as int
                        period.periodTypId = l3per.get('PeriodtypID', -1) as int
                        dateString = l3per.get('TomDatum', null)?.trim() as String
                        if(dateString) {
                            period.tomDatum = simpleDateFormat.parse(dateString)
                        } else {
                            period.tomDatum = null
                        }
                        period.save(failOnError: true)

                        count++
                        if((count % 100) == 0) {
                            log.info("Number of periods this far (${edu}): ${count}")
                        }
                    }
                }
            } catch(Throwable exception) {
                log.warn "Some problem calling /kataloginformation/grunddata/period: ${exception.getMessage()}"
            }
        }
    }

    @Transactional
    void updateLadok3StudieLokalisering(Edu edu) {
        if(edu) {
            int count = 0
            Map response = httpClientService.getLadok3MapFromJsonResponseByUrlAndType(edu, "/utbildningsinformation/grunddata/studielokalisering", "application/vnd.ladok-utbildningsinformation+json")
            log.info("Processing (${response?.Studielokalisering?response.Studielokalisering.size():0}) updateLadok3StudieLokalisering for edu: ${edu}")
            if (response && response.Studielokalisering) {
                response.Studielokalisering.each { Map studieLokalisering ->
                    if(!edu || (!studieLokalisering?.ID || studieLokalisering.ID.isEmpty())) {
                        throw new Exception("Missing <edu> or <studielokalisering.ID")
                    }
                    if(studieLokalisering) {
                        String kod = studieLokalisering.Kod?.trim() as String
                        if(kod) {
                            L3StudieLokalisering l3StudieLokalisering = L3StudieLokalisering.findOrCreateByEduAndKod(edu, kod)

                            l3StudieLokalisering.benamningEn = studieLokalisering.Benamning?.en?.trim() as String
                            l3StudieLokalisering.benamningSv = studieLokalisering.Benamning?.sv?.trim() as String
                            l3StudieLokalisering.beskrivningEn = studieLokalisering.Beskrivning?.en?.trim() as String
                            l3StudieLokalisering.beskrivningSv = studieLokalisering.Beskrivning?.sv?.trim() as String
                            l3StudieLokalisering.edu = edu
                            l3StudieLokalisering.kod = kod
                            l3StudieLokalisering.ladokId = studieLokalisering.ID as int

                            SimpleDateFormat simpleDateFormat = new SimpleDateFormat('yyyy-MM-dd')
                            String dateString = studieLokalisering.Giltighetsperiod?.Slutdatum?.trim() as String
                            if(dateString) {
                                try {
                                    l3StudieLokalisering.slutDatum = simpleDateFormat.parse(dateString)
                                } catch(Throwable exception) {
                                }
                            } else {
                                l3StudieLokalisering.slutDatum = null
                            }
                            dateString = studieLokalisering.Giltighetsperiod?.Startdatum?.trim() as String
                            if(dateString) {
                                try {
                                    l3StudieLokalisering.startDatum = simpleDateFormat.parse(dateString)
                                } catch(Throwable exception) {
                                }
                            } else {
                                l3StudieLokalisering.startDatum = null
                            }

                            l3StudieLokalisering.save(failOnError: true)
                            count++
                            if((count % 100) == 0) {
                                log.info("Number of studielokaliseringar this far (${edu}): ${count}")
                            }
                        }
                    }
                }
            }
        }
    }

    @Transactional
    void updateLadok3StudieTakt(Edu edu) {
        if(edu) {
            int count = 0
            Map response = httpClientService.getLadok3MapFromJsonResponseByUrlAndType(edu, "/kataloginformation/grunddata/studietakt", "application/vnd.ladok-kataloginformation+json")
            log.info("Processing (${response?.Studietakt?response.Studietakt.size():0}) updateLadok3StudieTakt for edu: ${edu}")
            if (response && response.Studietakt) {
                response.Studietakt.each { Map studietakt ->
                    if(!edu || (!studietakt?.ID || studietakt.ID.isEmpty())) {
                        throw new Exception("Missing <edu> or <studietakt.ID")
                    }
                    if(studietakt?.ID) {
                        L3StudieTakt l3Studietakt = L3StudieTakt.findOrCreateByEduAndLadokId(edu, studietakt.ID as int)
                        l3Studietakt.edu = edu
                        l3Studietakt.benamningEn = studietakt.Benamning?.en?.trim() as String
                        l3Studietakt.benamningSv = studietakt.Benamning?.sv?.trim() as String
                        l3Studietakt.beskrivningEn = studietakt.Beskrivning?.en?.trim() as String
                        l3Studietakt.beskrivningSv = studietakt.Beskrivning?.sv?.trim() as String
                        l3Studietakt.ladokId = studietakt.ID as int
                        l3Studietakt.larosateId = studietakt.LarosateID?: 0
                        l3Studietakt.kod = studietakt.Kod?.trim() as String
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat('yyyy-MM-dd')
                        String dateString = studietakt.Giltighetsperiod?.Slutdatum?.trim() as String
                        l3Studietakt.slutDatum = null
                        if(dateString) {
                            try {
                                l3Studietakt.slutDatum = simpleDateFormat.parse(dateString)
                            } catch(Throwable exception) {
                            }
                        }
                        dateString = studietakt.Giltighetsperiod?.Startdatum?.trim() as String
                        l3Studietakt.startDatum = null
                        if(dateString) {
                            try {
                                l3Studietakt.startDatum = simpleDateFormat.parse(dateString)
                            } catch(Throwable exception) {
                            }
                        }
                        l3Studietakt.takt = studietakt.Takt?:0

                        l3Studietakt.save(failOnError: true)
                        count++
                        if((count % 100) == 0) {
                            log.info("Number of studietakter this far (${edu}): ${count}")
                        }
                    }
                }
            }
        }
    }

    @Transactional
    void updateLadok3UndervisningsForm(Edu edu) {
        if(edu) {
            int count = 0
            Map response = httpClientService.getLadok3MapFromJsonResponseByUrlAndType(edu, "/utbildningsinformation/grunddata/undervisningsform", "application/vnd.ladok-utbildningsinformation+json")
            log.info("Processing (${response?.Undervisningsform?response.Undervisningsform.size():0}) updateLadok3Undervisningsform for edu: ${edu}")
            if (response && response.Undervisningsform) {
                response.Undervisningsform.each { Map undervisningsform ->
                    if(!edu || (!undervisningsform?.ID || undervisningsform.ID.isEmpty())) {
                        throw new Exception("Missing <edu> or <undervisningsform.ID")
                    }
                    if(undervisningsform) {
                        int ladokId = undervisningsform.ID as int
                        L3UndervisningsForm l3Undervisningsform = L3UndervisningsForm.findOrCreateByEduAndLadokId(edu, ladokId)
                        l3Undervisningsform.benamningEn = undervisningsform.Benamning?.en?.trim() as String
                        l3Undervisningsform.benamningSv = undervisningsform.Benamning?.sv?.trim() as String
                        l3Undervisningsform.beskrivningEn = undervisningsform.Beskrivning?.en?.trim() as String
                        l3Undervisningsform.beskrivningSv = undervisningsform.Beskrivning?.sv?.trim() as String
                        l3Undervisningsform.edu = edu
                        l3Undervisningsform.kod = undervisningsform.Kod?.trim() as String
                        l3Undervisningsform.ladokId = ladokId
                        String dateString = undervisningsform.Giltighetsperiod?.Slutdatum?.trim() as String
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat('yyyy-MM-dd')
                        l3Undervisningsform.slutDatum = null
                        if(dateString) {
                            l3Undervisningsform.slutDatum = simpleDateFormat.parse(dateString)
                        }
                        dateString = undervisningsform.Giltighetsperiod?.Startdatum?.trim() as String
                        l3Undervisningsform.startDatum = null
                        if(dateString) {
                            l3Undervisningsform.startDatum = simpleDateFormat.parse(dateString)
                        }

                        l3Undervisningsform.save(failOnError: true)
                        count++
                        if((count % 100) == 0) {
                            log.info("Number of undervisningsformer this far (${edu}): ${count}")
                        }
                    }
                }
            }
        }
    }

    @Transactional
    void updateLadok3UndervisningsTid(Edu edu) {
        if(edu) {
            int count = 0
            Map response = httpClientService.getLadok3MapFromJsonResponseByUrlAndType(edu, "/utbildningsinformation/grunddata/undervisningstid", "application/vnd.ladok-utbildningsinformation+json")
            log.info("Processing (${response?.Undervisningstid?response.Undervisningstid.size():0}) updateLadok3Undervisningstid for edu: ${edu}")
            if (response && response.Undervisningstid) {
                response.Undervisningstid.each { Map undervisningstid ->
                    if(!edu || (!undervisningstid?.ID || undervisningstid.ID.isEmpty())) {
                        throw new Exception("Missing <edu> or <undervisningstid.ID")
                    }
                    if(undervisningstid?.ID) {
                        int ladokId = undervisningstid.ID as int
                        L3UndervisningsTid l3Undervisningstid = L3UndervisningsTid.findOrCreateByEduAndLadokId(edu, ladokId)

                        l3Undervisningstid.benamningEn = undervisningstid.Benamning?.en?.trim() as String
                        l3Undervisningstid.benamningSv = undervisningstid.Benamning?.sv?.trim() as String
                        l3Undervisningstid.beskrivningEn = undervisningstid.Beskrivning?.en?.trim() as String
                        l3Undervisningstid.beskrivningSv = undervisningstid.Beskrivning?.sv?.trim() as String
                        l3Undervisningstid.edu = edu
                        l3Undervisningstid.kod = undervisningstid.Kod.trim() as String
                        l3Undervisningstid.ladokId  = ladokId

                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat('yyyy-MM-dd')
                        String dateString = undervisningstid.Giltighetsperiod?.Slutdatum?.trim() as String
                        l3Undervisningstid.slutDatum = null
                        if(dateString) {
                            l3Undervisningstid.slutDatum = simpleDateFormat.parse(dateString)
                        }
                        dateString = undervisningstid.Giltighetsperiod?.Startdatum?.trim() as String
                        l3Undervisningstid.startDatum = null
                        if(dateString) {
                            l3Undervisningstid.startDatum = simpleDateFormat.parse(dateString)
                        }

                        l3Undervisningstid.save(failOnError: true)
                        count++
                        if((count % 100) == 0) {
                            log.info("Number of undervisningstider this far (${edu}): ${count}")
                        }
                    }
                }
            }
        }
    }
}