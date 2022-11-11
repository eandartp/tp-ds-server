package it.tacticalpeople.tpdsserver.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LocationDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    private String address;
    private String city;
    private String postalCode;
    private String province;
    private String state;
    private String referenceName;
    private String email;

}
