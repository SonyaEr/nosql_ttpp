package ua.nure.st.kpp.example.demo.Music_instrument;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.TimeZone;

public class Database_Interaction {
    public static void Insert_Music_Instrument(Music_Instrument music_instrument) {
        try {
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/music?serverTimezone=" + TimeZone.getDefault().getID(), "root", "12345");

            PreparedStatement get_music_instrument = con.prepareStatement("SELECT * FROM music.music_instrument order by music_instrument_id DESC LIMIT 1");
            PreparedStatement get_type = con.prepareStatement("SELECT * FROM music.type order by type_id DESC LIMIT 1");
            PreparedStatement get_model = con.prepareStatement("SELECT * FROM music.Model order by model_id DESC LIMIT 1");
            PreparedStatement get_where_is_used = con.prepareStatement("SELECT * FROM music.where_is_used order by Where_is_used_id DESC LIMIT 1");
            PreparedStatement get_material = con.prepareStatement("SELECT * FROM music.material order by material_id DESC LIMIT 1");
            PreparedStatement get_music_instrument_all = con.prepareStatement("SELECT music_instrument_id FROM music.music_instrument where music_instrument_name =? and color=? and weight=? and length=? and fk_model_id=? and fk_type_id=? and fk_where_is_used_id=? and fk_additional_tool_id=? limit 1;");
            PreparedStatement get_model_all = con.prepareStatement("SELECT model_id FROM music.model where model_name=? and model_description=? limit 1");
            PreparedStatement get_type_all = con.prepareStatement("SELECT type_id FROM music.type where type_name=? and type_description=? limit 1");
            PreparedStatement get_where_is_used_all = con.prepareStatement("SELECT where_is_used_id FROM music.where_is_used where where_is_used_name=? and where_is_used_description=? limit 1");
            PreparedStatement get_music_instrument_material_all = con.prepareStatement("SELECT material_id, music_instrument_id FROM music.music_instrument_material where material_id=? and music_instrument_id =? limit 1");
            PreparedStatement get_addition_tool = con.prepareStatement("SELECT * FROM music.additional_tool order by additional_tool_id DESC LIMIT 1");
            PreparedStatement get_material_all = con.prepareStatement("SELECT material_id FROM music.material where material_name =? and material_description=? limit 1");
            PreparedStatement get_additional_tool_all = con.prepareStatement("SELECT * FROM music.additional_tool where additional_tool_name=? and fk_material_id=? LIMIT 1");

            PreparedStatement insert_music_instrument = con.prepareStatement("INSERT INTO music.music_instrument( music_instrument_name, color, weight, length, fk_model_id, fk_type_id, fk_where_is_used_id,fk_additional_tool_id) VALUES(?, ?, ?, ?, ?, ?, ?, ?);");
            PreparedStatement insert_type = con.prepareStatement("INSERT INTO music.type (type_name, type_description) VALUES(?,?)");
            PreparedStatement insert_model = con.prepareStatement("INSERT INTO music.Model (model_name, model_description) VALUES(?,?)");
            PreparedStatement insert_where_is_used = con.prepareStatement("INSERT INTO music.where_is_used (where_is_used_name, where_is_used_description) VALUES(?,?)");
            PreparedStatement insert_additional_tool = con.prepareStatement("INSERT INTO music.additional_tool (additional_tool_name, fk_material_id) VALUES(?, ?)");
            PreparedStatement insert_material = con.prepareStatement("INSERT INTO music.material (material_name, material_description) VALUES(?,?)");
            PreparedStatement insert_music_instrument_material = con.prepareStatement("INSERT INTO music.music_instrument_material (material_id, music_instrument_id) VALUES(?,?)");

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
            }
            else {
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
            }
            else {
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
            }
            else {
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
            }
        } catch (Exception ex) {

            System.out.println("error add row...");
            System.out.println(ex);
        }
    }

    public static void Delete_Music_Instrument_By_Id(int music_instrument_id) {
        try {
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/music?serverTimezone=" + TimeZone.getDefault().getID(), "root", "12345");
            PreparedStatement get_instrument_material = con.prepareStatement("SELECT * FROM music.music_instrument_material where music_instrument_id =?");
            PreparedStatement delete_music_instrument = con.prepareStatement("DELETE FROM music.music_instrument WHERE music_instrument_id = ?;");
            get_instrument_material.setInt(1, music_instrument_id);
            ResultSet resultMaterial_intrument = get_instrument_material.executeQuery();
            while (resultMaterial_intrument.next()) {
                int material_id = resultMaterial_intrument.getInt("material_id");
                Delete_Material_music_instrument_Id(music_instrument_id, material_id);
            }
            delete_music_instrument.setInt(1, music_instrument_id);
            delete_music_instrument.executeUpdate();
        } catch (Exception ex) {
            System.out.println("Query failed...");
            System.out.println(ex);
        }
    }
    public static void Delete_Material_music_instrument_Id(int music_instrument_id, int material_id) {
        try {
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/music?serverTimezone=" + TimeZone.getDefault().getID(), "root", "12345");
            PreparedStatement delete_material_music_instrument = con.prepareStatement("DELETE FROM music.music_instrument_material WHERE music_instrument_id = ? and material_id = ?");
            delete_material_music_instrument.setInt(1, music_instrument_id);
            delete_material_music_instrument.setInt(2, material_id);
            delete_material_music_instrument.executeUpdate();
        } catch (Exception ex) {
            System.out.println("Query failed...");
            System.out.println(ex);
        }
    }

    public static void Update_Type_By_Name(String type_name, String type_description, int music_instrument_id) {
        try {
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/music?serverTimezone=" + TimeZone.getDefault().getID(), "root", "12345");
            PreparedStatement get_type = con.prepareStatement("SELECT * FROM music.type order by type_id DESC LIMIT 1");
            PreparedStatement get_type_all = con.prepareStatement("SELECT type_id FROM music.type where type_name=? and type_description=? limit 1");
            get_type_all.setString(1, type_name);
            get_type_all.setString(2, type_description);
            ResultSet types_id = get_type_all.executeQuery();
            int type_id = 0;
            if (types_id.next() == false) {
                PreparedStatement update_type = con.prepareStatement("UPDATE music.type SET type_name=?, type_description=? WHERE type_id IN (select fk_type_id from music.music_instrument where music_instrument_id = ?)  limit 1;");
                update_type.setString(1, type_name);
                update_type.setString(2, type_description);
                update_type.setInt(3, music_instrument_id);
                update_type.executeUpdate();
                ResultSet resultSet = get_type.executeQuery();
                resultSet.next();
                type_id = resultSet.getInt("type_id");
            }
            else {
                type_id = types_id.getInt("type_id");
            }

        } catch (Exception ex) {
            System.out.println("Query failed...");
            System.out.println(ex);
        }
    }
    public static void Insert_Additional_Tool(Additional_tool additional_tool) {
        try {
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/music?serverTimezone=" + TimeZone.getDefault().getID(), "root", "12345");
            PreparedStatement insert_additional_tool = con.prepareStatement("INSERT INTO music.additional_tool (additional_tool_name, fk_material_id) VALUES(?, ?)");
            PreparedStatement insert_material = con.prepareStatement("INSERT INTO music.material (material_name, material_description) VALUES(?,?)");
            PreparedStatement get_addition_tool = con.prepareStatement("SELECT * FROM music.additional_tool order by additional_tool_id DESC LIMIT 1");
            PreparedStatement get_material = con.prepareStatement("SELECT * FROM music.material order by material_id DESC LIMIT 1");
            PreparedStatement get_material_all = con.prepareStatement("SELECT material_id FROM music.material where material_name =? and material_description=? limit 1");
            PreparedStatement get_additional_tool_all = con.prepareStatement("SELECT * FROM music.additional_tool where additional_tool_name=? and fk_material_id=? LIMIT 1");

            String material_name = additional_tool.getMaterial_name();
            String material_description = additional_tool.getMaterial_description();
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
            int fk_material_id = material_id;
            String additional_tool_name = additional_tool.getAdditional_tool_name();
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
            }
        } catch (Exception ex) {
            System.out.println("error...");
            System.out.println(ex);
        }
    }

    public static ArrayList <Object []> Select_Music_Instrument_By_Name(String music_instrument_name) {
        try {
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/music?serverTimezone=" + TimeZone.getDefault().getID(), "root", "12345");
            PreparedStatement get_music_instrument = con.prepareStatement("SELECT * FROM music.music_instrument where music_instrument_name = ?;");
            PreparedStatement get_type = con.prepareStatement("SELECT * FROM music.type where type_id=? limit 1");
            PreparedStatement get_model = con.prepareStatement("SELECT * FROM music.model where model_id=? limit 1;");
            PreparedStatement get_where_is_used = con.prepareStatement("SELECT * FROM music.where_is_used where where_is_used_id=? limit 1");
            PreparedStatement get_additional_tool = con.prepareStatement("SELECT * FROM music.additional_tool where additional_tool_id=? LIMIT 1");
            PreparedStatement get_material = con.prepareStatement("SELECT * FROM music.material where material_id =? limit 1");
            PreparedStatement get_instrument_material = con.prepareStatement("SELECT * FROM music.music_instrument_material where music_instrument_id =?");

            get_music_instrument.setString(1, music_instrument_name);
            ResultSet resultSet = get_music_instrument.executeQuery();
            ArrayList <Object []> result = new ArrayList<Object []>();
            while (resultSet.next()) {
                Object [] resobj = new Object[15];
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
                String[] stringDescription  = list_description.toArray(new String[0]);
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
}
