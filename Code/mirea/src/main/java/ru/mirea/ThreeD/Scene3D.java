package ru.mirea.ThreeD;

import com.interactivemesh.jfx.importer.obj.ObjModelImporter;
import javafx.scene.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.CullFace;
import javafx.scene.shape.MeshView;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Scene3D {

    private Group root = new Group();
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

    private SubScene scene;

    private final Color lightColor = Color.rgb(204, 205, 200);

    public SubScene getScene() {
        return scene;
    }

    public Scene3D(String comp) {
        buildCamera();
        buildScene(comp);
        scene = new SubScene(root, 640, 512, true, SceneAntialiasing.DISABLED);
        scene.setFill(Color.valueOf("#242424"));
        handleMouse();
        scene.setCamera(camera);
    }

    public void destroy(){
        scene.setOnMousePressed(null);
        scene.setOnMouseDragged(null);
        world.getChildren().remove(world.getChildren().get(3));
        cameraXform.ry.setAngle(320.0);
        cameraXform.rx.setAngle(40);
        mouseOldX = 0;
        mouseOldY = 0;
        mousePosX = 0;
        mousePosY = 0;
        mouseDeltaX = 0;
        mouseDeltaY = 0;
        camera.setTranslateZ(-cameraDistance);
        cameraXform2.t.setX(0);
        cameraXform2.t.setY(0);
    }

    public void undestroy(String comp){
        handleMouse();
        buildModel(comp);
    }

    private void buildModel(String comp) {
        Group mesh = new Group();
        ObjModelImporter importer = new ObjModelImporter();
        importer.read(getClass().getResource("/models/" + comp + "/" + comp + ".obj"));
        for (MeshView view : importer.getImport()) {
            view.setCullFace(CullFace.NONE);
            mesh.getChildren().add(view);
        }
        importer.close();
        world.getChildren().add(mesh);
    }

    private void buildScene(String comp) {
        PointLight pointLight = new PointLight(lightColor);
        pointLight.setLayoutX(0);
        pointLight.setLayoutY(-5000);
        pointLight.setTranslateZ(-1000);

        Color ambientColor = Color.rgb(80, 80, 80, 0);
        AmbientLight ambient = new AmbientLight(ambientColor);
        world.getChildren().add(pointLight);
        world.getChildren().add(ambient);
        root.getChildren().add(world);
        buildModel(comp);
    }

    private void buildCamera() {
        world.getChildren().add(cameraXform);
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

    public void handleVR(MouseEvent me) {
        mousePosX = me.getSceneX();
        mousePosY = me.getSceneY();
        mouseOldX = me.getSceneX();
        mouseOldY = me.getSceneY();
    }

    public void handle(MouseEvent me) {
        mouseOldX = mousePosX;
        mouseOldY = mousePosY;
        mousePosX = me.getSceneX();
        mousePosY = me.getSceneY();
        mouseDeltaX = (mousePosX - mouseOldX);
        mouseDeltaY = (mousePosY - mouseOldY);

        double modifier = 2.0;
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

    private void handleMouse() {
        scene.setOnMousePressed(this::handleVR);
        scene.setOnMouseDragged(this::handle);
    }
}
