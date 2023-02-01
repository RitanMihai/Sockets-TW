package com.example.packet.database;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
public class User implements Serializable {
    private String username;
    private String password;
}
