package ua.nure.st.kpp.example.demo.MyDAO;

import java.sql.SQLException;

public interface IDAO {
    void migrate_data() throws SQLException;
    void delete_all() throws SQLException;
}
