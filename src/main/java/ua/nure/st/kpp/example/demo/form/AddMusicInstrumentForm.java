package ua.nure.st.kpp.example.demo.form;

public class AddMusicInstrumentForm {

	private String music_instrument_name;
	private String color;
	private double weight;
	private int length;
	private String model_name;
	private String type_name;
	private String where_is_used_name;
	private String additional_tool_name;
	private String model_description;
	private String type_description;
	private String where_is_used_description;
	private String[] material_name;
	private String[] material_description;
	private String[] additional_tool_material;

	public AddMusicInstrumentForm() {
		// all null
	}


	public AddMusicInstrumentForm (String music_instrument_name, String color, double weight, int length, String model_name, String type_name,
							String where_is_used_name, String additional_tool_name,
							String model_description, String type_description, String  where_is_used_description,
							String[] material_name, String[] material_description,
							String[] additional_tool_material) {
		this.music_instrument_name = music_instrument_name;
		this.color = color;
		this.weight = weight;
		this.length = length;
		this.model_name = model_name;
		this.type_name = type_name;
		this.where_is_used_name=where_is_used_name;
		this.additional_tool_name = additional_tool_name;
		this.material_name=material_name;
		this.material_description=material_description;
		this.model_description=model_description;
		this.type_description=type_description;
		this.where_is_used_description=where_is_used_description;
		this.additional_tool_material=additional_tool_material;

		setColor(color);
		setWeight(weight);
		setLength(length);
		setModel_name(model_name);
		setType_name(type_name);
		setWhere_is_used_name(where_is_used_name);
		setAdditional_tool_name(additional_tool_name);
		setMaterial_name(material_name);
		setMaterial_description(material_description);
		setModel_description(model_description);
		setType_description(type_description);
		setWhere_is_used_description(where_is_used_description);
		setAdditional_tool_material(additional_tool_material);
	}

	public String getMusic_instrument_name() {
		return music_instrument_name;
	}

	public void setMusic_instrument_name(String music_instrument_name) {
		this.music_instrument_name = music_instrument_name;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public String getType_name() {
		return type_name;
	}

	public void setType_name(String type_name) {
		this.type_name = type_name;
	}

	public String getModel_name() {
		return model_name;
	}

	public void setModel_name(String model_name) {
		this.model_name = model_name;
	}

	public String getWhere_is_used_name() {
		return where_is_used_name;
	}

	public void setWhere_is_used_name(String where_is_used_name) {
		this.where_is_used_name = where_is_used_name;
	}

	public String getAdditional_tool_name() {
		return additional_tool_name;
	}

	public void setAdditional_tool_name(String additional_tool_name) {
		this.additional_tool_name = additional_tool_name;
	}

	public double getWeight() {
		return weight;
	}
	public void setWeight(double weight) {
		this.weight = weight;
	}

	public int getLength() {
		return length;
	}
	public void setLength(int length) {
		this.length = length;
	}

	public String getType_description() {
		return type_description;
	}

	public void setType_description(String type_description) {
		this.type_description = type_description;
	}

	public String getModel_description() {
		return model_description;
	}

	public void setModel_description(String model_description) {
		this.model_description = model_description;
	}

	public String getWhere_is_used_description() {
		return where_is_used_description;
	}

	public void setWhere_is_used_description(String where_is_used_description) {
		this.where_is_used_description = where_is_used_description;
	}

	public String[] getMaterial_name() {
		return material_name;
	}

	public void setMaterial_name(String[] material_name) {
		this.material_name = material_name;
	}

	public String[] getMaterial_description() {
		return material_description;
	}

	public void setMaterial_description(String[] material_description) {
		this.material_description = material_description;
	}

	public String[] getAdditional_tool_material() {
		return additional_tool_material;
	}

	public void setAdditional_tool_material(String[] additional_tool_material) {
		this.additional_tool_material = additional_tool_material;
	}

}
