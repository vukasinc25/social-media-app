package com.ftn.kvtsvtprojekat.controller;

import com.ftn.kvtsvtprojekat.model.User;
import com.ftn.kvtsvtprojekat.model.User;
import com.ftn.kvtsvtprojekat.model.User;
import com.ftn.kvtsvtprojekat.model.User;
import com.ftn.kvtsvtprojekat.model.dto.*;
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
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.Instant;
import java.util.List;

import static org.springframework.http.ResponseEntity.status;

@RestController
@RequestMapping("/api/user")
public class UserController {

    public final UserService userService;
    public final AuthenticationManager authenticationManager;
    public final TokenUtils tokenUtils;
    public final ModelMapper modelMapper;

    public UserController(UserService userService, AuthenticationManager authenticationManager, TokenUtils tokenUtils, ModelMapper modelMapper) {
        this.userService = userService;
        this.authenticationManager = authenticationManager;
        this.tokenUtils = tokenUtils;
        this.modelMapper = modelMapper;
    }

    @PostMapping("/signup")
    public ResponseEntity<UserDTO> create(@RequestBody @Validated UserDTO newUser){

        User createdUser = modelMapper.map(newUser, User.class);
        userService.save(createdUser);

        if(createdUser == null){
            return new ResponseEntity<>(null, HttpStatus.NOT_ACCEPTABLE);
        }
        UserDTO userDTO = new UserDTO(createdUser);

        return new ResponseEntity<>(userDTO, HttpStatus.CREATED);
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

        // Vrati token kao odgovor na uspesnu autentifikaciju

        return AuthenticationResponse.builder()
                .userId(userId)
                .authenticationToken(jwt)
                .expiresAt(Instant.ofEpochSecond(expiresIn))
                .username(user.getUsername())
                .build();
    }

    @PutMapping(value = "/{id}", consumes = "application/json")
    public ResponseEntity<UserDTO> updateUser(@PathVariable("id") Long id, @RequestBody UserDTO userDTO) {
        User user = userService.findOneById(id);
        if(user == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        user.setPassword(userDTO.getPassword());
        userService.save(user);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    
    @GetMapping(value = "/findByUsername", params = "username", consumes = "application/json")
    public ResponseEntity<UserDTO> getUser(@RequestParam String username) {
        User user = userService.findByUsername(username);

        if (user == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        UserDTO userDTO = modelMapper.map(user, UserDTO.class);

        return status(HttpStatus.OK).body(userDTO);
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
