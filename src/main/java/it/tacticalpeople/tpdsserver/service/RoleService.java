package it.tacticalpeople.tpdsserver.service;

import it.tacticalpeople.tpdsserver.coreexception.ResourceException;
import it.tacticalpeople.tpdsserver.domain.Role;
import it.tacticalpeople.tpdsserver.dto.RoleDto;
import it.tacticalpeople.tpdsserver.mapper.RoleMapper;
import it.tacticalpeople.tpdsserver.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
@Transactional
public class RoleService {

    private final Logger log = LoggerFactory.getLogger(RoleService.class);

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private RoleMapper mapper;



    public Role save(RoleDto roleDto) {
        log.debug("Request to save Role : {}", roleDto);
        Role role = this.mapper.fromDtoToRole(roleDto);
        return roleRepository.save(role);
    }

    public RoleDto partialUpdate(Long id, RoleDto roleDto) {
        log.debug("Role<Service - partialUpdate");
        Role role = null;
        try {
            role = roleRepository.findById(id).orElseThrow(() -> new ResourceException("Invalid Id"));
        } catch (ResourceException e) {
            throw new RuntimeException(e);
        }
        role = RoleMapper.fromDtoToRole(roleDto);
        role = roleRepository.save(role);
        return RoleMapper.fromRoleToDto(role);

    }


    @Transactional(readOnly = true)
    public List<RoleDto> findAll() {
        log.debug("Request to get all Role");
        return mapper.fromRoleListToDtoList(roleRepository.findAll());
    }


    @Transactional(readOnly = true)
    public RoleDto findOne(Long id) {
        log.debug("Request to get Role : {}", id);
        Optional<Role> role = roleRepository.findById(id);
        RoleDto roleDto = null;
        if (role.isPresent()) {
            roleDto = this.mapper.fromRoleToDto(role.get());
        }
        return roleDto;
    }


    public void delete(Long id) {
        log.debug("Request to delete Role : {}", id);
        roleRepository.deleteById(id);
    }

}
