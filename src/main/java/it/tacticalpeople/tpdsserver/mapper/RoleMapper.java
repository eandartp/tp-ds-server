package it.tacticalpeople.tpdsserver.mapper;

import it.tacticalpeople.tpdsserver.domain.Role;
import it.tacticalpeople.tpdsserver.dto.RoleDto;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class RoleMapper implements Serializable {

    private static final long serialVersionUID = 1L;


    public static Role fromDtoToRole(RoleDto roleDto) {
        Role role = new Role();
        role.setRoleName(roleDto.getRoleName());
        role.setRoleDescription(roleDto.getRoleDescription());
        return role;
    }

    public static List<Role> fromDtoListToRoleList(List<RoleDto> roleDtos){
        return roleDtos.stream().map(RoleMapper::fromDtoToRole)
                .collect(Collectors.toList());
    }

    public static List<RoleDto> fromRoleListToDtoList(List<Role> roles){
        return roles.stream().map(RoleMapper::fromRoleToDto)
                .collect(Collectors.toList());
    }

    public static RoleDto fromRoleToDto(Role role) {
        RoleDto roleDto = new RoleDto();
        roleDto.setRoleName(role.getRoleName());
        roleDto.setRoleDescription(role.getRoleDescription());
        return roleDto;
    }

}
