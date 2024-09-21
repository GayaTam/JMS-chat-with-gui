package chat;

import java.io.File;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnectionFactory;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.application.Platform;
import javafx.collections.ListChangeListener;

public class JMSChat extends Application {
    private MessageProducer messageProducer;
    private Session session;
    private String codeUser;

    public static void main(String[] args) {
        Application.launch(JMSChat.class);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("JMS Chat");
        BorderPane borderPane = new BorderPane();
        HBox hBox = new HBox();
        hBox.setPadding(new Insets(10, 0, 0, 0));
        hBox.setSpacing(10);
        Label labelCode = new Label("Code: ");
        TextField textFieldCode = new TextField("C1");

        Label labelHost = new Label("Host: ");
        TextField textFieldHost = new TextField("localhost");

        Label labelPort = new Label("Port: ");
        TextField textFieldPort = new TextField("61616");

        Button buttonConnecter = new Button("Connecter");
        hBox.getChildren().addAll(labelCode, textFieldCode, labelHost, textFieldHost, labelPort, textFieldPort,
                buttonConnecter);

        borderPane.setTop(hBox);

        VBox vBox = new VBox();
        GridPane gridPane = new GridPane();
        VBox hBox2 = new VBox();
        vBox.getChildren().add(gridPane);
        vBox.getChildren().add(hBox2);
        borderPane.setCenter(vBox);

        Label labelTo = new Label("To : ");
        TextField textFieldTo = new TextField("C1");
        textFieldTo.setPrefWidth(150);
        Label labelMessage = new Label("Message : ");
        TextField textFieldMessage = new TextField();
        Button buttonEnvoyer = new Button("Envoyer");
        Label labelImage = new Label("Image : ");
        File f = new File("images");
        ObservableList<String> observableListImages = FXCollections.observableArrayList(f.list());
        ComboBox<String> comboBoxImages = new ComboBox<>(observableListImages);
        comboBoxImages.getSelectionModel().select(0);
        Button buttonEnvoyerImage = new Button("Envoyer Image");

        gridPane.setPadding(new Insets(10));
        gridPane.setVgap(10);
        gridPane.setHgap(10);
        textFieldMessage.setPrefWidth(250);

        gridPane.add(labelTo, 0, 0);
        gridPane.add(textFieldTo, 1, 0);

        gridPane.add(labelMessage, 0, 1);
        gridPane.add(textFieldMessage, 1, 1);

        gridPane.add(labelImage, 0, 2);
        gridPane.add(comboBoxImages, 1, 2);
        gridPane.add(buttonEnvoyerImage, 2, 2);
        gridPane.add(buttonEnvoyer, 1, 3);

        ObservableList<String> observableListMessages = FXCollections.observableArrayList();

        ListView<String> listviewMessages = new ListView<>(observableListMessages);
        listviewMessages.setStyle("-fx-control-inner-background: #ffffff;");

        hBox2.getChildren().add(listviewMessages);
        hBox2.setPadding(new Insets(10));
        hBox2.setSpacing(10);

        Scene scene = new Scene(borderPane, 800, 500);
        primaryStage.setScene(scene);
        primaryStage.setTitle("JMS Chat");
        primaryStage.show();

        observableListMessages.addListener((ListChangeListener<String>) change -> {
            listviewMessages.setItems(observableListMessages);
        });

        buttonEnvoyer.setOnAction(e -> {
            // Envoi du message
            try {
                TextMessage textMessage = session.createTextMessage();
                String messageText = textFieldMessage.getText();
                textMessage.setText(messageText);
                textMessage.setStringProperty("code", textFieldTo.getText());
                messageProducer.send(textMessage);
                textFieldMessage.clear(); // Effacer le champ de texte après l'envoi
                observableListMessages.add(messageText); // Ajouter le message à la ListView
            } catch (JMSException ex) {
                ex.printStackTrace();
            }
        });


        buttonConnecter.setOnAction(event -> {
            // Connexion au serveur
            try {
                codeUser = textFieldCode.getText();
                String host = textFieldHost.getText();
                int port = Integer.parseInt(textFieldPort.getText());
                ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://" + host + ":" + port);
                Connection connection = connectionFactory.createConnection();
                connection.start();
                session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
                Destination destination = session.createTopic("enset.chat");
                MessageConsumer messageConsumer = session.createConsumer(destination);
                messageProducer = session.createProducer(destination);
                messageProducer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
                messageConsumer.setMessageListener(message -> {
                    // Réception des messages
                    if (message instanceof TextMessage) {
                        try {
                            TextMessage textMessage = (TextMessage) message;
                            String receivedCode = textMessage.getStringProperty("code");
                            if (codeUser.equals(receivedCode)) {
                                String receivedMessage = textMessage.getText();
                                Platform.runLater(() -> {
                                    if (!observableListMessages.contains(receivedMessage)) {
                                        observableListMessages.add(receivedMessage);
                                    }
                                });
                            }
                        } catch (JMSException e) {
                            e.printStackTrace();
                        }
                    }
                });

            } catch (NumberFormatException ex) {
                System.err.println("Port non valide !");
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });


    }
}
