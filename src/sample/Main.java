package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.util.Scanner;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Sequence Extrapolator");
        primaryStage.setScene(new Scene(root, 300, 275));
        primaryStage.show();
        System.out.println("Hello World");
    }


    public static void main(String[] args) {
        launch(args);
        System.out.println("Matrix Method!");
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter comma separated sequence: ");
        String seq = sc.next();
        System.out.println();

        Matrix small = new Matrix(seq);
        small.printAUG();
        System.out.println();
        small.printEF();
        System.out.println();
        small.printRREF();
        System.out.println();
        small.poly();
    }
}
