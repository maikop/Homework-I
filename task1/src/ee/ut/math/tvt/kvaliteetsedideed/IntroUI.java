package ee.ut.math.tvt.kvaliteetsedideed;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import org.apache.log4j.Logger;

public class IntroUI extends JFrame {
  private static final long serialVersionUID = 1L;
  private static final Logger LOGGER = Logger.getLogger(IntroUI.class);

  private Properties versionProperties;
  private Properties appProperties;

  private final GridBagConstraints c = new GridBagConstraints();
  private int currentRow = 0;

  public IntroUI() {
    setLayout(new GridBagLayout());
    setTitle("Kvaliteetsed ideed");
    c.anchor = GridBagConstraints.NORTHWEST;
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    versionProperties = loadProperties("version.properties");
    appProperties = loadProperties("application.properties");

    addLogo();
    addTeamNameLabel();
    addTeamLeaderLabel();
    addTeamMemberLabels();
    addVersionNumber();
    pack();
    LOGGER.info("Intro UI started");
  }

  private void addLogo() {
    String logoFileName = appProperties.getProperty("logo.filename");
    try {
      JLabel logo = new JLabel(new ImageIcon(ClassLoader.getSystemResource(logoFileName)));
      logo.setBorder(BorderFactory.createLineBorder(new Color(0, 0, 0)));
      add(logo, getC(0, currentRow++, 2, 1));
    } catch (Exception e) {
      LOGGER.warn("Error loading resource " + logoFileName);
    }
  }

  private void addTeamNameLabel() {
    add(new JLabel("Team name: "), getC(0, currentRow));
    add(new JLabel(appProperties.getProperty("team.name")), getC(1, currentRow++));
  }

  private void addTeamLeaderLabel() {
    add(new JLabel("Team leader: "), getC(0, currentRow));
    add(new JLabel(appProperties.getProperty("team.leader")), getC(1, currentRow++));

    add(new JLabel("Team leader email: "), getC(0, currentRow));
    add(new JLabel(appProperties.getProperty("team.leader.email")), getC(1, currentRow++));
  }

  private void addTeamMemberLabels() {
    String teamMembersString = appProperties.getProperty("team.members");
    if (teamMembersString != null) {
      String[] teamMembers = teamMembersString.split(",");
      add(new JLabel("Team members: "), getC(0, currentRow, 1, teamMembers.length));
      for (String name : teamMembers) {
        add(new JLabel(name), getC(1, currentRow++));
      }
    }
  }

  private void addVersionNumber() {
    add(new JLabel("Version: "), getC(0, currentRow));
    add(new JLabel(versionProperties.getProperty("build.number")), getC(1, currentRow++));
  }

  private Properties loadProperties(String fileName) {
    Properties properties = new Properties();
    try {
      InputStream inPropertiesInputStream = getClass().getClassLoader().getResourceAsStream(fileName);
      properties.load(inPropertiesInputStream);
      inPropertiesInputStream.close();
    } catch (IOException ex) {
      LOGGER.warn("Error loading resource " + fileName);
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