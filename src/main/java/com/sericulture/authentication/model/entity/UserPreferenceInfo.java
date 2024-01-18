package com.sericulture.authentication.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name="userPreference")
public class UserPreferenceInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_preference_seq")
    @SequenceGenerator(name = "user_preference_seq", sequenceName = "user_preference_seq", allocationSize = 1)
    @Column(name = "user_preference_id")
    private Long userPreferenceId;

    @Column(name = "user_master_id", unique = true)
    private Long userMasterId;

    @Column(name = "godown_master_id")
    private Long godownId;

    @Column(name = "ACTIVE", columnDefinition = "TINYINT")
    private Boolean active;
}
