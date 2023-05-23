package com.ftn.kvtsvtprojekat.controller;

import com.ftn.kvtsvtprojekat.model.User;
import com.ftn.kvtsvtprojekat.model.dto.*;
import com.ftn.kvtsvtprojekat.model.enums.Roles;
import com.ftn.kvtsvtprojekat.security.TokenUtils;
import com.ftn.kvtsvtprojekat.service.UserService;
import jakarta.servlet.http.HttpServletResponse;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

import static org.springframework.http.ResponseEntity.status;

@RestController
@RequestMapping("/api/user")
public class UserController {

    public final UserService userService;
    public final AuthenticationManager authenticationManager;
    public final TokenUtils tokenUtils;
    public final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;

    public UserController(UserService userService, AuthenticationManager authenticationManager, TokenUtils tokenUtils, ModelMapper modelMapper, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.authenticationManager = authenticationManager;
        this.tokenUtils = tokenUtils;
        this.modelMapper = modelMapper;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping(value = "/signup")
    public ResponseEntity<UserRegisterDTO> create(@RequestBody @Validated UserRegisterDTO newUser){

        User createdUser = modelMapper.map(newUser, User.class);
        createdUser.setRole(Roles.USER);
        createdUser.setIsDeleted(false);

        userService.save(createdUser);

        if(createdUser == null){
            return new ResponseEntity<>(null, HttpStatus.NOT_ACCEPTABLE);
        }

        return new ResponseEntity<>(newUser, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public AuthenticationResponse createAuthenticationToken(
            @RequestBody JwtAuthenticationRequest authenticationRequest, HttpServletResponse response) {

        // Ukoliko kredencijali nisu ispravni, logovanje nece biti uspesno, desice se
        // AuthenticationException
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                authenticationRequest.getUsername(), authenticationRequest.getPassword()));

        // Ukoliko je autentifikacija uspesna, ubaci korisnika u trenutni security
        // kontekst
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // Kreiraj token za tog korisnika
        UserDetails user = (UserDetails) authentication.getPrincipal();
        String jwt = tokenUtils.generateToken(user);
        int expiresIn = tokenUtils.getExpiredIn();

        User user2 = userService.findByUsername(user.getUsername());
        Long userId = user2.getId();
        LocalDateTime time = LocalDateTime.now();
        user2.setLastLogin(time);
        userService.save(user2);

        // Vrati token kao odgovor na uspesnu autentifikaciju

        return AuthenticationResponse.builder()
                .userId(userId)
                .authenticationToken(jwt)
                .expiresAt(Instant.ofEpochSecond(expiresIn))
                .username(user.getUsername())
                .build();
    }

    @PutMapping(value = "/{id}", consumes = "application/json")
    public ResponseEntity<UserPasswordDTO> updatePassword(@PathVariable("id") Long id, @RequestBody UserPasswordDTO userPasswordDTO) {
        User user = userService.findOneById(id);
        if(user == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        if (passwordEncoder.matches(userPasswordDTO.getPasswordOld(), user.getPassword())) {
            user.setPassword(passwordEncoder.encode(userPasswordDTO.getPassword()));
            userService.save(user);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
        }
    }

    @GetMapping(value = "/findByUsername", params = "username", consumes = "application/json")
    public ResponseEntity<UserLoginDTO> getUser(@RequestParam String username) {
        User user = userService.findByUsername(username);

        if (user == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        UserLoginDTO userLoginDTO = modelMapper.map(user, UserLoginDTO.class);

        return status(HttpStatus.OK).body(userLoginDTO);
    }

    @GetMapping("/all")
//    @PreAuthorize("hasRole('ADMIN')")
    public List<User> loadAll() {
        return this.userService.findAll();
    }

    @GetMapping("/whoami")
//    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public User user(Principal user) {
        return this.userService.findByUsername(user.getName());
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        if(id == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        User user = userService.findOneById(id);

        if (user != null) {
            userService.delete(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.I_AM_A_TEAPOT);
        }
    }
}
