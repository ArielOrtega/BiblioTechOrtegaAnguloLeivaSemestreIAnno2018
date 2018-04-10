package Interface;

import Domain.AudioVisual;
import Domain.Books;
import Domain.Loan;
import Domain.Student;
import File.AudioVisualFile;
import File.BooksFile;
import File.LoanFile;
import File.StudentFile;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OptionalDataException;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ArrayList;
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
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableCell;
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
    static String genre, idiom;
    static boolean digital;
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
    static TableView<Student> table;
    static MainInterface mI = new MainInterface();
    StudentFile sft = new StudentFile();
    File file = new File("studentFile.txt");

    //Interfaz para registrar a un estudiante con su ID, nombre y año de ingreso
    public GridPane studentRegister() {

        GridPane gpn_insertStudent = new GridPane();

        //Labels
        Label lbl_title = new Label("Add Students");
        lbl_title.setFont(Font.font("OPEN SANS", 16));
        lbl_title.setTextFill(Color.WHITE);

        Label lbl_name = new Label("Name");
        lbl_name.setFont(Font.font("OPEN SANS", 16));
        lbl_name.setTextFill(Color.WHITE);

        Label lbl_entryYear = new Label("Entry year");
        lbl_entryYear.setFont(Font.font("OPEN SANS", 16));
        lbl_entryYear.setTextFill(Color.WHITE);

        Label lbl_exception = new Label("Insert data correctly");
        lbl_exception.setFont(Font.font("OPEN SANS", 16));
        lbl_exception.setTextFill(Color.WHITE);

        Label lbl_phone = new Label("Telephone Number");
        lbl_phone.setFont(Font.font("OPEN SANS", 16));
        lbl_phone.setTextFill(Color.WHITE);
        lbl_exception.setVisible(false);

        //TextFields
        TextField tfd_name = new TextField();
        TextField tfd_entryYear = new TextField();
        TextField tfd_phone = new TextField();
        tfd_phone.setPromptText("11112222");

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
        
        tfd_phone.lengthProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable,
                    Number beforeValue, Number actualValue) {
                if (actualValue.intValue() > beforeValue.intValue()) {
                    // Revisa que la longitud del texto no sea mayor a la variable definida.
                    if (tfd_phone.getText().length() >= 8) {
                        tfd_phone.setText(tfd_phone.getText().substring(0, 8));
                    }
                }
            }
        });        

        //ComboBox para opciones de carreras existentes
        ComboBox<String> cb_career = new ComboBox<>();
        cb_career.getItems().addAll("Informática", "Administración", "Turismo");
        cb_career.setPromptText("Career");
        cb_career.setEditable(false);

        //boton ingresar
        Button btn_insertStudent = new Button("Insert Student");
        btn_insertStudent.setTextFill(Color.BLACK);
        btn_insertStudent.setFont(Font.font("OPEN SANS", FontWeight.BOLD, 15));
        btn_insertStudent.setStyle("-fx-background-color: antiquewhite;-fx-border-color: TRANSPARENT");
        btn_insertStudent.setOnMouseEntered((event) -> {

            btn_insertStudent.setTextFill(Color.ANTIQUEWHITE);
            btn_insertStudent.setStyle("-fx-background-color: lightseagreen;-fx-border-color: TRANSPARENT");

        });
        btn_insertStudent.setOnMouseExited((event) -> {

            btn_insertStudent.setTextFill(Color.BLACK);
            btn_insertStudent.setStyle("-fx-background-color: antiquewhite;-fx-border-color: TRANSPARENT");

        });


        //Funcion del ComboBox
        btn_insertStudent.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                try {

                    LocalDate date = LocalDate.now();
                    String name = tfd_name.getText();
                    int year = Integer.parseInt(tfd_entryYear.getText());
                    String phone = tfd_phone.getText();
                    String id = sft.getStudentId(cb_career.getValue(), tfd_entryYear.getText(),
                            observableArrayStudent.size());

                    if (tfd_name.getText().length() > 0 && tfd_entryYear.getText().length() > 0
                            && cb_career.getValue().length() > 0 && (tfd_phone.getText().length()
                            > 0 && tfd_phone.getText().length() < 9) && year <= date.getYear()) {

                        //Formato
                        String format = phone.substring(0, 4) + "-" + phone.substring(4);

                        Student s = new Student(name, year, cb_career.getValue(), format, id);

                        //Se añade al obsevable list
                        observableArrayStudent.add(s);
                        lbl_exception.setVisible(false);

                        if (file.exists()) {
                            sft.serializeList(s);
                        } else {
                            sft.serializeStudent(s);
                        }

                        try {
                            table.setItems(FXCollections.observableArrayList(sft.readList()));
                        } catch (FileNotFoundException | ClassNotFoundException | OptionalDataException ex) {
                            Logger.getLogger(InterfaceModules.class.getName()).log(Level.SEVERE, null, ex);
                        } catch (IOException ex) {
                            Logger.getLogger(InterfaceModules.class.getName()).log(Level.SEVERE, null, ex);
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
                } catch (IOException | ClassNotFoundException ex) {
                    Logger.getLogger(InterfaceModules.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });

        //Agregación de los nodulos al GridPane que se retorna
        gpn_insertStudent.add(lbl_title, 0, 0);
        gpn_insertStudent.add(lbl_name, 0, 1);
        gpn_insertStudent.add(tfd_name, 0, 2);
        gpn_insertStudent.add(lbl_entryYear, 0, 3);
        gpn_insertStudent.add(tfd_entryYear, 0, 4);
        gpn_insertStudent.add(lbl_phone, 0, 6);
        gpn_insertStudent.add(tfd_phone, 0, 7);
        gpn_insertStudent.add(cb_career, 0, 8);
        gpn_insertStudent.add(btn_insertStudent, 0, 10);
        gpn_insertStudent.add(lbl_exception, 0, 11);
        gpn_insertStudent.setVgap(20);
        gpn_insertStudent.setHgap(10);
        gpn_insertStudent.setPadding(new Insets(15));

        return gpn_insertStudent;
    }//fin del m'etodo

    public VBox showTableView() {

        //Se crea una tabla (Tableview) que mostrar'a la lista de estudiantes registrados
        table = new TableView<>();

        //Se muestra el carn'e del estudiante
        TableColumn columnId = new TableColumn("ID");
        columnId.setMinWidth(100);
        columnId.setCellValueFactory(new PropertyValueFactory("id"));

        //Se muestra el nombre del estudiante
        TableColumn columnName = new TableColumn("Name");
        columnName.setMinWidth(230);
        columnName.setCellValueFactory(new PropertyValueFactory("name"));

        //Se muestra el año de ingreso del estudiante
        TableColumn columnYear = new TableColumn("Year of income");
        columnYear.setMinWidth(150);
        columnYear.setCellValueFactory(new PropertyValueFactory("entryYear"));

        //Se muestra la carrera del estudiante
        TableColumn columnCareer = new TableColumn("Career");
        columnCareer.setMinWidth(100);
        columnCareer.setCellValueFactory(new PropertyValueFactory("career"));

        //Se muestra el tel'efono del estudiante
        TableColumn columnTelephone = new TableColumn("Telephone");
        columnTelephone.setMinWidth(100);
        columnTelephone.setCellValueFactory(new PropertyValueFactory("phoneNumber"));

        //Agregacion de n'odulos a la tabla
        table.getColumns().addAll(columnId, columnName, columnYear, columnCareer, columnTelephone);
        table.setPrefSize(670, 400);
        table.setEditable(false);

        //Bot'on para mostrar la lista de estudiantes
        Button btn_showRecords = new Button("See Records");

        btn_showRecords.setTextFill(Color.BLACK);
        btn_showRecords.setFont(Font.font("OPEN SANS", FontWeight.BOLD, 15));
        btn_showRecords.setStyle("-fx-background-color: antiquewhite;-fx-border-color: TRANSPARENT");
        btn_showRecords.setOnMouseEntered((event) -> {

            btn_showRecords.setTextFill(Color.ANTIQUEWHITE);
            btn_showRecords.setStyle("-fx-background-color: lightseagreen;-fx-border-color: TRANSPARENT");

        });
        btn_showRecords.setOnMouseExited((event) -> {

            btn_showRecords.setTextFill(Color.BLACK);
            btn_showRecords.setStyle("-fx-background-color: antiquewhite;-fx-border-color: TRANSPARENT");

        });

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
        vbox.setSpacing(10);
        vbox.setPadding(new Insets(40));
        vbox.getChildren().addAll(table, hbox_buttons);

        return vbox;
    }//fin del m'etodo

    public static GridPane insertBooks() throws IOException {

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

        //Label nombre
        Label lbl_name = new Label("Title");
        lbl_name.setTextFill(Color.WHITE);
        lbl_name.setFont(Font.font("OPEN SANS", FontWeight.BOLD, 16));
        gpn_enterBooks.add(lbl_name, 0, 0);

        //TextField para ingresar nombre
        TextField tfd_name = new TextField();
        gpn_enterBooks.add(tfd_name, 1, 0);

        //Label signatura
        Label lbl_signatureB = new Label("Signature");
        lbl_signatureB.setTextFill(Color.WHITE);
        lbl_signatureB.setFont(Font.font("OPEN SANS", FontWeight.BOLD, 16));
        gpn_enterBooks.add(lbl_signatureB, 3, 0);

        //TextField para ingresar signatura
        TextField tfd_signatureBo = new TextField();
        tfd_signatureBo.setText("ISBN-");
        gpn_enterBooks.add(tfd_signatureBo, 4, 0);
        tfd_signatureBo.lengthProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable,
                    Number beforeValue, Number actualValue) {
                if (actualValue.intValue() > beforeValue.intValue()) {
                    // Revisa que la longitud del texto no sea mayor a la variable definida.
                    if (tfd_signatureBo.getText().length() >= 9) {
                        tfd_signatureBo.setText(tfd_signatureBo.getText().substring(0, 9));
                    }
                }
            }
        });

        //Label autor
        Label lbl_author = new Label("Author");
        lbl_author.setTextFill(Color.WHITE);
        lbl_author.setFont(Font.font("OPEN SANS", FontWeight.BOLD, 16));
        gpn_enterBooks.add(lbl_author, 0, 2);

        //TextField para ingresar autor
        TextField tfd_author = new TextField();
        gpn_enterBooks.add(tfd_author, 1, 2);

        //Label g'enero
        Label lbl_genre = new Label("Genre");
        lbl_genre.setTextFill(Color.WHITE);
        lbl_genre.setFont(Font.font("OPEN SANS", FontWeight.BOLD, 16));
        gpn_enterBooks.add(lbl_genre, 3, 2);

        //ComboBox para elegir el g'enero
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

        //Label idioma
        Label lbl_idiom = new Label("Language");
        lbl_idiom.setTextFill(Color.WHITE);
        lbl_idiom.setFont(Font.font("OPEN SANS", FontWeight.BOLD, 16));
        gpn_enterBooks.add(lbl_idiom, 0, 4);

        //ComboBox para elegir el idioma
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

        //Label descripci'on
        Label lbl_description = new Label("Description");
        lbl_description.setTextFill(Color.WHITE);
        lbl_description.setFont(Font.font("OPEN SANS", FontWeight.BOLD, 16));
        gpn_enterBooks.add(lbl_description, 3, 4);

        //TextArea para describir el art'iculo
        TextArea txa_description = new TextArea();
        txa_description.setPrefSize(400, 500);
        gpn_enterBooks.add(txa_description, 4, 4);
        
        //checkbox para verificar si es digital o no
        CheckBox chk_digital = new CheckBox("It is digital?"); //**
        chk_digital.setTextFill(Color.WHITE);
        chk_digital.setFont((Font.font("OPEN SANS", FontWeight.BOLD, 15))); //**
        gpn_enterBooks.add(chk_digital, 4, 5);//**

        //Button para ingresar el libro
        Button btn_enterBook = new Button("Insert Book");
        btn_enterBook.setFont(Font.font("OPEN SANS", FontWeight.BOLD, 15));
        btn_enterBook.setStyle("-fx-background-color: antiquewhite;-fx-border-color: TRANSPARENT");
        btn_enterBook.setOnMouseEntered((event) -> {

            btn_enterBook.setTextFill(Color.ANTIQUEWHITE);
            btn_enterBook.setStyle("-fx-background-color: lightseagreen;-fx-border-color: TRANSPARENT");

        });
        btn_enterBook.setOnMouseExited((event) -> {

            btn_enterBook.setTextFill(Color.BLACK);
            btn_enterBook.setStyle("-fx-background-color: antiquewhite;-fx-border-color: TRANSPARENT");

        });

        Label lbl_wrongSignature = new Label("Incorrect signature");
        lbl_wrongSignature.setVisible(false);
        lbl_wrongSignature.setTextFill(Color.WHITE);
        lbl_wrongSignature.setFont(Font.font("OPEN SANS", FontWeight.BOLD, 13));
        gpn_enterBooks.add(lbl_wrongSignature, 4, 1);

        //Acci'on del bot'on
        btn_enterBook.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {

                genre = cbx_genre.getValue() + "";
                idiom = cbx_idiom.getValue() + "";
                
                if (chk_digital.isSelected()) {
                    digital = true;
                } else {
                    digital = false;
                }
                
                try {

                    if (tfd_signatureBo.getText().length() < 9) {
                        lbl_wrongSignature.setVisible(true);
                    } else {
                        //Instancia de la clase BooksFile para el manejo de sds m'etodos
                        BooksFile bfile = new BooksFile(new File("./Books.dat"));
                        //Se pasan por par'ametro el valor de sus atributos
                        Books book1 = new Books(tfd_author.getText(), genre, idiom, digital, tfd_name.getText(), tfd_signatureBo.getText(), 1, txa_description.getText());

                        lbl_wrongSignature.setVisible(false);
                        //Label para mostrar que se ha hecho un ingreso exitosamente
                        Label lbl_success = new Label("¡Book entered successfully!");
                        lbl_success.setTextFill(Color.FLORALWHITE);
                        lbl_success.setFont(Font.font("OPEN SANS", FontWeight.BOLD, 16));
                        lbl_success.setVisible(false);
                        gpn_enterBooks.add(lbl_success, 3, 7, 4, 4);

                        //Label para mostrar que se deben llenar todos los espacios
                        Label lbl_error = new Label("Unfilled spaces");
                        lbl_error.setTextFill(Color.FLORALWHITE);
                        lbl_error.setFont(Font.font("OPEN SANS", FontWeight.BOLD, 16));
                        lbl_error.setVisible(false);
                        gpn_enterBooks.add(lbl_error, 3, 7, 4, 4);

                        //Se aumenta el valor de disponibilidad
                        if (bfile.avaibilityBook(book1.getSignature(), 1)) {
                            lbl_success.setVisible(true);

                            //Validaciones
                        } else if (tfd_author.getText().isEmpty() || tfd_name.getText().isEmpty() || tfd_signatureBo.getText().isEmpty() || txa_description.getText().isEmpty()) {
                            lbl_error.setVisible(true);
                            lbl_success.setVisible(false);
                            lbl_wrongSignature.setVisible(false);
                            //Se limpian los valores de los TextField y los Combobox
                            tfd_name.setText("");
                            tfd_author.setText("");
                            tfd_signatureBo.setText("");
                            txa_description.setText("");
                            cbx_genre.setPromptText("");
                            cbx_idiom.setPromptText("");
                        } else {
                            //Si pasa no pasa el if se cumple correctamente la funci'on
                            lbl_success.setVisible(true);
                            lbl_wrongSignature.setVisible(false);
                            bfile.addEndRecord(book1);
                            bfile.close();
                            //Se limpian los valores de los TextField y los Combobox
                            tfd_name.setText("");
                            tfd_author.setText("");
                            tfd_signatureBo.setText("");
                            txa_description.setText("");
                            cbx_genre.setPromptText("");
                            cbx_idiom.setPromptText("");
                        }
                    }

                } catch (IOException ex) {
                    Logger.getLogger(InterfaceModules.class.getName()).log(Level.SEVERE, "Error inserting book", ex);
                }

            }
        });
        gpn_enterBooks.add(btn_enterBook, 4, 6);

        return gpn_enterBooks;
    }//fin del m'etodo

    public static GridPane enterAudioVisual() {

        GridPane gpn_enterAudioV = new GridPane();

        //Modelaci'on del GridPane
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

        //Label tipo
        Label lbl_kind = new Label("Tipo:");
        lbl_kind.setTextFill(Color.WHITE);
        lbl_kind.setFont(Font.font("OPEN SANS", FontWeight.BOLD, 16));
        gpn_enterAudioV.add(lbl_kind, 0, 0);

        //TextField para ingresar el tipo de art'iculo Audiovisual
        TextField tfd_kind = new TextField();
        gpn_enterAudioV.add(tfd_kind, 1, 0);

        //Label signatura
        Label lbl_signatureAV = new Label("Signature:");
        lbl_signatureAV.setTextFill(Color.WHITE);
        lbl_signatureAV.setFont(Font.font("OPEN SANS", FontWeight.BOLD, 16));
        gpn_enterAudioV.add(lbl_signatureAV, 3, 0);

        //Label para ingresar la signatura del art'iculo Audiovisual
        TextField tfd_signatureAVi = new TextField();
        gpn_enterAudioV.add(tfd_signatureAVi, 4, 0);
        tfd_signatureAVi.lengthProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable,
                    Number beforeValue, Number actualValue) {
                if (actualValue.intValue() > beforeValue.intValue()) {
                    // Revisa que la longitud del texto no sea mayor a la variable definida.
                    if (tfd_signatureAVi.getText().length() >= 5) {
                        tfd_signatureAVi.setText(tfd_signatureAVi.getText().substring(0, 5));
                    }
                }
            }
        });

        //Label marca
        Label lbl_brand = new Label("Brand:");
        lbl_brand.setTextFill(Color.WHITE);
        lbl_brand.setFont(Font.font("OPEN SANS", FontWeight.BOLD, 16));
        gpn_enterAudioV.add(lbl_brand, 0, 2);

        //Label para ingresar la marca del art'iculo Audiovisual
        TextField tfd_brand = new TextField();
        gpn_enterAudioV.add(tfd_brand, 1, 2);

        //Label descripci'on
        Label lbl_description = new Label("Description:");
        lbl_description.setTextFill(Color.WHITE);
        lbl_description.setFont(Font.font("OPEN SANS", FontWeight.BOLD, 16));
        gpn_enterAudioV.add(lbl_description, 0, 4);

        //TextArea para ingresar la descripci'on del art'iculo
        TextArea txa_description = new TextArea();
        txa_description.setPrefSize(300, 400);
        gpn_enterAudioV.add(txa_description, 1, 4);

        //Bot'on que cumple la funci'on de ingresar un art'iculo
        Button btn_enterAudioV = new Button("Insert Article");
        btn_enterAudioV.setFont(Font.font("OPEN SANS", FontWeight.BOLD, 15));
        btn_enterAudioV.setStyle("-fx-background-color: antiquewhite;-fx-border-color: TRANSPARENT");
        btn_enterAudioV.setOnMouseEntered((event) -> {

            btn_enterAudioV.setTextFill(Color.ANTIQUEWHITE);
            btn_enterAudioV.setStyle("-fx-background-color: lightseagreen;-fx-border-color: TRANSPARENT");

        });
        btn_enterAudioV.setOnMouseExited((event) -> {

            btn_enterAudioV.setTextFill(Color.BLACK);
            btn_enterAudioV.setStyle("-fx-background-color: antiquewhite;-fx-border-color: TRANSPARENT");

        });

        Label lbl_wrongSignature = new Label("Incorrect signature");
        lbl_wrongSignature.setVisible(false);
        lbl_wrongSignature.setTextFill(Color.WHITE);
        lbl_wrongSignature.setFont(Font.font("OPEN SANS", FontWeight.BOLD, 13));
        gpn_enterAudioV.add(lbl_wrongSignature, 4, 1);

        btn_enterAudioV.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                try {

                    if (tfd_signatureAVi.getText().length() < 5) {
                        lbl_wrongSignature.setVisible(true);
                    } else {

                        //Instancia de la clase AudioVisualFile para el menejo de sus m'etodos
                        AudioVisualFile afile = new AudioVisualFile(new File("./AudioVisual.dat"));
                        //Se pasa por par'ametro el valor de los atributos de la clase AudioVisual
                        AudioVisual audioVisual = new AudioVisual(tfd_brand.getText(), tfd_kind.getText(), tfd_signatureAVi.getText(), 1, txa_description.getText());

                        lbl_wrongSignature.setVisible(false);

                        //Label para mostrar que el art'iculo fue ingresado correctamente
                        Label lbl_success = new Label("¡Article entered successfully!");
                        lbl_success.setTextFill(Color.FLORALWHITE);
                        lbl_success.setFont(Font.font("OPEN SANS", FontWeight.BOLD, 16));
                        lbl_success.setVisible(false);
                        gpn_enterAudioV.add(lbl_success, 3, 7, 4, 4);

                        //Label para indicar que se deben mostrar todos los espacios
                        Label lbl_error = new Label("Unfilled spaces");
                        lbl_error.setTextFill(Color.FLORALWHITE);
                        lbl_error.setFont(Font.font("OPEN SANS", FontWeight.BOLD, 16));
                        lbl_error.setVisible(false);
                        gpn_enterAudioV.add(lbl_error, 3, 7, 4, 4);

                        //Condicional que verifica si y existe para aumentar disponibilidad o ingresar como articulo nuevo
                        if (afile.avaibilityAudioVisual(audioVisual.getSignature(), 1)) {
                            lbl_success.setVisible(true);
                            lbl_error.setVisible(false);
                        } else if (tfd_brand.getText().isEmpty() || tfd_kind.getText().isEmpty() || tfd_signatureAVi.getText().isEmpty() || txa_description.getText().isEmpty()) {
                            lbl_success.setVisible(false);
                            lbl_error.setVisible(true);
                        } else {
                            lbl_success.setVisible(true);
                            lbl_error.setVisible(false);
                            afile.addEndRecord(audioVisual);
                            afile.close();
                            tfd_kind.setText("");
                            tfd_signatureAVi.setText("");
                            txa_description.setText("");
                            tfd_brand.setPromptText("");
                        }
                    }

                } catch (IOException ex) {
                    Logger.getLogger(InterfaceModules.class.getName()).log(Level.SEVERE, "Error inserting article", ex);
                }

            }
        });
        gpn_enterAudioV.add(btn_enterAudioV, 4, 6);

        return gpn_enterAudioV;
    }//fin del m'etodo

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

        //Muestra el nombre del art'iculo audiovisual
        TableColumn nameAVColumn = new TableColumn("Name");
        nameAVColumn.setMinWidth(150);
        nameAVColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

        //Muestra la signatura del art'iculo audiovisual
        TableColumn signatureAVColumn = new TableColumn("Signature");
        signatureAVColumn.setMinWidth(150);
        signatureAVColumn.setCellValueFactory(new PropertyValueFactory<>("signature"));

        //Muestra la marca del art'iculo audiovisual
        TableColumn brandAVColumn = new TableColumn("Brand");
        brandAVColumn.setMinWidth(150);
        brandAVColumn.setCellValueFactory(new PropertyValueFactory<>("brand"));

        //Muestra la disponibilidad del art'iculo audiovisual
        TableColumn availabilityAVColumn = new TableColumn("Availability");
        availabilityAVColumn.setMinWidth(150);
        availabilityAVColumn.setCellValueFactory(new PropertyValueFactory<>("availability"));

        //Muestra la descripci'on del art'iculo audiovisual
        TableColumn descritionAVColumn = new TableColumn("Description");
        descritionAVColumn.setMinWidth(150);
        descritionAVColumn.setCellValueFactory(new PropertyValueFactory<>("description"));

        //Agregaci'on de los n'odulos a la tabla
        tvw_viewAudiovisual.getColumns().addAll(nameAVColumn, signatureAVColumn, brandAVColumn, availabilityAVColumn, descritionAVColumn);
        tvw_viewAudiovisual.setPrefSize(1100, 375);
        tvw_viewAudiovisual.setTableMenuButtonVisible(true);
        gpn_viewMaterial.add(tvw_viewAudiovisual, 1, 1);

        HBox hbx_2nodes = new HBox();
        hbx_2nodes.setSpacing(10);

        //label por si no selecciono un item en la tabla
        Label lbl_unselected_row = new Label("You have not selected any article");
        lbl_unselected_row.setVisible(false);
        lbl_unselected_row.setTextFill(Color.FLORALWHITE);
        lbl_unselected_row.setFont(Font.font("OPEN SANS", FontWeight.BOLD, 16));

        //exito al ingresar ejemplares
        Label lbl_success_row = new Label("Increased succesfully!");
        lbl_success_row.setVisible(false);
        lbl_success_row.setTextFill(Color.FLORALWHITE);
        lbl_success_row.setFont(Font.font("OPEN SANS", FontWeight.BOLD, 16));

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

        //Muestra el nombre del libro
        TableColumn nameBColumn = new TableColumn("Name");
        nameBColumn.setMinWidth(150);
        nameBColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

        //Muestra el autor del libro
        TableColumn authorBColumn = new TableColumn("Author");
        authorBColumn.setMinWidth(150);
        authorBColumn.setCellValueFactory(new PropertyValueFactory<>("autor"));

        //Muestra la signatura del libro
        TableColumn signatureBColumn = new TableColumn("Signature");
        signatureBColumn.setMinWidth(150);
        signatureBColumn.setCellValueFactory(new PropertyValueFactory<>("signature"));

        //Muestra el g'enero del libro
        TableColumn genreBColumn = new TableColumn("Genre");
        genreBColumn.setMinWidth(150);
        genreBColumn.setCellValueFactory(new PropertyValueFactory<>("genre"));

        //Muestra el idioma del libro
        TableColumn lenguageBColumn = new TableColumn("Idiom");
        lenguageBColumn.setMinWidth(150);
        lenguageBColumn.setCellValueFactory(new PropertyValueFactory<>("language"));
        
        //Muestra si un libro es digital o no
        TableColumn digitalColumn = new TableColumn("Digital"); //**
        digitalColumn.setMinWidth(100); //**
        digitalColumn.setCellValueFactory(new PropertyValueFactory<>("digital")); //**
        digitalColumn.setCellFactory(col -> new TableCell<Books, Boolean>() {
            @Override
            protected void updateItem(Boolean item, boolean empty) {
                super.updateItem(item, empty) ;
                setText(empty ? null : item ? "Digital" : "Not Digital" );
            }
        });//**

        //Muestra la disponibilidad del libro
        TableColumn availabilityBColumn = new TableColumn("Availability");
        availabilityBColumn.setMinWidth(10);
        availabilityBColumn.setCellValueFactory(new PropertyValueFactory<>("availability"));

        //Muestra la descripci'on del libro
        TableColumn descriptionBColumn = new TableColumn("Description");
        descriptionBColumn.setMinWidth(220);
        descriptionBColumn.setCellValueFactory(new PropertyValueFactory<>("description"));

        //Agregaci'on de los n'odulos a la tabla
        tvw_viewBooks.getColumns().addAll(nameBColumn, authorBColumn, signatureBColumn, genreBColumn,
                lenguageBColumn, digitalColumn, availabilityBColumn, descriptionBColumn);
        tvw_viewBooks.setPrefSize(1100, 375);
        gpn_viewMaterial.add(tvw_viewBooks, 1, 1);

        HBox hbx_2nodesB = new HBox();
        hbx_2nodesB.setSpacing(10);

        //label por si no selecciono un item en la tabla
        Label lbl_unselected_rowB = new Label("You have not selected any article");
        lbl_unselected_rowB.setVisible(false);
        lbl_unselected_rowB.setTextFill(Color.FLORALWHITE);
        lbl_unselected_rowB.setFont(Font.font("OPEN SANS", FontWeight.BOLD, 16));

        //exito al ingresar ejemplares
        Label lbl_success_rowB = new Label("Increased succesfully!");
        lbl_success_rowB.setVisible(false);
        lbl_success_rowB.setTextFill(Color.FLORALWHITE);
        lbl_success_rowB.setFont(Font.font("OPEN SANS", FontWeight.BOLD, 16));

        //inrementalibros
        Button btn_increaseAvaibilityB = new Button("Increase availability");
        btn_increaseAvaibilityB.setVisible(true);
        btn_increaseAvaibilityB.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                Books book1 = tvw_viewBooks.getSelectionModel().getSelectedItem();

                //Validaci'on
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
                    //Validaci'on
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

        //Label book
        Label lbl_titleBooks = new Label("Books");
        lbl_titleBooks.setFont(Font.font("Opens Sans"));
        lbl_titleBooks.setTextFill(Color.WHITE);
        lbl_titleBooks.setStyle("-fx-font-weight: bold; -fx-font-size: 25");
        gpn_viewMaterial.add(lbl_titleBooks, 1, 2);

        //Label audiovisual
        Label lbl_titleAudioVisuals = new Label("AudioVisuales");
        lbl_titleAudioVisuals.setVisible(false);
        lbl_titleAudioVisuals.setFont(Font.font("Opens Sans"));
        lbl_titleAudioVisuals.setTextFill(Color.WHITE);
        lbl_titleAudioVisuals.setStyle("-fx-font-weight: bold; -fx-font-size: 25");
        gpn_viewMaterial.add(lbl_titleAudioVisuals, 1, 2);

        btn_increaseAvaibility.setVisible(true);

        /*Bot'on que cumple la funci'on de mostrar el registro de libros
        al mostar la tabla*/
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

        /*Bot'on que cumple la funci'on de mostrar el registro de material audiovisual
        al mostar la tabla*/
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

    }//fin del m'etodo

    public static GridPane enterLoan() throws IOException, ClassNotFoundException {

        //Instancia de las clases que pertenecen al paquete File para el manejo de sus m'etodos
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

        //Label carn'e
        lbl_idStudent = new Label("Student ID");
        lbl_idStudent.setTextFill(Color.WHITE);
        lbl_idStudent.setFont(Font.font("Arial", FontWeight.BOLD, 15));
        gpn_enterLoan.add(lbl_idStudent, 0, 3);

        //Label que se muestra cuando el estudiante no ha sido encontrado en los registros
        Label lbl_exception = new Label("Student not found");
        lbl_exception.setTextFill(Color.WHITE);
        lbl_exception.setFont(Font.font("OPEN SANS", FontWeight.BOLD, 16));
        lbl_exception.setVisible(false);
        gpn_enterLoan.add(lbl_exception, 1, 5);

        //Label que muestra la informaci'on del estudiante 
        Label lbl_studentInfo = new Label();
        lbl_studentInfo.setTextFill(Color.WHITE);
        lbl_studentInfo.setFont(Font.font("OPEN SANS", FontWeight.BOLD, 16));
        gpn_enterLoan.add(lbl_studentInfo, 0, 4, 2, 5);

        //TextField carn'e
        tfd_idStudent = new TextField();
        Tooltip enter = new Tooltip("Press the enter key to check the ID");
        tfd_idStudent.setTooltip(enter);

        Label lbl_enter = new Label("Press the enter key to check the ID");
        lbl_enter.setTextFill(Color.WHITE);
        lbl_enter.setFont(Font.font("OPEN SANS", FontWeight.BOLD, 13));
        gpn_enterLoan.add(lbl_enter, 1, 4);
        
        StudentFile sft = new StudentFile();
        String idOptions[] = sft.autocompleteOptions();
        TextFields.bindAutoCompletion(tfd_idStudent, idOptions);

        //Funci'on del TextField
        tfd_idStudent.setOnAction((event) -> {
            try {
                if (sft.checkStudentRecord(tfd_idStudent.getText())) {
                    btn_checkStudent.setDisable(false);
                    lbl_exception.setVisible(false);
                    lbl_studentInfo.setText(sft.studentInfo(tfd_idStudent.getText()));
                    tfd_idStudent.setDisable(true);
                    lbl_enter.setVisible(false);
                } else {
                    tfd_idStudent.clear();
                    lbl_exception.setVisible(true);
                    btn_checkStudent.setDisable(true);
                }
            } catch (FileNotFoundException | ClassNotFoundException | OptionalDataException ex) {
                Logger.getLogger(InterfaceModules.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(InterfaceModules.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        //M'etodo para limitar el ingreso de caracteres en el TextField carn'e
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

        //Label de advertencia que el no se ha ingresado un valor correcto o no se ha ingresado valor
        Label lbl_notValue = new Label("Fill the fields");
        lbl_notValue.setVisible(false);
        lbl_notValue.setTextFill(Color.FLORALWHITE);
        lbl_notValue.setFont(Font.font("OPEN SANS", FontWeight.BOLD, 16));
        gpn_enterLoan.add(lbl_notValue, 2, 4, 3, 4);
        btn_checkStudent = new Button("Enter");
        btn_checkStudent.setDisable(true);
        btn_checkStudent.setOnAction((event) -> {

            //Validaci'on
            if (tfd_idStudent.getText().length() == 0) {
                lbl_notValue.setVisible(true);
            } else {
                lbl_notValue.setVisible(false);
                String student = tfd_idStudent.getText().replaceAll(" ", "");
                lbl_idStudent.setVisible(false);
                tfd_idStudent.setVisible(false);
                lbl_enter.setVisible(false);
                btn_checkStudent.setVisible(false);
                rdb_choiceAV.setVisible(true);
                rdb_choiceBook.setVisible(true);

            }

        });//end Button

        gpn_enterLoan.add(btn_checkStudent, 3, 3);

        //Label nombre del art'iculo
        lbl_nameArticle = new Label("Name of Article");
        lbl_nameArticle.setVisible(false);
        lbl_nameArticle.setTextFill(Color.WHITE);
        lbl_nameArticle.setFont(Font.font("OPEN SANS", FontWeight.BOLD, 16));
        gpn_enterLoan.add(lbl_nameArticle, 0, 2);

        //TextField para ingresar el nombre del art'iculo
        tfd_nameArticle = new TextField();
        tfd_nameArticle.setVisible(false);

        //Opciones de autocompletado
        String materialOptions[] = avfile.autocompleteOptions();
        TextFields.bindAutoCompletion(tfd_nameArticle, materialOptions);
        tfd_nameArticle.setOnAction((event) -> {

            try {
                BooksFile bf= new BooksFile(new File("./Books.dat"));
                tfd_signatureB.setText(bf.getSignature(tfd_nameArticle.getText()));
                tfd_signatureAV.setText(avfile.getSignature(tfd_nameArticle.getText()));
            } catch (IOException ex) {
                Logger.getLogger(InterfaceModules.class.getName()).log(Level.SEVERE, null, ex);
            }

        });

        gpn_enterLoan.add(tfd_nameArticle, 1, 2);

        //Label signatura
        lbl_signature = new Label("Article Signature");
        lbl_signature.setVisible(false);
        lbl_signature.setTextFill(Color.WHITE);
        lbl_signature.setFont(Font.font("OPEN SANS", FontWeight.BOLD, 16));
        gpn_enterLoan.add(lbl_signature, 0, 3);

        //TextField para ingresar la signatura del libro
        tfd_signatureB = new TextField("ISBN-");
        tfd_signatureB.setVisible(false);
        gpn_enterLoan.add(tfd_signatureB, 1, 3);
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

        //Label d'ia de devoluci'on
        lbl_deliveryDay = new Label("Delivery day");
        lbl_deliveryDay.setVisible(false);
        lbl_deliveryDay.setTextFill(Color.WHITE);
        lbl_deliveryDay.setFont(Font.font("OPEN SANS", FontWeight.BOLD, 16));
        gpn_enterLoan.add(lbl_deliveryDay, 3, 2);

        //DatePicker para elegir el d'ia de devoluci'on
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

        //Label para indicar que el pr'estamo no ha sido registrado
        lbl_warning = new Label("¡Unregistered loan!");
        lbl_warning.setVisible(false);
        lbl_warning.setTextFill(Color.FLORALWHITE);
        //lbl_warning.setFont(Font.font("Arial", FontWeight.BOLD, 15));
        gpn_enterLoan.add(lbl_warning, 3, 4, 4, 6);

        //Label para indicar que el registro del pr'estamo fue exitoso
        lbl_success = new Label("¡The loan was registered!");
        lbl_success.setVisible(false);
        lbl_success.setTextFill(Color.FLORALWHITE);
        lbl_success.setFont(Font.font("OPEN SANS", FontWeight.BOLD, 16));
        gpn_enterLoan.add(lbl_success, 3, 4, 4, 6);

        //ToggleGroup para mostrar RadioButton's
        ToggleGroup group = new ToggleGroup();

        /*RadioButton para elegir la opci'on libros
        despliega las opciones para ingresar un libro*/
        rdb_choiceBook = new RadioButton("Book");
        rdb_choiceBook.setTextFill(Color.WHITE);
        rdb_choiceBook.setVisible(false);
        rdb_choiceBook.setToggleGroup(group);
        rdb_choiceBook.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {

                //Se muestran las opciones de ingreso
                tfd_signatureAV.setVisible(false);
                lbl_signature.setVisible(true);
                tfd_signatureB.setVisible(true);
                lbl_deliveryDay.setVisible(true);
                dpk_delivaeyDay.setVisible(true);
                btn_enterLoan.setVisible(true);
                btn_exit.setVisible(true);
                lbl_nameArticle.setVisible(true);
                tfd_nameArticle.setVisible(true);
                lbl_enter.setVisible(false);

                btn_enterLoan.setDisable(false);
                dpk_delivaeyDay.setValue(LocalDate.now());

            }
        });
        gpn_enterLoan.add(rdb_choiceBook, 0, 1);

        //TextField para ingresar la signatura del art'iculo audiovisual
        tfd_signatureAV = new TextField();
        tfd_signatureAV.setVisible(false);
        //metodo para establecer un tamaño m'aximo de ingreso de valores en un TextField
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
        gpn_enterLoan.add(tfd_signatureAV, 1, 3);

        /*RadioButton para elegir la opci'on audiovisual
        despliega las opciones para ingresar un art'iculo audiovisual*/
        rdb_choiceAV = new RadioButton("Audiovisual");
        rdb_choiceAV.setTextFill(Color.WHITE);
        rdb_choiceAV.setVisible(false);
        rdb_choiceAV.setToggleGroup(group);
        rdb_choiceAV.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {

                //Se muestran las opciones de ingreso
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
                lbl_enter.setVisible(false);

            }
        });
        gpn_enterLoan.add(rdb_choiceAV, 1, 1);

        //Label para indicar que se debe presionar el bot'on "Exit" para realizar otro pr'estamo 
        lbl_info = new Label("Exit");
        lbl_info.setVisible(false);
        gpn_enterLoan.add(lbl_info, 2, 6, 3, 6);

        //Label para indicar que se debe llenar todos los espacios
        Label lbl_notValueEnter = new Label("Fill all spaces");
        lbl_notValueEnter.setVisible(false);
        lbl_notValueEnter.setTextFill(Color.FLORALWHITE);
        lbl_notValueEnter.setFont(Font.font("OPEN SANS", FontWeight.BOLD, 16));
        gpn_enterLoan.add(lbl_notValueEnter, 3, 4, 4, 6);

        //Bot'on que tiene como funci'on ingresar los valores para registrar un nuevo pr'estamo 
        btn_enterLoan = new Button("Enter Loan");
        btn_enterLoan.setVisible(false);
        btn_enterLoan.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                String idStudent = tfd_idStudent.getText().replaceAll(" ", "");

                //Validaci'on
                if ((tfd_signatureB.getText().replaceAll(" ", "").length() == 5
                        && tfd_signatureAV.getText().replaceAll(" ", "").length() == 0) || valueDelivery3 == 0) {
                    lbl_notValueEnter.setVisible(true);

                } else {
                    signatureB = tfd_signatureB.getText();
                    signatureAV = tfd_signatureAV.getText();
                    String loanDay = "" + LocalDate.now();
                    String deliveryDay = "" + date1;
                    boolean kind = false;

                    //Condici'on para saber el tipo de art'iculo que se est'a registrando
                    if (rdb_choiceAV.isSelected()) {
                        try {
                            if (avfile.getAvailability(signatureAV)) {
                                kind = true;
                                loan1 = new Loan(idStudent, signatureAV, loanDay, deliveryDay, kind);
                                //Se disminuye la disponibilidad del art'iculo audiovisual
                                avfile.lessAvaibilityAudioVisual(signatureAV);
                                avfile.close();
                                lFile.addEndRecord(loan1);
                                //Se dashabilitan las funciones
                                lbl_success.setVisible(true);
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
                                tfd_nameArticle.setText("");
                                lbl_enter.setVisible(false);

                            } else {
                                lbl_warning.setVisible(true);
                                lbl_warning.setText("Material not available");
                            }
                        } catch (IOException ex) {
                            Logger.getLogger(InterfaceModules.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    } else {
                        //Implementar excepcion 
                        try {
                            if (bfile.getAvailability(signatureB)) {
                                kind = false;
                                loan1 = new Loan(idStudent, signatureB, loanDay, deliveryDay, kind);
                                //Se disminuye la disponibilidad del libro
                                bfile.lessAvaibilityBook(signatureB);
                                bfile.close();
                                lFile.addEndRecord(loan1);
                                //Se deshabilidan las funciones
                                lbl_success.setVisible(true);
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
                                tfd_nameArticle.setText("");
                                lbl_enter.setVisible(false);

                            } else {
                                lbl_warning.setVisible(true);
                                lbl_warning.setText("Material not availabl");
                            }

                        } catch (IOException ex) {
                            Logger.getLogger(InterfaceModules.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }

                }

            }

        });
        gpn_enterLoan.add(btn_enterLoan, 4, 4);

        //Bot'on que tiene como funci'on hacer invisibles todas las funciones y mostrar la "p'agina por defecto"
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
            lbl_enter.setVisible(true);
            tfd_nameArticle.setDisable(false);
            lbl_studentInfo.setVisible(false);
        });
        gpn_enterLoan.add(btn_exit, 4, 6);

        return gpn_enterLoan;

    }//fin del m'etodo

    public static GridPane deleteLoans() throws IOException {

        //Instancia de las clases LoanFile, AudioVisualFile, BooksFile, para el manejo de sus m'etodos
        LoanFile lfile = new LoanFile(new File("./Loans.dat"));
        AudioVisualFile avfile = new AudioVisualFile(new File("./AudioVisual.dat"));
        BooksFile bfile = new BooksFile(new File("./Books.dat"));

        GridPane gpn_deleteLoan = new GridPane();
        //Configuraci'on del GridPage
        gpn_deleteLoan.getColumnConstraints().add(new ColumnConstraints(200));
        gpn_deleteLoan.getColumnConstraints().add(new ColumnConstraints(200));
        gpn_deleteLoan.getColumnConstraints().add(new ColumnConstraints(200));
        gpn_deleteLoan.getColumnConstraints().add(new ColumnConstraints(200));
        gpn_deleteLoan.getColumnConstraints().add(new ColumnConstraints(200));
        gpn_deleteLoan.getColumnConstraints().add(new ColumnConstraints(200));
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

        //Label carn'e
        Label lbl_idLoan = new Label("ID Student");
        lbl_idLoan.setTextFill(Color.WHITE);
        lbl_idLoan.setFont(Font.font("Arial", FontWeight.BOLD, 15));
        gpn_deleteLoan.add(lbl_idLoan, 1, 2);

        //TextField para ingresar el carn'e
        TextField tfd_idLoan = new TextField();
        gpn_deleteLoan.add(tfd_idLoan, 1, 3);

        //Label signatura
        Label lbl_signatLoan = new Label("Signature");
        lbl_signatLoan.setTextFill(Color.WHITE);
        lbl_signatLoan.setFont(Font.font("Arial", FontWeight.BOLD, 15));
        gpn_deleteLoan.add(lbl_signatLoan, 3, 2);

        //TextField para ingresar la signatura
        TextField tfd_signatLoan = new TextField();
        gpn_deleteLoan.add(tfd_signatLoan, 3, 3);

        //Label que indica que el pr'estamo buscado no ha sido registrado
        Label lbl_warningL = new Label("Unregistered loan");
        lbl_warningL.setVisible(false);
        lbl_warningL.setFont(Font.font("Arial", FontWeight.BOLD, 15));
        lbl_warningL.setTextFill(Color.FLORALWHITE);
        gpn_deleteLoan.add(lbl_warningL, 4, 3);

        //Label loan
        Label lbl_loans = new Label("Loan");
        lbl_loans.setTextFill(Color.WHITE);
        lbl_loans.setFont(Font.font("Arial", FontWeight.BOLD, 15));
        lbl_loans.setVisible(false);
        gpn_deleteLoan.add(lbl_loans, 0, 0);

        //Label para mostrar informaci'on del pr'estamo (Carn'e del estudiante)
        Label lbl_idStudentL = new Label();
        lbl_idStudentL.setFont(Font.font("Arial", FontWeight.BOLD, 15));
        lbl_idStudentL.setTextFill(Color.FLORALWHITE);
        gpn_deleteLoan.add(lbl_idStudentL, 1, 1);

        //Label para mostrar informaci'on del pr'estamo (Signatura del art'iculo)
        Label lbl_signatureL = new Label();
        lbl_signatureL.setFont(Font.font("Arial", FontWeight.BOLD, 15));
        lbl_signatureL.setTextFill(Color.FLORALWHITE);
        gpn_deleteLoan.add(lbl_signatureL, 2, 1);

        //Label para mostrar informaci'on del pr'estamo (D'ia de registro)
        Label lbl_loanDayL = new Label();
        lbl_loanDayL.setFont(Font.font("Arial", FontWeight.BOLD, 15));
        lbl_loanDayL.setTextFill(Color.FLORALWHITE);
        gpn_deleteLoan.add(lbl_loanDayL, 3, 1);

        //Label para mostrar informaci'on del pr'estamo (D'is de devoluci'on)
        Label lbl_deliveyDayL = new Label();
        lbl_deliveyDayL.setFont(Font.font("Arial", FontWeight.BOLD, 15));
        lbl_deliveyDayL.setTextFill(Color.FLORALWHITE);
        gpn_deleteLoan.add(lbl_deliveyDayL, 4, 1);

        //Label para mostrar informaci'on del pr'estamo (Tipo de art'iculo)
        Label lbl_kindL = new Label();
        lbl_kindL.setFont(Font.font("Arial", FontWeight.BOLD, 15));
        lbl_kindL.setTextFill(Color.FLORALWHITE);
        gpn_deleteLoan.add(lbl_kindL, 5, 1);

        //Label Payment
        Label lbl_payL = new Label("Payment");
        lbl_payL.setTextFill(Color.WHITE);
        lbl_payL.setFont(Font.font("Arial", FontWeight.BOLD, 15));
        lbl_payL.setVisible(false);
        gpn_deleteLoan.add(lbl_payL, 0, 2);

        /*Label para mostrar informaci'on del pr'estamo (Cantidad en colones 
        que se deben pagar por los días de atraso)*/
        Label lbl_payLoan = new Label();
        lbl_payLoan.setFont(Font.font("Arial", FontWeight.BOLD, 15));
        lbl_payLoan.setTextFill(Color.FLORALWHITE);
        gpn_deleteLoan.add(lbl_payLoan, 1, 2, 3, 3);

        //Bot'on que cumple con la funci'on de buscar pr'estramo
        Button btn_search = new Button("Search");
        btn_search.setOnAction((ActionEvent event) -> {

            signature = tfd_signatLoan.getText().replaceAll(" ", "");
            idLoan = tfd_idLoan.getText().replaceAll(" ", "");

            try {
                //Validaci'on
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
                    /*Al ser un booleano, se realiza una condici'on para mostrar si es true "Audiovisual" o 
                    o por lo contrario si es false se muestra "Libro"*/
                    if (lfile.getLoan(lfile.searchLoan(signature)).getKind()) {
                        lbl_kindL.setText("Article: AudioVisual");
                    } else {
                        lbl_kindL.setText("Article: Libro");
                    }
                    lbl_payL.setVisible(true);
                    btn_delete.setVisible(true);

                    //Instancia para mostrar el valor de la fecha actual en el formato ""yyyy-MM-dd""
                    LocalDate date = LocalDate.now();
                    String dateNow = "" + date;

                    long balance = lfile.numberOfDays(lfile.getLoan(lfile.searchLoan(signature)).getDeliveryDay(), dateNow);

                    //Condici'on para mostrar una salida en el label de morosidad
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

        //Label para indicar que el pr'etamos se elimin'o no exito
        Label lbl_successL = new Label("It has been removed successfully");
        lbl_successL.setTextFill(Color.FLORALWHITE);
        lbl_successL.setFont(Font.font("Arial", FontWeight.BOLD, 15));
        lbl_successL.setVisible(false);
        gpn_deleteLoan.add(lbl_successL, 4, 5, 5, 5);

        //Label disponibilidad
        Label lbl_avaibility = new Label();
        lbl_avaibility.setTextFill(Color.WHITE);
        lbl_avaibility.setFont(Font.font("Arial", FontWeight.BOLD, 15));
        gpn_deleteLoan.add(lbl_avaibility, 4, 6, 5, 6);

        //Bot'on que cumple la funci'on de eliminar el registro que se indica
        btn_delete = new Button("Delete");
        btn_delete.setVisible(false);
        btn_delete.setOnAction(
                (event) -> {

                    try {
                        //si la condicion se satisface es porque el articulo pertenece a Audiovisuales
                        if (lfile.getLoan(lfile.searchLoan(signature)).getKind()) {
                            avfile.avaibilityAudioVisual(signature, 1);
                        } else {
                            bfile.avaibilityBook(signature, 1);
                        }

                        //Se elimina el registro si el if anterior no se cumple
                        lfile.deleteLoan(signature);
                        lfile.close();

                        //Mensaje para indicar que la disponibilidad en este articulo a aumentado
                        lbl_avaibility.setText("The availability of article " + signature + " was increased");
                        //Manejo de interfaz deshabilitando y haciendo invisible algunas funciones
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

        /*Bot'on que cumple con la funci'on de ir a la "p'agina principal"
        haciendo visible algunos n'odulos e invisible otros*/
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

    }//fin del m'etodo

    public static GridPane viewLoans() throws IOException {

        GridPane gpn_viewloans = new GridPane();
        //Menejo del GridPane
        gpn_viewloans.setAlignment(Pos.TOP_CENTER);
        gpn_viewloans.setPadding(new Insets(20));
        gpn_viewloans.setPrefSize(700, 800);

        //Creaci'on de una tabla donde se mostrar'a los registros realizados
        TableView<Loan> tvw_viewLoan = new TableView();

        LoanFile lfile = new LoanFile(new File("./Loans.dat"));
        ObservableList<Loan> data = lfile.getAllLoans();
        tvw_viewLoan.setItems(data);

        //Muestra el carn'e del estudiante
        TableColumn idColumn = new TableColumn("ID Student");
        idColumn.setMinWidth(150);
        idColumn.setCellValueFactory(new PropertyValueFactory<>("studentId"));

        //Muestra la signatura del art'iculo
        TableColumn signatureColumn = new TableColumn("Signature");
        signatureColumn.setMinWidth(150);
        signatureColumn.setCellValueFactory(new PropertyValueFactory<>("signature"));

        //Muestra el d'ia que se llev'o a cabo el pr'estamo
        TableColumn loanDayColumn = new TableColumn("Loan Day");
        loanDayColumn.setMinWidth(150);
        loanDayColumn.setCellValueFactory(new PropertyValueFactory<>("loanDay"));

        //Muestra el d'ia que se debe devolver el art'iculo
        TableColumn deliveryColumn = new TableColumn("Delivery Day");
        deliveryColumn.setMinWidth(150);
        deliveryColumn.setCellValueFactory(new PropertyValueFactory<>("deliveryDay"));

        //Integraci'on de los n'odulos a la tabla
        tvw_viewLoan.getColumns().addAll(idColumn, signatureColumn, loanDayColumn, deliveryColumn);
        tvw_viewLoan.setPrefSize(600, 500);
        tvw_viewLoan.setTableMenuButtonVisible(true);
        gpn_viewloans.add(tvw_viewLoan, 0, 0);

        return gpn_viewloans;

    }//fin del m'etodo

}//Fin de la clase
