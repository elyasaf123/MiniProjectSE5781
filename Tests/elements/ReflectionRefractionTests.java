package elements;

import org.junit.jupiter.api.*;
import geometries.*;
import primitives.*;
import renderer.*;
import scene.*;
import java.util.LinkedList;
import java.util.List;

/**
 * Tests for reflection and transparency functionality, test for partial shadows
 * (with transparency)
 */
public class ReflectionRefractionTests {
    private Scene.SceneBuilder sceneBuilder = new Scene.SceneBuilder("Test scene");
    private RenderThread.RenderBuilder renderBuilder = new RenderThread.RenderBuilder().setMultithreading(3).setPrintPercent(true);

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
        RenderThread render = renderBuilder.build();

        render.renderImage();
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
        RenderThread render = renderBuilder.build();

        render.renderImage();
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
        RenderThread render = renderBuilder.build();

        render.renderImage();
        render.writeToImage();
    }


    /**
     * Produce a picture of some shapes lighted by some lights
     */
    @Test
    public void ex_7_our_picture() {
        Camera.CameraBuilder cameraBuilder =
                new Camera.CameraBuilder(
                        new Point3D(0, 0, 900),
                        new Vector(0, 0, -1),
                        new Vector(0, 1, 0))
                        .setViewPlaneSize(200, 200)
                        .setDistance(1000);
        Camera camera = cameraBuilder.build();

        Geometries geometries = new Geometries();
        geometries.add(
                new Triangle(
                        new Point3D(-150, -150, -115),
                        new Point3D(150, -150, -135),
                        new Point3D(75, 75, -150))
                        .setMaterial(new Material().setKd(0.5).setKs(0.5).setNShininess(60)),
                new Polygon(
                        new Point3D(-10, -40, 0),
                        new Point3D(10, -40, 0),
                        new Point3D(10, -50, 0),
                        new Point3D(-10, -50, 0))
                        .setMaterial(new Material().setKd(0.5).setKs(0.5).setNShininess(60))
                        .setEmission(new Color(java.awt.Color.red))
                        .setMaterial(new Material().setKt(0.9).setKr(0).setKd(1)),
                new Polygon(
                        new Point3D(-20, -60, 0),
                        new Point3D(20, -60, 0),
                        new Point3D(20, -70, 0),
                        new Point3D(-20, -70, 0))
                        .setMaterial(new Material().setKd(0.5).setKs(0.5).setNShininess(60))
                        .setEmission(new Color(java.awt.Color.blue))
                        .setMaterial(new Material().setKt(0.9).setKr(0).setKd(1)),
                new Polygon(
                        new Point3D(-30, -80, 0),
                        new Point3D(30, -80, 0),
                        new Point3D(30, -90, 0),
                        new Point3D(-30, -90, 0))
                        .setMaterial(new Material().setKd(0.5).setKs(0.5).setNShininess(60))
                        .setEmission(new Color(java.awt.Color.darkGray))
                        .setMaterial(new Material().setKt(0.9).setKr(0).setKd(1)),
                new Polygon(
                        new Point3D(-8, -5, 0),
                        new Point3D(8, -5, 0),
                        new Point3D(12, -17.5, 0),
                        new Point3D(8, -30, 0),
                        new Point3D(-8, -30, 0),
                        new Point3D(-12, -17.5, 0))
                        .setMaterial(new Material().setKd(0.5).setKs(0.5).setNShininess(60))
                        .setEmission(new Color(java.awt.Color.blue))
                        .setMaterial(new Material().setKt(0.9).setKr(0).setKd(1)),
                new Polygon(
                        new Point3D(50, 0, 0),
                        new Point3D(-50, 0, 0),
                        new Point3D(-200, -500, 0),
                        new Point3D(200, -500, 0))
                        .setMaterial(new Material().setKd(0.5).setKs(0.5).setNShininess(60))
                        .setEmission(new Color(java.awt.Color.cyan))
                        .setMaterial(new Material().setKt(0.9).setKr(0.6).setKd(0.4)),
                new Sphere(
                        new Point3D(60,50,-50), 30)
                        .setEmission(new Color(java.awt.Color.DARK_GRAY))
                        .setMaterial(new Material().setKd(0.2).setKs(0.2).setNShininess(30).setKt(0.6)),
                new Sphere(
                        new Point3D(-67,50,-50), 30)
                        .setEmission(new Color(java.awt.Color.DARK_GRAY))
                        .setMaterial(new Material().setKd(0.2).setKs(0.2).setNShininess(30).setKt(0.6)),
                new Cylinder(
                        new Ray(new Point3D(0,0,0), new Vector(0,-1,-20)), 10, 10)
                            .setEmission(new Color(java.awt.Color.red))
                            .setMaterial(new Material().setNShininess(6).setKs(0.1).setKd(0.8).setKt(0.8)),
                new Triangle(
                        new Point3D(-150, -150, -115),
                        new Point3D(150, -150, -135),
                        new Point3D(-75, 75, -150))
                        .setEmission(new Color(java.awt.Color.GREEN))
                        .setMaterial(new Material().setKd(0.5).setKs(0.5).setNShininess(60)),
                new Tube(
                        new Ray(new Point3D(75, 75, -150), new Vector(0,0,1)), 20)
                        .setEmission(new Color(java.awt.Color.ORANGE))
                        .setMaterial(new Material().setKt(0.2).setKs(0.4).setKd(0.5).setNShininess(36)),
                new Tube(
                        new Ray(new Point3D(75, 75, -150), new Vector(0,0,-100)), 20)
                        .setEmission(new Color(java.awt.Color.BLACK))
                        .setMaterial(new Material().setKt(0.2).setKs(0.4).setKd(0.5).setNShininess(36)),
                new Tube(
                        new Ray(new Point3D(-75, -75, -150), new Vector(0,0,1)), 20)
                        .setEmission(new Color(java.awt.Color.cyan))
                        .setMaterial(new Material().setKt(0.2).setKs(0.4).setKd(0.5).setNShininess(36)));

        LinkedList<LightSource> lightSources = new LinkedList<>();
        lightSources.add(
                new SpotLight(
                        new Color(700, 400, 400),
                        new Point3D(60, 50, 0),
                        new Vector(0, 0, -1))
                        .setKl(4E-5)
                        .setKq(2E-7));
        lightSources.add(
                new PointLight(
                        new Color(java.awt.Color.gray),
                        new Point3D(0,220,30))
                        .setKl(0.0006)
                        .setKq(0.003));
        lightSources.add(
                new DirectionalLight(
                        new Color(java.awt.Color.PINK),
                        new Vector(0,1,1)));

        sceneBuilder.setGeometries(geometries)
                .setLights(lightSources)
                .setAmbientLight(new AmbientLight(new Color(java.awt.Color.WHITE), 0.1));
        Scene scene = sceneBuilder.build();

        ImageWriter imageWriter = new ImageWriter("ex_7_our_picture", 600, 600);
        renderBuilder.setImageWriter(imageWriter).setCamera(camera).setRayTracer(new BasicRayTracer(scene));
        RenderThread render = renderBuilder.build();

        render.renderImage();
        render.writeToImage();
    }

    /**
     * Produce a picture of some shapes lighted by some lights
     */
    @Test
    public void ex_8_our_picture() {
        Camera.CameraBuilder cameraBuilder =
                new Camera.CameraBuilder(
                        new Point3D(0, 0, 1000),
                        new Vector(0, 0, -1),
                        new Vector(0, 1, 0))
                        .setViewPlaneSize(200, 200)
                        .setDistance(1000);
        Camera camera = cameraBuilder.build();

        Geometries geometries = new Geometries();
        geometries.add(
                new Cylinder(
                        new Ray(new Point3D(30,30,0),new Vector(0,0,1)),
                        10,
                        80)
                .setEmission(new Color(java.awt.Color.BLUE)),
                new Cylinder(
                        new Ray(new Point3D(30,-30,0),new Vector(0,1,0)),
                        10,
                        80)
                        .setEmission(new Color(java.awt.Color.BLUE)),
                new Cylinder(
                        new Ray(new Point3D(-30,-30,0),new Vector(0,1,0)),
                        10,
                        80)
                        .setEmission(new Color(java.awt.Color.BLUE)),
                new Cylinder(
                        new Ray(new Point3D(-30,30,0),new Vector(0,1,0)),
                        10,
                        80)
                        .setEmission(new Color(java.awt.Color.BLUE)),
                new Polygon(
                        new Point3D(30, 30, 80),
                        new Point3D(30, -30, 80),
                        new Point3D(-30, -30, 80),
                        new Point3D(-30, 30, 80))
            .setEmission(new Color(java.awt.Color.RED)));
        LinkedList<LightSource> lightSources = new LinkedList<>();
//        lightSources.add(
//                new SpotLight(
//                        new Color(700, 400, 400),
//                        new Point3D(60, 50, 0),
//                        new Vector(0, 0, -1))
//                        .setKl(4E-5)
//                        .setKq(2E-7));
//        lightSources.add(
//                new PointLight(
//                        new Color(java.awt.Color.gray),
//                        new Point3D(0,220,30))
//                        .setKl(0.0006)
//                        .setKq(0.003));
        lightSources.add(
                new DirectionalLight(
                        new Color(java.awt.Color.WHITE),
                        new Vector(0,-1,-1)));

        sceneBuilder.setGeometries(geometries)
                .setLights(lightSources)
                .setAmbientLight(new AmbientLight(new Color(java.awt.Color.WHITE), 0.1));
        Scene scene = sceneBuilder.build();

        ImageWriter imageWriter = new ImageWriter("ex_8_our_picture", 600, 600);
        renderBuilder.setImageWriter(imageWriter).setCamera(camera).setRayTracer(new BasicRayTracer(scene));
        RenderThread render = renderBuilder.build();

        render.renderImage();
        render.writeToImage();
    }

    /**
     * Produce a picture of some shapes lighted by some lights
     */
    @Test
    public void ex_9_our_picture() {
        Camera.CameraBuilder cameraBuilder =
                new Camera.CameraBuilder(
                        new Point3D(30, 1000, 50),
                        new Vector(0, -1, 0),
                        new Vector(0, 0, 1))
                        .setViewPlaneSize(200, 200)
                        .setDistance(700);
        Camera camera = cameraBuilder.build();
        int legRadius = 3;

        Color brown = new Color(23,13,2);
        Geometries geometries = new Geometries();
        geometries.add(

//////////////////         TABLE                ////////////////////////

//################         LEGS                 ############################
                new Sphere(
                        new Point3D(20,10,0),
                        legRadius)
                        .setEmission(new Color(0,0,500))
                        .setMaterial(new Material().setKd(0.5).setKs(0.5).setNShininess(30)),
                new Sphere(
                        new Point3D(20,-10,0),
                        legRadius)
                        .setEmission(new Color(0,0,500))
                        .setMaterial(new Material().setKd(0.5).setKs(0.5).setNShininess(30)),
                new Sphere(
                        new Point3D(-20,-10,0),
                        legRadius)
                        .setEmission(new Color(0,0,500))
                        .setMaterial(new Material().setKd(0.5).setKs(0.5).setNShininess(30)),
                new Sphere(
                        new Point3D(-20,10,0),
                        legRadius)
                        .setEmission(new Color(0,0,500))
                        .setMaterial(new Material().setKd(0.5).setKs(0.5).setNShininess(30)),
                new Sphere(
                        new Point3D(20,10,-4),
                        legRadius)
                        .setEmission(brown)
                        .setMaterial(new Material().setKd(0.5).setKs(0.5).setNShininess(30)),
                new Sphere(
                        new Point3D(20,-10,-4),
                        legRadius)
                        .setEmission(brown)
                        .setMaterial(new Material().setKd(0.5).setKs(0.5).setNShininess(30)),
                new Sphere(
                        new Point3D(-20,-10,-4),
                        legRadius)
                        .setEmission(brown)
                        .setMaterial(new Material().setKd(0.5).setKs(0.5).setNShininess(30)),
                new Sphere(
                        new Point3D(-20,10,-4),
                        legRadius)
                        .setEmission(brown)
                        .setMaterial(new Material().setKd(0.5).setKs(0.5).setNShininess(30)),
                new Sphere(
                        new Point3D(20,10,-8),
                        legRadius)
                        .setEmission(brown)
                        .setMaterial(new Material().setKd(0.5).setKs(0.5).setNShininess(30)),
                new Sphere(
                        new Point3D(20,-10,-8),
                        legRadius)
                        .setEmission(brown)
                        .setMaterial(new Material().setKd(0.5).setKs(0.5).setNShininess(30)),
                new Sphere(
                        new Point3D(-20,-10,-8),
                        legRadius)
                        .setEmission(brown)
                        .setMaterial(new Material().setKd(0.5).setKs(0.5).setNShininess(30)),
                new Sphere(
                        new Point3D(-20,10,-8),
                        legRadius)
                        .setEmission(brown)
                        .setMaterial(new Material().setKd(0.5).setKs(0.5).setNShininess(30)),
                new Sphere(
                        new Point3D(20,10,-12),
                        legRadius)
                        .setEmission(brown)
                        .setMaterial(new Material().setKd(0.5).setKs(0.5).setNShininess(30)),
                new Sphere(
                        new Point3D(20,-10,-12),
                        legRadius)
                        .setEmission(brown)
                        .setMaterial(new Material().setKd(0.5).setKs(0.5).setNShininess(30)),
                new Sphere(
                        new Point3D(-20,-10,-12),
                        legRadius)
                        .setEmission(brown)
                        .setMaterial(new Material().setKd(0.5).setKs(0.5).setNShininess(30)),
                new Sphere(
                        new Point3D(-20,10,-12),
                        legRadius)
                        .setEmission(brown)
                        .setMaterial(new Material().setKd(0.5).setKs(0.5).setNShininess(30)),


//##################    BOARD   ######################################
//                ((Rectangle)new Rectangle
//                        (new Point3D(40,20,3),
//                        new Point3D(40,-20,3),
//                        new Point3D(-40,-20,3),
//                        new Point3D(-40,20,3))
//                        .setEmission(brown)
//                        .setMaterial(new Material().setKd(1).setKs(0).setNShininess(50))).getCube(1),




                /////////           WALLS                 ///////////



                //      FLOOR
               new Polygon(
                       new Point3D(100,-40,-14),
                       new Point3D(-100,-40,-14),
                       new Point3D(-100,100,-14),
                       new Point3D(100,100,-14))
                       .setEmission(new Color(java.awt.Color.gray))
                        .setMaterial(new Material().setKd(0.5).setKs(0.5).setNShininess(30).setKr(1)),


                new Polygon(
                        new Point3D(100,-40,-14),
                        new Point3D(-100,-40,-14),
                        new Point3D(-100,-40,100),
                        new Point3D(100, -40, 100)).
                        setEmission(new Color(3,67,74))
                        .setMaterial(new Material()),

                new Polygon(
                        new Point3D(100,-40,-14),
                        new Point3D(100,40,-14),
                        new Point3D(100,40,100),
                        new Point3D(100, -40, 100)).
                        setEmission(new Color(3,67,74))
                        .setMaterial(new Material()),



                new Polygon(
                        new Point3D(5,5,3.5),
                        new Point3D(5,-5,3.5),
                        new Point3D(-5,-5,3.5),
                        new Point3D(-5,5,3.5))
                        .setEmission(new Color(2,72,104))
                        ,
                new Polygon(
                        new Point3D(5,5,4),
                        new Point3D(5,-5,4),
                        new Point3D(-5,-5,4),
                        new Point3D(-5,5,4))
                        .setEmission(new Color(2,72,104))
                        ,
                new Polygon(
                        new Point3D(5,5,3.5),
                        new Point3D(5,5,4),
                        new Point3D(-5,5,4),
                        new Point3D(-5,5,3.5))
                        .setEmission(new Color(2,72,104))
                        ,
                new Polygon(
                        new Point3D(5,-5,3.5),
                        new Point3D(5,-5,4),
                        new Point3D(-5,-5,4),
                        new Point3D(-5,-5,3.5))
                        .setEmission(new Color(2,72,104))
                        ,

                new Polygon(
                        new Point3D(5,5,4),
                        new Point3D(5,-2,10),
                        new Point3D(-5,-2,10),
                        new Point3D(-5,5,4))
                        .setEmission(new Color(2,72,104)),

                //MIRROR
                new Polygon(
                        new Point3D(60,-39,0),
                        new Point3D(-60,-39,0),
                        new Point3D(-60,-39,50),
                        new Point3D(60, -39, 50)).
                        setEmission(new Color(192, 192, 192))
                        .setMaterial(new Material().setKr(1).setKs(0.8).setNShininess(180)));
        LinkedList<LightSource> lightSources = new LinkedList<>();



        lightSources.add(
                new SpotLight(
                        new Color(100,60, 0),
                        new Point3D(80, 20, 20),
                        new Vector(-1, -1, -1))
                        .setKl(1E-5).setKq(1.5E-7));


        sceneBuilder.setGeometries(geometries)
                .setLights(lightSources)
                .setAmbientLight(new AmbientLight(new Color(java.awt.Color.WHITE), 0.1));
        Scene scene = sceneBuilder.build();

        ImageWriter imageWriter = new ImageWriter("ex_9_our_picture", 600, 600);
        renderBuilder.setImageWriter(imageWriter).setCamera(camera).setRayTracer(new BasicRayTracer(scene));
        RenderThread render = renderBuilder.build();

        render.renderImage();
        render.writeToImage();
    }

    double calcTilda(double zmove,double x,double z){
        return x + Math.sqrt(x*x - zmove*zmove - 2* zmove*z);
    }


    /**
     * Produce a picture of some shapes lighted by some lights
     */
    @Test
    public void ex_10_our_picture() {
        Camera.CameraBuilder cameraBuilder =
                new Camera.CameraBuilder(
                        new Point3D(0, 500, 20),
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
                        new Point3D(20,39,0)).getCube(-14);
        List<Geometry> secondLeg = new Rectangle
                (new Point3D(20,10,0),
                        new Point3D(19,10,0),
                        new Point3D(19,9,0),
                        new Point3D(20,9,0)).getCube(-14);
        List<Geometry> thirdLeg = new Rectangle
                (new Point3D(-20,10,0),
                        new Point3D(-20,9,0),
                        new Point3D(-19,9,0),
                        new Point3D(-19,10,0)).getCube(-14);
        List<Geometry> fourthLeg = new Rectangle
                (new Point3D(-20,40,0),
                        new Point3D(-20,39,0),
                        new Point3D(-19,39,0),
                        new Point3D(-19,40,0)).getCube(-14);


        List<Geometry> cover1 = new Rectangle
                (new Point3D(8.5,20,0),
                        new Point3D(4,20,0),
                        new Point3D(4,15,0),
                        new Point3D(8.5,15,0)).getCube(0.28);


        List<Geometry> pages = new LinkedList<>();
        for(int i = 0; i< 10; i++){
                    pages.addAll( new Rectangle
                        (new Point3D(8,20, 0.3+i*0.1),
                        new Point3D(4,20,0.3 + i*0.1),
                        new Point3D(4,15,0.3 + i*0.1),
                        new Point3D(8,15,0.3 + i*0.1)).moveByZ(2).getCube(0.08));
        }


        List<Geometry> cover2 = new Rectangle
                (new Point3D(8.5,20,1.4),
                        new Point3D(4,20,1.4),
                        new Point3D(4,15,1.4),
                        new Point3D(8.5,15,1.4)).moveByZ(2).getCube(0.18);

        List<Geometry> cover3 = new Rectangle
                (new Point3D(4,20,0),
                        new Point3D(3.5,20,0),
                        new Point3D(3.5,15,0),
                        new Point3D(4,15,0)).getCube(1.3);









        List<Geometry> lamp = new Rectangle
                (new Point3D(40,60,-14),
                        new Point3D(39,60,-14),
                        new Point3D(39,59,-14),
                        new Point3D(40,59,-14)).getCube(31);



        List<Geometry> lamp2 = new Rectangle
                (new Point3D(-40,60,-14),
                        new Point3D(-40,59,-14),
                        new Point3D(-39,59,-14),
                        new Point3D(-39,60,-14)).getCube(31);





        List<Geometry> board = new Rectangle
                (new Point3D(20,40,0),
                        new Point3D(20,9,0),
                        new Point3D(-20,9,0),
                        new Point3D(-20,40,0)).getCube(1);

        Color brown = new Color(23,13,2);
        Geometries geometries = new Geometries();
        geometries.add(
//
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
                        .setEmission(new Color(220,150,0))
                        .setMaterial(new Material().setKd(1).setKs(0).setNShininess(50)),
                cover1.get(1)
                        .setEmission(new Color(220,150,0))
                        .setMaterial(new Material().setKd(1).setKs(0).setNShininess(50)),
                cover1.get(2)
                        .setEmission(new Color(220,150,0))
                        .setMaterial(new Material().setKd(1).setKs(0).setNShininess(50)),
                cover1.get(3)
                        .setEmission(new Color(220,150,0))
                        .setMaterial(new Material().setKd(1).setKs(0).setNShininess(50)),
                cover1.get(4)
                        .setEmission(new Color(220,150,0))
                        .setMaterial(new Material().setKd(1).setKs(0).setNShininess(50)),
                cover1.get(5)
                        .setEmission(new Color(220,150,0))
                        .setMaterial(new Material().setKd(1).setKs(0).setNShininess(50)),










                cover2.get(0)
                        .setEmission(new Color(220,150,0))
                        .setMaterial(new Material().setKd(1).setKs(0).setNShininess(50)),
                cover2.get(1)
                        .setEmission(new Color(220,150,0))
                        .setMaterial(new Material().setKd(1).setKs(0).setNShininess(50)),
                cover2.get(2)
                        .setEmission(new Color(220,150,0))
                        .setMaterial(new Material().setKd(1).setKs(0).setNShininess(50)),
                cover2.get(3)
                        .setEmission(new Color(220,150,0))
                        .setMaterial(new Material().setKd(1).setKs(0).setNShininess(50)),
                cover2.get(4)
                        .setEmission(new Color(220,150,0))
                        .setMaterial(new Material().setKd(1).setKs(0).setNShininess(50)),
                cover2.get(5)
                        .setEmission(new Color(220,150,0))
                        .setMaterial(new Material().setKd(1).setKs(0).setNShininess(50)),




                cover3.get(0)
                        .setEmission(new Color(220,150,0))
                        .setMaterial(new Material().setKd(1).setKs(0).setNShininess(50)),
                cover3.get(1)
                        .setEmission(new Color(220,150,0))
                        .setMaterial(new Material().setKd(1).setKs(0).setNShininess(50)),
                cover3.get(2)
                        .setEmission(new Color(220,150,0))
                        .setMaterial(new Material().setKd(1).setKs(0).setNShininess(50)),
                cover3.get(3)
                        .setEmission(new Color(220,150,0))
                        .setMaterial(new Material().setKd(1).setKs(0).setNShininess(50)),
                cover3.get(4)
                        .setEmission(new Color(220,150,0))
                        .setMaterial(new Material().setKd(1).setKs(0).setNShininess(50)),
                cover3.get(5)
                        .setEmission(new Color(220,150,0))
                        .setMaterial(new Material().setKd(1).setKs(0).setNShininess(50)),














                lamp.get(0)
                        .setEmission(new Color(220,150,0))
                        .setMaterial(new Material().setKd(1).setKs(0).setNShininess(50)),
                lamp.get(1)
                        .setEmission(new Color(220,150,0))
                        .setMaterial(new Material().setKd(1).setKs(0).setNShininess(50)),
                lamp.get(2)
                        .setEmission(new Color(220,150,0))
                        .setMaterial(new Material().setKd(1).setKs(0).setNShininess(50)),
                lamp.get(3)
                        .setEmission(new Color(220,150,0))
                        .setMaterial(new Material().setKd(1).setKs(0).setNShininess(50)),
                lamp.get(4)
                        .setEmission(new Color(220,150,0))
                        .setMaterial(new Material().setKd(1).setKs(0).setNShininess(50)),
                lamp.get(5)
                        .setEmission(new Color(220,150,0))
                        .setMaterial(new Material().setKd(1).setKs(0).setNShininess(50)),




                lamp2.get(0)
                        .setEmission(new Color(220,150,0))
                        .setMaterial(new Material().setKd(1).setKs(0).setNShininess(50)),
                lamp2.get(1)
                        .setEmission(new Color(220,150,0))
                        .setMaterial(new Material().setKd(1).setKs(0).setNShininess(50)),
                lamp2.get(2)
                        .setEmission(new Color(220,150,0))
                        .setMaterial(new Material().setKd(1).setKs(0).setNShininess(50)),
                lamp2.get(3)
                        .setEmission(new Color(220,150,0))
                        .setMaterial(new Material().setKd(1).setKs(0).setNShininess(50)),
                lamp2.get(4)
                        .setEmission(new Color(220,150,0))
                        .setMaterial(new Material().setKd(1).setKs(0).setNShininess(50)),
                lamp2.get(5)
                        .setEmission(new Color(220,150,0))
                        .setMaterial(new Material().setKd(1).setKs(0).setNShininess(50)),









                new Sphere(new Point3D(39.5,60,20),3)
                        .setEmission(new Color(0,190,190))
                        .setMaterial(new Material().setKt(0.3).setKd(0.4).setKs(0.3).setNShininess(100)),





                new Sphere(new Point3D(-39.5,60,20),3)
                        .setEmission(new Color(0,190,190))
                        .setMaterial(new Material().setKt(0.3).setKd(0.4).setKs(0.3).setNShininess(100)),








                /////////           WALLS                 ///////////



                //      FLOOR
                new Polygon(
                      new Point3D(70,-40,-14),
                       new Point3D(-70,-40,-14),
                      new Point3D(-70,100,-14),
                      new Point3D(70,100,-14))
                     .setEmission(new Color(0,0,74))
                     .setMaterial(new Material().setKd(1).setKs(0).setNShininess(50))
                ,
                new Polygon(
                        new Point3D(70,-40,50),
                        new Point3D(-70,-40,50),
                        new Point3D(-70,100,50),
                        new Point3D(70,100,50))
                        .setEmission(new Color(3,0,0))
                        .setMaterial(new Material().setKd(1).setKs(0).setNShininess(50))
                ,

                new Polygon(
                        new Point3D(70,-40,-14),
                        new Point3D(70,100,-14),
                        new Point3D(70,100,50),
                        new Point3D(70,-40,50))
                        .setEmission(new Color(0,67,0))
                        .setMaterial(new Material().setKd(1).setKs(0).setNShininess(50))
                ,

                new Polygon(
                        new Point3D(-70,-40,-14),
                        new Point3D(-70,100,-14),
                        new Point3D(-70,100,50),
                        new Point3D(-70,-40,50))
                        .setEmission(new Color(0,67,0))
                        .setMaterial(new Material().setKd(1).setKs(0).setNShininess(50))
                ,


                new Polygon(
                        new Point3D(70,-40,-14),
                        new Point3D(-70,-40,-14),
                        new Point3D(-70,-40,50),
                        new Point3D(70, -40, 50)).
                        setEmission(new Color(3,67,74))
                        .setMaterial(new Material()),





                //MIRROR
                new Polygon(
                        new Point3D(60,-39,-8),
                        new Point3D(-60,-39,-8),
                        new Point3D(-60,-39,30),
                        new Point3D(60, -39, 30)).
                        setEmission(new Color(202, 202, 202))
                        .setMaterial(new Material().setKs(0.8).setNShininess(180).setKr(0.1))
                        );



        for(int i = 0; i< 60; i++){
            geometries.add(  pages.get(i)
                            .setEmission(new Color(255, 255, 255))
                            .setMaterial(new Material().setKd(1).setKs(0).setNShininess(50)));
        }



        LinkedList<LightSource> lightSources = new LinkedList<>();



        lightSources.add(
                new PointLight(
                        new Color(100,60, 0),
                        new Point3D(40, 60, 20)));


        lightSources.add(
                new PointLight(
                        new Color(100,60, 0),
                        new Point3D(-40, 60, 20)));

        sceneBuilder.setGeometries(geometries)
                .setLights(lightSources)
                .setAmbientLight(new AmbientLight(new Color(java.awt.Color.WHITE), 0.1));
        Scene scene = sceneBuilder.build();

        ImageWriter imageWriter = new ImageWriter("ex_10_our_picture", 600, 600);
        renderBuilder.setImageWriter(imageWriter).setCamera(camera).setRayTracer(new BasicRayTracer(scene));
        RenderThread render = renderBuilder.build();

        render.renderImage();
        render.writeToImage();
    }
}