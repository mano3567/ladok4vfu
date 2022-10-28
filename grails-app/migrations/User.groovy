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

            column(name: "email_address", type: "VARCHAR(128)") {
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

            column(name: "nor_edu_person_nin", type: "VARCHAR(16)") {
                constraints(nullable: "true")
            }

            column(name: "sn", type: "VARCHAR(64)") {
                constraints(nullable: "false")
            }

            column(name: "social_security_number", type: "VARCHAR(16)") {
                constraints(nullable: "true")
            }
        }
    }

    changeSet(author: "mano3567", id: "202210261835") {
        createTable(tableName: "affiliation") {
            column(autoIncrement: "true", name: "id", type: "bigint") {
                constraints(nullable: "false", primaryKey: "true", primaryKeyName: "affiliationPK")
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

            column(name: "affiliation", type: "VARCHAR(64)") {
                constraints(nullable: "false", unique: 'true')
            }
        }
    }

    changeSet(author: "mano3567", id: "202210261840") {
        createTable(tableName: "entitlement") {
            column(autoIncrement: "true", name: "id", type: "bigint") {
                constraints(nullable: "false", primaryKey: "true", primaryKeyName: "entitlementPK")
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

            column(name: "entitlement", type: "VARCHAR(128)") {
                constraints(nullable: "false", unique: 'true')
            }
        }
    }

    changeSet(author: "mano3567", id: "202210261845") {
        createTable(tableName: "user_affiliation") {
            column(autoIncrement: "true", name: "id", type: "bigint") {
                constraints(nullable: "false", primaryKey: "true", primaryKeyName: "userAffiliationPK")
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

            column(name: "affiliation_id", type: "bigint") {
                constraints(nullable: "false")
            }

            column(name: "user_id", type: "bigint") {
                constraints(nullable: "false")
            }
        }

        addUniqueConstraint(columnNames: "affiliation_id, user_id", constraintName: "UKUsAfUA", tableName: "user_affiliation")
        addForeignKeyConstraint(baseColumnNames: "affiliation_id", baseTableName: "user_affiliation", constraintName: "usafs2afFK", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "affiliation")
        addForeignKeyConstraint(baseColumnNames: "user_id", baseTableName: "user_affiliation", constraintName: "usafs2usFK", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "user")
    }

    changeSet(author: "mano3567", id: "202210261850") {
        createTable(tableName: "user_entitlement") {
            column(autoIncrement: "true", name: "id", type: "bigint") {
                constraints(nullable: "false", primaryKey: "true", primaryKeyName: "userEntitlementPK")
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

            column(name: "entitlement_id", type: "bigint") {
                constraints(nullable: "false")
            }

            column(name: "user_id", type: "bigint") {
                constraints(nullable: "false")
            }
        }

        addUniqueConstraint(columnNames: "entitlement_id, user_id", constraintName: "UKUsEnUE", tableName: "user_entitlement")
        addForeignKeyConstraint(baseColumnNames: "entitlement_id", baseTableName: "user_entitlement", constraintName: "usens2enFK", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "entitlement")
        addForeignKeyConstraint(baseColumnNames: "user_id", baseTableName: "user_entitlement", constraintName: "usen2usFK", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "user")
    }
}
