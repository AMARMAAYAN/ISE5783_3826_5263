package renderer;


import geometries.*;
import lighting.AmbientLight;
import lighting.DirectionalLight;
import lighting.PointLight;
import lighting.SpotLight;
import org.junit.jupiter.api.Test;
import primitives.*;
import renderer.Camera;
import renderer.ImageWriter;
import renderer.RayTracerBasic;
import scene.Scene;

import static java.awt.Color.*;

public class PRO7Test {
    private final Scene scene = new Scene.SceneBuilder("Test scene").setBackground(new Color(212, 230, 241)).build();

    @Test
    void SnowMan() {
        //Camera camera = new Camera(new Point(-2500, -2500, 800), new Vector(1, 1, 0), new Vector(0, 0, 1)) //
        //	.setVPSize(2500, 2500).setVPDistance(3000); //
        //first option: we see the bird from the back:
//        Camera camera = new Camera(
//                new Point(35,50,0), //position
//                new Vector(-1,-1,0), //vTo
//                new Vector(0,0,1)) //vUp
//                .setVPSize(60,60)
//                //.setAntiAliasingOn(20)
//                .setVPDistance(40)
//                .moveNearAway(40)
//                .moveUpDown(40)
//                .moveRightLeft(-10)
//                .spinAroundVRight(-40)
//                .spinAroundVTo(30)
//                .spinAroundVUp(-10);
        Camera camera = new Camera(new Point(-50,-50,0), new Vector(1,1,0), new Vector(0,0,1)) //
                .setVPSize(60,60).setVPDistance(40);
		/* //more option, need to fix, we see the bird from the front
		Camera camera = new Camera(new Point(-50,-50,0), new Vector(1,1,0), new Vector(0,0,1)) //
				.setVPSize(60,60).setVPDistance(40);*/
        Material snowmanSkin = new Material().setKt(0.2).setShininess(30).setKs(0.1).setKd(0.7);
        Material snowmanOrgans = new Material().setKd(0.7).setShininess(30);
        Material snowmanNose = new Material().setKr(0.001).setKs(0.2).setKd(0.5);
        Material snowmanEye = new Material().setKt(1).setKs(0.5).setKd(0.01);
        scene.setAmbientLight(new AmbientLight(new Color(214, 234, 248 ), new Double3(0.1)));//color of sky, less or more

        scene.getGeometries().add(
                //the bottun in the body
                new Sphere(new Point(-28.5,-28,-2),1)
                        .setEmission(new Color(236, 112, 99)).setMaterial(snowmanSkin),

                new Sphere(new Point(-28.5,-28,-5),1)
                        .setEmission(new Color(236, 112, 99)).setMaterial(snowmanSkin),

                new Sphere(new Point(-28.5,-28,-8),1)
                        .setEmission(new Color(236, 112, 99)).setMaterial(snowmanSkin),
                //body of the snowman
                new Sphere(new Point(-8,-5,-10), 14)
                        .setEmission(new Color(169, 204, 227  )).setMaterial(snowmanSkin),
                //head of the snowman
                new Sphere(new Point(-10,-5,8),10)
                        .setEmission(new Color(169, 204, 227)).setMaterial(snowmanSkin.setKt(0.05)),


                //eyes of the snowman
                new Sphere(new Point(-16,-18,7), 1)
                        .setEmission(new Color(0,0,0)).setMaterial(snowmanSkin),
                new Sphere(new Point(-14, -16, 7.5),2)
                        .setEmission(new Color(144, 148, 151)).setMaterial(snowmanEye),

                new Sphere(new Point(-20,-13,7),1)
                        .setEmission(new Color(0,0,0)).setMaterial(snowmanSkin),
                new Sphere(new Point(-18,-11,7.5),2)
                        .setEmission(new Color(144, 148, 151 )).setMaterial(snowmanEye),

                //the hand:
                new Triangle(new Point(-30,-20,-2), new Point(-35,-18,-4), new Point(-30,-19,-3.3))
                        .setEmission(new Color(138,96,30)).setMaterial(snowmanOrgans),

                new Triangle(new Point(-10,-20,-2), new Point(10,-18,-3.7), new Point(-10,-20.5,-3.4))
                        .setEmission(new Color(138,96,30)).setMaterial(snowmanOrgans),

                //nose of the snowman (pyramid)
                new Triangle(new Point(-21.7,-20,6), new Point(-16.8,-18,3.8), new Point(-17.5,-19,1))
                        .setEmission(new Color(130,50,10)).setMaterial(snowmanNose),

                new Triangle(new Point(-21.7,-20,6), new Point(-21.8,-18,3.5), new Point(-17.5,-19,1))
                        .setEmission(new Color(224,158,56)).setMaterial(snowmanNose),


                //the land (replace to tube, like a branch?) the water source, and the shore
                new Plane(new Point(215, 189, 226 ), new Point(-8,13,-25), new Point(1,1,-25))
                        .setEmission(new Color/*(140,50,40)*/(41, 128, 185 )).setMaterial(new Material().setKd(0.6).setShininess(10)),

                new Plane(new Point(-15,10,-25), new Point(-15,-10,-25), new Point(-35,0,-12))
                        .setEmission(new Color(0, 20, 255)).setMaterial(new Material().setKr(0.5)));//.setKd(0.7).setKs(0.4).setShininess(20)),



        //scene.lights.add(new PointLight(new Color(255,255,255), new Point(-30,0,20)).setKq(0.00001).setKl(0.00001));
        scene.getLights().add(new PointLight(new Color(255,255,255), new Point(0,0,10)).setKl(0.00001).setKq(0.0001));
        //scene.lights.add(new PointLight(new Color(800,500,250), new Point(-19,-9,4.5)).setKq(0.001));
        scene.getLights().add(new SpotLight(new Color(800,500,250), new Point(-80,20,20), new Vector(82,-18,-25)).setKl(0.1).setKq(0.0001));
        //scene.lights.add(new SpotLight(new Color(800,500,250), new Point(18,-34,0), new Vector(-12,28,-17)).setKl(0.1).setKq(0.0001));
        scene.getLights().add(new DirectionalLight(new Color(100, 150, 150), new Vector(22, -18, -35)));
        //scene.lights.add(new DirectionalLight(new Color(100,150,150), new Vector(17,12,20)));

        ImageWriter imageWriter = new ImageWriter("PR07 - move camera", 500, 500);
        camera.setImageWriter(imageWriter) //
                .setRayTracer(new RayTracerBasic(scene)) //
                .renderImage() //
                .writeToImage();
    }


}
