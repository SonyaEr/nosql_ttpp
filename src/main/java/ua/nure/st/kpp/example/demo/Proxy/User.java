package ua.nure.st.kpp.example.demo.Proxy;

import ua.nure.st.kpp.example.demo.Music_instrument.Music_Instrument;
import ua.nure.st.kpp.example.demo.MySQL_DAO.MySQLDAO;

import java.util.ArrayList;

public class User {
    private String login;
    private String pass;
    private Status status;
    private MySQLDAO dao;

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public User(String login, String pass) {
        this.login = login;
        this.pass = pass;
        status = Status.None;
        dao = new MySQLDAO();
    }

    public User() {
        status = Status.None;
        dao = new MySQLDAO();
    }

    public void login() {
        status = dao.login_user(this);
    }

    public void register() {
        dao.register_user(this);
    }

    public void changeStatus(Status st) {
        status = st;
    }

    public Music_Instrument Insert_Music_Instrument(Music_Instrument music_instrument) {
        return dao.Insert_Music_Instrument(music_instrument, status);
    }

    public ArrayList<Music_Instrument> Get_Music_Instruments() {
        return dao.Get_Music_Instruments();
    }

    public ArrayList<Music_Instrument> SelectMusicInstrumentsByTypedName(String type_name) {
        return dao.Select_Music_Instrument_By_TypeName(type_name);
    }

    public void Change_Type(String type_name, String type_description, int music_instrument_id) {
        dao.Update_Music_Instrument_Type_By_Name(type_name, type_description, music_instrument_id, status);
    }

    public void Delete_Music_Instrument_By_Id(int id) {
        dao.Delete_Music_Instrument_By_Id(id, status);
    }

    public Music_Instrument Get_Music_Instrument_By_Id(int id) {
        return dao.Get_Music_Instrument_By_Id(id);
    }
}