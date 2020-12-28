package ua.nure.st.kpp.example.demo.Music_instrument;

import ua.nure.st.kpp.example.demo.Memento.Memento;
import ua.nure.st.kpp.example.demo.MyDAO.MusicInstrument;

public class Music_Instrument implements Comparable<Music_Instrument> {
    private int id;
    private String music_instrument_name;
    private String color;
    private double weight;
    private int length;
    private String model_name;
    private String type_name;
    private String where_is_used_name;
    private String additional_tool_name;
    private String model_description;
    private String type_description;
    private String where_is_used_description;
    private String[] material_name;
    private String[] material_description;
    private String[] additional_tool_material;


    public static class Builder {
        private int id;
        private String music_instrument_name;
        private String color = "";
        private double weight = 0.0;
        private int length = 0;
        private String model_name;
        private String type_name;
        private String where_is_used_name;
        private String additional_tool_name;
        private String model_description = "";
        private String type_description = "";
        private String where_is_used_description = "";
        private String[] material_name;
        private String[] material_description;
        private String[] additional_tool_material;

        public Builder() {

        }

        public Builder buildInt(int id) {
            this.id = id;
            return this;
        }

        public Builder buildMusicInstrumentName(String music_instrument_name) {
            this.music_instrument_name = music_instrument_name;
            return this;
        }

        public Builder buildColor(String color) {
            this.color = color;
            return this;
        }

        public Builder buildWeight(double weight) {
            this.weight = weight;
            return this;
        }

        public Builder buildLength(int length) {
            this.length = length;
            return this;
        }

        public Builder buildModelName(String model_name) {
            this.model_name = model_name;
            return this;
        }

        public Builder buildTypeName(String type_name) {
            this.type_name = type_name;
            return this;
        }

        public Builder buildWhereIsUsedName(String where_is_used_name) {
            this.where_is_used_name = where_is_used_name;
            return this;
        }

        public Builder buildAdditionalToolName(String additional_tool_name) {
            this.additional_tool_name = additional_tool_name;
            return this;
        }

        public Builder buildModelDescription(String model_description) {
            this.model_description = model_description;
            return this;
        }

        public Builder buildTypeDescription(String type_description) {
            this.type_description = type_description;
            return this;
        }

        public Builder buildWhereIsUsedDescription(String where_is_used_description) {
            this.where_is_used_description = where_is_used_description;
            return this;
        }

        public Builder buildMaterialName(String[] material_name) {
            this.material_name = material_name;
            return this;
        }

        public Builder buildMaterialDescription(String[] material_description) {
            this.material_description = material_description;
            return this;
        }

        public Builder buildAdditionalToolMaterial(String[] additional_tool_material) {
            this.additional_tool_material = additional_tool_material;
            return this;
        }

        public Music_Instrument build() {
            return new Music_Instrument(this);
        }
    }

    public Music_Instrument() {
        // all null
    }

    public Music_Instrument(Music_Instrument music_instrument) {
        this.id = music_instrument.id;
        this.music_instrument_name = music_instrument.music_instrument_name;
        this.color = music_instrument.color;
        this.weight = music_instrument.weight;
        this.length = music_instrument.length;
        this.model_name = music_instrument.model_name;
        this.type_name = music_instrument.type_name;
        this.where_is_used_name = music_instrument.where_is_used_name;
        this.additional_tool_name = music_instrument.additional_tool_name;
        this.model_description = music_instrument.model_description;
        this.type_description = music_instrument.type_description;
        this.where_is_used_description = music_instrument.where_is_used_description;
        this.material_name = music_instrument.material_name;
        this.material_description = music_instrument.material_description;
        this.additional_tool_material = music_instrument.additional_tool_material;
    }

    public Music_Instrument(Builder builder) {
        this.id = builder.id;
        this.music_instrument_name = builder.music_instrument_name;
        this.color = builder.color;
        this.weight = builder.weight;
        this.length = builder.length;
        this.model_name = builder.model_name;
        this.type_name = builder.type_name;
        this.where_is_used_name = builder.where_is_used_name;
        this.additional_tool_name = builder.additional_tool_name;
        this.model_description = builder.model_description;
        this.type_description = builder.type_description;
        this.where_is_used_description = builder.where_is_used_description;
        this.material_name = builder.material_name;
        this.material_description = builder.material_description;
        this.additional_tool_material = builder.additional_tool_material;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMusic_instrument_name() {
        return music_instrument_name;
    }

    public void setMusic_instrument_name(String music_instrument_name) {
        this.music_instrument_name = music_instrument_name;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        if (weight >= 0) {
            this.weight = weight;
        } else {
            throw new IllegalArgumentException("Bad input");
        }
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        if (length >= 0) {
            this.length = length;
        } else {
            throw new IllegalArgumentException("Bad input");
        }
    }

    public String getModel_name() {
        return model_name;
    }

    public void setModel_name(String model_name) {
        this.model_name = model_name;
    }

    public String getType_name() {
        return type_name;
    }

    public void setType_name(String type_name) {
        this.type_name = type_name;
    }

    public String getWhere_is_used_name() {
        return where_is_used_name;
    }

    public void setWhere_is_used_name(String where_is_used_name) {
        this.where_is_used_name = where_is_used_name;
    }

    public String getAdditional_tool_name() {
        return additional_tool_name;
    }

    public void setAdditional_tool_name(String additional_tool_name) {
        this.additional_tool_name = additional_tool_name;
    }

    public String getType_description() {
        return type_description;
    }

    public void setType_description(String type_description) {
        this.type_description = type_description;
    }

    public String getModel_description() {
        return model_description;
    }

    public void setModel_description(String model_description) {
        this.model_description = model_description;
    }

    public String getWhere_is_used_description() {
        return where_is_used_description;
    }

    public void setWhere_is_used_description(String where_is_used_description) {
        this.where_is_used_description = where_is_used_description;
    }

    public String[] getMaterial_name() {
        return material_name;
    }

    public void setMaterial_name(String[] material_name) {
        this.material_name = material_name;
    }

    public String[] getMaterial_description() {
        return material_description;
    }

    public void setMaterial_description(String[] material_description) {
        this.material_description = material_description;
    }

    public String[] getAdditional_tool_material() {
        return additional_tool_material;
    }

    public void setAdditional_tool_material(String[] additional_tool_material) {
        this.additional_tool_material = additional_tool_material;
    }

    public String getDescription() {
        String message = "";
        String mat_name = "";
        String mat_desc = "";
        message += "id: " + id;
        message += ", name: " + music_instrument_name;
        message += ", color:" + color;
        message += ", weight:" + weight;
        message += ", length: " + length;
        message += ", type_name: " + type_name;
        message += ", type_description: " + type_description;
        message += ", model_name: " + model_name;
        message += ", model_description: " + model_description;
        message += ", where_is_used_name: " + where_is_used_name;
        message += ", where_is_used_description: " + where_is_used_description;
        String[] material_name_item = (String[]) material_name;
        String[] material_description_item = (String[]) material_description;
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
        message += ", additional_tool_name : " + additional_tool_name;
        String[] material_additioanl_tool_item = (String[]) additional_tool_material;
        if (material_name_item == null) {
            message += ", dditional_tool_material: null";
        } else {
            message += ", dditional_tool_material: [{" + material_additioanl_tool_item[0] + "}{" + material_additioanl_tool_item[1] + "}]";
        }
        return message;
    }

    @Override
    public String toString() {
        String message = "";
        message += "Информация о музыкальном инструменте " + music_instrument_name + '\n';
        message += "Цвет:" + color + '\n';
        message += "Вес: " + weight + '\n';
        message += "Длина: " + length + '\n';
        message += "Модель: " + model_name + " (" + model_description + ")" + '\n';
        message += "Тип: " + type_name + " (" + type_description + ")" + '\n';
        message += "Где используется: " + where_is_used_name + " (" + where_is_used_description + ")" + '\n';
        String[] material_name_item = (String[]) material_name;
        String[] material_description_item = (String[]) material_description;
        if (material_name_item == null) {
            message += "Материал[-]:  -  ( - )" + '\n';
        } else {
            for (int j = 0; j < material_name_item.length; j++) {
                message += "Материал[" + (j + 1) + "]: " + material_name_item[j] + " (" + material_description_item[j] + ")" + '\n';
            }
        }
        message += "Дополнительный инструмент: " + additional_tool_name + '\n';
        String[] material_additioanl_tool_item = (String[]) additional_tool_material;
        if (material_name_item == null) {
            message += "Материал дополнительного инструмента: - ( - )" + '\n';
        } else {
            message += "Материал дополнительного инструмента: " + material_additioanl_tool_item[0] + " (" + material_additioanl_tool_item[1] + ")" + '\n';
        }
        return message;
    }

    public Music_Instrument(int id, String music_instrument_name, String color, double weight, int length, String
            model_name, String type_name,
                            String where_is_used_name, String additional_tool_name,
                            String model_description, String type_description, String where_is_used_description,
                            String[] material_name, String[] material_description,
                            String[] additional_tool_material) {
        this.id = id;
        this.music_instrument_name = music_instrument_name;
        this.color = color;
        this.weight = weight;
        this.length = length;
        this.model_name = model_name;
        this.type_name = type_name;
        this.where_is_used_name = where_is_used_name;
        this.additional_tool_name = additional_tool_name;
        this.material_name = material_name;
        this.material_description = material_description;
        this.model_description = model_description;
        this.type_description = type_description;
        this.where_is_used_description = where_is_used_description;
        this.additional_tool_material = additional_tool_material;
        setWeight(weight);
        setLength(length);
    }

    @Override
    public int compareTo(Music_Instrument other) {
        if (this.length == other.length) {
            return 0;
        } else if (this.length < other.length) {
            return -1;
        } else {
            return 1;
        }
    }

    public Memento saveState() {
        return new Memento(this);
    }

    public void restoreState(Memento memento) {
        copy(memento.getState());
    }

    public void copy(Music_Instrument temp) {
        this.id = temp.id;
        this.music_instrument_name = temp.music_instrument_name;
        this.color = temp.color;
        this.weight = temp.weight;
        this.length = temp.length;
        this.model_name = temp.model_name;
        this.type_name = temp.type_name;
        this.where_is_used_name = temp.where_is_used_name;
        this.additional_tool_name = temp.additional_tool_name;
        this.model_description = temp.model_description;
        this.type_description = temp.type_description;
        this.where_is_used_description = temp.where_is_used_description;
        this.material_name = temp.material_name;
        this.material_description = temp.material_description;
        this.additional_tool_material = temp.additional_tool_material;
    }

}
