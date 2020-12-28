package ua.nure.st.kpp.example.demo;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.LoggerContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.nure.st.kpp.example.demo.MongoDB_DAO.MongoDBDAOFactory;
import ua.nure.st.kpp.example.demo.MongoDB_DAO.MongoDBIDAO;
import ua.nure.st.kpp.example.demo.MongoDB_DAO.MongoDBTypeDAO;
import ua.nure.st.kpp.example.demo.MySQL_DAO.MySQLDAOFactory;
import ua.nure.st.kpp.example.demo.MySQL_DAO.MySQLIDAO;
import ua.nure.st.kpp.example.demo.MySQL_DAO.MySQLTypeDAO;

import java.util.Random;
import java.util.Scanner;

public class TestMainBigDataTest {
    public static int number_all_records;
    public static long number_all_time;
    public static void main(String[] args) {
        LoggerContext loggerContext = (LoggerContext) LoggerFactory.getILoggerFactory();
        Logger rootLogger = loggerContext.getLogger("org.mongodb.driver");
        ((ch.qos.logback.classic.Logger) rootLogger).setLevel(Level.ERROR);

        Scanner in = new Scanner(System.in);
        System.out.println("Выберите тест:");
        System.out.println("1. mySQL test");
        System.out.println("2. mySQL test_index");
        System.out.println("3. mongodb test");
        System.out.println("4. mongodb test_index");
        System.out.print("Введите номер теста:");
        int num = in.nextInt();
        in.close();

        //100, 1000, 10000, 50000, 100000, 500000
        int[] add_records = { 100, 900, 9000, 40000, 50000, 400000 };
        MySQLIDAO daomysql = MySQLDAOFactory.getDAOInstance(MySQLTypeDAO.MySQL);
        MongoDBIDAO daomongodb = MongoDBDAOFactory.getDAOInstance(MongoDBTypeDAO.MongoDB);

        number_all_records = 0;
        number_all_time = 0;
        if (num == 1){
            daomysql.Delete_All_Test(1);
            for (int j : add_records)
                addDateTest(daomysql, 1, j);
        }
        else if(num == 2) {
            daomysql.Delete_All_Test(2);
            for (int j : add_records)
                addDateTest(daomysql, 2, j);
        }
        else if(num == 3) {
            daomongodb.Delete_All_Test(1);
            for (int j : add_records)
                addDateTest(daomongodb, 1, j);
        }
        else if(num == 4) {
            daomongodb.Delete_All_Test(2);
            for (int j : add_records)
                addDateTest(daomongodb, 2, j);
        }

    }
    public static void addDateTest(MySQLIDAO dao, int num_table, int size) {
        long timestart = System.nanoTime();
        for (int i = 0; i < size; i++) {
            dao.Add_Test("Гитара"+(number_all_records + i), "Описание "+(number_all_records + i), num_table);
        }
        long timeend =  System.nanoTime();
        number_all_records = number_all_records + size;
        number_all_time = number_all_time + timeend - timestart;
        System.out.println("Всего записей " + number_all_records + " добавлено за " + number_all_time/1000000 + " миллисек");

        int id = 0;
        timestart = System.nanoTime();
        for(int j = 0; j < 9; j++)
            id = dao.Select_Test(generateRandomWords(10),generateRandomWords(10), num_table);
        timeend =  System.nanoTime();

        System.out.println("Поиск записи выполнен за " + (timeend - timestart)/1000000 + " миллисек");

    }
    public static void addDateTest(MongoDBIDAO dao, int num_table, int size) {
        long timestart = System.nanoTime();
        for (int i = 0; i < size; i++) {
            dao.Add_Test(generateRandomWords(10), generateRandomWords(10), num_table);
        }
        long timeend =  System.nanoTime();
        number_all_records = number_all_records + size;
        number_all_time = number_all_time + timeend - timestart;
        System.out.println("Всего записей " + number_all_records + " добавлено за " + number_all_time/1000000 + " миллисек");

        String id = "";
        timestart = System.nanoTime();
        for(int j = 0; j < 9; j++)
            id = dao.Select_Test(generateRandomWords(10),generateRandomWords(10), num_table);
        timeend =  System.nanoTime();
        System.out.println("Поиск записи выполнен за " + (timeend - timestart)/1000000 + " миллисек");
    }
    public static String generateRandomWords(int numberOfWords){
        Random random = new Random();
        char[] word = new char[random.nextInt(8)+3]; // words of length 3 through 10. (1 and 2 letter words are boring.)
        for(int j = 0; j < word.length; j++)
            word[j] = (char)('A' + random.nextInt(52));
        return new String(word);
    }
}
