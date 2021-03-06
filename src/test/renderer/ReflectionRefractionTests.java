/**
 *
 */
package test.renderer;

import elements.*;
import geometries.Plane;
import geometries.Polygon;
import geometries.Sphere;
import geometries.Triangle;
import org.junit.Test;
import primitives.Color;
import primitives.Material;
import primitives.Point3D;
import primitives.Vector;
import renderer.ImageWriter;
import renderer.Render;
import scene.Scene;

/**
 * Tests for reflection and transparency functionality, test for partial shadows
 * (with transparency)
 *
 * @author dzilb
 */
public class ReflectionRefractionTests {

    /**
     * Produce a picture of a sphere lighted by a spot light
     */
    @Test
    public void twoSpheres() {
        Scene scene = new Scene("Test scene");
        scene.setCamera(new Camera(new Point3D(0, 0, -1000), new Vector(0, 0, 1), new Vector(0, -1, 0)));
        scene.setDistance(1000);
        scene.setBackground(Color.BLACK);
        scene.setAmbientLight(new AmbientLight(Color.BLACK, 0));

        scene.addGeometries(
                new Sphere(new Color(java.awt.Color.BLUE), new Material(0.4, 0.3, 100, 0.3, 0),
                        new Point3D(0, 0, 50), 50),
                new Sphere(new Color(java.awt.Color.RED), new Material(0.5, 0.5, 100), new Point3D(0, 0, 50), 25));

        scene.addLights(new SpotLight(new Color(1000, 600, 0), new Vector(-1, 1, 2), new Point3D(-100, 100, -500), 1,
                0.0004, 0.0000006));

        ImageWriter imageWriter = new ImageWriter("twoSpheres", 150, 150, 500, 500);
        Render render = new Render(scene, imageWriter);

        render.renderImage();
        render.writeToImage();
    }

    /**
     * Produce a picture of a sphere lighted by a spot light
     */
    @Test
    public void twoSpheresOnMirrors() {
        Scene scene = new Scene("Test scene");
        scene.setCamera(new Camera(new Point3D(0, 0, -10000), new Vector(0, 0, 1), new Vector(0, -1, 0)));
        scene.setDistance(10000);
        scene.setBackground(Color.BLACK);
        scene.setAmbientLight(new AmbientLight(new Color(255, 255, 255), 0.1));

        scene.addGeometries(
                new Sphere(new Color(0, 0, 100), new Material(0.25, 0.25, 20, 0.5, 0), new Point3D(-950, 900, 1000), 400),
                new Sphere(new Color(100, 20, 20), new Material(0.25, 0.25, 20), new Point3D(-950, 900, 1000), 200),
                new Triangle(new Color(20, 20, 20), new Material(0, 0, 0, 0, 1), new Point3D(1500, 1500, 1500),
                        new Point3D(-1500, -1500, 1500), new Point3D(670, -670, -3000)),
                new Triangle(new Color(20, 20, 20), new Material(0, 0, 0, 0, 0.5), new Point3D(1500, 1500, 1500),
                        new Point3D(-1500, -1500, 1500), new Point3D(-1500, 1500, 2000)));

        scene.addLights(new SpotLight(new Color(1020, 400, 400),
                new Vector(-1, 1, 4), new Point3D(-750, 750, 150), 1, 0.00001, 0.000005));

        ImageWriter imageWriter = new ImageWriter("twoSpheresMirrored", 2500, 2500, 500, 500);
        Render render = new Render(scene, imageWriter);

        render.renderImage();
        render.writeToImage();
    }

    /**
     * Produce a picture of a two triangles lighted by a spot light with a partially transparent Sphere
     * producing partial shadow
     */
    @Test
    public void trianglesTransparentSphere() {
        Scene scene = new Scene("Test scene");
        scene.setCamera(new Camera(new Point3D(0, 0, -1000), new Vector(0, 0, 1), new Vector(0, -1, 0)));
        scene.setDistance(1000);
        scene.setBackground(Color.BLACK);
        scene.setAmbientLight(new AmbientLight(new Color(java.awt.Color.WHITE), 0.15));

        scene.addGeometries( //
                new Triangle(Color.BLACK, new Material(0.5, 0.5, 60), //
                        new Point3D(-150, 150, 115), new Point3D(150, 150, 135), new Point3D(75, -75, 150)), //
                new Triangle(Color.BLACK, new Material(0.5, 0.5, 60), //
                        new Point3D(-150, 150, 115), new Point3D(-70, -70, 140), new Point3D(75, -75, 150)), //
                new Sphere(new Color(java.awt.Color.BLUE), new Material(0.2, 0.2, 30, 0.6, 0), // )
                        new Point3D(60, -50, 50), 30));

        scene.addLights(new SpotLight(new Color(700, 400, 400), //
                new Vector(0, 0, 1), new Point3D(60, -50, 0), 1, 4E-5, 2E-7));

        ImageWriter imageWriter = new ImageWriter("shadow with transparency", 200, 200, 600, 600);
        Render render = new Render(scene, imageWriter);

        render.renderImage();
        render.writeToImage();
    }

    @Test
    public void myPicture() {
        Scene scene = new Scene("Test scene");
        scene.setCamera(new Camera(new Point3D(0, 0, -1000), new Vector(0, 0, 1), new Vector(0, -1, 0)));
        scene.setDistance(1000);
        scene.setBackground(Color.BLACK);
        scene.setAmbientLight(new AmbientLight(new Color(java.awt.Color.WHITE), 0.15));

        scene.addGeometries(
                new Sphere(new Color(java.awt.Color.BLACK), new Material(0.8, 0.8, 30, 0.8, 0), new Point3D(0, 80, -200), 200),
                new Triangle(Color.BLACK, new Material(0.8, 1, 10000, 0, 1), //
                        new Point3D(400, -400, -300), new Point3D(150, 300, -150), new Point3D(100, -300, 0.0)), //
                new Sphere(new Color(java.awt.Color.BLACK), new Material(0.8, 0.8, 30, 0.8, 0), new Point3D(1, 80, -200), 300),

                new Triangle(Color.BLACK, new Material(0.8, 1, 10000, 0, 1), //
                        new Point3D(-500, 200, -100), new Point3D(1800, 200, -700), new Point3D(-1800, 200, -700)), //
                new Sphere(new Color(java.awt.Color.BLACK), new Material(0.8, 0.8, 30, 0.8, 0), new Point3D(1, 80, -200), 50),
                new Sphere(new Color(java.awt.Color.WHITE), new Material(0.8, 0.8, 30, 0.8, 0), new Point3D(0, 80, -200), 15));
        scene.addLights(
                new SpotLight(new Color(400, 400, 1020), new Vector(2, 2, -3), new Point3D(-300, -300, -100), 1.0, 0.00001, 0.000005),
                new SpotLight(new Color(650, 400, 300), new Vector(2, 2, -3), new Point3D(-300, -300, 100), 1.0, 0.00001, 0.000005),
                new SpotLight(new Color(400, 700, 400), new Vector(2, 2, -3), new Point3D(50, -300, 150), 1.0, 0.00001, 0.000005),
                new PointLight(new Color(650, 400, 400), new Point3D(0, 0, -200), 0.5, 0.5, 0.5));


        ImageWriter imageWriter = new ImageWriter("MyPicture", 1000, 1000, 1000, 1000);
        Render render = new Render(scene, imageWriter).setMultithreading(3).setDebugPrint();
        render.renderImage();
        imageWriter.writeToImage();
    }

    @Test
    public void myImprovedPicture() {
        Scene scene = new Scene("Test scene");
        scene.setCamera(new Camera(new Point3D(0, 0, -1000), new Vector(0, 0, 1), new Vector(0, -1, 0)));
        scene.setDistance(1000);
        scene.setBackground(new Color(50, 50, 50));
        scene.setAmbientLight(new AmbientLight(new Color(java.awt.Color.WHITE), 0.15));

        scene.addGeometries(
//                new Plane(Color.BLACK, new Material(0.1, 0.9, 10000, 0, 0.9),
//                        new Point3D(0, 200, 0), new Vector(0, -1, 0)),
                new Plane(Color.BLACK, new Material(0.2, 0.2, 10000, 0, 0.2).setGlossBlur(0.2),
                        new Point3D(0, -200, 0), new Vector(0, -1, 0)),
                new Sphere(new Color(java.awt.Color.BLACK), new Material(0.1, 0.1, 30, 0, 0.8),
                        new Point3D(220, 100, 0), 80),
                new Sphere(new Color(java.awt.Color.BLACK), new Material(0.1, 0.1, 30, 0, 0.8),
                        new Point3D(385, 100, 0), 80),
                new Sphere(new Color(java.awt.Color.BLACK), new Material(0.1, 0.1, 30, 0.8, 0),
                        new Point3D(-220, 100, 0), 80),
                new Sphere(new Color(java.awt.Color.BLACK), new Material(0.1, 0.1, 30, 0, 0.8),
                        new Point3D(-385, 100, 0), 80),
                new Sphere(new Color(java.awt.Color.BLACK), new Material(.2, .1, 30, 0, 0.7),
                        new Point3D(0, 100, 0), 80),

                new Sphere(new Color(java.awt.Color.BLACK), new Material(0.3, 0.7, 50, 0, 0),
                        new Point3D(220, -100, 0), 80),
                new Sphere(new Color(java.awt.Color.BLACK), new Material(0.3, 0.3, 30, 0, 0.4),
                        new Point3D(385, -100, 0), 80),
                new Sphere(new Color(java.awt.Color.BLACK), new Material(0.1, 0.1, 30, 0, 0.8),
                        new Point3D(-220, -100, 0), 80),
                new Sphere(new Color(java.awt.Color.BLACK), new Material(0.1, 0.1, 30, 0.8, 0),
                        new Point3D(-385, -100, 0), 80),
                new Sphere(new Color(java.awt.Color.BLACK), new Material(.3, .2, 30, 0.5, 0),
                        new Point3D(0, -100, 0), 80)
        );

        scene.addLights(
                new PointLight(new Color(java.awt.Color.WHITE), new Point3D(0, 19, 500), 1.0, 0.00001, 0.000005),
                new SpotLight(new Color(255, 120, 0), new Vector(2, 2, -3), new Point3D(-660, 0, -300), 1, 0.00000000001, 0.000000005)
        );

        ImageWriter imageWriter = new ImageWriter("MyImprovedPicture", 1000, 1000, 1000, 1000);
        Render render = new Render(scene, imageWriter).setNumOfGlossBlurRays(80).setNumOfSampleRays(0) //
                .setMultithreading(3).setDebugPrint();
        render.renderImage();
        imageWriter.writeToImage();
    }

    @Test
    public void spherePicture() {
        Scene scene = new Scene("Test scene");
        scene.setCamera(new Camera(new Point3D(0, 0, -1000), new Vector(0, 0, 1), new Vector(0, -1, 0)));
        scene.setDistance(1000);
        scene.setBackground(Color.BLACK);
        scene.setAmbientLight(new AmbientLight(new Color(java.awt.Color.WHITE), 0.15));

        scene.addGeometries(

                new Sphere(new Color(java.awt.Color.BLACK), new Material(0.3, 0.2, 50, .5, 0),
                        new Point3D(0, 0, 150), 200),

                new Polygon(Color.BLACK,
                        new Material(.1, .1, 0, .8, 0).setGlossBlur(0),
                        new Point3D(0, 0, 0),
                        new Point3D(300, 300, 0),
                        new Point3D(300, -300, 0)
                ),
                new Polygon(Color.BLACK,
                        new Material(.1, .1, 0, .8, 0).setGlossBlur(10),
                        new Point3D(0, 0, 0),
                        new Point3D(300, 300, 0),
                        new Point3D(-300, 300, 0)
                ),
                new Polygon(Color.BLACK,
                        new Material(.1, .1, 0, .8, 0).setGlossBlur(25),
                        new Point3D(0, 0, 0),
                        new Point3D(-300, -300, 0),
                        new Point3D(-300, 300, 0)
                ),
                new Polygon(Color.BLACK,
                        new Material(.1, .1, 0, .8, 0).setGlossBlur(50),
                        new Point3D(0, 0, 0),
                        new Point3D(300, -300, 0),
                        new Point3D(-300, -300, 0)
                )
        );

        scene.addLights(
                new PointLight(new Color(java.awt.Color.WHITE), new Point3D(0, 0, 150), 1.0, 10E-14, 10E-14)
                //new SpotLight(new Color(255, 120, 0), new Vector(2, 2, -3), new Point3D(-300, 0, -50), 1, 10E-14, 10E-10)
        );

        ImageWriter imageWriter = new ImageWriter("spherePicture", 1000, 1000, 1000, 1000);
        Render render = new Render(scene, imageWriter).setNumOfGlossBlurRays(80).setNumOfSampleRays(0) //
                .setMultithreading(3).setDebugPrint();
        render.renderImage();
        imageWriter.writeToImage();
    }

    @Test
    public void spheresPicture() {
        Scene scene = new Scene("Test scene");
        scene.setCamera(new Camera(new Point3D(0, 80, -650), new Vector(0, 0, 1), new Vector(0, -1, 0)));
        scene.setDistance(1000);
        scene.setBackground(Color.BLACK);
        scene.setAmbientLight(new AmbientLight(new Color(java.awt.Color.WHITE), 0.15));

        scene.addGeometries(
                new Sphere(new Color(java.awt.Color.BLACK), new Material(0.8, 0.8, 30, 0.8, 0), new Point3D(0, 120, -200), 55),
                new Sphere(new Color(java.awt.Color.black), new Material(0.8, 0.8, 30, 0.8, 0), new Point3D(170, 80, -100), 45),
                new Sphere(new Color(java.awt.Color.BLACK), new Material(0.8, 0.8, 30, 0.8, 0), new Point3D(-120, 140, -200), 35),
                new Sphere(new Color(java.awt.Color.BLACK), new Material(0.8, 0.8, 30, 0.8, 0), new Point3D(-70, 210, -50), 25),
                new Sphere(new Color(java.awt.Color.BLACK), new Material(0.8, 0.8, 30, 0.8, 0), new Point3D(110, 180, -100), 35),
              new Triangle(Color.BLACK, new Material(0.8, 1, 10000, 0, 1), //
                      new Point3D(-1000, -200, 250), new Point3D(2500, -600, -700), new Point3D(2500, -150, 700)),
                new Triangle(Color.BLACK, new Material(0.8, 1, 10000, 0, 1), //
                        new Point3D(-500, 200, -100), new Point3D(1800, 200, -700), new Point3D(-1800, 200, -700)));

        scene.addLights(
        new SpotLight(new Color(400, 400, 1020), new Vector(2, 2, 3), new Point3D(-100, -300, -100), 1.0, 0.00001, 0.000005),
            new SpotLight(new Color(450, 500, 300), new Vector(2, 2, -3), new Point3D(-900, -300, 100), 1.0, 0.00001, 0.000005),
               new SpotLight(new Color(450, 500, 300), new Vector(5, 5, -3), new Point3D(-900, -600, 300), 1.0, 0.00001, 0.000005),
               new SpotLight(new Color(450, 500, 300), new Vector(5, 5, -3), new Point3D(-1600, -400, 700), 1.0, 0.00001, 0.000005),
              // new SpotLight(new Color(200, 300, 300), new Vector(2, 2, -3), new Point3D(-500, -100, -100), 1.0, 0.00001, 0.000005),
               new DirectionalLight(new Color(400,400,400
                ),new Vector(0,0,-200)),
              // new SpotLight(new Color(400, 700, 400), new Vector(2, 2, -3), new Point3D(50, -300, 150), 1.0, 0.00001, 0.000005),
               // new PointLight(new Color(650, 400, 400), new Point3D(-1500, -2000, -200), 0.5, 0.5, 0.5),
        new PointLight(new Color(1150, 700, 400), new Point3D(0, 0, -200), 0.5, 0.5, 0.5));
        ImageWriter imageWriter = new ImageWriter("spheresMirroredPicture", 1000, 1000, 1000, 1000);

        Render render = new Render(scene, imageWriter).setNumOfGlossBlurRays(0).setNumOfSampleRays(80) //
                .setMultithreading(3).setDebugPrint();
        render.renderImage();
        imageWriter.writeToImage();
    }
}
