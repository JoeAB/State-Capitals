/**
 * Created by User on 1/8/2016.
 */
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Random;

public class USStateCap extends Application {

    HashMap<String, String> map = new HashMap<String, String>();
    Text question = new Text();
    Button option1 = new Button();
    Button option2 = new Button();
    Button option3 = new Button();
    Button option4 = new Button();
    String state;
    String stateCapitol;
    String wrong1 = null;
    String wrong2 = null;
    String wrong3 = null;
    int indexState;
    int indexWrong;
    int numberRemaining = 50;
    ArrayList<String> stateList;
    ArrayList<String> capitols;
    ArrayList<String> options ;
    Text numberQuestionsLeft = new Text();
    Text rightOrWrong = new Text();
    ImageView mapPic = new ImageView("USMAP.PNG");


    Random rand = new Random();

    VBox pane = new VBox();


    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader("US_State_Cap.txt"));
            String line = null;
            while ((line = br.readLine()) != null) {
                String df[] = line.split(",");
                map.put(df[0].trim(), df[1].trim());
            }
        } catch (FileNotFoundException e) {
            System.out.println("no");

        } catch (IOException e) {
            System.out.println("hmm");
        }
        mapPic.setFitHeight(300);
        mapPic.setFitWidth(400);
        play();

        pane.getChildren().addAll(mapPic,question,option1,option2,option3,option4, numberQuestionsLeft, rightOrWrong);
        option1.setOnMouseClicked(e -> {
            check(option1.getText());
        });
        option2.setOnMouseClicked(e -> {
            check(option2.getText());
        });
        option3.setOnMouseClicked(e -> {
            check(option3.getText());
        });
        option4.setOnMouseClicked(e -> {
            check(option4.getText());
        });
            question.setFont(Font.font("Verdana", 30));
            option1.setFont(Font.font("Verdana", 20));
            option2.setFont(Font.font("Verdana", 20));
            option3.setFont(Font.font("Verdana", 20));
            option4.setFont(Font.font("Verdana", 20));
            pane.setSpacing(30);
            pane.setAlignment(Pos.CENTER);
            pane.setStyle("-fx-background-color: #9697C3;");
            Scene scene = new Scene(pane,700,750);
            primaryStage.setScene(scene);
            primaryStage.setTitle("Capitol City Game");
            primaryStage.show();

    }
    public void check(String choice) {
        if (choice == stateCapitol) {
            stateList.remove(indexState);
            numberRemaining--;
            rightOrWrong.setText("Correct!");
        }
        else
            rightOrWrong.setText("Incorrect!");
        wrong1 = null;
        wrong2 = null;
        wrong3 = null;
        play();

    }

    public void play(){
        //start game
        numberQuestionsLeft.setText(numberRemaining+" States remaining.");
        stateList = new ArrayList<String>(map.keySet());
        capitols = new ArrayList<String>(map.values());
        options = new ArrayList<String>();
        Collections.shuffle(stateList);
        //picks a random number to identify one of the remaining numbers of states
        indexState = rand.nextInt(numberRemaining);
        //assigns the name of the state to a variable from the ArrayList
        state = stateList.get(indexState);
        //gets the value from the map that corresponds to the state's capitol
        stateCapitol = map.get(state);
        //gets values for the wrong cities as long as they're not already in use
        while (wrong1 == null) {
            indexWrong = rand.nextInt(50);
            if (capitols.get(indexWrong) != stateCapitol)
                wrong1 = capitols.get(indexWrong);
        }
        while (wrong2 == null) {
            indexWrong = rand.nextInt(50);
            if (capitols.get(indexWrong) != stateCapitol && capitols.get(indexWrong) != wrong1)
                wrong2 = capitols.get(indexWrong);
        }
        while (wrong3 == null) {
            indexWrong = rand.nextInt(50);
            if (capitols.get(indexWrong) != stateCapitol && capitols.get(indexWrong) != wrong1 && capitols.get(indexWrong) != wrong2)
                wrong3 = capitols.get(indexWrong);
        }
        options.add(stateCapitol);
        options.add(wrong1);
        options.add(wrong2);
        options.add(wrong3);
        Collections.shuffle(options);
        question.setText("What is the capitol city of " + state + "?");
        option1.setText(options.get(0));
        option2.setText(options.get(1));
        option3.setText(options.get(2));
        option4.setText(options.get(3));

    }
}