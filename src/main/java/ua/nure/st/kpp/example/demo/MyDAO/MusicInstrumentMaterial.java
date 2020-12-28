package ua.nure.st.kpp.example.demo.MyDAO;

import org.bson.types.ObjectId;

public class MusicInstrumentMaterial {
    public MusicInstrument music_instrument;
    public Material material;

    public MusicInstrumentMaterial(MusicInstrument music_instrument, Material material) {
        this.music_instrument = music_instrument;
        this.material = material;
    }

    public MusicInstrument getMusic_instrument() {
        return music_instrument;
    }

    public void setMusic_instrument(MusicInstrument music_instrument) {
        this.music_instrument = music_instrument;
    }

    public Material getMaterial() {
        return material;
    }

    public void setMaterial(Material material) {
        this.material = material;
    }
}
