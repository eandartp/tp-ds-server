package it.tacticalpeople.tpdsserver.service;

import it.tacticalpeople.tpdsserver.domain.ChatGroup;
import it.tacticalpeople.tpdsserver.dto.ChatGroupDto;
import it.tacticalpeople.tpdsserver.mapper.ChatGroupMapper;
import it.tacticalpeople.tpdsserver.repository.ChatGroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
@Transactional
public class ChatGroupService {

    private final Logger log = LoggerFactory.getLogger(ChatGroupService.class);

    @Autowired
    private ChatGroupRepository chatGroupRepository;

    @Autowired
    private ChatGroupMapper mapper;

    /**
     * Save a ChatGroup.
     *
     * @param chatGroupDto the dto to transform into entity and save
     * @return the persisted entity.
     */
    public ChatGroup save(ChatGroupDto chatGroupDto) {
        log.debug("Request to save ChatGroup : {}", chatGroupDto);
        ChatGroup chatGroup = this.mapper.fromDtoToChatGroup(chatGroupDto);
        return chatGroupRepository.save(chatGroup);
    }

    /**
     * Partially update a ChatGroup.
     *
     * @param chatGroupDto  the dto to transform into entity to update partially.
     * @return the persisted entity.
     */
    public Optional<ChatGroup> partialUpdate(ChatGroupDto chatGroupDto) {
        log.debug("Request to partially update chatGroup : {}", chatGroupDto);

        return chatGroupRepository
                .findById(chatGroupDto.getId())
                .map(existingChatGroup -> {
                    if (chatGroupDto.getNameGroup() != null) {
                        existingChatGroup.setNameGroup(chatGroupDto.getNameGroup());
                    }

                    return existingChatGroup;
                })
                .map(chatGroupRepository::save);
    }

    /**
     * Get all the ChatGroups.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<ChatGroupDto> findAll() {
        log.debug("Request to get all chat groups");
        return chatGroupRepository.findAll().stream()
                .map(chatGroup -> mapper.fromChatGroupToDto(chatGroup))
                .collect(Collectors.toList());
    }

    /**
     * Get one ChatGroup by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public ChatGroupDto findOne(Long id) {
        log.debug("Request to get chatGroup : {}", id);
        Optional<ChatGroup> chatGroup = chatGroupRepository.findById(id);
        ChatGroupDto chatGroupDto = null;
        if (chatGroup.isPresent()) {
            chatGroupDto = this.mapper.fromChatGroupToDto(chatGroup.get());
        }
        return chatGroupDto;
    }

    /**
     * Delete the ChatGroup by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete chatGroup : {}", id);
        chatGroupRepository.deleteById(id);
    }

}
