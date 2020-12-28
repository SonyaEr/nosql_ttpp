package ua.nure.st.kpp.example.demo.form;

import java.util.Arrays;

public class SelectMusicInstrumentByNameForm {
    private String music_instrument_name;

    public SelectMusicInstrumentByNameForm(String music_instrument_name ) {
        this.music_instrument_name = music_instrument_name;
    }
    public SelectMusicInstrumentByNameForm() {
        this.music_instrument_name = "";
    }

    public String getMusic_instrument_name() {
        return music_instrument_name;
    }
    public void setMusic_instrument_name(String music_instrument_name) {
        this.music_instrument_name = music_instrument_name;
    }
}

