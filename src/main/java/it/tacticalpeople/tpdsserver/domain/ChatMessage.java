package it.tacticalpeople.tpdsserver.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.time.ZonedDateTime;

@Entity
@Table(name="CHAT_MESSAGE")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChatMessage implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="ID")
    private Long id;

    @Column(name="USER_ID")
    private Long userId;

    @Column(name="DESTINARY_ID")
    private Long destinaryId;

    @Column(name="CHAT_GROUP_ID")
    private Long chatGroupId;

    @Column(name="MESSAGE")
    private String message;

    @Column(name="MESSAGE_DATE")
    private ZonedDateTime messageDate;

}
