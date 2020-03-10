package social.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.Set;

/**
 * Сейчас в user_info хранится ссылка на user_id,
 * Можно хранить ссылку на user_info в таблице users
 *
 * Simple object to represent a user
 */
@Entity
@Table(name = "users", schema = "public")
public class User
{
    @Id
    @Column(name = "id", updatable = false, nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_sequence_generator")
    @SequenceGenerator(name = "user_sequence_generator", sequenceName = "users_id_seq", allocationSize = 1, schema = "public")
    private Long id;

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    @Transient
    private String passwordConfirmed;

    @ManyToMany(cascade = CascadeType.PERSIST)
    @JoinTable(name = "user_to_role",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")   )
    private Set<Role> roles;

//    @OneToOne(optional = false)
//    private UserInfo userInfo;

    public Long getId()
    {
        return id;
    }

//    public UserInfo getUserInfo()
//    {
//        return userInfo;
//    }

//    public void setUserInfo(UserInfo userInfo)
//    {
//        this.userInfo = userInfo;
//    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public String getUsername()
    {
        return username;
    }

    public void setUsername(String username)
    {
        this.username = username;
    }

    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

    public String getPasswordConfirmed()
    {
        return passwordConfirmed;
    }

    public void setPasswordConfirmed(String passwordConfirmed)
    {
        this.passwordConfirmed = passwordConfirmed;
    }

    public Set<Role> getRoles()
    {
        return roles;
    }

    public void setRoles(Set<Role> roles)
    {
        this.roles = roles;
    }
}
