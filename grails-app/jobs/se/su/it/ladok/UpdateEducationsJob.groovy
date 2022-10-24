package se.su.it.ladok

class UpdateEducationsJob {
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
        log.info "Starting UpdateEducationsJob.execute(JobExecutionContext)"
        Edu edu = Edu.findByName(context.mergedJobDataMap.get('edu') as String)
        if(edu) {
            L3Program.UTBILDNINGTYPER.each { String utbildningsTypKod ->
                println("Updating ${edu} and ${utbildningsTypKod}")
                log.info "Updating ${edu} and ${utbildningsTypKod}"
                ladokService.updateL3EducationsByEduAndType(edu, utbildningsTypKod)
            }
            L3ProgramInriktning.UTBILDNINGTYPER.each { String utbildningsTypKod ->
                println("Updating ${edu} and ${utbildningsTypKod}")
                log.info "Updating ${edu} and ${utbildningsTypKod}"
                ladokService.updateL3EducationsByEduAndType(edu, utbildningsTypKod)
            }
            L3KursPaketering.UTBILDNINGTYPER.each { String utbildningsTypKod ->
                println("Updating ${edu} and ${utbildningsTypKod}")
                log.info "Updating ${edu} and ${utbildningsTypKod}"
                ladokService.updateL3EducationsByEduAndType(edu, utbildningsTypKod)
            }
            L3Kurs.UTBILDNINGTYPER.each { String utbildningsTypKod ->
                println("Updating ${edu} and ${utbildningsTypKod}")
                log.info "Updating ${edu} and ${utbildningsTypKod}"
                ladokService.updateL3EducationsByEduAndType(edu, utbildningsTypKod)
            }
        }

        log.info "UpdateEducationsJob.execute(JobExecutionContext) done in ${System.currentTimeMillis()-start} ms"
    }
}
