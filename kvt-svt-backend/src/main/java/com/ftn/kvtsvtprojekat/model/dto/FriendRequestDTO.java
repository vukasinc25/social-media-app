package com.ftn.kvtsvtprojekat.model.dto;

import com.ftn.kvtsvtprojekat.model.User;
import jakarta.persistence.OneToOne;
import lombok.Getter;
import lombok.Setter;


import java.time.LocalDateTime;

@Getter
@Setter
public class FriendRequestDTO {
    private Long id;
    private boolean approved;
    private LocalDateTime requestDate;
    private LocalDateTime responseDate;
    private Long requestFromId;
    private Long requestForId;
}
