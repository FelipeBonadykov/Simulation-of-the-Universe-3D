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
import javafx.scene.shape.Circle;
import javafx.scene.shape.Sphere;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;
import stuff_on_earth.StuffOnEarth;

public class MainSpace extends Application {
	public static Node Earth;
	public static ArrayList<Node> spaceObjects = new ArrayList<Node>();
	public static StackPane root = new StackPane();
	private boolean isScaled = false, isSetAtEarth = false, hasApartment = false, isPpressed = false;
	private int angleHorizontal;
	
	@Override
	public void start(Stage arg) throws Exception {
		for (int i = 0; i < 9; i++) {
			spaceObjects.add(SpaceObject.create(getObject(i)));
			new SpaceObject().move(getObject(i), spaceObjects.get(i)).rotate(10_000, spaceObjects.get(i));
		}
		var rings = new Circle(15.3);
		rings.setFill(new ImagePattern(new Image("file:files/textures/saturn rings with shadow.png")));
	    rings.getTransforms().add(new Rotate(90, Rotate.X_AXIS));
	    new SpaceObject().move(SATURN, rings).rotate(1800/SATURN.angleYear, rings);
	    spaceObjects.add(rings);
	    root.getChildren().addAll(spaceObjects);
		   
		Earth = spaceObjects.get(EARTH.number);
			
		var stuffOnEarth = new ArrayList<StuffOnEarth>();
		
		  var terra = new Sphere(.9*EARTH.radius);
		  terra.setMaterial(new PhongMaterial(Color.WHITE,new Image("file:files/textures/Earth/grass.jpg"),null,null,null));
		  terra.getTransforms().add(new Rotate(90, Rotate.Z_AXIS));
		  stuffOnEarth.add(new StuffOnEarth(.18f, terra));
		  {//trees
			  for (int i = -40; i < 0; i+=7)                        
				  stuffOnEarth.add(new StuffOnEarth("tree", "obj", 1.05f, .009f, i, 320-i));
			  for (int i = 0; i <= 40; i+=7)                        
				  stuffOnEarth.add(new StuffOnEarth("tree", "obj", 1.05f, .009f, i, 320+i));
			  for (int i = -25; i < 0; i+=7)                        
				  stuffOnEarth.add(new StuffOnEarth("tree", "obj", 1.05f, .009f, i, 350-i));
			  for (int i = 0; i <= 25; i+=7)                        
				  stuffOnEarth.add(new StuffOnEarth("tree", "obj", 1.05f, .009f, i, 350+i));
		  }
		  {//flowers
			  int minJ=-10, maxJ=10;
			  for (int i = 20; i >= 10; i=i-5) {
				  for (int j = minJ; j <= maxJ; j+=5)
					  stuffOnEarth.add(new StuffOnEarth("rose", "obj", 1.07f, .002f, j, i));
			      minJ+=5;maxJ-=5;
			  }
		  }
		  {//animals
			  var eagle = new StuffOnEarth("eagle", "3ds", 1.16f, .00003f, 30, 50);
		      for (Node eaglePart : eagle.getShape()) {
		    	  eaglePart.translateZProperty().bind(eagle.getAura().translateZProperty().subtract(250));
		    	  eaglePart.getTransforms().add(new Rotate(180+90, Rotate.X_AXIS));
		      }
		      stuffOnEarth.add(eagle);
		      
		      var lion = new StuffOnEarth("lion", "3ds", 1.15f, .0001f, -30, 50);
		      for (Node lionPart : lion.getShape()) 
		    	  lionPart.getTransforms().add(new Rotate(-90, Rotate.X_AXIS));
		      stuffOnEarth.add(lion);
		  }
		  //house
			  var house = new StuffOnEarth("house", "3ds", 1.12f, .000025f, 0,55);
			  for (var housePart : house.getShape()) 
				  housePart.getTransforms().add(new Rotate(-90, Rotate.X_AXIS));
		      stuffOnEarth.add(house);
		      
		      var apartment = Apartment.getApartment();
		      Apartment.putOnEarth(house);
		      apartment.setVisible(false); 
		 
		  var atmosphere = new Sphere(EARTH.radius*1.3);
		  var material =  new PhongMaterial();
		  material.setDiffuseColor(new Color(.6,.8,.9, .5));
		  material.setDiffuseMap(new Image("file:files/textures/Earth/sky.jpg"));
		  material.setSelfIlluminationMap(new Image("file:files/textures/Earth/clouds.png"));
		  atmosphere.getTransforms().add(new Rotate(90, Rotate.Y_AXIS));
		  atmosphere.setMaterial(material);
		  stuffOnEarth.add(new StuffOnEarth(.01f, atmosphere));
		  
		  var moon = new Sphere(EARTH.radius/4);
		  moon.setMaterial(new PhongMaterial(Color.WHITE,new Image("file:files/textures/Earth/moon.jpg"),null,null,null));
		  stuffOnEarth.add(new StuffOnEarth(10, moon));
		  
		var camera = new PerspectiveCamera(false);
		camera.getTransforms().add(new Rotate(90, Rotate.X_AXIS));
		camera.setTranslateX(-625); 
		camera.setTranslateY(700);
		camera.setTranslateZ(-380);
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
				if(hasApartment==false) {
					isPpressed=true;
					hasApartment = true;
				    atmosphere.setVisible(false);
				    for (var part : house.getShape()) part.setVisible(false);
				    apartment.setVisible(true);
				    Apartment.setCameraOnApartment(cameraPane, house);
				    resizeEarthStuff(stuffOnEarth, 100);
				    Earth.setScaleX(Earth.getScaleX()*100);
				    Earth.setScaleY(Earth.getScaleY()*100);
				    Earth.setScaleZ(Earth.getScaleZ()*100);
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
						resizeEarthStuff(stuffOnEarth, .125*.25);
						Earth.setScaleX(Earth.getScaleX()*.125*.25);
						Earth.setScaleY(Earth.getScaleY()*.125*.25);
						Earth.setScaleZ(Earth.getScaleZ()*.125*.25);
				} else {
					isSetAtEarth=true;
					cameraPane.translateXProperty().bind(Earth.translateXProperty());
					cameraPane.translateYProperty().bind(Earth.translateYProperty());
					cameraPane.translateZProperty().bind(Earth.translateZProperty());
					
					camera.setTranslateX(Earth.getTranslateX()-1100); 
					camera.setTranslateY(Earth.getTranslateY()-300);
					camera.setTranslateZ(Earth.getTranslateZ()-300);
					//resize
						resizeEarthStuff(stuffOnEarth, 32);
						Earth.setScaleX(Earth.getScaleX()*32);
						Earth.setScaleY(Earth.getScaleY()*32);
						Earth.setScaleZ(Earth.getScaleZ()*32);
				}
				break;
			case S://scale
				if (isSetAtEarth==false)
				if (isScaled) {
					resizeEarthStuff(stuffOnEarth, .125);
					for (Node planet : spaceObjects) {
						planet.setScaleX(planet.getScaleX()/8);
						planet.setScaleY(planet.getScaleY()/8);
						planet.setScaleZ(planet.getScaleZ()/8);
					}
					isScaled=false;
				} else {
					resizeEarthStuff(stuffOnEarth, 8);
					for (Node planet : spaceObjects) {
						planet.setScaleX(planet.getScaleX()*8);
					    planet.setScaleY(planet.getScaleY()*8);
					    planet.setScaleZ(planet.getScaleZ()*8);
					}
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
	private void resizeEarthStuff(List<StuffOnEarth> listOfObjects, double parameter) {
		for (var earthObject : listOfObjects) {
			for (var part : earthObject.getShape()) {
				part.setScaleX(part.getScaleX()*parameter);
                part.setScaleY(part.getScaleY()*parameter);
				part.setScaleZ(part.getScaleZ()*parameter);
			}
			earthObject.getAuraPane().translateZProperty().bind(Earth.translateZProperty().add(parameter*earthObject.angle));
		
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