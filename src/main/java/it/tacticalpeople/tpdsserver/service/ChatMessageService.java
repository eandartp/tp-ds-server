package it.tacticalpeople.tpdsserver.service;

import it.tacticalpeople.tpdsserver.domain.ChatMessage;
import it.tacticalpeople.tpdsserver.dto.ChatMessageDto;
import it.tacticalpeople.tpdsserver.mapper.ChatMessageMapper;
import it.tacticalpeople.tpdsserver.repository.ChatMessageRepository;
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
public class ChatMessageService {

    private final Logger log = LoggerFactory.getLogger(ChatMessageService.class);

    @Autowired
    private ChatMessageRepository chatMessageRepository;

    private ChatMessageMapper mapper;

    /**
     * Save a ChatMessage.
     *
     * @param chatMessageDto the entity to save.
     * @return the persisted entity.
     */
    public ChatMessage save(ChatMessageDto chatMessageDto) {
        log.debug("Request to save ChatMessage : {}", chatMessageDto);
        ChatMessage chatMessage = this.mapper.fromDtoToChatMessage(chatMessageDto);
        return chatMessageRepository.save(chatMessage);
    }

    /**
     * Partially update a ChatMessage.
     *
     * @param chatMessageDto the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<ChatMessage> partialUpdate(ChatMessageDto chatMessageDto) {
        log.debug("Request to partially update ChatMessage : {}", chatMessageDto);

        return chatMessageRepository
                .findById(chatMessageDto.getId())
                .map(existingMessage -> {
                    if (chatMessageDto.getMessage() != null) {
                        existingMessage.setMessage(chatMessageDto.getMessage());
                    }
                    if (chatMessageDto.getChatGroupId() != null) {
                        existingMessage.setChatGroupId(chatMessageDto.getChatGroupId());
                    }
                    if (chatMessageDto.getDestinaryId() != null) {
                        existingMessage.setDestinaryId(chatMessageDto.getDestinaryId());
                    }
                    if (chatMessageDto.getUserId() != null) {
                        existingMessage.setUserId(chatMessageDto.getUserId());
                    }
                    if (chatMessageDto.getMessageDate() != null) {
                        existingMessage.setMessageDate(chatMessageDto.getMessageDate());
                    }

                    return existingMessage;
                })
                .map(chatMessageRepository::save);
    }

    /**
     * Get all the Users.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<ChatMessageDto> findAll() {
        log.debug("Request to get all ChatMessages");
      return chatMessageRepository.findAll().stream()
              .map(chatMessage -> mapper.fromChatMessageToDto(chatMessage))
              .collect(Collectors.toList());
    }

    /**
     * Get one ChatMessage by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ChatMessage> findOne(Long id) {
        log.debug("Request to get ChatMessage : {}", id);
        return chatMessageRepository.findById(id);
    }

    /**
     * Delete the ChatMessage by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete ChatMessage : {}", id);
        chatMessageRepository.deleteById(id);
    }

}
