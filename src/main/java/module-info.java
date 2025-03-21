module com.example.ahorcado {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.json;  // Nueva dependencia para JSON.org
    requires org.hibernate.orm.core;
    requires jakarta.persistence;
    requires java.naming;

    opens Tests to org.hibernate.orm.core, org.json;
    opens com.example.ahorcado to javafx.fxml;
    exports com.example.ahorcado;
}

