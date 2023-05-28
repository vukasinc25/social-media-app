package com.ftn.kvtsvtprojekat.controller;

import com.ftn.kvtsvtprojekat.model.Post;
import com.ftn.kvtsvtprojekat.model.Report;
import com.ftn.kvtsvtprojekat.model.UserFriend;
import com.ftn.kvtsvtprojekat.model.User;
import com.ftn.kvtsvtprojekat.model.dto.*;
import com.ftn.kvtsvtprojekat.model.enums.Roles;
import com.ftn.kvtsvtprojekat.security.TokenUtils;
import com.ftn.kvtsvtprojekat.service.UserFriendService;
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

import javax.validation.Valid;
import java.security.Principal;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static org.springframework.http.ResponseEntity.status;

@RestController
@RequestMapping("/api/user")
public class UserController {

    public final UserService userService;
    private final UserFriendService userFriendService;
    public final AuthenticationManager authenticationManager;
    public final TokenUtils tokenUtils;
    public final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;

    public UserController(UserService userService, UserFriendService userFriendService, AuthenticationManager authenticationManager, TokenUtils tokenUtils, ModelMapper modelMapper, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.userFriendService = userFriendService;
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
        User user2 = userService.findByUsername(newUser.getUsername());
        newUser.setId(user2.getId());
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
        String role = String.valueOf(user2.getRole());
        LocalDateTime time = LocalDateTime.now();
        user2.setLastLogin(time);
        userService.save(user2);

        // Vrati token kao odgovor na uspesnu autentifikaciju

        return AuthenticationResponse.builder()
                .userId(userId)
                .authenticationToken(jwt)
                .expiresAt(Instant.ofEpochSecond(expiresIn))
                .username(user.getUsername())
                .role(role)
                .isBlocked(user2.getIsDeleted())
                .build();
    }


    @DeleteMapping(value = "/block/{id}")
    public ResponseEntity<UserPasswordDTO> blockUser(@PathVariable("id") Long id) {
        User user = userService.findOneById(id);
        if(user == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        user.setIsDeleted(!user.getIsDeleted());
        userService.save(user);
        return new ResponseEntity<>(HttpStatus.OK);
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

    @GetMapping(value = "/{id}")
    public ResponseEntity<UserRegisterDTO> getUser(@PathVariable("id") Long id) {
        User user = userService.findOneById(id);

        if (user == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        UserRegisterDTO userDTO = modelMapper.map(user, UserRegisterDTO.class);

        return status(HttpStatus.OK).body(userDTO);
    }

    @GetMapping(value = "/findUsersGroup/{groupId}")
    public ResponseEntity<List<UserRegisterDTO>> getUsersFromGroup(@PathVariable Long groupId) {

        List<User> users = userService.findAllUsersWithGroupRequests(groupId);
        List<UserRegisterDTO> userRegisterDTOS = new ArrayList<>();

        for (User user : users) {
            if(!user.getIsDeleted() ){
                UserRegisterDTO userRegisterDTO = modelMapper.map(user, UserRegisterDTO.class);
                userRegisterDTOS.add(userRegisterDTO);
            }
        }
        return status(HttpStatus.OK).body(userRegisterDTOS);
    }

    @GetMapping("/blocked")
    public ResponseEntity<List<UserRegisterDTO>> getBlockedUsers() {

        List<User> reports = userService.findAll();
        List<UserRegisterDTO> reportsDTO = new ArrayList<>();
        for (User report : reports) {
            if(report.getIsDeleted()) {
                UserRegisterDTO reportDTO = modelMapper.map(report, UserRegisterDTO.class);
                reportsDTO.add(reportDTO);
            }
        }
        return new ResponseEntity<>(reportsDTO, HttpStatus.OK);
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

    @PostMapping("/search")
    public ResponseEntity<List<UserRegisterDTO>> Search(@Valid @RequestBody UserRegisterDTO userRegisterDTO) {

        if(Objects.equals(userRegisterDTO.getFirstname(), "")){
            userRegisterDTO.setFirstname("eiwqopewiqpoewqiepwoqiewqpoieqpoeiwqpoeiqepoqiepqoeiqpoeiqpoeiwqpoeiwqpeowqieqwpoeiqwpo");
        }
        if(Objects.equals(userRegisterDTO.getLastname(), "")){
            userRegisterDTO.setLastname("eiwqopewiqpoewqiepwoqiewqpoieqpoeiwqpoeiqepoqiepqoeiqpoeiqpoeiwqpoeiwqpeowqieqwpoeiqwpo");
        }
        List<User> users = userService.searchByName(userRegisterDTO.getFirstname(), userRegisterDTO.getLastname());
        List<UserRegisterDTO> usersDTO = new ArrayList<>();
        for (User user : users) {
            if(!user.getIsDeleted()) {
                UserRegisterDTO userDTO = modelMapper.map(user, UserRegisterDTO.class);
                usersDTO.add(userDTO);
            }
        }
        return new ResponseEntity<>(usersDTO, HttpStatus.OK);
    }

    //---USER FRIEND---//
    @GetMapping("/userFriend/all")
    public ResponseEntity<List<UserFriendDTO>> getUserFriends() {

        List<UserFriend> userFriends = userFriendService.findAll();
        List<UserFriendDTO> userFriendsDTO = new ArrayList<>();
        for (UserFriend userFriend : userFriends) {
            if (!userFriend.getIsDeleted() ) {
                UserFriendDTO userFriendDTO = modelMapper.map(userFriend, UserFriendDTO.class);
                userFriendsDTO.add(userFriendDTO);
            }
        }
        return new ResponseEntity<>(userFriendsDTO, HttpStatus.OK);
    }

    @GetMapping("/userFriend/deleted")
    public ResponseEntity<List<UserFriendDTO>> getUserFriendsDeleted() {

        List<UserFriend> userFriends = userFriendService.findAll();
        List<UserFriendDTO> userFriendsDTO = new ArrayList<>();
        for (UserFriend userFriend : userFriends) {
            if (userFriend.getIsDeleted()) {
                UserFriendDTO userFriendDTO = modelMapper.map(userFriend, UserFriendDTO.class);
                userFriendsDTO.add(userFriendDTO);
            }
        }
        return new ResponseEntity<>(userFriendsDTO, HttpStatus.OK);
    }

    @GetMapping(value = "/userFriend/{id}")
    public ResponseEntity<UserFriendDTO> getUserFriend(@PathVariable("id") Long id) {
        UserFriend userFriend = userFriendService.findOneById(id);

        if (userFriend == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        UserFriendDTO userFriendDTO = modelMapper.map(userFriend, UserFriendDTO.class);

        return status(HttpStatus.OK).body(userFriendDTO);
    }

    @PostMapping(value = "/userFriend/create", consumes = "application/json")
    public ResponseEntity<UserFriend> createUserFriend(@Valid @RequestBody UserFriendDTO userFriendDTO) {
        UserFriend userFriend = modelMapper.map(userFriendDTO, UserFriend.class);
        userFriendService.save(userFriend);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @DeleteMapping(value = "/userFriend/{id}")
    public ResponseEntity<Void> deleteUserFriend(@PathVariable Long id) {
        if(id == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        UserFriend userFriend = userFriendService.findOneById(id);

        if (userFriend != null) {
            userFriend.setIsDeleted(true);
            userFriendService.save(userFriend);
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.I_AM_A_TEAPOT);
        }
    }
}
