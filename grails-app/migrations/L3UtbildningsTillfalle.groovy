databaseChangeLog = {
    changeSet(author: "mano3567", id: "202210281840") {
        createTable(tableName: "l3utbildnings_tillfalle") {
            column(autoIncrement: "true", name: "id", type: "bigint") {
                constraints(nullable: "false", primaryKey: "true", primaryKeyName: "utbildningsTillfallePK")
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

            column(name: "class", type: "VARCHAR(128)") {
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

            column(name: "undervisnings_tid_id", type: "INTEGER") {
                constraints(nullable: "false")
            }

            column(name: "utbildnings_instans_uid", type: "VARCHAR(64)") {
                constraints(nullable: "false")
            }

            column(name: "utbildnings_typ_id", type: "INTEGER") {
                constraints(nullable: "false")
            }
        }

        addUniqueConstraint(columnNames: "edu, start_period_id, tillfalles_kod", constraintName: "UKEduPerKodL3UtbTillf", tableName: "l3utbildnings_tillfalle")
    }

    changeSet(author: "mano3567", id: "202210301830") {
        createTable(tableName: "l3utbildnings_tillfalle_period") {
            column(autoIncrement: "true", name: "id", type: "bigint") {
                constraints(nullable: "false", primaryKey: "true", primaryKeyName: "utbildningsTillfallePeriodPK")
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

            column(name: "forsta_undervisnings_datum", type: "DATE") {
                constraints(nullable: "false")
            }

            column(name: "l3Utbildnings_tillfalle_id", type: "bigint") {
                constraints(nullable: "false")
            }

            column(name: "omfattning", type: "NUMERIC(6,2)") {
                constraints(nullable: "false")
            }

            column(name: "sista_undervisnings_datum", type: "DATE") {
                constraints(nullable: "false")
            }
        }
        addUniqueConstraint(columnNames: "l3Utbildnings_tillfalle_id, forsta_undervisnings_datum", constraintName: "UKEduPerKodL3UtbTillf123", tableName: "l3utbildnings_tillfalle_period")
        addForeignKeyConstraint(baseColumnNames: "l3Utbildnings_tillfalle_id", baseTableName: "l3utbildnings_tillfalle_period", constraintName: "utp2utFK", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "l3Utbildnings_tillfalle")
    }
}
