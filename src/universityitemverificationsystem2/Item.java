package universityitemverificationsystem2;
public class Item {
    private String type_category;
    private String model_brand;
    private int id;

    public Item(String type_category, String model_brand, int id) {
        this.type_category=type_category;
        this.model_brand=model_brand;
        this.id=id;
    }

    public String gettype_category() {
        return type_category;
    }

    public String getmodel_brand() {
        return model_brand;
    }

    public int getid() {
        return id;
    }

    @Override
    public String toString() {
        return "Item[Type: " + type_category + ", Model: " + model_brand + ", ID: " + id + "]";
    }
}
