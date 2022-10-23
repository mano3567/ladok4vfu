package se.su.it.ladok

import groovy.transform.CompileStatic

@CompileStatic
enum Edu {
    DOCH('uniarts.se', 'Stockholms konstnärliga högskola', 'uniarts.se', "student.uniarts.se", "SKH"),
    GIH('gih.se', 'Gymnastik- och idrottshögskolan', 'gih.se', "gih.se", "GIH"),
    HH('hh.se', 'Högskolan i Halmstad', 'hh.se', "hh.se", "HH"),
    HKR('hkr.se', 'Högskolan Kristianstad', 'hkr.se', "hkr.se", "HKR"),
    KF('konstfack.se', 'Konstfack', 'konstfack.se', "student.konstfack.se", "KF"),
    KMH('kmh.se', 'Kungliga Musikhögskolan', 'kmh.se', "kmh.se", "KMH"),
    KTH('kth.se', 'Kungliga Tekniska högskolan', 'kth.se', "kth.se", "KTH"),
    LU('lu.se', 'Lunds universitet', 'lu.se', "lu.se", "LU"),
    MAU('mau.se', 'Malmö universitet', 'mah.se', "mah.se", "MAU"),
    SH('suni.se', 'Södertörns högskola', 'suni.se', "suni.se", "SH"),
    SU('su.se', 'Stockholms universitet', 'su.se', "su.se", "SU")

    private final String itsAbbreviation
    private final String itsFullName
    private final String itsName
    private final String itsScope
    private final String itsStudentScope

    Edu(String theName, String theFullName, String theScope, String theStudentScope, String theAbbreviation) {
        itsAbbreviation = theAbbreviation?.trim()
        itsFullName = theFullName?.trim()
        itsName = theName?.trim()
        itsScope = theScope?.trim()
        itsStudentScope = theStudentScope?.trim()
    }

    static Edu findByName(String someName) {
        return Edu.values().find { it.name == someName }
    }

    static Edu findByScope(String someScope) {
        return Edu.values().find { it.scope == someScope }
    }

    String getAbbreviation() {
        return abbreviation
    }

    String getFullName() {
        return itsFullName
    }

    String getName() {
        return itsName
    }

    String getScope() {
        return itsScope
    }

    String getStudentScope() {
        return itsStudentScope
    }
}