package ua.nure.st.kpp.example.demo.Music_instrument;

public class Bow extends Additional_tool {
    private String shape;
    private int size;

    public int getSize() {
        return size;
    }
    public void setSize(int size) {
        if (size >= 0)
        {
            this.size = size;
        }
        else
        {
            throw new IllegalArgumentException("Bad input");
        }
    }
    public String getShape() { return shape; }
    public void setShape(String shape) { this.shape = shape; }

    public Bow(String additional_tool_name, String material_name, String material_description, String shape, int size) {
        super(additional_tool_name,material_name, material_description );
        this.shape = shape;
        this.size = size;

        setShape(shape);
        setSize(size);
    }

    public void getDescription() {
        String message = "";
        message += "Информация о дополнительном элементе: " + getAdditional_tool_name() + '\n';
        message += "Материал изготовления" + getMaterial_name() + '\n';
        message += "Форма" + getShape() + '\n';
        message += "Размер(мм)" + getSize() + '\n';
        System.out.print(message);
    }

    @Override
    public String toString() {
        return "Additional_tool: " + getAdditional_tool_name() + ", material: " + getMaterial_name();
    }
}

