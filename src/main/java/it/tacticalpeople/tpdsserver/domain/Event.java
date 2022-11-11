package it.tacticalpeople.tpdsserver.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.time.Instant;

@Entity
@Table(name="EVENT")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Event implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long idEvent;

    @Column(name = "NAME_EVENT")
    private String nameEvent;

    @Column(name = "LINK")
    private String link;

    @Column(name = "DATE_EVENT")
    private Instant dateEvent;
}
