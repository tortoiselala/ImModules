package com.tortoiselala.bean;

import lombok.Data;
import org.springframework.http.ResponseEntity;

/**
 * @author tortoiselala
 */

@Data
public class UserPair{

    private String status;

    private String username;

    private String password;

    private String newPassword;

    private String friend;

    private ResponseEntity<String> response;
}