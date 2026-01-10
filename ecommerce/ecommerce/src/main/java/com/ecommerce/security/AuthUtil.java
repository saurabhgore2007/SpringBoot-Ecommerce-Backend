package com.ecommerce.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.GrantedAuthority;

import java.util.Set;
import java.util.stream.Collectors;

public class AuthUtil {

    public static Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    public static String getCurrentUserEmail() {
        Authentication auth = getAuthentication();
        return (auth != null) ? auth.getName() : null;
    }

    public static Set<String> getCurrentUserRoles() {
        Authentication auth = getAuthentication();
        if (auth == null) return Set.of();
        return auth.getAuthorities().stream().map(GrantedAuthority::getAuthority)
                   .collect(Collectors.toSet());
    }

    public static boolean hasRole(String role) {
        return getCurrentUserRoles().contains(role);
    }
}