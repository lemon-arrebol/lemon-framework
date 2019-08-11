package com.lemon.common.helper;

import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.lemon.common.util.StringUtils;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.Set;
import java.util.Stack;

/**
 * @param
 * @author lemon
 * @description Placeholder helper functions. Apollo apollo-client.jar#com.ctrip.framework.apollo.spring.property.PlaceholderHelper
 * @return
 * @date 2019-07-31 18:50
 */
@Slf4j
public class PlaceholderHelper {
    /**
     * Default placeholder prefix: {@value}.
     */
    public static final String DEFAULT_PLACEHOLDER_PREFIX = "${";

    /**
     * Default placeholder suffix: {@value}.
     */
    public static final String DEFAULT_PLACEHOLDER_SUFFIX = "}";

    /**
     * Default value separator: {@value}.
     */
    public static final String DEFAULT_VALUE_SEPARATOR = ":";

    /**
     * SPEL placeholder prefix: {@value}.
     */
    public static final String DEFAULT_EXPRESSION_PREFIX = "#{";

    /**
     * SPEL placeholder suffix: {@value}.
     */
    public static final String DEFAULT_EXPRESSION_SUFFIX = "}";

    /**
     * SPEL placeholder prefix
     */
    private final String spelPlaceholderPrefix;

    /**
     * SPEL placeholder suffix
     */
    private final String spelPlaceholderSuffix;

    /**
     * Default placeholder prefix
     */
    private final String placeholderPrefix;

    /**
     * Default placeholder suffix
     */
    private final String placeholderSuffix;

    /**
     * 熟知的简单前缀
     */
    private final String simplePrefix;

    /**
     * Default value separator
     */
    private final String valueSeparator;

    /**
     * 熟知的简单后缀前缀映射关系
     */
    private static final Map<String, String> wellKnownSimplePrefixes = Maps.newHashMap();

    static {
        wellKnownSimplePrefixes.put("}", "{");
        wellKnownSimplePrefixes.put("]", "[");
        wellKnownSimplePrefixes.put(")", "(");
    }

    public PlaceholderHelper() {
        this(PlaceholderHelper.DEFAULT_EXPRESSION_PREFIX, PlaceholderHelper.DEFAULT_EXPRESSION_SUFFIX,
                PlaceholderHelper.DEFAULT_PLACEHOLDER_PREFIX, PlaceholderHelper.DEFAULT_PLACEHOLDER_SUFFIX, PlaceholderHelper.DEFAULT_VALUE_SEPARATOR);
    }

    public PlaceholderHelper(String spelPlaceholderPrefix, String spelPlaceholderSuffix, String placeholderPrefix,
                             String placeholderSuffix, String valueSeparator) {
        Preconditions.checkNotNull(spelPlaceholderPrefix, "'spelPlaceholderPrefix' must not be null");
        Preconditions.checkNotNull(spelPlaceholderSuffix, "'spelPlaceholderSuffix' must not be null");
        Preconditions.checkNotNull(placeholderPrefix, "'placeholderPrefix' must not be null");
        Preconditions.checkNotNull(placeholderSuffix, "'placeholderSuffix' must not be null");

        this.spelPlaceholderPrefix = spelPlaceholderPrefix;
        this.spelPlaceholderSuffix = spelPlaceholderSuffix;

        this.placeholderPrefix = placeholderPrefix;
        this.placeholderSuffix = placeholderSuffix;
        String simplePrefixForSuffix = wellKnownSimplePrefixes.get(this.placeholderSuffix);

        if (simplePrefixForSuffix != null && this.placeholderPrefix.endsWith(simplePrefixForSuffix)) {
            this.simplePrefix = simplePrefixForSuffix;
        } else {
            this.simplePrefix = this.placeholderPrefix;
        }

        this.valueSeparator = valueSeparator;
    }

    /**
     * Extract keys from placeholder, e.g.
     * <ul>
     * <li>${some.key} => "some.key"</li>
     * <li>${some.key:${some.other.key:100}} => "some.key", "some.other.key"</li>
     * <li>${${some.key}} => "some.key"</li>
     * <li>${${some.key:other.key}} => "some.key"</li>
     * <li>${${some.key}:${another.key}} => "some.key", "another.key"</li>
     * <li>#{new java.text.SimpleDateFormat('${some.key}').parse('${another.key}')} => "some.key", "another.key"</li>
     *
     * <li>${some.key:${some.other.key:${some.key}}} => "some.key", "some.other.key"</li> 循环引用
     * </ul>
     */
    public Set<String> extractPlaceholderKeys(String propertyString) {
        Set<String> placeholderKeySet = Sets.newHashSet();

        if (!isNormalizedPlaceholder(propertyString) && !isExpressionWithPlaceholder(propertyString)) {
            return placeholderKeySet;
        }

        Stack<String> stack = new Stack<>();
        stack.push(propertyString);
        String strVal;

        while (!stack.isEmpty()) {
            strVal = stack.pop();
            int startIndex = strVal.indexOf(this.placeholderPrefix);

            if (startIndex == -1) {
                placeholderKeySet.add(strVal);
                log.info("placeholderKey[{}]", strVal);
                continue;
            }

            int endIndex = this.findPlaceholderEndIndex(strVal, startIndex);

            if (endIndex == -1) {
                log.info("strVal[{}], startIndex[{}], endIndex[{}], invalid placeholder", strVal, startIndex, endIndex);
                continue;
            }

            String placeholderCandidate = strVal.substring(startIndex + this.placeholderPrefix.length(), endIndex);
            log.info("strVal[{}], startIndex[{}], endIndex[{}], placeholderCandidate[{}]", strVal, startIndex, endIndex, placeholderCandidate);

            // ${${some.key}} --> some.key
            // ${${some.key:other.key}} --> ${some.key:other.key}
            // ${${some.key}:${another.key}} --> ${some.key}:${another.key}
            if (placeholderCandidate.startsWith(this.placeholderPrefix)) {
                stack.push(placeholderCandidate);
            } else {
                int separatorIndex = placeholderCandidate.indexOf(this.valueSeparator);

                // ${some.key} --> some.key
                // #{new java.text.SimpleDateFormat('${some.key}').parse('${another.key}')} --> some.key, another.key
                if (separatorIndex == -1) {
                    stack.push(placeholderCandidate);
                    log.info("strVal[{}], endIndex[{}], placeholderCandidate[{}], no separatorIndex[{}]", strVal, endIndex, placeholderCandidate, this.valueSeparator);
                } else {
                    // ${some.key} --> some.key
                    // ${some.key:  } --> some.key:
                    // ${some.key:100} --> some.key:100
                    // ${some.key:${some.other.key:100}} --> some.key:${some.other.key:100}
                    // ${some.key:${some.other.key:${some.key}}} --> some.key:${some.other.key:${some.key}} 循环引用

                    // some.key
                    stack.push(placeholderCandidate.substring(0, separatorIndex));
                    log.info("strVal[{}], separatorIndex({})[{}], key[{}]", strVal, this.valueSeparator, separatorIndex, placeholderCandidate.substring(0, separatorIndex));
                    // 无 | 空格 | 100 | ${some.other.key:100}
                    String defaultValuePart = this.normalizeToPlaceholder(placeholderCandidate.substring(separatorIndex + this.valueSeparator.length()));
                    log.info("strVal[{}], separatorIndex({})[{}], value[{}]", strVal, this.valueSeparator, separatorIndex, defaultValuePart);

                    if (!Strings.isNullOrEmpty(defaultValuePart)) {
                        stack.push(defaultValuePart);
                    }
                }
            }

            // has remaining part, e.g. ${a}.${b}
            if (endIndex + this.placeholderSuffix.length() < strVal.length() - 1) {
                String remainingPart = this.normalizeToPlaceholder(strVal.substring(endIndex + this.placeholderSuffix.length()));
                log.info("strVal[{}], endIndex[{}], remainingPart[{}]", strVal, endIndex, remainingPart);

                if (!Strings.isNullOrEmpty(remainingPart)) {
                    stack.push(remainingPart);
                }
            }
        }

        return placeholderKeySet;
    }

    /**
     * @param
     * @return
     * @description 是否合法规范化占位符 ${}
     * @author lemon
     * @date 2019-08-01 09:38
     */
    private boolean isNormalizedPlaceholder(String propertyString) {
        return propertyString.startsWith(this.placeholderPrefix) && propertyString.endsWith(this.placeholderSuffix);
    }

    /**
     * @param
     * @return
     * @description 是否合法SPEL表达式，其中包含法规范化占位符 #{${}}，不包含${}就没必要解析
     * @author lemon
     * @date 2019-08-01 09:38
     */
    private boolean isExpressionWithPlaceholder(String propertyString) {
        return propertyString.startsWith(this.spelPlaceholderPrefix) && propertyString.endsWith(this.spelPlaceholderSuffix)
                && propertyString.contains(this.placeholderPrefix);
    }

    /**
     * @param
     * @return
     * @description 取出strVal中规范化占位符子串: strVal ${com.lemon-framework.key} 取出为 com.lemon-framework.key
     * @author lemon
     * @date 2019-08-01 09:37
     */
    private String normalizeToPlaceholder(String strVal) {
        int startIndex = strVal.indexOf(this.placeholderPrefix);

        if (startIndex == -1) {
            return null;
        }

        int endIndex = strVal.lastIndexOf(this.placeholderSuffix);

        if (endIndex == -1) {
            return null;
        }

        return strVal.substring(startIndex, endIndex + this.placeholderSuffix.length());
    }

    /**
     * @param
     * @return
     * @description org.springframework.util.PropertyPlaceholderHelper#findPlaceholderEndIndex ${以{结尾只需查找{即可
     * @author lemon
     * @date 2019-07-31 18:53
     */
    private int findPlaceholderEndIndex(CharSequence buf, int startIndex) {
        int index = startIndex + this.placeholderPrefix.length();
        // 记录嵌套${}数量，}匹配${
        int withinNestedPlaceholder = 0;

        while (index < buf.length()) {
            if (StringUtils.substringMatch(buf, index, this.placeholderSuffix)) {
                if (withinNestedPlaceholder > 0) {
                    withinNestedPlaceholder--;
                    index = index + this.placeholderSuffix.length();
                } else {
                    return index;
                }
            } else if (StringUtils.substringMatch(buf, index, this.simplePrefix)) {
                withinNestedPlaceholder++;
                index = index + this.simplePrefix.length();
            } else {
                index++;
            }
        }

        return -1;
    }
}
