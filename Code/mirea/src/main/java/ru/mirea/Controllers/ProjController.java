package ru.mirea.Controllers;

import javafx.animation.*;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Glow;
import javafx.scene.effect.InnerShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.*;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import ru.mirea.MireaApplication;
import ru.mirea.Start;
import ru.mirea.ThreeD.Scene3D;
import ru.mirea.data.User;
import ru.mirea.data.UsersImpl;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.regex.Pattern;

public class ProjController extends ModelController{

    private int id = 1;

    private UsersImpl usersImpl = (UsersImpl) MireaApplication.ctx.getBean("usersImpl");

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

    @FXML
    private Pane caps;

    @FXML
    private Pane p_edit;

    @FXML
    private TextField log;

    @FXML
    private PasswordField par;

    private User user;

    private Scene3D scene3D;

    @FXML
    private Pane logzan;

    @FXML
    private Label sgerpar;

    private boolean caps_lock = false;

    public void init()
    {
        user = usersImpl.getuser(Start.usename);
        if(user != null)
        {
            usern.setText(Start.usename);
            set_ico();
        }
        caps_lock = Toolkit.getDefaultToolkit().getLockingKeyState(java.awt.event.KeyEvent.VK_CAPS_LOCK);
        set_caps();
        p_edit.getScene().addEventHandler(KeyEvent.KEY_RELEASED, this::caps);
        /*((Pane) Start.roots.get("id_1")).setVisible(false);
        ((Pane) Start.roots.get("id_11")).setVisible(true);
        Pane view3d = (Pane) ((Pane) Start.roots.get("id_11")).getChildren().get(2);
        scene3D = new Scene3D(view3d.getId());
        view3d.getChildren().add(scene3D.getScene());*/
    }

    public void browse_3535() throws URISyntaxException, IOException {
        Desktop d = Desktop.getDesktop();
        d.browse(new URI("tel:+78005553535"));
    }

    public void browse_0088() throws URISyntaxException, IOException {
        Desktop d = Desktop.getDesktop();
        d.browse(new URI("tel:+53535550088"));
    }

    private void set_ico()
    {
        ico = user.getIcons();
        icon.setImage(new Image(getClass().getResourceAsStream("/img/ls-icon" + (ico + 1) + ".png")));
        switch (ico) {
            case 0 -> ra1.setSelected(true);
            case 1 -> ra2.setSelected(true);
            case 2 -> ra3.setSelected(true);
        }
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

    private void caps(KeyEvent keyEvent)
    {
        if(keyEvent.getCode() == KeyCode.CAPS) {
            caps_lock = !caps_lock;
            set_caps();
        }
    }

    private void set_caps()
    {
        caps.setVisible(caps_lock);
    }

    public void toNews()
    {
        glavn.setVisible(false);
        news.setVisible(true);
        cont.setVisible(false);
        edit.setVisible(false);
        ernull.setVisible(false);
        erpat.setVisible(false);
        gen.setVisible(false);
    }

    public void toContact()
    {
        glavn.setVisible(false);
        news.setVisible(false);
        cont.setVisible(true);
        edit.setVisible(false);
        ernull.setVisible(false);
        erpat.setVisible(false);
        gen.setVisible(false);
    }

    public void toMain()
    {
        glavn.setVisible(true);
        news.setVisible(false);
        cont.setVisible(false);
        edit.setVisible(false);
        ernull.setVisible(false);
        erpat.setVisible(false);
        gen.setVisible(false);
    }

    public void toEdit()
    {
        glavn.setVisible(false);
        news.setVisible(false);
        cont.setVisible(false);
        edit.setVisible(true);
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

    public void onUser()
    {
        menu.setVisible(true);
    }

    public void neUser()
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

    private Label getLabel(Object obj)
    {
        if(obj.getClass().getSimpleName().equals("Pane")) {
            Pane pane = (Pane) obj;
            return (Label) pane.getChildren().get(0);
        } else {
            return usern;
        }
    }

    public void onedit()
    {
        boolean stat1 = getstat(log);
        boolean stat2 = getstat(par);
        if(stat1 && stat2)
        {
            if(usersImpl.getuser(log.getText()) != null && !Objects.equals(user.getUsername(), log.getText()))
            {
                logzan.setVisible(true);
                return;
            }
            logzan.setVisible(false);
            Start.usename = log.getText();
            user.setUsername(log.getText());
            user.setPassword(par.getText());
            user.setIcons(ico);
            usersImpl.addorsave(user);
            toMain();
        }
    }

    public void toBegin() throws IOException {
        Start.usename = null;
        Start.close_project();
        Start.show_start();
        Start.starts();
    }

    public void ranpar()
    {
        String password = "";
        String symbols = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        for (int i = 0; i < 15; i++){
            password += symbols.charAt((int)Math.floor(Math.random() * symbols.length()));
        }
        par.setText(password);
        sgerpar.setText(password);
        gen.setVisible(true);
        Clipboard clipboard = Clipboard.getSystemClipboard();
        ClipboardContent content = new ClipboardContent();
        content.putString(password);
        clipboard.setContent(content);
    }
}
