package sudoku;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainSudoku extends Application {
	@Override
	public void start(Stage primaryStage) {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("mainview.fxml"));
		Scene currentScene = null;
		try {
			currentScene = new Scene((Parent) loader.load(), 600, 653);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		primaryStage.setScene(currentScene);
		primaryStage.show();
	}
	
	@Override
	public void stop(){
		System.exit(0);
	}
	
	public static void main(String[] args) {
//		System.out.println("Generated ------------------------");
//		int[][] gameGrid = SudokuGenerator.generate();
//		SudokuGenerator.printGrid(gameGrid);
//		
//		System.out.println("\n\nSolved ---------------------------");
//		SudokuGenerator.generateSudoku(gameGrid, false);
//		SudokuGenerator.printGrid(gameGrid);
		
		launch(args);
	}
}
