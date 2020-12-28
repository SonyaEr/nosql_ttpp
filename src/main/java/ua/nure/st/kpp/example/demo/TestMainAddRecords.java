package ua.nure.st.kpp.example.demo;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.LoggerContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.nure.st.kpp.example.demo.MongoDB_DAO.MongoDBDAOFactory;
import ua.nure.st.kpp.example.demo.MongoDB_DAO.MongoDBIDAO;
import ua.nure.st.kpp.example.demo.MongoDB_DAO.MongoDBTypeDAO;
import ua.nure.st.kpp.example.demo.Music_instrument.Guitar;
import ua.nure.st.kpp.example.demo.MySQL_DAO.MySQLDAOFactory;
import ua.nure.st.kpp.example.demo.MySQL_DAO.MySQLIDAO;
import ua.nure.st.kpp.example.demo.MySQL_DAO.MySQLTypeDAO;

import java.util.Random;
import java.util.Scanner;

import static java.lang.Math.round;

public class TestMainAddRecords {
    public static int number_all_records;
    public static long number_all_time;

    public static void main(String[] args) {
        LoggerContext loggerContext = (LoggerContext) LoggerFactory.getILoggerFactory();
        Logger rootLogger = loggerContext.getLogger("org.mongodb.driver");
        ((ch.qos.logback.classic.Logger) rootLogger).setLevel(Level.ERROR);

        Scanner in = new Scanner(System.in);
        System.out.println("Выберите базу:");
        System.out.println("1. mySQL");
        System.out.println("2. mongodb");
        System.out.print("Введите номер:");
        int num = in.nextInt();
        System.out.println("Удалить все записи перед добавлением?:");
        System.out.println("1. ДА");
        System.out.println("2. НЕТ");
        System.out.print("Введите номер:");
        int del = in.nextInt();
        System.out.println("Введите количество записей:");
        int size = in.nextInt();
        in.close();

        MySQLIDAO daomysql = MySQLDAOFactory.getDAOInstance(MySQLTypeDAO.MySQL);
        MongoDBIDAO daomongodb = MongoDBDAOFactory.getDAOInstance(MongoDBTypeDAO.MongoDB);

        number_all_records = 0;
        number_all_time = 0;
        if (num == 1) {
            if (del == 1) {
                daomysql.Delete_All_Music_Instrument();
            }
            addDate(num, size);
        } else if (num == 2) {
            if (del == 1) {
                daomongodb.Delete_All_Music_Instrument();
            }
            addDate(num, size);
        }
    }

    public static void addDate(int num_test, int size) {
        MySQLIDAO daomysql = MySQLDAOFactory.getDAOInstance(MySQLTypeDAO.MySQL);
        MongoDBIDAO daomongodb = MongoDBDAOFactory.getDAOInstance(MongoDBTypeDAO.MongoDB);

        String[] color = new String[]{"Бежевый", "Коричневый", "Красный", "Черный"};
        String[] model_name = new String[]{"Классическая", "Аккустическая", "Электроакустическая", "Электрогитара"};
        String[] type_name = new String[]{"Дредноут", "Джамбо", "Фолк", "Ритм", "Соло", "Бас"};
        String[] where_is_used_name = new String[]{"Оркестр симфонический", "Оркестр народный", "Рок-группа", "Джазбэнд", "Ансамбль"};
        String[] material_name = new String[]{"Фанера", "Палисандр", "Клен", "Амарант"};
        Random random = new Random();
        long timestart = System.nanoTime();
        for (int i = 0; i < size; i++) {
            char[] word = new char[random.nextInt(8) + 3]; // words of length 3 through 10. (1 and 2 letter words are boring.)
            Guitar b = new Guitar(0,"Гитара",
                    color[random.nextInt(4)],
                    2 + round(random.nextDouble() * 30.0) / 10.0,
                    100 + random.nextInt(71),
                    model_name[random.nextInt(4)],
                    type_name[random.nextInt(6)],
                    where_is_used_name[random.nextInt(5)],
                    "Медиатор",
                    "Описание модели",
                    "Описание типа",
                    "Описание использования",
                    new String[]{material_name[random.nextInt(4)]},
                    new String[]{"Описание материала"},
                    new String[]{"Пластмасса", "Толстая"},
                    6,
                    "Нейлон",
                    120,
                    "4-5");
            //Добавление музыкального инструмента
            if (num_test == 1) {
                daomysql.Insert_Music_Instrument(b);
            } else {
                daomongodb.Insert_Music_Instrument(b);
            }
        }
        long timeend = System.nanoTime();
        number_all_records = number_all_records + size;
        number_all_time = number_all_time + timeend - timestart;
        System.out.println("Всего записей " + number_all_records + " добавлено за " + number_all_time / 1000000 + " миллисек");

        String id = "";
        timestart = System.nanoTime();
        for (int j = 0; j < 9; j++)
            if (num_test == 1) {
                id = daomysql.Find_Music_Instrument_By_NameColor(generateRandomWords(10), generateRandomWords(10));
            } else {
                id = daomongodb.Find_Music_Instrument_By_NameColor(generateRandomWords(10), generateRandomWords(10));
            }
        timeend = System.nanoTime();

        System.out.println("Поиск записи выполнен за " + (timeend - timestart) / 1000000 / 10 + " миллисек");

    }

    public static String generateRandomWords(int numberOfWords) {
        Random random = new Random();
        char[] word = new char[random.nextInt(8) + 3]; // words of length 3 through 10. (1 and 2 letter words are boring.)
        for (int j = 0; j < word.length; j++)
            word[j] = (char) ('A' + random.nextInt(52));
        return new String(word);
    }
}
