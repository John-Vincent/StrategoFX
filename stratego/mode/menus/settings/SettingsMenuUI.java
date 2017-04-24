package stratego.mode.menus.settings;

import stratego.mode.Mode;
import stratego.mode.menus.main.MainMenuUI;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Slider;
import javafx.scene.control.ToggleGroup;
import stratego.components.gameboard.*;

/**
 * Settings Menu UI The settings menu contains buttons that will change
 * different settings for the gameplay experience.
 */
public class SettingsMenuUI extends Mode {
	BorderPane pane;
	static Scanner scanner;
	boolean existingSettings, changedFromFile;
	public static int cheats, freeForm;
	private final static double buttonWidth = 200;

	/**
	 * Constructor Uses BorderPane to create the settings menu.
	 */
	public SettingsMenuUI() {
		super(new BorderPane());
		this.setWorker(new SettingsMenuWorker());
		this.pane = (BorderPane) this.getRoot();
		this.pane.setPadding(new Insets(0, 30, 20, 30));
		this.setMinSize(500, 400);
		changedFromFile = false;	//Prevents MediaPlayer from starting the same song over when loading the settings menu.

		try {
			scanner = new Scanner(new File("SFXsettings.brad"));
			existingSettings = true;
			cheats = scanner.nextInt();
			scanner.nextInt();
			scanner.nextDouble();
			freeForm = scanner.nextInt();
			scanner.close();
		} catch (FileNotFoundException e) {
			existingSettings = false;
			cheats = 0;
			freeForm = 0;
		}

		Button back = new Button("Back");
		back.setMinWidth(buttonWidth);
		back.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
		back.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				setNextMode(new MainMenuUI());
				addTask("update");
				addTask("back");
			}
		});

		Text cheatTxt = new Text("Cheats: R To Activate");
		cheatTxt.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
		cheatTxt.autosize();

		Button cheatBtn = new Button();
		if (getCheatSetting()) {
			cheatBtn.setText("Enabled");
			GameScene.setCheat(true);
		} else {
			cheatBtn.setText("Disabled");
			GameScene.setCheat(false);
		}

		cheatBtn.setMinWidth(buttonWidth);
		cheatBtn.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
		cheatBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				setNextMode(new MainMenuUI());
				if (cheatBtn.getText().equals("Enabled")) {
					GameScene.setCheat(false);
					cheatBtn.setText("Disabled");
					cheats = 0;
				} else if (cheatBtn.getText().equals("Disabled")) {
					GameScene.setCheat(true);
					cheatBtn.setText("Enabled");
					cheats = 1;
				}
			}
		});

		VBox cheatBox = new VBox(5, cheatTxt, cheatBtn);
		cheatBox.setFillWidth(true);
		cheatBox.setAlignment(Pos.CENTER);
		VBox.setVgrow(cheatTxt, Priority.ALWAYS);
		VBox.setVgrow(cheatBtn, Priority.ALWAYS);

		Text musicTxt = new Text("Music Theme & Volume:");
		musicTxt.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
		musicTxt.autosize();

		ToggleGroup musicGroup = new ToggleGroup();

		RadioButton music0 = new RadioButton("Electronic");
		music0.setMinWidth(buttonWidth);
		music0.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
		music0.setToggleGroup(musicGroup);
		music0.selectedProperty().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> ov, Boolean prevSelected, Boolean currSelected) {
				if (currSelected) {
					if (changedFromFile) {
						changedFromFile = false;
					} else {
						stratego.components.MusicPlayer.changeMusic(stratego.components.MusicPlayer.HSH, stratego.components.MusicPlayer.getCurrentVolume());
					}
				}
			}
		});

		RadioButton music1 = new RadioButton("Tactical");
		music1.setMinWidth(buttonWidth);
		music1.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
		music1.setToggleGroup(musicGroup);
		music1.selectedProperty().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> ov, Boolean prevSelected, Boolean currSelected) {
				if (currSelected) {
					if (changedFromFile) {
						changedFromFile = false;
					} else {
						stratego.components.MusicPlayer.changeMusic(stratego.components.MusicPlayer.CSGO, stratego.components.MusicPlayer.getCurrentVolume());
					}
				}
			}
		});

		RadioButton music2 = new RadioButton("Mute");
		music2.setMinWidth(buttonWidth);
		music2.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
		music2.setToggleGroup(musicGroup);
		music2.selectedProperty().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> ov, Boolean prevSelected, Boolean currSelected) {
				if (currSelected) {
					if(changedFromFile){
						changedFromFile = false;
					}else{
					stratego.components.MusicPlayer.changeMusic("MUTE.mp3", stratego.components.MusicPlayer.getCurrentVolume());

					}
				}
			}
		});
		HBox musicSelect = new HBox(5, music0, music1, music2);
		musicSelect.setFillHeight(true);
		musicSelect.setAlignment(Pos.CENTER);
		HBox.setHgrow(music0, Priority.ALWAYS);
		HBox.setHgrow(music1, Priority.ALWAYS);
		HBox.setHgrow(music2, Priority.ALWAYS);

		Slider musicSlider = new Slider(0, 100, 100);
		musicSlider.setShowTickLabels(true);
		musicSlider.setShowTickMarks(true);
		musicSlider.setMajorTickUnit(50);
		musicSlider.valueProperty().addListener(new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> ov, Number oldVal, Number newVal) {
				stratego.components.MusicPlayer.setMusicVolume(newVal.doubleValue()/100.0);
			}

		});
		if (existingSettings) {
			changedFromFile = true;
			switch(stratego.components.MusicPlayer.getSettingMusicName()){
			case stratego.components.MusicPlayer.HSH:
				musicGroup.selectToggle(music0);
				break;
			case stratego.components.MusicPlayer.CSGO:
				musicGroup.selectToggle(music1);
				break;
			case stratego.components.MusicPlayer.MUTE:
				musicGroup.selectToggle(music2);
				break;
			}
			musicSlider.setValue(stratego.components.MusicPlayer.getSettingMusicVolume()*100.0);

		} else {
			musicGroup.selectToggle(music2);
			musicSlider.setValue(50);
		}

		VBox musicBox = new VBox(5, musicTxt, musicSelect, musicSlider);
		musicBox.setFillWidth(true);
		musicBox.setAlignment(Pos.CENTER);
		VBox.setVgrow(musicTxt, Priority.ALWAYS);
		VBox.setVgrow(musicSelect, Priority.ALWAYS);
		VBox.setVgrow(musicSlider, Priority.ALWAYS);

		// TODO No SFX implementation yet
		Text effectTxt = new Text("Sound Effects Volume:");
		effectTxt.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
		effectTxt.autosize();

		Slider effectSlider = new Slider(0, 100, 100);
		effectSlider.setShowTickLabels(true);
		effectSlider.setShowTickMarks(true);
		effectSlider.setMajorTickUnit(50);

		VBox effectBox = new VBox(5, effectTxt, effectSlider);
		effectBox.setFillWidth(true);
		effectBox.setAlignment(Pos.CENTER);
		VBox.setVgrow(effectTxt, Priority.ALWAYS);
		VBox.setVgrow(effectSlider, Priority.ALWAYS);


		Text freeformTxt = new Text("Freeform Game Graphics:");
		freeformTxt.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
		freeformTxt.autosize();

		Button freeformBtn = new Button();
		if (existingSettings) {
			if (freeForm == 1) {
				freeformBtn.setText("Enabled");
				GameScene.freeForm = 1;
			} else {
				freeformBtn.setText("Disabled");
				GameScene.freeForm = 0;
			}
		} else {
			freeformBtn.setText("Disabled");
			GameScene.freeForm = 0;
		}
		freeformBtn.setMinWidth(buttonWidth);
		freeformBtn.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
		freeformBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				setNextMode(new MainMenuUI());
				if (freeformBtn.getText().equals("Enabled")) {
					GameScene.freeForm = 0;
					freeformBtn.setText("Disabled");
					freeForm = 0;
				} else if (freeformBtn.getText().equals("Disabled")) {
					GameScene.freeForm = 1;
					freeformBtn.setText("Enabled");
					freeForm = 1;
				}
			}
		});

		VBox freeformBox = new VBox(5, freeformTxt, freeformBtn);
		freeformBox.setFillWidth(true);
		freeformBox.setAlignment(Pos.CENTER);
		VBox.setVgrow(freeformTxt, Priority.ALWAYS);
		VBox.setVgrow(freeformBtn, Priority.ALWAYS);



		VBox buttons = new VBox(50, cheatBox, musicBox, effectBox, freeformBox, back);
		VBox.setVgrow(cheatBox, Priority.ALWAYS);
		VBox.setVgrow(musicBox, Priority.ALWAYS);
		VBox.setVgrow(musicSlider, Priority.ALWAYS);
		VBox.setVgrow(back, Priority.ALWAYS);
		buttons.setPadding(new Insets(100, 100, 100, 100));
		buttons.setAlignment(Pos.CENTER);
		pane.setCenter(buttons);
		pane.setMinSize(400, 250);
	}

	public static boolean getCheatSetting(){
		try{
			scanner = new Scanner(new File("SFXsettings.brad"));
			int cheat = scanner.nextInt();
			scanner.close();
			return cheat == 1;
		}catch(Exception e){
			return false;
		}
	}


}
