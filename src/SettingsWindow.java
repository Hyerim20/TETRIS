import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class SettingsWindow extends Stage {

    private ToggleButton colorBlindModeToggle;
    private MenuButton resizeMenuButton;
    private Stage mainWindow;
    private boolean isColorBlindModeOn;
    private Button[] buttons;

    public SettingsWindow(Stage mainWindow) {
        this.mainWindow = mainWindow;
        setTitle("설정");
        setWidth(300);
        setHeight(400);

        BorderPane root = new BorderPane();

        VBox buttonBox = new VBox();
        buttonBox.setAlignment(Pos.CENTER);
        buttonBox.setSpacing(10);
        buttonBox.setPadding(new Insets(20));

        FlowPane modePanel = new FlowPane();
        modePanel.setAlignment(Pos.CENTER);
        modePanel.setHgap(10);
        Label colorBlindModeLabel = new Label("색맹 모드");
        colorBlindModeToggle = new ToggleButton("off");
        colorBlindModeToggle.setPrefSize(50, 25);
        colorBlindModeToggle.setOnAction(event -> handleButtonClick(event));
        colorBlindModeToggle.setFocusTraversable(false);
        isColorBlindModeOn = false;
        modePanel.getChildren().addAll(colorBlindModeLabel, colorBlindModeToggle);

        FlowPane resizePanel = new FlowPane();
        resizePanel.setAlignment(Pos.CENTER);
        resizePanel.setHgap(10);
        Label resizeLabel = new Label("화면 크기 조절");
        resizeMenuButton = new MenuButton();
        resizeMenuButton.setFocusTraversable(false); // 포커스 가능 여부 설정
        MenuItem item1 = new MenuItem("450 x 600");
        item1.setOnAction(event -> {
            resizeMenuButton.setText("450 x 600");
            SizeConstants.setSize(450, 600);
        });
        MenuItem item2 = new MenuItem("300 x 400");
        item2.setOnAction(event -> {
            resizeMenuButton.setText("300 x 400");
            SizeConstants.setSize(300, 400);
        });
        MenuItem item3 = new MenuItem("600 x 800");
        item3.setOnAction(event -> {
            resizeMenuButton.setText("600 x 800");
            SizeConstants.setSize(600, 800);
        });
        resizeMenuButton.getItems().addAll(item1, item2, item3);
        resizeMenuButton.setText("450 x 600"); // 기본값 "450 x 600"로 설정

        resizePanel.getChildren().addAll(resizeLabel, resizeMenuButton);

        buttonBox.getChildren().addAll(modePanel, resizePanel);

        Button keySettingsButton = new Button("키 설정");
        Button resetScoreButton = new Button("기록 초기화");
        Button resetAllButton = new Button("기본 설정으로 되돌리기");
        Button backButton = new Button("뒤로가기");

        buttons = new Button[]{keySettingsButton, resetScoreButton, resetAllButton, backButton};

        for (Button button : buttons) {
            button.setOnAction(event -> handleButtonClick(event));
            button.setFocusTraversable(true); // 포커스 가능하게 설정
            buttonBox.getChildren().add(button);
        }

        // 초기 포커스 설정
        keySettingsButton.requestFocus();

        backButton.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                hide();
                mainWindow.show();
            }
        });

        root.setCenter(buttonBox);

        Scene scene = new Scene(root);
        setScene(scene);
    }

    private void handleButtonClick(ActionEvent event) {
        Object source = event.getSource();
        if (source == colorBlindModeToggle) {
            isColorBlindModeOn = !isColorBlindModeOn;
            colorBlindModeToggle.setText(isColorBlindModeOn ? "on" : "off");
        } else if (source instanceof Button) {
            Button clickedButton = (Button) source;
            String buttonText = clickedButton.getText();
            if (buttonText.equals("뒤로가기")) {
                hide();
                mainWindow.show();
            } else if (buttonText.equals("기본 설정으로 되돌리기")) {
                showResetConfirmation();
            } else if (buttonText.equals("기록 초기화")) {
                showScoreResetConfirmation();
            }
        }
    }

    private void showResetConfirmation() {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("설정 초기화");
        alert.setHeaderText(null);
        alert.setContentText("정말 기본 설정으로 되돌리시겠습니까?");

        alert.showAndWait().ifPresent(result -> {
            if (result == ButtonType.OK) {
                resetSettings();
            }
        });
    }

    private void showScoreResetConfirmation() {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("기록 초기화");
        alert.setHeaderText(null);
        alert.setContentText("정말 모든 기록을 초기화하시겠습니까?");

        alert.showAndWait().ifPresent(result -> {
            if (result == ButtonType.OK) {
                resetScoreSettings();
            }
        });
    }



    private void resetSettings() {
        // 색맹 모드 초기화
        isColorBlindModeOn = false;
        colorBlindModeToggle.setSelected(false);
        colorBlindModeToggle.setText("off");

        // 화면 크기 초기화
        resizeMenuButton.setText("450 x 600");
        SizeConstants.setSize(450, 600);

        // 다른 설정들도 초기화하는 코드 추가
        System.out.println("기본 설정으로 되돌림");
    }

    private void resetScoreSettings() {
        // 기록 초기화 코드 입력 공간
    }
}