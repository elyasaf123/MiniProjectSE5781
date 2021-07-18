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

    private  Camera.CameraBuilder cameraBuilder =
            new Camera.CameraBuilder(
                    new Point3D(0, 500, 20),
                    new Vector(0, -1, 0),
                    new Vector(0, 0, 1))
                    .setViewPlaneSize(200, 200)
                    .setDistance(700);


    private     Camera camera = cameraBuilder.build();


    @Test
    void generateVideo() throws IOException {

        BufferedImage[] a = new BufferedImage[200];
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


        for(double j = 0; j< 100; j++) {
            List<Geometry> pages = new LinkedList<>();
            for(int i = 0; i< 10; i++){
                pages.addAll( new Rectangle
                        (new Point3D(8,20, 0.3+i*0.1),
                                new Point3D(4,20,0.3 + i*0.1),
                                new Point3D(4,15,0.3 + i*0.1),
                                new Point3D(8,15,0.3 + i*0.1)).moveByJ(j/25).getCube(0.08));
            }


            List<Geometry> cover2 = new Rectangle
                    (new Point3D(8.5,20,1.4),
                            new Point3D(4,20,1.4),
                            new Point3D(4,15,1.4),
                            new Point3D(8.5,15,1.4)).moveByJ(j/25).getCube(0.18);
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
                            .setEmission(new Color(220, 150, 0))
                            .setMaterial(new Material().setKd(1).setKs(0).setNShininess(50)),
                    cover1.get(1)
                            .setEmission(new Color(220, 150, 0))
                            .setMaterial(new Material().setKd(1).setKs(0).setNShininess(50)),
                    cover1.get(2)
                            .setEmission(new Color(220, 150, 0))
                            .setMaterial(new Material().setKd(1).setKs(0).setNShininess(50)),
                    cover1.get(3)
                            .setEmission(new Color(220, 150, 0))
                            .setMaterial(new Material().setKd(1).setKs(0).setNShininess(50)),
                    cover1.get(4)
                            .setEmission(new Color(220, 150, 0))
                            .setMaterial(new Material().setKd(1).setKs(0).setNShininess(50)),
                    cover1.get(5)
                            .setEmission(new Color(220, 150, 0))
                            .setMaterial(new Material().setKd(1).setKs(0).setNShininess(50)),


                    cover2.get(0)
                            .setEmission(new Color(220, 150, 0))
                            .setMaterial(new Material().setKd(1).setKs(0).setNShininess(50)),
                    cover2.get(1)
                            .setEmission(new Color(220, 150, 0))
                            .setMaterial(new Material().setKd(1).setKs(0).setNShininess(50)),
                    cover2.get(2)
                            .setEmission(new Color(220, 150, 0))
                            .setMaterial(new Material().setKd(1).setKs(0).setNShininess(50)),
                    cover2.get(3)
                            .setEmission(new Color(220, 150, 0))
                            .setMaterial(new Material().setKd(1).setKs(0).setNShininess(50)),
                    cover2.get(4)
                            .setEmission(new Color(220, 150, 0))
                            .setMaterial(new Material().setKd(1).setKs(0).setNShininess(50)),
                    cover2.get(5)
                            .setEmission(new Color(220, 150, 0))
                            .setMaterial(new Material().setKd(1).setKs(0).setNShininess(50)),


                    cover3.get(0)
                            .setEmission(new Color(220, 150, 0))
                            .setMaterial(new Material().setKd(1).setKs(0).setNShininess(50)),
                    cover3.get(1)
                            .setEmission(new Color(220, 150, 0))
                            .setMaterial(new Material().setKd(1).setKs(0).setNShininess(50)),
                    cover3.get(2)
                            .setEmission(new Color(220, 150, 0))
                            .setMaterial(new Material().setKd(1).setKs(0).setNShininess(50)),
                    cover3.get(3)
                            .setEmission(new Color(220, 150, 0))
                            .setMaterial(new Material().setKd(1).setKs(0).setNShininess(50)),
                    cover3.get(4)
                            .setEmission(new Color(220, 150, 0))
                            .setMaterial(new Material().setKd(1).setKs(0).setNShininess(50)),
                    cover3.get(5)
                            .setEmission(new Color(220, 150, 0))
                            .setMaterial(new Material().setKd(1).setKs(0).setNShininess(50)),


                    lamp.get(0)
                            .setEmission(new Color(220, 150, 0))
                            .setMaterial(new Material().setKd(1).setKs(0).setNShininess(50)),
                    lamp.get(1)
                            .setEmission(new Color(220, 150, 0))
                            .setMaterial(new Material().setKd(1).setKs(0).setNShininess(50)),
                    lamp.get(2)
                            .setEmission(new Color(220, 150, 0))
                            .setMaterial(new Material().setKd(1).setKs(0).setNShininess(50)),
                    lamp.get(3)
                            .setEmission(new Color(220, 150, 0))
                            .setMaterial(new Material().setKd(1).setKs(0).setNShininess(50)),
                    lamp.get(4)
                            .setEmission(new Color(220, 150, 0))
                            .setMaterial(new Material().setKd(1).setKs(0).setNShininess(50)),
                    lamp.get(5)
                            .setEmission(new Color(220, 150, 0))
                            .setMaterial(new Material().setKd(1).setKs(0).setNShininess(50)),


                    lamp2.get(0)
                            .setEmission(new Color(220, 150, 0))
                            .setMaterial(new Material().setKd(1).setKs(0).setNShininess(50)),
                    lamp2.get(1)
                            .setEmission(new Color(220, 150, 0))
                            .setMaterial(new Material().setKd(1).setKs(0).setNShininess(50)),
                    lamp2.get(2)
                            .setEmission(new Color(220, 150, 0))
                            .setMaterial(new Material().setKd(1).setKs(0).setNShininess(50)),
                    lamp2.get(3)
                            .setEmission(new Color(220, 150, 0))
                            .setMaterial(new Material().setKd(1).setKs(0).setNShininess(50)),
                    lamp2.get(4)
                            .setEmission(new Color(220, 150, 0))
                            .setMaterial(new Material().setKd(1).setKs(0).setNShininess(50)),
                    lamp2.get(5)
                            .setEmission(new Color(220, 150, 0))
                            .setMaterial(new Material().setKd(1).setKs(0).setNShininess(50)),


                    new Sphere(new Point3D(39.5, 60, 20), 3)
                            .setEmission(new Color(0, 190, 190))
                            .setMaterial(new Material().setKt(0.3).setKd(0.4).setKs(0.3).setNShininess(100)),


                    new Sphere(new Point3D(-39.5, 60, 20), 3)
                            .setEmission(new Color(0, 190, 190))
                            .setMaterial(new Material().setKt(0.3).setKd(0.4).setKs(0.3).setNShininess(100)),


                    /////////           WALLS                 ///////////


                    //      FLOOR
                    new Polygon(
                            new Point3D(70, -40, -14),
                            new Point3D(-70, -40, -14),
                            new Point3D(-70, 100, -14),
                            new Point3D(70, 100, -14))
                            .setEmission(new Color(0, 0, 74))
                            .setMaterial(new Material().setKd(1).setKs(0).setNShininess(50))
                    ,
                    new Polygon(
                            new Point3D(70, -40, 50),
                            new Point3D(-70, -40, 50),
                            new Point3D(-70, 100, 50),
                            new Point3D(70, 100, 50))
                            .setEmission(new Color(3, 0, 0))
                            .setMaterial(new Material().setKd(1).setKs(0).setNShininess(50))
                    ,

                    new Polygon(
                            new Point3D(70, -40, -14),
                            new Point3D(70, 100, -14),
                            new Point3D(70, 100, 50),
                            new Point3D(70, -40, 50))
                            .setEmission(new Color(0, 67, 0))
                            .setMaterial(new Material().setKd(1).setKs(0).setNShininess(50))
                    ,

                    new Polygon(
                            new Point3D(-70, -40, -14),
                            new Point3D(-70, 100, -14),
                            new Point3D(-70, 100, 50),
                            new Point3D(-70, -40, 50))
                            .setEmission(new Color(0, 67, 0))
                            .setMaterial(new Material().setKd(1).setKs(0).setNShininess(50))
                    ,


                    new Polygon(
                            new Point3D(70, -40, -14),
                            new Point3D(-70, -40, -14),
                            new Point3D(-70, -40, 50),
                            new Point3D(70, -40, 50)).
                            setEmission(new Color(3, 67, 74))
                            .setMaterial(new Material()),


                    //MIRROR
                    new Polygon(
                            new Point3D(60, -39, -8),
                            new Point3D(-60, -39, -8),
                            new Point3D(-60, -39, 30),
                            new Point3D(60, -39, 30)).
                            setEmission(new Color(202, 202, 202))
                            .setMaterial(new Material().setKs(0.8).setNShininess(180).setKr(0.1))
            );


            for (int i = 0; i < 60; i++) {
                geometries.add(pages.get(i)
                        .setEmission(new Color(255, 255, 255))
                        .setMaterial(new Material().setKd(1).setKs(0).setNShininess(50)));
            }


            LinkedList<LightSource> lightSources = new LinkedList<>();

            sceneBuilder.setGeometries(geometries);
            lightSources.add(
                    new SpotLight(
                            new Color(400, 240, 0),
                            new Point3D(-73, -73, 56),
                            new Vector(1, 1, -3))
                            .setKl(1E-5).setKq(1.5E-7));


            lightSources.add(
                    new PointLight(
                            new Color(100, 60, 0),
                            new Point3D(40, 60, 20)));


            lightSources.add(
                    new PointLight(
                            new Color(100, 60, 0),
                            new Point3D(-40, 60, 20)));
            sceneBuilder.setLights(lightSources);
            Scene scene = sceneBuilder.build();


            Render.RenderBuilder renderBuilder = new Render.RenderBuilder().
                    setImageWriter(new ImageWriter("shadowSphereTriangleLinkedSpot", 400, 400))
                    .setCamera(camera)
                    .setRayTracer(new BasicRayTracer(scene));
            Render render = renderBuilder.build();

            render.renderImage();
            a[(int) j] = render.getImage();
        }

        for(double j = 100; j< 200; j++) {
            List<Geometry> pages = new LinkedList<>();
            for(int i = 0; i< 10; i++){
                pages.addAll( new Rectangle
                        (new Point3D(8,20, 0.3+i*0.1),
                                new Point3D(4,20,0.3 + i*0.1),
                                new Point3D(4,15,0.3 + i*0.1),
                                new Point3D(8,15,0.3 + i*0.1)).moveByJ((100-j)/25).getCube(0.08));
            }


            List<Geometry> cover2 = new Rectangle
                    (new Point3D(8.5,20,1.4),
                            new Point3D(4,20,1.4),
                            new Point3D(4,15,1.4),
                            new Point3D(8.5,15,1.4)).moveByJ((100-j)/25).getCube(0.18);
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
                            .setEmission(new Color(220, 150, 0))
                            .setMaterial(new Material().setKd(1).setKs(0).setNShininess(50)),
                    cover1.get(1)
                            .setEmission(new Color(220, 150, 0))
                            .setMaterial(new Material().setKd(1).setKs(0).setNShininess(50)),
                    cover1.get(2)
                            .setEmission(new Color(220, 150, 0))
                            .setMaterial(new Material().setKd(1).setKs(0).setNShininess(50)),
                    cover1.get(3)
                            .setEmission(new Color(220, 150, 0))
                            .setMaterial(new Material().setKd(1).setKs(0).setNShininess(50)),
                    cover1.get(4)
                            .setEmission(new Color(220, 150, 0))
                            .setMaterial(new Material().setKd(1).setKs(0).setNShininess(50)),
                    cover1.get(5)
                            .setEmission(new Color(220, 150, 0))
                            .setMaterial(new Material().setKd(1).setKs(0).setNShininess(50)),


                    cover2.get(0)
                            .setEmission(new Color(220, 150, 0))
                            .setMaterial(new Material().setKd(1).setKs(0).setNShininess(50)),
                    cover2.get(1)
                            .setEmission(new Color(220, 150, 0))
                            .setMaterial(new Material().setKd(1).setKs(0).setNShininess(50)),
                    cover2.get(2)
                            .setEmission(new Color(220, 150, 0))
                            .setMaterial(new Material().setKd(1).setKs(0).setNShininess(50)),
                    cover2.get(3)
                            .setEmission(new Color(220, 150, 0))
                            .setMaterial(new Material().setKd(1).setKs(0).setNShininess(50)),
                    cover2.get(4)
                            .setEmission(new Color(220, 150, 0))
                            .setMaterial(new Material().setKd(1).setKs(0).setNShininess(50)),
                    cover2.get(5)
                            .setEmission(new Color(220, 150, 0))
                            .setMaterial(new Material().setKd(1).setKs(0).setNShininess(50)),


                    cover3.get(0)
                            .setEmission(new Color(220, 150, 0))
                            .setMaterial(new Material().setKd(1).setKs(0).setNShininess(50)),
                    cover3.get(1)
                            .setEmission(new Color(220, 150, 0))
                            .setMaterial(new Material().setKd(1).setKs(0).setNShininess(50)),
                    cover3.get(2)
                            .setEmission(new Color(220, 150, 0))
                            .setMaterial(new Material().setKd(1).setKs(0).setNShininess(50)),
                    cover3.get(3)
                            .setEmission(new Color(220, 150, 0))
                            .setMaterial(new Material().setKd(1).setKs(0).setNShininess(50)),
                    cover3.get(4)
                            .setEmission(new Color(220, 150, 0))
                            .setMaterial(new Material().setKd(1).setKs(0).setNShininess(50)),
                    cover3.get(5)
                            .setEmission(new Color(220, 150, 0))
                            .setMaterial(new Material().setKd(1).setKs(0).setNShininess(50)),


                    lamp.get(0)
                            .setEmission(new Color(220, 150, 0))
                            .setMaterial(new Material().setKd(1).setKs(0).setNShininess(50)),
                    lamp.get(1)
                            .setEmission(new Color(220, 150, 0))
                            .setMaterial(new Material().setKd(1).setKs(0).setNShininess(50)),
                    lamp.get(2)
                            .setEmission(new Color(220, 150, 0))
                            .setMaterial(new Material().setKd(1).setKs(0).setNShininess(50)),
                    lamp.get(3)
                            .setEmission(new Color(220, 150, 0))
                            .setMaterial(new Material().setKd(1).setKs(0).setNShininess(50)),
                    lamp.get(4)
                            .setEmission(new Color(220, 150, 0))
                            .setMaterial(new Material().setKd(1).setKs(0).setNShininess(50)),
                    lamp.get(5)
                            .setEmission(new Color(220, 150, 0))
                            .setMaterial(new Material().setKd(1).setKs(0).setNShininess(50)),


                    lamp2.get(0)
                            .setEmission(new Color(220, 150, 0))
                            .setMaterial(new Material().setKd(1).setKs(0).setNShininess(50)),
                    lamp2.get(1)
                            .setEmission(new Color(220, 150, 0))
                            .setMaterial(new Material().setKd(1).setKs(0).setNShininess(50)),
                    lamp2.get(2)
                            .setEmission(new Color(220, 150, 0))
                            .setMaterial(new Material().setKd(1).setKs(0).setNShininess(50)),
                    lamp2.get(3)
                            .setEmission(new Color(220, 150, 0))
                            .setMaterial(new Material().setKd(1).setKs(0).setNShininess(50)),
                    lamp2.get(4)
                            .setEmission(new Color(220, 150, 0))
                            .setMaterial(new Material().setKd(1).setKs(0).setNShininess(50)),
                    lamp2.get(5)
                            .setEmission(new Color(220, 150, 0))
                            .setMaterial(new Material().setKd(1).setKs(0).setNShininess(50)),


                    new Sphere(new Point3D(39.5, 60, 20), 3)
                            .setEmission(new Color(0, 190, 190))
                            .setMaterial(new Material().setKt(0.3).setKd(0.4).setKs(0.3).setNShininess(100)),


                    new Sphere(new Point3D(-39.5, 60, 20), 3)
                            .setEmission(new Color(0, 190, 190))
                            .setMaterial(new Material().setKt(0.3).setKd(0.4).setKs(0.3).setNShininess(100)),


                    /////////           WALLS                 ///////////


                    //      FLOOR
                    new Polygon(
                            new Point3D(70, -40, -14),
                            new Point3D(-70, -40, -14),
                            new Point3D(-70, 100, -14),
                            new Point3D(70, 100, -14))
                            .setEmission(new Color(0, 0, 74))
                            .setMaterial(new Material().setKd(1).setKs(0).setNShininess(50))
                    ,
                    new Polygon(
                            new Point3D(70, -40, 50),
                            new Point3D(-70, -40, 50),
                            new Point3D(-70, 100, 50),
                            new Point3D(70, 100, 50))
                            .setEmission(new Color(3, 0, 0))
                            .setMaterial(new Material().setKd(1).setKs(0).setNShininess(50))
                    ,

                    new Polygon(
                            new Point3D(70, -40, -14),
                            new Point3D(70, 100, -14),
                            new Point3D(70, 100, 50),
                            new Point3D(70, -40, 50))
                            .setEmission(new Color(0, 67, 0))
                            .setMaterial(new Material().setKd(1).setKs(0).setNShininess(50))
                    ,

                    new Polygon(
                            new Point3D(-70, -40, -14),
                            new Point3D(-70, 100, -14),
                            new Point3D(-70, 100, 50),
                            new Point3D(-70, -40, 50))
                            .setEmission(new Color(0, 67, 0))
                            .setMaterial(new Material().setKd(1).setKs(0).setNShininess(50))
                    ,


                    new Polygon(
                            new Point3D(70, -40, -14),
                            new Point3D(-70, -40, -14),
                            new Point3D(-70, -40, 50),
                            new Point3D(70, -40, 50)).
                            setEmission(new Color(3, 67, 74))
                            .setMaterial(new Material()),


                    //MIRROR
                    new Polygon(
                            new Point3D(60, -39, -8),
                            new Point3D(-60, -39, -8),
                            new Point3D(-60, -39, 30),
                            new Point3D(60, -39, 30)).
                            setEmission(new Color(202, 202, 202))
                            .setMaterial(new Material().setKs(0.8).setNShininess(180).setKr(0.1))
            );


            for (int i = 0; i < 60; i++) {
                geometries.add(pages.get(i)
                        .setEmission(new Color(255, 255, 255))
                        .setMaterial(new Material().setKd(1).setKs(0).setNShininess(50)));
            }


            LinkedList<LightSource> lightSources = new LinkedList<>();

            sceneBuilder.setGeometries(geometries);
            lightSources.add(
                    new SpotLight(
                            new Color(400, 240, 0),
                            new Point3D(-73, -73, 56),
                            new Vector(1, 1, -3))
                            .setKl(1E-5).setKq(1.5E-7));


            lightSources.add(
                    new PointLight(
                            new Color(100, 60, 0),
                            new Point3D(40, 60, 20)));


            lightSources.add(
                    new PointLight(
                            new Color(100, 60, 0),
                            new Point3D(-40, 60, 20)));
            sceneBuilder.setLights(lightSources);
            Scene scene = sceneBuilder.build();


            Render.RenderBuilder renderBuilder = new Render.RenderBuilder().
                    setImageWriter(new ImageWriter("shadowSphereTriangleLinkedSpot", 400, 400))
                    .setCamera(camera)
                    .setRayTracer(new BasicRayTracer(scene));
            Render render = renderBuilder.build();

            render.renderImage();
            a[(int) j] = render.getImage();
        }
        Video.generateVideo("first video", a, 20);

    }
}