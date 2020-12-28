package ua.nure.st.kpp.example.demo.MySQL_DAO;

public class MySQLDAOFactory {
	private static MySQLIDAO dao = null;

	public static MySQLIDAO getDAOInstance(MySQLTypeDAO type) {
		if (dao == null) {
			dao = new MySQLDAO();
		}
		return dao;
	}
}
