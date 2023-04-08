package edu.virginia.cs.gui;
import edu.virginia.cs.wordle.IllegalWordException;
import edu.virginia.cs.wordle.LetterResult;
import edu.virginia.cs.wordle.WordleDictionary;
import edu.virginia.cs.wordle.WordleImplementation;

import javafx.event.ActionEvent;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import java.util.EventListener;
import java.util.ArrayList;

public class WordleController {
    @FXML
    private Label wordleLabel;
    @FXML
    private Label YouWin;
    @FXML
    private Label YouLose;
    @FXML
    private Label PlayAgain;
    @FXML
    private Button exit;
    @FXML
    private Button playAgain;
    @FXML
    private Label theanswer;
    private WordleImplementation wordle = new edu.virginia.cs.wordle.WordleImplementation();
    private String answer = wordle.getAnswer();
    private int guesses;
    @FXML
    private Label errorLabel;
    //@FXML
    //private Button Yes;
    //@FXML
    //private Button No;
    private Node child;
    private TextField field;

    //Create 6x5 box
    @FXML
    GridPane gridPane = new GridPane();
    private int r = 0;
    private int c = 0;
    private String guess = "";
    private WordleDictionary dictionary = new WordleDictionary();
    //TextField[][] wordleGame = new TextField[5][6];

    public void exitTheGame(ActionEvent event){
        System.exit(0);
    }
    //if player guess is correct, end game

    public void Cursor(KeyEvent event){

        //makeLabelsInvisible();
        TextField field = (TextField) getNode(gridPane,c,r);
        field.setStyle("-fx-text-inner-color: #000000; -fx-background-color: #FFFFFF;");

        if(event.getCode().equals(KeyCode.BACK_SPACE)){
           // Node n1 = getNode(gridPane,c,r);
            //TextField newField = (TextField) n1;
            if(c ==0){
                field.setText("");
            }
            if(c>0){
                Node n2 = getNode(gridPane,c-1,r);
                TextField newField2 = (TextField) n2;
                newField2.requestFocus();
                field.clear();
                c--;
                guess = guess.substring(0,guess.length()-1);

            }

        }
        /*if(event.getCode().equals(KeyCode.)){

        }*/
        else{
            field.setEditable(true);
            //field.setText(event.getCharacter().toUpperCase());
            //TextField field1 = (TextField) getNode(gridPane,c,r);
            //field.setText(x);
            //String n = field.getText();
            guess+= field.getText();
            if(c<4){
                //field.requestFocus();
                //TextField newField = (TextField)getNode(gridPane,c+1,r);
                field.requestFocus();
                c++;

            }
            if(c==4){
                field.requestFocus();
            }
            if(guess.length()==5) {
                try {
                    LetterResult[] answer = wordle.submitGuess(guess);
                    for (int i = 0; i < 5; i++) {
                        Node node1 = getNode(gridPane, i, r);
                        TextField field1 = (TextField) node1;
                        if (answer[i] == LetterResult.YELLOW) {
                            field1.setStyle("-fx-background-color: #c8b653; -fx-text-inner-color: #FFFFFFF;");
                        } else if (answer[i] == LetterResult.GREEN) {
                            field1.setStyle("-fx-background-color: #228B22; -fx-text-inner-color: #FFFFFFF;");

                        } else if (answer[i] == LetterResult.GRAY) {
                            field1.setStyle("-fx-background-color: #808080; -fx-text-inner-color: #FFFFFFF;");

                        }
                    }
                    if (wordle.isGameOver()) {
                        stopPlaying();
                        r = 0;
                        c = 0;
                        Node n1 = getNode(gridPane, c, r);
                        TextField newField = (TextField) n1;
                        newField.requestFocus();
                        if (wordle.isWin()) {

                            //YouWin.setVisible(true);
                        } else if (wordle.isLoss()) {
                            String lose = "You Lose!";
                            //YouLose.setVisible(true);
                        }
                        //PlayAgain.setVisible(true);
                        //buttonsVisible();
                    }
                    else {
                        guess = "";
                        c = 0;
                        r++;
                        Node n1 = getNode(gridPane, c, r);
                        TextField newField = (TextField) n1;
                        newField.requestFocus();
                    }
                } catch(IllegalWordException newWord){
                    c = 4;
                    TextField newField = (TextField) getNode(gridPane,c,r);
                    newField.requestFocus();
                }
            }
        }
    }

    public void makeLabelsInvisible(){
        YouLose.setVisible(false);
        YouWin.setVisible(false);
        PlayAgain.setVisible(false);

    }
    /*public void buttonsVisible(){
        Yes.setVisible(true);
        No.setVisible(true);
    }*/

    public void moveToTextField(MouseEvent mouseEvent){
        EventType<MouseEvent> event = MouseEvent.MOUSE_PRESSED;
        if(mouseEvent.getEventType().equals(event)){
            TextField current = (TextField) getNode(gridPane,c,r);
            TextField t = (TextField) mouseEvent.getSource();
            if(!current.equals(t)){
                current.requestFocus();
            }
        }
    }

    public void stopPlaying(){
        int gridPaneWidth = 5;
        int gridPaneHeight = 6;
        for(int i =0;i<gridPaneWidth;i++){
            for(int j = 0;j<gridPaneHeight;j++){
                Node n1 = getNode(gridPane,i,j);
                TextField newField = (TextField) n1;
                newField.setEditable(false);
            }
        }
    }

    public Node getNode(GridPane gridPane,int column, int row){
        int x = 0;
        for(Node child: gridPane.getChildren()){
            if(gridPane.getColumnIndex(child)==null&&x==0){
                gridPane.setColumnIndex(child,0);
                x = 0;
            }
            if(gridPane.getRowIndex(child)==null&&x==0){
                gridPane.setRowIndex(child,0);
                x = 0;
            }
            if(RowandColumnCheck(child, column, row)){
                return child;
            }
        }
        return null;
    }

    public boolean RowandColumnCheck(Node child,int column, int row){
        if(gridPane.getRowIndex(child)==row && gridPane.getColumnIndex(child)==column){
            return true;
        }
        return false;
    }

    public void lastButtonsVisible() {
        playAgain.setVisible(true);
        exit.setVisible(true);
    }

    public void playAgain(ActionEvent event){
        int gpwidth = 5;
        int gpheight = 6;
        wordle = new edu.virginia.cs.wordle.WordleImplementation();
        answer = wordle.getAnswer();
        for(int i=0; i<gpwidth; i++) {
            for(int j=0; j<gpheight; j++) {
                Node n1 = getNode(gridPane, i, j);
                TextField newField = (TextField) n1;
                newField.setText("");
                newField.setStyle("-fx-background-color: #FFFFFF");
                newField.setEditable(true);
            }
        }
        playAgain.setVisible(false);
        theanswer.setVisible(false);
        exit.setVisible(false);
        r = 0;
        c = 0;
        Node n2 = getNode(gridPane, c, r);
        TextField curr = (TextField) n2;
        curr.requestFocus();
    }

    //if player runs out of guesses, end game and display the correct answer

}