package ua.nure.st.kpp.example.demo.Controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
        import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ua.nure.st.kpp.example.demo.Music_instrument.Music_Instrument;
import ua.nure.st.kpp.example.demo.MySQL_DAO.*;
import ua.nure.st.kpp.example.demo.Observer.DAOObserver;
import ua.nure.st.kpp.example.demo.StringMusicInstrumentPair.MusicInstrumentPair;
import ua.nure.st.kpp.example.demo.form.AddMusicInstrumentForm;
import ua.nure.st.kpp.example.demo.form.DeleteMusicInstrumentForm;
import ua.nure.st.kpp.example.demo.form.EditMusicInstrumentTypeForm;
import ua.nure.st.kpp.example.demo.form.SelectMusicInstrumentByNameForm;


@Controller
public class MusicInstrumentsController {
    private MySQLDAO dao = new MySQLDAO();

    MusicInstrumentsController() {
        dao.events.registerObserver(new DAOObserver());
    }
    @RequestMapping(value = { "/", "/music_instruments" }, method = { RequestMethod.GET, RequestMethod.POST })
    public String showAllMusicInstrument(Model model) {
        List<Object[]> list = dao.Select_Music_Instruments();
        model.addAttribute("allMusicInstruments", list);
        return "musicInstrumentsPage";
    }
    @RequestMapping(value = {"/log" }, method = { RequestMethod.GET, RequestMethod.POST })
    public String showLog(Model model) {
        ArrayList<MusicInstrumentPair> list = dao.events.Sumary();
        model.addAttribute("allMusicInstrumentsPair", list);
        return "logPage";
    }

    @RequestMapping(value = { "/addMusicInstrument" }, method = RequestMethod.GET)
    public String showAddMusicInstrumentView(Model model) {
        AddMusicInstrumentForm music_instrument = new AddMusicInstrumentForm();
        music_instrument.setMusic_instrument_name("Балалайка");
        music_instrument.setColor("зеленый");
        music_instrument.setWeight(2.3);
        music_instrument.setLength(2);
        music_instrument.setModel_name("Балалайка");
        music_instrument.setType_name("Балалайка");
        music_instrument.setWhere_is_used_name("оркестр");
        music_instrument.setAdditional_tool_name("руки");
        music_instrument.setModel_description("3 струны");
        music_instrument.setType_description("треугольная");
        music_instrument.setWhere_is_used_description("там");
        /*String [] mn = {"дерево", "металл"};
        music_instrument.setMaterial_name(mn);
        String [] md = {"сосна", "медь"};
        music_instrument.setMaterial_description(md);
        String [] ma = {"пальцы", "толстые"};
        music_instrument.setAdditional_tool_material(ma);

         */
        model.addAttribute("addMusicInstrumentForm", music_instrument);
        return "addMusicInstrument";
    }
    @RequestMapping(value = { "/addMusicInstrument" }, method = RequestMethod.POST)
    public String addMusicInstrument(Model model, AddMusicInstrumentForm music_instrument) {
        dao.Insert_Music_Instrument(new Music_Instrument(0, music_instrument.getMusic_instrument_name(), music_instrument.getColor(), music_instrument.getWeight(), music_instrument.getLength(), music_instrument.getModel_name(), music_instrument.getType_name(), music_instrument.getWhere_is_used_name(), music_instrument.getAdditional_tool_name(), music_instrument.getModel_description(), music_instrument.getType_description(), music_instrument.getWhere_is_used_description(), music_instrument.getMaterial_name(), music_instrument.getMaterial_description(), music_instrument.getAdditional_tool_material()));
        return "redirect:/music_instruments";
    }

    @RequestMapping(value = { "/deleteMusicInstrument" }, method = RequestMethod.GET)
    public String showDeleteMusicInstrumentView(Model model) {
        DeleteMusicInstrumentForm deleteMusicInstrumentForm = new DeleteMusicInstrumentForm();
        model.addAttribute("deleteMusicInstrument", deleteMusicInstrumentForm);
        return "deleteMusicInstrument";
    }
    @RequestMapping(value = { "/deleteMusicInstrument" }, method = RequestMethod.POST)
    public String deleteMusicInstrument(Model model, DeleteMusicInstrumentForm deleteMusicInstrumentForm) {
        dao.Delete_Music_Instrument_By_Id(deleteMusicInstrumentForm.getMusic_instrument_id());
        return "redirect:/music_instruments";
    }

    @RequestMapping(value = { "/musicInstrumentByName" }, method = RequestMethod.GET)
    public String showGetMusicInstrumentByNameView(Model model) {
        SelectMusicInstrumentByNameForm selectMusicInstrumentByNameForm = new SelectMusicInstrumentByNameForm("Гитара");
        List<String> list = dao.Select_Name_Music_Instrument();
        //model.addAttribute("selectMusicInstrumentByName", selectMusicInstrumentByNameForm);
        model.addAttribute("selectMusicInstrumentByName", list);
        model.addAttribute("selected_name", new String());
        return "musicInstrumentByName";
    }
    @RequestMapping(value = { "/musicInstrumentByName" }, method = RequestMethod.POST)
    public String showGetMusicInstrumentByNameView(@RequestParam(value="selected_name") String selected_name, Model model) {
        //String selected_name = "";
        List<Object[]> list = dao.Select_Music_Instrument_By_Name(selected_name);
        model.addAttribute("allMusicInstruments", list);
        return "musicInstrumentsPage";
    }
    @RequestMapping(value = { "/editType" }, method = RequestMethod.GET)
    public String showEditTypeView(Model model) {
        EditMusicInstrumentTypeForm editMusicInstrumentTypeForm = new EditMusicInstrumentTypeForm();
        model.addAttribute("editMusicInstrumentType", editMusicInstrumentTypeForm);
        return "editType";
    }

    @RequestMapping(value = { "/editType" }, method = RequestMethod.POST)
    public String editType(Model model, EditMusicInstrumentTypeForm editMusicInstrumentType) {

        dao.Update_Type_By_Name(editMusicInstrumentType.getType_name(), editMusicInstrumentType.getType_description(), editMusicInstrumentType.getMusic_instrument_id());
        return "redirect:/music_instruments";
    }


}