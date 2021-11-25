package ru.mirea.Controllers;

import javafx.animation.KeyValue;
import javafx.animation.RotateTransition;
import javafx.application.Platform;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.PasswordField;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Glow;
import javafx.scene.effect.InnerShadow;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.transform.Rotate;
import javafx.util.Duration;
import ru.mirea.MireaApplication;
import ru.mirea.Start;
import ru.mirea.data.User;
import ru.mirea.data.UsersImpl;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class StartController extends ModelController{

    @FXML
    private ScrollPane scrpan;

    @FXML
    private Pane up;

    @FXML
    private Pane nevlog;

    @FXML
    private Pane auth;

    @FXML
    private Pane reg;

    @FXML
    private Pane zagr;

    @FXML
    private Pane logzan;

    @FXML
    private Pane povpar;

    @FXML
    private StackPane prilozh;

    @FXML
    private TextField a_log;

    @FXML
    private PasswordField a_par;

    @FXML
    private TextField r_log;

    @FXML
    private PasswordField r_par;

    @FXML
    private PasswordField r_conf_par;

    @FXML
    private Pane l_caps;

    @FXML
    private Pane r_caps;

    private boolean caps_lock = false;

    private boolean login = true;

    private RotateTransition rt_a;

    private RotateTransition rt_r;

    private UsersImpl usersImpl;

    public void init() {
        zagr.setVisible(false);
        prilozh.setVisible(true);
        Platform.runLater(this::run);
        down();
        up();
        usersImpl = (UsersImpl) MireaApplication.ctx.getBean("usersImpl");
        caps_lock = Toolkit.getDefaultToolkit().getLockingKeyState(java.awt.event.KeyEvent.VK_CAPS_LOCK);
        set_caps();
        rt_a = new RotateTransition(Duration.millis(1000), auth);
        rt_r = new RotateTransition(Duration.millis(1000), reg);
        rt_a.setAxis(Rotate.X_AXIS);
        rt_r.setAxis(Rotate.X_AXIS);
        auth.getScene().addEventHandler(KeyEvent.KEY_RELEASED, this::caps);
        scrpan.vvalueProperty().addListener(this::changed);
    }

    private void changed(ObservableValue<? extends Number> ov, Number old_val, Number new_val) {
        if(new_val.floatValue() >= 0.75 && !up.isVisible()) {
            up.setVisible(true);
            Start.player.stop();
        }
        if(new_val.floatValue() < 0.75 && up.isVisible()) {
            up.setVisible(false);
            Start.player.play();
        }
    }

    private void run()
    {
        for(Node n : Start.root.getChildrenUnmodifiable())
            n.requestFocus();
    }

    public void onEnterR(KeyEvent keyEvent)
    {
        if(keyEvent.getCode() == KeyCode.ENTER) onreg();
    }

    public void onEnterA(KeyEvent keyEvent) throws IOException {
        if(keyEvent.getCode() == KeyCode.ENTER) onauth();
    }

    public void toreg()
    {
        destr_auth();
        login = false;
        reg.setVisible(true);
        rotate_this(rt_a, rt_r);
        erpat.setVisible(false);
        ernull.setVisible(false);
        if(erpat.getParent() != null) ((Pane)erpat.getParent()).getChildren().remove(erpat);
        if(ernull.getParent() != null) ((Pane)ernull.getParent()).getChildren().remove(ernull);
    }

    public void toauth()
    {
        destr_reg();
        login = true;
        auth.setVisible(true);
        rotate_this(rt_r, rt_a);
        erpat.setVisible(false);
        ernull.setVisible(false);
        if(erpat.getParent() != null) ((Pane)erpat.getParent()).getChildren().remove(erpat);
        if(ernull.getParent() != null) ((Pane)ernull.getParent()).getChildren().remove(ernull);
    }

    public void onreg()
    {
        boolean stat1 = getstat(r_log);
        boolean stat2 = getstat(r_par);
        boolean stat3 = getstat(r_conf_par);
        if(stat1 && stat2 && stat3)
        {
            if(usersImpl.getuser(r_log.getText()) != null)
            {
                logzan.setVisible(true);
                return;
            }
            logzan.setVisible(false);
            if(!r_par.getText().equals(r_conf_par.getText()))
            {
                povpar.setVisible(true);
                return;
            }
            povpar.setVisible(false);
            User user = new User();
            user.setUsername(r_log.getText());
            user.setPassword(r_par.getText());
            user.setIcons(ico);
            user.setSohr(1);
            usersImpl.addorsave(user);
            toauth();
        }
    }

    public void ranpar()
    {
        ranpar(new PasswordField[]{r_par, r_conf_par});
    }

    public void onauth() throws IOException {
        boolean stat1 = getstat(a_log);
        boolean stat2 = getstat(a_par);
        if(stat1 && stat2)
        {
            boolean chek = usersImpl.checkpar(a_log.getText(), a_par.getText());
            if(!chek)
            {
                nevlog.setVisible(true);
            }else{
                Start.usename = a_log.getText();
                Start.close_start();
                Start.show_project();
            }
        }
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

    private void caps(KeyEvent keyEvent)
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

    public void onUp(MouseEvent mouseEvent)
    {
        Pane pane = (Pane) mouseEvent.getSource();
        Glow glow = (Glow) pane.getEffect();
        List<KeyValue> kv = new ArrayList<>();
        kv.add(new KeyValue(glow.levelProperty(), 0.75, inter));
        played(kv, 100);
    }

    public void neUp(MouseEvent mouseEvent)
    {
        Pane pane = (Pane) mouseEvent.getSource();
        Glow glow = (Glow) pane.getEffect();
        List<KeyValue> kv = new ArrayList<>();
        kv.add(new KeyValue(glow.levelProperty(), 0, inter));
        played(kv, 100);
    }

    public void onPU(MouseEvent mouseEvent)
    {
        Pane pane = (Pane) mouseEvent.getSource();
        Glow glow = (Glow) pane.getEffect();
        DropShadow dropShadow = (DropShadow) glow.getInput();
        InnerShadow innerShadow = (InnerShadow) dropShadow.getInput();
        List<KeyValue> kv = new ArrayList<>();
        kv.add(new KeyValue(glow.levelProperty(), 1, inter));
        kv.add(new KeyValue(dropShadow.colorProperty(), Color.web("#000",0), inter));
        kv.add(new KeyValue(innerShadow.colorProperty(), Color.web("#000",1.0), inter));
        kv.add(new KeyValue(innerShadow.chokeProperty(), 0.3, inter));
        played(kv, 100);
    }

    public void nePU(MouseEvent mouseEvent)
    {
        Pane pane = (Pane) mouseEvent.getSource();
        Glow glow = (Glow) pane.getEffect();
        DropShadow dropShadow = (DropShadow) glow.getInput();
        InnerShadow innerShadow = (InnerShadow) dropShadow.getInput();
        List<KeyValue> kv = new ArrayList<>();
        kv.add(new KeyValue(glow.levelProperty(), 0, inter));
        kv.add(new KeyValue(dropShadow.colorProperty(), Color.web("#000",1), inter));
        kv.add(new KeyValue(innerShadow.colorProperty(), Color.web("#000",0), inter));
        kv.add(new KeyValue(innerShadow.chokeProperty(), 0, inter));
        played(kv, 100);
    }

    public void onGo(MouseEvent mouseEvent)
    {
        ImageView im = (ImageView) mouseEvent.getSource();
        Glow glow = (Glow) im.getEffect();
        List<KeyValue> kv = new ArrayList<>();
        kv.add(new KeyValue(glow.levelProperty(), 0.75, inter));
        played(kv, 100);
    }

    public void neGo(MouseEvent mouseEvent)
    {
        ImageView im = (ImageView) mouseEvent.getSource();
        Glow glow = (Glow) im.getEffect();
        List<KeyValue> kv = new ArrayList<>();
        kv.add(new KeyValue(glow.levelProperty(), 0, inter));
        played(kv, 100);
    }

    public void onPG(MouseEvent mouseEvent)
    {
        ImageView im = (ImageView) mouseEvent.getSource();
        Glow glow = (Glow) im.getEffect();
        DropShadow dropShadow = (DropShadow) glow.getInput();
        InnerShadow innerShadow = (InnerShadow) dropShadow.getInput();
        List<KeyValue> kv = new ArrayList<>();
        kv.add(new KeyValue(glow.levelProperty(), 1, inter));
        kv.add(new KeyValue(dropShadow.colorProperty(), Color.web("#000",0), inter));
        kv.add(new KeyValue(innerShadow.colorProperty(), Color.web("#000",1.0), inter));
        kv.add(new KeyValue(innerShadow.chokeProperty(), 0.3, inter));
        played(kv, 100);
    }

    public void nePG(MouseEvent mouseEvent)
    {
        ImageView im = (ImageView) mouseEvent.getSource();
        Glow glow = (Glow) im.getEffect();
        DropShadow dropShadow = (DropShadow) glow.getInput();
        InnerShadow innerShadow = (InnerShadow) dropShadow.getInput();
        List<KeyValue> kv = new ArrayList<>();
        kv.add(new KeyValue(glow.levelProperty(), 0, inter));
        kv.add(new KeyValue(dropShadow.colorProperty(), Color.web("#000",0.75), inter));
        kv.add(new KeyValue(innerShadow.colorProperty(), Color.web("#000",0), inter));
        kv.add(new KeyValue(innerShadow.chokeProperty(), 0, inter));
        played(kv, 100);
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
