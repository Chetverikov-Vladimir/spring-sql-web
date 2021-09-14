package che.vlvl.springsqlweb.repository.genre

import che.vlvl.springsqlweb.model.Genre
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest
import org.springframework.context.annotation.Import
import org.springframework.jdbc.core.JdbcTemplate

@JdbcTest
@Import(GenreRepositoryJdbcTemplateImpl::class)
@DisplayName("GenreRepositoryJdbcTemplateImpl should ")
internal class GenreRepositoryJdbcTemplateImplTest {
    @Autowired
    lateinit var template: JdbcTemplate

    @Autowired
    lateinit var repository: GenreRepositoryJdbcTemplateImpl


    @Test
    @DisplayName("correct save genre")
    fun save() {

        val genreForSave = Genre(genre = "test genre")

        val countBeforeSave = getCountWithTemplate()

        val savedGenre = repository.save(genreForSave)

        val countAfterSave = getCountWithTemplate()
        assertEquals(1, countAfterSave - countBeforeSave)

        val foundGenre =
            getGenreById(savedGenre.id) ?: throw RuntimeException("genre with id = ${savedGenre.id} not found")
        assertEquals(savedGenre, foundGenre)
    }

    @Test
    @DisplayName("correct find genre")
    fun find() {


        val savedGenre = repository.save(Genre(genre = "test genre"))

        val foundGenreWithTemplate =
            getGenreById(savedGenre.id) ?: throw RuntimeException("genre with id = ${savedGenre.id} not found")
        val foundGenreWithRepo = repository.find(savedGenre.id)

        assertEquals(foundGenreWithTemplate, foundGenreWithRepo)
    }

    @Test
    @DisplayName("correct update genre")
    fun update() {

        val savedGenre = repository.save(Genre(genre = "test genre"))
        val genreForUpdate = savedGenre.copy(genre = "updated genre")

        repository.update(genreForUpdate)

        val updatedGenre =
            getGenreById(genreForUpdate.id) ?: throw RuntimeException("genre with id = ${genreForUpdate.id} not found")
        assertEquals(genreForUpdate, updatedGenre)
    }

    @Test
    @DisplayName("correct delete genre")
    fun delete() {

        val savedGenre = repository.save(Genre(genre = "test genre"))

        val countBeforeDelete = getCountWithTemplate()

        repository.delete(savedGenre.id)

        val countAfterDelete = getCountWithTemplate()
        assertEquals(1, countBeforeDelete - countAfterDelete)

        val genreAfterDelete = getGenreById(savedGenre.id)
        assertNull(genreAfterDelete)

    }

    @Test
    @DisplayName("correct count genres")
    fun count() {
        assertEquals(getCountWithTemplate().toLong(), repository.count())
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