package stratego.mode.menus.main;

import stratego.components.Sizes;
import stratego.mode.Mode;
import stratego.mode.ModeWorker;
import javafx.scene.*;
import stratego.network.Networker;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.scene.image.*;
import javafx.scene.text.*;
import javafx.stage.Screen;
import javafx.geometry.*;
import javafx.event.EventHandler;
import javafx.event.ActionEvent;
import java.util.concurrent.ConcurrentLinkedQueue;

public class MainMenuUI extends Mode {

	BorderPane pane;

	public MainMenuUI() {
		super(new BorderPane());
		this.worker = new MainMenuWorker(this.task);
		this.pane = (BorderPane) this.getRoot();

		Button ai = new Button("Vs. AI");
		ai.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				// TODO
			}
		});
		Button pl = new Button("Vs. Player");
		pl.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				// TODO
			}
		});
		Button st = new Button("Settings");
		st.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				// TODO
			}
		});
		Button lo = new Button("Log Out");
		lo.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				task.add(worker.getPingRequest());
			}
		});

		VBox buttons = new VBox(10, ai, pl, st, lo);
		buttons.setAlignment(Pos.CENTER);
		pane.setLeft(buttons);
		VBox.setVgrow(ai, Priority.ALWAYS);
		VBox.setVgrow(pl, Priority.ALWAYS);
		VBox.setVgrow(st, Priority.ALWAYS);
		VBox.setVgrow(lo, Priority.ALWAYS);
		ai.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
		pl.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
		st.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
		lo.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);

		Text title = new Text("StrategoFX");
		title.setFont(Font.font("Tahoma", FontWeight.NORMAL, 50));
		title.autosize();
		pane.setTop(title);
		BorderPane.setAlignment(title, Pos.TOP_CENTER);

		Text fl = new Text("Friends List");
		fl.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
		Button af = new Button("Add a Friend");
		af.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				// TODO
			}
		});

		VBox friends = new VBox(5, fl, af);
		friends.setAlignment(Pos.CENTER);
		pane.setRight(friends);
		VBox.setVgrow(fl, Priority.ALWAYS);
		VBox.setVgrow(af, Priority.ALWAYS);
		af.setMaxSize(Double.MAX_VALUE, 50);

	}

}
