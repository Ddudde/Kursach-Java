package ru.mirea;

import com.interactivemesh.jfx.importer.obj.ObjModelImporter;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.*;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Start extends Application {

    private Group root = new Group();
    private final Group axisGroup = new Group();
    private final Xform world = new Xform();
    private final PerspectiveCamera camera = new PerspectiveCamera(true);
    private final Xform cameraXform = new Xform();
    private final Xform cameraXform2 = new Xform();
    private final Xform cameraXform3 = new Xform();
    private final double cameraDistance = 450;
    private double mousePosX;
    private double mousePosY;
    private double mouseOldX;
    private double mouseOldY;
    private double mouseDeltaX;
    private double mouseDeltaY;
    private final double MODEL_SCALE_FACTOR = 40;
    private final double MODEL_X_OFFSET = -400; // standard
    private final double MODEL_Y_OFFSET = -400; // standard
    private final double MODEL_Z_OFFSET = -400; // standard
    private final double MODEL_A_X_OFFSET = 0; // standard
    private final double MODEL_A_Y_OFFSET = 0; // standard
    private final double MODEL_A_Z_OFFSET = 0; // standard

    private final int VIEWPORT_SIZE = 800;

    private final Color lightColor = Color.rgb(144, 155, 150);

    private void buildScene() {
        MeshView[] meshViews = loadMeshViews();
        for (int i = 0; i < meshViews.length; i++) {
            meshViews[i].setTranslateX(VIEWPORT_SIZE / 2f + MODEL_X_OFFSET);
            meshViews[i].setTranslateY(VIEWPORT_SIZE / 2f + MODEL_Y_OFFSET);
            meshViews[i].setTranslateZ(VIEWPORT_SIZE / 2f + MODEL_Z_OFFSET);
            meshViews[i].getTransforms().setAll(new Rotate(MODEL_A_X_OFFSET, Rotate.X_AXIS), new Rotate(MODEL_A_Y_OFFSET, Rotate.Y_AXIS), new Rotate(MODEL_A_Z_OFFSET, Rotate.Z_AXIS));
        }

        PointLight pointLight = new PointLight(lightColor);
        pointLight.setTranslateX(100);
        pointLight.setTranslateY(-100);
        pointLight.setTranslateZ(100);

        Color ambientColor = Color.rgb(80, 80, 80, 0);
        AmbientLight ambient = new AmbientLight(ambientColor);

        Group mesh = new Group(meshViews);
        mesh.setScaleX(MODEL_SCALE_FACTOR);
        mesh.setScaleY(MODEL_SCALE_FACTOR);
        mesh.setScaleZ(MODEL_SCALE_FACTOR);
        root.getChildren().add(pointLight);
        root.getChildren().add(ambient);
        root.getChildren().add(world);
        root.getChildren().add(mesh);
    }

    private MeshView[] loadMeshViews() {
        ObjModelImporter importer = new ObjModelImporter();
        importer.read(getClass().getResource("/models/kuler/Be.obj"));
        return importer.getImport();
    }

    private void buildCamera() {
        root.getChildren().add(cameraXform);
        cameraXform.getChildren().add(cameraXform2);
        cameraXform2.getChildren().add(cameraXform3);
        cameraXform3.getChildren().add(camera);
        cameraXform3.setRotateZ(180.0);
        cameraXform3.setRotateX(200.0);

        camera.setNearClip(0.1);
        camera.setFarClip(10000.0);
        camera.setTranslateZ(-cameraDistance);
        cameraXform.ry.setAngle(320.0);
        cameraXform.rx.setAngle(40);
    }

    private void handleMouse(Scene scene, final Node root) {
        scene.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent me) {
                mousePosX = me.getSceneX();
                mousePosY = me.getSceneY();
                mouseOldX = me.getSceneX();
                mouseOldY = me.getSceneY();
            }
        });
        scene.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent me) {
                mouseOldX = mousePosX;
                mouseOldY = mousePosY;
                mousePosX = me.getSceneX();
                mousePosY = me.getSceneY();
                mouseDeltaX = (mousePosX - mouseOldX);
                mouseDeltaY = (mousePosY - mouseOldY);

                double modifier = 1.0;
                double modifierFactor = 0.1;

                if (me.isControlDown()) {
                    modifier = 0.1;
                }
                if (me.isShiftDown()) {
                    modifier = 10.0;
                }
                if (me.isPrimaryButtonDown()) {
                    cameraXform.ry
                            .setAngle(cameraXform.ry.getAngle() - mouseDeltaX * modifierFactor * modifier * 2.0);  // +
                    cameraXform.rx
                            .setAngle(cameraXform.rx.getAngle() + mouseDeltaY * modifierFactor * modifier * 2.0);  // -
                } else if (me.isSecondaryButtonDown()) {
                    double z = camera.getTranslateZ();
                    double newZ = z + mouseDeltaX * modifierFactor * modifier;
                    camera.setTranslateZ(newZ);
                } else if (me.isMiddleButtonDown()) {
                    cameraXform2.t.setX(cameraXform2.t.getX() + mouseDeltaX * modifierFactor * modifier * 0.3);  // -
                    cameraXform2.t.setY(cameraXform2.t.getY() + mouseDeltaY * modifierFactor * modifier * 0.3);  // -
                }
            }
        });
    }

    @Override
    public void start(Stage primaryStage) {
        buildScene();
        buildCamera();

        Scene scene = new Scene(root, VIEWPORT_SIZE, VIEWPORT_SIZE, true, SceneAntialiasing.BALANCED);
        scene.setFill(Color.GREY);
        handleMouse(scene, world);

        primaryStage.setTitle("Test 3D");
        primaryStage.setScene(scene);
        primaryStage.show();

        scene.setCamera(camera);
    }
}
