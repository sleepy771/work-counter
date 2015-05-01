package com.gmail.sleepy771.workcount.gui;

import java.util.ArrayList;
import java.util.List;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class CounterWindow extends Application {

	private HBox topControll;
	private VBox leftControll;
	private List<Hyperlink> hyperlinks;
	private Button startPauseBtn;
	private Button stopBtn;
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
		primaryStage.setTitle("Work Counter");
		
		fillHyperlinks();
		
		BorderPane border = new BorderPane();
		border.setTop(getTopControllPannel());
		border.setLeft(getLeftControllPanel());
		
		Scene scene = new Scene(border, 800, 600);
		
		primaryStage.setScene(scene);
		
		primaryStage.show();
	}
	
	private HBox getTopControllPannel() {
		if (topControll == null) {
			topControll = new HBox();
			topControll.setPadding(new Insets(15, 12, 15,12));
			topControll.setSpacing(10);
			topControll.setStyle("-fx-background-color: #336699;");
			topControll.getChildren().addAll(getStartPauseButton(), getStopButton());
		}
		return topControll;
	}
	
	private Button getStartPauseButton() {
		if (startPauseBtn == null) {
			startPauseBtn = new Button("Start");
			startPauseBtn.setPrefSize(100, 20);
		}
		return startPauseBtn;
	}
	
	private Button getStopButton() {
		if (stopBtn == null) {
			stopBtn = new Button("Stop");
			stopBtn.setPrefSize(100, 20);
		}
		return stopBtn;
	}
	
	private VBox getLeftControllPanel() {
		if (leftControll == null) {
			leftControll = new VBox();
		    leftControll.setPadding(new Insets(10));
		    leftControll.setSpacing(8);

		    Text title = new Text("Data");
		    title.setFont(Font.font("Arial", FontWeight.BOLD, 14));
		    leftControll.getChildren().add(title);

		    for(Hyperlink h : hyperlinks) {
		    	VBox.setMargin(h, new Insets(0,0,0,8));
		    	leftControll.getChildren().add(h);
		    }

		    return leftControll;
		}
		return leftControll;
	}
	
	public List<Hyperlink> getHyperlinks() {
		if (hyperlinks == null) {
			hyperlinks = new ArrayList<>();
		}
		return hyperlinks;
	}
	
	private void fillHyperlinks() {
		String[] links = {"Today", "Yesterday", "Last week", "Month", "Overall"};
		for (String link : links)
			getHyperlinks().add(new Hyperlink(link));
	}
	
	public static void main(String[] args) {
		Application.launch(args);
	}

}
