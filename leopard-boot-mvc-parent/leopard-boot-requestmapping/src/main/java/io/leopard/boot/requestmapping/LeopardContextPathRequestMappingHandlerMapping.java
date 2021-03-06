package io.leopard.boot.requestmapping;

import java.lang.reflect.Method;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;

/**
 * 增加ContextPath支持
 * 
 * @author 谭海潮
 *
 */
public class LeopardContextPathRequestMappingHandlerMapping extends LeopardRequestMappingHandlerMapping {
	@Value("${leopard.context-path:}") // 默认为空
	private String contextPath;

	// protected Set<String> addContextPath(Set<String> patterns) {
	// Set<String> patternSet = new LinkedHashSet<>();
	// for (String pattern : patterns) {
	// pattern = contextPath + "/" + pattern;
	// pattern = pattern.replace("//", "/");
	// patternSet.add(pattern);
	// }
	// return patternSet;
	// }

	@Override
	protected RequestMappingInfo getMappingForMethod(Method method, Class<?> handlerType) {
		RequestMappingInfo info = super.getMappingForMethod(method, handlerType);
		if (info != null) {
			boolean isEnableContextPath = isEnableContextPath(method, handlerType);
			if (isEnableContextPath) {
				RequestMappingInfo context = RequestMappingInfo.paths(contextPath).options(this.getConfig()).build();
				info = context.combine(info);
			}
			// Set<String> patterns = info.getPatternsCondition().getPatterns();
			// Set<String> patternSet = this.addContextPath(patterns);
			// info.getPatternsCondition().getPatterns();
			// // patterns.clear();
			// // patterns.addAll(patternSet);
			// // System.err.println("default patterns:" + info.getPatternsCondition().getPatterns());
		}
		return info;
	}

	protected boolean isEnableContextPath(Method method, Class<?> handlerType) {
		if (StringUtils.isEmpty(contextPath)) {
			return false;
		}
		if (handlerType.getName().startsWith("io.xiaoniu.")) {// TODO 暂时写死
			return false;
		}
		return true;
	}

}
