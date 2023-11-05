package test;

import javax.swing.table.TableColumn;
import javax.swing.text.TableView;

import org.graalvm.compiler.phases.common.NodeCounterPhase.Stage;

import Model.Person;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;

public class JSONTableDisplay extends Application {
    @Override
    public void start(Stage stage) {
        TableView<Person> table = new TableView<>();
        table.setItems(FXCollections.observableList(persons));

        TableColumn<Person, String> nameCol = new TableColumn<>("Name");
        nameCol.setCellValueFactory(data -> data.getValue().nameProperty());
        TableColumn<Person, Integer> ageCol = new TableColumn<>("Age");
        ageCol.setCellValueFactory(data -> data.getValue().ageProperty());
        TableColumn<Person, String> emailCol = new TableColumn<>("Email");
        emailCol.setCellValueFactory(data -> data.getValue().emailProperty());

        table.getColumns().addAll(nameCol, ageCol, emailCol);

        VBox root = new VBox(table);
        Scene scene = new Scene(root, 600, 400);
        stage.setScene(scene);
        stage.setTitle("JSON Table Display");
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

