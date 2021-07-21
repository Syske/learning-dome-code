/* Copyright © 2021 syske. All rights reserved. */
package io.github.syske.demo.springbootsecuritydemo.service;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * @author syske
 * @version 1.0
 * @date 2021-07-20 7:58
 */
@Service
public class ReaderRepository implements UserDetailsService {

    private static Map<String, UserDetails> userDetailsMap = Maps.newHashMap();

    static {
        userDetailsMap.put("admin", new UserInfo("admin", encrypt("admin"), "ADMIN"));
        userDetailsMap.put("user", new UserInfo("user", encrypt("123456"), "USER"));
        userDetailsMap.put("test", new UserInfo("test", encrypt("test"), "TEST"));
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if (userDetailsMap.containsKey(username)) {
            return userDetailsMap.get(username);
        } else {
            throw new UsernameNotFoundException("用户不存在");
        }
    }

    private static String encrypt(String password) {
        return new BCryptPasswordEncoder().encode(password);
    }

    public static class UserInfo implements UserDetails {
        private String username;
        private String password;
        private String[] authorities;

        public UserInfo() {
        }

        public UserInfo(String username, String password) {
            this.username = username;
            this.password = password;
        }

        public UserInfo(String username, String password, String... authorities) {
            this.username = username;
            this.password = password;
            this.authorities = authorities;
        }



        @Override
        public Collection<? extends GrantedAuthority> getAuthorities() {
            List<GrantedAuthority> authorityList = Lists.newArrayList();
            for (String authority : authorities) {
                authorityList.add(new SimpleGrantedAuthority(authority));
            }
            return authorityList;
        }

        @Override
        public String getPassword() {
            return this.password;
        }

        @Override
        public String getUsername() {
            return this.username;
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
    }
}
