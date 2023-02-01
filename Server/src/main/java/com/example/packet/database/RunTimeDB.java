package com.example.packet.database;

import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public enum RunTimeDB {
    INSTANCE;
    /* Connected clients */
    public List<Socket> clients = new ArrayList<>();
    public List<User> users = new ArrayList<>();

    RunTimeDB() {
        this.users = new ArrayList<>(List.of(
                User.builder().username("Mock 1").password("mock").build(),
                User.builder().username("Mock 2").password("mock").build(),
                User.builder().username("Mock 3").password("mock").build(),
                User.builder().username("Mock 4").password("mock").build(),
                User.builder().username("Mock 5").password("mock").build(),
                User.builder().username("Mock 6").password("mock").build()
        ));
    }
}
