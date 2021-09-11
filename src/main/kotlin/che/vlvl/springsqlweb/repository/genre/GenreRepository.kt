package che.vlvl.springsqlweb.repository.genre

import che.vlvl.springsqlweb.model.Genre

interface GenreRepository {
    fun save(genre:Genre):Genre
    fun find(id:Long):Genre?
    fun update(genre:Genre):Int
    fun delete(id:Long):Int
    fun count():Long
}