package space;

import static space.enumSpaceObjects.EARTH;
import static space.enumSpaceObjects.SATURN;
import static space.enumSpaceObjects.getObject;

import java.util.ArrayList;
import java.util.List;

import apartment.Actor;
import apartment.Apartment;
import javafx.application.Application;
import javafx.scene.Node;
import javafx.scene.PerspectiveCamera;
import javafx.scene.PointLight;
import javafx.scene.Scene;
import javafx.scene.SceneAntialiasing;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Sphere;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Scale;
import javafx.scene.transform.Translate;
import javafx.stage.Stage;
import stuff_on_earth.StuffOnEarth;

public class MainSpace extends Application {
	public static Node Earth;
	public static ArrayList<Node> spaceObjects = new ArrayList<Node>();
	public static StackPane root = new StackPane();
	private boolean isScaled = false, isSetAtEarth = false, isPpressed = false;
	private int angleHorizontal;
	
	@Override
	public void start(Stage arg) throws Exception {
		//space objects
		for (int i = 0; i < 9; i++) {
			spaceObjects.add(SpaceObject.create(getObject(i)));
			new SpaceObject().move(getObject(i), spaceObjects.get(i)).rotate(360_00, spaceObjects.get(i));
		}
		var rings = new Circle(15.3);
		rings.setFill(new ImagePattern(new Image("file:files/textures/saturn rings with shadow.png")));
	    rings.getTransforms().add(new Rotate(90, Rotate.X_AXIS));
	    new SpaceObject().move(SATURN, rings).rotate(1800/SATURN.angleYear, rings);
	    spaceObjects.add(rings);
	    root.getChildren().addAll(spaceObjects);
		   
		Earth = spaceObjects.get(EARTH.number);
		
		//objects on the Erath
		var stuffOnEarth = new ArrayList<StuffOnEarth>();
		
		  //trees
		  for (int i = -40; i < 0; i+=7)                        
	    	  stuffOnEarth.add(new StuffOnEarth("tree", "obj", .95f, .009f, i, 320-i));
	      for (int i = 0; i <= 40; i+=7)                        
	    	  stuffOnEarth.add(new StuffOnEarth("tree", "obj", .95f, .009f, i, 320+i));
	      for (int i = -25; i < 0; i+=7)                        
	    	  stuffOnEarth.add(new StuffOnEarth("tree", "obj", .95f, .009f, i, 350-i));
	      for (int i = 0; i <= 25; i+=7)                        
	    	  stuffOnEarth.add(new StuffOnEarth("tree", "obj", .95f, .009f, i, 350+i));
		  //flowers
	      int minJ=-10, maxJ=10;
	      for (int i = 20; i >= 10; i=i-5) {
	        for (int j = minJ; j <= maxJ; j+=5)
	      	  stuffOnEarth.add(new StuffOnEarth("rose", "obj", .95f, .002f, j, i));
	          minJ+=5;maxJ-=5;
	      }
		  //animal
		  var lion = new StuffOnEarth("lion", "3ds", 1.15f, .0001f, -30, 50);
		  stuffOnEarth.add(lion);
		  //house
		  var houseMesh = new Box(.25, .25, .35);
		  houseMesh.setMaterial(new PhongMaterial(Color.BROWN));
		  var house = new StuffOnEarth(1, 70, houseMesh);
		  stuffOnEarth.add(house);
		  
		  var apartment = new StuffOnEarth(1.01f, 80, Apartment.getApartment());
		  apartment.getShape().getTransforms().addAll
		      (new Rotate(90, Rotate.Y_AXIS), new Rotate(-90, Rotate.X_AXIS), new Scale(.001, .001, .001));
		  apartment.getShape().setVisible(false); 
		  stuffOnEarth.add(apartment);
		  
		  var water = new Sphere(1.05*EARTH.radius);
	      water.setMaterial(new PhongMaterial(Color.WHITE, new Image("file:files/textures/Earth/ocean.gif"),null,null,null));
		  water.getTransforms().add(new Rotate(90, Rotate.Z_AXIS));
		  stuffOnEarth.add(new StuffOnEarth(.08f, 235, water));
		      
		  var atmosphere = new Sphere(EARTH.radius*1.3);
		  var material =  new PhongMaterial();
		  material.setDiffuseColor(new Color(.6,.8,.9, .5));
		  material.setDiffuseMap(new Image("file:files/textures/Earth/sky.jpg"));
		  material.setSelfIlluminationMap(new Image("file:files/textures/Earth/clouds.png"));
		  atmosphere.getTransforms().add(new Rotate(90, Rotate.Y_AXIS));
		  atmosphere.setMaterial(material);
		  stuffOnEarth.add(new StuffOnEarth(0.01f, 0, atmosphere));
		  
		  var moon = new Sphere(EARTH.radius/4);
		  moon.setMaterial(new PhongMaterial(Color.WHITE,new Image("file:files/textures/Earth/moon.jpg"),null,null,null));
		  stuffOnEarth.add(new StuffOnEarth(10, 0, moon));
		  
		var camera = new PerspectiveCamera(false);
		camera.getTransforms().addAll
		(new Rotate(90, Rotate.X_AXIS), new Translate(-625, -400, -400));
		var cameraPane = new StackPane();
		cameraPane.getChildren().add(camera);
				   
		root.setStyle("-fx-background-color: transparent");
		root.getChildren().addAll(new PointLight(Color.WHITE), cameraPane, SpaceObject.getSky());
		Scene scene = new Scene(root, 1250, 650, true, SceneAntialiasing.BALANCED);
		scene.setCamera(camera);
		arg.setResizable(true);
		arg.setFullScreen(true);
		arg.setScene(scene);
		arg.setTitle("WORLD"); 
		arg.getIcons().add(new Image("file:files/icon.png"));
		arg.setOnCloseRequest(e-> System.exit(0));
		arg.show();
		
		scene.setOnKeyPressed(e-> {
			switch (e.getCode()) {
			case P:
				if(isScaled==false & isSetAtEarth==false & isPpressed==false) {
				    atmosphere.setVisible(false);
				    house.getShape().setVisible(false);
				    apartment.getShape().setVisible(true);
				    new StuffOnEarth(apartment, cameraPane);
    			    resizeEarthStuff(stuffOnEarth, 100, true);
				    Earth.getTransforms().add(new Scale(100, 100, 100));
				    isPpressed=true;
				}
				break;
			case C://camera
				if (isScaled==false)
				if (isSetAtEarth) {
					isSetAtEarth=false;
					cameraPane.translateXProperty().bind(spaceObjects.get(0).translateXProperty());
					cameraPane.translateYProperty().bind(spaceObjects.get(0).translateYProperty());
					cameraPane.translateZProperty().bind(spaceObjects.get(0).translateZProperty());
					//resize
					resizeEarthStuff(stuffOnEarth, 1/32, false);
					for (int i = 0; i < stuffOnEarth.size()-4; i++)
						stuffOnEarth.get(i).getShape().getTransforms().remove
						(stuffOnEarth.get(i).getShape().getTransforms().size()-1);
					Earth.getTransforms().remove(Earth.getTransforms().size()-1);
				} else {
					isSetAtEarth=true;
					cameraPane.translateXProperty().bind(Earth.translateXProperty());
					cameraPane.translateYProperty().bind(Earth.translateYProperty());
					cameraPane.translateZProperty().bind(Earth.translateZProperty());
					//resize
					resizeEarthStuff(stuffOnEarth, 32, true);
					for (int i = 0; i < stuffOnEarth.size()-4; i++)
						stuffOnEarth.get(i).getShape().getTransforms().add(new Scale(.8,.8,.8));
					Earth.getTransforms().add(new Scale(33,33,33));
				}
				break;
			case S://scale
				if (isSetAtEarth==false)
				if (isScaled) {
					resizeEarthStuff(stuffOnEarth, 1/8, false);
					for (int i = 0; i < stuffOnEarth.size()-4; i++)
						stuffOnEarth.get(i).getShape().setVisible(true);
					for (var planet : spaceObjects) 
						planet.getTransforms().remove(planet.getTransforms().size()-1);
					isScaled=false;
				} else {
					resizeEarthStuff(stuffOnEarth, 8, true);
					for (int i = 0; i < stuffOnEarth.size()-4; i++)
						stuffOnEarth.get(i).getShape().setVisible(false);
					for (var planet : spaceObjects) 
						planet.getTransforms().add(new Scale(8, 8, 8));
					isScaled = true;
				}break;
			//rotation 	
			case UP:   
				if (isPpressed) {
					Actor.goAhead();
				} else {
					camera.setTranslateX(camera.getTranslateX()+ Math.sin(Math.toRadians(angleHorizontal))*10);
					camera.setTranslateY(camera.getTranslateY()- Math.cos(Math.toRadians(angleHorizontal))*10); 
				}
				break;                                                                                     
			case DOWN: 
				if (isPpressed) {
					Actor.goBack();
				} else {
					camera.setTranslateX(camera.getTranslateX()- Math.sin(Math.toRadians(angleHorizontal))*10);
					camera.setTranslateY(camera.getTranslateY()+ Math.cos(Math.toRadians(angleHorizontal))*10);
				}
				break;
			case LEFT: 
				if (isPpressed) {
					Actor.goLeft();
				} else {
					camera.getTransforms().add(new Rotate(-1, Rotate.Y_AXIS));
					angleHorizontal--;
				}
				break;
			case RIGHT:
				if (isPpressed) {
					Actor.goRight();
				} else {
					camera.getTransforms().add(new Rotate(+1, Rotate.Y_AXIS));
					angleHorizontal++;
				}
				break;
			case EQUALS:     
				camera.setTranslateZ(camera.getTranslateZ()-10);
				break;
			case MINUS: 
				camera.setTranslateZ(camera.getTranslateZ()+10);
				break;
			case CONTROL: System.exit(0);
		    default: System.out.println("Wrong number"+e.getCode());break;
			}});
	}
	private void resizeEarthStuff(List<StuffOnEarth> listOfObjects, double parameter, boolean enlarge) {
		for (var earthObject : listOfObjects) {
			if (enlarge)
				earthObject.getShape().getTransforms().add(new Scale(parameter,parameter,parameter));
			 else 
				earthObject.getShape().getTransforms().remove(earthObject.getShape().getTransforms().size()-1);
			earthObject.getAuraPane().translateZProperty().
			bind(Earth.translateZProperty().add(parameter*earthObject.upsfift));
		
			var movement = earthObject.getMovement();
			var t = movement.getCurrentTime();
			movement.stop();
			movement.setPath(new Circle(earthObject.distanceFromTheCenterOfPlanet*parameter));
			movement.playFrom(t);
		}	
	}
	public static void main(String[] args) {
		launch(args);
	}
}