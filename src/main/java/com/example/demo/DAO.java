package com.example.demo;

import org.apache.commons.csv.CSVFormat;

import org.knowm.xchart.*;
import org.knowm.xchart.style.Styler;
import smile.clustering.KMeans;
import smile.clustering.PartitionClustering;
import smile.data.DataFrame;
import smile.data.Tuple;
import smile.data.measure.NominalScale;
import smile.data.vector.IntVector;
import smile.io.Read;
import smile.plot.swing.ScatterPlot;
import tech.tablesaw.api.Table;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.net.URISyntaxException;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;


import java.util.ArrayList;

public class DAO {
    protected DataFrame df;
    String path = "src/main/resources/Wuzzuf_Jobs.csv";

    { readCSV( path); }

    public DataFrame readCSV(String path) {
        CSVFormat format = CSVFormat.DEFAULT.withFirstRecordAsHeader ();
        try {
            df = Read.csv (path, format);
            df = df.select ("Title", "Company", "Location", "Type", "Level","YearsExp","Country","Skills");
            df = df.omitNullRows();
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace ();
        }
        return df;
    }

    public List<WuzzufData> getWuzzufList() throws IOException {
        assert df != null;
        List<WuzzufData> allData = new ArrayList<>();
        ListIterator<Tuple> iterator =cleanData();// df.stream ().collect (Collectors.toList ()).listIterator ();
        while (iterator.hasNext ()) {
            Tuple t = iterator.next ();
            WuzzufData p = new WuzzufData ();
            WuzzufData.id += 1;
            p.setTitle ((String) t.get ("Title"));
            p.setCompany ((String) t.get ("Company"));
            p.setCountry ((String) t.get ("Country"));
            p.setLevel ((String) t.get ("Level"));
            p.setLocation ((String) t.get ("Location"));
            p.setYearsExp ((String) t.get ("YearsExp"));
            p.setType ((String) t.get ("Type"));
            p.setSkills ((String) t.get ("Skills"));
            allData.add (p);
        }
        return allData;
    }

    public String viewData() throws IOException {

        assert df != null;
        System.out.println(df.get(0, 1));
        String html=String.format("<h1 style=\"text-align:center;font-family:verdana;background-color:lightblue;\">%s</h1>", "Sample From The Data") +
                "<table style=\"width:100%;text-align: center\">" +
                "<tr><th>Title</th><th>Company</th><th>Location</th><th>Type</th><th>Level</th><th>YearsExp</th><th>Country</th><th>Skills</th></tr>";
        List<WuzzufData>  lisTAlldata =getWuzzufList();
        for (int r=0;r<20;r++){
            html += "<tr>\n" ;
                html += "<td>"+ lisTAlldata.get(r).getTitle()+"</td>\n" ;
                html += "<td>"+ lisTAlldata.get(r).getCompany()+"</td>\n" ;
                html += "<td>"+ lisTAlldata.get(r).getLocation()+"</td>\n" ;
                html += "<td>"+ lisTAlldata.get(r).getType()+"</td>\n" ;
                html += "<td>"+ lisTAlldata.get(r).getLevel()+"</td>\n" ;
                html += "<td>"+ lisTAlldata.get(r).getYearsExp()+"</td>\n" ;
                html += "<td>"+ lisTAlldata.get(r).getCountry()+"</td>\n" ;
                html += "<td>"+ lisTAlldata.get(r).getSkills()+"</td>\n" ;
            html += "</tr>\n" ;
        }
        html += "</table>";
        return html;
    }

    public ListIterator<Tuple> cleanData() throws IOException {

       df=df.omitNullRows();
       ListIterator<Tuple> list= df.stream().filter(row -> !row.getString("YearsExp")
                .equals("null Yrs of Exp")).collect(Collectors.toList()).listIterator ();

        return list;

    }

    public static String index(){
        String body = "<style>\n" +
                "        .btn-group button {\n" +
                "          background-color: #2196F3; /* Green background */\n" +
                "          border: 1px solid background; /* Green border */\n" +
                "          color: white; /* White text */\n" +
                "          padding: 12px 30px; /* Some padding */\n" +
                "          cursor: pointer; /* Pointer/hand icon */\n" +
                "          width: 75%; /* Set a width if needed */\n" +
                "          display: block; /* Make the buttons appear below each other */\n" +
                "          margin: auto;\n" +
                "          margin-bottom: 5px;\n" +
                "          align-self: auto ;\n" +
                "          border-radius: 12px;\n" +
                "          font-size: 16px;\n" +
                "          font-family:cursive  ;\n" +
                "        }\n" +
                "\n" +
                "        .btn-group button:not(:last-child) {\n" +
                "          border-bottom: none; /* Prevent double borders */\n" +
                "        }\n" +
                "\n" +
                "        /* Add a background color on hover */\n" +
                "        .btn-group button:hover {\n" +
                "          background-color: #0b7dda;\n" +
                "        }\n" +
                "        .center {\n" +
                "            margin: auto;\n" +
                "            width: 60%;\n" +
                "            border: 0px solid #73AD21;\n" +
                "            padding: 10px;\n" +
                "\n" +
                "         }\n" +
                "        #GFG{\n" +
                "            text-decoration: none;\n" +
                "        }\n" +
                "        h1 {\n" +
                "            color: whitesmoke;\n" +
                "            background: black;\n" +
                "            font-family: verdana;\n" +
                "            font-size: 200%;\n" +
                "            padding: 12px 30px;\n" +
                "            margin-top: 0px;\n" +
                "          }\n" +
                "        </style><div class=\"btn-group center\">\n" +
                "\n" +
                "    <a id = \"GFG\" href=\"viewData\"><button> Read CSV and Display Some From It </button></a>\n" +
                "    <a id = \"GFG\" href=\"Summary\"><button> Display Structure and Summary for the Data</button></a>\n" +

                "    <a id = \"GFG\" href=\"mostDcompany\"><button>  What are the most demanding companies for jobs? </button></a>\n" +
                "    <a id = \"GFG\" href=\"companyChart\"><button> Display Most demanding companies for jobs in Pie Chart</button></a>\n" +

                "    <a id = \"GFG\" href=\"jobsTitle\"><button> Most popular jobs Title</button></a>\n" +
                "    <a id = \"GFG\" href=\"jobChart\"><button> Display the most popular job titles </button></a>\n" +

                "    <a id = \"GFG\" href=\"jobsArea\"><button> Most popular jobs Areas</button></a>\n" +
                "    <a id = \"GFG\" href=\"locationChart\"><button> Display The Most popular areas in Bar Chart</button></a>\n" +

                "    <a id = \"GFG\" href=\"viewSkills\"><button>  Most Important skills </button></a>\n" +
                "    <a id = \"GFG\" href=\"min_year_exp\"><button>Factorized Years Experience</button></a>\n" +
                "    <a id = \"GFG\" href=\"kmean_cluster\"><button> k means</button></a>\n" +
                "</div>";
        return body;
    }

    public String Summary() throws IOException {
        // Using TableSaw
        Table Data1 = Table.read().csv(path);
        String html=String.format("<h1 style=\"text-align:center;font-family:verdana;background-color:lightblue;\">%s</h1>", "Summary For The Data") +
                "<table style=\"width:100%;text-align: center\">" +
                "<tr><th>Summary</th><th>Title</th><th>Company</th><th>Location</th><th>Type</th><th>Level</th><th>YearsExp</th><th>Country</th><th>Skills</th></tr>";

        int counts = Data1.summary().columnCount();
        for (int col = 0; col < 4; col++) {
            html += "<tr>\n" ;
            for (int ind = 0; ind < counts; ind++) {
                html += "<td>"+Data1.summary().column(ind).get(col)+"</td>\n";
            }
            html += "</tr>\n" ;
        }
        html += "</table>\n" ;
        return html;
    }

    public Map<String, Integer> importantSkills(DataFrame data) {
        System.out.println(data.size());
        ArrayList<String > skills_array=new ArrayList<>();
        String skill_row;
        for (int row = 0; row < data.size()-1; row++) {

            skill_row=((String) df.get(row, 7));
            String[] sub_skills = skill_row.split(",");
            for(String one_skill :sub_skills) {
                skills_array.add(one_skill.toLowerCase());
            }
        }
        // to get the frequency of each word
        Set<String> unique = new HashSet<>(skills_array);
        Map<String ,Integer> sorted=new TreeMap<>();
        for (String key : unique) {
            sorted.put(key , Collections.frequency(skills_array, key));
        }
        // sorting map and showing the most important 20
        Map<String, Integer> mappings = sorted.entrySet().stream()
                .sorted(Collections.reverseOrder(Map.Entry.comparingByValue()))
                .limit(20)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
        mappings.forEach((k,v)->System.out.println(k+"="+v));

       return  mappings;
    }

    public String view_importantSkills(){

        Map<String,Integer> skilll= importantSkills(df);
        var ref = new Object() {
            String html = "<h1 style=\"text-align:center;font-family:verdana;background-color:lightblue;\">Most Important skills</h1>";
        };
        skilll.forEach((k,v)->   ref.html +="<h3 style=\"text-align:center;padding-left: 50px;\"> "+ k.toUpperCase()+"  \t\t\t   \t =  \t  "+v+"</h3>");
         return ref.html;
    }

    public  String min_year_exp(){
       df= df.merge (IntVector.of ("Factorized Years", encodeCategory (df, "YearsExp")));
            String html=String.format("<h1 style=\"text-align:center;font-family:verdana;background-color:lightblue;\">%s</h1>", "Factorized DataFrame") +
                    "<table style=\"width:100%;text-align: center\">" +
                    "<tr><th>Title</th><th>Company</th><th>Location</th><th>Type</th><th>Level</th><th>YearsExp</th><th>Country</th><th>Skills</th><th>Factorized Years</th></tr>";
            for (int row = 0; row < 20; row++) {
                html += "<tr>\n" ;
                if(! df.get(row, 5).equals( "null Yrs of Exp")){
                for (int col = 0; col < 9; col++) {
                    html += "<td>"+ df.get(row, col)+"</td>\n" ;
                    }
                }
                html += "</tr>\n" ;
            }
            html += "</table>";
            return html;
    }

    public static int[] encodeCategory(DataFrame df, String columnName) {
        String[] values = df.stringVector (columnName).distinct ().toArray (new String[]{});
        int[] newValues = df.stringVector (columnName).factorize (new NominalScale(values)).toIntArray ();
        return newValues;
    }

    public String  mostDcompany() throws IOException {

        List<WuzzufData>  lisTAlldata =getWuzzufList();
        List<String> companies=  graphCompany(lisTAlldata);
        var ref = new Object() {
            String html = "<h1 style=\"text-align:center;font-family:verdana;background-color:lightblue;\">Most Demanding Companies</h1>";
        };
        companies.forEach((v)->   ref.html +="<h3 style=\"text-align:center;padding-left: 50px;\"> "+ v.toUpperCase()+"</h3>");
        return ref.html;

      //  List<String> mostDemandJobs=graphJobTitle(lisTAlldata);

    }

    public String  jobsTitle() throws IOException {

        List<WuzzufData>  lisTAlldata =getWuzzufList();
        List<String> mostDemandJobs=graphJobTitle(lisTAlldata);
          var ref = new Object() {
            String html = "<h1 style=\"text-align:center;font-family:verdana;background-color:lightblue;\">Most Popular Job Title</h1>";
        };
        mostDemandJobs.forEach((v)->   ref.html +="<h3 style=\"text-align:center;padding-left: 50px;\"> "+ v.toUpperCase()+"</h3>");
        return ref.html;
    }

    public List<String> graphCompany(List<WuzzufData> wuzzufList) throws IOException {
        //filter to get a map of passenger class and total number of passengers in each class
        Map<String, Long> result =
                wuzzufList.stream ().collect (
                        Collectors.groupingBy (
                                WuzzufData::getCompany, Collectors.counting ()
                        )
                );
        // Create Chart
        PieChart chart = new PieChartBuilder().width (800).height (600).title ("Most Company counts").build ();
        List<String> C_Names = new ArrayList<>();

        // Series
        for (String element: result.keySet()) {
            long num = result.get(element);
            if (num > 20) {
                C_Names.add(element);
                chart.addSeries(element, result.get(element));
            }
        }
        // Show it
        BitmapEncoder.saveJPGWithQuality(chart, "target/classes/companychart.jpg", 0.95f);
      //  new SwingWrapper(chart).displayChart ();
        return C_Names;
    }

    public List<String> graphJobTitle(List<WuzzufData> wuzzufList) throws IOException  {
        //filter to get a map of passenger class and total number of passengers in each class
        Map<String, Long> jobMap =
                wuzzufList.stream().collect(
                        Collectors.groupingBy(
                                WuzzufData::getTitle, Collectors.counting()
                        )
                );
        CategoryChart chart = new CategoryChartBuilder().width(1900).height(1000).title("Job Title").xAxisTitle("jobs").yAxisTitle("Counts").build();
        chart.getStyler().setHasAnnotations(true);
        chart.getStyler().setLegendPosition(Styler.LegendPosition.OutsideE);
        chart.getStyler().setStacked(true);
        List<String> j_Names = new ArrayList<>();
        List<Float> j_COUNTS = new ArrayList<>();
        for (String element: jobMap.keySet()) {
            long num1 = jobMap.get(element);
            if (num1 > 15) {
                j_COUNTS.add(Float.valueOf(jobMap.get(element)));
                j_Names.add(element);
            }
        }
        chart.addSeries("job_count", j_Names , j_COUNTS );
        BitmapEncoder.saveJPGWithQuality(chart, "target/classes/jobchart.jpg", 0.95f);
        //new SwingWrapper(chart).displayChart();
        return j_Names;
    }

    public String  areas() throws IOException {
        List<WuzzufData>  lisTAlldata =getWuzzufList();
        List<String> mostDemandJobs=garphAreas(lisTAlldata);
        var ref = new Object() {
            String html = "<h1 style=\"text-align:center;font-family:verdana;background-color:lightblue;\">Most Popular Areas</h1>";
        };
        mostDemandJobs.forEach((v)->   ref.html +="<h3 style=\"text-align:center;padding-left: 50px;\"> "+ v.toUpperCase()+"</h3>");
        return ref.html;
    }

    public List<String> garphAreas(List<WuzzufData> wuzzufList) throws IOException  {
        //filter to get a map of passenger class and total number of passengers in each class
        Map<String, Long> areaMap =
                wuzzufList.stream().collect(
                        Collectors.groupingBy(
                                WuzzufData::getLocation, Collectors.counting()
                        )
                );
        CategoryChart chart = new CategoryChartBuilder().width(1900).height(1000).title("Popular Areas").xAxisTitle("Areas").yAxisTitle("Counts").build();
        chart.getStyler().setHasAnnotations(true);
        chart.getStyler().setLegendPosition(Styler.LegendPosition.OutsideE);
        chart.getStyler().setStacked(true);
        List<String> a_Names = new ArrayList<>();
        List<Float> a_COUNTS = new ArrayList<>();
        for (String element: areaMap.keySet()) {
            long num1 = areaMap.get(element);
            if (num1 > 20) {
                a_COUNTS.add(Float.valueOf(areaMap.get(element)));
                a_Names.add(element);
            }
        }
        chart.addSeries("job_count", a_Names , a_COUNTS );
        BitmapEncoder.saveJPGWithQuality(chart, "target/classes/locationchart.jpg", 0.95f);
        //new SwingWrapper(chart).displayChart();
        return a_Names;
    }

    public double[][] newDataframe() throws IOException {
        DataFrame  df2 = df.merge(IntVector.of("Factorized Title", encodeCategory(df, "Title")));
        df2 = df2.merge(IntVector.of("Factorized Company", encodeCategory(df, "Company")));
        // Write.csv(df2, get("I:/14-Java UML/Project/newWuzzuf.csv"));
        df=df2;
        DataFrame kmean = df2.select("Factorized Company", "Factorized Title");
        double[][] KMEAN= kmean.toArray();
        return KMEAN;
    }

    public void kmean_cluster() throws IOException {
        double [][] KMEANS=newDataframe();
        KMeans clusters = PartitionClustering.run(5, () -> KMeans.fit(KMEANS,3));
        try
        {
            ScatterPlot.of(KMEANS, clusters.y, '.').canvas().setAxisLabels("Companies", "Jobs").window();
        }
        catch (InvocationTargetException | InterruptedException e)
        {e.printStackTrace();}


        // Create Chart
        XYChart chart = new XYChartBuilder().width(800).height(600).build();

        // Customize Chart
        chart.getStyler().setDefaultSeriesRenderStyle(XYSeries.XYSeriesRenderStyle.Scatter);
        chart.getStyler().setChartTitleVisible(false);
        chart.getStyler().setLegendPosition(Styler.LegendPosition.InsideSW);
        chart.getStyler().setMarkerSize(2);

        // Series
        double[] comp = df.column("Factorized Company").toDoubleArray();
        double[] tit = df.column("Factorized Title").toDoubleArray();

        chart.addSeries("Gaussian Blob", comp, tit);
        new SwingWrapper(chart).displayChart ();

    }




}





