package ua.nure.st.kpp.example.demo.Music_instrument;

public class Violin extends Music_Instrument {
    private int numOfStrings;
    private String typeOfStrings;
    private int stringsLength;
    private String soundRange;

    public Violin(int id, String music_instrument_name, String color, double weight, int length,
                  String model_name, String type_name, String where_is_used_name, String additional_tool_name,
                  String model_description, String type_description, String where_is_used_description,
                  String[] material_name, String[] material_description, String[] additional_tool_material,
                  int numOfStrings, String typeOfStrings, int stringsLength, String soundRange) {
        super(id, music_instrument_name, color, weight, length, model_name, type_name, where_is_used_name, additional_tool_name, model_description, type_description, where_is_used_description, material_name, material_description, additional_tool_material);
        this.numOfStrings = numOfStrings;
        this.typeOfStrings = typeOfStrings;
        this.stringsLength = stringsLength;
        this.soundRange = soundRange;
    }


    public int getNumOfStrings() {
        return numOfStrings;
    }

    public void setNumOfStrings(int numOfStrings) {
        if (numOfStrings >= 0) {
            this.numOfStrings = numOfStrings;
        } else {
            throw new IllegalArgumentException("Bad input");
        }
    }

    public int getStringsLength() {
        return stringsLength;
    }

    public void setStringsLength(int stringsLength) {
        if (stringsLength >= 0) {
            this.stringsLength = stringsLength;
        } else {
            throw new IllegalArgumentException("Bad input");
        }
    }

    public String getTypeOfStrings() {
        return typeOfStrings;
    }

    public void setTypeOfStringsypeOfStrings(String typeOfStrings) {
        this.typeOfStrings = typeOfStrings;
    }

    public String getSoundRange() {
        return soundRange;
    }

    public void setSoundRangesoundRange(String soundRange) {
        this.soundRange = soundRange;
    }


    @Override
    public String getDescription() {
        String message = "";
        String mat_name = "";
        String mat_desc = "";
        message += "id: " + getId();
        message += ", name: " + getMusic_instrument_name();
        message += ", color:" + getColor();
        message += ", weight:" + getWeight();
        message += ", length: " + getLength();
        message += ", type_name: " + getType_name();
        message += ", type_description: " + getType_description();
        message += ", model_name: " + getModel_name();
        message += ", model_description: " + getModel_description();
        message += ", where_is_used_name: " + getWhere_is_used_name();
        message += ", where_is_used_description: " + getWhere_is_used_description();
        String[] material_name_item = (String[]) getMaterial_name();
        String[] material_description_item = (String[]) getMaterial_description();
        if (material_name_item == null) {
            message += ",material_name:  null";
            message += ",material_description:  null";
        } else {
            for (int j = 0; j < material_name_item.length; j++) {
                mat_name += "{" + material_name_item[j] + "}";
                mat_desc += "{" + material_description_item[j] + "}";
            }
            message += ",material_name: [" + mat_name + "]";
            message += ",material_description: [" + mat_desc + "]";
        }
        message += ", additional_tool_name : " + getAdditional_tool_name();
        String[] material_additioanl_tool_item = (String[]) getAdditional_tool_material();
        if (material_name_item == null) {
            message += ", dditional_tool_material: null";
        } else {
            message += ", dditional_tool_material: [{" + material_additioanl_tool_item[0] + "}{" + material_additioanl_tool_item[1] + "}]";
        }
        message += "Количество струн: " + getNumOfStrings();
        message += "Тип струн: " + getTypeOfStrings();
        message += "Длина струн(мм): " + getStringsLength();
        message += "Диапазон звучания: " + getSoundRange();
        return message;
    }
}

