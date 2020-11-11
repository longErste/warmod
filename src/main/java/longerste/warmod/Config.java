package longerste.warmod;

import longerste.warmod.proxy.CommonProxy;
import net.minecraftforge.common.config.Configuration;
import org.apache.logging.log4j.Level;

public class Config {
  private static final String CATEGORY_GENERAL = "General";
  private static final String CATEGORY_GUI = "TeamGUI";
  private static final String CATEGORY_FOUNDATION = "Foundation Block";
  private static final String CATEGORY_SIDEBASE = "Side Base";
  private static final String CATEGORY_BASEBUILDBLOCK = "Base building";
  private static final String CATEGORY_GUARDIAN = "Guardians";
  private static final String CATEGORY_OUTPUT = "Output";

  // General Settings
  public static boolean enableMod = true;
  public static String enableModComment =
      "Why not just uninstalled when you disable the whole mod ?";

  public static boolean enableModChunkloading = true;
  public static String enableModChunkLoadingComment =
      " Enable the chunkloading funciton of this mod";

  public static int serverMaxChunkLoad = 60;
  public static String serverMaxChunkComment =
      "Maximum amount of Chunks loaded by the server using the foundation block";

  public static boolean enableCustomTier = true;
  public static String enableCustomTierComment = "True when custom tier of bases are enabled";

  public static int maxNoOfTeams = 20;
  public static String maxNoOfTeamsComment = "Maximum numbers of team";

  // GUI Settings
  public static boolean enableTeamGUI = true;
  public static String enableTeamGUIComment = "Enable The Team GUI functionality";

  // Foundation Settings
  public static int minimumHardness = 50;
  public static int maximumHardness = 50000;
  public static int startingHardness = 100;

  // Side Base Settings
  public static boolean enableSideBase = true;
  public static String enableSideBaseComment = "Enable The Side Base functionality";

  // Base Building Settings
  public static boolean enableBaseBuilding = true;
  public static String enableBaseBuildingComment = "Enable The Base Building functionality";

  // Guardians Settings
  public static boolean enableGuardians = true;
  public static String enableGuardiansComment = "Enable The Guardians System";

  // Output Settings
  public static boolean enableOutputBlock = true;
  public static String enableOutputComment = "Enable The Output block functionality";

  public static void readConfig() {
    Configuration cfg = CommonProxy.config;

    try {
      cfg.load();
      initGeneralConfig(cfg);
      initGUIConfig(cfg);
      initFoundationConfig(cfg);
      initSBConfig(cfg);
      initBBConfig(cfg);
      initGuardianConfig(cfg);
      initOutputConfig(cfg);
    } catch (Exception e1) {
      WarMod.logger.log(Level.ERROR, "Problem loading config file!", e1);
    } finally {
      if (cfg.hasChanged()) {
        cfg.save();
      }
    }
  }

  private static void initGeneralConfig(Configuration cfg) {
    String category = CATEGORY_GENERAL;

    cfg.addCustomCategoryComment(
        category, "General Configuration, mainly for tweaking core mod features");

    enableMod = cfg.getBoolean("Enable Mod", category, enableMod, enableModComment);

    serverMaxChunkLoad =
        cfg.getInt(
            "Maximum of Chunks loaded",
            category,
            serverMaxChunkLoad,
            0,
            204800,
            serverMaxChunkComment);

    enableCustomTier = cfg.getBoolean("Enable Custom Tier list", category, enableCustomTier, enableCustomTierComment);
  }

  private static void initGUIConfig(Configuration cfg) {
    cfg.addCustomCategoryComment(CATEGORY_GUI, "Configuration on the Team GUI");
  }

  private static void initFoundationConfig(Configuration cfg) {
    cfg.addCustomCategoryComment(CATEGORY_FOUNDATION, "Config on the foundation block");
    minimumHardness = cfg.getInt("Minimum_Hardness", CATEGORY_FOUNDATION, minimumHardness, 5, maximumHardness, "min 5" );
    maximumHardness = cfg.getInt("Maximum_Hardness", CATEGORY_FOUNDATION, maximumHardness, minimumHardness, 100000, "max 50000");
    startingHardness = cfg.getInt("Starting_Hardness", CATEGORY_FOUNDATION, startingHardness, minimumHardness, maximumHardness, "Starting Hardness");
  }

  private static void initSBConfig(Configuration cfg) {
    String category = CATEGORY_SIDEBASE;
    cfg.addCustomCategoryComment(category, "Config on the alternative bases");
  }

  private static void initGuardianConfig(Configuration cfg) {
    String category = CATEGORY_GUARDIAN;
    cfg.addCustomCategoryComment(category, "Guardians System");
  }

  private static void initBBConfig(Configuration cfg) {
    String category = CATEGORY_BASEBUILDBLOCK;
    cfg.addCustomCategoryComment(category, "Config on the Base Building System");
  }

  private static void initOutputConfig(Configuration cfg) {
    String category = CATEGORY_OUTPUT;
    cfg.addCustomCategoryComment(category, "Config on the output block");
  }
}
