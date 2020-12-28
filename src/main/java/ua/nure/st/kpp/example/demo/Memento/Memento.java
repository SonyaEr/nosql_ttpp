package ua.nure.st.kpp.example.demo.Memento;

import ua.nure.st.kpp.example.demo.Music_instrument.Music_Instrument;

public class Memento {
    private final Music_Instrument state;

    public Memento(Music_Instrument state) {
        this.state = new Music_Instrument(state);
    }
    public Music_Instrument getState() {
        return state;
    }

}
