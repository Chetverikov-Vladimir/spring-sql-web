package che.vlvl.springsqlweb.model

import javax.persistence.*

@Entity
@Table(name = "author")
data class Author(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,

    @Column(name="name")
    val name:String,

    @OneToOne(
        targetEntity = Email::class,
        fetch = FetchType.EAGER,
        cascade = []
    )
    @JoinColumn(
        name = "email_id",
        foreignKey = ForeignKey(name = "FK_email")
    )
    val email: Email

)