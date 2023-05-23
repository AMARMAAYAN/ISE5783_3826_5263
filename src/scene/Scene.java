package scene;

import lighting.AmbientLight;
import geometries.Geometries;
import primitives.Color;

//================== Scene class (PDS - Plain Data Structure) ==================//
public class Scene {

    //==== use of design pattern called "builder pattern" ====//
    private final String name; // the scene's name
    private final Color background; // the background's color (default-black)
    private final AmbientLight ambientLight; //the ambientLight
    private final Geometries geometries; // the 3D model



    //constructor that gets only the sceneBuilder name-put the value for the scene
    private Scene(SceneBuilder builder) {
        this.name = builder.name;
        this.background = builder.background;
        this.ambientLight = builder.ambientLight;
        this.geometries = builder.geometries;
    }

    //getter function for the scene
    public String getName(){return name;}

    public Color getBackground(){return background;}

    public Geometries getGeometries(){return geometries;}

    public AmbientLight getAmbientLight() {return ambientLight;}


    //================== SceneBuilder class ==================//:
    public static class SceneBuilder{
        private final String name; // the scene's name
        private Color background=Color.BLACK; //define the background with black color
        private AmbientLight ambientLight=AmbientLight.NONE; //the ambientLight initialize to null
        private Geometries geometries =new Geometries(); // initialize to an empty 3D model


       // private Geometries geometries = new Geometries();
      //  private AmbientLight ambientLight=new AmbientLight();

        public SceneBuilder(String name){this.name=name;}


        //========= chaining method =========//
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

    }

}
