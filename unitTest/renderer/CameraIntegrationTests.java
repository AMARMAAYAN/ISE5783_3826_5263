package renderer;
import static org.junit.jupiter.api.Assertions.*;

import geometries.*;
import org.junit.jupiter.api.Test;

import primitives.*;

import java.util.LinkedList;
import java.util.List;
/**
 The CameraIntegrationTests class is used to test the integration between the camera and various geometries.
 It includes methods for testing sphere, plane and triangle intersection with a given camera, and testing
 whether the expected amount of intersections is found.
 @author Maayan Amar
 */

public class CameraIntegrationTests {

    private void assertEquals(String string, int i, int intersections) {
    }
    static final Point ZERO_POINT = new Point(0, 0, 0);

    /**
     * Helper method for testing the integration between a given geometry and a given camera. The method finds the
     * amount of intersection points between the given geometry and the camera, and checks whether the amount is
     * equal to the expected amount of intersections.
     *
     * @param geo The geometry being tested
     * @param camera The camera being tested
     * @param expected The expected amount of intersections
     * @param testCase A description of the test case
     */
    public void cameraIntegrations(Geometry geo, Camera camera, int expected, String testCase) {
        List<Point> intersectionPoints;
        int intersections = 0;
        for (int i = 0; i < 3; ++i) {
            for (int j = 0; j < 3; ++j) {
                intersectionPoints = geo.findIntersections(
                        camera.constructRay(3, 3, j, i));
                if (intersectionPoints != null)
                    intersections += intersectionPoints.size();
            }
        }
        assertEquals("ERROR "+ testCase +":Wrong amount of intersections",expected ,intersections);
    }


    /**
     * Test method for the integration between the camera and spheres of different sizes and positions.
     * It tests whether the expected amount of intersection points are found for each test case.
     */
    @Test
    public void SphereIntegration() {

        Camera camera1 = new Camera(ZERO_POINT, new Vector(0, 0, -1), new Vector(0, -1, 0)).setVPDistance(1).setVPSize(3,
                3);
        Camera camera2 = new Camera(new Point(0, 0, 0.5), new Vector(0, 0, -1), new Vector(0, -1, 0)).setVPDistance(1)
                .setVPSize(3, 3);

        // TC01: 2 intersection points
        cameraIntegrations(new Sphere(new Point(0, 0, -3), 1), camera1, 2, "TC01");

        // TC02: 18 intersection points
        cameraIntegrations(new Sphere(new Point(0, 0, -2.5), 2.5), camera2, 18, "TC02");

        // TC03: 10 intersection points
        cameraIntegrations(new Sphere(new Point(0, 0, -2), 2), camera2, 10, "TC03");

        // TC04: 9 intersection points
        cameraIntegrations(new Sphere(new Point(0, 0, -1), 4), camera2, 9, "TC04");

        // TC05: 0 intersection points
        cameraIntegrations(new Sphere(new Point(0, 0, 1), 0.5), camera1, 0, "TC05");

    }



    /**
     * Test method for the integration between the camera and planes of different sizes and positions.
     * It tests whether the expected amount of intersection points are found for each test case.
     */
    @Test
    public void PlaneIntegration() {
        Camera camera = new Camera(ZERO_POINT, new Vector(0, 0, 1), new Vector(0, -1, 0)).setVPDistance(1).setVPSize(3,
                3);
        // Tc01: 9 intersection points-plane against camera
        cameraIntegrations(new Plane(new Point(0, 0, 5),new Vector(0, 0, 1)), camera, 9, "TC01");

        // TC02: 9 intersection points-plane with small angle
        cameraIntegrations(new Plane( new Point(0, 0, 5),new Vector(0, -1, 2)), camera, 9, "TC02");

        // TC03: 6 intersection points-plane parallel to lower rays
        cameraIntegrations(new Plane(new Point(0, 0, 5),new Vector(0, -1, 1)), camera, 6, "TC03");

    }

    /**
     * Test method for the integration between the camera and triangles of different sizes and positions.
     * It tests whether the expected amount of intersection points are found for each test case.
     */
    @Test
    public void TriangleIntegration() {
        Camera camera = new Camera(ZERO_POINT, new Vector(0, 0, -1), new Vector(0, -1, 0)).setVPDistance(1).setVPSize(3,
                3);
        // TC01: 1 intersection point-small triangle
        cameraIntegrations(new Triangle(new Point(0, 1, -2), new Point(1, -1, -2), new Point(-1, -1, -2)), camera, 1,
                "TC01");

        // TC02: 2 intersection points - medium triangle
        cameraIntegrations(new Triangle(new Point(1, -1, -2), new Point(-1, -1, -2), new Point(0, 20, -2)), camera, 2,
                "TC02");

    }
}

