package com.simian.test_security2.pojo;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String roleName;
    private String roleNameCN;

    @ManyToMany(targetEntity = Menu.class, fetch = FetchType.EAGER)
    @JoinTable(name = "menu_role",
            joinColumns = {@JoinColumn(name="role_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "menu_id", referencedColumnName = "id")}
    )

    private List<Menu> menus;
}
