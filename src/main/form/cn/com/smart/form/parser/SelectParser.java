package cn.com.smart.form.parser;

import java.util.Map;

import org.springframework.stereotype.Component;

import cn.com.smart.form.enums.FormControlFieldType;
import cn.com.smart.form.enums.FormDataSourceType;

import com.mixsmart.enums.YesNoType;
import com.mixsmart.utils.StringUtils;

/**
 * 解析下拉框
 * @author lmq
 * @version 1.0 
 * @since 1.0
 * 2015年7月4日
 */
@Component
public class SelectParser implements IFormParser {

	@Override
	public String getPlugin() {
		return "select";
	}

	@Override
	public String parse(Map<String, Object> dataMap) {
		if(null == dataMap || dataMap.size()<1) {
			return null;
		}
		String content = StringUtils.handNull(dataMap.get("content"));
		String fromData = StringUtils.handNull(dataMap.get("from_data"));
		String dynamicLoad = StringUtils.handNull(dataMap.get("dynamicload"));
		String formControlField = StringUtils.handNull(dataMap.get("form_control_field"));
		FormControlFieldType fcfType = FormControlFieldType.getObjByValue(formControlField);
		String fcfClassName = null;
		if(null != fcfType) {
			fcfClassName = fcfType.getPluginValue();
		}
		FormDataSourceType sourceType = FormDataSourceType.getObj(fromData);
		switch (sourceType) {
		case DICT:
		case CUSTOM_URI:
			if(YesNoType.YES.getStrValue().equals(dynamicLoad)) {
				StringBuilder strBuild = new StringBuilder();
				strBuild.append("<select name=\""+StringUtils.handNull(dataMap.get("bind_table_field"))+"\" id=\""+dataMap.get("bind_table_field")+"\" data-label-name=\""+dataMap.get("title")+"\" ");
				strBuild.append(" class=\""+dataMap.get("class")+" cnoj-select "+StringUtils.handNull(fcfClassName)+" \"");
				strBuild.append(" style=\""+dataMap.get("style")+"\" data-uri=\""+dataMap.get("data_uri")+"\"></select>");
				content = strBuild.toString();
				break;
			}
		default:
			if(!StringUtils.isEmpty(content)) {
				/*StringBuilder strBuild = new StringBuilder();
				strBuild.append("<select name=\""+StringUtils.handNull(dataMap.get("bind_table_field"))+"\" id=\""+dataMap.get("bind_table_field")+"\" data-label-name=\""+dataMap.get("title")+"\" ");
				strBuild.append(" class=\""+dataMap.get("class")+" \"");
				strBuild.append(" style=\""+dataMap.get("style")+"\"></select>");
				content = strBuild.toString();*/
				
				content = content.replaceAll("<select([^>].*?)>", "<select class=\""+dataMap.get("class")+" "+StringUtils.handNull(fcfClassName)+"\" name=\"111\" >");
				content = content.replaceAll("(leipiplugins=\".*?\")|(field.*?=\".*?\")|(org.*?=\".*?\")|(from.*?=\".*?\")|(bind_.*?=\".*?\")", "");
				content = content.replaceAll("name=\".*?\"", "name=\""+dataMap.get("bind_table_field")+"\" id=\""+dataMap.get("bind_table_field")+"\" style=\""+dataMap.get("style")+"\"");
			}
			break;
		}
		return content;
	}
	
	
}
