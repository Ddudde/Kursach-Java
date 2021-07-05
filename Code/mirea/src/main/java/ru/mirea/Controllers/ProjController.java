package ru.mirea.Controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.media.MediaView;
import ru.mirea.MireaApplication;
import ru.mirea.Start;
import ru.mirea.data.User;
import ru.mirea.data.UsersImpl;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class ProjController {

    private int id = 1;

    private UsersImpl usersImpl = (UsersImpl) MireaApplication.ctx.getBean("usersImpl");

    @FXML
    private Label usern;

    @FXML
    private ImageView icon;

    private User user;

    public void init()
    {
        user = usersImpl.getuser(Start.usename);
        if(user != null)
        {
            usern.setText(Start.usename);
            icon.setImage(new Image(getClass().getResource("/img/ls-icon" + (user.getIcons() + 1) + ".png").toExternalForm()));
        }
    }

    public void browse_git() throws URISyntaxException, IOException {
        Desktop d = Desktop.getDesktop();
        d.browse(new URI("https://github.com/Ddudde/Kursach-Java"));
    }

    public void next_list()
    {
        Pane pane = (Pane) Start.roots.get("id_" + id);
        id++;
        Pane pane1 = (Pane) Start.roots.get("id_" + id);
        pane.setVisible(false);
        pane1.setVisible(true);
    }
}
