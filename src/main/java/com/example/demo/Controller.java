package com.example.demo;

import org.springframework.core.io.ClassPathResource;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.springframework.util.MimeTypeUtils.IMAGE_JPEG_VALUE;

@RestController
public class Controller {

    @GetMapping("/")
    public String index() throws IOException {
        return new DAO().index();
    }

    @GetMapping("/viewData")
    public String viewCleanedData() throws IOException {
       DAO d= new DAO();
       d.cleanData();
        return d.viewData();
    }

    @GetMapping("/viewSkills")
    public String view_importantSkills() {
        return new DAO().view_importantSkills();
    }

    @GetMapping("/min_year_exp")
    public String min_year_exp() {
        return new DAO().min_year_exp();
    }


    @GetMapping("/kmean_cluster")
    public void kmean_cluster() throws IOException {
         new DAO().kmean_cluster();
    }

    @GetMapping("/mostDcompany")
    public String mostDcompany() throws IOException {
      return  new DAO().mostDcompany();
    }
    @GetMapping("/jobsTitle")
    public String jobsTitle() throws IOException {
        return  new DAO().jobsTitle();
    }
    @GetMapping("/jobsArea")
    public String areas() throws IOException {
        return  new DAO().areas();
    }
    @GetMapping("/Summary")
    public String Summary() throws IOException {
        return new DAO().Summary();
    }

    @RequestMapping(value = "/summaryPic", method = RequestMethod.GET, produces = IMAGE_JPEG_VALUE)
    public void getSummaryPic(HttpServletResponse response) throws IOException {
        ClassPathResource imgFile = new ClassPathResource("summary.jpg");
        response.setContentType(IMAGE_JPEG_VALUE);
        StreamUtils.copy(imgFile.getInputStream(), response.getOutputStream());
    }

    @RequestMapping(value = "/companyChart", method = RequestMethod.GET, produces = IMAGE_JPEG_VALUE)
    public void getCompanyOffersChart(HttpServletResponse response) throws IOException {

//        services.PieChartPath();
        ClassPathResource imgFile = new ClassPathResource("companychart.jpg");

        response.setContentType(IMAGE_JPEG_VALUE);
        StreamUtils.copy(imgFile.getInputStream(), response.getOutputStream());
    }
    @RequestMapping(value = "/locationChart", method = RequestMethod.GET, produces = IMAGE_JPEG_VALUE)
    public void getLocationChart(HttpServletResponse response) throws IOException {

//        services.PieChartPath();
        ClassPathResource imgFile = new ClassPathResource("locationchart.jpg");

        response.setContentType(IMAGE_JPEG_VALUE);
        StreamUtils.copy(imgFile.getInputStream(), response.getOutputStream());
    }

    @RequestMapping(value = "/jobChart", method = RequestMethod.GET, produces = IMAGE_JPEG_VALUE)
    public void getjobChart(HttpServletResponse response) throws IOException {

//        services.PieChartPath();
        ClassPathResource imgFile = new ClassPathResource("jobchart.jpg");

        response.setContentType(IMAGE_JPEG_VALUE);
        StreamUtils.copy(imgFile.getInputStream(), response.getOutputStream());
    }
    @RequestMapping(value = "/viewDataPic", method = RequestMethod.GET, produces = IMAGE_JPEG_VALUE)
    public void getData(HttpServletResponse response) throws IOException {

//        services.PieChartPath();
        ClassPathResource imgFile = new ClassPathResource("viewdata.jpg");

        response.setContentType(IMAGE_JPEG_VALUE);
        StreamUtils.copy(imgFile.getInputStream(), response.getOutputStream());
    }


}
