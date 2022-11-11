package it.tacticalpeople.tpdsserver.rest;

import it.tacticalpeople.tpdsserver.coreexception.ResourceException;
import it.tacticalpeople.tpdsserver.domain.ChatGroup;
import it.tacticalpeople.tpdsserver.dto.ChatGroupDto;
import it.tacticalpeople.tpdsserver.repository.ChatGroupRepository;
import it.tacticalpeople.tpdsserver.service.ChatGroupService;
import it.tacticalpeople.tpdsserver.webutils.HeaderUtil;
import it.tacticalpeople.tpdsserver.webutils.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@RestController
@RequestMapping("/chatGroups")
public class ChatGroupController {

    private final Logger log = LoggerFactory.getLogger(ChatGroupController.class);

    private static final String ENTITY_NAME = "ChatGroup";

    //	@Value("${clientApp.name}")
    private String applicationName;


    @Autowired
    private ChatGroupService chatGroupService;

    @Autowired
    private ChatGroupRepository chatGroupRepository;

    @PostMapping
    public ResponseEntity<ChatGroup> createProt(@RequestBody ChatGroupDto protDTO) throws URISyntaxException, ResourceException {
        log.debug("REST request to save Prot : {}", protDTO);
        if (protDTO.getId() != null) {
            throw new ResourceException("A new ChatGroup cannot already have an ID");
        }
        ChatGroup result = chatGroupService.save(protDTO);
        return ResponseEntity
                .created(new URI("/chatGroups/createChatGroup/" + result.getId()))
                .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
                .body(result);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateProt(@PathVariable(value = "id", required = false) final Long id, @RequestBody ChatGroupDto protDTO)
            throws URISyntaxException, ResourceException {
        log.debug("REST request to update Prot : {}, {}", id, protDTO);
        if (protDTO.getId() == null) {
            throw new ResourceException("Invalid id");
        }
        if (!Objects.equals(id, protDTO.getId())) {
            throw new ResourceException("Invalid ID Object");
        }

        if (!chatGroupRepository.existsById(id)) {
            throw new ResourceException("Entity not found");
        }

        ChatGroup result = chatGroupService.save(protDTO);
        return ResponseEntity
                .ok()
                .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, protDTO.getId().toString()))
                .body(result);
    }

    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Object> partialUpdateProt(
            @PathVariable(value = "id", required = false) final Long id,
            @RequestBody ChatGroupDto protDTO
    ) throws URISyntaxException, ResourceException {
        log.debug("REST request to partial update Prot partially : {}, {}", id, protDTO);
        if (protDTO.getId() == null) {
            throw new ResourceException("Invalid id");
        }
        if (!Objects.equals(id, protDTO.getId())) {
            throw new ResourceException("Invalid ID Object");
        }

        if (!chatGroupRepository.existsById(id)) {
            throw new ResourceException("Entity not found");
        }

        Optional<Object> result = Optional.ofNullable(chatGroupService.partialUpdate(protDTO));

        return ResponseUtil.wrapOrNotFound(
                result,
                HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, protDTO.getId().toString())
        );
    }

    @GetMapping("/")
    public List<ChatGroupDto> getAllProts() {
        log.debug("REST request to get all Prots");
        return chatGroupService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getProt(@PathVariable Long id) {
        log.debug("REST request to get Prot : {}", id);
        Optional<Object> protDTO = Optional.ofNullable(chatGroupService.findOne(id));
        return ResponseUtil.wrapOrNotFound(protDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProt(@PathVariable Long id) {
        log.debug("REST request to delete Prot : {}", id);
        chatGroupService.delete(id);
        return ResponseEntity
                .noContent()
                .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
                .build();
    }

}
