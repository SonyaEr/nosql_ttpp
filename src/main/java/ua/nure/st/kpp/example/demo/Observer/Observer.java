package ua.nure.st.kpp.example.demo.Observer;
import ua.nure.st.kpp.example.demo.Music_instrument.Music_Instrument;
import ua.nure.st.kpp.example.demo.StringMusicInstrumentPair.MusicInstrumentPair;

import java.util.ArrayList;

public interface Observer {
    void update(String eventType, ArrayList<Object[]> music_instrument);
    ArrayList<MusicInstrumentPair> getLog();
}