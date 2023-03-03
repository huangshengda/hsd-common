package com.hsd.functer;

import com.google.common.collect.Sets;

import java.util.Set;

public class UserFactory {

    public User buildUser(long uid) {
        Lazy<String> departmentLazy = Lazy.of(() -> "department");
        // 通过部门获得主管
        // department -> supervisor
        Lazy<Long> supervisorLazy = departmentLazy.map(
                department -> 100L
        );
        // 通过部门和主管获得权限
        // department, supervisor -> permission
        Lazy<Set<String>> permissionsLazy = departmentLazy.flatMap(department ->
                supervisorLazy.map(
                        supervisor -> Sets.newHashSet()
                )
        );

        User user = new User();
        user.setUid(uid);
        user.setDepartment(departmentLazy);
        user.setSupervisor(supervisorLazy);
        user.setPermissions(permissionsLazy);
        return user;
    }
}