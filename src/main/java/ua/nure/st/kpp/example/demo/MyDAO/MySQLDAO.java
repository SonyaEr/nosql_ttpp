package ua.nure.st.kpp.example.demo.MyDAO;

import com.mongodb.MongoClientSettings;
import com.mongodb.ServerAddress;
import com.mongodb.WriteConcern;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.bson.types.ObjectId;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Arrays;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

import static com.mongodb.client.model.Filters.and;
import static com.mongodb.client.model.Filters.eq;


public class MySQLDAO implements IDAO {
    private MongoDatabase database;
    private MongoClient mongoClient;
    private static MySQLDAO instance = null;
    private static Connection con = null;
    private static int counter = 0;
    private static String get_model = "SELECT * FROM music.Model";
    private static String get_type = "SELECT * FROM music.type";
    private static String get_where_is_used = "SELECT * FROM music.where_is_used";
    private static String get_additional_tool = "SELECT * FROM music.additional_tool\n" +
            "LEFT JOIN music.material \n" +
            "ON fk_material_id = material_id";
    private static String get_material = "SELECT * FROM music.material";
    private static String get_music_instrument = "SELECT  * FROM\n" +
            "    music.music_instrument\n" +
            "        LEFT JOIN\n" +
            "    music.model ON fk_model_id = model_id\n" +
            "        LEFT JOIN\n" +
            "    music.type ON fk_type_id = type_id\n" +
            "        LEFT JOIN\n" +
            "    music.where_is_used ON fk_where_is_used_id = where_is_used_id\n" +
            "        LEFT JOIN\n" +
            "    music.additional_tool ON fk_additional_tool_id = additional_tool_id\n" +
            "        LEFT JOIN\n" +
            "    music.material ON fk_material_id = material_id  ";
    private static String get_music_instrument_material = "SELECT * FROM music.music_instrument_material";
    private static String get_music_instrument_material_by_music_instrument_id = "SELECT * FROM music.music_instrument_material AS t_mim\n" +
            "LEFT JOIN music.material AS t_mm\n" +
            "ON t_mm.material_id = t_mim.material_id\n" +
            "WHERE t_mim.music_instrument_id = ?";

    public MySQLDAO() {
        try {
            String url = "jdbc:mysql://localhost:3306/music?serverTimezone=" + TimeZone.getDefault().getID();
            con = DriverManager.getConnection(url, "root", "12345");
            MongoClientSettings settings = MongoClientSettings.builder().
                    applyToClusterSettings(builder -> builder.hosts(Arrays.asList(
                            new ServerAddress("localhost", 27040),
                            new ServerAddress("localhost", 27041),
                            new ServerAddress("localhost", 27042)
                    ))).build();
            mongoClient = MongoClients.create(settings);
            database = mongoClient.getDatabase("music");
        } catch (Exception ex) {
            System.out.println("Connection faliure...");
            System.out.println(ex);
        }
    }

    public static MySQLDAO getInstance() {
        if (instance == null) {
            instance = new MySQLDAO();
        }
        return instance;
    }

    public MySQLDAO(WriteConcern w) {
        try {
            String url = "jdbc:mysql://localhost:3306/music?serverTimezone=" + TimeZone.getDefault().getID();
            con = DriverManager.getConnection(url, "root", "12345");
            MongoClientSettings settings = MongoClientSettings.builder().
                    applyToClusterSettings(builder -> builder.hosts(Arrays.asList(
                            new ServerAddress("localhost", 27040),
                            new ServerAddress("localhost", 27041),
                            new ServerAddress("localhost", 27042)
                    ))).writeConcern(w).build();
            mongoClient = MongoClients.create(settings);
            database = mongoClient.getDatabase("music");
        } catch (Exception ex) {
            System.out.println("Connection faliure...");
            System.out.println(ex);
        }
    }

    public static MySQLDAO getInstance(WriteConcern w) {
        if (instance == null) {
            instance = new MySQLDAO(w);
        }
        return instance;
    }

    public void find_all() {
        MongoCollection coll = database.getCollection("music_instrument");
        long n = coll.countDocuments();
        System.out.println("Найдено " + n + " записей");
    }


    @Override
    public void delete_all() {
        database.getCollection("model").deleteMany(new Document());
        database.getCollection("type").deleteMany(new Document());
        database.getCollection("where_is_used").deleteMany(new Document());
        database.getCollection("material").deleteMany(new Document());
        database.getCollection("additional_tool").deleteMany(new Document());
        database.getCollection("music_instrument").deleteMany(new Document());
        database.getCollection("music_instrument_material").deleteMany(new Document());
    }

    @Override
    public void migrate_data() {
        Model model;
        Type type;
        WhereIsUsed whereIsUsed;
        AdditionalTool additionalTool;
        Material material;
        MusicInstrument musicInstrument;
        MusicInstrumentMaterial musicInstrumentMaterial;
        try {
            MongoCollection todoCollection = database.getCollection("model");
            PreparedStatement get_data = con.prepareStatement(get_model);
            ResultSet resultSet = get_data.executeQuery();
            while (resultSet.next()) {
                model = new Model(resultSet.getString("model_name"),
                        resultSet.getString("model_description"));
                try {
                    Document todoDocument = new Document("model_name", model.getModel_name())
                            .append("model_description", model.getModel_description());
                    todoCollection.insertOne(todoDocument);
                } catch (Exception e) {
                    if (counter < 3) {
                        counter++;
                        TimeUnit.SECONDS.sleep(1);
                        resultSet.previous();
                    } else {
                        System.out.println("Превышено количество попыток записи таблицы model");
                        System.exit(-1);
                    }
                }
                counter = 0;
            }
        } catch (Exception ex) {
            System.out.println("Query failed for table model...");
            System.out.println(ex);
        }

        try {
            MongoCollection todoCollection = database.getCollection("type");
            PreparedStatement get_data = con.prepareStatement(get_type);
            ResultSet resultSet = get_data.executeQuery();
            while (resultSet.next()) {
                type = new Type(resultSet.getString("type_name"),
                        resultSet.getString("type_description"));
                try {
                    Document todoDocument = new Document("type_name", type.getType_name())
                            .append("type_description", type.getType_description());
                    todoCollection.insertOne(todoDocument);
                } catch (Exception e) {
                    if (counter < 3) {
                        counter++;
                        TimeUnit.SECONDS.sleep(1);
                        resultSet.previous();
                    } else {
                        System.out.println("Превышено количество попыток записи таблицы type");
                        System.exit(-1);
                    }
                }
                counter = 0;
            }
        } catch (Exception ex) {
            System.out.println("Query failed while for table type...");
            System.out.println(ex);
        }

        try {
            MongoCollection todoCollection = database.getCollection("where_is_used");
            PreparedStatement get_data = con.prepareStatement(get_where_is_used);
            ResultSet resultSet = get_data.executeQuery();
            while (resultSet.next()) {
                whereIsUsed = new WhereIsUsed(resultSet.getString("where_is_used_name"),
                        resultSet.getString("where_is_used_description"));
                try {
                    Document todoDocument = new Document("where_is_used_name", whereIsUsed.getWhere_is_used_name())
                            .append("where_is_used_description", whereIsUsed.getWhere_is_used_description());
                    todoCollection.insertOne(todoDocument);
                } catch (Exception e) {
                    if (counter < 3) {
                        counter++;
                        TimeUnit.SECONDS.sleep(1);
                        resultSet.previous();
                    } else {
                        System.out.println("Превышено количество попыток записи таблицы where_is_used");
                        System.exit(-1);
                    }
                }
                counter = 0;
            }
        } catch (Exception ex) {
            System.out.println("Query failed while for table where_is_used...");
            System.out.println(ex);
        }

        try {
            MongoCollection todoCollection = database.getCollection("material");
            PreparedStatement get_data = con.prepareStatement(get_material);
            ResultSet resultSet = get_data.executeQuery();
            while (resultSet.next()) {
                material = new Material(resultSet.getString("material_name"),
                        resultSet.getString("material_description"));
                try {
                    Document todoDocument = new Document("material_name", material.getMaterial_name())
                            .append("material_description", material.getMaterial_description());
                    todoCollection.insertOne(todoDocument);
                } catch (Exception e) {
                    if (counter < 3) {
                        counter++;
                        TimeUnit.SECONDS.sleep(1);
                        resultSet.previous();
                    } else {
                        System.out.println("Превышено количество попыток записи таблицы material");
                        System.exit(-1);
                    }
                }
                counter = 0;
            }
            counter = 0;
        } catch (Exception ex) {
            System.out.println("Query failed while for table material...");
            System.out.println(ex);
        }

        try {
            MongoCollection<Document> coll_material = database.getCollection("material");
            MongoCollection todoCollection = database.getCollection("additional_tool");
            PreparedStatement get_data = con.prepareStatement(get_additional_tool);
            ResultSet resultSet = get_data.executeQuery();
            while (resultSet.next()) {
                material = new Material(resultSet.getString("material_name"),
                        resultSet.getString("material_description"));
                additionalTool = new AdditionalTool(resultSet.getString("additional_tool_name"),
                        material);
                Document at_material = coll_material.find(and(eq("material_name", additionalTool.material.material_name),
                        eq("material_description", additionalTool.material.material_description))).first();
                try {
                    Document todoDocument = new Document("additional_tool_name", additionalTool.getAdditional_tool_name())
                            .append("material", at_material.get("_id"));
                    todoCollection.insertOne(todoDocument);
                } catch (Exception e) {
                    if (counter < 3) {
                        counter++;
                        TimeUnit.SECONDS.sleep(1);
                        resultSet.previous();
                    } else {
                        System.out.println("Превышено количество попыток записи таблицы additional_tool_name");
                        System.exit(-1);
                    }
                }
                counter = 0;
            }
        } catch (Exception ex) {
            System.out.println("Query failed while for table additional_tool...");
            System.out.println(ex);
        }

        try {
            MongoCollection<Document> coll_type = database.getCollection("type");
            MongoCollection<Document> coll_model = database.getCollection("model");
            MongoCollection<Document> coll_where_is_used = database.getCollection("where_is_used");
            MongoCollection<Document> coll_material = database.getCollection("material");
            MongoCollection<Document> coll_additional_tool = database.getCollection("additional_tool");
            MongoCollection todoCollection = database.getCollection("music_instrument");
            MongoCollection todoCollection_mim = database.getCollection("music_instrument_material");

            PreparedStatement get_data_material = con.prepareStatement(get_music_instrument_material_by_music_instrument_id);
            PreparedStatement get_data = con.prepareStatement(get_music_instrument);
            ResultSet resultSet = get_data.executeQuery();
            while (resultSet.next()) {
                type = new Type(resultSet.getString("type_name"), resultSet.getString("type_description"));
                model = new Model(resultSet.getString("model_name"), resultSet.getString("model_description"));
                whereIsUsed = new WhereIsUsed(resultSet.getString("where_is_used_name"), resultSet.getString("where_is_used_description"));
                material = new Material(resultSet.getString("material_name"), resultSet.getString("material_description"));
                additionalTool = new AdditionalTool(resultSet.getString("additional_tool_name"), material);
                musicInstrument = new MusicInstrument(resultSet.getString("music_instrument_name"),
                        resultSet.getString("color"),
                        resultSet.getDouble("weight"),
                        resultSet.getInt("length"),
                        model,
                        type,
                        whereIsUsed,
                        additionalTool);
                ObjectId id_material_instrument = null;
                try {
                    Document mi_type = coll_type.find(and(eq("type_name", type.type_name),
                            eq("type_description", type.type_description))).first();
                    Document mi_model = coll_model.find(and(eq("model_name", model.model_name),
                            eq("model_description", model.model_description))).first();
                    Document mi_where_is_used = coll_where_is_used.find(and(eq("where_is_used_name", whereIsUsed.where_is_used_name),
                            eq("where_is_used_description", whereIsUsed.where_is_used_description))).first();
                    Document at_material = coll_material.find(and(eq("material_name", additionalTool.material.material_name),
                            eq("material_description", additionalTool.material.material_description))).first();
                    Document mi_additional_tool = coll_additional_tool.find(and(eq("additional_tool_name", additionalTool.additional_tool_name),
                            eq("material", at_material.getObjectId("_id")))).first();
                    Document todoDocument = new Document("music_instrument_name", musicInstrument.getMusic_instrument_name())
                            .append("color", musicInstrument.getColor())
                            .append("weight", musicInstrument.getWeight())
                            .append("length", musicInstrument.getLength())
                            .append("model", mi_model.getObjectId("_id"))
                            .append("type", mi_type.getObjectId("_id"))
                            .append("where_is_used", mi_where_is_used.getObjectId("_id"))
                            .append("additional_tool", mi_additional_tool.getObjectId("_id"));
                    todoCollection.insertOne(todoDocument);
                    id_material_instrument = todoDocument.getObjectId("_id");
                } catch (Exception e) {
                    if (counter < 3) {
                        counter++;
                        TimeUnit.SECONDS.sleep(1);
                        resultSet.previous();
                    } else {
                        System.out.println("Превышено количество попыток записи таблицы music_instrument");
                        System.exit(-1);
                    }
                }
                counter = 0;
                get_data_material.setInt(1, resultSet.getInt("music_instrument_id"));
                ResultSet resultSet_Material = get_data_material.executeQuery();
                while (resultSet_Material.next()) {
                    material = new Material(resultSet_Material.getString("material_name"), resultSet_Material.getString("material_description"));
                    musicInstrumentMaterial = new MusicInstrumentMaterial(musicInstrument, material);
                    Document mi_material = coll_material.find(and(eq("material_name", musicInstrumentMaterial.material.material_name),
                            eq("material_description", musicInstrumentMaterial.material.material_description))).first();
                    counter = 0;
                    try {
                        Document todoDocument_mim = new Document("music_instrument", id_material_instrument)
                                .append("material", mi_material.getObjectId("_id"));
                        todoCollection_mim.insertOne(todoDocument_mim);
                    } catch (Exception e) {
                        if (counter < 3) {
                            counter++;
                            TimeUnit.SECONDS.sleep(1);
                            resultSet.previous();
                        } else {
                            System.out.println("Превышено количество попыток записи таблицы music_instrument_material");
                            System.exit(-1);
                        }
                    }
                    counter = 0;
                }
            }
        } catch (Exception ex) {
            System.out.println("Query failed while for table music_instrument...");
            System.out.println(ex);
        }
    }
}