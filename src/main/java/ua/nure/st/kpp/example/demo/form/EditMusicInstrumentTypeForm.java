package ua.nure.st.kpp.example.demo.form;

public class EditMusicInstrumentTypeForm {
    private String type_name;
    private String type_description;
    private int music_instrument_id;

    public EditMusicInstrumentTypeForm() { }

    public EditMusicInstrumentTypeForm(String type_name, String type_description, int music_instrument_id) {

        this.type_name = type_name;
        this.type_description = type_description;
        this.music_instrument_id = music_instrument_id;
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
    public int getMusic_instrument_id() { return music_instrument_id; }
    public void setMusic_instrument_id(int music_instrument_id) { this.music_instrument_id = music_instrument_id; }
}
