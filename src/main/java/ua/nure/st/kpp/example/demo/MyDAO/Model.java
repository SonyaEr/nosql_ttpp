package ua.nure.st.kpp.example.demo.MyDAO;

public class Model {
    public String model_name;
    public String model_description;

    public Model(String model_name, String model_description) {
        this.model_name = model_name;
        this.model_description = model_description;
    }

    public String getModel_name() {
        return model_name;
    }

    public void setModel_name(String model_name) {
        this.model_name = model_name;
    }

    public String getModel_description() {
        return model_description;
    }

    public void setModel_description(String model_description) {
        this.model_description = model_description;
    }

}
