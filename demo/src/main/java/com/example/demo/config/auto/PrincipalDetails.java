package com.example.demo.config.auto;

import com.example.demo.dto.UserDTO;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;



//UserDetails 내장 인터페이스
public class PrincipalDetails implements UserDetails {

    private static final long serialVersionUID = -951226953749557253L;
	private UserDTO user;

    public PrincipalDetails(UserDTO user) {
        this.user = user;
    }

    public UserDTO getUser() {
        return user;
    }

    @Override
    public String getPassword() {
        return user.getPASSWORD();
    }

    @Override
    public String getUsername() {
        return user.getUSERNAME();
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

    //유저 권한을 부여하는 메소드
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
        System.out.println(user.getRoleList());
        user.getRoleList().forEach(r -> {
            authorities.add(() -> {
                return r;
            });
        });
        return authorities;
    }
}
