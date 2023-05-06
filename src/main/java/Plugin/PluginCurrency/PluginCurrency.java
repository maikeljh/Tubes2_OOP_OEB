package Plugin.PluginCurrency;

import DataStore.DataStore;
import Plugin.Decorator.SettingsDecorator;
import Plugin.Plugin;
import Plugin.PluginManager;
import jakarta.xml.bind.annotation.XmlRootElement;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import Core.Inventory;
import Core.Item;
import Core.Customer;
import Core.Bill;
import Core.PurchasedItem;
import Core.RegisteredCustomer;
import Core.Member;
import Core.VIP;
import Core.SalesReport;
import Core.Settings;
import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@XmlRootElement
public class PluginCurrency extends SettingsDecorator implements Serializable{
    private Inventory<Currency> currencies;
    private double currentKurs;
    private String currency;

    public PluginCurrency() {
        currencies = new Inventory<Currency>();
        this.currentKurs = 1;
        this.currency = "IDR";
        this.pluginName = "Plugin Currency";
        this.mainPlugin = true;
    }

    @Override
    public void execute(){
        // Try to read data currencies
        DataStore<Currency> currencyDataStore = new DataStore<Currency>();
        try {
            Settings temp = new Settings();
            temp.setFormat("xml");
            currencies = currencyDataStore.loadData("currency", temp, new Class<?>[]{Inventory.class, Currency.class});
        } catch (Exception e){
            // Do Nothing
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
            for(Currency currency : currencies.getBox()){
                formatOptions.getItems().add(currency.getCurrency());
            }
            formatOptions.setValue(this.currency);

            formatOptions.valueProperty().addListener((observable, oldValue, newValue) -> {
                double newKurs = 1;

                for(Currency currency : currencies.getBox()){
                    if(Objects.equals(currency.getCurrency(), newValue)){
                        newKurs = currency.getKurs();
                        this.currency = currency.getCurrency();
                    }
                }

                Inventory<Item> items = this.page.getItems();
                Inventory<Customer> customers = this.page.getCustomers();
                SalesReport report = this.page.getReport();

                for(Item item : items.getBox()){
                    item.setSellPrice(item.getSellPrice() / this.currentKurs * newKurs);
                    item.setBuyPrice(item.getBuyPrice() / this.currentKurs * newKurs);
                }

                for(Customer customer : customers.getBox()){
                    for(Bill bill : customer.getTransaction().getBox()){
                        for(PurchasedItem item : bill.getItems().getBox()){
                            item.setSellPrice(item.getSellPrice() / this.currentKurs * newKurs);
                            item.setBuyPrice(item.getBuyPrice() / this.currentKurs * newKurs);
                        }
                    }
                }

                for(PurchasedItem item : report.getItems().getBox()){
                    item.setSellPrice(item.getSellPrice() / this.currentKurs * newKurs);
                    item.setBuyPrice(item.getBuyPrice() / this.currentKurs * newKurs);
                }

                this.currentKurs = newKurs;

                // Save new data
                this.page.getItemDS().saveData("item", this.page.getSettings(), new Class<?>[]{Inventory.class, Item.class}, items);
                this.page.getCustomerDS().saveData("customer", this.page.getSettings(), new Class<?>[]{Inventory.class, Customer.class, RegisteredCustomer.class, Member.class, VIP.class, Bill.class, PurchasedItem.class}, customers);
                Inventory<SalesReport> tempReport = new Inventory<SalesReport>();
                tempReport.addElement(report);
                this.page.getReportDS().saveData("report", this.page.getSettings(), new Class<?>[]{Inventory.class, SalesReport.class, PurchasedItem.class}, tempReport);

                // Save settings
                Settings temp = new Settings();
                Inventory<Settings> tempSettings = new Inventory<Settings>();
                tempSettings.addElement(this.page.getSettings());
                List<Class<?>> clazzes = this.getPage().getSettings().getPluginManager().getClazzes();
                Class<?>[] classesArray = clazzes.toArray(new Class<?>[clazzes.size()]);
                Class<?>[] others = {Inventory.class, Settings.class, PluginManager.class, Plugin.class};
                Class<?>[] concatenated = Arrays.copyOf(classesArray, classesArray.length + others.length);
                System.arraycopy(others, 0, concatenated, classesArray.length, others.length);

                this.page.getSettingsDS().saveData("settings", temp, concatenated, tempSettings);
            });

            // Merge contents
            currencyBox.getChildren().addAll(title, formatOptions);
            currencyBox.setSpacing(20);

            details.getChildren().addAll(currencyBox);
            this.page.getChildren().add(details);
        });

        this.page.getOptions().getChildren().add(optionButton);

/*        this.page.getFormatOptions().setOnAction(e -> {
            File directory = new File(this.page.getSettings().getSaveDirectory());
            File[] files = directory.listFiles();
            for (File file : files) {
                if (file.isFile() && file.getName().startsWith("currency")) {
                    file.delete();
                    break;
                }
            }
            currencyDataStore.saveData("currency", this.page.getSettings(), new Class<?>[]{Inventory.class, Currency.class}, currencies);
        });*/

//        this.page.getPickFolder().addEventHandler(ActionEvent.ACTION, event -> {
//            currencyDataStore.saveData("currency", this.page.getSettings(), new Class<?>[]{Inventory.class, Currency.class}, currencies);
//        });
    }

    public static void main(String args[]){}

    public String getCurrency() {
        return currency;
    }

    public double getCurrentKurs() {
        return currentKurs;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public void setCurrentKurs(double currentKurs) {
        this.currentKurs = currentKurs;
    }
}