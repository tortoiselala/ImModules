package com.tortoiselala.bean;

import lombok.Data;

/**
 * @author tortoiselala
 */
@Data
public class ImUserBean {
    private String uuid;
    private String type;
    private int created;
    private int modified;
    private String username;
    private boolean activated;
    private String nickname;
    private String notifierName;
}
