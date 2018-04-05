package Interface;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class MainInterface extends Application {

    VBox presentador = new VBox();
    InterfaceModules interfaceM = new InterfaceModules();

    @Override
    public void start(Stage primaryStage) {

        BorderPane root = new BorderPane();
        root.getChildren().add(menuBar());
        root.setTop(menuBar());

        Scene scene = new Scene(root, 600, 600);

        primaryStage.setTitle("Bibliotech");
        primaryStage.setScene(scene);
        primaryStage.setMinWidth(500);
        primaryStage.setMinHeight(450);
        primaryStage.show();

    }//fin start

    public VBox menuBar() {

        HBox hbx_window = new HBox();
        VBox vbx_upperSide = new VBox();

        hbx_window.setAlignment(Pos.CENTER);
        hbx_window.setSpacing(10);

        Scene scene = new Scene(new VBox(), 1000, 650);

        //Menu que despliega categorias principales
        Menu mnu_menuStudents = new Menu("Estudiantes");
        mnu_menuStudents.setMnemonicParsing(true);

        MenuItem mim_enterStudent = new MenuItem("Ingresar");
        mim_enterStudent.setOnAction((event) -> {
            presentador.getChildren().clear();
            hbx_window.getChildren().clear();
            hbx_window.getChildren().addAll(interfaceM.studentRegister(), interfaceM.showTableView());
        });

        mnu_menuStudents.getItems().addAll(mim_enterStudent);

        Menu mnu_menuMaterials = new Menu("Materiales");
        mnu_menuMaterials.setMnemonicParsing(true);

        MenuItem mim_enterBooks = new MenuItem("Libros");
        mim_enterBooks.setOnAction((event) -> {
            try {
                hbx_window.getChildren().clear();
                presentador.getChildren().clear();
                presentador.getChildren().addAll(interfaceM.enterBooks());
            } catch (IOException ex) {
                Logger.getLogger(MainInterface.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        MenuItem mim_enterAudiovisual = new MenuItem("Audiovisual");
        mim_enterAudiovisual.setOnAction((event) -> {
            hbx_window.getChildren().clear();
            presentador.getChildren().clear();
            presentador.getChildren().addAll(interfaceM.enterAudioVisual());
        });

        MenuItem mim_enterView = new MenuItem("Mostrar");
        mim_enterView.setOnAction((event) -> {
            try {
                hbx_window.getChildren().clear();
                presentador.getChildren().clear();
                presentador.getChildren().addAll(interfaceM.viewMaterial());
            } catch (IOException ex) {
                Logger.getLogger(MainInterface.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        mnu_menuMaterials.getItems().addAll(mim_enterBooks, mim_enterAudiovisual, mim_enterView);

        Menu mnu_menuLoans = new Menu("Prestamos");
        mnu_menuLoans.setMnemonicParsing(true);

        MenuItem mim_performLoan = new MenuItem("Realizar Prestamo");
        mim_performLoan.setOnAction((event) -> {
            try {
                hbx_window.getChildren().clear();
                presentador.getChildren().clear();
                presentador.getChildren().addAll(interfaceM.enterLoan());
            } catch (IOException ex) {
                Logger.getLogger(MainInterface.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        MenuItem mim_deleteLoan = new MenuItem("Eliminar Prestamo");
        mim_deleteLoan.setOnAction((event) -> {
            
                
            try {
                hbx_window.getChildren().clear();
                presentador.getChildren().clear();
                presentador.getChildren().addAll(interfaceM.deleteLoans());
            } catch (IOException ex) {
                Logger.getLogger(MainInterface.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        });

        MenuItem mim_loansView = new MenuItem("Mostrar");
        mim_loansView.setOnAction((event) -> {

            try {
                hbx_window.getChildren().clear();
                presentador.getChildren().clear();
                presentador.getChildren().addAll(interfaceM.viewLoans());
            } catch (IOException ex) {
                Logger.getLogger(MainInterface.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        mnu_menuLoans.getItems().addAll(mim_performLoan, mim_deleteLoan, mim_loansView);

        MenuBar mnb_mainMenu = new MenuBar();
        mnb_mainMenu.getMenus().addAll(mnu_menuStudents, mnu_menuMaterials, mnu_menuLoans);

        //Despliegue del logo en el programa
        Image img_logo = new Image(MainInterface.class.getResourceAsStream("/Images/logoBiblioT.png"));
        ImageView imv_logo = new ImageView();
        imv_logo.setImage(img_logo);
        imv_logo.setFitHeight(170);
        imv_logo.setFitWidth(300);

        presentador.setPrefSize(scene.getWidth(), scene.getWidth());

        vbx_upperSide.getChildren().addAll(imv_logo, mnb_mainMenu, hbx_window, presentador);

        return vbx_upperSide;
    }

    public static void main(String[] args) throws IOException {
        launch(args);

    }

}
