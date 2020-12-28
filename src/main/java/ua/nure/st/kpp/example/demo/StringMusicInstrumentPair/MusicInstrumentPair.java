package ua.nure.st.kpp.example.demo.StringMusicInstrumentPair;

import ua.nure.st.kpp.example.demo.Music_instrument.Music_Instrument;

public class MusicInstrumentPair {
    public String getEvent() {
        return event;
    }

    public String event;
    public Object[] music_instrument;

    public MusicInstrumentPair(String event, Object[] music_instrument) {
        this.event = event;
        this.music_instrument = music_instrument;
    }
}
