package stuff_on_earth;

import com.interactivemesh.jfx.importer.ModelImporter;
import com.interactivemesh.jfx.importer.obj.ObjModelImporter;
import com.interactivemesh.jfx.importer.tds.TdsModelImporter;

import javafx.scene.Group;
import javafx.scene.Node;

final class Shape3dConstructor {
	static Group getShape3D(String name, String extension) {
		ModelImporter modelImporter;
		switch (extension) {
		case "obj":
			modelImporter = new ObjModelImporter();
			break;
		case "3ds":
			modelImporter = new TdsModelImporter();
			break;
		default:
			throw new RuntimeException("You have used an unsupported type");
		}
		Group model = null;
		try {
			modelImporter.read("files/3d/"+name+"/"+name+"."+extension);//directory of object in relation to the project
			model = new Group((Node[]) modelImporter.getImport());
			modelImporter.close();
		} catch (Exception e) {
			System.err.println(" FILE NOT FOUND  "+e.getMessage());
		}
		return model;
	}
	private Shape3dConstructor(){}
}
