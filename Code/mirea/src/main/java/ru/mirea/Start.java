package ru.mirea;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.SceneAntialiasing;
import javafx.scene.image.Image;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import lombok.extern.slf4j.Slf4j;
import ru.mirea.Controllers.StartController;
import ru.mirea.data.User;
import ru.mirea.data.UsersImpl;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

@Slf4j
public class Start extends Application {

    public static Map<String, Object> roots;

    public static FXMLLoader loader;

    public static Stage primStage;

    public static String usename;

    public static String path = System.getenv("APPDATA") + "\\CompMagaz\\data";

    public static List<String> off_replace = new ArrayList<>();

    public static List<String> off_save = new ArrayList<>();

    public static List<String> off_reg = new ArrayList<>();

    public static final MediaPlayer player = new MediaPlayer( new Media(Start.class.getResource("/video/background.mp4").toExternalForm()));

    @Override
    public void start(Stage primaryStage) throws IOException {
        primStage = primaryStage;
        primStage.getIcons().add(new Image(getClass().getResourceAsStream("/img/ico.png")));
        primStage.setTitle("Комплектующие для ПК");
        primStage.setResizable(false);
        start_scene("/fxml/start.fxml");
        start_spring();
        load_users();
        load_commands();
        //close_start();
        //start_scene("/fxml/project.fxml");
        //((ProjController)loader.getController()).init();
    }

    public static void start_scene(String path) throws IOException {
        loader = new FXMLLoader();
        loader.setLocation(Start.class.getResource(path));
        primStage.setScene(new Scene(loader.load(), 1280, 720, true, SceneAntialiasing.DISABLED));
        roots = loader.getNamespace();
        primStage.show();
        primStage.setOnCloseRequest(Start::close);
    }

    private static void close(WindowEvent event) {
        try {
            if (!Files.exists(Paths.get(path))) new File(path).mkdirs();
            PrintWriter out = new PrintWriter(path + "\\users");
            for(User user : UsersImpl.map.values()) out.println(user.toSave());
            out.flush();
            out.close();
            out = new PrintWriter(path + "\\commands");
            out.println(off_replace);
            out.println(off_save);
            out.println(off_reg);
            out.flush();
            out.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        System.exit(0);
    }

    public static void agree_with_bd()
    {
        UsersImpl usersImpl = (UsersImpl) MireaApplication.ctx.getBean("usersImpl");
        int id = 0;
        Map<String, User> map = new HashMap<>();
        User user;
        List<String> on_reg = new ArrayList<>();
        for(String log : off_reg) {
            user = UsersImpl.map.get(log);
            if(usersImpl.getuser(log) == null)
            {
                System.out.println("Reg! " + log);
                usersImpl.addorsave(user);
                on_reg.add(user.getUsername());
            }
        }
        user = null;
        for(String log : off_replace) {
            System.out.println(log);
            switch (id) {
                case 0 -> {
                    System.out.println("init1 " + !off_reg.contains(log) + " " + log);
                    System.out.println("init2 " + on_reg.contains(log) + " " + log);
                    if(!off_reg.contains(log) || on_reg.contains(log)) {
                        user = usersImpl.getuser(log);
                    }
                    if(user == null) {
                        if (map.containsKey(log))
                            user = map.get(log);
                        else user = UsersImpl.map.get(log);
                    }
                    if(user != null) map.put(log, user);
                }
                case 1 -> {
                    User user1 = UsersImpl.map.get(log);
                    if(user1 != null) {
                        user.setCh_u(Objects.equals(user.getUsername(), user1.getUsername()));
                        user.setId(user1.getId());
                        user.setUsername(user1.getUsername());
                        user.setPassword(user1.getPassword());
                        user.setIcons(user1.getIcons());
                        user.setSohr(user1.getSohr());
                        map.put(log, user);
                    } //else user.setUsername(log);
                    //map.put(log, user);
                    id = 0;
                    user = null;
                    continue;
                }
            }
            id++;
        }
        for(User use : map.values()) System.out.println(use);
        for(String log : off_save) {
            User user1 = UsersImpl.map.get(log);
            if(map.containsKey(log))
                user = map.get(log);
            else
                user = user1 != null ? user1 : usersImpl.getuser(log);
            user.setSohr(user1.getSohr());
        }
        System.out.println("Print!");
        for(User use : map.values()) System.out.println(use);
        for(String log : off_reg) if(off_reg.contains(log) && !on_reg.contains(log) && !map.containsKey(log)) System.out.println("Reg nik exists " + log);
        for(User us : map.values()) if(usersImpl.getuser(us.getUsername()) != null && !us.isCh_u()) System.out.println("Replace nik exists " + us.getUsername());
    }

    private void load_commands()
    {
        try {
            int id = 0;
            for(String str : Files.readAllLines(Paths.get(path + "\\commands"), StandardCharsets.UTF_8))
            {
                System.out.println(str);
                str = str.replace("[", "").replace("]", "");
                System.out.println(str);
                if(str.isEmpty())
                {
                    id++;
                    continue;
                }
                switch (id) {
                    case 0 -> off_replace.addAll(List.of(str.split(", ")));
                    case 1 -> off_save.addAll(List.of(str.split(", ")));
                    case 2 -> off_reg.addAll(List.of(str.split(", ")));
                }
                id++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void load_users()
    {
        try {
            int id = 0;
            User newuser = new User();
            for(String str : Files.readAllLines(Paths.get(path + "\\users"), StandardCharsets.UTF_8))
            {
                switch (id) {
                    case 0 -> newuser.setId(Integer.parseInt(str));
                    case 1 -> newuser.setUsername(str);
                    case 2 -> newuser.setPassword(str);
                    case 3 -> newuser.setIcons(Integer.parseInt(str));
                    case 4 -> {
                        newuser.setSohr(Integer.parseInt(str));
                        id = 0;
                        UsersImpl.map.put(newuser.getUsername(), newuser);
                        newuser = new User();
                        continue;
                    }
                }
                id++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void start_spring()
    {
        new StartSpring(new SetCtx()).start();
    }

    public static void starts() {
        ((StartController)loader.getController()).init();
        start_vid();
    }

    public static void close_start()
    {
        player.stop();
        primStage.close();
    }

    public static void close_project()
    {
        primStage.close();
    }

    public static void start_vid()
    {
        ((MediaView) roots.get("medWiu")).setMediaPlayer(player);
        player.setCycleCount(MediaPlayer.INDEFINITE);
        player.play();
    }
}
