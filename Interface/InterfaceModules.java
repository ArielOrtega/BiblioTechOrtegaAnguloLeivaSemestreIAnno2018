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
import com.sun.javafx.scene.control.skin.TextFieldSkin;
import com.sun.javafx.scene.control.skin.VirtualFlow;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OptionalDataException;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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
import javafx.scene.control.Tooltip;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.RowConstraints;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.util.converter.LocalDateStringConverter;
import org.controlsfx.control.textfield.TextFields;

public class InterfaceModules {

    //Creacion e instancia de las variables globales
    ObservableList<Student> observableArrayStudent = FXCollections.observableArrayList();
    ArrayList<Student> arrayListStudent = new ArrayList<>();
    LogicalMethods methods = new LogicalMethods();
    static String genre, idiom;
    static int valueDelivery1, valueDelivery2, valueDelivery3;
    static LocalDate date1;
    static Label lbl_choise, lbl_signature, lbl_deliveryDay, lbl_warning, lbl_success, lbl_idStudent, lbl_info, lbl_nameArticle;
    static RadioButton rdb_choiceBook, rdb_choiceAV;
    static TextField tfd_signatureB, tfd_idStudent, tfd_signatureAV, tfd_nameArticle;
    static DatePicker dpk_delivaeyDay;
    static Button btn_enterLoan, btn_checkStudent, btn_exit, btn_delete, btn_exitLoan;
    static Loan loan1;
    static AudioVisualFile avf;
    static String signatureB, signatureAV, signature, idLoan;
    static MainInterface mI = new MainInterface();
    StudentFile sft = new StudentFile();
    File file = new File("studentFile.txt");

    //Interfaz para registrar a un estudiante con su ID, nombre y año de ingreso
    public GridPane studentRegister() {

        GridPane gridpane = new GridPane();

        //Labels
        Label lbl_title = new Label("Add Students");
        Label lbl_name = new Label("Name");
        Label lbl_entryYear = new Label("Entry year");
        Label lbl_exception = new Label("Insert data correctly");
        Label lbl_phone = new Label("Telephone Number");
        lbl_exception.setVisible(false);

        //TextFields
        TextField tfd_name = new TextField();
        TextField tfd_entryYear = new TextField();
        TextField tfd_phone = new TextField();

        tfd_name.setPromptText("Name");
        tfd_entryYear.setPromptText("Year");
        tfd_entryYear.lengthProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable,
                    Number beforeValue, Number actualValue) {
                if (actualValue.intValue() > beforeValue.intValue()) {
                    // Revisa que la longitud del texto no sea mayor a la variable definida.
                    if (tfd_entryYear.getText().length() >= 5) {
                        tfd_entryYear.setText(tfd_entryYear.getText().substring(0, 5));
                    }
                }
            }
        });
        tfd_phone.setPromptText("Telephone");

        //ComboBox para opciones de carreras existentes
        ComboBox<String> cb_career = new ComboBox<>();
        cb_career.getItems().addAll("Informática", "Administración", "Turismo");
        cb_career.setPromptText("Career");
        cb_career.setEditable(false);

        //Funcion del ComboBox
        cb_career.setOnAction((event) -> {
            try {
                String name = tfd_name.getText();
                int year = Integer.parseInt(tfd_entryYear.getText());
                String phone = tfd_phone.getText();
                String id = methods.getStudentId(cb_career.getValue(), tfd_entryYear.getText(), observableArrayStudent.size());

                if (tfd_name.getText().length() > 0 && tfd_entryYear.getText().length() > 0
                        && cb_career.getValue().length() > 0 && (tfd_phone.getText().length() > 0 && tfd_phone.getText().length() < 9)) {

                    //Formato
                    String format = phone.substring(0, 4) + "-" + phone.substring(4);

                    Student s = new Student(name, year, cb_career.getValue(), format, id);

                    //Se añade al obsevable list
                    observableArrayStudent.add(s);

                    if (file.exists()) {
                        sft.serializeList(s);
                    } else {
                        sft.serializeStudent(s);
                    }

                    //arrayListStudent.add(s);
                    tfd_name.clear();
                    tfd_entryYear.clear();
                    tfd_phone.clear();
                } else {
                    lbl_exception.setVisible(true);
                    tfd_name.clear();
                    tfd_entryYear.clear();
                    tfd_phone.clear();
                }
            } catch (NumberFormatException nfe) {
                lbl_exception.setVisible(true);
                tfd_name.clear();
                tfd_entryYear.clear();
                tfd_phone.clear();
            } catch (IOException ex) {
                Logger.getLogger(InterfaceModules.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(InterfaceModules.class.getName()).log(Level.SEVERE, null, ex);
            }

        });

        //Agregación de los nodulos al GridPane que se retorna
        gridpane.add(lbl_title, 0, 0);
        gridpane.add(lbl_name, 0, 2);
        gridpane.add(tfd_name, 0, 3);
        gridpane.add(lbl_entryYear, 0, 5);
        gridpane.add(tfd_entryYear, 0, 6);
        gridpane.add(lbl_phone, 0, 8);
        gridpane.add(tfd_phone, 0, 9);
        gridpane.add(cb_career, 0, 11);
        gridpane.add(lbl_exception, 0, 12);
        gridpane.setVgap(5);

        return gridpane;
    }

    public VBox showTableView() {

        //Se crea una tabla (Tableview) que mostrar'a la lista de estudiantes registrados
        TableView<Student> table = new TableView<>();

        TableColumn columnId = new TableColumn("ID");
        columnId.setMinWidth(100);
        columnId.setCellValueFactory(new PropertyValueFactory("id"));

        TableColumn columnName = new TableColumn("Name");
        columnName.setMinWidth(100);
        columnName.setCellValueFactory(new PropertyValueFactory("name"));

        TableColumn columnYear = new TableColumn("Year of income");
        columnYear.setMinWidth(150);
        columnYear.setCellValueFactory(new PropertyValueFactory("entryYear"));

        TableColumn columnCareer = new TableColumn("Career");
        columnCareer.setMinWidth(100);
        columnCareer.setCellValueFactory(new PropertyValueFactory("career"));

        TableColumn columnTelephone = new TableColumn("Telephone");
        columnTelephone.setMinWidth(100);
        columnTelephone.setCellValueFactory(new PropertyValueFactory("phoneNumber"));

//        table.setItems(observableArrayStudent);
        //Agregacion de n'odulos a la tabla
        table.getColumns().addAll(columnId, columnName, columnYear, columnCareer, columnTelephone);
        table.setPrefSize(550, 400);
        table.setEditable(false);

        //Bot'on para mostrar la lista de estudiantes
        Button btn_showRecords = new Button("See Records");
        btn_showRecords.setOnAction((event) -> {
            try {
                table.setItems(FXCollections.observableArrayList(sft.readList()));
            } catch (FileNotFoundException | ClassNotFoundException | OptionalDataException ex) {
                Logger.getLogger(InterfaceModules.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(InterfaceModules.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        HBox hbox_buttons = new HBox();
        hbox_buttons.getChildren().addAll(btn_showRecords);

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

        Label lbl_name = new Label("Title");
        lbl_name.setTextFill(Color.BLACK);
        lbl_name.setFont(Font.font("Arial", FontWeight.BOLD, 15));
        gpn_enterBooks.add(lbl_name, 0, 0);

        TextField tfd_name = new TextField();
        gpn_enterBooks.add(tfd_name, 1, 0);

        Label lbl_signatureB = new Label("Signature");
        lbl_signatureB.setTextFill(Color.BLACK);
        lbl_signatureB.setFont(Font.font("Arial", FontWeight.BOLD, 15));
        gpn_enterBooks.add(lbl_signatureB, 3, 0);

        TextField tfd_signatureB = new TextField();
        gpn_enterBooks.add(tfd_signatureB, 4, 0);

        Label lbl_author = new Label("Author");
        lbl_author.setTextFill(Color.BLACK);
        lbl_author.setFont(Font.font("Arial", FontWeight.BOLD, 15));
        gpn_enterBooks.add(lbl_author, 0, 2);

        TextField tfd_author = new TextField();
        gpn_enterBooks.add(tfd_author, 1, 2);

        Label lbl_genre = new Label("Genre");
        lbl_genre.setTextFill(Color.BLACK);
        lbl_genre.setFont(Font.font("Arial", FontWeight.BOLD, 15));
        gpn_enterBooks.add(lbl_genre, 3, 2);

        ComboBox cbx_genre = new ComboBox();
        cbx_genre.setPrefWidth(400);
        cbx_genre.getItems().addAll("Bibliography", "Classics of Literature", "Comics", "Essays",
                "Fantasy", "Literary Fiction", "History", "Humor", "Infantile", "Poetry", "Romantic",
                "Academic", "Other");
        cbx_genre.setValue("Academic");

        cbx_genre.valueProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue ov, String t, String t1) {
                cbx_genre.setValue(t1);
            }
        });
        gpn_enterBooks.add(cbx_genre, 4, 2);

        Label lbl_idiom = new Label("Idiom");
        lbl_idiom.setTextFill(Color.BLACK);
        lbl_idiom.setFont(Font.font("Arial", FontWeight.BOLD, 15));
        gpn_enterBooks.add(lbl_idiom, 0, 4);

        ComboBox cbx_idiom = new ComboBox();
        cbx_idiom.setPrefWidth(400);
        cbx_idiom.getItems().addAll("English", "Spanish", "Chinesse", "German",
                "French", "Portuguese", "Other");
        cbx_idiom.setValue("Spanish");

        cbx_idiom.valueProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue ov, String t, String t1) {
                cbx_idiom.setValue(t1);
            }
        });
        gpn_enterBooks.add(cbx_idiom, 1, 4);

        Label lbl_description = new Label("Description");
        lbl_description.setTextFill(Color.BLACK);
        lbl_description.setFont(Font.font("Arial", FontWeight.BOLD, 15));
        gpn_enterBooks.add(lbl_description, 3, 4);

        TextArea txa_description = new TextArea();
        txa_description.setPrefSize(400, 500);
        gpn_enterBooks.add(txa_description, 4, 4);

        Button btn_enterBook = new Button("Enter Book");
        btn_enterBook.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {

                genre = cbx_genre.getValue() + "";
                idiom = cbx_idiom.getValue() + "";
                try {

                    BooksFile bfile = new BooksFile(new File("./Books.dat"));

                    Books book1 = new Books(tfd_author.getText(), genre, idiom, tfd_name.getText(), tfd_signatureB.getText(), 1, txa_description.getText());

                    Label lbl_success = new Label("¡Book entered successfully!");
                    lbl_success.setTextFill(Color.GREEN);
                    lbl_success.setFont(Font.font("Arial", FontWeight.BOLD, 15));
                    lbl_success.setVisible(false);
                    gpn_enterBooks.add(lbl_success, 3, 7, 4, 4);

                    Label lbl_error = new Label("Unfilled spaces");
                    lbl_error.setVisible(false);
                    gpn_enterBooks.add(lbl_error, 3, 7, 4, 4);

                    if (bfile.avaibilityBook(book1.getSignature(), 1)) {
                        lbl_success.setVisible(true);

                    } else if (tfd_author.getText().isEmpty() || tfd_name.getText().isEmpty() || tfd_signatureB.getText().isEmpty() || txa_description.getText().isEmpty()) {
                        lbl_error.setVisible(true);
                        lbl_success.setVisible(false);
                    } else {
                        lbl_success.setVisible(true);

                        bfile.addEndRecord(book1);
                        bfile.close();
                    }

                } catch (IOException ex) {
                    Logger.getLogger(InterfaceModules.class.getName()).log(Level.SEVERE, "Error inserting book", ex);
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

        Label lbl_signatureAV = new Label("Signature:");
        lbl_signatureAV.setTextFill(Color.BLACK);
        lbl_signatureAV.setFont(Font.font("Arial", FontWeight.BOLD, 15));
        gpn_enterAudioV.add(lbl_signatureAV, 3, 0);

        TextField tfd_signatureAV = new TextField();
        gpn_enterAudioV.add(tfd_signatureAV, 4, 0);

        Label lbl_brand = new Label("Brand:");
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
        Label lbl_description = new Label("Description:");
        lbl_description.setTextFill(Color.BLACK);
        lbl_description.setFont(Font.font("Arial", FontWeight.BOLD, 15));
        gpn_enterAudioV.add(lbl_description, 0, 4);

        TextArea txa_description = new TextArea();
        txa_description.setPrefSize(300, 400);
        gpn_enterAudioV.add(txa_description, 1, 4);

        Button btn_enterAudioV = new Button("Enter Article");
        btn_enterAudioV.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                try {

                    AudioVisualFile afile = new AudioVisualFile(new File("./AudioVisual.dat"));

                    AudioVisual audioVisual = new AudioVisual(tfd_brand.getText(), tfd_kind.getText(), tfd_signatureAV.getText(), 1, txa_description.getText());

                    Label lbl_success = new Label("¡Article entered successfully!");
                    lbl_success.setTextFill(Color.GREEN);
                    lbl_success.setFont(Font.font("Arial", FontWeight.BOLD, 15));
                    lbl_success.setVisible(false);
                    gpn_enterAudioV.add(lbl_success, 3, 7, 4, 4);

                    Label lbl_error = new Label("Unfilled spaces");
                    lbl_error.setVisible(false);
                    gpn_enterAudioV.add(lbl_error, 3, 7, 4, 4);

                    //condicional que verifica si y existe para aumentar disponibilidad o ingresar como articulo nuevo
                    if (afile.avaibilityAudioVisual(audioVisual.getSignature(), 1)) {
                        lbl_success.setVisible(true);
                        lbl_error.setVisible(false);
                    } else if (tfd_brand.getText().isEmpty() || tfd_kind.getText().isEmpty() || tfd_signatureAV.getText().isEmpty() || txa_description.getText().isEmpty()) {
                        lbl_success.setVisible(false);
                        lbl_error.setVisible(true);
                    } else {
                        lbl_success.setVisible(true);
                        lbl_error.setVisible(false);
                        afile.addEndRecord(audioVisual);
                        afile.close();
                    }

                } catch (IOException ex) {
                    Logger.getLogger(InterfaceModules.class.getName()).log(Level.SEVERE, "Error inserting article", ex);
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
        TableColumn nameAVColumn = new TableColumn("Name");
        nameAVColumn.setMinWidth(150);
        nameAVColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn signatureAVColumn = new TableColumn("Signature");
        signatureAVColumn.setMinWidth(150);
        signatureAVColumn.setCellValueFactory(new PropertyValueFactory<>("signature"));

        TableColumn brandAVColumn = new TableColumn("Brand");
        brandAVColumn.setMinWidth(150);
        brandAVColumn.setCellValueFactory(new PropertyValueFactory<>("brand"));

        TableColumn availabilityAVColumn = new TableColumn("Availability");
        availabilityAVColumn.setMinWidth(150);
        availabilityAVColumn.setCellValueFactory(new PropertyValueFactory<>("availability"));

        TableColumn descritionAVColumn = new TableColumn("Description");
        descritionAVColumn.setMinWidth(150);
        descritionAVColumn.setCellValueFactory(new PropertyValueFactory<>("description"));

        tvw_viewAudiovisual.getColumns().addAll(nameAVColumn, signatureAVColumn, brandAVColumn, availabilityAVColumn, descritionAVColumn);
        tvw_viewAudiovisual.setPrefSize(750, 475);
        tvw_viewAudiovisual.setTableMenuButtonVisible(true);
        gpn_viewMaterial.add(tvw_viewAudiovisual, 1, 1);

        HBox hbx_2nodes = new HBox();
        hbx_2nodes.setSpacing(10);

        //label por si no selecciono un item en la tabla
        Label lbl_unselected_row = new Label("You have not selected any article");
        lbl_unselected_row.setVisible(false);
        lbl_unselected_row.setTextFill(Color.RED);
        lbl_unselected_row.setFont(Font.font("Arial", FontWeight.BOLD, 15));

        //exito al ingresar ejemplares
        Label lbl_success_row = new Label("Increased succesfully!");
        lbl_success_row.setVisible(false);
        lbl_success_row.setTextFill(Color.GREEN);
        lbl_success_row.setFont(Font.font("Arial", FontWeight.BOLD, 15));

        //boton para aumentar la disponibilidad audiovisual
        Button btn_increaseAvaibility = new Button("Increase availability");
        btn_increaseAvaibility.setVisible(false);

        //se annaden el boton y el label a un hbox para que ambos esten debajo de la tabla
        hbx_2nodes.getChildren().addAll(btn_increaseAvaibility, lbl_unselected_row, lbl_success_row);
        gpn_viewMaterial.add(hbx_2nodes, 1, 3);
        btn_increaseAvaibility.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                AudioVisual av1 = tvw_viewAudiovisual.getSelectionModel().getSelectedItem();

                if (av1 != null) {

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
                    dialog.setTitle("Availability");
                    dialog.setHeaderText("Enter the increase of AudioVisuals");
                    dialog.setContentText("Quantity");

                    // obtener el valor.
                    Optional<String> result = dialog.showAndWait();
                    if (result.isPresent()) {
                        try {
                            AudioVisualFile avf = new AudioVisualFile(new File("./AudioVisual.dat"));

                            int quanty = Integer.parseInt(result.get());
                            if (avf.avaibilityAudioVisual(av1.getSignature(), quanty)) {
                                ObservableList<AudioVisual> audioVisuals = avf.getAudioVisuals();
                                tvw_viewAudiovisual.setItems(audioVisuals);
                                lbl_success_row.setVisible(true);
                            } else {

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

        TableColumn nameBColumn = new TableColumn("Name");
        nameBColumn.setMinWidth(150);
        nameBColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn authorBColumn = new TableColumn("Author");
        authorBColumn.setMinWidth(150);
        authorBColumn.setCellValueFactory(new PropertyValueFactory<>("autor"));

        TableColumn signatureBColumn = new TableColumn("Signature");
        signatureBColumn.setMinWidth(150);
        signatureBColumn.setCellValueFactory(new PropertyValueFactory<>("signature"));

        TableColumn genreBColumn = new TableColumn("Genre");
        genreBColumn.setMinWidth(150);
        genreBColumn.setCellValueFactory(new PropertyValueFactory<>("genre"));

        TableColumn lenguageBColumn = new TableColumn("Idiom");
        lenguageBColumn.setMinWidth(150);
        lenguageBColumn.setCellValueFactory(new PropertyValueFactory<>("language"));

        TableColumn availabilityBColumn = new TableColumn("Availability");
        availabilityBColumn.setMinWidth(10);
        availabilityBColumn.setCellValueFactory(new PropertyValueFactory<>("availability"));

        TableColumn descriptionBColumn = new TableColumn("Description");
        descriptionBColumn.setMinWidth(220);
        descriptionBColumn.setCellValueFactory(new PropertyValueFactory<>("description"));

        tvw_viewBooks.getColumns().addAll(nameBColumn, authorBColumn, signatureBColumn, genreBColumn,
                lenguageBColumn, availabilityBColumn, descriptionBColumn);
        tvw_viewBooks.setPrefSize(1100, 475);
        gpn_viewMaterial.add(tvw_viewBooks, 1, 1);

        HBox hbx_2nodesB = new HBox();
        hbx_2nodesB.setSpacing(10);

        //label por si no selecciono un item en la tabla
        Label lbl_unselected_rowB = new Label("You have not selected any article");
        lbl_unselected_rowB.setVisible(false);
        lbl_unselected_rowB.setTextFill(Color.RED);
        lbl_unselected_rowB.setFont(Font.font("Arial", FontWeight.BOLD, 15));

        //exito al ingresar ejemplares
        Label lbl_success_rowB = new Label("Increased succesfully!");
        lbl_success_rowB.setVisible(false);
        lbl_success_rowB.setTextFill(Color.GREEN);
        lbl_success_rowB.setFont(Font.font("Arial", FontWeight.BOLD, 15));

        //inrementalibros
        Button btn_increaseAvaibilityB = new Button("Increase availability");
        btn_increaseAvaibilityB.setVisible(true);
        btn_increaseAvaibilityB.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                Books book1 = tvw_viewBooks.getSelectionModel().getSelectedItem();

                if (book1 != null) {

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
                    dialog.setTitle("Availability");
                    dialog.setHeaderText("Enter the increase of AudioVisuals");
                    dialog.setContentText("Quantity");

                    // obtener el valor.
                    Optional<String> result = dialog.showAndWait();
                    if (result.isPresent()) {

                        try {
                            int quanty = Integer.parseInt(result.get());
                            if (bfile.avaibilityBook(book1.getSignature(), quanty)) {
                                ObservableList<Books> books = bfile.getBooks();
                                tvw_viewBooks.setItems(books);
                                lbl_success_rowB.setVisible(true);
                            } else {

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

        Label lbl_titleBooks = new Label("Books");
        lbl_titleBooks.setFont(Font.font("Opens Sans"));
        lbl_titleBooks.setStyle("-fx-font-weight: bold; -fx-font-size: 25");
        gpn_viewMaterial.add(lbl_titleBooks, 1, 2);

        Label lbl_titleAudioVisuals = new Label("AudioVisuales");
        lbl_titleAudioVisuals.setVisible(false);
        lbl_titleAudioVisuals.setFont(Font.font("Opens Sans"));
        lbl_titleAudioVisuals.setStyle("-fx-font-weight: bold; -fx-font-size: 25");
        gpn_viewMaterial.add(lbl_titleAudioVisuals, 1, 2);

        btn_increaseAvaibility.setVisible(true);

        Button btn_viewBooks = new Button("View books");
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

        Button btn_viewAudiov = new Button("View Audiovisual");
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
        AudioVisualFile avfile = new AudioVisualFile(new File("./AudioVisual.dat"));
        BooksFile bfile = new BooksFile(new File("./Books.dat"));

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

        lbl_idStudent = new Label("Student ID");
        lbl_idStudent.setTextFill(Color.WHITE);
        lbl_idStudent.setFont(Font.font("Arial", FontWeight.BOLD, 15));
        gpn_enterLoan.add(lbl_idStudent, 0, 3);

        Label lbl_exception = new Label("Student not found");
        lbl_exception.setTextFill(Color.RED);
        lbl_exception.setFont(Font.font("Arial", FontWeight.BOLD, 15));
        lbl_exception.setVisible(false);
        gpn_enterLoan.add(lbl_exception, 1, 4);

        Label lbl_studentInfo = new Label();
        lbl_studentInfo.setTextFill(Color.WHITE);
        lbl_studentInfo.setFont(Font.font("Arial", FontWeight.BOLD, 15));
        gpn_enterLoan.add(lbl_studentInfo, 0, 4, 2, 5);

        tfd_idStudent = new TextField();
        Tooltip enter = new Tooltip("Presione enter");
        tfd_idStudent.setTooltip(enter);
        tfd_idStudent.setOnAction((event) -> {
            try {
                LogicalMethods methods = new LogicalMethods();
                StudentFile sft = new StudentFile();
                if (!methods.checkStudentRecord(tfd_idStudent.getText())) {
                    tfd_idStudent.clear();
                    lbl_exception.setVisible(true);
                    btn_checkStudent.setDisable(true);
                } else {
                    btn_checkStudent.setDisable(false);
                    lbl_exception.setVisible(false);
                    lbl_studentInfo.setText(sft.studentInfo(tfd_idStudent.getText()));
                    tfd_idStudent.setDisable(true);
                }
            } catch (FileNotFoundException | ClassNotFoundException | OptionalDataException ex) {
                Logger.getLogger(InterfaceModules.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(InterfaceModules.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        tfd_idStudent.lengthProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable,
                    Number beforeValue, Number actualValue) {
                if (actualValue.intValue() > beforeValue.intValue()) {
                    // Revisa que la longitud del texto no sea mayor a la variable definida.
                    if (tfd_idStudent.getText().length() >= 5) {
                        tfd_idStudent.setText(tfd_idStudent.getText().substring(0, 5));
                    }
                }
            }
        });

        gpn_enterLoan.add(tfd_idStudent, 1, 3);

        Label lbl_notValue = new Label("Enter a value");
        lbl_notValue.setVisible(false);
        lbl_notValue.setTextFill(Color.RED);
        lbl_notValue.setFont(Font.font("Arial", FontWeight.BOLD, 15));
        gpn_enterLoan.add(lbl_notValue, 2, 4, 3, 4);
        btn_checkStudent = new Button("Enter");
        btn_checkStudent.setDisable(true);
        btn_checkStudent.setOnAction((event) -> {

            if (tfd_idStudent.getText().length() == 0) {
                lbl_notValue.setVisible(true);
            } else {
                lbl_notValue.setVisible(false);
                String student = tfd_idStudent.getText().replaceAll(" ", "");
                lbl_idStudent.setVisible(false);
                tfd_idStudent.setVisible(false);
                btn_checkStudent.setVisible(false);
                rdb_choiceAV.setVisible(true);
                rdb_choiceBook.setVisible(true);

            }

        });//end Button
        gpn_enterLoan.add(btn_checkStudent, 3, 3);

        lbl_nameArticle = new Label("Name of Article");
        lbl_nameArticle.setVisible(false);
        lbl_nameArticle.setTextFill(Color.WHITE);
        lbl_nameArticle.setFont(Font.font("Arial", FontWeight.BOLD, 15));
        gpn_enterLoan.add(lbl_nameArticle, 0, 3);

        tfd_nameArticle = new TextField();
        tfd_nameArticle.setVisible(false);
        gpn_enterLoan.add(tfd_nameArticle, 1, 3);

        lbl_signature = new Label("Article Signature");
        lbl_signature.setVisible(false);
        lbl_signature.setTextFill(Color.WHITE);
        lbl_signature.setFont(Font.font("Arial", FontWeight.BOLD, 15));
        gpn_enterLoan.add(lbl_signature, 0, 2);

        tfd_signatureB = new TextField("ISBN-");
        tfd_signatureB.setVisible(false);
        gpn_enterLoan.add(tfd_signatureB, 1, 2);
        tfd_signatureB.lengthProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable,
                    Number beforeValue, Number actualValue) {
                if (actualValue.intValue() > beforeValue.intValue()) {
                    // Revisa que la longitud del texto no sea mayor a la variable definida.
                    if (tfd_signatureB.getText().length() >= 9) {
                        tfd_signatureB.setText(tfd_signatureB.getText().substring(0, 9));
                    }
                }
            }
        });

        lbl_deliveryDay = new Label("Delivery day");
        lbl_deliveryDay.setVisible(false);
        lbl_deliveryDay.setTextFill(Color.WHITE);
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
        //Desabilidado por motivos de pruebas a la hora de la defensa
//        final Callback<DatePicker, DateCell> dayCellFactory
//                = new Callback<DatePicker, DateCell>() {
//            @Override
//            public DateCell call(final DatePicker datePicker) {
//                return new DateCell() {
//                    @Override
//                    public void updateItem(LocalDate item, boolean empty) {
//                        super.updateItem(item, empty);
//
//                        if (item.isBefore(dpk_loanDay.getValue().plusDays(-1))) {
//                            setDisable(true);
//                            setStyle("-fx-background-color: #ffc0cb;");
//                        }
//                    }
//                };
//            }
//        };
//        dpk_delivaeyDay.setDayCellFactory(dayCellFactory);
//        dpk_delivaeyDay.setValue(dpk_loanDay.getValue().plusDays(0));
        gpn_enterLoan.add(dpk_delivaeyDay, 4, 2);

        lbl_warning = new Label("¡Unregistered loan!");
        lbl_warning.setVisible(false);
        lbl_warning.setTextFill(Color.RED);
        lbl_warning.setFont(Font.font("Arial", FontWeight.BOLD, 15));
        gpn_enterLoan.add(lbl_warning, 3, 4, 4, 6);

        lbl_success = new Label("¡The loan was registered!");
        lbl_success.setVisible(false);
        lbl_success.setTextFill(Color.GREEN);
        lbl_success.setFont(Font.font("Arial", FontWeight.BOLD, 15));
        gpn_enterLoan.add(lbl_success, 3, 4, 4, 6);

        ToggleGroup group = new ToggleGroup();

        rdb_choiceBook = new RadioButton("Book");
        rdb_choiceBook.setTextFill(Color.WHITE);
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
                    lbl_nameArticle.setVisible(true);
                    tfd_nameArticle.setVisible(true);

                    //tfd_signatureB.setText("ISBN-");
                    LogicalMethods methods = new LogicalMethods();
                    String options[] = methods.autocompleteOptions();
                    TextFields.bindAutoCompletion(tfd_nameArticle, options);

                    btn_enterLoan.setDisable(false);
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
        rdb_choiceAV.setTextFill(Color.WHITE);        
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
                lbl_nameArticle.setVisible(true);
                tfd_nameArticle.setVisible(true);

            }
        });
        gpn_enterLoan.add(rdb_choiceAV, 1, 1);

        lbl_info = new Label("Exit");
        lbl_info.setVisible(false);
        gpn_enterLoan.add(lbl_info, 2, 6, 3, 6);

        Label lbl_notValueEnter = new Label("Fill all spaces");
        lbl_notValueEnter.setVisible(false);
        lbl_notValueEnter.setTextFill(Color.RED);
        lbl_notValueEnter.setFont(Font.font("Arial", FontWeight.BOLD, 15));
        gpn_enterLoan.add(lbl_notValueEnter, 3, 4, 4, 6);

        btn_enterLoan = new Button("Enter Loan");
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
                    boolean kind = false;

                    if (rdb_choiceAV.isSelected()) {
                        kind = true;
                        loan1 = new Loan(idStudent, signatureAV, loanDay, deliveryDay, kind);
                        try {
                            avfile.lessAvaibilityAudioVisual(signatureAV);
                            avfile.close();
                        } catch (IOException ex) {
                            Logger.getLogger(InterfaceModules.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    } else {
                        kind = false;
                        loan1 = new Loan(idStudent, signatureB, loanDay, deliveryDay, kind);
                        try {
                            bfile.lessAvaibilityBook(signatureB);
                            bfile.close();
                        } catch (IOException ex) {
                            Logger.getLogger(InterfaceModules.class.getName()).log(Level.SEVERE, null, ex);
                        }
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
                        lbl_studentInfo.setVisible(false);
                        tfd_nameArticle.setDisable(true);
                        lbl_success.setVisible(true);

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

        btn_exit = new Button("Exit");
        btn_exit.setVisible(false);
        btn_exit.setOnAction((event) -> {

            lbl_notValueEnter.setVisible(false);
            lbl_signature.setVisible(false);
            tfd_signatureB.setVisible(false);
            lbl_deliveryDay.setVisible(false);
            dpk_delivaeyDay.setVisible(false);
            rdb_choiceBook.setVisible(false);
            rdb_choiceAV.setVisible(false);
            btn_enterLoan.setVisible(false);
            lbl_warning.setVisible(false);
            lbl_success.setVisible(false);
            lbl_info.setVisible(false);
            btn_exit.setVisible(false);
            tfd_signatureAV.setVisible(false);
            lbl_idStudent.setVisible(true);
            tfd_idStudent.setVisible(true);
            btn_checkStudent.setVisible(true);
            tfd_idStudent.setText("");
            tfd_signatureAV.setText("");
            tfd_signatureB.setText("");
            dpk_delivaeyDay.setValue(LocalDate.now());
            tfd_signatureB.setDisable(false);
            tfd_signatureAV.setDisable(false);
            dpk_delivaeyDay.setDisable(false);
            rdb_choiceBook.setDisable(false);
            rdb_choiceAV.setDisable(false);
            btn_enterLoan.setDisable(false);
            tfd_idStudent.setDisable(false);
            lbl_nameArticle.setVisible(false);
            tfd_nameArticle.setVisible(false);
        });
        gpn_enterLoan.add(btn_exit, 4, 6);

        return gpn_enterLoan;

    }//end method

    public static GridPane deleteLoans() throws IOException {

        LoanFile lfile = new LoanFile(new File("./Loans.dat"));
        AudioVisualFile avfile = new AudioVisualFile(new File("./AudioVisual.dat"));
        BooksFile bfile = new BooksFile(new File("./Books.dat"));

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

        Label lbl_signatLoan = new Label("Signature");
        lbl_signatLoan.setTextFill(Color.WHITE);
        lbl_signatLoan.setFont(Font.font("Arial", FontWeight.BOLD, 15));
        gpn_deleteLoan.add(lbl_signatLoan, 0, 2);

        TextField tfd_signatLoan = new TextField();
        gpn_deleteLoan.add(tfd_signatLoan, 1, 2);

        Label lbl_idLoan = new Label("ID Student");
        lbl_idLoan.setTextFill(Color.WHITE);
        lbl_idLoan.setFont(Font.font("Arial", FontWeight.BOLD, 15));
        gpn_deleteLoan.add(lbl_idLoan, 3, 2);

        TextField tfd_idLoan = new TextField();
        gpn_deleteLoan.add(tfd_idLoan, 4, 2);

        Label lbl_warningL = new Label("Unregistered loan");
        lbl_warningL.setVisible(false);
        lbl_warningL.setFont(Font.font("Arial", FontWeight.BOLD, 15));
        lbl_warningL.setTextFill(Color.RED);
        gpn_deleteLoan.add(lbl_warningL, 4, 3);

        Label lbl_loans = new Label("Loan");
        lbl_loans.setTextFill(Color.WHITE);
        lbl_loans.setFont(Font.font("Arial", FontWeight.BOLD, 15));
        lbl_loans.setVisible(false);
        gpn_deleteLoan.add(lbl_loans, 0, 0);

        Label lbl_idStudentL = new Label();
        lbl_idStudentL.setFont(Font.font("Arial", FontWeight.BOLD, 15));
        lbl_idStudentL.setTextFill(Color.FLORALWHITE);
        gpn_deleteLoan.add(lbl_idStudentL, 1, 1);

        Label lbl_signatureL = new Label();
        lbl_signatureL.setFont(Font.font("Arial", FontWeight.BOLD, 15));
        lbl_signatureL.setTextFill(Color.FLORALWHITE);
        gpn_deleteLoan.add(lbl_signatureL, 2, 1);

        Label lbl_loanDayL = new Label();
        lbl_loanDayL.setFont(Font.font("Arial", FontWeight.BOLD, 15));
        lbl_loanDayL.setTextFill(Color.FLORALWHITE);
        gpn_deleteLoan.add(lbl_loanDayL, 3, 1);

        Label lbl_deliveyDayL = new Label();
        lbl_deliveyDayL.setFont(Font.font("Arial", FontWeight.BOLD, 15));
        lbl_deliveyDayL.setTextFill(Color.FLORALWHITE);
        gpn_deleteLoan.add(lbl_deliveyDayL, 4, 1);

        Label lbl_kindL = new Label();
        lbl_kindL.setFont(Font.font("Arial", FontWeight.BOLD, 15));
        lbl_kindL.setTextFill(Color.FLORALWHITE);
        gpn_deleteLoan.add(lbl_kindL, 5, 1);

        Label lbl_payL = new Label("Payment");
        lbl_payL.setTextFill(Color.WHITE);
        lbl_payL.setFont(Font.font("Arial", FontWeight.BOLD, 15));
        lbl_payL.setVisible(false);
        gpn_deleteLoan.add(lbl_payL, 0, 2);

        Label lbl_payLoan = new Label();
        lbl_payLoan.setFont(Font.font("Arial", FontWeight.BOLD, 15));
        lbl_payLoan.setTextFill(Color.FLORALWHITE);
        gpn_deleteLoan.add(lbl_payLoan, 1, 2, 3, 3);

        Button btn_search = new Button("Search");
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
                    lbl_idStudentL.setText("ID: " + lfile.getLoan(lfile.searchLoan(signature)).getStudentId());
                    lbl_signatureL.setText("Signature: " + lfile.getLoan(lfile.searchLoan(signature)).getSignature());
                    lbl_loanDayL.setText("Loan day: " + lfile.getLoan(lfile.searchLoan(signature)).getLoanDay());
                    lbl_deliveyDayL.setText("Delivery day: " + lfile.getLoan(lfile.searchLoan(signature)).getDeliveryDay());
                    lbl_kindL.setText("Article: " + lfile.getLoan(lfile.searchLoan(signature)).getKind());
                    lbl_payL.setVisible(true);
                    btn_delete.setVisible(true);

                    LocalDate date = LocalDate.now();
                    String dateNow = "" +date;


                    long balance = lfile.numberOfDays(lfile.getLoan(lfile.searchLoan(signature)).getDeliveryDay(), dateNow);

                    if (balance < 0) {
                        lbl_payLoan.setText("Without defaults");
                    } else {
                        lbl_payLoan.setText(lfile.fineOfPayment(balance) + " colones for " + balance + " days of delay");
                    }

                }
            } catch (IOException ex) {
                Logger.getLogger(InterfaceModules.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ParseException ex) {
                Logger.getLogger(InterfaceModules.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
        );
        gpn_deleteLoan.add(btn_search, 5, 4);

        Label lbl_successL = new Label("It has been removed successfully");
        lbl_successL.setTextFill(Color.GREEN);
        lbl_successL.setFont(Font.font("Arial", FontWeight.BOLD, 15));
        lbl_successL.setVisible(false);
        gpn_deleteLoan.add(lbl_successL, 4, 5, 5, 5);
        
        Label lbl_avaibility = new Label();
        lbl_avaibility.setTextFill(Color.WHITE);
        lbl_avaibility.setFont(Font.font("Arial", FontWeight.BOLD, 15));
        gpn_deleteLoan.add(lbl_avaibility, 4, 6, 5, 6);
        
        btn_delete = new Button("Delete");
        btn_delete.setVisible(false);
        btn_delete.setOnAction(
                (event) -> {

                    try {
                        //si la condicion se satisface es porque el articulo pertenece a Audiovisuales
                        if(lfile.getLoan(lfile.searchLoan(signature)).getKind()){                            
                            avfile.avaibilityAudioVisual(signature, 1);                            
                        }else{
                            bfile.avaibilityBook(signature, 1);
                        }

                        lfile.deleteLoan(signature);
                        lfile.close();
                        
                        lbl_avaibility.setText("The availability of article " + signature + " was increased");
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

        btn_exitLoan = new Button("Exit");
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

        TableColumn idColumn = new TableColumn("ID Student");
        idColumn.setMinWidth(150);
        idColumn.setCellValueFactory(new PropertyValueFactory<>("studentId"));

        TableColumn signatureColumn = new TableColumn("Signature");
        signatureColumn.setMinWidth(150);
        signatureColumn.setCellValueFactory(new PropertyValueFactory<>("signature"));

        TableColumn loanDayColumn = new TableColumn("Loan Day");
        loanDayColumn.setMinWidth(150);
        loanDayColumn.setCellValueFactory(new PropertyValueFactory<>("loanDay"));

        TableColumn deliveryColumn = new TableColumn("Delivery Day");
        deliveryColumn.setMinWidth(150);
        deliveryColumn.setCellValueFactory(new PropertyValueFactory<>("deliveryDay"));


        tvw_viewLoan.getColumns().addAll(idColumn, signatureColumn, loanDayColumn, deliveryColumn);
        tvw_viewLoan.setPrefSize(600, 500);
        tvw_viewLoan.setTableMenuButtonVisible(true);
        gpn_viewloans.add(tvw_viewLoan, 0, 0);

        return gpn_viewloans;

    }

}
