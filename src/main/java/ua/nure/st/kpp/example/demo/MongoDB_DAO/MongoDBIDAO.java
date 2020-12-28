package ua.nure.st.kpp.example.demo.MongoDB_DAO;

import ua.nure.st.kpp.example.demo.Music_instrument.Music_Instrument;

import java.util.ArrayList;

public interface MongoDBIDAO {
    Music_Instrument Insert_Music_Instrument(Music_Instrument music_instrument);

    void Delete_Music_Instrument_By_Id(String music_instrument_id);

    void Delete_All_Music_Instrument();

    void Delete_All_Additional_Tool();

    void Update_Type_By_Name(String type_name, String type_description, String music_instrument_id);

    ArrayList<Music_Instrument> Select_Music_Instruments();

    ArrayList<Music_Instrument> Select_Music_Instruments_by_Name_Color(String music_instrument_name, String color);

    String Find_Music_Instrument_By_NameColor(String name, String color);

    void Delete_All_Test(int num_table);

    void Add_Test(String name, String description, int num_table);

    String Select_Test(String name, String description, int num_table);

    int Sum_MusicInstrument_by_Model(String model_name);

    int Sum_MusicInstrument_by_Material(String material_name);

    int Sum_MusicInstrument_by_WhereIsUsed_Color(String where_is_used_name, String color);

    Double AverageWeight_MusicInstrument_by_Type(String type_name);

    int MaxLength_MusicInstrument_by_Model_WhereIsUsed(String model_name, String where_is_used_name);

    int AF_Sum_MusicInstrument_by_Model(String model_name);

    int AF_Sum_MusicInstrument_by_Material(String material_name);

    int AF_Sum_MusicInstrument_by_WhereIsUsed_Color(String where_is_used_name, String color);

    Double AF_AverageWeight_MusicInstrument_by_Type(String type_name);

    int AF_MaxLength_MusicInstrument_by_Model_WhereIsUsed(String model_name, String where_is_used_name);
}
