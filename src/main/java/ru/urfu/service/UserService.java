package ru.urfu.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.urfu.model.dto.AuthDto;
import ru.urfu.model.dto.RegistrationDto;
import ru.urfu.model.entity.Role;
import ru.urfu.model.entity.User;
import ru.urfu.model.repository.UserRepository;
import ru.urfu.utils.JwtUtils;

import java.util.Set;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;
    private final AuthenticationManager authenticationManager;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtUtils jwtUtils, AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtils = jwtUtils;
        this.authenticationManager = authenticationManager;
    }

    /**
     * Добавить пользователя в БД
     */
    @Transactional
    public void registerUser(RegistrationDto registrationDto) throws Exception {

        if (userRepository.findByUsername(registrationDto.username()) != null)
            throw new RuntimeException("Указанный Username занят");

        if (userRepository.findByEmail(registrationDto.email()) != null)
            throw new RuntimeException("Указанный email занят");

        User user = new User();
        user.setUsername(registrationDto.username());
        user.setEmail(registrationDto.email());
        user.setPassword(passwordEncoder.encode(registrationDto.password()));
        user.setRoles(Set.of(Role.USER));
        userRepository.save(user);
    }

    /**
     * Авторизовать пользователя
     * @return токен для авторизации
     */
    public String authUser(AuthDto authDto) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authDto.username(), authDto.password()));
            User user = userRepository.findByUsername(authDto.username());
            return jwtUtils.generateToken(user.toUserDetails());
        } catch (InternalAuthenticationServiceException e){
            throw new RuntimeException("Пользователь не найден");
        }
    }
}
