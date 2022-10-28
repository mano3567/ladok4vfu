databaseChangeLog = {
    changeSet(author: "mano3567", id: "202210231740") {
        createTable(tableName: "l3utbildning") {
            column(autoIncrement: "true", name: "id", type: "bigint") {
                constraints(nullable: "false", primaryKey: "true", primaryKeyName: "utbildningPK")
            }

            column(name: "version", type: "bigint") {
                constraints(nullable: "false")
            }

            column(name: "avvecklad", type: "BIT(1)") {
                constraints(nullable: "false")
            }

            column(name: "benamning", type: "VARCHAR(255)") {
                constraints(nullable: "false")
            }

            column(name: "benamning_en", type: "VARCHAR(255)") {
                constraints(nullable: "true")
            }

            column(name: "benamning_sv", type: "VARCHAR(255)") {
                constraints(nullable: "false")
            }

            column(name: "class", type: "VARCHAR(128)") {
                constraints(nullable: "false")
            }

            column(name: "date_created", type: "datetime") {
                constraints(nullable: "true")
            }

            column(name: "last_updated", type: "datetime") {
                constraints(nullable: "true")
            }

            column(name: "edu", type: "VARCHAR(16)") {
                constraints(nullable: "false")
            }

            column(name: "giltig_fran_period_id", type: "INTEGER") {
                constraints(nullable: "false")
            }

            column(name: "omfattnings_varde", type: "NUMERIC(6,2)") {
                constraints(nullable: "false")
            }

            column(name: "organisations_uid", type: "VARCHAR(64)") {
                constraints(nullable: "false")
            }

            column(name: "overliggande_utbildnings_uid", type: "VARCHAR(64)") {
                constraints(nullable: "true")
            }

            column(name: "process_status_id", type: "INTEGER") {
                constraints(nullable: "false")
            }

            column(name: "senaste_version", type: "BIT(1)") {
                constraints(nullable: "false")
            }

            column(name: "uid", type: "VARCHAR(64)") {
                constraints(nullable: "false")
            }

            column(name: "utbildnings_kod", type: "VARCHAR(32)") {
                constraints(nullable: "false")
            }

            column(name: "utbildnings_typ_id", type: "INTEGER") {
                constraints(nullable: "false")
            }

            column(name: "utbildnings_uid", type: "VARCHAR(64)") {
                constraints(nullable: "false")
            }

            column(name: "versions_nummer", type: "INTEGER") {
                constraints(nullable: "false")
            }
        }

        addUniqueConstraint(columnNames: "edu, utbildnings_uid", constraintName: "UKEduUtbUidL3Utb", tableName: "l3utbildning")
        addUniqueConstraint(columnNames: "edu, overliggande_utbildnings_uid, utbildnings_kod, versions_nummer", constraintName: "UKEduQuartetL3Utb", tableName: "l3utbildning")
    }
}
