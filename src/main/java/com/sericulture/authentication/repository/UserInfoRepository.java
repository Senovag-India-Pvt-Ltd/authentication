package com.sericulture.authentication.repository;

import com.sericulture.authentication.model.entity.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserInfoRepository extends JpaRepository<UserInfo, Integer> {
    Optional<UserInfo> findByUsername(String username);
    Optional<UserInfo> findByEmailID(String emailId);
    Optional<UserInfo> findByEmailIDAndRoleId(String emailId,long roleId);
}
