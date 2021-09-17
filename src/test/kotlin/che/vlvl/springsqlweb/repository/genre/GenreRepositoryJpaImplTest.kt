package che.vlvl.springsqlweb.repository.genre

import che.vlvl.springsqlweb.model.Genre
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager
import org.springframework.context.annotation.Import

@DataJpaTest
@Import(GenreRepositoryJpaImpl::class)
internal class GenreRepositoryJpaImplTest {
    @Autowired
    lateinit var em: TestEntityManager

    @Autowired
    lateinit var repo: GenreRepositoryJpaImpl

    @Test
    fun save() {
        val genre = Genre(genre = "test")
        repo.save(genre)

        em.clear()
        val foundGenre = em.find(Genre::class.java, genre.id)
        assertEquals(foundGenre, genre)
    }

    @Test
    fun findById() {
        val genre = Genre(genre = "test")
        em.persistAndFlush(genre)

        val foundGenre = repo.findById(genre.id)
        assertEquals(foundGenre, genre)
    }

    @Test
    fun delete() {
        val genre = Genre(genre = "test")
        repo.save(genre)
        em.flush()

        repo.delete(genre)
        val foundGenre = repo.findById(genre.id)
        assertNull(foundGenre)
    }

    @Test
    fun count() {
        val genre = Genre(genre = "test")
        repo.save(genre)
        em.flush()

        val countFromEm =
            em.entityManager.createQuery("select count(g) from Genre g", java.lang.Long::class.java).singleResult
        assertEquals(countFromEm, repo.count())
    }
}