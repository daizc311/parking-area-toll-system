package run.bequick.dreamccc.pats.gen;

import freemarker.template.Configuration;
import freemarker.template.Template;
import lombok.SneakyThrows;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import run.bequick.dreamccc.pats.domain.*;

import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.HashMap;

public class FreemarkerGen {

    final static String PROJECT_PATH = "D:\\IdeaProjects\\demo\\pats-server\\";

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

            var writer = new OutputStreamWriter(new FileOutputStream(
                    PROJECT_PATH +
                            "src\\main\\java\\" +
                            targetFilePath+"\\"+
                            targetClassName + ".java"));
            template.process(map, writer);
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
