package ua.nure.st.kpp.example.demo.MongoDB_DAO;

import com.mongodb.client.*;
import com.mongodb.client.model.Accumulators;
import com.mongodb.client.model.Aggregates;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;
import ua.nure.st.kpp.example.demo.Music_instrument.Music_Instrument;

import static com.mongodb.client.model.Filters.*;
import static com.mongodb.client.model.Projections.*;
import static com.mongodb.client.model.Updates.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class MongoDBDAO implements MongoDBIDAO {
    private static MongoDBDAO instance = null;
    private MongoDatabase database;
    private MongoClient mongoClient;

    public MongoDBDAO() {
        mongoClient = MongoClients.create();
        database = mongoClient.getDatabase("music");
    }

    public static MongoDBDAO getInstance() {
        if (instance == null) {
            instance = new MongoDBDAO();
        }
        return instance;
    }

    @Override
    public Music_Instrument Insert_Music_Instrument(Music_Instrument music_instrument) {
        try {
            MongoCollection Collection_musicinstrument = database.getCollection("music_instrument");
            MongoCollection Collection_model = database.getCollection("model");
            MongoCollection Collection_type = database.getCollection("type");
            MongoCollection Collection_whereisused = database.getCollection("where_is_used");
            MongoCollection Collection_additionaltool = database.getCollection("additional_tool");
            MongoCollection Collection_material = database.getCollection("material");
            MongoCollection Collection_musicinstrumentmaterial = database.getCollection("music_instrument_material");

            MongoCollection<Document> coll_model = database.getCollection("model");
            MongoCollection<Document> coll_type = database.getCollection("type");
            MongoCollection<Document> coll_whereisused = database.getCollection("where_is_used");
            MongoCollection<Document> coll_additionaltool = database.getCollection("additional_tool");
            MongoCollection<Document> coll_material = database.getCollection("material");
            MongoCollection<Document> coll_musicinstrumentmaterial = database.getCollection("music_instrument_material");


            String model_name = music_instrument.getModel_name();
            String type_name = music_instrument.getType_name();
            ;
            String where_is_used_name = music_instrument.getWhere_is_used_name();
            String additional_tool_name = music_instrument.getAdditional_tool_name();
            String model_description = music_instrument.getModel_description();
            String type_description = music_instrument.getType_description();
            String where_is_used_description = music_instrument.getWhere_is_used_description();

            Document model = coll_model.find(and(eq("model_name", model_name), eq("model_description", model_description))).first();
            if (model == null) {
                model = new Document("model_name", model_name).append("model_description", model_description);
                Collection_model.insertOne(model);
            }
            ObjectId id_model = model.getObjectId("_id");

            Document type = coll_type.find(and(eq("type_name", type_name), eq("type_description", type_description))).first();
            if (type == null) {
                type = new Document("type_name", type_name).append("type_description", type_description);
                Collection_type.insertOne(type);
            }
            ObjectId id_type = type.getObjectId("_id");

            Document where_is_used = coll_whereisused.find(and(eq("where_is_used_name", where_is_used_name), eq("where_is_used_description", where_is_used_description))).first();
            if (where_is_used == null) {
                where_is_used = new Document("where_is_used_name", where_is_used_name).append("where_is_used_description", where_is_used_description);
                Collection_whereisused.insertOne(where_is_used);
            }
            ObjectId id_where_is_used = where_is_used.getObjectId("_id");

            String[] additional_tool_material = music_instrument.getAdditional_tool_material();
            String additional_tool_material_name = additional_tool_material[0];
            String additional_tool_material_description = additional_tool_material[1];
            Document at_material = coll_material.find(and(eq("material_name", additional_tool_material_name), eq("material_description", additional_tool_material_description))).first();
            if (at_material == null) {
                at_material = new Document("material_name", additional_tool_material_name).append("material_description", additional_tool_material_description);
                Collection_material.insertOne(at_material);
            }
            ObjectId id_additional_tool_material = at_material.getObjectId("_id");

            Document additional_tool = coll_additionaltool.find(and(eq("additional_tool_name", additional_tool_name), eq("material", id_additional_tool_material))).first();
            if (additional_tool == null) {
                additional_tool = new Document("additional_tool_name", additional_tool_name).append("material", id_additional_tool_material);
                Collection_additionaltool.insertOne(additional_tool);
            }
            ObjectId id_additional_tool = additional_tool.getObjectId("_id");

            Document new_music_instrument = new Document("music_instrument_name", music_instrument.getMusic_instrument_name())
                    .append("color", music_instrument.getColor())
                    .append("weight", music_instrument.getWeight())
                    .append("length", music_instrument.getLength())
                    .append("model", id_model)
                    .append("type", id_type)
                    .append("where_is_used", id_where_is_used)
                    .append("additional_tool", id_additional_tool);
            Collection_musicinstrument.insertOne(new_music_instrument);
            ObjectId id_musicinstrument = new_music_instrument.getObjectId("_id");

            String[] materials_name = music_instrument.getMaterial_name();
            String[] materials_description = music_instrument.getMaterial_description();
            int size = materials_name.length;
            for (int i = 0; i < size; i++) {
                String material_name = materials_name[i];
                String material_description = "";
                try {
                    material_description = materials_description[i];
                } catch (Exception ex) {
                    System.out.println("index for Material_description is out range");
                    System.out.println(ex);
                    break;
                }
                Document mi_material = coll_material.find(and(eq("material_name", material_name), eq("material_description", material_description))).first();
                if (mi_material == null) {
                    mi_material = new Document("material_name", material_name).append("material_description", material_description);
                    Collection_material.insertOne(mi_material);
                }
                ObjectId id_material = mi_material.getObjectId("_id");

                Document instrument_material = coll_musicinstrumentmaterial.find(and(eq("music_instrument", id_musicinstrument), eq("material", id_material))).first();
                if (instrument_material == null) {
                    instrument_material = new Document("music_instrument", id_musicinstrument).append("material", id_material);
                    Collection_musicinstrumentmaterial.insertOne(instrument_material);
                }

            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return music_instrument;
    }

    @Override
    public ArrayList<Music_Instrument> Select_Music_Instruments() {
        MongoCollection coll_musicinstrument = database.getCollection("music_instrument");
        MongoCollection<Document> coll_model = database.getCollection("model");
        MongoCollection<Document> coll_type = database.getCollection("type");
        MongoCollection<Document> coll_whereisused = database.getCollection("where_is_used");
        MongoCollection<Document> coll_additionaltool = database.getCollection("additional_tool");
        MongoCollection<Document> coll_material = database.getCollection("material");
        MongoCollection<Document> coll_instrumentmaterial = database.getCollection("music_instrument_material");

        FindIterable<Document> cursor_musicinstrument = coll_musicinstrument.find();
        ArrayList<Music_Instrument> result = new ArrayList<Music_Instrument>();
        for (Document i : cursor_musicinstrument) {
            String[] additional_tool_material = {"", ""};
            Document additonal_tool = coll_additionaltool.find(eq("_id", new ObjectId((String) i.get("additional_tool")))).first();
            if (additonal_tool != null) {
                Document at_material = coll_material.find(eq("_id", new ObjectId((String) additonal_tool.get("material")))).first();
                if (at_material != null) {
                    String at_material_name = (String) at_material.get("material_name");
                    String at_material_description = (String) at_material.get("material_description");
                    additional_tool_material = new String[]{at_material_name, at_material_description};
                }
            }
            FindIterable<Document> cursor_instrumentmaterial = coll_instrumentmaterial.find(eq("music_instrument", i.get("_id")));
            List<String> list_name = new ArrayList<String>();
            List<String> list_description = new ArrayList<String>();
            for (Document j : cursor_instrumentmaterial) {
                Document materials = coll_material.find(eq("_id", new ObjectId((String) j.get("material")))).first();
                if (materials != null) {
                    list_name.add((String) materials.get("material_name"));
                    list_description.add((String) materials.get("material_description"));
                }
            }
            String[] material_name = list_name.toArray(new String[0]);
            String[] material_description = list_description.toArray(new String[0]);

            result.add(new Music_Instrument(0, (String) i.get("music_instrument_name"),
                            (String) i.get("color"),
                            (double) i.get("weight"),
                            (int) i.get("length"),
                            (String) coll_model.find(eq("_id", new ObjectId((String) i.get("model")))).first().get("model_name"),
                            (String) coll_type.find(eq("_id", new ObjectId((String) i.get("type")))).first().get("type_name"),
                            (String) coll_whereisused.find(eq("_id", new ObjectId((String) i.get("where_is_used")))).first().get("where_is_used_name"),
                            (String) coll_additionaltool.find(eq("_id", new ObjectId((String) i.get("additional_tool")))).first().get("additional_tool_name"),
                            (String) coll_model.find(eq("_id", new ObjectId((String) i.get("model")))).first().get("model_description"),
                            (String) coll_type.find(eq("_id", new ObjectId((String) i.get("type")))).first().get("type_description"),
                            (String) coll_whereisused.find(eq("_id", new ObjectId((String) i.get("where_is_used")))).first().get("where_is_used_description"),
                            (String[]) (material_name),
                            (String[]) (material_description),
                            (String[]) (additional_tool_material)
                    )
            );

        }
        return result;
    }

    @Override
    public ArrayList<Music_Instrument> Select_Music_Instruments_by_Name_Color(String music_instrument_name, String color) {
        MongoCollection coll_musicinstrument = database.getCollection("music_instrument");
        MongoCollection<Document> coll_model = database.getCollection("model");
        MongoCollection<Document> coll_type = database.getCollection("type");
        MongoCollection<Document> coll_whereisused = database.getCollection("where_is_used");
        MongoCollection<Document> coll_additionaltool = database.getCollection("additional_tool");
        MongoCollection<Document> coll_material = database.getCollection("material");
        MongoCollection<Document> coll_instrumentmaterial = database.getCollection("music_instrument_material");

        FindIterable<Document> cursor_musicinstrument = coll_musicinstrument.find(and(eq("music_instrument_name", music_instrument_name),
                eq("color", color)));
        ArrayList<Music_Instrument> result = new ArrayList<Music_Instrument>();
        for (Document i : cursor_musicinstrument) {
            String[] additional_tool_material = {"", ""};
            Document additonal_tool = coll_additionaltool.find(eq("_id", i.get("additional_tool"))).first();
            if (additonal_tool != null) {
                Document at_material = coll_material.find(eq("_id", additonal_tool.get("material"))).first();
                if (at_material != null) {
                    String at_material_name = (String) at_material.get("material_name");
                    String at_material_description = (String) at_material.get("material_description");
                    additional_tool_material = new String[]{at_material_name, at_material_description};
                }
            }
            FindIterable<Document> cursor_instrumentmaterial = coll_instrumentmaterial.find(eq("music_instrument", i.get("_id")));
            List<String> list_name = new ArrayList<String>();
            List<String> list_description = new ArrayList<String>();
            for (Document j : cursor_instrumentmaterial) {
                Document materials = coll_material.find(eq("_id", j.get("material"))).first();
                if (materials != null) {
                    list_name.add((String) materials.get("material_name"));
                    list_description.add((String) materials.get("material_description"));
                }
            }
            String[] material_name = list_name.toArray(new String[0]);
            String[] material_description = list_description.toArray(new String[0]);

            result.add(new Music_Instrument(0, (String) i.get("music_instrument_name"),
                            (String) i.get("color"),
                            (double) i.get("weight"),
                            (int) i.get("length"),
                            (String) coll_model.find(eq("_id", i.get("model"))).first().get("model_name"),
                            (String) coll_type.find(eq("_id", i.get("type"))).first().get("type_name"),
                            (String) coll_whereisused.find(eq("_id", i.get("where_is_used"))).first().get("where_is_used_name"),
                            (String) coll_additionaltool.find(eq("_id", i.get("additional_tool"))).first().get("additional_tool_name"),
                            (String) coll_model.find(eq("_id", i.get("model"))).first().get("model_description"),
                            (String) coll_type.find(eq("_id", i.get("type"))).first().get("type_description"),
                            (String) coll_whereisused.find(eq("_id", i.get("where_is_used"))).first().get("where_is_used_description"),
                            (String[]) (material_name),
                            (String[]) (material_description),
                            (String[]) (additional_tool_material)
                    )
            );

        }
        return result;
    }

    @Override
    public void Update_Type_By_Name(String type_name, String type_description, String music_instrument_id) {
        MongoCollection Collection_type = database.getCollection("type");
        MongoCollection<Document> coll_type = database.getCollection("type");

        Document type = coll_type.find(and(eq("type_name", type_name), eq("type_description", type_description))).first();
        if (type == null) {
            type = new Document("type_name", type_name).append("type_description", type_description);
            Collection_type.insertOne(type);
        }
        ObjectId id_type = type.getObjectId("_id");

        MongoCollection<Document> coll_musicinstrument = database.getCollection("music_instrument");
        coll_musicinstrument.updateOne(eq("_id", new ObjectId(music_instrument_id)), set("type", id_type));
    }

    @Override
    public void Delete_Music_Instrument_By_Id(String music_instrument_id) {
        try {
            MongoCollection<Document> coll_instrumentmaterial = database.getCollection("music_instrument_material");
            coll_instrumentmaterial.deleteMany(eq("music_instrument", music_instrument_id));
        } catch (Exception ex) {
            System.out.println("Delete instrument materials is failed...");
            System.out.println(ex);
            return;
        }
        try {
            MongoCollection<Document> coll_musicinstrument = database.getCollection("music_instrument");
            coll_musicinstrument.deleteMany(eq("_id", new ObjectId(music_instrument_id)));
        } catch (Exception ex) {
            System.out.println("Delete music instrument is failed...");
            System.out.println(ex);
            return;
        }
    }

    @Override
    public void Delete_All_Music_Instrument() {
        try {
            MongoCollection<Document> coll_instrumentmaterial = database.getCollection("music_instrument_material");
            coll_instrumentmaterial.deleteMany(new Document());
        } catch (Exception ex) {
            System.out.println("Delete all instrument materials is failed...");
            System.out.println(ex);
            return;
        }
        try {
            MongoCollection<Document> coll_musicinstrument = database.getCollection("music_instrument");
            coll_musicinstrument.deleteMany(new Document());
        } catch (Exception ex) {
            System.out.println("Delete music instrument is failed...");
            System.out.println(ex);
            return;
        }
    }

    @Override
    public void Delete_All_Additional_Tool() {
        try {
            MongoCollection<Document> coll_additionaltool = database.getCollection("additional_tool");
            coll_additionaltool.deleteMany(new Document());
        } catch (Exception ex) {
            System.out.println("Delete all additional tool is failed...");
            System.out.println(ex);
            return;
        }
    }

    @Override
    public String Find_Music_Instrument_By_NameColor(String music_instrument_name, String color) {
        try {
            MongoCollection<Document> coll;
            coll = database.getCollection("music_instrument");
            Document result = coll.find(and(eq("music_instrument_name", music_instrument_name), eq("color", color))).first();
            if (result == null) {
                return "";
            } else {
                return result.getString("_id");
            }
        } catch (Exception ex) {
            System.out.println("error select row...");
            System.out.println(ex);
            return "";
        }
    }

    @Override
    public void Delete_All_Test(int num_table) {
        try {
            MongoCollection<Document> delete_test;
            if (num_table == 1)
                delete_test = database.getCollection("test");
            else
                delete_test = database.getCollection("test_index");
            delete_test.deleteMany(new Document());
        } catch (Exception ex) {
            System.out.println("Delete all test is failed...");
            System.out.println(ex);
        }
    }

    @Override
    public void Add_Test(String name, String description, int num_table) {
        try {
            MongoCollection insert_test;
            if (num_table == 1)
                insert_test = database.getCollection("test");
            else
                insert_test = database.getCollection("test_index");
            insert_test.insertOne(new Document("name", name).append("description", description));
        } catch (Exception ex) {
            System.out.println("error add row...");
            System.out.println(ex);
        }
    }

    @Override
    public String Select_Test(String name, String description, int num_table) {
        try {
            MongoCollection<Document> coll_test;
            if (num_table == 1)
                coll_test = database.getCollection("test");
            else
                coll_test = database.getCollection("test_index");
            Document test = coll_test.find(and(eq("name", name), eq("description", description))).first();
            if (test == null) {
                return "";
            } else {
                return test.getString("_id");
            }
        } catch (Exception ex) {
            System.out.println("error select row...");
            System.out.println(ex);
            return "";
        }
    }

    @Override
    public int Sum_MusicInstrument_by_Model(String model_name) {
        MongoCollection<Document> coll_model = database.getCollection("model");
        FindIterable<Document> cursor_model = coll_model.find(eq("model_name", model_name));
        ArrayList<ObjectId> objectIds = new ArrayList<ObjectId>();
        for (Document i : cursor_model) {
            objectIds.add((ObjectId) i.get("_id"));
        }
        MongoCollection coll = database.getCollection("music_instrument");
        FindIterable<Document> cursor = coll.find(in("model", objectIds));
        int result = 0;
        for (Document i : cursor) {
            result++;
        }
        return result;
    }

    @Override
    public int Sum_MusicInstrument_by_Material(String material_name) {
        MongoCollection<Document> coll_material = database.getCollection("material");
        FindIterable<Document> cursor_material = coll_material.find(eq("material_name", material_name));
        ArrayList<ObjectId> objectIds = new ArrayList<ObjectId>();
        for (Document i : cursor_material) {
            objectIds.add((ObjectId) i.get("_id"));
        }
        MongoCollection<Document> coll_music_instrument_material = database.getCollection("music_instrument_material");
        FindIterable<Document> cursor_music_instrument_material = coll_music_instrument_material.find(in("material", objectIds));
        ArrayList<ObjectId> objectIds_m = new ArrayList<ObjectId>();
        for (Document i : cursor_music_instrument_material) {
            objectIds_m.add((ObjectId) i.get("music_instrument"));
        }
        MongoCollection coll = database.getCollection("music_instrument");
        FindIterable<Document> cursor = coll.find(in("_id", objectIds_m));
        int result = 0;
        for (Document i : cursor) {
            result++;
        }
        return result;
    }

    @Override
    public int Sum_MusicInstrument_by_WhereIsUsed_Color(String where_is_used_name, String color) {
        MongoCollection<Document> coll_where_is_used = database.getCollection("where_is_used");
        FindIterable<Document> cursor_where_is_used = coll_where_is_used.find(eq("where_is_used_name", where_is_used_name));
        ArrayList<ObjectId> objectIds = new ArrayList<ObjectId>();
        for (Document i : cursor_where_is_used) {
            objectIds.add((ObjectId) i.get("_id"));
        }
        MongoCollection coll = database.getCollection("music_instrument");
        FindIterable<Document> cursor = coll.find(and(in("where_is_used", objectIds), eq("color", color)));
        int result = 0;
        for (Document i : cursor) {
            result++;
        }
        return result;
    }

    @Override
    public Double AverageWeight_MusicInstrument_by_Type(String type_name) {
        MongoCollection<Document> coll_type = database.getCollection("type");
        FindIterable<Document> cursor_type = coll_type.find(eq("type_name", type_name));
        ArrayList<ObjectId> objectIds = new ArrayList<ObjectId>();
        for (Document i : cursor_type) {
            objectIds.add((ObjectId) i.get("_id"));
        }
        MongoCollection coll = database.getCollection("music_instrument");
        FindIterable<Document> cursor = coll.find(in("type", objectIds));
        Double result = 0.0;
        int counter = 0;
        for (Document i : cursor) {
            counter++;
            result += i.getDouble("weight");
        }
        result = Math.round(result / counter * 100.0) / 100.0;
        return result;
    }

    @Override
    public int MaxLength_MusicInstrument_by_Model_WhereIsUsed(String model_name, String where_is_used_name) {
        MongoCollection<Document> coll_model = database.getCollection("model");
        FindIterable<Document> cursor_model = coll_model.find(eq("model_name", model_name));
        ArrayList<ObjectId> objectIds_m = new ArrayList<ObjectId>();
        for (Document i : cursor_model) {
            objectIds_m.add((ObjectId) i.get("_id"));
        }
        MongoCollection<Document> coll_where_is_used = database.getCollection("where_is_used");
        FindIterable<Document> cursor_where_is_used = coll_where_is_used.find(eq("where_is_used_name", where_is_used_name));
        ArrayList<ObjectId> objectIds_w = new ArrayList<ObjectId>();
        for (Document i : cursor_where_is_used) {
            objectIds_w.add((ObjectId) i.get("_id"));
        }
        MongoCollection coll = database.getCollection("music_instrument");
        FindIterable<Document> cursor = coll.find(and(in("model", objectIds_m), in("where_is_used", objectIds_w)));
        int maxLength = -1;
        int length = 0;
        for (Document i : cursor) {
            length = i.getInteger("length");
            if (length > maxLength) {
                maxLength = length;
            }
        }
        return maxLength;
    }

    @Override
    public int AF_Sum_MusicInstrument_by_Model(String model_name) {
        MongoCollection<Document> coll_model = database.getCollection("model");
        FindIterable<Document> cursor_model = coll_model.find(eq("model_name", model_name));
        ArrayList<ObjectId> objectIds = new ArrayList<ObjectId>();
        for (Document i : cursor_model) {
            objectIds.add((ObjectId) i.get("_id"));
        }
        MongoCollection coll = database.getCollection("music_instrument");
        Bson project = Aggregates.project(fields(excludeId(), include("model")));
        Bson match = Aggregates.match(in("model", objectIds));
        Bson group = Aggregates.group("countMusicInstrument", Accumulators.sum("count", 1));
        Document result = (Document) coll.aggregate(Arrays.asList(project, match, group)).first();
        if (result != null) {
            return result.getInteger("count");
        } else {
            return -1;
        }
    }

    @Override
    public int AF_Sum_MusicInstrument_by_Material(String material_name) {
        MongoCollection<Document> coll_material = database.getCollection("material");
        FindIterable<Document> cursor_material = coll_material.find(eq("material_name", material_name));
        ArrayList<ObjectId> objectIds = new ArrayList<ObjectId>();
        for (Document i : cursor_material) {
            objectIds.add((ObjectId) i.get("_id"));
        }
        MongoCollection coll = database.getCollection("music_instrument_material");
        Bson project = Aggregates.project(fields(excludeId(), include("material", "music_instrument")));
        Bson match = Aggregates.match(in("material", objectIds));
        Bson group = Aggregates.group("countMusicInstrument", Accumulators.sum("count", 1));
        Document result = (Document) coll.aggregate(Arrays.asList(project, match, group)).first();
        if (result != null) {
            return result.getInteger("count");
        } else {
            return -1;
        }
    }

    @Override
    public int AF_Sum_MusicInstrument_by_WhereIsUsed_Color(String where_is_used_name, String color) {
        MongoCollection<Document> coll_where_is_used = database.getCollection("where_is_used");
        FindIterable<Document> cursor_where_is_used = coll_where_is_used.find(eq("where_is_used_name", where_is_used_name));
        ArrayList<ObjectId> objectIds = new ArrayList<ObjectId>();
        for (Document i : cursor_where_is_used) {
            objectIds.add((ObjectId) i.get("_id"));
        }
        MongoCollection coll = database.getCollection("music_instrument");
        Bson project = Aggregates.project(fields(excludeId(), include("where_is_used", "color")));
        Bson match = Aggregates.match(and(in("where_is_used", objectIds), eq("color", color)));
        Bson group = Aggregates.group("countMusicInstrument", Accumulators.sum("count", 1));
        Document result = (Document) coll.aggregate(Arrays.asList(project, match, group)).first();
        if (result != null) {
            return result.getInteger("count");
        } else {
            return -1;
        }
    }

    @Override
    public Double AF_AverageWeight_MusicInstrument_by_Type(String type_name) {
        MongoCollection<Document> coll_type = database.getCollection("type");
        FindIterable<Document> cursor_type = coll_type.find(eq("type_name", type_name));
        ArrayList<ObjectId> objectIds = new ArrayList<ObjectId>();
        for (Document i : cursor_type) {
            objectIds.add((ObjectId) i.get("_id"));
        }
        MongoCollection coll = database.getCollection("music_instrument");
        Bson project = Aggregates.project(fields(excludeId(), include("type", "weight")));
        Bson match = Aggregates.match(in("type", objectIds));
        Bson group = Aggregates.group("averageMusicInstrument", Accumulators.avg("average", "$weight"));
        Document result = (Document) coll.aggregate(Arrays.asList(project, match, group)).first();
        if (result != null) {
            return Math.round(result.getDouble("average") * 100.0) / 100.0;
        } else {
            return -1.0;
        }
    }

    @Override
    public int AF_MaxLength_MusicInstrument_by_Model_WhereIsUsed(String model_name, String where_is_used_name) {
        MongoCollection<Document> coll_model = database.getCollection("model");
        FindIterable<Document> cursor_model = coll_model.find(eq("model_name", model_name));
        ArrayList<ObjectId> objectIds_m = new ArrayList<ObjectId>();
        for (Document i : cursor_model) {
            objectIds_m.add((ObjectId) i.get("_id"));
        }
        MongoCollection<Document> coll_where_is_used = database.getCollection("where_is_used");
        FindIterable<Document> cursor_where_is_used = coll_where_is_used.find(eq("where_is_used_name", where_is_used_name));
        ArrayList<ObjectId> objectIds_w = new ArrayList<ObjectId>();
        for (Document i : cursor_where_is_used) {
            objectIds_w.add((ObjectId) i.get("_id"));
        }
        MongoCollection coll = database.getCollection("music_instrument");
        Bson project = Aggregates.project(fields(excludeId(), include("model", "where_is_used", "length")));
        Bson match = Aggregates.match(and(in("model", objectIds_m), in("where_is_used", objectIds_w)));
        Bson group = Aggregates.group("maxMusicInstrument", Accumulators.max("max", "$length"));
        Document result = (Document) coll.aggregate(Arrays.asList(project, match, group)).first();
        if (result != null) {
            return result.getInteger("max");
        } else {
            return -1;
        }
    }

}
