package ee.ut.math.tvt.kvaliteetsedideed;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class IntroUI extends JFrame {
  private static final long serialVersionUID = 1L;

  private final String logoPath = "logo.jpg";
  private final String teamLeader = "Mikk Maasik";
  private final String[] teamMembers = new String[] { "Maiko Plinte", "Aleksei Panarin", "Lembit Gerz" };
  private int currentRow = 0;

  private final GridBagConstraints c = new GridBagConstraints();

  public IntroUI() {
    setLayout(new GridBagLayout());
    c.anchor = GridBagConstraints.NORTHWEST;

    addLogo();
    addTeamLeaderLabel();
    addTeamMemberLabels();
    addVersionNumber();
  }

  private void addLogo() {
    try {
      File file = new File(logoPath);
      System.out.println(file.getAbsolutePath());
      JLabel logo = new JLabel(new ImageIcon(ImageIO.read(ClassLoader.getSystemResource("logo.jpg"))));
      logo.setBorder(BorderFactory.createLineBorder(new Color(0, 0, 0)));
      add(logo, getC(0, currentRow++, 2, 1));
    } catch (IOException e) {
      // TODO LOG4J WARNING
      e.printStackTrace();
    }
  }

  private void addTeamLeaderLabel() {
    add(new JLabel("Team leader: "), getC(0, currentRow));
    add(new JLabel(teamLeader), getC(1, currentRow++));
  }

  private void addTeamMemberLabels() {
    add(new JLabel("Team members: "), getC(0, currentRow, 1, teamMembers.length));
    for (String name : teamMembers) {
      add(new JLabel(name), getC(1, currentRow++));
    }
  }

  private void addVersionNumber() {
    Properties versionProperties = getVersionProperties();
    add(new JLabel("Versioon: "), getC(0, currentRow));
    add(new JLabel(versionProperties.getProperty("build.number")), getC(1, currentRow++));
  }

  private Properties getVersionProperties() {
    Properties properties = new Properties();
    try {
      InputStream inPropertiesInputStream = getClass().getClassLoader().getResourceAsStream("version.properties");
      properties.load(inPropertiesInputStream);
      inPropertiesInputStream.close();
    } catch (IOException ex) {
      // TODO log4j logging
    }
    return properties;
  }

  private GridBagConstraints getC(int x, int y, int xWidth, int yWidth) {
    c.gridx = x;
    c.gridy = y;
    c.gridwidth = xWidth;
    c.gridheight = yWidth;
    return c;
  }

  private GridBagConstraints getC(int x, int y) {
    return getC(x, y, 1, 1);
  }

}
