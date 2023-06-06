package scene;

import geometries.Geometries;
import lighting.AmbientLight;
import lighting.LightSource;
import primitives.Color;

import java.util.LinkedList;
import java.util.List;

//================== Scene class (PDS - Plain Data Structure) ==================//
public class Scene {

    //==== use of design pattern called "builder pattern" ====//
    private final String name; // the scene's name
    private final Color background; // the background's color (default-black)
    private final Geometries geometries; // the 3D model
    private AmbientLight ambientLight; //the ambientLight
    private List<LightSource> lights = new LinkedList<LightSource>();

    //constructor that gets only the sceneBuilder name-put the value for the scene
    private Scene(SceneBuilder builder) {
        this.name = builder.name;
        this.background = builder.background;
        this.ambientLight = builder.ambientLight;
        this.geometries = builder.geometries;
    }

    //getter function for the scene
    public String getName() {
        return name;
    }

    public Color getBackground() {
        return background;
    }

    public AmbientLight getAmbientLight() {
        return ambientLight;
    }

    ////////////תוספת לא כתובה
    public Scene setAmbientLight(AmbientLight ambientLight) {
        this.ambientLight = ambientLight;
        return this;
    }

    public Geometries getGeometries() {
        if (geometries == null) return new Geometries();
        return geometries;
    }

    public List<LightSource> getLights() {
        return this.lights;
    }

    public Scene setLight(List<LightSource> lights) {
        this.lights = lights;
        return this;
    }

    //================== SceneBuilder class ==================//:
    public static class SceneBuilder {
        private final String name; // the scene's name
        public Geometries geometries = new Geometries(); // initialize to an empty 3D model
        private Color background = Color.BLACK; //define the background with black color
        private AmbientLight ambientLight = AmbientLight.NONE;//the ambientLight initialize to null

        public SceneBuilder(String name) {
            this.name = name;
        }


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
