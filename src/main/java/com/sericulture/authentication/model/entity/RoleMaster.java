package com.sericulture.authentication.model.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name="ROLE_MASTER")
@Getter
@Setter
public class RoleMaster {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "role_master_seq")
    @SequenceGenerator(name = "role_master_seq", sequenceName = "role_master_seq", allocationSize = 1)
    @Column(name = "role_id")
    private Long roleId;

    @Column(name = "ROLE_NAME")
    private String roleName;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "CREATED_DATE")
    private Date createdDate;
    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "MODIFIED_DATE")
    private Date modifiedDate;
    @Getter
    @Setter
    @Column(name = "ACTIVE", columnDefinition = "TINYINT")
    private Boolean active;
}
