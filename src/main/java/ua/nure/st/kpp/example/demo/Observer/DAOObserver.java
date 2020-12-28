package ua.nure.st.kpp.example.demo.Observer;
import ua.nure.st.kpp.example.demo.Music_instrument.Music_Instrument;
import ua.nure.st.kpp.example.demo.StringMusicInstrumentPair.MusicInstrumentPair;

import java.util.ArrayList;

    public class DAOObserver implements Observer {
        private ArrayList<MusicInstrumentPair> log = new ArrayList<MusicInstrumentPair>();

        @Override
        public ArrayList<MusicInstrumentPair> getLog() {
            return log;
        }

        @Override
        public void update(String eventType, ArrayList<Object[]> music_instrument ) {
            for (Object[] p: music_instrument) {
                System.out.println(eventType + " | " + p);
             log.add(new MusicInstrumentPair(eventType, p));
            }
        }
    }
