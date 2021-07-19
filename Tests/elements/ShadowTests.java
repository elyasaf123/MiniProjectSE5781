package elements;

import org.junit.jupiter.api.*;
import geometries.*;
import primitives.*;
import renderer.*;
import scene.Scene;
import java.util.LinkedList;
import java.util.List;


public class ShadowTests {

    private Scene.SceneBuilder sceneBuilder = new Scene.SceneBuilder("Test scene");
    private Camera.CameraBuilder cameraBuilder =
            new Camera.CameraBuilder(
                    new Point3D(0, 0, 1000),
                    new Vector(0, 0, -1),
                    new Vector(0, 1, 0))
            .setViewPlaneSize(200, 200).setDistance(1000);
    private Camera camera = cameraBuilder.build();

    @Test
    public void sphereTriangleInitial() {
        Geometries geometries = new Geometries(
                new Sphere(
                        new Point3D(0, 0, -200), 60)
                        .setEmission(new Color(java.awt.Color.BLUE))
                        .setMaterial(new Material().setKd(0.5).setKs(0.5).setNShininess(30)),
                new Triangle(
                        new Point3D(-70, -40, 0),
                        new Point3D(-40, -70, 0),
                        new Point3D(-68, -68, -4))
                        .setEmission(new Color(java.awt.Color.BLUE))
                        .setMaterial(new Material().setKd(0.5).setKs(0.5).setNShininess(30))
        );

        sceneBuilder.setGeometries(geometries);
        Scene scene = sceneBuilder.build();
        scene.lights.add(
                new SpotLight(
                        new Color(400, 240, 0),
                        new Point3D(-100, -100, 200),
                        new Vector(1, 1, -3))
                        .setKl(1E-5).setKq(1.5E-7));

        Render render = new Render();
        render.setImageWriter(new ImageWriter("shadowSphereTriangleInitial", 400, 400))
                .setCamera(camera)
                .setRayTracer(new BasicRayTracer(scene));

        render.renderImage();
        render.writeToImage();
    }

    /**
     * Produce a picture of a two triangles lighted by a spot light with a Sphere
     * producing a shading
     */
    @Test
    public void trianglesSphere() {
        sceneBuilder.setAmbientLight(new AmbientLight(new Color(java.awt.Color.WHITE), 0.15));
        Geometries geometries = new Geometries(
                new Triangle(
                        new Point3D(-150, -150, -115),
                        new Point3D(150, -150, -135),
                        new Point3D(75, 75, -150))
                        .setMaterial(new Material().setKs(0.8).setNShininess(60)),
                new Triangle(
                        new Point3D(-150, -150, -115),
                        new Point3D(-70, 70, -140),
                        new Point3D(75, 75, -150))
                        .setMaterial(new Material().setKs(0.8).setNShininess(60)),
                new Sphere(new Point3D(0, 0, -115), 30)
                        .setEmission(new Color(java.awt.Color.BLUE))
                        .setMaterial(new Material().setKd(0.5).setKs(0.5).setNShininess(30))
        );
        sceneBuilder.setGeometries(geometries);
        List<LightSource> lights = new LinkedList<LightSource>();
        lights.add(//
                new SpotLight(
                        new Color(700, 400, 400),
                        new Point3D(40, 40, 115),
                        new Vector(-1, -1, -4))
                        .setKl(4E-4).setKq(2E-5));
        sceneBuilder.setGeometries(geometries);

        sceneBuilder.setLights(lights);
        Scene scene  = sceneBuilder.build();

        Render render = new Render();
        render.setImageWriter(new ImageWriter("shadowTrianglesSphere", 600, 600))
                .setCamera(camera)
                .setRayTracer(new BasicRayTracer(scene));
        render.renderImage();
        render.writeToImage();
    }

    @Test
    public void sphereTriangleCloserTriangle() {
        Geometries geometries = new Geometries(
                new Sphere(
                        new Point3D(0, 0, -200), 60)
                        .setEmission(new Color(java.awt.Color.BLUE))
                        .setMaterial(new Material().setKd(0.5).setKs(0.5).setNShininess(30)),
                new Triangle(
                        new Point3D(-60, -30, 0),
                        new Point3D(-30, -60, 0),
                        new Point3D(-58, -58, -4))
                        .setEmission(new Color(java.awt.Color.BLUE))
                        .setMaterial(new Material().setKd(0.5).setKs(0.5).setNShininess(30))
        );

        sceneBuilder.setGeometries(geometries);
        Scene scene = sceneBuilder.build();
        scene.lights.add(
                new SpotLight(
                        new Color(400, 240, 0),
                        new Point3D(-100, -100, 200),
                        new Vector(1, 1, -3))
                        .setKl(1E-5).setKq(1.5E-7));

        Render render = new Render();
        render.setImageWriter(new ImageWriter("shadowSphereTriangleCloserTriangle", 400, 400))
                .setCamera(camera)
                .setRayTracer(new BasicRayTracer(scene));

        render.renderImage();
        render.writeToImage();
    }

    @Test
    public void sphereTriangleLinkedTriangle() {
        Geometries geometries = new Geometries(
                new Sphere(
                        new Point3D(0, 0, -200), 60)
                        .setEmission(new Color(java.awt.Color.BLUE))
                        .setMaterial(new Material().setKd(0.5).setKs(0.5).setNShininess(30)),
                new Triangle(
                        new Point3D(-49, -19, 0),
                        new Point3D(-19, -49, 0),
                        new Point3D(-47, -47, -4))
                        .setEmission(new Color(java.awt.Color.BLUE))
                        .setMaterial(new Material().setKd(0.5).setKs(0.5).setNShininess(30))
        );

        sceneBuilder.setGeometries(geometries);
        Scene scene = sceneBuilder.build();
        scene.lights.add(
                new SpotLight(
                        new Color(400, 240, 0),
                        new Point3D(-100, -100, 200),
                        new Vector(1, 1, -3))
                        .setKl(1E-5).setKq(1.5E-7));

        Render render = new Render();
        render.setImageWriter(new ImageWriter("shadowSphereTriangleLinkedTriangle", 400, 400))
                .setCamera(camera)
                .setRayTracer(new BasicRayTracer(scene));

        render.renderImage();
        render.writeToImage();
    }

    @Test
    public void sphereTriangleCloserSpot() {
        Geometries geometries = new Geometries(
                new Sphere(
                        new Point3D(0, 0, -200), 60)
                        .setEmission(new Color(java.awt.Color.BLUE))
                        .setMaterial(new Material().setKd(0.5).setKs(0.5).setNShininess(30)),
                new Triangle(
                        new Point3D(-70, -40, 0),
                        new Point3D(-40, -70, 0),
                        new Point3D(-68, -68, -4))
                        .setEmission(new Color(java.awt.Color.BLUE))
                        .setMaterial(new Material().setKd(0.5).setKs(0.5).setNShininess(30))
        );

        sceneBuilder.setGeometries(geometries);
        Scene scene = sceneBuilder.build();
        scene.lights.add(
                new SpotLight(
                        new Color(400, 240, 0),
                        new Point3D(-90, -90, 200),
                        new Vector(1, 1, -3))
                        .setKl(1E-5).setKq(1.5E-7));

        Render render = new Render();
        render.setImageWriter(new ImageWriter("shadowSphereTriangleCloserSpot", 400, 400))
                .setCamera(camera)
                .setRayTracer(new BasicRayTracer(scene));

        render.renderImage();
        render.writeToImage();
    }

    @Test
    public void sphereTriangleLinkedSpot() {
        Geometries geometries = new Geometries(
                new Sphere(
                        new Point3D(0, 0, -200), 60)
                        .setEmission(new Color(java.awt.Color.BLUE))
                        .setMaterial(new Material().setKd(0.5).setKs(0.5).setNShininess(30)),
                new Triangle(
                        new Point3D(-70, -40, 0),
                        new Point3D(-40, -70, 0),
                        new Point3D(-68, -68, -4))
                        .setEmission(new Color(java.awt.Color.BLUE))
                        .setMaterial(new Material().setKd(0.5).setKs(0.5).setNShininess(30))
        );

        sceneBuilder.setGeometries(geometries);
        Scene scene = sceneBuilder.build();
        scene.lights.add(
                new SpotLight(
                        new Color(400, 240, 0),
                        new Point3D(-73, -73, 56),
                        new Vector(1, 1, -3))
                        .setKl(1E-5).setKq(1.5E-7));

        Render render = new Render();
        render.setImageWriter(new ImageWriter("shadowSphereTriangleLinkedSpot", 400, 400))
                .setCamera(camera)
                .setRayTracer(new BasicRayTracer(scene));

        render.renderImage();
        render.writeToImage();
    }
}