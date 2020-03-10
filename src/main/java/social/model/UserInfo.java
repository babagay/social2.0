package social.model;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.validator.constraints.UniqueElements;
import org.springframework.beans.factory.annotation.Autowired;
import social.dao.UserDao;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;


@Entity
@Table(name = "user_info", schema = "public", uniqueConstraints = {
        @UniqueConstraint(columnNames = "user_id")
})
public class UserInfo
{
    @Id
    @Column(name = "id", updatable = false, nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_details_sequence_generator")
    @SequenceGenerator(name = "user_details_sequence_generator", sequenceName = "user_details_id_seq", allocationSize = 1, schema = "public")
    private Long id;

    @Column(name = "first_name")
    @NotNull
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    // FetchType.LAZY можно использовать ТОЛЬКО, когда optional = false
    // При сохранении userInfo автоматически сохранится и User
    @OneToOne(fetch = FetchType.LAZY, optional = false, cascade = CascadeType.PERSIST, orphanRemoval = true)
    @JoinColumn(unique = true, name = "user_id")
    @NotNull
    private User user;

    // TODO
    // [!] В конструкторе передан user, т.к. UserDetails всегда должны ссылаться на конкретного юзера
    // нужна ли такая манипуляция?
    // [!] Если делать так, на момент конструирования UserDetails объект User должен иметь id
    // Т.о., нужно вкинуть User в базу, вызвать persist(), проверить, что у него есть id и потом создавать UserDetails
    public UserInfo(User user)
    {
        this.user = user;
    }

    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public String getFirstName()
    {
        return firstName;
    }

    public void setFirstName(String firstName)
    {
        this.firstName = firstName;
    }

    public String getLastName()
    {
        return lastName;
    }

    public void setLastName(String lastName)
    {
        this.lastName = lastName;
    }

    public User getUser()
    {
        return user;
    }

    public void setUser(User user)
    {
        this.user = user;
    }
}
