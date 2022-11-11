package it.tacticalpeople.tpdsserver.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.ZonedDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChatMessageDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    private Long userId;
    private Long destinaryId;
    private Long chatGroupId;
    private String message;
    private ZonedDateTime messageDate;

}
