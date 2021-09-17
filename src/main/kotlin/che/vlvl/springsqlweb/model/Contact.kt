package che.vlvl.springsqlweb.model

import javax.persistence.Embeddable

@Embeddable //Встраиваемый класс
class Contact(
    var name: String,
    var site: String
)
