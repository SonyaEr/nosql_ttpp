package ua.nure.st.kpp.example.demo.MyDAO;

public class Material {
    public String material_name;
    public String material_description;

    public Material(String material_name, String material_description) {
        this.material_name = material_name;
        this.material_description = material_description;
    }

    public String getMaterial_name() {
        return material_name;
    }

    public void setMaterial_name(String material_name) {
        this.material_name = material_name;
    }

    public String getMaterial_description() {
        return material_description;
    }

    public void setMaterial_description(String material_description) {
        this.material_description = material_description;
    }
}
