package com.puppet.sample;
import com.puppet.sample.Polyglot;

import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.Spark;
import spark.template.thymeleaf.ThymeleafTemplateEngine;
import spark.servlet.SparkApplication;

import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import static spark.Spark.get;
import static spark.Spark.before;

public class App implements SparkApplication
{
  private static String requestInfoToString(Request request) {
    StringBuilder sb = new StringBuilder();
    sb.append(request.requestMethod());
    sb.append(" " + request.url());
    sb.append(" " + request.body());
    return sb.toString();
  }

  public static void main(String[] args) {
    new App().init();
  }

  @Override
  public void init() {

    before((request, response) -> {
        System.out.println(requestInfoToString(request));
    });

    get("/", (request, response) -> {
      String urlpath = request.url().replaceAll("/[A-z][A-z]$", "");
      response.redirect(urlpath + "en");
      return null;
    });

    get("/:lang", App::helloWorld, new ThymeleafTemplateEngine());
  }

  public static ModelAndView helloWorld(Request req, Response res) {
    Map<String, Object> params = new HashMap<>();

    App t = new App();

    ResourceBundle resource = ResourceBundle.getBundle("application");
    params.put("version", resource.getString("app.version"));

    Polyglot p = new Polyglot();
    params.put("url", req.url().replaceAll("/[A-z][A-z]$", ""));

    switch(req.params(":lang")) {
      case "en": params.put("lang", p.enMsg());
                 break;
      case "sp": params.put("lang", p.spMsg());
                 break;
      case "zh": params.put("lang", p.zhMsg());
                 break;
      case "ar": params.put("lang", p.arMsg());
                 break;
      case "hi": params.put("lang", p.hiMsg());
                 break;
      default: String msg = "I don't know that language ~> ";
               msg += req.params(":lang");
               params.put("lang", msg); 
    }


    return new ModelAndView(params, "index");
  }

}
