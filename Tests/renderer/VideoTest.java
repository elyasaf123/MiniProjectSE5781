package renderer;

import elements.*;
import geometries.*;
import org.junit.jupiter.api.*;
import primitives.*;
import scene.*;
import java.awt.image.*;
import java.io.*;
import java.util.LinkedList;
import java.util.List;

class VideoTest {

    private Scene.SceneBuilder sceneBuilder = new Scene.SceneBuilder("Test scene");
    private RenderThread.RenderBuilder renderBuilder = new RenderThread.RenderBuilder().setMultithreading(3).setPrintPercent(true);

    private Camera.CameraBuilder cameraBuilder =
            new Camera.CameraBuilder(
                    new Point3D(0, 0, 1000),
                    new Vector(0, 0, -1),
                    new Vector(0, 1, 0))
                    .setViewPlaneSize(200, 200).setDistance(1000);

    private Camera camera = cameraBuilder.build();

    @Test
    void generateVideo() throws IOException {
        BufferedImage[] a = new BufferedImage[10];

        for(int i = 0; i< 10; i++){
            Geometries geometries = new Geometries(
                    new Sphere(
                            new Point3D(0, 0, -200), 60 + i*10)
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

            Render.RenderBuilder renderBuilder = new Render.RenderBuilder().
                    setImageWriter(new ImageWriter("shadowSphereTriangleLinkedSpot", 400, 400))
                    .setCamera(camera)
                    .setRayTracer(new BasicRayTracer(scene));
            Render render = renderBuilder.build();

            render.renderImage();
            a[i] = render.getImage();
        }
        Video.generateVideo("first video",a,2);
    }

    /**
     * Produce a picture of some shapes lighted by some lights
     */
    @Test
    public void ex_11_our_picture() throws IOException {

        BufferedImage[] a = new BufferedImage[10];

        Camera.CameraBuilder cameraBuilder =
                new Camera.CameraBuilder(
                        new Point3D(0, 1000, 20),
                        new Vector(0, -1, 0),
                        new Vector(0, 0, 1))
                        .setViewPlaneSize(200, 200)
                        .setDistance(700);
        Camera camera = cameraBuilder.build();

        int legRadius = 3;
        List<Geometry> firstLeg = new Rectangle
                (new Point3D(20,40,0),
                        new Point3D(19,40,0),
                        new Point3D(19,39,0),
                        new Point3D(20,39,0)).getCube(-12);
        List<Geometry> secondLeg = new Rectangle
                (new Point3D(20,10,0),
                        new Point3D(19,10,0),
                        new Point3D(19,9,0),
                        new Point3D(20,9,0)).getCube(-12);
        List<Geometry> thirdLeg = new Rectangle
                (new Point3D(-20,10,0),
                        new Point3D(-20,9,0),
                        new Point3D(-19,9,0),
                        new Point3D(-19,10,0)).getCube(-12);
        List<Geometry> fourthLeg = new Rectangle
                (new Point3D(-20,40,0),
                        new Point3D(-20,39,0),
                        new Point3D(-19,39,0),
                        new Point3D(-19,40,0)).getCube(-12);


        List<Geometry> cover1 = new Rectangle
                (new Point3D(10,20,0),
                        new Point3D(-2,20,0),
                        new Point3D(-2,15,0),
                        new Point3D(10,15,0)).getCube(0.8);

        List<Geometry> board = new Rectangle
                (new Point3D(20,40,0),
                        new Point3D(20,9,0),
                        new Point3D(-20,9,0),
                        new Point3D(-20,40,0)).getCube(1);

        Color brown = new Color(23,13,2);
        Geometries geometries = new Geometries();

        for(int i = 0; i< 10; i++){
            List<Geometry> pages = new LinkedList<>();
            for(int j = 0; j< 10; j++){
                pages.addAll( new Rectangle
                        (new Point3D(8,20, 1 + 0.1*i*j),
                                new Point3D(-1.5,20,1 + 0.1*j),
                                new Point3D(-1.5,15,1 + 0.1*j),
                                new Point3D(8,15,1 + 0.1*i*j)).getCube(0.08));
            }

            List<Geometry> cover2 = new Rectangle
                    (new Point3D(10,20,1 + i),
                            new Point3D(-2,20,1),
                            new Point3D(-2,15,1),
                            new Point3D(10,15,1 + i)).getCube(0.8);

            geometries.add(
//                //################        TABLE            #####################
                    firstLeg.get(0)
                            .setEmission(brown)
                            .setMaterial(new Material().setKd(1).setKs(0).setNShininess(50)),
                    firstLeg.get(1)
                            .setEmission(brown)
                            .setMaterial(new Material().setKd(1).setKs(0).setNShininess(50)),
                    firstLeg.get(2)
                            .setEmission(brown)
                            .setMaterial(new Material().setKd(1).setKs(0).setNShininess(50)),

                    firstLeg.get(3)
                            .setEmission(brown)
                            .setMaterial(new Material().setKd(1).setKs(0).setNShininess(50)),
                    firstLeg.get(4)
                            .setEmission(brown)
                            .setMaterial(new Material().setKd(1).setKs(0).setNShininess(50)),
                    firstLeg.get(5)
                            .setEmission(brown)
                            .setMaterial(new Material().setKd(1).setKs(0).setNShininess(50)),

                    secondLeg.get(0)
                            .setEmission(brown)
                            .setMaterial(new Material().setKd(1).setKs(0).setNShininess(50)),
                    secondLeg.get(1)
                            .setEmission(brown)
                            .setMaterial(new Material().setKd(1).setKs(0).setNShininess(50)),
                    secondLeg.get(2)
                            .setEmission(brown)
                            .setMaterial(new Material().setKd(1).setKs(0).setNShininess(50)),
                    secondLeg.get(3)
                            .setEmission(brown)
                            .setMaterial(new Material().setKd(1).setKs(0).setNShininess(50)),
                    secondLeg.get(4)
                            .setEmission(brown)
                            .setMaterial(new Material().setKd(1).setKs(0).setNShininess(50)),
                    secondLeg.get(5)
                            .setEmission(brown)
                            .setMaterial(new Material().setKd(1).setKs(0).setNShininess(50)),

                    thirdLeg.get(0)
                            .setEmission(brown)
                            .setMaterial(new Material().setKd(1).setKs(0).setNShininess(50)),
                    thirdLeg.get(1)
                            .setEmission(brown)
                            .setMaterial(new Material().setKd(1).setKs(0).setNShininess(50)),
                    thirdLeg.get(2)
                            .setEmission(brown)
                            .setMaterial(new Material().setKd(1).setKs(0).setNShininess(50)),
                    thirdLeg.get(3)
                            .setEmission(brown)
                            .setMaterial(new Material().setKd(1).setKs(0).setNShininess(50)),
                    thirdLeg.get(4)
                            .setEmission(brown)
                            .setMaterial(new Material().setKd(1).setKs(0).setNShininess(50)),
                    thirdLeg.get(5)
                            .setEmission(brown)
                            .setMaterial(new Material().setKd(1).setKs(0).setNShininess(50)),

                    fourthLeg.get(0)
                            .setEmission(brown)
                            .setMaterial(new Material().setKd(1).setKs(0).setNShininess(50)),
                    fourthLeg.get(1)
                            .setEmission(brown)
                            .setMaterial(new Material().setKd(1).setKs(0).setNShininess(50)),
                    fourthLeg.get(2)
                            .setEmission(brown)
                            .setMaterial(new Material().setKd(1).setKs(0).setNShininess(50)),
                    fourthLeg.get(3)
                            .setEmission(brown)
                            .setMaterial(new Material().setKd(1).setKs(0).setNShininess(50)),
                    fourthLeg.get(4)
                            .setEmission(brown)
                            .setMaterial(new Material().setKd(1).setKs(0).setNShininess(50)),
                    fourthLeg.get(5)
                            .setEmission(brown)
                            .setMaterial(new Material().setKd(1).setKs(0).setNShininess(50)),

                    board.get(0)
                            .setEmission(brown)
                            .setMaterial(new Material().setKd(1).setKs(0).setNShininess(50)),
                    board.get(1)
                            .setEmission(brown)
                            .setMaterial(new Material().setKd(1).setKs(0).setNShininess(50)),
                    board.get(2)
                            .setEmission(brown)
                            .setMaterial(new Material().setKd(1).setKs(0).setNShininess(50)),
                    board.get(3)
                            .setEmission(brown)
                            .setMaterial(new Material().setKd(1).setKs(0).setNShininess(50)),
                    board.get(4)
                            .setEmission(brown)
                            .setMaterial(new Material().setKd(1).setKs(0).setNShininess(50)),
                    board.get(5)
                            .setEmission(brown)
                            .setMaterial(new Material().setKd(1).setKs(0).setNShininess(50)),

                    cover1.get(0)
                            .setEmission(new Color(0,0,0))
                            .setMaterial(new Material().setKd(1).setKs(0).setNShininess(50)),
                    cover1.get(1)
                            .setEmission(new Color(0,0,0))
                            .setMaterial(new Material().setKd(1).setKs(0).setNShininess(50)),
                    cover1.get(2)
                            .setEmission(new Color(0,0,0))
                            .setMaterial(new Material().setKd(1).setKs(0).setNShininess(50)),
                    cover1.get(3)
                            .setEmission(new Color(0,0,0))
                            .setMaterial(new Material().setKd(1).setKs(0).setNShininess(50)),
                    cover1.get(4)
                            .setEmission(new Color(0,0,0))
                            .setMaterial(new Material().setKd(1).setKs(0).setNShininess(50)),
                    cover1.get(5)
                            .setEmission(new Color(0,0,0))
                            .setMaterial(new Material().setKd(1).setKs(0).setNShininess(50)),

//                    cover2.get(0)
//                            .setEmission(new Color(0,0,0))
//                            .setMaterial(new Material().setKd(1).setKs(0).setNShininess(50)),
//                    cover2.get(1)
//                            .setEmission(new Color(0,0,0))
//                            .setMaterial(new Material().setKd(1).setKs(0).setNShininess(50)),
//                    cover2.get(2)
//                            .setEmission(new Color(0,0,0))
//                            .setMaterial(new Material().setKd(1).setKs(0).setNShininess(50)),
//                    cover2.get(3)
//                            .setEmission(new Color(0,0,0))
//                            .setMaterial(new Material().setKd(1).setKs(0).setNShininess(50)),
//                    cover2.get(4)
//                            .setEmission(new Color(0,0,0))
//                            .setMaterial(new Material().setKd(1).setKs(0).setNShininess(50)),
//                    cover2.get(5)
//                            .setEmission(new Color(0,0,0))
//                            .setMaterial(new Material().setKd(1).setKs(0).setNShininess(50)),

                    new Sphere(new Point3D(-2,19.5,0.5),1)
                            .setEmission(new Color(255,0,0))
                            .setMaterial(new Material().setKd(1).setKs(0).setNShininess(50)),
                    new Sphere(new Point3D(-2,18.5,0.5),1)
                            .setEmission(new Color(255,0,0))
                            .setMaterial(new Material().setKd(1).setKs(0).setNShininess(50)),
                    new Sphere(new Point3D(-2,17.5,0.5),1)
                            .setEmission(new Color(255,0,0))
                            .setMaterial(new Material().setKd(1).setKs(0).setNShininess(50)),
                    new Sphere(new Point3D(-2,16.5,0.5),1)
                            .setEmission(new Color(255,0,0))
                            .setMaterial(new Material().setKd(1).setKs(0).setNShininess(50)),
                    new Sphere(new Point3D(-2,15.5,0.5),1)
                            .setEmission(new Color(255,0,0))
                            .setMaterial(new Material().setKd(1).setKs(0).setNShininess(50)),

                    /////////           WALLS                 ///////////

                    //      FLOOR
                    new Polygon(
                            new Point3D(100,-40,-14),
                            new Point3D(-100,-40,-14),
                            new Point3D(-100,100,-14),
                            new Point3D(100,100,-14))
                            .setEmission(new Color(0,0,74))
                            .setMaterial(new Material().setKd(1).setKs(0).setNShininess(50)),
                    new Polygon(
                            new Point3D(100,-40,100),
                            new Point3D(-100,-40,100),
                            new Point3D(-100,100,100),
                            new Point3D(100,100,100))
                            .setEmission(new Color(3,0,0))
                            .setMaterial(new Material().setKd(1).setKs(0).setNShininess(50)),
                    new Polygon(
                            new Point3D(100,-40,-14),
                            new Point3D(100,100,-14),
                            new Point3D(100,100,100),
                            new Point3D(100,-40,100))
                            .setEmission(new Color(0,67,0))
                            .setMaterial(new Material().setKd(1).setKs(0).setNShininess(50)),
                    new Polygon(
                            new Point3D(-100,-40,-14),
                            new Point3D(-100,100,-14),
                            new Point3D(-100,100,100),
                            new Point3D(-100,-40,100))
                            .setEmission(new Color(0,67,0))
                            .setMaterial(new Material().setKd(1).setKs(0).setNShininess(50)),
                    new Polygon(
                            new Point3D(100,-40,-14),
                            new Point3D(-100,-40,-14),
                            new Point3D(-100,-40,100),
                            new Point3D(100, -40, 100)).
                            setEmission(new Color(3,67,74))
                            .setMaterial(new Material()),

                    //MIRROR
                    new Polygon(
                            new Point3D(60,-39,-8),
                            new Point3D(-60,-39,-8),
                            new Point3D(-60,-39,50),
                            new Point3D(60, -39, 50)).
                            setEmission(new Color(0, 134, 0))
                            .setMaterial(new Material().setKs(0.8).setNShininess(180))
            );

            for(int p = 0; p< 60; p++){
                geometries.add(  pages.get(p)
                        .setEmission(new Color(255, 255, 255))
                        .setMaterial(new Material().setKd(1).setKs(0).setNShininess(50)));
            }

            LinkedList<LightSource> lightSources = new LinkedList<>();

            lightSources.add(
                    new PointLight(
                            new Color(100,60, 0),
                            new Point3D(40, 20, 50)));

            sceneBuilder.setGeometries(geometries)
                    .setLights(lightSources)
                    .setAmbientLight(new AmbientLight(new Color(java.awt.Color.WHITE), 0.1));
            Scene scene = sceneBuilder.build();

            Render.RenderBuilder renderBuilder = new Render.RenderBuilder().
                    setImageWriter(new ImageWriter("shadowSphereTriangleLinkedSpot", 400, 400))
                    .setCamera(camera)
                    .setRayTracer(new BasicRayTracer(scene));
            Render render = renderBuilder.build();

            render.renderImage();
            a[i] = render.getImage();
            render.renderImage();
            a[i] = render.getImage();
        }
        Video.generateVideo("first video",a,2);
    }
}