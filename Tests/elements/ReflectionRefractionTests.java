package elements;

import org.junit.jupiter.api.*;
import geometries.*;
import primitives.*;
import renderer.*;
import scene.*;
import java.util.LinkedList;

/**
 * Tests for reflection and transparency functionality, test for partial shadows
 * (with transparency)
 */
public class ReflectionRefractionTests {
    private Scene.SceneBuilder sceneBuilder = new Scene.SceneBuilder("Test scene");
    private Render.RenderBuilder renderBuilder = new Render.RenderBuilder();

    /**
     * Produce a picture of a sphere lighted by a spot light
     */
    @Test
    public void twoSpheres() {
        Camera.CameraBuilder cameraBuilder =
                new Camera.CameraBuilder(
                        new Point3D(0, 0, 1000),
                        new Vector(0, 0, -1),
                        new Vector(0, 1, 0))
                        .setViewPlaneSize(150, 150)
                        .setDistance(1000);
        Camera camera = cameraBuilder.build();

        Geometries geometries =
                new Geometries(
                        new Sphere(
                                new Point3D(0, 0, -50), 50)
                                .setEmission(new Color(java.awt.Color.BLUE))
                                .setMaterial(new Material().setKd(0.4).setKs(0.3).setNShininess(100).setKt(0.3)),
                        new Sphere(
                                new Point3D(0, 0, -50), 25)
                                .setEmission(new Color(java.awt.Color.RED))
                                .setMaterial(new Material().setKd(0.5).setKs(0.5).setNShininess(100)));

        LinkedList<LightSource> lightSources = new LinkedList<>();
        lightSources.add(
                new SpotLight(
                        new Color(1000, 600, 0),
                        new Point3D(-100, -100, 500),
                        new Vector(-1, -1, -2))
                        .setKl(0.0004)
                        .setKq(0.0000006));

        sceneBuilder.setGeometries(geometries).setLights(lightSources);
        Scene scene = sceneBuilder.build();

        renderBuilder.setImageWriter(
                new ImageWriter("refractionTwoSpheres", 500, 500))
                .setCamera(camera)
                .setRayTracer(new BasicRayTracer(scene));
        Render render = renderBuilder.build();

        render.renderImage(scene);
        render.writeToImage();
    }

    /**
     * Produce a picture of a sphere lighted by a spot light
     */
    @Test
    public void twoSpheresOnMirrors() {
        Camera.CameraBuilder cameraBuilder =
                new Camera.CameraBuilder(
                        new Point3D(0, 0, 10000),
                        new Vector(0, 0, -1),
                        new Vector(0, 1, 0))
                        .setViewPlaneSize(2500, 2500)
                        .setDistance(10000);
        Camera camera = cameraBuilder.build();

        Geometries geometries = new Geometries();
        geometries.add( //
                new Sphere(
                        new Point3D(-950, -900, -1000), 400)
                        .setEmission(new Color(0, 0, 100))
                        .setMaterial(new Material().setKd(0.25).setKs(0.25).setNShininess(20).setKt(0.5)),
                new Sphere(
                        new Point3D(-950, -900, -1000), 200)
                        .setEmission(new Color(100, 20, 20))
                        .setMaterial(new Material().setKd(0.25).setKs(0.25).setNShininess(20)),
                new Triangle(
                        new Point3D(1500, -1500, -1500),
                        new Point3D(-1500, 1500, -1500),
                        new Point3D(670, 670, 3000))
                        .setEmission(new Color(20, 20, 20))
                        .setMaterial(new Material().setKr(1)),
                new Triangle(
                        new Point3D(1500, -1500, -1500),
                        new Point3D(-1500, 1500, -1500),
                        new Point3D(-1500, -1500, -2000))
                        .setEmission(new Color(20, 20, 20))
                        .setMaterial(new Material().setKr(0.5)));

        LinkedList<LightSource> lightSources = new LinkedList<>();
        lightSources.add(
                new SpotLight(
                        new Color(1020, 400, 400),
                        new Point3D(-750, -750, -150),
                        new Vector(-1, -1, -4))
                        .setKl(0.00001)
                        .setKq(0.000005));

        sceneBuilder.setGeometries(geometries)
                .setLights(lightSources)
                .setAmbientLight(new AmbientLight(new Color(255, 255, 255), 0.1));
        Scene scene = sceneBuilder.build();

        ImageWriter imageWriter = new ImageWriter("reflectionTwoSpheresMirrored", 500, 500);
        renderBuilder.setImageWriter(imageWriter)
                .setCamera(camera)
                .setRayTracer(new BasicRayTracer(scene));
        Render render = renderBuilder.build();

        render.renderImage(scene);
        render.writeToImage();
    }

    /**
     * Produce a picture of a two triangles lighted by a spot light with a partially
     * transparent Sphere producing partial shadow
     */
    @Test
    public void trianglesTransparentSphere() {
        Camera.CameraBuilder cameraBuilder =
                new Camera.CameraBuilder(
                        new Point3D(0, 0, 1000),
                        new Vector(0, 0, -1),
                        new Vector(0, 1, 0))
                        .setViewPlaneSize(200, 200)
                        .setDistance(1000);
        Camera camera = cameraBuilder.build();

        Geometries geometries = new Geometries();
        geometries.add( //
                new Triangle(
                        new Point3D(-150, -150, -115),
                        new Point3D(150, -150, -135),
                        new Point3D(75, 75, -150))
                        .setMaterial(new Material().setKd(0.5).setKs(0.5).setNShininess(60)),
                new Triangle(
                        new Point3D(-150, -150, -115),
                        new Point3D(-70, 70, -140),
                        new Point3D(75, 75, -150))
                        .setMaterial(new Material().setKd(0.5).setKs(0.5).setNShininess(60)),
                new Sphere(
                        new Point3D(60, 50, -50), 30)
                        .setEmission(new Color(java.awt.Color.BLUE))
                        .setMaterial(new Material().setKd(0.2).setKs(0.2).setNShininess(30).setKt(0.6)));

        LinkedList<LightSource> lightSources = new LinkedList<>();
        lightSources.add(
                new SpotLight(
                        new Color(700, 400, 400),
                        new Point3D(60, 50, 0),
                        new Vector(0, 0, -1))
                        .setKl(4E-5)
                        .setKq(2E-7));

        sceneBuilder.setGeometries(geometries)
                .setLights(lightSources)
                .setAmbientLight(new AmbientLight(new Color(java.awt.Color.WHITE), 0.15));
        Scene scene = sceneBuilder.build();

        ImageWriter imageWriter = new ImageWriter("refractionShadow", 600, 600);
        renderBuilder.setImageWriter(imageWriter).setCamera(camera).setRayTracer(new BasicRayTracer(scene));
        Render render = renderBuilder.build();

        render.renderImage(scene);
        render.writeToImage();
    }
}