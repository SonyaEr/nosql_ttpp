package ua.nure.st.kpp.example.demo.MyDAO;

import com.mongodb.client.*;
import org.bson.Document;
import org.bson.types.ObjectId;
import ua.nure.st.kpp.example.demo.Music_instrument.Music_Instrument;
import ua.nure.st.kpp.example.demo.Observer.MusicInstrumentObservable;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.TimeZone;

import static com.mongodb.client.model.Filters.and;
import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Updates.set;


public class MongoDBDAO implements IDAO {
    private MongoDatabase database;
    private MongoClient mongoClient;
    private static MongoDBDAO instance = null;
    private static Connection con = null;

    private static String insert_music_instrument = "INSERT INTO music.music_instrument(music_instrument_name, color, weight, length, fk_model_id, fk_type_id, fk_where_is_used_id, fk_additional_tool_id) VALUES(?, ?, ?, ?, ?, ?, ?, ?);";
    private static String insert_music_instrument_material = "INSERT INTO music.music_instrument_material (material_id, music_instrument_id) VALUES(?,?)";
    private static String insert_additional_tool = "INSERT INTO music.additional_tool (additional_tool_name, fk_material_id) VALUES(?, ?)";
    private static String insert_model = "INSERT INTO music.Model (model_name, model_description) VALUES(?,?)";
    private static String insert_type = "INSERT INTO music.type (type_name, type_description) VALUES(?,?)";
    private static String insert_where_is_used = "INSERT INTO music.where_is_used (where_is_used_name, where_is_used_description) VALUES(?,?)";
    private static String insert_material = "INSERT INTO music.material (material_name, material_description) VALUES(?,?)";
    private static String select_material = "SELECT material_id FROM music.material WHERE material_name = ? AND material_description = ? LIMIT 1";
    private static String select_type = "SELECT type_id FROM music.type WHERE type_name = ? AND type_description = ? LIMIT 1";
    private static String select_model = "SELECT model_id FROM music.model WHERE model_name = ? AND model_description = ? LIMIT 1";
    private static String select_where_is_used = "SELECT where_is_used_id FROM music.where_is_used WHERE where_is_used_name = ? AND where_is_used_description = ? LIMIT 1";
    private static String select_additional_tool = "SELECT additional_tool_id FROM music.additional_tool WHERE additional_tool_name = ? AND fk_material_id = ? LIMIT 1";
    private static String select_music_instrument_id_last = "SELECT * FROM music.music_instrument order by music_instrument_id DESC LIMIT 1";

    public MongoDBDAO() {
        try {
            String url = "jdbc:mysql://localhost:3306/music?serverTimezone=" + TimeZone.getDefault().getID();
            con = DriverManager.getConnection(url, "root", "12345");
            mongoClient = MongoClients.create();
            database = mongoClient.getDatabase("music");
        } catch (Exception ex) {
            System.out.println("Connection faliure...");
            System.out.println(ex);
        }
    }

    public static MongoDBDAO getInstance() {
        if (instance == null) {
            instance = new MongoDBDAO();
        }
        return instance;
    }

    @Override
    public void delete_all() {
        try {
            con.prepareStatement("DELETE FROM music.music_instrument_material").executeUpdate();
            con.prepareStatement("DELETE FROM music.music_instrument").executeUpdate();
            con.prepareStatement("DELETE FROM music.additional_tool").executeUpdate();
            con.prepareStatement("DELETE FROM music.material").executeUpdate();
            con.prepareStatement("DELETE FROM music.where_is_used").executeUpdate();
            con.prepareStatement("DELETE FROM music.type").executeUpdate();
            con.prepareStatement("DELETE FROM music.model").executeUpdate();
        } catch (Exception ex) {
            System.out.println("Delete data mySQL is failed...");
            System.out.println(ex);
        }
    }

    @Override
    public void migrate_data() {
        try {
            PreparedStatement add_music_instrument = con.prepareStatement(insert_music_instrument);
            PreparedStatement add_type = con.prepareStatement(insert_type);
            PreparedStatement add_model = con.prepareStatement(insert_model);
            PreparedStatement add_where_is_used = con.prepareStatement(insert_where_is_used);
            PreparedStatement add_additional_tool = con.prepareStatement(insert_additional_tool);
            PreparedStatement add_material = con.prepareStatement(insert_material);
            PreparedStatement add_music_instrument_material = con.prepareStatement(insert_music_instrument_material);
            PreparedStatement get_material = con.prepareStatement(select_material);
            PreparedStatement get_type = con.prepareStatement(select_type);
            PreparedStatement get_model = con.prepareStatement(select_model);
            PreparedStatement get_where_is_used = con.prepareStatement(select_where_is_used);
            PreparedStatement get_additional_tool = con.prepareStatement(select_additional_tool);
            PreparedStatement get_music_instrument_id_last = con.prepareStatement(select_music_instrument_id_last);

            MongoCollection coll_type = database.getCollection("type");
            MongoCollection coll_model = database.getCollection("model");
            MongoCollection coll_where_is_used = database.getCollection("where_is_used");
            MongoCollection coll_material = database.getCollection("material");
            MongoCollection coll_additional_tool = database.getCollection("additional_tool");
            MongoCollection coll_music_instrument = database.getCollection("music_instrument");
            MongoCollection coll_music_instrument_material = database.getCollection("music_instrument_material");

            FindIterable<Document> cursor;
            Model model;
            Type type;
            WhereIsUsed whereIsUsed;
            Material material;
            AdditionalTool additionalTool;
            MusicInstrument musicInstrument;
            MusicInstrumentMaterial musicInstrumentMaterial;
            ResultSet resultSet;

            cursor = coll_model.find();
            for (Document i : cursor) {
                model = new Model((String) i.get("model_name"), (String) i.get("model_description"));
                try {
                    add_model.setString(1, model.model_name);
                    add_model.setString(2, model.model_description);
                    add_model.executeUpdate();
                } catch (Exception ex) {
                    System.out.println("Error while inserting model into mySQL...");
                }
            }

            cursor = coll_type.find();
            for (Document i : cursor) {
                type = new Type((String) i.get("type_name"), (String) i.get("type_description"));
                try {
                    add_type.setString(1, type.type_name);
                    add_type.setString(2, type.type_description);
                    add_type.executeUpdate();
                } catch (Exception ex) {
                    System.out.println("Error while inserting type into mySQL...");
                }
            }

            cursor = coll_where_is_used.find();
            for (Document i : cursor) {
                whereIsUsed = new WhereIsUsed((String) i.get("where_is_used_name"), (String) i.get("where_is_used_description"));
                try {
                    add_where_is_used.setString(1, whereIsUsed.where_is_used_name);
                    add_where_is_used.setString(2, whereIsUsed.where_is_used_description);
                    add_where_is_used.executeUpdate();
                } catch (Exception ex) {
                    System.out.println("Error while inserting where_is_used into mySQL...");
                }
            }

            cursor = coll_material.find();
            for (Document i : cursor) {
                material = new Material((String) i.get("material_name"), (String) i.get("material_description"));
                try {
                    add_material.setString(1, material.material_name);
                    add_material.setString(2, material.material_description);
                    add_material.executeUpdate();
                } catch (Exception ex) {
                    System.out.println("Error while inserting material into mySQL...");
                }
            }

            cursor = coll_additional_tool.find();
            for (Document i : cursor) {
                Document at_material = (Document) coll_material.find(eq("_id", i.get("material"))).first();
                material = new Material(at_material.getString("material_name"), at_material.getString("material_description"));
                additionalTool = new AdditionalTool((String) i.get("additional_tool_name"), material);
                try {
                    get_material.setString(1, additionalTool.material.material_name);
                    get_material.setString(2, additionalTool.material.material_description);
                    resultSet = get_material.executeQuery();
                    resultSet.next();
                    int material_id = resultSet.getInt("material_id");

                    add_additional_tool.setString(1, additionalTool.additional_tool_name);
                    add_additional_tool.setInt(2, material_id);
                    add_additional_tool.executeUpdate();
                } catch (Exception ex) {
                    System.out.println("Error while inserting additional_tool into mySQL...");
                }
            }

            cursor = coll_music_instrument.find();
            for (Document i : cursor) {
                Document mi_model = (Document) coll_model.find(eq("_id", i.get("model"))).first();
                Document mi_type = (Document) coll_type.find(eq("_id", i.get("type"))).first();
                Document mi_where_is_used = (Document) coll_where_is_used.find(eq("_id", i.get("where_is_used"))).first();
                Document mi_additional_tool = (Document) coll_additional_tool.find(eq("_id", i.get("additional_tool"))).first();
                Document at_material = (Document) coll_material.find(eq("_id", mi_additional_tool.get("material"))).first();

                model = new Model((String) mi_model.get("model_name"), (String) mi_model.get("model_description"));
                type = new Type((String) mi_type.get("type_name"), (String) mi_type.get("type_description"));
                whereIsUsed = new WhereIsUsed((String) mi_where_is_used.get("where_is_used_name"), (String) mi_where_is_used.get("where_is_used_description"));
                material = new Material(at_material.getString("material_name"), at_material.getString("material_description"));
                additionalTool = new AdditionalTool((String) mi_additional_tool.get("additional_tool_name"), material);
                musicInstrument = new MusicInstrument((String) i.get("music_instrument_name"), (String) i.get("color"),
                        (double) i.get("weight"), (int) i.get("length"), model, type, whereIsUsed, additionalTool);
                try {
                    get_model.setString(1, model.model_name);
                    get_model.setString(2, model.model_description);
                    resultSet = get_model.executeQuery();
                    resultSet.next();
                    int model_id = resultSet.getInt("model_id");

                    get_type.setString(1, type.type_name);
                    get_type.setString(2, type.type_description);
                    resultSet = get_type.executeQuery();
                    resultSet.next();
                    int type_id = resultSet.getInt("type_id");

                    get_where_is_used.setString(1, whereIsUsed.where_is_used_name);
                    get_where_is_used.setString(2, whereIsUsed.where_is_used_description);
                    resultSet = get_where_is_used.executeQuery();
                    resultSet.next();
                    int where_is_used_id = resultSet.getInt("where_is_used_id");

                    get_material.setString(1, additionalTool.material.material_name);
                    get_material.setString(2, additionalTool.material.material_description);
                    resultSet = get_material.executeQuery();
                    resultSet.next();
                    int material_id = resultSet.getInt("material_id");

                    get_additional_tool.setString(1, additionalTool.additional_tool_name);
                    get_additional_tool.setInt(2, material_id);
                    resultSet = get_additional_tool.executeQuery();
                    resultSet.next();
                    int additional_tool_id = resultSet.getInt("additional_tool_id");

                    add_music_instrument.setString(1, musicInstrument.music_instrument_name);
                    add_music_instrument.setString(2, musicInstrument.color);
                    add_music_instrument.setDouble(3, musicInstrument.weight);
                    add_music_instrument.setInt(4, musicInstrument.length);
                    add_music_instrument.setInt(5, model_id);
                    add_music_instrument.setInt(6, type_id);
                    add_music_instrument.setInt(7, where_is_used_id);
                    add_music_instrument.setInt(8, additional_tool_id);
                    add_music_instrument.executeUpdate();
                    resultSet = get_music_instrument_id_last.executeQuery();
                    resultSet.next();
                    int music_instrument_id = resultSet.getInt("music_instrument_id");

                    FindIterable<Document> cursorM = coll_music_instrument_material.find(eq("music_instrument", i.get("_id")));
                    for (Document j : cursorM) {
                        Document mi_material = (Document) coll_material.find(eq("_id", j.get("material"))).first();
                        material = new Material(mi_material.getString("material_name"), mi_material.getString("material_description"));
                        musicInstrumentMaterial = new MusicInstrumentMaterial(musicInstrument, material);

                        get_material.setString(1, musicInstrumentMaterial.material.material_name);
                        get_material.setString(2, musicInstrumentMaterial.material.material_description);
                        resultSet = get_material.executeQuery();
                        resultSet.next();
                        int mi_material_id = resultSet.getInt("material_id");
                        try {
                            add_music_instrument_material.setInt(1, mi_material_id);
                            add_music_instrument_material.setInt(2, music_instrument_id);
                            add_music_instrument_material.executeUpdate();
                        } catch (Exception ex) {
                            System.out.println("Error while inserting music instrument material into mySQL...");
                        }
                    }
                } catch (Exception ex) {
                    System.out.println("Error while inserting music instrument into mySQL...");
                    System.out.println(ex);
                }
            }
        } catch (Exception ex) {
            System.out.println("Error while inserting into mySQL...");
            System.out.println(ex);
        }
    }
}
