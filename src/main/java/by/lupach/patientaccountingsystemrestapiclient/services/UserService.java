package by.lupach.patientaccountingsystemrestapiclient.services;


import by.lupach.patientaccountingsystemrestapiclient.dto.UserDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class UserService implements UserDetailsService {
    @Autowired
    private RestTemplate restTemplate;
    private final String apiUrl = "http://localhost:8080/api/v1/users";

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    @Override
    public UserDTO loadUserByUsername(String username) throws UsernameNotFoundException {
        logger.info("Attempting to load user by username: {}", username);
        Optional<Object> result = Optional.ofNullable(restTemplate.getForObject(apiUrl + "/load-user?username=" + username, Object.class));;
        if (result.isEmpty()) {
            logger.warn("User with username '{}' not found", username);
            throw new UsernameNotFoundException("User not found with username: " + username);
        }
        logger.info("User found: {}", username);
        Map userMap = new LinkedHashMap((Map) result.get());

        Integer id = ((Integer) userMap.get("id"));
        String password = userMap.get("password").toString();
        String name = (userMap.get("name").toString());
        String email = (userMap.get("email").toString());
        String phone = (userMap.get("phone").toString());
        UserDTO.Role role = UserDTO.Role.ROLE_MEDIC;
        if (userMap.get("role").equals("ROLE_ADMIN")){
            role = UserDTO.Role.ROLE_ADMIN;
        }
        UserDTO user = UserDTO.builder()
                .id(id)
                .name(name)
                .username(username)
                .email(email)
                .phone(phone)
                .password(password)
                .role(role)
                .build();
        return user;
    }
}
