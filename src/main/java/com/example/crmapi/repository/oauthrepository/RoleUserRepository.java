package com.example.crmapi.repository.oauthrepository;

import com.example.crmapi.model.auth.RoleUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface RoleUserRepository extends JpaRepository<RoleUser, Long> {

//
//    @Query(value="delete from role_user where user_id=?1 and role_id=?2", nativeQuery = true)
//    void deleteRoleAssignedToUser(Long userid, Long roleId);

    @Query("SELECT c FROM RoleUser c where c.userId.id=?1 AND c.roleId.id=?2")
    Optional<RoleUser> findByUserIdAndRoleId(Long userId, Long roleId);


}
