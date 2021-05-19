package renderer;

import elements.*;
import geometries.*;
import primitives.*;
import org.junit.jupiter.api.*;
import org.w3c.dom.*;
import org.xml.sax.*;
import scene.*;
import javax.xml.*;
import javax.xml.parsers.*;
import java.io.*;
import java.util.Arrays;
import java.util.List;
import java.util.stream.*;
import static java.lang.Double.*;

/**
 * Test rendering a basic image
 */
public class RenderTests {

    Camera.CameraBuilder cameraBuilder = new Camera.CameraBuilder(
                    Point3D.ZERO,
                    new Vector(0, 0, -1),
                    new Vector(0, 1, 0))
                    .setDistance(100)
                    .setViewPlaneSize(500, 500);
    Camera camera = cameraBuilder.build();

   /**
   * Produce a scene with basic 3D model and render it into a jpeg image with a grid
   */
    @Test
    public void basicRenderTwoColorTest() {

        Geometries geometries = new Geometries();
        geometries.add(new Sphere(new Point3D(0, 0, -100), 50),
                new Triangle(new Point3D(-100, 0, -100), new Point3D(0, 100, -100), new Point3D(-100, 100, -100)), // up left
                new Triangle(new Point3D(100, 0, -100), new Point3D(0, 100, -100), new Point3D(100, 100, -100)), // up right
                new Triangle(new Point3D(-100, 0, -100), new Point3D(0, -100, -100), new Point3D(-100, -100, -100)), // down left
                new Triangle(new Point3D(100, 0, -100), new Point3D(0, -100, -100), new Point3D(100, -100, -100))); // down right

        Scene.SceneBuilder sceneBuilder =
                new Scene.SceneBuilder("Test scene")
                        .setAmbientLight(new AmbientLight(new Color(255, 191, 191), 1))
                        .setBackground(new Color(75, 127, 90))
                        .setGeometries(geometries);
        Scene scene = sceneBuilder.build();

        ImageWriter imageWriter = new ImageWriter("base render test", 1000, 1000);

        Render.RenderBuilder renderBuilder =
                new Render.RenderBuilder()
                        .setImageWriter(imageWriter)
                        .setCamera(camera)
                        .setRayTracer(new BasicRayTracer(scene));
        Render render = renderBuilder.build();

        render.renderImage(scene);
        render.printGrid(100, new Color(java.awt.Color.YELLOW));
        render.writeToImage();
    }

    /**
     * Test for XML based scene
     */
    @Test
    public void basicRenderXml() throws ParserConfigurationException {

        DalXml dalXml = new DalXml("basicRenderTestTwoColors");
        Scene scene = dalXml.getSceneFromXML();
        // enter XML file name and parse from XML file into scene object

        ImageWriter imageWriter = new ImageWriter("xml render test", 1000, 1000);

        Render.RenderBuilder renderBuilder =
                new Render.RenderBuilder()
                        .setImageWriter(imageWriter)
                        .setCamera(camera)
                        .setRayTracer(new BasicRayTracer(scene));
        Render render = renderBuilder.build();

        render.renderImage(scene);
        render.printGrid(100, new Color(java.awt.Color.YELLOW));
        render.writeToImage();
    }

    public class DalXml {

        // parse a XML file
        private DocumentBuilder db;
        private final String path = "xml files\\";
        private final String fileName;

        public DalXml(String fileName) throws ParserConfigurationException {
            this.fileName = fileName;
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            dbf.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);
            db = dbf.newDocumentBuilder();
        }

        public Scene getSceneFromXML() {
            try {
                Scene.SceneBuilder sceneBuilder = new Scene.SceneBuilder(fileName);
                Document doc = db.parse(new File(path + fileName + ".xml"));
                // optional, but recommended
                // http://stackoverflow.com/questions/13786607/normalization-in-dom-parsing-with-java-how-does-it-work
                doc.getDocumentElement().normalize();

                var scene = doc.getDocumentElement();
                String color = scene.getAttribute("background-color");
                List<String> stringList = Arrays.stream(color.split("\\s")).collect(Collectors.toList());
                Color backgroundColor = new Color(
                        Double.valueOf(stringList.get(0)),
                        Double.valueOf(stringList.get(1)),
                        Double.valueOf(stringList.get(2))
                );
                sceneBuilder.setBackground(backgroundColor);

                var list = scene.getElementsByTagName("ambient-light");
                var ambientLight = list.item(0);
                var element = (Element) ambientLight;
                color = ((Element) ambientLight).getAttribute("color");
                stringList = Arrays.stream(color.split("\\s")).collect(Collectors.toList());
                Color ambientLightColor;
                ambientLightColor = new Color(parseDouble(stringList.get(0)),
                        parseDouble(stringList.get(1)),
                        parseDouble(stringList.get(2)));

                AmbientLight ambientLight1 = new AmbientLight(ambientLightColor, 1d);
                sceneBuilder.setAmbientLight(ambientLight1);

                Geometries geos = new Geometries();

                list = scene.getElementsByTagName("geometries");

                var geometries = list.item(0).getChildNodes();
                var sphereNode = geometries.item(1);
                var sphere = (Element) sphereNode;
                String center = sphere.getAttribute("center");
                stringList = Arrays.stream(center.split("\\s")).collect(Collectors.toList());
                Point3D point = new Point3D(
                        Double.valueOf(stringList.get(0)),
                        Double.valueOf(stringList.get(1)),
                        Double.valueOf(stringList.get(2))
                );

                String radius = sphere.getAttribute("radius");
                Sphere mySphere = new Sphere(point, Double.valueOf(radius));
                geos.add(mySphere);

                for (int i = 3; i < geometries.getLength(); i += 2) {

                    var triangle = geometries.item(i);
                    var triangleEle = (Element) triangle;
                    String pointStr = triangleEle.getAttribute("p0");
                    stringList = Arrays.stream(pointStr.split("\\s")).collect(Collectors.toList());
                    Point3D p0 = new Point3D(
                            Double.valueOf(stringList.get(0)),
                            Double.valueOf(stringList.get(1)),
                            Double.valueOf(stringList.get(2))
                    );
                    pointStr = triangleEle.getAttribute("p1");
                    stringList = Arrays.stream(pointStr.split("\\s")).collect(Collectors.toList());
                    Point3D p1 = new Point3D(
                            Double.valueOf(stringList.get(0)),
                            Double.valueOf(stringList.get(1)),
                            Double.valueOf(stringList.get(2))
                    );
                    pointStr = triangleEle.getAttribute("p2");
                    stringList = Arrays.stream(pointStr.split("\\s")).collect(Collectors.toList());
                    Point3D p2 = new Point3D(
                            Double.valueOf(stringList.get(0)),
                            Double.valueOf(stringList.get(1)),
                            Double.valueOf(stringList.get(2))
                    );
                    Triangle triangle1 = new Triangle(p0, p1, p2);
                    geos.add(triangle1);
                }
                sceneBuilder.setGeometries(geos);
                return sceneBuilder.build();
            } catch (SAXException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
    }

     /**
     * Produce a scene with basic 3D model - including individual lights of the bodies
     * and render it into a png image with a grid
     */
    @Test
    public void basicRenderMultiColorTest() {
        Scene.SceneBuilder sceneBuilder = new Scene.SceneBuilder("Test scene")//
                .setAmbientLight(new AmbientLight(new Color(java.awt.Color.WHITE), 0.2)); //
        Geometries geometries = new Geometries();
        geometries.add(new Sphere(new Point3D(0, 0, -100), 50) //
                        .setEmission(new Color(java.awt.Color.CYAN)), //
                new Triangle(new Point3D(-100, 0, -100), new Point3D(0, 100, -100), new Point3D(-100, 100, -100)) // up left
                        .setEmission(new Color(java.awt.Color.GREEN)),
                new Triangle(new Point3D(100, 0, -100), new Point3D(0, 100, -100), new Point3D(100, 100, -100)), // up right
                new Triangle(new Point3D(-100, 0, -100), new Point3D(0, -100, -100), new Point3D(-100, -100, -100)) // down left
                        .setEmission(new Color(java.awt.Color.RED)),
                new Triangle(new Point3D(100, 0, -100), new Point3D(0, -100, -100), new Point3D(100, -100, -100)) // down right
                        .setEmission(new Color(java.awt.Color.BLUE)));
        sceneBuilder.setGeometries(geometries);
        Scene scene = sceneBuilder.build();

        ImageWriter imageWriter = new ImageWriter("color render test", 1000, 1000);
        Render.RenderBuilder renderBuilder = new Render.RenderBuilder() //
                .setImageWriter(imageWriter) //
                .setCamera(camera) //
                .setRayTracer(new BasicRayTracer(scene));
        Render render = renderBuilder.build();

        render.renderImage(scene);
        render.printGrid(100, new Color(java.awt.Color.WHITE));
        render.writeToImage();
    }
}