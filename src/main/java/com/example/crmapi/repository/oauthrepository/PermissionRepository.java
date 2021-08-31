package com.example.crmapi.repository.oauthrepository;

import com.example.crmapi.model.auth.Permission;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PermissionRepository extends JpaRepository<Permission, Long> {
}
