package che.vlvl.springsqlweb.repository.genre

import che.vlvl.springsqlweb.model.Genre
import org.springframework.stereotype.Repository
import java.sql.Statement
import javax.sql.DataSource

@Repository
class GenreRepositoryJdbcImpl(private val ds: DataSource) : GenreRepositoryJdbc {

    private val saveRequest = "insert into genres(id, genre) values (null, ?)"
    private val findRequest = "select * from genres where id = ?"
    private val updateRequest = "update genres set genre = ? where id = ?"
    private val deleteQuery = "delete from genres where id = ?"
    private val countQuery = "select count(*) from genres"

    override fun save(genre: Genre): Genre {
        ds.connection.prepareStatement(saveRequest, Statement.RETURN_GENERATED_KEYS).use {
            it.setString(1, genre.genre)

            if (it.executeUpdate() > 0) {
                val rs = it.generatedKeys
                rs.next()
                return genre.copy(id = rs.getLong("id"))
            } else {
                throw RuntimeException("Не удалось вставить запись $genre")
            }
        }
    }

    override fun find(id: Long): Genre? {
        ds.connection.prepareStatement(findRequest).use {
            it.setLong(1, id)
            it.execute()

            val rs = it.resultSet
            return if (rs.next()) {
                Genre(
                    id = rs.getLong("id"),
                    genre = rs.getString("genre")
                )
            } else null
        }

    }

    override fun update(genre: Genre) {
        ds.connection.prepareStatement(updateRequest).use {
            it.setLong(2, genre.id)
            it.setString(1, genre.genre)
            it.executeUpdate()
        }

    }

    override fun delete(id: Long) {
        ds.connection.prepareStatement(deleteQuery).use {
            it.setLong(1, id)
            it.executeUpdate()
        }
    }

    override fun count(): Long {
        ds.connection.prepareStatement(countQuery).use {
            it.execute()
            it.resultSet.next()
            return it.resultSet.getLong(1)
        }
    }
}