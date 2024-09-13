package com.example.theswitcher_nunosilva.model

class DivisionRepository(private val divisionDAO: DivisionsDAO) {

    suspend fun addDivision(division: Division) {
        divisionDAO.addDivision(division)
    }

    suspend fun deleteDivision(id: Int) {
        divisionDAO.deleteDivision(id)
    }

    fun readAllData(): List<Division> {
        return divisionDAO.readAllData()
    }

}