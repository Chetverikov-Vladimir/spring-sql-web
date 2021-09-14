package che.vlvl.springsqlweb.repository.genre

import che.vlvl.springsqlweb.model.Genre

interface GenreRepositoryJdbc {
    fun save(genre: Genre): Genre
    fun find(id: Long): Genre?
    fun update(genre: Genre)
    fun delete(id: Long)
    fun count(): Long
}