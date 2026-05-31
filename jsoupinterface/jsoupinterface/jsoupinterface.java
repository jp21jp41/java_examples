//Jsoup Interface
package jsoupinterface;

//Import packages (Configured in module-info.java)
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuButton;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Callback;


// Class with extend for JavaFX
public class jsoupinterface<T> extends Application {
	static Document doc = null;
	static private String html;
	static private boolean began = false;
	static private Elements elements;
	HashMap<String, ArrayList<T>> assets1 = new HashMap<String, ArrayList<T>>();
	// Function to change the HTML into one editable string
    public void textScene(Stage primaryStage) throws IOException {
    	// Default: gets jsoup.org
    	if (doc == null) {
    		doc = Jsoup.connect("http://jsoup.org").get();
    		Document doc_restore = doc;
    	}
    	// Assign HTML variable
    	if (!began) {
			began = true;
			System.out.println(began);
			html = doc.html();
		}
    	// Constructor functions and variable assignment
    	BorderPane border = new BorderPane();
    	TextArea textpiece = new TextArea(html);
    	/*
    	textpiece.textProperty().addListener((DocOfAllText, oldValue, newValue) -> {
    		if (!assets1.containsKey("Task Bar")) {
    		    
    		}
			System.out.println("Old: " + oldValue.length() + "\nNew: " + newValue.length());
		});
		*/
    	textpiece.textProperty().addListener(new ChangeListener<String>() {
    		@Override
			public void changed(ObservableValue<? extends String> DocOfAllText, String oldValue, String newValue) {
				html = newValue;
			}
    	});
    	ListView<Button> sideButtons = new ListView<Button>();
    	// Set Stage Items
    	if (!assets1.containsKey("Task Bar")) {
			Button toggleButton = new Button("Toggle");
			Button undoButton = new Button("Undo");
			undoButton.setOpacity(0.5);
			Button redoButton = new Button("Redo");
			redoButton.setOpacity(0.5);
			toggleButton.setOnAction(e -> {
				try {
					elementScene(primaryStage);
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			});
			Button returnButton = new Button("Return");
			returnButton.setOnAction(e -> {
				start(primaryStage);
			});
			ArrayList<T> taskbar = new ArrayList<T>();
			taskbar.add((T) toggleButton);
			taskbar.add((T) undoButton);
			taskbar.add((T) redoButton);
			taskbar.add((T) returnButton);
        	assets1.put("Task Bar", taskbar);
        	ArrayList<T> restorers = new ArrayList<T>();
        	if (!assets1.containsKey("Restore Tasks")) {
        		Button restoreButton = new Button("Restore Options");
        		restorers.add((T) restoreButton);
        		assets1.put("Restore Tasks", restorers);
        	} else {
        		restorers.add(assets1.get("Restore Tasks").get(0));
        	}
        	for (T button : taskbar) {
				sideButtons.getItems().add((Button) button);
			}
			sideButtons.getItems().add((Button) restorers.get(0));
		} else {
			ArrayList<T> restorers = new ArrayList<T>();
        	if (!assets1.containsKey("Restore Tasks")) {
        		Button restoreButton = new Button("Restore Options");
        		restorers.add((T) restoreButton);
        		assets1.put("Restore Tasks", restorers);
        	} else {
        		restorers.add(assets1.get("Restore Tasks").get(0));
        	}
			ArrayList<T> assets = assets1.get("Task Bar");
			((Button) assets.get(0)).setOnAction(e -> {
				try {
					elementScene(primaryStage);
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			});
			for (T button : assets) {
				sideButtons.getItems().add((Button) button);
			}
			sideButtons.getItems().add((Button) restorers.get(0));
		}
    	border.setCenter(textpiece);
    	border.setRight(sideButtons);
    	Scene scene2 = new Scene(border,800,800);
    	primaryStage.setScene(scene2);
    	primaryStage.show();
    };
	// HTMLElement class: Creates a class to be implemented with observableArrayList
	public static class HTMLElement {
		// Class Attributes
		private final String row;
		private final String html;
		private final String deleteString;
		// HTMLElement Constructor
		public HTMLElement(String row, String html, String deleteString) {
			this.row = row;
			this.html = html;
			this.deleteString = deleteString;
		}
		// Functions to return the row as well as the HTML each
		public String getrow() {return row;}
		public String gethtml() {return html;}
		public String getdeletestring() {return deleteString;}
	}
	    // Function to change the html into element-by-element format
	    public void elementScene(Stage primaryStage) throws IOException {
	    	// Listview object with HTMLElement data type
	    	ListView<HTMLElement> listView = new ListView<>();
	   		// Set Cell Factory to allow proper observableArrayList compatibility
	   		listView.setCellFactory(new Callback<ListView<HTMLElement>, ListCell<HTMLElement>>() {
	    		// Call function with element adjustments
	   			public ListCell<HTMLElement> call(ListView<HTMLElement> listView) {
	    			return new ListCell<HTMLElement>() {
	    				// HBox graphic
	    				private final HBox graphic = new HBox(10);
	    				StackPane graphicstack = new StackPane();
	    				// Labels
						private final Label label1 = new Label();
	    				TextArea itemScrollText = new TextArea();
	    				ScrollPane htmlScroll = new ScrollPane(itemScrollText);
	    				private final Button deleteButton = new Button();
	    				{
	    					htmlScroll.setPrefWidth(400);
		    				htmlScroll.setPrefHeight(500);
							// Add the labels to the HBox and set width and height
	    					StackPane.setAlignment(deleteButton, Pos.CENTER_RIGHT);
	    					StackPane.setMargin(itemScrollText, new Insets(30, 30, 30, 30));
	    					graphic.getChildren().addAll(label1, htmlScroll);
	    					graphicstack.getChildren().addAll(graphic, deleteButton);
	   						label1.setPrefWidth(100);
	  						label1.setPrefHeight(150);
	  						itemScrollText.setTranslateX(50);
	  						itemScrollText.textProperty().addListener(new ChangeListener<String>() {
		    		    		@Override
		    					public void changed(ObservableValue<? extends String> DocOfAllText, String oldValue, String newValue) {
		    						System.out.println("");
		    					}
		    		    	});
	    				}
	    				// Item Update override
	    				@Override
	    				protected void updateItem(HTMLElement item, boolean empty) {
	    					// call super class
	    					super.updateItem(item, empty);
	    					// set empty text as null or set the text and HTML
	    					if (empty || item == null) {
	    						setGraphic(null);
	    						setText(null);
	    					} else {
	    						label1.setText(item.getrow());
	    						itemScrollText.setText(item.gethtml());
	    						deleteButton.setText(item.deleteString);
	    						setGraphic(graphicstack);
	    					}
	    				}
	    			};
	   			}
	    	});
	    	// Default: gets jsoup.org
	   		if (doc == null) {
			    doc = Jsoup.connect("http://jsoup.org").get();
			    html = doc.html();
			    Document doc_restore = doc;
	   		}
	   		if (!began) {
				began = true;
				System.out.println(began);
				// Select elements
				elements = doc.select("*");
			} else {
				Document html_convert = Jsoup.parse(html);
				elements = doc.select("*");
			}
			// Add Border and a scene with the border, instantiate pieces with
			// Constructor functions and variable assignment
			BorderPane border = new BorderPane();
			Scene scene2 = new Scene(border,900,800);
			TextArea textpiece = new TextArea();
			ContextMenu contextMenu = new ContextMenu();
			ObservableList<HTMLElement> items = FXCollections.observableArrayList();
			int row = 1;
			// ForEach loop: Add rows and HTML one after another
			for (Element element : elements){
				String elementString = element.html();
				items.add(new HTMLElement(String.valueOf(row), elementString, "Delete Row " + String.valueOf(row)));
				 // Add a ListChangeListener
				/*
		        items.addListener((ListChangeListener<? super HTMLElement>) change -> {
		            while (change.next()) {
		                if (change.wasAdded()) {
		                    System.out.println("Added: " + change.getAddedSubList());
		                }
		                if (change.wasRemoved()) {
		                    System.out.println("Removed: " + change.getRemoved());
		                }
		                if (change.wasUpdated()) {
		                    System.out.println("Updated: " + items.subList(change.getFrom(), change.getTo()));
		                }
		            }
		        });
		        */
				row ++;
			}
			// Set stage items
			listView.setItems(items);
			textpiece.setContextMenu(contextMenu);
			TableView<HTMLElement> tableView = new TableView<>();
			tableView.setItems(items);
			ListView<Button> sideButtons = new ListView<Button>();
			if (!assets1.containsKey("Task Bar")) {
				Button toggleButton = new Button("Toggle");
				toggleButton.setOnAction(e -> {
					try {
						textScene(primaryStage);
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				});
				Button undoButton = new Button("Undo");
				undoButton.setOpacity(0.5);
				Button redoButton = new Button("Redo");
				redoButton.setOpacity(0.5);
				Button returnButton = new Button("Return");
				returnButton.setOnAction(e -> {
					start(primaryStage);
				});
				ArrayList<T> taskbar = new ArrayList<T>();
				taskbar.add((T) toggleButton);
				taskbar.add((T) undoButton);
				taskbar.add((T) redoButton);
				taskbar.add((T) returnButton);
	        	assets1.put("Task Bar", taskbar);
	        	for (T button : taskbar) {
					sideButtons.getItems().add((Button) button);
				}
			} else {
				ArrayList<T> assets = assets1.get("Task Bar");
				((Button) assets.get(0)).setOnAction(e -> {
					try {
						textScene(primaryStage);
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				});
				for (T button : assets) {
					sideButtons.getItems().add((Button) button);
				}
			}
			border.setCenter(listView);
			border.setRight(sideButtons);
			primaryStage.setScene(scene2);
			primaryStage.show();
	    }
	    // Function override to set stage
		@Override
		public void start(Stage primaryStage) {
			// Try-except: Primary Stage setup
			try {
				Button button1 = new Button("Change by Element");
				Button button2 = new Button("Change with Text");
				HBox buttonBox = new HBox(10);
				buttonBox.getChildren().addAll(button1, button2);
		        Scene scene1 = new Scene(buttonBox, 400, 400);
				// Title set
				primaryStage.setTitle("HTML data checker");
				// Add CSS file to scene, set scene, show
				scene1.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
				button1.setOnAction(e -> {
					try {
						elementScene(primaryStage);
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				});
				button2.setOnAction(e -> {
					try {
						textScene(primaryStage);
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				});
				primaryStage.setScene(scene1);
				primaryStage.show();
			} catch(Exception e) {
				e.printStackTrace(); //Exception
			}
		}
		
		public static void main(String[] args) throws IOException {
			launch(args); //Runs whole JavaFX
		}
}
