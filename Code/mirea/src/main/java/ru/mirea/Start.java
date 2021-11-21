package ru.mirea;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.SceneAntialiasing;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.util.Map;

import lombok.extern.slf4j.Slf4j;
import ru.mirea.Controllers.ProjController;
import ru.mirea.Controllers.StartController;
import ru.mirea.ThreeD.Scene3D;

@Slf4j
public class Start extends Application {

    public static Pane root;

    private static MediaView medWiu;

    public static Map<String, Object> roots;

    private static Scene scene;

    private static FXMLLoader loader;

    public static Stage primStage;

    public static String usename;

    @Override
    public void start(Stage primaryStage) throws IOException {
        primStage = primaryStage;
        primStage.getIcons().add(new Image(getClass().getResourceAsStream("/img/ico.png")));
        primStage.setTitle("Комплектующие для ПК");
        primStage.setResizable(false);
        //show_start();
        //close_start();
        show_project();
    }

    public void show_start() throws IOException {
        loader = new FXMLLoader();
        URL xmlUrl = getClass().getResource("/fxml/start.fxml");
        loader.setLocation(xmlUrl);
        root = loader.load();
        roots = loader.getNamespace();
        scene = new Scene(root,1280,720, true, SceneAntialiasing.DISABLED);
        primStage.setScene(scene);
        primStage.show();
        start_spring();
    }

    public void start_spring()
    {
        new StartSpring(new SetCtx(this)).start();
    }

    public void starts() {
        ((StartController)loader.getController()).init();
        start_vid();
    }

    public static void close_start()
    {
        primStage.close();
    }

    public static void show_project() throws IOException {
        loader = new FXMLLoader();

        URL xmlUrl = Start.class.getResource("/fxml/project.fxml");
        loader.setLocation(xmlUrl);
        root = loader.load();
        scene = new Scene(root,1280,720, true, SceneAntialiasing.DISABLED);
        primStage.setScene(scene);
        primStage.show();
        roots = loader.getNamespace();
        primStage.setOnCloseRequest(event -> System.exit(0));
        ((ProjController)loader.getController()).init();
    }

    public static void start_vid()
    {
        MediaPlayer player = new MediaPlayer( new Media(Start.class.getResource("/video/background.mp4").toExternalForm()));
        medWiu = (MediaView) roots.get("medWiu");
        medWiu.setMediaPlayer(player);
        player.setCycleCount(MediaPlayer.INDEFINITE);
        player.play();
    }
}
