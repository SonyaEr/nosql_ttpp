package ua.nure.st.kpp.example.demo.Music_instrument;

public class Mediator extends Additional_tool {
    private Float thickness;
    private String shape;
    private int size;

    public float getThickness() {
        return thickness;
    }
    public void setThickness(float thickness) {
        if (thickness >= 0)
        {
            this.thickness = thickness;
        }
        else
        {
            throw new IllegalArgumentException("Bad input");
        }
    }
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

    public Mediator(String additional_tool_name, String material_name, String material_description, float thickness, String shape, int size) {
        super(additional_tool_name,material_name, material_description);
        this.thickness = thickness;
        this.shape = shape;
        this.size = size;

        setThickness(thickness);
        setShape(shape);
        setSize(size);
    }

    public void getDescription() {
        String message = "";
        message += "Информация о дополнительном элементе: " + getAdditional_tool_name() + '\n';
        message += "Материал изготовления" + getMaterial_name() + '\n';
        message += "Толщина" + getThickness() + '\n';
        message += "Форма" + getShape() + '\n';
        message += "Размер(мм)" + getSize() + '\n';
        System.out.print(message);
    }

    @Override
    public String toString() {
        return "Additional_tool: " + getAdditional_tool_name() + ", material: " + getMaterial_name();
    }
}

