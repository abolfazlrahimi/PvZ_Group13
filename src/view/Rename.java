package view;

import in_game.Account;
import javafx.application.Application;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class Rename extends Application {
    static Stage initiateStage;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage myStage) throws Exception {
        myStage.setTitle("rename");
        GridPane rootNode = new GridPane();
        rootNode.setPadding(new Insets(15));
        rootNode.setHgap(5);
        rootNode.setVgap(5);
        rootNode.setAlignment(Pos.CENTER);

        Scene myScene = new Scene(rootNode, 300, 200);

        rootNode.add(new Label("userName"), 0, 0);
        TextField firstValue = new TextField();
        rootNode.add(firstValue, 1, 0);
        rootNode.add(new Label("rename Account"), 0, 2);
        Button aButton = new Button("rename Account");
        rootNode.add(aButton, 1, 2);
        GridPane.setHalignment(aButton, HPos.LEFT);
        aButton.setOnAction(e -> {
            Login.mainAccount.setName(firstValue.getText());
            Profile profile=new Profile();
            try {
                profile.start(myStage);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        myStage.setScene(myScene);
        myStage.setResizable(false);
        Stage initiateStage = myStage;
        myStage.show();

    }

    public Stage getInitiateStage() {
        return initiateStage;
    }
}
