package ru.mirea;

import javafx.fxml.FXML;
import javafx.scene.control.ScrollPane;

public class StartController {

    @FXML
    private ScrollPane scrpan;

    public void down()
    {
        scrpan.setVvalue(720);
    }

    public void up()
    {
        scrpan.setVvalue(0);
    }
}
