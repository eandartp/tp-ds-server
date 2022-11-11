package it.tacticalpeople.tpdsserver.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name="TEACHER")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Teacher implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "MATRICOLA")
    private String matricola;

    @Column(name = "NAME")
    private String name;

    @Transient
    private List<Course> courses;
}
