package login;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Banking extends Application {

    public static Stage stage = null;

    private  double xOffset = 0;
    private  double yOffset = 0;

    @Override
    public void start(Stage stage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("LoginScreen.fxml"));


        Scene scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("/design/design.css").toExternalForm());
        stage.initStyle(StageStyle.UNDECORATED);

        stage.setScene(scene);

        root.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
               xOffset = event.getSceneX();
               yOffset= event.getSceneY();
            }
        });

        root.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                stage.setX(event.getScreenX() - xOffset);
                stage.setY(event.getScreenY() - yOffset);
            }
        });

        this.stage = stage;
        stage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
