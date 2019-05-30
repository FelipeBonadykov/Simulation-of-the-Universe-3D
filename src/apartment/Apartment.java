package apartment;

import java.util.ArrayList;
import java.util.List;

import com.interactivemesh.jfx.importer.ModelImporter;
import com.interactivemesh.jfx.importer.obj.ObjModelImporter;
import javafx.animation.Animation;
import javafx.animation.Interpolator;
import javafx.animation.PathTransition;
import javafx.animation.RotateTransition;
import javafx.animation.Timeline;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import javafx.scene.shape.Circle;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Scale;
import javafx.scene.transform.Translate;
import javafx.util.Duration;
import space.MainSpace;
import stuff_on_earth.StuffOnEarth;

public class Apartment {
	private static Group apartment = new Group();
	public static Group getApartment() {
		createFloor();
		buildWalls();
		addDoorsAndWindows();
		addHomeStuff();
		addActor();
		return apartment;
	}
	static List<Node[]> actionstuff = new ArrayList<>();
	static Box[] wall;
	public static void putOnEarth(StuffOnEarth house) {
		Scale scale = new Scale();
		scale.setX(.11);
		scale.setY(.11);
		scale.setZ(.11);
		apartment.getTransforms().addAll(new Rotate(90, Rotate.X_AXIS), new Rotate(-85, Rotate.Y_AXIS), scale);
		
		var rotate = new RotateTransition(new Duration(360_00), apartment);
		rotate.setAxis(Rotate.Z_AXIS);
		rotate.setByAngle(360);
		rotate.setFromAngle(house.getMovement().getCurrentTime().toMillis()/100);
		rotate.setInterpolator(Interpolator.LINEAR);
		rotate.setCycleCount(Timeline.INDEFINITE);
		rotate.play();
		
		apartment.translateXProperty().bind(house.getAura().translateXProperty().add(MainSpace.spaceObjects.get(3).translateXProperty()));
		apartment.translateYProperty().bind(house.getAura().translateYProperty().add(MainSpace.spaceObjects.get(3).translateYProperty()));
		apartment.translateZProperty().bind(house.getAura().translateZProperty().add(MainSpace.spaceObjects.get(3).translateZProperty()));
		
	    MainSpace.root.getChildren().addAll(apartment);
	}
	public static void setCameraOnApartment(Pane cameraPane, StuffOnEarth house) {
		var camera = cameraPane.getChildren().remove(0);
		house.getAuraPane().getChildren().add(camera);
		camera.getTransforms().addAll(new Rotate(-170, Rotate.Y_AXIS), new Translate(-650, 20, 1000));
		
		var rotateCamera = new RotateTransition(new Duration(360_00), camera);
		rotateCamera.setAxis(Rotate.Z_AXIS);
		rotateCamera.setByAngle(360);
		rotateCamera.setFromAngle(house.getMovement().getCurrentTime().toMillis()/100+70);
		rotateCamera.setInterpolator(Interpolator.LINEAR);
		rotateCamera.setCycleCount(Timeline.INDEFINITE);
		rotateCamera.play();
		
		var moveCamera = new PathTransition(new Duration(360_00), new Circle(50), camera);
		moveCamera.setInterpolator(Interpolator.LINEAR);
		moveCamera.setCycleCount(Animation.INDEFINITE);
		moveCamera.playFrom(new Duration(house.getMovement().getCurrentTime().toMillis()));
	}

	private static void createFloor() {
		Box floor = new Box(500, 500, 1);
		floor.setMaterial(new PhongMaterial(Color.WHITE, new Image("file:files/apartment/apartment.jpg"), null, null, null));
		apartment.getChildren().add(floor);
	}
	private static void buildWalls() {
		Box [] walls = new Box[9];
		walls[0] = new Box(500, 140, 1);
		walls[0].setTranslateY(-250);
		walls[1] = new Box(1, 140, 500);
		walls[1].setTranslateX(-250);
		walls[2] = new Box(250, 140, 1);
		walls[2].setTranslateX(-500/4);
		walls[2].setTranslateY(250);
		walls[3] = new Box(1, 140, 500/3*2);
		walls[3].setTranslateX(250);
		walls[3].setTranslateY(-500/3+80);
		walls[4] = new Box(200, 140, 1);
		walls[4].setTranslateX(500/4+25);
		walls[4].setTranslateY(-250/3-10);
		walls[5] = new Box(250, 140, 1);
		walls[5].setTranslateX(500/4);
		walls[5].setTranslateY(250/3);
		walls[6] = new Box(1, 140, 290);
		walls[6].setTranslateX(0);
		walls[6].setTranslateY(-500/4+20);
		walls[7] = new Box(200, 140, 1);
		walls[7].setTranslateX(-500/4-28);
		walls[7].setTranslateY(-15);
		walls[8] = new Box(1, 140, 500/3);
		walls[8].setTranslateX(0);
		walls[8].setTranslateY(500/3);
		for (Box wall : walls) {
			wall.getTransforms().add(new Rotate(90, Rotate.X_AXIS));
			wall.setMaterial(new PhongMaterial(Color.WHITE, new Image("file:files/apartment/wallpaper.jpg"), null, null, null));
			wall.setTranslateZ(-70);
		}
		wall = walls;
		apartment.getChildren().addAll(walls);
		
		Box[] exteriorWalls = new Box[6];
		exteriorWalls[0] = new Box(504, 140, 1);
		exteriorWalls[0].setTranslateX(0);
		exteriorWalls[0].setTranslateY(-251);
		exteriorWalls[1] = new Box(1, 140, 504);
		exteriorWalls[1].setTranslateX(-251);
		exteriorWalls[1].setTranslateY(0);
		exteriorWalls[2] = new Box(254, 140, 1);
		exteriorWalls[2].setTranslateX(-500/4);
		exteriorWalls[2].setTranslateY(251);
		exteriorWalls[3] = new Box(1, 140, 504/3*2);
		exteriorWalls[3].setTranslateX(251);
		exteriorWalls[3].setTranslateY(-500/3+83);
		exteriorWalls[4] = new Box(1, 140, 500/3+2);
		exteriorWalls[4].setTranslateX(1);
		exteriorWalls[4].setTranslateY(500/3+1);
		exteriorWalls[5] = new Box(253, 140, 1);
		exteriorWalls[5].setTranslateX(500/4+1);
		exteriorWalls[5].setTranslateY(250/3+1);
		for (Box wall : exteriorWalls) {
			wall.getTransforms().add(new Rotate(90, Rotate.X_AXIS));
			wall.setMaterial(new PhongMaterial(Color.WHITE, new Image("file:files/apartment/wall.jpg"), null, null, null));
			wall.setTranslateZ(-70);
		}
		apartment.getChildren().addAll(exteriorWalls);
	}
	private static void addDoorsAndWindows() {
		var window1 = getShape3D("window", .7, -249, -100, -40);
		var window1b = getShape3D("window", .7, -253, -100, -40);
		for (var windowPart : window1) 
			windowPart.getTransforms().add(new Rotate(90, Rotate.Y_AXIS));
		for (var windowPart : window1b) 
			windowPart.getTransforms().add(new Rotate(90, Rotate.Y_AXIS));
		apartment.getChildren().addAll(window1);
		apartment.getChildren().addAll(window1b);
		
		var window2  = getShape3D("window", .7, -249, 130, -40);
		var window2b = getShape3D("window", .7, -253, 130, -40);
		for (var windowPart : window2) 
			windowPart.getTransforms().add(new Rotate(90, Rotate.Y_AXIS));
		for (var windowPart : window2b) 
			windowPart.getTransforms().add(new Rotate(90, Rotate.Y_AXIS));
		apartment.getChildren().addAll(window2);
		apartment.getChildren().addAll(window2b);
		
		var window3  = getShape3D("window", .7, 248, 0, -40);
		var window3b = getShape3D("window", .7, 253, 0, -40);
		for (var windowPart : window3) 
			windowPart.getTransforms().add(new Rotate(90, Rotate.Y_AXIS));
		for (var windowPart : window3b) 
			windowPart.getTransforms().add(new Rotate(90, Rotate.Y_AXIS));
		apartment.getChildren().addAll(window3);
		apartment.getChildren().addAll(window3b);
		
		var scale = new Scale();
		scale.setX(.7);
		
		var door1 = getShape3D("door", 10, 0, 62, -65);
		for (var doorPart : door1) {
			doorPart.setVisible(false);
			doorPart.getTransforms().addAll(new Rotate(90, Rotate.Y_AXIS), scale);
		}
		door1[3].setVisible(true);
		apartment.getChildren().addAll(door1);
		
		var door2 = getShape3D("door", 10, 26, -98, -65);
		for (var doorPart : door2) {
			doorPart.setVisible(false);
			doorPart.getTransforms().add(scale);
		}
		door2[3].setVisible(true);
		apartment.getChildren().addAll(door2);
		
		var door3 = getShape3D("door", 10, -28, -10, -65);
		for (var doorPart : door3) {
			doorPart.setVisible(false);
			doorPart.getTransforms().add(scale);
		}
		door3[3].setVisible(true);
		apartment.getChildren().addAll(door3);
		
		var exteriorDoor  = getShape3D("exterior_door", 9, -1, 120, -60);
		var exteriorDoorB = getShape3D("exterior_door", 9, 2, 120, -60);
		for (var doorPart : exteriorDoor)
			doorPart.getTransforms().add(new Rotate(90, Rotate.Y_AXIS));
		for (var doorPart : exteriorDoorB)
			doorPart.getTransforms().add(new Rotate(90, Rotate.Y_AXIS));
		apartment.getChildren().addAll(exteriorDoor);
		apartment.getChildren().addAll(exteriorDoorB);
		
	}
    private static void addHomeStuff() {
		var sofa = getShape3D("sofa", .7, -180, 200, 0);
		for (var sofaPart : sofa) 
			sofaPart.getTransforms().add(new Rotate(180, Rotate.Y_AXIS));
		apartment.getChildren().addAll(sofa);
		actionstuff.add(sofa);
		
		ImageView[] screen = {new ImageView("file:files/apartment/3d models/0 PEOPLE/watchTV man/nature.gif")};
		screen[0].getTransforms().addAll(new Translate(-215, 5, -90), new Rotate(90, Rotate.X_AXIS), new Scale(.19,.2,1));
		screen[0].setVisible(false);
		apartment.getChildren().addAll(screen);
		actionstuff.add(screen);
		
		var bed = getShape3D("bed", .7, 170, -80, 0);
	    for (var bedPart : bed) 
	    	bedPart.getTransforms().add(new Rotate(90, Rotate.Y_AXIS));
		apartment.getChildren().addAll(bed);
		actionstuff.add(bed);
		
		var fridge = getShape3D("fridge", 1, -35,-210, -60);
		ImageView[] openedFridge = {new ImageView("file:files/apartment/3d models/0 PEOPLE/eat/fridge.jpg")};
		openedFridge[0].setVisible(false);
		openedFridge[0].getTransforms().addAll(new Translate(-105, -177, -120), new Rotate(90, Rotate.X_AXIS), new Scale(.14,.13,.15));
		apartment.getChildren().addAll(openedFridge);
		actionstuff.add(openedFridge);
		apartment.getChildren().addAll(fridge);
		
		var sedan = getShape3D("suv", .5, 250, 200, -50);
		for (Node sedanPart : sedan) 
			sedanPart.getTransforms().add(new Rotate(90, Rotate.Y_AXIS));
		apartment.getChildren().addAll(sedan);
		
		var saloonTable = getShape3D("saloon_table", .7, -180, 130, 0);
		apartment.getChildren().addAll(saloonTable);
		
		var newspaper = getShape3D("newspaper", 1, -200, 130, -37);
		apartment.getChildren().addAll(newspaper);
		var comics = getShape3D("bdOuverte", 1, -170, 130, -40);
		apartment.getChildren().addAll(comics);
		
		var bookcase = getShape3D("full-bookcase", .7, -90, 0, -60);
		apartment.getChildren().addAll(bookcase);
		
		var tv = getShape3D("tv", 1, -180, 0, -30);
		apartment.getChildren().addAll(tv);
		
		var tvShelf = getShape3D("tv_shelf", 1, -180, 10, -30);
		apartment.getChildren().addAll(tvShelf);
		
		var desk =  getShape3D("desk", .7, -50, 240, 0);
		for (var deskPart : desk) 
			deskPart.getTransforms().add(new Rotate(180, Rotate.Y_AXIS));
		apartment.getChildren().addAll(desk);
		
		var chair = getShape3D("chair", .7, -50, 180, 0);
		apartment.getChildren().addAll(chair);
		
		var laptop =  getShape3D("laptop", 1, -50, 220, -53);
		for (var laptopPart : laptop) 
			laptopPart.getTransforms().add(new Rotate(180, Rotate.Y_AXIS));
		apartment.getChildren().addAll(laptop);
		
		var chest =getShape3D("chest", .6, 5, -10, 0);
		for (var chestPart : chest)
			chestPart.getTransforms().add(new Rotate(-90, Rotate.Y_AXIS));
		apartment.getChildren().addAll(chest);
		
		var bedsideTable1 = getShape3D("bedside_table", .6, 220, -70, 0);
		for (var tablePart : bedsideTable1) 
			tablePart.getTransforms().add(new Rotate(90, Rotate.Y_AXIS));
		var lamp1 = getShape3D("lamp", 90, 230, -110, -25);
		apartment.getChildren().addAll(bedsideTable1);
		apartment.getChildren().addAll(lamp1);

		var bedsideTable2 = getShape3D("bedside_table", .6, 220, 60, 0);
		for (var tablePart : bedsideTable2) 
			tablePart.getTransforms().add(new Rotate(90, Rotate.Y_AXIS));
		var lamp2 = getShape3D("lamp", 90, 230, 25, -25);
		apartment.getChildren().addAll(bedsideTable2);
		apartment.getChildren().addAll(lamp2);
		
		var sink = getShape3D("sink", .6, 40, -250, 0);
		apartment.getChildren().addAll(sink);
		
		var bath = getShape3D("bath_L", .8, 210, -170, 0);
		for (var bathPart : bath) 
			bathPart.getTransforms().add(new Rotate(90, Rotate.Y_AXIS));
		apartment.getChildren().addAll(bath);
		
		var toilet = getShape3D("toilet", 1, 90, -250, 0);
		apartment.getChildren().addAll(toilet);
		
		var kitchenSink = getShape3D("kitchenSink", .7, -100, -220, 0);
		apartment.getChildren().addAll(kitchenSink);
		
		var kitchenIsland = getShape3D("kitchenIsland", 25, -177,-220, -30);
		apartment.getChildren().addAll(kitchenIsland);
		
		var flowers = getShape3D("flowers", .4, -130,-170, -70);
		apartment.getChildren().addAll(flowers);
		
		var kitchenTable = getShape3D("wooden_table_office", .7, -120, -100, 0);
		apartment.getChildren().addAll(kitchenTable);
		
		var food = getShape3D("apples", 1, -120, -100, -50);
		apartment.getChildren().addAll(food);
		
		var stove = getShape3D("stove", .9, -150,-220, -55);
		apartment.getChildren().addAll(stove); 
	}
    private static void addActor() {
		apartment.getChildren().addAll(Actor.getActor());
		apartment.getChildren().addAll(Actor.getWatchingTV());
		apartment.getChildren().addAll(Actor.getSleeping());
		apartment.getChildren().addAll(Actress.getActress());
	}
	
    private static Node[] getShape3D(String name, double d, int x, int y, int z, String... extension) {
		ModelImporter modelImporter = new ObjModelImporter();
		String direction = "files/apartment/3d models/"+name+"/"+name+".obj";
		Node[] mesh=null;
		try {
			modelImporter.read(direction);//directory of object in relation to the project
			mesh = (Node[]) modelImporter.getImport();
			modelImporter.close();
		} catch (Exception e) {
			System.err.println(" FILE NOT FOUND  "+e.getMessage());
		}
		var scale = new Scale();	
		scale.setX(d);
	    scale.setY(d);	
	    scale.setZ(d);	
	    for (var part : mesh) {
	    	part.getTransforms().addAll(new Rotate(90, Rotate.X_AXIS), scale);
		    part.setTranslateX(x);
		    part.setTranslateY(y);
		    part.setTranslateZ(z);
		}
		return mesh;
	}
}
