package com.sericulture.authentication.repository;

import com.sericulture.authentication.model.entity.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserInfoRepository extends JpaRepository<UserInfo, Integer> {
    Optional<UserInfo> findByUsernameAndRoleId(String username,int roleId);
    Optional<UserInfo> findByEmailId(String emailId);
    Optional<UserInfo> findByEmailIdAndRoleId(String emailId,int roleId);
}
