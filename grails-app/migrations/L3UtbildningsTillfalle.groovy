databaseChangeLog = {
    changeSet(author: "mano3567", id: "202210281840") {
        createTable(tableName: "l3utbildning_tillfalle") {
            column(autoIncrement: "true", name: "id", type: "bigint") {
                constraints(nullable: "false", primaryKey: "true", primaryKeyName: "utbildningTillfallePK")
            }

            column(name: "version", type: "bigint") {
                constraints(nullable: "false")
            }

            column(name: "date_created", type: "datetime") {
                constraints(nullable: "true")
            }

            column(name: "last_updated", type: "datetime") {
                constraints(nullable: "true")
            }

            column(name: "benamning_en", type: "VARCHAR(160)") {
                constraints(nullable: "true")
            }

            column(name: "benamning_sv", type: "VARCHAR(192)") {
                constraints(nullable: "false")
            }

            column(name: "edu", type: "VARCHAR(16)") {
                constraints(nullable: "false")
            }

            column(name: "finansierings_form_id", type: "INTEGER") {
                constraints(nullable: "false")
            }

            column(name: "installt", type: "BIT(1)") {
                constraints(nullable: "false")
            }

            column(name: "omfattnings_varde", type: "NUMERIC(6,2)") {
                constraints(nullable: "false")
            }

            column(name: "organisations_uid", type: "VARCHAR(64)") {
                constraints(nullable: "false")
            }

            column(name: "slut_datum", type: "DATE") {
                constraints(nullable: "false")
            }

            column(name: "specificerad_omfattning", type: "NUMERIC(6,2)") {
                constraints(nullable: "false")
            }

            column(name: "start_datum", type: "DATE") {
                constraints(nullable: "false")
            }

            column(name: "start_period_id", type: "INTEGER") {
                constraints(nullable: "false")
            }

            column(name: "status", type: "INTEGER") {
                constraints(nullable: "false")
            }

            column(name: "studie_lokalisering_id", type: "INTEGER") {
                constraints(nullable: "false")
            }

            column(name: "studie_takt_id", type: "INTEGER") {
                constraints(nullable: "false")
            }

            column(name: "tillfalles_kod", type: "VARCHAR(16)") {
                constraints(nullable: "false")
            }

            column(name: "uid", type: "VARCHAR(64)") {
                constraints(nullable: "false")
            }

            column(name: "utbildnings_instans_uid", type: "VARCHAR(64)") {
                constraints(nullable: "false")
            }

            column(name: "utbildnings_typ_id", type: "INTEGER") {
                constraints(nullable: "false")
            }
        }

        addUniqueConstraint(columnNames: "edu, start_period_id, tillfalles_kod", constraintName: "UKEduPerKodL3UtbTillf", tableName: "l3utbildning_tillfalle")
    }
}
