package ua.nure.st.kpp.example.demo.MyDAO;

import org.bson.types.ObjectId;

public class MusicInstrument {
    public String music_instrument_name;
    public String color;
    public Double weight;
    public int length;
    public Model model;
    public Type type;
    public WhereIsUsed where_is_used;
    public AdditionalTool additional_tool;

    public MusicInstrument(String music_instrument_name, String color, Double weight, int length, Model model, Type type, WhereIsUsed where_is_used, AdditionalTool additional_tool) {
        this.music_instrument_name = music_instrument_name;
        this.color = color;
        this.weight = weight;
        this.length = length;
        this.model = model;
        this.type = type;
        this.where_is_used = where_is_used;
        this.additional_tool = additional_tool;
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

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public Model getModel() {
        return model;
    }

    public void setModel(Model model) {
        this.model = model;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public WhereIsUsed getWhere_is_used() {
        return where_is_used;
    }

    public void setWhere_is_used(WhereIsUsed where_is_used) {
        this.where_is_used = where_is_used;
    }

    public AdditionalTool getAdditional_tool() {
        return additional_tool;
    }

    public void setAdditional_tool(AdditionalTool additional_tool) {
        this.additional_tool = additional_tool;
    }
}
