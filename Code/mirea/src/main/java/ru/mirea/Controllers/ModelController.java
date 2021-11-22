package ru.mirea.Controllers;

import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Glow;
import javafx.scene.effect.InnerShadow;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import ru.mirea.Start;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.regex.Pattern;

public class ModelController{

    @FXML
    Pane ernull;

    @FXML
    Pane erpat;

    @FXML
    Pane gen;

    @FXML
    public RadioButton ra1;

    @FXML
    public RadioButton ra2;

    @FXML
    public RadioButton ra3;

    public int ico = 0;

    public final Interpolator inter = Interpolator.EASE_BOTH;

    public final ArrayList<String> list_nonlat = new ArrayList<>();

    public final ArrayList<String> list_null = new ArrayList<>();

    public void rad1()
    {
        ico = 0;
        ra1.setSelected(true);
    }

    public void rad2()
    {
        ico = 1;
        ra2.setSelected(true);
    }

    public void rad3()
    {
        ico = 2;
        ra3.setSelected(true);
    }

    public void textch(KeyEvent keyEvent)
    {
        TextField text = (TextField) keyEvent.getSource();
        if (text.getText().isEmpty())
        {
            if (!list_null.contains(text.getId())) add_null(text.getId());
        } else {
            rem_null(text.getId());
            if (Pattern.matches("[A-Za-z0-9]+", text.getText()))
                rem_nonlat(text.getId());
            else {
                if (!list_nonlat.contains(text.getId())) add_nonlat(text.getId());
            }
            erpat.setVisible(!list_nonlat.isEmpty());
            if(!list_nonlat.isEmpty()) gen.setVisible(false);
        }
        ernull.setVisible(!list_null.isEmpty());
        if(!list_null.isEmpty()) gen.setVisible(false);
    }

    public boolean getstat(TextField text)
    {
        if(!list_null.contains(text.getId()) && !list_nonlat.contains(text.getId()))
        {
            if(text.getText().isEmpty()) {
                add_null(text.getId());
                ernull.setVisible(true);
                gen.setVisible(false);
                return false;
            }
            return true;
        }
        return false;
    }

    private void add_null(String id)
    {
        list_null.add(id);
        TextField text = (TextField) Start.roots.get(id);
        InnerShadow innerShadow = (InnerShadow) text.getEffect();
        DropShadow dropShadow = (DropShadow) innerShadow.getInput();
        List<KeyValue> kv = new ArrayList<>();
        kv.add(new KeyValue(innerShadow.colorProperty(), Color.web("#ff0000",1), inter));
        kv.add(new KeyValue(dropShadow.colorProperty(), Color.web("#ff0000",1), inter));
        played(kv, 100);
    }

    private void rem_null(String id)
    {
        list_null.remove(id);
        TextField text = (TextField) Start.roots.get(id);
        InnerShadow innerShadow = (InnerShadow) text.getEffect();
        DropShadow dropShadow = (DropShadow) innerShadow.getInput();
        List<KeyValue> kv = new ArrayList<>();
        kv.add(new KeyValue(innerShadow.colorProperty(), Color.web("#ff0000",0), inter));
        kv.add(new KeyValue(dropShadow.colorProperty(), Color.web("#ff0000",0), inter));
        played(kv, 100);
    }

    private void add_nonlat(String id)
    {
        list_nonlat.add(id);
        TextField text = (TextField) Start.roots.get(id);
        InnerShadow innerShadow = (InnerShadow) text.getEffect();
        DropShadow dropShadow = (DropShadow) innerShadow.getInput();
        List<KeyValue> kv = new ArrayList<>();
        kv.add(new KeyValue(innerShadow.colorProperty(), Color.web("#ff0000",1), inter));
        kv.add(new KeyValue(dropShadow.colorProperty(), Color.web("#ff0000",1), inter));
        played(kv, 100);
    }

    private void rem_nonlat(String id)
    {
        list_nonlat.remove(id);
        TextField text = (TextField) Start.roots.get(id);
        InnerShadow innerShadow = (InnerShadow) text.getEffect();
        DropShadow dropShadow = (DropShadow) innerShadow.getInput();
        List<KeyValue> kv = new ArrayList<>();
        kv.add(new KeyValue(innerShadow.colorProperty(), Color.web("#ff0000",0), inter));
        kv.add(new KeyValue(dropShadow.colorProperty(), Color.web("#ff0000",0), inter));
        played(kv, 100);
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
        kv.add(new KeyValue(dropShadow.colorProperty(), Color.web("#000",0.5), inter));
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
        kv.add(new KeyValue(dropShadow.colorProperty(), Color.web("#000",1), inter));
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
        kv.add(new KeyValue(innerShadow.colorProperty(), Color.web("#000",1), inter));
        kv.add(new KeyValue(glow.levelProperty(), 0, inter));
        kv.add(new KeyValue(dropShadow.colorProperty(), Color.web("#000",0.5), inter));
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
        kv.add(new KeyValue(innerShadow.colorProperty(), Color.web("#fff",0.25), inter));
        kv.add(new KeyValue(glow.levelProperty(), 0, inter));
        kv.add(new KeyValue(dropShadow.colorProperty(), Color.web("#000",1), inter));
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

    public void handle1(ActionEvent event)
    {
        Timeline time1 = (Timeline) event.getSource();
        time1.stop();
        time1.getKeyFrames().remove(0, time1.getKeyFrames().size());
        time1 = null;
    }

    public void played(Collection<KeyValue> kv, double time)
    {
        Timeline time1 = new Timeline();
        for(KeyValue key : kv)
        {
            time1.getKeyFrames().add(new KeyFrame(Duration.millis(time), key));
        }
        time1.setOnFinished(this::handle1);
        time1.play();
    }

    public void browse_git() throws URISyntaxException, IOException {
        Desktop d = Desktop.getDesktop();
        d.browse(new URI("https://github.com/Ddudde/Kursach-Java"));
    }
}