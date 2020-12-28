package ua.nure.st.kpp.example.demo.MySQL_DAO;

import ua.nure.st.kpp.example.demo.Music_instrument.Music_Instrument;
import ua.nure.st.kpp.example.demo.Proxy.Status;

import java.util.ArrayList;


public interface MySQLIDAO {
	void Insert_Music_Instrument(Music_Instrument music_instrument);
	Music_Instrument Insert_Music_Instrument(Music_Instrument music_instrument, Status st);
	void Delete_Music_Instrument_By_Id(int music_instrument_id);
	void Delete_Music_Instrument_By_Id(int music_instrument_id, Status st);
	void Update_Type_By_Name(String type_name, String type_description, int music_instrument_id);
	void Update_Music_Instrument_Type_By_Name(String type_name, String type_description, int music_instrument_id, Status st);
	ArrayList <Object []> Select_Music_Instrument_By_Name(String music_instrument_name);
	ArrayList <Object []> Select_Music_Instruments();
	ArrayList <Object []> Select_Music_Instrument_By_Id(int music_instrument_id);
	ArrayList <String> Select_Name_Music_Instrument();
	void Delete_All_Music_Instrument();
	String Find_Music_Instrument_By_NameColor(String name, String color);
	void Delete_All_Test(int num_table);
	void Add_Test(String name, String description, int num_table);
	int Select_Test(String name, String description, int num_table);
	Music_Instrument Get_Music_Instrument_By_Id(int music_instrument_id);
	void Restore_Music_Instrument(Music_Instrument music_instrument);
}
