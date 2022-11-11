package it.tacticalpeople.tpdsserver.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChatGroupDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    private String nameGroup;

}
