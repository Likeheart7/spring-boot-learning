/*
 * Copyright 2012-2021 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.springframework.boot.logging;

import java.lang.reflect.Constructor;
import java.util.Collections;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;

import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.util.Assert;
import org.springframework.util.ClassUtils;
import org.springframework.util.StringUtils;

/**
 * 日志系统的统一抽象
 * Common abstraction over logging systems.
 *
 * @author Phillip Webb
 * @author Dave Syer
 * @author Andy Wilkinson
 * @author Ben Hale
 * @since 1.0.0
 */
public abstract class LoggingSystem {

	/**
	 * A System property that can be used to indicate the {@link LoggingSystem} to use.
	 */
	public static final String SYSTEM_PROPERTY = LoggingSystem.class.getName();

	/**
	 * The value of the {@link #SYSTEM_PROPERTY} that can be used to indicate that no
	 * {@link LoggingSystem} should be used.
	 */
	public static final String NONE = "none";

	/**
	 * The name used for the root logger. LoggingSystem implementations should ensure that
	 * this is the name used to represent the root logger, regardless of the underlying
	 * implementation.
	 */
	public static final String ROOT_LOGGER_NAME = "ROOT";

	private static final LoggingSystemFactory SYSTEM_FACTORY = LoggingSystemFactory.fromSpringFactories();

	/**
	 * Return the {@link LoggingSystemProperties} that should be applied.
	 * @param environment the {@link ConfigurableEnvironment} used to obtain value
	 * @return the {@link LoggingSystemProperties} to apply
	 * @since 2.4.0
	 */
	public LoggingSystemProperties getSystemProperties(ConfigurableEnvironment environment) {
		return new LoggingSystemProperties(environment);
	}

	/**
	 * 初始化之前调用，目的是减少日志输出
	 * Reset the logging system to be limit output. This method may be called before
	 * {@link #initialize(LoggingInitializationContext, String, LogFile)} to reduce
	 * logging noise until the system has been fully initialized.
	 */
	public abstract void beforeInitialize();

	/**
	 * 初始化日志
	 * Fully initialize the logging system.
	 * @param initializationContext the logging initialization context
	 * @param configLocation a log configuration location or {@code null} if default
	 * initialization is required
	 * @param logFile the log output file that should be written or {@code null} for
	 * console only output
	 */
	public void initialize(LoggingInitializationContext initializationContext, String configLocation, LogFile logFile) {
	}

	/**
	 * 清除日志
	 * Clean up the logging system. The default implementation does nothing. Subclasses
	 * should override this method to perform any logging system-specific cleanup.
	 */
	public void cleanUp() {
	}

	/**
	 * Returns a {@link Runnable} that can handle shutdown of this logging system when the
	 * JVM exits. The default implementation returns {@code null}, indicating that no
	 * shutdown is required.
	 * @return the shutdown handler, or {@code null}
	 */
	public Runnable getShutdownHandler() {
		return null;
	}

	/**
	 * 获取支持的日志级别
	 * Returns a set of the {@link LogLevel LogLevels} that are actually supported by the
	 * logging system.
	 * @return the supported levels
	 */
	public Set<LogLevel> getSupportedLogLevels() {
		return EnumSet.allOf(LogLevel.class);
	}

	/**
	 * 设置日志级别
	 * Sets the logging level for a given logger.
	 * @param loggerName the name of the logger to set ({@code null} can be used for the
	 * root logger).
	 * @param level the log level ({@code null} can be used to remove any custom level for
	 * the logger and use the default configuration instead)
	 */
	public void setLogLevel(String loggerName, LogLevel level) {
		throw new UnsupportedOperationException("Unable to set log level");
	}

	/**
	 * 获取日志配置
	 * Returns a collection of the current configuration for all a {@link LoggingSystem}'s
	 * loggers.
	 * @return the current configurations
	 * @since 1.5.0
	 */
	public List<LoggerConfiguration> getLoggerConfigurations() {
		throw new UnsupportedOperationException("Unable to get logger configurations");
	}

	/**
	 * Returns the current configuration for a {@link LoggingSystem}'s logger.
	 * @param loggerName the name of the logger
	 * @return the current configuration
	 * @since 1.5.0
	 */
	public LoggerConfiguration getLoggerConfiguration(String loggerName) {
		throw new UnsupportedOperationException("Unable to get logger configuration");
	}

	/**
	 * 获取日志系统
	 * Detect and return the logging system in use. Supports Logback and Java Logging.
	 * @param classLoader the classloader
	 * @return the logging system
	 */
	public static LoggingSystem get(ClassLoader classLoader) {
		// 获取系统属性
		String loggingSystemClassName = System.getProperty(SYSTEM_PROPERTY);
		// 系统属性存在
		if (StringUtils.hasLength(loggingSystemClassName)) {
			// 是不是NONE
			if (NONE.equals(loggingSystemClassName)) {
				// 空的日志系统
				return new NoOpLoggingSystem();
			}
			// 获取配置的日志系统对应的日志系统实例
			return get(classLoader, loggingSystemClassName);
		}
		// 获取具体日志系统，默认情况下是LogbackLoggingSystem
		LoggingSystem loggingSystem = SYSTEM_FACTORY.getLoggingSystem(classLoader);
		Assert.state(loggingSystem != null, "No suitable logging system located");
		return loggingSystem;
	}

	/**
	 * 如果有配置日志相关的系统属性，会通过这个方法来获取日志系统
	 * @param classLoader	类加载器
	 * @param loggingSystemClassName 日志系统的类名
	 */
	private static LoggingSystem get(ClassLoader classLoader, String loggingSystemClassName) {
		try {
			Class<?> systemClass = ClassUtils.forName(loggingSystemClassName, classLoader);
			Constructor<?> constructor = systemClass.getDeclaredConstructor(ClassLoader.class);
			constructor.setAccessible(true);
			return (LoggingSystem) constructor.newInstance(classLoader);
		}
		catch (Exception ex) {
			throw new IllegalStateException(ex);
		}
	}

	/**
	 * {@link LoggingSystem} that does nothing.
	 */
	static class NoOpLoggingSystem extends LoggingSystem {

		@Override
		public void beforeInitialize() {

		}

		@Override
		public void setLogLevel(String loggerName, LogLevel level) {

		}

		@Override
		public List<LoggerConfiguration> getLoggerConfigurations() {
			return Collections.emptyList();
		}

		@Override
		public LoggerConfiguration getLoggerConfiguration(String loggerName) {
			return null;
		}

	}

}
