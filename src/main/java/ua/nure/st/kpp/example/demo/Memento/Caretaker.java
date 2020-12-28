package ua.nure.st.kpp.example.demo.Memento;

import java.util.ArrayList;

public class Caretaker {
    private ArrayList<Memento> mementos;

    public Caretaker(){
        mementos = new ArrayList<>();
    }
    public Memento getMemento(int i) {
        if (mementos.size() == 0) {
            return null;
        }
        else {
            return mementos.get(i);
        }
    }
    public void addMemento(Memento memento) {
        this.mementos.add(memento);
    }
}
