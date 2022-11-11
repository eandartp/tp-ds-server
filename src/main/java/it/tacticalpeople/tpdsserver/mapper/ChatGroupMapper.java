package it.tacticalpeople.tpdsserver.mapper;

import it.tacticalpeople.tpdsserver.domain.ChatGroup;
import it.tacticalpeople.tpdsserver.dto.ChatGroupDto;
import org.springframework.stereotype.Component;

import java.io.Serializable;

@Component
public class ChatGroupMapper implements Serializable {

    public ChatGroup fromDtoToChatGroup(ChatGroupDto chatGroupDto) {
        ChatGroup chatGroup = new ChatGroup();
        chatGroup.setId(chatGroupDto.getId());
        chatGroup.setNameGroup(chatGroupDto.getNameGroup());
        return chatGroup;
    }

    public ChatGroupDto fromChatGroupToDto(ChatGroup chatGroup) {
        ChatGroupDto chatGroupDto = new ChatGroupDto();
        chatGroupDto.setId(chatGroup.getId());
        chatGroupDto.setNameGroup(chatGroup.getNameGroup());
        return chatGroupDto;
    }

}
