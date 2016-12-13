package capstoneproject.androidnanodegree.com.cochelper.database;

import net.simonvt.schematic.annotation.AutoIncrement;
import net.simonvt.schematic.annotation.DataType;
import net.simonvt.schematic.annotation.NotNull;
import net.simonvt.schematic.annotation.PrimaryKey;

public class DatabseColumns {
    @DataType(DataType.Type.INTEGER)
    @NotNull
    @PrimaryKey
    @AutoIncrement
    public static final String ID = "_id";

    @DataType(DataType.Type.TEXT)
    @NotNull
    public static final String VIDEOID = "videoId";

    @DataType(DataType.Type.TEXT)
    @NotNull
    public static final String TITLE = "title";

    @DataType(DataType.Type.TEXT)
    @NotNull
    public static final String DESCRIPTION = "description";

    @DataType(DataType.Type.TEXT)
    @NotNull
    public static final String URL = "url";

}
