package it.tacticalpeople.tpdsserver.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name="SEDE")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Sede implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "SITE_CITY")
    private String siteCity;

    @Column(name = "SITE_ADDRESS")
    private String siteAddress;

    @Column(name = "SITE_NUMBER")
    private String siteNumber;

    @Column(name = "ZP")
    private String zp;

    @Column(name = "PROV")
    private String prov;

}
