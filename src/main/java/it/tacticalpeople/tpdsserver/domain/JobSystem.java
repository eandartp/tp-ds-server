package it.tacticalpeople.tpdsserver.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name="JOB_SYSTEM")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class JobSystem implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "DESCRIPTION")
    private String description;

}
