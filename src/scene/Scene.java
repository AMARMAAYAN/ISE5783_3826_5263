package scene;
import geometries.Geometries;
import lighting.AmbientLight;
import primitives.*;
import java.awt.Color;
import java.util.List;

public class Scene {
    private final String name;
    private final Color background;
    private final Geometries geometries;
    private AmbientLight ambientLight;


    //constructor that gets only the sceneBuilder name-put the value for the scene
    private Scene(SceneBuilder builder){
        name=builder.name;
        background=builder.background;
        geometries=builder.geometries;
        ambientLight=builder.ambientLight;
    }

    //getter function for the scene
    public String getName(){return name;}

    public Color getBackground(){return background;}

    public Geometries getGeometries(){return geometries;}

    public AmbientLight getAmbientLight() {return ambientLight;}

    public Scene setAmbientLight(AmbientLight ambientLight){
        this.ambientLight=ambientLight;
        return this;
    }


    //create the builder class for the scene:
    public static class SceneBuilder{
        private String name;

        //define the background with black color
        private Color background=Color.BLACK;
        private Geometries geometries = new Geometries();
        private AmbientLight ambientLight=new AmbientLight();

        public SceneBuilder(String name){this.name=name;}


        //setter methods - return this
        public SceneBuilder setBackground(Color background) {
            this.background = background;
            return this;
        }

        public SceneBuilder setAmbientLight(AmbientLight ambientLight) {
            this.ambientLight = ambientLight;
            return this;
        }

        public SceneBuilder setGeometries(Geometries geometries) {
            this.geometries = geometries;
            return this;
        }


        //return the scene from the builder
        public Scene build() {
            return new Scene(this);
        }

        //validate
        public void validateObject(Scene scene){
            //do nothing
        }

        //for the file
        public SceneBuilder readXmlFile(String fileName){
            return  this;
        }
    }








}
