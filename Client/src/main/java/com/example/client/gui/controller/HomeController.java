package com.example.client.gui.controller;

import com.example.client.util.CurrentUser;
import com.example.packet.Command;
import com.example.packet.Packet;
import com.example.client.socket.SocketManager;
import com.example.packet.database.User;
import javafx.application.Platform;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.ResourceBundle;

public class HomeController implements Initializable {
    public TextArea chatArea;
    public TextField messageField;
    public Button sendButton;

    public void onMessageSend(MouseEvent mouseEvent) {
        String messageToSend = messageField.getText();
        Packet packet = new Packet(CurrentUser.INSTANCE.user, messageToSend, Command.MESSAGE);

        SocketManager.getInstance().sendMessage(packet);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //chatArea.setText("" + CurrentUser.INSTANCE.user.getUsername());
        SocketManager.getInstance().setChatArea(chatArea);
        SocketManager.getInstance().start();

        /* Notify the others about the connection */
        User system = User.builder().username("System").build();
        Packet packet = new Packet(system, CurrentUser.INSTANCE.user.getUsername() + " user has been connected;", Command.MESSAGE);
        SocketManager.getInstance().sendMessage(packet);
    }
}
