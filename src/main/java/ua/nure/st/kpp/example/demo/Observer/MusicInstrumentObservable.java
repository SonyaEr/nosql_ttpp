package ua.nure.st.kpp.example.demo.Observer;
import ua.nure.st.kpp.example.demo.Music_instrument.Music_Instrument;
import ua.nure.st.kpp.example.demo.StringMusicInstrumentPair.MusicInstrumentPair;
import java.util.*;

public class MusicInstrumentObservable implements Observable{
    private ArrayList<Observer> observers = new ArrayList<Observer>();

    @Override
    public void registerObserver(Observer observer) {
        observers.add(observer);
    }

    @Override
    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObservers(String eventType, ArrayList<Object[]> music_instrument) {
        for (Observer user : observers) {
            user.update(eventType, music_instrument);
        }
    }

    public ArrayList<MusicInstrumentPair> Sumary() {
        ArrayList<MusicInstrumentPair> sumary = new ArrayList<MusicInstrumentPair>();
        for (Observer ob : observers) {
            ArrayList<MusicInstrumentPair> log = ob.getLog();

            for (int i = log.size()-1; i >= 0; i--)
            {
                sumary.add(log.get(i));
            }
        }
        return sumary;
    }
}
