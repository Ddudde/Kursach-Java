package ru.mirea;

import com.jme3.input.MouseInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.AnalogListener;
import com.jme3.input.controls.MouseAxisTrigger;
import com.jme3.input.controls.MouseButtonTrigger;
import com.jme3.light.AmbientLight;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.scene.CameraNode;
import com.jme3.scene.Spatial;
import com.jme3.scene.control.CameraControl;
import com.jme3.system.AppSettings;
import com.jme3.system.JmeSystem;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
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
import com.jme3x.jfx.injfx.JmeToJFXApplication;
import com.jme3x.jfx.injfx.JmeToJFXIntegrator;

public class Start extends Application {

    private StackPane root;

    private MediaView medWiu;

    private Map<String, Object> roots;

    private Scene scene;

    private Stage primStage;

    @Override
    public void start(Stage primaryStage) throws IOException {
        primStage = primaryStage;
        primStage.getIcons().add(new Image(getClass().getResourceAsStream("/img/ico.png")));
        primStage.setTitle("Комплектующие для ПК");
        primStage.setResizable(false);
        show_start();
        //close_start();
        //show_project();
    }

    public void show_start() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        URL xmlUrl = getClass().getResource("/fxml/start.fxml");
        loader.setLocation(xmlUrl);
        root = loader.load();
        scene = new Scene(root,1280,720);
        primStage.setScene(scene);
        primStage.show();
        roots = loader.getNamespace();
        for(Node n : root.getChildrenUnmodifiable())
            n.requestFocus();
        init_sc();
    }

    public void close_start()
    {
        primStage.close();
    }

    public void show_project() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        URL xmlUrl = getClass().getResource("/fxml/project.fxml");
        loader.setLocation(xmlUrl);
        root = loader.load();
        scene = new Scene(root,1280,720);
        primStage.setScene(scene);
        primStage.show();
        roots = loader.getNamespace();
        for(Node n : root.getChildrenUnmodifiable())
            n.requestFocus();
        Canvas canvas = new Canvas();
        root.getChildren().add(canvas);
        canvas.setOnMouseClicked(event -> canvas.requestFocus());
        canvas.setWidth(640);
        canvas.setHeight(640);
        JmeToJFXApplication application = makeJmeApplication();
        JmeToJFXIntegrator.startAndBind(application, canvas, Thread::new);

    }

    private static JmeToJFXApplication makeJmeApplication() {

        AppSettings set = new AppSettings(true);
        set.setResizable(false);
        set.setGraphicsDebug(false);
        set.setVSync(false);
        set.setSamples(0);
        JmeToJFXIntegrator.prepareSettings(set, 60);
        JmeToJFXApplication application = new JmeToJFXApplication() {

            private com.jme3.scene.Node teaNode;
            private Spatial teaGeom;
            private boolean rotate = false;

            @Override
            public void simpleInitApp() {
                super.simpleInitApp();
                setDisplayStatView(false);
                teaGeom = assetManager.loadModel("models/CPU/CPU.j3o");
                teaNode = new com.jme3.scene.Node("teaNode");
                teaNode.attachChild(teaGeom);
                rootNode.attachChild(teaNode);
                teaGeom.rotate(0, 0, 0);
                teaGeom.addLight(new AmbientLight(ColorRGBA.White));
                CameraNode camNode = new CameraNode("Camera Node", cam);
                camNode.setControlDir(CameraControl.ControlDirection.SpatialToCamera);
                teaNode.attachChild(camNode);
                camNode.setLocalTranslation(new Vector3f(0, 100, 0));
                camNode.lookAt(teaNode.getLocalTranslation(), Vector3f.UNIT_Y);
                flyCam.setEnabled(false);
                registerInput();
            }

            public void registerInput() {
                inputManager.addMapping("toggleRotate", new MouseButtonTrigger(MouseInput.BUTTON_LEFT));
                inputManager.addMapping("rotateRightX", new MouseAxisTrigger(MouseInput.AXIS_X, true));
                inputManager.addMapping("rotateLeftX", new MouseAxisTrigger(MouseInput.AXIS_X, false));
                inputManager.addMapping("rotateRightY", new MouseAxisTrigger(MouseInput.AXIS_Y, true));
                inputManager.addMapping("rotateLeftY", new MouseAxisTrigger(MouseInput.AXIS_Y, false));
                inputManager.addListener(actionListener, "toggleRotate");
                inputManager.addListener(analogListener, "rotateRightX", "rotateLeftX", "rotateRightY", "rotateLeftY");
            }

            /** Use this listener for KeyDown/KeyUp events */
            private ActionListener actionListener = (name, keyPressed, tpf) -> {
                if (name.equals("toggleRotate") && keyPressed) rotate = true;
                if (name.equals("toggleRotate") && !keyPressed) rotate = false;
            };

            /** Use this listener for continuous events */
            private AnalogListener analogListener = new AnalogListener() {
                public void onAnalog(String name, float value, float tpf) {
                    if (name.equals("rotateRightX") && rotate) teaGeom.rotate(0, 5 * tpf, 0);
                    if (name.equals("rotateLeftX") && rotate) teaGeom.rotate(0, -5 * tpf, 0);
                    if (name.equals("rotateRightY") && rotate) teaGeom.rotate(5 * tpf, 0, 0);
                    if (name.equals("rotateLeftY") && rotate) teaGeom.rotate(-5 * tpf, 0, 0);
                }
            };
        };

        application.setSettings(set);
        application.setShowSettings(false);

        return application;
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
