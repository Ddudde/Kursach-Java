package ru.mirea.Controllers;

import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.media.MediaView;
import ru.mirea.MireaApplication;
import ru.mirea.Start;
import ru.mirea.ThreeD.Scene3D;
import ru.mirea.data.User;
import ru.mirea.data.UsersImpl;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Objects;

public class ProjController {

    private int id = 1;

    private UsersImpl usersImpl = (UsersImpl) MireaApplication.ctx.getBean("usersImpl");

    @FXML
    private Label usern;

    @FXML
    private ImageView icon;

    private User user;

    private Scene3D scene3D;

    public void init()
    {
        user = usersImpl.getuser(Start.usename);
        if(user != null)
        {
            usern.setText(Start.usename);
            icon.setImage(new Image(getClass().getResourceAsStream("/img/ls-icon" + (user.getIcons() + 1) + ".png")));
        }
        /*((Pane) Start.roots.get("id_1")).setVisible(false);
        ((Pane) Start.roots.get("id_11")).setVisible(true);
        Pane view3d = (Pane) ((Pane) Start.roots.get("id_11")).getChildren().get(2);
        scene3D = new Scene3D(view3d.getId());
        view3d.getChildren().add(scene3D.getScene());*/
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
        if(scene3D != null && pane.getChildren().size() > 2)
        {
            Pane view3d = (Pane) pane.getChildren().get(2);
            view3d.getChildren().remove(scene3D);
            scene3D.destroy();
        }
        pane1.setVisible(true);
        if(pane1.getChildren().size() > 2) {
            Pane view3d = (Pane) pane1.getChildren().get(2);
            if(scene3D == null)
                scene3D = new Scene3D(view3d.getId());
            else
                scene3D.undestroy(view3d.getId());
            view3d.getChildren().add(scene3D.getScene());
        }
    }
}
