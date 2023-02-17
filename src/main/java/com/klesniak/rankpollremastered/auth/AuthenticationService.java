package com.klesniak.rankpollremastered.auth;

import com.klesniak.rankpollremastered.user.constants.RoleConstants;
import com.klesniak.rankpollremastered.user.entity.Role;
import com.klesniak.rankpollremastered.user.entity.User;
import com.klesniak.rankpollremastered.user.repo.RoleRepository;
import com.klesniak.rankpollremastered.user.repo.UserRepository;
import com.klesniak.rankpollremastered.user.service.JwtService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

import static com.klesniak.rankpollremastered.user.constants.RoleConstants.USER;
import static java.util.stream.Collectors.toSet;

@Service
public class AuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    private final JwtService jwtService;

    public AuthenticationService(UserRepository userRepository, PasswordEncoder passwordEncoder,
                                 RoleRepository roleRepository, JwtService jwtService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
        this.jwtService = jwtService;
    }

    public AuthenticationResponse register(RegisterRequest request) {
        Set<Role> userRoles = assignRoles(USER);
        User user = new User(request.getEmail(), passwordEncoder.encode(request.getPassword()), userRoles);
        userRepository.save(user);
        String jwtToken = jwtService.generateToken(user);
        return new AuthenticationResponse(jwtToken);
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        return null;
    }

    private Set<Role> assignRoles(RoleConstants... roleConstants) {
        return Arrays.stream(roleConstants)
                .map(role -> roleRepository.findByName(role.name()))
                .filter(role -> !role.isPresent())
                .map(Optional::get)
                .collect(toSet());
    }
}
