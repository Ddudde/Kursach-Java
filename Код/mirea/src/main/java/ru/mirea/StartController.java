package ru.mirea;

import javafx.fxml.FXML;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;

import java.awt.*;
import java.awt.event.KeyEvent;

public class StartController {

    @FXML
    private ScrollPane scrpan;

    @FXML
    private Pane auth;

    public void initialize()
    {
        //caps();
        auth.setOnKeyReleased(event -> {
            if (event.getCode() == KeyCode.CAPS) caps();
        });
    }

    public void caps()
    {
        boolean isCapsLockOn = Toolkit.getDefaultToolkit().getLockingKeyState(KeyEvent.VK_CAPS_LOCK);
        System.out.println(isCapsLockOn);
    }

    public void down()
    {
        scrpan.setVvalue(720);
    }

    public void up()
    {
        scrpan.setVvalue(0);
    }
}
