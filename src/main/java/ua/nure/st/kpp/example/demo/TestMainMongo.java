package ua.nure.st.kpp.example.demo;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.LoggerContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.nure.st.kpp.example.demo.MongoDB_DAO.MongoDBDAOFactory;
import ua.nure.st.kpp.example.demo.MongoDB_DAO.MongoDBIDAO;
import ua.nure.st.kpp.example.demo.MongoDB_DAO.MongoDBTypeDAO;
import ua.nure.st.kpp.example.demo.Music_instrument.Guitar;
import ua.nure.st.kpp.example.demo.Music_instrument.Music_Instrument;
import ua.nure.st.kpp.example.demo.Music_instrument.Violin;

import java.util.ArrayList;
import java.util.Scanner;

public class TestMainMongo {
    public static void main(String[] args) {
        LoggerContext loggerContext = (LoggerContext) LoggerFactory.getILoggerFactory();
        Logger rootLogger = loggerContext.getLogger("org.mongodb.driver");
        ((ch.qos.logback.classic.Logger) rootLogger).setLevel(Level.ERROR);

        MongoDBIDAO dao = MongoDBDAOFactory.getDAOInstance(MongoDBTypeDAO.MongoDB);

        Scanner in = new Scanner(System.in);
        System.out.println("Выберите этап теста:");
        System.out.println("1. Добавление музыкальных инструментов");
        System.out.println("2. Изменение материала инструмента по его ИД");
        System.out.println("3. Выбор музыкальных инструментов по названию и цвету");
        System.out.println("4. Удаление музыкального инструмента по его ИД");
        System.out.println("5. Удаление всех музыкальных инструментов");
        System.out.print("Введите номер теста:");
        int num = in.nextInt();
        in.close();
        if (num == 1) {
            //Добавление музыкального инструмента
            Guitar b1 = new Guitar(0, "Гитара", "Бежевый", 4.3, 161, "Электронная",
                    "Jumbo", "Група", "Медиатор",
                    "звукосниматели H-S-H, струнодержатель - Fat 10 tremolo",
                    "Гитары с большим корпусом", "преимущественно рок-группа",
                    new String[]{"Агатис"}, new String[]{"\"Дешевое красное дерево\" из семейства сосновых"},
                    new String[]{"Пластмасса", "Толстая"}, 6, "Нейлон", 120, "4-5");
            Violin b2 = new Violin(0, "Скрипка", "Коричневый", 2.3, 90, "Аккустическая",
                    "3/4", "Симфонический оркестр", "Смычок",
                    "маленькая", "Carlo Giordano",
                    "Применяется в качестве аккомпанирующего или сольного инструмента",
                    new String[]{"Ольха", "Клен"},
                    new String[]{"Эта порода деревьев используются для внутренних частей инструмента",
                            "Для грифа, нижней деки, а также обечаек и подставки"},
                    new String[]{"Фернамбук", "Плотность - 630 кг/м3"}, 4, "Бронза", 650,
                    "3-4");
            Guitar b3 = new Guitar(0, "Гитара", "черный", 4.3, 161, "Электронная",
                    "Jumbo", "Група", "Медиатор",
                    "звукосниматели H-S-H, PSND1/PSND-S/PSND2, струнодержатель -Fat 10 tremolo",
                    "Гитары с большим корпусом и округлыми формам", "преимущественно рок-группа",
                    new String[]{"Агатис", "Ясень"}, new String[]{"\"Дешевое красное дерево\" из семейства сосновых", "обычное дерево"},
                    new String[]{"Пластмасса", "Тонкая"}, 6, "Нейлон", 120, "4-5");
            dao.Insert_Music_Instrument(b1);
            dao.Insert_Music_Instrument(b2);
            dao.Insert_Music_Instrument(b3);
        } else if (num == 2) {
            //Изменение материала музыкального инструмента по его ИД
            dao.Update_Type_By_Name("test1", "opisanie test1", "5fd669bf30bea072923c4aa2");
        } else if (num == 3) {
            //Выбор музыкальных инструментов по названию и цвету
            dao.Select_Music_Instruments_by_Name_Color("Гитара", "Бежевый");
        } else if (num == 4) {
            //Удаление музыкального инструмента по его ИД
            dao.Delete_Music_Instrument_By_Id("5fd67a0fb0f7b10efc3c9a0b");
        } else if (num == 5) {
            //Удаление всех музыкальных инструментов
            dao.Delete_All_Music_Instrument();
        }

        //Вывод на экран информации о всех инструментах
        ArrayList<Music_Instrument> music_instruments = dao.Select_Music_Instruments();
        for (Music_Instrument i : music_instruments) {
            String message = "";
            message += "Информация о музыкальном инструменте " + i.getMusic_instrument_name() + '\n';
            message += "Цвет:" + i.getColor() + '\n';
            message += "Вес: " + i.getWeight() + '\n';
            message += "Длина: " + i.getLength() + '\n';
            message += "Модель: " + i.getModel_name() + " (" + i.getModel_description() + ")" + '\n';
            message += "Тип: " + i.getType_name() + " (" + i.getType_description() + ")" + '\n';
            message += "Где используется: " + i.getWhere_is_used_name() + " (" + i.getWhere_is_used_description() + ")" + '\n';
            String[] material_name_item = (String[]) i.getMaterial_name();
            String[] material_description_item = (String[]) i.getMaterial_description();
            for (int j = 0; j < material_name_item.length; j++) {
                message += "Материал[" + (j + 1) + "]: " + material_name_item[j] + " (" + material_description_item[j] + ")" + '\n';
            }
            message += "Дополнительный инструмент: " + i.getAdditional_tool_name() + '\n';
            String[] material_additioanl_tool_item = (String[]) i.getAdditional_tool_material();
            message += "Материал дополнительного инструмента: " + material_additioanl_tool_item[0] + " (" + material_additioanl_tool_item[1] + ")" + '\n';
            System.out.println(message);

        }
    }
}

