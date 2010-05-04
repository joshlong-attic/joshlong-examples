package com.joshlong.springbatch.starter;

import org.apache.commons.lang.SystemUtils;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.File;
import java.util.Date;

/**
 * @author <a href="mailto:josh@joshlong.com">Josh Long</a>
 */
public class Main {
    public static void main(String[] args) throws Throwable {
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("flexible-reader.xml");
        applicationContext.start();
        applicationContext.registerShutdownHook();

        File desktopFiles = new File(new File(SystemUtils.getUserHome(), "Desktop"), "files");

        FlexibleFileProcessor flexibleFlexibleFileProcessor = applicationContext.getBean(FlexibleFileProcessor.class);

        Date when = new Date();

        FileBatchProcessContext[] jobs = {
                new FileBatchProcessContext(new File(desktopFiles, "cats.txt"), DelimitedLineTokenizer.DELIMITER_TAB,
                                            "category,cnt".split(","), when),
                new FileBatchProcessContext(new File(desktopFiles, "city_cuisine.csv"),
                                            DelimitedLineTokenizer.DELIMITER_TAB, "cat_id,city_id".split(","), when)
        };

        for (FileBatchProcessContext fileBatchProcessContext : jobs) {
            System.out.println(fileBatchProcessContext.toString());
            flexibleFlexibleFileProcessor.process(fileBatchProcessContext);
        }
    }
}
