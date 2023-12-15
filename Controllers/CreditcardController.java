package Talabat.Controllers;

import Talabat.Classes.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.event.ActionEvent;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CreditcardController {
    private static User user;
    private final List<CreditCard> creditcards = new ArrayList<>();
    String userinputcardnumber;
    @FXML
    private Button Submit;
    @FXML
    private Label cardnumberlabel;
    @FXML
    private Label cardholderslabel;
    @FXML
    private Label expirationdatelabel;
    @FXML
    private Label cardTypeLabel;
    @FXML
    private Label cvvlabel;
    @FXML
    private TextField cardnumberTextField; // Add these fields
    @FXML
    private TextField cvvTextField;
    @FXML
    private TextField expirationdateTextField;
    @FXML
    private TextField cardholdernameTextField;
    @FXML
    private TextField cardtypeTextField;
    private CreditCard creditcard;

    public static void setUser(User user1) {
        user = user1;
    }

    public void Submit(ActionEvent event) {
        try {
            boolean isValid = true;
            String cardnumber = cardnumberTextField.getText();
            String cardholders = cardholdernameTextField.getText();
            String cvvtex = cvvTextField.getText();
            String expirationdate = expirationdateTextField.getText();
            String cardtype = identifyCreditCardType(cardnumber);
            cardTypeLabel.setText(cardtype);
            userinputcardnumber = cardnumber;
            if (cardnumber.isEmpty()) {
                cardnumberlabel.setText("Card Number is required");
                isValid = false;
            } else if (cardnumber.length() < 16 || !cardnumber.matches("\\d+")) {
                cardnumberlabel.setText("Card number is invalid.");
                isValid = false;
            } else {
                cardnumberlabel.setText("");
            }
            if (cardholders.isEmpty()) {
                cardholderslabel.setText("Cardholder name is required.");
                isValid = false;
            } else {
                cardholderslabel.setText("");
            }
            if (expirationdate.isEmpty()) {
                expirationdatelabel.setText("Expiration Date is required");
                isValid = false;

            } else if (expirationdate.length() != 5) {
                expirationdatelabel.setText("Expiration Date is invalid");
                isValid = false;

            } else {
                expirationdatelabel.setText("");
            }
            if (cvvtex.isEmpty()) {
                cvvlabel.setText("CVV is require");
                isValid = false;
            } else if (cvvtex.length() != 3) {
                cvvlabel.setText("CVV is invalid");
                isValid = false;
            } else {
                cvvlabel.setText("");
            }
            int cvv = Integer.parseInt(cvvtex);
            for (CreditCard existingCard : creditcards) {

                if (existingCard.getCardNumber().equals(cardnumber)) {
                    cardnumberlabel.setText("This card already exists.");
                    isValid = false;
                    break;
                }
            }
            if (isValid) {
                creditcard = new CreditCard(cardnumber, cvv, cardholders, expirationdate);
                creditcards.add(creditcard);
                user.addCreditCard(cardnumber, cvv, cardholders, expirationdate);
                cardnumberlabel.setText("");
                switchToUserInfoScene(event);
            }
        } catch (Exception e) {
            System.out.println(e);
        }


    }

    public void switchToUserInfoScene(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("../Fxmls/UserInformationPage.fxml"));
        Stage stage = (Stage) (((Node) event.getSource()).getScene().getWindow());
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public CreditCard getCreditcard() {
        return creditcard;
    }

    public void setCreditcard(CreditCard creditcard) {
        this.creditcard = creditcard;


    }

    public String getCardNumber() {
        return userinputcardnumber;
    }

    private String identifyCreditCardType(String cardNumber) {
        if (cardNumber.startsWith("4")) {
            return "Visa";
        } else if (cardNumber.startsWith("5")) {
            return "Mastercard";
        } else {
            return "Unknown";
        }

    }
}
