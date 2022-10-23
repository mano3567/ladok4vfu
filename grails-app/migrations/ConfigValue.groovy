databaseChangeLog = {
    changeSet(author: "mano3567", id: "202210171600") {
        createTable(tableName: "config_value") {
            column(autoIncrement: "true", name: "id", type: "bigint") {
                constraints(nullable: "false", primaryKey: "true", primaryKeyName: "configValuePK")
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

            column(name: "name", type: "VARCHAR(128)") {
                constraints(nullable: "false", unique: 'true')
            }

            column(name: "boolean_value", type: "BIT(1)") {
                constraints(nullable: "false")
            }

            column(name: "double_value", type: "NUMERIC(12,6)") {
                constraints(nullable: "false")
            }

            column(name: "int_value", type: "INTEGER") {
                constraints(nullable: "false")
            }

            column(name: "string_value", type: "VARCHAR(250)") {
                constraints(nullable: "false")
            }
        }
    }
}
