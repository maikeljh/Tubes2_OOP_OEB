package Plugin;

import DataStore.DataStore;
import Plugin.Decorator.SettingsDecorator;
import jakarta.xml.bind.annotation.XmlRootElement;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import System.Inventory;
import System.Item;
import System.Customer;
import System.FixedBill;
import System.PurchasedItem;
import System.RegisteredCustomer;
import System.Member;
import System.VIP;
import java.io.File;
import java.io.Serializable;

@XmlRootElement
public class PluginCurrency extends SettingsDecorator {
    private Inventory<Currency> currencies;
    private double currentKurs;

    public PluginCurrency() {
        currencies = new Inventory<Currency>();
        this.currentKurs = 1;
    }

    @Override
    public void execute(){
        // Try to read data currencies
        DataStore<Currency> currencyDataStore = new DataStore<Currency>();
        try {
            currencies = currencyDataStore.loadData("currency", this.page.getSettings(), new Class<?>[]{Inventory.class, Currency.class});
        } catch (Exception e){
            e.printStackTrace();
        }

        // Create new settings option
        Button optionButton = new Button("Currency");
        ImageView iconCurrency = new ImageView(new Image("/images/icon/currency.png"));

        // Button style
        optionButton.setPadding(new Insets(10, 0, 10, 65));
        optionButton.setFont(Font.font("Montserrat", FontWeight.SEMI_BOLD, 20));
        optionButton.setStyle("-fx-border-color: transparent; -fx-background-color: #FFFFFF");
        optionButton.setOnMouseEntered(event -> optionButton.setStyle("-fx-background-color: #C8DFE8"));
        optionButton.setOnMouseExited(event -> optionButton.setStyle("-fx-border-color: transparent; -fx-background-color: #FFFFFF"));
        optionButton.setGraphic(iconCurrency);
        optionButton.setPrefWidth(500);
        optionButton.setGraphicTextGap(20);
        optionButton.setContentDisplay(ContentDisplay.LEFT);
        optionButton.setAlignment(Pos.CENTER_LEFT);

        // When option button clicked
        optionButton.setOnAction(event -> {
            // Remove old details
            VBox details = this.page.getDetails();
            this.page.getChildren().remove(details);

            // Styling new details
            details.setPadding(new Insets(30, 50, 10, 50));
            details.setSpacing(35);
            details.getChildren().clear();

            // Create main layout for details
            VBox currencyBox = new VBox();

            // Add title setting
            Label title = new Label("Currency");
            title.setFont(Font.font("Montserrat", FontWeight.SEMI_BOLD, 30));

            ComboBox<String> formatOptions = new ComboBox<>();
            for(Currency currency : currencies.getList()){
                formatOptions.getItems().add(currency.getCurrency());
            }
            formatOptions.setValue(formatOptions.getItems().get(0));

            formatOptions.valueProperty().addListener((observable, oldValue, newValue) -> {
                double newKurs = 1;
                for(Currency currency : currencies.getList()){
                    if(currency.getCurrency() == newValue){
                        newKurs = currency.getKurs();
                    }
                }

                Inventory<Item> items = this.page.getItems();
                Inventory<Customer> customers = this.page.getCustomers();

                for(Item item : items.getList()){
                    item.setSellPrice(item.getSellPrice() / this.currentKurs * newKurs);
                    item.setBuyPrice(item.getBuyPrice() / this.currentKurs * newKurs);
                }

                for(Customer customer : customers.getList()){
                    for(FixedBill bill : customer.getTransaction().getList()){
                        for(PurchasedItem item : bill.getItems().getList()){
                            item.setSellPrice(item.getSellPrice() / this.currentKurs * newKurs);
                            item.setBuyPrice(item.getBuyPrice() / this.currentKurs * newKurs);
                        }
                    }
                }

                // Save new data
                this.page.getItemDS().saveData("item", this.page.getSettings(), new Class<?>[]{Inventory.class, Item.class}, items);
                this.page.getCustomerDS().saveData("customer", this.page.getSettings(), new Class<?>[]{Inventory.class, Customer.class, RegisteredCustomer.class, Member.class, VIP.class, FixedBill.class, PurchasedItem.class}, customers);
                this.currentKurs = newKurs;
            });

            // Merge contents
            currencyBox.getChildren().addAll(title, formatOptions);
            currencyBox.setSpacing(20);

            details.getChildren().addAll(currencyBox);
            this.page.getChildren().add(details);
        });

        this.page.getOptions().getChildren().add(optionButton);

        this.page.getFormatOptions().setOnAction(e -> {
            File directory = new File(this.page.getSettings().getSaveDirectory());
            File[] files = directory.listFiles();
            for (File file : files) {
                if (file.isFile() && file.getName().startsWith("currency")) {
                    file.delete();
                    break;
                }
            }
            currencyDataStore.saveData("currency", this.page.getSettings(), new Class<?>[]{Inventory.class, Currency.class}, currencies);
        });

        this.page.getPickFolder().addEventHandler(ActionEvent.ACTION, event -> {
            currencyDataStore.saveData("currency", this.page.getSettings(), new Class<?>[]{Inventory.class, Currency.class}, currencies);
        });
    }

    public static void main(String args[]){}
}

@XmlRootElement
class Currency implements Serializable {
    private String currency;
    private double kurs;

    public Currency(){}

    public Currency(String currency, double kurs){
        this.currency = currency;
        this.kurs = kurs;
    }

    public String getCurrency() {
        return currency;
    }

    public double getKurs() {
        return kurs;
    }

    public void setCurrency(String currency){
        this.currency = currency;
    }

    public void setKurs(double kurs){
        this.kurs = kurs;
    }
}