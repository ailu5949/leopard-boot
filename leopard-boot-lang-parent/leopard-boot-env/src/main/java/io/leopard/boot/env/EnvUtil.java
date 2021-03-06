package io.leopard.boot.env;

public class EnvUtil {
	public static final String ENV_PROD = "prod";

	public static final String ENV_BENCHMARK = "benchmark";

	public static final String ENV_INTEGRATION = "integration";

	public static final String ENV_DEV = "dev";

	public static final String NAME_PROJECTNO = "LPROJECTNO";

	public static final String NAME_ENV = "LENV";

	// /**
	// * 获取当前项目名称.
	// *
	// * @return
	// */
	// public static String getProjectCode() {
	// String project = getenv(NAME_PROJECTNO);
	// if (StringUtils.isEmpty(project)) {
	// project = new ProjectInfoDaoImpl().getCode();
	// }
	// return project;
	// }

	public static void setProjectName(String projectName) {
		System.setProperty(NAME_PROJECTNO, projectName);
	}

	public static String getProjectCode() {
		String project = getenv(NAME_PROJECTNO);
		return project;
	}

	/**
	 * 获取当前运行环境(dev|test|prod)
	 * 
	 * @return
	 */
	public static String getEnv() {
		String env = getenv(NAME_ENV);
		if (env == null || env.length() == 0) {
			return EnvUtil.ENV_DEV;
		}
		return env;
	}

	public static boolean isProdEnv() {
		String env = EnvUtil.getEnv();
		return EnvUtil.ENV_PROD.equalsIgnoreCase(env);
	}

	public static boolean isDevEnv() {
		String env = EnvUtil.getEnv();
		// AssertData.notEmpty(env, "未配置环境变量" + NAME_ENV + ".");
		return !EnvUtil.ENV_PROD.equalsIgnoreCase(env);
	}

	protected static String getenv(String name) {
		String value = System.getenv(name);
		if (value == null || value.length() == 0) {
			value = System.getProperty(name);
		}
		return value;
	}

	public static String getModuleName(String path) {
		if (path.endsWith("/")) {
			throw new IllegalArgumentException("非法路径格式[" + path + "].");
		}
		int index = path.lastIndexOf("/");
		String moduleName = path.substring(index + 1);
		return moduleName;
	}

	/**
	 * 是否web环境.
	 * 
	 * @return
	 */
	public static boolean isWebserver() {
		throw new RuntimeException("not impl.");
	}

	/**
	 * 是否单元测试
	 * 
	 * @return
	 */
	public static boolean isJunit() {
		// TODO 这里改成通过堆栈信息做判断?
		String junit = System.getProperty("env.junit");
		return "true".equals(junit);
	}
}
