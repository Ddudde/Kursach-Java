package ru.mirea.Controllers;

import javafx.animation.*;
import javafx.beans.property.DoubleProperty;
import javafx.beans.value.WritableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Glow;
import javafx.scene.effect.InnerShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import ru.mirea.Start;
import ru.mirea.ThreeD.Scene3D;
import ru.mirea.data.User;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Predicate;
import java.util.regex.Pattern;

public class ProjController {

    private int id = 1;

    //private UsersImpl usersImpl = (UsersImpl) MireaApplication.ctx.getBean("usersImpl");

    @FXML
    private Label usern;

    @FXML
    private ImageView icon;

    @FXML
    private Pane glavn;

    @FXML
    private Pane news;

    @FXML
    private Pane cont;

    @FXML
    private VBox menu;

    @FXML
    private Pane edit;

    private User user;

    private Scene3D scene3D;

    private final Interpolator inter = Interpolator.EASE_BOTH;

    private int ico = 0;

    public void init()
    {
        //user = usersImpl.getuser(Start.usename);
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

    public void browse_3535() throws URISyntaxException, IOException {
        Desktop d = Desktop.getDesktop();
        d.browse(new URI("tel:+78005553535"));
    }

    public void browse_0088() throws URISyntaxException, IOException {
        Desktop d = Desktop.getDesktop();
        d.browse(new URI("tel:+53535550088"));
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

    public void toNews()
    {
        glavn.setVisible(false);
        news.setVisible(true);
        cont.setVisible(false);
        edit.setVisible(false);
    }

    public void toContact()
    {
        glavn.setVisible(false);
        news.setVisible(false);
        cont.setVisible(true);
        edit.setVisible(false);
    }

    public void toMain()
    {
        glavn.setVisible(true);
        news.setVisible(false);
        cont.setVisible(false);
        edit.setVisible(false);
    }

    public void toEdit()
    {
        glavn.setVisible(false);
        news.setVisible(false);
        cont.setVisible(false);
        edit.setVisible(true);
    }

    public void onBut(MouseEvent mouseEvent)
    {
        Button but = (Button) mouseEvent.getSource();
        InnerShadow innerShadow = (InnerShadow) but.getEffect();
        Glow glow = (Glow) innerShadow.getInput();
        Label label = (Label) but.getChildrenUnmodifiable().get(0);
        DropShadow dropShadow = (DropShadow) label.getEffect();
        List<KeyValue> kv = new ArrayList<>();
        kv.add(new KeyValue(innerShadow.offsetYProperty(), 0, inter));
        kv.add(new KeyValue(glow.levelProperty(), 0.3, inter));
        kv.add(new KeyValue(dropShadow.colorProperty(), Color.web("#000000",0.5), inter));
        played(kv, 100);
    }

    public void neBut(MouseEvent mouseEvent)
    {
        Button but = (Button) mouseEvent.getSource();
        InnerShadow innerShadow = (InnerShadow) but.getEffect();
        Glow glow = (Glow) innerShadow.getInput();
        Label label = (Label) but.getChildrenUnmodifiable().get(0);
        DropShadow dropShadow = (DropShadow) label.getEffect();
        List<KeyValue> kv = new ArrayList<>();
        kv.add(new KeyValue(innerShadow.offsetYProperty(), -5, inter));
        kv.add(new KeyValue(glow.levelProperty(), 0, inter));
        kv.add(new KeyValue(dropShadow.colorProperty(), Color.web("#000000",1), inter));
        played(kv, 100);
    }

    public void onPRB(MouseEvent mouseEvent)
    {
        Button but = (Button) mouseEvent.getSource();
        InnerShadow innerShadow = (InnerShadow) but.getEffect();
        Glow glow = (Glow) innerShadow.getInput();
        Label label = (Label) but.getChildrenUnmodifiable().get(0);
        DropShadow dropShadow = (DropShadow) label.getEffect();
        List<KeyValue> kv = new ArrayList<>();
        kv.add(new KeyValue(innerShadow.offsetYProperty(), 0, inter));
        kv.add(new KeyValue(innerShadow.widthProperty(), 60, inter));
        kv.add(new KeyValue(innerShadow.heightProperty(), 60, inter));
        kv.add(new KeyValue(innerShadow.colorProperty(), Color.web("#000000",1), inter));
        kv.add(new KeyValue(glow.levelProperty(), 0, inter));
        kv.add(new KeyValue(dropShadow.colorProperty(), Color.web("#000000",0.5), inter));
        played(kv, 100);
    }

    public void nePRB(MouseEvent mouseEvent)
    {
        Button but = (Button) mouseEvent.getSource();
        InnerShadow innerShadow = (InnerShadow) but.getEffect();
        Glow glow = (Glow) innerShadow.getInput();
        Label label = (Label) but.getChildrenUnmodifiable().get(0);
        DropShadow dropShadow = (DropShadow) label.getEffect();
        List<KeyValue> kv = new ArrayList<>();
        kv.add(new KeyValue(innerShadow.offsetYProperty(), -5, inter));
        kv.add(new KeyValue(innerShadow.widthProperty(), 0, inter));
        kv.add(new KeyValue(innerShadow.heightProperty(), 1, inter));
        kv.add(new KeyValue(innerShadow.colorProperty(), Color.web("#ffffff",0.25), inter));
        kv.add(new KeyValue(glow.levelProperty(), 0, inter));
        kv.add(new KeyValue(dropShadow.colorProperty(), Color.web("#000000",1), inter));
        played(kv, 100);
    }

    public void onNav(MouseEvent mouseEvent)
    {
        Label label = getLabel(mouseEvent.getSource());
        Glow glow = (Glow) label.getEffect();
        List<KeyValue> kv = new ArrayList<>();
        kv.add(new KeyValue(glow.levelProperty(), 1.0, inter));
        played(kv, 100);
    }

    public void neNav(MouseEvent mouseEvent)
    {
        Label label = getLabel(mouseEvent.getSource());
        Glow glow = (Glow) label.getEffect();
        List<KeyValue> kv = new ArrayList<>();
        kv.add(new KeyValue(glow.levelProperty(), 0, inter));
        played(kv, 100);
    }

    public void onUser(MouseEvent mouseEvent)
    {
        menu.setVisible(true);
    }

    public void neUser(MouseEvent mouseEvent)
    {
        menu.setVisible(false);
    }

    public void onUS(MouseEvent mouseEvent)
    {
        menu.setVisible(true);
        onNav(mouseEvent);
    }

    public void onPN(MouseEvent mouseEvent)
    {
        Label label = getLabel(mouseEvent.getSource());
        InnerShadow innerShadow = (InnerShadow) ((DropShadow)((Glow)label.getEffect()).getInput()).getInput();
        List<KeyValue> kv = new ArrayList<>();
        kv.add(new KeyValue(innerShadow.chokeProperty(), 0.3, inter));
        played(kv, 100);
    }

    public void nePN(MouseEvent mouseEvent)
    {
        Label label = getLabel(mouseEvent.getSource());
        InnerShadow innerShadow = (InnerShadow) ((DropShadow)((Glow)label.getEffect()).getInput()).getInput();
        List<KeyValue> kv = new ArrayList<>();
        kv.add(new KeyValue(innerShadow.chokeProperty(), 0, inter));
        played(kv, 100);
    }

    public void onLink(MouseEvent mouseEvent)
    {
        Hyperlink label = (Hyperlink) mouseEvent.getSource();
        Glow glow = (Glow) label.getEffect();
        List<KeyValue> kv = new ArrayList<>();
        kv.add(new KeyValue(glow.levelProperty(), 1.0, inter));
        played(kv, 100);
    }

    public void neLink(MouseEvent mouseEvent)
    {
        Hyperlink label = (Hyperlink) mouseEvent.getSource();
        Glow glow = (Glow) label.getEffect();
        List<KeyValue> kv = new ArrayList<>();
        kv.add(new KeyValue(glow.levelProperty(), 0, inter));
        played(kv, 100);
    }

    public void onPL(MouseEvent mouseEvent)
    {
        Hyperlink label = (Hyperlink) mouseEvent.getSource();
        Glow glow = (Glow) label.getEffect();
        InnerShadow innerShadow = (InnerShadow) ((DropShadow)glow.getInput()).getInput();
        List<KeyValue> kv = new ArrayList<>();
        kv.add(new KeyValue(glow.levelProperty(), 0, inter));
        kv.add(new KeyValue(innerShadow.chokeProperty(), 0.3, inter));
        played(kv, 100);
    }

    public void nePL(MouseEvent mouseEvent)
    {
        Hyperlink label = (Hyperlink) mouseEvent.getSource();
        InnerShadow innerShadow = (InnerShadow) ((DropShadow)((Glow)label.getEffect()).getInput()).getInput();
        List<KeyValue> kv = new ArrayList<>();
        kv.add(new KeyValue(innerShadow.chokeProperty(), 0, inter));
        played(kv, 100);
    }

    private Label getLabel(Object obj)
    {
        if(obj.getClass().getSimpleName().equals("Pane")) {
            Pane pane = (Pane) obj;
            return (Label) pane.getChildren().get(0);
        } else {
            return usern;
        }
    }

    public void handle1(ActionEvent event)
    {
        Timeline time1 = (Timeline) event.getSource();
        time1.stop();
        time1.getKeyFrames().remove(0, time1.getKeyFrames().size());
        time1 = null;
    }

    private void played(Collection<KeyValue> kv, double time)
    {
        Timeline time1 = new Timeline();
        for(KeyValue key : kv)
        {
            time1.getKeyFrames().add(new KeyFrame(Duration.millis(time), key));
        }
        time1.setOnFinished(this::handle1);
        time1.play();
    }

    public void textch(KeyEvent keyEvent)
    {

    }

    public void onreg()
    {

    }

    public void rad1()
    {
        ico = 0;
    }

    public void rad2()
    {
        ico = 1;
    }

    public void rad3()
    {
        ico = 2;
    }

    public void ranpar()
    {

    }
}
