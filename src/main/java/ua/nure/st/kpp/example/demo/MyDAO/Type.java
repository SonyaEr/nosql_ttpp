package ua.nure.st.kpp.example.demo.MyDAO;

public class Type {
    public String type_name;
    public String type_description;

    public Type(String model_name, String model_description) {
        this.type_name = model_name;
        this.type_description = model_description;
    }

    public String getType_name() {
        return type_name;
    }

    public void setType_name(String type_name) {
        this.type_name = type_name;
    }

    public String getType_description() {
        return type_description;
    }

    public void setType_description(String type_description) {
        this.type_description = type_description;
    }

}
