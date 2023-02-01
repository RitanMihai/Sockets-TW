package com.example.client.util;

import com.example.packet.database.User;
public enum CurrentUser {
    INSTANCE;

    public User user = User.builder().build();
}
