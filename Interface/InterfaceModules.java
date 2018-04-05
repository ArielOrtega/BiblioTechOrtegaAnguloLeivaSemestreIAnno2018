package Interface;

import Domain.AudioVisual;
import Domain.Books;
import Domain.Loan;
import Domain.LogicalMethods;
import Domain.Student;
import File.AudioVisualFile;
import File.BooksFile;
import File.LoanFile;
import File.StudentFile;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OptionalDataException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.RowConstraints;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.util.Callback;
import javafx.util.converter.LocalDateStringConverter;
import javax.swing.JOptionPane;
import org.controlsfx.control.textfield.TextFields;
//import org.controlsfx.control.textfield.TextFields;

public class InterfaceModules {

    ObservableList<Student> observableArrayStudent = FXCollections.observableArrayList();
    ArrayList<Student> arrayListStudent = new ArrayList<>();
    LogicalMethods methods = new LogicalMethods();    
    static String genre, idiom;
    static int valueDelivery1, valueDelivery2, valueDelivery3;
    static LocalDate date1;
    static Label lbl_choise, lbl_signature, lbl_deliveryDay, lbl_warning, lbl_success, lbl_idStudent, lbl_info;
    static RadioButton rdb_choiceBook, rdb_choiceAV;
    static TextField tfd_signatureB, tfd_idStudent, tfd_signatureAV;
    static DatePicker dpk_delivaeyDay;
    static Button btn_enterLoan, btn_checkStudent, btn_exit, btn_delete, btn_exitLoan;
    static Loan loan1;
    static AudioVisualFile avf;
    static String signatureB, signatureAV, signature, idLoan;
    static MainInterface mI = new MainInterface();
    StudentFile sft= new StudentFile();
    

    public GridPane studentRegister() {
       
        GridPane gridpane = new GridPane();

        Label lbl_title = new Label("Registrar Estudiantes");
        Label lbl_name = new Label("Nombre");
        Label lbl_entryYear = new Label("Año de ingreso");
        Label lbl_exception= new Label("Ingresar datos correctamente");
        lbl_exception.setVisible(false);

        TextField tf_name = new TextField();
        TextField tf_entryYear = new TextField();

        tf_name.setPromptText("Nombre");
        tf_entryYear.setPromptText("Año");;

        ComboBox<String> cb_career = new ComboBox<>();
        cb_career.getItems().addAll("Informática", "Administración", "Turismo");
        cb_career.setPromptText("Carrera");
        cb_career.setEditable(false);

        cb_career.setOnAction((event) -> {
            try{
             String name= tf_name.getText();
             int year= Integer.parseInt(tf_entryYear.getText());
                
            if (tf_name.getText().length()>0 && tf_entryYear.getText().length()>0 && cb_career.getValue().length()>0) {
                Student s = new Student(name, year,
                        cb_career.getValue(), "metodo", methods.getStudentId(cb_career.getValue(), tf_entryYear.getText(), arrayListStudent.size()));

                observableArrayStudent.add(s);
                arrayListStudent.add(s);

                tf_name.clear();
                tf_entryYear.clear();
            }
            }catch(NumberFormatException nfe){
                lbl_exception.setVisible(true);
                tf_name.clear();
                tf_entryYear.clear();
            }
            
        });

        gridpane.add(lbl_title, 0, 0);
        gridpane.add(lbl_name, 0, 2);
        gridpane.add(tf_name, 0, 3);
        gridpane.add(lbl_entryYear, 0, 5);
        gridpane.add(tf_entryYear, 0, 6);
        gridpane.add(cb_career, 0, 8);
        gridpane.add(lbl_exception, 0, 9);
        gridpane.setVgap(5);

        return gridpane;
    }

    public VBox showTableView() {
        
        TableView<Student> table = new TableView<>();

        TableColumn columnId = new TableColumn("Carnet");
        columnId.setCellValueFactory(new PropertyValueFactory("id"));

        TableColumn columnName = new TableColumn("Nombre");
        columnName.setCellValueFactory(new PropertyValueFactory("name"));

        TableColumn columnYear = new TableColumn("Año Ingreso");
        columnYear.setCellValueFactory(new PropertyValueFactory("entryYear"));

        TableColumn columnCareer = new TableColumn("Carrera");
        columnCareer.setCellValueFactory(new PropertyValueFactory("career"));

        TableColumn columnLoans = new TableColumn("Préstamos");
        columnLoans.setCellValueFactory(new PropertyValueFactory("previousLoans"));

        table.getColumns().addAll(columnId, columnName, columnYear, columnCareer, columnLoans);
        table.setItems(observableArrayStudent);
        table.setEditable(false);

        Button btn_addToFile= new Button("Añadir al archivo");
        btn_addToFile.setOnAction((event) -> {
            sft.writeFile(arrayListStudent);
        });
        
        Button btn_showRecords= new Button("Ver Registros");
        btn_showRecords.setOnAction((event) -> {
            try {
                table.setItems(sft.readFile());
            } catch (FileNotFoundException | ClassNotFoundException | OptionalDataException ex) {
                Logger.getLogger(InterfaceModules.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        
        HBox hbox_buttons= new HBox();
        hbox_buttons.getChildren().addAll(btn_addToFile, btn_showRecords);

        VBox vbox = new VBox();
        vbox.getChildren().addAll(table, hbox_buttons);

        return vbox;
    }

    public static GridPane enterBooks() throws IOException {

        //creacion del gridpane con sus caracteristicas
        GridPane gpn_enterBooks = new GridPane();
        gpn_enterBooks.setPadding(new Insets(20));
        gpn_enterBooks.setPrefSize(300, 300);
        //se define ancho de las columnas y filas
        gpn_enterBooks.getColumnConstraints().add(new ColumnConstraints(200));
        gpn_enterBooks.getColumnConstraints().add(new ColumnConstraints(250));
        gpn_enterBooks.getColumnConstraints().add(new ColumnConstraints(150));
        gpn_enterBooks.getColumnConstraints().add(new ColumnConstraints(200));
        gpn_enterBooks.getColumnConstraints().add(new ColumnConstraints(250));
        gpn_enterBooks.getRowConstraints().add(new RowConstraints(60));
        gpn_enterBooks.getRowConstraints().add(new RowConstraints(60));
        gpn_enterBooks.getRowConstraints().add(new RowConstraints(60));
        gpn_enterBooks.getRowConstraints().add(new RowConstraints(60));
        gpn_enterBooks.getRowConstraints().add(new RowConstraints(60));
        gpn_enterBooks.getRowConstraints().add(new RowConstraints(60));
        gpn_enterBooks.getRowConstraints().add(new RowConstraints(60));
        gpn_enterBooks.setAlignment(Pos.CENTER);

        Label lbl_name = new Label("Título");
        lbl_name.setTextFill(Color.BLACK);
        lbl_name.setFont(Font.font("Arial", FontWeight.BOLD, 15));
        gpn_enterBooks.add(lbl_name, 0, 0);

        TextField tfd_name = new TextField();
        gpn_enterBooks.add(tfd_name, 1, 0);
        
        Label lbl_signatureB = new Label("Signatura");
        lbl_signatureB.setTextFill(Color.BLACK);
        lbl_signatureB.setFont(Font.font("Arial", FontWeight.BOLD, 15));
        gpn_enterBooks.add(lbl_signatureB, 3, 0);

        TextField tfd_signatureB = new TextField();
        gpn_enterBooks.add(tfd_signatureB, 4, 0);

        Label lbl_author = new Label("Autor");
        lbl_author.setTextFill(Color.BLACK);
        lbl_author.setFont(Font.font("Arial", FontWeight.BOLD, 15));
        gpn_enterBooks.add(lbl_author, 0, 2);

        TextField tfd_author = new TextField();
        gpn_enterBooks.add(tfd_author, 1, 2);

        Label lbl_genre = new Label("Género");
        lbl_genre.setTextFill(Color.BLACK);
        lbl_genre.setFont(Font.font("Arial", FontWeight.BOLD, 15));
        gpn_enterBooks.add(lbl_genre, 3, 2);

        ComboBox cbx_genre = new ComboBox();
        cbx_genre.setPrefWidth(400);
        cbx_genre.getItems().addAll("Bibliografía", "Clásicos de la Literatura", "Comics", "Ensayos",
                "Fantasía", "Ficción Literaria", "Historia", "Humor", "Infantil", "Poesía", "Romántico",
                "Académico", "Otro");
        cbx_genre.setValue("Académico");

        cbx_genre.valueProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue ov, String t, String t1) {
                cbx_genre.setValue(t1);
            }
        });
        gpn_enterBooks.add(cbx_genre, 4, 2);

        Label lbl_idiom = new Label("Idioma");
        lbl_idiom.setTextFill(Color.BLACK);
        lbl_idiom.setFont(Font.font("Arial", FontWeight.BOLD, 15));
        gpn_enterBooks.add(lbl_idiom, 0, 4);

        ComboBox cbx_idiom = new ComboBox();
        cbx_idiom.setPrefWidth(400);
        cbx_idiom.getItems().addAll("Inglés", "Español", "Chino", "Alemán",
                "Frances", "Portugués", "Otro");
        cbx_idiom.setValue("Español");

        cbx_idiom.valueProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue ov, String t, String t1) {
                cbx_idiom.setValue(t1);
            }
        });
        gpn_enterBooks.add(cbx_idiom, 1, 4);

        Label lbl_description = new Label("Descripción");
        lbl_description.setTextFill(Color.BLACK);
        lbl_description.setFont(Font.font("Arial", FontWeight.BOLD, 15));
        gpn_enterBooks.add(lbl_description, 3, 4);

        TextArea txa_description = new TextArea();
        txa_description.setPrefSize(400, 500);
        gpn_enterBooks.add(txa_description, 4, 4);

        Button btn_enterBook = new Button("Ingresar Libro");
        btn_enterBook.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                
                genre = cbx_genre.getValue()+"";
                idiom = cbx_idiom.getValue()+"";
                try {
                    
                    BooksFile bfile = new BooksFile(new File("./Books.dat"));
                    
                    Books book1 = new Books(tfd_author.getText(), genre, idiom, tfd_name.getText(),tfd_signatureB.getText(), 1, txa_description.getText());
                    
                    Label lbl_success = new Label("¡Libro ingresado con exito!");
                    lbl_success.setTextFill(Color.GREEN);
                    lbl_success.setFont(Font.font("Arial", FontWeight.BOLD, 15));
                    lbl_success.setVisible(false);
                    gpn_enterBooks.add(lbl_success, 3, 7, 4, 4);
                    
                    Label lbl_error = new Label("Llene todos los datos");
                    lbl_error.setVisible(false);
                    gpn_enterBooks.add(lbl_error, 3, 7, 4, 4);

                    if(bfile.avaibilityBook(book1.getSignature(),1)){
                        lbl_success.setVisible(true);
                        
                    } else if(tfd_author.getText().isEmpty() || tfd_name.getText().isEmpty() || tfd_signatureB.getText().isEmpty() || txa_description.getText().isEmpty()){
                        lbl_error.setVisible(true);
                        lbl_success.setVisible(false);
                    }else{
                        lbl_success.setVisible(true);
                        
                        
                    
                        bfile.addEndRecord(book1);
                        bfile.close();
                    }
                    
                    
                    
                } catch (IOException ex) {
                    Logger.getLogger(InterfaceModules.class.getName()).log(Level.SEVERE, "Error insertando libro", ex);
                }

                tfd_name.setText("");
                tfd_author.setText("");
                tfd_signatureB.setText("");
                txa_description.setText("");
                cbx_genre.setPromptText("");
                cbx_idiom.setPromptText("");
            }
        });
        gpn_enterBooks.add(btn_enterBook, 4, 6);

        return gpn_enterBooks;
    }

    public static GridPane enterAudioVisual() {

        GridPane gpn_enterAudioV = new GridPane();

        gpn_enterAudioV.getColumnConstraints().add(new ColumnConstraints(200));
        gpn_enterAudioV.getColumnConstraints().add(new ColumnConstraints(250));
        gpn_enterAudioV.getColumnConstraints().add(new ColumnConstraints(150));
        gpn_enterAudioV.getColumnConstraints().add(new ColumnConstraints(200));
        gpn_enterAudioV.getColumnConstraints().add(new ColumnConstraints(250));
        gpn_enterAudioV.getRowConstraints().add(new RowConstraints(60));
        gpn_enterAudioV.getRowConstraints().add(new RowConstraints(60));
        gpn_enterAudioV.getRowConstraints().add(new RowConstraints(60));
        gpn_enterAudioV.getRowConstraints().add(new RowConstraints(60));
        gpn_enterAudioV.getRowConstraints().add(new RowConstraints(60));
        gpn_enterAudioV.getRowConstraints().add(new RowConstraints(60));
        gpn_enterAudioV.getRowConstraints().add(new RowConstraints(60));
        gpn_enterAudioV.setAlignment(Pos.CENTER);
        gpn_enterAudioV.setPadding(new Insets(20));
        gpn_enterAudioV.setPrefSize(300, 300);

        Label lbl_kind = new Label("Tipo:");
        lbl_kind.setTextFill(Color.BLACK);
        lbl_kind.setFont(Font.font("Arial", FontWeight.BOLD, 15));
        gpn_enterAudioV.add(lbl_kind, 0, 0);

        TextField tfd_kind = new TextField();
        gpn_enterAudioV.add(tfd_kind, 1, 0);

        Label lbl_signatureAV = new Label("Signatura:");
        lbl_signatureAV.setTextFill(Color.BLACK);
        lbl_signatureAV.setFont(Font.font("Arial", FontWeight.BOLD, 15));
        gpn_enterAudioV.add(lbl_signatureAV, 3, 0);

        TextField tfd_signatureAV = new TextField();
        gpn_enterAudioV.add(tfd_signatureAV, 4, 0);

        Label lbl_brand = new Label("Marca:");
        lbl_brand.setTextFill(Color.BLACK);
        lbl_brand.setFont(Font.font("Arial", FontWeight.BOLD, 15));
        gpn_enterAudioV.add(lbl_brand, 0, 2);

        TextField tfd_brand = new TextField();
        gpn_enterAudioV.add(tfd_brand, 1, 2);

//        Label lbl_model = new Label("Modelo:");
//        lbl_model.setTextFill(Color.BLACK);
//        lbl_model.setFont(Font.font("Arial", FontWeight.BOLD, 15));
//        gpn_enterAudioV.add(lbl_model, 3, 2);
//
//        TextField tfd_model = new TextField();
//        gpn_enterAudioV.add(tfd_model, 4, 2);

        Label lbl_description = new Label("Descripción:");
        lbl_description.setTextFill(Color.BLACK);
        lbl_description.setFont(Font.font("Arial", FontWeight.BOLD, 15));
        gpn_enterAudioV.add(lbl_description, 0, 4);

        TextArea txa_description = new TextArea();
        txa_description.setPrefSize(300, 400);
        gpn_enterAudioV.add(txa_description, 1, 4);

        Button btn_enterAudioV = new Button("Ingresar Artículo");
        btn_enterAudioV.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                try {
                    
                    AudioVisualFile afile = new AudioVisualFile(new File("./AudioVisual.dat"));
                    
                    AudioVisual audioVisual = new AudioVisual(tfd_brand.getText(), tfd_kind.getText(), tfd_signatureAV.getText(), 1, txa_description.getText());
                    
                   
                    Label lbl_success = new Label("¡AudioVisual ingresado con exito!");
                    lbl_success.setTextFill(Color.GREEN);
                    lbl_success.setFont(Font.font("Arial", FontWeight.BOLD, 15));
                    lbl_success.setVisible(false);
                        gpn_enterAudioV.add(lbl_success, 3, 7, 4, 4);

                    Label lbl_error = new Label("Llene todos los datos");
                    lbl_error.setVisible(false);
                        gpn_enterAudioV.add(lbl_error, 3, 7, 4, 4);
                    
                    //condicional que verifica si y existe para aumentar disponibilidad o ingresar como articulo nuevo
                    if(afile.avaibilityAudioVisual(audioVisual.getSignature(),1)){
                        lbl_success.setVisible(true);
                        lbl_error.setVisible(false);
                    }else if(tfd_brand.getText().isEmpty() || tfd_kind.getText().isEmpty() || tfd_signatureAV.getText().isEmpty() || txa_description.getText().isEmpty()){
                        lbl_success.setVisible(false);
                        lbl_error.setVisible(true);
                    }else{
                        lbl_success.setVisible(true);
                        lbl_error.setVisible(false);
                        afile.addEndRecord(audioVisual);
                        afile.close();
                    }    
                    
                    
                } catch (IOException ex) {
                    Logger.getLogger(InterfaceModules.class.getName()).log(Level.SEVERE, "Error insertando libro", ex);
                }

                tfd_kind.setText("");
                //tfd_model.setText("");
                tfd_signatureAV.setText("");
                txa_description.setText("");
                tfd_brand.setPromptText("");
            }
        });
        gpn_enterAudioV.add(btn_enterAudioV, 4, 6);

        return gpn_enterAudioV;
    }//end method

    public static GridPane viewMaterial() throws IOException {

        //declaracion del gridpane y sus caracteristicas
        GridPane gpn_viewMaterial = new GridPane();
        gpn_viewMaterial.setAlignment(Pos.TOP_CENTER);
        gpn_viewMaterial.setPadding(new Insets(20));
        gpn_viewMaterial.setPrefSize(700, 800);
        gpn_viewMaterial.setVgap(12);
        gpn_viewMaterial.setHgap(12);

        
        //TableView que muestra materiales audiovisuales
        TableView<AudioVisual> tvw_viewAudiovisual = new TableView();
        tvw_viewAudiovisual.setVisible(false);
        
        
        AudioVisualFile avf = new AudioVisualFile(new File("./AudioVisual.dat"));
        ObservableList<AudioVisual> audioVisuals = avf.getAudioVisuals();
        tvw_viewAudiovisual.setItems(audioVisuals);

//      Falta el observableList // agregado arriba        
        TableColumn nameAVColumn = new TableColumn("Nombre");
        nameAVColumn.setMinWidth(150);
        nameAVColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn signatureAVColumn = new TableColumn("Signatura");
        signatureAVColumn.setMinWidth(150);
        signatureAVColumn.setCellValueFactory(new PropertyValueFactory<>("signature"));

        TableColumn brandAVColumn = new TableColumn("Marca");
        brandAVColumn.setMinWidth(150);
        brandAVColumn.setCellValueFactory(new PropertyValueFactory<>("brand"));

        TableColumn availabilityAVColumn = new TableColumn("Disponibilidad");
        availabilityAVColumn.setMinWidth(150);
        availabilityAVColumn.setCellValueFactory(new PropertyValueFactory<>("availability"));

        TableColumn descritionAVColumn = new TableColumn("Descripcion");
        descritionAVColumn.setMinWidth(150);
        descritionAVColumn.setCellValueFactory(new PropertyValueFactory<>("description"));

        tvw_viewAudiovisual.getColumns().addAll(nameAVColumn, signatureAVColumn, brandAVColumn, availabilityAVColumn, descritionAVColumn);
        tvw_viewAudiovisual.setPrefSize(750, 475);
        tvw_viewAudiovisual.setTableMenuButtonVisible(true);
        gpn_viewMaterial.add(tvw_viewAudiovisual, 1, 1);
        
        HBox hbx_2nodes = new HBox();
        hbx_2nodes.setSpacing(10);
        
        //label por si no selecciono un item en la tabla
        Label lbl_unselected_row = new Label("No ha seleccionado un ejemplar");
        lbl_unselected_row.setVisible(false);
        lbl_unselected_row.setTextFill(Color.RED);
        lbl_unselected_row.setFont(Font.font("Arial", FontWeight.BOLD, 15));
        
        //exito al ingresar ejemplares
        Label lbl_success_row = new Label("Increased succesfully!");
        lbl_success_row.setVisible(false);
        lbl_success_row.setTextFill(Color.GREEN);
        lbl_success_row.setFont(Font.font("Arial", FontWeight.BOLD, 15));
        
        //boton para aumentar la disponibilidad audiovisual
        Button btn_increaseAvaibility = new Button("Aumentar disponibilidad");
        btn_increaseAvaibility.setVisible(false);
        
        //se annaden el boton y el label a un hbox para que ambos esten debajo de la tabla
        hbx_2nodes.getChildren().addAll(btn_increaseAvaibility, lbl_unselected_row, lbl_success_row);
        gpn_viewMaterial.add(hbx_2nodes, 1, 3);
        btn_increaseAvaibility.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                
                AudioVisual av1 = tvw_viewAudiovisual.getSelectionModel().getSelectedItem();
                
                if(av1 != null){
                    
                    lbl_unselected_row.setVisible(false);
                    //ventana de alerta para ingresar el aumento de disponibilidad
                    List<String> choices = new ArrayList<>();
                    choices.add("1");
                    choices.add("2");
                    choices.add("3");
                    choices.add("4");
                    choices.add("5");
                    choices.add("6");
                    choices.add("7");
                    choices.add("8");
                    choices.add("9");
                    choices.add("10");
                    
                    ChoiceDialog<String> dialog = new ChoiceDialog<>("1", choices);
                    dialog.setTitle("Disponibilidad");
                    dialog.setHeaderText("Ingrese el aumento de AudioVisuales");
                    dialog.setContentText("Cantidad");
                    
                    // obtener el valor.
                    Optional<String> result = dialog.showAndWait();
                    if (result.isPresent()){
                        try {
                            AudioVisualFile avf = new AudioVisualFile(new File("./AudioVisual.dat"));
                            
                            int quanty = Integer.parseInt(result.get());
                            if(avf.avaibilityAudioVisual(av1.getSignature(),quanty)){
                                ObservableList<AudioVisual> audioVisuals = avf.getAudioVisuals();
                                tvw_viewAudiovisual.setItems(audioVisuals);
                                lbl_success_row.setVisible(true);
                            }else{
                                
                            }
                        } catch (IOException ex) {
                            Logger.getLogger(InterfaceModules.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        
                        
                    }
                } else { //si no selecciono nada en la tabla
                    lbl_unselected_row.setVisible(true);
                    lbl_success_row.setVisible(false);
                }
            
        }
        });

        
        //TableView que muestra materiales: libros
        TableView<Books> tvw_viewBooks = new TableView();

        BooksFile bfile = new BooksFile(new File("./Books.dat"));
        ObservableList<Books> datos = bfile.getBooks();
        tvw_viewBooks.setItems(datos);

        TableColumn nameBColumn = new TableColumn("Nombre");
        nameBColumn.setMinWidth(150);
        nameBColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn authorBColumn = new TableColumn("Autor");
        authorBColumn.setMinWidth(150);
        authorBColumn.setCellValueFactory(new PropertyValueFactory<>("autor"));

        TableColumn signatureBColumn = new TableColumn("Signatura");
        signatureBColumn.setMinWidth(150);
        signatureBColumn.setCellValueFactory(new PropertyValueFactory<>("signature"));

        TableColumn genreBColumn = new TableColumn("Género");
        genreBColumn.setMinWidth(150);
        genreBColumn.setCellValueFactory(new PropertyValueFactory<>("genre"));

        TableColumn lenguageBColumn = new TableColumn("Idioma");
        lenguageBColumn.setMinWidth(150);
        lenguageBColumn.setCellValueFactory(new PropertyValueFactory<>("language"));

        TableColumn availabilityBColumn = new TableColumn("Disponibilidad");
        availabilityBColumn.setMinWidth(10);
        availabilityBColumn.setCellValueFactory(new PropertyValueFactory<>("availability"));

        TableColumn descriptionBColumn = new TableColumn("Descripción");
        descriptionBColumn.setMinWidth(220);
        descriptionBColumn.setCellValueFactory(new PropertyValueFactory<>("description"));

        tvw_viewBooks.getColumns().addAll(nameBColumn, authorBColumn, signatureBColumn, genreBColumn,
                lenguageBColumn, availabilityBColumn, descriptionBColumn);
        tvw_viewBooks.setPrefSize(1100, 475);
        gpn_viewMaterial.add(tvw_viewBooks, 1, 1);
        
        
        HBox hbx_2nodesB = new HBox();
        hbx_2nodesB.setSpacing(10);
        
        //label por si no selecciono un item en la tabla
        Label lbl_unselected_rowB = new Label("No ha seleccionado un ejemplar");
        lbl_unselected_rowB.setVisible(false);
        lbl_unselected_rowB.setTextFill(Color.RED);
        lbl_unselected_rowB.setFont(Font.font("Arial", FontWeight.BOLD, 15));
        
        //exito al ingresar ejemplares
        Label lbl_success_rowB = new Label("Increased succesfully!");
        lbl_success_rowB.setVisible(false);
        lbl_success_rowB.setTextFill(Color.GREEN);
        lbl_success_rowB.setFont(Font.font("Arial", FontWeight.BOLD, 15));
        
        //inrementalibros
        Button btn_increaseAvaibilityB = new Button("Aumentar disponibilidad");
        btn_increaseAvaibilityB.setVisible(true);
        btn_increaseAvaibilityB.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                
                Books book1 = tvw_viewBooks.getSelectionModel().getSelectedItem();
                
                if(book1 != null){
                    
                    lbl_unselected_rowB.setVisible(false);
                    
                    //ventana de alerta para ingresar el aumento de disponibilidad
                    List<String> choices = new ArrayList<>();
                    choices.add("1");
                    choices.add("2");
                    choices.add("3");
                    choices.add("4");
                    choices.add("5");
                    choices.add("6");
                    choices.add("7");
                    choices.add("8");
                    choices.add("9");
                    choices.add("10");
                    
                    ChoiceDialog<String> dialog = new ChoiceDialog<>("1", choices);
                    dialog.setTitle("Disponibilidad");
                    dialog.setHeaderText("Ingrese el aumento de libros");
                    dialog.setContentText("Cantidad");
                    
                    // obtener el valor.
                    Optional<String> result = dialog.showAndWait();
                    if (result.isPresent()){
                        
                            
                        try {
                            int quanty = Integer.parseInt(result.get());
                            if(bfile.avaibilityBook(book1.getSignature(),quanty)){
                                ObservableList<Books> books = bfile.getBooks();
                                tvw_viewBooks.setItems(books);
                                lbl_success_rowB.setVisible(true);
                            }else{
                                
                            }
                        } catch (IOException ex) {
                            Logger.getLogger(InterfaceModules.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        
                    }
                } else { //si no selecciono nada en la columna
                    lbl_unselected_rowB.setVisible(true);
                    lbl_success_rowB.setVisible(false);
                }
            
        }
        });
        
        //se annaden el boton y el label para que deja debajo de la tabla
        hbx_2nodesB.getChildren().addAll(btn_increaseAvaibilityB, lbl_unselected_rowB, lbl_success_rowB);
        gpn_viewMaterial.add(hbx_2nodesB, 1, 3);
        
        Label lbl_titleBooks = new Label("Libros");
        lbl_titleBooks.setFont(Font.font("Opens Sans"));
        lbl_titleBooks.setStyle("-fx-font-weight: bold; -fx-font-size: 25");
        gpn_viewMaterial.add(lbl_titleBooks, 1, 2);
        
        Label lbl_titleAudioVisuals = new Label("AudioVisuales");
        lbl_titleAudioVisuals.setVisible(false);
        lbl_titleAudioVisuals.setFont(Font.font("Opens Sans"));
        lbl_titleAudioVisuals.setStyle("-fx-font-weight: bold; -fx-font-size: 25");
        gpn_viewMaterial.add(lbl_titleAudioVisuals, 1, 2);
        
        btn_increaseAvaibility.setVisible(true);

        Button btn_viewBooks = new Button("Ver libros");
        btn_viewBooks.setOnAction((event) -> {
            tvw_viewAudiovisual.setVisible(false);
            lbl_titleAudioVisuals.setVisible(false);
            tvw_viewBooks.setVisible(true);
            lbl_titleBooks.setVisible(true);
            btn_increaseAvaibilityB.setVisible(true);
            btn_increaseAvaibility.setVisible(false);
            hbx_2nodesB.setVisible(true);
            hbx_2nodes.setVisible(false);
            lbl_success_row.setVisible(false);
            lbl_unselected_row.setVisible(false);
        });
        gpn_viewMaterial.add(btn_viewBooks, 0, 0);

        Button btn_viewAudiov = new Button("Ver Audiovisual");
        btn_viewAudiov.setOnAction((event) -> {
            tvw_viewBooks.setVisible(false);
            lbl_titleBooks.setVisible(false);
            tvw_viewAudiovisual.setVisible(true);
            lbl_titleAudioVisuals.setVisible(true);
            btn_increaseAvaibility.setVisible(true);
            btn_increaseAvaibilityB.setVisible(false);
            hbx_2nodes.setVisible(true);
            hbx_2nodesB.setVisible(false);
            lbl_success_rowB.setVisible(false);
            lbl_unselected_rowB.setVisible(false);
        });
        gpn_viewMaterial.add(btn_viewAudiov, 1, 0);
        
        

        return gpn_viewMaterial;

    }

   public static GridPane enterLoan() throws IOException {

        LoanFile lFile = new LoanFile(new File("./Loans.dat"));

        GridPane gpn_enterLoan = new GridPane();

        //Acomodar las columnas y las filas en el tamaño que sea necesario
        gpn_enterLoan.getColumnConstraints().add(new ColumnConstraints(200));
        gpn_enterLoan.getColumnConstraints().add(new ColumnConstraints(250));
        gpn_enterLoan.getColumnConstraints().add(new ColumnConstraints(150));
        gpn_enterLoan.getColumnConstraints().add(new ColumnConstraints(200));
        gpn_enterLoan.getColumnConstraints().add(new ColumnConstraints(250));
        gpn_enterLoan.getRowConstraints().add(new RowConstraints(60));
        gpn_enterLoan.getRowConstraints().add(new RowConstraints(60));
        gpn_enterLoan.getRowConstraints().add(new RowConstraints(60));
        gpn_enterLoan.getRowConstraints().add(new RowConstraints(60));
        gpn_enterLoan.getRowConstraints().add(new RowConstraints(60));
        gpn_enterLoan.getRowConstraints().add(new RowConstraints(60));
        gpn_enterLoan.getRowConstraints().add(new RowConstraints(60));
        gpn_enterLoan.setAlignment(Pos.CENTER);
        gpn_enterLoan.setPadding(new Insets(20));
        gpn_enterLoan.setPrefSize(300, 300);

        lbl_idStudent = new Label("Carné del Estudiante");
        lbl_idStudent.setTextFill(Color.BLACK);
        lbl_idStudent.setFont(Font.font("Arial", FontWeight.BOLD, 15));
        gpn_enterLoan.add(lbl_idStudent, 0, 3);
        
        Label lbl_exception= new Label("Estudiante no encontrado");
        lbl_exception.setTextFill(Color.RED);
        lbl_exception.setFont(Font.font("Arial", FontWeight.BOLD, 15));
        lbl_exception.setVisible(false);
        gpn_enterLoan.add(lbl_exception, 1, 4);

        tfd_idStudent = new TextField();
        
        tfd_idStudent.setOnAction((event) -> {
            try {
                LogicalMethods methods= new LogicalMethods();
                if(!methods.checkStudentRecord(tfd_idStudent.getText())){
                    tfd_idStudent.clear();
                    lbl_exception.setVisible(true);
                    btn_checkStudent.setDisable(true);
                }else{
                    btn_checkStudent.setDisable(false);
                    lbl_exception.setVisible(false);
                }
            } catch (FileNotFoundException | ClassNotFoundException | OptionalDataException ex) {
                Logger.getLogger(InterfaceModules.class.getName()).log(Level.SEVERE, null, ex);
            }            
        });
        
        gpn_enterLoan.add(tfd_idStudent, 1, 3);

        Label lbl_notValue = new Label("Ingresar un valor");
        lbl_notValue.setVisible(false);
        lbl_notValue.setTextFill(Color.RED);
        lbl_notValue.setFont(Font.font("Arial", FontWeight.BOLD, 15));
        gpn_enterLoan.add(lbl_notValue, 2, 4, 3, 4);

        btn_checkStudent = new Button("Ingresar");
        btn_checkStudent.setOnAction((event) -> {

            if (tfd_idStudent.getText().length() == 0) {
                lbl_notValue.setVisible(true);
            } else {
                lbl_notValue.setVisible(false);
                String student = tfd_idStudent.getText().replaceAll(" ", "");
                lbl_idStudent.setVisible(false);
                tfd_idStudent.setVisible(false);
                btn_checkStudent.setVisible(false);
                lbl_choise.setVisible(true);
                rdb_choiceAV.setVisible(true);
                rdb_choiceBook.setVisible(true);

            }

        });//end Button
        gpn_enterLoan.add(btn_checkStudent, 3, 3);

        lbl_choise = new Label("Seleccione una opción");
        lbl_choise.setVisible(false);
        lbl_choise.setTextFill(Color.BLACK);
        lbl_choise.setFont(Font.font("Arial", FontWeight.BOLD, 15));
        gpn_enterLoan.add(lbl_choise, 0, 0);

        lbl_signature = new Label("Signatura del Artículo");
        lbl_signature.setVisible(false);
        lbl_signature.setTextFill(Color.BLACK);
        lbl_signature.setFont(Font.font("Arial", FontWeight.BOLD, 15));
        gpn_enterLoan.add(lbl_signature, 0, 2);

        tfd_signatureB = new TextField("ISBN-");
        tfd_signatureB.setVisible(false);
        gpn_enterLoan.add(tfd_signatureB, 1, 2);

        lbl_deliveryDay = new Label("Día de Entrega");
        lbl_deliveryDay.setVisible(false);
        lbl_deliveryDay.setTextFill(Color.BLACK);
        lbl_deliveryDay.setFont(Font.font("Arial", FontWeight.BOLD, 15));
        gpn_enterLoan.add(lbl_deliveryDay, 3, 2);

        DatePicker dpk_loanDay = new DatePicker(LocalDate.now());
        dpk_loanDay.setEditable(false);

        dpk_delivaeyDay = new DatePicker();
        dpk_delivaeyDay.setPrefWidth(250);
        dpk_delivaeyDay.setEditable(false);
        dpk_delivaeyDay.setVisible(false);
        dpk_delivaeyDay.setShowWeekNumbers(true);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        dpk_delivaeyDay.setConverter(new LocalDateStringConverter(formatter, null));
        dpk_delivaeyDay.setConverter(new LocalDateStringConverter(FormatStyle.FULL)); //Fecha en un formato String

        dpk_delivaeyDay.setOnAction(new EventHandler() {
            public void handle(Event t) {
                date1 = dpk_delivaeyDay.getValue();
                valueDelivery1 = date1.getDayOfMonth();
                valueDelivery2 = date1.getMonthValue();
                valueDelivery3 = date1.getYear();
                dpk_delivaeyDay.setDisable(false);
            }
        });

//        Metodo para deshabilitar los dias anteriores al actual
        final Callback<DatePicker, DateCell> dayCellFactory
                = new Callback<DatePicker, DateCell>() {
            @Override
            public DateCell call(final DatePicker datePicker) {
                return new DateCell() {
                    @Override
                    public void updateItem(LocalDate item, boolean empty) {
                        super.updateItem(item, empty);

                        if (item.isBefore(dpk_loanDay.getValue().plusDays(-1))) {
                            setDisable(true);
                            setStyle("-fx-background-color: #ffc0cb;");
                        }
                    }
                };
            }
        };
        dpk_delivaeyDay.setDayCellFactory(dayCellFactory);
        dpk_delivaeyDay.setValue(dpk_loanDay.getValue().plusDays(0));
        gpn_enterLoan.add(dpk_delivaeyDay, 4, 2);

        lbl_warning = new Label("Prestamo no registrado");
        lbl_warning.setVisible(false);
        lbl_warning.setTextFill(Color.RED);
        lbl_warning.setFont(Font.font("Arial", FontWeight.BOLD, 15));
        gpn_enterLoan.add(lbl_warning, 3, 4, 4, 6);

        lbl_success = new Label("Se registro el prestamo");
        lbl_success.setVisible(false);
        lbl_success.setTextFill(Color.GREEN);
        lbl_success.setFont(Font.font("Arial", FontWeight.BOLD, 15));
        gpn_enterLoan.add(lbl_success, 3, 4, 4, 6);

        ToggleGroup group = new ToggleGroup();

        rdb_choiceBook = new RadioButton("Libro");
        rdb_choiceBook.setVisible(false);
        rdb_choiceBook.setToggleGroup(group);
        rdb_choiceBook.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {

                try {
                    tfd_signatureAV.setVisible(false);
                    lbl_signature.setVisible(true);
                    tfd_signatureB.setVisible(true);
                    lbl_deliveryDay.setVisible(true);
                    dpk_delivaeyDay.setVisible(true);
                    btn_enterLoan.setVisible(true);
                    btn_exit.setVisible(true);
                    
                    //tfd_signatureB.setText("ISBN-");
                    LogicalMethods methods= new LogicalMethods();
                    String options[]= methods.autocompleteOptions();
                    TextFields.bindAutoCompletion(tfd_signatureB, options);
                    
                    btn_enterLoan.setDisable(true);                                       
                    dpk_delivaeyDay.setValue(LocalDate.now());
                    
                } catch (IOException ex) {
                    Logger.getLogger(InterfaceModules.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
        });
        gpn_enterLoan.add(rdb_choiceBook, 0, 1);

        tfd_signatureAV = new TextField();
        tfd_signatureAV.setVisible(false);
        //metodo para establecer un tamaño maximo de ingreso de valores en un TextField
        tfd_signatureAV.lengthProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable,
                    Number beforeValue, Number actualValue) {
                if (actualValue.intValue() > beforeValue.intValue()) {
                    // Revisa que la longitud del texto no sea mayor a la variable definida.
                    if (tfd_signatureAV.getText().length() >= 5) {
                        tfd_signatureAV.setText(tfd_signatureAV.getText().substring(0, 5));
                    }
                }
            }
        });
        gpn_enterLoan.add(tfd_signatureAV, 1, 2);

        rdb_choiceAV = new RadioButton("Audiovisual");
        rdb_choiceAV.setVisible(false);
        rdb_choiceAV.setToggleGroup(group);
        rdb_choiceAV.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {

                tfd_signatureB.setVisible(false);
                lbl_signature.setVisible(true);
                tfd_signatureAV.setVisible(true);
                lbl_deliveryDay.setVisible(true);
                dpk_delivaeyDay.setVisible(true);
                btn_enterLoan.setVisible(true);
                btn_exit.setVisible(true);
                tfd_signatureAV.setText("");
                dpk_delivaeyDay.setValue(LocalDate.now());

            }
        });
        gpn_enterLoan.add(rdb_choiceAV, 1, 1);

        lbl_info = new Label("Salir para realizar otro prestamo");
        lbl_info.setVisible(false);
        gpn_enterLoan.add(lbl_info, 2, 6, 3, 6);

        Label lbl_notValueEnter = new Label("Llenar todos lo espacios");
        lbl_notValueEnter.setVisible(false);
        lbl_notValueEnter.setTextFill(Color.RED);
        lbl_notValueEnter.setFont(Font.font("Arial", FontWeight.BOLD, 15));
        gpn_enterLoan.add(lbl_notValueEnter, 3, 4, 4, 6);

        btn_enterLoan = new Button("Ingresar Prestamo");
        btn_enterLoan.setVisible(false);
        btn_enterLoan.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                String idStudent = tfd_idStudent.getText().replaceAll(" ", "");

                if ((tfd_signatureB.getText().replaceAll(" ", "").length() == 5
                        && tfd_signatureAV.getText().replaceAll(" ", "").length() == 0) || valueDelivery3 == 0) {
                    lbl_notValueEnter.setVisible(true);

                } else {
                    signatureB = tfd_signatureB.getText();
                    signatureAV = tfd_signatureAV.getText();
                    String loanDay = "" + LocalDate.now();
                    String deliveryDay = "" + date1;
                    String kind = "";

                    if (rdb_choiceAV.isSelected()) {
                        kind = "Audiovisual";
                        loan1 = new Loan(idStudent, signatureAV, loanDay, deliveryDay, kind);
                    } else {
                        kind = "Libro";
                        loan1 = new Loan(idStudent, signatureB, loanDay, deliveryDay, kind);
                    }
                    try {

                        lFile.addEndRecord(loan1);
                        lFile.close();

                        lbl_notValueEnter.setVisible(false);
                        tfd_signatureB.setDisable(true);
                        tfd_signatureAV.setDisable(true);
                        dpk_delivaeyDay.setDisable(true);
                        rdb_choiceBook.setDisable(true);
                        rdb_choiceAV.setDisable(true);
                        btn_enterLoan.setDisable(true);
                        lbl_info.setVisible(true);

                        //Se limpian los valores anteriormente ingresados 
                        tfd_idStudent.setText("");
                        tfd_signatureB.setText("");
                        dpk_delivaeyDay.setValue(LocalDate.now()); //Se asigna el valor por defecto del DatePicker

                    } catch (IOException ex) {
                        Logger.getLogger(InterfaceModules.class.getName()).log(Level.SEVERE, null, ex);
                    }

                }

            }
        });
        gpn_enterLoan.add(btn_enterLoan, 4, 4);

        btn_exit = new Button("Salir");
        btn_exit.setVisible(false);
        btn_exit.setOnAction((event) -> {
            
//            lbl_signature.setVisible(false);
//            tfd_signatureB.setVisible(false);
//            lbl_deliveryDay.setVisible(false);
//            dpk_delivaeyDay.setVisible(false);
//            lbl_choise.setVisible(false);
//            rdb_choiceBook.setVisible(false);
//            rdb_choiceAV.setVisible(false);
//            btn_enterLoan.setVisible(false);
//            lbl_warning.setVisible(false);
//            lbl_success.setVisible(false);
//            lbl_info.setVisible(false);
//            btn_exit.setVisible(false);
//            tfd_signatureAV.setVisible(false);
//            lbl_idStudent.setVisible(true);
//            tfd_idStudent.setVisible(true);
//            btn_checkStudent.setVisible(true);
//            tfd_idStudent.setText("");
//            tfd_signatureAV.setText("");
//            tfd_signatureB.setText("");
//            dpk_delivaeyDay.setValue(LocalDate.now());
//            tfd_signatureB.setDisable(false);
//            tfd_signatureAV.setDisable(false);
//            dpk_delivaeyDay.setDisable(false);
//            rdb_choiceBook.setDisable(false);
//            rdb_choiceAV.setDisable(false);
//            btn_enterLoan.setDisable(false);
        });
        gpn_enterLoan.add(btn_exit, 4, 6);

        return gpn_enterLoan;

    }//end method

    public static GridPane deleteLoans() throws IOException {

        LoanFile lfile = new LoanFile(new File("./Loans.dat"));

        GridPane gpn_deleteLoan = new GridPane();
        gpn_deleteLoan.getColumnConstraints().add(new ColumnConstraints(150));
        gpn_deleteLoan.getColumnConstraints().add(new ColumnConstraints(150));
        gpn_deleteLoan.getColumnConstraints().add(new ColumnConstraints(150));
        gpn_deleteLoan.getColumnConstraints().add(new ColumnConstraints(250));
        gpn_deleteLoan.getColumnConstraints().add(new ColumnConstraints(250));
        gpn_deleteLoan.getColumnConstraints().add(new ColumnConstraints(150));
        gpn_deleteLoan.getRowConstraints().add(new RowConstraints(60));
        gpn_deleteLoan.getRowConstraints().add(new RowConstraints(60));
        gpn_deleteLoan.getRowConstraints().add(new RowConstraints(60));
        gpn_deleteLoan.getRowConstraints().add(new RowConstraints(60));
        gpn_deleteLoan.getRowConstraints().add(new RowConstraints(60));
        gpn_deleteLoan.getRowConstraints().add(new RowConstraints(60));
        gpn_deleteLoan.getRowConstraints().add(new RowConstraints(60));
        gpn_deleteLoan.setAlignment(Pos.CENTER);
        gpn_deleteLoan.setPadding(new Insets(20));
        gpn_deleteLoan.setPrefSize(300, 300);

        Label lbl_signatLoan = new Label("Signatura");
        gpn_deleteLoan.add(lbl_signatLoan, 0, 2);

        TextField tfd_signatLoan = new TextField();
        gpn_deleteLoan.add(tfd_signatLoan, 1, 2);

        Label lbl_idLoan = new Label("Carné estudiante");
        gpn_deleteLoan.add(lbl_idLoan, 3, 2);

        TextField tfd_idLoan = new TextField();        
        gpn_deleteLoan.add(tfd_idLoan, 4, 2);

        Label lbl_warningL = new Label("Prestamo no resgistrado");
        lbl_warningL.setVisible(false);
        lbl_warningL.setTextFill(Color.RED);
        gpn_deleteLoan.add(lbl_warningL, 4, 3);

        Label lbl_loans = new Label("Prestamo");
        lbl_loans.setVisible(false);
        gpn_deleteLoan.add(lbl_loans, 0, 0);

        Label lbl_idStudentL = new Label();
        lbl_idStudentL.setTextFill(Color.BROWN);
        gpn_deleteLoan.add(lbl_idStudentL, 1, 1);

        Label lbl_signatureL = new Label();
        lbl_signatureL.setTextFill(Color.BROWN);
        gpn_deleteLoan.add(lbl_signatureL, 2, 1);

        Label lbl_loanDayL = new Label();
        lbl_loanDayL.setTextFill(Color.BROWN);
        gpn_deleteLoan.add(lbl_loanDayL, 3, 1);

        Label lbl_deliveyDayL = new Label();
        lbl_deliveyDayL.setTextFill(Color.BROWN);
        gpn_deleteLoan.add(lbl_deliveyDayL, 4, 1);

        Label lbl_kindL = new Label();
        lbl_kindL.setTextFill(Color.BROWN);
        gpn_deleteLoan.add(lbl_kindL, 5, 1);

        Label lbl_payL = new Label("Pago");
        lbl_payL.setVisible(false);
        gpn_deleteLoan.add(lbl_payL, 0, 2);

        Label lbl_payLoan = new Label();
        lbl_payLoan.setTextFill(Color.BROWN);
        gpn_deleteLoan.add(lbl_payLoan, 1, 2, 3, 3);

        Button btn_search = new Button("Buscar");
        btn_search.setOnAction((ActionEvent event) -> {

            signature = tfd_signatLoan.getText().replaceAll(" ", "");
            idLoan = tfd_idLoan.getText().replaceAll(" ", "");

            try {
                if (lfile.searchDeleteLoan(signature, idLoan) == -1) {
                    lbl_warningL.setVisible(true);

                } else {

                    lbl_warningL.setVisible(false);
                    lbl_signatLoan.setVisible(false);
                    tfd_signatLoan.setVisible(false);
                    lbl_idLoan.setVisible(false);
                    tfd_idLoan.setVisible(false);
                    btn_search.setVisible(false);
                    lbl_loans.setVisible(true);
                    lbl_idStudentL.setText("Carné: " + lfile.getLoan(lfile.searchLoan(signature)).getStudentId());
                    lbl_signatureL.setText("Signatura: " + lfile.getLoan(lfile.searchLoan(signature)).getSignature());
                    lbl_loanDayL.setText("Día de prestamo: " + lfile.getLoan(lfile.searchLoan(signature)).getLoanDay());
                    lbl_deliveyDayL.setText("Día de devolución: " + lfile.getLoan(lfile.searchLoan(signature)).getDeliveryDay());
                    lbl_kindL.setText("Articulo: " + lfile.getLoan(lfile.searchLoan(signature)).getKind());
                    lbl_payL.setVisible(true);
                    btn_delete.setVisible(true);

                    Calendar calendar = new GregorianCalendar();
                    int year = calendar.get(Calendar.YEAR);
                    int month = calendar.get(Calendar.MONTH) + 1;
                    int day = calendar.get(Calendar.DAY_OF_MONTH);

                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                    LocalDate localDate = LocalDate.parse(lfile.getLoan(lfile.searchDeleteLoan(signature, idLoan)).getDeliveryDay(), formatter);
                    int value1 = localDate.getDayOfMonth();
                    int value2 = localDate.getMonthValue();
                    int value3 = localDate.getYear();

                    long balance = lfile.numberOfDays(value1, value2, value3, day, month, year);

                    if (balance >= 365) {
                        lbl_payLoan.setText("Sin morosidades");
                    } else {
                        lbl_payLoan.setText(lfile.fineOfPayment(balance) + " colones por " + balance + " dias retrasados");
                    }

                }
            } catch (IOException ex) {
                Logger.getLogger(InterfaceModules.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
        );
        gpn_deleteLoan.add(btn_search, 5, 4);

        Label lbl_successL = new Label("Se ha eliminado con exito");
        lbl_successL.setTextFill(Color.GREEN);
        lbl_successL.setVisible(false);
        gpn_deleteLoan.add(lbl_successL, 4, 5, 5, 5);

        btn_delete = new Button("Eliminar");
        btn_delete.setVisible(false);
        btn_delete.setOnAction(
                (event) -> {

                    try {

                        lfile.deleteLoan(signature);
                        lfile.close();
                        lbl_successL.setVisible(true);
                        lbl_payLoan.setDisable(true);
                        lbl_loans.setDisable(true);
                        lbl_idStudentL.setDisable(true);
                        lbl_signatureL.setDisable(true);
                        lbl_loanDayL.setDisable(true);
                        lbl_deliveyDayL.setDisable(true);
                        lbl_kindL.setDisable(true);
                        lbl_payL.setDisable(true);
                        btn_delete.setVisible(false);
                        btn_exitLoan.setVisible(true);

                    } catch (IOException ex) {
                        Logger.getLogger(InterfaceModules.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
        );
        gpn_deleteLoan.add(btn_delete, 5, 4);

        btn_exitLoan = new Button("Salir");
        btn_exitLoan.setVisible(false);
        btn_exitLoan.setOnAction((event) -> {

            btn_exitLoan.setVisible(false);
            lbl_successL.setVisible(false);
            lbl_payLoan.setVisible(false);
            lbl_loans.setVisible(false);
            lbl_idStudentL.setVisible(false);
            lbl_signatureL.setVisible(false);
            lbl_loanDayL.setVisible(false);
            lbl_deliveyDayL.setVisible(false);
            lbl_kindL.setVisible(false);
            lbl_payL.setVisible(false);
            lbl_payLoan.setVisible(false);
            lbl_loans.setDisable(false);
            lbl_idStudentL.setDisable(false);
            lbl_signatureL.setDisable(false);
            lbl_loanDayL.setDisable(false);
            lbl_deliveyDayL.setDisable(false);
            lbl_kindL.setDisable(false);
            lbl_payL.setDisable(false);
            lbl_signatLoan.setVisible(true);
            tfd_signatLoan.setVisible(true);
            lbl_idLoan.setVisible(true);
            tfd_idLoan.setVisible(true);
            btn_search.setVisible(true);

        });
        gpn_deleteLoan.add(btn_exitLoan, 5, 4);

        return gpn_deleteLoan;

    }

    public static GridPane viewLoans() throws IOException {

        GridPane gpn_viewloans = new GridPane();
        gpn_viewloans.setAlignment(Pos.TOP_CENTER);
        gpn_viewloans.setPadding(new Insets(20));
        gpn_viewloans.setPrefSize(700, 800);

        TableView<Loan> tvw_viewLoan = new TableView();

        LoanFile lfile = new LoanFile(new File("./Loans.dat"));
        ObservableList<Loan> data = lfile.getAllLoans();
        tvw_viewLoan.setItems(data);

        TableColumn idColumn = new TableColumn("Carné");
        idColumn.setMinWidth(150);
        idColumn.setCellValueFactory(new PropertyValueFactory<>("studentId"));

        TableColumn signatureColumn = new TableColumn("Signatura");
        signatureColumn.setMinWidth(150);
        signatureColumn.setCellValueFactory(new PropertyValueFactory<>("signature"));

        TableColumn loanDayColumn = new TableColumn("Día de prestamo");
        loanDayColumn.setMinWidth(150);
        loanDayColumn.setCellValueFactory(new PropertyValueFactory<>("loanDay"));

        TableColumn deliveryColumn = new TableColumn("Dia de devolución");
        deliveryColumn.setMinWidth(150);
        deliveryColumn.setCellValueFactory(new PropertyValueFactory<>("deliveryDay"));

        TableColumn kindColumn = new TableColumn("Artículo");
        kindColumn.setMinWidth(150);
        kindColumn.setCellValueFactory(new PropertyValueFactory<>("kind"));

        tvw_viewLoan.getColumns().addAll(idColumn, signatureColumn, loanDayColumn, deliveryColumn, kindColumn);
        tvw_viewLoan.setPrefSize(750, 500);
        tvw_viewLoan.setTableMenuButtonVisible(true);
        gpn_viewloans.add(tvw_viewLoan, 0, 0);

        return gpn_viewloans;

    }

}
