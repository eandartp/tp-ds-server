package it.tacticalpeople.tpdsserver.rest;

import it.tacticalpeople.tpdsserver.domain.Role;
import it.tacticalpeople.tpdsserver.dto.RoleDto;
import it.tacticalpeople.tpdsserver.repository.RoleRepository;
import it.tacticalpeople.tpdsserver.service.RoleService;
import it.tacticalpeople.tpdsserver.webutils.HeaderUtil;
import it.tacticalpeople.tpdsserver.webutils.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/roles")
public class RoleController {

    private final Logger log = LoggerFactory.getLogger(RoleController.class);

    private static final String ENTITY_NAME = "Role";

    //	@Value("${clientApp.name}")
    private String applicationName;

    @Autowired
    private RoleService roleService;

    @Autowired
    private RoleRepository roleRepository;

    @PostMapping(path= "/", consumes = "application/json", produces = "application/json")
    public ResponseEntity saveRole(@RequestBody RoleDto protDTO) throws Exception
    {
        log.debug("RoleController - url: /saveRole - method: saveRole");

        //add resource
        Role role = roleService.save(protDTO);

        //create resource location
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(role.getId())
                .toUri();

        //send location in response
        return ResponseEntity.created(location).body(role);
    }

    @PatchMapping(value = "/{id}", consumes = {"application/json", "application/merge-patch+json"})
    public ResponseEntity partialUpdateRole(
            @PathVariable(value = "id") final Long id,
            @RequestBody RoleDto protDTO) {
        log.debug("RoleController - url: /patchRole/{id} - method: partialUpdateRole");
        return ResponseEntity.ok(roleService.partialUpdate(id, protDTO));
    }

    @GetMapping("/")
    public List<RoleDto> getAllRoles() {
        log.debug("REST request to get all Roles");
        return roleService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<RoleDto> getRoleById(@PathVariable Long id) {
        log.debug("REST request to get Role : {}", id);
        Optional<RoleDto> protDTO = Optional.ofNullable(roleService.findOne(id));
        return ResponseUtil.wrapOrNotFound(protDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRole(@PathVariable Long id) {
        log.debug("REST request to delete Role : {}", id);
        roleService.delete(id);
        return ResponseEntity
                .noContent()
                .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
                .build();
    }



}
