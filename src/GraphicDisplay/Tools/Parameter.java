package GraphicDisplay.Tools;

public class Parameter {

    public int id;
    public float value;
    public double delta;
    public String name;

    public Parameter(int id, float number, double delta, String name){
        this.id = id;
        this.value = number;
        this.delta = delta;
        this.name = name;
    }
}
