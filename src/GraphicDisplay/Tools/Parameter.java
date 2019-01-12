/**
 * This class represents parameters of any shape that can be changed.
 */

package GraphicDisplay.Tools;

public class Parameter {
    /** ID of the parameter. Allows to differentiate every parameter for one shape. **/
    public int id;

    /** Current value of the parameter. **/
    public float value;

     /** Value that will be added/subtracted to the current value of the parameter in event of changes demanded. **/
    public double delta;

    /** Name of the parameter. It will be displayed on the button related to this parameter. **/
    public String name;

    public Parameter(int id, float number, double delta, String name){
        this.id = id;
        this.value = number;
        this.delta = delta;
        this.name = name;
    }
}
