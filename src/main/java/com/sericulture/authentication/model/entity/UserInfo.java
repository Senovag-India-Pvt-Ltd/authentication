package com.sericulture.authentication.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name="userMaster")
public class UserInfo {

    private String firstName;
    private String middleName;
    private String lastName;
    private String password;
    @Id
    private String emailId;
    private String username;
    private int roleId;

}
