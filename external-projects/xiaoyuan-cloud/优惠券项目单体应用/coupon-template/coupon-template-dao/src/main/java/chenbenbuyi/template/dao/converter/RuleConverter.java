package chenbenbuyi.template.dao.converter;

import chenbenbuyi.template.api.beans.rules.TemplateRule;
import cn.hutool.json.JSONUtil;

import javax.persistence.AttributeConverter;

/**
 * @author chen
 * @date 2023/2/21 22:05
 * @Description
 */
public class RuleConverter implements AttributeConverter<TemplateRule, String> {

    @Override
    public String convertToDatabaseColumn(TemplateRule rule) {
        return JSONUtil.toJsonStr(rule);
    }

    @Override
    public TemplateRule convertToEntityAttribute(String rule) {
        return JSONUtil.toBean(rule, TemplateRule.class);
    }
}
