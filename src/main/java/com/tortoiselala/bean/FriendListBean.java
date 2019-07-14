package com.tortoiselala.bean;

import lombok.Data;

import java.util.List;

/**
 * @author tortoiselala
 */
@Data
public class FriendListBean {
    private String uid;
    private int count;
    private List<String> friends;
}
