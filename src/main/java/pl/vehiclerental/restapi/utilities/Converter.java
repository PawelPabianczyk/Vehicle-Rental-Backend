package pl.vehiclerental.restapi.utilities;

import pl.vehiclerental.restapi.models.ERole;
import pl.vehiclerental.restapi.models.Role;
import pl.vehiclerental.restapi.repository.RoleRepository;

import java.util.HashSet;
import java.util.Set;

public class Converter {

    public static Set<Role> stringsToRoles(RoleRepository roleRepository, Set<String> strRoles){
        Set<Role> roles = new HashSet<>();
        if (strRoles == null) {
            Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            roles.add(userRole);
        } else {
            strRoles.forEach(role -> {
                switch (role) {
                    case "admin":
                        Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(adminRole);

                        break;
                    case "manager":
                        Role modRole = roleRepository.findByName(ERole.ROLE_MANAGER)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(modRole);

                        break;

                    case "employee":
                        Role regularRole = roleRepository.findByName(ERole.ROLE_REGULAR)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(regularRole);

                        break;
                    default:
                        throw new RuntimeException("Error: Role is not found.");
                }
            });
        }
        return roles;
    }
}
