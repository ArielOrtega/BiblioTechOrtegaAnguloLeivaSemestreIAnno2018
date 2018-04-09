package Interface;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class MainInterface extends Application {

    //Instancia del HBox que tendrá la funcion de presentar cada interfaz
    VBox presenter = new VBox();
    //Instanciamos la cable InterfaceModules para manejar sus metodos
    InterfaceModules interfaceM = new InterfaceModules();

    @Override
    public void start(Stage primaryStage) {

        //Creacion de la "raiz" sobre la que se presentará el "Front-end" 
        BorderPane root = new BorderPane();
        root.getChildren().add(menuBar());
        root.setTop(menuBar());

        //Instancia de la escena con su parametro y medidas
        Scene scene = new Scene(root, 600, 600);

        //Manejo del escenario  
        primaryStage.setTitle("Library");
        primaryStage.setScene(scene);
        primaryStage.setMinWidth(500);
        primaryStage.setMinHeight(450);
        primaryStage.setMaximized(true);
        primaryStage.show();

    }//fin start

    public VBox menuBar() {

        //HBox y VBox
        HBox hbx_window = new HBox();
        VBox vbx_upperSide = new VBox();

        //Modificaciones del HBox
        hbx_window.setAlignment(Pos.CENTER);
        hbx_window.setSpacing(40);

        Scene scene = new Scene(new VBox(), 1000, 650);

        //Menu que despliega categor'ias principales
        //Categor'ia estudiantes
        Menu mnu_menuStudents = new Menu("Students");
        mnu_menuStudents.setMnemonicParsing(true);

        //Subcategor'ia para ingresar y ver estudiantes
        MenuItem mim_enterStudent = new MenuItem("Enter new Student");
        mim_enterStudent.setOnAction((event) -> {
            presenter.getChildren().clear();
            hbx_window.getChildren().clear();
            hbx_window.getChildren().addAll(interfaceM.studentRegister(), interfaceM.showTableView());
        });

        mnu_menuStudents.getItems().addAll(mim_enterStudent);

        //Categor'ia materiales
        Menu mnu_menuMaterials = new Menu("Materials");
        mnu_menuMaterials.setMnemonicParsing(true);

        //Subcategor'ia de materiales para ingresar un libro
        MenuItem mim_enterBooks = new MenuItem("Enter Books");
        mim_enterBooks.setOnAction((event) -> {
            try {
                hbx_window.getChildren().clear();
                presenter.getChildren().clear();
                presenter.getChildren().addAll(interfaceM.enterBooks());
            } catch (IOException ex) {
                Logger.getLogger(MainInterface.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        //Subcategor'ia de materiales para ingresar art'iculos Audiovisuales
        MenuItem mim_enterAudiovisual = new MenuItem("Enter Audiovisual");
        mim_enterAudiovisual.setOnAction((event) -> {
            hbx_window.getChildren().clear();
            presenter.getChildren().clear();
            presenter.getChildren().addAll(interfaceM.enterAudioVisual());
        });

        //Subcategor'ia de materiales para ver los materiales registrados
        MenuItem mim_enterView = new MenuItem("View Material");
        mim_enterView.setOnAction((event) -> {
            try {
                hbx_window.getChildren().clear();
                presenter.getChildren().clear();
                presenter.getChildren().addAll(interfaceM.viewMaterial());
            } catch (IOException ex) {
                Logger.getLogger(MainInterface.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        //Agregaci'on de las subcategor'ias a la categor'ia materiales
        mnu_menuMaterials.getItems().addAll(mim_enterBooks, mim_enterAudiovisual, mim_enterView);

        //Categor'ia prestamos
        Menu mnu_menuLoans = new Menu("Loans");
        mnu_menuLoans.setMnemonicParsing(true);

        //Subcategor'ia de pr'estamos para realizar un prestamo
        MenuItem mim_performLoan = new MenuItem("Make a loan");
        mim_performLoan.setOnAction((event) -> {
            try {
                hbx_window.getChildren().clear();
                presenter.getChildren().clear();
                presenter.getChildren().addAll(interfaceM.enterLoan());
            } catch (IOException | ClassNotFoundException ex) {
                Logger.getLogger(MainInterface.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        //Subcategor'ia de pr'estamos para eliminar un pr'estamo
        MenuItem mim_deleteLoan = new MenuItem("Delete loan");
        mim_deleteLoan.setOnAction((event) -> {

            try {
                hbx_window.getChildren().clear();
                presenter.getChildren().clear();
                presenter.getChildren().addAll(interfaceM.deleteLoans());
            } catch (IOException ex) {
                Logger.getLogger(MainInterface.class.getName()).log(Level.SEVERE, null, ex);
            }

        });

        //Subcategor'ia de pr'estamos para ver los pr'estamos realizados
        MenuItem mim_loansView = new MenuItem("View Loans");
        mim_loansView.setOnAction((event) -> {

            try {
                hbx_window.getChildren().clear();
                presenter.getChildren().clear();
                presenter.getChildren().addAll(interfaceM.viewLoans());
            } catch (IOException ex) {
                Logger.getLogger(MainInterface.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        //Agregaci'on de las subcategor'ias a la categor'ia pr'estamos
        mnu_menuLoans.getItems().addAll(mim_performLoan, mim_deleteLoan, mim_loansView);

        //Creaci'on del MenuBar que depliega las opciones a elegir
        MenuBar mnb_mainMenu = new MenuBar();
        mnb_mainMenu.setStyle("-fx-background-color: \n" +
"    linear-gradient(#7F7679, #9C9C9C),\n" +
"    linear-gradient(#9C9C9C, #7F7679),\n" +
"    linear-gradient(from 0% 0% to 15% 50%, rgba(246,246,246,0.9), rgba(246,246,246,0));\n" +
"    -fx-font-size: 14px;\n" +
"    -fx-padding: 10 20 10 20;");
        //Agregaci'on de las categor'ias al MenuBar
        mnb_mainMenu.getMenus().addAll(mnu_menuStudents, mnu_menuMaterials, mnu_menuLoans);

        //Despliegue del logo en el programa
        Image img_logo = new Image(MainInterface.class.getResourceAsStream("/Images/logoVL.png"));
        ImageView imv_logo = new ImageView();
        imv_logo.setImage(img_logo);
        imv_logo.setFitHeight(170);
        imv_logo.setFitWidth(1100);

        vbx_upperSide.setStyle("-fx-background-color: DIMGRAY");

        //Tamaño del presentador
        presenter.setPrefSize(scene.getWidth(), scene.getWidth());

        //Agregaci'on de todos los componentes de la interfaz 
        vbx_upperSide.getChildren().addAll(imv_logo, mnb_mainMenu, hbx_window, presenter);

        return vbx_upperSide;

    }//end menuBar()

    public static void main(String[] args) throws IOException {
        launch(args);
    }//end main()

}//end mainInterface
