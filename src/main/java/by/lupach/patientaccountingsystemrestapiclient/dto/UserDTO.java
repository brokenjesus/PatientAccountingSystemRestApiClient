package by.lupach.patientaccountingsystemrestapiclient.dto;

import lombok.Builder;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

import static by.lupach.patientaccountingsystemrestapiclient.dto.UserDTO.Role.ROLE_ADMIN;

@Data
@Builder
public class UserDTO implements UserDetails {
    private Integer id;
    private String username;
    private String password;
    private String name;
    private String email;
    private String phone;
    private Role role = Role.ROLE_MEDIC;


    public boolean isAdmin() {
        return this.role == ROLE_ADMIN;
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(ROLE_ADMIN.name()));
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public enum Role {
        ROLE_MEDIC, ROLE_ADMIN
    }
}