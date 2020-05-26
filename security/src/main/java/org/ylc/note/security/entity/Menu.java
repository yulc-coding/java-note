package org.ylc.note.security.entity;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class Menu {

    private Integer id;

    private Integer pid;

    private String name;

    private String permission;

    private String type;

    private String url;

    private String path;

    private String component;

    private String btnKey;

    private String icon;

    private Byte seq;

    private String remark;

    private Integer createUser;

    private Date createTime;

    private Integer updateUser;

    private Date updateTime;

}