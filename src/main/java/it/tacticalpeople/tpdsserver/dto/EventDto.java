package it.tacticalpeople.tpdsserver.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EventDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long idEvent;

    private String nameEvent;
    private String link;
    private Instant dateEvent;

}
