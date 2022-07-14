module VierGewinntClient {
	requires transitive javafx.graphics;
	requires transitive javafx.controls;
	requires javafx.fxml;
	requires javafx.base;
	
	opens sudoku to javafx.fxml;
	
	exports sudoku;
}