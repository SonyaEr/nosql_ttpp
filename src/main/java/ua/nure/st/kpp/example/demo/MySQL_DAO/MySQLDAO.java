package ua.nure.st.kpp.example.demo.MySQL_DAO;

import ua.nure.st.kpp.example.demo.Music_instrument.Music_Instrument;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.TimeZone;

import ua.nure.st.kpp.example.demo.Observer.MusicInstrumentObservable;
import ua.nure.st.kpp.example.demo.Proxy.Status;
import ua.nure.st.kpp.example.demo.Proxy.User;


public class MySQLDAO implements MySQLIDAO {
    public MusicInstrumentObservable events;
    private static MySQLDAO instance = null;
    private static Connection con = null;
    private static String add_music_instrument = "INSERT INTO music.music_instrument( music_instrument_name, color, weight, length, fk_model_id, fk_type_id, fk_where_is_used_id,fk_additional_tool_id) VALUES(?, ?, ?, ?, ?, ?, ?, ?);";
    private static String update_music_instrument_type_by_id = "UPDATE music.music_instrument SET fk_type_id=? WHERE music_instrument_id = ? limit 1";
    private static String restore_music_instrument = "UPDATE music_instrument SET music_instrument_id=?, music_instrument_name=?, color=?, weight=?, length=?, fk_model_id=?, fk_type_id=?, fk_where_is_used_id=?, fk_additional_tool_id=? WHERE music_instrument_id = ?";
    private static String delete_music_instrument_by_id = "DELETE FROM music.music_instrument WHERE music_instrument_id = ?";
    private static String get_music_instrument_by_all = "SELECT music_instrument_id FROM music.music_instrument where music_instrument_name =? and color=? and weight=? and length=? and fk_model_id=? and fk_type_id=? and fk_where_is_used_id=? and fk_additional_tool_id=? limit 1";
    private static String get_music_instrument_names = "SELECT distinct music_instrument_name FROM music.music_instrument";
    private static String get_music_instrument_by_names_color = "SELECT music_instrument_id FROM music.music_instrument WHERE music_instrument_name =? and color=? limit 1";
    private static String get_music_instrument_by_typename = "SELECT * FROM music_instrument WHERE fk_type_id IN (SELECT type_id FROM `type` WHERE type_name = ?)";

    private static String get_music_instrument_by_name = "SELECT * FROM music.music_instrument where music_instrument_name = ?";
    private static String get_music_instrument = "SELECT * FROM music.music_instrument";
    private static String get_music_instrument_id_last = "SELECT * FROM music.music_instrument order by music_instrument_id DESC LIMIT 1";
    private static String get_music_instrument_by_id = "SELECT * FROM music.music_instrument where music_instrument_id = ?";

    private static String add_music_instrument_material = "INSERT INTO music.music_instrument_material (material_id, music_instrument_id) VALUES(?,?)";
    private static String delete_music_instrument_material_by_id = "DELETE FROM music.music_instrument_material WHERE music_instrument_id = ? and material_id = ?";
    private static String get_music_instrument_material_by_all = "SELECT material_id, music_instrument_id FROM music.music_instrument_material where material_id=? and music_instrument_id =? limit 1;";
    private static String get_material_by_music_instrument_id = "SELECT * FROM music.music_instrument_material where music_instrument_id =?";

    private static String add_additional_tool = "INSERT INTO music.additional_tool (additional_tool_name, fk_material_id) VALUES(?, ?)";
    private static String get_additional_tool_by_all = "SELECT * FROM music.additional_tool where additional_tool_name=? and fk_material_id=? LIMIT 1";
    private static String get_additional_tool_by_id = "SELECT * FROM music.additional_tool where additional_tool_id=? LIMIT 1";
    private static String get_additional_tool_by_material_id = "SELECT * FROM music.additional_tool order by additional_tool_id DESC LIMIT 1";

    private static String add_model = "INSERT INTO music.Model (model_name, model_description) VALUES(?,?)";
    private static String get_model_by_all = "SELECT model_id FROM music.model where model_name=? and model_description=? limit 1;";
    private static String get_model_by_id = "SELECT * FROM music.model where model_id=? limit 1;";
    private static String get_model_id = "SELECT * FROM music.Model order by model_id DESC LIMIT 1";

    private static String add_type = "INSERT INTO music.type (type_name, type_description) VALUES(?,?)";
    private static String update_type_by_id = "UPDATE music.type SET type_name=?, type_description=? WHERE type_id IN (select fk_type_id from music.music_instrument where music_instrument_id = ?)  limit 1;";
    private static String get_type_by_all = "SELECT type_id FROM music.type where type_name=? and type_description=? limit 1";
    private static String get_type_by_id = "SELECT * FROM music.type where type_id=? limit 1";
    private static String get_type_id = "SELECT * FROM music.type order by type_id DESC LIMIT 1";

    private static String add_where_is_used = "INSERT INTO music.where_is_used (where_is_used_name, where_is_used_description) VALUES(?,?)";
    private static String get_where_is_used_by_all = "SELECT where_is_used_id FROM music.where_is_used where where_is_used_name=? and where_is_used_description=? limit 1";
    private static String get_where_is_used_by_id = "SELECT * FROM music.where_is_used where where_is_used_id=? limit 1";
    private static String get_where_is_used_id = "SELECT * FROM music.where_is_used order by Where_is_used_id DESC LIMIT 1";

    private static String add_material = "INSERT INTO music.material (material_name, material_description) VALUES(?,?)";
    private static String get_material_by_all = "SELECT material_id FROM music.material where material_name =? and material_description=? limit 1";
    private static String get_material_by_id = "SELECT * FROM music.material where material_id =? limit 1";
    private static String get_material_id = "SELECT * FROM music.material order by material_id DESC LIMIT 1";

    private static String delete_all_music_instrument = "DELETE FROM music.music_instrument";
    private static String delete_all_music_instrument_materials = "DELETE FROM music.music_instrument_material";

    private static String delete_all_test = "DELETE FROM music.test";
    private static String delete_all_test_index = "DELETE FROM music.test_index";

    private static String add_test = "INSERT INTO music.test (name, description) VALUES(?,?)";
    private static String add_test_index = "INSERT INTO music.test_index (name, description) VALUES(?,?)";
    private static String get_test_by_all = "SELECT id FROM music.test where name =?  and description=? limit 1";
    private static String get_test_index_by_all = "SELECT id FROM music.test_index where name =? and description=? limit 1";

    private static String reg_usr = "INSERT INTO user (login, password) VALUES(?, ?)";
    private static String get_usr = "SELECT * FROM user where login = ? and password = ?";

    public MySQLDAO() {
        events = new MusicInstrumentObservable();
        try {
            String url = "jdbc:mysql://localhost:3306/music?serverTimezone=" + TimeZone.getDefault().getID();
            con = DriverManager.getConnection(url, "root", "12345");
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }

    public static MySQLDAO getInstance() {
        if (instance == null) {
            instance = new MySQLDAO();
        }
        return instance;
    }

    @Override
    public void Insert_Music_Instrument(Music_Instrument music_instrument) {
        try {
            PreparedStatement get_music_instrument = con.prepareStatement(get_music_instrument_id_last);
            PreparedStatement get_type = con.prepareStatement(get_type_id);
            PreparedStatement get_model = con.prepareStatement(get_model_id);
            PreparedStatement get_where_is_used = con.prepareStatement(get_where_is_used_id);
            PreparedStatement get_material = con.prepareStatement(get_material_id);

            PreparedStatement get_music_instrument_all = con.prepareStatement(get_music_instrument_by_all);
            PreparedStatement get_model_all = con.prepareStatement(get_model_by_all);
            PreparedStatement get_type_all = con.prepareStatement(get_type_by_all);
            PreparedStatement get_where_is_used_all = con.prepareStatement(get_where_is_used_by_all);
            PreparedStatement get_music_instrument_material_all = con.prepareStatement(get_music_instrument_material_by_all);
            PreparedStatement get_addition_tool = con.prepareStatement(get_additional_tool_by_material_id);
            PreparedStatement get_material_all = con.prepareStatement(get_material_by_all);
            PreparedStatement get_additional_tool_all = con.prepareStatement(get_additional_tool_by_all);

            PreparedStatement insert_music_instrument = con.prepareStatement(add_music_instrument);
            PreparedStatement insert_type = con.prepareStatement(add_type);
            PreparedStatement insert_model = con.prepareStatement(add_model);
            PreparedStatement insert_where_is_used = con.prepareStatement(add_where_is_used);
            PreparedStatement insert_additional_tool = con.prepareStatement(add_additional_tool);
            PreparedStatement insert_material = con.prepareStatement(add_material);
            PreparedStatement insert_music_instrument_material = con.prepareStatement(add_music_instrument_material);

            String Model_name = music_instrument.getModel_name();
            String Model_description = music_instrument.getModel_description();
            get_model_all.setString(1, Model_name);
            get_model_all.setString(2, Model_description);
            ResultSet models_id = get_model_all.executeQuery();
            int model_id = 0;
            if (models_id.next() == false) {
                insert_model.setString(1, Model_name);
                insert_model.setString(2, Model_description);
                insert_model.executeUpdate();
                ResultSet resultSet = get_model.executeQuery();
                resultSet.next();
                model_id = resultSet.getInt("model_id");
            } else {
                model_id = models_id.getInt("model_id");
            }
            String type_name = music_instrument.getType_name();
            String type_description = music_instrument.getType_description();
            get_type_all.setString(1, type_name);
            get_type_all.setString(2, type_description);

            ResultSet types_id = get_type_all.executeQuery();
            int type_id = 0;
            if (types_id.next() == false) {

                insert_type.setString(1, type_name);
                insert_type.setString(2, type_description);
                insert_type.executeUpdate();

                ResultSet resultSet = get_type.executeQuery();
                resultSet.next();
                type_id = resultSet.getInt("type_id");
            } else {
                type_id = types_id.getInt("type_id");
            }
            String where_is_used_name = music_instrument.getWhere_is_used_name();
            String where_is_used_description = music_instrument.getWhere_is_used_description();
            get_where_is_used_all.setString(1, where_is_used_name);
            get_where_is_used_all.setString(2, where_is_used_description);
            ResultSet where_is_useds_id = get_where_is_used_all.executeQuery();
            int where_is_used_id = 0;
            if (where_is_useds_id.next() == false) {
                insert_where_is_used.setString(1, where_is_used_name);
                insert_where_is_used.setString(2, where_is_used_description);
                insert_where_is_used.executeUpdate();
                ResultSet resultSet = get_where_is_used.executeQuery();
                resultSet.next();
                where_is_used_id = resultSet.getInt("where_is_used_id");
            } else {
                where_is_used_id = where_is_useds_id.getInt("where_is_used_id");
            }
            String[] additional_tool_material = music_instrument.getAdditional_tool_material();
            String additional_tool_material_name = additional_tool_material[0];
            String additional_tool_material_description = additional_tool_material[1];
            get_material_all.setString(1, additional_tool_material_name);
            get_material_all.setString(2, additional_tool_material_description);
            ResultSet additional_tool_materials_id = get_material_all.executeQuery();
            int additional_tool_material_id = 0;
            if (additional_tool_materials_id.next() == false) {
                insert_material.setString(1, additional_tool_material_name);
                insert_material.setString(2, additional_tool_material_description);
                insert_material.executeUpdate();
                ResultSet resultSet = get_material.executeQuery();
                resultSet.next();
                additional_tool_material_id = resultSet.getInt("material_id");
            } else {
                additional_tool_material_id = additional_tool_materials_id.getInt("material_id");
            }
            int fk_material_id = additional_tool_material_id;
            String additional_tool_name = music_instrument.getAdditional_tool_name();
            get_additional_tool_all.setString(1, additional_tool_name);
            get_additional_tool_all.setInt(2, fk_material_id);
            ResultSet additional_tools_id = get_additional_tool_all.executeQuery();
            int additional_tool_id = 0;
            if (additional_tools_id.next() == false) {
                insert_additional_tool.setString(1, additional_tool_name);
                insert_additional_tool.setInt(2, fk_material_id);
                insert_additional_tool.executeUpdate();
                ResultSet resultSet = get_addition_tool.executeQuery();
                resultSet.next();
                additional_tool_id = resultSet.getInt("additional_tool_id");
            } else {
                additional_tool_id = additional_tools_id.getInt("additional_tool_id");
            }

            int fk_model_id = model_id;
            int fk_type_id = type_id;
            int fk_where_is_used_id = where_is_used_id;
            int fk_additional_tool_id = additional_tool_id;
            String music_instrument_name = music_instrument.getMusic_instrument_name();
            String color = music_instrument.getColor();
            double weight = music_instrument.getWeight();
            int length = music_instrument.getLength();
            get_music_instrument_all.setString(1, music_instrument_name);
            get_music_instrument_all.setString(2, color);
            get_music_instrument_all.setDouble(3, weight);
            get_music_instrument_all.setInt(4, length);
            get_music_instrument_all.setInt(5, fk_model_id);
            get_music_instrument_all.setInt(6, fk_type_id);
            get_music_instrument_all.setInt(7, fk_where_is_used_id);
            get_music_instrument_all.setInt(8, fk_additional_tool_id);
            ResultSet music_instruments_id = get_music_instrument_all.executeQuery();
            int music_instrument_id = 0;
            if (music_instruments_id.next() == false) {
                insert_music_instrument.setString(1, music_instrument_name);
                insert_music_instrument.setString(2, color);
                insert_music_instrument.setDouble(3, weight);
                insert_music_instrument.setInt(4, length);
                insert_music_instrument.setInt(5, fk_model_id);
                insert_music_instrument.setInt(6, fk_type_id);
                insert_music_instrument.setInt(7, fk_where_is_used_id);
                insert_music_instrument.setInt(8, fk_additional_tool_id);
                insert_music_instrument.executeUpdate();
                ResultSet resultSet = get_music_instrument.executeQuery();
                resultSet.next();
                music_instrument_id = resultSet.getInt("music_instrument_id");
            } else {
                music_instrument_id = music_instruments_id.getInt("music_instrument_id");
            }
            String[] materials = music_instrument.getMaterial_name();
            int size = materials.length;
            String[] descriptions = music_instrument.getMaterial_description();
            for (int i = 0; i < size; i++) {
                String material_name = materials[i];
                String material_description = "";
                try {
                    material_description = descriptions[i];
                } catch (Exception ex) {
                    System.out.println("index for Material_description is out range");
                    System.out.println(ex);
                    break;
                }
                get_material_all.setString(1, material_name);
                get_material_all.setString(2, material_description);
                ResultSet materials_id = get_material_all.executeQuery();
                int material_id = 0;
                if (materials_id.next() == false) {
                    insert_material.setString(1, material_name);
                    insert_material.setString(2, material_description);
                    insert_material.executeUpdate();
                    ResultSet resultSet = get_material.executeQuery();
                    resultSet.next();
                    material_id = resultSet.getInt("material_id");
                } else {
                    material_id = materials_id.getInt("material_id");
                }
                get_music_instrument_material_all.setInt(1, material_id);
                get_music_instrument_material_all.setInt(2, music_instrument_id);
                ResultSet instrument_materials_id = get_music_instrument_material_all.executeQuery();
                if (instrument_materials_id.next() == false) {
                    insert_music_instrument_material.setInt(1, material_id);
                    insert_music_instrument_material.setInt(2, music_instrument_id);
                    insert_music_instrument_material.executeUpdate();
                }
                ArrayList<Object[]> music_instruments_insert = Select_Music_Instrument_By_Id(music_instrument_id);
                events.notifyObservers("Add", music_instruments_insert);
            }
        } catch (Exception ex) {

            System.out.println("error add row...");
            System.out.println(ex);
        }
    }

    public Music_Instrument Insert_Music_Instrument(Music_Instrument music_instrument, Status st) {
        try {
            if (st == Status.ADMIN) {
                PreparedStatement get_music_instrument = con.prepareStatement(get_music_instrument_id_last);
                PreparedStatement get_type = con.prepareStatement(get_type_id);
                PreparedStatement get_model = con.prepareStatement(get_model_id);
                PreparedStatement get_where_is_used = con.prepareStatement(get_where_is_used_id);
                PreparedStatement get_material = con.prepareStatement(get_material_id);

                PreparedStatement get_music_instrument_all = con.prepareStatement(get_music_instrument_by_all);
                PreparedStatement get_model_all = con.prepareStatement(get_model_by_all);
                PreparedStatement get_type_all = con.prepareStatement(get_type_by_all);
                PreparedStatement get_where_is_used_all = con.prepareStatement(get_where_is_used_by_all);
                PreparedStatement get_music_instrument_material_all = con.prepareStatement(get_music_instrument_material_by_all);
                PreparedStatement get_addition_tool = con.prepareStatement(get_additional_tool_by_material_id);
                PreparedStatement get_material_all = con.prepareStatement(get_material_by_all);
                PreparedStatement get_additional_tool_all = con.prepareStatement(get_additional_tool_by_all);

                PreparedStatement insert_music_instrument = con.prepareStatement(add_music_instrument);
                PreparedStatement insert_type = con.prepareStatement(add_type);
                PreparedStatement insert_model = con.prepareStatement(add_model);
                PreparedStatement insert_where_is_used = con.prepareStatement(add_where_is_used);
                PreparedStatement insert_additional_tool = con.prepareStatement(add_additional_tool);
                PreparedStatement insert_material = con.prepareStatement(add_material);
                PreparedStatement insert_music_instrument_material = con.prepareStatement(add_music_instrument_material);

                String Model_name = music_instrument.getModel_name();
                String Model_description = music_instrument.getModel_description();
                get_model_all.setString(1, Model_name);
                get_model_all.setString(2, Model_description);
                ResultSet models_id = get_model_all.executeQuery();
                int model_id = 0;
                if (models_id.next() == false) {
                    insert_model.setString(1, Model_name);
                    insert_model.setString(2, Model_description);
                    insert_model.executeUpdate();
                    ResultSet resultSet = get_model.executeQuery();
                    resultSet.next();
                    model_id = resultSet.getInt("model_id");
                } else {
                    model_id = models_id.getInt("model_id");
                }
                String type_name = music_instrument.getType_name();
                String type_description = music_instrument.getType_description();
                get_type_all.setString(1, type_name);
                get_type_all.setString(2, type_description);

                ResultSet types_id = get_type_all.executeQuery();
                int type_id = 0;
                if (types_id.next() == false) {

                    insert_type.setString(1, type_name);
                    insert_type.setString(2, type_description);
                    insert_type.executeUpdate();

                    ResultSet resultSet = get_type.executeQuery();
                    resultSet.next();
                    type_id = resultSet.getInt("type_id");
                } else {
                    type_id = types_id.getInt("type_id");
                }
                String where_is_used_name = music_instrument.getWhere_is_used_name();
                String where_is_used_description = music_instrument.getWhere_is_used_description();
                get_where_is_used_all.setString(1, where_is_used_name);
                get_where_is_used_all.setString(2, where_is_used_description);
                ResultSet where_is_useds_id = get_where_is_used_all.executeQuery();
                int where_is_used_id = 0;
                if (where_is_useds_id.next() == false) {
                    insert_where_is_used.setString(1, where_is_used_name);
                    insert_where_is_used.setString(2, where_is_used_description);
                    insert_where_is_used.executeUpdate();
                    ResultSet resultSet = get_where_is_used.executeQuery();
                    resultSet.next();
                    where_is_used_id = resultSet.getInt("where_is_used_id");
                } else {
                    where_is_used_id = where_is_useds_id.getInt("where_is_used_id");
                }
                String[] additional_tool_material = music_instrument.getAdditional_tool_material();
                String additional_tool_material_name = additional_tool_material[0];
                String additional_tool_material_description = additional_tool_material[1];
                get_material_all.setString(1, additional_tool_material_name);
                get_material_all.setString(2, additional_tool_material_description);
                ResultSet additional_tool_materials_id = get_material_all.executeQuery();
                int additional_tool_material_id = 0;
                if (additional_tool_materials_id.next() == false) {
                    insert_material.setString(1, additional_tool_material_name);
                    insert_material.setString(2, additional_tool_material_description);
                    insert_material.executeUpdate();
                    ResultSet resultSet = get_material.executeQuery();
                    resultSet.next();
                    additional_tool_material_id = resultSet.getInt("material_id");
                } else {
                    additional_tool_material_id = additional_tool_materials_id.getInt("material_id");
                }
                int fk_material_id = additional_tool_material_id;
                String additional_tool_name = music_instrument.getAdditional_tool_name();
                get_additional_tool_all.setString(1, additional_tool_name);
                get_additional_tool_all.setInt(2, fk_material_id);
                ResultSet additional_tools_id = get_additional_tool_all.executeQuery();
                int additional_tool_id = 0;
                if (additional_tools_id.next() == false) {
                    insert_additional_tool.setString(1, additional_tool_name);
                    insert_additional_tool.setInt(2, fk_material_id);
                    insert_additional_tool.executeUpdate();
                    ResultSet resultSet = get_addition_tool.executeQuery();
                    resultSet.next();
                    additional_tool_id = resultSet.getInt("additional_tool_id");
                } else {
                    additional_tool_id = additional_tools_id.getInt("additional_tool_id");
                }

                int fk_model_id = model_id;
                int fk_type_id = type_id;
                int fk_where_is_used_id = where_is_used_id;
                int fk_additional_tool_id = additional_tool_id;
                String music_instrument_name = music_instrument.getMusic_instrument_name();
                String color = music_instrument.getColor();
                double weight = music_instrument.getWeight();
                int length = music_instrument.getLength();
                get_music_instrument_all.setString(1, music_instrument_name);
                get_music_instrument_all.setString(2, color);
                get_music_instrument_all.setDouble(3, weight);
                get_music_instrument_all.setInt(4, length);
                get_music_instrument_all.setInt(5, fk_model_id);
                get_music_instrument_all.setInt(6, fk_type_id);
                get_music_instrument_all.setInt(7, fk_where_is_used_id);
                get_music_instrument_all.setInt(8, fk_additional_tool_id);
                ResultSet music_instruments_id = get_music_instrument_all.executeQuery();
                int music_instrument_id = 0;
                if (music_instruments_id.next() == false) {
                    insert_music_instrument.setString(1, music_instrument_name);
                    insert_music_instrument.setString(2, color);
                    insert_music_instrument.setDouble(3, weight);
                    insert_music_instrument.setInt(4, length);
                    insert_music_instrument.setInt(5, fk_model_id);
                    insert_music_instrument.setInt(6, fk_type_id);
                    insert_music_instrument.setInt(7, fk_where_is_used_id);
                    insert_music_instrument.setInt(8, fk_additional_tool_id);
                    insert_music_instrument.executeUpdate();
                    ResultSet resultSet = get_music_instrument.executeQuery();
                    resultSet.next();
                    music_instrument_id = resultSet.getInt("music_instrument_id");
                } else {
                    music_instrument_id = music_instruments_id.getInt("music_instrument_id");
                }
                String[] materials = music_instrument.getMaterial_name();
                int size = materials.length;
                String[] descriptions = music_instrument.getMaterial_description();
                for (int i = 0; i < size; i++) {
                    String material_name = materials[i];
                    String material_description = "";
                    try {
                        material_description = descriptions[i];
                    } catch (Exception ex) {
                        System.out.println("index for Material_description is out range");
                        System.out.println(ex);
                        break;
                    }
                    get_material_all.setString(1, material_name);
                    get_material_all.setString(2, material_description);
                    ResultSet materials_id = get_material_all.executeQuery();
                    int material_id = 0;
                    if (materials_id.next() == false) {
                        insert_material.setString(1, material_name);
                        insert_material.setString(2, material_description);
                        insert_material.executeUpdate();
                        ResultSet resultSet = get_material.executeQuery();
                        resultSet.next();
                        material_id = resultSet.getInt("material_id");
                    } else {
                        material_id = materials_id.getInt("material_id");
                    }
                    get_music_instrument_material_all.setInt(1, material_id);
                    get_music_instrument_material_all.setInt(2, music_instrument_id);
                    ResultSet instrument_materials_id = get_music_instrument_material_all.executeQuery();
                    if (instrument_materials_id.next() == false) {
                        insert_music_instrument_material.setInt(1, material_id);
                        insert_music_instrument_material.setInt(2, music_instrument_id);
                        insert_music_instrument_material.executeUpdate();
                    }
                    music_instrument.setId(music_instrument_id);
                    ArrayList<Object[]> music_instruments_insert = Select_Music_Instrument_By_Id(music_instrument_id);
                    events.notifyObservers("Add", music_instruments_insert);
                }
            } else {
                System.out.println("Недостаточно прав для добавления записи");
                return new Music_Instrument();
            }
        } catch (Exception ex) {

            System.out.println("error add row...");
            System.out.println(ex);
        }
        return music_instrument;
    }

    @Override
    public void Delete_Music_Instrument_By_Id(int music_instrument_id) {
        try {
            ArrayList<Object[]> music_instrument_delete = Select_Music_Instrument_By_Id(music_instrument_id);
            PreparedStatement get_instrument_material = con.prepareStatement(get_material_by_music_instrument_id);
            PreparedStatement delete_music_instrument = con.prepareStatement(delete_music_instrument_by_id);
            get_instrument_material.setInt(1, music_instrument_id);
            ResultSet resultMaterial_instrument = get_instrument_material.executeQuery();
            while (resultMaterial_instrument.next()) {
                int material_id = resultMaterial_instrument.getInt("material_id");
                Delete_Material_music_instrument_Id(music_instrument_id, material_id);
            }
            delete_music_instrument.setInt(1, music_instrument_id);
            delete_music_instrument.executeUpdate();
            events.notifyObservers("Delete", music_instrument_delete);
        } catch (Exception ex) {
            System.out.println("Query failed...");
            System.out.println(ex);
        }
    }

    public void Delete_Music_Instrument_By_Id(int music_instrument_id, Status st) {
        try {
            if (st == Status.ADMIN) {
                ArrayList<Object[]> music_instrument_delete = Select_Music_Instrument_By_Id(music_instrument_id);

                PreparedStatement get_instrument_material = con.prepareStatement(get_material_by_music_instrument_id);
                PreparedStatement delete_music_instrument = con.prepareStatement(delete_music_instrument_by_id);
                get_instrument_material.setInt(1, music_instrument_id);
                ResultSet resultMaterial_instrument = get_instrument_material.executeQuery();
                while (resultMaterial_instrument.next()) {
                    int material_id = resultMaterial_instrument.getInt("material_id");
                    Delete_Material_music_instrument_Id(music_instrument_id, material_id);
                }
                delete_music_instrument.setInt(1, music_instrument_id);
                delete_music_instrument.executeUpdate();
                events.notifyObservers("Delete", music_instrument_delete);
            } else {
                System.out.println("Недостаточно прав для удаления записи");
            }
        } catch (Exception ex) {
            System.out.println("Query failed...");
            System.out.println(ex);
        }
    }

    public void Delete_Material_music_instrument_Id(int music_instrument_id, int material_id) {
        try {
            ArrayList<Object[]> music_instrument_before = Select_Music_Instrument_By_Id(music_instrument_id);
            PreparedStatement delete_material_music_instrument = con.prepareStatement(delete_music_instrument_material_by_id);
            delete_material_music_instrument.setInt(1, music_instrument_id);
            delete_material_music_instrument.setInt(2, material_id);
            delete_material_music_instrument.executeUpdate();
            ArrayList<Object[]> music_instruments_after = Select_Music_Instrument_By_Id(music_instrument_id);
            events.notifyObservers("Edit_before", music_instrument_before);
            events.notifyObservers("Edit_after", music_instruments_after);
        } catch (Exception ex) {
            System.out.println("Query failed...");
            System.out.println(ex);
        }
    }

    public void Update_Type_By_Name(String type_name, String type_description, int music_instrument_id) {
        try {
            ArrayList<Object[]> music_instrument_before = Select_Music_Instrument_By_Id(music_instrument_id);
            PreparedStatement get_type = con.prepareStatement(get_type_id);
            PreparedStatement get_type_all = con.prepareStatement(get_type_by_all);
            get_type_all.setString(1, type_name);
            get_type_all.setString(2, type_description);
            ResultSet types_id = get_type_all.executeQuery();
            int type_id = 0;
            if (types_id.next() == false) {
                PreparedStatement update_type = con.prepareStatement(update_type_by_id);
                update_type.setString(1, type_name);
                update_type.setString(2, type_description);
                update_type.setInt(3, music_instrument_id);
                update_type.executeUpdate();
                ResultSet resultSet = get_type.executeQuery();
                resultSet.next();
                type_id = resultSet.getInt("type_id");
            } else {
                type_id = types_id.getInt("type_id");
            }
            ArrayList<Object[]> music_instruments_after = Select_Music_Instrument_By_Id(music_instrument_id);
            events.notifyObservers("Edit_before", music_instrument_before);
            events.notifyObservers("Edit_after", music_instruments_after);
        } catch (Exception ex) {
            System.out.println("Query failed...");
            System.out.println(ex);
        }
    }

    public void Update_Music_Instrument_Type_By_Name(String type_name, String type_description, int music_instrument_id) {
        try {
            Music_Instrument music_instrument = Get_Music_Instrument_By_Id(music_instrument_id);
            PreparedStatement get_type_all = con.prepareStatement(get_type_by_all);
            get_type_all.setString(1, type_name);
            get_type_all.setString(2, type_description);
            ResultSet types_id = get_type_all.executeQuery();
            int type_id;
            if (types_id.next() == false) {
                PreparedStatement insert_type = con.prepareStatement(add_type);
                insert_type.setString(1, type_name);
                insert_type.setString(2, type_description);
                insert_type.executeUpdate();
                ResultSet resultSet = con.prepareStatement(get_type_id).executeQuery();
                resultSet.next();
                type_id = resultSet.getInt("type_id");
            } else {
                type_id = types_id.getInt("type_id");
            }
            PreparedStatement update_music_instrument = con.prepareStatement(update_music_instrument_type_by_id);
            update_music_instrument.setInt(1, type_id);
            update_music_instrument.setInt(2, music_instrument_id);
            update_music_instrument.executeUpdate();
        } catch (Exception ex) {
            System.out.println("Query failed...");
            System.out.println(ex);
        }
    }

    public void Update_Music_Instrument_Type_By_Name(String type_name, String type_description, int music_instrument_id, Status st) {
        try {
            if (st == Status.ADMIN) {
                Music_Instrument music_instrument = Get_Music_Instrument_By_Id(music_instrument_id);
                PreparedStatement get_type_all = con.prepareStatement(get_type_by_all);
                get_type_all.setString(1, type_name);
                get_type_all.setString(2, type_description);
                ResultSet types_id = get_type_all.executeQuery();
                int type_id;
                if (types_id.next() == false) {
                    PreparedStatement insert_type = con.prepareStatement(add_type);
                    insert_type.setString(1, type_name);
                    insert_type.setString(2, type_description);
                    insert_type.executeUpdate();
                    ResultSet resultSet = con.prepareStatement(get_type_id).executeQuery();
                    resultSet.next();
                    type_id = resultSet.getInt("type_id");
                } else {
                    type_id = types_id.getInt("type_id");
                }
                PreparedStatement update_music_instrument = con.prepareStatement(update_music_instrument_type_by_id);
                update_music_instrument.setInt(1, type_id);
                update_music_instrument.setInt(2, music_instrument_id);
                update_music_instrument.executeUpdate();
            } else {
                System.out.println("Недостаточно прав для внесения изменений");
            }
        } catch (Exception ex) {
            System.out.println("Query failed...");
            System.out.println(ex);
        }
    }

    @Override
    public ArrayList<Object[]> Select_Music_Instrument_By_Name(String music_instrument_name) {
        try {
            PreparedStatement get_music_instrument = con.prepareStatement(get_music_instrument_by_name);
            PreparedStatement get_type = con.prepareStatement(get_type_by_id);
            PreparedStatement get_model = con.prepareStatement(get_model_by_id);
            PreparedStatement get_where_is_used = con.prepareStatement(get_where_is_used_by_id);
            PreparedStatement get_additional_tool = con.prepareStatement(get_additional_tool_by_id);
            PreparedStatement get_material = con.prepareStatement(get_material_by_id);
            PreparedStatement get_instrument_material = con.prepareStatement(get_material_by_music_instrument_id);

            get_music_instrument.setString(1, music_instrument_name);
            ResultSet resultSet = get_music_instrument.executeQuery();
            ArrayList<Object[]> result = new ArrayList<Object[]>();
            while (resultSet.next()) {
                Object[] resobj = new Object[15];
                resobj[0] = resultSet.getInt("music_instrument_id");
                resobj[1] = resultSet.getString("music_instrument_name");
                resobj[2] = resultSet.getString("color");
                resobj[3] = resultSet.getFloat("weight");
                resobj[4] = resultSet.getInt("length");
                get_model.setInt(1, resultSet.getInt("fk_model_id"));
                ResultSet resultModel = get_model.executeQuery();
                if (resultModel.next() == true) {
                    resobj[5] = resultModel.getString("model_name");
                    resobj[9] = resultModel.getString("model_description");
                }
                get_type.setInt(1, resultSet.getInt("fk_type_id"));
                ResultSet resultType = get_type.executeQuery();
                if (resultType.next() == true) {
                    resobj[6] = resultType.getString("type_name");
                    resobj[10] = resultType.getString("type_description");
                }
                get_where_is_used.setInt(1, resultSet.getInt("fk_where_is_used_id"));
                ResultSet resultWhere = get_where_is_used.executeQuery();
                if (resultWhere.next() == true) {
                    resobj[7] = resultWhere.getString("where_is_used_name");
                    resobj[11] = resultWhere.getString("where_is_used_description");
                }
                get_additional_tool.setInt(1, resultSet.getInt("fk_additional_tool_id"));
                ResultSet resultAdditionalTool = get_additional_tool.executeQuery();
                if (resultAdditionalTool.next() == true) {
                    resobj[8] = resultAdditionalTool.getString("additional_tool_name");
                    get_material.setInt(1, resultAdditionalTool.getInt("fk_material_id"));
                    ResultSet resultMaterial = get_material.executeQuery();
                    String[] stringAT_material = new String[2];
                    if (resultMaterial.next() == true) {
                        stringAT_material[0] = resultMaterial.getString("material_name");
                        stringAT_material[1] = resultMaterial.getString("material_description");
                        resobj[14] = stringAT_material;
                    }
                }
                get_instrument_material.setInt(1, resultSet.getInt("music_instrument_id"));
                ResultSet resultMaterial_instrument = get_instrument_material.executeQuery();
                List<String> list_name = new ArrayList<String>();
                List<String> list_description = new ArrayList<String>();
                int material_id;
                while (resultMaterial_instrument.next()) {
                    material_id = resultMaterial_instrument.getInt("material_id");
                    get_material.setInt(1, material_id);
                    ResultSet resultMaterial = get_material.executeQuery();
                    if (resultMaterial.next() == true) {
                        list_name.add(resultMaterial.getString("material_name"));
                        list_description.add(resultMaterial.getString("material_description"));
                    }
                }
                String[] stringName = list_name.toArray(new String[0]);
                String[] stringDescription = list_description.toArray(new String[0]);
                resobj[12] = stringName;
                resobj[13] = stringDescription;
                result.add(resobj);
            }
            return result;
        } catch (Exception ex) {
            System.out.println("Query failed...");
            System.out.println(ex);
            return null;
        }
    }

    @Override
    public Music_Instrument Get_Music_Instrument_By_Id(int music_instrument_id) {
        try {
            PreparedStatement get_music_instrument = con.prepareStatement(get_music_instrument_by_id);
            PreparedStatement get_type = con.prepareStatement(get_type_by_id);
            PreparedStatement get_model = con.prepareStatement(get_model_by_id);
            PreparedStatement get_where_is_used = con.prepareStatement(get_where_is_used_by_id);
            PreparedStatement get_additional_tool = con.prepareStatement(get_additional_tool_by_id);
            PreparedStatement get_material = con.prepareStatement(get_material_by_id);
            PreparedStatement get_instrument_material = con.prepareStatement(get_material_by_music_instrument_id);

            get_music_instrument.setInt(1, music_instrument_id);
            ResultSet resultSet = get_music_instrument.executeQuery();
            if (resultSet.next()) {
                Object[] resobj = new Object[15];
                resobj[0] = resultSet.getInt("music_instrument_id");
                resobj[1] = resultSet.getString("music_instrument_name");
                resobj[2] = resultSet.getString("color");
                resobj[3] = resultSet.getDouble("weight");
                resobj[4] = resultSet.getInt("length");
                get_model.setInt(1, resultSet.getInt("fk_model_id"));
                ResultSet resultModel = get_model.executeQuery();
                if (resultModel.next() == true) {
                    resobj[5] = resultModel.getString("model_name");
                    resobj[9] = resultModel.getString("model_description");
                }
                get_type.setInt(1, resultSet.getInt("fk_type_id"));
                ResultSet resultType = get_type.executeQuery();
                if (resultType.next() == true) {
                    resobj[6] = resultType.getString("type_name");
                    resobj[10] = resultType.getString("type_description");
                }
                get_where_is_used.setInt(1, resultSet.getInt("fk_where_is_used_id"));
                ResultSet resultWhere = get_where_is_used.executeQuery();
                if (resultWhere.next() == true) {
                    resobj[7] = resultWhere.getString("where_is_used_name");
                    resobj[11] = resultWhere.getString("where_is_used_description");
                }
                get_additional_tool.setInt(1, resultSet.getInt("fk_additional_tool_id"));
                ResultSet resultAdditionalTool = get_additional_tool.executeQuery();
                if (resultAdditionalTool.next() == true) {
                    resobj[8] = resultAdditionalTool.getString("additional_tool_name");
                    get_material.setInt(1, resultAdditionalTool.getInt("fk_material_id"));
                    ResultSet resultMaterial = get_material.executeQuery();
                    String[] stringAT_material = new String[2];
                    if (resultMaterial.next() == true) {
                        stringAT_material[0] = resultMaterial.getString("material_name");
                        stringAT_material[1] = resultMaterial.getString("material_description");
                        resobj[14] = stringAT_material;
                    }
                }
                get_instrument_material.setInt(1, resultSet.getInt("music_instrument_id"));
                ResultSet resultMaterial_instrument = get_instrument_material.executeQuery();
                List<String> list_name = new ArrayList<String>();
                List<String> list_description = new ArrayList<String>();
                int material_id;
                while (resultMaterial_instrument.next()) {
                    material_id = resultMaterial_instrument.getInt("material_id");
                    get_material.setInt(1, material_id);
                    ResultSet resultMaterial = get_material.executeQuery();
                    if (resultMaterial.next() == true) {
                        list_name.add(resultMaterial.getString("material_name"));
                        list_description.add(resultMaterial.getString("material_description"));
                    }
                }
                String[] stringName = list_name.toArray(new String[0]);
                String[] stringDescription = list_description.toArray(new String[0]);
                resobj[12] = stringName;
                resobj[13] = stringDescription;

                Music_Instrument result = new Music_Instrument((int) resobj[0],
                        (String) resobj[1],
                        (String) resobj[2],
                        (Double) resobj[3],
                        (int) resobj[4],
                        (String) resobj[5],
                        (String) resobj[6],
                        (String) resobj[7],
                        (String) resobj[8],
                        (String) resobj[9],
                        (String) resobj[10],
                        (String) resobj[11],
                        (String[]) resobj[12],
                        (String[]) resobj[13],
                        (String[]) resobj[14]);
                return result;
            } else {
                return new Music_Instrument();
            }
        } catch (Exception ex) {
            System.out.println("Query failed...");
            System.out.println(ex);
            return null;
        }
    }

    public ArrayList<Music_Instrument> Get_Music_Instruments() {
        try {
            PreparedStatement get_music_instruments = con.prepareStatement(get_music_instrument);
            PreparedStatement get_type = con.prepareStatement(get_type_by_id);
            PreparedStatement get_model = con.prepareStatement(get_model_by_id);
            PreparedStatement get_where_is_used = con.prepareStatement(get_where_is_used_by_id);
            PreparedStatement get_additional_tool = con.prepareStatement(get_additional_tool_by_id);
            PreparedStatement get_material = con.prepareStatement(get_material_by_id);
            PreparedStatement get_instrument_material = con.prepareStatement(get_material_by_music_instrument_id);

            ResultSet resultSet = get_music_instruments.executeQuery();
            ArrayList<Music_Instrument> result = new ArrayList<Music_Instrument>();
            while (resultSet.next()) {
                Object[] resobj = new Object[15];
                resobj[0] = resultSet.getInt("music_instrument_id");
                resobj[1] = resultSet.getString("music_instrument_name");
                resobj[2] = resultSet.getString("color");
                resobj[3] = resultSet.getDouble("weight");
                resobj[4] = resultSet.getInt("length");
                get_model.setInt(1, resultSet.getInt("fk_model_id"));
                ResultSet resultModel = get_model.executeQuery();
                if (resultModel.next() == true) {
                    resobj[5] = resultModel.getString("model_name");
                    resobj[9] = resultModel.getString("model_description");
                }
                get_type.setInt(1, resultSet.getInt("fk_type_id"));
                ResultSet resultType = get_type.executeQuery();
                if (resultType.next() == true) {
                    resobj[6] = resultType.getString("type_name");
                    resobj[10] = resultType.getString("type_description");
                }
                get_where_is_used.setInt(1, resultSet.getInt("fk_where_is_used_id"));
                ResultSet resultWhere = get_where_is_used.executeQuery();
                if (resultWhere.next() == true) {
                    resobj[7] = resultWhere.getString("where_is_used_name");
                    resobj[11] = resultWhere.getString("where_is_used_description");
                }
                get_additional_tool.setInt(1, resultSet.getInt("fk_additional_tool_id"));
                ResultSet resultAdditionalTool = get_additional_tool.executeQuery();
                if (resultAdditionalTool.next() == true) {
                    resobj[8] = resultAdditionalTool.getString("additional_tool_name");
                    get_material.setInt(1, resultAdditionalTool.getInt("fk_material_id"));
                    ResultSet resultMaterial = get_material.executeQuery();
                    String[] stringAT_material = new String[2];
                    if (resultMaterial.next() == true) {
                        stringAT_material[0] = resultMaterial.getString("material_name");
                        stringAT_material[1] = resultMaterial.getString("material_description");
                        resobj[14] = stringAT_material;
                    }
                }
                get_instrument_material.setInt(1, resultSet.getInt("music_instrument_id"));
                ResultSet resultMaterial_instrument = get_instrument_material.executeQuery();
                List<String> list_name = new ArrayList<String>();
                List<String> list_description = new ArrayList<String>();
                int material_id;
                while (resultMaterial_instrument.next()) {
                    material_id = resultMaterial_instrument.getInt("material_id");
                    get_material.setInt(1, material_id);
                    ResultSet resultMaterial = get_material.executeQuery();
                    if (resultMaterial.next() == true) {
                        list_name.add(resultMaterial.getString("material_name"));
                        list_description.add(resultMaterial.getString("material_description"));
                    }
                }
                String[] stringName = list_name.toArray(new String[0]);
                String[] stringDescription = list_description.toArray(new String[0]);
                resobj[12] = stringName;
                resobj[13] = stringDescription;

                result.add(new Music_Instrument((int) resobj[0],
                        (String) resobj[1],
                        (String) resobj[2],
                        (Double) resobj[3],
                        (int) resobj[4],
                        (String) resobj[5],
                        (String) resobj[6],
                        (String) resobj[7],
                        (String) resobj[8],
                        (String) resobj[9],
                        (String) resobj[10],
                        (String) resobj[11],
                        (String[]) resobj[12],
                        (String[]) resobj[13],
                        (String[]) resobj[14]));
            }
            return result;
        } catch (Exception ex) {
            System.out.println("Query failed...");
            System.out.println(ex);
            return null;
        }
    }

    public ArrayList<Music_Instrument> Select_Music_Instrument_By_TypeName(String type_name) {
        try {
            PreparedStatement get_music_instruments = con.prepareStatement(get_music_instrument_by_typename);
            PreparedStatement get_type = con.prepareStatement(get_type_by_id);
            PreparedStatement get_model = con.prepareStatement(get_model_by_id);
            PreparedStatement get_where_is_used = con.prepareStatement(get_where_is_used_by_id);
            PreparedStatement get_additional_tool = con.prepareStatement(get_additional_tool_by_id);
            PreparedStatement get_material = con.prepareStatement(get_material_by_id);
            PreparedStatement get_instrument_material = con.prepareStatement(get_material_by_music_instrument_id);

            get_music_instruments.setString(1, type_name);
            ResultSet resultSet = get_music_instruments.executeQuery();
            ArrayList<Music_Instrument> result = new ArrayList<Music_Instrument>();
            while (resultSet.next()) {
                Object[] resobj = new Object[15];
                resobj[0] = resultSet.getInt("music_instrument_id");
                resobj[1] = resultSet.getString("music_instrument_name");
                resobj[2] = resultSet.getString("color");
                resobj[3] = resultSet.getDouble("weight");
                resobj[4] = resultSet.getInt("length");
                get_model.setInt(1, resultSet.getInt("fk_model_id"));
                ResultSet resultModel = get_model.executeQuery();
                if (resultModel.next() == true) {
                    resobj[5] = resultModel.getString("model_name");
                    resobj[9] = resultModel.getString("model_description");
                }
                get_type.setInt(1, resultSet.getInt("fk_type_id"));
                ResultSet resultType = get_type.executeQuery();
                if (resultType.next() == true) {
                    resobj[6] = resultType.getString("type_name");
                    resobj[10] = resultType.getString("type_description");
                }
                get_where_is_used.setInt(1, resultSet.getInt("fk_where_is_used_id"));
                ResultSet resultWhere = get_where_is_used.executeQuery();
                if (resultWhere.next() == true) {
                    resobj[7] = resultWhere.getString("where_is_used_name");
                    resobj[11] = resultWhere.getString("where_is_used_description");
                }
                get_additional_tool.setInt(1, resultSet.getInt("fk_additional_tool_id"));
                ResultSet resultAdditionalTool = get_additional_tool.executeQuery();
                if (resultAdditionalTool.next() == true) {
                    resobj[8] = resultAdditionalTool.getString("additional_tool_name");
                    get_material.setInt(1, resultAdditionalTool.getInt("fk_material_id"));
                    ResultSet resultMaterial = get_material.executeQuery();
                    String[] stringAT_material = new String[2];
                    if (resultMaterial.next() == true) {
                        stringAT_material[0] = resultMaterial.getString("material_name");
                        stringAT_material[1] = resultMaterial.getString("material_description");
                        resobj[14] = stringAT_material;
                    }
                }
                get_instrument_material.setInt(1, resultSet.getInt("music_instrument_id"));
                ResultSet resultMaterial_instrument = get_instrument_material.executeQuery();
                List<String> list_name = new ArrayList<String>();
                List<String> list_description = new ArrayList<String>();
                int material_id;
                while (resultMaterial_instrument.next()) {
                    material_id = resultMaterial_instrument.getInt("material_id");
                    get_material.setInt(1, material_id);
                    ResultSet resultMaterial = get_material.executeQuery();
                    if (resultMaterial.next() == true) {
                        list_name.add(resultMaterial.getString("material_name"));
                        list_description.add(resultMaterial.getString("material_description"));
                    }
                }
                String[] stringName = list_name.toArray(new String[0]);
                String[] stringDescription = list_description.toArray(new String[0]);
                resobj[12] = stringName;
                resobj[13] = stringDescription;

                result.add(new Music_Instrument((int) resobj[0],
                        (String) resobj[1],
                        (String) resobj[2],
                        (Double) resobj[3],
                        (int) resobj[4],
                        (String) resobj[5],
                        (String) resobj[6],
                        (String) resobj[7],
                        (String) resobj[8],
                        (String) resobj[9],
                        (String) resobj[10],
                        (String) resobj[11],
                        (String[]) resobj[12],
                        (String[]) resobj[13],
                        (String[]) resobj[14]));
            }
            return result;
        } catch (Exception ex) {
            System.out.println("Query failed...");
            System.out.println(ex);
            return null;
        }
    }

    @Override
    public ArrayList<Object[]> Select_Music_Instrument_By_Id(int music_instrument_id) {
        try {
            PreparedStatement get_music_instrument = con.prepareStatement(get_music_instrument_by_id);
            PreparedStatement get_type = con.prepareStatement(get_type_by_id);
            PreparedStatement get_model = con.prepareStatement(get_model_by_id);
            PreparedStatement get_where_is_used = con.prepareStatement(get_where_is_used_by_id);
            PreparedStatement get_additional_tool = con.prepareStatement(get_additional_tool_by_id);
            PreparedStatement get_material = con.prepareStatement(get_material_by_id);
            PreparedStatement get_instrument_material = con.prepareStatement(get_material_by_music_instrument_id);

            get_music_instrument.setInt(1, music_instrument_id);
            ResultSet resultSet = get_music_instrument.executeQuery();
            ArrayList<Object[]> result = new ArrayList<Object[]>();
            while (resultSet.next()) {
                Object[] resobj = new Object[15];
                resobj[0] = resultSet.getInt("music_instrument_id");
                resobj[1] = resultSet.getString("music_instrument_name");
                resobj[2] = resultSet.getString("color");
                resobj[3] = resultSet.getFloat("weight");
                resobj[4] = resultSet.getInt("length");
                get_model.setInt(1, resultSet.getInt("fk_model_id"));
                ResultSet resultModel = get_model.executeQuery();
                if (resultModel.next() == true) {
                    resobj[5] = resultModel.getString("model_name");
                    resobj[9] = resultModel.getString("model_description");
                }
                get_type.setInt(1, resultSet.getInt("fk_type_id"));
                ResultSet resultType = get_type.executeQuery();
                if (resultType.next() == true) {
                    resobj[6] = resultType.getString("type_name");
                    resobj[10] = resultType.getString("type_description");
                }
                get_where_is_used.setInt(1, resultSet.getInt("fk_where_is_used_id"));
                ResultSet resultWhere = get_where_is_used.executeQuery();
                if (resultWhere.next() == true) {
                    resobj[7] = resultWhere.getString("where_is_used_name");
                    resobj[11] = resultWhere.getString("where_is_used_description");
                }
                get_additional_tool.setInt(1, resultSet.getInt("fk_additional_tool_id"));
                ResultSet resultAdditionalTool = get_additional_tool.executeQuery();
                if (resultAdditionalTool.next() == true) {
                    resobj[8] = resultAdditionalTool.getString("additional_tool_name");
                    get_material.setInt(1, resultAdditionalTool.getInt("fk_material_id"));
                    ResultSet resultMaterial = get_material.executeQuery();
                    String[] stringAT_material = new String[2];
                    if (resultMaterial.next() == true) {
                        stringAT_material[0] = resultMaterial.getString("material_name");
                        stringAT_material[1] = resultMaterial.getString("material_description");
                        resobj[14] = stringAT_material;
                    }
                }
                get_instrument_material.setInt(1, resultSet.getInt("music_instrument_id"));
                ResultSet resultMaterial_instrument = get_instrument_material.executeQuery();
                List<String> list_name = new ArrayList<String>();
                List<String> list_description = new ArrayList<String>();
                int material_id;
                while (resultMaterial_instrument.next()) {
                    material_id = resultMaterial_instrument.getInt("material_id");
                    get_material.setInt(1, material_id);
                    ResultSet resultMaterial = get_material.executeQuery();
                    if (resultMaterial.next() == true) {
                        list_name.add(resultMaterial.getString("material_name"));
                        list_description.add(resultMaterial.getString("material_description"));
                    }
                }
                String[] stringName = list_name.toArray(new String[0]);
                String[] stringDescription = list_description.toArray(new String[0]);
                resobj[12] = stringName;
                resobj[13] = stringDescription;
                result.add(resobj);
            }
            return result;
        } catch (Exception ex) {
            System.out.println("Query failed...");
            System.out.println(ex);
            return null;
        }
    }

    @Override
    public ArrayList<Object[]> Select_Music_Instruments() {
        try {
            PreparedStatement get_music_instrument_all = con.prepareStatement(get_music_instrument);
            PreparedStatement get_type = con.prepareStatement(get_type_by_id);
            PreparedStatement get_model = con.prepareStatement(get_model_by_id);
            PreparedStatement get_where_is_used = con.prepareStatement(get_where_is_used_by_id);
            PreparedStatement get_additional_tool = con.prepareStatement(get_additional_tool_by_id);
            PreparedStatement get_material = con.prepareStatement(get_material_by_id);
            PreparedStatement get_instrument_material = con.prepareStatement(get_material_by_music_instrument_id);

            ResultSet resultSet = get_music_instrument_all.executeQuery();
            ArrayList<Object[]> result = new ArrayList<Object[]>();
            while (resultSet.next()) {
                Object[] resobj = new Object[15];
                resobj[0] = resultSet.getString("music_instrument_id");
                resobj[1] = resultSet.getString("music_instrument_name");
                resobj[2] = resultSet.getString("color");
                resobj[3] = resultSet.getFloat("weight");
                resobj[4] = resultSet.getInt("length");
                get_model.setString(1, resultSet.getString("fk_model_id"));
                ResultSet resultModel = get_model.executeQuery();
                if (resultModel.next() == true) {
                    resobj[5] = resultModel.getString("model_name");
                    resobj[9] = resultModel.getString("model_description");
                }
                get_type.setString(1, resultSet.getString("fk_type_id"));
                ResultSet resultType = get_type.executeQuery();
                if (resultType.next() == true) {
                    resobj[6] = resultType.getString("type_name");
                    resobj[10] = resultType.getString("type_description");
                }
                get_where_is_used.setString(1, resultSet.getString("fk_where_is_used_id"));
                ResultSet resultWhere = get_where_is_used.executeQuery();
                if (resultWhere.next() == true) {
                    resobj[7] = resultWhere.getString("where_is_used_name");
                    resobj[11] = resultWhere.getString("where_is_used_description");
                }
                get_additional_tool.setString(1, resultSet.getString("fk_additional_tool_id"));
                ResultSet resultAdditionalTool = get_additional_tool.executeQuery();
                if (resultAdditionalTool.next() == true) {
                    resobj[8] = resultAdditionalTool.getString("additional_tool_name");
                    get_material.setString(1, resultAdditionalTool.getString("fk_material_id"));
                    ResultSet resultMaterial = get_material.executeQuery();
                    String[] stringAT_material = new String[2];
                    if (resultMaterial.next() == true) {
                        stringAT_material[0] = resultMaterial.getString("material_name");
                        stringAT_material[1] = resultMaterial.getString("material_description");
                        resobj[14] = stringAT_material;
                    }
                }
                get_instrument_material.setString(1, resultSet.getString("music_instrument_id"));
                ResultSet resultMaterial_intrument = get_instrument_material.executeQuery();
                List<String> list_name = new ArrayList<String>();
                List<String> list_description = new ArrayList<String>();
                String material_id;
                while (resultMaterial_intrument.next()) {
                    material_id = resultMaterial_intrument.getString("material_id");
                    get_material.setString(1, material_id);
                    ResultSet resultMaterial = get_material.executeQuery();
                    if (resultMaterial.next() == true) {
                        list_name.add(resultMaterial.getString("material_name"));
                        list_description.add(resultMaterial.getString("material_description"));
                    }
                }
                String[] stringName = list_name.toArray(new String[0]);
                String[] stringDescription = list_description.toArray(new String[0]);
                resobj[12] = stringName;
                resobj[13] = stringDescription;
                result.add(resobj);
            }
            return result;
        } catch (Exception ex) {
            System.out.println("Query failed...");
            System.out.println(ex);
            return null;
        }
    }

    @Override
    public ArrayList<String> Select_Name_Music_Instrument() {
        try {
            PreparedStatement get_music_instrument_name = con.prepareStatement(get_music_instrument_names);

            ResultSet resultSet = get_music_instrument_name.executeQuery();
            ArrayList<String> result = new ArrayList<String>();
            while (resultSet.next()) {
                result.add(resultSet.getString("music_instrument_name"));
            }
            return result;
        } catch (Exception ex) {
            System.out.println("Query failed...");
            System.out.println(ex);
            return null;
        }
    }

    @Override
    public void Delete_All_Music_Instrument() {

        try {
            PreparedStatement delete_music_instrument_material = con.prepareStatement(delete_all_music_instrument_materials);
            delete_music_instrument_material.executeUpdate();
        } catch (Exception ex) {
            System.out.println("Delete all instrument materials is failed...");
            System.out.println(ex);
        }
        try {
            PreparedStatement delete_music_instrument = con.prepareStatement(delete_all_music_instrument);
            delete_music_instrument.executeUpdate();
        } catch (Exception ex) {
            System.out.println("Delete music instrument is failed...");
            System.out.println(ex);
        }
    }

    @Override
    public String Find_Music_Instrument_By_NameColor(String music_instrument_name, String color) {
        try {
            PreparedStatement get_music_instruments;
            get_music_instruments = con.prepareStatement(get_music_instrument_by_names_color);
            get_music_instruments.setString(1, music_instrument_name);
            get_music_instruments.setString(2, color);
            ResultSet resultSet = get_music_instruments.executeQuery();
            if (resultSet.next()) {
                return resultSet.getString("music_instrument_id");
            } else {
                return "";
            }
        } catch (Exception ex) {
            System.out.println("Query failed...");
            System.out.println(ex);
            return "";
        }
    }

    @Override
    public void Delete_All_Test(int num_table) {
        try {
            PreparedStatement delete_test;
            if (num_table == 1)
                delete_test = con.prepareStatement(delete_all_test);
            else
                delete_test = con.prepareStatement(delete_all_test_index);
            delete_test.executeUpdate();
        } catch (Exception ex) {
            System.out.println("Delete test is failed...");
            System.out.println(ex);
        }
    }

    @Override
    public void Add_Test(String name, String description, int num_table) {
        try {
            PreparedStatement insert_test;
            if (num_table == 1)
                insert_test = con.prepareStatement(add_test);
            else
                insert_test = con.prepareStatement(add_test_index);
            insert_test.setString(1, name);
            insert_test.setString(2, description);
            insert_test.executeUpdate();
        } catch (Exception ex) {

            System.out.println("error add row...");
            System.out.println(ex);
        }
    }

    @Override
    public int Select_Test(String name, String description, int num_table) {
        try {
            PreparedStatement get_test;
            if (num_table == 1)
                get_test = con.prepareStatement(get_test_by_all);
            else
                get_test = con.prepareStatement(get_test_index_by_all);
            get_test.setString(1, name);
            get_test.setString(2, description);
            ResultSet resultSet = get_test.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt("id");
            } else {
                return 0;
            }
        } catch (Exception ex) {
            System.out.println("Query failed...");
            System.out.println(ex);
            return 0;
        }
    }

    @Override
    public void Restore_Music_Instrument(Music_Instrument music_instrument) {
        try {
            PreparedStatement get_music_instrument = con.prepareStatement(get_music_instrument_id_last);
            PreparedStatement get_type = con.prepareStatement(get_type_id);
            PreparedStatement get_model = con.prepareStatement(get_model_id);
            PreparedStatement get_where_is_used = con.prepareStatement(get_where_is_used_id);
            PreparedStatement get_material = con.prepareStatement(get_material_id);

            PreparedStatement get_music_instrument_all = con.prepareStatement(get_music_instrument_by_all);
            PreparedStatement get_model_all = con.prepareStatement(get_model_by_all);
            PreparedStatement get_type_all = con.prepareStatement(get_type_by_all);
            PreparedStatement get_where_is_used_all = con.prepareStatement(get_where_is_used_by_all);
            PreparedStatement get_music_instrument_material_all = con.prepareStatement(get_music_instrument_material_by_all);
            PreparedStatement get_addition_tool = con.prepareStatement(get_additional_tool_by_material_id);
            PreparedStatement get_material_all = con.prepareStatement(get_material_by_all);
            PreparedStatement get_additional_tool_all = con.prepareStatement(get_additional_tool_by_all);

            PreparedStatement restor_music_instrument = con.prepareStatement(restore_music_instrument);
            PreparedStatement insert_type = con.prepareStatement(add_type);
            PreparedStatement insert_model = con.prepareStatement(add_model);
            PreparedStatement insert_where_is_used = con.prepareStatement(add_where_is_used);
            PreparedStatement insert_additional_tool = con.prepareStatement(add_additional_tool);
            PreparedStatement insert_material = con.prepareStatement(add_material);
            PreparedStatement insert_music_instrument_material = con.prepareStatement(add_music_instrument_material);

            String Model_name = music_instrument.getModel_name();
            String Model_description = music_instrument.getModel_description();
            get_model_all.setString(1, Model_name);
            get_model_all.setString(2, Model_description);
            ResultSet models_id = get_model_all.executeQuery();
            int model_id = 0;
            if (models_id.next() == false) {
                insert_model.setString(1, Model_name);
                insert_model.setString(2, Model_description);
                insert_model.executeUpdate();
                ResultSet resultSet = get_model.executeQuery();
                resultSet.next();
                model_id = resultSet.getInt("model_id");
            } else {
                model_id = models_id.getInt("model_id");
            }
            String type_name = music_instrument.getType_name();
            String type_description = music_instrument.getType_description();
            get_type_all.setString(1, type_name);
            get_type_all.setString(2, type_description);

            ResultSet types_id = get_type_all.executeQuery();
            int type_id = 0;
            if (types_id.next() == false) {

                insert_type.setString(1, type_name);
                insert_type.setString(2, type_description);
                insert_type.executeUpdate();

                ResultSet resultSet = get_type.executeQuery();
                resultSet.next();
                type_id = resultSet.getInt("type_id");
            } else {
                type_id = types_id.getInt("type_id");
            }
            String where_is_used_name = music_instrument.getWhere_is_used_name();
            String where_is_used_description = music_instrument.getWhere_is_used_description();
            get_where_is_used_all.setString(1, where_is_used_name);
            get_where_is_used_all.setString(2, where_is_used_description);
            ResultSet where_is_useds_id = get_where_is_used_all.executeQuery();
            int where_is_used_id = 0;
            if (where_is_useds_id.next() == false) {
                insert_where_is_used.setString(1, where_is_used_name);
                insert_where_is_used.setString(2, where_is_used_description);
                insert_where_is_used.executeUpdate();
                ResultSet resultSet = get_where_is_used.executeQuery();
                resultSet.next();
                where_is_used_id = resultSet.getInt("where_is_used_id");
            } else {
                where_is_used_id = where_is_useds_id.getInt("where_is_used_id");
            }
            String[] additional_tool_material = music_instrument.getAdditional_tool_material();
            String additional_tool_material_name = additional_tool_material[0];
            String additional_tool_material_description = additional_tool_material[1];
            get_material_all.setString(1, additional_tool_material_name);
            get_material_all.setString(2, additional_tool_material_description);
            ResultSet additional_tool_materials_id = get_material_all.executeQuery();
            int additional_tool_material_id = 0;
            if (additional_tool_materials_id.next() == false) {
                insert_material.setString(1, additional_tool_material_name);
                insert_material.setString(2, additional_tool_material_description);
                insert_material.executeUpdate();
                ResultSet resultSet = get_material.executeQuery();
                resultSet.next();
                additional_tool_material_id = resultSet.getInt("material_id");
            } else {
                additional_tool_material_id = additional_tool_materials_id.getInt("material_id");
            }
            int fk_material_id = additional_tool_material_id;
            String additional_tool_name = music_instrument.getAdditional_tool_name();
            get_additional_tool_all.setString(1, additional_tool_name);
            get_additional_tool_all.setInt(2, fk_material_id);
            ResultSet additional_tools_id = get_additional_tool_all.executeQuery();
            int additional_tool_id = 0;
            if (additional_tools_id.next() == false) {
                insert_additional_tool.setString(1, additional_tool_name);
                insert_additional_tool.setInt(2, fk_material_id);
                insert_additional_tool.executeUpdate();
                ResultSet resultSet = get_addition_tool.executeQuery();
                resultSet.next();
                additional_tool_id = resultSet.getInt("additional_tool_id");
            } else {
                additional_tool_id = additional_tools_id.getInt("additional_tool_id");
            }

            int fk_model_id = model_id;
            int fk_type_id = type_id;
            int fk_where_is_used_id = where_is_used_id;
            int fk_additional_tool_id = additional_tool_id;

            restor_music_instrument.setInt(1, music_instrument.getId());
            restor_music_instrument.setString(2, music_instrument.getMusic_instrument_name());
            restor_music_instrument.setString(3, music_instrument.getColor());
            restor_music_instrument.setDouble(4, music_instrument.getWeight());
            restor_music_instrument.setInt(5, music_instrument.getLength());
            restor_music_instrument.setInt(6, fk_model_id);
            restor_music_instrument.setInt(7, fk_type_id);
            restor_music_instrument.setInt(8, fk_where_is_used_id);
            restor_music_instrument.setInt(9, fk_additional_tool_id);
            restor_music_instrument.setInt(10, music_instrument.getId());
            restor_music_instrument.executeUpdate();
            ResultSet resultSet = get_music_instrument.executeQuery();
            resultSet.next();
            ArrayList<Object[]> music_instruments_insert = Select_Music_Instrument_By_Id(resultSet.getInt("music_instrument_id"));
            events.notifyObservers("Add", music_instruments_insert);
        } catch (Exception ex) {

            System.out.println("error add row...");
            System.out.println(ex);
        }
    }

    public void register_user(User usr) {
        try {
            PreparedStatement query = con.prepareStatement(reg_usr);
            query.setString(1, usr.getLogin());
            query.setString(2, usr.getPass());
            query.executeUpdate();

        } catch (Exception ex) {
        }
    }

    public Status login_user(User usr) {
        try {
            PreparedStatement query = con.prepareStatement(get_usr);
            query.setString(1, usr.getLogin());
            query.setString(2, usr.getPass());

            ResultSet resultSet = query.executeQuery();
            int counter = 0;
            if (resultSet.next()) {
                //select id, login, password, fk_status
                counter++;
                if (resultSet.getInt("fk_status") == 1) {
                    return Status.USER;
                } else {
                    return Status.ADMIN;
                }
            } else {
                return Status.None;
            }
        } catch (Exception ex) {
            System.out.println("Query failed...");
            System.out.println(ex);
            return Status.None;
        }
    }
}
