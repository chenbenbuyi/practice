package test;

import cn.hutool.json.JSONObject;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class TemplateConf implements Serializable {

    private static final long serialVersionUID = -4482106861306152693L;

    private String repo;

    private String username;

    private String password;

    private String templateJson;

    /**
     * 工具命令变动参数封装
     */
    private List<Conf> confs;

    @Data
    public static class Conf {

        private String command;

        private JSONObject params;
    }

}
