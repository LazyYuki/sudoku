package sudoku;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.*;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.*;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;

public class Controller {
	@FXML public GridPane grid;
	
	static final int width = 9;
	static final int height = 9;
	
	private StringProperty[][] gridPaneArray = new StringProperty[width][height];
	
	@FXML
	public void initialize() {
		System.out.println("Controller loaded");
		
		for (int i = 0; i < 9; i++) {
			for(int j = 0; j < 9; j++) {
				StringProperty prop = new SimpleStringProperty();
				gridPaneArray[i][j] = prop;
				
				TextField t = new TextField();
				t.setFont(Font.font(27d));
				t.setAlignment(Pos.CENTER);
				t.setOnKeyTyped((KeyEvent e) -> inputTypedField(e));
				GridPane.setMargin(t, new Insets(5, 5, 5, 5));
				t.textProperty().bindBidirectional(prop);
				grid.add(t, j, i);
			}
		}
    }  
	
	@FXML
	private void inputTypedField(KeyEvent e) {
		String text = ((TextField) e.getSource()).getText();
		
		if (text.length() > 1 || (text.length() == 1 && !Character.isDigit(text.charAt(0)))) {
			text = text.substring(0, text.length() - 1);
		}
		
		((TextField) e.getSource()).setText(text);
	}
	
	private void setGameField(int[][] gameGrid) {
		for (int y = 0; y < 9; y++) {
			for (int x = 0; x < 9; x++) {
				int num = gameGrid[y][x];
				
				if (num != 0) {
					gridPaneArray[y][x].set(String.valueOf(gameGrid[y][x]));
				} else {
					gridPaneArray[y][x].set("");
				}
			}
		}
	}
	
	private int[][] getGameField() {
		int[][] out = new int[9][9];

		for (int y = 0; y < 9; y++) {
			for (int x  = 0; x < 9;x++) {
				String text = gridPaneArray[y][x].get();
				
				if (text == null || text.equals("")) {
					out[y][x] = 0;
				} else {
					out[y][x] = Integer.valueOf(text);
				}
			}
		}
		
		return out;
	}
	
	public Node getNodeByRowColumnIndex (final int row, final int column) {
		Node node = grid.getChildren().get( column * 9 + row );
		ColumnConstraints colum = grid.getColumnConstraints().get(column);
		
		GridPane.getColumnIndex(node);
		
		if (node instanceof TextField) {
			return node;
		}
		return null;
	}
	
	@FXML 
	private void generate(MouseEvent event) {
		int[][] gameGrid = SudokuGenerator.generate();
		SudokuGenerator.printGrid(gameGrid);
		setGameField(gameGrid);
	}
	
	@FXML 
	private void solve(MouseEvent event) {
		int[][] gameGrid = getGameField();
		
		System.out.println("------------------------");
		SudokuGenerator.printGrid(gameGrid);
		
		SudokuGenerator.generateSudoku(gameGrid, false);
		
		setGameField(gameGrid);
	}
	
	@FXML 
	private void validate(MouseEvent event) {
		System.out.println("validate");
	}
	
	@FXML 
	private void clear(MouseEvent event) {
		System.out.println("clear");
	}
}
