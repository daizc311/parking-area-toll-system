package run.bequick.dreamccc.pats.gen;

import freemarker.template.Configuration;
import freemarker.template.Template;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import run.bequick.dreamccc.pats.domain.*;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.HashMap;

@Log4j2
public class FreemarkerGen {

    final static String PROJECT_PATH = "D:\\IdeaProjects\\demo\\pats-server\\";
    public static final String JAVA_SRC_PATH = "src\\main\\java\\";
    public static final String KOTLIN_SRC_PATH = "src\\main\\kotlin\\";

    @SneakyThrows
    public static void main(String[] args) {
        Configuration configuration = new Configuration(Configuration.DEFAULT_INCOMPATIBLE_IMPROVEMENTS);

        configuration.setDefaultEncoding("UTF-8");
        // 指定模板的路径
        DefaultResourceLoader resourceLoader = new DefaultResourceLoader();
        Resource resource = resourceLoader.getResource("codeGenerate");
        configuration.setDirectoryForTemplateLoading(resource.getFile());
        configuration.unsetCacheStorage();
        // 根据模板名称获取路径下的模板
        Template template = configuration.getTemplate("CommonRepository.ftl");

        Class<?>[] classes = {
                ParkingCard.class,
                ParkingCardAmountLogDO.class,
                Customer.class,
                CarInfo.class,
                CarParkingLogDO.class,
                CarParkingStatus.class
        };
        for (Class<?> aClass : classes) {
            String entityPackage = aClass.getPackageName();
            String entityClassName = aClass.getSimpleName();
            String targetClassName = entityClassName + "Repository";
            String targetPackage = "run.bequick.dreamccc.pats.repository";
            String targetFilePath = targetPackage.replace(".", "\\");
            var map = new HashMap<String, String>();
            map.put("entityPackage", entityPackage);
            map.put("entityClassName", entityClassName);
            map.put("targetClassName", targetClassName);
            map.put("targetPackage", targetPackage);
            map.put("targetFilePath", targetFilePath);


            File file = new File(PROJECT_PATH + JAVA_SRC_PATH + targetFilePath + "\\" +
                    targetClassName + ".java");
            if (!file.exists()) {
                log.info("开始生成:{}", targetClassName + ".java");
                var writer = new OutputStreamWriter(new FileOutputStream(file));
                template.process(map, writer);
            } else {
                log.info("文件[{}]已存在，跳过生成\n跳过生成 path={}", targetClassName + ".java", file.getAbsolutePath());
            }
        }


//        Entity entity = new Entity();


//        new JavaProperties().var
//        String javaName = javaProperties.getEntityName().concat(ext);
//        String packageName = javaProperties.getPkg();
//
//        String out = rootPath.concat(Stream.of(packageName.split("\\."))
//                .collect(Collectors.joining("/", "/", "/" + javaName)));
    }
//
//    @Data
//    static class Entity {
//        private String targetPackageName;
//        private String targetClassName;
//        private String
//    }
}
