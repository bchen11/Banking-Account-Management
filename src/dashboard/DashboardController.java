package dashboard;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.File;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import jdk.internal.util.xml.impl.Input;
import login.Banking;
import login.LoginScreenController;
import javafx.scene.text.Text;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.Node;


public class DashboardController implements Initializable {

    private  double xOffset = 0;
    private  double yOffset = 0;


    @FXML
    private Pane dashboard_main;

    @FXML
    private  Text name;
    @FXML
    private FontAwesomeIconView ico;
    @FXML
    private Circle profilepic;
    @FXML
    private  void closeApp(MouseEvent event){
        Platform.exit();
        System.exit(0);
    }

    @FXML
    private  void minimizeApp(MouseEvent event){
        Stage stage = (Stage) ico.getScene().getWindow();
        stage.setIconified(true);
    }

    public void setData(){
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try{
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://192.168.64.2/Bank","root","");
            String sql = "SELECT * FROM userdata WHERE AccountNo=?";
            ps = con.prepareStatement(sql);


            ps.setString(1,LoginScreenController.acc);

            rs= ps.executeQuery();



            if(rs.next()){

                name.setText(rs.getString("Name"));
                InputStream is = rs.getBinaryStream("ProfilePic");
                OutputStream os = new FileOutputStream(new File("pic.jpg"));
                byte[] content = new byte[1024];
                int size = 0;
                while((size = is.read(content)) != -1){
                    os.write(content,0,size);
                }
                os.close();
                is.close();
                Image img = new Image("file:pic.jpg", false);
                profilepic.setFill(new ImagePattern(img));

            }
            else{
                Alert a = new Alert(Alert.AlertType.ERROR);
                a.setTitle("Error");
                a.setHeaderText("Error in Login");
                a.setContentText("There is some error. TRY AGAIN!");
                a.showAndWait();
            }


        }catch (Exception e){
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setTitle("Error");
            a.setHeaderText("Error in Login");
            a.setContentText("Your account number or password is wrong. Enter again" );
            a.showAndWait();

        }
    }
    @FXML
    public void click(MouseEvent event){
        xOffset = event.getSceneX();
        yOffset = event.getSceneY();

    }
    @FXML
    public void drag(MouseEvent event){
        LoginScreenController.stage.setX(event.getScreenX() - xOffset);
        LoginScreenController.stage.setY(event.getScreenY() - yOffset);

    }


    @FXML
    public void accountInformation(MouseEvent event) throws IOException{
        Parent fxml = FXMLLoader.load(getClass().getResource("/accountinfo/AccountInformation.fxml"));
        dashboard_main.getChildren().clear();
        dashboard_main.getChildren().addAll(fxml);



    }
    @FXML
    public void withdraw(MouseEvent event) throws IOException{
        Parent fxml = FXMLLoader.load(getClass().getResource("/withdraw/WithdrawAmount.fxml"));
        dashboard_main.getChildren().clear();
        dashboard_main.getChildren().addAll(fxml);



    }
    @FXML
    public void transactionHistory(MouseEvent event) throws IOException{
        Parent fxml= FXMLLoader.load(getClass().getResource("/transactionhistory/TransactionHistory.fxml"));
        dashboard_main.getChildren().clear();
        dashboard_main.getChildren().addAll(fxml);
    }

    @FXML
    public void deposit(MouseEvent event) throws IOException{
        Parent fxml = FXMLLoader.load(getClass().getResource("/deposit/DepositAmount.fxml"));
        dashboard_main.getChildren().clear();
        dashboard_main.getChildren().addAll(fxml);



    }
    @FXML
    public void pinChange(MouseEvent event) throws IOException{
        Parent fxml = FXMLLoader.load(getClass().getResource("/changepin/ChangePIN.fxml"));
        dashboard_main.getChildren().clear();
        dashboard_main.getChildren().addAll(fxml);

    }

    @FXML
    public void transferAmount(MouseEvent event) throws IOException{
        Parent fxml = FXMLLoader.load(getClass().getResource("/transferamount/TransferAmount.fxml"));
        dashboard_main.getChildren().clear();
        dashboard_main.getChildren().addAll(fxml);

    }

    @FXML
    public void mainScreen() throws IOException{
        Parent fxml = FXMLLoader.load(getClass().getResource("MainScreen.fxml"));
        dashboard_main.getChildren().clear();
        dashboard_main.getChildren().addAll(fxml);


    }

    public void logout(MouseEvent event) throws IOException{
        ((Node)event.getSource()).getScene().getWindow().hide();
        Parent root = FXMLLoader.load(getClass().getResource("/login/LoginScreen.fxml"));
        Scene scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("/design/design.css").toExternalForm());
        Stage stage= new Stage();
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setScene(scene);
        stage.show();

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
    }




    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setData();
        try {
            mainScreen();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
