package ee.ut.math.tvt.salessystem.ui.tabs;

import java.awt.Component;

public interface SalesSystemTab {
	public Component draw();
	public String getName();
	public void refreshContents();
}
