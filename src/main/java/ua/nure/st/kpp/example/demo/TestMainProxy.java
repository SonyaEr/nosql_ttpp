package ua.nure.st.kpp.example.demo;

import ua.nure.st.kpp.example.demo.Music_instrument.Guitar;
import ua.nure.st.kpp.example.demo.Music_instrument.Music_Instrument;
import ua.nure.st.kpp.example.demo.Proxy.User;

import java.util.ArrayList;

public class TestMainProxy {
    public static void main(String[] args) {
        System.out.println("Авторизация пользователя admin.");
        User userAdmin = new User("admin", "12345");
        userAdmin.register();
        userAdmin.login();
        System.out.println("Статус пользователя: " + userAdmin.getStatus()+"\n");

        Music_Instrument music_instrument;
        ArrayList<Music_Instrument> result;
        Guitar guitar = new Guitar(0,"Гитара гавайская", "Бежевый", 1.3, 60, "Аккустическая", "Укулеле", "Группа", "нет", "", "Гитрара с четырмя струнами","", new String[]{"Фанера"}, new String[]{"фанера обычная"}, new String[]{"нет","нет"}, 4, "Нейлон", 120, "4-5");

        System.out.println("Добавление новой записи пользователем admin:");
        music_instrument = userAdmin.Insert_Music_Instrument(guitar);
        System.out.println(music_instrument.getDescription()+"\n");

        System.out.println("Изменение типа инструмента в добавленной записи пользователем admin:");
        userAdmin.Change_Type("Классическая", "треугольная", music_instrument.getId());
        music_instrument = userAdmin.Get_Music_Instrument_By_Id(music_instrument.getId());
        System.out.println(music_instrument.getDescription()+"\n");

        System.out.println("Удаление добавленной записи пользователем admin:");
        userAdmin.Delete_Music_Instrument_By_Id(music_instrument.getId());
        music_instrument = userAdmin.Get_Music_Instrument_By_Id(music_instrument.getId());
        System.out.println(music_instrument.getDescription()+"\n");

        System.out.println("Добавление новой записи пользователем admin:");
        Music_Instrument music_instrument_admin = userAdmin.Insert_Music_Instrument(guitar);
        System.out.println(music_instrument_admin.getDescription()+"\n");

        System.out.println("Авторизация пользователя user.");
        User userUser = new User("user", "11111");
        userUser.register();
        userUser.login();
        System.out.println("Статус пользователя: " + userUser.getStatus());

        System.out.println("Попытка добавления новой записи пользователем user:");
        music_instrument = userUser.Insert_Music_Instrument(guitar);
        System.out.println(music_instrument.getDescription()+"\n");

        System.out.println("Попытка изменение типа инструмента в записи пользователем user:");
        userUser.Change_Type("Классическая", "треугольная", music_instrument_admin.getId());
        System.out.println("Чтение записи из базы данных пользователем user:");
        music_instrument = userUser.Get_Music_Instrument_By_Id(music_instrument_admin.getId());
        System.out.println(music_instrument.getDescription()+"\n");

        System.out.println("Попытка удаления записи пользователем user:");
        userUser.Delete_Music_Instrument_By_Id(music_instrument.getId());
        System.out.println("Чтение записи из базы данных пользователем user:");
        music_instrument = userUser.Get_Music_Instrument_By_Id(music_instrument_admin.getId());
        System.out.println(music_instrument.getDescription()+"\n");
    }
}
