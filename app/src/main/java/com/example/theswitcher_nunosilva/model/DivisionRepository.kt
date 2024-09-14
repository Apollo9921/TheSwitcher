package com.example.theswitcher_nunosilva.model

class DivisionRepository(private val divisionDAO: DivisionsDAO) {

    suspend fun addDivision(division: Division) {
        divisionDAO.addDivision(division)
    }

    suspend fun deleteDivision(division: Division) {
        divisionDAO.deleteDivision(division)
    }

    fun readAllData(): List<Division> {
        return divisionDAO.readAllData()
    }

    suspend fun updateDivisionMode(division: Division) {
        divisionDAO.updateDivisionMode(division)
    }

}