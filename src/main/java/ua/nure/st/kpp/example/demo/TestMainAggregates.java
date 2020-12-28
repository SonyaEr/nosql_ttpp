package ua.nure.st.kpp.example.demo;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.LoggerContext;
import com.mongodb.WriteConcern;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.nure.st.kpp.example.demo.MongoDB_DAO.MongoDBDAOFactory;
import ua.nure.st.kpp.example.demo.MongoDB_DAO.MongoDBIDAO;
import ua.nure.st.kpp.example.demo.MongoDB_DAO.MongoDBTypeDAO;
import ua.nure.st.kpp.example.demo.MyDAO.MySQLDAO;

import java.util.Scanner;

public class TestMainAggregates {
    public static void main(String[] args) {
        LoggerContext loggerContext = (LoggerContext) LoggerFactory.getILoggerFactory();
        Logger rootLogger = loggerContext.getLogger("org.mongodb.driver");
        ((ch.qos.logback.classic.Logger) rootLogger).setLevel(Level.ERROR);

        MongoDBIDAO dao = MongoDBDAOFactory.getDAOInstance(MongoDBTypeDAO.MongoDB);
        long t1 = 0;
        long t2 = 0;
        int result_int = 0;
        Double result_double = 0.0;

        System.out.println("1. Сумарна кількість інструментів моделі \"Акустична\"");
        t1 = System.nanoTime();
        result_int = dao.Sum_MusicInstrument_by_Model("Аккустическая");
        t2 = System.nanoTime();
        System.out.println("Function Sum_MusicInstrument_by_Model() | Result: " + result_int + " Time: " + (t2 - t1) / 1000000 + " миллисек");

        t1 = System.nanoTime();
        result_int = dao.AF_Sum_MusicInstrument_by_Model("Аккустическая");
        t2 = System.nanoTime();
        System.out.println("Function AF_Sum_MusicInstrument_by_Model() | Result: " + result_int + " Time: " + (t2 - t1) / 1000000 + " миллисек\n");

        System.out.println("2. Сумарна кількість інструментів з матеріала \"Фанера\"");
        t1 = System.nanoTime();
        result_int = dao.Sum_MusicInstrument_by_Material("Фанера");
        t2 = System.nanoTime();
        System.out.println("Function Sum_MusicInstrument_by_Material() | Result: " + result_int + " Time: " + (t2 - t1) / 1000000 + " миллисек");

        t1 = System.nanoTime();
        result_int = dao.AF_Sum_MusicInstrument_by_Material("Фанера");
        t2 = System.nanoTime();
        System.out.println("Function AF_Sum_MusicInstrument_by_Material() | Result: " + result_int + " Time: " + (t2 - t1) / 1000000 + " миллисек\n");

        System.out.println("3. Сумарна кількість інструментів для \"Джазбенду\" та \"Червоного\" кольору ");
        t1 = System.nanoTime();
        result_int = dao.Sum_MusicInstrument_by_WhereIsUsed_Color("Джазбэнд", "Красный");
        t2 = System.nanoTime();
        System.out.println("Function Sum_MusicInstrument_by_WhereIsUsed_Color() | Result: " + result_int + " Time: " + (t2 - t1) / 1000000 + " миллисек");

        t1 = System.nanoTime();
        result_int = dao.AF_Sum_MusicInstrument_by_WhereIsUsed_Color("Джазбэнд", "Красный");
        t2 = System.nanoTime();
        System.out.println("Function AF_Sum_MusicInstrument_by_WhereIsUsed_Color() | Result: " + result_int + " Time: " + (t2 - t1) / 1000000 + " миллисек\n");

        System.out.println("4. Середня вага інструментів \"Джамбо\"");
        t1 = System.nanoTime();
        result_double = dao.AverageWeight_MusicInstrument_by_Type("Джамбо");
        t2 = System.nanoTime();
        System.out.println("Function AverageWeight_MusicInstrument_by_Type() | Result: " + result_double + " Time: " + (t2 - t1) / 1000000 + " миллисек");

        t1 = System.nanoTime();
        result_double = dao.AF_AverageWeight_MusicInstrument_by_Type("Джамбо");
        t2 = System.nanoTime();
        System.out.println("Function AF_AverageWeight_MusicInstrument_by_Type() | Result: " + result_double + " Time: " + (t2 - t1) / 1000000 + " миллисек\n");

        System.out.println("5. Максимальна довжина \"Акустичних\" гітар для \"Ансамбля\"");
        t1 = System.nanoTime();
        result_int = dao.MaxLength_MusicInstrument_by_Model_WhereIsUsed("Аккустическая", "Ансамбль");
        t2 = System.nanoTime();
        System.out.println("Function MaxLength_MusicInstrument_by_Model_WhereIsUsed() | Result: " + result_int + " Time: " + (t2 - t1) / 1000000 + " миллисек");

        t1 = System.nanoTime();
        result_int = dao.AF_MaxLength_MusicInstrument_by_Model_WhereIsUsed("Аккустическая", "Ансамбль");
        t2 = System.nanoTime();
        System.out.println("Function AF_MaxLength_MusicInstrument_by_Model_WhereIsUsed() | Result: " + result_int + " Time: " + (t2 - t1) / 1000000 + " миллисек\n");
    }
}
