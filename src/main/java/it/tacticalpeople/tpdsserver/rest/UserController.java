package it.tacticalpeople.tpdsserver.rest;

import it.tacticalpeople.tpdsserver.coreexception.ResourceException;
import it.tacticalpeople.tpdsserver.domain.User;
import it.tacticalpeople.tpdsserver.dto.UserDto;
import it.tacticalpeople.tpdsserver.repository.UserRepository;
import it.tacticalpeople.tpdsserver.service.UserService;
import it.tacticalpeople.tpdsserver.webutils.HeaderUtil;
import it.tacticalpeople.tpdsserver.webutils.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;


@RestController
@RequestMapping("/users")
public class UserController {

    private final Logger log = LoggerFactory.getLogger(UserController.class);

    private static final String ENTITY_NAME = "User";

    //	@Value("${clientApp.name}")
    private String applicationName;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @PostMapping("/")
    public ResponseEntity saveProt(@RequestBody UserDto protDTO) throws URISyntaxException, ResourceException {
        log.debug("REST request to save User : {}", protDTO);
        return ResponseEntity.ok(userService.saveUser(protDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity updateUser(@PathVariable(value = "id", required = false) final Long id, @RequestBody UserDto protDTO)
            throws URISyntaxException, ResourceException {
        return ResponseEntity
                .ok(userService.updateUser(id, protDTO));
    }

    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Object> partialUpdateUser(
            @PathVariable(value = "id", required = false) final Long id,
            @RequestBody UserDto protDTO
    ) throws URISyntaxException, ResourceException {
        log.debug("REST request to partial update User partially : {}, {}", id, protDTO);
        if (protDTO.getId() == null) {
            throw new ResourceException("Invalid id");
        }
        if (!Objects.equals(id, protDTO.getId())) {
            throw new ResourceException("Invalid ID Object");
        }

        if (!userRepository.existsById(id)) {
            throw new ResourceException("Entity not found");
        }

        Optional<Object> result = Optional.ofNullable(userService.partialUpdate(protDTO));

        return ResponseUtil.wrapOrNotFound(
                result,
                HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, protDTO.getId().toString())
        );
    }

    @GetMapping("/")
    public List<UserDto> getAllProts() {
        log.debug("REST request to get all Users");
        return userService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable Long id) {
        log.debug("REST request to get User : {}", id);
        Optional<UserDto> protDTO = Optional.ofNullable(userService.findOne(id));
        return ResponseUtil.wrapOrNotFound(protDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        log.debug("REST request to delete User : {}", id);
        userService.delete(id);
        return ResponseEntity
                .noContent()
                .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
                .build();
    }

}
