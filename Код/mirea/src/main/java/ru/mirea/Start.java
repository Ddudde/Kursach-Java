package ru.mirea;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.ScrollBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.util.Map;

public class Start extends Application {

    private StackPane root;

    private MediaView medWiu;

    private Map<String, Object> roots;

    private Scene scene;

    @Override
    public void start(Stage primaryStage) throws IOException {
        primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("/img/ico.png")));

        FXMLLoader loader = new FXMLLoader();
        URL xmlUrl = getClass().getResource("/fxml/start.fxml");
        loader.setLocation(xmlUrl);
        root = loader.load();

        scene = new Scene(root,1280,720);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Комплектующие для ПК");
        primaryStage.setResizable(false);
        primaryStage.show();

        roots = loader.getNamespace();
        for(Node n : root.getChildrenUnmodifiable())
            n.requestFocus();
        init_sc();
    }

    public void init_sc()
    {
        start_vid();
    }

    public void start_vid()
    {
        MediaPlayer player = new MediaPlayer( new Media(getClass().getResource("/video/background.mp4").toExternalForm()));
        medWiu = (MediaView) roots.get("medWiu");
        medWiu.setMediaPlayer(player);
        player.setAutoPlay(true);
        player.setCycleCount(MediaPlayer.INDEFINITE);
    }
}
