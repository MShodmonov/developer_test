package musobek.shodmonov.developer_test.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import musobek.shodmonov.developer_test.entity.abstractEntityLayer.AbstractEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@Entity(name = "users")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User extends AbstractEntity implements UserDetails {

    @Column(unique = true)
    private String username;

    private String email;
    private String fullName;
    private String password;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private List<Role> roles;

    @OneToOne(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    private Attachment attachment;




    private boolean isAccountNonExpired = true;
    private boolean isAccountNonLocked = true;
    private boolean isCredentialNonExpired = true;
    private boolean isEnabled = false;
    private UUID activationCode;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.roles;
    }

    @Override
    public boolean isAccountNonExpired() {
        return isAccountNonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return isAccountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return isAccountNonExpired;
    }

    @Override
    public boolean isEnabled() {
        return isEnabled;
    }

    public User(String username,String email, String fullName, String password,List<Role> roles,UUID activationCode) {
        this.username = username;
        this.email = email;
        this.fullName = fullName;
        this.password = password;
        this.roles = roles;
        this.activationCode = activationCode;
    }

    public User(String username,String email, String fullName, String password,List<Role> roles,boolean isEnabled) {
        this.username = username;
        this.email = email;
        this.fullName = fullName;
        this.password = password;
        this.roles = roles;
        this.isEnabled = isEnabled;
    }
}
