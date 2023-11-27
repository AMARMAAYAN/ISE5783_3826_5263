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
    // int firstX = 5000;
    // int distanceBetweenPins = 70;

    @Test
    public void picture() {

        scene.setAmbientLight(new AmbientLight(new Color(214, 234, 248 ), new Double3(0.1)));

        // There are two versions of the photo- the normal one, and another one taken from the top to show another angle of the created scene, for ease of working with it
        Camera camera = new Camera(new Point(-1000, 0, 500), new Vector(1, 0, -0.05), new Vector(0.05, 0, 1)) //
                .setVPSize(150, 100).setVPDistance(Math.sqrt(500000));
        Camera cameraTop = new Camera(new Point(5000, 0, 580), new Vector(0, 0, -1), new Vector(1, 0, 0)) //
                .setVPSize(70, 120).setVPDistance(100);

        //created three lanes
        createLanes();



        //creating walls for the room- with a directional light which you can see hitting the left lane
        //this is light #1-directional light
        createWalls();
        //creating the black boxes to symbol the end of lane, to make the image more realistic
        createBoxForEndOfLane();


        //light # 2- this light is located in front of the  bowling pins, and you can see it on the pink mirrors-
        SpotLight lightFixture2 = new SpotLight(new Color(102, 255, 51), new Point(4670, -20, 7), new Vector(1, 0, 0.02));
        scene.getLights().add(lightFixture2);


        //creates light #3- the spotlight located on the top of the room, pointing downwards
        createLightFixture(new Point(3500, 0, 550));

        createLightFixture(new Point(500, 0, 550));

//       creates the texture on top of the end of lane box- to make the image look nicer
        // createTexture(new Ray(new Point(6999, -330, 200), new Vector(0, 1, 1)));

        addPin(new Point(6500, 50.1, 17));
        addPin(new Point(6500, -44.1, 17));
        addPin(new Point(6500, 3.1, 17));

        addPin(new Point(6100, 23.1, 17));
        addPin(new Point(6100, -17.1, 17));

        addPin(new Point(5700, 3.1, 17));

        addPin(new Point(4000, 3.1, 17));

        //ball
        ball(new Point(5000,215,25), new Color(51, 200, 200));

        ball(new Point(3000,-225,25), new Color(231, 76, 60));


        camera.setImageWriter(new ImageWriter("final picture", 2000, 2000)) //
                .setRayTracer(new RayTracerBasic(scene)) //
                .renderImage()
                //.renderImageMultiThreadingASS() //
                //.renderImageSuperSampling()
                .writeToImage();

//        cameraTop.setImageWriter(new ImageWriter("ourPictureMP2UpBVH", 2000, 2000)) //
//                .setRayTracer(new RayTracerBasic(_scene)) //
//                .renderImageMultiThreadingASS() //
//                .writeToImage();

    }




    void createLightFixture(Point p) {
        double lightX = p.getX();
        double lightY = p.getY();
        double lightZ = p.getZ();
        Geometry lightFront = new Polygon(new Point(lightX - 25, lightY - 25, lightZ), new Point(lightX - 25, lightY + 25, lightZ), new Point(lightX - 15, lightY + 10, lightZ + 20), new Point(lightX - 15, lightY - 10, lightZ + 20))
                .setMaterial(new Material().setShininess(19).setKs(1)).setEmission(new Color(BLACK));
        Geometry lightRight = new Polygon(new Point(lightX - 25, lightY - 25, lightZ), new Point(lightX + 25, lightY - 25, lightZ), new Point(lightX + 15, lightY - 10, lightZ + 20), new Point(lightX - 15, lightY - 10, lightZ + 20))
                .setMaterial(new Material().setShininess(19).setKs(1)).setEmission(new Color(BLACK));
        Geometry lightBack = new Polygon(new Point(lightX + 25, lightY - 25, lightZ), new Point(lightX + 25, lightY + 25, lightZ), new Point(lightX + 15, lightY + 10, lightZ + 20), new Point(lightX + 15, lightY - 10, lightZ + 20))
                .setMaterial(new Material().setShininess(19).setKs(1)).setEmission(new Color(BLACK));
        Geometry lightLeft = new Polygon(new Point(lightX + 25, lightY + 25, lightZ), new Point(lightX - 25, lightY + 25, lightZ), new Point(lightX - 15, lightY + 10, lightZ + 20), new Point(lightX + 15, lightY + 10, lightZ + 20))
                .setMaterial(new Material().setShininess(19).setKs(1)).setEmission(new Color(BLACK));
        Sphere lightCover = (Sphere) new Sphere(p, 15d).setEmission(new Color(ORANGE)) //
                .setMaterial(new Material().setKd(0.4).setKs(0.2).setShininess(19).setKt(0.3));
        scene.getGeometries().add(lightCover, lightFront, lightRight, lightBack, lightLeft);

        SpotLight middleLight = new SpotLight(new Color(WHITE), new Point(lightX, 0, lightZ), new Vector(0, 0, -1));

        scene.getLights().add(middleLight);
    }
    private void ball(Point p, Color color){
        //scene.lights.add(new PointLight(new Color(255,255,255), new Point(-30,0,20)).setKq(0.00001).setKl(0.00001));
        scene.getLights().add(new PointLight(new Color(255,255,255), new Point(p.getX()+50,p.getY(),p.getZ())).setKl(0.00001).setKq(0.0001));

        Geometry ball= new Sphere(new Point(p.getX(),p.getY(),p.getZ()),25)
                .setEmission(new Color (color.getColor())).setMaterial(new Material().setShininess(50).setKs(10).setKd(0.25).setKr(0.0));
        scene.getGeometries().add(ball);
    }



    public void addPin(Point p)
    {
        Material pinBody = new Material().setKt(0.2).setShininess(30).setKs(30).setKd(5).setKr(0.03);
        scene.getGeometries().add(
                new Sphere(new Point(p.getX(), p.getY(), p.getZ()+12), 17).setEmission(new Color(235, 245, 251  )).setMaterial(new Material().setShininess(30).setKs(1).setKd(0.25).setKr(0.5)),
                new Sphere(new Point(p.getX(), p.getY(), p.getZ()), 17) .setEmission(new Color (235, 245, 251 )).setMaterial(new Material().setShininess(30).setKs(1).setKd(0.25).setKr(0.5)),
                //
                new Cylinder( 15, new Ray(new Point(p.getX(), p.getY(), p.getZ()+27), new Vector(0, 0, 1)), 8).setEmission(new Color(RED)).setMaterial(new Material().setShininess(25).setKs(1).setKd(0.25).setKr(0.1)),
                new Cylinder( 15, new Ray(new Point(p.getX(),  p.getY(), p.getZ()+28), new Vector(0, 0, 1)), 8).setEmission(new Color(RED)).setMaterial(new Material().setShininess(25).setKs(1).setKd(0.25).setKr(0.1)),

                new Sphere(new Point(p.getX(),  p.getY(), p.getZ()+29), 8).setEmission(new Color(235, 245, 251 )).setMaterial(new Material().setShininess(40).setKs(1).setKd(0.25).setKr(0.5)),
                new Sphere(new Point(p.getX(),  p.getY(), p.getZ()+34.7), 8.3).setEmission(new Color(235, 245, 251 )).setMaterial(new Material().setShininess(40).setKs(1).setKd(0.25).setKr(0.5))


        );

    }

    private void createWalls() {
        Material wall = new Material().setKd(0.4).setKr(0.05).setShininess(10);
        Color wallColor = new Color( 36, 113, 163).scale(2);

        int closeX = 4000;
        int farX = 5500;
        int bottomZ = 300;
        int topZ = 450;
        Point topFarPoint = new Point(farX, 340, topZ);
        Point bottomFarPoint = new Point(farX, 340, bottomZ);
        Point topClosePoint = new Point(closeX, 340, topZ);
        Point bottomClosePoint = new Point(closeX, 340, bottomZ);
        Polygon leftWall1 = (Polygon) new Polygon(new Point(-500, 340, 1500), new Point(lengthToX, 340, 1500), new Point(lengthToX, 340, 0), new Point(-500, 340, 0)).setEmission(wallColor)
                .setMaterial(wall);

        scene.getGeometries().add(leftWall1/*, leftWall2, leftWall3, leftWall4,verticalStick, horizontalStick*/);

        scene.getLights().add(new DirectionalLight(new Color(165, 105, 189), new Vector(0, -1, -0.7)));

        Polygon rightWall = (Polygon) new Polygon(new Point(-500, -340, 1500), new Point(lengthToX, -340, 1500), new Point(lengthToX, -340, 0), new Point(-500, -340, 0)).setEmission(wallColor)
                .setMaterial(wall);
        Polygon backWall = (Polygon) new Polygon(new Point(7000, 340, 1500), new Point(7000, -340, 1500), new Point(7000, -340, 0), new Point(7000, 340, 0)).setEmission(wallColor)
                .setMaterial(wall);
        Color roofColor = new Color(40, 116, 166 );
        Geometry roof = new Polygon(new Point(7000, 340, 600), new Point(7000, -340, 600), new Point(-500, -340, 600), new Point(-600, 340, 600))
                .setEmission(new Color(40, 116, 166 )).setMaterial(new Material().setKd(0.9).setShininess(19));
//        Geometry roof = new Polygon(new Point(7000, 340, 600), new Point(7000, -340, 600), new Point(-500, -340, 600), new Point(-600, 340, 600))
//                .setEmission(new Color(0, 153, 204)).setMaterial(new Material().setKd(0.9).setShininess(19));
        scene.getGeometries().add(rightWall, backWall, roof);
    }

    private void createBoxForEndOfLane() {
        int boxHeight = 200;
        Geometry leftBox = new Polygon(new Point(6000, 335, boxHeight), new Point(lengthToX, 335, boxHeight), new Point(lengthToX, 335, 0), new Point(6000, 335, 0))
                .setEmission(new Color(BLACK)).setMaterial(new Material().setKs(0.8).setShininess(19));
        Geometry rightBox = new Polygon(new Point(6000, -335, boxHeight), new Point(lengthToX, -335, boxHeight), new Point(lengthToX, -335, 0), new Point(6000, -335, 0))
                .setEmission(new Color(BLACK)).setMaterial(new Material().setKs(0.8).setShininess(19));
        Geometry backMirror= new Polygon(new Point(6995, -335, boxHeight), new Point(6990, -335, 0), new Point(6990, -120, 0), new Point(6995, -120, boxHeight))
                .setMaterial(new Material().setKd(0.1).setKr(0.3).setShininess(19)).setEmission(new Color(255, 105, 180));//box color neon pink

        Geometry backMirror1= new Polygon(new Point(6995, -335, boxHeight), new Point(6990, -335, 0), new Point(6990, -120, 0), new Point(6995, -120, boxHeight))
                .setMaterial(new Material().setKd(0.1).setKr(0.3).setShininess(19)).setEmission(new Color(255, 105, 180));//box color neon pink

        Geometry backMirror2= new Polygon(new Point(6995, -120, boxHeight), new Point(6990, -120, 0), new Point(6990, 100, 0), new Point(6995, 100, boxHeight))
                .setMaterial(new Material().setKd(0.1).setKr(0.3).setShininess(19)).setEmission(new Color(255, 105, 180));//box color neon pink


        Geometry backMirror3= new Polygon(new Point(6995, 100, boxHeight), new Point(6990, 100, 0), new Point(6990, 340, 0), new Point(6995, 340, boxHeight))
                .setMaterial(new Material().setKd(0.1).setKr(0.3).setShininess(19)).setEmission(new Color(255, 105, 180));//box color neon pink


        Geometry secondBox = new Polygon(new Point(6000, -120, boxHeight), new Point(lengthToX, -120, boxHeight), new Point(lengthToX, -120, 0), new Point(6000, -120, 0)).setEmission(new Color(BLACK))
                .setMaterial(new Material().setKs(0.8).setShininess(19));
        Geometry thirdBox = new Polygon(new Point(6000, 120, boxHeight), new Point(lengthToX, 120, boxHeight), new Point(lengthToX, 120, 0), new Point(6000, 120, 0)).setEmission(new Color(BLACK))
                .setMaterial(new Material().setKs(0.8).setShininess(19));
        Geometry topBox = new Polygon(new Point(6000, -335, boxHeight), new Point(lengthToX, -335, boxHeight), new Point(lengthToX, 335, boxHeight), new Point(6000, 335, boxHeight)).setEmission(new Color(BLACK))
                .setMaterial(new Material().setKs(0.8).setShininess(19));
        Geometry backBox = new Polygon(new Point(6995, 340, boxHeight), new Point(6995, -340, boxHeight), new Point(6995, -340, 0), new Point(6995, 340, 0)).setEmission(new Color(255, 105, 180))//box color neon pink
                .setMaterial(new Material().setKs(0.8).setShininess(19));
        scene.getGeometries().add(leftBox, rightBox, topBox, backBox, secondBox, thirdBox,backMirror1,backMirror2,backMirror3);
    }

//
//    public void addPin(Point p)
//    {
//        Material pinBody = new Material().setKt(0.2).setShininess(30).setKs(0.1).setKd(0.7);
//        scene.getGeometries().add(
//                new Sphere(new Point(p.getX(), 1.1, p.getZ()), 6).setEmission(new Color(169, 204, 227  )).setMaterial(pinBody),
//                new Cylinder(2.2 ,new Ray(new Point( p.getX(), 0.6, p.getZ()), new Vector(0, 1, 0)), 6).setEmission(new Color(169, 204, 227  )).setMaterial(pinBody),
//               new Sphere(new Point(p.getX(), 3.1, p.getZ()), 6).setEmission(new Color(WHITE)),
//                new Cylinder(3, new Ray(new Point(p.getX(), 4.6, p.getZ()), new Vector(0, 1, 0)), 5).setEmission(new Color(RED)),
//                new Cylinder(0.6 , new Ray(new Point(p.getX(), 5.3, p.getZ()), new Vector(0, 1, 0)), 1.01).setEmission(new Color(WHITE)),
//                new Sphere(new Point(p.getX(), 7.3, p.getZ()), 5.25).setEmission(new Color(WHITE)),
//               new Cylinder(1, new Ray(new Point(p.getX(), 7.6, p.getZ()), new Vector(0, 1, 0)), 5.25 ).setEmission(new Color(WHITE)),
//                new Sphere(new Point(p.getX(), 8.3, p.getZ()), 5.25).setEmission(new Color(WHITE))
//        );
//
//    }


    private void createLanes() {
        Material laneMaterial = new Material().setKs(1).setKd(0.1).setKr(0.05).setShininess(70);
        Color laneColor = new Color(64, 64, 64); // Dark grey for lanes

        Polygon lane = (Polygon) new Polygon(new Point(-500, 100, 0), new Point(lengthToX, 100, 0), new Point(lengthToX, -100, 0), new Point(-500, -100, 0)).setEmission(laneColor)
                .setMaterial(laneMaterial);
        Polygon laneRight = (Polygon) new Polygon(new Point(-500, -140, 0), new Point(lengthToX, -140, 0), new Point(lengthToX, -340, 0), new Point(-500, -340, 0)).setEmission(laneColor)
                .setMaterial(laneMaterial);
        Polygon laneLeft = (Polygon) new Polygon(new Point(-500, 140, 0), new Point(lengthToX, 140, 0), new Point(lengthToX, 340, 0), new Point(-500, 340, 0)).setEmission(laneColor)
                .setMaterial(laneMaterial);
        Polygon leftPolR = (Polygon) new Polygon(new Point(-500, 100, 0), new Point(lengthToX, 100, 0), new Point(lengthToX, 110, -20), new Point(-500, 110, -20)).setEmission(new Color(GRAY))
                .setMaterial(new Material().setKs(1).setKd(0.3).setKr(0.3).setShininess(25));
        Polygon leftPolM = (Polygon) new Polygon(new Point(-500, 110, -20), new Point(lengthToX, 110, -20), new Point(lengthToX, 130, -20), new Point(-500, 130, -20)).setEmission(new Color(GRAY))
                .setMaterial(new Material().setKs(1).setKd(0.3).setKr(0.3).setShininess(25));
        Tube leftTube = (Tube) new Tube(new Ray(new Point(-500, 120, -15), new Vector(1, 0, 0)), 5)
                .setMaterial(new Material().setKs(1).setKd(0.6).setKr(0.4));
        Polygon leftPolL = (Polygon) new Polygon(new Point(-500, 140, 0), new Point(lengthToX, 140, 0), new Point(lengthToX, 130, -20), new Point(-500, 130, -20)).setEmission(new Color(GRAY))
                .setMaterial(new Material().setKs(1).setKd(0.3).setKr(0.3).setShininess(25));
        Polygon rightPolR = (Polygon) new Polygon(new Point(-500, -140, 0), new Point(lengthToX, -140, 0), new Point(lengthToX, -130, -20), new Point(-500, -130, -20)).setEmission(new Color(GRAY))
                .setMaterial(new Material().setKs(1).setKd(0.3).setKr(0.3).setShininess(25));
        Polygon rightPolM = (Polygon) new Polygon(new Point(-500, -110, -20), new Point(lengthToX, -110, -20), new Point(lengthToX, -130, -20), new Point(-500, -130, -20)).setEmission(new Color(GRAY))
                .setMaterial(new Material().setKs(1).setKd(0.3).setKr(0.3).setShininess(25));
        Tube rightTube = (Tube) new Tube(new Ray(new Point(-500, -120, -15), new Vector(1, 0, 0)), 5)
                .setMaterial(new Material().setKs(1).setKd(0.6).setKr(0.4));
        Polygon rightPolL = (Polygon) new Polygon(new Point(-500, -100, 0), new Point(lengthToX, -100, 0), new Point(lengthToX, -110, -20), new Point(-500, -110, -20)).setEmission(new Color(GRAY))
                .setMaterial(new Material().setKs(1).setKd(0.3).setKr(0.3).setShininess(25));
        scene.getGeometries().add(lane, laneLeft, laneRight);
        scene.getGeometries().add(rightPolL, rightPolR, leftPolL, leftPolR, rightPolM, leftPolM);
        scene.getGeometries().add(rightTube, leftTube);

    }


}
