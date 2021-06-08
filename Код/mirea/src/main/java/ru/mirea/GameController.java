package ru.mirea;

import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;

import java.io.IOException;
import java.net.URL;

public class GameController {

    private final Image n = new Image(getClass().getResourceAsStream("/img/nolik.png"));
    private final Image k = new Image(getClass().getResourceAsStream("/img/krest.png"));
    boolean bob = false;
    boolean nolik = false;
    boolean krest = false;
    Scene scene;
    int vs = 0;
    String [][] field = new String[4][4];
    VBox root;

    public void start(MouseEvent event) {
        Button btn = (Button)event.getSource();
        scene = btn.getScene();
        root = (VBox) scene.getRoot();
        root.getChildren().remove(btn);
        SplitPane pane = (SplitPane)getEl("splitter", root.getChildren());
        pane.setVisible(true);
        String nolS = ((Text)getEl("nolikS", root.getChildren())).getText();
        String kreS = ((Text)getEl("krestS", root.getChildren())).getText();
        AnchorPane parent = (AnchorPane) getEl("Content", pane.getItems());
        Label nol = (Label)getEl("nolik", parent.getChildren());
        nol.setText(nol.getText() + nolS);
        Label kre = (Label)getEl("krest", parent.getChildren());
        kre.setText(kre.getText() + kreS);
    }

    private int[] getNums(String[] id_s)
    {
        return new int[]{Integer.parseInt(id_s[0]) - 1, Integer.parseInt(id_s[1]) - 1};
    }

    public void b(MouseEvent event) throws IOException {
        ImageView view = (ImageView)event.getSource();
        view.setImage(bob ? n : k);
        scene = view.getScene();
        root = (VBox) scene.getRoot();
        SplitPane pane = (SplitPane)getEl("splitter", root.getChildren());
        AnchorPane parent = (AnchorPane) getEl("Content", pane.getItems());
        Line kr = (Line) getEl("krA", parent.getChildren());
        Line no = (Line) getEl("noA", parent.getChildren());
        view.setOnMouseClicked(null);
        int[] id = getNums(view.getId().split(" "));
        field[id[0]][id[1]] = bob ? "n" : "k";
        vs++;
        ch_fin();
        bob = !bob;
        kr.setVisible(!bob);
        no.setVisible(bob);
    }

    private void hasHDfin()
    {
        int nd = 0, kd = 0;
        for(int i = 0; i < 4; i++) {
            int n = 0, k = 0;
            for (int j = 0; j < 4; j++) {
                if(field[i][j] == null) continue;
                switch (field[i][j])
                {
                    case "n":
                        n++;
                        if(i == j)
                            nd++;
                        break;
                    case "k":
                        k++;
                        if(i == j)
                            kd++;
                }
            }
            if(n == 4)
            {
                nolik = true;
                return;
            }
            if(k == 4)
            {
                krest = true;
                return;
            }
        }
        if(nd == 4)
        {
            nolik = true;
            return;
        }
        if(kd == 4)
            krest = true;
    }

    private void hasVDfin()
    {
        int nd = 0, kd = 0;
        for(int j = 0; j < 4; j++) {
            int n = 0, k = 0;
            for (int i = 0; i < 4; i++) {
                if(field[i][j] == null) continue;
                switch (field[i][j])
                {
                    case "n":
                        n++;
                        if(i + j == 3)
                            nd++;
                        break;
                    case "k":
                        k++;
                        if(i + j == 3)
                            kd++;
                }
            }
            if(n == 4)
            {
                nolik = true;
                return;
            }
            if(k == 4)
            {
                krest = true;
                return;
            }
        }
        if(nd == 4)
        {
            nolik = true;
            return;
        }
        if(kd == 4)
            krest = true;
    }

    private void messag(String mes) throws IOException {
        Alert alert = new Alert(Alert.AlertType.INFORMATION,mes);
        alert.setTitle("Результат");
        alert.setHeaderText(null);
        alert.showAndWait();
        root = null;
        FXMLLoader loader = new FXMLLoader();
        URL xmlUrl = getClass().getResource("fxml/game.fxml");
        loader.setLocation(xmlUrl);
        root = loader.load();
        VBox rt = (VBox) scene.getRoot();
        Text nol = (Text) getEl("nolikS", rt.getChildren());
        Text kre = (Text) getEl("krestS", rt.getChildren());
        if(nolik)
            nol.setText(((int)Integer.parseInt(nol.getText()) + 1) + "");
        if(krest)
            kre.setText(((int)Integer.parseInt(kre.getText()) + 1) + "");
        root.getChildren().addAll(nol, kre);
        scene.setRoot(root);
    }

    private Node getEl(String el, ObservableList<Node> nodes)
    {
        for(Node d : nodes)
        {
            if(d.getId() != null && d.getId().equals(el))
                return d;
        }
        return null;
    }

    private void fin() throws IOException {
        messag("Победа присуждается " + (nolik ? "нолику" : "крестику") + "!");
    }

    private void ch_fin() throws IOException {
        hasHDfin();
        if (nolik || krest)
        {
            fin();
            return;
        }
        hasVDfin();
        if (nolik || krest)
        {
            fin();
            return;
        }
        if(vs == 16)
        {
            messag("Ничья!");
        }
    }
}