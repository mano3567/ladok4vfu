package se.su.it.ladok

class UpdateL3BasicsJob {
    LadokService ladokService

    static triggers = {
        // no defined trigger, we schedule it manually
    }

    String group = "GRAILS_JOBS"
    String description = "L3Basics Update Job"

    // not volatile, so persisted on restart.
    boolean volatility = true

    void execute(org.quartz.JobExecutionContext context) {
        long start = System.currentTimeMillis()
        int addressCount = 0
        log.info "Starting UpdateL3BasicsJob.execute(JobExecutionContext)"
        Edu edu = Edu.findByName(context.mergedJobDataMap.get('edu') as String)
        if(edu) {
            try {
                L3BevisBenamning.withTransaction {
                    ladokService.updateLadok3BevisBenamning(edu)
                }
            } catch(Throwable exception) {
                log.info "Some problem updating BevisBenamning : ${exception.getMessage()}"
            }
            try {
                L3FinansieringsForm.withTransaction {
                    ladokService.updateLadok3Finansieringsform(edu)
                }
            } catch(Throwable exception) {
                log.info "Some problem updating FinansieringsForm : ${exception.getMessage()}"
            }
            try {
                L3Organisation.withTransaction {
                    ladokService.updateLadok3Organizations(edu)
                }
            } catch(Throwable exception) {
                log.info "Some problem updating Organisation: ${exception.getMessage()}"
            }
            try {
                L3Period.withTransaction {
                    ladokService.updateLadok3Periods(edu)
                }
            } catch(Throwable exception) {
                log.info "Some problem updating Period: ${exception.getMessage()}"
            }
            try {
                L3StudieLokalisering.withTransaction {
                    ladokService.updateLadok3StudieLokalisering(edu)
                }
            } catch(Throwable exception) {
                log.info "Some problem updating StudieLokalisering: ${exception.getMessage()}"
            }
            try {
                L3StudieTakt.withTransaction {
                    ladokService.updateLadok3StudieTakt(edu)
                }
            } catch(Throwable exception) {
                log.info "Some problem updating StudieTakt: ${exception.getMessage()}"
            }
            try {
                L3UndervisningsForm.withTransaction {
                    ladokService.updateLadok3UndervisningsForm(edu)
                }
            } catch(Throwable exception) {
                log.info "Some problem updating UndervisningsForm: ${exception.getMessage()}"
            }
            try {
                L3UndervisningsTid.withTransaction {
                    ladokService.updateLadok3UndervisningsTid(edu)
                }
            } catch(Throwable exception) {
                log.info "Some problem updating UndervisningsTid: ${exception.getMessage()}"
            }
            try {
                L3UtbildningsTyp.withTransaction {
                    ladokService.updateLadok3UtbildningsTyp(edu)
                }
            } catch(Throwable exception) {
                log.info "Some problem updating UtbildningsTyp: ${exception.getMessage()}"
            }
        }

        log.info "UpdateL3BasicsJob.execute(JobExecutionContext) done in ${System.currentTimeMillis()-start} ms"
    }
}
