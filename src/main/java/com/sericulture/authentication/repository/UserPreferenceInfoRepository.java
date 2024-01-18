package com.sericulture.authentication.repository;

import com.sericulture.authentication.model.entity.UserPreferenceInfo;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface UserPreferenceInfoRepository extends PagingAndSortingRepository<UserPreferenceInfo, Long> {
    public UserPreferenceInfo findByUserMasterIdAndActive(long userPreferenceId, boolean isActive);
}
