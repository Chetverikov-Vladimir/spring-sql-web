package che.vlvl.springsqlweb.repository.genre

import che.vlvl.springsqlweb.model.Genre

interface GenreRepositoryJpa {
    fun save(genre: Genre): Genre
    fun findById(id: Long): Genre?
    fun delete(genre: Genre)
    fun count(): Long
}