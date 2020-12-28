package ua.nure.st.kpp.example.demo.Music_instrument;

import ua.nure.st.kpp.example.demo.MySQL_DAO.MySQLDAOFactory;
import ua.nure.st.kpp.example.demo.MySQL_DAO.MySQLIDAO;
import ua.nure.st.kpp.example.demo.MySQL_DAO.MySQLTypeDAO;

public class main {
        public static void main(String[] args) {


                MySQLIDAO dao = MySQLDAOFactory.getDAOInstance(MySQLTypeDAO.MySQL);

                Guitar b = new Guitar(0,"Гитара", "Бежевый", 4.3, 161, "Электронная", "Jumbo", "Група", "Медиатор", "звукосниматели H-S-H, PSND1/PSND-S/PSND2, струнодержатель -Fat 10 tremolo", "Гитары с большим корпусом и округлыми формам","преимущественно рок-группа", new String[]{"Агатис"}, new String[]{"\"Дешевое красное дерево\" из семейства сосновых"}, new String[]{"Пластмасса","Толстая"}, 6, "Нейлон", 120, "4-5");
                Guitar b1 = new Guitar(0,"Гитара", "Бежевый", 4.3, 161, "Аккустическая", "Jumbo", "Народный оркестр", "Медиатор", "средняя", "Гитары с большим корпусом и округлыми формам","Применяется в качестве аккомпанирующего или сольного инструмента", new String[]{"Ситхинская ель", "Ольха"}, new String[]{"Делает звук ообенно звонким и четким", "Эта порода деревьев используются для внутренних частей инструмента"}, new String[]{"Пластмасса","толстая"}, 6, "Нейлон", 120, "4-5");
                Violin d = new Violin(0,"Скрипка", "Коричневый", 2.3, 90, "Акустическая", "Классическая", "Симфонический оркестр", "Смычок",  "маленькая","Cremona","Применяется в качестве аккомпанирующего или сольного инструмента", new String[]{"Ольха", "Клен"}, new String[]{"Эта порода деревьев используются для внутренних частей инструмента", "Для грифа, нижней деки, а также обечаек и подставки"}, new String[]{"Фернамбук","Плотность - 630 кг/м3"},4, "Бронза", 650, "3-4");
                dao.Insert_Music_Instrument(b);
                dao.Insert_Music_Instrument(b1);
                dao.Insert_Music_Instrument(d);

    }
}
