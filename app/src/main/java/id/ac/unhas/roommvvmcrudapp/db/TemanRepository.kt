package id.ac.unhas.roommvvmcrudapp.db

class TemanRepository(private val dao: TemanDAO) {

    val teman = dao.getAllTeman()

    suspend fun insert(teman: Teman): Long {
        return dao.insertTeman(teman)
    }

    suspend fun update(teman: Teman): Int {
        return dao.updateTeman(teman)
    }

    suspend fun delete(teman: Teman): Int {
        return dao.deleteTeman(teman)
    }

    suspend fun deleteAll(): Int {
        return dao.deleteAll()
    }
}