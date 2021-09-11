package che.vlvl.springsqlweb.repository.genre

import che.vlvl.springsqlweb.model.Genre
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest
import org.springframework.context.annotation.Import
import org.springframework.jdbc.core.JdbcTemplate

@JdbcTest
@Import(GenreRepositoryJdbcImpl::class)
@DisplayName("GenreRepositoryJdbcImpl should ")
internal class GenreRepositoryJdbcImplTest {

    @Autowired
    lateinit var template: JdbcTemplate

    @Autowired
    lateinit var repository: GenreRepositoryJdbcImpl

    @Test
    @DisplayName("correct save genre")
    fun save() {

        val genreForSave = Genre(genre = "test genre")

        val countBeforeSave = getCountWithTemplate()

        val savedGenre = repository.save(genreForSave)

        val countAfterSave = getCountWithTemplate()
        assert(countAfterSave - countBeforeSave == 1)

        val foundGenre =
            getGenreById(savedGenre.id) ?: throw RuntimeException("genre with id = ${savedGenre.id} not found")
        assert(foundGenre == savedGenre)
    }

    @Test
    @DisplayName("correct find genre")
    fun find() {


        val savedGenre = repository.save(Genre(genre = "test genre"))

        val foundGenreWithTemplate =
            getGenreById(savedGenre.id) ?: throw RuntimeException("genre with id = ${savedGenre.id} not found")
        val foundGenreWithRepo = repository.find(savedGenre.id)

        assert(foundGenreWithRepo == foundGenreWithTemplate)
    }

    @Test
    @DisplayName("correct update genre")
    fun update() {

        val updatedRows = 1
        val savedGenre = repository.save(Genre(genre = "test genre"))
        val genreForUpdate = savedGenre.copy(genre = "updated genre")

        val updatedCount = repository.update(genreForUpdate)
        assert(updatedCount == updatedRows)

        val updatedGenre =
            getGenreById(genreForUpdate.id) ?: throw RuntimeException("genre with id = ${genreForUpdate.id} not found")
        assert(genreForUpdate == updatedGenre)
    }

    @Test
    @DisplayName("correct delete genre")
    fun delete() {
        val deletedRows = 1
        val savedGenre = repository.save(Genre(genre = "test genre"))

        val countBeforeDelete = getCountWithTemplate()

        val deletedCount = repository.delete(savedGenre.id)
        assert(deletedCount == deletedRows)

        val countAfterDelete = getCountWithTemplate()
        assert(countBeforeDelete - countAfterDelete == deletedRows)

        val genreAfterDelete = getGenreById(savedGenre.id)
        assert(genreAfterDelete == null)

    }

    @Test
    fun count() {
        assert(repository.count() == getCountWithTemplate().toLong())
    }


    private fun getGenreById(id: Long) = template.query("select * from genres where id = $id") { rs, _ ->
        if (rs.isAfterLast) {
            null
        } else {
            Genre(id = rs.getLong("id"), genre = rs.getString("genre"))
        }
    }.filterNotNull().firstOrNull()

    private fun getCountWithTemplate() = template.queryForObject("select count(*) from genres", Int::class.java)
        ?: throw RuntimeException("count is null")

}