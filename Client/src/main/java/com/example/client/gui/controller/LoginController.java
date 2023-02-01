package com.example.client.gui.controller;

import com.example.client.gui.util.StageManager;
import com.example.client.util.CurrentUser;
import com.example.packet.Packet;
import com.example.packet.database.User;
import com.example.packet.Command;
import com.example.client.socket.SocketManager;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;


public class LoginController {
    @FXML
    public TextField usernameField;
    @FXML
    public TextField passwordField;

    public void onMouseClicked(MouseEvent mouseEvent) throws Exception {
        User user = User.builder()
                .username(usernameField.getText())
                .password(passwordField.getText())
                .build();

        Packet packet = Packet.builder()
                .user(user)
                .command(Command.LOGIN)
                .build();

        String login = SocketManager.getInstance().login(packet);

        if(login.equals("ok")){
            CurrentUser.INSTANCE.user = user;
            StageManager.setPageTitle("Chat Room");
            StageManager.setPageIcon("chat-room-icon.png");
            StageManager.replaceSceneContent("home.fxml");
        }
    }
}
