package ua.nure.st.kpp.example.demo.MongoDB_DAO;

public class MongoDBDAOFactory {
	private static MongoDBIDAO dao = null;

	public static MongoDBIDAO getDAOInstance(MongoDBTypeDAO type) {
		if (dao == null) {
			dao = new MongoDBDAO();
		}
		return dao;
	}
}
