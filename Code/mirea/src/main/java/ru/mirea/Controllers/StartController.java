package ru.mirea.Controllers;

import javafx.animation.RotateTransition;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.transform.Rotate;
import javafx.util.Duration;
import ru.mirea.MireaApplication;
import ru.mirea.Start;
import ru.mirea.data.User;
import ru.mirea.data.UsersImpl;

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
    private Pane nevlog;

    @FXML
    private Pane ernull;

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
    private Pane gen;

    @FXML
    private Label sgerpar;

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

    private final ArrayList<String> list_nonlat = new ArrayList<>();
    
    private final ArrayList<String> list_null = new ArrayList<>();

    private RotateTransition rt_a;

    private RotateTransition rt_r;

    private UsersImpl usersImpl;

    private int ico = 0;

    public void init() {
        zagr.setVisible(false);
        prilozh.setVisible(true);
        Platform.runLater(new Runnable()
        {
            @Override
            public void run()
            {
                for(Node n : Start.root.getChildrenUnmodifiable())
                    n.requestFocus();
            }
        });
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
        gen.setVisible(false);
    }

    public void onreg()
    {
        if(getstat(r_log) && getstat(r_par) && getstat(r_conf_par))
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
            usersImpl.add(user);
            toauth();
        }
    }

    public void ranpar()
    {
        String password = "";
        String symbols = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        for (int i = 0; i < 15; i++){
            password += symbols.charAt((int)Math.floor(Math.random() * symbols.length()));
        }
        r_par.setText(password);
        r_conf_par.setText(password);
        sgerpar.setText(password);
        gen.setVisible(true);
        Clipboard clipboard = Clipboard.getSystemClipboard();
        ClipboardContent content = new ClipboardContent();
        content.putString(password);
        clipboard.setContent(content);
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

    public void onauth() throws IOException {
        if(getstat(a_log) && getstat(a_par))
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

    private boolean getstat(TextField text)
    {
        if(!list_null.contains(text.getId()) && !list_nonlat.contains(text.getId()))
        {
            if(text.getText().isEmpty()) {
                if (!list_null.contains(text.getId())) list_null.add(text.getId());
                ernull.setVisible(!list_null.isEmpty());
                return false;
            }
            return true;
        }
        return false;
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
