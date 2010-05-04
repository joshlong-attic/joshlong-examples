package com.joshlong.springbatch.starter;

import org.apache.commons.lang.StringUtils;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameter;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Required;

import java.util.HashMap;
import java.util.Map;

/**
 * This processor will handle transforming files into objects, which we will log. Special requirements: we need to vary
 * the column names, and so on. An expansion case will then render those objects into the database (I'll embed H2 or
 * something so that it's imminently relatable).
 *
 * @author <a href="mailto:josh@joshlong.com">Josh Long</a>
 */
public class FlexibleFileProcessor {
    private JobLauncher jobLauncher;
    private Job job;

    public JobExecution process(FileBatchProcessContext fileBatchProcessContext) throws Throwable {
        Map<String, JobParameter> parameterMap = new HashMap<String, JobParameter>();

        parameterMap.put("file", new JobParameter(fileBatchProcessContext.getFile().getAbsolutePath()));
        parameterMap.put("delimiter", new JobParameter(Character.toString(fileBatchProcessContext.getDelimiter())));
        parameterMap.put("timestamp", new JobParameter(fileBatchProcessContext.getDate().getTime()));
        parameterMap.put("columnNames", new JobParameter(StringUtils.join(fileBatchProcessContext.getColumnNames(),
                                                                          ",")));

        return this.jobLauncher.run(job, new JobParameters(parameterMap));
    }

    @Required
    public void setJobLauncher(final JobLauncher jobLauncher) {
        this.jobLauncher = jobLauncher;
    }

    @Required
    public void setJob(final Job job) {
        this.job = job;
    }
}
