package com.demo.api.rest.config.jersey.filters;

import com.demo.api.rest.resource.ApiResource;
import org.glassfish.jersey.server.model.ResourceMethod;
import org.glassfish.jersey.server.monitoring.MonitoringStatistics;
import org.glassfish.jersey.server.monitoring.MonitoringStatisticsListener;
import org.glassfish.jersey.server.monitoring.ResourceMethodStatistics;
import org.glassfish.jersey.server.monitoring.ResourceStatistics;
import org.glassfish.jersey.server.monitoring.TimeWindowStatistics;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.Path;
import javax.ws.rs.ext.Provider;
import java.lang.reflect.Method;
import java.util.Map;

@Provider
public class StatisticListener implements MonitoringStatisticsListener {

    private static final long MILLIS_BETWEEN_STATISTICS_LOG = 60 * 60 * 1000;
    private static final long STATISTICS_TIME_WINDOW = 60 * 60 * 1000;
    private static long lastTime = System.currentTimeMillis();

    private static final Logger LOGGER = LoggerFactory.getLogger(StatisticListener.class);

    @Override
    public void onStatistics(MonitoringStatistics monitoringStatistics) {

        if (System.currentTimeMillis() > lastTime + MILLIS_BETWEEN_STATISTICS_LOG) {

            logStatisticsByOperation(monitoringStatistics);

            logSummaryStatistics(monitoringStatistics);

            lastTime = System.currentTimeMillis();
        }
    }

    private void logStatisticsByOperation(MonitoringStatistics monitoringStatistics) {
        for (Map.Entry<Class<?>, ResourceStatistics> classStatsEntry : monitoringStatistics.getResourceClassStatistics().entrySet()) {

            for (Map.Entry<ResourceMethod, ResourceMethodStatistics> methodStatsEntry : classStatsEntry.getValue().getResourceMethodStatistics().entrySet()) {

                if (ApiResource.class.isAssignableFrom(classStatsEntry.getKey())) {
                    TimeWindowStatistics timeWindowStatistics = methodStatsEntry.getValue().getRequestStatistics().getTimeWindowStatistics().get(STATISTICS_TIME_WINDOW);

                    String fullPath = getFullPath(classStatsEntry.getKey(), methodStatsEntry.getKey().getInvocable().getHandlingMethod());

                    LOGGER.info("Last hour statistics - {} - {} - {}"
                            , methodStatsEntry.getKey().getHttpMethod()
                            , fullPath
                            , getStatsMessage(timeWindowStatistics));

                }
            }

        }
    }

    private String getFullPath(Class<?> resourceClass, Method resourceMethod) {
        Path resourcePath = resourceClass.getAnnotation(Path.class);
        Path methodPath = resourceMethod.getAnnotation(Path.class);

        StringBuilder fullPath = new StringBuilder();

        if (resourcePath != null) {
            fullPath.append(resourcePath.value());
        }

        if (methodPath != null) {
            fullPath.append(methodPath.value());
        }

        return fullPath.toString();
    }

    private void logSummaryStatistics(MonitoringStatistics monitoringStatistics) {
        TimeWindowStatistics timeWindowStatistics =  monitoringStatistics.getRequestStatistics().getTimeWindowStatistics().get(STATISTICS_TIME_WINDOW);
        LOGGER.info("Last hour statistics - Summary - {} ", getStatsMessage(timeWindowStatistics));
    }

    private String getStatsMessage(TimeWindowStatistics timeWindowStatistics) {
        return String.format("Total req: %d - Req/s: %.2f - Duration max min avg: %d %d %d ms"
                , timeWindowStatistics.getRequestCount()
                , timeWindowStatistics.getRequestsPerSecond()
                , timeWindowStatistics.getMaximumDuration()
                , timeWindowStatistics.getMinimumDuration()
                , timeWindowStatistics.getAverageDuration());
    }

}
