package com.chetan.springbootbatch.config;


import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;

import com.chetan.springbootbatch.model.Employee;

@Configuration
@EnableBatchProcessing
public class SpringBatchConfig {
	
	@Bean
	public Job job(JobBuilderFactory jobBuilderFactory, StepBuilderFactory stepBuilderFactory,
			ItemReader<Employee> itemReader, ItemProcessor<Employee,Employee> itemProcessor,
			ItemWriter<Employee> itemWriter) {
		
		Step step = stepBuilderFactory.get("ETL-file-load")
				.<Employee,Employee>chunk(100)
				.reader(itemReader)
				.processor(itemProcessor)
				.writer(itemWriter)
				.build();				
				
		return jobBuilderFactory.get("ETL-Load").incrementer(new RunIdIncrementer())
		.start(step).build();
	}
	
	@Bean
	public FlatFileItemReader<Employee> itemReader(@Value("${input}") Resource resource){
		
		FlatFileItemReader<Employee> flatFileItemReader = new FlatFileItemReader<Employee>();
		flatFileItemReader.setResource(resource);
		flatFileItemReader.setName("CSV-Reader");
		flatFileItemReader.setLinesToSkip(1);
		flatFileItemReader.setLineMapper(lineMapper());
		
		return flatFileItemReader;
	}
	
	@Bean
	public LineMapper<Employee> lineMapper(){
		
		DefaultLineMapper<Employee> defaultLineMapper = new DefaultLineMapper<Employee>();
		DelimitedLineTokenizer lineTokenizer = new DelimitedLineTokenizer();
		
		lineTokenizer.setDelimiter(",");
		lineTokenizer.setStrict(false);
		lineTokenizer.setNames(new String[] {"id", "name", "dept", "salary"});
		
		BeanWrapperFieldSetMapper<Employee> fieldSetMapper = new BeanWrapperFieldSetMapper<>();
		fieldSetMapper.setTargetType(Employee.class);
		
		defaultLineMapper.setLineTokenizer(lineTokenizer);
		return null;
	}

}
