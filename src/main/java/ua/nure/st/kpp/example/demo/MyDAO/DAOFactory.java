package ua.nure.st.kpp.example.demo.MyDAO;

public class DAOFactory {
    private static ua.nure.st.kpp.example.demo.MyDAO.IDAO dao = null;

    public static IDAO getDAOInstance(TypeDAO type) {
        if (dao == null) {
            if (type == TypeDAO.MySQL)
            {
                dao = new MySQLDAO();
            }
            else
            {
                dao = new MongoDBDAO();
            }
        }
        return dao;
    }
}
