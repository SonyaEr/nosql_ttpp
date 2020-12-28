package ua.nure.st.kpp.example.demo.MyDAO;

public class WhereIsUsed {
    public String where_is_used_name;
    public String where_is_used_description;

    public WhereIsUsed(String where_is_used_name, String where_is_used_description) {
        this.where_is_used_name = where_is_used_name;
        this.where_is_used_description = where_is_used_description;
    }

    public String getWhere_is_used_name() {
        return where_is_used_name;
    }

    public void setWhere_is_used_name(String where_is_used_name) {
        this.where_is_used_name = where_is_used_name;
    }

    public String getWhere_is_used_description() {
        return where_is_used_description;
    }

    public void setWhere_is_used_description(String where_is_used_description) {
        this.where_is_used_description = where_is_used_description;
    }
}
