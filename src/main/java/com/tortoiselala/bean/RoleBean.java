package com.tortoiselala.bean;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author tortoiselala
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class RoleBean extends BaseBean {
    private String name;

    private String description;

    private String[] privileges;

    public RoleBean(String name, String description, String[] privileges) {
        this.name = name;
        this.description = description;
        this.privileges = privileges;
    }
}
