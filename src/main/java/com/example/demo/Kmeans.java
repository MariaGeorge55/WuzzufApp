//package com.example.demo;
//
//import smile.clustering.KMeans;
//import tech.tablesaw.api.DoubleColumn;
//import tech.tablesaw.api.NumberColumn;
//import tech.tablesaw.api.Table;
//import tech.tablesaw.columns.Column;
//import tech.tablesaw.api.StringColumn;
//import tech.tablesaw.util.DoubleArrays;
//
///**
// * K-Means clustering
// */
//public class Kmeans {
//
//    private final KMeans kMeans;
//    private final NumberColumn[] inputColumns;
//
//    public Kmeans(int k, NumberColumn... columns) {
//        double[][] input = DoubleArrays.to2dArray(columns);
//        this.kMeans = new KMeans(input, k);
//        this.inputColumns = columns;
//    }
//
//    public Kmeans(int k, int maxIterations, NumberColumn... columns) {
//        double[][] input = DoubleArrays.to2dArray(columns);
//        this.kMeans = new KMeans(input, k, maxIterations);
//        this.inputColumns = columns;
//    }
//
//    public int predict(double[] x) {
//        return kMeans.predict(x);
//    }
//
//    public double[][] centroids() {
//        return kMeans.centroids();
//    }
//
//    public double distortion() {
//        return kMeans.distortion();
//    }
//
//    public int getClusterCount() {
//        return 2;// kMeans.getNumClusters();
//    }
//
//    public int[] getClusterLabels() {
//        return kMeans.getClusterLabel();
//    }
//
//    public int[] getClusterSizes() {
//        return kMeans.getClusterSize();
//    }
//
//    public Table clustered(Column labels) {
//        Table table = Table.create("Clusters");
//        StringColumn labelColumn = StringColumn.create("Label");
//        NumberColumn clusterColumn = DoubleColumn.create("Cluster");
//        table.addColumns(labelColumn, clusterColumn);
//        int[] clusters = kMeans.getClusterLabel();
//        for (int i = 0; i < clusters.length; i++) {
//            labelColumn.appendCell(labels.getString(i));
//            clusterColumn.append(clusters[i]);
//        }
//        table = table.sortAscendingOn("Cluster", "Label");
//        return table;
//    }
//
//    public Table labeledCentroids() {
//        Table table = Table.create("Centroids");
//        StringColumn labelColumn = StringColumn.create("Cluster");
//        table.addColumns(labelColumn);
//
//        for (NumberColumn inputColumn : inputColumns) {
//            NumberColumn centroid = DoubleColumn.create(inputColumn.name());
//            table.addColumns(centroid);
//        }
//
//        double[][] centroids = kMeans.centroids();
//
//        for (int i = 0; i < centroids.length; i++) {
//            labelColumn.appendCell(String.valueOf(i));
//            double[] values = centroids[i];
//            for (int k = 0; k < values.length; k++) {
//                table.numberColumn(k + 1).append((double) values[k]);
//            }
//        }
//        return table;
//    }
//}
