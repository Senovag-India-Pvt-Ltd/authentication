package com.sericulture.authentication.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.management.relation.Role;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name="userMaster")
public class UserInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_master_seq")
    @SequenceGenerator(name = "user_master_seq", sequenceName = "user_master_seq", allocationSize = 1)
    @Column(name = "user_master_id")
    private Long userMasterId;


    @Size(min = 2, max = 250, message = "First name should be more than 1 characters.")
    @Column(name = "first_name")
    private String firstName;

    @Size(min = 0, max = 250, message = "Middle name should be less than 250 characters.")
    @Column(name = "middle_name")
    private String middleName;

    @Size(min = 0, max = 250, message = "Last name should be more than 1 characters.")
    @Column(name = "last_name")
    private String lastName;

    @Size(min = 2, max = 250, message = "password name should be more than 1 characters.")
    @Column(name = "password")
    private String password;

    @Size(min = 0, max = 250, message = "Email name should be more than 1 characters.")
    @Column(name = "email_id")
    private String emailID;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "role_id")
    private RoleMaster role;

    @Column(name = "username", unique = true)
    private String username;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "market_id")
    private int marketId;

    @Column(name = "user_type")
    private int userType;

    @Column(name = "user_type_id")
    private Long userTypeId;

    @Column(name = "device_id")
    private String deviceId;

}
