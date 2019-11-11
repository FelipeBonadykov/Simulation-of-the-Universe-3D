package apartment;

import java.util.ArrayList;
import java.util.List;

import com.interactivemesh.jfx.importer.ModelImporter;
import com.interactivemesh.jfx.importer.obj.ObjModelImporter;

import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Scale;
import javafx.scene.transform.Translate;

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
	static List<Group> actionstuff = new ArrayList<>();
	static Box[] wall;
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
		window1.getTransforms().add(new Rotate(90, Rotate.Y_AXIS));
		window1b.getTransforms().add(new Rotate(90, Rotate.Y_AXIS));
		apartment.getChildren().addAll(window1);
		apartment.getChildren().addAll(window1b);
		
		var window2  = getShape3D("window", .7, -249, 130, -40);
		var window2b = getShape3D("window", .7, -253, 130, -40);
		window2.getTransforms().add(new Rotate(90, Rotate.Y_AXIS));
		window2b.getTransforms().add(new Rotate(90, Rotate.Y_AXIS));
		apartment.getChildren().addAll(window2);
		apartment.getChildren().addAll(window2b);
		
		var window3  = getShape3D("window", .7, 248, 0, -40);
		var window3b = getShape3D("window", .7, 253, 0, -40);
		window3.getTransforms().add(new Rotate(90, Rotate.Y_AXIS));
		window3b.getTransforms().add(new Rotate(90, Rotate.Y_AXIS));
		apartment.getChildren().addAll(window3);
		apartment.getChildren().addAll(window3b);
		
		var door1 = getShape3D("door", 10, 0, 62, -65);
		door1.setVisible(false);
		door1.getTransforms().addAll(new Rotate(90, Rotate.Y_AXIS), new Scale(.7,.7,.7));
		door1.getChildren().get(3).setVisible(true);
		apartment.getChildren().addAll(door1);
		
		var door2 = getShape3D("door", 10, 26, -98, -65);
		door2.setVisible(false);
		door2.getTransforms().add(new Scale(.7,.7,.7));
		door2.getChildren().get(3).setVisible(true);
		apartment.getChildren().addAll(door2);
		
		var door3 = getShape3D("door", 10, -28, -10, -65);
		door3.setVisible(false);
		door3.getTransforms().add(new Scale(.7,.7,.7));
		door3.getChildren().get(3).setVisible(true);
		apartment.getChildren().addAll(door3);
		
		var exteriorDoor  = getShape3D("exterior_door", 9, -1, 120, -60);
		var exteriorDoorB = getShape3D("exterior_door", 9, 2, 120, -60);
		exteriorDoor.getTransforms().add(new Rotate(90, Rotate.Y_AXIS));
		exteriorDoorB.getTransforms().add(new Rotate(90, Rotate.Y_AXIS));
		apartment.getChildren().addAll(exteriorDoor);
		apartment.getChildren().addAll(exteriorDoorB);
		
	}
    private static void addHomeStuff() {
		var sofa = getShape3D("sofa", .7, -180, 200, 0);
		sofa.getTransforms().add(new Rotate(180, Rotate.Y_AXIS));
		apartment.getChildren().addAll(sofa);
		actionstuff.add(sofa);
		
		var screen = new Group(new ImageView("file:files/apartment/3d models/0 PEOPLE/watchTV man/nature.gif"));
		screen.getTransforms().addAll
		(new Translate(-215, 5, -90), new Rotate(90, Rotate.X_AXIS), new Scale(.19,.2,1));
		screen.setVisible(false);
		apartment.getChildren().addAll(screen);
		actionstuff.add(screen);
		
		var bed = getShape3D("bed", .7, 170, -80, 0);
	    bed.getTransforms().add(new Rotate(90, Rotate.Y_AXIS));
		apartment.getChildren().addAll(bed);
		actionstuff.add(bed);
		
		var fridge = getShape3D("fridge", 1, -35,-210, -60);
		var openedFridge = new Group(new ImageView("file:files/apartment/3d models/0 PEOPLE/eat/fridge.jpg"));
		openedFridge.setVisible(false);
		openedFridge.getTransforms().addAll(new Translate(-105, -177, -120), new Rotate(90, Rotate.X_AXIS), new Scale(.14,.13,.15));
		apartment.getChildren().addAll(openedFridge);
		actionstuff.add(openedFridge);
		apartment.getChildren().addAll(fridge);
		
		var sedan = getShape3D("suv", .5, 250, 200, -50);
		sedan.getTransforms().add(new Rotate(90, Rotate.Y_AXIS));
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
		desk.getTransforms().add(new Rotate(180, Rotate.Y_AXIS));
		apartment.getChildren().addAll(desk);
		
		var chair = getShape3D("chair", .7, -50, 180, 0);
		apartment.getChildren().addAll(chair);
		
		var laptop =  getShape3D("laptop", 1, -50, 220, -53);
		laptop.getTransforms().add(new Rotate(180, Rotate.Y_AXIS));
		apartment.getChildren().addAll(laptop);
		
		var chest =getShape3D("chest", .6, 5, -10, 0);
		chest.getTransforms().add(new Rotate(-90, Rotate.Y_AXIS));
		apartment.getChildren().addAll(chest);
		
		var bedsideTable1 = getShape3D("bedside_table", .6, 220, -70, 0);
		bedsideTable1.getTransforms().add(new Rotate(90, Rotate.Y_AXIS));
		var lamp1 = getShape3D("lamp", 90, 230, -110, -25);
		apartment.getChildren().addAll(bedsideTable1);
		apartment.getChildren().addAll(lamp1);

		var bedsideTable2 = getShape3D("bedside_table", .6, 220, 60, 0);
		bedsideTable2.getTransforms().add(new Rotate(90, Rotate.Y_AXIS));
		var lamp2 = getShape3D("lamp", 90, 230, 25, -25);
		apartment.getChildren().addAll(bedsideTable2);
		apartment.getChildren().addAll(lamp2);
		
		var sink = getShape3D("sink", .6, 40, -250, 0);
		apartment.getChildren().addAll(sink);
		
		var bath = getShape3D("bath_L", .8, 210, -170, 0);
		bath.getTransforms().add(new Rotate(90, Rotate.Y_AXIS));
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
	
    private static Group getShape3D(String name, double d, int x, int y, int z, String... extension) {
		ModelImporter modelImporter = new ObjModelImporter();
		String direction = "files/apartment/3d models/"+name+"/"+name+".obj";
		Group mesh=null;
		try {
			modelImporter.read(direction);//directory of object in relation to the project
			mesh = new Group((Node[]) modelImporter.getImport());
			modelImporter.close();
		} catch (Exception e) {
			System.err.println(" FILE NOT FOUND  "+e.getMessage());
		}	
	    mesh.getTransforms().addAll(new Rotate(90, Rotate.X_AXIS), new Scale(d, d, d));
		mesh.setTranslateX(x);
		mesh.setTranslateY(y);
		mesh.setTranslateZ(z);
		return mesh;
	}
}
