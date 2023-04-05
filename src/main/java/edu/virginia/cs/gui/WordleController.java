package edu.virginia.cs.gui;
import edu.virginia.cs.wordle.WordleImplementation;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;

public class WordleController {
    @FXML
    private Label wordleLabel;
    private WordleImplementation wordle = new edu.virginia.cs.wordle.WordleImplementation();
    private String guess;
    private String answer;

    private int guesses;
    @FXML
    private Label errorLabel;
    private Node child;
    private TextField field;
    private int r;
    private int c;
    //Create 6x5 box
    @FXML
    GridPane gridPane = new GridPane();
    TextField[][] wordleGame = new TextField[5][6];


    //each box accepts one letter and automatically moves on to the next

    //once answer is entered, color each box and leave that guess up and move to next row
    /*@FXML
    protected void EnterKey(KeyEvent event) {
        if (event.getCode().equals(KeyCode.ENTER)) {
            try {
                if(guess == answer){

                }
            } catch (NumberFormatException e) {
                errorLabel.setText("Error: Enter a valid int");
            }
        }
    }*/
    public void exitTheGame(ActionEvent event){
        System.exit(0);
    }
    //if player guess is correct, end game
    public void Cursor(KeyEvent event){
        field = (TextField) getNode(gridPane,c,r);
        field.setStyle("-fx-text-inner-color: #000000; -fx-background-color: #FFFFFF;");
        if(!event.getCharacter().isBlank()){
            String x = event.getCharacter().toUpperCase();
            field.setText(x);
            String n = field.getText();
            guesses = guesses + Integer.parseInt(n);

        }
    }
    public Node getNode(GridPane gridPane,int column, int row){
        int x = 0;
        for(Node child: gridPane.getChildren()){
            if(gridPane.getRowIndex(child)==null&&x==0){
                gridPane.setRowIndex(child,0);
                x = 0;
            }
            if(gridPane.getColumnIndex(child)==null&&x==0){
                gridPane.setColumnIndex(child,0);
                x = 0;
            }
            if(RowandColumnCheck(child, column, row)){
                return child;
            }
        }
        return null;
    }
    public boolean RowandColumnCheck(Node child,int column, int row){
        if(gridPane.getRowIndex(child)==column && gridPane.getColumnIndex(child)==row){
            return true;
        }
        return false;
    }

    //if player runs out of guesses, end game and display the correct answer

}