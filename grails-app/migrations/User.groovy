databaseChangeLog = {
    changeSet(author: "mano3567", id: "202210161920") {
        createTable(tableName: "user") {
            column(autoIncrement: "true", name: "id", type: "bigint") {
                constraints(nullable: "false", primaryKey: "true", primaryKeyName: "userPK")
            }

            column(name: "version", type: "bigint") {
                constraints(nullable: "false")
            }

            column(name: "date_created", type: "datetime") {
                constraints(nullable: "true")
            }

            column(name: "display_name", type: "VARCHAR(128)") {
                constraints(nullable: "false")
            }

            column(name: "eppn", type: "VARCHAR(96)") {
                constraints(nullable: "false", unique: 'true')
            }

            column(name: "given_name", type: "VARCHAR(64)") {
                constraints(nullable: "false")
            }

            column(name: "last_updated", type: "datetime") {
                constraints(nullable: "true")
            }

            column(name: "sn", type: "VARCHAR(64)") {
                constraints(nullable: "false")
            }
        }
    }
}
