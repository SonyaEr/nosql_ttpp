package ua.nure.st.kpp.example.demo;

import ua.nure.st.kpp.example.demo.Memento.Caretaker;
import ua.nure.st.kpp.example.demo.Music_instrument.Music_Instrument;
import ua.nure.st.kpp.example.demo.MySQL_DAO.MySQLDAO;

public class TestMainMemento {
    public static void main(String[] args) {
        MySQLDAO dao = MySQLDAO.getInstance();
        Caretaker me = new Caretaker();
        Music_Instrument music_instrument;
        dao.Update_Music_Instrument_Type_By_Name("классическая", "треугольная", 25354);
        music_instrument = dao.Get_Music_Instrument_By_Id(25354);
        me.addMemento(music_instrument.saveState());
        System.out.println("Состояние №0 (начальное): ");
        System.out.println(music_instrument);

        dao.Update_Music_Instrument_Type_By_Name("3/4", "Carlo Giordano", 25354);
        music_instrument = dao.Get_Music_Instrument_By_Id(25354);
        me.addMemento(music_instrument.saveState());
        System.out.println("Состояние №1 (измененное): ");
        System.out.println(music_instrument);

        dao.Update_Music_Instrument_Type_By_Name("Jumbo", "Гитары с большим корпусом и округлыми формам", 25354);
        music_instrument = dao.Get_Music_Instrument_By_Id(25354);
        me.addMemento(music_instrument.saveState());
        System.out.println("Состояние №2 (измененное): ");
        System.out.println(music_instrument);

        music_instrument.restoreState(me.getMemento(1));
        dao.Restore_Music_Instrument(music_instrument);
        music_instrument = dao.Get_Music_Instrument_By_Id(25354);
        System.out.println("Возврат состояния №1: ");
        System.out.println(music_instrument);

        music_instrument.restoreState(me.getMemento(0));
        dao.Restore_Music_Instrument(music_instrument);
        music_instrument = dao.Get_Music_Instrument_By_Id(25354);
        System.out.println("Возврат состояния №0: ");
        System.out.println(music_instrument);

    }
}

