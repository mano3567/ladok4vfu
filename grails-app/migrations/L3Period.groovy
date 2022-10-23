databaseChangeLog = {
    changeSet(author: "mano3567", id: "202210181935") {
        createTable(tableName: "l3period") {
            column(autoIncrement: "true", name: "id", type: "bigint") {
                constraints(nullable: "false", primaryKey: "true", primaryKeyName: "periodPK")
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

            column(name: "benamning_en", type: "VARCHAR(64)") {
                constraints(nullable: "false")
            }

            column(name: "benamning_sv", type: "VARCHAR(64)") {
                constraints(nullable: "false")
            }

            column(name: "edu", type: "VARCHAR(16)") {
                constraints(nullable: "false")
            }

            column(name: "from_datum", type: "DATE") {
                constraints(nullable: "true")
            }

            column(name: "kod", type: "VARCHAR(16)") {
                constraints(nullable: "false")
            }

            column(name: "ladok_id", type: "INTEGER") {
                constraints(nullable: "false", unique: 'true')
            }

            column(name: "larosate_id", type: "INTEGER") {
                constraints(nullable: "false")
            }

            column(name: "period_typ_id", type: "INTEGER") {
                constraints(nullable: "false")
            }

            column(name: "tom_datum", type: "DATE") {
                constraints(nullable: "true")
            }
        }

        addUniqueConstraint(columnNames: "edu, kod", constraintName: "UKEduKodL3Peri", tableName: "l3period")
    }
}
