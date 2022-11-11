package it.tacticalpeople.tpdsserver.rest;

import it.tacticalpeople.tpdsserver.coreexception.ResourceException;
import it.tacticalpeople.tpdsserver.domain.ChatMessage;
import it.tacticalpeople.tpdsserver.dto.ChatMessageDto;
import it.tacticalpeople.tpdsserver.repository.ChatMessageRepository;
import it.tacticalpeople.tpdsserver.service.ChatMessageService;
import it.tacticalpeople.tpdsserver.webutils.HeaderUtil;
import it.tacticalpeople.tpdsserver.webutils.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;


@RestController
@RequestMapping("/chatMessages")
public class ChatMessageController {

    private final Logger log = LoggerFactory.getLogger(ChatMessageController.class);

    private static final String ENTITY_NAME = "ChatMessage";

    //	@Value("${clientApp.name}")
    private String applicationName;


    @Autowired
    private ChatMessageService chatMessageService;

    private ChatMessageRepository chatMessageRepository;

    @PostMapping("/")
    public ResponseEntity<ChatMessage> createChatMessage(@RequestBody ChatMessageDto protDTO) throws URISyntaxException, ResourceException {
        log.debug("REST request to save Prot : {}", protDTO);
        if (protDTO.getId() != null) {
            throw new ResourceException("A new ChatMessage cannot already have an ID");
        }
        ChatMessage result = chatMessageService.save(protDTO);
        return ResponseEntity
                .created(new URI("/chatMessages/saveChatMessage/" + result.getId()))
                .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
                .body(result);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateChatMessage(@PathVariable(value = "id", required = false) final Long id, @RequestBody ChatMessageDto protDTO)
            throws URISyntaxException, ResourceException {
        log.debug("REST request to update ChatMessage : {}, {}", id, protDTO);
        if (protDTO.getId() == null) {
            throw new ResourceException("Invalid id");
        }
        if (!Objects.equals(id, protDTO.getId())) {
            throw new ResourceException("Invalid ID Object");
        }

        if (!chatMessageRepository.existsById(id)) {
            throw new ResourceException("Entity not found");
        }

        ChatMessage result = chatMessageService.save(protDTO);
        return ResponseEntity
                .ok()
                .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, protDTO.getId().toString()))
                .body(result);
    }

    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Object> partialUpdateChatMessage(
            @PathVariable(value = "id", required = false) final Long id,
            @RequestBody ChatMessageDto protDTO
    ) throws URISyntaxException, ResourceException {
        log.debug("REST request to partial update Prot partially : {}, {}", id, protDTO);
        if (protDTO.getId() == null) {
            throw new ResourceException("Invalid id");
        }
        if (!Objects.equals(id, protDTO.getId())) {
            throw new ResourceException("Invalid ID Object");
        }

        if (!chatMessageRepository.existsById(id)) {
            throw new ResourceException("Entity not found");
        }

        Optional<Object> result = Optional.ofNullable(chatMessageService.partialUpdate(protDTO));

        return ResponseUtil.wrapOrNotFound(
                result,
                HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, protDTO.getId().toString())
        );
    }

    @GetMapping("/")
    public List<ChatMessageDto> getAllChatMessage() {
        log.debug("REST request to get all ChatMessage");
        return chatMessageService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getChatMessageById(@PathVariable Long id) {
        log.debug("REST request to get Prot : {}", id);
        Optional<Object> protDTO = Optional.ofNullable(chatMessageService.findOne(id));
        return ResponseUtil.wrapOrNotFound(protDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteChatMessage(@PathVariable Long id) {
        log.debug("REST request to delete Prot : {}", id);
        chatMessageService.delete(id);
        return ResponseEntity
                .noContent()
                .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
                .build();
    }

}
