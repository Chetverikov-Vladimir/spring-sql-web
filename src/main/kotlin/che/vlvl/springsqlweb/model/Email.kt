package che.vlvl.springsqlweb.model

import javax.persistence.*

@Entity
@Table(name = "email")
data class Email (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id:Long,

    @Column(name = "email")
    val email:String
)


