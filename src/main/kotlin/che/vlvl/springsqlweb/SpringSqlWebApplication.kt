package che.vlvl.springsqlweb

import che.vlvl.springsqlweb.model.Genre
import che.vlvl.springsqlweb.repository.genre.GenreRepository
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class SpringSqlWebApplication

fun main(args: Array<String>) {
    val context = runApplication<SpringSqlWebApplication>(*args)
    //Console.main()

    val repo = context.getBean(GenreRepository::class.java)
    val savedGenre = repo.save(Genre(genre = "fantastic"))
    println("saved genre: $savedGenre")

    println("found genre: ${repo.find(savedGenre.id)}")

    println("Update genre with id = ${savedGenre.id}: updated ${repo.update(savedGenre.copy(genre = "updated value"))} rows")

    println("found genre after update: ${repo.find(savedGenre.id)}")

    println("Count genre ${repo.count()}")

    println("delete from genre with id = ${savedGenre.id} deleted ${repo.delete(savedGenre.id)} rows")

    println("found genre after delete: ${repo.find(savedGenre.id)}")

    println("Count genre ${repo.count()}")

}
