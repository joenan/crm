package com.example.crmapi.controller.AuthController;

import com.example.crmapi.Dto.LoginRequestDto;
import com.example.crmapi.Dto.TokenRequestDto;
import com.example.crmapi.Dto.UserDto;
import com.example.crmapi.constants.ApiResponse;
import com.example.crmapi.constants.CustomMessages;
import com.example.crmapi.exceptions.RecordAlreadyPresentException;
import com.example.crmapi.exceptions.RecordNotFoundException;
import com.example.crmapi.model.auth.OauthClientDetails;
import com.example.crmapi.model.auth.Role;
import com.example.crmapi.model.auth.RoleUser;
import com.example.crmapi.model.auth.User;
import com.example.crmapi.service.auth.OauthService;

import io.swagger.annotations.ApiOperation;
//import org.json.JSONObject;
import org.json.JSONObject;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.ServletContext;
import java.io.File;
import java.net.URI;
import java.util.*;
import java.util.stream.Collectors;

//@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/user/v1/credentials")
@Validated
public class UserController {


    private static final Logger logger = LoggerFactory.getLogger(UserController.class);


    @Autowired
    OauthService oauthService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    ServletContext context;

    @Autowired
    private AuthenticationManager authenticationManager;

    private static final Logger log = LoggerFactory.getLogger(UserController.class);

    ModelMapper modelMapper = new ModelMapper();


    @PostMapping("/authenticateAndGetUserRoles")
    public ResponseEntity authenticateUser(@RequestBody LoginRequestDto loginRequest) {



//        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword());
        try {
            Authentication authentication = this.authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(authentication);

            String access_token_url = "http://localhost:9090/crm-api/oauth/token";

            TokenRequestDto tokenRequestDto = requestAccessToken(loginRequest.getUsername(), loginRequest.getPassword(), access_token_url);

            User principal = (User) authentication.getPrincipal();

            UserDto userDTO = modelMapper.map(principal, UserDto.class);

            //get userID from database

            Optional<User> userIdToGet = oauthService.getUserByUsername(principal.getUsername());


            userDTO.setId(userIdToGet.get().getId());
            userDTO.setToken(tokenRequestDto.getAccess_token());

            return ResponseEntity.ok().body(new ApiResponse<>(CustomMessages.Success, userDTO));
        }
        catch(BadCredentialsException e) {
            return ResponseEntity.ok().body(new ApiResponse<>(CustomMessages.Failed, "Invalid Username or Password"));
        }

    }


    @ApiOperation(value = "Get All Active Users")
    @GetMapping("/user")
    public Object getAllUser() {
        List<User> userInfos = oauthService.findAll();
        if (userInfos == null || userInfos.isEmpty()) {
            return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
        }
        return userInfos;
    }

    @ApiOperation(value = "Create new User")
    @PostMapping("/createUser")
    public ResponseEntity addUser(@RequestBody User userRecord) {

        Optional<User> findUserByUsername = oauthService.getUserByUsername(userRecord.getUsername());
        try {
            if (!findUserByUsername.isPresent()) {
                log.info("Sending Person {} from Client with Values " + "Surname: " + userRecord.getFirstName() + " LastName: " + userRecord.getLastName() + " Password: " + userRecord.getPassword() + "Email: " + userRecord.getEmail());

                if (userRecord == null) {
                    return ResponseEntity.badRequest().body(Collections.singletonMap("message", "Null Object was submitted"));
                }

                String encodedPassword = passwordEncoder.encode(userRecord.getPassword());
//        String encodedClientSecret = passwordEncoder.encode(userRecord.getClientSecret());

                userRecord.setPlainPassword(userRecord.getPassword());
                userRecord.setPassword(encodedPassword);
                userRecord.setAccountNonExpired(true);
                userRecord.setEnabled(true);
                userRecord.setDateCreated(new Date());
                userRecord.setCredentialsNonExpired(true);
                userRecord.setAccountNonLocked(true);

                OauthClientDetails clientDetails = new OauthClientDetails();
                clientDetails.setAccessTokenValidity(10800);
                clientDetails.setAutoapprove("");
                clientDetails.setAdditionalInformation("{}");
                clientDetails.setAuthorizedGrantTypes("authorization_code,password,refresh_token,implicit");
                clientDetails.setClientId(userRecord.getUsername());
                clientDetails.setClientSecret(encodedPassword);
                clientDetails.setRefreshTokenValidity(10800);
                clientDetails.setResourceIds("crm-rest-api");
                clientDetails.setScope("READ,WRITE");
                clientDetails.setWebServerRedirectUri("http://localhost:9090/");

                System.out.println(userRecord);

                oauthService.saveUser(userRecord);


                oauthService.saveClientDetails(clientDetails);


                return ResponseEntity.ok(new ApiResponse<>(CustomMessages.Success, userRecord));


            } else
                throw new RecordAlreadyPresentException("User with username: " + userRecord.getUsername() + " already exists!!");
        } catch (RecordAlreadyPresentException e) {
            return ResponseEntity.ok(new ApiResponse<>(CustomMessages.Failed, "User with username: " + userRecord.getUsername() + " already exists!!"));
        }
    }

    @ApiOperation(value = "To change a user Password")
    @PutMapping("/changePassword")
    public ResponseEntity updatePassword(@RequestParam(value = "userId") Long userId,@RequestParam(value = "newPassword") String newPassword) {
        Optional<User> optionalUser = oauthService.getUserById(userId);


        if (optionalUser.get().getId() == userId) {

            optionalUser.get().setEnabled(true);
            optionalUser.get().setAccountNonLocked(true);
            optionalUser.get().setAccountNonExpired(true);
            optionalUser.get().setCredentialsNonExpired(true);
            optionalUser.get().setPlainPassword(newPassword);
            String encodedPassword = passwordEncoder.encode(newPassword);
            optionalUser.get().setPassword(encodedPassword);
            oauthService.saveUser(optionalUser.get());

            Optional<OauthClientDetails> clientDetails = oauthService.getClientDetailsByUsername(optionalUser.get().getUsername());
//
            clientDetails.get().setClientSecret(encodedPassword);


            clientDetails.get().setAccessTokenValidity(10800);
            clientDetails.get().setAutoapprove("");
            clientDetails.get().setAdditionalInformation("{}");
            clientDetails.get().setAuthorizedGrantTypes("authorization_code,password,refresh_token,implicit");
            clientDetails.get().setClientId(optionalUser.get().getUsername());
            clientDetails.get().setRefreshTokenValidity(10800);
            clientDetails.get().setResourceIds("crm-rest-api");
            clientDetails.get().setScope("READ,WRITE");
            clientDetails.get().setWebServerRedirectUri("http://localhost:9090/");

            oauthService.saveClientDetails(clientDetails.get());

            return ResponseEntity.ok(new ApiResponse<>(CustomMessages.Success));

        } else {
            throw new RecordNotFoundException("No such  id is found!");
        }


    }

    @ApiOperation(value = "Edit User Details")
    @PutMapping("/editUser")
    public ResponseEntity editUser(@RequestBody User userRecord) {
        User user = oauthService.saveUser(userRecord);
        return ResponseEntity.ok(new ApiResponse<>(CustomMessages.Success, user));
    }

    @ApiOperation("To add role to user")
    @PostMapping(value = "/add/userRole", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity addSupplierAccount(@RequestBody User user) {

        Set<Role> roleList = user.getRoles().stream().distinct().collect(Collectors.toSet());
        user.setRoles(roleList);
        User savedSupplierAccount = oauthService.saveUser(user);
        return ResponseEntity.ok(new ApiResponse<>(CustomMessages.Success, savedSupplierAccount));
    }

    @ApiOperation("Save Roles")
    @PostMapping(value = "/add/role", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity addRoles(@RequestBody Role role) {
        //Check if Rolename Exists before saving a new role
        Optional<Role> checkRole = oauthService.findRoleByName(role.getName());

        if (checkRole.isPresent()) {
            return ResponseEntity.ok(new ApiResponse<>(CustomMessages.Failed, CustomMessages.Exists));
        }

        Role roles = oauthService.saveRole(role);
        return ResponseEntity.ok(new ApiResponse<>(CustomMessages.Success, roles));
    }


    @ApiOperation("Save Roles")
    @PutMapping("/edit/role")
    public ResponseEntity editRoles(@RequestBody Role role) {
        Optional<Role> r = oauthService.findRoleById(role.getId());
        if (r.isPresent()) {
            r.get().setName(role.getName());
            Role roles = oauthService.saveRole(r.get());
            return ResponseEntity.ok(new ApiResponse<>(CustomMessages.Success, roles));
        }
        return ResponseEntity.ok(new ApiResponse<>(CustomMessages.NotFound, "Role to edit not found"));
    }


    @ApiOperation(value = "Delete Role by ID")
    @DeleteMapping("/role/delete/{id}")
    public ResponseEntity<?> deleteRoleBy(@PathVariable("id") long id) {
        return oauthService.findRoleById(id)
                .map(record -> {
                    oauthService.deleteUserRole(id);
                    return ResponseEntity.ok(new ApiResponse<>(CustomMessages.Success, CustomMessages.Deleted));
                }).orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(CustomMessages.NotFoundMessage, CustomMessages.NotFound)));
    }

    @ApiOperation("Get All Roles")
    @GetMapping(value = "/list/roles", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Role> getListOfRoles() {
        List<Role> roleList = oauthService.findAllRoles();
        return roleList;
    }


    @ApiOperation(value = "Get User by ID")
    @GetMapping("/user/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        Optional<User> User = oauthService.getUserById(id);
        if (User == null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(User.get(), HttpStatus.OK);
    }


    @ApiOperation("To delete a User details by ID")
    @DeleteMapping("/user/{id}")
    public ResponseEntity deleteProductById(@PathVariable("id") Long id) {
        oauthService.deleteUserById(id);
//        userService.deleteUserById(id);
        return ResponseEntity.ok(new ApiResponse<>(CustomMessages.Deleted, CustomMessages.Deleted));
    }

    @ApiOperation("To return all Users")
    @GetMapping("/list/users")
    public List<User> getAllRoles() {
        List<User> userList = oauthService.findAll();
        oauthService.findAll();
//        List<User> userFilter = userList.stream().filter(x -> !x.getUsername().equalsIgnoreCase("nandom")).collect(Collectors.toList());
        return userList;
    }

    @ApiOperation("To delete a Role assigned to a user")
    @DeleteMapping("/deleteAssignedRole/{userId}/{roleId}")
    public ResponseEntity deleteAssignedRole(@PathVariable("userId") Long userId, @PathVariable("roleId") Long roleId) {

        Optional<RoleUser> ru = oauthService.findRoleUserById(userId, roleId);

        if (ru.isPresent()) {
            oauthService.deleteRoleUser(ru.get());
            return ResponseEntity.ok().body(new ApiResponse<>(CustomMessages.Success, CustomMessages.Deleted));
        }
        return ResponseEntity.ok().body(new ApiResponse<>(CustomMessages.NotFoundMessage, CustomMessages.NotFound));
    }

    @ApiOperation("To add a role to a user")
    @PostMapping("/assignRoleToUser")
    public ResponseEntity addRoleToUser(@RequestParam("userId") Long userId, @RequestParam("roleId") Long roleId) {
        Optional<User> searchedUser = oauthService.findUserById(userId);
        Optional<Role> searchedRole = oauthService.findRoleById(roleId);

        RoleUser ru = new RoleUser();
        ru.setUserId(searchedUser.get());
        ru.setRoleId(searchedRole.get());

        RoleUser savedRu = oauthService.saveRoleUser(ru);

        return ResponseEntity.ok().body(new ApiResponse<>(CustomMessages.Success, savedRu));

    }

    public TokenRequestDto requestAccessToken(String username, String password, String oauthaurl) {

        String access_token = null;
        String refresh_token = null;
        String token_type = null;
        Integer expires_in = null;
        String scope = null;

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.setBasicAuth(username, password);
        HttpEntity<String> entity = new HttpEntity<String>("parameters", headers);

        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(oauthaurl)
                .queryParam("grant_type", "password")
                .queryParam("username", username)
                .queryParam("password", password);

        URI myUri = builder.buildAndExpand().toUri();


        ResponseEntity<?> result = restTemplate.exchange(myUri, HttpMethod.POST, entity, String.class);


        JSONObject jsonObject = new JSONObject(result.getBody().toString());

        //get access_token from jsonObject here
        access_token = jsonObject.getString("access_token");
        refresh_token = jsonObject.getString("refresh_token");
        token_type = jsonObject.getString("token_type");
        expires_in = jsonObject.getInt("expires_in");
        scope = jsonObject.getString("scope");


        //Passing the variables to the TokenRequestDto so that it can be returned.

        TokenRequestDto dto = new TokenRequestDto();
        dto.setAccess_token(access_token);
        dto.setExpires_in(expires_in);
        dto.setRefresh_token(refresh_token);
        dto.setScope(scope);
        dto.setToken_type(token_type);

        return dto;

    }


}
