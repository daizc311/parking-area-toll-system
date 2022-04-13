package run.bequick.dreamccc.pats.gen;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import run.bequick.dreamccc.pats.domain.*;

import java.io.*;
import java.util.HashMap;
import java.util.function.Function;

@Log4j2
public class CodeGenerator {

    public final static String BASE_PROJECT_PATH_2 = "D:\\IdeaProjects\\demo\\pats-server\\";
    public final static String BASE_PROJECT_PATH = "D:\\Git_Project\\parking-area-toll-system\\pats-server\\";
    public static final String BASE_PROJECT_PACKAGE = "run.bequick.dreamccc.pats";
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
        Template[] templates = {
                configuration.getTemplate("Repository.ftl"),
                configuration.getTemplate("DService.ftl"),
                configuration.getTemplate("DServiceImpl.ftl"),
        };
        Class<?>[] classes = {
                ParkingSetting.class
//                ParkingCard.class,
//                ParkingCardAmountLogDO.class,
//                Customer.class,
//                CarInfo.class,
//                CarParkingLogDO.class,
//                CarParkingStatus.class
        };
        for (Class<?> aClass : classes) {
            String entityPackage = aClass.getPackageName();
            String entityClassName = aClass.getSimpleName();
            //
            String repoClassName = entityClassName + "Repository";
            String repoPackage = BASE_PROJECT_PACKAGE + ".repository";
            String repoFilePath = repoPackage.replace(".", "\\") + "\\";
            //
            String dServiceClassName = entityClassName + "DService";
            String dServicePackage = BASE_PROJECT_PACKAGE + ".service.data";
            String dServiceFilePath = dServicePackage.replace(".", "\\") + "\\";
            //
            String dServiceImplClassName = entityClassName + "DServiceImpl";
            String dServiceImplPackage = BASE_PROJECT_PACKAGE + ".service.data";
            String dServiceImplFilePath = dServiceImplPackage.replace(".", "\\") + "\\";

            var map = new HashMap<String, String>();
            map.put("entityPackage", entityPackage);
            map.put("entityClassName", entityClassName);
//
            map.put("repoClassName", repoClassName);
            map.put("repoPackage", repoPackage);
//
            map.put("dServiceClassName", dServiceClassName);
            map.put("dServicePackage", dServicePackage);
//
            map.put("dServiceImplClassName", dServiceImplClassName);
            map.put("dServiceImplPackage", dServiceImplPackage);

            writer(generate(configuration.getTemplate("Repository.ftl"), map),
                    BASE_PROJECT_PATH + JAVA_SRC_PATH + repoFilePath + repoClassName +   ".java");
            writer(generate(configuration.getTemplate("DService.ftl"), map),
                    BASE_PROJECT_PATH + JAVA_SRC_PATH + dServiceFilePath + dServiceClassName + ".java");
            writer(generate(configuration.getTemplate("DServiceImpl.ftl"), map),
                    BASE_PROJECT_PATH + JAVA_SRC_PATH + dServiceImplFilePath + dServiceImplClassName + ".java");

        }
    }

    private static String generate(Template template, HashMap<String, String> map) {
        var byteArrayOutputStream = new ByteArrayOutputStream();
        var writer = new OutputStreamWriter(byteArrayOutputStream);
        try {
            template.process(map, writer);
        } catch (TemplateException e) {
            log.error("模板发生异常", e);
            throw new RuntimeException(e);
        } catch (IOException ignored) {
        }
        return byteArrayOutputStream.toString();
    }

    private static void writer(String data, String filePath) throws IOException {
        File file = new File(filePath);
        if (!file.exists()) {
            log.info("开始写入文件:{}", file.getName());
            var writer = new OutputStreamWriter(new FileOutputStream(file));
            writer.write(data);
            writer.flush();
            writer.close();
            log.debug("文件写入完成:path={}", file.getAbsolutePath());
        } else {
            log.debug("跳过已存在的文件:path={}", file.getAbsolutePath());
        }
    }


    @Getter
    @RequiredArgsConstructor
    static class GenEntity {
        private final String name;
        private final Function<String, Template> templateProvider;
        private final Function<String, String> packageNameProvider;
        private final Function<String, String> filePathProvider;

        public Template getTemplate() {
            return templateProvider.apply(this.name);
        }

        public String getPackageName() {
            return packageNameProvider.apply(this.name);
        }

        public String getFilePath() {
            return filePathProvider.apply(this.name);
        }
    }
}
