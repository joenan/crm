package com.example.crmapi.service.auth;


import com.example.crmapi.model.auth.*;

import java.util.List;
import java.util.Optional;

public interface OauthService {
    public User saveUser(User user);

    public Optional<User> getUserById(Long id);

    public Optional<User> getUserByUsername(String username);

    public List<User> findAll();

    public Permission savePermission(Permission permission);

    public Role saveRole(Role role);

    public RoleUser saveRoleUser(RoleUser roleUser);

    public void deleteRoleUser(RoleUser user);

    public Optional<RoleUser> findRoleUserById(Long userId, Long roleId);

    public OauthClientDetails saveClientDetails(OauthClientDetails details);

    public Optional<OauthClientDetails> getClientDetailsById(String clientId);

    public void saveAllPermission(List<Permission> list);

    public void saveAllRoles(List<Role> list);

    public List<Role> findAllRoles();

    public void deleteUserById(Long id);

    public void deleteUserRole(Long id);

    Optional<User> findUserById(Long userId);

    Optional<Role> findRoleByName(String name);

    Optional<Role> findRoleById(Long id);

    Optional<OauthClientDetails> getClientDetailsByUsername(String username);

}
