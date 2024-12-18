package application.singleton;

import javafx.scene.layout.HBox;

public class CommonObjects {

	private static CommonObjects commonObjs = new CommonObjects();

	private HBox mainBox;

	private CommonObjects() {
	}

	public static CommonObjects getInstance() {
		return commonObjs;
	}

	public HBox getMainBox() {
		return mainBox;
	}

	public void setMainBox(HBox mainBox) {
		this.mainBox = mainBox;
	}
}
