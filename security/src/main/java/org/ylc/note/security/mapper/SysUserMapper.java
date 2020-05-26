package org.ylc.note.security.mapper;

import org.springframework.stereotype.Component;
import org.ylc.note.security.entity.SysUser;

import java.util.List;

@Component
public interface SysUserMapper {

    List<SysUser> list();
}