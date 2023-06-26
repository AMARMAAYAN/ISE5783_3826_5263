package finalImage;
import org.junit.jupiter.api.Test;

import static java.awt.Color.*;

import lighting.*;
import geometries.*;
import primitives.*;
import renderer.Camera;
import renderer.ImageWriter;
import renderer.RayTracerBasic;
import scene.Scene;
import scene.Scene.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class TryMP1 {
    private Scene scene = new SceneBuilder("Test scene").build();

    //length till end of lanes
    int lengthToX = 10000;
    //first x for the bowling pins-
    int firstX = 5000;
    int distanceBetweenPins = 70;

    @Test
    public void picture() {
        // There are two versions of the photo- the normal one, and another one taken from the top to show another angle of the created scene, for ease of working with it
        Camera camera = new Camera(new Point(-1000, 0, 500), new Vector(1, 0, -0.05), new Vector(0.05, 0, 1)) //
                .setVPSize(200, 150).setVPDistance(Math.sqrt(500000));
        Camera cameraTop = new Camera(new Point(5000, 0, 580), new Vector(0, 0, -1), new Vector(1, 0, 0)) //
                .setVPSize(200, 150).setVPDistance(100);

        //created three lanes
       createLanes();
        //creating the bowling pins
        // createBowlingPins(firstX, 0);

       // createBowlingPins(firstX, -240);

        //createFallingBowlingPins(firstX, 240);
        //creating walls for the room- including a window on the left side, with a directional light which you can see hitting the left lane
        //this is light number one
        createWalls();
        //creating the black boxes to symbol the end of lane, to make the image more realistic
        createBoxForEndOfLane();

        //addPin(new Point(10 + 3 * Math.sqrt(27), 0, -9));

        addPin(new Point(2250, -150, 3));


        //light number 2- this light is located in front of the middle bowling pins, and you can see it on the back wall-
        // it is green, and you can see the shape of the bowling pins shaded on the back wall.
        SpotLight lightFixture2 = new SpotLight(new Color(102, 255, 51), new Point(4670, 20, 7), new Vector(1, 0, 0.02));
        scene.getLights().add(lightFixture2);
        //creates the bal holder on the left of the image- If you zoom in on each ball you can see the whole room reflected
        //  createBallHolder(2500, -150, 3);

        //creates light number 3- the spotlight located on the top of the room, pointing downwards
        //createLightFixture(new Point(3500, 0, 550));

        //creates the texture on top of the end of lane box- to make the image look nicer
        //createTexture(new Ray(new Point(6999, -330, 200), new Vector(0, 1, 1)));

        camera.setImageWriter(new ImageWriter("final picture", 2000, 2000)) //
                .setRayTracer(new RayTracerBasic(scene)) //
                .renderImage()
//                .renderImageMultiThreadingASS() //
                .writeToImage();

//        cameraTop.setImageWriter(new ImageWriter("ourPictureMP2UpBVH", 2000, 2000)) //
//                .setRayTracer(new RayTracerBasic(_scene)) //
//                .renderImageMultiThreadingASS() //
//                .writeToImage();

    }

    private void createWalls() {
        Material wallMat = new Material().setKd(0.4).setKr(0.2).setShininess(10);
        Color wallColor = new Color(136, 78, 160  ).scale(1.8);
        //create wall with space for window
        int closeX = 4000;
        int farX = 5500;
        int bottomZ = 300;
        int topZ = 450;
        Point topFarPoint = new Point(farX, 340, topZ);
        Point bottomFarPoint = new Point(farX, 340, bottomZ);
        Point topClosePoint = new Point(closeX, 340, topZ);
        Point bottomClosePoint = new Point(closeX, 340, bottomZ);
        Polygon leftWall1 = (Polygon) new Polygon(new Point(-500, 340, 1500), new Point(closeX, 340, 1500), new Point(closeX, 340, 0), new Point(-500, 340, 0)).setEmission(wallColor)
                .setMaterial(wallMat);
        Polygon leftWall2 = (Polygon) new Polygon(new Point(farX, 340, 1500), new Point(lengthToX, 340, 1500), new Point(lengthToX, 340, 0), new Point(farX, 340, 0)).setEmission(wallColor)
                .setMaterial(wallMat);
        Polygon leftWall3 = (Polygon) new Polygon(new Point(closeX, 340, 1500), new Point(farX, 340, 1500), new Point(farX, 340, topZ), new Point(closeX, 340, topZ)).setEmission(wallColor)
                .setMaterial(wallMat.setKd(0.25).setKr(0.1));
        Polygon leftWall4 = (Polygon) new Polygon(new Point(closeX, 340, bottomZ), new Point(farX, 340, bottomZ), new Point(farX, 340, 0), new Point(closeX, 340, 0)).setEmission(wallColor)
                .setMaterial(wallMat);
        Geometry verticalStick = new Polygon(new Point((farX + closeX) / 2d - 20, 339, topZ), new Point((farX + closeX) / 2d + 20, 339, topZ), new Point((farX + closeX) / 2d + 20, 339, bottomZ), new Point((farX + closeX) / 2d - 20, 339, bottomZ))
                .setEmission(new Color(LIGHT_GRAY));
        Geometry horizontalStick = new Polygon(new Point(closeX, 339, (topZ + bottomZ) / 2d - 5), new Point(farX, 339, (topZ + bottomZ) / 2d - 5), new Point(farX, 339, (topZ + bottomZ) / 2d + 5), new Point(closeX, 339, (topZ + bottomZ) / 2d + 5))
                .setEmission(new Color(LIGHT_GRAY));
        scene.getGeometries().add(leftWall1, leftWall2, leftWall3, leftWall4, verticalStick, horizontalStick);
        //creating window
//        Geometry window = new Polygon(topFarPoint, topClosePoint, bottomClosePoint, bottomFarPoint)
//                .setMaterial(new Material().setKt(0.7).setKd(0.3).setShininess(1)).setEmission(new Color(230, 230, 255));
//        scene.getGeometries().add(window);
//        scene.getLights().add(new DirectionalLight(new Color(255, 230, 128), new Vector(0, -1, -0.7)));

        Polygon rightWall = (Polygon) new Polygon(new Point(-500, -340, 1500), new Point(lengthToX, -340, 1500), new Point(lengthToX, -340, 0), new Point(-500, -340, 0)).setEmission(wallColor)
                .setMaterial(wallMat);
        Polygon backWall = (Polygon) new Polygon(new Point(7000, 340, 1500), new Point(7000, -340, 1500), new Point(7000, -340, 0), new Point(7000, 340, 0)).setEmission(wallColor)
                .setMaterial(wallMat);
        Geometry roof = new Polygon(new Point(7000, 340, 600), new Point(7000, -340, 600), new Point(-500, -340, 600), new Point(-600, 340, 600))
                .setEmission(new Color(108, 52, 131)).setMaterial(new Material().setKd(0.9).setShininess(19));
        scene.getGeometries().add(rightWall, backWall, roof);
    }

    private void createBoxForEndOfLane() {
        int boxHeight = 200;
       Geometry leftBox = new Polygon(new Point(6000, 335, boxHeight), new Point(lengthToX, 335, boxHeight), new Point(lengthToX, 335, 0), new Point(6000, 335, 0)).setEmission(new Color(BLACK))
                .setMaterial(new Material().setKs(0.8).setShininess(19));
        Geometry rightBox = new Polygon(new Point(6000, -335, boxHeight), new Point(lengthToX, -335, boxHeight), new Point(lengthToX, -335, 0), new Point(6000, -335, 0)).setEmission(new Color(BLACK))                .setMaterial(new Material().setKs(0.8).setShininess(19));
       Geometry backMirror= new Polygon(new Point(6995, -335, boxHeight), new Point(6990, -335, 0), new Point(6990, -120, 0), new Point(6995, -120, boxHeight))
                .setMaterial(new Material().setKd(0.1).setKr(0.3).setShininess(19)).setEmission(new Color(0, 122, 204));


        Geometry secondBox = new Polygon(new Point(6000, -120, boxHeight), new Point(lengthToX, -120, boxHeight), new Point(lengthToX, -120, 0), new Point(6000, -120, 0)).setEmission(new Color(BLACK))
               .setMaterial(new Material().setKs(0.8).setShininess(19));
        Geometry thirdBox = new Polygon(new Point(6000, 120, boxHeight), new Point(lengthToX, 120, boxHeight), new Point(lengthToX, 120, 0), new Point(6000, 120, 0)).setEmission(new Color(BLACK))
                .setMaterial(new Material().setKs(0.8).setShininess(19));
        Geometry topBox = new Polygon(new Point(6000, -335, boxHeight), new Point(lengthToX, -335, boxHeight), new Point(lengthToX, 335, boxHeight), new Point(6000, 335, boxHeight)).setEmission(new Color(BLACK))
                .setMaterial(new Material().setKs(0.8).setShininess(19));
        Geometry backBox = new Polygon(new Point(6995, 340, boxHeight), new Point(6995, -340, boxHeight), new Point(6995, -340, 0), new Point(6995, 340, 0)).setEmission(new Color(89, 89, 89))
                .setMaterial(new Material().setKs(0.8).setShininess(19));
        scene.getGeometries().add(leftBox, rightBox, topBox, backBox, secondBox, thirdBox,backMirror);
    }


    public void addPin(Point p)
    {
        Material pinBody = new Material().setKt(0.2).setShininess(30).setKs(0.1).setKd(0.7);
        scene.getGeometries().add(
                new Sphere(new Point(p.getX(), 1.1, p.getZ()), 6).setEmission(new Color(169, 204, 227  )).setMaterial(pinBody),
                new Cylinder(2.2 ,new Ray(new Point( p.getX(), 0.6, p.getZ()), new Vector(0, 1, 0)), 6).setEmission(new Color(169, 204, 227  )).setMaterial(pinBody),
               new Sphere(new Point(p.getX(), 3.1, p.getZ()), 6).setEmission(new Color(WHITE)),
                new Cylinder(3, new Ray(new Point(p.getX(), 4.6, p.getZ()), new Vector(0, 1, 0)), 5).setEmission(new Color(RED)),
                new Cylinder(0.6 , new Ray(new Point(p.getX(), 5.3, p.getZ()), new Vector(0, 1, 0)), 1.01).setEmission(new Color(WHITE)),
                new Sphere(new Point(p.getX(), 7.3, p.getZ()), 5.25).setEmission(new Color(WHITE)),
               new Cylinder(1, new Ray(new Point(p.getX(), 7.6, p.getZ()), new Vector(0, 1, 0)), 5.25 ).setEmission(new Color(WHITE)),
                new Sphere(new Point(p.getX(), 8.3, p.getZ()), 5.25).setEmission(new Color(WHITE))
        );

    }


    private void createLanes() {
        Material laneMaterial = new Material().setKs(0.7).setKd(0.1).setKr(0.05).setShininess(19);
        Color laneColor = new Color(255, 223, 128).reduce(9d / 5d);

        Polygon lane = (Polygon) new Polygon(new Point(-500, 100, 0), new Point(lengthToX, 100, 0), new Point(lengthToX, -100, 0), new Point(-500, -100, 0)).setEmission(laneColor)
                .setMaterial(laneMaterial);
        Polygon laneRight = (Polygon) new Polygon(new Point(-500, -140, 0), new Point(lengthToX, -140, 0), new Point(lengthToX, -340, 0), new Point(-500, -340, 0)).setEmission(laneColor)
                .setMaterial(laneMaterial);
        Polygon laneLeft = (Polygon) new Polygon(new Point(-500, 140, 0), new Point(lengthToX, 140, 0), new Point(lengthToX, 340, 0), new Point(-500, 340, 0)).setEmission(laneColor)
                .setMaterial(laneMaterial);
        Polygon leftPolR = (Polygon) new Polygon(new Point(-500, 100, 0), new Point(lengthToX, 100, 0), new Point(lengthToX, 110, -20), new Point(-500, 110, -20)).setEmission(new Color(GRAY))
                .setMaterial(new Material().setKs(1).setKd(0.3).setKr(0.3).setShininess(19));
        Polygon leftPolM = (Polygon) new Polygon(new Point(-500, 110, -20), new Point(lengthToX, 110, -20), new Point(lengthToX, 130, -20), new Point(-500, 130, -20)).setEmission(new Color(GRAY))
                .setMaterial(new Material().setKs(1).setKd(0.3).setKr(0.3).setShininess(19));
        Tube leftTube = (Tube) new Tube(new Ray(new Point(-500, 120, -15), new Vector(1, 0, 0)), 5)
                .setMaterial(new Material().setKs(1).setKd(0.6).setKr(0.4));
        Polygon leftPolL = (Polygon) new Polygon(new Point(-500, 140, 0), new Point(lengthToX, 140, 0), new Point(lengthToX, 130, -20), new Point(-500, 130, -20)).setEmission(new Color(GRAY))
                .setMaterial(new Material().setKs(1).setKd(0.3).setKr(0.3).setShininess(19));
        Polygon rightPolR = (Polygon) new Polygon(new Point(-500, -140, 0), new Point(lengthToX, -140, 0), new Point(lengthToX, -130, -20), new Point(-500, -130, -20)).setEmission(new Color(GRAY))
                .setMaterial(new Material().setKs(1).setKd(0.3).setKr(0.3).setShininess(19));
        Polygon rightPolM = (Polygon) new Polygon(new Point(-500, -110, -20), new Point(lengthToX, -110, -20), new Point(lengthToX, -130, -20), new Point(-500, -130, -20)).setEmission(new Color(GRAY))
                .setMaterial(new Material().setKs(1).setKd(0.3).setKr(0.3).setShininess(19));
        Tube rightTube = (Tube) new Tube(new Ray(new Point(-500, -120, -15), new Vector(1, 0, 0)), 5)
                .setMaterial(new Material().setKs(1).setKd(0.6).setKr(0.4));
        Polygon rightPolL = (Polygon) new Polygon(new Point(-500, -100, 0), new Point(lengthToX, -100, 0), new Point(lengthToX, -110, -20), new Point(-500, -110, -20)).setEmission(new Color(GRAY))
                .setMaterial(new Material().setKs(1).setKd(0.3).setKr(0.3).setShininess(19));
        scene.getGeometries().add(lane, laneLeft, laneRight);
        scene.getGeometries().add(rightPolL, rightPolR, leftPolL, leftPolR, rightPolM, leftPolM);
        scene.getGeometries().add(rightTube, leftTube);

    }
    public static class BowlingPin  {

        private final Geometries _spheres;

        /**
         * creates a bowling pin using many spheres
         *
         * @param ray      direction of bowling pin
         * @param material material of bowling pin
         * @param all      color of the pin
         * @param special  special color for line on pin
         */
        public BowlingPin(Ray ray, Material material, Color all, Color special) {
            _spheres = new Geometries();
            _spheres.add(new Sphere(ray.getP0().add(ray.getDir().scale(10)), 10).setMaterial(material).setEmission(all));
            _spheres.add(new Sphere(ray.getP0().add(ray.getDir().scale(13)), 11).setMaterial(material).setEmission(all));
            _spheres.add(new Sphere(ray.getP0().add(ray.getDir().scale(17)), 12).setMaterial(material).setEmission(all));

        }
    }
}
