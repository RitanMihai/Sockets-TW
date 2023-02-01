package com.example.packet;

import com.example.packet.database.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Packet implements Serializable {
    @Serial
    private static final long serialVersionUID = 7281851983542639272L;

    private User user;
    private String message;
    private Command command;
}
