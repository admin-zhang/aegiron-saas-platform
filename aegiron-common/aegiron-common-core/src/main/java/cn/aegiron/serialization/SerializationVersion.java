package cn.aegiron.serialization;

/**
 * @author panda
 * @version 1.0
 * @ClassName SerializationVersion.java
 * @Description 序列化版本号生成类
 * @since 2026/2/8 17:29
 */
public class SerializationVersion {

    // 从配置文件或常量获取项目版本号
    private static final String PROJECT_VERSION = "1.0.0";  // 示例项目版本号

    // 动态生成的 serialVersionUID
    public static final long serialVersionUID = generateSerialVersionUID();

    private static long generateSerialVersionUID() {
        int hash = PROJECT_VERSION.hashCode();
        // 使用类名或其他标识信息生成唯一的ID
        return ((long) hash << 32) | SerializationVersion.class.getName().hashCode();
    }
}
