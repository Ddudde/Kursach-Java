package ru.mirea;

import com.jme3.input.MouseInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.AnalogListener;
import com.jme3.input.controls.MouseAxisTrigger;
import com.jme3.input.controls.MouseButtonTrigger;
import com.jme3.jfx.injfx.processor.FrameTransferSceneProcessor;
import com.jme3.light.AmbientLight;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.scene.CameraNode;
import com.jme3.scene.Spatial;
import com.jme3.scene.control.CameraControl;
import com.jme3.system.AppSettings;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
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
import com.jme3.jfx.injfx.JmeToJfxApplication;
import com.jme3.jfx.injfx.JmeToJfxIntegrator;
import lombok.extern.slf4j.Slf4j;
import ru.mirea.Controllers.StartController;

@Slf4j
public class Start extends Application {

    public static Pane root;

    private static MediaView medWiu;

    private static Map<String, Object> roots;

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
        show_start();
        //close_start();
        //show_project();
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

    public void starts()
    {
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
        for(Node n : root.getChildrenUnmodifiable())
            n.requestFocus();
        ImageView view3d = new ImageView();
        view3d.setFitWidth(512);
        view3d.setFitHeight(512);
        primStage.setOnCloseRequest(event -> System.exit(0));
        JmeToJfxApplication application = makeJmeApplication();
        JmeToJfxIntegrator.startAndBind(application, view3d, Thread::new, FrameTransferSceneProcessor.TransferMode.UNBUFFERED);
        root.getChildren().add(view3d);
    }

    private static JmeToJfxApplication makeJmeApplication() {

        AppSettings set = new AppSettings(true);
        set.setResolution(512,512);
        set.setVSync(false);
        set.setGraphicsDebug(false);
        set.setFrameRate(70);
        set.setFrequency(60);
        set.setGammaCorrection(false);
        set.setResizable(false);
        set.setAudioRenderer(null);
        JmeToJfxIntegrator.prepareSettings(set);
        JmeToJfxApplication application = new JmeToJfxApplication() {

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
                //renderer.setMainFrameBufferSrgb(true);
                //renderer.setLinearizeSrgbImages(true);
                rootNode.attachChild(teaNode);
                teaGeom.rotate(0, 0, 0);
                rootNode.addLight(new AmbientLight(ColorRGBA.fromRGBA255(235,233,188, 0)));
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

        application.setShowSettings(false);
        application.setSettings(set);

        return application;
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
