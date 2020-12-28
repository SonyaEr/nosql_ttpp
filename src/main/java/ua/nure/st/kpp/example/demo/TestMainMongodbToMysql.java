package ua.nure.st.kpp.example.demo;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.LoggerContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.nure.st.kpp.example.demo.MyDAO.MongoDBDAO;

public class TestMainMongodbToMysql {
    public static void main(String[] args) {
        LoggerContext loggerContext = (LoggerContext) LoggerFactory.getILoggerFactory();
        Logger rootLogger = loggerContext.getLogger("org.mongodb.driver");
        ((ch.qos.logback.classic.Logger) rootLogger).setLevel(Level.ERROR);

        MongoDBDAO dao = MongoDBDAO.getInstance();
        dao.delete_all();
        dao.migrate_data();
    }
}
