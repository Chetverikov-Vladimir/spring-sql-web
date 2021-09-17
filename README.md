# spring-sql-web
Проект написан без учета особенностей работы с Jpa в Kotlin https://habr.com/ru/company/haulmont/blog/572574/

Это связано с объединением JDBC подхода (самостоятельное изменение id сущности после инсерта в базу) и JPA подхода - управление id в persistence context провайдером JPA.
