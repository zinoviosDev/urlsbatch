package fr.marcdev.urlsbatch.config;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.aop.Advisor;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.interceptor.PerformanceMonitorInterceptor;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;

@Configuration
@Aspect
public class PerformanceMonitorConfig {
	
	@Bean
    public PerformanceMonitorInterceptor performanceMonitorInterceptor() {
        return new PerformanceMonitorInterceptor(true);
    }
 
    /**
     * Pointcut for execution of methods on classes annotated with {@link Service}
     * annotation
     */
    @Pointcut("execution(* fr.marcdev.urlsbatch.service..*.*(..))")
    public void serviceAnnotation() {
    }
    
    @Pointcut("execution(* fr.marcdev.urlsbatch.util..*.*(..))")
    public void utilAnnotation() {
    }
 
    @Pointcut("serviceAnnotation() || utilAnnotation()")
    public void performanceMonitor() {
    }
 
    @Bean
    public Advisor performanceMonitorAdvisor() {
        AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
        pointcut.setExpression("fr.marcdev.urlsbatch.config.PerformanceMonitorConfig.performanceMonitor()");
        return new DefaultPointcutAdvisor(pointcut, performanceMonitorInterceptor());
    }
}
