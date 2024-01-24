package com.sericulture.authentication.repository;

import com.sericulture.authentication.model.entity.MarketMasterInfo;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MarketMasterInfoRepository extends PagingAndSortingRepository<MarketMasterInfo, Long> {
    public MarketMasterInfo findByMarketMasterIdAndActive(long marketMasterId, boolean isActive);
}