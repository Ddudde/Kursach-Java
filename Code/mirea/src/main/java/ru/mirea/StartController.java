package ru.mirea;

import javafx.animation.RotateTransition;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.transform.Rotate;
import javafx.util.Duration;

import java.awt.Desktop;
import java.awt.Toolkit;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.regex.Pattern;

public class StartController {

    @FXML
    private ScrollPane scrpan;

    @FXML
    private Pane erpat;

    @FXML
    private Pane ernull;

    @FXML
    private Pane auth;

    @FXML
    private Pane reg;

    @FXML
    private Pane zagr;

    @FXML
    private StackPane prilozh;

    @FXML
    private TextField a_log;

    @FXML
    private TextField a_par;

    @FXML
    private TextField r_log;

    @FXML
    private TextField r_par;

    @FXML
    private TextField r_conf_par;

    @FXML
    private Pane l_caps;

    @FXML
    private Pane r_caps;

    private boolean caps_lock = false;

    private boolean login = true;

    private final ArrayList<String> list_nonlat = new ArrayList<>();
    
    private final ArrayList<String> list_null = new ArrayList<>();

    private RotateTransition rt_a;

    private RotateTransition rt_r;

    public void init() {
        zagr.setVisible(false);
        prilozh.setVisible(true);
        caps_lock = Toolkit.getDefaultToolkit().getLockingKeyState(java.awt.event.KeyEvent.VK_CAPS_LOCK);
        set_caps();
        rt_a = new RotateTransition(Duration.millis(1000), auth);
        rt_r = new RotateTransition(Duration.millis(1000), reg);
        rt_a.setAxis(Rotate.X_AXIS);
        rt_r.setAxis(Rotate.X_AXIS);
        auth.getScene().addEventHandler(KeyEvent.KEY_RELEASED, this::caps);
    }

    public void browse_git() throws URISyntaxException, IOException {
        Desktop d = Desktop.getDesktop();
        d.browse(new URI("https://github.com/Ddudde/Kursach-Java"));
    }

    public void toreg()
    {
        login = false;
        reg.setVisible(true);
        rotate_this(rt_a, rt_r);
        destr_auth();
        erpat.setVisible(false);
        ernull.setVisible(false);
    }

    public void toauth()
    {
        login = true;
        auth.setVisible(true);
        rotate_this(rt_r, rt_a);
        destr_reg();
        erpat.setVisible(false);
        ernull.setVisible(false);
    }

    private void rotate_this(RotateTransition rt1, RotateTransition rt2)
    {
        rt1.setByAngle(0);
        rt1.setToAngle(90);
        rt2.setByAngle(270);
        rt2.setToAngle(360);
        rt1.play();
        rt2.play();
        rt2.onFinishedProperty().setValue(this::chvis);
    }

    private void chvis(ActionEvent actionEvent) {
        RotateTransition rt = (RotateTransition) actionEvent.getSource();
        rt.onFinishedProperty().setValue(null);
        setvis();
    }

    private void destr_auth()
    {
        destr_field(a_log);
        destr_field(a_par);
    }

    private void destr_reg()
    {
        destr_field(r_log);
        destr_field(r_par);
        destr_field(r_conf_par);
    }

    private void destr_field(TextField text)
    {
        text.clear();
        list_nonlat.remove(text.getId());
        list_null.remove(text.getId());
    }

    private void setvis()
    {
        auth.setVisible(login);
        reg.setVisible(!login);
    }

    public void textch(KeyEvent keyEvent)
    {
        TextField text = (TextField) keyEvent.getSource();
        if (text.getText().isEmpty())
        {
            if (!list_null.contains(text.getId())) list_null.add(text.getId());
        } else {
            list_null.remove(text.getId());
            if (Pattern.matches("[A-Za-z0-9]+", text.getText()))
                list_nonlat.remove(text.getId());
            else {
                if (!list_nonlat.contains(text.getId())) list_nonlat.add(text.getId());
            }
            erpat.setVisible(!list_nonlat.isEmpty());
        }
        ernull.setVisible(!list_null.isEmpty());
    }

    public void caps(KeyEvent keyEvent)
    {
        if(keyEvent.getCode() == KeyCode.CAPS) {
            caps_lock = !caps_lock;
            set_caps();
        }
    }

    private void set_caps()
    {
        r_caps.setVisible(caps_lock);
        l_caps.setVisible(caps_lock);
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
