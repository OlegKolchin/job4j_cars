package ru.job4j.cars.servlet;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import ru.job4j.cars.model.*;
import ru.job4j.cars.store.AdStore;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Properties;
import java.util.stream.Collectors;

@WebServlet(name = "AdServlet", value = "/ad.do")
@MultipartConfig
public class AdServlet extends HttpServlet {
    private static final Gson GSON = new GsonBuilder().create();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json; charset=utf-8");
        OutputStream output = resp.getOutputStream();
        String json = GSON.toJson(AdStore.getInstance().findAll());
        output.write(json.getBytes(StandardCharsets.UTF_8));
        output.flush();
        output.close();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String brand = extractPartValue("brand", req);
        String model = extractPartValue("model", req);
        String body = extractPartValue("body", req);
        String description = extractPartValue("description", req);
        User user = (User) req.getSession().getAttribute("session_user");
        String fileName = String.format("%d_%s_%s.png", user.getId(), brand, model);
        String fileUrl = getImgDirectory() + fileName;
        req.getPart("formFile").write(fileUrl);

        AdStore.getInstance().save(Ad.of(description, fileUrl, Car.of(model, Brand.of(brand), Body.of(body)), user));

    }

    private String extractPartValue(String partName, HttpServletRequest req) throws ServletException, IOException {
        String value = new BufferedReader(
                new InputStreamReader(req.getPart(partName).getInputStream(), StandardCharsets.UTF_8))
                .lines()
                .collect(Collectors.joining(""));
        return value;
    }

    private String getImgDirectory() throws IOException {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        InputStream input = classLoader.getResourceAsStream("app.properties");
        Properties cfg = new Properties();
        cfg.load(input);
        return cfg.getProperty("imageDirectory");
    }
}
