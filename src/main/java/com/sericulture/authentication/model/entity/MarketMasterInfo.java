package com.sericulture.authentication.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name="marketMaster")
public class MarketMasterInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "market_master_seq")
    @SequenceGenerator(name = "market_master_seq", sequenceName = "market_master_seq", allocationSize = 1)
    @Column(name = "market_master_id")
    private Long marketMasterId;

    @Size(min = 2, max = 250, message = "Market name should be more than 1 characters.")
    @Column(name = "market_name", unique = true)
    private String marketMasterName;

    @Size(min = 2, max = 250, message = "Market address should be more than 1 characters.")
    @Column(name = "market_address", unique = true)
    private String marketMasterAddress;

    @Column(name = "box_weight")
    private Long boxWeight;

    @Column(name = "lot_weight")
    private Long lotWeight;

    @Column(name = "STATE_ID")
    private Long stateId;

    @Column(name = "DISTRICT_ID")
    private Long districtId;

    @Column(name = "TALUK_ID")
    private Long talukId;

    @Column(name = "ISSUE_BID_SLIP_START_TIME")
    private LocalTime issueBidSlipStartTime;


    @Column(name = "ISSUE_BID_SLIP_END_TIME")
    private LocalTime issueBidSlipEndTime;


    @Column(name = "AUCTION_1_START_TIME")
    private LocalTime auction1StartTime;


    @Column(name = "AUCTION_2_START_TIME")
    private LocalTime auction2StartTime;


    @Column(name = "AUCTION_3_START_TIME")
    private LocalTime auction3StartTime;


    @Column(name = "AUCTION_1_END_TIME")
    private LocalTime auction1EndTime;


    @Column(name = "AUCTION_2_END_TIME")
    private LocalTime auction2EndTime;


    @Column(name = "AUCTION_3_END_TIME")
    private LocalTime auction3EndTime;

    @Column(name = "AUCTION1_ACCEPT_START_TIME")
    private LocalTime auctionAcceptance1StartTime;

    @Column(name = "AUCTION2_ACCEPT_START_TIME")
    private LocalTime auctionAcceptance2StartTime;

    @Column(name = "AUCTION3_ACCEPT_START_TIME")
    private LocalTime auctionAcceptance3StartTime;

    @Column(name = "AUCTION1_ACCEPT_END_TIME")
    private LocalTime auctionAcceptance1EndTime;

    @Column(name = "AUCTION2_ACCEPT_END_TIME")
    private LocalTime auctionAcceptance2EndTime;

    @Column(name = "AUCTION3_ACCEPT_END_TIME")
    private LocalTime auctionAcceptance3EndTime;

    @Column(name = "SERIAL_NUMBER_PREFIX")
    private String serialNumberPrefix;

    @Column(name = "market_type_master_id")
    private Long marketTypeMasterId;

    @Column(name = "releer_minimum_balance")
    private Long reelerMinimumBalance;

    @Column(name = "market_lat")
    private BigDecimal marketLatitude;

    @Column(name = "market_longitude")
    private BigDecimal marketLongitude;

    @Column(name = "radius")
    private BigDecimal radius;

    @Column(name = "ACTIVE", columnDefinition = "TINYINT")
    private Boolean active;
}
