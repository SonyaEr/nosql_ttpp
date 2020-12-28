package ua.nure.st.kpp.example.demo.Music_instrument;

public class Additional_tool {
    private String additional_tool_name; //
    private String material_name; //
    private String material_description; //

    public String getAdditional_tool_name() { return additional_tool_name; }
    public void setAdditional_tool_name(String additional_tool_name) { this.additional_tool_name = additional_tool_name; }

    public String getMaterial_name() { return material_name; }
    public void setMaterial_name(String material_name) {this.material_name = material_name;}

    public String getMaterial_description() { return material_description; }
    public void setMaterial_description(String material_description) {this.material_description = material_description;}

    public void getDescription() {
        String message = "";
        message += "Информация о дополнительном элементе: " + additional_tool_name + '\n';
        message += "Материал изготовления" + material_name + '\n';
        message += "Материал описание" + material_description + '\n';
        System.out.print(message);
    }

    @Override
    public String toString() {
        return "Additional_tool: " + additional_tool_name + ", material: " + material_name+", description: " + material_description;
    }

    public Additional_tool(String additional_tool_name, String material_name, String material_description) {
        this.additional_tool_name = additional_tool_name;
        this.material_name = material_name;
        this.material_description = material_description;

        setAdditional_tool_name(additional_tool_name);
        setMaterial_name(material_name);
        setMaterial_description(material_description);
    }

}
