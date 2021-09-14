package che.vlvl.springsqlweb

import org.h2.tools.Console
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class SpringSqlWebApplication

fun main(args: Array<String>) {
    runApplication<SpringSqlWebApplication>(*args)
    Console.main()
}
