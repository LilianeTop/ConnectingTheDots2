package controller;

import database.mySQL.DBAccess;
import database.mySQL.UserDAO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.TextField;
import main.Main;
import model.User;

public class LoginSceneController {
    private UserDAO userDAO;
    private DBAccess dbAccess;
    private String userName;
    private String password;
    public static String currentRole;
    public static User currentUser;

    @FXML
    private TextField nameTextField;
    @FXML
    private TextField passwordField;
    @FXML
    private Hyperlink passwordRetrievalLink;
    @FXML
    private Hyperlink registerLink;

    public LoginSceneController() {
        super();
        this.dbAccess = Main.getDBAccess();
        this.userDAO = new UserDAO(dbAccess);
    }



    public void doLogin(ActionEvent actionEvent) {
        StringBuilder warningText = new StringBuilder();
        boolean correctInput = true;
        userName = nameTextField.getText();
        password = passwordField.getText();
        User user = userDAO.getOneByUsername(userName);
        if (user == null) {
            warningText.append("Please enter your username.\n");
            correctInput = false;
        }
        else if (!user.getPassword().equals(password) || user.getPassword().isEmpty()) {
            warningText.append("Username in combination with password is not valid.");
            correctInput = false;
        }
        if (!correctInput) {
            Alert error = new Alert(Alert.AlertType.ERROR);
            error.setContentText(warningText.toString());
            error.show();
        } else {
            currentUser = user;
            User.setCurrentUser(currentUser);
            currentRole = user.getRole();
            Main.getSceneManager().showWelcomeScene();
        }
    }

    public void doQuit(ActionEvent actionEvent) {
        System.exit(0);
    }

    public void doRetrievePassword(ActionEvent actionEvent){
        Main.getSceneManager().showRetrievePasswordScene();
    }

    public void doRegister(ActionEvent actionEvent) {
        Main.getSceneManager().showRegisterScene();
    }
}
