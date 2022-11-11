package it.tacticalpeople.tpdsserver.mapper;

import it.tacticalpeople.tpdsserver.domain.ChatMessage;
import it.tacticalpeople.tpdsserver.dto.ChatMessageDto;
import org.springframework.stereotype.Component;

import java.io.Serializable;

@Component
public class ChatMessageMapper implements Serializable {

    private static final long serialVersionUID = 1L;

    public ChatMessage fromDtoToChatMessage(ChatMessageDto chatMessageDto) {
        ChatMessage chatMessage = new ChatMessage();
        chatMessage.setId(chatMessageDto.getId());
        chatMessage.setUserId(chatMessageDto.getDestinaryId());
        chatMessage.setDestinaryId(chatMessageDto.getDestinaryId());
        chatMessage.setChatGroupId(chatMessageDto.getChatGroupId());
        chatMessage.setMessage(chatMessageDto.getMessage());
        chatMessage.setMessageDate(chatMessageDto.getMessageDate());
        return chatMessage;
    }
    public ChatMessageDto fromChatMessageToDto(ChatMessage chatMessage) {
        ChatMessageDto chatMessageDto = new ChatMessageDto();
        chatMessageDto.setId(chatMessage.getId());
        chatMessageDto.setUserId(chatMessage.getDestinaryId());
        chatMessageDto.setDestinaryId(chatMessage.getDestinaryId());
        chatMessageDto.setChatGroupId(chatMessage.getChatGroupId());
        chatMessageDto.setMessage(chatMessage.getMessage());
        chatMessageDto.setMessageDate(chatMessage.getMessageDate());
        return chatMessageDto;
    }

}
