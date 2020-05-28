package com.cloud.platformuser.service;

import com.cloud.common.utils.R;
import com.cloud.model.common.Page;
import com.cloud.model.platformuser.SysRole;
import com.cloud.platformuser.dto.SysRoleDto;

import java.util.Map;
import java.util.Set;

public interface SysRoleService {

	R save(SysRole sysRole);

	void update(SysRole sysRole);

	void deleteRole(String id);

	void setPermissionToRole(String id, Set<String> permissionIds);

	SysRole findById(String id);

	Page<SysRoleDto> findRoles(Map<String, Object> params);



}
