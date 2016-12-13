package capstoneproject.androidnanodegree.com.cochelper.database;

import net.simonvt.schematic.annotation.Database;
import net.simonvt.schematic.annotation.Table;

@Database(version = DatabseDefinition.VERSION)
public final class DatabseDefinition {
    private DatabseDefinition() {
    }

    public static final int VERSION = 1;
    @Table(DatabseColumns.class)
    public static final String QUOTES = "quotes";
}
