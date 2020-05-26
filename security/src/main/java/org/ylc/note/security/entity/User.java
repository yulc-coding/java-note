package org.ylc.note.security.entity;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class User {

    private Long id;

    private String name;

    private String username;

    private String password;

    private String phone;

    private String gender;

    private String remark;

    private Byte enabled;

    private Integer createUser;

    private Date createTime;

    private Integer updateUser;

    private Date updateTime;

}