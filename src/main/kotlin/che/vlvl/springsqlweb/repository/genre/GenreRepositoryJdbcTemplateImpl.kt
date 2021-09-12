package che.vlvl.springsqlweb.repository.genre

import che.vlvl.springsqlweb.model.Genre
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations
import org.springframework.jdbc.support.GeneratedKeyHolder
import org.springframework.stereotype.Repository

@Repository
class GenreRepositoryJdbcTemplateImpl(
    private val template: NamedParameterJdbcOperations
) : GenreRepository {

    private val saveRequest = "insert into genres(id, genre) values (null, :genre)"
    private val findRequest = "select * from genres where id = :id"
    private val updateRequest = "update genres set genre = :genre where id = :id"
    private val deleteQuery = "delete from genres where id = :id"
    private val countQuery = "select count(*) from genres"

    override fun save(genre: Genre): Genre {
        val keyHolder = GeneratedKeyHolder()
        val parameterSource = MapSqlParameterSource(
            mapOf("genre" to genre.genre)
        )
        template.update(saveRequest, parameterSource, keyHolder)
        return genre.copy(id = keyHolder.key?.toLong() ?: throw RuntimeException("genre $genre was not saved"))
    }

    override fun find(id: Long): Genre? {
        val parameters = mapOf("id" to id)
        return template.query(findRequest, parameters) { rs, _ ->
            if (rs.isAfterLast) {
                null
            } else {
                Genre(id = rs.getLong("id"), genre = rs.getString("genre"))
            }
        }.filterNotNull().firstOrNull()
    }

    override fun update(genre: Genre): Int {
        val parameters = mapOf("id" to genre.id, "genre" to genre.genre)
        return template.update(updateRequest, parameters)
    }

    override fun delete(id: Long): Int {
        val parameters = mapOf("id" to id)
        return template.update(deleteQuery, parameters)
    }

    override fun count(): Long {
        return template.queryForObject(countQuery, emptyMap<String, String>(), Long::class.java)
            ?: throw RuntimeException("count is null")
    }
}