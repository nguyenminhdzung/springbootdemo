package demo.oauthserver.web.viewmodel;

import org.springframework.security.core.GrantedAuthority;

import java.util.*;

public abstract class TransferHelper {
    
    public static Map<String, Boolean> createPermissionMap(Collection<? extends GrantedAuthority> authorities) {
        Map<String, Boolean> permissionMap = new HashMap<String, Boolean>();
        for (GrantedAuthority authority : authorities) {
            permissionMap.put(authority.getAuthority(), Boolean.TRUE);
//            String role = authority.getAuthority();
//            role = role.toLowerCase();
//            role = role.replace("role_", "");
//            roles.put(role, Boolean.TRUE);
        }

        return permissionMap;
    }
}
