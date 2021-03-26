package dashboard;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;

import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;
import javafx.scene.control.Label;
import login.LoginScreenController;
import sun.rmi.runtime.Log;

public class MainScreenController implements Initializable {


    @FXML
    private Label name;

    @FXML
    private Label body;

    public void setInfo(){
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try{
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://192.168.64.2/Bank","root","");
            String sql = "SELECT * FROM userdata WHERE AccountNo=?";
            ps = con.prepareStatement(sql);


            ps.setString(1, LoginScreenController.acc);

            rs= ps.executeQuery();



            if(rs.next()){

                name.setText(rs.getString("Name"));

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
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        body.setText("Allied Bank Limited is the fifth largest commercial bank in Pakistan and is a subsidiary \n  of Ibrahim Group. Allied Bank, with its registered Office in Lahore, is one of the largest banks \n within the country with over\n 1350 branches and ATMs");
        setInfo();
    }
}
