package ua.nure.st.kpp.example.demo;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.LoggerContext;
import com.mongodb.WriteConcern;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.nure.st.kpp.example.demo.Music_instrument.Guitar;
import ua.nure.st.kpp.example.demo.Music_instrument.Violin;
import ua.nure.st.kpp.example.demo.MyDAO.MySQLDAO;

import java.util.Scanner;

public class TestMainReplica {
    public static void main(String[] args) {
        LoggerContext loggerContext = (LoggerContext) LoggerFactory.getILoggerFactory();
        Logger rootLogger = loggerContext.getLogger("org.mongodb.driver");
        ((ch.qos.logback.classic.Logger) rootLogger).setLevel(Level.ERROR);

        Scanner in = new Scanner(System.in);
        System.out.println("Выберите этап теста:");
        System.out.println("1. Режим WriteConcern.ACKNOWLEDGED default");
        System.out.println("2. Режим WriteConcern.W1");
        System.out.println("3. Режим WriteConcern.UNACKNOWLEDGED");
        System.out.println("4. Режим WriteConcern.JOURNALED");
        System.out.println("5. Режим WriteConcern.MAJORITY");
        System.out.print("Введите номер теста:");
        int num = in.nextInt();
        in.close();

        MySQLDAO dao = MySQLDAO.getInstance();
        if (num == 2) {
            dao = MySQLDAO.getInstance(WriteConcern.W1);
        } else if (num == 3) {
            dao = MySQLDAO.getInstance(WriteConcern.UNACKNOWLEDGED);
        } else if (num == 4) {
            dao = MySQLDAO.getInstance(WriteConcern.JOURNALED);
        } else if (num == 5) {
            dao = MySQLDAO.getInstance(WriteConcern.MAJORITY);
        }

        System.out.println("Начало записи");
        long t1 = System.nanoTime();
        dao.migrate_data();
        long t2 = System.nanoTime();
        System.out.println("Запись " + (t2 - t1) / 1000000 + " миллисек");
        t1 = System.nanoTime();
        dao.find_all();
        t2 = System.nanoTime();
        System.out.println("Чтение " + (t2 - t1) / 1000000 + " миллисек");
    }
}
