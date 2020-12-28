package ua.nure.st.kpp.example.demo.form;

public class DeleteMusicInstrumentForm {
	private int music_instrument_id;

	public int getMusic_instrument_id() {
		return music_instrument_id;
	}
	public void setMusic_instrument_id(int music_instrument_id) {
		this.music_instrument_id = music_instrument_id;
	}

	public DeleteMusicInstrumentForm( int music_instrument_id) {
		this.music_instrument_id = music_instrument_id;
	}

	public DeleteMusicInstrumentForm() {
	}
}
