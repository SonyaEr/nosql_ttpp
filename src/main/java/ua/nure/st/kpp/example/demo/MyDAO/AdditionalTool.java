package ua.nure.st.kpp.example.demo.MyDAO;

import org.bson.types.ObjectId;

public class AdditionalTool {
    public String additional_tool_name;
    public Material material;

    public AdditionalTool(String additional_tool_name, Material material) {
        this.additional_tool_name = additional_tool_name;
        this.material = material;
    }

    public String getAdditional_tool_name() {
        return additional_tool_name;
    }

    public void setAdditional_tool_name(String additional_tool_name) {
        this.additional_tool_name = additional_tool_name;
    }

    public Material getMaterial_id() {
        return material;
    }

    public void setMaterial_id(Material material) {
        this.material = material;
    }
}
