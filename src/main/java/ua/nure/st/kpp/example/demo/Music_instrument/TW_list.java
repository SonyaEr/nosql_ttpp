package ua.nure.st.kpp.example.demo.Music_instrument;
import ua.nure.st.kpp.example.demo.Music_instrument.Music_Instrument;

public interface TW_list extends Iterable
{
    void add(Music_Instrument data);
    void clear();
    boolean remove(Music_Instrument element);
    Music_Instrument[] toArray();
    int size();
    boolean contains(Music_Instrument element);
    void print();
    void rprint();
}