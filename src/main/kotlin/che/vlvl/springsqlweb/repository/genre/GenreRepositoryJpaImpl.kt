package che.vlvl.springsqlweb.repository.genre

import che.vlvl.springsqlweb.model.Genre
import org.springframework.stereotype.Repository
import javax.persistence.EntityManager
import javax.persistence.PersistenceContext

@Repository
class GenreRepositoryJpaImpl(
    @PersistenceContext
    val em: EntityManager
) : GenreRepositoryJpa {


    override fun save(genre: Genre): Genre {
        if (genre.id == 0L) em.persist(genre) else em.merge(genre)
        return genre
    }

    override fun findById(id: Long): Genre? = em.find(Genre::class.java, id)


    override fun delete(genre: Genre) {
        em.remove(genre)
    }

    override fun count(): Long {
        // С просто Long::class.java не работает,
        // ошибка java.lang.IllegalArgumentException: Type specified for TypedQuery [long] is incompatible with query return type [class java.lang.Long]
        return em.createQuery("select count(g) from Genre g", java.lang.Long::class.java).singleResult.toLong()
    }
}